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

    @Override
    public String toString() {
        return Id + " : " + Nom;
    }
}
