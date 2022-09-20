package main.networking;

import main.transferClass.CompanyList;
import main.transferClass.MovieTransfer;
import main.transferClass.PrCompanyLogin;
import movie.process.*;

import java.util.HashMap;
import java.util.List;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private SocketWrapper socketWrapper;
    private HashMap<String, SocketWrapper> clientMap;
    private HashMap<String, ProductionCompany> MovieMap;
    private List<String> companies;

    public ReadThreadServer(SocketWrapper socketWrapper, List<String> companies, HashMap<String, SocketWrapper> socketMap, HashMap<String, ProductionCompany> MovieMap) {
        this.clientMap = socketMap;
        this.socketWrapper = socketWrapper;
        this.companies = companies;
        this.MovieMap = MovieMap;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if(o instanceof String) {
                    String str = (String) o;
                    String s[] = str.split(",");
                    if(str.equals("start")) {
                        socketWrapper.write(new CompanyList(companies));
                    } else if(str.equals("write")) {
                        System.out.println("Writing in Files");
                        List<Movie> allMovies = ioFromFile.MapToList(companies, MovieMap);
                        ioFromFile.outputMovies(allMovies, Server.FILE_NAME);
                        ioFromFile.outputPassword(companies, MovieMap, Server.FILE_NAME2);
                        ioFromFile.outputLink(Server.FILE_NAME3, allMovies, 0);
                        ioFromFile.outputLink(Server.FILE_NAME4, allMovies, 1);
                    } else if(s[0].equals("end")) {
                        clientMap.remove(s[1]);
                    } else if(str.equals("close")) {
                        socketWrapper.closeConnection();
                        System.out.println("Client Connection closed");
                    }
                }
                else if(o instanceof PrCompanyLogin) {
                    PrCompanyLogin obj = (PrCompanyLogin) o;
                    String clientName = obj.getClientName();
                    String password = obj.getPassword();
                    if(clientMap!=null && clientMap.containsKey(clientName)) {
                        socketWrapper.write("Logged");
                    }
                    else {
                        if (MovieMap.get(clientName).getPassword().equals(password)) {
                            System.out.println("000000");
                            clientMap.put(clientName, socketWrapper);
                            socketWrapper.write(MovieMap.get(clientName));
                            socketWrapper.write("home");
                            System.out.println("000000");
                        } else {
                            System.out.println("Hello");
                            socketWrapper.write("Password");
                        }
                    }
                } else if (o instanceof ProductionCompany) {
                    ProductionCompany pr = (ProductionCompany) o;
                    socketWrapper.write(pr);
                    MovieMap.remove(pr.getCompanyName());
                    MovieMap.put(pr.getCompanyName(), pr);
                } else if (o instanceof MovieTransfer) {
                    MovieTransfer obj = (MovieTransfer) o;
                    if((new SearchMovies(MovieMap.get(obj.getTo()).MovieList).searchByTitle(obj.getData().getTitle()))!=null) {
                        System.out.println(obj.getTo() + " already has the movie");
                        clientMap.get(obj.getFrom()).write("notSendMovie");
                    }
                    else {
                        System.out.println("Transferring Movie from "+obj.getFrom()+" to " +obj.getTo());
                        clientMap.get(obj.getFrom()).write("sendMovie");
                        clientMap.get(obj.getFrom()).write(obj);
                        if(clientMap.containsKey(obj.getTo())) clientMap.get(obj.getTo()).write(obj);
                        else {
                            obj.getData().setProduction_Company(obj.getTo());
                            MovieMap.get(obj.getTo()).MovieList.add(obj.getData());
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error in ReadServer");
            e.printStackTrace();
        }
        finally {
            socketWrapper.closeConnection();
        }
    }
}