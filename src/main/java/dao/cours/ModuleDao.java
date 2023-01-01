package dao.cours;

import dao.DAO;
import models.cours.Module;

public interface ModuleDao extends DAO<Module> {
    public Module get(int IdModule);
}
