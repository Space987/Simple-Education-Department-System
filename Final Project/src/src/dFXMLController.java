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

public class dFXMLController extends Menu implements Initializable {

    // Main table 
    @FXML
    private TableView<Department> AllDepartments;

    // all of the columns 
    @FXML
    private TableColumn<Department, Integer> dIdCol;
    @FXML
    private TableColumn<Department, String> dDescriptionCol;
    @FXML
    private TableColumn<Department, String> dDeanCol;

    // Textfields 
    @FXML
    private TextField dIdTf;
    @FXML
    private TextField dDescriptionTf;

    // Buttons 
    @FXML
    private Button dDeleteBtn;
    @FXML
    private Button dUpdateBtn;
    @FXML
    private Button dSearchBtn;
    @FXML
    private Button dBackBtn;
    @FXML
    private Button dInsBtn;
    @FXML
    private Button dLoadBtn;

    // Stage 
    private Stage stage;
    private Scene scene;
    private Parent root;

    tFXMLController tfc = new tFXMLController();
    sFXMLController sfc = new sFXMLController();
    stFXMLController stfc = new stFXMLController();

    // This method is used to initialize all of the objects inside of the text file
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dLoadBtn.fire();
    }

    // This is a FXML action that holds all of the actions that happens when a user clicks a button
    @FXML
    private void chooseAction(ActionEvent event) {

        // This calls the dfirstValues() method if the dLoadBtn is clicked 
        if (event.getSource() == dLoadBtn) {
            // This makes sure that the button is only fired once
            if (dIV == false) {
                dfirstValues();
            }
            // This adds it into the observable arraylist in the menu. 
            addD(allD);
        }

        // This calls the toMenu() mehtod if the dBackBtn is clicked 
        if (event.getSource() == dBackBtn) {
            toMenu();
        }

        //This calls the InsertDepartment method
        if (event.getSource() == dInsBtn) {
            // This makes sure that the ID is unique before adding anything
            if (uniqueCheck() == true) {
                // Creates a string and adds the elements to it
                String D = "\n" + Integer.parseInt(dIdTf.getText()) + ", " + dDescriptionTf.getText() + "";
                // Calls the InsertDepartment() method
                InsertDepartment();
                // Calls the toFile() method with "D" as its parameter
                toFile(D);
                // Adds it into the observable arraylist
                addD(allD);
            }
        }

        // This calls the DeleteDepartment()and overWrite() method if the dDeleteBtn is clicked
        if (event.getSource() == dDeleteBtn) {
            DeleteDepartment();
            overWrite();
            tfc.overWrite();
            sfc.overWrite();
            stfc.overWrite();
        }

        // This calls the SearchDepartment() method if the dSearchBtn is clicked
        if (event.getSource() == dSearchBtn) {
            SearchDepartment();
        }

        // This calls the UpdateDepartment() and overWrite() method if the dUpdateBtn is clicked
        if (event.getSource() == dUpdateBtn) {
            UpdateDepartment();
            overWrite();
        }
    }

    // This method reads the text file and adds it into the arraylist 
    public void dfirstValues() {
        try {
            RandomAccessFile file = new RandomAccessFile(dPath, "r");
            String n;
            // This loops the file until there is no more lines left
            while ((n = file.readLine()) != null) {
                String dept[] = n.split(", ");
                d = new Department(Integer.parseInt(dept[0]), dept[1]);
                allD.add(d);
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        // Changes the value like that the button dosent get fired again
        dIV = true;
    }

    // This makes sure that all of the columns have the proper values inside of 
    // them according to the arraylist in the argument
    public void addD(ObservableList<Department> arr) {
        dIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dDeanCol.setCellValueFactory(new PropertyValueFactory<>("dean"));
        AllDepartments.setItems(arr);
    }

    // This method makes it like that the scene is changed back to the main menu
    public void toMenu() {
        try {
            root = FXMLLoader.load(getClass().getResource("menuFXML.fxml"));
            stage = (Stage) ((Node) dBackBtn).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    // This method makes sure that the ID is unique and has not been used before 
    public boolean uniqueCheck() {
        dId = Integer.parseInt(dIdTf.getText());
        uniqueId = false;
        //Loops throught the array while it is false
        while (uniqueId == false) {
            boolean match = false;
            for (int a = 0; a < allD.size(); a++) {

                if (allD.get(a).getId() == dId) {
                    match = true;
                }
            }
            // If it finds a match then it equals true and means that there is already an ID like that 
            if (match == false) {
                uniqueId = true;
            } else {
                throw new NumberFormatException("ERROR: Department ID already found.");
            }
        }
        return true;
    }

    // This adds the new elements that the user has put in into the observable arralyist
    public void InsertDepartment() {
        d = new Department(Integer.parseInt(dIdTf.getText()), dDescriptionTf.getText());
        allD.add(d);
    }

    // This method adds the changes are made into the text file
    public void toFile(String S) {

        File file = new File(dPath);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            // changes it to write
            fw.write(S);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            // Makes sure that no matter what it stays closed 
            try {
                fw.close();
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }
    }

    // This method deletes the department that the user chooses
    public void DeleteDepartment() {
        // This loops through the array and finds the id that the user has chosen
        for (int i = 0; i < allD.size(); i++) {
            if (allD.get(i).getId() == Integer.parseInt(dIdTf.getText())) {
                //These 3 for loops remove the teacher, student, and staff objects from the allT, allS, and allSt arraylists.
                for (int j = 0; j < allT.size(); j++) {
                    if (allT.get(j).getDepId() == Integer.parseInt(dIdTf.getText())) {
                        allT.remove(j);
                    }
                }
                for (int j = 0; j < allS.size(); j++) {
                    if (allS.get(j).getDepId() == Integer.parseInt(dIdTf.getText())) {
                        allS.remove(j);
                    }
                }
                for (int j = 0; j < allSt.size(); j++) {
                    if (allSt.get(j).getDepId() == Integer.parseInt(dIdTf.getText())) {
                        allSt.remove(j);
                    }
                }
                allD.remove(i);
            }
        }

    }

    // This method is used to overwrite the file after a change has been made
    public void overWrite() {
        String F = "";
        try {
            Files.write(Paths.get(dPath), F.getBytes());
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        for (int i = 0; i < allD.size(); i++) {
            if (i == allD.size() - 1) {
                F = allD.get(i).getId() + ", " + allD.get(i).getDescription();
                toFile(F);
            } else {
                F = allD.get(i).getId() + ", " + allD.get(i).getDescription() + "\n";
                toFile(F);
            }
        }
    }

    //This method updates the text file
    public void UpdateDepartment() {
        // Creates a new object that is used to add the new values
        d = new Department(Integer.parseInt(dIdTf.getText()), dDescriptionTf.getText());
        // loops through the array until the right ID is found
        for (int a = 0; a < allD.size(); a++) {
            if (allD.get(a).getId() == Integer.parseInt(dIdTf.getText())) {
                // Then the array is replaced
                allD.set(a, d);
            }
        }
    }

    // This method is used to search the array for a certain ID number
    public void SearchDepartment() {
        // Creates an object with an emty constructor
        Department myD = new Department();
        // Creates an object with an emty constructor
        Department currentD = new Department();
        Iterator<Department> iterator = allD.iterator();
        // Loops through the array until a match is made
        while (iterator.hasNext()) {
            currentD = iterator.next();
            if (currentD.getId() == Integer.parseInt(dIdTf.getText())) {
                myD.setId(currentD.getId());
                myD.setDescription(currentD.getDescription());
            }
        }
        // If the array is found then the elements are displayed in the textfields 
        if (myD.getId() != 0) {
            dIdTf.setText(Integer.toString(myD.getId()));
            dDescriptionTf.setText(myD.getDescription());
            // If not an error message occures
        } else {
            throw new NumberFormatException("ERROR: Department not found.");
        }
    }

}
