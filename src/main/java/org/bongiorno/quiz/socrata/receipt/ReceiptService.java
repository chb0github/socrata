package org.bongiorno.quiz.socrata.receipt;

import org.bongiorno.quiz.socrata.receipt.classifiers.Classifier;
import org.bongiorno.quiz.socrata.receipt.taxes.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cbongiorno
 */
@Component
public class ReceiptService {

    @Autowired
    private Collection<Tax> taxes;

    @Autowired
    private Classifier classifier;

    private final Pattern pattern = Pattern.compile("([0-9]+) (.*) at ([0-9]+\\.[0-9]{2})");

    public ReceiptService() {
    }

    public ReceiptService(Collection<Tax> taxes) {
        this.taxes = taxes;
    }

    public Receipt getReceipt(List<Item> items) {
        Receipt result = new Receipt();

        for (Item item : items) {
            double salesTaxes = 0.0d;
            Collection<Tax> applied = new LinkedList<Tax>();
            for (Tax tax : taxes) {

                double itemTax = tax.apply(item);
                // though not ideal, a tax of zero implies not taxes were applied
                if(itemTax != 0.0d)
                    applied.add(tax);

                salesTaxes += itemTax;
            }

            result.addItem(item,salesTaxes,applied);
        }
        return result;
    }


    private List<Item> readEntry(Pattern p, BufferedReader reader, String startLine) throws IOException {
        List<Item> items = new LinkedList<Item>();
        String line = startLine;
        while (true) {
            //1 book at 12.49
            Matcher matcher = p.matcher(line);
            if (!matcher.matches())
                break;

            MatchResult matchResult = matcher.toMatchResult();

            int quantity = Integer.parseInt(matchResult.group(1));
            String name = matchResult.group(2);
            Double price = new Double(matchResult.group(3));

            Set<String> classifications = classifier.classify(name);

            for (int i = 0; i < quantity; i++)
                items.add(new Item(name, classifications, price));

            if (!reader.ready())
                break;
            line = reader.readLine();

        }
        return items;
    }

    private String moveForward(BufferedReader reader) throws IOException {

        String line = "";
        do {
            line = reader.readLine();
        }
        while (reader.ready() && line.matches("Input [0-9]+:") && line.trim().length() != 0);

        return line;
    }

    public List<Receipt> loadPurchaseOrder(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<Receipt> receipts = new LinkedList<Receipt>();
        while (reader.ready()) {
            String startEntry = moveForward(reader);
            List<Item> items = readEntry(pattern, reader, startEntry);

            Receipt receipt = getReceipt(items);
            receipts.add(receipt);
        }
        return receipts;

    }
}
