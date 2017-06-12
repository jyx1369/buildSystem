package tasks;

import exceptions.BuildException;
import java.io.IOException;

import static util.Util.ADB_PATH;
import static util.Util.APK_FILE_PATH;


/**
 * A task to install and launch the Android application on the device/simulator.
 */
public class LaunchTask implements Task {
  @Override
  public void execute() throws BuildException {
    try {
      ProcessBuilder builder = new ProcessBuilder(ADB_PATH, "install", APK_FILE_PATH + "/signed.apk");
      Process process = builder.start();
      if (process.waitFor() != 0) {
        throw new BuildException("Failed to run Launch task!");
      }
    } catch (IOException | InterruptedException e) {
      throw new BuildException(e);
    }
  }

  @Override
  public String taskName()
  {
    return TaskFactory.LAUNCH_TASK;
  }

  @Override
  public int taskId()
  {
    return TaskFactory.LAUNCH_TASK_ID;
  }
}
