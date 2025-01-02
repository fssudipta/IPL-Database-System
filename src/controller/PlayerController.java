package controller;

import data.db.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerController {

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label playerPositionLabel;

    @FXML
    private Button playerDetailsButton;

    @FXML
    private Button playerSellButton;

    @FXML
    private Label playerPriceLabel;

    private Player player;
    private ClubHomeWindowController clubHomeWindowController;
    //private GuestController guestController;

    @FXML
    void sellPlayer(ActionEvent event) {
        if (player.isInTransferList()) {
            // khareed
            clubHomeWindowController.buyPlayer(player);
        } else {
            // becha
            boolean b = showPlayerSaleConfirmationWindow();
            if (b) clubHomeWindowController.sellPlayer(player.getName(), player.getPrice());
        }
    }

    private boolean showPlayerSaleConfirmationWindow() {
        boolean b = false;
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Sale Confirmation");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXMLs/Sold.fxml"));

            Parent root = fxmlLoader.load();

            SoldController controller = fxmlLoader.getController();
            controller.setPlayer(this.player);
            controller.setStage(window);

            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();

            b = controller.isSaleConfirm();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    @FXML
    void hideSellButton(MouseEvent event) {
        playerSellButton.setVisible(false);
    }

    @FXML
    void showSellButton(MouseEvent event) {
        playerSellButton.setVisible(true);
    }

    @FXML
    void showPlayerDetails(ActionEvent event) {

        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(player.getName());

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXMLs/PlayerDetails.fxml"));
            Parent root = fxmlLoader.load();

            PlayerDetailsController playerDetails = fxmlLoader.getController();
            playerDetails.setData(player);

            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setData(Player player) {
        this.player = player;
        playerNameLabel.setText(player.getName());
        playerPositionLabel.setText(player.getPosition());
        playerPriceLabel.setText("Price: " + String.format("%f", player.getPrice()) + " USD");
        if (player.isInTransferList()) {
            playerSellButton.setText("Buy");
            playerPriceLabel.setVisible(true);
        } else {
            playerSellButton.setText("Sell");
            playerPriceLabel.setVisible(false);
        }
    }
    public void setClubHomeWindowController(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
    }
    public ClubHomeWindowController getClubHomeWindowController() {
        return clubHomeWindowController;
    }

    // public void setGuestController(GuestController guestController) {
    //     this.guestController = guestController;
    // }
}