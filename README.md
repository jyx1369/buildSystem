* Since `android create project` command is deprecated, I was not able to create the android project from the sdk commmand lines. So here I used the sample android project, "AndroidTest" as the base to create the android project structure(which should create res/, src/, AndroidManifest.xml files inside the new android project).


* For creating the Android project, run 
```
sh create_android_project.sh
```
which will create a dir named "AndroidProject", which will have the android project structure, referenced by sample android project AndroidTest.


* There is also a simple script, which will both create and build the android project. 
```
sh create_and_build_android_project_sample.sh
```
In order for you to use, please change the build.cfg file to your own (absolute path needed), and run this command under buildSystem dir.


* There is a additional “README” file inside build_system dir. Please read before building the project.
