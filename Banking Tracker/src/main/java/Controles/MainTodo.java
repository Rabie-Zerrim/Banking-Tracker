package Controles;

import Entities.Task;
import Services.TaskService;
import Services.ToDoService;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import java.util.List;
import java.sql.Date;

public class MainTodo {
    @FXML
    private TableColumn<Task, Double> AmountTab;
@FXML
    private TableColumn<Task, Date> creationDateTab;

    @FXML
    private TableColumn<Task, String> DescriptionTab;

    @FXML
    private TableColumn<Task, Date> DuedateTab;

    @FXML
    private TableColumn<Task, String> PriorityTab;
    @FXML
    private ChoiceBox<String> Listoption;
    @FXML
    private TableColumn<Task, String> StatusTab;

    @FXML
    private TableView<Task> TableTasks;

    @FXML
    private JFXButton addlistbutton;

    @FXML
    private JFXButton addtaskbutton;
    TaskService taskService ;
    @FXML
    void ManageListButton(ActionEvent event) {
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/views/AjouterList.fxml"));
        Parent root = null;
        try {
            root = loader3.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void addmaintask(ActionEvent event) throws Exception {
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/views/AjouterTodo.fxml"));
        Parent root = loader3.load();
        AjouterTodo ajouterTodo = loader3.getController();
        ToDoService todoService = new ToDoService();
        TaskService taskService = new TaskService();
        //added around 28/02/2024
        Listoption.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Get the ID of the selected list
                int selectedidTodo = todoService.gettodoIdByName(newValue);
                // Retrieve the list items for the selected list
                List<Task> taskItems = taskService.getAllTasks(selectedidTodo);


                // Populate the table with the retrieved list items
                try {

                    populateData(taskItems);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }

        });//newly added
        ajouterTodo.setActionAfterEdit(() -> {
            try {
                populateTable();
                //Update the table after adding the task
            } catch (SQLException e) {

                System.out.println(   e.getMessage());
                // Handle SQLException
            }
        });


        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();


    }

    @FXML
    void initialize() throws SQLException {
        setCells() ;
        populateTable() ;
        ToDoService toDoService = new ToDoService();
        List<String> todoNames = toDoService.gettodobyname();
        Listoption.getItems().addAll(todoNames);

        ToDoService todoService = new ToDoService();
            TaskService taskService = new TaskService();
        Listoption.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    // Get the ID of the selected list
                int selectedidTodo = todoService.gettodoIdByName(newValue);
                // Retrieve the wishlist items for the selected list
                List<Task> taskItems = taskService.getAllTasks(selectedidTodo);
                // Populate the table with the retrieved list items
                        setCells() ;
                    populateData(taskItems);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    private void populateTable() throws SQLException {
        TaskService taskService = new TaskService();

        List<Task> tasks = taskService.recuperer();

tasks.forEach((Task task) -> System.out.println(task.toString()));
        // Clear existing table data
        TableTasks.getItems().clear();
        // Add new tasks to the table
        ObservableList<Task> data = FXCollections.observableArrayList(tasks);
        TableTasks.setItems(data);


    }
    private void setCells(){
        // Set cell value factories for each column
        DescriptionTab.setCellValueFactory(new PropertyValueFactory<>("descriptionTask"));


        AmountTab.setCellValueFactory(new PropertyValueFactory<>("mtAPayer"));
        PriorityTab.setCellValueFactory(new PropertyValueFactory<>("priority"));
        StatusTab.setCellValueFactory(new PropertyValueFactory<>("statusTask"));
        DuedateTab.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        creationDateTab.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

    }
    private void populateData(List<Task> tasks) throws SQLException {


        // Clear existing table data
        TableTasks.getItems().clear();
        // Add new tasks to the table
        ObservableList<Task> data = FXCollections.observableArrayList(tasks);

        TableTasks.setItems(data);


    }

    @FXML
    void DeleteTask(ActionEvent event) {
        TaskService taskService1 = new TaskService();
        Task selectedTask = TableTasks.getSelectionModel().getSelectedItem();
        if(selectedTask == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a task.");
            alert.show();
            return;
        }
        try {
            taskService1.supprimer(selectedTask.getIdTask());
            TableTasks.getItems().remove(selectedTask); // Remove the selected task from the table
            TableTasks.refresh(); // Refresh the table to reflect the changes
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Task deleted successfully.");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error deleting task: " + e.getMessage());
            alert.show();
        }
    }




    @FXML
    void ModifyTask(ActionEvent event) throws IOException {
        Task selectedTask = TableTasks.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a task.");
            alert.show();
            return;
        }

        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/views/AjouterTodo.fxml"));
        Parent root = loader3.load();
        AjouterTodo ajouterTodo = loader3.getController();
        ajouterTodo.InfluateUi(selectedTask);

        // Set the action to be taken after the edit operation
        ajouterTodo.setActionAfterEdit(() -> {

            try {

               populateTable(); // Update the table after editing the task
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle SQLException
            }




        });



        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }





}
