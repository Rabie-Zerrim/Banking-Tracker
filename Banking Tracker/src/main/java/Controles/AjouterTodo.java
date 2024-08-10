package Controles;

import Entities.SubCategory;
import Entities.Task;
import Entities.ToDo;
import Services.SubCategoryServices;
import Services.TaskService;
import Services.ToDoService;
import com.google.api.client.util.DateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.security.GeneralSecurityException;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

public class AjouterTodo {

    @FXML
    private JFXComboBox<String> Subcat;

    @FXML
    private JFXComboBox<String> TodoList;

    @FXML
    private TextField desctask;

    @FXML
    private DatePicker duedate;

    @FXML
    private JFXComboBox<String> priority;
    @FXML

  private JFXCheckBox statusTask;
    private Runnable actionAfterEdit;
private int idtask ;
    Boolean isInEditMode = Boolean.FALSE;


  @FXML
    private TaskService taskService;

    private  SubCategoryServices subCategoryServices ;
   private ToDoService toDoService ;


List<SubCategory>  subCategories ;
    List<Task> tasks ;
    List<ToDo> todos ;

    public void setActionAfterEdit(Runnable actionAfterEdit) {
        this.actionAfterEdit = actionAfterEdit;
    }

    @FXML
    private void addtask(ActionEvent event) throws SQLException {
        Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        String taskDescription = desctask.getText().trim();

        if (taskDescription.isEmpty()) {
            showAlert("Please enter a task description.");
            return;
        }

        String taskPriority = priority.getValue();
        if (taskPriority == null || taskPriority.isEmpty()) {
            showAlert("Please choose a task priority.");
            return;
        }
        LocalDate localDate = duedate.getValue();
        if (localDate == null) {
            showAlert("Please choose a due date.");
            return;
        }
        if (localDate.isBefore(LocalDate.now())) {
            showAlert("Please enter a reasonable due date.");
            return;
        }
        DateTime startDate = new DateTime(localDate.toString() + "T00:00:00Z");

        // Assuming the task duration is one day, calculate the end date
        LocalDate endLocalDate = localDate.plusDays(1);
        DateTime endDate = new DateTime(endLocalDate.toString() + "T00:00:00Z");

        // Add event to Google Calendar
        try {
            GoogleCalendarService.addEvent(taskDescription, "Task Description", startDate, endDate);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace(); // Handle IOException and GeneralSecurityException
            showAlert("Error adding event to Google Calendar: " + e.getMessage());
            return; // Exit the method if an error occurs
        }
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date dueD = Date.from(instant);
        String statusTaskValue = String.valueOf(statusTask.isSelected());
        String namesubCategory = Subcat.getValue();
        if (namesubCategory == null || namesubCategory.isEmpty()) {
            showAlert("Please choose a subcategory.");
            return;
        }
        String nameTodo = TodoList.getValue();
        if (nameTodo == null || nameTodo.isEmpty()) {
            showAlert("Please choose a todo list.");
            return;
        }
      int idsubCategory = subCategoryServices.getsubcategoryIdByName(namesubCategory);
      int idToDo = toDoService.gettodoIdByName(nameTodo);
      double amount = subCategoryServices.getAmount(idsubCategory);
Date creationDate = new Date() ;
        java.sql.Date sqlDate = new java.sql.Date(dueD.getTime());
        java.sql.Date sqlcreationDate = new java.sql.Date(creationDate.getTime());


     Task task = new Task(taskDescription, sqlcreationDate ,amount,taskPriority, sqlDate,idToDo,statusTaskValue,idsubCategory);

     if (isInEditMode){
    handleEditOperation();
    return;
}
        try {

            taskService.ajouter(task);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Task added successfully.");
            alert.show();

            // Clear the form fields
getStage().close();



        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error adding task: " + e.getMessage());
            alert.show();
        }
    }









    public void handleEditOperation() throws SQLException {
        String taskDescription = desctask.getText();
        String taskPriority = priority.getValue();
        LocalDate localDate = duedate.getValue();
        String statusTaskValue = String.valueOf(statusTask.isSelected());
        String namesubCategory = Subcat.getValue();
        String nameTodo = TodoList.getValue();
        int idsubCategory = subCategoryServices.getsubcategoryIdByName(namesubCategory);
        int idToDo = toDoService.gettodoIdByName(nameTodo);
        double amount = subCategoryServices.getAmount(idsubCategory);
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date creationDate = new Date() ;
// Convert Instant to java.util.Date

        Date dueD = Date.from(instant);
        java.sql.Date sqlDate = new java.sql.Date(dueD.getTime());
        java.sql.Date sqlcreationDate = new java.sql.Date(creationDate.getTime());

        Task task = new Task(idtask,taskDescription,sqlcreationDate, amount,taskPriority, sqlDate,idToDo,statusTaskValue,idsubCategory);
        taskService.modifier(task);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Task Has been Modified.");
        alert.show();
        if (actionAfterEdit != null) {
            actionAfterEdit.run(); // Execute the action after editing the task
        }
    getStage().close();}
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
    @FXML
    private void initialize() throws SQLException  {
        toDoService = new ToDoService();
        taskService = new TaskService();
        subCategoryServices = new SubCategoryServices();
        tasks = taskService.recuperer()  ;
        todos = toDoService.recuperer()  ;
        subCategories = subCategoryServices.recuperer()  ;
      //  SubCategoryServices = new SubCategoryServices();
      //  subCategories = SubCategoryServices.recuperer() ;
       // Subcat.getItems().addAll(subCategories);
        // Use the gettodobyname method to retrieve the list of titleTodo values from the todolist table
        List<String> todoNames = toDoService.gettodobyname();
        List<String> subNames = subCategoryServices.getsubbyname();
        // Create a new ArrayList to store the ToDo objects
        List<ToDo> newTodos = new ArrayList<>();
        List<SubCategory> newSubs = new ArrayList<>();
        // Iterate through the list of titleTodo values
        for (String titleTodo : todoNames) {
            // Use the gettodoIdByName method to retrieve the idTodo of a ToDo object based on its titleTodo
            int idTodo = toDoService.gettodoIdByName(titleTodo);
        }
        for (String name : subNames) {
            int idSubCategory = subCategoryServices.getsubcategoryIdByName(name);

        }
        // Add the newTodos list to the todos list
        todos = newTodos;
        subCategories = newSubs;
        // Add the todos list to the TodoList combo box
        TodoList.getItems().addAll(toDoService.gettodobyname());
        Subcat.getItems().addAll(subCategoryServices.getsubbyname());
        priority.getItems().addAll(
                new String("High"),
                new String("Medium"),
                new String("Low")

                );
        statusTask.setSelected(true);

}
    public Stage getStage() {
        return (Stage) desctask.getScene().getWindow();
    }
    public void InfluateUi(Task task)  {
        idtask = task.getIdTask();
         desctask.setText(task.getDescriptionTask());
         priority.setValue(task.getPriority());
        String subname =  subCategoryServices.recupererById(task.getSubCategory()).getName();
        Subcat.setValue(subname);
        String todoname =  toDoService.SearchById(task.getIdTodo()).getTitleTodo();
        TodoList.setValue(todoname);
        isInEditMode = Boolean.TRUE;


    }
}