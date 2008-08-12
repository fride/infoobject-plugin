#!/bin/sh
#if [ -f '~/.m2/repository/net/sf/magicmap/magicmapclient/0.9.4/magicmapclient-0.9.4.jar']; then
mvn install:install-file\
	-Dfile=../magicmapclient/magicmap.jar\
	-DgroupId=net.sf.magicmap\
	-DartifactId=magicmapclient\
	-Dversion=0.9.4\
	-DgeneratePom=true\
	-Dpackaging=jar
#fi

mvn install:install-file\
	-Dfile=../magicmapclient/lib/piccolo.jar\
	-DgroupId=piccolo\
	-DartifactId=piccolo\
	-Dversion=1.2\
	-DgeneratePom=true\
	-Dpackaging=jar

mvn install:install-file\
	-Dfile=../magicmapclient/lib/piccolox.jar\
	-DgroupId=piccolo\
	-DartifactId=piccolox\
	-Dversion=1.2\
	-DgeneratePom=true\
	-Dpackaging=jar

mvn install:install-file\
	-Dfile=../magicmapclient/lib/EventBus-1.1.jar\
	-DgroupId=eventbus\
	-DartifactId=eventbus\
	-Dversion=1.1\
	-DgeneratePom=true\
	-Dpackaging=jar

mvn install:install-file\
	-Dfile=../magicmapclient/lib/jung-1.5.2.jar\
	-DgroupId=net.sf.jung\
	-DartifactId=jung\
	-Dversion=1.5.2\
	-DgeneratePom=true\
	-Dpackaging=jar

mvn install:install-file\
	-Dfile=lib/smack.jar\
	-DgroupId=org.igniterealtime\
	-DartifactId=smack\
	-Dversion=3.0.4\
	-DgeneratePom=true\
	-Dpackaging=jar

mvn install:install-file\
	-Dfile=lib/smackx.jar\
	-DgroupId=org.igniterealtime\
	-DartifactId=smackx\
	-Dversion=3.0.4\
	-DgeneratePom=true\
	-Dpackaging=jar


mvn install:install-file\
	-Dfile=lib/smackx-debug.jar\
	-DgroupId=org.igniterealtime\
	-DartifactId=smackx-debug\
	-Dversion=3.0.4\
	-DgeneratePom=true\
	-Dpackaging=jar

#mvn install:install-file\
#	-Dfile=lib/db4o-6.4.48.10991-java5.jar\
#	-DgroupId=com.db4o\
#	-DartifactId=db4o\
#	-Dversion=6.4.48.10991\
#	-DgeneratePom=true\
#	-Dpackaging=jar

#mvn  assembly:assembly -Dmaven.test.skip=true
#cp target/infoobject-0.1-SNAPSHOT-magicmap-plugin.jar ../magicmapclient/plugins/infoobject.jar

