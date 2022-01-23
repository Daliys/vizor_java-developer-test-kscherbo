package com.mygdx.xmlreader;

public class XMLTile {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int index;
    private boolean flipVertical;
    private boolean flipHorizontal;

    public XMLTile(int posX, int posY, int width, int height, int index, boolean flipVertical, boolean flipHorizontal){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.index = index;
        this.flipVertical = flipVertical;
        this.flipHorizontal = flipHorizontal;
    }

    public String toString(){
        return "x: " + posX + ", y: " + posY + ", width: " + width + ", height: " + height
                + ", index: " + index + ", flipVertical: " + flipVertical + ", flipHorizontal: " + flipHorizontal;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getIndex() {
        return index;
    }
    public boolean isFlipVertical() {
        return flipVertical;
    }
    public boolean isFlipHorizontal() {
        return flipHorizontal;
    }
}
