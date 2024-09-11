package ds.model;

import java.util.List;

import ds.appexceptions.AppException;
import ds.domain.Person;

public interface IModel
{
    public void createPerson(final Person person) throws AppException;
    public List<Person> getAllPersons() throws AppException;
    public Person getPerson(final Person person) throws AppException;
    public Person updatePerson(final Person person) throws AppException;
    public void deletePerson(final Person person) throws AppException;
}
