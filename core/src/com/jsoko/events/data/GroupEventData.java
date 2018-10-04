package com.jsoko.events.data;

import com.jsoko.LevelGroup;

public class GroupEventData implements EventData {

    private LevelGroup group;

    public LevelGroup getGroup() {
        return group;
    }

    public void setGroup(LevelGroup group) {
        this.group = group;
    }
}
