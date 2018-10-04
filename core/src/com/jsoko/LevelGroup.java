package com.jsoko;

import com.badlogic.gdx.utils.Array;

public class LevelGroup {

    private int id;
    private int passedLevels;
    private int levelsCount;
    private Array<Level> levels;
    private String name;
    private String title;

    public Level getLevel(int id) {
        return levels.get(id);
    }

    @Override
    public String toString() {
        return String.format("LevelGroup { id: %d, name: %s, passedLevels: %d, levelsCount: %d }",
                             id, name, passedLevels, levelsCount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevelsCount() {
        return levelsCount;
    }

    public void setLevelsCount(int levelsCount) {
        this.levelsCount = levelsCount;
    }

    public Array<Level> getLevels() {
        return levels;
    }

    public void setLevels(Array<Level> levels) {
        this.levels = levels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassedLevels() {
        return passedLevels;
    }

    public void setPassedLevels(int passedLevels) {
        this.passedLevels = passedLevels;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
