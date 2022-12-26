package dao;

import models.users.Utilisateur;
import models.users.UtilisateurEleve;
import ConnectionJDBC.ConnectionJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UtilisateurEleveDaoImpl implements UtilisateurEleveDao{
    public UtilisateurEleveDaoImpl(){}

    public List<String> getGroupesbyEleve(String idEleve){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement StatementGroupes = cnx.prepareStatement
                    ("select groupe_id from eleves where utilisateur_id like ?");
            StatementGroupes.setString(1, "%" + idEleve + "%");
            ResultSet DonneesGroupes = StatementGroupes.executeQuery();
            List<String> Groupes = new LinkedList<String>();
            while (DonneesGroupes.next()){
                Groupes.add(DonneesGroupes.getString("groupe_id"));
            }
            DonneesGroupes.close();
            cnx.close();
            return Groupes;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    //Fonction retournant la liste des élèves d'un groupe.
    public List<UtilisateurEleve> getElevesByGroup(String groupe){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
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
                List<String> Groupes = getGroupesbyEleve(DonneesEleves.getString("utilisateur_id"));
                ListeElevesGroupe.add(new UtilisateurEleve(DonneesEleves.getString("utilisateur_id")
                                                        , DonneesEleves.getString("fonction_id")
                                                        , Groupes));
            }
            DonneesEleves.close();
            cnx.close();
            return ListeElevesGroupe;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    //Fonction permettant de créer un utilisateur à partir d'un login et un mot de passe.
    //Renvoie null si aucun compte ne correspond.
    public Utilisateur getUtilisateurConnection(String login, String mdp) {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select u.utilisateur_id, u.fonction_id"
                            + " from utilisateurs u"
                            + " where u.utilisateur_id like ?"
                            + " and u.password like ?");
            statement.setString(1, "%" + login + "%");
            statement.setString(2, "%" + mdp + "%");
            ResultSet DonneesEleves = statement.executeQuery();
            while (DonneesEleves.next()){
                //Renvoie une reference UtilisateurEleve si c'est un élève.
                if(DonneesEleves.getString("fonction_id").equals("ELV")){
                    return new UtilisateurEleve(DonneesEleves.getString("utilisateur_id")
                                                , "ELV"
                                                , getGroupesbyEleve(DonneesEleves.getString("utilisateur_id")));
                }
                //Renvoie une reference Utilisateur si c'est un simple utilisateur.
                return new Utilisateur(DonneesEleves.getString("utilisateur_id"),
                                        DonneesEleves.getString("fonction_id"));
            }
            DonneesEleves.close();
            cnx.close();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }
}
