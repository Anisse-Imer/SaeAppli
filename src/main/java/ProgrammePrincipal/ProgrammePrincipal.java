package ProgrammePrincipal;

import dao.cours.CoursDaoImpl;
import dao.time.TrancheHoraireDao;
import dao.time.TrancheHoraireDaoImpl;
import dao.users.UtilisateurDaoImpl;
import models.cours.Cours;
import models.time.TrancheHoraire;
import models.usersFactory.User;

import java.nio.file.attribute.UserDefinedFileAttributeView;
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

    public static String entreeSemaine(){
        afficheSemaines();
        List<String> ListSemaine = new LinkedList<String>();
        ListSemaine.add("exit");
        for(int i = 1 ; i < new TrancheHoraireDaoImpl().lastSemaine() + 1; i++){
            ListSemaine.add(Integer.toString(i));
        }
        String EntreeSemaine = entree("L'emploi du temps de quelle semaine voulez-vous :"
                , ListSemaine);
        return EntreeSemaine;
    }

    public static User entreeUser(User user){
        String login;
        User UserAnalyse;
        do {
            System.out.println("Entrer le login de l'utilisateur dont vous voulez l'emploi du temps :");
            login = new Scanner(System.in).next();
            UserAnalyse = new UtilisateurDaoImpl().getUtilisateurConnectionById(user, login);
        }while (UserAnalyse == null && (!login.equals("exit")));
        return UserAnalyse;
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

    public static void whisper(){

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

    public static void redirect(User user){
        if(user.getFonction().equals("ENS")){
            enseignantMain(user);
        }
        else if(user.getFonction().equals("ELV")){
            eleveMain(user);
        }
        else if(user.getFonction().equals("ADMIN")){
            adminMain(user);
        }
    }

    public static void fonctionnaListeCoursSemaine(User user){
        String EntreeSemaine = entreeSemaine();
        if(!EntreeSemaine.equals("exit")){
            List<Cours> ListCoursDeUser = new CoursDaoImpl().getCoursByUser(user,Integer.parseInt(EntreeSemaine));
            if(ListCoursDeUser.isEmpty()){
                System.out.println("Aucun cours cette semaine :\n");
            }
            else {
                System.out.println(new CoursDaoImpl().toString(ListCoursDeUser));
            }
        }
        redirect(user);
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
                fonctionnaListeCoursSemaine(user);
            }
            default:;
        }
    }

    public static void fonctionnaliteIndispoSemaine(User user){
        String EntreeSemaine = entreeSemaine();
        if(!EntreeSemaine.equals("exit")){
            List<TrancheHoraire> ListIndispoDeUser = new TrancheHoraireDaoImpl().getIndisponibiliteEnseignantSemaine(user.getId()
                                                                                                                    , Integer.parseInt(EntreeSemaine));
            if(ListIndispoDeUser.isEmpty() || ListIndispoDeUser == null){
                System.out.println("Aucune indisponibilité(s) :");
            }
            else {
                System.out.println("Indisponibilités de " + new UtilisateurDaoImpl().getNomUser(user.getId()) + " pour la semaine " + EntreeSemaine + "\n"
                        + new TrancheHoraireDaoImpl().toString(ListIndispoDeUser));
            }
        }
        redirect(user);
    }

    public static void enseignantMain(User user){
        List<Fonctionnalite> FoncEns = new ArrayList<Fonctionnalite>();
        FoncEns.add(new Fonctionnalite(1, "Lecture emploi du temps"));
        FoncEns.add(new Fonctionnalite(2, "Lecture indisponibilités :"));
        int Choix = choixFonctionnalite(FoncEns);
        switch (Choix){
            case 0 : {
                System.out.println("Vous quittez :");
            }; break;
            case 1 : {
                fonctionnaListeCoursSemaine(user);
            };break;
            case 2 : {
                fonctionnaliteIndispoSemaine(user);
            };break;
            default:;
        }
    }

    public static void fonctionnaListeCoursSemaineSansRedirect(User user){
        String EntreeSemaine = entreeSemaine();
        if(!EntreeSemaine.equals("exit")){
            List<Cours> ListCoursDeUser = new CoursDaoImpl().getCoursByUser(user,Integer.parseInt(EntreeSemaine));
            if(ListCoursDeUser.isEmpty()){
                System.out.println("Aucune cours cette semaine :\n");
            }
            else {
                System.out.println("Indisponibilités de : " + user.getId() + " pour la semaine " + EntreeSemaine);
                System.out.println(new CoursDaoImpl().toString(ListCoursDeUser));
            }
        }
    }
    public static void fonctionnaListeCoursSemaineEnAdmin(User user){
        User UserAnalyse = entreeUser(user);
        if(UserAnalyse != null) {
            fonctionnaListeCoursSemaineSansRedirect(UserAnalyse);
        }
        redirect(user);
    }

    public static void fonctionnaliteIndispoSemaineSansRedirect(User user){
        String EntreeSemaine = entreeSemaine();
        if(!EntreeSemaine.equals("exit")){
            List<TrancheHoraire> ListIndispoDeUser = new TrancheHoraireDaoImpl().getIndisponibiliteEnseignantSemaine(user.getId()
                    , Integer.parseInt(EntreeSemaine));
            if(ListIndispoDeUser.isEmpty() || ListIndispoDeUser == null){
                System.out.println("Aucune indisponibilité : la semaine " + EntreeSemaine);
            }
            else {
                System.out.println("Indisponibilités de : " + user.getId() + " pour la semaine " + EntreeSemaine + "\n"
                        + new TrancheHoraireDaoImpl().toString(ListIndispoDeUser));
            }
        }
    }

    public static void fonctionnaliteIndispoSemaineEnAdmin(User user){
        User UserAnalyse = entreeUser(user);
        if(UserAnalyse != null) {
            fonctionnaliteIndispoSemaineSansRedirect(UserAnalyse);
        }
        redirect(user);
    }

    public static void fonctionnaliteCoursGroupeEnAdmin(User user){
        System.out.println("Quel groupe : ");
        String EntreeGroupe = new Scanner(System.in).next();
        if(!EntreeGroupe.equals("exit")){
            String EntreeSemaine = entreeSemaine();
            if(!EntreeSemaine.equals("exit")) {
                List<Cours> ListCoursGroupe = new CoursDaoImpl()
                        .getCoursByGroup(EntreeGroupe, Integer.parseInt(EntreeSemaine));
                new CoursDaoImpl().toString(ListCoursGroupe);
            }
        }
        redirect(user);
    }

    public static void adminMain(User user){
        List<Fonctionnalite> FoncAdmin = new ArrayList<Fonctionnalite>();
        FoncAdmin.add(new Fonctionnalite(1, "Lecture de l'emploi du temps d'un utilisateur :"));
        FoncAdmin.add(new Fonctionnalite(2, "Lecture de l'emploi du temps d'un groupe :"));
        FoncAdmin.add(new Fonctionnalite(3, "Lecture des indisponibilités d'un enseignant :"));
        int Choix = choixFonctionnalite(FoncAdmin);
        switch (Choix){
            case 0 : {
                System.out.println("Vous quittez :");
            }; break;
            case 1 : {
                fonctionnaListeCoursSemaineEnAdmin(user);
            };break;
            case 2 : {
                fonctionnaliteCoursGroupeEnAdmin(user);
            };break;
            case 3 : {
                fonctionnaliteIndispoSemaineEnAdmin(user);
            };break;
            default:;
        }
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
