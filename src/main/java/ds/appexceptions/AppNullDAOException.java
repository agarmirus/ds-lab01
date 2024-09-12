package ds.appexceptions;

public class AppNullDAOException extends AppException
{
    public AppNullDAOException() {  super(); }
    public AppNullDAOException(String message) { super(message); }
    public AppNullDAOException(String message, Throwable cause) { super(message, cause); }    
}
