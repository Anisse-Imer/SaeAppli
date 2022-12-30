package dao.time;

import models.time.TrancheHoraire;

import java.sql.Date;
import java.util.List;

public interface TrancheHoraireDao {
    public TrancheHoraire getTrancheHoraireById(int id);
    public int lastSemaine();
    public Date lastDateSemaines();
    public Date dateInterval(Date D1, int interval);
    public void addSemaine(int SemaineId);
    public String getDateByTrancheHoraire(TrancheHoraire T1);
    public void saveTrancheHoraire(TrancheHoraire T1);
    public int getIdTrancheHoraire(TrancheHoraire T1);
    public TrancheHoraire getTrancheHoraireExist(TrancheHoraire T1);
    public List<TrancheHoraire> getIndisponibiliteEnseignant(String IdEnseignant);

    public List<TrancheHoraire> getIndisponibiliteEnseignantSemaine(String IdEnseignant, int IdSemaine);

    public String toString(TrancheHoraire T1);
    public String toString(List<TrancheHoraire> LT1);
}
