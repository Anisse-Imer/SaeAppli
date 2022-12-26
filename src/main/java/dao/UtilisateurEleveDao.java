package dao;

import models.users.Utilisateur;
import models.users.UtilisateurEleve;

import java.util.List;

public interface UtilisateurEleveDao {
    public List<UtilisateurEleve> getElevesByGroup(String groupe);
    public List<String> getGroupesbyEleve(String idEleve);
    public Utilisateur getUtilisateurConnection(String id, String mdp);
}
