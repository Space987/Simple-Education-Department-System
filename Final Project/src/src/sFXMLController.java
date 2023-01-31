package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.ResourceBundle;
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

public class sFXMLController extends Menu implements Initializable {

    // Main table 
    @FXML
    private TableView<Student> AllStudents;

    // Textfields 
    @FXML
    private TextField sIdTf;
    @FXML
    private TextField sNameTf;
    @FXML
    private TextField sAgeTf;
    @FXML
    private TextField sGenderTf;
    @FXML
    private TextField sCourseTf;
    @FXML
    private TextField sSemesterTf;
    @FXML
    private TextField sDeptTf;

    // Columns  
    @FXML
    private TableColumn<Student, Integer> sIdCol;
    @FXML
    private TableColumn<Student, String> sNameCol;
    @FXML
    private TableColumn<Student, Integer> sAgeCol;
    @FXML
    private TableColumn<Student, String> sGenderCol;
    @FXML
    private TableColumn<Student, String> sCourseCol;
    @FXML
    private TableColumn<Student, String> sSemesterCol;
    @FXML
    private TableColumn<Student, Integer> sDeptCol;

    // Buttons
    @FXML
    private Button sDeleteBtn;
    @FXML
    private Button sUpdateBtn;
    @FXML
    private Button sSearchBtn;
    @FXML
    private Button sBackBtn;
    @FXML
    private Button sInsButton;
    @FXML
    private Button sLoadBtn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // This method is used to initialize all of the objects inside of the text file
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sLoadBtn.fire();
    }

    // This is a FXML action that holds all of the actions that happens when a user clicks a button
    @FXML
    private void chooseAction(ActionEvent event) {

        // This calls the sfirstValues() method if the sLoadBtn is clicked 
        if (event.getSource() == sLoadBtn) {
            // This makes sure that the buttin is only fired once
            if (sIV == false) {
                sfirstValues();
            }
            // This adds it into the observable arraylist in the menu. 
            addS(allS);
        }
        // This calls the toMenu() mehtod if the sBackBtn is clicked 
        if (event.getSource() == sBackBtn) {
            toMenu();
        }
        //This calls the InsertStudent method
        if (event.getSource() == sInsButton) {
            // This makes sure that the ID is unique before adding anything
            if (uniqueCheck() == true && DepExist() == true) {
                // Creates a string and adds the elements to it
                String D = "\n" + Integer.parseInt(sIdTf.getText()) + ", " + sNameTf.getText()
                        + ", " + Integer.parseInt(sAgeTf.getText()) + ", " + sGenderTf.getText() + ", " + sCourseTf.getText() + ", "
                        + Integer.parseInt(sSemesterTf.getText()) + ", " + Integer.parseInt(sDeptTf.getText());
                // Calls the InsertStudent() method
                InsertStudent();
                // Calls the toFile() method with "D" as its parameter
                toFile(D);
                // Adds it into the observable arraylist
                addS(allS);
            }
        }
        // This calls the DeleteDepartment()and overWrite() method if the dDeleteBtn is clicked
        if (event.getSource() == sDeleteBtn) {
            DeleteStudent();
            overWrite();
        }
        // This calls the SearchStudent() method if the sSearchBtn is clicked
        if (event.getSource() == sSearchBtn) {
            SearchStudent();
        }
        // This calls the UpdateStudent() and overWrite() method if the sUpdateBtn is clicked
        if (event.getSource() == sUpdateBtn) {
            UpdateStudent();
            overWrite();
        }
    }

    // This method makes it like that the scene is changed back to the main menu
    public void toMenu() {
        try {
            root = FXMLLoader.load(getClass().getResource("menuFXML.fxml"));
            stage = (Stage) ((Node) sBackBtn).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    // This makes sure that all of the columns have the proper values inside of them
    public void addS(ObservableList<Student> arr) {
        sIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        sNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        sGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        sCourseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        sSemesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        sDeptCol.setCellValueFactory(new PropertyValueFactory<>("depId"));
        AllStudents.setItems(arr);
    }

    // This method reads the text file and adds it into the arraylist 
    public void sfirstValues() {

        if (sIV == false) {
            try {
                RandomAccessFile file = new RandomAccessFile(sPath, "r");
                String n;
                // This loops the file until there is no more lines left
                while ((n = file.readLine()) != null) {
                    String stu[] = n.split(", ");
                    s = new Student(Integer.parseInt(stu[0]), stu[1], Integer.parseInt(stu[2]), stu[3], stu[4], Integer.parseInt(stu[5]), Integer.parseInt(stu[6]));
                    allS.add(s);
                    for (int i = 0; i < allD.size(); i++) {
                        if (allD.get(i).getId() == s.getDepId()) {
                            allD.get(i).sList.add(s);
                        }
                    }
                }
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
            // Changes the value like that the button dosent get fired again
            sIV = true;
        }
    }

    // This method makes sure that the ID is unique and has not been used before 
    public boolean uniqueCheck() {
        pId = Integer.parseInt(sIdTf.getText());
        uniqueId = false;

        while (uniqueId == false) {

            boolean match = false;
            for (int a = 0; a < allS.size(); a++) {
                if (allS.get(a).getId() == pId) {
                    match = true;
                }
            }
            if (match == false) {
                uniqueId = true;
            } else {
                throw new NumberFormatException("ERROR: Student ID already found.");

            }
        }
        return true;
    }

    // This adds the new elements that the user has put in into the observable arralyist
    public void InsertStudent() {

        s = new Student(Integer.parseInt(sIdTf.getText()), sNameTf.getText(),
                Integer.parseInt(sAgeTf.getText()), sGenderTf.getText(), sCourseTf.getText(),
                Integer.parseInt(sSemesterTf.getText()), Integer.parseInt(sDeptTf.getText()));
        allS.add(s);
    }

    // This method makes sure that the department exist
    public boolean DepExist() {
        dId = Integer.parseInt(sDeptTf.getText());
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

    // This method adds the changes are made into the text file
    public void toFile(String S) {

        File file = new File(sPath);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            fw.write(S);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }
    }

    // This method deletes the department that the user chooses
    public void DeleteStudent() {
        int sId = Integer.parseInt(sIdTf.getText());
        // This loops through the array and finds the id that the user has chosen
        for (int i = 0; i < allD.size(); i++) {
            for (int j = 0; j < allD.get(i).sList.size(); j++) {
                if (allD.get(i).sList.get(j).getId() == sId) {
                    // This removes it from the Department arraylist 
                    allD.get(i).sList.remove(j);
                }
            }
        }
        // This loops through the array and finds the id that the user has chosen
        for (int i = 0; i < allS.size(); i++) {
            if (allS.get(i).getId() == sId) {
                // This removes it from the Student arraylist 
                allS.remove(i);
            }
        }
    }

    // This method is used to overwrite the file after a change has been made
    public void overWrite() {
        String F = "";
        try {
            Files.write(Paths.get(sPath), F.getBytes());
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        for (int i = 0; i < allS.size(); i++) {
            if (i != allS.size() - 1) {
                F = allS.get(i).getId() + ", " + allS.get(i).getName() + ", " + allS.get(i).getAge() + ", "
                        + allS.get(i).getGender() + ", " + allS.get(i).getCourse() + ", " + allS.get(i).getSemester() + ", "
                        + allS.get(i).getDepId() + "\n";
                toFile(F);
            }
            if (i == allS.size() - 1) {
                F = allS.get(i).getId() + ", " + allS.get(i).getName() + ", " + allS.get(i).getAge() + ", "
                        + allS.get(i).getGender() + ", " + allS.get(i).getCourse() + ", " + allS.get(i).getSemester() + ", "
                        + allS.get(i).getDepId();
                toFile(F);

            }
        }
    }

    // This method is used to search the array for a certain ID number
    public void SearchStudent() {
        // Creates an object with an emty constructor
        Student myS = new Student();
        // Creates an object with an emty constructor
        Student currentS = new Student();
        Iterator<Student> iterator = allS.iterator();
        // Loops through the array until a match is made
        while (iterator.hasNext()) {
            currentS = iterator.next();
            if (currentS.getId() == Integer.parseInt(sIdTf.getText())) {
                myS.setId(currentS.getId());
                myS.setName(currentS.getName());
                myS.setAge(currentS.getAge());
                myS.setGender(currentS.getGender());
                myS.setCourse(currentS.getCourse());
                myS.setSemester(currentS.getSemester());
                myS.setDepId(currentS.getDepId());

            }
        }
        // If the array is found then the elements are displayed in the textfields 
        if (myS.getId() != 0) {
            sIdTf.setText(Integer.toString(myS.getId()));
            sNameTf.setText(myS.getName());
            sAgeTf.setText(Integer.toString(myS.getAge()));
            sGenderTf.setText(myS.getGender());
            sCourseTf.setText(myS.getCourse());
            sSemesterTf.setText(Integer.toString(myS.getSemester()));
            sDeptTf.setText(Integer.toString(myS.getDepId()));
            // If not an error message occures
        } else {
            throw new NumberFormatException("ERROR: Student not found.");
        }
    }

    //This method updates the text file
    public void UpdateStudent() {
        // Creates a new object that is used to add the new values
        s = new Student(Integer.parseInt(sIdTf.getText()), sNameTf.getText(),
                Integer.parseInt(sAgeTf.getText()), sGenderTf.getText(), sCourseTf.getText(),
                Integer.parseInt(sSemesterTf.getText()), Integer.parseInt(sDeptTf.getText()));
        // loops through the array until the right ID is found
        for (int a = 0; a < allS.size(); a++) {
            if (allS.get(a).getId() == Integer.parseInt(sIdTf.getText())) {
                // Then the array is replaced
                allS.set(a, s);
            }
        }
    }

}
