package Objekte;

public class Accounts {
    public String passwort;
    public String username;
    public String plattform;

    public Accounts(String passwort, String username, String plattform){
        this.passwort = passwort;
        this.username = username;
        this.plattform = plattform;
    }

    public String getPasswort(){
        return passwort;
    }

    public void setPasswort(String passwort){
        this.passwort = passwort;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(){
        this.username = username;
    }

    public String getPlattform(){
        return plattform;
    }

    public void setPlatttform(){
        this.plattform = plattform;
    }
}
