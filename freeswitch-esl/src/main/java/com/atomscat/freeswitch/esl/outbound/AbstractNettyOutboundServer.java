/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.atomscat.freeswitch.esl.outbound;

import com.atomscat.freeswitch.esl.OutboundServerService;
import com.atomscat.freeswitch.esl.outbound.handler.OutboundChannelHandler;
import com.atomscat.freeswitch.esl.outbound.listener.ChannelEventListener;
import com.atomscat.freeswitch.esl.outbound.option.OutboundServerOption;
import com.atomscat.freeswitch.esl.transport.message.EslFrameDecoder;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author : <a href="everyone@aliyun.com">everyone</a>
 */
abstract class AbstractNettyOutboundServer extends AbstractService implements ChannelEventListener, OutboundServerService {

    final ServerBootstrap bootstrap;
    final EventLoopGroup workerGroup;
    final EventLoopGroup parentGroup;
    final ExecutorService publicExecutor;
    final OutboundServerOption option;

    final Logger log = LoggerFactory.getLogger(getClass());

    private static final ThreadFactory onEslThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("outbound-onEsl-pool-%d").build();

    //专门接收订阅事件的单一线程池(保证顺序)
    private static final ExecutorService onEslExecutor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(100000), onEslThreadFactory);

    private static final ThreadFactory onConnectThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("outbound-onConnect-pool-%d").build();

    //专用于处理新来电onConnect的多线程池
    private static final ExecutorService onConnectExecutor = new ThreadPoolExecutor(32, 512,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2048), onConnectThreadFactory);


    AbstractNettyOutboundServer(OutboundServerOption option) {
        this.option = option;

        bootstrap = new ServerBootstrap();

        publicExecutor = new ScheduledThreadPoolExecutor(option.publicExecutorThread(),
                new DefaultThreadFactory("Outbound-Executor", true));
        parentGroup = new NioEventLoopGroup(option.parentGroupThread(), new DefaultThreadFactory("outbound-parent", true));
        workerGroup = new NioEventLoopGroup(option.workerGroupThread(), new DefaultThreadFactory("outbound-worker", true));
        bootstrap.group(parentGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_SNDBUF, option.sndBufSize())
                .childOption(ChannelOption.SO_RCVBUF, option.rcvBufSize())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("encoder", new StringEncoder());
                        pipeline.addLast("decoder", new EslFrameDecoder(8192, true));
                        pipeline.addLast("server-idle-handler", new IdleStateHandler(0, 0, option.readerIdleTimeSeconds(), MILLISECONDS));
                        // now the inbound client logic
                        pipeline.addLast("clientHandler", new OutboundChannelHandler(AbstractNettyOutboundServer.this, publicExecutor, onEslExecutor, onConnectExecutor));
                    }
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.startAsync();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdown() {
        this.stopAsync();
    }

}
