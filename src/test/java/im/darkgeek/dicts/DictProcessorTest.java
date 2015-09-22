package im.darkgeek.dicts;

import org.dom4j.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Test on DictProcessorTest
 * Created by justin on 15-9-8.
 */
public class DictProcessorTest {

    private static String DICT_TEST_XML_FILE = "/dict_test.xml";
    private static Path dictTestFilePath;

    private static String COMPLEX_DICT_TEST_XML_FILE = "/complex_dict_test.xml";
    private static Path complexDictTestFilePath;

    private static String MORE_COMPLEX_DICT_TEST_XML_FILE = "/more_complex_dict_test.xml";
    private static Path moreComplexDictTestFilePath;

    @Before
    public void loadTestXMLFile() throws URISyntaxException {
        URL dictTestFileUrl = getClass().getResource(DICT_TEST_XML_FILE);
        Assert.assertNotNull(DICT_TEST_XML_FILE + " is missing", dictTestFileUrl);

        URL complexDictTestFileUrl = getClass().getResource(COMPLEX_DICT_TEST_XML_FILE);
        Assert.assertNotNull(COMPLEX_DICT_TEST_XML_FILE + " is missing", complexDictTestFileUrl);

        URL moreComplexDictTestFileUrl = getClass().getResource(MORE_COMPLEX_DICT_TEST_XML_FILE);
        Assert.assertNotNull(MORE_COMPLEX_DICT_TEST_XML_FILE + " is missing", moreComplexDictTestFileUrl);

        dictTestFilePath = Paths.get(dictTestFileUrl.toURI());
        complexDictTestFilePath = Paths.get(complexDictTestFileUrl.toURI());
        moreComplexDictTestFilePath = Paths.get(moreComplexDictTestFileUrl.toURI());

        System.setProperty("entityExpansionLimit", "640000");
    }

    @Test
    public void testSimpleDictItemsGenerate() {
        XMLDictReader reader = XMLDictReader.getInstance();
        DictProcessor dictProcessor = new DictProcessor();
        List<DictItem> expectedItems = new ArrayList<DictItem>(3);

        DictItem dictItem1 = new DictItem();
        dictItem1.setWord("U");
        dictItem1.setExplanation("<p><ent>U</ent><br/>            \n" +
                "<hw>U</hw> <pr>(?)</pr>, <def>the twenty-first letter of the English alphabet, is a cursive form of the letter V, with which it was formerly used interchangeably, both letters being then used both as vowels and consonants. U and V are now, however, differentiated, U being used only as a vowel or semivowel, and V only as a consonant. The true primary vowel sound of U, in Anglo-Saxon, was the sound which it still retains in most of the languages of Europe, that of long <xex>oo</xex>, as in <xex>tool</xex>, and short <xex>oo</xex>, as in <xex>wood</xex>, answering to the French <xex>ou</xex> in <xex>tour</xex>. Etymologically U is most closely related to <xex>o</xex>, <xex>y</xex> (vowel), <xex>w</xex>, and <xex>v</xex>; as in <xex>two</xex>, d<xex>u</xex>et, d<xex>y</xex>ad, t<xex>w</xex>ice; t<xex>o</xex>p, t<xex>u</xex>ft; s<xex>o</xex>p, s<xex>u</xex>p; a<xex>u</xex>spice, a<xex>v</xex>iary. See <er>V</er>, also <er>O</er> and <er>Y</er>.</def><br/>\n" +
                "            [<source>1913 Webster</source>]</p><p>See <xex>Guide to Pronunciation</xex>, 130-144.<br/>\n" +
                "            [<source>1913 Webster</source>]</p>");

        DictItem dictItem2 = new DictItem();
        dictItem2.setWord("Uakari");
        dictItem2.setExplanation("<p><ent>Uakari</ent><br/>            <hw>Ua*ka\"ri</hw> <pr>(?)</pr>, <pos>n.</pos> <fld>(Zool.)</fld> <def>Same as <er>Ouakari</er>.</def><br/>            [<source>1913 Webster</source>]</p>");

        DictItem dictItem3 = new DictItem();
        dictItem3.setWord("Uberous");
        dictItem3.setExplanation("<p><ent>Uberous</ent><br/>            <hw>U\"ber*ous</hw> <pr>(?)</pr>, <pos>a.</pos> <ety>[L. <ets>uber</ets>.]</ety> <def>Fruitful; copious; abundant; plentiful.</def> <mark>[Obs.]</mark>  <rj><au>Sir T. Herbert.</au></rj><br/>            [<source>1913 Webster</source>]</p>");
        expectedItems.add(dictItem1);
        expectedItems.add(dictItem2);
        expectedItems.add(dictItem3);

        List<DictItem> list = dictProcessor
                .loadXML(reader.loadRawDict(dictTestFilePath.toString()))
                .generate();

        Assert.assertEquals("actual size of list is less than expected",
                expectedItems.size(), list.size());
        Assert.assertFalse("list has duplicates dict items", hasDuplicates(list));
        for (DictItem item : list) {
            Assert.assertTrue("test simple dictItems generate failed: Unexpected item ", expectedItems.contains(item));
        }
    }

    @Test
    public void testComplexDictItemsGenerate() {
        XMLDictReader reader = XMLDictReader.getInstance();
        DictProcessor dictProcessor = new DictProcessor();
        List<DictItem> expectedItems = new ArrayList<DictItem>(5);

        DictItem dictItem1 = new DictItem();
        dictItem1.setWord("U");
        dictItem1.setExplanation("<p><ent>U</ent><br/>            \n" +
                "<hw>U</hw> <pr>(?)</pr>, <def>the twenty-first letter of the English alphabet, is a cursive form of the letter V, with which it was formerly used interchangeably, both letters being then used both as vowels and consonants. U and V are now, however, differentiated, U being used only as a vowel or semivowel, and V only as a consonant. The true primary vowel sound of U, in Anglo-Saxon, was the sound which it still retains in most of the languages of Europe, that of long <xex>oo</xex>, as in <xex>tool</xex>, and short <xex>oo</xex>, as in <xex>wood</xex>, answering to the French <xex>ou</xex> in <xex>tour</xex>. Etymologically U is most closely related to <xex>o</xex>, <xex>y</xex> (vowel), <xex>w</xex>, and <xex>v</xex>; as in <xex>two</xex>, d<xex>u</xex>et, d<xex>y</xex>ad, t<xex>w</xex>ice; t<xex>o</xex>p, t<xex>u</xex>ft; s<xex>o</xex>p, s<xex>u</xex>p; a<xex>u</xex>spice, a<xex>v</xex>iary. See <er>V</er>, also <er>O</er> and <er>Y</er>.</def><br/>\n" +
                "            [<source>1913 Webster</source>]</p><p>See <xex>Guide to Pronunciation</xex>, 130-144.<br/>\n" +
                "            [<source>1913 Webster</source>]</p>");

        DictItem dictItem2 = new DictItem();
        dictItem2.setWord("Uakari");
        dictItem2.setExplanation("<p><ent>Uakari</ent><br/>            <hw>Ua*ka\"ri</hw> <pr>(?)</pr>, <pos>n.</pos> <fld>(Zool.)</fld> <def>Same as <er>Ouakari</er>.</def><br/>            [<source>1913 Webster</source>]</p>");

        DictItem dictItem3 = new DictItem();
        dictItem3.setWord("Uberous");
        dictItem3.setExplanation("<p><ent>Uberous</ent><br/>            <hw>U\"ber*ous</hw> <pr>(?)</pr>, <pos>a.</pos> <ety>[L. <ets>uber</ets>.]</ety> <def>Fruitful; copious; abundant; plentiful.</def> <mark>[Obs.]</mark>  <rj><au>Sir T. Herbert.</au></rj><br/>            [<source>1913 Webster</source>]</p>");

        DictItem dictItem4 = new DictItem();
        dictItem4.setWord("Umbriere");
        dictItem4.setExplanation("<p><ent>Umbriere</ent><br/>\n" +
                "        <ent>Umbrere</ent><br/>\n" +
                "        <mhw>{ <hw>Um*brere</hw>, <hw>Um*briere</hw>  }</mhw> <pr>(?)</pr>, <pos>n.</pos> <ety>[F. <ets>ombre</ets> a shade, L. <ets>umbra</ets>; cf. F. <ets>ombrelle</ets> a sunshade, OF. also <ets>ombrire</ets>. See <er>Umbrella</er>.]</ety> <def>In ancient armor, a visor, or projection like the peak of a cap, to which a face guard was sometimes attached. This was sometimes fixed, and sometimes moved freely upon the helmet and could be raised like the beaver. Called also <altname>umber</altname>, and <xex>umbril</xex>.</def> <mark>[Obs.]</mark><br/>\n" +
                "        [<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "<p><q>But only vented up her <qex>umbriere</qex>.</q> <rj><qau>Spenser.</qau></rj><br/>\n" +
                "            [<source>1913 Webster</source>]</p>");

        DictItem dictItem5 = new DictItem();
        dictItem5.setWord("Umbrere");
        dictItem5.setExplanation("<p><ent>Umbriere</ent><br/>\n" +
                "        <ent>Umbrere</ent><br/>\n" +
                "        <mhw>{ <hw>Um*brere</hw>, <hw>Um*briere</hw>  }</mhw> <pr>(?)</pr>, <pos>n.</pos> <ety>[F. <ets>ombre</ets> a shade, L. <ets>umbra</ets>; cf. F. <ets>ombrelle</ets> a sunshade, OF. also <ets>ombrire</ets>. See <er>Umbrella</er>.]</ety> <def>In ancient armor, a visor, or projection like the peak of a cap, to which a face guard was sometimes attached. This was sometimes fixed, and sometimes moved freely upon the helmet and could be raised like the beaver. Called also <altname>umber</altname>, and <xex>umbril</xex>.</def> <mark>[Obs.]</mark><br/>\n" +
                "        [<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "<p><q>But only vented up her <qex>umbriere</qex>.</q> <rj><qau>Spenser.</qau></rj><br/>\n" +
                "            [<source>1913 Webster</source>]</p>");

        expectedItems.add(dictItem1);
        expectedItems.add(dictItem2);
        expectedItems.add(dictItem3);
        expectedItems.add(dictItem4);
        expectedItems.add(dictItem5);

        List<DictItem> list = dictProcessor
                .loadXML(reader.loadRawDict(complexDictTestFilePath.toString()))
                .generate();

        Assert.assertEquals("actual size of list is less than expected",
                expectedItems.size(), list.size());
        Assert.assertFalse("list has duplicates dict items", hasDuplicates(list));
        for (DictItem item : list) {
            Assert.assertTrue("test complex dictItems generate failed: Unexpected item ", expectedItems.contains(item));
        }
    }

    @Test
    public void testMoreComplexDictItemsGenerate() {
        XMLDictReader reader = XMLDictReader.getInstance();
        DictProcessor dictProcessor = new DictProcessor();
        List<DictItem> expectedItems = new ArrayList<DictItem>(5);

        DictItem dictItem1 = new DictItem();
        dictItem1.setWord("U");
        dictItem1.setExplanation("<p><ent>U</ent><br/>            \n" +
                "<hw>U</hw> <pr>(?)</pr>, <def>the twenty-first letter of the English alphabet, is a cursive form of the letter V, with which it was formerly used interchangeably, both letters being then used both as vowels and consonants. U and V are now, however, differentiated, U being used only as a vowel or semivowel, and V only as a consonant. The true primary vowel sound of U, in Anglo-Saxon, was the sound which it still retains in most of the languages of Europe, that of long <xex>oo</xex>, as in <xex>tool</xex>, and short <xex>oo</xex>, as in <xex>wood</xex>, answering to the French <xex>ou</xex> in <xex>tour</xex>. Etymologically U is most closely related to <xex>o</xex>, <xex>y</xex> (vowel), <xex>w</xex>, and <xex>v</xex>; as in <xex>two</xex>, d<xex>u</xex>et, d<xex>y</xex>ad, t<xex>w</xex>ice; t<xex>o</xex>p, t<xex>u</xex>ft; s<xex>o</xex>p, s<xex>u</xex>p; a<xex>u</xex>spice, a<xex>v</xex>iary. See <er>V</er>, also <er>O</er> and <er>Y</er>.</def><br/>\n" +
                "            [<source>1913 Webster</source>]</p><p>See <xex>Guide to Pronunciation</xex>, 130-144.<br/>\n" +
                "            [<source>1913 Webster</source>]</p>");

        DictItem dictItem2 = new DictItem();
        dictItem2.setWord("Uakari");
        dictItem2.setExplanation("<p><ent>Uakari</ent><br/>            <hw>Ua*ka\"ri</hw> <pr>(?)</pr>, <pos>n.</pos> <fld>(Zool.)</fld> <def>Same as <er>Ouakari</er>.</def><br/>            [<source>1913 Webster</source>]</p>");

        DictItem dictItem3 = new DictItem();
        dictItem3.setWord("Uberous");
        dictItem3.setExplanation("<p><ent>Uberous</ent><br/>            <hw>U\"ber*ous</hw> <pr>(?)</pr>, <pos>a.</pos> <ety>[L. <ets>uber</ets>.]</ety> <def>Fruitful; copious; abundant; plentiful.</def> <mark>[Obs.]</mark>  <rj><au>Sir T. Herbert.</au></rj><br/>            [<source>1913 Webster</source>]</p>");

        DictItem dictItem4 = new DictItem();
        dictItem4.setWord("Un-");
        dictItem4.setExplanation("<p><ent>Un-</ent><br/>\n" +
                "            <hw>Un-</hw>. <ety>[OE. <ets>un-</ets>, <ets>on-</ets>, the unaccented form of the accented prefix <ets>and-</ets> (cf. <er>Answer</er>); akin to D. <ets>ont-</ets>, G. <ets>ent-</ets>, OHG. <ets>int-</ets>, Goth. <ets>and-</ets>. See <er>Anti-</er>.]</ety> <def>An inseparable verbal prefix or particle. It is prefixed: <sd>(a)</sd> To verbs to express the contrary, and not the simple negative, of the action of the verb to which it is prefixed; as in <xex>un</xex>bend, <xex>un</xex>coil, <xex>un</xex>do, <xex>un</xex>fold. <sd>(b)</sd> To nouns to form verbs expressing privation of the thing, quality, or state expressed by the noun, or separation from it; as in <xex>un</xex>child, <xex>un</xex>sex. Sometimes particles and participial adjectives formed with this prefix coincide in form with compounds of the negative prefix <xex>un-</xex> (see 2d <er>Un-</er>); as in <xex>un</xex>done (from <xex>un</xex>do), meaning unfastened, ruined; and <xex>un</xex>done (from 2d <xex>un-</xex> and <xex>done</xex>) meaning not done, not finished. <xex>Un-</xex> is sometimes used with an intensive force merely; as in <xex>un</xex>loose.</def><br/>\n" +
                "            [<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "<p><note>Compounds of this prefix are given in full in their proper order in the Vocabulary.</note><br/>\n" +
                "            [<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "<p><sd>I</sd>. <def><ex>Un-</ex> is prefixed to adjectives, or to words used adjectively.</def> Specifically: --<br/>\n" +
                "            [<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "<p><sd>(a)</sd> <def>To adjectives, to denote the absence of the quality designated by the adjective</def>; as, --<br/>\n" +
                "            [<source>1913 Webster</source>]</p>" +
                "<p><br/><sd>(b)</sd><def>To past particles, or to adjectives formed after the analogy of past particles, to indicate the absence of the condition or state expressed by them</def></p>" +
                "<p><sd>(c)</sd> <def>To present participles which come from intransitive verbs, or are themselves employed as adjectives, to mark the absence of the activity, disposition, or condition implied by the participle;</def> as, --<br/>\n" +
                "            [<source>1913 Webster</source>]</p>");

        DictItem dictItem5 = new DictItem();
        dictItem5.setWord("Unabolishable");
        dictItem5.setExplanation("<p><h2>Unabolishable</h2><br/><hw>Unabolishable</hw><def>&nbsp;&nbsp;-&nbsp;&nbsp;<def>See <er>abolishable</er>.</def></def></p>");

        DictItem dictItem6 = new DictItem();
        dictItem6.setWord("Unabsolvable");
        dictItem6.setExplanation("<p><h2>Unabsolvable</h2><br/><hw>Unabsolvable</hw><def>&nbsp;&nbsp;-&nbsp;&nbsp;<def>See <er>absolvable</er>.</def></def></p>");

        DictItem dictItem7 = new DictItem();
        dictItem7.setWord("Unabsurd");
        dictItem7.setExplanation("<p><h2>Unabsurd</h2><br/><hw>Unabsurd</hw><def>&nbsp;&nbsp;-&nbsp;&nbsp;<def>See <er>absurd</er>.</def></def></p>");

        DictItem dictItem8 = new DictItem();
        dictItem8.setWord("Unabased");
        dictItem8.setExplanation("<p><h2>Unabased</h2><br/><hw>Unabased</hw><def>&nbsp;&nbsp;-&nbsp;&nbsp;<def>See <er>abased</er>.</def></def></p>");

        DictItem dictItem9 = new DictItem();
        dictItem9.setWord("Unabashed");
        dictItem9.setExplanation("<p><h2>Unabashed</h2><br/><hw>Unabashed</hw><def>&nbsp;&nbsp;-&nbsp;&nbsp;<def>See <er>abashed</er>.</def></def></p>");

        DictItem dictItem10 = new DictItem();
        dictItem10.setWord("Unabated");
        dictItem10.setExplanation("<p><h2>Unabated</h2><br/><hw>Unabated</hw><def>&nbsp;&nbsp;-&nbsp;&nbsp;<def>See <er>abated</er>.</def></def></p>");

        expectedItems.add(dictItem1);
        expectedItems.add(dictItem2);
        expectedItems.add(dictItem3);
        expectedItems.add(dictItem4);
        expectedItems.add(dictItem5);
        expectedItems.add(dictItem6);
        expectedItems.add(dictItem7);
        expectedItems.add(dictItem8);
        expectedItems.add(dictItem9);
        expectedItems.add(dictItem10);

        List<DictItem> list = dictProcessor
                .loadXML(reader.loadRawDict(moreComplexDictTestFilePath.toString()))
                .generate();

        Assert.assertEquals("actual size of list is less than expected",
                expectedItems.size(), list.size());
        Assert.assertFalse("list has duplicates dict items", hasDuplicates(list));
        for (DictItem item : list) {
            Assert.assertTrue("test complex dictItems generate failed: Unexpected item " + item, expectedItems.contains(item));
        }
    }

    @Test
    public void testQuirk() {
        XMLDictReader reader = XMLDictReader.getInstance();
        DictProcessor dictProcessor = new DictProcessor();
        List<DictItem> expectedItems = new ArrayList<DictItem>(3);

        DictItem dictItem1 = new DictItem();
        dictItem1.setWord("U");
        dictItem1.setExplanation("<p><ent>U</ent><br/>            \n" +
                "<hw>U</hw> <pr>(?)</pr>, <def>the twenty-first letter of the English alphabet, is a cursive form of the letter V, with which it was formerly used interchangeably, both letters being then used both as vowels and consonants. U and V are now, however, differentiated, U being used only as a vowel or semivowel, and V only as a consonant. The true primary vowel sound of U, in Anglo-Saxon, was the sound which it still retains in most of the languages of Europe, that of long <xex>oo</xex>, as in <xex>tool</xex>, and short <xex>oo</xex>, as in <xex>wood</xex>, answering to the French <xex>ou</xex> in <xex>tour</xex>. Etymologically U is most closely related to <xex>o</xex>, <xex>y</xex> (vowel), <xex>w</xex>, and <xex>v</xex>; as in <xex>two</xex>, d<xex>u</xex>et, d<xex>y</xex>ad, t<xex>w</xex>ice; t<xex>o</xex>p, t<xex>u</xex>ft; s<xex>o</xex>p, s<xex>u</xex>p; a<xex>u</xex>spice, a<xex>v</xex>iary. See <a href=\"V\">V</a>, also <a href=\"O\">O</a> and <a href=\"Y\">Y</a>.</def><br/>\n" +
                "            [<source>1913 Webster</source>]</p><p>See <xex>Guide to Pronunciation</xex>, 130-144.<br/>\n" +
                "            [<source>1913 Webster</source>]</p>");

        DictItem dictItem2 = new DictItem();
        dictItem2.setWord("Uakari");
        dictItem2.setExplanation("<p><ent>Uakari</ent><br/>            <hw>Ua*ka\"ri</hw> <pr>(?)</pr>, <pos>n.</pos> <fld>(Zool.)</fld> <def>Same as <a href=\"Ouakari\">Ouakari</a>.</def><br/>            [<source>1913 Webster</source>]</p>");

        DictItem dictItem3 = new DictItem();
        dictItem3.setWord("Uberous");
        dictItem3.setExplanation("<p><ent>Uberous</ent><br/>            <hw>U\"ber*ous</hw> <pr>(?)</pr>, <pos>a.</pos> <ety>[L. <ets>uber</ets>.]</ety> <def>Fruitful; copious; abundant; plentiful.</def> <mark>[Obs.]</mark>  <rj><au>Sir T. Herbert.</au></rj><br/>            [<source>1913 Webster</source>]</p>");
        expectedItems.add(dictItem1);
        expectedItems.add(dictItem2);
        expectedItems.add(dictItem3);

        Callback handleERblock = new Callback() {
            public int process(Element element) {
                element.setName("a");
                element.addAttribute("href", element.getTextTrim());
                return 1;
            }
        };
        List<DictItem> list = dictProcessor
                .loadXML(reader.loadRawDict(dictTestFilePath.toString()))
                .addQuirk("er", handleERblock)
                .generate();

        Assert.assertEquals("actual size of list is less than expected",
                expectedItems.size(), list.size());
        Assert.assertFalse("list has duplicates dict items", hasDuplicates(list));
        for (DictItem item : list) {
            Assert.assertTrue("test simple dictItems generate failed: Unexpected item " + item, expectedItems.contains(item));
        }
    }

    private boolean hasDuplicates(List<DictItem> dictItems) {
        Set<DictItem> dictItemsWithoutDuplicates = new HashSet<DictItem>(dictItems);

        return !(dictItems.size() == dictItemsWithoutDuplicates.size());
    }
}
