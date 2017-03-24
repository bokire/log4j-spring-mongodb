package com.github.bokire.log4mongo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.github.bokire.spring.SpringContextUtil;
import com.mongodb.MongoException;

public class MongoAppender extends AppenderSkeleton {

	//private LoggingEventBsonifier bsonifier = new LoggingEventBsonifierImpl();

	private LoggingEventTranslate loggingEventTranslate = new LoggingEventTranslateImpl();
	
	private MongoTemplate mongoTemplate;
	
	private String mongoTemplateId;
	
	private String collectionName;
	
	private String translateBeanId;
	
    /**
     * @see org.apache.log4j.Appender#requiresLayout()
     */
    public boolean requiresLayout() {
        return false;
    }

    @Override
    public void activateOptions() {
    	if(StringUtils.isEmpty(this.mongoTemplateId) ||
    			StringUtils.isEmpty(this.collectionName)){
    		 LogLog.error("Either mongoTemplateId or collectionName options are not set for appender [" + this.name + "].It doesn't work!");
    	}
    }
    /**
     * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
     */
    @Override
    protected void append(final LoggingEvent loggingEvent) {
    	try {
    		if(StringUtils.isNotEmpty(this.mongoTemplateId) &&
    				StringUtils.isNotEmpty(this.collectionName)) {
    			
    			Object bean = SpringContextUtil.getBean(mongoTemplateId);
    	    	if(bean instanceof MongoTemplate) {
    	    		setMongoTemplate((MongoTemplate) bean);
    	    	} 
    	    	if(mongoTemplate != null) {
        			//DBObject bson = bsonifier.bsonify(loggingEvent);
        			if(StringUtils.isNotEmpty(this.translateBeanId)) {
        				Object translateBean = SpringContextUtil.getBean(translateBeanId);
        				if(translateBean instanceof LoggingEventTranslate) {
        					setLoggingEventTranslate(loggingEventTranslate); 
            	    	} 
        			}
        	        mongoTemplate.save(loggingEventTranslate.translate(loggingEvent), this.collectionName);
    	    	} else {
    	    		errorHandler.error("can't find mongoTemplate ");
    	    	}
    		}
    	
    	} catch (MongoException e) {
            errorHandler.error("Failed to append log to MongoDB", e,
                    ErrorCode.WRITE_FAILURE);
        } catch(Exception e) {
        	 errorHandler.error("System error happened when append log to MongoDB", e,
                     ErrorCode.WRITE_FAILURE);
        }
    }


	@Override
	public void close() {
		
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public String getMongoTemplateId() {
		return mongoTemplateId;
	}

	public void setMongoTemplateId(String mongoTemplateId) {
		this.mongoTemplateId = mongoTemplateId;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getTranslateBeanId() {
		return translateBeanId;
	}

	public void setTranslateBeanId(String translateBeanId) {
		this.translateBeanId = translateBeanId;
	}

	public LoggingEventTranslate getLoggingEventTranslate() {
		return loggingEventTranslate;
	}

	public void setLoggingEventTranslate(LoggingEventTranslate loggingEventTranslate) {
		this.loggingEventTranslate = loggingEventTranslate;
	}
	
	
}
