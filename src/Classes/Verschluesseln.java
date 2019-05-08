package Classes;

import Oberfläche.AccountsAnlegen;

import javax.xml.bind.DatatypeConverter;

public class Verschluesseln {

    public void AccountDatenVerschluesseln(String plattform, String username, String passwort){

        String usernameEncoded = DatatypeConverter.printBase64Binary(username.getBytes());
        String passwortEncoded = DatatypeConverter.printBase64Binary(passwort.getBytes());

        AccountsAnlegen anlegen = new AccountsAnlegen();

        anlegen.anlegen(passwortEncoded, usernameEncoded, plattform);
    }

}
