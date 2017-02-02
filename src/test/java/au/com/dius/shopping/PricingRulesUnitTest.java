package au.com.dius.shopping;

import au.com.dius.shopping.constant.SKU;
import au.com.dius.shopping.context.ApplicationContext;
import au.com.dius.shopping.domain.Catalogue;
import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.domain.ItemStatistics;
import au.com.dius.shopping.rules.BulkDiscountPricingRule;
import au.com.dius.shopping.rules.FreeBeePricingRule;
import au.com.dius.shopping.rules.IPricingRule;
import au.com.dius.shopping.rules.ThreeForTwoPricingRule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for various pricing rule (strategy) configured in the application
 */
public class PricingRulesUnitTest {

    private final Catalogue catalogue = ApplicationContext.getCatalogue();

    @Test
    public void testCheckItemsInCatalogue() {

        assertEquals(549.99, catalogue.getItem(SKU.ipd).getPrice(), 0.0);
        assertEquals("VGA adapter", catalogue.getItem(SKU.vga).getName());

        assertEquals(4, catalogue.getAllItems().size());
    }

    @Test
    public void testThreeForTwoPricingRule() {

        IPricingRule rule = new ThreeForTwoPricingRule(SKU.atv);

        Map<Item, ItemStatistics> cart = new HashMap<>();

        Item appleTv = catalogue.getItem(SKU.atv);
        ItemStatistics stats = new ItemStatistics();

        cart.put(appleTv, stats);
        rule.applyOn(cart);
        assertEquals(0.0, cart.get(appleTv).getAmount(), 0.0);

        // cart now has 1 appleTv
        stats.incrementQuantity(1);
        cart.put(appleTv, stats);
        rule.applyOn(cart);
        // customer has to pay for 1 appleTv
        assertEquals(appleTv.getPrice(), cart.get(appleTv).getAmount(), 0.0);

        // cart now has 2 appleTv
        stats.incrementQuantity(1);
        cart.put(appleTv, stats);
        rule.applyOn(cart);
        // customer has to pay for 2 appleTv
        assertEquals(2 * appleTv.getPrice(), cart.get(appleTv).getAmount(), 0.0);

        // cart now has 3 appleTv
        stats.incrementQuantity(1);
        cart.put(appleTv, stats);
        rule.applyOn(cart);
        // customer has to pay for 2 appleTv only
        assertEquals(2 * appleTv.getPrice(), cart.get(appleTv).getAmount(), 0.0);

        // cart now has 5 appleTv
        stats.incrementQuantity(2);
        cart.put(appleTv, stats);
        rule.applyOn(cart);
        // customer has to pay for 4 appleTv only
        assertEquals(4 * appleTv.getPrice(), cart.get(appleTv).getAmount(), 0.0);

    }

    @Test
    public void testBulkDiscountPricingRule() {

        IPricingRule rule = new BulkDiscountPricingRule(SKU.ipd, 4, 499.99);

        Map<Item, ItemStatistics> cart = new HashMap<>();

        Item superIPad = catalogue.getItem(SKU.ipd);
        ItemStatistics stats = new ItemStatistics();

        cart.put(superIPad, stats);
        rule.applyOn(cart);
        assertEquals(0.0, cart.get(superIPad).getAmount(), 0.0);

        // cart now has 1 superIPad
        stats.incrementQuantity(1);
        cart.put(superIPad, stats);
        rule.applyOn(cart);
        // customer has to pay full price per superIPad
        assertEquals(superIPad.getPrice(), cart.get(superIPad).getAmount(), 0.0);

        // cart now has 2 superIPad
        stats.incrementQuantity(1);
        cart.put(superIPad, stats);
        rule.applyOn(cart);
        // customer has to pay full price per superIPad
        assertEquals(2 * superIPad.getPrice(), cart.get(superIPad).getAmount(), 0.0);

        // cart now has 6 superIPad
        stats.incrementQuantity(4);
        cart.put(superIPad, stats);
        rule.applyOn(cart);
        // customer has to pay discounted price per superIPad
        assertEquals((6 * 499.99), cart.get(superIPad).getAmount(), 0.0);

    }

    @Test
    public void testFreeBeePricingRule() {

        IPricingRule rule = new FreeBeePricingRule(SKU.mbp, SKU.vga);

        Map<Item, ItemStatistics> cart = new HashMap<>();

        Item macBookPro = catalogue.getItem(SKU.mbp);
        ItemStatistics macBookProStats = new ItemStatistics();

        Item vgaAdaptor = catalogue.getItem(SKU.vga);
        ItemStatistics vgaAdaptorStats = new ItemStatistics();

        cart.put(macBookPro, macBookProStats);
        cart.put(vgaAdaptor, vgaAdaptorStats);

        rule.applyOn(cart);
        assertEquals(0.0, cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(0.0, cart.get(vgaAdaptor).getAmount(), 0.0);

        // cart now has 1 macBookPro and 0 vgaAdaptor
        macBookProStats.incrementQuantity(1);
        rule.applyOn(cart);
        assertEquals(macBookPro.getPrice(), cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(0.0, cart.get(vgaAdaptor).getAmount(), 0.0);

        // cart now has 0 macBookPro and 1 vgaAdaptor
        macBookProStats.incrementQuantity(-1);
        vgaAdaptorStats.incrementQuantity(1);
        rule.applyOn(cart);
        assertEquals(0.0, cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(vgaAdaptor.getPrice(), cart.get(vgaAdaptor).getAmount(), 0.0);

        // cart now has 1 macBookPro and 1 vgaAdaptor
        macBookProStats.incrementQuantity(1);
        rule.applyOn(cart);
        assertEquals(macBookPro.getPrice(), cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(0.0, cart.get(vgaAdaptor).getAmount(), 0.0);

        // cart now has 3 macBookPro and 1 vgaAdaptor
        macBookProStats.incrementQuantity(2);
        rule.applyOn(cart);
        assertEquals(3 * macBookPro.getPrice(), cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(0.0, cart.get(vgaAdaptor).getAmount(), 0.0);

        // cart now has 3 macBookPro and 5 vgaAdaptor
        vgaAdaptorStats.incrementQuantity(4);
        rule.applyOn(cart);
        assertEquals(3 * macBookPro.getPrice(), cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(2 * vgaAdaptor.getPrice(), cart.get(vgaAdaptor).getAmount(), 0.0);

    }

}
