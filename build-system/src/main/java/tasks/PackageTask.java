package tasks;

import exceptions.BuildException;

import java.io.IOException;

import static util.Util.*;


/**
 * A task to package all resources and compiled code inside an android application package (APK) file and sign it.
 */
public class PackageTask implements Task
{

  @Override
  public void execute() throws BuildException {
    try {
      // Create keystore
      ProcessBuilder builder = new ProcessBuilder(JAVA_HOME + "/bin/keytool", "-genkeypair", "-validity",
              "10000", "-dname", "CN=yuxij,OU=1,O=yuxij,L=Sf,S=CA,C=01", "-keystore", "AndroidTest.keystore",
              "-storepass", "password", "-keypass", "password", "-alias", "AndroidTestKey", "-keyalg", "RSA", "-v");
      Process process = builder.start();
      if (process.waitFor() != 0) {
        throw new BuildException("Failed to run Package task -- Create keystore");
      }

      // Create the initial APK package
      builder.command(AAPT_PATH, "package", "-f", "-M", "AndroidManifest.xml", "-S", "res", "-I", ANDROID_CP, "-F",
              APK_FILE_PATH + "/unsigned.apk");
      process = builder.start();
      if (process.waitFor() != 0) {
        throw new BuildException("Failed to run Package task -- Create the initial APK package");
      }

      // Add compiled binaries to the existing APK
      builder.command(AAPT_PATH, "add", APK_FILE_PATH + "/unsigned.apk", "classes.dex");
      process = builder.start();
      if (process.waitFor() != 0) {
        throw new BuildException("Failed to run Package task -- Add compiled binaries to the existing APK");
      }

      // Sign APK file
      builder.command(JAVA_HOME + "/bin/jarsigner", "-keystore", "AndroidTest.keystore", "-storepass", "password",
              "-keypass", "password", APK_FILE_PATH + "/unsigned.apk", "AndroidTestKey");
      process = builder.start();
      if (process.waitFor() != 0) {
        throw new BuildException("Failed to run Package task -- Sign APK file");
      }

      // Sign APK file
      builder.command(ZIPALIGN_PATH, "-v", "-f", "4", APK_FILE_PATH + "/unsigned.apk", APK_FILE_PATH + "/signed.apk");
      process = builder.start();
      if (process.waitFor() != 0) {
        throw new BuildException("Failed to run Package task -- Zipalign APK file");
      }
    } catch (IOException | InterruptedException e) {
      throw new BuildException(e);
    }
  }

  @Override
  public String taskName()
  {
    return TaskFactory.PACKAGE_TASK;
  }

  @Override
  public int taskId()
  {
    return TaskFactory.PACKAGE_TASK_ID;
  }
}
