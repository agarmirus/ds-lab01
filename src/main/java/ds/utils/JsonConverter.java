package ds.utils;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class JsonConverter
{
    public static String toJson(Object object)
    {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json()
    {
        return JsonConverter::toJson;
    }
}
