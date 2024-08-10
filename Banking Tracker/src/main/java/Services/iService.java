package Services;

import Entities.Task;

import java.sql.SQLException;
import java.util.List;

public interface iService<T> {




    void ajouter (T t) throws SQLException;
    void modifier (T t) throws SQLException;

    void supprimer (int id) throws SQLException;


    List<T> recuperer() throws SQLException;


   // Task recupererById(int idTask) throws SQLException;
}