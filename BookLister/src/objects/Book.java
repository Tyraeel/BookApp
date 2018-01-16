package objects;

public class Book {
    private static int CURRENT_ID=1;
    
    private int idBook;
    private String titleBook;
    private String authorBook;
    private String descriptionBook;
    private String readingDateBook;
    private boolean readStateBook;

    public Book(String nameBook, String authorBook, String descriptionBook, String readingDateBook, boolean readStateBook) {
        this.idBook = Book.CURRENT_ID++;
        this.titleBook = nameBook;
        this.authorBook = authorBook;
        this.descriptionBook = descriptionBook;
        this.readingDateBook = readingDateBook;
        this.readStateBook = readStateBook;
    }
    
    /*GETTERS AND SETTERS*/ 

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
