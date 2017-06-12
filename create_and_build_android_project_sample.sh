#!/bin/sh
sh create_android_project.sh
cd AndroidProject
java -jar ../build-system/target/build-system-1.0-SNAPSHOT.jar  /Users/yuxij/Downloads/buildSystem/build-system/build.cfg
