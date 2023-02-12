package Utils;

// Book class
public class Book {
    private String title;
    private String author;
    private String publisher;
    private String publishedYear;
    private String ISBN;
    private int number;

    public String getPublishedYear() {
        return publishedYear;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // this object -> array
    public String[] toList() {
        return new String[]{getTitle(), getAuthor(), getPublisher(), getPublishedYear(), getISBN(), Integer.toString(getNumber())};
    }

    // Judgment of equality
    public boolean equals(Book book) {
        return book.getTitle().equals(title) && book.getAuthor().equals(author) &&
                book.getPublisher().equals(publisher) && book.getPublishedYear().equals(publishedYear) &&
                book.getISBN().equals(ISBN) && book.getNumber() == number;
    }
}
