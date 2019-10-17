package org.sdrc.bbbp.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UUIDAuthenticationException extends AuthenticationException {
	
	/**
	 * Constructs an {@code AuthenticationException} with the specified message and root
	 * cause.
	 *
	 * @param msg the detail message
	 * @param t the root cause
	 */
	public UUIDAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * Constructs an {@code AuthenticationException} with the specified message and no
	 * root cause.
	 *
	 * @param msg the detail message
	 */
	public UUIDAuthenticationException(String msg) {
		super(msg);
	}

}
