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
        Callback handleComplexListBlock = new Callback() {
            public int process(Element element) {
                String noteText = element.getTextTrim();

                // remove the "-" sign at the end
                if (noteText.endsWith("-")) {
                    element.setText(noteText.substring(0, noteText.length() - 2));
                    System.out.println("-->" + element.getTextTrim() + "<--");
                }

                List contents = element.content();
                Iterator iterator = contents.iterator();
                // Remove the annoying "--" sign
                while (iterator.hasNext()) {
                    Node node = (Node) iterator.next();
                    if (node.getNodeType() == Node.TEXT_NODE) {
                        if (node.getText().trim().equals("--"))
                            node.setText("");
                    }
                }

                boolean isFirstColNode = true;
                for (Iterator i = element.elementIterator(); i.hasNext();) {
                    Element csNode = (Element) i.next();
                    if (csNode.getName().equals("sd")) {
                        List elements = csNode.getParent().elements();
                        elements.add(elements.indexOf(csNode), DocumentHelper.createElement("br"));
                    }
                    else if (csNode.getName().equals("col") || csNode.getName().equals("mcol")) {
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
        Callback wrapQuoteToAsInDef = new Callback() {
                public int process(Element element) {
                    for (Iterator j = element.elementIterator(); j.hasNext();) {
                        Element defNode = (Element) j.next();
                        if (defNode.getName().equals("as")) {
//                            System.out.println(defNode.asXML());
                            List contents = defNode.content();
                            Iterator iterator = contents.iterator();
                            while (iterator.hasNext()) {
                                Node node = (Node) iterator.next();
                                switch (node.getNodeType()) {
                                    case Node.ELEMENT_NODE:
//                                        System.out.println("Node type: element node");
//                                        System.out.println("Node value: " + node.asXML());
                                        break;
                                    case Node.TEXT_NODE:
//                                        System.out.println("Node type: text node");
//                                        System.out.println("Node value: " + node.getText());
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
                                .addQuirk("cs", handleComplexListBlock)
                                .addQuirk("note", handleComplexListBlock)
                                .addQuirk("def", wrapQuoteToAsInDef)
                                .generate();
        System.out.println("Size: " + list.size());
        DictGenerator.createGeneratorScript(list);
    }
}
