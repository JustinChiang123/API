package com.zhapp.api.android.network.callback;

/**
 * <pre>
 * Describe: 请简单描述这个类是干什么的。
 *
 * Author: <a href="mailto:Justin_Chiang@foxmail.com">Justin_Chiang<a/>
 * Date: 2017-10-30
 * Time: 17:08
 * <pre/>
 */
public class NeedLoginException extends RuntimeException {

    public static String SUCCESS = "SUCCESS";
    public static String FAILURE = "FAILURE";

    /**
     * Constructs an NeedLoginException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public NeedLoginException() {
        super();
    }

    /**
     * Constructs an NeedLoginException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * @param s the String that contains a detailed message
     */
    public NeedLoginException(String s) {
        super(s);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     * <p>
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link Throwable#getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *                is permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     *
     * @since 1.5
     */
    public NeedLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     *
     * @since 1.5
     */
    public NeedLoginException(Throwable cause) {
        super(cause);
    }

    static final long serialVersionUID = -1848914673222119416L;
}
