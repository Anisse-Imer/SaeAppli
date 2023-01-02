package dao;

import models.usersFactory.User;

public interface DAOString<T> {
    public T get(String Id);
}
