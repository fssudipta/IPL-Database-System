package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RegWindowController {

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button resetButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private PasswordField retypePasswordTextField;

    @FXML
    private ImageView clubLogo;

    private Client client;

    @FXML
    void register(ActionEvent event) {
        String password = passwordTextField.getText();
        String retypePassword = retypePasswordTextField.getText();

        if (password.isBlank() || retypePassword.isBlank()) {
            showAlert("Password field cannot be empty.");
        } else if (!password.equals(retypePassword)) {
            showAlert("Retyped password did not match.");
            reset(event);
        } else {
            client.registerClub(userNameLabel.getText(), password);
        }
    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Registration not successful");
        alert.setContentText(text);
        alert.showAndWait();
    }

    @FXML
    void reset(ActionEvent event) {
        passwordTextField.clear();
        retypePasswordTextField.clear();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUserNameLabelText(String userName) {
        if (this.userNameLabel != null) {
            this.userNameLabel.setText(userName);
            setClubLogo(userName); // Club logo set korlam
        } else {
            System.err.println("Error: userNameLabel is null.");
        }
    }

    private void setClubLogo(String clubName) {
        // onno file theke anlam
        String sanitizedClubName = clubName.replaceAll("\\s+", "");
        String logoPath = "/images/" + sanitizedClubName + ".png";

        try {
            Image logoImage = new Image(getClass().getResourceAsStream(logoPath));
            clubLogo.setImage(logoImage);
        } catch (Exception e) {
            System.err.println("Logo not found for: " + clubName);
            clubLogo.setImage(new Image(getClass().getResourceAsStream("/images/defaultLogo.png"))); // Default logo
        }
    }
}
