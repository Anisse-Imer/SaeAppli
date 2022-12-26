package TEST;
import dao.UtilisateurDaoImpl;
import models.usersFactory.UtilisateurEleve;

import java.util.List;

public class test {
    public static void main(String[] args) {
        UtilisateurDaoImpl UE = new UtilisateurDaoImpl();
        List<UtilisateurEleve> ListeBUT = UE.getElevesByGroup("BUT");
        for (UtilisateurEleve u:
             ListeBUT) {
            System.out.println(u.toString());
        }
    }
}