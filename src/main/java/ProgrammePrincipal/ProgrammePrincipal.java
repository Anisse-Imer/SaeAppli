package ProgrammePrincipal;

import dao.UtilisateurDaoImpl;
import models.usersFactory.User;

import java.util.*;

public class ProgrammePrincipal {

    public static String Entree(String Message, List<String> EntreesValables){
        Scanner console = new Scanner(System.in);
        String scan;
        do{
            System.out.println(Message);
            scan = console.next();
            System.out.println(scan);
        }while(!EntreesValables.contains(scan));
        return scan;
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

    public static void eleveMain(User user){
        System.out.println("eleveMain" + user.toString());
    }

    public static void enseignantMain(User user){
        System.out.println("enseignantMain" + user.toString());
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
