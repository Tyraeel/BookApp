/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import objects.Book;

/**
 * FXML Controller class
 *
 * @author Thibault
 */
public class AddBookController implements Initializable {

    @FXML
    private Button buttonConfirm;
    @FXML
    private DatePicker readingDateField;
    @FXML
    private CheckBox readStateField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextArea descriptionField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonConfirm(ActionEvent event) {
        
        if (!titleField.getText().equals("") && !authorField.getText().equals("")){
            String readState;
    
            Book livre = new Book(titleField.getText(), authorField.getText(), this.descriptionField.getText(), readingDateField.getValue().toString(), readStateField.selectedProperty().get());        }
    }
    
}
