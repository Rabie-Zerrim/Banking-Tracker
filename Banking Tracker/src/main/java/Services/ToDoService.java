package Services;
import Entities.SubCategory;
import Entities.Task;
import Entities.ToDo;
import utilities.MyDatabase;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class ToDoService implements iService<ToDo> {

    private Connection connection;

    public ToDoService() {

        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(ToDo todo) throws SQLException {
        String req = "INSERT INTO todolist (titleTodo, statusTodo, progress) VALUES ('" +
                todo.getTitleTodo() + "', '" +
                todo.getStatusTodo() + "', " +
                todo.getProgress() + ")";

        Statement st = connection.createStatement();
        st.executeUpdate(req);

        System.out.println("List Added Successfully");
    }


    @Override
    public void modifier(ToDo todo) throws SQLException {

        String req = "UPDATE todolist SET titleTodo = ?, statusTodo=? , progress=? idTodo = ? ";

        PreparedStatement ps = connection.prepareStatement(req);

        ps.setString(1,todo.getTitleTodo());

        ps.setString(2,todo.getStatusTodo());
        ps.setDouble(3,todo.getProgress());
        ps.setInt(4,todo.getIdTodo());

        int updatedRows = ps.executeUpdate();
        if (updatedRows > 0) {
            System.out.println("List updated successfully.");
        } else {
            System.out.println("No Update took place.");
        }

    }

    @Override
    public void supprimer(int idTodo) throws SQLException {

        String req = "Delete FROM todolist WHERE idTodo = ?";

        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1,idTodo);
        int deletedRows = ps.executeUpdate();
        if (deletedRows > 0) {
            System.out.println("List Deleted successfully.");
        } else {
            System.out.println("No List has been deleted.");
        }



    }

    public List<ToDo> recuperer() {
        List<ToDo> todos = new ArrayList<>();
        String req = "SELECT * FROM todolist";

        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(req);

            while (rs.next()) {
                ToDo todo = new ToDo();
                todo.setIdTodo(rs.getInt("idTodo"));
                todo.setStatusTodo(rs.getString("statusTodo"));
                todo.setTitleTodo(rs.getString("titleTodo"));
                todo.setProgress(rs.getDouble("progress"));

                todos.add(todo);
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }

        return todos;
    }

    public ToDo SearchById(int idTodo)  {
        String req = "SELECT * FROM todolist WHERE idTodo = ?";

        ToDo todo = new ToDo();

        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(1, idTodo);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    todo.setIdTodo(rs.getInt("idTodo"));
                    todo.setStatusTodo(rs.getString("statusTodo"));
                    todo.setTitleTodo(rs.getString("titleTodo"));
                    todo.setProgress(rs.getDouble("progress"));

                } else {
                    System.out.println("No id found for name: " + idTodo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return todo;     }
    public int gettodoIdByName(String titleTodo) {
        String req = "SELECT idTodo FROM todolist WHERE titleTodo = ?";
        int idTodo = -1;

        System.out.println("Getting idTodo for titleTodo: " + titleTodo);

        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, titleTodo);

            try (ResultSet resultSet = st.executeQuery()) {
                if (resultSet.next()) {
                    idTodo = resultSet.getInt("idTodo");
                    System.out.println("idTodo: " + idTodo);
                } else {
                    System.out.println("No idTodo found for titleTodo: " + titleTodo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return idTodo;
    }

    public List<String> gettodobyname() {
        List<String> todoNames = new ArrayList<>();
        String req = "SELECT titleTodo FROM todolist";


            try (PreparedStatement st = connection.prepareStatement(req)) {
                ResultSet resultSet = st.executeQuery();

                while (resultSet.next()) {
                    String name = resultSet.getString("titleTodo");
                    todoNames.add(name);
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return todoNames;
        }
}