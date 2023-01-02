package dao.cours;

import ConnectionJDBC.ConnectionJDBC;
import dao.time.TrancheHoraireDaoImpl;
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

    //Renvoie un Lieu selon son Id (il faut noter que si le Lieu n'a aucune reservation
    // , il ne sera pas renvoyé par la méthode).
    public Lieu get(String Id){
        try{
            Connection cnx = ConnectionJDBC.getInstance().getConnection();
            PreparedStatement statement = cnx.prepareStatement
                    ("select l.lieu_id" +
                            "  , l.capacite" +
                            "  , rs.tranche_horaire" +
                            " from lieux l" +
                            "    , reservations_lieux rs" +
                            "  where l.lieu_id = rs.lieu_id" +
                            "    and l.lieu_id = ?");
            statement.setString(1, Id);
            ResultSet ListLieu = statement.executeQuery();
            if(ListLieu.next()){
                String id = ListLieu.getString("lieu_id");
                int capacite = ListLieu.getInt("capacite"); //On récupère d'abord la première ligne.
                List<TrancheHoraire> Indispo = new LinkedList<TrancheHoraire>();    //Puis les indisponibilités.
                do {
                    Indispo.add(new TrancheHoraireDaoImpl().get(ListLieu.getInt("tranche_horaire")));
                } while (ListLieu.next());
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

    //Renvoie tous les lieux réservés au moins une fois de la base.
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

    //Enregistre un nouveau lieu dans la base ou le met à jour si il existe déjà.
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

    //Met à jour la capacité d'un lieu.
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

    //Supprime un lieu de la base selon son Id.
    //Attention le lieu peut être réservé, donc impossible de le supprimer.
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

    //Renvoie les informations de Lieu en utilisant d'autres DAO.
    @Override
    public String toString(Lieu LieuS) {
        if(LieuS.getIndisponibilites() != null) {
            return LieuS.getId() + " : " + LieuS.getCapacite() + " : Indisponibilités"
                    + "\n" +new TrancheHoraireDaoImpl().toString(LieuS.getIndisponibilites());
        }
        return LieuS.getId() + " : " + LieuS.getCapacite();
    }

    //Renvoie les informations de plusieurs lieux.
    @Override
    public String toString(List<Lieu> ListeLieuS) {
        if(ListeLieuS != null){
            String compilationLieux = "";
            for (Lieu l:
                 ListeLieuS) {
                compilationLieux = toString(l);
            }
            return compilationLieux;
        }
        return "";
    }
}
