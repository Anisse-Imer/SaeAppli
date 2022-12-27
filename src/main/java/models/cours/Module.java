package models.cours;

public class Module {
    public int Id;
    public String Nom;

    public Module(int id, String nom) {
        Id = id;
        Nom = nom;
    }

    @Override
    public String toString() {
        return "Module{" +
                "Id=" + Id +
                ", Nom='" + Nom + '\'' +
                '}';
    }
}
