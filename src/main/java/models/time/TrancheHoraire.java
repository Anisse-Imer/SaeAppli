package models.time;
import models.cours.Lieu;
import models.cours.Module;
import java.security.InvalidParameterException;
import java.sql.Time;

public class TrancheHoraire {
    int id;
    int IdSemaine;
    int IdJour;
    Time Debut;
    Time Fin;

    //Constructeur par défaut.
    public TrancheHoraire() {
        id = -1;
        IdSemaine = -1;
        IdJour = -1;
        this.Debut = new Time(0,0,0);
        this.Fin = new Time(0,0,0);
    }

    //Constructeur vérifiant si les données sont correctes.
    public TrancheHoraire(int id, int IdSemaine, int IdJour, Time debut, Time fin) throws InvalidParameterException{
        if(debut == null || fin == null)
            throw new InvalidParameterException("TrancheHoraire : debut/fin null");
        if(debut.compareTo(fin) == 0)
            throw new InvalidParameterException("TrancheHoraire : debut = fin");
        if(debut.compareTo(fin) == 1)
            SwitchTimes(debut, fin);
        this.id = id;
        this.IdSemaine = IdSemaine;
        this.IdJour = IdJour;
        this.Debut = debut;
        this.Fin = fin;
    }

    public TrancheHoraire(int tranche_horaire, int semaine_id, int jour_id, Time heure_debut, Time heure_fin, Lieu lieu_id, String groupe_id, String utilisateur_id, String type_cours_id, Module module_id_) {
    }

    //Permet d'échanger les références de deux Time.
    public void SwitchTimes(Time A, Time B){
        Time C = A ;
        A = B;
        B = C;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdSemaine() {
        return IdSemaine;
    }

    public int getIdJour() {
        return IdJour;
    }

    public Time getDebut() {
        return Debut;
    }

    public Time getFin() {
        return Fin;
    }

    //Si this chevauche TempTest alors renvoie false, si l'inverse true.
    public boolean IsNotIn(TrancheHoraire TempsTest){
        if(this.IdSemaine != TempsTest.IdSemaine || this.IdJour != TempsTest.IdJour)
            return true;
        if(((this.Debut.compareTo(TempsTest.Debut) <= 0) && (this.Fin.compareTo(TempsTest.Debut) <= 0)))
            return true;
        if(((this.Debut.compareTo(TempsTest.Fin)) >= 0 && (this.Fin.compareTo(TempsTest.Fin) >= 0)))
            return true;
        return false;
    }

    //Calcul la différence de temps entre debut et fin.
    public Time difference(){
        long Difference;
        if(Debut != null && Fin != null) {
            Difference = Math.abs(this.Fin.getTime() - this.Debut.getTime());
            return new Time(Difference - new Time(2, 0, 0).getTime());
        }
        return new Time(0,0,0);
    }

    @Override
    public String toString() {
        return "TrancheHoraire{" +
                "id=" + id +
                ", IdSemaine=" + IdSemaine +
                ", IdJour=" + IdJour +
                ", Debut=" + Debut +
                ", Fin=" + Fin +
                '}';
    }
}
