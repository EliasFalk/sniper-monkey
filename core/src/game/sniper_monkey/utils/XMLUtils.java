package game.sniper_monkey.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLUtils {
    private static final DocumentBuilder db = XMLUtils.createDocumentBuilder();

    private XMLUtils() {
    }

    public static Document readDocument(String s) {
        Document document;
        FileHandle mapHandle = Gdx.files.internal(s); //TODO fix path
        try {
            document = db.parse(mapHandle.file());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        document.getDocumentElement().normalize();
        return document;
    }

    public static DocumentBuilder createDocumentBuilder() {
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
