/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import static booklister.FXMLDocumentController.conn;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mathieu
 */
public class deleteBookController implements Initializable {

    @FXML
    private Button DeleteButton;
    @FXML
    private CheckBox confirmDeleteField;
    @FXML
    private TextField idField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleDeleteButton(ActionEvent event) throws SQLException {
        
        if (confirmDeleteField.selectedProperty().get()){
            
            try{
                Statement statement = conn.createStatement();
                statement.executeQuery("DELETE FROM \"Books\" WHERE id = " + idField.getText() + ";");
                statement.close();
                
            }catch(SQLException e){
            }
            
            Stage stage = (Stage) DeleteButton.getScene().getWindow();
            stage.close();
        }
    }
    
}
