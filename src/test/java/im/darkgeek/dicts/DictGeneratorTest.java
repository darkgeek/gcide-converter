package im.darkgeek.dicts;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by justin on 15-9-20.
 */
public class DictGeneratorTest {
    private List<DictItem> dictItems = new ArrayList<DictItem>(5);
    private String scriptName = "dict_creator.py";

    @Before
    public void populateDictItems() {
        DictItem dictItem1 = new DictItem();
        dictItem1.setWord("dual");
        dictItem1.setExplanation("<p><ent>Dual</ent><br/>\n" +
                "<hw>Du\"al</hw> <pr>(?)</pr>, <pos>a.</pos> <ety>[L. <ets>dualis</ets>, fr. <ets>duo</ets> two. See <er>Two</er>.]</ety> <def>Expressing, or consisting of, the number two; belonging to two; <as>as, the <ex>dual</ex> number of nouns, etc.</as> , in Greek.</def><br/>\n" +
                "[<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "<p><q>Here you have one half of our <qex>dual</qex> truth.</q> <rj><qau>Tyndall.</qau></rj><br/>\n" +
                "[<source>1913 Webster</source>]</p>");

        DictItem dictItem2 = new DictItem();
        dictItem2.setWord("Dualin");
        dictItem2.setExplanation("<p><ent>Dualin</ent><br/>\n" +
                "<hw>Du\"a*lin</hw> <pr>(?)</pr>, <pos>n.</pos> <fld>(Chem.)</fld> <def>An explosive substance consisting essentially of sawdust or wood pulp, saturated with nitroglycerin and other similar nitro compounds. It is inferior to dynamite, and is more liable to explosion.</def><br/>\n" +
                "[<source>1913 Webster</source>]</p>");

        DictItem dictItem3 = new DictItem();
        dictItem3.setWord("Ularburong");
        dictItem3.setExplanation("<p><ent>Ularburong</ent><br/>\n" +
                "<hw>U*lar\"bu*rong</hw> <pr>(?)</pr>, <pos>n.</pos> <ety>[From the native Malay name.]</ety> <fld>(Zool.)</fld> <def>A large East Indian nocturnal tree snake (<spn>Dipsas dendrophila</spn>). It is not venomous.</def><br/>\n" +
                "[<source>1913 Webster</source>]</p>");

        DictItem dictItem4 = new DictItem();
        dictItem4.setWord("Unsuspecting");
        dictItem4.setExplanation("<p><h2>Unsuspecting</h2><br/><hw>Unsuspecting</hw><def>&nbsp;&nbsp;-&nbsp;&nbsp;" +
                "See <er>suspecting</er>.</def></p>");

        DictItem dictItem5 = new DictItem();
        dictItem5.setWord("Y");
        dictItem5.setWord("<p><ent>Y</ent><br/>\n" +
                "<hw>Y</hw> <pr>(w&imacr;)</pr>. <def>Y, the twenty-fifth letter of the English alphabet, at the beginning of a word or syllable, except when a prefix (see Y-), is usually a fricative vocal consonant; as a prefix, and usually in the middle or at the end of a syllable, it is a vowel.  See <xex>Guide to Pronunciation</xex>, &sect;&sect; 145, 178-9, 272.</def><br/>\n" +
                "[<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "<p><note> It derives its form from the Latin Y, which is from the Greek &Upsilon;, originally the same letter as V. Etymologically, it is most nearly related to <xex>u</xex>, <xex>i</xex>, <xex>o</xex>, and <xex>j</xex>. <xex>g</xex>; as in <xex>full</xex>, <xex>fill</xex>, AS. <xex>fyllan</xex>; E. <xex>crypt</xex>, <xex>grotto</xex>; <xex>young</xex>, <xex>juvenile</xex>; <xex>day</xex>, AS. <xex>d&aelig;g</xex>.  See <er>U</er>, <er>I</er>, and <er>J</er>, <er>G</er>.</note><br/>\n" +
                "[<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "<p><note>&hand_; Y has been called the <xex>Pythagorean letter</xex>, because the Greek letter &Upsilon; was taken to represent the sacred triad, formed by the duad proceeding from the monad; and also because it represents the dividing of the paths of vice and virtue in the development of human life.</note><br/>\n" +
                "[<source>1913 Webster</source>]</p>");

        dictItems.add(dictItem1);
        dictItems.add(dictItem2);
        dictItems.add(dictItem3);
        dictItems.add(dictItem4);
        dictItems.add(dictItem5);
    }

    @Test
    public void testNormalScriptGenerate() {
        DictGenerator.createGeneratorScript(dictItems);
        String expectedScriptContent = "#!/usr/bin/env python\n" +
                "import slob\n" +
                "with slob.create('gcide.slob') as w:\n" +
                "    w.tag(\"\"\"label\"\"\", \"\"\"The Collaborative International Dictionary of English v0.51\"\"\")\n" +
                "    w.tag(\"\"\"copyright\"\"\", \"\"\"Public Domain\"\"\")\n" +
                "    w.tag(\"\"\"source\"\"\", \"\"\"http://www.ibiblio.org/webster/\"\"\")\n" +
                "    w.tag(\"\"\"uri\"\"\", \"\"\"http://savannah.gnu.org/projects/gcide/\"\"\")\n" +
                "    w.tag(\"\"\"created.by\"\"\", \"\"\"https://github.com/darkgeek/gcide-converter\"\"\")\n" +
                "    w.tag(\"\"\"license.url\"\"\", \"\"\"http://www.gnu.org/licenses/gpl.html\"\"\")\n" +
                "    w.tag(\"\"\"license.name\"\"\", \"\"\"GNU General Public License v3 or later\"\"\")\n" +
                "    w.add(\"\"\"body {\n" +
                "    background-color: black;\n" +
                "    color: gray;\n" +
                "}\n" +
                "\n" +
                "h1, h2, h3, h4, h5, h6 {\n" +
                "    color: gray !important;\n" +
                "}\n" +
                "\n" +
                "img {\n" +
                "    background-color: lightgray;\n" +
                "}\n" +
                "\n" +
                "table {\n" +
                "    background-color: black !important;\n" +
                "    color: gray !important;\n" +
                "}\n" +
                "\n" +
                "div.thumbinner {\n" +
                "    background-color: #191919 !important;\n" +
                "}\n" +
                "\n" +
                "div.thumb {\n" +
                "    border-color: black !important;\n" +
                "}\n" +
                "\n" +
                "a {\n" +
                "    color: green;\n" +
                "}\n" +
                "\n" +
                "a:visited {\n" +
                "    color: darkgreen;\n" +
                "}\n" +
                "\"\"\".encode('utf-8'), \"\"\"~/css/night.css\"\"\", content_type='text/html; charset=utf-8')\n" +
                "    w.add(\"\"\"pos {\n" +
                "\tcolor: darkgreen;\n" +
                "\tfont-weight: bold;\n" +
                "}\n" +
                "\n" +
                "hw,sn {\n" +
                "\tfont-weight: bold;\n" +
                "}\n" +
                "\n" +
                "q,it,ant,asp,booki,causedby,cnvto,compof,\n" +
                "contr,cref,film,fld,itran,itrans,abbr,altname,altnpluf,\n" +
                "ets,etsep,ex,mark,qex,sd,ship,source,pluf,uex,isa,mathex,\n" +
                "ratio,singf,xlati,tran,tr,iref,figref,ptcl,title,stype,\n" +
                "part,parts,membof,member,members,corr,qperson,prod,prodmac,\n" +
                "stage,inv,methodfor,examp,unit,uses,usedby,perf,recipr,sig,wns,\n" +
                "w16ns,spn,kingdom,phylum,subphylum,class,subclass,ord,subord,\n" +
                "suborder,fam,subfam,gen,var,varn\n" +
                "{\n" +
                "\tfont-style: italic;\n" +
                "}\n" +
                "\n" +
                "qau,au {\n" +
                "\tfont-style: italic;\n" +
                "    font-weight: bold;\n" +
                "}\"\"\".encode('utf-8'), \"\"\"~/css/default.css\"\"\", content_type='text/html; charset=utf-8')\n" +
                "    w.add(\"\"\"<link rel=\"alternate stylesheet\" href=\"~/css/night.css\" type=\"text/css\" title=\"Night\"><link rel=\"stylesheet\" href=\"~/css/default.css\" type=\"text/css\"><p><ent>Dual</ent><br/><hw>Du\"al</hw> <pr>(?)</pr>, <pos>a.</pos> <ety>[L. <ets>dualis</ets>, fr. <ets>duo</ets> two. See <er>Two</er>.]</ety> <def>Expressing, or consisting of, the number two; belonging to two; <as>as, the <ex>dual</ex> number of nouns, etc.</as> , in Greek.</def><br/>[<source>1913 Webster</source>]</p><p><q>Here you have one half of our <qex>dual</qex> truth.</q> <rj><qau>Tyndall.</qau></rj><br/>[<source>1913 Webster</source>]</p>\"\"\".encode('utf-8'), \"\"\"dual\"\"\", content_type='text/html; charset=utf-8')\n" +
                "    w.add(\"\"\"<link rel=\"alternate stylesheet\" href=\"~/css/night.css\" type=\"text/css\" title=\"Night\"><link rel=\"stylesheet\" href=\"~/css/default.css\" type=\"text/css\"><p><ent>Dualin</ent><br/><hw>Du\"a*lin</hw> <pr>(?)</pr>, <pos>n.</pos> <fld>(Chem.)</fld> <def>An explosive substance consisting essentially of sawdust or wood pulp, saturated with nitroglycerin and other similar nitro compounds. It is inferior to dynamite, and is more liable to explosion.</def><br/>[<source>1913 Webster</source>]</p>\"\"\".encode('utf-8'), \"\"\"Dualin\"\"\", content_type='text/html; charset=utf-8')\n" +
                "    w.add(\"\"\"<link rel=\"alternate stylesheet\" href=\"~/css/night.css\" type=\"text/css\" title=\"Night\"><link rel=\"stylesheet\" href=\"~/css/default.css\" type=\"text/css\"><p><ent>Ularburong</ent><br/><hw>U*lar\"bu*rong</hw> <pr>(?)</pr>, <pos>n.</pos> <ety>[From the native Malay name.]</ety> <fld>(Zool.)</fld> <def>A large East Indian nocturnal tree snake (<spn>Dipsas dendrophila</spn>). It is not venomous.</def><br/>[<source>1913 Webster</source>]</p>\"\"\".encode('utf-8'), \"\"\"Ularburong\"\"\", content_type='text/html; charset=utf-8')\n" +
                "    w.add(\"\"\"<link rel=\"alternate stylesheet\" href=\"~/css/night.css\" type=\"text/css\" title=\"Night\"><link rel=\"stylesheet\" href=\"~/css/default.css\" type=\"text/css\"><p><h2>Unsuspecting</h2><br/><hw>Unsuspecting</hw><def>&nbsp;&nbsp;-&nbsp;&nbsp;See <er>suspecting</er>.</def></p>\"\"\".encode('utf-8'), \"\"\"Unsuspecting\"\"\", content_type='text/html; charset=utf-8')\n" +
                "    w.add(\"\"\"<link rel=\"alternate stylesheet\" href=\"~/css/night.css\" type=\"text/css\" title=\"Night\"><link rel=\"stylesheet\" href=\"~/css/default.css\" type=\"text/css\">null\"\"\".encode('utf-8'), \"\"\"<p><ent>Y</ent><br/><hw>Y</hw> <pr>(w&imacr;)</pr>. <def>Y, the twenty-fifth letter of the English alphabet, at the beginning of a word or syllable, except when a prefix (see Y-), is usually a fricative vocal consonant; as a prefix, and usually in the middle or at the end of a syllable, it is a vowel.  See <xex>Guide to Pronunciation</xex>, &sect;&sect; 145, 178-9, 272.</def><br/>[<source>1913 Webster</source>]</p><p><note> It derives its form from the Latin Y, which is from the Greek &Upsilon;, originally the same letter as V. Etymologically, it is most nearly related to <xex>u</xex>, <xex>i</xex>, <xex>o</xex>, and <xex>j</xex>. <xex>g</xex>; as in <xex>full</xex>, <xex>fill</xex>, AS. <xex>fyllan</xex>; E. <xex>crypt</xex>, <xex>grotto</xex>; <xex>young</xex>, <xex>juvenile</xex>; <xex>day</xex>, AS. <xex>d&aelig;g</xex>.  See <er>U</er>, <er>I</er>, and <er>J</er>, <er>G</er>.</note><br/>[<source>1913 Webster</source>]</p><p><note>&hand_; Y has been called the <xex>Pythagorean letter</xex>, because the Greek letter &Upsilon; was taken to represent the sacred triad, formed by the duad proceeding from the monad; and also because it represents the dividing of the paths of vice and virtue in the development of human life.</note><br/>[<source>1913 Webster</source>]</p>\"\"\", content_type='text/html; charset=utf-8')\n";
        String actualScriptContent = "";
        try {
            actualScriptContent = FileUtils.readFileToString(new File(scriptName));
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Unable to find generated script!");
        }

        Assert.assertEquals("test generate script file failed", expectedScriptContent, actualScriptContent);
        FileUtils.deleteQuietly(new File(scriptName));
    }

    @Test(expected = IOException.class)
    public void testEmptyDictItems() throws IOException {
        DictGenerator.createGeneratorScript(new ArrayList<DictItem>());
        FileUtils.readFileToString(new File(scriptName));
        FileUtils.deleteQuietly(new File(scriptName));
    }

    @Test(expected = IOException.class)
    public void testNullDictItems() throws IOException {
        DictGenerator.createGeneratorScript(null);
        FileUtils.readFileToString(new File(scriptName));
    }

    @After
    public void clean() {
        FileUtils.deleteQuietly(new File(scriptName));
    }
}
