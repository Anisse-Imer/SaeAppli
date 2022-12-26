package models.usersFactory;

import models.time.TrancheHoraire;

import java.util.List;

public class UserFactory {

    public UserFactory(){}

    public User create(String Id, String Fonction, Object ...parameters){
        User user;
        switch (Fonction){
            case "ELV" : {
                List<String> Groupes = (List<String>)parameters[0];
                user = new UtilisateurEleve(Id, Fonction, Groupes);
            }; break;
            case "ENS" : {
                List<TrancheHoraire> Tranches = (List<TrancheHoraire>)parameters[0];
                user = new UtilisateurEnseignant(Id, Fonction, Tranches);
            };break;
            case "ADMIN" : {
                user = new Utilisateur(Id, Fonction);
            };break;
            default:user=null;
        }
        return user;
    }
}
