package link.thingscloud.freeswitch.xml.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class XmlCurl implements Serializable {

    String hostname;

    String section;

    @JSONField(name = "tag_name")
    String tagName;

    @JSONField(name = "key_name")
    String keyName;

    @JSONField(name = "key_value")
    String keyValue;

    @JSONField(name = "Event-Name")
    String eventName;

    @JSONField(name = "Core-UUID")
    String coreUuid;

    @JSONField(name = "FreeSWITCH-Hostname")
    String freeSwitchHostName;

    @JSONField(name = "FreeSWITCH-Switchname")
    String freeSwitchSwitchName;

    @JSONField(name = "FreeSWITCH-IPv4")
    String freeSwitchIPv4;

    @JSONField(name = "FreeSWITCH-IPv6")
    String freeSwitchIPv6;

    @JSONField(name = "Event-Date-Local")
    String eventDateLocal;

    @JSONField(name = "Event-Date-GMT")
    String eventDateGMT;

    @JSONField(name = "Event-Date-Timestamp")
    String eventDateTimestamp;

    @JSONField(name = "Event-Calling-File")
    String eventCallingFile;

    @JSONField(name = "Event-Calling-Function")
    String eventCallingFunction;

    @JSONField(name = "Event-Calling-Line-Number")
    String eventCallingLineNumber;

    @JSONField(name = "Event-Sequence")
    String eventSequence;

    @JSONField(name = "Channel-State")
    String channelState;

    @JSONField(name = "Channel-Call-State")
    String channelCallState;

    @JSONField(name = "Channel-State-Number")
    String channelStateNumber;

    @JSONField(name = "Channel-Name")
    String channelName;

    @JSONField(name = "Unique-ID")
    String uniqueID;

    @JSONField(name = "Call-Direction")
    String callDirection;

    @JSONField(name = "Presence-Call-Direction")
    String PresenceCallDirection;

    @JSONField(name = "Channel-HIT-Dialplan")
    String channelHITDialplan;

    @JSONField(name = "Channel-Presence-ID")
    String channelPresenceID;

    @JSONField(name = "Channel-Call-UUID")
    String channelCallUUID;

    @JSONField(name = "Answer-State")
    String answerState;

    @JSONField(name = "Caller-Direction")
    String callerDirection;

    @JSONField(name = "Caller-Logical-Direction")
    String callerLogicalDirection;

    @JSONField(name = "Caller-Username")
    String callerUsername;

    @JSONField(name = "Caller-Dialplan")
    String callerDialplan;

    @JSONField(name = "Caller-Caller-ID-Name")
    String callerCallerIDName;

    @JSONField(name = "Caller-Caller-ID-Number")
    String callerCallerIDNumber;

    @JSONField(name = "Caller-Orig-Caller-ID-Name")
    String callerOrigCallerIDName;

    @JSONField(name = "Caller-Orig-Caller-ID-Number")
    String callerOrigCallerIDNumber;

    @JSONField(name = "Caller-Network-Addr")
    String callerNetworkAddr;

    @JSONField(name = "Caller-ANI")
    String callerANI;

    @JSONField(name = "Caller-Destination-Number")
    String callerDestinationNumber;

    @JSONField(name = "Caller-Unique-ID")
    String callerUniqueID;

    @JSONField(name = "Caller-Source")
    String callerSource;

    @JSONField(name = "Caller-Context")
    String callerContext;

    @JSONField(name = "Caller-Channel-Name")
    String callerChannelName;

    @JSONField(name = "Caller-Profile-Index")
    String callerProfileIndex;

    @JSONField(name = "Caller-Profile-Created-Time")
    String callerProfileCreatedTime;

    @JSONField(name = "Caller-Channel-Created-Time")
    String callerChannelCreatedTime;

    @JSONField(name = "Caller-Channel-Answered-Time")
    String callerChannelAnsweredTime;

    @JSONField(name = "Caller-Channel-Progress-Time")
    String callerChannelProgressTime;

    @JSONField(name = "Caller-Channel-Progress-Media-Time")
    String callerChannelProgressMediaTime;

    @JSONField(name = "Caller-Channel-Hangup-Time")
    String callerChannelHangupTime;

    @JSONField(name = "Caller-Channel-Transfer-Time")
    String callerChannelTransferTime;

    @JSONField(name = "Caller-Channel-Resurrect-Time")
    String callerChannelResurrectTime;

    @JSONField(name = "Caller-Channel-Bridged-Time")
    String callerChannelBridgedTime;

    @JSONField(name = "Caller-Channel-Last-Hold")
    String callerChannelLastHold;

    @JSONField(name = "Caller-Channel-Hold-Accum")
    String callerChannelHoldAccum;

    @JSONField(name = "Caller-Screen-Bit")
    String callerScreenBit;

    @JSONField(name = "Caller-Privacy-Hide-Name")
    String callerPrivacyHideName;

    @JSONField(name = "Caller-Privacy-Hide-Number")
    String callerPrivacyHideNumber;

    @JSONField(name = "variable_direction")
    String variableDirection;

    @JSONField(name = "variable_uuid")
    String variableUuid;

    @JSONField(name = "variable_session_id")
    String variableSessionId;

    @JSONField(name = "variable_sip_from_user")
    String variableSipFromUser;

    @JSONField(name = "variable_sip_from_uri")
    String variableSipFromUri;

    @JSONField(name = "variable_sip_from_host")
    String variableSipFromHost;

    @JSONField(name = "variable_video_media_flow")
    String variableVideoMediaFlow;

    @JSONField(name = "variable_audio_media_flow")
    String variableAudioMediaFlow;

    @JSONField(name = "variable_text_media_flow")
    String variableTextMediaFlow;

    @JSONField(name = "variable_channel_name")
    String variableChannelName;

    @JSONField(name = "variable_sip_call_id")
    String variableSipCallId;

    @JSONField(name = "variable_sip_local_network_addr")
    String variableSipLocalNetworkAddr;

    @JSONField(name = "variable_sip_network_ip")
    String variableAipNetworkIp;

    @JSONField(name = "variable_sip_network_port")
    String variableSipNetworkPort;

    @JSONField(name = "variable_sip_invite_stamp")
    String variableSipInviteStamp;

    @JSONField(name = "variable_sip_received_ip")
    String variableSipReceivedIp;

    @JSONField(name = "variable_sip_received_port")
    String variableSipReceivedPort;

    @JSONField(name = "variable_sip_via_protocol")
    String variableSipViaProtocol;

    @JSONField(name = "variable_sip_authorized")
    String variableSipAuthorized;

    @JSONField(name = "variable_sip_acl_authed_by")
    String variableSipAclAuthedBy;

    @JSONField(name = "variable_sip_from_user_stripped")
    String variableSipFromUserStripped;

    @JSONField(name = "variable_sip_from_tag")
    String variableSipFromTag;

    @JSONField(name = "variable_sofia_profile_name")
    String variableSofiaProfileName;

    @JSONField(name = "variable_sofia_profile_url")
    String variableSofiaProfileUrl;

    @JSONField(name = "variable_recovery_profile_name")
    String variableRecoveryProfileName;

    @JSONField(name = "variable_sip_invite_route_uri")
    String variableSipInviteRouteUri;

    @JSONField(name = "variable_sip_invite_record_route")
    String variableSipInviteRecordRoute;

    @JSONField(name = "variable_sip_full_via")
    String variableSipFullVia;

    @JSONField(name = "variable_sip_from_display")
    String variableSipFromDisplay;

    @JSONField(name = "variable_sip_full_from")
    String variableSipFullFrom;

    @JSONField(name = "variable_sip_full_to")
    String variableSipFullTo;

    @JSONField(name = "variable_sip_allow")
    String variableSipAllow;

    @JSONField(name = "variable_sip_req_user")
    String variableSipRequser;

    @JSONField(name = "variable_sip_req_port")
    String variableSipReqPort;

    @JSONField(name = "variable_sip_req_uri")
    String variableSipReqUri;

    @JSONField(name = "variable_sip_req_host")
    String variableSipReqHost;

    @JSONField(name = "variable_sip_to_user")
    String variableSipToUser;

    @JSONField(name = "variable_sip_to_uri")
    String variableSipToUri;

    @JSONField(name = "variable_sip_to_host")
    String variableSipToHost;

    @JSONField(name = "variable_sip_contact_params")
    String variableSipContactParams;

    @JSONField(name = "variable_sip_contact_user")
    String variableSipContactUser;

    @JSONField(name = "variable_sip_contact_port")
    String variableSipContactPort;

    @JSONField(name = "variable_sip_contact_uri")
    String variableSipContactUri;

    @JSONField(name = "variable_sip_contact_host")
    String variableSipContactHost;

    @JSONField(name = "variable_rtp_use_codec_string")
    String variableRtpUseCodecString;

    @JSONField(name = "variable_sip_user_agent")
    String variableSipUserAgent;

    @JSONField(name = "variable_sip_via_host")
    String variableSipViaHost;

    @JSONField(name = "variable_sip_via_port")
    String variableSipViaPort;

    @JSONField(name = "variable_max_forwards")
    String variablemaxforwards;

    @JSONField(name = "variable_presence_id")
    String variablePresenceId;

    @JSONField(name = "variable_switch_r_sdp")
    String variableSwitchRSdp;

    @JSONField(name = "variable_ep_codec_string")
    String variableEpCodecString;

    @JSONField(name = "variable_endpoint_disposition")
    String variableEndpointDisposition;

    @JSONField(name = "variable_call_uuid")
    String variableCallUuid;

    @JSONField(name = "Hunt-Direction")
    String huntDirection;

    @JSONField(name = "Hunt-Logical-Direction")
    String huntLogicalDirection;

    @JSONField(name = "Hunt-Username")
    String huntUsername;

    @JSONField(name = "Hunt-Dialplan")
    String huntDialplan;

    @JSONField(name = "Hunt-Caller-ID-Name")
    String huntCallerIDName;

    @JSONField(name = "Hunt-Caller-ID-Number")
    String huntCallerIDNumber;

    @JSONField(name = "Hunt-Orig-Caller-ID-Name")
    String huntOrigCallerIDName;

    @JSONField(name = "Hunt-Orig-Caller-ID-Number")
    String huntOrigCallerIDNumber;

    @JSONField(name = "Hunt-Network-Addr")
    String huntNetworkAddr;

    @JSONField(name = "Hunt-ANI")
    String huntANI;

    @JSONField(name = "Hunt-Destination-Number")
    String huntDestinationNumber;

    @JSONField(name = "Hunt-Unique-ID")
    String huntUniqueID;

    @JSONField(name = "Hunt-Source")
    String huntSource;

    @JSONField(name = "Hunt-Context")
    String huntContext;

    @JSONField(name = "Hunt-Channel-Name")
    String huntChannelName;

    @JSONField(name = "Hunt-Profile-Index")
    String huntProfileIndex;

    @JSONField(name = "Hunt-Profile-Created-Time")
    String huntProfileCreatedTime;

    @JSONField(name = "Hunt-Channel-Created-Time")
    String huntChannelCreatedTime;

    @JSONField(name = "Hunt-Channel-Answered-Time")
    String huntChannelAnsweredTime;

    @JSONField(name = "Hunt-Channel-Progress-Time")
    String huntChannelProgressTime;

    @JSONField(name = "Hunt-Channel-Progress-Media-Time")
    String huntChannelProgressMediaTime;

    @JSONField(name = "Hunt-Channel-Hangup-Time")
    String huntChannelHangupTime;

    @JSONField(name = "Hunt-Channel-Transfer-Time")
    String huntChannelTransferTime;

    @JSONField(name = "Hunt-Channel-Resurrect-Time")
    String huntChannelResurrectTime;

    @JSONField(name = "Hunt-Channel-Bridged-Time")
    String huntChannelBridgedTime;

    @JSONField(name = "Hunt-Channel-Last-Hold")
    String huntChannelLastHold;

    @JSONField(name = "Hunt-Channel-Hold-Accum")
    String huntChannelHoldAccum;

    @JSONField(name = "Hunt-Screen-Bit")
    String huntScreenBit;

    @JSONField(name = "Hunt-Privacy-Hide-Name")
    String huntPrivacyHideName;

    @JSONField(name = "Hunt-Privacy-Hide-Number")
    String huntPrivacyHideNumbe;


}
