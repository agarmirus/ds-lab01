package ds.dao;

import java.util.List;
import java.util.Optional;

import ds.appexceptions.AppException;

public interface IDAO<T>
{
    public void setConnection(String url, String usr, String pswd) throws AppException;

    public Optional<T> create(final T entity) throws AppException;

    public Optional<List<T>> get() throws AppException;
    public Optional<T> get(final T entity) throws AppException;

    public Optional<T> update(final T entity) throws AppException;

    public void delete(final T entity) throws AppException;
}
