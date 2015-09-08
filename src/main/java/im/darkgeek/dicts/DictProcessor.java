package im.darkgeek.dicts;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by justin on 15-8-28.
 */
public class DictProcessor {
    private String dictContents;
    private Map<String, Callback> quirksMap = new HashMap<String, Callback>(5);

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

        DictItem currentDictItem = null;
        Element root = document.getRootElement();
        for (Iterator i = root.elementIterator("p"); i.hasNext();) {
            Element pElement = (Element) i.next();
            for (Iterator j = pElement.elementIterator(); j.hasNext();) {
                Element currElement = (Element) j.next();
                if (currElement.getName().equals("ent")) {
                    String currText = currElement.getTextTrim();
                    if (currentDictItem != null) {
                        itemList.add(currentDictItem);
                    }
                    currentDictItem = new DictItem();
                    currentDictItem.setWord(currText);
                    currentDictItem.setExplanation("");
                }
                // Handle other kinds of elements specifically
                if (quirksMap != null && quirksMap.containsKey(currElement.getName())) {
                    quirksMap.get(currElement.getName()).process(currElement);
                }
                // Post process for the element
                postElementProcess(currElement);
            }
            if (currentDictItem != null) {
                currentDictItem.setExplanation(currentDictItem.getExplanation() + pElement.asXML());
            }

            // Add the last word in XML
            if (!i.hasNext()) {
                itemList.add(currentDictItem);
            }
        }

        return itemList;
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

        element.setName("div");
        element.addAttribute("class", elemName);
    }
}
