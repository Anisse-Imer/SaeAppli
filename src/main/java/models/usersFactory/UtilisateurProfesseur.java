package models.usersFactory;

import models.time.TrancheHoraire;

import java.security.InvalidParameterException;
import java.util.List;

public class UtilisateurProfesseur extends Utilisateur{
    public List<TrancheHoraire> Indisponibilites;

    public UtilisateurProfesseur(List<TrancheHoraire> indisponibilites) {
        Indisponibilites = indisponibilites;
    }

    public UtilisateurProfesseur(String id, String fonction, List<TrancheHoraire> indisponibilites) throws InvalidParameterException {
        super(id, fonction);
        Indisponibilites = indisponibilites;
    }

    @Override
    public String toString() {
        return "UtilisateurProfesseur{" +
                "Indisponibilites=" + Indisponibilites +
                ", id='" + id + '\'' +
                ", fonction='" + fonction + '\'' +
                '}';
    }
}
