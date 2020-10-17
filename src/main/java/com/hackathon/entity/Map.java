package com.hackathon.entity;

import java.util.List;

public class Map {
    private int[] startPoint;
    private List<int[]> rightPoints;
    private List<int[]> fooPoints;
    private int size;
    private List<int[]> monster;
    private List<int[]> userPoints;
    private List<int[]> momPoints;
    private String username;

    public List<int[]> getMomPoints() {
        return momPoints;
    }

    public void setMomPoints(List<int[]> momPoints) {
        this.momPoints = momPoints;
    }

    public int[] getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int[] startPoint) {
        this.startPoint = startPoint;
    }

    public List<int[]> getRightPoints() {
        return rightPoints;
    }

    public void setRightPoints(List<int[]> rightPoints) {
        this.rightPoints = rightPoints;
    }

    public List<int[]> getFooPoints() {
        return fooPoints;
    }

    public void setFooPoints(List<int[]> fooPoints) {
        this.fooPoints = fooPoints;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<int[]> getMonster() {
        return monster;
    }

    public void setMonster(List<int[]> monster) {
        this.monster = monster;
    }

    public List<int[]> getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(List<int[]> userPoints) {
        this.userPoints = userPoints;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
