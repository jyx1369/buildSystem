package tasks;

import exceptions.BuildException;
import java.io.IOException;


/**
 * A task to run a geneic command.
 */
public class CommandTask implements Task {
  private final int taskId;
  private final String taskName;
  private final String command;

  public CommandTask(int taskId, String taskName, String command) {
    this.taskId = taskId;
    this.taskName = taskName;
    this.command = command;
  }

  @Override
  public void execute() throws BuildException {
    try {
      Runtime.getRuntime().exec(this.command);
    } catch (IOException e) {
      throw new BuildException(e);
    }
  }

  @Override
  public String taskName()
  {
    return this.taskName;
  }

  @Override
  public int taskId()
  {
    return this.taskId;
  }
}
