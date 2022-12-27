package dao.cours;

import ConnectionJDBC.ConnectionJDBC;
import models.cours.Cours;
import models.time.TrancheHoraire;
import models.usersFactory.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursDaoImpl {

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
                                        , new LieuDaoImpl().getLieuById(Cours.getString("lieu_id"))
                                        , Cours.getString("groupe_id")
                                        , Cours.getString("utilisateur_id")
                                        , Cours.getString("type_cours_id")
                                        , new ModuleDaoImpl().getModuleById(Cours.getInt("module_id"))));
            }
            Cours.close();
            return ListeCours;
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }
}