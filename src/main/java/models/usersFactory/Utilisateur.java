package models.usersFactory;

public class Utilisateur implements User{
    public String id;
    public String fonction;

    public Utilisateur(String id, String fonction){
        this.id = id;
        this.fonction = fonction;
    }

    public String getFonction() {
        return fonction;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id='" + id + '\'' +
                ", fonction='" + fonction + '\'' +
                '}';
    }
}
