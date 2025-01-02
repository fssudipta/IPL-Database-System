package controller;

import data.db.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerDetailsController implements Initializable {

    @FXML
    private ImageView playerImage;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label playerPositionLabel;

    @FXML
    private Label playerClubLabel;

    @FXML
    private Label playerCountryLabel;

    @FXML
    private Label playerAgeLabel;

    @FXML
    private Label playerSalaryLabel;

    @FXML
    private Label playerHeightLabel;

    @FXML
    private Label playerNumberLabel;

    public void setData(Player player) {
        playerNameLabel.setText(player.getName());
        playerPositionLabel.setText(player.getPosition());
        playerClubLabel.setText(player.getClub());
        playerCountryLabel.setText(player.getCountry());
        // playerCountryLabel.setStyle("-fx-font-family: Cambria");
        playerAgeLabel.setText("Age: " + player.getAge() + " years");
        playerHeightLabel.setText("Height: " + player.getHeight() + " meters");
        if (player.getJerseyNumber() != null) {
            playerNumberLabel.setText("Jersey Number: " + player.getJerseyNumber());
        } else {
            playerNumberLabel.setText("Jersey Number: N/A");
        }
        playerSalaryLabel.setText("Weekly Salary: " + String.format("%d", player.getWeeklySalary()) + " USD");
        setPlayerImage(player);
    }

    private void setPlayerImage(Player player) {
        String sanitizedName = player.getName().replaceAll("\\s+", "");
        String picPath = "/images/" + sanitizedName + ".png";

        try {
            Image PlayerImage = new Image(getClass().getResourceAsStream(picPath));
            playerImage.setImage(PlayerImage);
        } catch (Exception e) {
            System.err.println("Image not found for: " + player.getName());
            playerImage.setImage(new Image(getClass().getResourceAsStream("/images/defaultLogo.png"))); // Default logo
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}