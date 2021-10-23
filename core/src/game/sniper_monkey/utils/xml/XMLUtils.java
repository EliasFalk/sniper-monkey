package game.sniper_monkey.utils.xml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * A utility class to read xml documents.
 * <p>
 * Used by MapReader.
 * Used by TileReader.
 *
 * @author Vincent Hellner
 * @author Elias Falk
 */
public final class XMLUtils {
    private static final DocumentBuilder db = createDocumentBuilder();

    private XMLUtils() {
    }

    /**
     * Reads a .xml file and returns the read file as a Document object.
     *
     * @param filepath The filepath to the XML file. Is read from project root.
     * @return A document object representing the contents of the XML file.
     */
    public static Document readDocument(String filepath) {
        Document document;
        FileHandle mapHandle = Gdx.files.internal(filepath);
        try {
            document = db.parse(mapHandle.file());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        document.getDocumentElement().normalize();
        return document;
    }

    private static DocumentBuilder createDocumentBuilder() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        return db;
    }
}
