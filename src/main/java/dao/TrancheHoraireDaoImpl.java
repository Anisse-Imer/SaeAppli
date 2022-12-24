package dao;

import ConnectionJDBC.ConnectionJDBC;
import models.time.TrancheHoraire;
import models.users.Utilisateur;
import models.users.UtilisateurEleve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrancheHoraireDaoImpl implements TrancheHoraireDao{

    @Override
    public TrancheHoraire GetTrancheHoraireById(int id) {
        try{
            Connection cnx = new ConnectionJDBC("com.mysql.cj.jdbc.Driver"
                    , "jdbc:mysql://127.0.0.1:3306/IUT"
                    , "root"
                    , "an56im18").connection;
            PreparedStatement statement = cnx.prepareStatement
                    ("select u.utilisateur_id, u.fonction_id"
                            + " from utilisateurs u"
                            + " where u.utilisateur_id like ?"
                            + " and u.password like ?");
            statement.setString(1, "%" + login + "%");
            statement.setString(2, "%" + mdp + "%");
            ResultSet DonneesEleves = statement.executeQuery();
            DonneesEleves.
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        catch (ClassNotFoundException CNFE){
            CNFE.printStackTrace();
        }
        return null;
    }
}
