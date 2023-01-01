package models.quota;

import models.cours.Module;

public class QuotaEnseignant {
    String IdEnseignant;
    String TypeCours;
    Module ModuleQ;
    int Quantite;
    int QuantiteHeurePlanifie;
    int QuantiteMinutesPlafnifie;

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
