package com.atomscat.freeswitch.xml.domain.configuration.ivr;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author th158
 */
@Data
@JacksonXmlRootElement(localName = "menu", namespace = "menu")
public class Menu implements Serializable {

    /**
     * the name of the ivr menu.
     */
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    /**
     * the menu prompt played the first time the menu is played. May be a filename (starting with \ or /) or "say:Text to speak" for TTS, or "phrase:phrase_macro_name" to speak a phrase macro.
     * When using a "phrase:my_phrase" you can specify the string to pass along for input pattern matching like so: "phrase:my_phrase:options|option2".
     */
    @JacksonXmlProperty(localName = "greet-long", isAttribute = true)
    private String greetLong;

    /**
     * the shorter version of the menu prompt played when the menu loops. May be filename, say, or phrase.
     */
    @JacksonXmlProperty(localName = "greet-short", isAttribute = true)
    private String greetShort;

    /**
     * played when no entry or an invalid entry is made. May be filename, say, or phrase.
     */
    @JacksonXmlProperty(localName = "invalid-sound", isAttribute = true)
    private String invalidSound;

    /**
     * played when the menu is terminated, May be filename, say, or phrase.
     */
    @JacksonXmlProperty(localName = "exit-sound", isAttribute = true)
    private String exitSound;

    /**
     * number of milliseconds to wait for a selection.
     */
    @JacksonXmlProperty(localName = "inter-digit-timeout", isAttribute = true)
    private String interDigitTimeout;

    /**
     * number of milliseconds to wait for a after playing confirm-macro to conform entered digits.
     */
    @JacksonXmlProperty(localName = "timeout", isAttribute = true)
    private String timeout;

    @JacksonXmlProperty(localName = "confirm-macro", isAttribute = true)
    private String confirmMacro;

    /**
     * the key that tells the IVR that digit-entry is finished. Defaults to #, even if blank.
     */
    @JacksonXmlProperty(localName = "confirm-key", isAttribute = true)
    private String confirmKey;

    @JacksonXmlProperty(localName = "confirm-attempts", isAttribute = true)
    private String confirmAttempts;

    /**
     * maximum wrong digits entry(ies) before ending the menu (default 3 if not specified or invalid (less than 1) values are Specified).
     */
    @JacksonXmlProperty(localName = "max-failures", isAttribute = true)
    private String maxFailures;

    /**
     * maximum timeout retry(ies) before ending the menu (default will use the max_failures value or 3 if both are left blank Or invalid (less than 1) values are specified).
     */
    @JacksonXmlProperty(localName = "max-timeouts", isAttribute = true)
    private String maxTimeouts;

    /**
     * Execute a FreeSWITCH dialplan application on maximum failures
     */
    @JacksonXmlProperty(localName = "exec-on-max-failures", isAttribute = true)
    private String execOnMaxFailures;

    /**
     * Execute a FreeSWITCH dialplan application on maximum timeouts
     */
    @JacksonXmlProperty(localName = "exec-on-max-timeouts", isAttribute = true)
    private String execOnMaxTimeouts;

    /**
     * name of TTS engine to speak text (ie. cepstral). (optional).
     */
    @JacksonXmlProperty(localName = "tts-engine", isAttribute = true)
    private String ttsEngine;

    /**
     * name of TTS voice to use to speak text (ie. david). (Necessary if tts-engine is specified).
     */
    @JacksonXmlProperty(localName = "tts-voice", isAttribute = true)
    private String ttsVoice;

    /**
     * maximum number of digits to collect before searching for a matching menu entry.
     */
    @JacksonXmlProperty(localName = "digit-len", isAttribute = true)
    private String digitLen;


    @JacksonXmlElementWrapper(localName = "entry", useWrapping = false)
    private List<Entry> entry;
}
