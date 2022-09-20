package main.networking;

import main.transferClass.CompanyList;
import main.transferClass.MovieTransfer;
import movie.process.ProductionCompany;

public class ReadThreadClient implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    private MainApplication application;

    public ReadThreadClient(MainApplication application) {
        this.application = application;
        this.socketWrapper = application.socketWrapper;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if(o instanceof String) {
                    String str = (String) o;
                    if(str.equals("Logged")) {
                        application.showError("LOGIN Unsuccessful!!", "The Production Company already Logged in");
                    } else if(str.equals("Password")) {
                        application.showError("LOGIN Unsuccessful!!", "Password did not match");
                    } else if(str.equals("home")) {
                        application.HomePage();
                    } else if(str.equals("notSendMovie")) {
                        application.showError("TRANSFER Error!!", application.toCompany+" already has the movie");
                    } else if(str.equals("sendMovie")) {
                        System.out.println("Sending Movie successful");
                    }
                }
                else if (o instanceof ProductionCompany) {
                    System.out.println("111111");
                    ProductionCompany companyData = (ProductionCompany) o;
                    application.setData(companyData);
                }
                else if (o instanceof CompanyList) {
                    CompanyList comp = (CompanyList) o;
                    application.setData2(comp.companies);
                }
                else if (o instanceof MovieTransfer) {
                    MovieTransfer obj = (MovieTransfer) o;
                    if(application.productionCompany.getCompanyName().equals(obj.getFrom())) {
                        System.out.println("Movie removed");
                        application.removelist(obj.getData());
                    } else if(application.productionCompany.getCompanyName().equals(obj.getTo())) {
                        System.out.println("Movie Added");
                        application.updatelist(obj.getData());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in ReadClient");
            e.printStackTrace();
        } finally {
            socketWrapper.closeConnection();
        }
    }
}



