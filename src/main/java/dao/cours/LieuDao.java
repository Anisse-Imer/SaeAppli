package dao.cours;

import dao.DAO;
import dao.DAOString;
import models.cours.Lieu;

import java.util.List;

public interface LieuDao extends DAO<Lieu>, DAOString<Lieu> {
    public String toString(Lieu LieuS);
    public String toString(List<Lieu> ListeLieuS);
}
