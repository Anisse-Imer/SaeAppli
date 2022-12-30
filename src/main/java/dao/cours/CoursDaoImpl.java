package dao.cours;

import ConnectionJDBC.ConnectionJDBC;
import dao.time.TrancheHoraireDaoImpl;
import dao.users.UtilisateurDaoImpl;
import models.cours.Cours;
import models.time.TrancheHoraire;
import models.usersFactory.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursDaoImpl implements CoursDao{

    public String CoursUser(User user){
        if(user.getFonction().equals("ENS")){
            //Retourne les cours d'un enseignant sur une semaine
            return "select c.*" +
                    "     , t_h.*" +
                    "  from cours c" +
                    "     , tranches_horaires t_h" +
                    " where c.tranche_horaire = t_h.tranche_horaire" +
                    "   and t_h.semaine_id = ?" +
                    "   and c.utilisateur_id = ?" +
                    " ORDER BY semaine_id, jour_id, heure_debut";
        }
        //Retourne les cours d'un élève sur une semaine
        return " select c.*" +
                "     , t_h.*" +
                "  from cours c" +
                "     , tranches_horaires t_h" +
                " where c.tranche_horaire = t_h.tranche_horaire" +
                "   and t_h.semaine_id = ?" +
                "   and groupe_id in (select groupe_id" +
                                    " from eleves" +
                                    " where utilisateur_id = ?)" +
                " ORDER BY semaine_id, jour_id, heure_debut";
    }

    //Retourne l'emploi du temps d'un utilisateur selon la semaine.
    public List<Cours> getCoursByUser(User user, int IdSemaine){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement(CoursUser(user));
            statement.setInt(1, IdSemaine);
            statement.setString(2, user.getId());
            ResultSet Cours = statement.executeQuery();
            List<Cours> ListeCours = new ArrayList<Cours>();
            while (Cours.next()){
                ListeCours.add(new Cours(Cours.getInt("cours_id")
                                        , new TrancheHoraire(Cours.getInt("tranche_horaire")                                   , Cours.getInt("semaine_id")
                                                            , Cours.getInt("jour_id")
                                                            , Cours.getTime("heure_debut")
                                                            , Cours.getTime("heure_fin"))
                                        , new LieuDaoImpl().get(Cours.getString("lieu_id"))
                                        , Cours.getString("groupe_id")
                                        , Cours.getString("utilisateur_id")
                                        , Cours.getString("type_cours_id")
                                        , new ModuleDaoImpl().get(Cours.getInt("module_id"))));
            }
            Cours.close();
            return ListeCours;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    public List<Cours> getCoursByGroup(String GroupeID, int SemaineID){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    (" select c.*" +
                            "     , t_h.*" +
                            "  from cours c" +
                            "     , tranches_horaires t_h" +
                            " where c.tranche_horaire = t_h.tranche_horaire" +
                            "   and groupe_id = ?" +
                            "   and t_h.semaine_id = ?" +
                            " ORDER BY semaine_id, jour_id, heure_debut");
            statement.setString(1, GroupeID);
            statement.setInt(2, SemaineID);
            ResultSet Cours = statement.executeQuery();
            List<Cours> ListeCours = new ArrayList<Cours>();
            while (Cours.next()){
                ListeCours.add(new Cours(Cours.getInt("cours_id")
                        , new TrancheHoraire(Cours.getInt("tranche_horaire")                                   , Cours.getInt("semaine_id")
                        , Cours.getInt("jour_id")
                        , Cours.getTime("heure_debut")
                        , Cours.getTime("heure_fin"))
                        , new LieuDaoImpl().get(Cours.getString("lieu_id"))
                        , Cours.getString("groupe_id")
                        , Cours.getString("utilisateur_id")
                        , Cours.getString("type_cours_id")
                        , new ModuleDaoImpl().get(Cours.getInt("module_id"))));
            }
            Cours.close();
            System.out.println(toString(ListeCours));
            return ListeCours;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    public String toString(Cours cours){
        if(cours != null) {
            UtilisateurDaoImpl UDAO = new UtilisateurDaoImpl();
            return "\nCours : " + cours.getId() + " : le " + new TrancheHoraireDaoImpl().toString(cours.getTemporalite()) + " : en " + cours.getEmplacement().getId()
                    + "\n" + cours.getModule().getNom() + " : " + cours.getTypeCours()
                    + "\nGroupe " + cours.getGroupeId() + " avec " + UDAO.getNomUser(cours.getEnseignantId()) + "\n";
        }
        return "";
    }

    public String toString(List<Cours> ListeCours){
        if (ListeCours != null){
            String CompilationCours = "";
            for (Cours C:
                 ListeCours) {
                CompilationCours = CompilationCours + toString(C);
            }
            return CompilationCours;
        }
        return "";
    }
}