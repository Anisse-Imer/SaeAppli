package models.usersFactory;

import models.time.TrancheHoraire;
import java.util.List;

//Class permettant de stocker les utilisateurs enseignants et leurs indisponibilités.
//Table utilisateurs et indisponibilités_tuteurs.
public class UtilisateurEnseignant implements User{

    public String id;
    public String fonction;     //Fonction (=ENS obligatoirement)
    public List<TrancheHoraire> Indisponibilites;   //Liste des indisponibilités de l'enseignant.

    public UtilisateurEnseignant(String id, String fonction, List<TrancheHoraire> indisponibilites) {
        this.id = id;
        this.fonction = fonction;
        this.Indisponibilites = indisponibilites;
    }

    public String getId() {
        return id;
    }

    public String getFonction() {
        return fonction;
    }

    @Override
    public String toString() {
        return "UtilisateurEnseignant{" +
                "id='" + id + '\'' +
                ", fonction='" + fonction + '\'' +
                ", Indisponibilites=" + Indisponibilites +
                '}';
    }
}
