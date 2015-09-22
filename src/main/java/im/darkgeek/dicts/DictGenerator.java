package im.darkgeek.dicts;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generate script (Python script only) to create slob dictionary file
 * Created by justin on 15-9-10.
 */
public class DictGenerator {
    private static String GENERATOR_SCRIPT_HEADER = "#!/usr/bin/env python\n" +
            "import slob\n" +
            "with slob.create('gcide.slob') as w:\n";
    private static String GENERATOR_TEMPLATE =
            "    w.add(\"\"\"#DEFINATION#\"\"\".encode('utf-8'), \"\"\"#WORD#\"\"\", content_type='text/html; charset=utf-8')";
    private static String GENERATOR_TAG_TEMPLATE =
            "    w.tag(\"\"\"#NAME#\"\"\", \"\"\"#VALUE#\"\"\")";
    private static String nightModeCss = "body {\n" +
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
            "}\n";
    private static String nightModeCssLink = "<link rel=\"alternate stylesheet\" href=\"~/css/night.css\"" +
            " type=\"text/css\" title=\"Night\">";
    private static String defaultCss = "pos {\n" +
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
            "}";
    private static String defaultCssLink = "<link rel=\"stylesheet\" href=\"~/css/default.css\" type=\"text/css\">";
    private static String SCRIPT_NAME = "dict_creator.py";
    private static Map<String, String> tagMap = new HashMap<String, String>(7);

    static {
        // slob tag info
        tagMap.put("created.by", "https://github.com/darkgeek/gcide-converter");
        tagMap.put("license.name", "GNU General Public License v3 or later");
        tagMap.put("license.url", "http://www.gnu.org/licenses/gpl.html");
        tagMap.put("source", "http://www.ibiblio.org/webster/");
        tagMap.put("label", "The Collaborative International Dictionary of English v0.51");
        tagMap.put("uri", "http://savannah.gnu.org/projects/gcide/");
        tagMap.put("copyright", "Public Domain");
    }

    /**
     * Generate the python script to create slob file
     * @param items A list of DictItem instances extracted from raw gcide xml file
     */
    public static void createGeneratorScript(List<DictItem> items) {
        if (items == null || items.size() == 0)
            return;

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(SCRIPT_NAME, "UTF-8");

            // write script header
            printWriter.print(GENERATOR_SCRIPT_HEADER);

            // write tags
            Set<String> tagNames = tagMap.keySet();
            for (String name : tagNames) {
                printWriter.println(
                        GENERATOR_TAG_TEMPLATE
                            .replace("#NAME#", name)
                            .replace("#VALUE#", tagMap.get(name))
                );
            }

            // write css resources
            printWriter.println(
                    GENERATOR_TEMPLATE
                        .replace("#DEFINATION#", nightModeCss)
                        .replace("#WORD#", "~/css/night.css")
            );
            printWriter.println(
                    GENERATOR_TEMPLATE
                        .replace("#DEFINATION#", defaultCss)
                        .replace("#WORD#", "~/css/default.css")
            );

            // write words
            for (DictItem item : items) {
                printWriter.println(
                        GENERATOR_TEMPLATE
                            .replace("#DEFINATION#", nightModeCssLink + defaultCssLink + item.getExplanation())
                            .replace("#WORD#", item.getWord())
                );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            printWriter.close();
        }
    }
}
