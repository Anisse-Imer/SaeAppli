package ProgrammePrincipal;

//Class permettant le lier un numéro de fonctionnalités à un message pour accéder à des méthodes.
//Remarque : une version améliorée de cette classe serait la possibilité de stocker ls références de méthodes
//pour pouvoir les appeler de manière plus dynamique.
public class Fonctionnalite {
    int NumeroF;
    String NomF;

    public Fonctionnalite(int numeroF, String chaineF) {
        NumeroF = numeroF;
        NomF = chaineF;
    }

    public int getNumeroF() {
        return NumeroF;
    }

    public String getChaineF() {
        return NomF;
    }

    @Override
    public String toString() {
        return NumeroF + " : " + NomF;
    }
}
