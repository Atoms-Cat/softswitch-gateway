package link.thingscloud.freeswitch.esl.inbound;

import link.thingscloud.freeswitch.esl.builder.Command;
import link.thingscloud.freeswitch.esl.inbound.option.InboundClientOption;

import java.util.List;
import java.util.Map;

import static link.thingscloud.freeswitch.esl.builder.Command.*;

/**
 * @author zhouhailin
 * @since 1.6.0
 */
abstract class AbstractInboundClientCommand extends AbstractInboundClient {

    private static final String EMPTY = "";

    private final boolean debugEnabled = log.isDebugEnabled();

    AbstractInboundClientCommand(InboundClientOption option) {
        super(option);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String answer(String address, String uuid) {
        String command = Command.cmd(UUID_ANSWER).arg(uuid).toString();
        if (debugEnabled) {
            log.debug("answer address : {}, command : {}", address, command);
        }
        return sendAsyncApiCommand(address, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String bridge(String address, String uuid, String otherUuid) {
        String command = Command.cmd(UUID_BRIDGE).arg(uuid).arg(otherUuid).toString();
        if (debugEnabled) {
            log.debug("bridge address : {}, command : {}", address, command);
        }
        return sendAsyncApiCommand(address, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String broadcast(String address, String uuid, String path, String smf) {
        String command = Command.cmd(UUID_BROADCAST).arg(uuid).arg(path).arg(smf).toString();
        if (debugEnabled) {
            log.debug("broadcast address : {}, command : {}", address, command);
        }
        return sendAsyncApiCommand(address, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String break0(String address, String uuid, boolean all) {
        String command = Command.cmd(UUID_BREAK).arg(uuid).arg(all ? "all" : null).toString();
        if (debugEnabled) {
            log.debug("break0 address : {}, command : {}", address, command);
        }
        return sendAsyncApiCommand(address, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String hold(String address, String smf, String uuid, boolean display) {
        String command = Command.cmd(UUID_HOLD).arg(smf).arg(uuid).arg(display ? "all" : EMPTY).toString();
        if (debugEnabled) {
            log.debug("hold address : {}, command : {}", address, command);
        }
        return sendAsyncApiCommand(address, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getVar(String address, String uuid, String var) {
        String command = Command.cmd(UUID_GETVAR).arg(uuid).arg(var).toString();
        if (debugEnabled) {
            log.debug("getVar address : {}, command : {}", address, command);
        }
        return sendSyncApiCommand(address, command, EMPTY).getBodyLines();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String setVar(String address, String uuid, String var, String val) {
        String command = Command.cmd(UUID_SETVAR).arg(uuid).arg(var).arg(val).toString();
        if (debugEnabled) {
            log.debug("setVar address : {}, command : {}", address, command);
        }
        return sendAsyncApiCommand(address, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String multiSetVar(String address, String uuid, Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return EMPTY;
        }
        StringBuilder command = new StringBuilder("uuid_setvar_multi " + uuid + " ");
        map.forEach((key, value) -> command.append(key).append("=").append(value).append(";"));
        command.deleteCharAt(command.length() - 1);
        if (debugEnabled) {
            log.debug("multiSetVar address : {}, command : {}", address, command);
        }
        return sendAsyncApiCommand(address, command.toString(), EMPTY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String record(String address, String uuid, String action, String path, int limit) {
        String command = Command.cmd(UUID_RECORD).arg(uuid).arg(action).arg(path).arg(limit < 1 ? null : String.valueOf(limit)).toString();
        if (debugEnabled) {
            log.debug("record address : {}, command : {}", address, command);
        }
        return sendAsyncApiCommand(address, command, EMPTY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String transfer(String address, String uuid, String smf, String dest, String dialplan, String context) {
        String command = Command.cmd(UUID_TRANSFER).arg(uuid).arg(smf).arg(dest).arg(dialplan).arg(context).toString();
        if (debugEnabled) {
            log.debug("transfer address : {}, command : {}", address, command);
        }
        return sendAsyncApiCommand(address, command, EMPTY);
    }
}
