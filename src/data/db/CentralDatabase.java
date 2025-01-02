package data.db;

import java.util.ArrayList;
import java.util.List;

public class CentralDatabase {
    private List<Player> playerList;
    private List<Country> countryList;
    private List<Club> clubList;
    private List<String> positionList;

    public CentralDatabase() {
        playerList = new ArrayList<>();
        countryList = new ArrayList<>();
        clubList = new ArrayList<>();
        positionList = new ArrayList<>();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public List<Club> getClubList() {
        return clubList;
    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }

    public List<String> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<String> positionList) {
        this.positionList = positionList;
    }

    // returns null if not found
    public Player searchPlayerByName(String name) {
        for (int i = 0; i < this.playerList.size(); i++) {
            Player p = this.playerList.get(i);
            if (p.getName().equalsIgnoreCase(name))
                return p;
        }
        return null;
    }

    public List<Player> searchPlayersByClubAndCountry(String country, String club) {
        List<Player> result = new ArrayList<>();
        for (Player it : this.playerList) {
            if (it.getCountry().equalsIgnoreCase(country)
                    && (it.getClub().equalsIgnoreCase(club) || club.equalsIgnoreCase("ANY"))) {
                result.add(it);
            }
        }
        return result;
    }

    public List<Player> searchPlayerByCountry(String countryName) {
        List<Player> players = new ArrayList<>();
        if (countryName.equalsIgnoreCase("any")) {
            players.addAll(this.playerList);
        } else {
            for (Country c : this.countryList) {
                if (countryName.equalsIgnoreCase(c.getName())) {
                    players.addAll(c.getPlayerList());
                    break;
                }
            }
        }
        return players;
    }

    public List<Player> searchPlayerByPosition(String position) {
        List<Player> players = new ArrayList<>();
        for (Player p : this.playerList) {
            if (p.getPosition().equalsIgnoreCase(position))
                players.add(p);
        }
        return players;
    }

    public int addPlayer(Player player) {
        Player p = searchPlayerByName(player.getName());
        if (p == null) {
            boolean b = checkClubValidity(player.getClub());
            if (!b)
                return -1; // club is full
            modifyClubName(player);
            modifyCountryName(player);
            playerList.add(player);
            updateCountryList(player);
            updateClubList(player);
            return 1;// added
        }
        return 0;// alr present
    }

    public void addPlayer(List<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
            addPlayer(player);
        }
    }

    private void updateCountryList(Player player) {
        for (Country c : countryList) {
            if (c.getName().equalsIgnoreCase(player.getCountry())) {
                c.addPlayer(player);
                return;
            }
        }
        Country c = new Country(player);
        countryList.add(c);
    }

    // returns false if club already has maximum number of players, as club e highest 18-25 jon player thake
    public boolean checkClubValidity(String club) {
        for (Club c : clubList) {
            if (c.getClubName().equalsIgnoreCase(club) && c.getPlayerCount() == 25) {
                return false;
            }
        }
        return true;
    }

    private void updateClubList(Player player) {
        String club = player.getClub();
        for (Club c : this.clubList) {
            if (c.getClubName().equalsIgnoreCase(club)) {
                c.addPlayer(player);
                return;
            }
        }
        Club c = new Club(player);
        clubList.add(c);
    }

    public Club searchClub(String clubName) {
        for (int i = 0; i < this.clubList.size(); i++) {
            Club c = this.clubList.get(i);
            if (c.getClubName().equalsIgnoreCase(clubName))
                return c;
        }
        return null;
    }

    private void modifyClubName(Player player) {
        for (Club club : clubList) {
            if (club.getClubName().equalsIgnoreCase(player.getClub())) {
                player.setClub(club.getClubName());
                return;
            }
        }
    }

    private void modifyCountryName(Player player) {
        for (Country country : countryList) {
            if (country.getName().equalsIgnoreCase(player.getCountry())) {
                player.setCountry(country.getName());
                return;
            }
        }
    }

    public List<Player> searchPlayerByAge(int lo, int hi) {
        List<Player> playerList = new ArrayList<>();
        for (Player p : this.playerList) {
            int age = p.getAge();
            if (age >= lo && age <= hi)
                playerList.add(p);
        }
        return playerList;
    }

    public List<Player> searchPlayerByHeight(double lo, double hi) {
        List<Player> playerList = new ArrayList<>();
        for (Player p : this.playerList) {
            double height = p.getHeight();
            if (height >= lo && height <= hi)
                playerList.add(p);
        }
        return playerList;
    }

    public List<Player> searchPlayerBySalary(int lo, int hi) {
        List<Player> players = new ArrayList<>();
        for (Player p : this.playerList) {
            int salary = p.getWeeklySalary();
            if (salary >= lo && salary <= hi)
                players.add(p);
        }
        return players;
    }

    public void removePlayerFromClub(String playerName) throws Exception {
        Player player = searchPlayerByName(playerName);
        Club club = searchClub(player.getClub());
        club.removePlayer(playerName);
    }
}