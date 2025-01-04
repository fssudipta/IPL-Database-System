package controller;

import data.buyNsell.*;
import data.db.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// error ashtese khali, pore dekha lagbe

public class GuestController {

    @FXML
    private Label clubNameLine;

    @FXML
    private Label clubBudgetLabel;

    @FXML
    private Button buyPlayerButton;

    @FXML
    private VBox bodyVBox;

    @FXML
    private HBox topBarHBox;

    @FXML
    private TextField searchPlayerNameTextField;

    @FXML
    private ImageView clubLogoImage;

    @FXML
    private Button searchPlayerNameButton;

    @FXML
    private Button resetPlayerNameButton;

    @FXML
    private MenuButton clubMenuButton;

    @FXML
    private MenuItem usernameMenuItem;

    @FXML
    private HBox listPlayerHBox;

    @FXML
    private VBox playerListVBox;

    @FXML
    private TreeView<CheckBox> filterTreeCountry;

    @FXML
    private TreeView<CheckBox> filterTreePosition;

    @FXML
    private TextField ageFromTextField;

    @FXML
    private TextField ageToTextField;

    @FXML
    private TextField heightFromTextField;

    @FXML
    private TextField salaryFromTextField;

    @FXML
    private TextField heightToTextField;

    @FXML
    private TextField salaryToTextField;

    @FXML
    private Button applyFiltersButton;

    @FXML
    private Button resetFiltersButton;

    @FXML
    private HBox bottomBarHBox;

    @FXML
    private Button addPlayer;

    private Club club;
    private String clubName;
    private List<Player> playerListOnDisplay;
    private Client client;
    private boolean aBoolean = false;
    private CentralDatabase db = new CentralDatabase();


    void loadTransferWindow() {
        List<?> players = this.client.loadTransferList();
        if (players != null) {
            List<Player> playerList = new ArrayList<>();
            for (Object e : players) {
                if (e instanceof Player && !((Player) e).getClub().equals(this.clubName)) {
                    playerList.add((Player) e);
                }
            }
            loadPlayerCards(playerList);
        }
    }

    @FXML
    void searchPlayerByName(ActionEvent event) {
        String playerName = searchPlayerNameTextField.getText().trim();
        CentralDatabase db = new CentralDatabase();
        db.addPlayer(club.getPlayers());
        Player player = db.searchPlayerByName(playerName);
        db.setPlayerList(new ArrayList<>());
        if (player != null) {
            db.getPlayerList().add(player);
        }
        loadPlayerCards(db.getPlayerList());
    }

    @FXML
    void resetPlayerNameTextField(ActionEvent event) {
        searchPlayerNameTextField.setText("");
        loadPlayerCards(db.getPlayerList());
    }

    @FXML
    void applyFilters(ActionEvent event) {
        CentralDatabase db2 = new CentralDatabase();
        db2.addPlayer(db.getPlayerList());
        applyFiltersCountry(db2);
        applyFiltersPosition(db2);
        applyFiltersAge(db2);
        applyFiltersHeight(db2);
        applyFiltersSalary(db2);

        loadPlayerCards(db2.getPlayerList());
    }

    private void applyFiltersSalary(CentralDatabase db2) {
        int lo, hi;
        try {
            lo = Integer.parseInt(salaryFromTextField.getText());
        } catch (Exception e) {
            lo = 0;
            salaryFromTextField.setText(String.valueOf(lo));
        }
        try {
            hi = Integer.parseInt(salaryToTextField.getText());
        } catch (Exception e) {
            hi = club.searchPlayerWithMaxSalary().getFirst().getWeeklySalary();
            salaryToTextField.setText(String.valueOf(hi));
        }
        db2.setPlayerList(db.searchPlayerBySalary(lo, hi));
    }

    private void applyFiltersHeight(CentralDatabase db2) {
        double lo, hi;
        try {
            lo = Double.parseDouble(heightFromTextField.getText());
        } catch (Exception e) {
            lo = 0;
            heightFromTextField.setText(String.valueOf(lo));
        }
        try {
            hi = Double.parseDouble(heightToTextField.getText());
        } catch (Exception e) {
            hi = club.searchPlayersWithMaxHeight().getFirst().getHeight();
            heightToTextField.setText(String.valueOf(hi));
        }
        db2.setPlayerList(db2.searchPlayerByHeight(lo, hi));
    }

    private void applyFiltersAge(CentralDatabase db2) {
        int lo, hi;
        try {
            lo = Integer.parseInt(ageFromTextField.getText());
        } catch (Exception e) {
            lo = 0;
            ageFromTextField.setText(String.valueOf(lo));
        }
        try {
            hi = Integer.parseInt(ageToTextField.getText());
        } catch (Exception e) {
            hi = club.searchPlayersWithMaxAge().getFirst().getAge();
            ageToTextField.setText(String.valueOf(hi));
        }
        db2.setPlayerList(db2.searchPlayerByAge(lo, hi));
    }

    private void applyFiltersPosition(CentralDatabase db2) {
        for (TreeItem<CheckBox> item : filterTreePosition.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                for (Player player : db2.searchPlayerByPosition(item.getValue().getText())) {
                    db2.getPlayerList().remove(player);
                }
            }
        }
    }

    private void applyFiltersCountry(CentralDatabase db2) {
        for (TreeItem<CheckBox> item : filterTreeCountry.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                for (Player player : db2.searchPlayerByCountry(item.getValue().getText())) {
                    db2.getPlayerList().remove(player);
                }
            }
        }
    }

    @FXML
    void resetFilters(ActionEvent event) {
        for (TreeItem<CheckBox> item : filterTreeCountry.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                item.getValue().setSelected(true);
            }
        }
        for (TreeItem<CheckBox> item : filterTreePosition.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                item.getValue().setSelected(true);
            }
        }
        ageFromTextField.setText("");
        ageToTextField.setText("");
        heightFromTextField.setText("");
        heightToTextField.setText("");
        salaryFromTextField.setText("");
        salaryToTextField.setText("");
        applyFilters(event);
    }

    public void init() {
        loadClubData();
        loadPlayerCards(db.getPlayerList());
        makeFilterTree();
        makeMenu();
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    int toRefreshRate(String newValue) {
        String[] choice = newValue.split(" ");
        int rate = Integer.parseInt(choice[0]);
        if (choice[1].charAt(0) == 'm')
            rate *= 60;
        return rate;
    }

    private void makeMenu() {
        clubMenuButton.setText("");
        usernameMenuItem.setText("Signed in as " + clubName);
    }

    private void makeFilterTree() {
        makeFilterTreeCountry();
        makeFilterTreePosition();
    }

    private void makeFilterTreePosition() {
        TreeItem<CheckBox> root = new TreeItem<>();
        root.setExpanded(true);
        db.getPositionList().forEach(e -> makeBranchFilterTree(e, root));
        filterTreePosition.setRoot(root);
        filterTreePosition.setShowRoot(false);
    }

    private void makeFilterTreeCountry() {
        TreeItem<CheckBox> root = new TreeItem<>();
        root.setExpanded(true);
        db.getCountryList().forEach(e -> makeBranchFilterTree(e.getName(), root));
        filterTreeCountry.setRoot(root);
        filterTreeCountry.setShowRoot(false);
    }

    private TreeItem<CheckBox> makeBranchFilterTree(String title, TreeItem<CheckBox> parent) {
        CheckBox checkBox = new CheckBox(title);
        checkBox.setSelected(true);
        TreeItem<CheckBox> item = new TreeItem<>(checkBox);
        parent.getChildren().add(item);
        return item;
    }

    public void loadPlayerCards(List<Player> playerList) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXMLs/PlayerList.fxml"));
            Parent root = fxmlLoader.load();

            PlayerListController playerListViewController = fxmlLoader.getController();
            //playerListViewController.setGuestController(this);
            //playerListViewController.loadPlayerCards2(playerList);

            listPlayerHBox.getChildren().clear();
            listPlayerHBox.getChildren().add(root);

            this.playerListOnDisplay = new ArrayList<>(playerList);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadClubData() {
        this.club = client.loadClubFromServer(this.clubName);
    }

    @FXML
    void listMaxAgePlayers(ActionEvent event) {
        loadPlayerCards(club.searchPlayersWithMaxAge());
    }

    @FXML
    void listMaxHeightPlayers(ActionEvent event) {
        loadPlayerCards(club.searchPlayersWithMaxHeight());
    }

    @FXML
    void listMaxSalaryPlayers(ActionEvent event) {
        loadPlayerCards(club.searchPlayerWithMaxSalary());
    }

    @FXML
    void showTotalYearlySalary(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Total Yearly Salary");
        a.setHeaderText(this.clubName);
        a.setContentText(
                "Total yearly salary is " + String.format("%d", this.club.calculateTotalYearlySalary()) + " USD");
        a.showAndWait();
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @FXML
    void logoutClub(ActionEvent event) {
        client.logoutClub(this.clubName);
    }

    public int getRefreshRate() {
        return 5;
    }

    @FXML
    private void buttonHoverEnter(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(
                "-fx-background-color: #2980b9; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 4);");
    }

    @FXML
    private void buttonHoverExit(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle(
                "-fx-background-color: #3498db; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0, 0, 2);");
    }
}