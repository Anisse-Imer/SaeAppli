package dao;

import models.usersFactory.User;

public interface DAOInt<T> {
    public T get(int Id);
}
