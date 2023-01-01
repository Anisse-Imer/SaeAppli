package dao.quota;

import dao.DAO;
import models.quota.QuotaEnseignant;
import models.usersFactory.User;

import java.util.List;

public interface QuotaEnseignantDao {
    public int[] quantiteModuleTypeCours(String IdEnseignant, String TypeCours, int module);

    public List<QuotaEnseignant> quotaEnseignantModule(User UserEns, int IdModule);

    public String toString(QuotaEnseignant Q);
}
