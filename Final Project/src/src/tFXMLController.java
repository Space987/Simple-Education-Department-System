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

public class tFXMLController extends Menu implements Initializable {

    // Main table 
    @FXML
    private TableView<Teacher> AllTeachers;

    // Textfields 
    @FXML
    private TextField tIdTf;
    @FXML
    private TextField tNameTf;
    @FXML
    private TextField tAgeTf;
    @FXML
    private TextField tGenderTf;
    @FXML
    private TextField tDegreeTf;
    @FXML
    private TextField tSpecialtyTf;
    @FXML
    private TextField tDeptTf;

    // Columns 
    @FXML
    private TableColumn<Teacher, Integer> tIdCol;
    @FXML
    private TableColumn<Teacher, String> tNameCol;
    @FXML
    private TableColumn<Teacher, Integer> tAgeCol;
    @FXML
    private TableColumn<Teacher, String> tGenderCol;
    @FXML
    private TableColumn<Teacher, String> tDegreeCol;
    @FXML
    private TableColumn<Teacher, String> tSpecialtyCol;
    @FXML
    private TableColumn<Teacher, Double> tPayCol;
    @FXML
    private TableColumn<Teacher, Integer> tDeptCol;

    // Buttons 
    @FXML
    private Button tBackBtn;
    @FXML
    private Button tLoadBtn;
    @FXML
    private Button tDeleteBtn;
    @FXML
    private Button tUpdateBtn;
    @FXML
    private Button tSearchBtn;
    @FXML
    private Button tInsBtn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // This method is used to initialize all of the objects inside of the text file
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tLoadBtn.fire();
    }

    // This is a FXML action that holds all of the actions that happens when a user clicks a button
    @FXML
    private void chooseAction(ActionEvent event) {

        // This calls the firstValues() method if the tLoadBtn is clicked 
        if (event.getSource() == tLoadBtn) {
            // This makes sure that the buttin is only fired once
            if (tIV == false) {
                firstValues();
            }
            // This adds it into the observable arraylist in the menu. 
            addT(allT);
        }
        // This calls the toMenu() mehtod if the tBackBtn is clicked 
        if (event.getSource() == tBackBtn) {
            toMenu();
        }
        //This calls the InsertTeacher method
        if (event.getSource() == tInsBtn) {
            // This makes sure that the ID is unique before adding anything
            if (uniqueCheck() == true && DepExist() == true) {
                // Calls the InsertTeacher() method
                InsertTeacher();
                // Creates a string and adds the elements to it
                String T = "\n" + Integer.parseInt(tIdTf.getText()) + ", " + tNameTf.getText() + ", " + Integer.parseInt(tAgeTf.getText()) + ", " + tGenderTf.getText() + ", " + tDegreeTf.getText() + ", " + tSpecialtyTf.getText() + ", " + allT.get(allT.size() - 1).getPay() + ", " + Integer.parseInt(tDeptTf.getText());
                toFile(T);
                // Adds it into the observable arraylist
                addT(allT);
            }
        }
        // This calls the DeleteTeacher()and overWrite() method if the tDeleteBtn is clicked
        if (event.getSource() == tDeleteBtn) {
            DeleteTeacher();
            overWrite();
        }
        // This calls the SearchTeacher() method if the tSearchBtn is clicked
        if (event.getSource() == tSearchBtn) {
            SearchTeacher();
        }
        // This calls the UpdateTeacher() and overWrite() method if the tUpdateBtn is clicked
        if (event.getSource() == tUpdateBtn) {
            UpdateTeacher();
            overWrite();
        }
    }

    // This method reads the text file and adds it into the arraylist 
    public void firstValues() {
        try {
            RandomAccessFile file = new RandomAccessFile(tPath, "r");
            String n;
            // This loops the file until there is no more lines left
            while ((n = file.readLine()) != null) {
                String teach[] = n.split(", ");
                t = new Teacher(Integer.parseInt(teach[0]), teach[1], Integer.parseInt(teach[2]),
                        teach[3], teach[4], teach[5], Double.parseDouble(teach[6]), Integer.parseInt(teach[7]));
                allT.add(t);
                for (int i = 0; i < allD.size(); i++) {
                    if (allD.get(i).getId() == t.getDepId()) {
                        allD.get(i).tList.add(t);
                    }
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        // Changes the value like that the button dosent get fired again
        tIV = true;

    }

    // This makes sure that all of the columns have the proper values inside of them
    public void addT(ObservableList<Teacher> arr) {
        tIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        tAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        tDegreeCol.setCellValueFactory(new PropertyValueFactory<>("degree"));
        tSpecialtyCol.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        tPayCol.setCellValueFactory(new PropertyValueFactory<>("pay"));
        tDeptCol.setCellValueFactory(new PropertyValueFactory<>("depId"));
        AllTeachers.setItems(arr);
    }

    // This method makes it like that the scene is changed back to the main menu
    public void toMenu() {
        try {
            root = FXMLLoader.load(getClass().getResource("menuFXML.fxml"));
            stage = (Stage) ((Node) tBackBtn).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    // This method makes sure that the ID is unique and has not been used before 
    public boolean uniqueCheck() {
        pId = Integer.parseInt(tIdTf.getText());
        uniqueId = false;
        //Loops throught the array while it is false
        while (uniqueId == false) {
            boolean match = false;
            for (int a = 0; a < allT.size(); a++) {
                if (allT.get(a).getId() == pId) {
                    match = true;
                }
            }
            // If it finds a match then it equals true and means that there is already an ID like that 
            if (match == false) {
                uniqueId = true;
            } else {
                throw new NumberFormatException("ERROR: Teacher ID already found.");
            }
        }
        return true;
    }

    // This method makes sure that the department exist
    public boolean DepExist() {
        dId = Integer.parseInt(tDeptTf.getText());
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

    // This adds the new elements that the user has put in into the observable arralyist
    public void InsertTeacher() {
        t = new Teacher(Integer.parseInt(tIdTf.getText()), tNameTf.getText(), Integer.parseInt(tAgeTf.getText()), tGenderTf.getText(), tDegreeTf.getText(), tSpecialtyTf.getText(), Integer.parseInt(tDeptTf.getText()));
        t.setPay();
        allT.add(t);
        for (int i = 0; i < allD.size(); i++) {
            if (allD.get(i).getId() == t.getDepId()) {
                allD.get(i).tList.add(t);
            }
        }
    }

    // This method adds the changes are made into the text file
    public void toFile(String S) {

        File file = new File(tPath);
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
    public void DeleteTeacher() {
        int tId = Integer.parseInt(tIdTf.getText());
        // This loops through the array and finds the id that the user has chosen
        for (int i = 0; i < allD.size(); i++) {
            for (int j = 0; j < allD.get(i).tList.size(); j++) {
                // This removes it from the Department arraylist 
                if (allD.get(i).tList.get(j).getId() == tId) {
                    allD.get(i).tList.remove(j);
                }
            }
        }
        // This loops through the array and finds the id that the user has chosen
        for (int i = 0; i < allT.size(); i++) {
            if (allT.get(i).getId() == tId) {
                // This removes it from the Teacher arraylist 
                allT.remove(i);
            }
        }
    }

    // This method is used to overwrite the file after a change has been made
    public void overWrite() {
        String F = "";
        try {
            Files.write(Paths.get(tPath), F.getBytes());
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        for (int i = 0; i < allT.size(); i++) {
            if (i == allT.size() - 1) {
                F = allT.get(i).getId() + ", " + allT.get(i).getName() + ", " + allT.get(i).getAge() + ", " + allT.get(i).getGender() + ", " + allT.get(i).getDegree() + ", " + allT.get(i).getSpecialty() + ", " + allT.get(i).getPay() + ", " + allT.get(i).getDepId();
                toFile(F);
            } else {
                F = allT.get(i).getId() + ", " + allT.get(i).getName() + ", " + allT.get(i).getAge() + ", " + allT.get(i).getGender() + ", " + allT.get(i).getDegree() + ", " + allT.get(i).getSpecialty() + ", " + allT.get(i).getPay() + ", " + allT.get(i).getDepId() + "\n";
                toFile(F);
            }
        }
    }

    // This method is used to search the array for a certain ID number
    public void SearchTeacher() {
        // Creates an object with an emty constructor
        Teacher myT = new Teacher();
        // Creates an object with an emty constructor
        Teacher currentT = new Teacher();
        Iterator<Teacher> iterator = allT.iterator();
        // Loops through the array until a match is made
        while (iterator.hasNext()) {
            currentT = iterator.next();
            if (currentT.getId() == Integer.parseInt(tIdTf.getText())) {
                myT.setId(currentT.getId());
                myT.setName(currentT.getName());
                myT.setAge(currentT.getAge());
                myT.setGender(currentT.getGender());
                myT.setDegree(currentT.getDegree());
                myT.setSpecialty(currentT.getSpecialty());
                myT.setDepId(currentT.getDepId());
            }
        }
        // If the array is found then the elements are displayed in the textfields 
        if (myT.getId() != 0) {
            tIdTf.setText(Integer.toString(myT.getId()));
            tNameTf.setText(myT.getName());
            tAgeTf.setText(Integer.toString(myT.getAge()));
            tGenderTf.setText(myT.getGender());
            tDegreeTf.setText(myT.getDegree());
            tSpecialtyTf.setText(myT.getSpecialty());
            tDeptTf.setText(Integer.toString(myT.getDepId()));
            // If not an error message occures
        } else {
            throw new NumberFormatException("ERROR: Teacher not found.");
        }
    }

    //This method updates the text file
    public void UpdateTeacher() {
        // Creates a new object that is used to add the new values
        t = new Teacher(Integer.parseInt(tIdTf.getText()), tNameTf.getText(), Integer.parseInt(tAgeTf.getText()), tGenderTf.getText(), tDegreeTf.getText(), tSpecialtyTf.getText(), Integer.parseInt(tDeptTf.getText()));
        t.setPay();
        // loops through the array until the right ID is found
        for (int a = 0; a < allT.size(); a++) {
            if (allT.get(a).getId() == Integer.parseInt(tIdTf.getText())) {
                // Then the array is replaced
                allT.set(a, t);
            }
        }
    }
}
