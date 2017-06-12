package util;

import exceptions.BuildException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Util and some env variables settings
 */
public class Util {
    private static final Logger logger = Logger.getLogger(Util.class);
    public static final String ANDROID_HOME = System.getenv("ANDROID_HOME");
    public static final String JAVA_HOME = System.getenv("JAVA_HOME");
    public static String ANDROID_PLATFORMS_VERSION;
    public static String ANDROID_BUILD_TOOLS_VERSION;
    public static final String R_FILE_PATH = "gen";
    public static final String CLASSES_PATH = "build/intermediates/classes";
    public static final String APK_FILE_PATH = "build/output/apk";
    public static final String ADB_PATH = ANDROID_HOME + "/platform-tools/adb";
    public static String AAPT_PATH;
    public static String DX_PATH;
    public static String ANDROID_CP;
    public static String ZIPALIGN_PATH;

    static {
        ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", "ls $ANDROID_HOME/platforms | sort -n | tail -1");
        try {
            Process process = builder.start();
            if (process.waitFor() != 0) {
                throw new BuildException("Failed to find ANDROID_PLATFORMS_VERSION");
            }
            ANDROID_PLATFORMS_VERSION = getString(process.getInputStream());

            builder.command("/bin/bash", "-c", "ls $ANDROID_HOME//build-tools | sort -n | tail -1");
            process = builder.start();
            if (process.waitFor() != 0) {
                throw new BuildException("Failed to find ANDROID_BUILD_TOOLS_VERSION");
            }
            ANDROID_BUILD_TOOLS_VERSION = getString(process.getInputStream());

            AAPT_PATH = ANDROID_HOME + "/build-tools/" + ANDROID_BUILD_TOOLS_VERSION + "/aapt";
            DX_PATH = ANDROID_HOME + "/build-tools/" + ANDROID_BUILD_TOOLS_VERSION + "/dx";
            ANDROID_CP = ANDROID_HOME + "/platforms/" + ANDROID_PLATFORMS_VERSION + "/android.jar";
            ZIPALIGN_PATH = ANDROID_HOME + "/build-tools/" + ANDROID_BUILD_TOOLS_VERSION + "/zipalign";
        } catch (Exception e) {
            logger.error("Android platforms or build tools version not found");
            System.exit(1);
        }
    }

    public static String getString(InputStream is) throws IOException {
        Reader reader = new InputStreamReader(is);
        int ch;
        StringBuilder sb = new StringBuilder();

        while ((ch = reader.read()) != -1) {
            if (ch == '\n') {
                sb.append(" ");
            } else {
                sb.append((char) ch);
            }
        }
        reader.close();
        return sb.toString().trim();
    }
}
