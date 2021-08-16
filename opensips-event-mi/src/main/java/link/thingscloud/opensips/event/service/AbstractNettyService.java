package link.thingscloud.opensips.event.service;

import com.google.common.util.concurrent.AbstractService;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import link.thingscloud.opensips.event.EventClientService;
import link.thingscloud.opensips.event.handler.EventChannelHandler;
import link.thingscloud.opensips.event.listener.ChannelEventListener;
import link.thingscloud.opensips.event.option.EventClientOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author th158
 */
public abstract class AbstractNettyService extends AbstractService implements ChannelEventListener, EventClientService {
    final Bootstrap bootstrap;
    final EventLoopGroup workerGroup;
    final ExecutorService publicExecutor;
    final EventClientOption option;

    final Logger log = LoggerFactory.getLogger(getClass());

    AbstractNettyService(EventClientOption option) {
        this.option = option;

        bootstrap = new Bootstrap();

        publicExecutor = new ScheduledThreadPoolExecutor(option.publicExecutorThread(),
                new DefaultThreadFactory("Opensips-Event", true));
        workerGroup = new NioEventLoopGroup(option.workerGroupThread(), new DefaultThreadFactory("outbound-worker", true));
        bootstrap.group(workerGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_SNDBUF, option.sndBufSize())
                .option(ChannelOption.SO_RCVBUF, option.rcvBufSize())
                .handler(new EventChannelHandler(AbstractNettyService.this, publicExecutor));
    }

    @Override
    public void start() {
        this.startAsync();
    }

    @Override
    public void shutdown() {
        this.stopAsync();
    }
}
