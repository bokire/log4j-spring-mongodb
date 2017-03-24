package com.github.bokire.log4mongo.model;

import java.util.List;

public class ExceptionThrowable {
	
	private String message;
	
	private List<StackTrace> stackTraces;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<StackTrace> getStackTraces() {
		return stackTraces;
	}

	public void setStackTraces(List<StackTrace> stackTraces) {
		this.stackTraces = stackTraces;
	}
	
	
}
