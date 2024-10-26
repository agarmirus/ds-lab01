package ds.controller;

import spark.Response;
import spark.Request;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;

import ds.domain.Person;
import ds.model.IModel;
import ds.response.*;
import ds.utils.JsonConverter;

public class PersonController implements IController
{
    private IModel model = null;

    protected Person performPersonCreating(Request req, Response res)
    {
        Person result = null;
        IErrorResponse errRes = new ErrorResponse();

        try
        {
            res.type("application/json");

            Person newPerson = new Gson().fromJson(req.body(), Person.class);
            
            if (newPerson.getName() == null || newPerson.getName().trim().isEmpty())
                errRes.addProp("name");
            if (newPerson.getAge() == null || newPerson.getAge() < 0)
                errRes.addProp("age");
            if (newPerson.getAddress() == null || newPerson.getAddress().trim().isEmpty())
                errRes.addProp("address");
            if (newPerson.getWork() == null || newPerson.getWork().trim().isEmpty())
                errRes.addProp("work");

            if (errRes.isError())
            {
                errRes.setMessage("Invalid data");

                res.status(400);
                res.body(errRes.toString());
            }
            else
            {
                Optional<Person> optCreatedPerson = model.createPerson(newPerson);

                res.status(201);
                res.header("Location", String.format("/api/v1/persons/%d", optCreatedPerson.get().getId()));
            }
        }
        catch (Exception e)
        {
            errRes.setMessage(e.getMessage());

            res.status(500);
            res.body(errRes.toString());
        }

        return result;
    }

    protected List<Person> performAllPersonsReading(Response res)
    {
        List<Person> resultLst = null;
        IErrorResponse errRes = new ErrorResponse();

        try
        {
            res.type("application/json");

            Optional<List<Person>> optResultLst = model.getAllPersons();

            if (optResultLst.isPresent())
                resultLst = optResultLst.get();
            else
                resultLst = new ArrayList<Person>();

            res.status(200);
        }
        catch (Exception e)
        {
            errRes.setMessage(e.getMessage());

            res.status(500);
            res.body(errRes.toString());
        }

        return resultLst;
    }

    protected Person performPersonReading(Request req, Response res)
    {
        Person result = null;
        IErrorResponse errRes = new ErrorResponse();

        try
        {
            res.type("application/json");

            Person searchingPerson = new Person();
            searchingPerson.setId(Integer.parseInt(req.params(":id")));

            if (searchingPerson.getId() == null)
            {
                errRes.setMessage("Invalid data");

                res.status(400);
                res.body(errRes.toString());
            }
            else
            {
                Optional<Person> optFoundPerson = model.getPerson(searchingPerson);

                if (!optFoundPerson.isPresent())
                {
                    errRes.setMessage("Not found Person for ID");

                    res.status(404);
                    res.body(errRes.toString());
                }
                else
                {
                    result = optFoundPerson.get();
                    res.status(200);
                }
            }
        }
        catch (Exception e)
        {
            errRes.setMessage(e.getMessage());

            res.status(500);
            res.body(errRes.toString());
        }

        return result;
    }

    protected Person performPersonUpdate(Request req, Response res)
    {
        Person result = null;
        IErrorResponse errRes = new ErrorResponse();

        try
        {
            res.type("application/json");

            Person updatingPerson = new Gson().fromJson(req.body(), Person.class);
            updatingPerson.setId(Integer.parseInt(req.params(":id")));

            if (updatingPerson.getId() == null)
                errRes.addProp("id");
            if (updatingPerson.getName() == null || updatingPerson.getName().trim().isEmpty())
                errRes.addProp("name");
            if (updatingPerson.getAge() == null || updatingPerson.getAge() < 0)
                errRes.addProp("age");
            if (updatingPerson.getAddress() == null || updatingPerson.getAddress().trim().isEmpty())
                errRes.addProp("address");
            if (updatingPerson.getWork() == null || updatingPerson.getWork().trim().isEmpty())
                errRes.addProp("work");

            if (errRes.isError())
            {
                errRes.setMessage("Invalid data");

                res.status(400);
                res.body(errRes.toString());
            }
            else
            {
                Optional<Person> optUpdatedPerson = model.updatePerson(updatingPerson);

                if (!optUpdatedPerson.isPresent())
                {
                    errRes.setMessage("Not found Person for ID");

                    res.status(404);
                    res.body(errRes.toString());
                }
                else
                {
                    result = optUpdatedPerson.get();
                    res.status(200);
                }
            }
        }
        catch (Exception e)
        {
            errRes.setMessage(e.getMessage());

            res.status(500);
            res.body(errRes.toString());
        }

        return result;
    }

    protected Integer performPersonRemoving(Request req, Response res)
    {
        Integer removingPersonId = null;
        IErrorResponse errRes = new ErrorResponse();

        try
        {
            res.type("application/json");
            
            removingPersonId = Integer.parseInt(req.params(":id"));

            Person removingPerson = new Person();
            removingPerson.setId(removingPersonId);

            model.deletePerson(removingPerson);

            res.status(204);
        }
        catch (Exception e)
        {
            errRes.setMessage(e.getMessage());

            res.status(500);
            res.body(errRes.toString());
        }

        return removingPersonId;
    }

    public PersonController(final IModel model, final Integer port)
    {
        this.model = model;

        Spark.port(port);

        Spark.get("/api/v1/persons", (req, res) -> performAllPersonsReading(res), JsonConverter.json());
        Spark.get("/api/v1/persons/:id", (req, res) -> performPersonReading(req, res), JsonConverter.json());

        Spark.post("/api/v1/persons", (req, res) -> performPersonCreating(req, res), JsonConverter.json());
        
        Spark.delete("/api/v1/persons:id", (req, res) -> performPersonRemoving(req, res));

        Spark.patch("/api/v1/persons/:id", (req, res) -> performPersonUpdate(req, res), JsonConverter.json());
    }
}
