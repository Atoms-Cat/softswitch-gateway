package com.atomscat.freeswitch.xml.domain.dialplan;

/**
 * 参考： https://freeswitch.org/confluence/display/FREESWITCH/mod_dptools
 * Dialplan tools provide the apps (commands) to process call sessions in XML dialplans.
 * @author th158
 */
public enum AppEnum {

    answer("answer", "Answer the call for a channel."),
    att_xfer("att_xfer", "Attended Transfer."),


    bgsystem("bgsystem", "Execute an operating system command in the background."),
    bind_digit_action("bind_digit_action", "Bind a key sequence or regex to an action."),
    bind_meta_app("bind_meta_app", "Respond to certain DTMF sequences on specified call leg(s) during a bridge and execute another dialplan application."),
    block_dtmf("block_dtmf", "Block DTMFs from being sent or received on the channel."),
    // todo
    //break("break", "Cancel an application currently running on the channel."),
    bridge("bridge", "Bridge a new channel to the existing one."),
    bridge_export("bridge_export", "Export a channel variable across any bridge."),

    callcenter("callcenter",  "Inbound caller join a callcenter queue"),
    capture("capture",  "Capture data into a channel variable."),
    chat("chat",  "Send a text message to an IM client"),
    check_acl("check_acl",  "Check originating address against an Access Control List"),
    clear_digit_action("clear_digit_action",  "Clear all digit bindings"),
    clear_speech_cache("clear_speech_cache",  "Clear speech handle cache."),
    cluechoo("cluechoo",  "Console-only \"ConCon\" choo-choo train"),
    cng_plc("cng_plc",  "Packet Loss Concealment on lost packets + comfort noise generation"),
    conference("conference",  "Establish an inbound or outbound conference call"),

    db("db",  "insert information into the database."),
    deflect("deflect",  "Send a call deflect/refer."),
    delay_echo("delay_echo",  "Echo audio at a specified delay."),
    detect_speech("detect_speech",  "Implements speech recognition."),
    digit_action_set_realm("digit_action_set_realm",  "Change binding realm."),
    displace_session("displace_session",  "Displace audio on a channel."),

    early_hangup("early_hangup",  "Enable early hangup on a channel."),
    eavesdrop("eavesdrop",  "Spy on a channel."),
    echo("echo",  "Echo audio and video back to the originator."),
    enable_heartbeat("enable_heartbeat",  "Enable Media Heartbeat."),
    endless_playback("endless_playback",  "Continuously play file to caller.[old wiki]"),
    // todo
    // enum("enum",  "Perform E.164 lookup."),
    erlang("erlang",  "Handle a call using Erlang."),
    eval("eval",  "Evaluates a string."),
    event("event",  "Fire an event."),
    execute_extension("execute_extension",  "Execute an extension from within another extension and return."),
    export("export",  "Export a channel variable across a bridge <varname>=<value>"),

    fax_detect("fax_detect",  "Detect FAX CNG - may be deprecated."),
    fifo("fifo",  "Send caller to a FIFO queue."),
    fifo_track_call("fifo_track_call",  "Count a call as a FIFO call in the manual_calls queue."),
    flush_dtmf("flush_dtmf",  "Flush any queued DTMF."),

    gentones("gentones",  "Generate TGML tones."),
    group("group",  "Insert or delete members in a group."),

    hangup("hangup",  "Hang up the current channel."),
    hash("hash",  "Add a hash to the db."),
    hold("hold",  "Send a hold message."),
    httapi("httapi",  "Send call control to a Web server with the HTTAPI infrastructure"),

    info("info",  "Display Call Info."),
    intercept("intercept",  "Lets you pickup a call and take it over if you know the uuid."),
    ivr("ivr",  "Run an IVR menu.[old wiki]"),

    javascript("javascript",  "Run a JavaScript script from the dialplan"),
    jitterbuffer("jitterbuffer",  "Send a jitter buffer message to a session"),

    limit("limit",  "Set a limit on number of calls to/from a resource"),
    limit_execute("limit_execute",  "Set the limit on a specific application"),
    limit_hash("limit_hash",  "Set a limit on number of calls to/from a resource"),
    limit_hash_execute("limit_hash_execute",  "Set the limit on a specific application"),
    log("log",  "Logs a channel variable for the channel calling the application"),
    loop_playback("loop_playback",  "Playback a file to the channel looply for limted times"),
    lua("lua",  "Run a Lua script from the dialplan [API link]"),

    media_reset("media_reset",  "Reset all bypass/proxy media flags."),
    mkdir("mkdir",  "Create a directory."),
    multiset("multiset",  "Set multiple channel variables with a single action."),
    mutex("mutex",  "Block on a call flow, allowing only one at a time"),

    page("page",  "Play an audio file as a page."),
    park("park",  "Park a call."),
    park_state("park_state",  "Park State."),
    phrase("phrase",  "Say a Phrase."),
    pickup("pickup",  "Pickup a call."),
    play_and_detect_speech("play_and_detect_speech",  "Play while doing speech recognition."),
    play_and_get_digits("play_and_get_digits",  "Play and get Digits."),
    play_fsv("play_fsv",  "Play an FSV file. FSV - (FS Video File Format) additional description needed"),
    playback("playback",  "Play a sound file to the originator."),
    pre_answer("pre_answer",  "Answer a channel in early media mode.[old wiki]"),
    preprocess("preprocess",  "description needed"),
    presence("presence",  "Send Presence"),
    privacy("privacy",  "Set caller privacy on calls."),

    queue_dtmf("queue_dtmf",  "Send DTMF digits after a successful bridge."),

    read("read",  "Read Digits."),
    record("record",  "Record a file from the channel's input."),
    record_fsv("record_fsv",  "Record a FSV file. FSV - (FS Video File Format) additional description needed"),
    record_session("record_session",  "Record Session."),
    recovery_refresh("recovery_refresh",  "Send a recovery refresh. addition information needed"),
    redirect("redirect",  "Send a redirect message to a session."),
    regex("regex",  "Perform a regex."),
    remove_bugs("remove_bugs",  "Remove media bugs."),
    rename("rename",  "Rename file."),
    respond("respond",  "Send a respond message to a session."),
    ring_ready("ring_ready",  "Indicate Ring_Ready on a channel."),
    rxfax("rxfax",  "Receive a fax as a tif file."),

    say("say",  "Say time/date/ip_address/digits/etc. With pre-recorded prompts."),
    sched_broadcast("sched_broadcast",  "Enable Scheduled Broadcast."),
    sched_cancel("sched_cancel",  "Cancel a scheduled future broadcast/transfer."),
    sched_hangup("sched_hangup",  "Enable Scheduled Hangup."),
    sched_heartbeat("sched_heartbeat",  "Enable Scheduled Heartbeat. Additional information needed"),
    sched_transfer("sched_transfer",  "Enable Scheduled Transfer."),
    send_display("send_display",  "Sends an info packet with a sipfrag."),
    send_dtmf("send_dtmf",  "Send inband DTMF, 2833, or SIP Info digits from a session."),
    send_info("send_info",  "Send info to the endpoint."),
    session_loglevel("session_loglevel",  "Override the system's loglevel for this channel."),
    set("set",  "Set a channel variable for the channel calling the application."),
    set_audio_level("set_audio_level",  "Adjust the read or write audio levels for a channel."),
    set_global("set_global",  "Set a global variable."),
    set_name("set_name",  "Name the channel."),
    set_profile_var("set_profile_var",  "Set a caller profile variable."),
    set_user("set_user",  "Set a user."),
    set_zombie_exec("set_zombie_exec",  "Sets the zombie execution flag on the current channel."),
    sleep("sleep",  "Pause a channel."),
    socket("socket",  "Establish an outbound socket connection."),
    sound_test("sound_test",  "Analyze Audio."),
    speak("speak",  "Speaks a string or file of text to the channel using the defined TTS engine.[old wiki]"),
    soft_hold("soft_hold",  "Put a bridged channel on hold."),
    start_dtmf("start_dtmf",  "Start inband DTMF detection."),
    stop_dtmf("stop_dtmf",  "Stop inband DTMF detection."),
    start_dtmf_generate("start_dtmf_generate",  "Start inband DTMF generation."),
    stop_displace_session("stop_displace_session",  "Stop displacement audio on a channel."),
    stop_dtmf_generate("stop_dtmf_generate",  "Stop inband DTMF generation."),
    stop_record_session("stop_record_session",  "Stop Record Session."),
    stop_tone_detect("stop_tone_detect",  "Stop detecting tones."),
    strftime("strftime",  "Returns formatted date and time."),
    system("system",  "Execute an operating system command."),

    three_way("three_way",  "Three way call with a UUID."),
    tone_detect("tone_detect",  "Detect the presence of a tone and execute a command if found."),
    transfer("transfer",  "Immediately transfer the calling channel to a new extension.[old wiki]"),
    translate("translate",  "Number translation."),

    unbind_meta_app("unbind_meta_app",  "Unbind a key from an application."),
    unset("unset",  "Unset a variable."),
    unhold("unhold",  "Send a un-hold message."),
    userspy("userspy",  "Provides persistent eavesdrop on all channels bridged to a certain user using eavesdrop."),

    verbose_events("verbose_events",  "Make ALL Events verbose (Make all variables appear in every single event for this channel)."),
    wait_for_silence("wait_for_silence",  "Pause processing while waiting for silence on the channel."),
    wait_for_answer("wait_for_answer",  "Pause processing while waiting for the call to be answered."),
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
