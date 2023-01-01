package models.quota;

import models.cours.Module;

public class QuotaGroupe {
    String IdGroupe;
    Module ModuleQ;
    String TypeCours;
    int IdSemaine;

    public QuotaGroupe(String idGroupe, Module moduleQ, String typeCours, int idSemaine) {
        IdGroupe = idGroupe;
        ModuleQ = moduleQ;
        TypeCours = typeCours;
        IdSemaine = idSemaine;
    }
}
