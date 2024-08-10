package Services;

import Entities.SubCategory;
import Entities.Task;
import utilities.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryServices implements iService<SubCategory> {
    private Connection connection;

    public SubCategoryServices() {

        connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(SubCategory subCategory) throws SQLException {


    }

    @Override
    public void modifier(SubCategory subCategory) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<SubCategory> recuperer() throws SQLException {
        List<SubCategory> tasks = new ArrayList<>();
        String req = "SELECT * FROM SubCategory";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            SubCategory task = new SubCategory();
            task.setIdSubCategory(rs.getInt("idSubCategory"));
            task.setName(rs.getString("name"));
            task.setAmtSpent(rs.getDouble("AmtAssigned"));
            task.setAmtSpent(rs.getDouble("AmtSpent"));


            tasks.add(task);
        }
        return tasks;
    }

    public SubCategory recupererById(int idSub)  {
        String req = "SELECT * FROM subcategory WHERE idSubCategory = ?";

        SubCategory task = new SubCategory();

        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(1, idSub);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    task.setIdSubCategory(rs.getInt("idSubCategory"));
                    task.setName(rs.getString("name"));
                    task.setAmtSpent(rs.getDouble("AmtAssigned"));
                    task.setAmtSpent(rs.getDouble("AmtSpent"));
                } else {
                    System.out.println("No id found for name: " + idSub);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return task;    }
    public int getsubcategoryIdByName(String name) {
        String req = "SELECT idSubCategory FROM subcategory WHERE name = ?";
        int idSubCategory = -1;

        System.out.println("Getting id for subcat: " + name);

        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, name);

            try (ResultSet resultSet = st.executeQuery()) {
                if (resultSet.next()) {
                    idSubCategory = resultSet.getInt("idSubCategory");
                    System.out.println("idsubcat: " + idSubCategory);
                } else {
                    System.out.println("No id found for name: " + name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return idSubCategory;
    }

    public List<String> getsubbyname() {
        List<String> subcategoryNames = new ArrayList<>();
        String req = "SELECT name FROM subcategory";


        try (PreparedStatement st = connection.prepareStatement(req)) {
            ResultSet resultSet = st.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                subcategoryNames.add(name);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return subcategoryNames;
    }



    public double getAmount(int idSubCategory) {
        String req = "SELECT AmtAssigned FROM subcategory WHERE idSubCategory = ?";
        double amount=0;

        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(1, idSubCategory);

            try (ResultSet resultSet = st.executeQuery()) {
                if (resultSet.next()) {
                    amount = resultSet.getDouble("AmtAssigned");
                    System.out.println("amount: " + amount);
                } else {
                    System.out.println("No id found for id: " + idSubCategory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return amount;
    }

}
