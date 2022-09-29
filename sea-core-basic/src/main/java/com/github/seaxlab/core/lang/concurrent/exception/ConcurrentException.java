package com.github.seaxlab.core.lang.concurrent.exception;


/**
 * <p>
 * An exception class used for reporting error conditions related to accessing
 * data of background tasks.
 * </p>
 * <p>
 * The purpose of this exception class is analogous to the default JDK exception
 * class {@link java.util.concurrent.ExecutionException}, i.e. it wraps an
 * exception that occurred during the execution of a task. However, in contrast
 * to {@code ExecutionException}, it wraps only checked exceptions. Runtime
 * exceptions are thrown directly.
 * </p>
 *
 * @since 3.0
 */
public class ConcurrentException extends Exception {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 6622707671812226130L;

    /**
     * Creates a new, uninitialized instance of {@code ConcurrentException}.
     */
    protected ConcurrentException() {
        super();
    }

    /**
     * Creates a new instance of {@code ConcurrentException} and initializes it
     * with the given cause.
     *
     * @param cause the cause of this exception
     * @throws IllegalArgumentException if the cause is not a checked exception
     */
    public ConcurrentException(final Throwable cause) {
        super(ConcurrentUtil.checkedException(cause));
    }

    /**
     * Creates a new instance of {@code ConcurrentException} and initializes it
     * with the given message and cause.
     *
     * @param msg   the error message
     * @param cause the cause of this exception
     * @throws IllegalArgumentException if the cause is not a checked exception
     */
    public ConcurrentException(final String msg, final Throwable cause) {
        super(msg, ConcurrentUtil.checkedException(cause));
    }
}
