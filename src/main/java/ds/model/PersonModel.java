package ds.model;

import java.util.List;
import java.util.Optional;

import ds.appexceptions.*;
import ds.dao.IDAO;
import ds.domain.Person;

public class PersonModel implements IModel
{
    private IDAO<Person> personDAO = null;

    public PersonModel(final IDAO<Person> personDAO) { this.personDAO = personDAO; }

    public Optional<Person> createPerson(final Person person) throws AppException
    {
        if (personDAO == null)
            throw new AppNullDAOException(
                "PersonModel.createPerson(person): person DAO is null"
            );

        if (person.getName() == null || person.getName().trim().isEmpty())
            throw new AppInvalidPersonDataException(
                "PersonModel.createPerson(person): invalid person name"
            );
        
        if (person.getAge() == null || person.getAge() < 0)
            throw new AppInvalidPersonDataException(
                "PersonModel.createPerson(person): invalid person age"
            );

        if (person.getAddress() == null || person.getAddress().trim().isEmpty())
            throw new AppInvalidPersonDataException(
                "PersonModel.createPerson(person): invalid person address"
            );
        
        if (person.getWork() == null || person.getWork().trim().isEmpty())
            throw new AppInvalidPersonDataException(
                "PersonModel.createPerson(person): invalid person work"
            );

        Optional<Person> optCreatedPerson = personDAO.create(person);

        if (!optCreatedPerson.isPresent())
            throw new AppInvalidPersonDataException(
                "PersonModel.createPerson(person): empty result"
            );
        
        return optCreatedPerson;
    }

    public Optional<List<Person>> getAllPersons() throws AppException
    {
        if (personDAO == null)
            throw new AppNullDAOException(
                "PersonModel.getAllPersons(): person DAO is null"
            );
        
        return personDAO.get();
    }

    public Optional<Person> getPerson(final Person person) throws AppException
    {
        if (personDAO == null)
            throw new AppNullDAOException(
                "PersonModel.getPerson(person): person DAO is null"
            );
        
        if (person.getId() == null)
            throw new AppInvalidPersonDataException(
                "PersonModel.getPerson(person): invalid person id"
            );
        
        return personDAO.get(person);
    }

    public Optional<Person> updatePerson(final Person person) throws AppException
    {
        if (personDAO == null)
            throw new AppNullDAOException(
                "PersonModel.updatePerson(person): person DAO is null"
            );
        
        if (person.getId() == null)
            throw new AppInvalidPersonDataException(
                "PersonModel.updatePerson(person): invalid person id"
            );
        
        if (person.getName() == null || person.getName().trim().isEmpty())
            throw new AppInvalidPersonDataException(
                "PersonModel.updatePerson(person): invalid person name"
            );
        
        if (person.getAge() == null || person.getAge() < 0)
            throw new AppInvalidPersonDataException(
                "PersonModel.updatePerson(person): invalid person age"
            );

        if (person.getAddress() == null || person.getAddress().trim().isEmpty())
            throw new AppInvalidPersonDataException(
                "PersonModel.updatePerson(person): invalid person address"
            );
        
        if (person.getWork() == null || person.getWork().trim().isEmpty())
            throw new AppInvalidPersonDataException(
                "PersonModel.updatePerson(person): invalid person work"
            );
        
        return personDAO.update(person);
    }

    public void deletePerson(final Person person) throws AppException
    {
        if (personDAO == null)
            throw new AppNullDAOException(
                "PersonModel.updatePerson(person): person DAO is null"
            );
        
        if (person.getId() == null)
            throw new AppInvalidPersonDataException(
                "PersonModel.updatePerson(person): invalid person id"
            );
        
        personDAO.delete(person);
    }
}
