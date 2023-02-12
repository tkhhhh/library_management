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

// login interface
public class LoginInterface extends JFrame {
    private JPanel checkPanel, inputPanel;
    private JButton quit, login;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private MemberOperation fileOperation; // operation class for member
    public LoginInterface() throws IOException {
        fileOperation = new MemberOperation();
        Container container = getContentPane();

        inputPanel = new JPanel();
        usernameLabel = new JLabel("username:");
        usernameField = new JTextField(10);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.setBorder(new EmptyBorder(60,60,60,60));
        container.add(inputPanel, BorderLayout.CENTER);

        checkPanel = new JPanel();
        login = new JButton("login");
        quit = new JButton("quit");
        buttonEvent();
        checkPanel.add(login);
        checkPanel.add(quit);
        checkPanel.setBorder(new EmptyBorder(10,10,10,10));
        container.add(checkPanel, BorderLayout.SOUTH);

        setTitle("Library");
        setSize(360,240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    // button event setting
    private void buttonEvent() {
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(usernameField.getText().equals("admin")) {
                        Member member = new Staff();
                        member.setUsername("admin");
                        member.setName("admin");
                        new UserBaseInterface(member);
                    }
                    else {
                        Member member = fileOperation.checkLogin(usernameField.getText());
                        if(member != null){
                            boolean inRecord = false;
                            for(Member item: Globals.members) {
                                if(item.equals(member)) inRecord = true;
                            }
                            // check or load this member in the record
                            if(!inRecord) Globals.members.add(member);
                            new UserBaseInterface(member);
                            dispose();
                        }

                        else {
                            JOptionPane.showMessageDialog(checkPanel,"invalid username");
                        }
                    }
                } catch (IOException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
