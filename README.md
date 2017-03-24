# a log4j appender with spring-data-mongodb
如何使用:  

1、将在Spring中添加SpringContextUtil的bean对象
```
<bean id="SpringContextUtil" class="com.github.bokire.spring.SpringContextUtil"/>
```
2、配置log4j
```
log4j.appender.log4mongo=cn.cloudidea.log4mongo.MongoAppender
log4j.appender.log4mongo.Threshold=INFO
log4j.appender.log4mongo.mongoTemplateId=log4MongoTemplate
log4j.appender.log4mongo.collectionName=sys_log
```
