package RegularView;

import Utils.BookOperation;
import Utils.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

// main interface
public class UserBaseInterface extends JFrame {
    private JPanel basicPanel;
    private TablePanel tablePanel;
    private RecordPanel recordPanel;
    private ArrayList<JButton> buttonGroup;
    /* current config name in config file -> normal.properties
        use this attribute to identify the current type of table
     */
    private String currentTableConfig;
    private Member member;
    public UserBaseInterface(Member member) throws IOException, ParseException {
        this.member = member;
        generateButtons();
        basicPanel = new JPanel();
        basicPanel.setLayout(new BoxLayout(basicPanel, BoxLayout.Y_AXIS));

        JLabel nameField = new JLabel("Hello "+ member.getName());
        JButton exit = new JButton("exit");
        exit.setPreferredSize(buttonGroup.get(0).getPreferredSize());
        basicPanel.add(nameField);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        basicPanel.add(exit);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new LoginInterface();
                    dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonGroup.forEach(button -> {
            button.setMargin(new Insets(5,5,5,5));
            basicPanel.add(button);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
        });
        currentTableConfig = "book_path";
        tablePanel = new TablePanel(currentTableConfig, member);

        add(basicPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Home Page");
        setSize(1000, 800);
        pack();
        setVisible(true);
    }

    // initialise buttons and bind events
    private void generateButtons() {
        buttonGroup = new ArrayList<>();
        JButton memberManagement, bookManagement, recordManagement;
        memberManagement = new JButton("memberManagement");
        memberManagement.setName("member_path");
        bookManagement = new JButton("bookManagement");
        bookManagement.setName("book_path");
        recordManagement = new JButton("recordManagement");
        recordManagement.setName("record_path");

        memberManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(e);
            }
        });
        memberManagement.setEnabled(member.getUsername().equals("admin"));
        bookManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(e);
            }
        });
        recordManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(e);
            }
        });

        buttonGroup.add(bookManagement);
        buttonGroup.add(memberManagement);
        buttonGroup.add(recordManagement);
    }

    // change type of table
    private void updateTable(ActionEvent e) {
        try {
            String newConfig = ((JButton)e.getSource()).getName();
            if(!newConfig.equals(currentTableConfig)) {
                if(currentTableConfig.equals("record_path")) {
                    remove(recordPanel);
                } else {
                    remove(tablePanel);
                }
                if(newConfig.equals("record_path")) {
                    recordPanel = new RecordPanel(member, new BookOperation());
                    add(recordPanel, BorderLayout.CENTER);
                } else {
                    tablePanel = new TablePanel(newConfig, member);
                    add(tablePanel, BorderLayout.CENTER);
                }
                currentTableConfig = newConfig;
                pack();
                revalidate();
                repaint();
            }
        } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
