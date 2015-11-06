package org.apache.aphrodite.exception;

/**
 * 
 * @author huang.yuewen 2015年11月6日下午3:19:11
 *
 */
public class DaoException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
