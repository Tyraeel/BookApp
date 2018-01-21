package objects;

public class Book {
     
    private int idBook;
    private String titleBook;
    private String authorBook;
    private String descriptionBook;
    private String readingDateBook;
    private boolean readStateBook;
    
    public Book(int id, String nameBook, String authorBook, String descriptionBook, String readingDateBook, boolean readStateBook) {
        this.idBook = id;
        this.titleBook = nameBook;
        this.authorBook = authorBook;
        this.descriptionBook = descriptionBook;
        this.readingDateBook = readingDateBook;
        this.readStateBook = readStateBook;
    }
    
    /*GETTERS AND SETTERS*/ 
    
    public int getId(){
        
        return this.idBook;
    }
    
    public String getTitle() {
        return this.titleBook;
    }
    
    public String getAuthor() {
        return this.authorBook;
    }
    
    public String getDescription() {
        return this.descriptionBook;
    }
    
    public String getReadingDate() {
        return this.readingDateBook;
    }
    
    public String getReadState() {
        if(this.readStateBook)
            return "Lu";
        else
            return "Non lu";
    }
}
