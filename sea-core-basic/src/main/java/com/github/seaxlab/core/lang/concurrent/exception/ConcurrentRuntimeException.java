package com.github.seaxlab.core.lang.concurrent.exception;

/**
 * <p>
 * An exception class used for reporting runtime error conditions related to
 * accessing data of background tasks.
 * </p>
 * <p>
 * This class is an analogue of the {@link org.apache.commons.lang3.concurrent.ConcurrentException} exception class.
 * However, it is a runtime exception and thus does not need explicit catch
 * clauses. Some methods of {@link org.apache.commons.lang3.concurrent.ConcurrentUtils} throw {@code
 * ConcurrentRuntimeException} exceptions rather than
 * {@link ConcurrentException} exceptions. They can be used by client code that
 * does not want to be bothered with checked exceptions.
 * </p>
 *
 * @since 3.0
 */
public class ConcurrentRuntimeException extends RuntimeException {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -6582182735562919670L;

    /**
     * Creates a new, uninitialized instance of {@code
     * ConcurrentRuntimeException}.
     */
    protected ConcurrentRuntimeException() {
        super();
    }

    /**
     * Creates a new instance of {@code ConcurrentRuntimeException} and
     * initializes it with the given cause.
     *
     * @param cause the cause of this exception
     * @throws IllegalArgumentException if the cause is not a checked exception
     */
    public ConcurrentRuntimeException(final Throwable cause) {
        super(ConcurrentUtil.checkedException(cause));
    }

    /**
     * Creates a new instance of {@code ConcurrentRuntimeException} and
     * initializes it with the given message and cause.
     *
     * @param msg   the error message
     * @param cause the cause of this exception
     * @throws IllegalArgumentException if the cause is not a checked exception
     */
    public ConcurrentRuntimeException(final String msg, final Throwable cause) {
        super(msg, ConcurrentUtil.checkedException(cause));
    }
}
