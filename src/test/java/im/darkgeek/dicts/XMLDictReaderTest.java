package im.darkgeek.dicts;

import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by justin on 15-9-20.
 */
public class XMLDictReaderTest {

    private static String DICT_TEST_XML_FILE = "/dict_test.xml";
    private static Path dictTestFilePath;
    private static String fileContent;

    @Before
    public void loadTestFile() throws URISyntaxException {
        URL dictTestFileUrl = getClass().getResource(DICT_TEST_XML_FILE);
        assertNotNull(DICT_TEST_XML_FILE + " is missing", dictTestFileUrl);

        dictTestFilePath = Paths.get(dictTestFileUrl.toURI());
        fileContent = "<?xml version =\"1.0\"?>\n" +
                "<dictionary>\n" +
                "    <p><!-- p. 1560 pr=PI --></p>\n" +
                "\n" +
                "    <p><centered><point26>U.</point26></centered></p>\n" +
                "\n" +
                "    <p><ent>U</ent><br/>\n" +
                "        <hw>U</hw> <pr>(?)</pr>, <def>the twenty-first letter of the English alphabet, is a cursive form of the letter V, with which it was formerly used interchangeably, both letters being then used both as vowels and consonants. U and V are now, however, differentiated, U being used only as a vowel or semivowel, and V only as a consonant. The true primary vowel sound of U, in Anglo-Saxon, was the sound which it still retains in most of the languages of Europe, that of long <xex>oo</xex>, as in <xex>tool</xex>, and short <xex>oo</xex>, as in <xex>wood</xex>, answering to the French <xex>ou</xex> in <xex>tour</xex>. Etymologically U is most closely related to <xex>o</xex>, <xex>y</xex> (vowel), <xex>w</xex>, and <xex>v</xex>; as in <xex>two</xex>, d<xex>u</xex>et, d<xex>y</xex>ad, t<xex>w</xex>ice; t<xex>o</xex>p, t<xex>u</xex>ft; s<xex>o</xex>p, s<xex>u</xex>p; a<xex>u</xex>spice, a<xex>v</xex>iary. See <er>V</er>, also <er>O</er> and <er>Y</er>.</def><br/>\n" +
                "        [<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "    <p>See <xex>Guide to Pronunciation</xex>, 130-144.<br/>\n" +
                "        [<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "    <p><ent>Uakari</ent><br/>\n" +
                "        <hw>Ua*ka\"ri</hw> <pr>(?)</pr>, <pos>n.</pos> <fld>(Zool.)</fld> <def>Same as <er>Ouakari</er>.</def><br/>\n" +
                "        [<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "    <p><ent>Uberous</ent><br/>\n" +
                "        <hw>U\"ber*ous</hw> <pr>(?)</pr>, <pos>a.</pos> <ety>[L. <ets>uber</ets>.]</ety> <def>Fruitful; copious; abundant; plentiful.</def> <mark>[Obs.]</mark>  <rj><au>Sir T. Herbert.</au></rj><br/>\n" +
                "        [<source>1913 Webster</source>]</p>\n" +
                "</dictionary>";
    }

    @Test
    public void loadNormalXMLFileTest() {
        assertEquals("Normal file test failed", fileContent, XMLDictReader.getInstance().loadRawDict(dictTestFilePath.toString()));
    }

    @Test
    public void loadNullFileTest() {
        assertEquals("Null file test failed", "", XMLDictReader.getInstance().loadRawDict(null));
    }

    @Test
    public void loadInvalidFileTest() {
        assertEquals("Invalid file \"\" test failed", "", XMLDictReader.getInstance().loadRawDict(""));
        assertEquals("Invalid file \"file-not-exists.xml\" test failed", "", XMLDictReader.getInstance().loadRawDict("file-not-exists.xml"));
    }
}
