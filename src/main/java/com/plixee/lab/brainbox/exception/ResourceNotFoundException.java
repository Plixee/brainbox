package com.plixee.lab.brainbox.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 5059994926043312155L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
