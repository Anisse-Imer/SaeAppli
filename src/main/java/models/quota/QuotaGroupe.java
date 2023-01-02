package models.quota;

import models.cours.Module;

public class QuotaGroupe {
    String IdGroupe;
    Module ModuleQ;
    String TypeCours;
    int IdSemaine;
    int Quantite;


    public QuotaGroupe(String idGroupe, Module moduleQ, String typeCours, int idSemaine, int quantite) {
        IdGroupe = idGroupe;
        ModuleQ = moduleQ;
        TypeCours = typeCours;
        IdSemaine = idSemaine;
        Quantite = quantite;
    }

    public String getIdGroupe() {
        return IdGroupe;
    }

    public Module getModuleQ() {
        return ModuleQ;
    }

    public String getTypeCours() {
        return TypeCours;
    }

    public int getIdSemaine() {
        return IdSemaine;
    }

    public int getQuantite() {
        return Quantite;
    }
}
