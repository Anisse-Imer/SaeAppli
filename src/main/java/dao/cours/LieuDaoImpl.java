package dao.cours;

import ConnectionJDBC.ConnectionJDBC;
import models.cours.Lieu;
import models.time.TrancheHoraire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LieuDaoImpl implements LieuDao{
    public Lieu get(String Id){
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
            statement.setString(1, Id);
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

    @Override
    public List<Lieu> getAll() {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select lieu_id from lieux");
            ResultSet resultLieux = statement.executeQuery();
            if(resultLieux.next()){
                List<Lieu> AllLieux = new ArrayList<Lieu>();
                do{
                    AllLieux.add(get(resultLieux.getString("lieu_id")));
                }while (resultLieux.next());
                resultLieux.close();
                return AllLieux;
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Lieu lieu) {
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            if(lieu.getId() != null) {
                update(lieu);
            }
            else {
                PreparedStatement statement = cnx.prepareStatement
                        ("insert INTO lieux(" +
                                "lieu_id" +
                                ", capacite" +
                                ")" +
                                " values(" +
                                "?" +
                                ", ?" +
                                ")");
                statement.setString(1, lieu.getId());
                statement.setInt(2, lieu.getCapacite());
                statement.execute();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    @Override
    public void update(Lieu lieu) {
        try {
            if (lieu.getId() != null) {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("UPDATE LIEUX SET capacite = ? where lieu_id = ?");
                statement.setInt(1, lieu.getCapacite());
                statement.setString(2, lieu.getId());
                statement.execute();
            }
        }
        catch (SQLException SQLE){
            SQLE.printStackTrace();
        }
    }

    @Override
    public int delete(Lieu lieu) {
        if(lieu.getId() != null){
            try {
                Connection cnx = ConnectionJDBC.getInstance().getConnection();
                PreparedStatement statement = cnx.prepareStatement
                        ("delete from lieux where lieu_id = ?");
                statement.setString(1, lieu.getId());
                return statement.executeUpdate();
            }
            catch (SQLException SQLE){
                SQLE.printStackTrace();
            }
        }
        return 0;
    }
}
