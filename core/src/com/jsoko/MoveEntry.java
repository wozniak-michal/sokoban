package com.jsoko;

import com.jsoko.objects.GameObject;

public class MoveEntry {

    public GameObject leftObject, rightObject;
    public Move move;

    public MoveEntry() {
    }

    public static MoveEntry create(GameObject left, GameObject right, Move move) {
        MoveEntry entry = new MoveEntry();
        entry.leftObject = left;
        entry.rightObject = right;
        entry.move = move;
        return entry;
    }
}
