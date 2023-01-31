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

public class dnFXMLController extends Menu implements Initializable {

    // Main table 
    @FXML
    private TableView<Department> dnDepartments;

    // all of the columns 
    @FXML
    private TableColumn<Department, Integer> dnIdCol;
    @FXML
    private TableColumn<Department, String> dnDescriptionCol;
    @FXML
    private TableColumn<Department, Integer> dnDeanCol;

    // Textfields 
    @FXML
    private TextField dnDepTf;
    @FXML
    private TextField dnTeacherTf;

    // Buttons 
    @FXML
    private Button dnBackBtn;
    @FXML
    private Button dnEnterBtn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    // This is a FXML action that holds all of the actions that happens when a user clicks a button
    @FXML
    public void chooseAction(ActionEvent event) {
        // This calls the toMenu() mehtod if the dBackBtn is clicked 
        if (event.getSource() == dnBackBtn) {
            toMenu();
        }
        // This calls the addDean() mehtod if the dnEnterBtn is clicked 
        if (event.getSource() == dnEnterBtn) {
            addDean();
        }
    }

    // This method makes it like that the scene is changed back to the main menu
    public void toMenu() {
        try {
            root = FXMLLoader.load(getClass().getResource("menuFXML.fxml"));
            stage = (Stage) ((Node) dnBackBtn).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    //This method adds the dean to a specific department
    public void addDean() {
        ObservableList<Department> tempD = FXCollections.observableArrayList();
        addD(tempD);
        boolean depcheck = false;
        boolean teachcheck = false;
        int tId = Integer.parseInt(dnTeacherTf.getText());
        int dId = Integer.parseInt(dnDepTf.getText());
        // Loops throught the entire array until a match is found
        for (int i = 0; i < allD.size(); i++) {
            if (allD.get(i).getId() == dId) {
                depcheck = true;
                // Loops throught the entire teacher array until that ID is a match
                for (int j = 0; j < allD.get(i).tList.size(); j++) {
                    if (allD.get(i).tList.get(j).getId() == tId) {
                        if (allD.get(i).tList.get(j).getDepId() == dId) {
                            allD.get(i).setDean(allD.get(i).tList.get(j).getId());
                            tempD.add(allD.get(i));
                            // If all of the IDs match then it is added into 
                            //the addD method with the department ID and the teacher ID that the user wanted
                            addD(tempD);
                            teachcheck = true;
                        }
                    }
                }
            }
        }
        if (depcheck == false) {
            throw new NumberFormatException("The Department ID that the user has entered is not valid");
        }
        if (teachcheck == false) {
            throw new NumberFormatException("The teacher ID that the user has "
                    + "entered is not valid for that department or does not exist ");
        }

    }

    // This makes sure that all of the columns have the proper values inside of them
    public void addD(ObservableList<Department> arr) {
        dnIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dnDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dnDeanCol.setCellValueFactory(new PropertyValueFactory<>("dean"));
        dnDepartments.setItems(arr);
    }
}
