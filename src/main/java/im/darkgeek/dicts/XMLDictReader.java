package im.darkgeek.dicts;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by justin on 15-8-28.
 */
public class XMLDictReader {
    private XMLDictReader reader = new XMLDictReader();
    private String entityCache = "";

    public void loadEntity(File entityXMLFile) {
        try {
            entityCache = FileUtils.readFileToString(entityXMLFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadDict(File dictXMLFile) {
        String dictContent = "";

        try {
            dictContent = FileUtils.readFileToString(dictXMLFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entityCache + dictContent;
    }

    public XMLDictReader getInstance() {
        return reader;
    }

    private XMLDictReader() {

    }
}
