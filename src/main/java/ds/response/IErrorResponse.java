package ds.response;

public interface IErrorResponse
{
    public String getMessage();
    public void setMessage(String msg);

    public void addProp(String prop);

    public String toString();

    public Boolean isError();
}
