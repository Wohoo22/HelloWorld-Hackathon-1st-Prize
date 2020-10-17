package com.hackathon.entity;

import com.hackathon.enums.DieReason;
import com.hackathon.enums.Status;

import java.util.List;

public class Response {
    private List<int[]> userPoints;
    private Map map;
    private boolean rightWay;
    private int dieAtStep;
    private DieReason dieReason;
    private int unlockLevel;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<int[]> getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(List<int[]> userPoints) {
        this.userPoints = userPoints;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isRightWay() {
        return rightWay;
    }

    public void setRightWay(boolean rightWay) {
        this.rightWay = rightWay;
    }

    public int getDieAtStep() {
        return dieAtStep;
    }

    public void setDieAtStep(int dieAtStep) {
        this.dieAtStep = dieAtStep;
    }

    public DieReason getDieReason() {
        return dieReason;
    }

    public void setDieReason(DieReason dieReason) {
        this.dieReason = dieReason;
    }

    public int getUnlockLevel() {
        return unlockLevel;
    }

    public void setUnlockLevel(int unlockLevel) {
        this.unlockLevel = unlockLevel;
    }
}
