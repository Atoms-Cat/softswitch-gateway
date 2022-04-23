package com.atomscat.freeswitch.esl.exception;

public class OutboundClientException extends RuntimeException {
    /**
     * <p>Constructor for InboundClientException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public OutboundClientException(String message) {
        super(message);
    }

    /**
     * <p>Constructor for InboundClientException.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param cause   a {@link java.lang.Throwable} object.
     */
    public OutboundClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Constructor for InboundClientException.</p>
     *
     * @param cause a {@link java.lang.Throwable} object.
     */
    public OutboundClientException(Throwable cause) {
        super(cause);
    }
}
