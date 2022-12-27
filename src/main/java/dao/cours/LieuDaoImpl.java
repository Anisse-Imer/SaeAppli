package dao.cours;

import ConnectionJDBC.ConnectionJDBC;
import models.cours.Cours;
import models.cours.Lieu;
import models.time.TrancheHoraire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LieuDaoImpl {
    public Lieu getLieuById(String IdLieu){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select l.lieu_id" +
                            "  , l.capacite" +
                            "  , th.*" +
                            " from lieux l" +
                            "    , reservations_lieux rs" +
                            "    , tranches_horaires th" +
                            "  where l.lieu_id = rs.lieu_id" +
                            "    and th.tranche_horaire = rs.tranche_horaire" +
                            "    and l.lieu_id = ?");
            statement.setString(1, IdLieu);
            ResultSet ListLieu = statement.executeQuery();
            if(ListLieu.next()){
                //On récupère d'abord la première ligne
                String id = ListLieu.getString("lieu_id");
                int capacite = ListLieu.getInt("capacite");
                List<TrancheHoraire> Indispo = new LinkedList<TrancheHoraire>();
                Indispo.add(new TrancheHoraire(ListLieu.getInt("tranche_horaire")
                        , ListLieu.getInt("semaine_id")
                        , ListLieu.getInt("jour_id")
                        , ListLieu.getTime("heure_debut")
                        , ListLieu.getTime("heure_fin")));
                //Puis on récupère le reste des indsiponibilités.
                while (ListLieu.next()){
                    Indispo.add(new TrancheHoraire(ListLieu.getInt("tranche_horaire")
                            , ListLieu.getInt("semaine_id")
                            , ListLieu.getInt("jour_id")
                            , ListLieu.getTime("heure_debut")
                            , ListLieu.getTime("heure_fin")));
                }
                Lieu lieu = new Lieu(id, capacite, Indispo);
                ListLieu.close();
                return lieu;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }
}
