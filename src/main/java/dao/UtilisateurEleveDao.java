package dao;

import models.users.Utilisateur;
import models.users.UtilisateurEleve;

import java.util.List;

public interface UtilisateurEleveDao {
    public List<UtilisateurEleve> GetElevesByGroup(String groupe);
    public List<String> GetGroupesbyEleve(String idEleve);
    public Utilisateur GetUtilisateurConnection(String id, String mdp);
}
