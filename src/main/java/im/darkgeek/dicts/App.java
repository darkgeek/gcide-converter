package im.darkgeek.dicts;

import org.dom4j.*;

import java.util.Iterator;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws DocumentException {
        String text = "<!DOCTYPE dictionary [" +
                "<!ENTITY emacr        '&#x0113;'>" +
                "<!ENTITY oelig        '&#x0153;'>" +
                "<!ENTITY radic        '&#x221A;'>" +
                "]>" +
                "<p><ent>D</ent><br/>\n" +
                "<hw>D</hw> <pr>(d&emacr;)</pr> <sn>1.</sn> <def>The fourth " +
                "letter of the English alphabet, and a vocal consonant. The English letter is from Latin, which is from" +
                " Greek, which took it from Ph&oelig;nician, the probable ultimate origin being Egyptian. It is related" +
                " most nearly to <xex>t</xex> and <xex>th</xex>; <as>as, Eng. <xex>d</xex>eep, G. <xex>t</xex>ief; Eng." +
                " <xex>d</xex>aughter, G. <xex>t</xex>ochter, Gr. <grk>qyga`thr</grk>, Skr. <xex>d</xex></as>." +
                " See <xex>Guide to Pronunciation</xex>, &radic;178, 179, 229.</def><br/>\n" +
                "[<source>1913 Webster</source>]</p>";
        Document document = DocumentHelper.parseText(text);

        Element root = document.getRootElement();
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Element element = (Element) i.next();
            if (element.getName().equals("def")) {
                for (Iterator j = element.elementIterator(); j.hasNext();) {
                    Element defNode = (Element) j.next();
                    if (defNode.getName().equals("as")) {
                        System.out.println(defNode.asXML());
                        List contents = defNode.content();
                        Iterator iterator = contents.iterator();
                        while (iterator.hasNext()) {
                            Node node = (Node) iterator.next();
                            switch (node.getNodeType()) {
                                case Node.ELEMENT_NODE:
                                    System.out.println("Node type: element node");
                                    System.out.println("Node value: " + node.asXML());
                                    break;
                                case Node.TEXT_NODE:
                                    System.out.println("Node type: text node");
                                    System.out.println("Node value: " + node.getText());
                                    if (node.getText().startsWith("as, ")) {
                                        StringBuffer sb = new StringBuffer(node.getText());
                                        int insertPoint = sb.indexOf("as, ") + 4;
                                        sb.insert(insertPoint, "\"");
                                        node.setText(sb.toString());
                                    }
                                    break;
                            }
                        }
                        defNode.addText("\"");
                    }
                }
            }
        }
        System.out.println("full: " + root.asXML());
    }
}
