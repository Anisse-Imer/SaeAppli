package TEST;
import ConnectionJDBC.ConnectionJDBC;
import dao.UtilisateurEleveDao;
import dao.UtilisateurEleveDaoImpl;
import models.users.Utilisateur;
import models.users.UtilisateurEleve;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class test {
    public static void main(String[] args) {
        UtilisateurEleveDaoImpl UE = new UtilisateurEleveDaoImpl();
        List<UtilisateurEleve> ListeBUT = UE.GetElevesByGroup("BUT");
        for (UtilisateurEleve u:
             ListeBUT) {
            System.out.println(u.toString());
        }
    }
}
