package models.users;

public class Utilisateur {
    public String id;
    public String fonction;

    public boolean RoleValide(){
            return (fonction == "ENS") || (fonction == "ELV") || (fonction == "ADMIN");
    }

    public Utilisateur() {
        this.id = null;
        this.fonction = null;
    }
    public Utilisateur(String id, String role) {
        this.id = id;
        this.fonction = role;
    }
}
