package dao.users;
import dao.time.TrancheHoraireDaoImpl;
import models.usersFactory.*;
import ConnectionJDBC.ConnectionJDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UtilisateurDaoImpl implements UtilisateurDao {

    //Méthode nous permettant de nous servir du DAO dans un contexte static.
    public UtilisateurDaoImpl(){}

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

    //Méthode renvoyant un User(Elève, enseignant ou admin) selon son id.
    //Renvoie null si aucun User ne correspond.
    public User get(String Id){
        try {
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select u.utilisateur_id, u.fonction_id"
                            + " from utilisateurs u"
                            + " where u.utilisateur_id = ?");
            statement.setString(1, Id);
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

    //Renvoie tous les utilisateurs (Elève, enseignant ou admin) de la base.
    //Renvoie null si aucun utilisateur n'est enregistré.
    public List<User> getAll() {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select u.utilisateur_id, u.fonction_id"
                            + " from utilisateurs u");
            ResultSet DonneesEleves = statement.executeQuery();
            List<User> AllUser = new ArrayList<>();
            while (DonneesEleves.next()){
                AllUser.add(get(DonneesEleves.getString("utilisateur_id")));
            }
            DonneesEleves.close();
            return AllUser;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    //Méthode permettant de sauvegarder les informations non essentielles à l'application dans la base.
    @Override
    public void saveInfo(User user, String MotDePasse, String Nom, String Prenom, String Telephone, String Mail) {
        save(user);
        try{
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("UPDATE utilisateurs SET utilisateur_nom = ?" +
                                " , utilisateur_prenom = ?" +
                                " , utilisateurs.password = ?" +
                                " , mail = ?" +
                                " , telephone = ?" +
                                " where utilisateur_id = ?");
                statement.setString(1, Nom);
                statement.setString(2, Prenom);
                statement.setString(3, MotDePasse);
                statement.setString(4, Mail);
                statement.setString(5, Telephone);
                statement.setString(6, user.getId());
                statement.execute();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    //Méthode permettant de créer ou de mettre à jour un User.
    @Override
    public void save(User user) {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            if(get(user.getId()) != null) {
                update(user);
            }
            else {
                PreparedStatement statement = cnx.prepareStatement
                        ("INSERT INTO utilisateurs(" +
                                "   utilisateur_id" +
                                "  , utilisateur_nom" +
                                "  , utilisateur_prenom" +
                                "  , password" +
                                "  , mail" +
                                "  , telephone" +
                                "  , fonction_id" +
                                ")" +
                                " VALUES(" +
                                "   ?" +
                                "  , ?" +
                                "  , ?" +
                                "  , ?" +
                                "  , ?" +
                                "  , ?" +
                                "  , ?" +
                                ")");
                statement.setString(1, user.getId());
                statement.setString(2, "");
                statement.setString(3,"" );
                statement.setString(4, "");
                statement.setString(5, "");
                statement.setString(6, "");
                statement.setString(7, user.getFonction());
                statement.execute();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    //Méthode permattant de modifier la fonction d'un User dans la base.
    //Attention on ne retransforme pas user dans la méthode.
    //Il faut extraire à nouveau le User avec un get.
    @Override
    public void update(User user) {
        try {
            if (get(user.getId()) != null) {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("update utilisateurs set fonction_id = ? where utilisateur_id = ?");
                statement.setString(1,user.getFonction());
                statement.setString(2,user.getId());
                statement.execute();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    //Supprime un User si il existe, renvoie 0 si echec et 1 si réussite.
    @Override
    public int delete(User user) {
        try {
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("delete from utilisateurs where utilisateur_id = ?");
            statement.setString(1, user.getId());
            return statement.executeUpdate();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return 0;
    }

    //Méthode relative à la table groupes, permettant de récupérer tous les groupes.
    public List<String> getAllGroupes(){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement StatementGroupes = cnx.prepareStatement
                    ("select groupe_id from groupes");
            ResultSet DonneesGroupes = StatementGroupes.executeQuery();
            List<String> Groupes = new LinkedList<>();
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

    //Méthode renvoyant les groupes auxquels appartient un élève.
    //Null si l'élève n'existe pas ou s'il n'appartient à aucun groupe.
    public List<String> getGroupesbyEleve(String idEleve){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement StatementGroupes = cnx.prepareStatement
                    ("select groupe_id from eleves where utilisateur_id like ?");
            StatementGroupes.setString(1, "%" + idEleve + "%");
            ResultSet DonneesGroupes = StatementGroupes.executeQuery();
            List<String> Groupes = new LinkedList<>();
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
            List<UtilisateurEleve> ListeElevesGroupe = new LinkedList<>();
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

    //Méthode renvoyant le nom et le prénom d'un utilisateur selon son Id.
    //On ne l'utilise que dans le contexte où l'utilisateur existe dans la base.
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
}
