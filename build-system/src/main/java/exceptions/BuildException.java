package exceptions;

/**
 * Generic exception thrown for build failures.
 */
public class BuildException extends Exception {

    public BuildException(String message)
    {
        super(message);
    }


    public BuildException(Exception embedded)
    {
        super(embedded);
    }
}
