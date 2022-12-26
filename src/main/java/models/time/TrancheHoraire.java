package models.time;

import dao.TrancheHoraireDaoImpl;

import java.security.InvalidParameterException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

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

    public static void main(String[] args) {
        TrancheHoraire t1 = new TrancheHoraire(0, 1, 1 ,
                new Time(0,0,0),
                new Time(5,30,0));
        TrancheHoraire t2 = new TrancheHoraire(1, 1, 1 ,
                new Time(5,0,0),
                new Time(10,0,0));

        System.out.println(t2.toString());
        TrancheHoraireDaoImpl thDAO = new TrancheHoraireDaoImpl();
        System.out.println(thDAO.getDateByTrancheHoraire(t2));

        System.out.println(thDAO.lastSemaine());

        Date d1 = thDAO.lastDateSemaines();
        Date d2 = thDAO.dateInterval(d1, 7);
        System.out.println(d1.toString());
        System.out.println(d2.toString());

        thDAO.addSemaine(4);

        TrancheHoraire t3 = new TrancheHoraire(0, 6, 3 ,
                new Time(0,0,0),
                new Time(5,30,0));

        System.out.println(t3.toString());
        t3 = thDAO.getTrancheHoraireExist(t3);
        System.out.println(t3.toString());
    }
}
