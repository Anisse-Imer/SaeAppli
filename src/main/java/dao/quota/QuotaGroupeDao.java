package dao.quota;

import dao.DAO;
import models.quota.QuotaGroupe;

import java.util.List;

//Classe gérant les données de la table quantites_heures_modules, représentant les heures que doit réaliser une classe,
//selon le module, le type de cours ainsi que la semaine, sert à la composition d'un emploi du temps.
public interface QuotaGroupeDao extends DAO<QuotaGroupe> {

    List<QuotaGroupe> quotasByGroupeSemaine(String IdGroupe, int IdSemaine);

    String toString(QuotaGroupe Q);
    String toString(List<QuotaGroupe> QList);
}
