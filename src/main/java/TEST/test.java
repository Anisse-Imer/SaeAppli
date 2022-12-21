package TEST;
import ConnectionJDBC.ConnectionJDBC;
import dao.UtilisateurEleveDao;
import models.users.UtilisateurEleve;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<UtilisateurEleve> ListeBUT = UtilisateurEleveDao.GetElevesbyGroup("BUT");
        for (UtilisateurEleve u:
             ListeBUT) {
            System.out.println(u.toString());
        }
    }
}
