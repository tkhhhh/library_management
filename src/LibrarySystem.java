import RegularView.LoginInterface;
import Utils.*;

import java.io.IOException;
import java.util.ArrayList;

public class LibrarySystem {
    public static void main(String []args) throws IOException {
        // load original data in record (memory)
        MemberOperation memberOperation = new MemberOperation();
        Member user = memberOperation.getAllRows().get(4);
        BookOperation bookOperation = new BookOperation();
        Book book1 = bookOperation.getAllRows().get(0);
        Book book2 = bookOperation.getAllRows().get(3);
        LoanBook loanBook1 = new LoanBook(book1);
        loanBook1.setBorrowDate("01/11/2022");
        loanBook1.setReturnDate("08/11/2022");
        LoanBook loanBook2 = new LoanBook(book2);
        loanBook2.setBorrowDate("15/11/2022");
        loanBook2.setReturnDate("22/11/2022");
        ArrayList<LoanBook> loanBooks = user.getLoanBooks();
        loanBooks.add(loanBook1);
        loanBooks.add(loanBook2);
        user.setLoanBooks(loanBooks);
        Globals.members.add(user);
        new LoginInterface();
    }
}
