package dao;

import models.usersFactory.User;

public interface DAOString<T> {

    //Renvoie l'objet T qui a comme Id un String.
    public T get(String Id);
}
