package com.github.bokire.log4mongo;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import com.github.bokire.log4mongo.model.ClassInfo;
import com.github.bokire.log4mongo.model.ExceptionThrowable;
import com.github.bokire.log4mongo.model.Host;
import com.github.bokire.log4mongo.model.Log;
import com.github.bokire.log4mongo.model.StackTrace;

public class LoggingEventTranslateImpl implements LoggingEventTranslate {

	public Object translate(LoggingEvent loggingEvent) {
		Log log = new Log();
		log.setLogTime(new Date(loggingEvent.getTimeStamp()));
		log.setLevel(loggingEvent.getLevel().toString());
		log.setThread(loggingEvent.getThreadName());
		log.setMessage(loggingEvent.getMessage());
		
		addHostInfor(log);
		addLocationInfor(log, loggingEvent.getLocationInformation());
		addThrowableInfor(log, loggingEvent.getThrowableInformation());
		return log;
	}
	
	/**
	 * 添加host信息
	 * @param log
	 */
	private void addHostInfor(Log log) {
		Host host = new Host();
		host.setProcess(ManagementFactory.getRuntimeMXBean().getName());
        try {
        	host.setName(InetAddress.getLocalHost().getHostName());
        	host.setIp(InetAddress.getLocalHost().getHostAddress());
        	log.setHost(host);
        } catch (UnknownHostException e) {
            LogLog.warn(e.getMessage());
        }
	}
	
	/**
	 * 添加location信息
	 * @param log
	 * @param locationInfo
	 */
	private void addLocationInfor(Log log, final LocationInfo locationInfo) {
        if (locationInfo != null) {
            log.setFileName(locationInfo.getFileName());
            log.setMethod(locationInfo.getMethodName());
            log.setLineNumber(locationInfo.getLineNumber());
            log.setClassInfo(buildClassInfo(locationInfo.getClassName()));
        }
    }
	
	/**
	 * 构建class信息
	 * @param className
	 */
    private ClassInfo buildClassInfo(final String className) {
    	ClassInfo result = null;

        if (className != null && className.trim().length() > 0) {
            result = new ClassInfo();
            result.setFullyQualifiedClassName(className);
            String[] package_path = className.split("\\.");
            result.setPackage_path(package_path);
            result.setClassName(package_path[package_path.length - 1]);
        }
        return (result);
    }
    
    /**
     * 添加 异常信息
     * @param log
     * @param throwableInfo
     */
    private void addThrowableInfor(Log log, final ThrowableInformation throwableInfo) {
        if (throwableInfo != null) {
            Throwable currentThrowable = throwableInfo.getThrowable();
            List<ExceptionThrowable> throwables = new ArrayList<ExceptionThrowable>();

            while (currentThrowable != null) {
            	ExceptionThrowable throwable = buildThrowable(currentThrowable);

                if (throwable != null) {
                    throwables.add(throwable);
                }

                currentThrowable = currentThrowable.getCause();
            }

            if (throwables.size() > 0) {
            	log.setExceptionThrowable(throwables);
            }
        }
    }

    /**
     * 构建异常信息
     * @param throwable
     * @return
     */
	private ExceptionThrowable buildThrowable(Throwable throwable) {
		ExceptionThrowable result = null;

        if (throwable != null) {
            result = new ExceptionThrowable();

            result.setMessage(throwable.getMessage());
            result.setStackTraces(buildStackTrace(throwable.getStackTrace()));
        }

        return (result);
	}

	/**
	 * 构建异常堆栈信息
	 * @param stackTrace
	 * @return
	 */
	private List<StackTrace> buildStackTrace(StackTraceElement[] stackTrace) {
		List<StackTrace> result = null;

        if (stackTrace != null && stackTrace.length > 0) {
            result = new ArrayList<StackTrace>();

            for (int i=0; i<stackTrace.length && i<10; i++ ) {
            	StackTraceElement element = stackTrace[i];
            	StackTrace st = new StackTrace();
            	st.setFileName(element.getFileName());
            	st.setLineNumber(element.getLineNumber());
            	st.setMethod(element.getMethodName());
            	st.setClassName(element.getClassName());
            	result.add(st);
            }
        }

        return (result);
	}
}
