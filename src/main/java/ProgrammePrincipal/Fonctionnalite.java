package ProgrammePrincipal;

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
