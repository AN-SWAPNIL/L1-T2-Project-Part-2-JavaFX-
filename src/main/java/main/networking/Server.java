package main.networking;

import movie.process.Movie;
import movie.process.ProductionCompany;
import movie.process.ProductionManage;
import movie.process.ioFromFile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.sort;

public class Server {
    private ServerSocket serverSocket;
    private HashMap<String, ProductionCompany> prMovies;
    private HashMap<String, SocketWrapper> prSocket;
    private List<String> prCompanies;
    private List<Movie> allMovieList;
    public static final String FILE_NAME = "Data/movies.txt";
    public static final String FILE_NAME2 = "Data/prCompanies.txt";
    public static final String FILE_NAME3 = "Data/thumbnail_Links.txt";
    public static final String FILE_NAME4 = "Data/trailers.txt";

    public Server(int port) throws Exception {
        System.out.println("Reading From Files");
        allMovieList = ioFromFile.inputMovies(FILE_NAME);
        ioFromFile.inputLink(FILE_NAME3, allMovieList, 0);
        ioFromFile.inputLink(FILE_NAME4, allMovieList, 1);
        ProductionManage prManage = new ProductionManage(allMovieList);
        prCompanies = prManage.listOfCompany();
        sort(prCompanies);
        prMovies = ioFromFile.ListToMap(prCompanies, allMovieList);
        ioFromFile.inputPassword(FILE_NAME2, prMovies);
        prSocket = new HashMap<>();
        System.out.println("Server is Running...");
        serverSocket = new ServerSocket(port);
    }

    public void serve(Socket clientSocket) throws IOException{
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
        new ReadThreadServer(socketWrapper, prCompanies, prSocket, prMovies);
    }

    public static void main(String[] args) throws Exception {
        int port = 33333;
        Server server = new Server(port);
        try {
            while (true) {
                Socket clientSocket = server.serverSocket.accept();
                System.out.println("new Client Connected");
                server.serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }
}
