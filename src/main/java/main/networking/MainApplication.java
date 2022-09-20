package main.networking;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.process.*;
import main.transferClass.PrCompanyLogin;
import movie.process.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainApplication extends Application {
    public ListViewController listView;
    public int currentListType;
    public String contains;
    public Text totalMovies;
    public Text totalProfit;
    public String toCompany;
    public TempPane tempPane;
    public AnchorPane currentPane;
    public EditPageController editPageController;
    public TransferPageController transferPageController;
    public ProductionCompany productionCompany;
    public MainApplication main;
    public Socket socket;
    public SocketWrapper socketWrapper;
    public AnchorPane mainPane;
    public HomePageController homeController;
    public Stage stage;
    public String Local;
    public int port;
    public Parent RootOfAll;
    public Text CounterText;
    public double x, y;
    public List<String> Companies;
    private Stage tempStage;
    private Object latestMovie;
    public int currentType;

    @Override
    public void start(Stage stage) throws IOException {
        main = this;
        this.stage = stage;
        Local = "127.0.0.1";
        port = 33333;
        System.out.println("in start");
        LoginPage();
    }

    public void setData(ProductionCompany prData) {
        productionCompany = prData;
    }

    public void setData2(List<String> companies) {
        this.Companies = companies;
    }

    public void connectToServer(String clientName, String password) throws IOException {
        socketWrapper.write(new PrCompanyLogin(clientName, password));
    }

    public void showError(String s1, String s2) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(s1);
                a.setContentText(s2);
                a.show();
            }
        });
    }

    public void showWarning(String s1, String s2) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText(s1);
                a.setContentText(s2);
                a.show();
            }
        });
    }

    public void LoginPage() throws IOException {
        socket = new Socket(Local, port);
        socketWrapper = new SocketWrapper(socket);
        new ReadThreadClient(this);
        socketWrapper.write("start");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        System.out.println(getClass().getResource("LoginPage.fxml"));
        System.out.println(getClass().getResource("HomePage.fxml"));
        System.out.println(getClass().getResource("SearchPage.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Login Page");
        System.out.println("in loginpage");
        stage.setScene(scene);
        stage.setResizable(false);
        LoginPageController controller = loader.getController();
        controller.initiate(this);
        stage.show();
        stage.setOnCloseRequest(event -> {
            event.consume();
            try {
                logout(2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void HomePage() throws IOException {
        currentListType = 0;
        Collections.sort(productionCompany.MovieList, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
                try {
                    RootOfAll = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setScene(new javafx.scene.Scene(RootOfAll));
                stage.setTitle(productionCompany.getCompanyName()+"/Home Page");
                stage.setResizable(false);
                System.out.println("in homepage");
                homeController = loader.getController();
                try {
                    homeController.initiate(main);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setOnCloseRequest(event -> {
                    event.consume();
                    try {
                        logout(1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }
    public void showMovies(AnchorPane pane, List<Movie> Movies, int pageType) throws IOException {
        if(currentListType!=1) Collections.sort(Movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
         Platform.runLater(new Runnable() {
             @Override
             public void run() {
                 pane.getChildren().clear();
                 var loader = new FXMLLoader();
                 loader.setLocation(MainApplication.class.getResource("ListView.fxml"));
                 Parent root = null;
                 try {
                     root = loader.load();
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
                 FadeTransition fadeTransition = new FadeTransition(Duration.millis(750), root);
                 fadeTransition.setFromValue(0);
                 fadeTransition.setToValue(1);
                 fadeTransition.setCycleCount(1);
                 fadeTransition.setInterpolator(Interpolator.EASE_BOTH);
                 fadeTransition.play();
                 pane.getChildren().add(root);
                 ListViewController listViewController = loader.getController();
                 listViewController.setMain(main);
                 try {
                     listViewController.initiate(pane, Movies, pageType);
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
                 stage.show();
             }
         });
    }
    public void SearchPage() throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchPage.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                root.setOnMousePressed(event -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event -> {
                    stage.setX(event.getSceneX()-x);
                    stage.setY(event.getSceneY()-y);
                });
                stage.setScene(new javafx.scene.Scene(root));
                stage.setTitle(productionCompany.getCompanyName()+"/Search Page");
                System.out.println("in searchpage");
                SearchPageController controller = loader.getController();
                try {
                    controller.initiate(main);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.show();
            }
        });
    }

    public void EditPage() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditPage.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                root.setOnMousePressed(event -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event -> {
                    stage.setX(event.getSceneX()-x);
                    stage.setY(event.getSceneY()-y);
                });
                stage.setScene(new javafx.scene.Scene(root));
                stage.setTitle(productionCompany.getCompanyName()+"/Edit Database Page");
                System.out.println("in editpage");
                EditPageController controller = loader.getController();
                try {
                    controller.initiate(main);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.show();
            }
        });
    }

    public void movieDetails(Stage newStage, Movie movie) throws IOException {
        latestMovie = movie;
        tempStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieDetails.fxml"));
        Parent root = loader.load();
        MovieDetailsController p = loader.getController();
        p.setController(this, newStage);
        Scene scene = new javafx.scene.Scene(root);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(750), root);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.setInterpolator(Interpolator.EASE_IN);
        fadeTransition.play();
        newStage.setScene(scene);
        newStage.setTitle("Move Details");
        newStage.setResizable(false);
        p.initiate(movie);
        if (!newStage.isShowing()) {
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        }
    }

    public void TransferPage() {
        currentListType = 0;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("TransferPage.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                root.setOnMousePressed(event -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged(event -> {
                    stage.setX(event.getSceneX() - x);
                    stage.setY(event.getSceneY() - y);
                });
                stage.setScene(new javafx.scene.Scene(root));
                stage.setTitle(productionCompany.getCompanyName() + "/Transfer Page");
                System.out.println("in transferpage");
                TransferPageController controller = loader.getController();
                try {
                    controller.initiate(main);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.show();
            }
        });
    }

    public void removelist(Movie movie) throws IOException {
        showMovies.printMovie(movie);
        for(int i=0;i<productionCompany.MovieList.size();i++) {
            if(productionCompany.MovieList.get(i).getTitle().equalsIgnoreCase(movie.getTitle())) {
                productionCompany.MovieList.remove(i);
                break;
            }
        }
        socketWrapper.write(productionCompany);
        showMovies(currentPane, productionCompany.getMovieList(), currentType);
        totalMovies.setText("Total Movies :     " + productionCompany.MovieList.size());
        totalProfit.setText("Total Profit :     $" + new ProductionManage(productionCompany.MovieList).totalProfit(productionCompany.MovieList));
    }

    public void updatelist(Movie movie) throws IOException {
        showMovies.printMovie(movie);
        movie.setProduction_Company(productionCompany.getCompanyName());
        productionCompany.MovieList.add(movie);
        socketWrapper.write(productionCompany);
        if(!productionCompany.MovieList.contains(movie)) productionCompany.MovieList.add(movie);
        ProductionManage prManage = new ProductionManage(productionCompany.MovieList);
        SearchMovies search = new SearchMovies(productionCompany.MovieList);
        totalMovies.setText("Total Movies :     " + productionCompany.MovieList.size());
        totalProfit.setText("Total Profit :     $" + prManage.totalProfit(productionCompany.MovieList));
        String str1 = prManage.mostRecent(productionCompany.MovieList);
        String str2 = prManage.maxRevenue(productionCompany.MovieList);
        if(currentListType==0) {
            showMovies(currentPane, productionCompany.MovieList, currentType);
        } else if(currentListType==1) {
            showMovies(currentPane, search.searchByProfit(), currentType);
        } else if(currentListType==2) {
            CounterText.setText("$"+str2);
            showMovies(currentPane, prManage.maxRevenueMovies(str2), currentType);
        } else if(currentListType==3) {
            CounterText.setText(str1);
            showMovies(currentPane, prManage.mostRecentMovies(str1), currentType);
        } else if(currentListType==4) {
            showMovies(currentPane, search.searchByTitles(contains), currentType);
        } else if(currentListType==5) {
            showMovies(currentPane, search.searchByReleaseYear(contains), currentType);
        } else if(currentListType==6) {
            String ss[] = contains.split(",");
            showMovies(currentPane, search.searchByRunningTime(Long.parseLong(ss[0]), Long.parseLong(ss[1])), currentType);
        } else if(currentListType==7) {
            showMovies(currentPane, search.searchByGenres(contains), currentType);
        }
    }

    public void logout(int t) throws IOException {
        System.out.println("logging out");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                if(t==1) {
                    a.setHeaderText("LOGOUT Confirmation!!");
                    a.setContentText("Are you sure to Logout from " + productionCompany.getCompanyName() + "?");
                    if(a.showAndWait().get()== ButtonType.OK) {
                        socketWrapper.write(productionCompany);
                        socketWrapper.write("end"+","+productionCompany.getCompanyName());
                        socketWrapper.write("write");
                        try {
                            LoginPage();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else if(t==2) {
                    a.setHeaderText("EXIT Confirmation!!");
                    a.setContentText("Are you sure to Exit the Application?");
                    if(a.showAndWait().get()==ButtonType.OK) {
                        socketWrapper.write("close");
                        stage.close();
                        System.exit(0);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}