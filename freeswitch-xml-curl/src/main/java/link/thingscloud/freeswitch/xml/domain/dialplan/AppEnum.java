package link.thingscloud.freeswitch.xml.domain.dialplan;

/**
 * 参考： https://freeswitch.org/confluence/display/FREESWITCH/mod_dptools
 * Dialplan tools provide the apps (commands) to process call sessions in XML dialplans.
 * @author th158
 */
public enum AppEnum {

    answer("answer", "Answer the call for a channel."),

    set("set", "Set a channel variable for the channel calling the application."),

    sleep("sleep", "Pause a channel."),

    socket("socket", "Establish an outbound socket connection."),

    ivr("ivr", "Run an IVR menu.")


    ;

    private String application;

    private String describe;

    AppEnum(String application, String describe) {
        this.application = application;
        this.describe = describe;
    }

    public String getApplication() {
        return application;
    }

    public static AppEnum instance(String application) {
        AppEnum[] enums = values();
        for (AppEnum app : enums) {
            if (app.getApplication().equals(application)) {
                return app;
            }
        }
        return null;
    }


}
