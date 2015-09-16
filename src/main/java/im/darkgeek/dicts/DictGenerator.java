package im.darkgeek.dicts;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by justin on 15-9-10.
 */
public class DictGenerator {
    private static String GENERATOR_SCRIPT_HEADER = "#!/usr/bin/env python\n" +
            "import slob\n" +
            "with slob.create('gcide.slob') as w:\n";
    private static String GENERATOR_TEMPLATE =
            "    w.add(\"\"\"#DEFINATION#\"\"\".encode('utf-8'), \"\"\"#WORD#\"\"\", content_type='text/html; charset=utf-8')";
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

    public static void createGeneratorScript(List<DictItem> items) {
        if (items == null)
            return;

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(SCRIPT_NAME, "UTF-8");

            printWriter.print(GENERATOR_SCRIPT_HEADER);
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
