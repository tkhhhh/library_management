package RegularView;

import Utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Panel for records table
public class RecordPanel extends JPanel {

    public RecordPanel(Member member, BookOperation bookOperation) throws IOException {
        setLayout(new GridLayout(2,1));


        List<LoanBook> reserveBooks = new ArrayList<>(), loanBooks = new ArrayList<>();
        if(member.getUsername().equals("admin")) {
            for(Member item: Globals.members) {
                reserveBooks.addAll(item.getReserveBooks());
                loanBooks.addAll(item.getLoanBooks());
            }
        } else {
            for(Member item: Globals.members) {
                if(member.equals(item)) {
                    reserveBooks = item.getReserveBooks();
                    loanBooks = item.getLoanBooks();
                }
            }
        }

        String[] columnsArray = bookOperation.getColumnNames();

        //reserve book list for table
        List<String> columnsReserveList = new ArrayList<>(Arrays.asList(columnsArray));
        columnsReserveList.add("reserveDate");
        columnsReserveList.add("returnDate");
        TableDemo reserveTable = new TableDemo<LoanBook>(reserveBooks, columnsReserveList);
        JScrollPane reservePanel = new JScrollPane(reserveTable);
        reservePanel.setBorder(new EmptyBorder(5,5,5,5));

        //borrow book list for table
        List<String> columnsBorrowList = new ArrayList<>(Arrays.asList(columnsArray));
        columnsBorrowList.add("borrowDate");
        columnsBorrowList.add("returnDate");
        TableDemo borrowTable = new TableDemo<LoanBook>(loanBooks, columnsBorrowList);
        JScrollPane borrowPanel = new JScrollPane(borrowTable);
        borrowPanel.setBorder(new EmptyBorder(5,5,5,5));

        add(reservePanel);
        add(borrowPanel);
    }
}
