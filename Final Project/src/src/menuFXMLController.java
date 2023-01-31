package src;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class menuFXMLController extends Menu implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    boolean iv = false;

    // Main table 
    @FXML
    private Button mDepartment;
    // Buttons
    @FXML
    private Button mTeacher;
    @FXML
    private Button mStudent;
    @FXML
    private Button mStaff;
    @FXML
    private Button mView;
    @FXML
    private Button mDean;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    // This is a FXML action that holds all of the actions that happens when a user clicks a button
    @FXML
    public void chooseAction(ActionEvent event) {

        // This calls the toD() method if the mDepartment is clicked 
        if (event.getSource() == mDepartment) {
            toD();
        }

        // This calls the toT() method if the mTeacher is clicked 
        if (event.getSource() == mTeacher) {
            toT();
        }

        // This calls the toS() method if the mStudent is clicked 
        if (event.getSource() == mStudent) {
            toS();
        }

        // This calls the toSt() method if the mStaff is clicked 
        if (event.getSource() == mStaff) {
            toSt();
        }

        // This calls the toV() method if the mView is clicked 
        if (event.getSource() == mView) {
            toV();
        }

        // This calls the toDn() method if the mDean is clicked 
        if (event.getSource() == mDean) {
            toDn();
        }
    }

    // This method changes to the department interface where the user can do more actions there
    public void toD() {
        try {
            root = FXMLLoader.load(getClass().getResource("dFXML.fxml"));
            stage = (Stage) ((Node) mDepartment).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    // This method changes to the teacher interface where the user can do more actions there
    public void toT() {
        try {
            root = FXMLLoader.load(getClass().getResource("tFXML.fxml"));
            stage = (Stage) ((Node) mTeacher).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    // This method changes to the student interface where the user can do more actions there
    public void toS() {
        try {
            root = FXMLLoader.load(getClass().getResource("sFXML.fxml"));
            stage = (Stage) ((Node) mStudent).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    // This method changes to the staff interface where the user can do more actions there
    public void toSt() {
        try {
            root = FXMLLoader.load(getClass().getResource("stFXML.fxml"));
            stage = (Stage) ((Node) mStaff).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    // This method changes to the view table interface where the user can do more actions there
    public void toV() {
        try {
            root = FXMLLoader.load(getClass().getResource("vFXML.fxml"));
            stage = (Stage) ((Node) mView).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    // This method changes to the dean interface where the user can do more actions there
    public void toDn() {
        try {
            root = FXMLLoader.load(getClass().getResource("dnFXML.fxml"));
            stage = (Stage) ((Node) mDean).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
