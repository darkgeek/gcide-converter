package im.darkgeek.dicts;

import junit.framework.TestCase;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 * Created by justin on 15-9-8.
 */
public class DictProcessorTest
    extends TestCase {

    private static String DICT_TEST_XML_FILE = "/dict_test.xml";
    private static Path dictTestFilePath;

    private static String COMPLEX_DICT_TEST_XML_FILE = "/complex_dict_test.xml";
    private static Path complexDictTestFilePath;

    private static String MORE_COMPLEX_DICT_TEST_XML_FILE = "/more_complex_dict_test.xml";
    private static Path moreComplexDictTestFilePath;

    @Before
    public void loadTestXMLFile() throws URISyntaxException {
        URL dictTestFileUrl = getClass().getResource(DICT_TEST_XML_FILE);
        assertNotNull(DICT_TEST_XML_FILE + " is missing", dictTestFileUrl);

        URL complexDictTestFileUrl = getClass().getResource(COMPLEX_DICT_TEST_XML_FILE);
        assertNotNull(COMPLEX_DICT_TEST_XML_FILE + " is missing", complexDictTestFileUrl);

        URL moreComplexDictTestFileUrl = getClass().getResource(MORE_COMPLEX_DICT_TEST_XML_FILE);
        assertNotNull(MORE_COMPLEX_DICT_TEST_XML_FILE + " is missing", moreComplexDictTestFileUrl);

        dictTestFilePath = Paths.get(dictTestFileUrl.toURI());
        complexDictTestFilePath = Paths.get(complexDictTestFileUrl.toURI());
        moreComplexDictTestFilePath = Paths.get(moreComplexDictTestFileUrl.toURI());
    }

    @Test
    public void testSimpleDictItemsGenerate() {

    }

    @Test
    public void testComplexDictItemsGenerate() {

    }

    @Test
    public void testMoreComplexDictItemsGenerate() {

    }
}
