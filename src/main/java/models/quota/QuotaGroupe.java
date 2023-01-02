package models.quota;

import models.cours.Module;

//Class représentant les heures de cours à attribuer à un groupe.
public class QuotaGroupe {
    String IdGroupe;        //Groupe à qui on doit enseigner.
    Module ModuleQ;         //Le module que l'on doit enseigner.
    String TypeCours;       //Type de cours à enseigner.
    int IdSemaine;          //La semaine où cette quantité d'heures devra être réalisée.
    int Quantite;           //La quantité d'heures de cours à réaliser.


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
