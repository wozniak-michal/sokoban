package com.jsoko.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.jsoko.Settings;
import com.jsoko.Utils;

public class SettingsLoader extends AsynchronousAssetLoader<Settings, SettingsLoader.SettingsParameter> {

    public SettingsLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SettingsParameter parameter) {
    }

    @Override
    public Settings loadSync(AssetManager manager, String fileName, FileHandle file, SettingsParameter parameter) {
        if (file.exists()) {
            return Utils.Json.fromJson(Settings.class, file);
        }
        return Settings.getDefault();
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, SettingsParameter parameter) {
        return null;
    }

    static public class SettingsParameter extends AssetLoaderParameters<Settings> {
    }
}
