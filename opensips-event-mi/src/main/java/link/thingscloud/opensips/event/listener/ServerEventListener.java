package link.thingscloud.opensips.event.listener;


import link.thingscloud.opensips.event.handler.Context;

public interface ServerEventListener {

    void handleEvent(Context context, Object msg);

    void onConnect(Context context, Object msg);

}
