package ds.controller;

import spark.Response;
import spark.Route;
import spark.Request;
import spark.Spark;

import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;

import ds.appexceptions.AppException;
import ds.domain.Person;
import ds.model.IModel;
import ds.utils.JsonConverter;

public class PersonController implements IController
{
    private IModel model = null;

    protected Person performPersonCreating(Request req, Response res)
    {
        return null;
    }

    protected List<Person> performAllPersonsReading()
    {
        return null;
    }

    protected Person performPersonReading(Request req)
    {
        return null;
    }

    protected Person performPersonUpdate(Request req)
    {
        return null;
    }

    protected Integer performPersonRemoving(Request req)
    {
        return null;
    }

    public PersonController(final IModel model)
    {
        this.model = model;

        Spark.get("/api/v1/persons", (req, res) -> performAllPersonsReading(), JsonConverter.json());
        Spark.get("/api/v1/persons/:id", (req, res) -> performPersonReading(req), JsonConverter.json());

        Spark.post("/api/v1/persons", (req, res) -> performPersonCreating(req, res), JsonConverter.json());
        
        Spark.delete("/api/v1/persons:id", (req, res) -> performPersonRemoving(req));

        Spark.patch("/api/v1/persons/:id", (req, res) -> performPersonUpdate(req), JsonConverter.json());
    }
}
