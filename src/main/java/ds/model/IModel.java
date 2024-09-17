package ds.model;

import java.util.List;
import java.util.Optional;

import ds.appexceptions.AppException;
import ds.domain.Person;

public interface IModel
{
    public Optional<Person> createPerson(final Person person) throws AppException;
    public Optional<List<Person>> getAllPersons() throws AppException;
    public Optional<Person> getPerson(final Person person) throws AppException;
    public Optional<Person> updatePerson(final Person person) throws AppException;
    public void deletePerson(final Person person) throws AppException;
}
