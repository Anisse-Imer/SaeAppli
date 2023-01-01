package models.cours;

public class Module {
    public int Id;
    public String Nom;

    public Module(int id, String nom) {
        Id = id;
        Nom = nom;
    }

    public String getNom() {
        return Nom;
    }

    public int getId() {
        return Id;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    @Override
    public String toString() {
        return Id + " : " + Nom;
    }
}
