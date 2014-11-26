package org.bongiorno.quiz.socrata.receipt;

import org.bongiorno.quiz.socrata.receipt.taxes.Tax;

import java.util.*;

/**
 * @author cbongiorno
 */
public class Receipt {

    private Map<String,List<Entry>> entries = new LinkedHashMap<String, List<Entry>>();

    public void addItem(Item item, double totalTax, Collection<Tax> taxesApplied) {
        List<Entry> items = entries.get(item.getName());
        if(items == null) {
            items = new LinkedList<Entry>();
            entries.put(item.getName(),items);
        }
        items.add(new Entry(item, totalTax, taxesApplied));
    }

    @Override
    public String toString() {
        double totalTaxes = 0.0d;
        double total = 0.0d;

        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<String, List<Entry>> purchaseItem : entries.entrySet()) {
            // put all items in 1 list since taxes are summed
            List<Entry> items = purchaseItem.getValue();

            for (Entry itemEntry : items) {
                totalTaxes += itemEntry.getTotalTax();
                Item item = itemEntry.getItem();
                total += item.getPrice();
                String line = String.format("%d %s: %.02f", items.size(), item.getName(), item.getPrice() + itemEntry.getTotalTax());
                buffer.append(line).append(System.lineSeparator());
            }

        }

        buffer.append("Sales Tax: ").append(String.format("%.02f",totalTaxes)).append(System.lineSeparator());
        buffer.append("Total: ").append(String.format("%.02f",total + totalTaxes)).append(System.lineSeparator());

        return buffer.toString();
    }


    private static class Entry {

        private Item item;
        private Collection<Tax> taxesApplied;
        private double totalTax;

        private Entry(Item item, double totalTax, Collection<Tax> taxesApplied) {
            this.item = item;
            this.taxesApplied = taxesApplied;
            this.totalTax = totalTax;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            String otherName = ((Entry) o).item.getName();
            return item.getName().equals(otherName);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        private Item getItem() {
            return item;
        }

        private Collection<Tax> getTaxesApplied() {
            return taxesApplied;
        }

        private double getTotalTax() {
            return totalTax;
        }

        @Override
        public String toString() {
            return item.getName() + ": " + item.getPrice();
        }
    }


}
