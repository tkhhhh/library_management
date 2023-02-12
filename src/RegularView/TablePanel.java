package RegularView;

import Utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

// tablePanel for operation
public class TablePanel extends JPanel {
    private TableDemo tableDemo;
    private JButton searchButton, refreshButton, createButton;
    private JTextField searchField;
    private JScrollPane tableScroll;

    // config name in config file -> normal.properties
    private String tableConfigName;
    private BasicOperation basicOperation;
    private Member member;

    public TablePanel(String tableConfigName, Member member) throws IOException, ParseException {
        super(new BorderLayout());
        this.tableConfigName = tableConfigName;
        this.member = member;
        if(tableConfigName.equals("member_path")){
            basicOperation = new MemberOperation();
            List<Member> all = basicOperation.getAllRows();
            tableDemo = new TableDemo<Member>(all, (MemberOperation) basicOperation);
        } else {
            basicOperation = new BookOperation();
            List<Book> all = basicOperation.getAllRows();
            tableDemo = new TableDemo<Book>(all, (BookOperation) basicOperation);
        }
        setTableDemo();
        tableScroll = new JScrollPane(tableDemo);

        searchButton = new JButton("search");
        searchField = new JTextField(10);
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchButton);
        searchPanel.add(searchField);

        refreshButton = new JButton("refresh");
        createButton = new JButton("create");
        JPanel editPanel = new JPanel();
        editPanel.add(refreshButton);
        editPanel.add(createButton);

        buttonAction();

        add(searchPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(editPanel, BorderLayout.SOUTH);

        setBorder(new EmptyBorder(10, 30, 10, 10));
    }

    // bind event for buttons
    private void buttonAction() {
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateForm(tableConfigName);
            }
        });
        createButton.setEnabled(member.getUsername().equals("admin"));
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(tableConfigName.equals("member_path")){
                        List<Member> all = basicOperation.getAllRows();
                        DefaultTableModel tableModel = new DefaultTableModel(all.stream().map(Member::toList).toArray(String[][]::new), basicOperation.getColumnNames()){
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        tableDemo.setModel(tableModel);
                        tableDemo.setCurrentData(all);
                    } else {
                        List<Book> all = basicOperation.getAllRows();
                        DefaultTableModel tableModel = new DefaultTableModel(all.stream().map(Book::toList).toArray(String[][]::new), basicOperation.getColumnNames()){
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        tableDemo.setModel(tableModel);
                        tableDemo.setCurrentData(all);
                    }
                    revalidate();
                    repaint();
                } catch (IOException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(tableConfigName.equals("member_path")){
                        List<Member> all = basicOperation.search(searchField.getText());
                        DefaultTableModel tableModel = new DefaultTableModel(all.stream().map(Member::toList).toArray(String[][]::new), basicOperation.getColumnNames()){
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        tableDemo.setModel(tableModel);
                        tableDemo.setCurrentData(all);
                    } else {
                        List<Book> all = basicOperation.search(searchField.getText());
                        DefaultTableModel tableModel = new DefaultTableModel(all.stream().map(Book::toList).toArray(String[][]::new), basicOperation.getColumnNames()){
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        tableDemo.setModel(tableModel);
                        tableDemo.setCurrentData(all);
                    }
                    revalidate();
                    repaint();
                } catch (IOException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // tableDemo setting
    private void setTableDemo() {
        tableDemo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableDemo.setRowSelectionAllowed(true);
        tableDemo.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                tableDemo.clearSelection();
                // click event on every row in the table
                if(!e.getValueIsAdjusting()){
                    int index = e.getLastIndex();
                    if(tableConfigName.equals("book_path")) {
                        Book book = null;
                        try {
                            book = tableDemo.getBook(index);
                        } catch (IOException | ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            new BookDetail(book, member);
                        } catch (IOException | ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if(tableConfigName.equals("member_path")) {
                        Member item = null;
                        try {
                            item = tableDemo.getMember(index);
                        } catch (IOException | ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            new MemberDetail(item, member);
                        } catch (IOException | ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
    }
}