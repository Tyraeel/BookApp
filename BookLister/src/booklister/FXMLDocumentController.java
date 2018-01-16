package booklister;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import objects.Book;

/**
 *
 * @author Thibault
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ComboBox<String> listCategory;
    @FXML
    private TableView<Book> bookList;
    @FXML
    private TableColumn<Book, String> columnTitle;
    @FXML
    private TableColumn<Book, String> columnAuthor;
    @FXML
    private TableColumn<Book, String> columnDescription;
    @FXML
    private TableColumn<Book, String> columnReadingDate;
    @FXML
    private TableColumn<Book, String> columnRead;
    @FXML
    private Button buttonAdd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listCategory.getItems().addAll("Bibliothèque", "Livres lus", "Livres non lus");
        listCategory.setValue("Bibliothèque");

        columnTitle.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Book, String> b) {
                return new ReadOnlyObjectWrapper(b.getValue().getTitle());
            }
        });

        columnAuthor.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Book, String> b) {
                return new ReadOnlyObjectWrapper(b.getValue().getAuthor());
            }
        });

        columnDescription.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Book, String> b) {
                return new ReadOnlyObjectWrapper(b.getValue().getDescription());
            }
        });

        columnReadingDate.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Book, String> b) {
                return new ReadOnlyObjectWrapper(b.getValue().getReadingDate());
            }
        });

        columnRead.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Book, String> b) {
                return new ReadOnlyObjectWrapper(b.getValue().getReadState());
            }
        });

        bookList.getItems().add(new Book("Shining", "Stephen King", "Un livre guénial avec une longue description.", new String("21/09/2017"), true));
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frames/AddBook.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


//new Book("Shining", "Stephen King", "Un livre guénial", new String("21/09/2017"), true)
