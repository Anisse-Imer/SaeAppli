package models.usersFactory;

import models.time.TrancheHoraire;

import java.util.List;

public class UtilisateurEnseignant implements User{

    public String id;
    public String fonction;
    public List<TrancheHoraire> Indisponibilites;

    public UtilisateurEnseignant(String id, String fonction, List<TrancheHoraire> indisponibilites) {
        this.id = id;
        this.fonction = fonction;
        Indisponibilites = indisponibilites;
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
