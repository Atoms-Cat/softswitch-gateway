package com.atomscat.opensips.event.listener;


import com.atomscat.opensips.event.handler.Context;

public interface ServerEventListener {

    void handleEvent(Context context, Object msg);

    void onConnect(Context context, Object msg);

}
