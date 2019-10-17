Query to find duplicate usernames :

select * from (select user_name,count(*) from user_tbl u group by user_name) as uu where uu.count > 1  



<!-- configuration file for LogBack (slf4J implementation) See here for more 
	details: http://gordondickens.com/wordpress/2013/03/27/sawing-through-the-java-loggers/ -->
<!-- <configuration debug="false" scanPeriod="30 seconds"> -->

<!-- <contextName>bbbp</contextName> -->
<!-- 	<!-- <property name="DEV_HOME" value="D:/bbbplogs/" /> --> -->
<!-- 	<!-- <property name="DEV_HOME" value="/opt/ess/logs" /> --> -->

<!-- 	<appender name="console" class="ch.qos.logback.core.ConsoleAppender"> -->
<!-- 		<!-- encoders are assigned the type ch.qos.logback.classic.encoder.PatternLayoutEncoder  -->
<!-- 			by default --> -->
<!-- 		<encoder> -->
<!-- 			<pattern> -->
<!-- 				%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg %ex %n -->
<!-- 			</pattern> -->
<!-- 		</encoder> -->
<!-- 	</appender> -->

<!-- 	<appender name="file" -->
<!-- 		class="ch.qos.logback.core.rolling.RollingFileAppender"> -->
<!-- 		 <file>bbbp.log</file> -->
<!-- 		<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder"> -->
<!-- 			 <pattern>%date %level [%thread] %logger{10}  %msg %ex%n</pattern> -->
<!-- 		</encoder> -->

<!-- 		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy"> -->
<!-- 			<!-- rollover daily --> -->
<!-- 			<fileNamePattern>${DEV_HOME}/archived/debug-test.%d{dd-MM-yyyy}.%i.log -->
<!-- 			</fileNamePattern> -->
<!-- 			<timeBasedFileNamingAndTriggeringPolicy -->
<!-- 				class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP"> -->
<!-- 				<maxFileSize>10MB</maxFileSize> -->
<!-- 			</timeBasedFileNamingAndTriggeringPolicy> -->
<!-- 		</rollingPolicy> -->
<!-- 	</appender> -->

<!-- 	<appender name="email" class="ch.qos.logback.classic.net.SMTPAppender"> -->
<!-- 		<smtpHost>smtp.gmail.com</smtpHost> -->
<!-- 		<smtpPort>587</smtpPort> -->
<!-- 		<STARTTLS>true</STARTTLS> -->
<!-- 		<username>techsupport@sdrc.co.in</username> -->
<!-- 		<password>pass@123</password> -->
<!-- 		<!-- <to>bbbpmis@sdrc.co.in</to> --> -->
<!-- 		<to>azaruddin@sdrc.co.in</to> -->
<!-- 		<subject>Exception Occurred in BBBP Production: %logger{20} -->
<!-- 		</subject> -->
<!-- 		<layout class="ch.qos.logback.classic.PatternLayout"> -->
<!-- 			%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} -%msg %ex %n -->
<!-- 		</layout> -->
<!-- 	</appender> -->

<!-- 	<logger name="org.sdrc.bbbp" level="info" additivity="false"> -->
<!-- 		<appender-ref ref="console" /> -->
<!-- 		<appender-ref ref="file" /> -->
<!-- 	</logger> -->

<!-- 	<logger name="org.sdrc.bbbp" level="error" additivity="false"> -->
<!-- 		<appender-ref ref="console" /> -->
<!-- 		<appender-ref ref="file" /> -->
<!-- 		<appender-ref ref="email" /> -->
<!-- 	</logger> -->

<!-- 	<root level="debug"> -->
<!-- 		<appender-ref ref="console" /> -->
<!-- 		<appender-ref ref="file" /> -->
<!-- 		<appender-ref ref="email" /> -->
<!-- 	</root> -->

<!-- </configuration> -->

<!-- 	<include resource="org/springframework/boot/logging/logback/base.xml"/> -->

<!-- <property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/> -->

