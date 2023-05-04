package com.atomscat.freeswitch.xml.constant;

/**
 * @author th158
 */
public enum ConfName {


    IVR("ivr.conf"),
    SOFIA("sofia.conf");

    public final String name;

    public String getName() {
        return name;
    }


    ConfName(String name) {
        this.name = name;
    }

}
