package models.users;

import java.security.InvalidParameterException;

public class Utilisateur {
    public String id;
    public String fonction;

    public boolean RoleValide(String fonction){
            return ((fonction.equals("ENS")) || (fonction.equals("ELV")) || (fonction.equals("ADMIN")));
    }

    public Utilisateur() {
        this.id = null;
        this.fonction = null;
    }
    public Utilisateur(String id, String fonction) throws InvalidParameterException{
        if(!RoleValide(fonction))
            throw new InvalidParameterException("fonction invalide");
        this.id = id;
        this.fonction = fonction;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id='" + id + '\'' +
                ", fonction='" + fonction + '\'' +
                '}';
    }
}
