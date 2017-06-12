package tasks;

import exceptions.BuildException;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;

import static util.Util.*;


/**
 * A task to compile all source files in the app and dex them.
 */
public class CompileTask implements Task {
    private static final Logger logger = Logger.getLogger(CompileTask.class);

    @Override
    public void execute() throws BuildException {
        try {
            // Create output file dirs
            ProcessBuilder builder = new ProcessBuilder("mkdir", "-p", R_FILE_PATH, CLASSES_PATH, APK_FILE_PATH);
            Process process = builder.start();
            if (process.waitFor() != 0) {
                throw new BuildException("Failed to run Compile task -- Make dirs");
            }

            // Generate the R.java file
            builder.command(AAPT_PATH, "package", "-m", "-J", R_FILE_PATH, "-M", "AndroidManifest.xml", "-S", "res/", "-I", ANDROID_CP);
            process = builder.start();
            if (process.waitFor() != 0) {
                throw new BuildException("Failed to run Compile task -- Generate the R.java file");
            }

            // Compile java classes
            builder.command("/bin/bash", "-c", "find . -name \"*.java\"");
            process = builder.start();
            if (process.waitFor() != 0) {
                throw new BuildException("Failed to run Compile task -- Find java files for the next step");
            }
            String javaFileSb = getString(process.getInputStream());
            StringBuilder sb = new StringBuilder();
            sb.append(JAVA_HOME + "/bin/javac -classpath ").append(ANDROID_CP).append(" -sourcepath ").append(R_FILE_PATH).append(" -d ")
                    .append(CLASSES_PATH).append(" ").append(javaFileSb);
            builder.command("/bin/bash", "-c", sb.toString());
            process = builder.start();
            if (process.waitFor() != 0) {
                throw new BuildException("Failed to run Compile task -- Compile java classes");
            }

            // Create the dex file
            builder = new ProcessBuilder(DX_PATH, "--dex", "--output=classes.dex", CLASSES_PATH);
            process = builder.start();
            if (process.waitFor() != 0) {
                throw new BuildException("Failed to run Compile task -- Create the dex file");
            }
        } catch (IOException | InterruptedException e) {
            throw new BuildException(e);
        }
    }

    @Override
    public String taskName() {
        return TaskFactory.COMPILE_TASK;
    }

    @Override
    public int taskId() {
        return TaskFactory.COMPILE_TASK_ID;
    }


}
