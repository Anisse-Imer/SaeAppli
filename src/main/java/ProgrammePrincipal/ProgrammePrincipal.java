package ProgrammePrincipal;

import dao.cours.CoursDaoImpl;
import dao.time.TrancheHoraireDao;
import dao.time.TrancheHoraireDaoImpl;
import dao.users.UtilisateurDaoImpl;
import models.cours.Cours;
import models.usersFactory.User;
import models.usersFactory.UtilisateurEleve;

import java.util.*;

public class ProgrammePrincipal {

    //Méthode servant à l'entrée de données par l'utilisateur.
    public static String entree(String Message, List<String> EntreesValables){
        Scanner console = new Scanner(System.in);
        String scan;
        do{
            System.out.println(Message);
            scan = console.next();
        }while(!EntreesValables.contains(scan));
        return scan;
    }

    public static void afficheSemaines(){
        TrancheHoraireDao ThDao = new TrancheHoraireDaoImpl();
        Date LastDate = ThDao.lastDateSemaines();
        int LastSemaine = ThDao.lastSemaine();
        if(LastSemaine != -1 && LastDate != null) {
            for (int i = LastSemaine; 0 < i; i = i - 1) {
                String affichage = "Semaine " + (i) + " : " +
                        ThDao.dateInterval((java.sql.Date) LastDate, - 6 + (7 * (i - LastSemaine))).toString() +
                        " : " +
                        ThDao.dateInterval((java.sql.Date) LastDate,(7 * (i - LastSemaine)));
                System.out.println(affichage);
            }
        }
    }

    public static User authentification(){
        Scanner console = new Scanner(System.in);
        String login;
        String mdp;
        User user;
        do{
            System.out.println("Entrez votre login :");
            login = console.next();
            System.out.println("Entrez votre mot de passse :");
            mdp = console.next();
            user = new UtilisateurDaoImpl().getUtilisateurConnection(login, mdp);
        }while(!(user != null || mdp.equals("exit") || login.equals("exit")));
        return user;
    }

    public static void fonctionnaliteCoursSemaine(User user){
        afficheSemaines();
        List<String> ListSemaine = new LinkedList<String>();
        ListSemaine.add("exit");
        for(int i = 1 ; i < new TrancheHoraireDaoImpl().lastSemaine() + 1; i++){
            ListSemaine.add(Integer.toString(i));
        }
        String Entree = entree("L'emploi du temps de quelle semaine voulez-vous :"
                                , ListSemaine);
        if(!Entree.equals("exit")){
            List<Cours> ListCoursDeUser = new CoursDaoImpl().getCoursByUser(user,Integer.parseInt(Entree));
            if(ListCoursDeUser.isEmpty()){
                System.out.println("Aucune cours cette semaine :\n");
            }
            else {
                System.out.println(ListCoursDeUser.toString());
            }
        }
        eleveMain(user);
    }

    public static int choixFonctionnalite(List<Fonctionnalite> ListFonc){
        System.out.println(ListFonc.toString());
        List<String> Choix = new ArrayList<String>();
        Choix.add("exit");
        for (Fonctionnalite F :
             ListFonc) {
            Choix.add(Integer.toString(F.getNumeroF()));
        }
        String Entree = entree("Quelle fonctionnalite ? : "
                                , Choix);
        if(Entree.equals("exit")){
            return 0;
        }
        return Integer.parseInt(Entree);
    }

    public static void eleveMain(User user){
        List<Fonctionnalite> FoncEleve = new ArrayList<Fonctionnalite>();
        FoncEleve.add(new Fonctionnalite(1, "Lecture emploi du temps"));
        int Choix = choixFonctionnalite(FoncEleve);
        switch (Choix){
            case 0 : {
                System.out.println("Vous quittez :");
            }; break;
            case 1 : {
                fonctionnaliteCoursSemaine(user);
            }
            default:;
        }
    }

    public static void enseignantMain(User user){
        List<Fonctionnalite> FoncEns = new ArrayList<Fonctionnalite>();
        FoncEns.add(new Fonctionnalite(1, "Lecture emploi du temps"));
        int Choix = choixFonctionnalite(FoncEns);
        switch (Choix){
            case 0 : {
                System.out.println("Vous quittez :");
            }; break;
            case 1 : {
                fonctionnaliteCoursSemaine(user);
            }
            default:;
        }
    }

    public static void adminMain(User user){
        System.out.println("adminMain" + user.toString());
    }

    public static void application(User user){
        if(user != null) {
            switch (user.getFonction()) {
                case "ELV": {
                    eleveMain(user);
                };break;
                case "ENS": {
                    enseignantMain(user);
                };break;
                case "ADMIN": {
                    adminMain(user);
                };break;
            }
        }
    }

    public static void main(String[] args) {
        User user = authentification();
        application(user);
    }
}
