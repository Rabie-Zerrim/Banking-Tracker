package MyTests;
import Entities.Task;
import Services.SubCategoryServices;
import Services.TaskService;
import Entities.SubCategory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
public class Main {
    /* public static void main(String[] args) {
         Category category = new Category(1, "Test Category", 500, 200);
         SubCategory subCategory = new SubCategory(1, "Test SubCategory", 300, 0, category.getIdCategory());

         Date today = new Date(System.currentTimeMillis());
         Task task = new Task(1, "Test Task", 200, "medium", today, false, category, subCategory);
         //ToDoService ps = new ToDoService();
         TaskService ps = new TaskService();


         try {

             //ps.ajouter(new ToDo("aqwwwwwwwwwwwwwwwwwwwwwwwwwt","Pay Your Rent","Pending"));
             ps.ajouter(new Task("rent" ,200, "medium",today,false,new Category(),false));
             //  ps.ajouter(new Personne("simpson ","bart"));
             //ps.modifier(new Personne(1, "Zerrim","Rabie"));
             //ps.supprimer(1);
             //System.out.println(ps.recuperer());
         } catch (SQLException e) {
             e.printStackTrace();
         }


     }


 }*/
    public static void main(String[] args) {
        TaskService ps = new TaskService();
        LocalDate specificDate = LocalDate.of(2022, 1, 1);
        Date date = Date.valueOf(specificDate);
        try {
           //Date today = new Date(System.currentTimeMillis());

            // Create a new SubCategory object with required attributes
            //   SubCategory subCategory = new SubCategory();
            //subCategory.setIdSubCategory(3);
            //subCategory.setAmtAssigned(100.50);

            // Create a new Task object with required attributes


            //  Task newTask = new Task("pay mortgage ","medium",Date.valueOf(specificDate), true,3, subCategory);

           // Task newTask = new Task("pay mortgage ","medium",Date.valueOf(specificDate), true,1, subCategory);

            //ps.modifier(newTask);
            //ps.supprimer(12);
            //System.out.println(ps.recupererParPriorite("low"));
            System.out.println(ps.recuperer());

                // Insert the task into the database
          //     ps.ajouter(newTask);


            SubCategoryServices ss = new SubCategoryServices();
            System.out.println(ss.getAmount(4));
            System.out.println(ss.getAmount(1));







            } catch (SQLException e) {
                e.printStackTrace();
            }

            }}
