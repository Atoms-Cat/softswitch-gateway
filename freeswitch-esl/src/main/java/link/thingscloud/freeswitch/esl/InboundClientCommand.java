package link.thingscloud.freeswitch.esl;

import java.util.List;
import java.util.Map;

/**
 * @author zhouhailin
 * @since 1.6.0
 */
public interface InboundClientCommand {

    /**
     * uuid_answer &lt;uuid&gt;
     *
     * @param address address
     * @param uuid    leg uuid
     * @return Job UUID
     */
    String answer(String address, String uuid);

    /**
     * uuid_bridge &lt;uuid&gt; &lt;other_uuid&gt;
     *
     * @param address   address
     * @param uuid      leg uuid
     * @param otherUuid other leg uuid
     * @return Job UUID
     */
    String bridge(String address, String uuid, String otherUuid);

    /**
     * uuid_broadcast  &lt;uuid&gt; &lt;path&gt; [aleg|bleg|holdb|both]
     *
     * @param address address
     * @param uuid    leg uuid
     * @param path    file path
     * @param smf     swithc media flag : aleg|bleg|holdb|both
     * @return Job UUID
     */
    String broadcast(String address, String uuid, String path, String smf);

    /**
     * uuid_break &lt;uuid&gt; [all]
     *
     * @param address address
     * @param uuid    leg uuid
     * @return Job UUID
     */
    default String break0(String address, String uuid) {
        return break0(address, uuid, false);
    }

    /**
     * uuid_break &lt;uuid&gt; [all]
     *
     * @param address address
     * @param uuid    leg uuid
     * @param all     false
     * @return Job UUID
     */
    String break0(String address, String uuid, boolean all);

    /**
     * uuid_hold [off|toggle] &lt;uuid&gt; [&lt;display&gt;]
     *
     * @param address address
     * @param uuid    leg uuid
     * @return Job UUID
     */
    default String hold(String address, String uuid) {
        return hold(address, "off", uuid, false);
    }

    /**
     * uuid_hold [off|toggle] &lt;uuid&gt; [&lt;display&gt;]
     *
     * @param address address
     * @param smf     off|toggle
     * @param uuid    leg uuid
     * @param display false
     * @return Job UUID
     */
    String hold(String address, String smf, String uuid, boolean display);

    /**
     * uuid_getvar &lt;uuid&gt; &lt;var&gt;
     *
     * @param address address
     * @param uuid    leg uuid
     * @param var     变量名
     * @return Job UUID
     */
    List<String> getVar(String address, String uuid, String var);

    /**
     * uuid_setvar &lt;uuid&gt; &lt;var&gt; [value]
     *
     * @param address address
     * @param uuid    leg uuid
     * @param var     变量名
     * @param val     变量值, 为空时则删除该变量
     * @return Job UUID
     */
    String setVar(String address, String uuid, String var, String val);

    /**
     * uuid_setvar_multi &lt;uuid&gt; &lt;var&gt;=&lt;value&gt;;&lt;var&gt;=&lt;value&gt;...
     *
     * @param address address
     * @param uuid    leg uuid
     * @param map     键值对集合
     * @return Job UUID
     */
    String multiSetVar(String address, String uuid, Map<String, String> map);

    /**
     * uuid_record &lt;uuid&gt; [start|stop|mask|unmask] &lt;path&gt; [&lt;limit&gt;]
     *
     * @param address address
     * @param uuid    leg uuid
     * @param action  start|stop|mask|unmask
     * @param path    录音路径
     * @param limit   limit
     * @return Job UUID
     */
    String record(String address, String uuid, String action, String path, int limit);

    /**
     * uuid_transfer &lt;uuid&gt; [-bleg|-both] &lt;dest-exten&gt; [&lt;dialplan&gt;] [&lt;context&gt;]
     *
     * @param address address
     * @param uuid    leg uuid
     * @param dest    dest extension
     * @return Job UUID
     */
    default String transfer(String address, String uuid, String dest) {
        return transfer(address, uuid, null, dest, null, null);
    }

    /**
     * uuid_transfer &lt;uuid&gt; [-bleg|-both] &lt;dest-exten&gt; [&lt;dialplan&gt;] [&lt;context&gt;]
     *
     * @param address  address
     * @param uuid     leg uuid
     * @param smf      [-bleg|-both]
     * @param dest     dest extension
     * @param dialplan XML
     * @param context  dialplan context name
     * @return Job UUID
     */
    String transfer(String address, String uuid, String smf, String dest, String dialplan, String context);

}
