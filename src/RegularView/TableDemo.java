package RegularView;

import Utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

// tableDemo for initialising table
public class TableDemo<T> extends JTable{
    private List<T> currentData;
    private BasicOperation fileOperation;

    // tableDemo for book
    TableDemo(List<Book> objectData, BookOperation fileOperation) throws IOException, ParseException {
        DefaultTableModel tableModel = new DefaultTableModel(objectData.stream().map(Book::toList).toArray(String[][]::new), fileOperation.getColumnNames()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(tableModel);
        this.fileOperation = fileOperation;
        this.currentData = this.fileOperation.getAllRows();
    }

    // tableDemo for member
    TableDemo(List<Member> objectData, MemberOperation fileOperation) throws IOException, ParseException {
        DefaultTableModel tableModel = new DefaultTableModel(objectData.stream().map(Member::toList).toArray(String[][]::new), fileOperation.getColumnNames()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(tableModel);
        this.fileOperation = fileOperation;
        this.currentData = this.fileOperation.getAllRows();
    }

    // tableDemo for borrow / reserve book
    TableDemo(List<LoanBook> objectData, List<String> columns) {
        DefaultTableModel tableModel = new DefaultTableModel(objectData.stream().map(LoanBook::toList).toArray(String[][]::new), columns.toArray(String[]::new)){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(tableModel);
    }

    public Book getBook(int index) throws IOException, ParseException {
        return (Book) currentData.get(index);
    }

    public Member getMember(int index) throws IOException, ParseException {
        return (Member) currentData.get(index);
    }

    public void setCurrentData(List<T> currentData) {
        this.currentData = currentData;
    }
}
