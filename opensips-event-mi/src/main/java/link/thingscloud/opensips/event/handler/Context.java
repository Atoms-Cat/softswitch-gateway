package link.thingscloud.opensips.event.handler;

import io.netty.channel.Channel;


/**
 * @author th158
 */
public class Context implements IModEslApi {

    private final EventChannelHandler handler;
    private final Channel channel;

    public Context(Channel channel, EventChannelHandler clientHandler) {
        this.handler = clientHandler;
        this.channel = channel;
    }

    @Override
    public boolean canSend() {
        return channel != null && channel.isActive();
    }

    public Channel channel() {
        return channel;
    }

    public EventChannelHandler handler() {
        return handler;
    }


}
