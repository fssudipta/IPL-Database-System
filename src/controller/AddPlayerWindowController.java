package controller;

import data.db.Club;
import data.db.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import server.FileOperations;

public class AddPlayerWindowController {

    @FXML
    private TextField playerNameField;
    @FXML
    private TextField playerCountryField;
    @FXML
    private TextField playerAgeField;
    @FXML
    private TextField playerHeightField;
    @FXML
    private TextField playerClubField;
    @FXML
    private TextField playerPositionField;
    @FXML
    private TextField playerJerseyNumberField;
    @FXML
    private TextField playerWeeklySalaryField;

    private Club club;
    private ClubHomeWindowController clubHomeWindowController;
    private FileOperations fileOperations = new FileOperations();

    public void initData(Club club, ClubHomeWindowController clubHomeWindowController) {
        this.club = club;
        this.clubHomeWindowController = clubHomeWindowController;
    }

    @FXML
    public void addPlayer(ActionEvent event) {
        try {
            String name = playerNameField.getText();
            String country = playerCountryField.getText();
            int age = Integer.parseInt(playerAgeField.getText());
            double height = Double.parseDouble(playerHeightField.getText());
            String clubName = playerClubField.getText();
            String position = playerPositionField.getText();
            if (position.equalsIgnoreCase("bowler"))
                position = "Bowler";
            else if (position.equalsIgnoreCase("batsman"))
                position = "Batsman";
            else if (position.equalsIgnoreCase("allrounder"))
                position = "Allrounder";
            else if (position.equalsIgnoreCase("wicketkeeper"))
                position = "Wicketkeeper";
            Integer jerseyNumber = playerJerseyNumberField.getText().isEmpty() ? null
                    : Integer.parseInt(playerJerseyNumberField.getText());
            int weeklySalary = Integer.parseInt(playerWeeklySalaryField.getText());

            Player newPlayer = new Player(name, country, age, height, club.getClubName(), position, jerseyNumber,
                    weeklySalary);
            club.addPlayer(newPlayer);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // jhamela?
            // accha added refresh
            clubHomeWindowController.loadPlayerCards(club.getPlayers());
            System.out.println("Player added to club: " + newPlayer);
            try {
                fileOperations.writeToFile(newPlayer);
            } catch (Exception e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
}
