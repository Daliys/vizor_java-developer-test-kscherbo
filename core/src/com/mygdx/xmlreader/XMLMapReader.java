package com.mygdx.xmlreader;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.*;

import java.util.StringTokenizer;

public class XMLMapReader {

    protected XmlReader xml = new XmlReader();
    protected XmlReader.Element root;
    protected boolean convertObjectToTileSpace;
    protected boolean flipY = true;

    protected int mapTileWidth;
    protected int mapWidth;
    protected int mapTileHeight;
    protected int mapHeight;
    protected int mapWidthInPixels;
    protected int mapHeightInPixels;

    protected TiledMap map;
    protected static final int MASK_CLEAR = 0xE0000000;

    public static class Parameters extends AssetLoaderParameters<TiledMap> {
        /**
         * generate mipmaps?
         **/
        public boolean generateMipMaps = false;
        /**
         * The TextureFilter to use for minification
         **/
        public Texture.TextureFilter textureMinFilter = Texture.TextureFilter.Nearest;
        /**
         * The TextureFilter to use for magnification
         **/
        public Texture.TextureFilter textureMagFilter = Texture.TextureFilter.Nearest;
        /**
         * Whether to convert the objects' pixel position and size to the equivalent in tile space.
         **/
        public boolean convertObjectToTileSpace = false;
        /**
         * Whether to flip all Y coordinates so that Y positive is up. All LibGDX renderers require flipped Y coordinates, and
         * thus flipY set to true. This parameter is included for non-rendering related purposes of TMX files, or custom renderers.
         */
        public boolean flipY = true;
    }

    public TiledMap loadXMLTiledMap(FileHandle file) {
        Parameters parameter = new Parameters();

        FileHandle xmlFile = file;
        root = xml.parse(xmlFile);

        ObjectMap<String, Texture> textures = new ObjectMap<String, Texture>();

        final Array<FileHandle> textureFiles = getDependencyFileHandles(xmlFile);

        for (FileHandle textureFile : textureFiles) {
            Texture texture = new Texture(textureFile, parameter.generateMipMaps);
            texture.setFilter(parameter.textureMinFilter, parameter.textureMagFilter);
            textures.put(textureFile.path(), texture);

        }


        TiledMap map = loadTiledMap(xmlFile, parameter, new ImageResolver.DirectImageResolver(textures));
        map.setOwnedResources(textures.values().toArray());
        return map;

    }

    protected TiledMap loadTiledMap(FileHandle tmxFile, Parameters parameter, ImageResolver imageResolver) {
        this.map = new TiledMap();

        if (parameter != null) {
            this.convertObjectToTileSpace = parameter.convertObjectToTileSpace;
            this.flipY = parameter.flipY;
        } else {
            this.convertObjectToTileSpace = false;
            this.flipY = true;
        }

        String mapOrientation = root.getAttribute("orientation", "orthogonal");

        int tileWidth = root.getIntAttribute("tileWidth", 0);
        int tileHeight = root.getIntAttribute("tileHeight", 0);
        mapWidth = root.getIntAttribute("tileMapWidth", 0) / tileWidth;
        mapHeight = root.getIntAttribute("tileMapHeight", 0) / tileHeight;
        int hexSideLength = 0;


        String staggerAxis = root.getAttribute("staggeraxis", null);
        String staggerIndex = root.getAttribute("staggerindex", null);
        String mapBackgroundColor = root.getAttribute("backgroundcolor", null);

        MapProperties mapProperties = map.getProperties();
        if (mapOrientation != null) {
            mapProperties.put("orientation", mapOrientation);
        }
        mapProperties.put("width", mapWidth);
        mapProperties.put("height", mapHeight);
        mapProperties.put("tilewidth", tileWidth);
        mapProperties.put("tileheight", tileHeight);
        mapProperties.put("hexsidelength", hexSideLength);
        if (staggerAxis != null) {
            mapProperties.put("staggeraxis", staggerAxis);
        }
        if (staggerIndex != null) {
            mapProperties.put("staggerindex", staggerIndex);
        }
        if (mapBackgroundColor != null) {
            mapProperties.put("backgroundcolor", mapBackgroundColor);
        }
        this.mapTileWidth = tileWidth;
        this.mapTileHeight = tileHeight;
        this.mapWidthInPixels = mapWidth * tileWidth;
        this.mapHeightInPixels = mapHeight * tileHeight;

        if (mapOrientation != null) {
            if ("staggered".equals(mapOrientation)) {
                if (mapHeight > 1) {
                    this.mapWidthInPixels += tileWidth / 2;
                    this.mapHeightInPixels = mapHeightInPixels / 2 + tileHeight / 2;
                }
            }
        }

        loadTileSet(tmxFile, imageResolver);

        loadTileLayer(map, map.getLayers());

        return map;
    }

    protected void loadTileLayer(TiledMap map, MapLayers parentLayers) {

        int width = mapWidth;
        int height = mapHeight;
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);
        TiledMapTileLayer layer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);

        loadBasicLayerInfo(layer);
        Array<XMLTile> tiles = XMLTileLoader.ReadXMLTiles(root);

        TiledMapTileSets tilesets = map.getTileSets();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int id = tiles.get(y * width + x).getIndex();

                TiledMapTile tile = tilesets.getTile(id & ~MASK_CLEAR);
                if (tile != null) {
                    TiledMapTileLayer.Cell cell = createTileLayerCell(tiles.get(y * width + x).isFlipHorizontal(), tiles.get(y * width + x).isFlipVertical(), false);
                    cell.setTile(tile);
                    layer.setCell(x, flipY ? height - 1 - y : y, cell);
                }
            }
        }

        parentLayers.add(layer);

    }

    protected TiledMapTileLayer.Cell createTileLayerCell(boolean flipHorizontally, boolean flipVertically, boolean flipDiagonally) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        if (flipDiagonally) {
            if (flipHorizontally && flipVertically) {
                cell.setFlipHorizontally(true);
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_270);
            } else if (flipHorizontally) {
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_270);
            } else if (flipVertically) {
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_90);
            } else {
                cell.setFlipVertically(true);
                cell.setRotation(TiledMapTileLayer.Cell.ROTATE_270);
            }
        } else {
            cell.setFlipHorizontally(flipHorizontally);
            cell.setFlipVertically(flipVertically);
        }
        return cell;
    }


    protected void loadBasicLayerInfo(MapLayer layer) {
        String name = "Tile Layer 1";
        float opacity = 1.0f;
        boolean visible = true;
        float offsetX = 0;
        float offsetY = 0;

        layer.setName(name);
        layer.setOpacity(opacity);
        layer.setVisible(visible);
        layer.setOffsetX(offsetX);
        layer.setOffsetY(offsetY);
    }

    protected void loadTileSet(FileHandle tmxFile, ImageResolver imageResolver) {
        //  if (element.getName().equals("tileset")) {
        int firstgid = root.getIntAttribute("firstgid", 1);
        String imageSource = "";
        int imageWidth = 0;
        int imageHeight = 0;
        FileHandle image = null;

        imageSource = root.getAttribute("image", null);

        imageWidth = (root.getIntAttribute("tilesPerAtlasColumn", 0) * mapTileWidth) + (root.getIntAttribute("tilesPerAtlasColumn", 0) * 2 * root.getIntAttribute("tileBorderSize", 0));
        imageHeight = (root.getIntAttribute("tilesPerAtlasRow", 0) * mapTileHeight) + (root.getIntAttribute("tilesPerAtlasRow", 0) * 2 * root.getIntAttribute("tileBorderSize", 0));
        image = getRelativeFileHandle(tmxFile, imageSource);


        String name = image.name().substring(0, image.name().length() - 4);
        //   System.out.println(name);
        int tilewidth = mapTileWidth;
        int tileheight = mapTileHeight;
        int spacing = 4;
        int margin = 0;

        int offsetX = 0;
        int offsetY = 0;

        TiledMapTileSet tileSet = new TiledMapTileSet();

        // TileSet
        tileSet.setName(name);
        final MapProperties tileSetProperties = tileSet.getProperties();

        tileSetProperties.put("firstgid", firstgid);


        addStaticTiles(tmxFile, imageResolver, tileSet, name, firstgid, tilewidth, tileheight, spacing,
                margin, offsetX, offsetY, imageSource, imageWidth, imageHeight, image);

        Array<AnimatedTiledMapTile> animatedTiles = new Array<AnimatedTiledMapTile>();


        // replace original static tiles by animated tiles
        for (AnimatedTiledMapTile animatedTile : animatedTiles) {
            tileSet.putTile(animatedTile.getId(), animatedTile);
        }

        map.getTileSets().addTileSet(tileSet);

    }

    protected void addStaticTiles(FileHandle tmxFile, ImageResolver imageResolver, TiledMapTileSet tileSet, String name, int firstgid, int tilewidth, int tileheight, int spacing, int margin,
                                  int offsetX, int offsetY, String imageSource, int imageWidth, int imageHeight, FileHandle image) {

        MapProperties props = tileSet.getProperties();
        if (image != null) {

            TextureRegion texture = imageResolver.getImage(image.path());

            props.put("imagesource", imageSource);
            props.put("imagewidth", imageWidth);
            props.put("imageheight", imageHeight);
            props.put("tilewidth", tilewidth);
            props.put("tileheight", tileheight);
            props.put("margin", margin);
            props.put("spacing", spacing);

            int stopWidth = texture.getRegionWidth() - tilewidth;
            int stopHeight = texture.getRegionHeight() - tileheight;

            int id = firstgid;

            for (int y = margin; y <= stopHeight; y += tileheight + spacing) {
                for (int x = margin; x <= stopWidth; x += tilewidth + spacing) {
                    TextureRegion tileRegion = new TextureRegion(texture, x, y, tilewidth, tileheight);
                    int tileId = id++;
                    addStaticTiledMapTile(tileSet, tileRegion, tileId, offsetX, offsetY);
                }
            }
        }
    }

    protected void addStaticTiledMapTile(TiledMapTileSet tileSet, TextureRegion textureRegion, int tileId, float offsetX,
                                         float offsetY) {
        TiledMapTile tile = new StaticTiledMapTile(textureRegion);
        tile.setId(tileId);
        tile.setOffsetX(offsetX);
        tile.setOffsetY(flipY ? -offsetY : offsetY);
        tileSet.putTile(tileId, tile);
    }

    protected Array<FileHandle> getDependencyFileHandles(FileHandle tmxFile) {
        Array<FileHandle> fileHandles = new Array<FileHandle>();

        FileHandle image = getRelativeFileHandle(tmxFile, root.getAttribute("image"));
        fileHandles.add(image);

        return fileHandles;
    }

    protected static FileHandle getRelativeFileHandle(FileHandle file, String path) {
        StringTokenizer tokenizer = new StringTokenizer(path, "\\/");
        FileHandle result = file.parent();
        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            if (token.equals(".."))
                result = result.parent();
            else {
                result = result.child(token);
            }
        }
        return result;
    }

}


