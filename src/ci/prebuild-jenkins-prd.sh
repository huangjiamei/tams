#!/bin/bash -ex

FILENAME=${WORKSPACE}/pom.xml
if [ -f $FILENAME ]; then
	sed -i $FILENAME -e "/<directory>\${user.home}\/apache-tomcat\/webapps<\/directory>/d"
fi

FILENAME=${WORKSPACE}/src/main/resources/cn/edu/cqu/ngtl/BootStrapConfig.xml
if [ -f $FILENAME ]; then
	sed -i $FILENAME -e "s@\(<param name=\"http\.port\">\).*\(</param>\)@\180\2@" \
	                 -e "s@\(<param name=\"application\.host\">\).*\(</param>\)@\1mystudy.cqu.edu.cn\2@" \
	                 -e "s@\(<param name=\"dev\.mode\">\).*\(</param>\)@\1false\2@" \
	                 -e "s@LoginFilter@IdstarLoginFilter@" \
	                 -e "s@\(<param name=\"datasource\.url\">\).*\(</param>\)@\1jdbc:oracle:thin:\@202.202.0.123:1521/jwdb\2@" \
	                 -e "s@\(<param name=\"datasource\.username\">\).*\(</param>\)@\1cqdx_jxglxx\2@" \
	                 -e "s@\(<param name=\"datasource\.password\">\).*\(</param>\)@\1BdsVm2a86dbB\2@" 
fi

PROPERTYNAME=${WORKSPACE}/src/main/resources/log4j.properties
if [ -f $PROPERTYNAME ]; then
	sed -i $PROPERTYNAME -e 's/jdbc:oracle:thin:@10.254.9.30:1521:orcl/jdbc:oracle:thin:@202.202.0.123:1521\/jwdb/' \
			     -e 's/log4j.appender.exam.user=RICE/log4j.appender.exam.user=cqdx_jxglxx/' \
			     -e 's/log4j.appender.exam.password=RICE/log4j.appender.exam.password=BdsVm2a86dbB/'
fi			 


		 