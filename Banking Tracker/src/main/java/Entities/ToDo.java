package Entities;

import java.util.List;

public class ToDo
{

    private int idTodo;

    private String titleTodo;
    private String statusTodo;
    private double Progress ;
    private List<Task> tasks;
    public ToDo() {
    }

    public ToDo(int idTodo, String titleTodo, String statusTodo, double progress) {
        this.idTodo = idTodo;
        this.titleTodo = titleTodo;
        this.statusTodo = statusTodo;
        Progress = progress;
    }

    public ToDo(String titleTodo, String statusTodo, double progress) {
        this.titleTodo = titleTodo;
        this.statusTodo = statusTodo;
        Progress = progress;
    }

    public int getIdTodo() {
        return idTodo;
    }

    public void setIdTodo(int idTodo) {
        this.idTodo = idTodo;
    }

    public String getTitleTodo() {
        return titleTodo;
    }

    public void setTitleTodo(String titleTodo) {
        this.titleTodo = titleTodo;
    }

    public String getStatusTodo() {
        return statusTodo;
    }

    public void setStatusTodo(String statusTodo) {
        this.statusTodo = statusTodo;
    }

    public double getProgress() {
        return Progress;
    }

    public void setProgress(double progress) {
        Progress = progress;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "idTodo=" + idTodo +
                ", titleTodo='" + titleTodo + '\'' +
                ", statusTodo='" + statusTodo + '\'' +
                ", Progress=" + Progress +
                ", tasks=" + tasks +
                '}';
    }
}