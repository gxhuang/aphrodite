package org.apache.aphrodite.exception;

/**
 * 
 * @author huang.yuewen 2015年11月6日下午3:19:34
 *
 */
public class ServiceException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
