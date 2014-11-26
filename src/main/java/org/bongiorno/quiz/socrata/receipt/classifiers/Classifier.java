package org.bongiorno.quiz.socrata.receipt.classifiers;

import org.bongiorno.quiz.socrata.receipt.Item;

import java.util.Set;

/**
 * @author cbongiorno
 */
public interface Classifier {

    public Set<String> classify(String item);
}
