#!/bin/bash -ex

FILENAME=${WORKSPACE}/pom.xml
if [ -f $FILENAME ]; then
	sed -i $FILENAME -e "/<directory>\${user.home}\/apache-tomcat\/webapps<\/directory>/d"
	sed -i $FILENAME -e "0,/\(<version>\)\(.*\)\(<\/version>\)/s//\1\2-rev${SVN_REVISION}\3/"
fi

FILENAME=${WORKSPACE}/src/main/resources/cn/edu/cqu/ngtl/BootStrapConfig.xml
if [ -f $FILENAME ]; then
	sed -i $FILENAME -e "s@\(<param name=\"http.port\">\).*\(</param>\)@\180\2@" \
	                 -e "s@\(<param name=\"application.host\">\).*\(</param>\)@\1isse.cqu.edu.cn\2@"
	sed -i $FILENAME -e "s@\(<param name=\"dev.mode\">\).*\(</param>\)@\1true\2@"
fi
