package models.users;

import java.security.InvalidParameterException;
import java.util.List;

public class UtilisateurEleve extends Utilisateur{
    public List<String> groupes;

    public UtilisateurEleve(String id, String fonction, List<String> groupes){
        super(id, fonction);
        this.groupes = groupes;
    }

    public UtilisateurEleve(Utilisateur UtilisateurA, List<String> groupes) {
        super(UtilisateurA.id, UtilisateurA.fonction);
        this.groupes = groupes;
    }

    @Override
    public String toString() {
        return "UtilisateurEleve{" +
                "groupes=" + groupes +
                ", id='" + id + '\'' +
                ", fonction='" + fonction + '\'' +
                '}';
    }
}
