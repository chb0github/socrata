package org.bongiorno.quiz.socrata.receipt.classifiers;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * provide a really primitive means of determining an item type
 * @author cbongiorno
 */
@Component
public class SimpleClassifier implements Classifier {

    private static final Map<String,String> classifications = new HashMap<String, String>();

    static {
        classifications.put("book","book");
        classifications.put("books","book");
        classifications.put("novel","book");
        classifications.put("fiction","book");
        classifications.put("chocolate","food");
        classifications.put("chocolates","food");
        classifications.put("vanilla","food");
        classifications.put("pills","medical product");
        classifications.put("pill","medical product");
        classifications.put("device","medical product");
        classifications.put("devices","medical product");
        classifications.put("imported","imported");
        classifications.put("exotic","imported");
    }

    @Override
    public Set<String> classify(String item) {
        Set<String> result = new HashSet<String>();
        String[] parts = item.split(" ");
        // first word that matches, wins. Very crude. Better to use expert System
        // See http://www.uspto.gov/web/patents/patog/week30/OG/html/1392-4/US08495068-20130723.html

        for (String part : parts) {
            String type = classifications.get(part);
            if(type != null)
                result.add(type);
            else
                result.add("other");
        }
        return result;
    }
}
