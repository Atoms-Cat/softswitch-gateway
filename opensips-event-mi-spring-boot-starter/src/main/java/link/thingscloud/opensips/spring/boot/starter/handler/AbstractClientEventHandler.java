package link.thingscloud.opensips.spring.boot.starter.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractClientEventHandler implements ClientEventHandler {
    protected final Logger log = LoggerFactory.getLogger(getClass());
}
