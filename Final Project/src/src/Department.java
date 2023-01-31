package src;

//Importing ArrayList
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Department {

    //Creating the attributes of Department
    private int id;
    private String description;
    private int dean;

    //The Teacher and Student ArrayLists of a Department
    public ObservableList<Teacher> tList = FXCollections.observableArrayList();
    public ObservableList<Student> sList = FXCollections.observableArrayList();
    public ObservableList<Staff> stList = FXCollections.observableArrayList();

    //Constructor
    public Department(int i, String D) {
        this.id = i;
        this.description = D;
    }

    public Department() {
    }

    //Setters
    public void setId(int i) {
        this.id = i;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDean(int newD) {
        this.dean = newD;
    }

    //Getters
    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public int getDean() {
        return this.dean;
    }
}
