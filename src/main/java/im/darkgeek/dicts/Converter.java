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
        Callback addBRafter = new Callback() {
                public int process(Element element) {
                        List elements = element.getParent().elements();
                        elements.add(elements.indexOf(element) + 1, DocumentHelper.createElement("br"));
                        return 1;
                }
        };
        Callback handleComplexListBlock = new Callback() {
            public int process(Element element) {
                if (element.getName().equals("note")) {
                    List noteContents = element.content();
                    Iterator contentsIterator = noteContents.iterator();
                    while (contentsIterator.hasNext()) {
                        Node node = (Node) contentsIterator.next();
                        if (node.getNodeType() == Node.TEXT_NODE) {
                            node.setText("Note: " + node.getText());
                            break;
                        }
                    }
                }
                
                boolean isFirstColNode = true;
                for (Iterator i = element.elementIterator(); i.hasNext();) {
                    Element csNode = (Element) i.next();
                    if (csNode.getName().equals("col") || csNode.getName().equals("mcol")) {
                        List elements = csNode.getParent().elements();
                        if (!isFirstColNode)
                            elements.add(elements.indexOf(csNode), DocumentHelper.createElement("br"));
                        elements.add(elements.indexOf(csNode), DocumentHelper.createElement("br"));
                        isFirstColNode = false;
                    }
                }
                return 1;
            }
        };
        Callback addBRbefore = new Callback() {
            public int process(Element element) {
                List elements = element.getParent().elements();
                elements.add(elements.indexOf(element), DocumentHelper.createElement("br"));
                return 1;
            }
        };
        Callback handleASblock = new Callback() {
            public int process(Element element) {
                List contents = element.content();
                Iterator iterator = contents.iterator();
                while (iterator.hasNext()) {
                    Node node = (Node) iterator.next();
                    switch (node.getNodeType()) {
                        case Node.TEXT_NODE:
                            if (node.getText().startsWith("as, ")) {
                                StringBuffer sb = new StringBuffer(node.getText());
                                int insertPoint = sb.indexOf("as, ") + 4;
                                sb.insert(insertPoint, "\"");
                                node.setText(sb.toString());
                            }
                            break;
                    }
                }
                element.addText("\"");
                return 1;
            }
        };
        Callback rewriteElementNameToBR = new Callback() {
            public int process(Element element) {
                element.setName("br");
                return 1;
            }
        };

        System.setProperty("entityExpansionLimit", "640000");
        System.out.println(System.getProperty("user.dir"));
        List<DictItem> list = dictProcessor
                                .loadXML(reader.loadRawDict("gcide.xml"))
                                .addQuirk("ety", addBRafter)
                                .addQuirk("pos", addBRafter)
                                .addQuirk("cs", handleComplexListBlock)
                                .addQuirk("note", handleComplexListBlock)
                                .addQuirk("p", handleComplexListBlock)
                                .addQuirk("sd", addBRbefore)
                                .addQuirk("as", handleASblock)
                                .addQuirk("pbr", rewriteElementNameToBR)
                                .generate();
        System.out.println("Size: " + list.size());
        DictGenerator.createGeneratorScript(list);
    }
}
