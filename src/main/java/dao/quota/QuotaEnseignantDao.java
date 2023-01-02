package dao.quota;

import dao.DAO;
import models.quota.QuotaEnseignant;
import models.usersFactory.User;

import java.util.List;

public interface QuotaEnseignantDao extends DAO<QuotaEnseignant>{

    public QuotaEnseignant get(String IdEnseignant, String TypeCours, int module);

    //Retourne la quantite d'heures et de minutes prévues dans l'emploi du temps selon l'enseignant, le type de cours et le module.
    public int[] quantiteModuleTypeCours(String IdEnseignant, String TypeCours, int module);

    public List<QuotaEnseignant> quotaEnseignantModule(User UserEns, int IdModule);

    public String toString(QuotaEnseignant Q);
}
