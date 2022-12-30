package models.cours;

import models.time.TrancheHoraire;

import java.util.List;

public class Cours {
    public int Id;
    public TrancheHoraire Temporalite;
    public Lieu Emplacement;
    public String GroupeId;
    public String EnseignantId;
    public String TypeCours;
    public Module Module;

    public Cours(int id, TrancheHoraire temporalite, Lieu emplacement, String groupeId, String enseignantId, String typeCours, Module module) {
        Id = id;
        Temporalite = temporalite;
        Emplacement = emplacement;
        GroupeId = groupeId;
        EnseignantId = enseignantId;
        TypeCours = typeCours;
        Module = module;
    }

    public String toString(List<Cours> CoursAffiches){
        String affichage = new String();
        for (Cours c :
             CoursAffiches) {
            if(c != null){
                affichage.concat(c.toString());
            }
        }
        return affichage;
    }
    
    @Override
    public String toString() {
        return "Cours{" +
                "Id=" + Id +
                ", Temporalite=" + Temporalite +
                ", Emplacement=" + Emplacement +
                ", GroupeId='" + GroupeId + '\'' +
                ", EnseignantId='" + EnseignantId + '\'' +
                ", TypeCours='" + TypeCours + '\'' +
                ", Module=" + Module +
                '}';
    }

    public int getId() {
        return Id;
    }

    public TrancheHoraire getTemporalite() {
        return Temporalite;
    }

    public Lieu getEmplacement() {
        return Emplacement;
    }

    public String getGroupeId() {
        return GroupeId;
    }

    public String getEnseignantId() {
        return EnseignantId;
    }

    public String getTypeCours() {
        return TypeCours;
    }

    public models.cours.Module getModule() {
        return Module;
    }
}
