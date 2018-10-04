package com.jsoko;

public class Settings {

    private String title;
    private String version;

    private int maxUndoMoves;

    public static Settings getDefault() {
        Settings settings = new Settings();
        settings.setTitle("Sokoban");
        settings.setVersion("0");
        settings.setMaxUndoMoves(10);
        return settings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getMaxUndoMoves() {
        return maxUndoMoves;
    }

    public void setMaxUndoMoves(int maxUndoMoves) {
        this.maxUndoMoves = maxUndoMoves;
    }
}
