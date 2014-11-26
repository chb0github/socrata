package org.bongiorno.quiz.socrata.receipt;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * @author cbongiorno
 */
public class Item {

    private Map<String,Object> properties = new LinkedHashMap<String, Object>();

    private double price;

    String receiptLine = "";

    public Item(String name, Set<String> classification, double price) {
        properties.put("classifications",classification);
        properties.put("name",name);

        this.price = price;
        receiptLine = properties.get("name") + ": " + price +"\n";
    }

    public Item(String name, double price, String ... classifications) {
        this(name,new HashSet<String>(asList(classifications)),price);
    }

    public Object setProperty(String name, String value) {
        return properties.put(name, value);
    }

    public boolean hasProperty(String name) {
        return name != null && properties.containsKey(name);
    }

    public String getName() {
        return properties.get("name").toString();
    }

    public Set<String> getClassifications() {
        return (Set<String>) properties.get("classifications");
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "{" +
                "price=" + price +
                ", properties=" + properties +
                ", receiptLine='" + receiptLine + '\'' +
                '}';
    }

    public String getReceiptLine() {
        return receiptLine;
    }
}
