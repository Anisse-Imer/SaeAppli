package dao.quota;

import ConnectionJDBC.ConnectionJDBC;
import dao.cours.ModuleDaoImpl;
import models.cours.Module;
import models.quota.QuotaEnseignant;
import models.quota.QuotaGroupe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuotaGroupeDaoImpl implements QuotaGroupeDao{
    public List<QuotaGroupe> quotasByGroupeSemaine(String IdGroupe, int IdSemaine){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select *" +
                            "  from quantites_heures_modules q" +
                            " where semaine_id = ?" +
                            "   and groupe_id = ?");
            statement.setString(2, IdGroupe);
            statement.setInt(1, IdSemaine);
            ResultSet resultSetQuota = statement.executeQuery();
            List<QuotaGroupe> Quotas = new ArrayList<QuotaGroupe>();
            while (resultSetQuota.next()){
                Quotas.add(new QuotaGroupe(IdGroupe
                                            , new ModuleDaoImpl().get(resultSetQuota.getInt("module_id"))
                                            , resultSetQuota.getString("type_cours_id")
                                            , IdSemaine
                                            , resultSetQuota.getInt("quantite_heures")));
            }
            return Quotas;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    public QuotaGroupe get(String IdGroupe, int IdModule, String TypeCours, int IdSemaine){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select *" +
                            "  from quantites_heures_modules" +
                            " where groupe_id = ?" +
                            "   and type_cours_id = ?" +
                            "   and module_id = ?" +
                            "   and semaine_id = ?");
            statement.setString(1,IdGroupe);
            statement.setString(2,TypeCours);
            statement.setInt(3, IdModule);
            statement.setInt(4, IdSemaine);
            ResultSet ResultQuotas = statement.executeQuery();
            if(ResultQuotas.next()){
                QuotaGroupe Quota = new QuotaGroupe(IdGroupe
                        , new ModuleDaoImpl().get(IdModule)
                        , TypeCours
                        , IdSemaine
                        , ResultQuotas.getInt("quantite_heures"));
                ResultQuotas.close();
                return Quota;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    @Override
    public List<QuotaGroupe> getAll() {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select *" +
                            "  from quantites_heures_modules");
            ResultSet ResultModule = statement.executeQuery();
            if(ResultModule.next()){
                List<QuotaGroupe> AllQuotas = new ArrayList<QuotaGroupe>();
                do{
                    AllQuotas.add(get(ResultModule.getString("groupe_id")
                            , ResultModule.getInt("module_id")
                            , ResultModule.getString("type_cours_id")
                            , ResultModule.getInt("semaine_id")));
                }while (ResultModule.next());
                ResultModule.close();
                return AllQuotas;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(QuotaGroupe quotaGroupe) {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            if(get(quotaGroupe.getIdGroupe()
                    , quotaGroupe.getModuleQ().getId()
                    , quotaGroupe.getTypeCours()
                    , quotaGroupe.getIdSemaine()) != null) {
                update(quotaGroupe);
            }
            else {
                PreparedStatement statement = cnx.prepareStatement
                        ("insert into quantites_heures_modules(" +
                                "groupe_id" +
                                ", module_id" +
                                ", type_cours_id" +
                                ", semaine_id" +
                                ", quantite_heures" +
                                ")" +
                                " values(" +
                                "'BUT1'" +
                                ", 2" +
                                ", 'CM'" +
                                ", 1" +
                                ", 1" +
                                ")");
                statement.setString(1,quotaGroupe.getIdGroupe());
                statement.setInt(2, quotaGroupe.getModuleQ().getId());
                statement.setString(3, quotaGroupe.getTypeCours());
                statement.setInt(4, quotaGroupe.getIdSemaine());
                statement.setInt(5, quotaGroupe.getQuantite());
                statement.execute();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    @Override
    public void update(QuotaGroupe quotaGroupe) {
        try {
            if (get(quotaGroupe.getIdGroupe()
                    , quotaGroupe.getModuleQ().getId()
                    , quotaGroupe.getTypeCours()
                    , quotaGroupe.getIdSemaine()) != null) {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("UPDATE quantites_heures_modules" +
                                "  SET quantite_heures = ? " +
                                "where groupe_id = 'BUT1'" +
                                "  and module_id = 1 " +
                                "  and semaine_id = 2" +
                                "  and type_cours_id = 'CM'");
                statement.setInt(1,quotaGroupe.getQuantite());
                statement.setString(2,quotaGroupe.getIdGroupe());
                statement.setInt(3,quotaGroupe.getModuleQ().getId());
                statement.setInt(4,quotaGroupe.getIdSemaine());
                statement.setString(5,quotaGroupe.getTypeCours());
                statement.execute();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    @Override
    public int delete(QuotaGroupe quotaGroupe) {
        if(get(quotaGroupe.getIdGroupe()
                , quotaGroupe.getModuleQ().getId()
                , quotaGroupe.getTypeCours()
                , quotaGroupe.getIdSemaine()) != null) {
            try {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("delete from quantites_heures_modules" +
                                " where groupe_id = ?" +
                                "   and module_id = ?" +
                                "   and semaine_id = ?" +
                                "   and type_cours_id = ?");
                statement.setString(1, quotaGroupe.getIdGroupe());
                statement.setInt(2,quotaGroupe.getModuleQ().getId());
                statement.setInt(3,quotaGroupe.getIdSemaine());
                statement.setString(4,quotaGroupe.getTypeCours());
                return statement.executeUpdate();
            } catch (SQLException SQLE) {
                SQLE.printStackTrace();
            }
        }
        return 0;
    }

    public String toString(QuotaGroupe Q){
        if(Q != null) {
            return "Semaine " + Q.getIdSemaine() + " : Groupe " + Q.getIdGroupe() + " : "
                    + Q.getTypeCours() + " : " + Q.getModuleQ().getNom();
        }
        return "";
    }

    @Override
    public String toString(List<QuotaGroupe> QList) {
        if(QList != null) {
            String Compilation = "";
            for (QuotaGroupe Q :
                    QList) {
                Compilation = Compilation + toString(Q) + "\n";
            }
            return Compilation;
        }
        return "";
    }
}
