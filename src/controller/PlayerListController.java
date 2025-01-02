package controller;

import data.db.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.List;

public class PlayerListController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    private ClubHomeWindowController clubHomeWindowController;
    private GuestController guestController;

    public void loadPlayerCards(List<Player> playerList) {
        try {
            int row = 0;
            int col = 0;
            for (Player player :
                    playerList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FXMLs/Player.fxml"));
                Parent card = fxmlLoader.load();
                PlayerController playerCardController = fxmlLoader.getController();
                playerCardController.setData(player);
                playerCardController.setClubHomeWindowController(this.clubHomeWindowController);
                gridPane.add(card, col, row++);
                card.getStyleClass().add(player.getClub().replace(' ', '_'));
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);
                double width = 464;
                gridPane.setMinWidth(width);
                gridPane.setPrefWidth(width);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(card, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public void loadPlayerCards2(List<Player> playerList) {
    //     try {
    //         int row = 0;
    //         int col = 0;
    //         for (Player player :
    //                 playerList) {
    //             FXMLLoader fxmlLoader = new FXMLLoader();
    //             fxmlLoader.setLocation(getClass().getResource("/FXMLs/Player.fxml"));
    //             Parent card = fxmlLoader.load();
    //             PlayerController playerCardController = fxmlLoader.getController();
    //             playerCardController.setData(player);
    //             playerCardController.setGuestController(this.guestController);
    //             gridPane.add(card, col, row++);
    //             card.getStyleClass().add(player.getClub().replace(' ', '_'));
    //             gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
    //             gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
    //             gridPane.setMaxWidth(Region.USE_PREF_SIZE);
    //             double width = 464;
    //             gridPane.setMinWidth(width);
    //             gridPane.setPrefWidth(width);
    //             gridPane.setMaxWidth(Region.USE_PREF_SIZE);
    //             gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
    //             gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
    //             gridPane.setMaxHeight(Region.USE_PREF_SIZE);

    //             GridPane.setMargin(card, new Insets(10));
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

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