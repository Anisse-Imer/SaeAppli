package models.cours;

import models.time.TrancheHoraire;

import java.util.List;

//Class représentant les tables lieux et reservations_lieux.
public class Lieu {
    public String Id;
    public int Capacite;
    public List<TrancheHoraire> Indisponibilites;   //Liste des indisponibilités du lieu.

    public Lieu(String id, int capacite, List<TrancheHoraire> indisponibilites) {
        Id = id;
        Capacite = capacite;
        Indisponibilites = indisponibilites;
    }

    public String getId() {
        return Id;
    }

    public int getCapacite() {
        return Capacite;
    }

    public List<TrancheHoraire> getIndisponibilites() {
        return Indisponibilites;
    }

    @Override
    public String toString() {
        return "Lieu{" +
                "Id='" + Id + '\'' +
                ", Capacite=" + Capacite +
                ", Indisponibilites=" + Indisponibilites +
                '}';
    }
}
