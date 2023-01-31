package src;

//Importing Scanner and ArrayList
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Menu {

    //Creating the ArrayLists that hold all the Department objects, Teacher objects, and Student objects respectively.
    public static ObservableList<Department> allD = FXCollections.observableArrayList();
    public static ObservableList<Teacher> allT = FXCollections.observableArrayList();
    public static ObservableList<Student> allS = FXCollections.observableArrayList();
    public static ObservableList<Staff> allSt = FXCollections.observableArrayList();

    //Creating a Department object, Teacher object, and Student object respectively.
    public static Department d;
    public static Teacher t;
    public static Student s;
    public static Staff st;

    //Initial Value variables
    public static boolean dIV = false;
    public static boolean tIV = false;
    public static boolean sIV = false;
    public static boolean stIV = false;

    //Creating the variables specific to Department
    public static int dId;

    //Creating the variables for both Teachers and Students
    public static int pId;

    //Paths for the initial values
    public static String dPath = "C:\\Users\\natan\\OneDrive - Vanier College\\Documents\\NetBeansProjects\\Assinment3 Final\\Assignment 3 Text Files\\Departments.txt";
    public static String tPath = "C:\\Users\\natan\\OneDrive - Vanier College\\Documents\\NetBeansProjects\\Assinment3 Final\\Assignment 3 Text Files\\Teachers.txt";
    public static String sPath = "C:\\Users\\natan\\OneDrive - Vanier College\\Documents\\NetBeansProjects\\Assinment3 Final\\Assignment 3 Text Files\\Students.txt";
    public static String stPath = "C:\\Users\\natan\\OneDrive - Vanier College\\Documents\\NetBeansProjects\\Assinment3 Final\\Assignment 3 Text Files\\Staff.txt";

    public static boolean uniqueId; //This boolean variable is used when checking to see if an ID is unique or not
    public static boolean idExists; //This boolean variable is used when checking to see if an ID exists or not.
}
