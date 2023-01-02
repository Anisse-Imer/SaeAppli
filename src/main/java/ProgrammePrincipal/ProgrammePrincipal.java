package ProgrammePrincipal;

import dao.cours.CoursDaoImpl;
import dao.cours.LieuDaoImpl;
import dao.cours.ModuleDaoImpl;
import dao.quota.QuotaEnseignantDaoImpl;
import dao.quota.QuotaGroupeDaoImpl;
import dao.time.TrancheHoraireDao;
import dao.time.TrancheHoraireDaoImpl;
import dao.users.UtilisateurDaoImpl;
import models.cours.Cours;
import models.cours.Lieu;
import models.cours.Module;
import models.quota.QuotaEnseignant;
import models.quota.QuotaGroupe;
import models.time.TrancheHoraire;
import models.usersFactory.User;
import java.util.*;

public class ProgrammePrincipal {

    //----------------------------Partie méthodes pratiques

    //Méthode servant à l'entrée de données par l'utilisateur, ne laisse passer que les chaines de caractères,
    //contenues dans la liste EntreesValables.
    //Cette méthode sert de base à toutes les autres entrées de données.
    //On fera souvent passer "exit" dans la liste pour gérer le retour en arrière.
    //Va se dresser alors souvent un même schéma, on dresse une liste des entrées possibles et en onvoie vers cette méthode.
    public static String entree(String Message, List<String> EntreesValables){
        Scanner console = new Scanner(System.in);
        String scan;
        do{
            System.out.println(Message);
            scan = console.next();
        }while(!EntreesValables.contains(scan));
        return scan;
    }

    //Méhtode servant à afficher des fonctions pour ensuite les sélectionner.
    public static void afficheFonc(List<Fonctionnalite> Foncs){
        System.out.println("\n");
        System.out.println("Fonctionnalités;");
        for (Fonctionnalite F:
             Foncs) {
            System.out.println(F);
        }
    }

    //Affiche toutes les semaines contenues dans la base.
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

    //Affiche tous les modules contenus dans la base.
    public static void afficheModules(){
        System.out.println("\n");
        List<Module> ListModules = new ModuleDaoImpl().getAll();
        if(ListModules != null) {
            for (Module M :
                    ListModules) {
                System.out.println(M.toString());
            }
        }
        else {
            System.out.println("\nAucun module dans la base :");
        }
    }

    //Permet l'entrée d'une semaine existante dans la base.
    public static String entreeSemaine(){
        afficheSemaines();
        List<String> ListSemaine = new LinkedList<>();
        ListSemaine.add("exit");
        for(int i = 1 ; i < new TrancheHoraireDaoImpl().lastSemaine() + 1; i++){
            ListSemaine.add(Integer.toString(i));
        }
        return entree("De quelle semaine voulez-vous les informations :"
                , ListSemaine);
    }

    //Permet l'entrée du numéro d'un module.
    //On récupère les modules puis on rassemble les numéros de module dans une Liste que l'utilisateur devra
    public static int entreeModule(){
        afficheModules();
        List<Module> ListModules = new ModuleDaoImpl().getAll();
        if(ListModules != null) {
            List<String> IdModules = new LinkedList<>();
            for (Module M :
                    ListModules) {
                IdModules.add(Integer.toString(M.getId()));
            }
            IdModules.add("exit");
            String EntreeModule = entree("\nEntrez le numéro de module : ", IdModules);
            if(!EntreeModule.equals("exit")) {
                return Integer.parseInt(EntreeModule);
            }
        }
        return -1;
    }

    //Méthode demandant et renvoyant l'id d'un lieu entré par l'utilisateur.
    //Renvoie "exit" s'il n'existe pas de lieu dans la base.
    public static String entreLieu(){
        List<Lieu> AllLieu = new LieuDaoImpl().getAll();
        if(AllLieu != null) {
            List<String> EntreePossibleLieu = new LinkedList<>();
            EntreePossibleLieu.add("exit");
            for (Lieu L :
                    AllLieu) {
                if (L != null) {
                    EntreePossibleLieu.add(L.getId());
                }
            }
            System.out.println(EntreePossibleLieu);
            String EntreeLieu = entree("\nDe quel lieu voulez-vous les informations? :", EntreePossibleLieu);
            if (!EntreeLieu.equals("exit")) {
                return EntreeLieu;
            }
            return "exit";
        }
        System.out.println("\nAucun lieu : ");
        return "exit";
    }

    //L'utilisateur entre d'abord son login et son mot de passe.
    //Si un compte correspond dans la base, on donne accès à ce compte en renvoyant le User.
    //L'utilisateur doit se connecter, taper 'exit' s'il veut quitter.
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

    //Méthode retournant le numéro de la fonctionnalité choisie par l'utilisateur.
    //Renvoie 0 si l'utilisateur veut quitter.
    public static int choixFonctionnalite(List<Fonctionnalite> ListFonc){
        afficheFonc(ListFonc);
        List<String> Choix = new ArrayList<>();
        Choix.add("exit");                                      //L'option de sortie est présente.
        for (Fonctionnalite F :
                ListFonc) {
            Choix.add(Integer.toString(F.getNumeroF()));        //On remplit les choix valables.
        }
        String Entree = entree("Quelle fonctionnalite ? : "
                , Choix);
        if(Entree.equals("exit")){
            return 0;
        }
        return Integer.parseInt(Entree);
    }

    //Méthode permettant d'entrer sans être un user un login et un mot de passe pour tenter de se connecter à la base.
    //On peut partir en entrant "exit".
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

    //Méthode permettant de rediriger un utilisateur selon sa fonction vers le menu principal associé.
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

    //----------------------------Partie Eleve

    //Fonctionnalité qui permet à l'utilisateur de récupérer ses cours pour la semaine selon son rôle.
    //Remarque un utilisateur Admin peut se servir de la connexion d'un autre utilisateur pour accéder à cette
    //fonctionnalité.
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

    //Menu principal des élèves.
    public static void eleveMain(User user){
        List<Fonctionnalite> FoncEleve = new ArrayList<>();
        FoncEleve.add(new Fonctionnalite(1, "Lecture emploi du temps"));
        int Choix = choixFonctionnalite(FoncEleve);
        switch (Choix){
            case 0 : {
                System.out.println("Vous quittez :");
            } break;
            case 1 : {
                fonctionnaListeCoursSemaine(user);
            }
            default:
        }
    }

    //----------------------------Partie Enseignant
    public static void fonctionnaliteIndispoSemaine(User user){
        String EntreeSemaine = entreeSemaine();
        if(!EntreeSemaine.equals("exit")){
            List<TrancheHoraire> ListIndispoDeUser = new TrancheHoraireDaoImpl().getIndisponibiliteEnseignantSemaine(user.getId()
                                                                                                                    , Integer.parseInt(EntreeSemaine));
            if(ListIndispoDeUser.isEmpty()){
                System.out.println("Aucune indisponibilité(s) :");
            }
            else {
                System.out.println("Indisponibilités de " + new UtilisateurDaoImpl().getNomUser(user.getId()) + " pour la semaine " + EntreeSemaine + "\n"
                        + new TrancheHoraireDaoImpl().toString(ListIndispoDeUser));
            }
        }
        redirect(user);
    }

    //Menu principal des enseignants
    public static void enseignantMain(User user){
        List<Fonctionnalite> FoncEns = new ArrayList<>();
        FoncEns.add(new Fonctionnalite(1, "Lecture emploi du temps"));
        FoncEns.add(new Fonctionnalite(2, "Lecture indisponibilités :"));
        int Choix = choixFonctionnalite(FoncEns);
        switch (Choix){
            case 0 : {
                System.out.println("Vous quittez :");
            } break;
            case 1 : {
                fonctionnaListeCoursSemaine(user);
            }break;
            case 2 : {
                fonctionnaliteIndispoSemaine(user);
            }break;
            default:
        }
    }

    //----------------------------Partie Admin

    //Méthode fonctionnaListeCoursSemaine sans redirection vers le menu principal.
    //Cela aurait pour effet d'envoyer l'admin vers le menu de l'utilisateur de substitution.
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

    //Méthode permettant à l'admin d'accéder à la fonctionnalité fonctionnaListeCoursSemaine en tant que UserAnalyse
    //par substitution.
    public static void fonctionnaListeCoursSemaineEnAdmin(User user){
        User UserAnalyse = entreeUser(user);
        if(UserAnalyse != null) {
            fonctionnaListeCoursSemaineSansRedirect(UserAnalyse);
        }
        redirect(user);
    }

    //Méthode fonctionnaliteIndispoSemaine sans redirection vers le menu principal.
    //Cela aurait pour effet d'envoyer l'admin vers le menu de l'utilisateur de substitution.
    public static void fonctionnaliteIndispoSemaineSansRedirect(User user){
        String EntreeSemaine = entreeSemaine();
        if(!EntreeSemaine.equals("exit")){
            List<TrancheHoraire> ListIndispoDeUser = new TrancheHoraireDaoImpl().getIndisponibiliteEnseignantSemaine(user.getId()
                    , Integer.parseInt(EntreeSemaine));
            if(ListIndispoDeUser.isEmpty()){
                System.out.println("Aucune indisponibilité : la semaine " + EntreeSemaine);
            }
            else {
                System.out.println("Indisponibilités de : " + new UtilisateurDaoImpl().getNomUser(user.getId()) + " pour la semaine " + EntreeSemaine + "\n"
                        + new TrancheHoraireDaoImpl().toString(ListIndispoDeUser));
            }
        }
    }

    //Fonctionnalité permettant à l'admin d'accéder à la fonctionnalité de lecture des indisponibilités
    //d'un professeur en tant que ce même professeur.
    public static void fonctionnaliteIndispoSemaineEnAdmin(User user){
        User UserAnalyse = entreeUser(user);
        if(UserAnalyse != null) {
            fonctionnaliteIndispoSemaineSansRedirect(UserAnalyse);
        }
        redirect(user);
    }

    //Fonctionnalité permettant à l'admin d'obtenir l'emploi du temps d'un groupe.
    public static void fonctionnaliteCoursGroupeEnAdmin(User user){
        if(new UtilisateurDaoImpl().getAllGroupes() != null) {
            System.out.println(new UtilisateurDaoImpl().getAllGroupes());
            List<String> Groupes = new UtilisateurDaoImpl().getAllGroupes();
            Groupes.add("exit");
            String EntreeGroupe = entree("Quel groupe ? : ", Groupes);
            if (!EntreeGroupe.equals("exit")) {
                String EntreeSemaine = entreeSemaine();
                if (!EntreeSemaine.equals("exit")) {
                    List<Cours> ListCoursGroupe = new CoursDaoImpl()
                            .getCoursByGroup(EntreeGroupe, Integer.parseInt(EntreeSemaine));
                    if(ListCoursGroupe != null && !ListCoursGroupe.isEmpty()) {
                        new CoursDaoImpl().toString(ListCoursGroupe);
                    }
                    else {
                        System.out.println("Aucun cours");
                    }
                }
            }
            redirect(user);
        }
        else {
            System.out.println("Pas de groupe enregistré");
            redirect(user);
        }
    }

    //Permet de relever les informations relatives à la quantité prévue d'heures de cours d'un enseignant.
    public static void fonctionnaliteHeuresEnseignantenAdmin(User user){
        User Enseignant = entreeUser(user);
        if(Enseignant != null){
            int ModuleId = entreeModule();
            if(ModuleId != -1) {
                List<QuotaEnseignant> Quotas = new QuotaEnseignantDaoImpl().quotaEnseignantModule(Enseignant, ModuleId);
                if(Quotas != null) {
                    if (Quotas.isEmpty()) {
                        System.out.println("Rien d'enregistrer");
                    }
                    System.out.println(new QuotaEnseignantDaoImpl().toString(Quotas));
                }
            }
        }
        redirect(user);
    }

    //Permet de relever les informations relatives à la quantité prévue d'heures de cours attribuée à un groupe.
    public static void fonctionnaliteHeuresGroupe(User user){
        if(new UtilisateurDaoImpl().getAllGroupes() != null) {
            System.out.println(new UtilisateurDaoImpl().getAllGroupes());
            List<String> Groupes = new UtilisateurDaoImpl().getAllGroupes();
            Groupes.add("exit");
            String EntreeGroupe = entree("Quel groupe ? : ", Groupes);
            if (!EntreeGroupe.equals("exit")) {
                String EntreeSemaine = entreeSemaine();
                if (!EntreeSemaine.equals("exit")) {
                    List<QuotaGroupe> ListQGroupe = new QuotaGroupeDaoImpl().quotasByGroupeSemaine(EntreeGroupe, Integer.parseInt(EntreeSemaine));
                    if(ListQGroupe != null && !ListQGroupe.isEmpty()) {
                        System.out.println(new QuotaGroupeDaoImpl().toString(ListQGroupe));
                    }
                    else {
                        System.out.println("Aucunne données enregistrées :");
                    }
                }
            }
            redirect(user);
        }
        else {
            System.out.println("Pas de groupe enregistré");
            redirect(user);
        }
    }

    //Permet d'afficher tous les utilisateurs de la base.
    public static void fonctionnaliteAllUsers(User user){
        List<User> AllUsers = new UtilisateurDaoImpl().getAll();
        if(AllUsers != null && !AllUsers.isEmpty()) {
            for (User U :
                    AllUsers) {
                if(U != null) {
                    System.out.println(new UtilisateurDaoImpl().getNomUser(U.getId())
                            + " : " + U.getId() + " : " + U.getFonction());
                }
            }
        }
        redirect(user);
    }

    //Fonctionnalité permettant d'afficher tous les modules de la base
    public static void fonctionnaliteAllModules(User user){
        List<Module> AllModule = new ModuleDaoImpl().getAll();
        if(AllModule != null && !AllModule.isEmpty()) {
            for (Module M :
                    AllModule) {
                System.out.println(M.getId() + " : " + M.getNom());
            }
        }
        redirect(user);
    }

    //Fonctionnalité permettant d'obtenir les informations à propos d'un lieu dans la base.
    public static void fonctionnaliteLieu(User user){
        String EntreeLieu= entreLieu();
        if(!EntreeLieu.equals("exit")){
            Lieu LieuInforme = new LieuDaoImpl().get(EntreeLieu);
            System.out.println(new LieuDaoImpl().toString(LieuInforme));

        }
        redirect(user);
    }

    //Menu principal des utilisateurs admins, menu où ils doivent choisir la fonctionnalité voulue.
    public static void adminMain(User user){
        List<Fonctionnalite> FoncAdmin = new ArrayList<>();
        FoncAdmin.add(new Fonctionnalite(1, "Lecture de l'emploi du temps d'un utilisateur :"));
        FoncAdmin.add(new Fonctionnalite(2, "Lecture de l'emploi du temps d'un groupe :"));
        FoncAdmin.add(new Fonctionnalite(3, "Lecture des indisponibilités d'un enseignant :"));
        FoncAdmin.add(new Fonctionnalite(4, "Lecture des quantités d'heures attribuées à un professeur pour un module :"));
        FoncAdmin.add(new Fonctionnalite(5, "Lecture des quantités d'heures attribuées à un groupe :"));
        FoncAdmin.add(new Fonctionnalite(6, "Informations utilisateur :"));
        FoncAdmin.add(new Fonctionnalite(7, "Modules de cours :"));
        FoncAdmin.add(new Fonctionnalite(8, "Lecture informations lieux"));
        int Choix = choixFonctionnalite(FoncAdmin);
        switch (Choix){
            case 0 : {
                System.out.println("Vous quittez :");
            }break;
            case 1 : {
                fonctionnaListeCoursSemaineEnAdmin(user);
            }break;
            case 2 : {
                fonctionnaliteCoursGroupeEnAdmin(user);
            }break;
            case 3 : {
                fonctionnaliteIndispoSemaineEnAdmin(user);
            }break;
            case 4 : {
                fonctionnaliteHeuresEnseignantenAdmin(user);
            }break;
            case 5 : {
                fonctionnaliteHeuresGroupe(user);
            }break;
            case 6 : {
                fonctionnaliteAllUsers(user);
            }break;
            case 7 : {
                fonctionnaliteAllModules(user);
            }break;
            case 8 : {
                fonctionnaliteLieu(user);
            }break;
            default:
        }
    }

    //On dirige l'utilisateur vers le menu principal qu'il lui correspond, selon donc sa fonction
    //après vérification grâce à l'authentification.
    public static void application(User user){
        if(user != null) {
            switch (user.getFonction()) {
                case "ELV": {
                    eleveMain(user);
                }break;
                case "ENS": {
                    enseignantMain(user);
                }break;
                case "ADMIN": {
                    adminMain(user);
                }break;
            }
        }
    }

    public static void main(String[] args) {
        User user = authentification();
        application(user);
    }
}
