package com.atomscat.freeswitch.esl.exception;

public class OutboundServerException extends RuntimeException {
    /**
     * <p>Constructor for InboundClientException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public OutboundServerException(String message) {
        super(message);
    }

    /**
     * <p>Constructor for InboundClientException.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param cause   a {@link java.lang.Throwable} object.
     */
    public OutboundServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Constructor for InboundClientException.</p>
     *
     * @param cause a {@link java.lang.Throwable} object.
     */
    public OutboundServerException(Throwable cause) {
        super(cause);
    }
}
