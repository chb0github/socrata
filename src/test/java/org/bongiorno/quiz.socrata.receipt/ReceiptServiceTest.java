package org.bongiorno.quiz.socrata.receipt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;
import java.util.List;

/**
 * @author cbongiorno
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class ReceiptServiceTest {

    @Autowired
    private ReceiptService receiptService;

    @Test
    public void testReceiptService() throws Exception {



        ClassPathResource resource = new ClassPathResource("purchase-order.txt");
        InputStream inputStream = resource.getInputStream();

        List<Receipt> receipts = receiptService.loadPurchaseOrder(inputStream);

        int count = 1;
        for (Receipt receipt : receipts) {
            System.out.println("Output " + count++ +":");
            System.out.println(receipt);
        }
        System.out.println("==========");
    }

}
