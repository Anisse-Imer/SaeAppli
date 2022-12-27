package dao.cours;

import ConnectionJDBC.ConnectionJDBC;
import models.cours.Cours;
import models.cours.Module;
import models.time.TrancheHoraire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleDaoImpl {
    public Module getModuleById(int IdModule){
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
        Module m = new ModuleDaoImpl().getModuleById(2);
        System.out.println(m);
    }
}
