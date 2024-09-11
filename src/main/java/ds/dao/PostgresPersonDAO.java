package ds.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import ds.appexceptions.*;
import ds.domain.Person;

public class PostgresPersonDAO implements IDAO<Person>
{
    private Connection conn;

    public PostgresPersonDAO(String url, String usr, String pswd) throws Exception
    {
        this.conn = DriverManager.getConnection(url, usr, pswd);
    }

    public void setConnection(String url, String user, String pswd) throws AppException
    {
        try
        {
            if (!conn.isClosed())
                this.conn.close();

            this.conn = DriverManager.getConnection(
                url,
                user,
                pswd
            );
        }
        catch (SQLException e)
        {
            throw new AppDataAccessException(
                String.format(
                    "PostgresPersonDAO.setConnection(conn): %s",
                    e.getMessage()
                )
            );
        }
    }

    public Optional<Person> create(final Person person) throws AppException
    {
        Optional<Person> optResult = Optional.empty();

        try
        {
            if (conn.isClosed() || !conn.isValid(0))
            {
                throw new AppDataAccessException(
                    "PostgresPersonDAO.create(person): no connection to database"
                );
            }

            String query = String.format(
                "insert into person values ('%s', %d, '%s', '%s') returning id;",
                person.getName(),
                person.getAge(),
                person.getAddress(),
                person.getWork()
            );

            Statement statement = conn.createStatement();
            ResultSet queryResultSet = statement.executeQuery(query);

            if (queryResultSet.next())
            {
                var result = new Person();
                result.setId(queryResultSet.getInt("id"));

                optResult = Optional.of(result);
            }
        }
        catch (SQLException e)
        {
            throw new AppDataAccessException(
                String.format(
                    "PostgresPersonDAO.create(person): %s",
                    e.getMessage()
                )
            );
        }

        return optResult;
    }

    public Optional<List<Person>> get() throws AppException
    {
        Optional<List<Person>> optResult = Optional.empty();
        
        try
        {
            if (conn.isClosed() || !conn.isValid(0))
            {
                throw new AppDataAccessException(
                    "PostgresPersonDAO.get(): no connection to database"
                );
            }

            String query = "select * from person;";

            Statement statement = conn.createStatement();
            ResultSet queryResultSet = statement.executeQuery(query);

            List<Person> resultLst = new ArrayList<Person>();

            while (queryResultSet.next())
            {
                var person = new Person(
                    queryResultSet.getInt("id"),
                    queryResultSet.getString("name"),
                    queryResultSet.getInt("age"),
                    queryResultSet.getString("address"),
                    queryResultSet.getString("work")
                );

                resultLst.add(person);
            }

            if (!resultLst.isEmpty())
                optResult = Optional.of(resultLst);
        }
        catch (SQLException e)
        {
            throw new AppDataAccessException(
                String.format(
                    "PostgresPersonDAO.get(): %s",
                    e.getMessage()
                )
            );
        }

        return optResult;
    }

    public Optional<Person> get(final Person person) throws AppException
    {
        Optional<Person> optResult = Optional.empty();

        try
        {
            if (conn.isClosed() || !conn.isValid(0))
            {
                throw new AppDataAccessException(
                    "PostgresPersonDAO.get(person): no connection to database"
                );
            }

            String query = String.format(
                "select * from person where id = %d;",
                person.getId()
            );

            Statement statement = conn.createStatement();
            ResultSet queryResultSet = statement.executeQuery(query);

            if (queryResultSet.next())
            {
                var result = new Person(
                    queryResultSet.getInt("id"),
                    queryResultSet.getString("name"),
                    queryResultSet.getInt("age"),
                    queryResultSet.getString("address"),
                    queryResultSet.getString("work")
                );

                optResult = Optional.of(result);
            }
        }
        catch (SQLException e)
        {
            throw new AppDataAccessException(
                String.format(
                    "PostgresPersonDAO.get(person): %s",
                    e.getMessage()
                )
            );
        }

        return optResult;
    }

    public Optional<Person> update(final Person person) throws AppException
    {
        Optional<Person> optResult = Optional.empty();

        try
        {
            if (conn.isClosed() || !conn.isValid(0))
            {
                throw new AppDataAccessException(
                    "PostgresPersonDAO.update(person): no connection to database"
                );
            }

            String query = String.format(
                "update person set name = '%s', age = %d, address = '%s', work = '%s' where id = %d returning *;",
                person.getName(),
                person.getAge(),
                person.getAddress(),
                person.getWork(),
                person.getId()
            );

            Statement statement = conn.createStatement();
            ResultSet queryResultSet = statement.executeQuery(query);

            if (queryResultSet.next())
            {
                var result = new Person(
                    queryResultSet.getInt("id"),
                    queryResultSet.getString("name"),
                    queryResultSet.getInt("age"),
                    queryResultSet.getString("address"),
                    queryResultSet.getString("work")
                );

                optResult = Optional.of(result);
            }
        }
        catch (SQLException e)
        {
            throw new AppDataAccessException(
                String.format(
                    "PostgresPersonDAO.update(person): %s",
                    e.getMessage()
                )
            );
        }

        return optResult;
    }

    public void delete(final Person person) throws AppException
    {
        try
        {
            if (conn.isClosed() || !conn.isValid(0))
            {
                throw new AppDataAccessException(
                    "PostgresPersonDAO.delete(person): no connection to database"
                );
            }

            String query = String.format(
                "delete from person where id = %d;",
                person.getId()
            );

            Statement statement = conn.createStatement();
            statement.executeQuery(query);
        }
        catch (SQLException e)
        {
            throw new AppDataAccessException(
                String.format(
                    "PostgresPersonDAO.delete(person): %s",
                    e.getMessage()
                )
            );
        }
    }
}
