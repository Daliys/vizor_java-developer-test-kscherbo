package com.mygdx.file_loader;

import com.mygdx.file_loader.woodcutter.WoodcutterAllFiles;

public class FilesLoader {

    public final BuildingFiles buildings;
    public final TreesFiles trees;
    public final MapFiles map;
    public final WoodcutterAllFiles woodcutter;

    public FilesLoader(){
        buildings = new BuildingFiles();
        trees = new TreesFiles();
        map = new MapFiles();
        woodcutter = new WoodcutterAllFiles();
    }


    public void dispose(){
        buildings.dispose();
        trees.dispose();
        map.dispose();
        woodcutter.dispose();

    }


}
