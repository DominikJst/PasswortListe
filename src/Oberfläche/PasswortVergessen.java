package Oberfläche;

import Classes.MailVersenden;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswortVergessen extends JFrame{
    private JButton jaButton;
    private JButton neinButton;
    private JPanel panel1;

    public PasswortVergessen() {
        jaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MailVersenden versenden = new MailVersenden();
                versenden.mailSenden();
                dispose();
            }
        });
        neinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });
    }

    public void huh () {

        add(panel1);
        setSize(700, 500);
        setTitle("Passwort vergessen");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


}
