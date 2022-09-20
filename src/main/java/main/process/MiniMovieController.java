package main.process;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.networking.MainApplication;
import main.transferClass.MovieTransfer;
import movie.process.Movie;

import java.io.IOException;

public class MiniMovieController {
    private int type;
    private MainApplication application;
    private Movie movie;
    public VBox vBox;
    public Label TitleLabel;
    public Label YearLabel;
    public Label RunTimeLabel;
    public Label ProfitLabel;
    public Button transformButton;

    public void showDetails(ActionEvent actionEvent) throws IOException {
        application.movieDetails(new Stage(), movie);
    }

    public void showPage(ActionEvent actionEvent) throws IOException {
        if(type==1) {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("EditMovie.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Edit Movie/" + movie.getTitle());
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            EditMovieController controller = loader.getController();
            controller.initiate(movie, application, stage);
            if(!stage.isShowing()) {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }
        } else if(type==2) {
            if(application.Companies.contains(application.toCompany)) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("TRANSFER Confirmation!!");
                a.setContentText("Are you sure to transfer movie to " + application.toCompany + "?");
                if(a.showAndWait().get()== ButtonType.OK) {
                    application.socketWrapper.write(new MovieTransfer(application.productionCompany.getCompanyName(), application.toCompany, movie));
                }
            }
            else {
                application.showError("TRANSFER Error!!", "Production Company has not been selected");
            }
        }
    }

    public void setMain(MainApplication application) {
        this.application = application;
    }

    public VBox initiate(Movie m, int pageType) {
        movie = m;
        type = pageType;
        TitleLabel.setText("  Title : " + m.getTitle());
        YearLabel.setText("  Year of Release : " + m.getYear_of_Release());
        ProfitLabel.setText("  Profit : $" + m.getProfit());
        RunTimeLabel.setText("  Run Time : " + m.getRunning_Time() + " mins");
        if(pageType==0) {
            transformButton.setText(null);
            transformButton.setVisible(false);
        }
        else if(pageType==1) {
            transformButton.setText("Edit");
        }
        else {
            transformButton.setText("Transfer");
        }
        return vBox;
    }
}
