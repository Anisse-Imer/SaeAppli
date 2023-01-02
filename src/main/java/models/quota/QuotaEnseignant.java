package models.quota;

import models.cours.Module;

//Permet de stocker les heures qui devront être réalisées par l'enseignant.
public class QuotaEnseignant {
    String IdEnseignant;        //Id de l'enseignant assigné.
    String TypeCours;           //Type de cours à réaliser.
    Module ModuleQ;             //Module a enseigné.
    int Quantite;               //Quantité d'heures que doit réaliser l'enseignant.
    int QuantiteHeurePlanifie;  //Les heures déjà planifiées dans l'emploi du temps.
    int QuantiteMinutesPlafnifie;   //Les minutes déjà planifiées dans l'emploi du temps.

    public QuotaEnseignant(String idEnseignant, String typeCours, Module moduleQ, int quantite, int quantiteHeurePlanifie, int quantiteMinutesPlafnifie) {
        IdEnseignant = idEnseignant;
        TypeCours = typeCours;
        ModuleQ = moduleQ;
        Quantite = quantite;
        QuantiteHeurePlanifie = quantiteHeurePlanifie;
        QuantiteMinutesPlafnifie = quantiteMinutesPlafnifie;
    }

    public String getIdEnseignant() {
        return IdEnseignant;
    }

    public String getTypeCours() {
        return TypeCours;
    }

    public Module getModuleQ() {
        return ModuleQ;
    }

    public int getQuantite() {
        return Quantite;
    }

    public int getQuantiteHeurePlanifie() {
        return QuantiteHeurePlanifie;
    }

    public int getQuantiteMinutesPlafnifie() {
        return QuantiteMinutesPlafnifie;
    }

    @Override
    public String toString() {
        return "QuotaEnseignant{" +
                "IdEnseignant='" + IdEnseignant + '\'' +
                ", TypeCours='" + TypeCours + '\'' +
                ", ModuleQ=" + ModuleQ +
                ", Quantite=" + Quantite +
                ", QuantiteHeurePlanifie=" + QuantiteHeurePlanifie +
                ", QuantiteMinutesPlafnifie=" + QuantiteMinutesPlafnifie +
                '}';
    }
}
