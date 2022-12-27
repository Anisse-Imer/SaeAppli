package dao.cours;

import models.cours.Cours;

import java.util.List;

public interface CoursDao {
    public List<Cours> getCoursEleve(String IdEleve);
}
