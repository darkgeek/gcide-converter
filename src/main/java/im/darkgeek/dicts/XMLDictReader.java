package im.darkgeek.dicts;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.io.File;
import java.io.IOException;

/**
 * The XMLDictReader class read the main dictionary files and return the whole contents to caller
 * Created by justin on 15-8-28.
 */
public class XMLDictReader {
    private static XMLDictReader reader = new XMLDictReader();

    /**
     * Read the main dict XML file and load the contents to memory
     * @param dictXMLFilePath the main dictionary XML file path
     * @return the whole dictionary XML files contents
     */
    public String loadRawDict(String dictXMLFilePath) {
        String dictContent = "";

        try {
            dictContent = FileUtils.readFileToString(new File(dictXMLFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return dictContent;
    }

    /**
     * Get the singleton instance of class XMLDictReader
     * @return the singleton instance
     */
    public static XMLDictReader getInstance() {
        return reader;
    }

    private XMLDictReader() {

    }
}
