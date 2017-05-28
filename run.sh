#!/usr/bin/env bash

rm -rf target/OAuth2-Server-1.0-SNAPSHOT-jar-with-dependencies
mkdir target/OAuth2-Server-1.0-SNAPSHOT-jar-with-dependencies
cd target/OAuth2-Server-1.0-SNAPSHOT-jar-with-dependencies && unzip ../OAuth2-Server-1.0-SNAPSHOT-jar-with-dependencies.jar
classpath=`pwd`
java -cp $classpath com.strategicgains.oauth.Main
