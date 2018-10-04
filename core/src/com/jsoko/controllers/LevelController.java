package com.jsoko.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.jsoko.Assets;
import com.jsoko.Constants;
import com.jsoko.Level;
import com.jsoko.LevelGroup;
import com.jsoko.Levels;
import com.jsoko.Utils;

public class LevelController implements Disposable {

    private final Levels levelsProgressWrapper;
    private final Levels levelsWrapper;
    private final Array<LevelGroup> levels;
    private LevelGroup currentGroup;
    private int currentLevelGroup;
    private int currentLevel;

    private boolean progressSaveNeeded;

    public LevelController() {
        this.levelsWrapper = Assets.getLevels();
        this.levels = levelsWrapper.getGroups();
        this.levelsProgressWrapper = loadUserProgress();

        parseUserProgress();
    }

    public LevelGroup getGroupData() {
        return currentGroup;
    }

    public Level getCurrentLevel() {
        return currentGroup.getLevel(currentLevel);
    }

    public Array<LevelGroup> getGroupsData() {
        return levels;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    public void setCurrentGroup(int group) {
        this.currentLevelGroup = group;
        this.currentGroup = levels.get(group);
    }

    private Levels loadUserProgress() {
        FileHandle file = Gdx.files.local(Constants.LEVELS_PROGRESS_FILE);

        if (file.exists()) {
            return Utils.Json.fromJson(Levels.class, file);
        }

        return Levels.getDefault();
    }

    private void parseUserProgress() {
        rewriteLevelsData(levelsProgressWrapper, levelsWrapper);
    }

    public void saveUserProgress() {
        if (progressSaveNeeded) {

            Array<LevelGroup> progressGroups = levelsProgressWrapper.getGroups();

            if (progressGroups == null) {
                progressGroups = new Array<>(levels.size);
                levelsProgressWrapper.setGroups(progressGroups);
            }

            rewriteLevelsData(levelsWrapper, levelsProgressWrapper);

            String progressString = Utils.Json.toJson(levelsProgressWrapper, Levels.class);
            Gdx.files.local(Constants.LEVELS_PROGRESS_FILE).writeString(progressString, false);

            progressSaveNeeded = false;
        }
    }

    public boolean nextLevel() {
        int groupPassedLevels = currentGroup.getPassedLevels();
        int groupLevelCount = currentGroup.getLevelsCount();

        // update passed levels
        if (currentLevel > groupPassedLevels) {
            currentGroup.setPassedLevels(currentLevel);
            progressSaveNeeded = true;
        }

        // proceed to next level
        currentLevel++;

        // if exceeding level count, go to next group
        if (currentLevel > groupLevelCount) {
            currentLevelGroup++;

            if (currentLevelGroup > levels.size) {
                return false;
            } else {
                setCurrentGroup(currentLevelGroup);
                setCurrentLevel(1);
            }
        }

        return true;
    }

    private void rewriteLevelsData(Levels src, Levels dst) {
        Array<LevelGroup> srcGroups = src.getGroups();
        Array<LevelGroup> dstGroups = dst.getGroups();

        if (srcGroups == null) return;
        if (dstGroups == null) return;

        if (srcGroups.size > dstGroups.size) {
            for (LevelGroup srcGroup : srcGroups) {
                int id = srcGroup.getId();
                int passedLevels = srcGroup.getPassedLevels();

                LevelGroup dstGroup = new LevelGroup();
                dstGroup.setId(id);
                dstGroup.setPassedLevels(passedLevels);
                dstGroups.add(dstGroup);
            }
        } else {
            for (LevelGroup srcGroup : srcGroups) {
                int id = srcGroup.getId();
                int passedLevels = srcGroup.getPassedLevels();

                LevelGroup dstGroup = dstGroups.get(id);
                dstGroup.setPassedLevels(passedLevels);
            }
        }
    }

    @Override
    public void dispose() {

    }
}
