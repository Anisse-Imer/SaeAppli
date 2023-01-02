package dao;

import models.usersFactory.User;

public interface DAOInt<T> {
    //Renvoie l'objet T qui a comme Id un int.
    public T get(int Id);
}
