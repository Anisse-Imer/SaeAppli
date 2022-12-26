package dao;

import models.time.TrancheHoraire;

import java.sql.Date;

public interface TrancheHoraireDao {
    public TrancheHoraire GetTrancheHoraireById(int id);
    public int LastSemaine();
    public Date LastDateSemaines();
    public Date DateInterval(Date D1, int interval);
    public void AddSemaine(int SemaineId);
    public String GetDateByTrancheHoraire(TrancheHoraire T1);
    public void saveTrancheHoraire(TrancheHoraire T1);
    public int getIdTrancheHoraire(TrancheHoraire T1);
    public TrancheHoraire GetTrancheHoraireExist(TrancheHoraire T1);

}
