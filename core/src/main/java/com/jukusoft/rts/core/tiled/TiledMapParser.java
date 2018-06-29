package com.jukusoft.rts.core.tiled;

import com.jukusoft.rts.core.logging.LocalLogger;
import com.jukusoft.rts.core.tiled.tileset.TextureTileset;
import com.jukusoft.rts.core.tiled.tileset.Tileset;
import com.jukusoft.rts.core.tiled.tileset.TsxTileset;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* XML parser for tiled .tmx map format
 *
 * @link http://docs.mapeditor.org/en/stable/reference/tmx-map-format/
*/
public class TiledMapParser {

    protected String tmxFormatVersion = "";
    protected Orientation orientation = Orientation.ORTHOGONAL;

    //tiles count
    protected int width = 0;//The map width in tiles.
    protected int height = 0;//The map height in tiles.

    //tile size
    protected int tileWidth = 0;//The width of a tile.
    protected int tileHeight = 0;//The height of a tile.

    //list with tilesets
    protected List<Tileset> tilesets = new ArrayList<>();

    //list with all layers
    protected List<TiledLayer> layers = new ArrayList<>();

    public TiledMapParser () {
        //
    }

    /**
    * load .tmx map
    */
    public void load (File xmlFile) throws IOException {
        //reset tiled map parser first
        this.tilesets.clear();
        this.layers.clear();

        if (xmlFile == null) {
            throw new NullPointerException("tiled map file cannot be null.");
        }

        if (!xmlFile.exists()) {
            throw new FileNotFoundException("tiled map doesnt exists.");
        }

        Document doc = null;

        try {
            doc = this.parse(xmlFile);
        } catch (DocumentException e) {
            LocalLogger.printStacktrace(e);
            throw new IllegalStateException("Cannot parse tiled file: " + xmlFile.getAbsolutePath());
        }

        //first get root element
        Element rootElement = doc.getRootElement();

        //get tmx format version
        this.tmxFormatVersion = rootElement.attributeValue("version");

        if (!this.tmxFormatVersion.equals("1.0")) {
            throw new TiledParserException("Unknown tmx format version '" + this.tmxFormatVersion + "': " + xmlFile.getAbsolutePath());
        }

        //parse orientation
        String orientation = rootElement.attributeValue("orientation");

        if (orientation.equals("orthogonal")) {
            this.orientation = Orientation.ORTHOGONAL;
        } else if (orientation.equals("isometric")) {
            this.orientation = Orientation.ISOMETRIC;
        } else {
            throw new UnsupportedOperationException("orientation '" + orientation + "' isnt supported by this tmx parser.");
        }

        /**
         *  check render-order, only right-down is supported
         *
         * from tmx format specification:
         *
         * The order in which tiles on tile layers are rendered.
         * Valid values are right-down (the default),
         * right-up, left-down and left-up.
         *
         * In all cases, the map is drawn row-by-row.
         * (only supported for orthogonal maps at the moment)
        */
        if (!rootElement.attributeValue("renderorder").equals("right-down")) {
            throw new UnsupportedOperationException("Unsupported tmx render order '" + rootElement.attributeValue("right-down") + "' of tmx file: " + xmlFile.getAbsolutePath());
        }

        //get tiles count
        this.width = Integer.parseInt(rootElement.attributeValue("width"));
        this.height = Integer.parseInt(rootElement.attributeValue("height"));

        //get tile width & height
        this.tileWidth = Integer.parseInt(rootElement.attributeValue("tilewidth"));
        this.tileHeight = Integer.parseInt(rootElement.attributeValue("tileheight"));

        //get all tilesets
        List<Node> tilesets = doc.selectNodes("/map/tileset");

        for (Node node : tilesets) {
            Element element = (Element) node;

            //get first tileID of tileset
            int firstTileID = Integer.parseInt(element.attributeValue("firstgid"));

            LocalLogger.print("firstgid: " + firstTileID);

            if (element.attributeValue("source") != null) {
                //its a .tsx tileset

                //get source file
                String source = element.attributeValue("source");

                //TODO: add code here

                TsxTileset tileset = new TsxTileset(firstTileID, source);

                //add tileset to list
                this.tilesets.add(tileset);
            } else {
                //its a normal texture tileset

                //get name
                String name = element.attributeValue("name");

                if (name == null) {
                    throw new TiledParserException("tileset doesnt contains a name.");
                }

                LocalLogger.print("element: " + element.getName());

                //get tile width
                int tilesetTileWidth = Integer.parseInt(element.attributeValue("tilewidth"));
                int tilesetTileHeight = Integer.parseInt(element.attributeValue("tileheight"));

                //get number of tiles
                int tileCount = Integer.parseInt(element.attributeValue("tilecount"));

                //get columns
                int columns = Integer.parseInt(element.attributeValue("columns"));

                //create new tileset
                TextureTileset tileset = new TextureTileset(firstTileID, name, tilesetTileWidth, tilesetTileHeight, tileCount, columns);

                //add textures to tileset
                for (Node imageNode : node.selectNodes("image")) {
                    Element imageElement = (Element) imageNode;

                    String source = imageElement.attributeValue("source");

                    if (source == null) {
                        throw new TiledParserException("tileset image source is not set.");
                    }

                    if (source.isEmpty()) {
                        throw new TiledParserException("tileset image source is empty.");
                    }

                    int imageWidth = Integer.parseInt(imageElement.attributeValue("width"));
                    int imageHeight = Integer.parseInt(imageElement.attributeValue("height"));

                    LocalLogger.print("tileset image found: " + source + ", width: " + imageWidth + ", height: " + imageHeight);

                    //add image to tileset
                    tileset.addImage(source, imageWidth, imageHeight);
                }

                //add tileset to list
                this.tilesets.add(tileset);
            }
        }

        //get all layers
        List<Node> layers = doc.selectNodes("/map/layer");

        for (Node layerNode : layers) {
            Element layerElement = (Element) layerNode;

            //get name
            String name = layerElement.attributeValue("name");

            if (name == null) {
                throw new TiledParserException("layer name is not set.");
            }

            if (name.isEmpty()) {
                throw new TiledParserException("layer name cannot be empty.");
            }

            //get layer width & height in tiles
            int layerWidth = Integer.parseInt(layerElement.attributeValue("width"));
            int layerHeight = Integer.parseInt(layerElement.attributeValue("height"));

            //create new layer
            TiledLayer layer = new TiledLayer(name, layerWidth, layerHeight);

            //parse layer, get data element
            Node dataNode = layerNode.selectSingleNode("data");

            if (dataNode == null) {
                throw new TiledParserException("One of layer elements doesnt have a data node.");
            }

            Element dataElement = (Element) dataNode;

            //check encoding
            String encoding = dataElement.attributeValue("encoding");

            if (encoding == null) {
                //no encoding set, plain XML
                this.parsePlainXMLLayer(dataElement, layer);
            } else if (encoding.equals("base64")) {
                //base64 encoding
                this.parseBase64Layer(dataElement, layer);
            } else {
                throw new UnsupportedOperationException("TMX layer encoding '" + encoding + "' isnt supported yet, use plain xml or 'base64' instead.");
            }

            //add layer to list
            this.layers.add(layer);
        }
    }

    protected void parsePlainXMLLayer (Element dataElement, TiledLayer layer) {
        //
    }

    protected void parseBase64Layer (Element dataElement, TiledLayer layer) {
        //
    }

    protected Document parse(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }

    public String getTmxFormatVersion() {
        return tmxFormatVersion;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getWidthInPixels () {
        return this.width * this.tileWidth;
    }

    public int getHeightInPixels () {
        return this.height * this.tileHeight;
    }

    public List<Tileset> listTilesets() {
        return tilesets;
    }

    public List<TiledLayer> listLayers() {
        return layers;
    }

}
