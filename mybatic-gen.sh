#!/usr/bin/env bash
java -jar \
    libs/mybatis-generator-core-1.3.5.jar \
    -configfile src/main/resources/generatorConfig.xml -overwrite