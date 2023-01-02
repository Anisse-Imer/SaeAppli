package dao.quota;

import dao.DAO;
import models.quota.QuotaEnseignant;
import models.quota.QuotaGroupe;

import java.util.List;

public interface QuotaGroupeDao extends DAO<QuotaGroupe> {

    public List<QuotaGroupe> quotasByGroupeSemaine(String IdGroupe, int IdSemaine);

    public String toString(QuotaGroupe Q);
    public String toString(List<QuotaGroupe> QList);
}
