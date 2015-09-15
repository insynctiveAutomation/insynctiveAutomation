package insynctive.utils;

public class LoginData {
    protected  String userName;
    protected  String pass;

    public LoginData(String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
    }

    public LoginData() {

    }
    public String getUserName() {
        return userName;
    }

    public String getPass() {
        return pass;
    }
}
