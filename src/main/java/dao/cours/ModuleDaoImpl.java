package dao.cours;

import ConnectionJDBC.ConnectionJDBC;
import models.cours.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleDaoImpl implements ModuleDao{

    //Permet de récupérer un Module selon son Id s'il existe.
    //Sinon renvoie null.
    public Module get(int IdModule){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select *" +
                            " from modules" +
                            " where module_id = ?");
            statement.setInt(1, IdModule);
            ResultSet ResultModule = statement.executeQuery();
            if(ResultModule.next()){
                Module ModuleCherche = new Module(ResultModule.getInt("module_id")
                                                , ResultModule.getString("module_nom"));
                ResultModule.close();
                return ModuleCherche;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    //Renvoie tous les modules de la base.
    //Null si aucun module n'est enregistré.
    @Override
    public List<Module> getAll() {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select module_id" +
                            " from modules");
            ResultSet ResultModule = statement.executeQuery();
            List<Module> Modules = new ArrayList<>();
            if(ResultModule.next()){
                do {
                    Modules.add(get(ResultModule.getInt("module_id")));
                }while (ResultModule.next());
                ResultModule.close();
                return Modules;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    //Permet d'enregistrer un nouveau module dans la base ou le mettre à jour s'il existe déjà.
    @Override
    public void save(Module module) {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            if(module.getId() != 0) {
                update(module);
            }
            else {
                PreparedStatement statement = cnx.prepareStatement
                        ("INSERT INTO modules(" +
                                "module_nom" +
                                ")" +
                                " VALUES(" +
                                " ?)");
                statement.setString(1, module.getNom());
                statement.execute();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    //Permet de mettre à jour un module dans la base selon son id (met à jour le nom).
    @Override
    public void update(Module module) {
        try {
            if (module.getId() != 0) {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("update modules set module_nom = ? where module_id = ?");
                statement.setString(1, module.getNom());
                statement.setInt(2, module.getId());
                statement.execute();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    //Supprime un module de la base selon son id.
    @Override
    public int delete(Module module) {
        try {
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("delete from modules where module_id = ?");
            statement.setInt(1, module.getId());
            return statement.executeUpdate();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return 0;
    }
}
