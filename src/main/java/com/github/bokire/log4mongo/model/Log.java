package com.github.bokire.log4mongo.model;

import java.util.Date;
import java.util.List;

public class Log {

	private ClassInfo classInfo ;
	
	private String fileName;
	
	private Host host;
	
	private String level;
	
	private String lineNumber;
	
	private Object message;
	
	private String method;
	
	private String thread;
	
	private Date logTime;
	
	private List<ExceptionThrowable> exceptionThrowable;
	
	public ClassInfo getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}

	public List<ExceptionThrowable> getExceptionThrowable() {
		return exceptionThrowable;
	}

	public void setExceptionThrowable(List<ExceptionThrowable> exceptionThrowable) {
		this.exceptionThrowable = exceptionThrowable;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	
}
