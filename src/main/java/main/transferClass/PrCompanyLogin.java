package main.transferClass;

import java.io.Serializable;

public class PrCompanyLogin implements Serializable {
    private String clientName;
    private String password;
    public PrCompanyLogin(String s1, String s2){
        clientName = s1;
        password = s2;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
