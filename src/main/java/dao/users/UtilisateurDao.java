package dao.users;
import dao.DAO;
import dao.DAOString;
import models.usersFactory.User;
import models.usersFactory.UtilisateurEleve;
import java.util.List;

//Classe permettant de gérer la table utilisateurs.
public interface UtilisateurDao extends DAO<User>, DAOString<User>{
    List<UtilisateurEleve> getElevesByGroup(String groupe);
    List<String> getAllGroupes();
    List<String> getGroupesbyEleve(String idEleve);
    
    //Les deux gets sachant que getUtilisateurConnectionById doit faire passer obligatoirement un compte admin en parametre.
    User getUtilisateurConnection(String id, String mdp);
    User getUtilisateurConnectionById(User AdminUser, String login);
    String getNomUser(String IdUser);
    User get(String Id);
    List<User> getAll();
    void saveInfo(User user, String MotDePasse, String Nom, String Prenom, String Telephone, String Mail);
}
