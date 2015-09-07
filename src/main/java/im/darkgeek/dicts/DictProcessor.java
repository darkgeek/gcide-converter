package im.darkgeek.dicts;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by justin on 15-8-28.
 */
public class DictProcessor {
    private String dictContents;
    private Map<String, Callback> quirksMap;

    public DictProcessor loadXML(String dictContentsXML) {
        dictContents = dictContentsXML;

        return this;
    }

    public DictProcessor setQuirksCallbacks(Map<String, Callback> quirksMap) {
        this.quirksMap = quirksMap;

        return this;
    }

    public DictProcessor addQuirk(String tagname, Callback callback) {
        if (quirksMap != null) {
            quirksMap.put(tagname, callback);
        }

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
                // TODO Handle other kinds of elements
            }
            if (currentDictItem != null) {
                currentDictItem.setExplanation(currentDictItem.getExplanation() + pElement.asXML());
            }
        }

        return itemList;
    }
}
