package com.mygdx.xmlreader;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.util.Comparator;

public class XMLTileLoader {

    public static Array<XMLTile> ReadXMLTiles(XmlReader.Element root){
        Array<XmlReader.Element> tilesElements = root.getChildByName("items").getChildByName("list").getChildrenByName("Tile");
        System.out.println("tilesElements " + tilesElements.size);

        Array<XMLTile> tiles = new Array<>();

        for(XmlReader.Element element : tilesElements){
            int posX = element.getInt("x");
            int posY = element.getInt("y");
            int width = element.getInt("width");
            int height = element.getInt("height");
            int index = element.getInt("index");
            boolean flipVertical = element.getBoolean("flipVertical");
            boolean flipHorizontal = element.getBoolean("flipHorizontal");
            tiles.add(new XMLTile(posX,posY,width,height,index,flipVertical,flipHorizontal));

        }

        SortTiles(tiles);

        return tiles;
    }

    private static void SortTiles(Array<XMLTile> tiles){
        tiles.sort(new SortByPosY());

        int currentPosY = tiles.get(0).getPosX();

        Array<XMLTile> temp = new Array<>();
        Array<XMLTile> sortedArray = new Array<>();

        for(XMLTile tile: tiles){
            if(tile.getPosY() == currentPosY) temp.add(tile);
            else{
                currentPosY = tile.getPosY();
                temp.sort(new SortByPosX());
                sortedArray.addAll(temp);
                temp.clear();

                temp.add(tile);
            }
        }
        sortedArray.addAll(temp);

        tiles.clear();
        tiles.addAll( sortedArray);
    }


}class SortByPosY implements Comparator<XMLTile> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(XMLTile a,XMLTile b)
    {
        return a.getPosY() - b.getPosY();
    }
}

class SortByPosX implements Comparator<XMLTile> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(XMLTile a,XMLTile b)
    {
        return a.getPosX() - b.getPosX();
    }
}