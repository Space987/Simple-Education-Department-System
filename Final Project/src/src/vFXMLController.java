package src;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class vFXMLController extends Menu implements Initializable {

    // Main table 
    @FXML
    private TableView<Teacher> vTeachers;

    // Textfields 
    @FXML
    private TextField vIdTf;
    @FXML
    private Button vEnterBtn;
    @FXML
    private Button vBackBtn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Columns 
    @FXML
    private TableColumn<Teacher, Integer> vTIdCol;
    @FXML
    private TableColumn<Teacher, String> vTNameCol;
    @FXML
    private TableColumn<Teacher, Integer> vTAgeCol;
    @FXML
    private TableColumn<Teacher, String> vTGenderCol;
    @FXML
    private TableColumn<Teacher, String> vTDegreeCol;
    @FXML
    private TableColumn<Teacher, String> vTSpecialtyCol;
    @FXML
    private TableColumn<Teacher, Double> vTPayCol;
    @FXML
    private TableColumn<Teacher, Integer> vTDeptCol;

    // Main table 
    @FXML
    private TableView<Staff> vStaff;

    // Columns 
    @FXML
    private TableColumn<Staff, Integer> vStIdCol;
    @FXML
    private TableColumn<Staff, String> vStNameCol;
    @FXML
    private TableColumn<Staff, Integer> vStAgeCol;
    @FXML
    private TableColumn<Staff, String> vStGenderCol;
    @FXML
    private TableColumn<Staff, String> vStDutyCol;
    @FXML
    private TableColumn<Staff, Integer> vStWorkloadCol;
    @FXML
    private TableColumn<Staff, Double> vStPayCol;
    @FXML
    private TableColumn<Staff, Integer> vStDeptCol;

    // Main table 
    @FXML
    private TableView<Student> vStudents;

    // Columns 
    @FXML
    private TableColumn<Student, Integer> vSIdCol;
    @FXML
    private TableColumn<Student, String> vSNameCol;
    @FXML
    private TableColumn<Student, Integer> vSAgeCol;
    @FXML
    private TableColumn<Student, String> vSGenderCol;
    @FXML
    private TableColumn<Student, String> vSCourseCol;
    @FXML
    private TableColumn<Student, Integer> vSSemesterCol;
    @FXML
    private TableColumn<Student, Integer> vSDeptCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    // This is a FXML action that holds all of the actions that happens when a user clicks a button
    @FXML
    public void chooseAction(ActionEvent event) {
        // This calls the toMenu() mehtod if the sBackBtn is clicked 
        if (event.getSource() == vBackBtn) {
            toMenu();
        }
        // This calls the insert teacher, Staff and Student into the different tables
        if (event.getSource() == vEnterBtn) {
            DepExist();
            InsertTeachers();
            InsertStaff();
            InsertStudents();
        }
    }

    // This method makes it like that the scene is changed back to the main menu
    public void toMenu() {
        try {
            root = FXMLLoader.load(getClass().getResource("menuFXML.fxml"));
            stage = (Stage) ((Node) vBackBtn).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    // This displays all of the teachers in the array 
    public void InsertTeachers() {
        ObservableList<Teacher> tempT = FXCollections.observableArrayList();
        for (int i = 0; i < allT.size(); i++) {
            if (allT.get(i).getDepId() == Integer.parseInt(vIdTf.getText())) {
                tempT.add(allT.get(i));
            }
        }
        addT(tempT);
    }

    // This displays all of the Staff in the array 
    public void InsertStaff() {
        ObservableList<Staff> tempSt = FXCollections.observableArrayList();
        for (int i = 0; i < allSt.size(); i++) {
            if (allSt.get(i).getDepId() == Integer.parseInt(vIdTf.getText())) {
                tempSt.add(allSt.get(i));
            }
        }
        addSt(tempSt);
    }

    // This displays all of the Student in the array 
    public void InsertStudents() {
        ObservableList<Student> tempS = FXCollections.observableArrayList();
        for (int i = 0; i < allS.size(); i++) {
            if (allS.get(i).getDepId() == Integer.parseInt(vIdTf.getText())) {
                tempS.add(allS.get(i));
            }
        }
        addS(tempS);
    }

    // This method makes sure that the department exist
    public boolean DepExist() {
        dId = Integer.parseInt(vIdTf.getText());
        idExists = false;
        while (idExists == false) {
            for (int b = 0; b < allD.size(); b++) {
                if (dId == allD.get(b).getId()) {
                    idExists = true;
                    allD.get(b).tList.add(t);
                }
            }
            if (idExists == false) {
                // If the Departent does not exist it throws an exceptions to stop the program
                throw new NumberFormatException("ERROR: Department not found.");
            }
        }
        return true;
    }

    // Puts the values of the arraylist into the columns
    public void addT(ObservableList<Teacher> arr) {
        vTIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        vTNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        vTGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        vTAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        vTDegreeCol.setCellValueFactory(new PropertyValueFactory<>("degree"));
        vTSpecialtyCol.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        vTPayCol.setCellValueFactory(new PropertyValueFactory<>("pay"));
        vTDeptCol.setCellValueFactory(new PropertyValueFactory<>("depId"));
        vTeachers.setItems(arr);
    }

    // Puts the values of the arraylist into the columns
    public void addSt(ObservableList<Staff> arr) {
        vStIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        vStNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        vStGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        vStAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        vStDutyCol.setCellValueFactory(new PropertyValueFactory<>("duty"));
        vStWorkloadCol.setCellValueFactory(new PropertyValueFactory<>("workload"));
        vStPayCol.setCellValueFactory(new PropertyValueFactory<>("pay"));
        vStDeptCol.setCellValueFactory(new PropertyValueFactory<>("depId"));
        vStaff.setItems(arr);
    }

    // Puts the values of the arraylist into the columns
    public void addS(ObservableList<Student> arr) {
        vSIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        vSNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        vSGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        vSAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        vSCourseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        vSSemesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        vSDeptCol.setCellValueFactory(new PropertyValueFactory<>("depId"));
        vStudents.setItems(arr);
    }
}
