package dao;

import models.time.TrancheHoraire;

public interface TrancheHoraireDao {
    public TrancheHoraire GetTrancheHoraireById(int id);
    public String GetDateByTrancheHoraire(TrancheHoraire T1);

}
