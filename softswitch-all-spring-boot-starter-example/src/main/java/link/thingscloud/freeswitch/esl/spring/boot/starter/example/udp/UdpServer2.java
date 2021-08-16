package link.thingscloud.freeswitch.esl.spring.boot.starter.example.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * todo 测试
 */
@Slf4j
public class UdpServer2 {

    //如果不设置超时，连接会一直占用本地线程，端口，连接客户端一多，会导致本地端口用尽及CPU压力
    private final int timeout = 5;

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //通过NioDatagramChannel创建Channel，并设置Socket参数支持广播
            //UDP相对于TCP不需要在客户端和服务端建立实际的连接，因此不需要为连接（ChannelPipeline）设置handler
            Bootstrap b = new Bootstrap();
            //设置超时时间
            b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout);
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpServerHandler2());
            //获取配置文件中的主机、端口
            String host = "192.168.10.116";
            int port = 8082;
            InetSocketAddress addr = new InetSocketAddress(host, port);
            Channel channel = b.bind(addr).sync().channel();
            System.out.println("UdpServer start success on2 " + port);
            channel.closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
