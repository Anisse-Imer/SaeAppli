package models.cours;

//Class représentant les modules enseignés.
public class Module {
    public int Id;                          //Id du module.
    public String Nom;                      //Nom du module.

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
