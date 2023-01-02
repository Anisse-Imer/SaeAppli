package dao.cours;

import dao.DAO;
import dao.DAOInt;
import models.cours.Cours;
import models.usersFactory.User;

import java.util.List;

public interface CoursDao extends DAO<Cours>, DAOInt<Cours> {
    List<Cours> getCoursByUser(User user, int IdSemaine);

    List<Cours> getCoursByGroup(String GroupeID, int IDSemaine);

    String toString(Cours cours);

    String toString(List<Cours> ListeCours);

}
