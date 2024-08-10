package Controles;

import Entities.Task;
import Entities.ToDo;
import Services.TaskService;
import Services.ToDoService;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;


import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AjouterList {
    @FXML
    private Button AddBu;
    @FXML
    private Button ModifyBu;

    @FXML
    private TableColumn<ToDo, String> DescriptionTab;

    @FXML
    private TableColumn<ToDo, String> StatusTab;
    @FXML
    private TableColumn<ToDo,Double> ProgressTab;

    @FXML
    private TableView<ToDo> TableList;
    @FXML
    private TextField nameTodolist;

    @FXML
    private JFXComboBox<String> statusList;



    private ToDoService toDoService ;
    @FXML
    void DeleteList(ActionEvent event) {
        ToDo selectedTask = TableList.getSelectionModel().getSelectedItem();
        try {
            if (selectedTask == null) {
                showAlert("Please choose a valid list to delete.");
                return; // Exit method
            }
            toDoService.supprimer(selectedTask.getIdTodo());
            populateTable(); // Update the table after adding the item
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("To Do List Deleted successfully.");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error adding task: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    void ModifyList(ActionEvent event) {
        String todoDescription = nameTodolist.getText().trim();;
        String todoPriority = statusList.getValue();
        ToDo selectedTask = TableList.getSelectionModel().getSelectedItem();
        if (isListNameExists(todoDescription)) {
            showAlert("List name already exists. Please enter a unique list name.");
            return; // Exit method
        }

        if (selectedTask == null) {
            showAlert("Please choose a list to edit.");
            return; // Exit method
        }

        nameTodolist.setText(selectedTask.getTitleTodo());
        AddBu.setVisible(false);
        ModifyBu.setVisible(true);

    }

    @FXML
    void AjouterList(ActionEvent event) {
        String todoDescription = nameTodolist.getText().trim();;
        String todoPriority = statusList.getValue();

        ToDo todolist = new ToDo(todoDescription, todoPriority ,0);

        try {
            if (isListNameExists(todoDescription)) {
                showAlert("List name already exists. Please enter a unique list name.");
                return; // Exit method
            }
            if (todoDescription.isEmpty()) {
                showAlert("Please enter a valid list name.");
                return; // Exit method
            }
            if (todoPriority == null || todoPriority.isEmpty()) {
                showAlert("Please choose a status for the list.");
                return; // Exit method
            }
            toDoService.ajouter(todolist);
            populateTable(); // Update the table after adding the item
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("To Do List added successfully.");
            alert.show();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error adding task: " + e.getMessage());
            alert.show();
        }
    }
    private boolean isListNameExists(String listName) {
        // Get the list of existing tasks
        List<ToDo> existingTasks = TableList.getItems();

        // Check if the entered list name already exists in the existing tasks
        for (ToDo task : existingTasks) {
            if (task.getTitleTodo().equalsIgnoreCase(listName)) {
                return true;
            }
        }
        return false;
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
    @FXML
    void ModifyValue(ActionEvent event) {
        ToDo selectedTask = TableList.getSelectionModel().getSelectedItem();

        String todoDescription = nameTodolist.getText();
        String todoPriority = statusList.getValue();
        ToDo todolist = new ToDo(selectedTask.getIdTodo(), todoDescription, todoPriority , 0);
        try {

            toDoService.modifier(todolist);
            populateTable(); // Update the table after adding the item
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("To Do List added successfully.");
            alert.show();
            AddBu.setVisible(true);
            ModifyBu.setVisible(false);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error adding task: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    void initialize() {
        toDoService = new ToDoService();
        populateTable(); // Populate table on initialization
        statusList.getItems().addAll("To Do", "Done");
        ModifyBu.setVisible(false);

    }


    private void populateTable() {
        ToDoService toDoService = new ToDoService();
        TaskService taskService = new TaskService();
        // Retrieve todos list
        List<ToDo> todos = toDoService.recuperer();
// Populate table
        ObservableList<ToDo> data = FXCollections.observableArrayList(todos);
        TableList.setItems(data);

// Set cell value factory for description and status columns
        DescriptionTab.setCellValueFactory(new PropertyValueFactory<>("titleTodo"));
        StatusTab.setCellValueFactory(new PropertyValueFactory<>("statusTodo"));

/* Set cell factory for progress column
        ProgressTab.setCellValueFactory(new PropertyValueFactory<>("progress"));
        ProgressTab.setCellFactory(column -> {
            return new TableCell<ToDo, Double>() {
                private ProgressBar progressBar = new ProgressBar();
                private Label label = new Label();

                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(new HBox(progressBar, label));
                }

                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(new HBox(progressBar, label));
                        progressBar.setProgress(item /100 ); // Convert progress value to range between 0 and 1
                        label.setText(String.format("%.0f%%", item)); // Display percentage text
                    }
                }
            };
        });*/
        ProgressTab.setCellValueFactory(new PropertyValueFactory<>("progress"));
        ProgressTab.setCellFactory(ProgressBarTableCell.forTableColumn());
        for (ToDo item : data) {
            double progress = item.getProgress();
            item.setProgress(progress / 100.0);
        }

        String red = "#FF6347";
        String orange = "#FFA500";
        String green = "#32CD32";
        ProgressTab.setCellFactory(column -> new TableCell<>() {
            private final StackPane stackPane = new StackPane();
            private final ProgressBar progressBar = new ProgressBar();
            private final Label progressLabel = new Label();
            {
                // Set the width of the progress bar
                progressBar.setPrefWidth(100);
                progressBar.setPrefHeight(25); // Adjust height as needed
                // Apply the custom style
                progressBar.getStyleClass().add("custom-progress-bar");
                progressLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: grey;");

                stackPane.getChildren().addAll(progressBar, progressLabel);


                // Set the text to display the progress value as a percentage
                progressBar.setTooltip(new Tooltip());
                progressBar.progressProperty().addListener((observable, oldValue, newValue) -> {
                    double value = newValue.doubleValue();
                    // Set the progress bar color based on the progress value
                    if (value < 0.3) {
                        setProgressBarColor(progressBar, red); // Red color
                    } else if (value < 0.7) {
                        setProgressBarColor(progressBar, orange); // Orange color
                    } else {
                        setProgressBarColor(progressBar, green); // Green color
                    }
                    // Update the tooltip to display the progress value as a percentage
                    progressBar.getTooltip().setText(String.format("%.0f%%", value * 100));

                    // Update the label text with the progress value
                    progressLabel.setText(String.format("%.0f%%", value * 100));
                });
                // Add hover functionality to display estimate purchase date
                stackPane.setOnMouseEntered(event -> {
                    ToDo item = (ToDo) getTableRow().getItem();
                    List<Task> taskList = taskService.getTasks(item.getIdTodo());
                    if (taskList.isEmpty()) {
                        progressBar.getTooltip().setText("Not Started yet");
                    } else {
                        Task t1 = taskList.get(0);
                        Task G2 = taskList.get(taskList.size() - 1);
                        long durationMillis = G2.getDueDate().getTime() - t1.getCreationDate().getTime();
                        long days = TimeUnit.MILLISECONDS.toDays(durationMillis);
                        long hours = TimeUnit.MILLISECONDS.toHours(durationMillis) % 24;
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60;
                        long seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis) % 60;
                        double progressValue = progressBar.getProgress();

                        if (progressValue < 1.0) {
                            progressBar.getTooltip().setText("Not Done yet");
                        } else {
                            String durationString = String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
                            progressBar.getTooltip().setText("Task Done In: " + durationString);
                        }
                    }
                });
                stackPane.setOnMouseExited(event -> {
                    progressBar.getTooltip().setText(""); // Clear tooltip when mouse exits
                });
            }
            @Override
            protected void updateItem(Double progress, boolean empty) {
                super.updateItem(progress, empty);

                if (empty || progress == null) {
                    setGraphic(null);
                } else {
                    progressBar.setProgress(progress);
                    setGraphic(stackPane);
                }
            }
            private void setProgressBarColor(ProgressBar progressBar, String color) {
                String css = String.format("-fx-accent: %s;", color);
                progressBar.setStyle(css);
            }
        });
    }
}
