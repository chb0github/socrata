package org.bongiorno.quiz.socrata.receipt.taxes;

import org.bongiorno.quiz.socrata.receipt.classifiers.Classifier;
import org.bongiorno.quiz.socrata.receipt.classifiers.SimpleClassifier;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author cbongiorno
 */
public class ClassifierTest {

    @Test
    public void testSimpleClassifier() throws Exception {
        Classifier classifier = new SimpleClassifier();

        Set<String> classifications = classifier.classify("book");
        assertTrue(classifications.contains("book"));

        classifications = classifier.classify("music CD");
        assertTrue(classifications.contains("other"));

        classifications = classifier.classify("chocolate bar");
        assertTrue(classifications.contains("food"));

        classifications = classifier.classify("imported box of chocolates");
        assertEquals(3,classifications.size());
        assertTrue(classifications.containsAll(Arrays.asList("food", "imported","other")));

        classifications = classifier.classify("imported bottle of perfume");
        assertEquals(2,classifications.size());
        assertTrue(classifications.containsAll(Arrays.asList("other", "imported")));


        classifications = classifier.classify("bottle of perfume");
        assertTrue(classifications.contains("other"));

        classifications = classifier.classify("packet of headache pills");
        assertEquals(2,classifications.size());
        assertTrue(classifications.containsAll(Arrays.asList("other", "medical product")));

        classifications = classifier.classify("box of imported chocolates");
        assertEquals(3,classifications.size());
        assertTrue(classifications.containsAll(Arrays.asList("food", "imported","other")));


    }
}
