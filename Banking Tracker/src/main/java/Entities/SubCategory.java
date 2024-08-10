package Entities;

public class SubCategory {
    private int idSubCategory;
    private String name;
    private double amtAssigned;
    private double amtSpent;


    public SubCategory(int idSubCategory, String name, double amtAssigned, double amtSpent) {
        this.idSubCategory = idSubCategory;
        this.name = name;
        this.amtAssigned = amtAssigned;
        this.amtSpent = amtSpent;

    }

    public SubCategory( String name, double amtAssigned, double amtSpent) {

        this.name = name;
        this.amtAssigned = amtAssigned;
        this.amtSpent = amtSpent;

    }

    public SubCategory() {
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "idSubCategory=" + idSubCategory +
                ", name='" + name + '\'' +
                ", amtAssigned=" + amtAssigned +
                ", amtSpent=" + amtSpent +

                '}';
    }

    public int getIdSubCategory() {
        return idSubCategory;
    }

    public void setIdSubCategory(int idSubCategory) {
        this.idSubCategory = idSubCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmtAssigned() {
        return amtAssigned;
    }

    public void setAmtAssigned(double amtAssigned) {
        this.amtAssigned = amtAssigned;
    }

    public double getAmtSpent() {
        return amtSpent;
    }

    public void setAmtSpent(double amtSpent) {
        this.amtSpent = amtSpent;
    }
}