package dao.users;

import dao.time.TrancheHoraireDaoImpl;
import dao.users.UtilisateurDao;
import models.usersFactory.*;
import ConnectionJDBC.ConnectionJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UtilisateurDaoImpl implements UtilisateurDao {
    public UtilisateurDaoImpl(){}

    public List<String> getAllGroupes(){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement StatementGroupes = cnx.prepareStatement
                    ("select groupe_id from groupes");
            ResultSet DonneesGroupes = StatementGroupes.executeQuery();
            List<String> Groupes = new LinkedList<String>();
            while (DonneesGroupes.next()){
                Groupes.add(DonneesGroupes.getString("groupe_id"));
            }
            DonneesGroupes.close();
            return Groupes;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

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
            List<UtilisateurEleve> ListeElevesGroupe = new LinkedList<UtilisateurEleve>();
            while(DonneesEleves.next()){
                List<String> Groupes = getGroupesbyEleve(DonneesEleves.getString("utilisateur_id"));
                ListeElevesGroupe.add(new UtilisateurEleve(DonneesEleves.getString("utilisateur_id")
                                                        , DonneesEleves.getString("fonction_id")
                                                        , Groupes));
            }
            DonneesEleves.close();
            return ListeElevesGroupe;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    //Fonction renvoyant un User (Elève, enseignant, admin) selon les logins.
    //Renvoie null si la fonction n'est pas "ENS", "ELV", "ADMIN" ou si le compte n'existe pas dans la base.
    public User getUtilisateurConnection(String login, String mdp) {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select u.utilisateur_id, u.fonction_id"
                            + " from utilisateurs u"
                            + " where u.utilisateur_id = ?"
                            + " and u.password = ?");
            statement.setString(1, login);
            statement.setString(2, mdp);
            ResultSet DonneesEleves = statement.executeQuery();
            while (DonneesEleves.next()){
                //Renvoie une reference UtilisateurEleve si c'est un élève.
                if(DonneesEleves.getString("fonction_id").equals("ELV")){
                    return new UserFactory().create(DonneesEleves.getString("utilisateur_id")
                                                    , "ELV"
                                                    , getGroupesbyEleve(DonneesEleves.getString("utilisateur_id")));
                }
                else if(DonneesEleves.getString("fonction_id").equals("ENS")) {
                    return new UserFactory().create(DonneesEleves.getString("utilisateur_id")
                                                    , "ENS"
                                                    , new TrancheHoraireDaoImpl().getIndisponibiliteEnseignant(
                                                        DonneesEleves.getString("utilisateur_id")));
                }
                else if(DonneesEleves.getString("fonction_id").equals("ADMIN")){
                    return new UserFactory().create(DonneesEleves.getString("utilisateur_id")
                                                    , DonneesEleves.getString("fonction_id"));
                }
            }
            DonneesEleves.close();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    //Permet à un compte administrateur d'obtenir les informations d'un autre utilisateur en ne possédant que mon login.
    public User getUtilisateurConnectionById(User AdminUser, String login) {
        if (AdminUser.getFonction().equals("ADMIN")) {
            try {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("select u.utilisateur_id, u.fonction_id"
                                + " from utilisateurs u"
                                + " where u.utilisateur_id = ?");
                statement.setString(1, login);
                ResultSet DonneesEleves = statement.executeQuery();
                while (DonneesEleves.next()) {
                    //Renvoie une reference UtilisateurEleve si c'est un élève.
                    if (DonneesEleves.getString("fonction_id").equals("ELV")) {
                        return new UserFactory().create(DonneesEleves.getString("utilisateur_id")
                                , "ELV"
                                , getGroupesbyEleve(DonneesEleves.getString("utilisateur_id")));
                    } else if (DonneesEleves.getString("fonction_id").equals("ENS")) {
                        return new UserFactory().create(DonneesEleves.getString("utilisateur_id")
                                , "ENS"
                                , new TrancheHoraireDaoImpl().getIndisponibiliteEnseignant(
                                        DonneesEleves.getString("utilisateur_id")));
                    } else if (DonneesEleves.getString("fonction_id").equals("ADMIN")) {
                        return new UserFactory().create(DonneesEleves.getString("utilisateur_id")
                                , DonneesEleves.getString("fonction_id"));
                    }
                }
                DonneesEleves.close();
            } catch (SQLException SQLE) {
                SQLE.printStackTrace();
            }
            return null;
        }
        return null;
    }

    public String getNomUser(String IdUser) {
        try {
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select u.utilisateur_nom, u.utilisateur_prenom"
                            + " from utilisateurs u"
                            + " where u.utilisateur_id = ?");
            statement.setString(1, IdUser);
            ResultSet ResultNoms = statement.executeQuery();
            if (ResultNoms.next()) {
                String NomPrenom = ResultNoms.getString("utilisateur_nom")
                        + " " + ResultNoms.getString("utilisateur_prenom");
                ResultNoms.close();
                return NomPrenom;
            }
        }
        catch(SQLException SQLE){
                SQLE.printStackTrace();
            }
        return "";
    }

    public static void main(String[] args) {
        List<String> tousgroupes = new UtilisateurDaoImpl().getAllGroupes();
        System.out.println(tousgroupes);
    }
}
