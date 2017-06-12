* Assumption:
1. ANDROID_HOME and JAVA_HOME are defined;
2. An active simulator is running already.
2. Android project contains at least these three: src/, res/, AndroidManifest.xml. And we only support Android java
classes from Android.jar
3. Code is always run from android project's root directory
4. A build.cfg sample file is already provided inside the project root dir.


* How to build:
```
   cd root directory
   java -jar ${build_system_path}/target/build-system-1.0-SNAPSHOT.jar $build_file_absolute_path/build.cfg
```


* After running the jar, the android project's layout should be:
```
AndroidProject
   src/ 
 	res/
  	AndroidManifest.xml
  	gen/  (R.java inside)	
 	build/
      intermediates/classes (.class files)
		output/apk/ (apk files inside)
  	AndroidTest.keystore
 	classes.dex
```   
   
