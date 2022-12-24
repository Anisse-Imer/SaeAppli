package models.time;

import java.security.InvalidParameterException;
import java.sql.Time;

public class TrancheHoraire {
    int id;
    int IdSemaine;
    int IdJour;
    Time Debut;
    Time Fin;

    public TrancheHoraire() {
        id = -1;
        IdSemaine = -1;
        IdJour = -1;
        this.Debut = new Time(0,0,0);
        this.Fin = new Time(0,0,0);
    }

    public void SwitchTimes(Time A, Time B){
        Time C = A ;
        A = B;
        B = C;
    }

    public TrancheHoraire(int id, int idSemaine, int idJour, Time debut, Time fin) throws InvalidParameterException{
        if(debut == null || fin == null)
            throw new InvalidParameterException("TrancheHoraire : debut/fin null");
        if(debut.compareTo(fin) == 0)
            throw new InvalidParameterException("TrancheHoraire : debut = fin");
        if(debut.compareTo(fin) == 1)
            SwitchTimes(debut, fin);
        this.id = id;
        IdSemaine = IdSemaine;
        IdJour = idJour;
        this.Debut = debut;
        this.Fin = fin;
    }

    public boolean IsNotIn(TrancheHoraire TempsTest){
        if(this.IdSemaine != TempsTest.IdSemaine || this.IdJour != TempsTest.IdJour)
            return true;
        if(((this.Debut.compareTo(TempsTest.Debut) == -1 || this.Debut.compareTo(TempsTest.Debut) == 0)
                && (this.Fin.compareTo(TempsTest.Debut) == -1 || this.Fin.compareTo(TempsTest.Debut) == 0)))
            return true;
        if(((this.Debut.compareTo(TempsTest.Fin) == 1 || this.Debut.compareTo(TempsTest.Fin) == 0)
                && (this.Fin.compareTo(TempsTest.Fin) == 1 || this.Fin.compareTo(TempsTest.Fin) == 0)))
            return true;
        return false;
    }

    public static void main(String[] args) {
        TrancheHoraire t1 = new TrancheHoraire(0, 1, 1 ,
                new Time(0,0,0),
                new Time(5,30,0));
        TrancheHoraire t2 = new TrancheHoraire(1, 1, 1 ,
                new Time(5,0,0),
                new Time(10,0,0));
        boolean in = t1.IsNotIn(t2);
        System.out.println(in);
        System.out.println(new Time(10,0,0).compareTo(new Time(13,0,0)));
    }
}
