package link.thingscloud.freeswitch.cdr.exception;

/**
 * <p>ParserException class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
public class ParserException extends Exception {

    /**
     * <p>Constructor for ParserException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public ParserException(String message) {
        super(message);
    }

    /**
     * <p>Constructor for ParserException.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param cause   a {@link java.lang.Throwable} object.
     */
    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
