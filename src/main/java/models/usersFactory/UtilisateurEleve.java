package models.usersFactory;

import java.util.List;

public class UtilisateurEleve implements User{

    public String id;
    public String fonction;
    public List<String> groupes;

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
