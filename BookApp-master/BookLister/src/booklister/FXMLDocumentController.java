package booklister;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import objects.Book;

/**
 *
 * @author Mathieu
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
    @FXML
    private TableColumn<Book, Integer> columnId;
    public static Connection conn;
    private static ArrayList<Book> bookDisplayed = new ArrayList<Book>();
    @FXML
    private Button refreshButton;
    @FXML
    private TableColumn<Book, Boolean> Supp;
    @FXML
    private Button deleteButton;
    @FXML
    private Label booksNbSentence;
    private int nbLivres;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Class.forName("org.postgresql.Driver");
            
            String sUrl = "jdbc:postgresql://postgresql-piterboth.alwaysdata.net:5432/piterboth_bookapp";
            String user = "piterboth";
            String passwd = "2809015885L-";

       
            conn = DriverManager.getConnection(sUrl, user, passwd);
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ClassNotFoundException c){
            
        }
            
        listCategory.getItems().addAll("Bibliothèque", "Livres lus", "Livres non lus");
        listCategory.setValue("Bibliothèque");
        
        columnId.setCellValueFactory(new Callback<CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> b) {
                return new ReadOnlyObjectWrapper(b.getValue().getId());
            }
        });
        
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
        
        Supp.setCellFactory(CheckBoxTableCell.forTableColumn(Supp));
        
        try {
            bookDisplayed = getAllBooks();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < bookDisplayed.size(); i++){
            
            bookList.getItems().add(bookDisplayed.get(i));
        }
        
        nbLivres = bookDisplayed.size();
        booksNbSentence.setText("Il y a actuellement " + nbLivres + " livres dans la bibliothèque.");
    }
    
    //bookList.getItems().add(new Book("Shining", "Stephen King", "Un livre guénial avec une longue description.", new String("21/09/2017"), true));
    
    public ArrayList<Book> getAllBooks() throws SQLException{
        
        ArrayList<Book> bookDisplayed = new ArrayList<Book>();
        
        try {
            
            //Création d'un objet Statement
            Statement state = conn.createStatement();
            //L'objet ResultSet contient le résultat de la requête SQL
            ResultSet result = state.executeQuery("SELECT * FROM \"Books\"");
            ResultSetMetaData resultMeta = result.getMetaData();

            while(result.next()){     
                
                String description = "";
                String dateLecture = "";
                         
                if (result.getObject(4) == null)
                    description = "";
                else
                    description = result.getObject(4).toString();

                if (result.getObject(5) == null)
                    dateLecture = "";
                else
                    dateLecture = result.getObject(5).toString();
                            
                Book currentBook = new Book(result.getInt(1), result.getObject(2).toString(), result.getObject(3).toString(), description, dateLecture, result.getBoolean(6));
                bookDisplayed.add(currentBook);             
            }
            
            nbLivres = bookDisplayed.size();
            booksNbSentence.setText("Il y a actuellement " + nbLivres + " livres dans la bibliothèque.");

            result.close();
            state.close();
         
        } catch (SQLException e) {
        }
        
        return bookDisplayed;
    }
    
    public ArrayList<Book> getReadBooks(){
        
        ArrayList<Book> readBooks = new ArrayList<Book>();
        
        try{
            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM \"Books\" WHERE \"etatLecture\" = True");
            ResultSetMetaData resultMeta = result.getMetaData();
            
            while(result.next()){     
                
                String description = "";
                String dateLecture = "";
                         
                if (result.getObject(4) == null)
                    description = "";
                else
                    description = result.getObject(4).toString();

                if (result.getObject(5) == null)
                    dateLecture = "";
                else
                    dateLecture = result.getObject(5).toString();
                            
                Book currentBook = new Book(result.getInt(1), result.getObject(2).toString(), result.getObject(3).toString(), description, dateLecture, result.getBoolean(6));
                readBooks.add(currentBook);             
            }
            
            nbLivres = bookDisplayed.size();
            booksNbSentence.setText("Vous avez lu " + nbLivres + " livres.");
            
            result.close();
            statement.close();
            
        }catch(SQLException e){
        }
        
        return readBooks;
    }
    
    public ArrayList<Book> getNonReadBooks(){
        
        ArrayList<Book> NonreadBooks = new ArrayList<Book>();
        
        try{
            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM \"Books\" WHERE \"etatLecture\" = False");
            ResultSetMetaData resultMeta = result.getMetaData();
            
            while(result.next()){     
                
                String description = "";
                String dateLecture = "";
                         
                if (result.getObject(4) == null)
                    description = "";
                else
                    description = result.getObject(4).toString();

                if (result.getObject(5) == null)
                    dateLecture = "";
                else
                    dateLecture = result.getObject(5).toString();
                            
                Book currentBook = new Book(result.getInt(1), result.getObject(2).toString(), result.getObject(3).toString(), description, dateLecture, result.getBoolean(6));
                NonreadBooks.add(currentBook);             
            }
            
            nbLivres = NonreadBooks.size();
            booksNbSentence.setText("Il y a actuellement " + nbLivres + " livres non-lus dans la bibliothèque.");
        
            result.close();
            statement.close();
            
        }catch(SQLException e){
        }
        
        return NonreadBooks;
    }
    
    @FXML
    private void handleButtonAdd(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frames/addBook.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Ajouter un livre");
            stage.show();
        } catch (IOException e) {
        }
    }

    @FXML
    private void handleComboBox(ActionEvent event) {
        
        if (listCategory.getValue() == "Bibliothèque"){
            
            bookList.getItems().clear();
            
            try {
            bookDisplayed = getAllBooks();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            for (int i = 0; i < bookDisplayed.size(); i++){

                bookList.getItems().add(bookDisplayed.get(i));
            }
        }
        
        else if (listCategory.getValue() == "Livres lus"){
            
            bookDisplayed = getReadBooks();
            bookList.getItems().clear();
            
            for (int i = 0; i < bookDisplayed.size(); i++){

                bookList.getItems().add(bookDisplayed.get(i));
            }
        }
        
        else{
            
            bookDisplayed = getNonReadBooks();
            bookList.getItems().clear();
            
            for (int i = 0; i < bookDisplayed.size(); i++){

                bookList.getItems().add(bookDisplayed.get(i));
            }
        }
    }

    @FXML
    private void handleRefreshButton(ActionEvent event) {
       
       bookList.getItems().clear();
       
       listCategory.setValue("Bibliothèque");
       
       try {
            bookDisplayed = getAllBooks();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < bookDisplayed.size(); i++){
            
            bookList.getItems().add(bookDisplayed.get(i));
        }
        
        nbLivres = bookDisplayed.size();
        booksNbSentence.setText("Il y a actuellement " + nbLivres + " livres dans la bibliothèque.");
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frames/deleteBook.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Supprimer un livre");
            stage.show();
            
            
        } catch (IOException e) {
        }
    }

}
