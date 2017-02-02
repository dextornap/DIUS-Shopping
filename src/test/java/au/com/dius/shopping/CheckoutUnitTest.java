package au.com.dius.shopping;

import au.com.dius.shopping.constant.SKU;
import au.com.dius.shopping.context.ApplicationContext;
import au.com.dius.shopping.rules.IPricingRule;
import au.com.dius.shopping.service.Checkout;
import org.junit.Test;

import java.util.Map;

import static au.com.dius.shopping.constant.SKU.*;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for checkout operations at POS.
 */
public class CheckoutUnitTest {

    private final Map<SKU, IPricingRule> pricingRules = ApplicationContext.getPricingRules();

    @Test
    public void testCheckout1() {

        Checkout checkout = new Checkout(pricingRules);
        checkout.scan(atv);
        checkout.scan(atv);
        checkout.scan(atv);
        checkout.scan(vga);
        Double total = checkout.total();

        assertEquals(249.00, total, 0.0);

    }

    @Test
    public void testCheckout2() {

        Checkout checkout = new Checkout(pricingRules);
        checkout.scan(atv)
                .scan(ipd)
                .scan(ipd)
                .scan(atv)
                .scan(ipd)
                .scan(ipd)
                .scan(ipd);
        Double total = checkout.total();

        assertEquals(2718.95, total, 0.0);

    }

    @Test
    public void testCheckout3() {

        Checkout checkout = new Checkout(pricingRules);
        checkout.scan(mbp)
                .scan(vga)
                .scan(ipd);
        Double total = checkout.total();

        assertEquals(1949.98, total, 0.0);

    }

    // ----------------- Corner cases -----------------------

    @Test(expected = RuntimeException.class)
    public void testCheckoutNullItem() {

        Checkout checkout = new Checkout(pricingRules);
        checkout.scan(null);

    }

    @Test(expected = RuntimeException.class)
    public void testCheckoutItemNotInCatalogue() {

        Checkout checkout = new Checkout(pricingRules);
        checkout.scan(iphone);

    }

    @Test
    public void testCheckoutCartClearedAfterTotal() {

        Checkout checkout = new Checkout(pricingRules);
        checkout.scan(mbp)
                .scan(vga);
        Double total = checkout.total();

        assertEquals(1399.99, total, 0.0);

        // old cart cleared. ready to serve next customer.
        checkout.scan(atv)
                .scan(atv)
                .scan(atv);

        total = checkout.total();

        assertEquals(219.00, total, 0.0);

    }


}
