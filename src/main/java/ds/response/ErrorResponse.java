package ds.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

public class ErrorResponse implements IErrorResponse
{
    private String msg = null;
    private List<String> props = new ArrayList<String>();

    public ErrorResponse() {}
    public ErrorResponse(String msg) { this.msg = msg; }

    public String getMessage() { return msg; }
    public void setMessage(String msg) { this.msg = msg; }

    public void addProp(String prop) { props.add(prop); }

    public String toString()
    {
        String result = "";

        if (msg != null)
        {
            var jsonObject = new JsonObject();

            jsonObject.addProperty("message", msg);
            
            if (!props.isEmpty())
            {
                var errorJsonObject = new JsonObject();

                for (int i = 0; i < props.size(); ++i)
                    errorJsonObject.addProperty(String.format("additionalProp%d", i + 1), props.get(i));

                jsonObject.add("errors", errorJsonObject);
            }

            result = jsonObject.toString();
        }

        return result;
    }

    public Boolean isError()
    {
        return (msg != null && !msg.isEmpty()) || props.size() > 0;
    }
}
