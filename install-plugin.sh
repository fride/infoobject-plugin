#!/bin/sh

mvn  clean assembly:assembly -Dmaven.test.skip=true
cp target/infoobject-plugin-1.0-SNAPSHOT-magicmap-plugin.jar ../magicmapclient/plugins/infoobject.jar
#cp target/magic-infoobject-1.0-SNAPSHOT-magicmap-plugin.jar