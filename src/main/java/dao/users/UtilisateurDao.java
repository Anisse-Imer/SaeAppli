package dao.users;

import ConnectionJDBC.ConnectionJDBC;
import dao.DAO;
import dao.DAOInt;
import dao.DAOString;
import models.usersFactory.User;
import models.usersFactory.Utilisateur;
import models.usersFactory.UtilisateurEleve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UtilisateurDao extends DAO<User>, DAOString<User>{
    public List<UtilisateurEleve> getElevesByGroup(String groupe);
    public List<String> getAllGroupes();
    public List<String> getGroupesbyEleve(String idEleve);
    
    //Les deux gets sachant que getUtilisateurConnectionById doit faire passer obligatoirement un compte admin en parametre.
    public User getUtilisateurConnection(String id, String mdp);
    public User getUtilisateurConnectionById(User AdminUser, String login);
    public String getNomUser(String IdUser);
    public User get(String Id);
    public List<User> getAll();

    public void saveInfo(User user, String MotDePasse, String Nom, String Prenom, String Telephone, String Mail);
}
