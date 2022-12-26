package dao;

import models.usersFactory.Utilisateur;
import models.usersFactory.UtilisateurEleve;

import java.util.List;

public interface UtilisateurDao {
    public List<UtilisateurEleve> getElevesByGroup(String groupe);
    public List<String> getGroupesbyEleve(String idEleve);
    public Utilisateur getUtilisateurConnection(String id, String mdp);
}
