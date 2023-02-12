package RegularView;

import Utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

// book detail interface
public class BookDetail extends JFrame {
    private ArrayList<JButton> buttonGroup;
    private JButton borrow, returnBack, reserve, renew, delete, update;
    private Book book;
    private Member member;
    private JPanel infoPanel, operationPanel, datePanel;
    private JLabel titleLabel, authorsLabel, publisherLabel, publishedYearLabel, ISBNLabel, numberOfStorageLabel, dateLabel;
    private JTextField dateTextField, titleTextField, authorsTextField, publisherTextField, publishedYearTextField, ISBNTextField, numberOfStorageTextField;
    /*
        IS_XXXX means permission of this operation
     */
    private boolean IS_RESERVE = false, IS_BORROW = false, IS_RETURN = false, IS_RENEW = false, IS_DELETE = false, IS_UPDATE = false;
    private BookOperation bookOperation;
    /* index of this book in the data array which comes from original *.csv file,
     */
    private int indexOfTable;
    /* index of this book in the data array which comes from borrow ArrayList,
        index of this book in the data array which comes from reserve ArrayList,
        these two original data are stored in Globals.members
     */
    private HashMap<Integer, Integer> indexesOfRecord, indexesOfReserve;
    BookDetail(Book book, Member member) throws IOException, ParseException{
        this.book = book;
        this.member = member;
        bookOperation = new BookOperation();
        indexOfTable = bookOperation.getRowKey(book);
        indexesOfRecord = new HashMap<>();
        indexesOfReserve = new HashMap<>();

        generateButtons();
        infoPanel = new JPanel();
        operationPanel = new JPanel();
        datePanel = new JPanel();

        titleLabel = new JLabel("title: ");
        if(book.getTitle() == null || book.getTitle().equals("")) titleTextField = new JTextField(6);
        else titleTextField = new JTextField(book.getTitle());
        JPanel panel = new JPanel();
        panel.add(titleLabel);
        panel.add(titleTextField);
        infoPanel.add(panel);

        authorsLabel = new JLabel("authors: ");
        if(book.getAuthor() == null || book.getAuthor().equals("")) authorsTextField = new JTextField(6);
        else authorsTextField = new JTextField(book.getAuthor());
        panel = new JPanel();
        panel.add(authorsLabel);
        panel.add(authorsTextField);
        infoPanel.add(panel);

        publisherLabel = new JLabel("publisher: ");
        if(book.getPublisher() == null || book.getPublisher().equals("")) publisherTextField = new JTextField(6);
        else publisherTextField = new JTextField(book.getPublisher());
        panel = new JPanel();
        panel.add(publisherLabel);
        panel.add(publisherTextField);
        infoPanel.add(panel);

        publishedYearLabel = new JLabel("publishedYear: ");
        if(book.getPublishedYear() == null || book.getPublishedYear().equals("")) publishedYearTextField = new JTextField(6);
        else publishedYearTextField = new JTextField(book.getPublishedYear());
        panel = new JPanel();
        panel.add(publishedYearLabel);
        panel.add(publishedYearTextField);
        infoPanel.add(panel);

        ISBNLabel = new JLabel("ISBN: ");
        if(book.getISBN() == null || book.getISBN().equals("")) ISBNTextField = new JTextField(6);
        else ISBNTextField = new JTextField(book.getISBN());
        panel = new JPanel();
        panel.add(ISBNLabel);
        panel.add(ISBNTextField);
        infoPanel.add(panel);

        numberOfStorageLabel = new JLabel("numberOfStorage: ");
        numberOfStorageTextField = new JTextField(Integer.toString(book.getNumber()));
        panel = new JPanel();
        panel.add(numberOfStorageLabel);
        panel.add(numberOfStorageTextField);
        infoPanel.add(panel);

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.Y_AXIS));

        for(Member item: Globals.members) {
            ArrayList<LoanBook> books = item.getLoanBooks();
            for(LoanBook loanBook:books) {
                if(loanBook.equals(book)) {
                    // show borrow information of this book by using JLabel
                    JLabel borrowerLabel = new JLabel("borrower: " + item.getName());
                    JLabel returnDateLabel = new JLabel("returnDate: " + loanBook.getReturnDate());
                    infoPanel.add(borrowerLabel);
                    infoPanel.add(returnDateLabel);
                    indexesOfRecord.put(Globals.members.indexOf(item), books.indexOf(loanBook));
                }
            }
            ArrayList<LoanBook> reserveBooks = item.getReserveBooks();
            for(LoanBook loanBook:reserveBooks) {
                if(loanBook.equals(book)) {
                    // show reserve information of this book by using JLabel
                    JLabel reserveLabel = new JLabel("reserve member: " + item.getName());
                    JLabel reserveDateLabel = new JLabel("returnDate: " + loanBook.getReturnDate());
                    infoPanel.add(reserveLabel);
                    infoPanel.add(reserveDateLabel);
                    indexesOfReserve.put(Globals.members.indexOf(item), books.indexOf(loanBook));
                }
            }
        }
        infoPanel.add(update);

        buttonGroup.forEach(button -> {
            operationPanel.add(button);
        });

        dateLabel = new JLabel("Date(Reserve/Renew): ");
        dateTextField = new JTextField(6);
        datePanel.add(dateLabel);
        datePanel.add(dateTextField);
        operationPanel.add(datePanel);

        infoPanel.setBorder(new EmptyBorder(5,5,5,5));
        add(infoPanel, BorderLayout.WEST);
        add(operationPanel, BorderLayout.CENTER);
        setTitle("Book Detail");
        setSize(800, 300);
        pack();
        setVisible(true);
    }

    // initialise buttons and bind events
    private void generateButtons() throws ParseException {
        buttonGroup = new ArrayList<>();
        renew = new JButton("renew");
        returnBack = new JButton("return");
        borrow = new JButton("borrow");
        reserve = new JButton("reserve");
        delete = new JButton("delete");
        update = new JButton("update");
        updateButtonStatus();

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                book.setTitle(titleTextField.getText());
                book.setAuthor(authorsTextField.getText());
                book.setPublisher(publisherTextField.getText());
                book.setPublishedYear(publishedYearTextField.getText());
                book.setISBN(ISBNTextField.getText());
                book.setNumber(Integer.parseInt(numberOfStorageTextField.getText()));
                try {
                    // update in *.csv file
                    bookOperation.update(book, indexOfTable);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                // update in borrow data -> Globals.member
                for(int key:indexesOfRecord.keySet()){
                    LoanBook loanBook = Globals.members.get(key).getLoanBooks().get(indexesOfRecord.get(key));
                    loanBook.setTitle(titleTextField.getText());
                    loanBook.setAuthor(authorsTextField.getText());
                    loanBook.setPublisher(authorsTextField.getText());
                    loanBook.setPublishedYear(publishedYearTextField.getText());
                    loanBook.setISBN(ISBNTextField.getText());
                    loanBook.setNumber(Integer.parseInt(numberOfStorageTextField.getText()));
                    Globals.members.get(key).getLoanBooks().set(indexesOfRecord.get(key), loanBook);
                }
                // update in reserve data -> Globals.member
                for(int key:indexesOfReserve.keySet()){
                    LoanBook reserveBook = Globals.members.get(key).getReserveBooks().get(indexesOfReserve.get(key));
                    reserveBook.setTitle(titleTextField.getText());
                    reserveBook.setAuthor(authorsTextField.getText());
                    reserveBook.setPublisher(authorsTextField.getText());
                    reserveBook.setPublishedYear(publishedYearTextField.getText());
                    reserveBook.setISBN(ISBNTextField.getText());
                    reserveBook.setNumber(Integer.parseInt(numberOfStorageTextField.getText()));
                    Globals.members.get(key).getReserveBooks().set(indexesOfReserve.get(key), reserveBook);
                }
                dispose();
            }
        });
        returnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // update in borrow data -> Globals.member
                for(Member item:Globals.members) {
                    if(item.equals(member)) {
                        ArrayList<LoanBook> books = item.getLoanBooks();
                        books.removeIf(loanBook -> loanBook.equals(book));
                        item.setLoanBooks(books);
                    }
                }
                try {
                    // update in *.csv file
                    book.setNumber(book.getNumber() + 1);
                    bookOperation.changeStorage(book, 1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    updateButtonStatus();
                    dispose();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        borrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try {
                        // update in *.csv file
                        bookOperation.changeStorage(book, -1);
                        book.setNumber(book.getNumber() - 1);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    // update in borrow data -> Globals.member
                    for(Member item: Globals.members) {
                        if(item.equals(member)) {
                            ArrayList<LoanBook> books = item.getLoanBooks();
                            LoanBook loanBook = new LoanBook(book);
                            DateOperation date = new DateOperation("25/11/2022");
                            loanBook.setBorrowDate(date.toString());
                            loanBook.setReturnDate(date.add(7));
                            books.add(loanBook);
                            item.setLoanBooks(books);
                            ArrayList<LoanBook> reserveBooks = item.getReserveBooks();
                            reserveBooks.removeIf(reserveBook -> reserveBook.equals(book));
                        }
                    }
                    updateButtonStatus();
                    dispose();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        reserve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(DateOperation.testFormat(dateTextField.getText())) {
                    try {
                        // update in reserve data -> Globals.member
                        for(Member item:Globals.members) {
                            if(item.equals(member)) {
                                ArrayList<LoanBook> books = item.getReserveBooks();
                                LoanBook loanBook = new LoanBook(book);
                                DateOperation date = new DateOperation(dateTextField.getText());
                                loanBook.setBorrowDate(date.toString());
                                loanBook.setReturnDate(date.add(7));
                                books.add(loanBook);
                                item.setReserveBooks(books);
                            }
                        }
                        updateButtonStatus();
                        dispose();
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(operationPanel,"invalid dateFormat");
                }
            }
        });
        renew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(DateOperation.testFormat(dateTextField.getText())) {
                    try {
                        // update in borrow data -> Globals.member
                        for(Member item:Globals.members) {
                            if(item.equals(member)) {
                                ArrayList<LoanBook> books = item.getLoanBooks();
                                for(LoanBook loanBook:books) {
                                    if(loanBook.equals(book)) {
                                        DateOperation renewDate = new DateOperation(dateTextField.getText());
                                        String borrowDateString = loanBook.getBorrowDate();
                                        DateOperation borrowDate = new DateOperation(borrowDateString);
                                        borrowDateString = borrowDate.add(member.getMaxlengthLoan());
                                        if(renewDate.compare(borrowDateString) != 1) {
                                            loanBook.setReturnDate(renewDate.toString());
                                        }else{
                                            JOptionPane.showMessageDialog(operationPanel,"beyond your limitation");
                                        }
                                    }
                                }
                                item.setLoanBooks(books);
                            }
                        }
                        updateButtonStatus();
                        dispose();
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(operationPanel,"invalid dateFormat");
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // update in *.csv file
                    bookOperation.delete(book);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                // update in borrow / reserve data -> Globals.member
                for(Member item: Globals.members) {
                    ArrayList<LoanBook> books = item.getLoanBooks();
                    books.removeIf(loanBook -> loanBook.equals(book));
                    ArrayList<LoanBook> reserveBooks = item.getReserveBooks();
                    reserveBooks.removeIf(loanBook -> loanBook.equals(book));
                }
                dispose();
            }
        });

        buttonGroup.add(borrow);
        buttonGroup.add(returnBack);
        buttonGroup.add(reserve);
        buttonGroup.add(renew);
        buttonGroup.add(delete);
    }

    // update permission of every operation button
    private void updateButtonStatus() throws ParseException {
        if(member.getUsername().equals("admin")) {
            IS_UPDATE = true;
            IS_DELETE = true;
            IS_RETURN = false;
            IS_RENEW = false;
            IS_BORROW = false;
            IS_RESERVE = false;
        } else {
            IS_UPDATE = false;
            IS_DELETE = false;
            IS_RETURN = false;
            IS_RENEW = false;
            IS_BORROW = true;
            IS_RESERVE = true;
            for(Member item: Globals.members) {
                if(item.equals(member)) {
                    IS_RESERVE = (item.getLoanBooks().size() < member.getMaxNumberLoan()) &&
                            (book.getNumber() > 0 || Globals.reserveAvailable(book, "25/11/2022", member.getMaxlengthLoan()));
                    IS_BORROW = (item.getLoanBooks().size() < member.getMaxNumberLoan()) && (book.getNumber() > 0);
                    ArrayList<LoanBook> loanBooks = item.getLoanBooks();
                    for(LoanBook loanBook:loanBooks) {
                        if(loanBook.equals(book)) {
                            IS_RETURN = true;
                            IS_RENEW = true;
                            IS_RESERVE = false;
                            IS_BORROW = false;
                        }
                    }
                    ArrayList<LoanBook> reserveBooks = item.getReserveBooks();
                    for(LoanBook loanBook:reserveBooks) {
                        if(loanBook.equals(book)) {
                            IS_RETURN = false;
                            IS_RENEW = false;
                            IS_RESERVE = false;
                            IS_BORROW = loanBook.getBorrowDate().equals("25/11/2022");
                        }
                    }
                }
            }
        }
        borrow.setEnabled(IS_BORROW);
        returnBack.setEnabled(IS_RETURN);
        reserve.setEnabled(IS_RESERVE);
        renew.setEnabled(IS_RENEW);
        delete.setEnabled(IS_DELETE);
        update.setEnabled(IS_UPDATE);
    }
}
