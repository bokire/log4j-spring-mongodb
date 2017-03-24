package com.github.bokire.log4mongo;

import org.apache.log4j.spi.LoggingEvent;

public interface LoggingEventTranslate {

	public Object translate(LoggingEvent loggingEvent) ;
}
