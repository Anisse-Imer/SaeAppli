package models.cours;

import models.time.TrancheHoraire;

import java.util.List;

public class Lieu {
    public String Id;
    public int Capacite;
    public List<TrancheHoraire> Indisponibilites;

    public Lieu(String id, int capacite, List<TrancheHoraire> indisponibilites) {
        Id = id;
        Capacite = capacite;
        Indisponibilites = indisponibilites;
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
