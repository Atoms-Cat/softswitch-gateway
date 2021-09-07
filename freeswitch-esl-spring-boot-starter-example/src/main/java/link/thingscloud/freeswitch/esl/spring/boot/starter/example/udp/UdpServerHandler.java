package link.thingscloud.freeswitch.esl.spring.boot.starter.example.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.nio.charset.StandardCharsets;

/**
 * todo 测试
 */
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    /**
     * 接收数据
     *
     * @param ctx a {@link ChannelHandlerContext} object.
     * @param packet a {@link DatagramPacket} object.
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        ByteBuf buf = packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        //复制内容到字节数组bytes
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        System.out.println("UdpServer数据1: " + body);
        //向客户端原样返回消息
        DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(req), packet.sender());
        ctx.writeAndFlush(data);
    }

    /**
     * 捕获异常
     *
     * @param ctx a {@link ChannelHandlerContext} object.
     * @param cause a {@link Throwable} object.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("UdpServer捕获异常1: " + ctx.channel().remoteAddress());
        ctx.close();
        cause.printStackTrace();
    }
}