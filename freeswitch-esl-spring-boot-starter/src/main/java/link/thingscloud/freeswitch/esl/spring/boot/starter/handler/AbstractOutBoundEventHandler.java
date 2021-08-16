package link.thingscloud.freeswitch.esl.spring.boot.starter.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractOutBoundEventHandler implements OutBoundEventHandler {
    protected final Logger log = LoggerFactory.getLogger(getClass());
}
