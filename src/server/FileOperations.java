package server;

import data.db.CentralDatabase;
import data.db.Player;

import java.io.*;
import java.util.List;

public class FileOperations {
    private String inputFileName;
    private String outputFileName;

    public FileOperations() {
        inputFileName = outputFileName = "players.txt";
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void readFromFile(CentralDatabase db) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFileName));

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");

            Player player = new Player();
            player.setName(tokens[0]);
            player.setCountry(tokens[1]);
            player.setAge(Integer.parseInt(tokens[2]));
            player.setHeight(Double.parseDouble(tokens[3]));
            player.setClub(tokens[4]);
            player.setPosition(tokens[5]);
            player.setJerseyNumber(tokens[6].isEmpty() ? null : Integer.parseInt(tokens[6]));
            player.setWeeklySalary(Integer.parseInt(tokens[7]));
            int check = db.addPlayer(player);
            // check if player is added successfully
        }
        br.close();
    }

    public void writeToFile(List<Player> players) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));
        for (Player player : players) {
            bw.write(player.getName() + ",");
            bw.write(player.getCountry() + ",");
            bw.write(player.getAge() + ",");
            bw.write(player.getHeight() + ",");
            bw.write(player.getClub() + ",");
            bw.write(player.getPosition() + ",");
            bw.write(player.getJerseyNumber() + ",");
            bw.write(player.getWeeklySalary() + "");
            bw.write("\n");
        }
        bw.close();
    }

    public void writeToFile(Player player) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName, true));

        bw.write(player.getName() + ",");
        bw.write(player.getCountry() + ",");
        bw.write(player.getAge() + ",");
        bw.write(player.getHeight() + ",");
        bw.write(player.getClub() + ",");
        bw.write(player.getPosition() + ",");
        bw.write(player.getJerseyNumber() + ",");
        bw.write(player.getWeeklySalary() + "");
        bw.write("\n");

        bw.close();
    }
}