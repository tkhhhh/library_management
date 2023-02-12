package Utils;

import java.text.ParseException;
import java.util.ArrayList;

// Records memory, using global variable
public class Globals{
    public static ArrayList<Member> members = new ArrayList<>();

    // check available when reserving
    public static boolean reserveAvailable(Book book, String dateString, int max0fLength) throws ParseException {
        for(Member item: members) {
            ArrayList<LoanBook> books = item.getLoanBooks();
            for(LoanBook loanBook:books) {
                if(loanBook.getTitle().equals(book.getTitle()) && loanBook.getAuthor().equals(book.getAuthor()) &&
                        loanBook.getPublisher().equals(book.getPublisher())){
                    DateOperation dateNow = new DateOperation(dateString);
                    dateString = dateNow.add(max0fLength);
                    DateOperation dateLoan = new DateOperation(loanBook.getReturnDate());
                    if(dateLoan.compare(dateString) == 1) return true;
                }
            }
        }
        return false;
    }
}
