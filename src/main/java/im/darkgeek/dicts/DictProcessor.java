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
                        entNodeCounts++;
                        if (entNodeCounts == 1) {
                            // If it's the first <ent> node, it indicates the end of the previous word(s)
                            addDictItems(currentDictItem, itemList);
                            currentDictItem = new CompoundDictItem();
                        }
                        currentDictItem.words.add(word);
                    }
                    // Handle other kinds of elements specifically
                    if (quirksMap != null && quirksMap.containsKey(currElement.getName())) {
                        quirksMap.get(currElement.getName()).process(currElement);
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

        if (compoundDictItem.words.size() > 2) {
            System.out.println("size: " + compoundDictItem.words.size() + " === " + compoundDictItem.words.get(0));
        }
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
        Set<String> wordsNotToBeProcessed = new HashSet<String>(Arrays.asList("br", "p"));

        if (wordsNotToBeProcessed.contains(elemName))
            return;

        element.setName("span");
        element.addAttribute("class", elemName);
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

}
