package TEST;
import dao.UtilisateurEleveDaoImpl;
import models.users.UtilisateurEleve;

import java.util.List;

public class test {
    public static void main(String[] args) {
        UtilisateurEleveDaoImpl UE = new UtilisateurEleveDaoImpl();
        List<UtilisateurEleve> ListeBUT = UE.getElevesByGroup("BUT");
        for (UtilisateurEleve u:
             ListeBUT) {
            System.out.println(u.toString());
        }
    }
}
