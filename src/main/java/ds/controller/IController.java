package ds.controller;

import ds.appexceptions.AppException;

public interface IController
{
    public void prepare() throws AppException;
    public void run() throws AppException;
}
