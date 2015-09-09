package im.darkgeek.dicts;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.io.File;
import java.io.IOException;

/**
 * Created by justin on 15-8-28.
 */
public class XMLDictReader {
    private static XMLDictReader reader = new XMLDictReader();

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

    public static XMLDictReader getInstance() {
        return reader;
    }

    private XMLDictReader() {

    }
}
