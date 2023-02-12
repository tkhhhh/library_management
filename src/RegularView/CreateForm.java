package RegularView;

import Utils.Book;
import Utils.BookOperation;
import Utils.Member;
import Utils.MemberOperation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

// form format for creating new member or book
public class CreateForm extends JFrame {
    public CreateForm(String tableConfigName) {
        JButton confirmButton = new JButton("confirm");
        getRootPane().setBorder(new EmptyBorder(5,5,5,5));
        // form for creating new member entry
        if(tableConfigName.equals("member_path")) {
            JLabel nameLabel = new JLabel("name: ");
            JLabel usernameLabel = new JLabel("username: ");
            JLabel emailLabel = new JLabel("email: ");
            JLabel levelLabel = new JLabel("level: ");
            JLabel expireDateLabel = new JLabel("expireDate: ");

            JTextField nameTextField = new JTextField(8);
            JTextField usernameTextField = new JTextField(8);
            JTextField emailTextField = new JTextField(8);
            JTextField levelTextField = new JTextField(8);
            JTextField expireDateTextField = new JTextField(8);

            setLayout(new GridLayout(6,2));
            add(nameLabel);
            add(nameTextField);
            add(usernameLabel);
            add(usernameTextField);
            add(emailLabel);
            add(emailTextField);
            add(levelLabel);
            add(levelTextField);
            add(expireDateLabel);
            add(expireDateTextField);

            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Member member = new Member();
                    member.setName(nameTextField.getText());
                    member.setUsername(usernameTextField.getText());
                    member.setEmail(emailTextField.getText());
                    member.setLevel(levelTextField.getText());
                    member.setExpireDate(expireDateTextField.getText());
                    try {
                        MemberOperation memberOperation = new MemberOperation();
                        memberOperation.insert(member);
                        dispose();
                    } catch (IOException | ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            add(confirmButton);
        }
        // form for creating new book entry
        else{
            JLabel titleLabel = new JLabel("title: ");
            JLabel authorsLabel = new JLabel("authors: ");
            JLabel publisherLabel = new JLabel("publisher: ");
            JLabel publishedYearLabel = new JLabel("publishedYear: ");
            JLabel ISBNLabel = new JLabel("ISBN: ");
            JLabel numberLabel = new JLabel("number: ");

            JTextField titleTextField = new JTextField(8);
            JTextField authorsTextField = new JTextField(8);
            JTextField publisherTextField = new JTextField(8);
            JTextField publishedYearTextField = new JTextField(8);
            JTextField ISBNTextField = new JTextField(8);
            JTextField numberTextField = new JTextField(8);

            setLayout(new GridLayout(7,2));
            add(titleLabel);
            add(titleTextField);
            add(authorsLabel);
            add(authorsTextField);
            add(publisherLabel);
            add(publisherTextField);
            add(publishedYearLabel);
            add(publishedYearTextField);
            add(ISBNLabel);
            add(ISBNTextField);
            add(numberLabel);
            add(numberTextField);

            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Book book = new Book();
                    book.setTitle(titleTextField.getText());
                    book.setAuthor(authorsTextField.getText());
                    book.setPublisher(publisherTextField.getText());
                    book.setPublishedYear(publishedYearTextField.getText());
                    book.setISBN(ISBNTextField.getText());
                    book.setNumber(Integer.parseInt(numberTextField.getText()));
                    try {
                        BookOperation bookOperation = new BookOperation();
                        bookOperation.insert(book);
                        dispose();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            add(confirmButton);
        }
        setTitle("New Entry");
        setSize(800, 300);
        pack();
        setVisible(true);
    }
}
