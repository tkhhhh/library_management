package Utils;

// loan book class
public class LoanBook extends Book {
    private String borrowDate;
    private String returnDate;

    public LoanBook(Book book) {
        super();
        setTitle(book.getTitle());
        setAuthor(book.getAuthor());
        setPublisher(book.getPublisher());
        setPublishedYear(book.getPublishedYear());
        setISBN(book.getISBN());
        setNumber(book.getNumber());
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    // this object -> array
    public String[] toList() {
        return new String[]{getTitle(), getAuthor(), getPublisher(), getPublishedYear(), getISBN(), Integer.toString(getNumber()), getBorrowDate(), getReturnDate()};
    }
}
