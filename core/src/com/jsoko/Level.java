package com.jsoko;

public class Level {

    private int id;
    private int groupId;
    private int width;
    private int height;
    private String mapData;
    private String solveData;

    @Override
    public String toString() {
        return String.format("Level { id: %d, groupId: %d }", id, groupId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getMapData() {
        return mapData;
    }

    public void setMapData(String mapData) {
        this.mapData = mapData;
    }

    public String getSolveData() {
        return solveData;
    }

    public void setSolveData(String solveData) {
        this.solveData = solveData;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}