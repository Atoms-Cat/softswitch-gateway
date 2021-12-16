package com.atomscat.freeswitch.xml.constant;

/**
 * @author th158
 */
public enum ConfName {


    IVR("ivr.conf"),
    SOFIA("sofia.conf")
    ;

    public String confName;

    public String getConfName() {
        return confName;
    }


    ConfName(String confName) {
        this.confName = confName;
    }

}
