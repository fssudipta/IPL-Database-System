package data.db;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Club implements java.io.Serializable {
    private List<Player> players;
    private String clubName;

    public Club() {
        players = new ArrayList<>();
    }

    public Club(List<Player> players, String clubName) {
        this.players = players;
        this.clubName = clubName;
    }

    public Club(Player player) {
        players = new ArrayList<>();
        setClubName(player.getClub());
        addPlayer(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> searchPlayerWithMaxSalary() {
        int max = -1;
        List<Player> maxSalaryPlayers = new ArrayList<>();
        for (Player it : players) {
            if (it.getClub().equalsIgnoreCase(clubName)) {
                if (it.getWeeklySalary() > max) {
                    max = it.getWeeklySalary();
                    maxSalaryPlayers.clear();
                    maxSalaryPlayers.add(it);
                } else if (it.getWeeklySalary() == max) {
                    maxSalaryPlayers.add(it);
                }
            }
        }
        return maxSalaryPlayers;
    }

    public List<Player> searchPlayersWithMaxAge() {
        int maxAge = -1;
        List<Player> maxAgePlayers = new ArrayList<>();

        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(clubName)) {
                if (player.getAge() > maxAge) {
                    maxAge = player.getAge();
                    maxAgePlayers.clear();
                    maxAgePlayers.add(player);
                } else if (player.getAge() == maxAge) {
                    maxAgePlayers.add(player);
                }
            }
        }
        return maxAgePlayers;
    }

    public List<Player> searchPlayersWithMaxHeight() {
        double maxHeight = -1;
        List<Player> maxHeightPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(clubName)) {
                if (player.getHeight() > maxHeight) {
                    maxHeight = player.getHeight();
                    maxHeightPlayers.clear();
                    maxHeightPlayers.add(player);
                } else if (player.getHeight() == maxHeight) {
                    maxHeightPlayers.add(player);
                }
            }
        }
        return maxHeightPlayers;
    }

    public long calculateTotalYearlySalary() {
        long totalYearlySalary = 0;

        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(clubName)) {
                totalYearlySalary += (long) (player.getWeeklySalary() * 52L);
            }
        }
        return totalYearlySalary;
    }

    public void removePlayer(String playerName) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equalsIgnoreCase(playerName)) {
                players.remove(i);
                return;
            }
        }
    }

    public List<String> getPositionList() {
        Set<String> positionSet = new LinkedHashSet<>();
        this.players.forEach(e -> positionSet.add(e.getPosition()));
        return new ArrayList<>(positionSet);
    }

    public List<String> getCountryList() {
        Set<String> countrySet = new LinkedHashSet<>();
        this.players.forEach(e -> countrySet.add(e.getCountry()));
        return new ArrayList<>(countrySet);
    }
}
