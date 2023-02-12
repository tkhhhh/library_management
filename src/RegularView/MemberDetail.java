package RegularView;

import Utils.Globals;
import Utils.Member;
import Utils.MemberOperation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

// member detail interface
public class MemberDetail extends JFrame {
    private Integer indexOfTable, indexOfRecord;
    private JButton delete, update;
    private Member member, user;
    private JLabel nameLabel, usernameLabel, emailLabel, levelLabel, expireDateLabel;
    private JTextField nameTextField, usernameTextField, emailTextField, levelTextField, expireDateTextField;
    private MemberOperation memberOperation;
    private JPanel infoPanel;
    public MemberDetail(Member member, Member user) throws IOException, ParseException{
        this.member = member;
        this.user = user;
        this.memberOperation = new MemberOperation();
        this.indexOfTable = this.memberOperation.getRowKey(member);
        for(Member item: Globals.members) {
            if(item.equals(member)) {
                this.indexOfRecord = Globals.members.indexOf(item);
            }
        }

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new EmptyBorder(5,5,5,5));

        nameLabel = new JLabel("name: ");
        if(member.getName() == null || member.getName().equals("")) nameTextField = new JTextField(6);
        else nameTextField = new JTextField(member.getName());
        JPanel panel = new JPanel();
        panel.add(nameLabel);
        panel.add(nameTextField);
        infoPanel.add(panel);

        usernameLabel = new JLabel("username: ");
        if(member.getUsername() == null || member.getUsername().equals("")) usernameTextField = new JTextField(6);
        else usernameTextField = new JTextField(member.getUsername());
        panel = new JPanel();
        panel.add(usernameLabel);
        panel.add(usernameTextField);
        infoPanel.add(panel);

        emailLabel = new JLabel("email: ");
        if(member.getEmail() == null || member.getEmail().equals("")) emailTextField = new JTextField(6);
        else emailTextField = new JTextField(member.getEmail());
        panel = new JPanel();
        panel.add(emailLabel);
        panel.add(emailTextField);
        infoPanel.add(panel);

        levelLabel = new JLabel("level: ");
        if(member.getLevel() == null || member.getLevel().equals("")) levelTextField = new JTextField(6);
        else levelTextField = new JTextField(member.getLevel());
        panel = new JPanel();
        panel.add(levelLabel);
        panel.add(levelTextField);
        infoPanel.add(panel);

        expireDateLabel = new JLabel("expireDate: ");
        if(member.getExpireDate() == null || member.getExpireDate().equals("")) expireDateTextField = new JTextField(6);
        else expireDateTextField = new JTextField(member.getExpireDate());
        panel = new JPanel();
        panel.add(expireDateLabel);
        panel.add(expireDateTextField);
        infoPanel.add(panel);

        update = new JButton("update");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // update in Globals.member
                member.setName(nameTextField.getText());
                member.setUsername(usernameTextField.getText());
                member.setEmail(emailTextField.getText());
                member.setLevel(levelTextField.getText());
                member.setExpireDate(expireDateTextField.getText());
                if(indexOfRecord != null) Globals.members.set(indexOfRecord, member);
                try {
                    // update in *.csv
                    memberOperation.update(member, indexOfTable);
                } catch (IOException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        delete = new JButton("delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // update in Globals.member
                Globals.members.removeIf(item -> item.equals(member));
                try {
                    // update in *.csv
                    memberOperation.delete(member);
                    dispose();
                } catch (IOException | ParseException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        panel = new JPanel();
        panel.add(update);
        panel.add(delete);
        infoPanel.add(panel);

        add(infoPanel);
        setTitle("Member Detail");
        setSize(800, 300);
        pack();
        setVisible(true);
    }
}
