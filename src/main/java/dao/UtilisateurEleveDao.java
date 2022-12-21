package dao;

import models.users.UtilisateurEleve;
import ConnectionJDBC.ConnectionJDBC;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UtilisateurEleveDao {
    public static List<UtilisateurEleve> GetElevesbyGroup(String groupe){
        try{
            Connection cnx = new ConnectionJDBC("com.mysql.cj.jdbc.Driver"
                    , "jdbc:mysql://127.0.0.1:3306/IUT"
                    , "root"
                    , "an56im18").connection;
            PreparedStatement statement = cnx.prepareStatement
                        ("select e.utilisateur_id, u.fonction_id "
                                + "from eleves e" +
                                ", utilisateurs u"
                                + " where e.groupe_id = ?"
                                + " and e.utilisateur_id = u.utilisateur_id");
            statement.setString(1,groupe);
            ResultSet DonneesEleves = statement.executeQuery();

            List<UtilisateurEleve> ListeElevesGroupe = new LinkedList<>();
            while(DonneesEleves.next()){
                PreparedStatement StatementGroupes = cnx.prepareStatement
                        ("select groupe_id from eleves where utilisateur_id = ?");
                StatementGroupes.setString(1,DonneesEleves.getString("utilisateur_id"));
                ResultSet DonneesGroupes = StatementGroupes.executeQuery();

                List<String> Groupes = new LinkedList<String>();
                while (DonneesGroupes.next()){
                    Groupes.add(DonneesGroupes.getString("groupe_id"));
                }
                ListeElevesGroupe.add(new UtilisateurEleve(DonneesEleves.getString("utilisateur_id")
                                                        , DonneesEleves.getString("fonction_id")
                                                        , Groupes));
            }
            return ListeElevesGroupe;
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
