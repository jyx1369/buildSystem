package tasks;

import org.apache.log4j.Logger;
import exceptions.BuildException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A factory to create tasks.
 */
public class TaskFactory
{
  public static final int COMPILE_TASK_ID = 2;
  public static final int PACKAGE_TASK_ID = 5;
  public static final int LAUNCH_TASK_ID = 8;
  private static final Logger logger = Logger.getLogger(TaskFactory.class);
  static final String COMPILE_TASK = "compile";
  static final String PACKAGE_TASK = "package";
  static final String LAUNCH_TASK = "launch";

  private static final String BEFORE_SUFFIX = ".before";
  private static final String AFTER_SUFFIX = ".after";

  private static final Map<String, Integer> TASK_NAME_TO_ID_MAP = new HashMap<String, Integer>();
  static {
    TASK_NAME_TO_ID_MAP.put(COMPILE_TASK, COMPILE_TASK_ID);
    TASK_NAME_TO_ID_MAP.put(PACKAGE_TASK, PACKAGE_TASK_ID);
    TASK_NAME_TO_ID_MAP.put(LAUNCH_TASK, LAUNCH_TASK_ID);
  }

  public static Task getTask(String taskName, String extraCommand) throws BuildException {
    String sanitizedTaskName = taskName.toLowerCase(Locale.ENGLISH);
    int indexOffset = 0;
    if (sanitizedTaskName.endsWith(BEFORE_SUFFIX)) {
      sanitizedTaskName = sanitizedTaskName.substring(0, sanitizedTaskName.length() - BEFORE_SUFFIX.length());
      indexOffset = -1;
    } else if (sanitizedTaskName.endsWith(AFTER_SUFFIX)) {
      sanitizedTaskName = sanitizedTaskName.substring(0, sanitizedTaskName.length() - AFTER_SUFFIX.length());
      indexOffset = 1;
    }

    Integer taskId = TASK_NAME_TO_ID_MAP.get(sanitizedTaskName);
    if (taskId == null) {
      logger.error("Unknown task " + taskName + " Ignoring");
      return null;
    }

    taskId = taskId + indexOffset;

    switch (taskId) {
      case COMPILE_TASK_ID:
        return new CompileTask();
      case PACKAGE_TASK_ID:
        return new PackageTask();
      case LAUNCH_TASK_ID:
        return new LaunchTask();
      default:
        return (extraCommand == null) ? null : new CommandTask(taskId, taskName, extraCommand);
    }
  }
}
