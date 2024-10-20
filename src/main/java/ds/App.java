package ds;

import java.io.BufferedReader;
import java.io.FileReader;

import org.json.JSONObject;

import ds.controller.IController;
import ds.controller.PersonController;
import ds.dao.IDAO;
import ds.dao.PostgresPersonDAO;
import ds.domain.Person;
import ds.model.IModel;
import ds.model.PersonModel;

public class App
{
    static protected String readConfigFile(String fileName) throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String result = "";

        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null)
        {
            sb.append(line);
            sb.append(System.lineSeparator());

            line = br.readLine();
        }

        result = sb.toString();

        br.close();

        return result;
    }
    
    public static void main(String[] args)
    {
        try
        {
            String jsonString = readConfigFile("config.json");
            JSONObject jsonObject = new JSONObject(jsonString);

            String connStr = jsonObject.getString("conn");
            Integer port = jsonObject.getInt("port");

            IDAO<Person> personDAO = new PostgresPersonDAO(connStr, "postgres", "postgres");
            IModel personModel = new PersonModel(personDAO);
            IController personController = new PersonController(personModel, port);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
