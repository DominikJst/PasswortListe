package Oberfläche;

import Classes.Verschluesseln;
import org.apache.commons.lang.RandomStringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswortAutomGenerieren extends JFrame{
    private JTextField textField1;
    private JPanel panel1;
    private JTextField textField2;
    private JButton erstellenButton;

    public void openPassGenerieren(){

        add(panel1);
        setSize(300, 400);
        setTitle("Passwort Generator");
        setVisible(true);
    }

    public PasswortAutomGenerieren() {
        erstellenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                passGenerieren();
                dispose();
            }
        });
    }

    public void passGenerieren() {

        String plattform = textField1.getText();
        String username = textField2.getText();

        String passwort = RandomStringUtils.randomAlphanumeric(10);


        Verschluesseln verschluesseln = new Verschluesseln();

        verschluesseln.AccountDatenVerschluesseln(plattform, username, passwort);
    }
}
