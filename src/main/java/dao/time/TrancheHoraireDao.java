package dao.time;

import dao.DAO;
import dao.DAOInt;
import models.time.TrancheHoraire;

import java.sql.Date;
import java.util.List;

//Class permettant de gérer les coordonnées temporelles de la base.
//Repose sur un principe, une semaine et un jour représente un jour.
// Une semaine a un jour de début ainsi qu'un jour de fin, on représente ensuite chacun de ces jours par un chiffre.
//(exemple : 2 -> mardi)
//Couplé à une semaine, un jour représente donc une date.
// On prend en référence la première semaine de la base et donc la toute première date de la base.
public interface TrancheHoraireDao extends DAO<TrancheHoraire>, DAOInt<TrancheHoraire> {
    public int lastSemaine();
    public Date lastDateSemaines();
    public Date dateInterval(Date D1, int interval);
    public void addSemaine(int SemaineId);
    public String getDateByTrancheHoraire(TrancheHoraire T1);
    public int getIdTrancheHoraire(TrancheHoraire T1);
    public TrancheHoraire getTrancheHoraireExist(TrancheHoraire T1);
    public List<TrancheHoraire> getIndisponibiliteEnseignant(String IdEnseignant);

    public List<TrancheHoraire> getIndisponibiliteEnseignantSemaine(String IdEnseignant, int IdSemaine);

    public String toString(TrancheHoraire T1);
    public String toString(List<TrancheHoraire> LT1);
}
