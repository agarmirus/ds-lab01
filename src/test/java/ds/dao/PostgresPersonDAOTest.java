package ds.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import ds.domain.Person;

public class PostgresPersonDAOTest
{
    static private PostgresPersonDAO dao;

    static private String connStr;
    static private Connection conn;

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

    @BeforeClass
    static public void preapareTest() throws Exception
    {
        String jsonString = readConfigFile("config.json");
        var jsonObject = new JSONObject(jsonString);

        connStr = jsonObject.getString("conn");
        conn = DriverManager.getConnection(connStr, "postgres", "postgres");
        dao = new PostgresPersonDAO(connStr, "postgres", "postgres");
    }

    @AfterClass
    static void cleanTest() throws Exception
    {
        Statement statement = conn.createStatement();
        statement.execute("call clear_tables()");

        conn.close();
    }

    @Before
    public void prepareDatabase() throws Exception
    {
        Statement statement = conn.createStatement();
        statement.execute("call generate_test_data()");
    }

    @After
    public void cleanDatabase() throws Exception
    {
        Statement statement = conn.createStatement();
        statement.execute("call clear_tables()");
    }

    @Test
    public void createTest() throws Exception
    {
        Person newPerson = new Person(6, "Oleg", 26, "Tombov", "Managment");

        Optional<Person> optResult = dao.create(newPerson);

        assertNotNull(optResult);
        assertTrue(optResult.isPresent());

        Person result = optResult.get();

        assertEquals(newPerson.getId(), result.getId());
    }

    @Test
    public void getAllTest() throws Exception
    {
        List<Person> validPersonLst = new ArrayList<Person>();
        validPersonLst.add(new Person(1, "Andrew", 19, "Moscow", "IT"));
        validPersonLst.add(new Person(2, "Sergey", 23, "Rostov", "Engineering"));
        validPersonLst.add(new Person(3, "Ivan", 35, "Saint Petersburg", "Science"));
        validPersonLst.add(new Person(4, "Anton", 22, "Moscow", "IT"));
        validPersonLst.add(new Person(5, "Elena", 27, "Kazan", "IT"));

        Optional<List<Person>> optResultLst = dao.get();

        assertNotNull(optResultLst);
        assertTrue(optResultLst.isPresent());

        List<Person> resultLst = optResultLst.get();

        assertEquals(validPersonLst.size(), resultLst.size());

        for (int i = 0; i < resultLst.size(); ++i)
        {
            Person curValidPerson = validPersonLst.get(i);
            Person curResultPerson = resultLst.get(i);

            assertEquals(curValidPerson.getId(), curResultPerson.getId());
            assertEquals(curValidPerson.getName(), curResultPerson.getName());
            assertEquals(curValidPerson.getAge(), curResultPerson.getAge());
            assertEquals(curValidPerson.getAddress(), curResultPerson.getAddress());
            assertEquals(curValidPerson.getWork(), curResultPerson.getWork());
        }
    }

    @Test
    public void getByIdTest() throws Exception
    {
        Person validPerson = new Person(3, "Ivan", 35, "Saint Petersburg", "Science");

        Optional<Person> optResult = dao.get(validPerson);

        assertNotNull(optResult);
        assertTrue(optResult.isPresent());

        Person result = optResult.get();

        assertEquals(validPerson.getId(), result.getId());
        assertEquals(validPerson.getName(), result.getName());
        assertEquals(validPerson.getAge(), result.getAge());
        assertEquals(validPerson.getAddress(), result.getAddress());
        assertEquals(validPerson.getWork(), result.getWork());
    }

    @Test
    public void updateTest() throws Exception
    {
        Person updatedPerson = new Person(3, "Ivan", 36, "Magadan", "Police");

        Optional<Person> optResult = dao.update(updatedPerson);

        assertNotNull(optResult);
        assertTrue(optResult.isPresent());

        Person result = optResult.get();

        assertEquals(updatedPerson.getName(), result.getName());
        assertEquals(updatedPerson.getAge(), result.getAge());
        assertEquals(updatedPerson.getAddress(), result.getAddress());
        assertEquals(updatedPerson.getWork(), result.getWork());
    }
}
