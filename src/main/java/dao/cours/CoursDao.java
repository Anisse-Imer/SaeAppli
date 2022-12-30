package dao.cours;

import dao.DAO;
import models.cours.Cours;
import models.usersFactory.User;

import java.util.List;

public interface CoursDao{
    public List<Cours> getCoursByUser(User user, int IdSemaine);

    public String toString(Cours cours);

    public String toString(List<Cours> ListeCours);

    public List<Cours> getCoursByGroup(String GroupeID, int IDSemaine);
}
