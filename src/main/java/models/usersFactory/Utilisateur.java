package models.usersFactory;

//Class permettant de stocker un utilisateur admin ou autre.
public class Utilisateur implements User{
    public String id;
    public String fonction;     //Fonction de l'utilisateur. (= ADMIN ou AUTRE)

    public Utilisateur(String id, String fonction){
        this.id = id;
        this.fonction = fonction;
    }

    public String getId() {
        return id;
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
