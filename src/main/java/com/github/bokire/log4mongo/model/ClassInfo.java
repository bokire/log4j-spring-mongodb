package com.github.bokire.log4mongo.model;

public class ClassInfo {
	
	private String className;
	
	private String fullyQualifiedClassName;
	
	private String[] package_path;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getFullyQualifiedClassName() {
		return fullyQualifiedClassName;
	}
	public void setFullyQualifiedClassName(String fullyQualifiedClassName) {
		this.fullyQualifiedClassName = fullyQualifiedClassName;
	}
	public String[] getPackage_path() {
		return package_path;
	}
	public void setPackage_path(String[] package_path) {
		this.package_path = package_path;
	} 
	
	
}
