package org.bongiorno.quiz.socrata.receipt.taxes;

import org.bongiorno.quiz.socrata.receipt.Item;
import org.springframework.stereotype.Component;

/**
 * @author cbongiorno
 */
@Component
public class ImportDuty implements Tax {

    private Double rate = .05d;

    public ImportDuty() {
    }

    public ImportDuty(double rate) {
        this.rate = rate;
    }


    @Override
    public Double apply(Item item) {
        Double result = 0.0d;
        if(item != null && item.getClassifications().contains("imported"))
            result = item.getPrice() *  rate;

        return Math.round(result * 20) / 20.0;
    }

    @Override
    public boolean appliesTo(String classification) {
       return classification != null && classification.equals("imported");
    }


    public Double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "ImportDuty";
    }
}
