package tasks;

import exceptions.BuildException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Utility class to parse tasks from a build configuration file.
 */
public class TaskParser {
    public static List<Task> parseTasks(String filePath) throws IOException, BuildException {
        List<Task> tasks = new ArrayList<Task>();
        Reader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            reader = new InputStreamReader(new FileInputStream(filePath));
            int valueRead;
            String taskName;
            while ((valueRead = reader.read()) != -1) {
                char ch = (char) valueRead;
                if (!Character.isWhitespace(ch)) {
                    builder.append(ch);
                }

                if (Character.isWhitespace(ch) && builder.length() > 0) {
                    taskName = builder.toString().trim();
                    builder.setLength(0);
                    while ((valueRead = reader.read()) != -1) {
                        ch = (char) valueRead;
                        if (Character.isWhitespace(ch)) {
                            continue;
                        }

                        String extraCommand = null;
                        if (ch == '{') {
                            extraCommand = parseBlock(taskName, reader);
                        } else {
                            builder.append(ch);
                        }

                        Task task = TaskFactory.getTask(taskName, extraCommand);
                        if (task != null) {
                            tasks.add(task);
                            break;
                        }
                    }
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return tasks;
    }

    private static String parseBlock(String taskName, Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int valueRead;
        int nestingLevel = 1;
        while ((valueRead = reader.read()) != -1) {
            char ch = (char) valueRead;
            if (ch == '\n') {
                continue;
            }

            if (ch == '}') {
                nestingLevel--;
                if (nestingLevel == 0) {
                    break;
                }
            }

            builder.append(ch);
        }

        if (nestingLevel != 0) {
            throw new IOException("Did not find matching end curly brace for task: " + taskName);
        }

        return builder.toString();
    }
}
