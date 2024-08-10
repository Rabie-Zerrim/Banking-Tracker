package Entities;

import java.sql.Date;


public class Task {
    private int idTask;
    private String descriptionTask;
    private Date creationDate;

    private double mtAPayer;
    private String priority;
    private Date dueDate;
    private int idTodo;
    private String statusTask;
    private int subCategory;

    public Task(String descriptionTask, Date creationDate ,double mtAPayer, String priority, Date dueDate, int idTodo, String statusTask, int subCategory) {
        this.descriptionTask = descriptionTask;
        this.creationDate= creationDate;
        this.mtAPayer = mtAPayer;
        this.priority = priority;
        this.dueDate = dueDate;
        this.idTodo = idTodo;
        this.statusTask = statusTask;
        this.subCategory = subCategory;
    }

    public Task(int idTask, String descriptionTask,Date creationDate, double mtAPayer, String priority, Date dueDate, int idTodo, String statusTask, int subCategory) {
        this.idTask = idTask;
        this.descriptionTask = descriptionTask;
        this.creationDate = creationDate;
        this.mtAPayer = mtAPayer;
        this.priority = priority;
        this.dueDate = dueDate;
        this.idTodo = idTodo;
        this.statusTask = statusTask;
        this.subCategory = subCategory;
    }

    public Task() {
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    public double getMtAPayer() {
        return mtAPayer;
    }

    public void setMtAPayer(double mtAPayer) {
        this.mtAPayer = mtAPayer;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getIdTodo() {
        return idTodo;
    }

    public void setIdTodo(int idTodo) {
        this.idTodo = idTodo;
    }

    public String isStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", creationDate=" + creationDate +
                ", descriptionTask='" + descriptionTask + '\'' +
                ", mtAPayer=" + mtAPayer +
                ", priority='" + priority + '\'' +
                ", dueDate=" + dueDate +
                ", idTodo=" + idTodo +
                ", statusTask='" + statusTask + '\'' +
                ", subCategory=" + subCategory +
                '}';
    }
}