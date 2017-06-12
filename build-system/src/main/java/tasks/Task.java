package tasks;

import exceptions.BuildException;


/**
 * Represents an executable task.
 */
public interface Task {
    /**
     * Execute this task.
     */
    void execute() throws BuildException;

    /**
     * The canonical name of this task.
     */
    String taskName();

    /**
     * The ID of this tasks. Tasks are executed in increasing order of their IDs.
     */
    int taskId();
}
