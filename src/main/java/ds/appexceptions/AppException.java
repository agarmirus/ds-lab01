package ds.appexceptions;

import java.lang.Exception;

public class AppException extends Exception
{
    public AppException() {  super(); }
    public AppException(String message) { super(message); }
    public AppException(String message, Throwable cause) { super(message, cause); }
}
