package dao.time;

import ConnectionJDBC.ConnectionJDBC;
import models.time.TrancheHoraire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrancheHoraireDaoImpl implements TrancheHoraireDao {

    public TrancheHoraireDaoImpl(){}

    @Override
    public TrancheHoraire get(int id) {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select *" +
                            " from tranches_horaires" +
                            " where tranche_horaire = ?");
            statement.setInt(1, id);
            ResultSet Tranche = statement.executeQuery();
            if(Tranche.next()) {
                TrancheHoraire TID = new TrancheHoraire(Tranche.getInt("tranche_horaire"),
                        Tranche.getInt("semaine_id"),
                        Tranche.getInt("jour_id"),
                        Tranche.getTime("heure_debut"),
                        Tranche.getTime("heure_fin"));
                return TID;
            }
            Tranche.close();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TrancheHoraire> getAll() {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select tranche_horaire" +
                            " from tranches_horaires");
            ResultSet IdTranches = statement.executeQuery();
            if(IdTranches.next()){
                List<TrancheHoraire> Tranches = new ArrayList<TrancheHoraire>();
                do {
                    Tranches.add(get(IdTranches.getInt("tranche_horaire")));
                }while (IdTranches.next());
                IdTranches.close();
                return Tranches;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    public void save(TrancheHoraire T1) {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("INSERT INTO tranches_horaires(" +
                            "heure_debut" +
                            ", heure_fin" +
                            ", jour_id" +
                            ", semaine_id" +
                            ")" +
                            " VALUES(" +
                            " ?" +
                            ", ?" +
                            ", ?" +
                            ", ?" +
                            ")");
            statement.setTime(1, T1.getDebut());
            statement.setTime(2, T1.getFin());
            statement.setInt(3, T1.getIdJour());
            statement.setInt(4, T1.getIdSemaine());
            statement.execute();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    @Override
    public void update(TrancheHoraire trancheHoraire) {
        //Cette méthode ne fait rien car mettre à jour une tranche horaire met en danger l'intégrité de la base
        //dans sa logique de gestion du temps. On attribue ou on crée une tranche horaire, on ne la modifie pas.
    }

    //Cette méthode est à utiliser avec grande précaution puisque supprimer des données qui sont pour la plupart,
    //générées automatiquement peuvent mettre en danger la base.
    //Supprimer une TrancheHoraire de la base.
    @Override
    public int delete(TrancheHoraire trancheHoraire) {
        if(get(trancheHoraire.getId()) != null){
            try {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("delete from tranches_horaires where tranche_horaire = ?");
                statement.setInt(1, trancheHoraire.getId());
                return statement.executeUpdate();
            }
            catch (SQLException SQLE){
                SQLE.printStackTrace();
            }
        }
        return 0;
    }

    //Version améliorée du save permettant
    public TrancheHoraire getTrancheHoraireExist(TrancheHoraire T1){
        if(getIdTrancheHoraire(T1) == 0){
            addSemaine(T1.getIdSemaine());
            save(T1);
            T1.setId(getIdTrancheHoraire(T1));
            return T1;
        }
        T1.setId(getIdTrancheHoraire(T1));
        return T1;
    }


    public int lastSemaine(){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select MAX(semaine_id)" +
                            " from semaines");
            ResultSet Semaine = statement.executeQuery();
            if(Semaine.next()){
                int LastId = Semaine.getInt("MAX(semaine_id)");
                Semaine.close();
                return LastId;
            }
            Semaine.close();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return -1;
    }

    public Date lastDateSemaines(){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select MAX(semaine_fin)" +
                            " from semaines");
            ResultSet Semaine = statement.executeQuery();
            if(Semaine.next()){
                Date LastDate = Semaine.getDate("MAX(semaine_fin)");
                Semaine.close();
                return LastDate;
            }
            Semaine.close();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    public Date dateInterval(Date D1, int interval){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select str_to_date(?, '%Y-%m-%d') + interval ? day as DINTERVAL" +
                            " from dual");
            statement.setString(1,D1.toString());
            statement.setInt(2, interval);
            ResultSet DateInterval = statement.executeQuery();
            if(DateInterval.next()){
                Date DInterval = DateInterval.getDate("DINTERVAL");
                DateInterval.close();
                return DInterval;
            }
            DateInterval.close();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }
    public void addSemaine(int SemaineId){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            int LastSemaine = lastSemaine();
            Date LastDate = lastDateSemaines();
            if(LastSemaine != -1 && SemaineId > LastSemaine){
                PreparedStatement statement = cnx.prepareStatement
                        ("INSERT INTO semaines" +
                                "(semaine_debut, semaine_fin)" +
                                " VALUES (?, ?)");
                for(int i = LastSemaine ; i < SemaineId + 1 ; i++) {
                    statement.setString(1,
                            dateInterval(LastDate, 1 + (i - LastSemaine) * 7 ).toString());
                    statement.setString(2,
                            dateInterval(LastDate, 7 + (i - LastSemaine) * 7 ).toString());
                    statement.execute();
                    PreparedStatement StatementJour = cnx.prepareStatement
                            ("CALL creer_jours_semaine(?)");
                    StatementJour.setInt(1, i + 1);
                    StatementJour.execute();
                }
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }
    public String getDateByTrancheHoraire(TrancheHoraire T1){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select s.semaine_debut + interval jour_id - 1 DAY as StringDate" +
                            " from tranches_horaires th" +
                            " , semaines s"+
                            " where th.semaine_id = s.semaine_id" +
                            " and th.jour_id = ?" +
                            " and th.semaine_id = ?" );
            statement.setInt(1, T1.getIdJour());
            statement.setInt(2, T1.getIdSemaine());
            ResultSet Tranche = statement.executeQuery();
            if(Tranche.next()){
                return Tranche.getDate("StringDate").toString();
            }
            Tranche.close();
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    public int getIdTrancheHoraire(TrancheHoraire T1){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select *" +
                            " from tranches_horaires" +
                            " where jour_id = ?" +
                            " and semaine_id = ?" +
                            " and heure_debut = ?" +
                            " and heure_fin = ?");
            statement.setInt(1, T1.getIdJour());
            statement.setInt(2,T1.getIdSemaine());
            statement.setTime(3,T1.getDebut());
            statement.setTime(4,T1.getFin());
            ResultSet Tranche = statement.executeQuery();
            if(Tranche.next()){
                int IdCherche = Tranche.getInt("tranche_horaire");
                Tranche.close();
                return IdCherche;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return 0;
    }

    public List<TrancheHoraire> getIndisponibiliteEnseignant(String IdEnseignant){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select tranche_horaire" +
                            " from indisponibilites_tuteurs" +
                            " where utilisateur_id = ?");
            statement.setString(1, IdEnseignant);
            ResultSet IdTranchesHoraires = statement.executeQuery();
            List<TrancheHoraire> Tranches = new ArrayList<TrancheHoraire>();
            while (IdTranchesHoraires.next()){
                TrancheHoraire TrancheSelonId = get(
                                                IdTranchesHoraires.getInt("tranche_horaire"));
                if(TrancheSelonId != null){
                    Tranches.add(TrancheSelonId);
                }
            }
            IdTranchesHoraires.close();
            return Tranches;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    public List<TrancheHoraire> getIndisponibiliteEnseignantSemaine(String IdEnseignant, int IdSemaine){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select th.tranche_horaire" +
                            " from indisponibilites_tuteurs i_d" +
                            "    , tranches_horaires th" +
                            " where i_d.utilisateur_id = ?" +
                            "   and th.tranche_horaire = i_d.tranche_horaire" +
                            "   and th.semaine_id = ?");
            statement.setString(1, IdEnseignant);
            statement.setInt(2, IdSemaine);
            ResultSet IdTranchesHoraires = statement.executeQuery();
            List<TrancheHoraire> Tranches = new ArrayList<TrancheHoraire>();
            while (IdTranchesHoraires.next()){
                TrancheHoraire TrancheSelonId = get(
                        IdTranchesHoraires.getInt("tranche_horaire"));
                if(TrancheSelonId != null){
                    Tranches.add(TrancheSelonId);
                }
            }
            IdTranchesHoraires.close();
            return Tranches;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    public String toString(TrancheHoraire T1){
        if(T1 != null) {
            String Date = getDateByTrancheHoraire(T1);
            return "[" + T1.getId() + " : " + Date + " : de " + T1.getDebut().toString() + " à " + T1.getFin() + "]";
        }
        return "";
    }

    public String toString(List<TrancheHoraire> LT1) {
        String Compilation = "";
        if (LT1 != null){
            for (TrancheHoraire T1 :
                    LT1) {
                Compilation = Compilation + toString(T1) + "\n";
            }
        }
        return Compilation;
    }
}
