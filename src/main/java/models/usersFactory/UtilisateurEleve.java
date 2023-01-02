package models.usersFactory;

import java.util.List;

public class UtilisateurEleve implements User{

    public String id;               //Id de l'utilisateur.
    public String fonction;         //Fonction de l'utilisateur (=ELV obligatoirement)
    public List<String> groupes;    //Liste des Id des groupes auxquels l'élève.

    public UtilisateurEleve(String id, String fonction, List<String> groupes) {
        this.id = id;
        this.fonction = fonction;
        this.groupes = groupes;
    }

    public String getId() {
        return id;
    }

    public String getFonction() {
        return fonction;
    }

    @Override
    public String toString() {
        return "UtilisateurEleve{" +
                "id='" + id + '\'' +
                ", fonction='" + fonction + '\'' +
                ", groupes=" + groupes +
                '}';
    }
}
