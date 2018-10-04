package com.jsoko;

import com.badlogic.gdx.utils.Array;

public class Levels {
    private Array<LevelGroup> groups;

    public static Levels getDefault() {
        return new Levels();
    }

    public Array<LevelGroup> getGroups() {
        return groups;
    }

    public void setGroups(Array<LevelGroup> groups) {
        this.groups = groups;
    }
}
