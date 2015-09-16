package im.darkgeek.dicts;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by justin on 15-8-28.
 */
public class DictProcessor {
    private String dictContents;
    private Map<String, Callback> quirksMap = new HashMap<String, Callback>(5);

    private static class CompoundDictItem {
        public List<String> words = new ArrayList<String>(1);
        public String explanation = "";
    }

    public DictProcessor loadXML(String dictContentsXML) {
        dictContents = dictContentsXML;

        return this;
    }

    public DictProcessor addQuirk(String tagname, Callback callback) {
        quirksMap.put(tagname, callback);

        return this;
    }

    public List<DictItem> generate() {
        if (dictContents == null || "".equals(dictContents))
            return new ArrayList<DictItem>(1);

        List<DictItem> itemList = new ArrayList<DictItem>();
        Document document;
        try {
            document = DocumentHelper.parseText(dictContents);
        } catch (DocumentException e) {
            e.printStackTrace();
            return new ArrayList<DictItem>(1);
        }

        CompoundDictItem currentDictItem = null;
        Element root = document.getRootElement();
        for (Iterator k = root.elementIterator("body"); k.hasNext(); ) {
            Element bodyElement = (Element) k.next();
            pNodeLoop:
            for (Iterator i = bodyElement.elementIterator("p"); i.hasNext();) {
                Element pElement = (Element) i.next();
                int entNodeCounts = 0;
                // Add the last word in XML
                if (!i.hasNext()) {
                    addDictItems(currentDictItem, itemList);
                }
                if (isToBeOmitted(pElement)) {
                    continue;
                }
                for (Iterator j = pElement.elementIterator(); j.hasNext();) {
                    Element currElement = (Element) j.next();
                    // Every <ent> node in <p> indicates a word, and there might be more than one <ent> nodes
                    // in this <p>, that all have the same definition, so this is the reason why we use CompoundDictItem
                    // here
                    if (currElement.getName().equals("ent")) {
                        String word = currElement.getTextTrim();
                        if (isMarkwordOfUnList(word)) {
                            // Process several long lists of Un- prefixed words
                            processLonglistUnPrefixedWords(pElement, itemList, currentDictItem);
                            continue pNodeLoop;
                        }
                        entNodeCounts++;
                        if (entNodeCounts == 1) {
                            // If it's the first <ent> node, it indicates the end of the previous word(s)
                            addDictItems(currentDictItem, itemList);
                            currentDictItem = new CompoundDictItem();
                        }
                        currentDictItem.words.add(word);
                    }
                    // Post process for the element
                    postElementProcess(currElement);
                }
                if (currentDictItem != null) {
                    currentDictItem.explanation = currentDictItem.explanation + pElement.asXML();
                }

            }
        }

        return itemList;
    }

    private void addDictItems(CompoundDictItem compoundDictItem, List<DictItem> itemList) {
        if (compoundDictItem == null || itemList == null)
            return;

        for (String word : compoundDictItem.words) {
            DictItem dictItem = new DictItem();
            dictItem.setWord(word);
            dictItem.setExplanation(compoundDictItem.explanation);

            itemList.add(dictItem);
//            System.out.println("Add new item: " + dictItem.getWord());
        }
    }

    private void postElementProcess(Element element) {
        for (Iterator i = element.elementIterator(); i.hasNext(); ) {
            Element child = (Element) i.next();
            postElementProcess(child);
        }

        String elemName = element.getName();
        // Handle elements specifically
        if (quirksMap != null && quirksMap.containsKey(elemName)) {
            quirksMap.get(elemName).process(element);
        }
    }

    /**
     * Omit element that is mostly used for comment or metadata
     * @param element The element to be checked
     * @return whether this element should be omitted
     */
    private boolean isToBeOmitted(Element element) {
        String elementXML = element.asXML();
        Pattern pattern = Pattern.compile("^(<p><!--|<p><centered>)");

        return pattern.matcher(elementXML).lookingAt();
    }

    /**
     * Check if the given value of <ent> node is the "markword" of the "Un-" list
     * Note: The "markword" is the start word of a long list of Un- prefixed words, in "Un-"'s definition.
     *       For instance, see the snippets below:
     *
     *       <p><ent>Un-</ent><br/>
     *       <hw>Un-</hw>. <ety>[OE. &amp; AS. <ets>un-</ets>; akin to OFries. <ets>un-</ets>, D. <ets>on-</ets>
     *       ...skip...
     *       <br/>
     *       <p><ent>Undoubtful</ent><br/>
     *       <ent>Undomestic</ent><br/>
     *       <ent>Undivine</ent><br/>
     *       <ent>Undividable</ent><br/>
     *       <ent>Undistinguishable</ent><br/>
     *       <ent>Undissolvable</ent><br/>
     *       <ent>Undiscoverable</ent><br/>
     *       <ent>Undiscordant</ent><br/>
     *       ...skip...
     *       <hw>Undoubtful</hw>  <def>See <er>doubtful</er>.</def><br/>
     *       <colbreak/><!-- end of subcol 1 (of 3) in col 3 (of 3) p. 1562.
     *          ca. 110 entries per sub column
     *       --><br/>
     *       [<source>1913 Webster</source>]</p>
     *
     *       <p><ent>Unheritable</ent><br/>
     *       <ent>Unhelpful</ent><br/>
     *       <ent>Unheedful</ent><br/>
     *       ...skip...
     *
     *       The first line denotes this is the word "Un-", whose definitions are right below. There are several lists
     *       whose words have "Un" prefixed, seperated by different <p></p> nodes. The first list starts with word
     *       "Undoubtful" at "<p><ent>Undoubtful</ent><br/>" line, the second list starts with "Unheritable" at
     *       "<p><ent>Unheritable</ent><br/>" line, and so on. Both "Undoubtful" and "Unheritable" are "markword".
     * @param entValue the <ent> node value
     * @return whether it's a "markword"
     */
    private boolean isMarkwordOfUnList(String entValue) {
        Set<String> markwords = new HashSet<String>(Arrays.asList("Undoubtful", "Unheritable", "Unobjectionable",
                "Unzealous", "Unyielded", "Unyielding"));

        return markwords.contains(entValue);
    }

    private void processLonglistUnPrefixedWords(Element pElement, List<DictItem> itemList, CompoundDictItem unCDictItem) {
        CompoundDictItem compoundDictItem = new CompoundDictItem();
        String unDefTemplate = "<p><h1>#WORD#</h1><br/><hw>#WORD#</hw>" +
                "<def>&nbsp;&nbsp;-&nbsp;&nbsp;#DEFINITION#</def></p>";

        for (Iterator i = pElement.elementIterator(); i.hasNext(); ) {
            Element currElement = (Element) i.next();

            if (currElement.getName().equals("hw")) {
                compoundDictItem.words.add(currElement.getTextTrim());
            }
            else if (currElement.getName().equals("def")) {
                postElementProcess(currElement);
//                System.out.println(currElement.asXML());
                // This fixes the issue to parse the line:
                // "<hw>Unemphatic</hw>  <def>See <er>emphatic</er>.</def>  <def>See <er>employable</er>.</def>"
                // It's strange to see one <hw>, followed by two <def> blocks. I think the latter one is misplaced.
                if (compoundDictItem.words.size() == 0)
                    continue;
                compoundDictItem.explanation = unDefTemplate.replace("#WORD#", compoundDictItem.words.get(0))
                                                            .replace("#DEFINITION#", currElement.asXML());
                addDictItems(compoundDictItem, itemList);
                compoundDictItem = new CompoundDictItem();
            }
            else if (currElement.getName().equals("sd")) {
                postElementProcess(currElement);
                unCDictItem.explanation += "<p><br/>" + currElement.asXML() +
                        "<def>" + ((Element) i.next()).getTextTrim() + "</def></p>";
            }
        }
    }

}
