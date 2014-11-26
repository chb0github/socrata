package org.bongiorno.quiz.socrata.receipt.taxes;

import org.bongiorno.quiz.socrata.receipt.Item;

/**
 * @author cbongiorno
 */
public interface Tax {

    public Double apply(Item item);

    public boolean appliesTo(String category);

    public Double getRate();

}
