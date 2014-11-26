package org.bongiorno.quiz.socrata.receipt.taxes;

import org.bongiorno.quiz.socrata.receipt.Item;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author cbongiorno
 */
@Component
public class SalesTax implements Tax {

    private Set<String> exemptions = new HashSet<String>(Arrays.asList("book","food","medical product"));

    private double rate =.1d;

    public SalesTax() {
    }

    public SalesTax(double rate) {
        this.rate = rate;
    }

    public SalesTax(Set<String> exemptions) {
        this.exemptions = exemptions;
    }

    public SalesTax(Set<String> exemptions, double rate) {
        this.exemptions = exemptions;
        this.rate = rate;
    }

    @Override
    public Double apply(Item item) {
        double result = 0.0d;
        if (item != null) {
            boolean applies = true;
            // wouldn't it be nice if I had an 'intersects method that didn't modify the set!
            for (String classification : item.getClassifications()) {
                if(!appliesTo(classification)) {
                    applies = false;
                    break;
                }
            }
            if(applies)
                result =  item.getPrice() * rate;

        }

        return Math.round(result * 20) / 20.0;
    }

    @Override
    public boolean appliesTo(String classification) {
        return classification != null && !exemptions.contains(classification);
    }

    public Double getRate() {
        return rate;
    }

    public Set<String> getExemptions() {
        return exemptions;
    }

    @Override
    public String toString() {
        return "SalesTax";
    }
}
