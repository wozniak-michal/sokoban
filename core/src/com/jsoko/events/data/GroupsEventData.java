package com.jsoko.events.data;

import com.badlogic.gdx.utils.Array;
import com.jsoko.LevelGroup;

public class GroupsEventData implements EventData {

    private Array<LevelGroup> groups;

    public Array<LevelGroup> getGroups() {
        return groups;
    }

    public void setGroups(Array<LevelGroup> groups) {
        this.groups = groups;
    }
}
