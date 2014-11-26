package org.bongiorno.quiz.socrata.receipt.taxes;

import org.bongiorno.quiz.socrata.receipt.Item;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * @author cbongiorno
 */
public class TaxTest {


    @Test
    public void testSalesTax() throws Exception {
        Tax salesTax = new SalesTax();

        Item item = new Item("bottle of perfume", 10.0d, "perfume", "imported");
        Double result = salesTax.apply(item);
        assertEquals(new Double(1.0d), result);

        item = new Item("music CD", 10.0d, "other", "imported");
        result = salesTax.apply(item);
        assertEquals(new Double(1.0d),result);
    }

    @Test
    public void testSalesTaxExemption() throws Exception {
        Tax tax = new SalesTax();

        Item item = new Item("pack of headache pills", 10.0d, "medical product");
        Double result = tax.apply(item);
        assertEquals(new Double(0.0d), result);

        item = new Item("best book in America", 10.0d, "book", "fiction");
        result = tax.apply(item);
        assertEquals(new Double(0.0d), result);


        item = new Item("imported chocolate", 10.0d, "food", "imported");
        result = tax.apply(item);
        assertEquals(new Double(0.0d), result);
    }

    @Test
    public void testRounding() throws Exception {
        Tax salesTax = new SalesTax();
        Tax importDuty = new ImportDuty();

        Item testItem = new Item("test item", 27.99d, "other", "imported");

        Double taxApplied = salesTax.apply(testItem);
        assertEquals(new Double(2.8d), taxApplied);

        taxApplied = importDuty.apply(testItem);
        assertEquals(new Double(1.4d), taxApplied);

    }

    @Test
    public void testImportDuty() throws Exception {
        Tax tax = new ImportDuty();

        // exempted
        Item item = new Item("book",27.99d, "book");
        Double taxApplied = tax.apply(item);
        assertEquals(new Double(0.0d), taxApplied);

        item = new Item("book", 10.0d, "imported", "other");
        taxApplied = tax.apply(item);
        assertEquals(new Double(.5d), taxApplied);

    }
}
