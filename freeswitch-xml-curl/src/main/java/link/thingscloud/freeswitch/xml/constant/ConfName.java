package link.thingscloud.freeswitch.xml.constant;

/**
 * @author th158
 */
public enum ConfName {


    IVR("ivr.conf", "menus")
    ;

    public String confName;

    public String listName;

    public String getConfName() {
        return confName;
    }

    public String getListName() {
        return listName;
    }

    ConfName(String confName, String listName) {
        this.confName = confName;
        this.listName = listName;
    }

}
