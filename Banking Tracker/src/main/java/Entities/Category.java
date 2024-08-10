package Entities;

public class Category {
    private int idCategory;
    private String nameCategory;
    private double totalAmtAssigned;
    private double totalAmtSpent;

    public Category(int idCategory, String nameCategory, double totalAmtAssigned, double totalAmtSpent) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this.totalAmtAssigned = totalAmtAssigned;
        this.totalAmtSpent = totalAmtSpent;
    }

    public Category(String nameCategory, double totalAmtAssigned, double totalAmtSpent) {

        this.nameCategory = nameCategory;
        this.totalAmtAssigned = totalAmtAssigned;
        this.totalAmtSpent = totalAmtSpent;
    }

    public Category() {


    }

    public int getIdCategory() {
        return idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public double getTotalAmtAssigned() {
        return totalAmtAssigned;
    }

    public double getTotalAmtSpent() {
        return totalAmtSpent;
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory=" + idCategory +
                ", nameCategory='" + nameCategory + '\'' +
                ", totalAmtAssigned=" + totalAmtAssigned +
                ", totalAmtSpent=" + totalAmtSpent +
                '}';
    }
}