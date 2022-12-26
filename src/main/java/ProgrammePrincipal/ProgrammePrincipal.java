package ProgrammePrincipal;

import dao.UtilisateurDaoImpl;
import models.usersFactory.Utilisateur;

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

    public static Utilisateur Authentification(){
        Scanner console = new Scanner(System.in);
        String login;
        String mdp;
        Utilisateur user;
        do{
            System.out.println("Entrez votre login :");
            login = console.next();
            System.out.println("Entrez votre mot de passse :");
            mdp = console.next();
            user = new UtilisateurDaoImpl().getUtilisateurConnection(login, mdp);
        }while(!(user != null || mdp.equals("exit") || login.equals("exit")));
        return user;
    }


    public static void main(String[] args) {
        Utilisateur user = Authentification();

    }
}
