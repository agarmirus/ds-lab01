package ds.appexceptions;

public class AppInvalidPersonDataException extends AppException
{
    public AppInvalidPersonDataException() {  super(); }
    public AppInvalidPersonDataException(String message) { super(message); }
    public AppInvalidPersonDataException(String message, Throwable cause) { super(message, cause); }
}
