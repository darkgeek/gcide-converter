package im.darkgeek.dicts;

import org.dom4j.*;

import java.util.Iterator;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Converter
{
    public static void main( String[] args ) throws DocumentException {
        XMLDictReader reader = XMLDictReader.getInstance();
        DictProcessor dictProcessor = new DictProcessor();
        Callback addBr = new Callback() {
                public int process(Element element) {
                        List elements = element.getParent().elements();
                        elements.add(elements.indexOf(element) + 1, DocumentHelper.createElement("br"));
                        return 1;
                }
        };
        Callback wrapQuoteToAsInDef = new Callback() {
                public int process(Element element) {
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
                    return 1;
                }
        };

        System.setProperty("entityExpansionLimit", "640000");
        System.out.println(System.getProperty("user.dir"));
        List<DictItem> list = dictProcessor
                                .loadXML(reader.loadRawDict("gcide.xml"))
                                .addQuirk("ety", addBr)
                                .addQuirk("pos", addBr)
                                .addQuirk("def", wrapQuoteToAsInDef)
                                .generate();
        System.out.println("Size: " + list.size());
    }
}
