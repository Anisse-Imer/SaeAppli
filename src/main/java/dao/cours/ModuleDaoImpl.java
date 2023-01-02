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

    public static void main(String[] args) {
        Module m = new ModuleDaoImpl().get(2);
        System.out.println(new ModuleDaoImpl().delete(m));
    }

    @Override
    public List<Module> getAll() {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select module_id" +
                            " from modules");
            ResultSet ResultModule = statement.executeQuery();
            List<Module> Modules = new ArrayList<Module>();
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
