package dao.quota;

import ConnectionJDBC.ConnectionJDBC;
import dao.cours.ModuleDaoImpl;
import dao.time.TrancheHoraireDao;
import dao.time.TrancheHoraireDaoImpl;
import dao.users.UtilisateurDaoImpl;
import models.cours.Module;
import models.quota.QuotaEnseignant;
import models.time.TrancheHoraire;
import models.usersFactory.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuotaEnseignantDaoImpl implements QuotaEnseignantDao{

    public int[] quantiteModuleTypeCours(String IdEnseignant, String TypeCours, int IdModule) {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select tranche_horaire" +
                            "  from cours c" +
                            " where c.utilisateur_id = ?" +
                            "   and c.module_id = ?" +
                            "   and c.type_cours_id = ?");
            statement.setString(1, IdEnseignant);
            statement.setInt(2, IdModule);
            statement.setString(3, TypeCours);
            ResultSet resultSetQuota = statement.executeQuery();
            if (resultSetQuota.next()){
                int TotalHeure = 0;
                int TotalMinutes = 0;
                TrancheHoraireDao THDAO = new TrancheHoraireDaoImpl();
                do{
                    TrancheHoraire THCOURS = THDAO.get(resultSetQuota.getInt("tranche_horaire"));
                    Time Difference = THCOURS.difference();
                    TotalHeure = TotalHeure + Difference.getHours();
                    TotalMinutes = TotalMinutes + Difference.getMinutes();
                }while (resultSetQuota.next());
                TotalHeure = TotalHeure + (TotalMinutes / 60);
                TotalMinutes = TotalMinutes % 60 ;
                resultSetQuota.close();
                int[] Quantites = new int[2];
                Quantites[0] = TotalHeure;
                Quantites[1] = TotalMinutes;
                return Quantites;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    public List<QuotaEnseignant> quotaEnseignantModule(User UserEns, int IdModule) {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select q.*" +
                            "     , m.*" +
                            "  from quotas q" +
                            "     , modules m" +
                            " where q.module_id = m.module_id" +
                            "   and q.utilisateur_id = ?" +
                            "   and q.module_id = ?");
            statement.setString(1, UserEns.getId());
            statement.setInt(2, IdModule);
            ResultSet resultSetQuota = statement.executeQuery();
            List<QuotaEnseignant> Quotas = new ArrayList<QuotaEnseignant>();
            if (resultSetQuota.next()){
                do{
                    int[] Quantites = quantiteModuleTypeCours(UserEns.getId()
                                                            , resultSetQuota.getString("type_cours_id")
                                                            , IdModule);
                    if(Quantites != null) {
                        Quotas.add(new QuotaEnseignant(resultSetQuota.getString("utilisateur_id")
                                , resultSetQuota.getString("type_cours_id")
                                , new ModuleDaoImpl().get(IdModule)
                                , resultSetQuota.getInt("nombre_heures_quota")
                                , Quantites[0]
                                , Quantites[1]));
                    }
                    else {
                        Quotas.add(new QuotaEnseignant(resultSetQuota.getString("utilisateur_id")
                                , resultSetQuota.getString("type_cours_id")
                                , new ModuleDaoImpl().get(IdModule)
                                , resultSetQuota.getInt("nombre_heures_quota")
                                , 0
                                , 0));
                    }
                }while (resultSetQuota.next());
                resultSetQuota.close();
                return Quotas;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    public QuotaEnseignant get(String IdEnseignant, String TypeCours, int IdModule){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select *" +
                            " from quotas" +
                            " where utilisateur_id = ?" +
                            "   and type_cours_id = ?" +
                            "   and module_id = ?");
            statement.setString(1, IdEnseignant);
            statement.setString(2, TypeCours);
            statement.setInt(3, IdModule);
            ResultSet resultSetQuota = statement.executeQuery();
            if(resultSetQuota.next()){
                QuotaEnseignant Quota;
                int[] Quantites = quantiteModuleTypeCours(IdEnseignant
                        , resultSetQuota.getString("type_cours_id")
                        , IdModule);
                if(Quantites != null) {
                    Quota = new QuotaEnseignant(resultSetQuota.getString("utilisateur_id")
                            , resultSetQuota.getString("type_cours_id")
                            , new ModuleDaoImpl().get(IdModule)
                            , resultSetQuota.getInt("nombre_heures_quota")
                            , Quantites[0]
                            , Quantites[1]);
                }
                else {
                    Quota = new QuotaEnseignant(resultSetQuota.getString("utilisateur_id")
                            , resultSetQuota.getString("type_cours_id")
                            , new ModuleDaoImpl().get(IdModule)
                            , resultSetQuota.getInt("nombre_heures_quota")
                            , 0
                            , 0);
                }
                resultSetQuota.close();
                return Quota;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    @Override
    public List<QuotaEnseignant> getAll() {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select *" +
                            " from quotas");
            ResultSet resultSetQuota = statement.executeQuery();
            List<QuotaEnseignant> AllQuota = new ArrayList<QuotaEnseignant>();
            if(resultSetQuota.next()){
                do {
                    AllQuota.add(get(resultSetQuota.getString("utilisateur_id")
                            , resultSetQuota.getString("type_cours_id")
                            , resultSetQuota.getInt("module_id")));
                }while (resultSetQuota.next());
                resultSetQuota.close();
                return AllQuota;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(QuotaEnseignant quotaEnseignant) {
        try {
            if (get(quotaEnseignant.getIdEnseignant()
                    , quotaEnseignant.getTypeCours()
                    , quotaEnseignant.getModuleQ().getId()) != null) {
                update(quotaEnseignant);
            }
            else {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("insert INTO quotas(" +
                                "utilisateur_id" +
                                ", type_cours_id" +
                                ", module_id" +
                                ", nombre_heures_quota" +
                                ")" +
                                " values(" +
                                "?" +
                                ",?" +
                                ",?" +
                                ",?" +
                                ")");
                statement.setString(1,quotaEnseignant.getIdEnseignant());
                statement.setString(2,quotaEnseignant.getTypeCours());
                statement.setInt(3,quotaEnseignant.getModuleQ().getId());
                statement.setInt(4,quotaEnseignant.getQuantite());
                statement.execute();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    @Override
    public void update(QuotaEnseignant quotaEnseignant) {
        try {
            if (get(quotaEnseignant.getIdEnseignant()
                    , quotaEnseignant.getTypeCours()
                    , quotaEnseignant.getModuleQ().getId()) != null) {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("UPDATE quotas" +
                                "   SET nombre_heures_quota = ?" +
                                " where utilisateur_id = ?" +
                                "   and module_id = ?" +
                                "   and type_cours_id = ?");
                statement.setInt(1,quotaEnseignant.getQuantite());
                statement.setString(2,quotaEnseignant.getIdEnseignant());
                statement.setInt(3,quotaEnseignant.getModuleQ().getId());
                statement.setString(4,quotaEnseignant.getTypeCours());
                statement.executeUpdate();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    @Override
    public int delete(QuotaEnseignant quotaEnseignant) {
        try {
            if (get(quotaEnseignant.getIdEnseignant()
                    , quotaEnseignant.getTypeCours()
                    , quotaEnseignant.getModuleQ().getId()) != null) {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("delete from quotas" +
                                " where utilisateur_id = ?" +
                                " and type_cours_id = ?" +
                                " and module_id = ?");
                statement.setString(1,quotaEnseignant.getIdEnseignant());
                statement.setString(2,quotaEnseignant.getTypeCours());
                statement.setInt(3,quotaEnseignant.getModuleQ().getId());
                return statement.executeUpdate();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return 0;
    }

    public String toString(QuotaEnseignant Q){
        if(Q != null){
            return new UtilisateurDaoImpl().getNomUser(Q.getIdEnseignant()) + " : " + Q.getModuleQ().getNom()
                    + " " + Q.getTypeCours() + "\nHeures prévues : " + Q.getQuantite() + "Heures planifiées : "
                    + Q.getQuantiteHeurePlanifie() + "heure(s):" + Q.getQuantiteMinutesPlafnifie() + "minute(s)\n";
        }
        return "";
    }
    public String toString(List<QuotaEnseignant> QList){
        String compilationQuotas = "";
        if(QList != null){
            for (QuotaEnseignant q:
                 QList) {
                compilationQuotas = compilationQuotas + toString(q) + "\n";
            }
            return compilationQuotas;
        }
        return "";
    }
}
