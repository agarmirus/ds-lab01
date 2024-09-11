package ds.dao;

import java.util.List;

import ds.appexceptions.AppException;

public interface IDAO<T>
{
    public void setConnection(String url, String usr, String pswd) throws AppException;

    public void create(final T entity) throws AppException;

    public List<T> get() throws AppException;
    public T get(final T entity) throws AppException;

    public void update(final T entity) throws AppException;

    public void delete(final T entity) throws AppException;
}
