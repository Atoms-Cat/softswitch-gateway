package link.thingscloud.opensips.spring.boot.starter.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractClientConnectHandler implements ClientConnectHandler {
    protected final Logger log = LoggerFactory.getLogger(getClass());
}
