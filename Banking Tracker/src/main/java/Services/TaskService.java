package Services;
import Entities.Task;
import utilities.MyDatabase;
import Entities.SubCategory;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;


public class TaskService implements iService<Task> {

    private Connection connection;

    public TaskService() {

        connection = MyDatabase.getInstance().getConnection();
    }


    public void ajouter(Task task) throws SQLException {

        // Now, proceed with inserting the task into the database
        String req = "INSERT INTO task (descriptionTask, mtAPayer, priority, dueDate, statusTask, creationDate ,idTodo, idSubCategory) " +
                "SELECT ?, ?, ?, ?, ?, ?, ?, ? ";

        PreparedStatement ps = connection.prepareStatement(req);

        ps.setString(1, task.getDescriptionTask());
        ps.setDouble(2, task.getMtAPayer());
        ps.setString(3, task.getPriority());
        ps.setDate(4, task.getDueDate());
        ps.setString(5, task.isStatusTask());
        ps.setDate(6, task.getCreationDate());

        ps.setInt(7, task.getIdTodo());
        ps.setInt(8, task.getSubCategory());

        ps.executeUpdate();

    }



    public void modifier(Task task) throws SQLException {

        String req = "UPDATE task " +
                "SET descriptionTask = ?, mtAPayer = ?, priority = ?, dueDate = ?, statusTask = ?, creationDate=? ,idTodo=?,idSubCategory=? " +
                "WHERE idTask = ?";

        PreparedStatement ps = connection.prepareStatement(req);

        ps.setString(1, task.getDescriptionTask());
        ps.setDouble(2, task.getMtAPayer());
        ps.setString(3, task.getPriority());
        ps.setDate(4, task.getDueDate());
        ps.setString(5, task.isStatusTask());
        ps.setDate(6, task.getCreationDate());

        ps.setInt(7, task.getIdTodo());

        ps.setInt(8, task.getSubCategory());
        ps.setInt(9, task.getIdTask());

        ps.executeUpdate();
        System.out.println("done with modification"+ ps.toString());
    }
    @Override
    public void supprimer(int idTask) throws SQLException {

        String req = "Delete FROM task WHERE idTask = ?";

        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, idTask);
        int deletedRows = ps.executeUpdate();
        if (deletedRows > 0) {
            System.out.println("Task Deleted successfully.");
        } else {
            System.out.println("No Task has been deleted.");
        }


    }


    @Override
    public List<Task> recuperer() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String req = "SELECT * FROM task";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Task task = new Task();
            task.setIdTask(rs.getInt("idTask"));
            task.setDescriptionTask(rs.getString("descriptionTask"));
            System.out.println("the description is "+ task.getDescriptionTask());
            task.setMtAPayer(rs.getInt("mtAPayer"));
            task.setPriority(rs.getString("priority"));
            task.setDueDate(rs.getDate("dueDate"));
            task.setStatusTask(rs.getString("statusTask"));
            task.setCreationDate(rs.getDate("creationDate"));
            System.out.println("the creation date is " + rs.getString("creationDate"));
            task.setIdTodo(rs.getInt("idTodo"));
            task.setSubCategory(rs.getInt("idSubCategory"));

            tasks.add(task);
        }
        return tasks;
    }

    public Task recupererById(int idTask) throws SQLException {
        return null;
    }
    public List<Task> recupererParPriorite(String priorite) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String req = "SELECT * FROM task WHERE priority = ?";

        PreparedStatement pst = connection.prepareStatement(req);
        pst.setString(1, priorite);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Task task = new Task();
            task.setIdTask(rs.getInt("idTask"));
            task.setDescriptionTask(rs.getString("descriptionTask"));
            task.setMtAPayer(rs.getInt("mtAPayer"));
            task.setPriority(rs.getString("priority"));
            task.setStatusTask(rs.getString("statusTask"));
            task.setDueDate(rs.getDate("dueDate"));
            task.setIdTodo(rs.getInt("idTodo"));

            tasks.add(task);
        }

        return tasks;
    }

    private List<SubCategory> getSubCategoriesById(int idSubCategory) throws SQLException {
        List<SubCategory> subCategories = new ArrayList<>();
        String req = "SELECT * FROM subcategory WHERE idSubCategory = ?";

        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, idSubCategory);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            SubCategory subCategory = new SubCategory(
                    rs.getInt("idSubCategory"),
                    rs.getString("name"),
                    rs.getDouble("amtAssigned"),
                    rs.getDouble("amtSpent")

            );
            subCategories.add(subCategory);
        }

        return subCategories;
    }

    public List<Task> getAllTasks(int todoId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM task WHERE idTodo=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, todoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setIdTask(rs.getInt("idTask"));
                task.setDescriptionTask(rs.getString("descriptionTask"));
                task.setMtAPayer(rs.getDouble("mtAPayer"));
                task.setPriority(rs.getString("priority"));
                task.setStatusTask(rs.getString("statusTask"));
                task.setDueDate(rs.getDate("dueDate"));
                task.setCreationDate(rs.getDate("creationDate"));

                task.setIdTodo(rs.getInt("idTodo"));

                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }
    public List<Task> getTasks(int idTodo) {
        List<Task> tasks = new ArrayList<>();

        String query = "SELECT * FROM task WHERE idTodo = ? AND statusTask = 'true' ORDER BY dueDate;";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idTodo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setIdTask(rs.getInt("idTask"));
                task.setDescriptionTask(rs.getString("descriptionTask"));
                task.setMtAPayer(rs.getDouble("mtAPayer"));
                task.setPriority(rs.getString("priority"));
                task.setStatusTask(rs.getString("statusTask"));
                task.setDueDate(rs.getDate("dueDate"));
                task.setCreationDate(rs.getDate("creationDate"));

                task.setIdTodo(rs.getInt("idTodo"));

                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;

    }
    public int gettodoId(Task selectedTask) throws SQLException {
        int taskId = selectedTask.getIdTask(); // Assuming you have a method to get the task ID
        int todoId = -1; // Default value if no todoId is found

        // SQL query to retrieve todoId based on taskId
        String query = "SELECT todoId FROM tasks WHERE taskId = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, taskId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                todoId = resultSet.getInt("todoId");
            }
        }

        return todoId;
    }
    public int getTasksCount(int todoId) throws SQLException {
        int tasksCount = 0;
        String query = "SELECT COUNT(*) FROM task WHERE idTodo  = ?";

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, todoId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    tasksCount = resultSet.getInt(1);
                }
            }
        }
        return tasksCount;
    }
    public int getCompletedTasksCount(int todoId) throws SQLException {
        int completedTasksCount = 0;
        String query = "SELECT COUNT(*) FROM task WHERE idTodo  = ? AND statusTask = 'true'";

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, todoId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    completedTasksCount = resultSet.getInt(1);
                }
            }
        }
        return completedTasksCount;
    }
}










