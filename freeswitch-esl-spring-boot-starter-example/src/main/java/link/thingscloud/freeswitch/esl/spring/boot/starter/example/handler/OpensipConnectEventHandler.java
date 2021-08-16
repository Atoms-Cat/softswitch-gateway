package link.thingscloud.freeswitch.esl.spring.boot.starter.example.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;
import link.thingscloud.opensips.event.handler.Context;
import link.thingscloud.opensips.spring.boot.starter.handler.ClientConnectHandler;
import link.thingscloud.opensips.spring.boot.starter.handler.ClientEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 * @author th158
 */
@Slf4j
@Configuration
public class OpensipConnectEventHandler implements ClientEventHandler, ClientConnectHandler {
    @Override
    public void onConnect(Context context, Object msg) {

        if (msg instanceof DatagramPacket) {
            DatagramPacket datagramPacket = ((DatagramPacket)msg);
            ByteBuf buf = datagramPacket.copy().content();
            byte[] req = new byte[buf.readableBytes()];
            //复制内容到字节数组bytes
            buf.readBytes(req);
            String body = new String(req, StandardCharsets.UTF_8);
            log.info("onConnect : {}", body);
            // 读完就释放
            buf.release();
            datagramPacket.release();
        }
    }

    @Override
    public void handler(Context context, Object msg) {

        if (msg instanceof DatagramPacket) {
            DatagramPacket datagramPacket = ((DatagramPacket)msg);
            ByteBuf buf = datagramPacket.copy().content();
            byte[] req = new byte[buf.readableBytes()];
            //复制内容到字节数组bytes
            buf.readBytes(req);
            String body = new String(req, StandardCharsets.UTF_8);
            log.info("handler: {}", body);
            // 读完就释放
            buf.release();
            datagramPacket.release();
        }
    }
}
