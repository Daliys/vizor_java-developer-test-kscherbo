package com.mygdx.file_loader.woodcutter;

public class WoodcutterAllFiles {

    public final WoodcutterClothFiles cloth;
    public final WoodcutterDoubleClothFiles doubleCloth;
    public final WoodcutterHatFiles hat;
    public final WoodcutterDoubleHatFiles doubleHat;
    public final WoodcutterFiles woodcutter;

    public WoodcutterAllFiles(){
        cloth = new WoodcutterClothFiles();
        doubleCloth = new WoodcutterDoubleClothFiles();
        hat = new WoodcutterHatFiles();
        doubleHat = new WoodcutterDoubleHatFiles();
        woodcutter = new WoodcutterFiles();
    }

    public void dispose(){
        cloth.dispose();
        doubleCloth.dispose();
        hat.dispose();
        doubleHat.dispose();
        woodcutter.dispose();
    }


}
