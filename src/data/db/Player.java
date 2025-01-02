package data.db;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String country;
    private int age;
    private double height;
    private String club;
    private String position;
    private Integer jerseyNumber; // may be n/a as well
    private int weeklySalary;
    private double price;
    private boolean isInTransferList;

    public Player(String name, String country, int age, double height, String club, String position, Integer jerseyNumber, int weeklySalary) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.club = club;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.weeklySalary = weeklySalary;
    }

    public Player() {
        // Default constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public int getWeeklySalary() {
        return weeklySalary;
    }

    public void setWeeklySalary(int weeklySalary) {
        this.weeklySalary = weeklySalary;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInTransferList() {
        return isInTransferList;
    }

    public void setInTransferList(boolean inTransferList) {
        isInTransferList = inTransferList;
    }

    @Override
    public String toString() {
        return name + "," + country + "," + age + "," + height + "," + club + "," + position + "," +
                (jerseyNumber != null ? jerseyNumber : "") + "," + weeklySalary;
    } //jersey number ta check korsi
}
