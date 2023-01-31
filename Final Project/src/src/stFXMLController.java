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

public class stFXMLController extends Menu implements Initializable {

    // Main table 
    @FXML
    private TableView<Staff> AllStaff;

    // Textfields 
    @FXML
    private TextField stIdTf;
    @FXML
    private TextField stNameTf;
    @FXML
    private TextField stAgeTf;
    @FXML
    private TextField stGenderTf;
    @FXML
    private TextField stDutyTf;
    @FXML
    private TextField stWorkloadTf;
    @FXML
    private TextField stDeptTf;

    // all of the columns 
    @FXML
    private TableColumn<Staff, Integer> stIdCol;
    @FXML
    private TableColumn<Staff, String> stNameCol;
    @FXML
    private TableColumn<Staff, Integer> stAgeCol;
    @FXML
    private TableColumn<Staff, String> stGenderCol;
    @FXML
    private TableColumn<Staff, String> stDutyCol;
    @FXML
    private TableColumn<Staff, Integer> stWorkloadCol;
    @FXML
    private TableColumn<Staff, Double> stPayCol;
    @FXML
    private TableColumn<Staff, Integer> stDeptCol;

    // Buttons 
    @FXML
    private Button stLoadBtn;
    @FXML
    private Button stDeleteBtn;
    @FXML
    private Button stUpdateBtn;
    @FXML
    private Button stSearchBtn;
    @FXML
    private Button stBackBtn;
    @FXML
    private Button stInsBtn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    // This method is used to initialize all of the objects inside of the text file
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stLoadBtn.fire();
    }

    // This is a FXML action that holds all of the actions that happens when a user clicks a button
    @FXML
    private void chooseAction(ActionEvent event) {

        // This calls the firstValues() method if the stLoadBtn is clicked
        if (event.getSource() == stLoadBtn) {
            // This makes sure that the button is only fired once
            if (stIV == false) {
                firstValues();
            }
            // This adds it into the observable arraylist in the menu. 
            addSt(allSt);
        }
        // This calls the toMenu() mehtod if the dBackBtn is clicked 
        if (event.getSource() == stBackBtn) {
            toMenu();

        }
        //This calls the InsertDepartment method
        if (event.getSource() == stInsBtn) {

            checkWorkload();

            // This makes sure that the ID is unique before adding anything
            if (stUniqueCheck() == true && DepExist() == true) {
                // Calls the InsertStaff() method
                InsertStaff();
                // Creates a string and adds the elements to it
                String D = "\n" + Integer.parseInt(stIdTf.getText()) + ", "
                        + stNameTf.getText() + ", " + Integer.parseInt(stAgeTf.getText()) + ", "
                        + stGenderTf.getText() + ", " + stDutyTf.getText() + ", "
                        + Integer.parseInt(stWorkloadTf.getText()) + ", " + allSt.get(allSt.size() - 1).getPay()
                        + ", " + Integer.parseInt(stDeptTf.getText());
                toFile(D);
                // Adds it into the observable arraylist
                addSt(allSt);
            }
        }
        // This calls the DeleteStaff()and overWrite() method if the stDeleteBtn is clicked
        if (event.getSource() == stDeleteBtn) {
            DeleteStaff();
            overWrite();
        }
        // This calls the SearchStaff() method if the stSearchBtn is clicked
        if (event.getSource() == stSearchBtn) {
            SearchStaff();
        }
        // This calls the UpdateStaff() and overWrite() method if the stUpdateBtn is clicked
        if (event.getSource() == stUpdateBtn) {
            UpdateStaff();
            overWrite();
        }
    }

    // This method reads the text file and adds it into the arraylist 
    public void firstValues() {
        try {
            RandomAccessFile file = new RandomAccessFile(stPath, "r");
            String n;
            while ((n = file.readLine()) != null) {
                String stu[] = n.split(", ");
                st = new Staff(Integer.parseInt(stu[0]), stu[1], Integer.parseInt(stu[2]), stu[3], stu[4], Integer.parseInt(stu[5]), Double.parseDouble(stu[6]), Integer.parseInt(stu[7]));
                allSt.add(st);
                for (int i = 0; i < allD.size(); i++) {
                    if (allD.get(i).getId() == st.getDepId()) {
                        allD.get(i).stList.add(st);
                    }
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        // Changes the value like that the button dosent get fired again
        stIV = true;

    }

    // This makes sure that all of the columns have the proper values inside of them
    public void addSt(ObservableList<Staff> arr) {
        stIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        stNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        stGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        stDutyCol.setCellValueFactory(new PropertyValueFactory<>("duty"));
        stPayCol.setCellValueFactory(new PropertyValueFactory<>("pay"));
        stWorkloadCol.setCellValueFactory(new PropertyValueFactory<>("workload"));
        stDeptCol.setCellValueFactory(new PropertyValueFactory<>("depId"));
        AllStaff.setItems(arr);
    }

    // This method makes it like that the scene is changed back to the main menu
    public void toMenu() {
        try {
            root = FXMLLoader.load(getClass().getResource("menuFXML.fxml"));
            stage = (Stage) ((Node) stBackBtn).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    // This method makes sure that the ID is unique and has not been used before 
    public boolean stUniqueCheck() {
        pId = Integer.parseInt(stIdTf.getText());
        uniqueId = false;

        //Loops throught the array while it is false
        while (uniqueId == false) {

            boolean match = false;
            for (int a = 0; a < allSt.size(); a++) {
                if (allSt.get(a).getId() == pId) {
                    match = true;
                }
            }
            // If it finds a match then it equals true and means that there is already an ID like that 
            if (match == false) {
                uniqueId = true;
            } else {
                throw new NumberFormatException("ERROR: Staff ID already found.");
            }
        }
        return true;
    }

    // This method makes sure that the department exist
    public boolean DepExist() {
        dId = Integer.parseInt(stDeptTf.getText());
        idExists = false;
        while (idExists == false) {
            for (int b = 0; b < allD.size(); b++) {
                if (dId == allD.get(b).getId()) {
                    idExists = true;
                    allD.get(b).tList.add(t);
                }
            }
            if (idExists == false) {
                throw new NumberFormatException("ERROR: Department not found.");
            }
        }
        return true;
    }

    // This adds the new elements that the user has put in into the observable arralyist
    public void InsertStaff() {

        st = new Staff(Integer.parseInt(stIdTf.getText()), stNameTf.getText(), Integer.parseInt(stAgeTf.getText()), stGenderTf.getText(), stDutyTf.getText(), Integer.parseInt(stWorkloadTf.getText()), Integer.parseInt(stDeptTf.getText()));
        st.setPay();
        allSt.add(st);
        for (int i = 0; i < allD.size(); i++) {
            if (allD.get(i).getId() == st.getDepId()) {
                allD.get(i).stList.add(st);
            }
        }
    }

    // This method adds the changes are made into the text file
    public void toFile(String S) {

        File file = new File(stPath);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            // changes it to write
            fw.write(S);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            try {
                // Makes sure that no matter what it stays closed 
                fw.close();
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }
    }

    // This method deletes the department that the user chooses
    public void DeleteStaff() {
        int stId = Integer.parseInt(stIdTf.getText());
        // This loops through the array and finds the id that the user has chosen
        for (int i = 0; i < allD.size(); i++) {
            for (int j = 0; j < allD.get(i).stList.size(); j++) {
                if (allD.get(i).stList.get(j).getId() == stId) {
                    // This removes it from the Department arraylist 
                    allD.get(i).stList.remove(j);
                }
            }
        }
        // This loops through the array and finds the id that the user has chosen
        for (int i = 0; i < allSt.size(); i++) {
            if (allSt.get(i).getId() == stId) {
                // This removes it from the Staff arraylist 
                allSt.remove(i);
            }
        }
    }

    // This method is used to overwrite the file after a change has been made
    public void overWrite() {
        String F = "";
        try {
            Files.write(Paths.get(stPath), F.getBytes());
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        for (int i = 0; i < allSt.size(); i++) {
            if (i != allSt.size() - 1) {
                F = allSt.get(i).getId() + ", " + allSt.get(i).getName() + ", " + allSt.get(i).getAge() + ", " + allSt.get(i).getGender() + ", " + allSt.get(i).getDuty() + ", " + allSt.get(i).getWorkload() + ", " + allSt.get(i).getPay() + ", " + allSt.get(i).getDepId() + "\n";
                toFile(F);
            }
            if (i == allSt.size() - 1) {
                F = allSt.get(i).getId() + ", " + allSt.get(i).getName() + ", " + allSt.get(i).getAge() + ", " + allSt.get(i).getGender() + ", " + allSt.get(i).getDuty() + ", " + allSt.get(i).getWorkload() + ", " + allSt.get(i).getPay() + ", " + allSt.get(i).getDepId();
                toFile(F);

            }
        }
    }

    // This method is used to search the array for a certain ID number
    public void SearchStaff() {
        // Creates an object with an emty constructor
        Staff mySt = new Staff();
        // Creates an object with an emty constructor
        Staff currentSt = new Staff();
        Iterator<Staff> iterator = allSt.iterator();
        // Loops through the array until a match is made
        while (iterator.hasNext()) {
            currentSt = iterator.next();
            if (currentSt.getId() == Integer.parseInt(stIdTf.getText())) {
                mySt.setId(currentSt.getId());
                mySt.setName(currentSt.getName());
                mySt.setAge(currentSt.getAge());
                mySt.setGender(currentSt.getGender());
                mySt.setDuty(currentSt.getDuty());
                mySt.setWorkload(currentSt.getWorkload());
                mySt.setDepId(currentSt.getDepId());

            }
        }
        // If the array is found then the elements are displayed in the textfields 
        if (mySt.getId() != 0) {
            stIdTf.setText(Integer.toString(mySt.getId()));
            stNameTf.setText(mySt.getName());
            stAgeTf.setText(Integer.toString(mySt.getAge()));
            stGenderTf.setText(mySt.getGender());
            stDutyTf.setText(mySt.getDuty());
            stWorkloadTf.setText(Integer.toString(mySt.getWorkload()));
            stDeptTf.setText(Integer.toString(mySt.getDepId()));
            // If not an error message occures
        } else {
            throw new NumberFormatException("ERROR: Staff not found.");
        }
    }

    //This method updates the text file
    public void UpdateStaff() {
        // Creates a new object that is used to add the new values
        st = new Staff(Integer.parseInt(stIdTf.getText()), stNameTf.getText(),
                Integer.parseInt(stAgeTf.getText()), stGenderTf.getText(), stDutyTf.getText(),
                Integer.parseInt(stWorkloadTf.getText()), Integer.parseInt(stDeptTf.getText()));
        st.setPay();
        // loops through the array until the right ID is found
        for (int a = 0; a < allSt.size(); a++) {
            if (allSt.get(a).getId() == Integer.parseInt(stIdTf.getText())) {
                // Then the array is replaced
                allSt.set(a, st);
            }
        }
    }

    public void checkWorkload() {
        if (Integer.parseInt(stWorkloadTf.getText()) > 36) {
            throw new NumberFormatException("ERROR: Workload cannot be over 36 hours");
        }
    }
}
