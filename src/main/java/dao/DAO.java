package dao;
import java.util.List;

//Interface basique, elle ne contient pas de get puisque certain DAO prennent en ID des "String".
public interface DAO<T>{

    //Obtient tous les T de la base.
    List<T> getAll();

    //Enregistre T dans la base.
    void save(T t);

    //Met à jour T dans la base selon son id et les informations contenues dans l'objet.
    void update(T t);

    //Supprime t de la base selon id.
    int delete(T t);
}
