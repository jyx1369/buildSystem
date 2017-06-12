import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import tasks.Task;
import tasks.TaskParser;

import static util.Util.ANDROID_HOME;


/**
 * Main class to kick off a build.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    private static final String BUILD_FILE_PATH = "/Users/yuxij/Downloads/buildSystem/build-system/build.cfg";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("A build file absolute path is needed. Eg. $build_file_absolute_path/build.cfg");
        }
        if (ANDROID_HOME == null || ANDROID_HOME.equals("")) {
            throw new Exception("Please set ANDROID_HOME.");
        }

        logger.info("Starting to build.");
        String buildFilePath = args[0];
        List<Task> tasks = TaskParser.parseTasks(buildFilePath);

        // sort tasks by id
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.taskId() - o2.taskId();
            }
        });

        for (Task task : tasks) {
            logger.info("Executing: " + task.taskName());
            task.execute();
        }
        logger.info("Finished.");
    }
}
