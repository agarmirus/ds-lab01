package ds.appexceptions;

public class AppDataAccessException extends AppException
{
    public AppDataAccessException() {  super(); }
    public AppDataAccessException(String message) { super(message); }
    public AppDataAccessException(String message, Throwable cause) { super(message, cause); }
}
