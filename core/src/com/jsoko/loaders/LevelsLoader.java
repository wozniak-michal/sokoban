package com.jsoko.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.jsoko.Levels;
import com.jsoko.Utils;

public class LevelsLoader extends AsynchronousAssetLoader<Levels, LevelsLoader.LevelsParameter> {

    public LevelsLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, LevelsParameter parameter) {
    }

    @Override
    public Levels loadSync(AssetManager manager, String fileName, FileHandle file, LevelsParameter parameter) {
        if (file.exists()) {
            return Utils.Json.fromJson(Levels.class, file);
        }

        return Levels.getDefault();
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, LevelsParameter parameter) {
        return null;
    }

    static public class LevelsParameter extends AssetLoaderParameters<Levels> {
    }
}
