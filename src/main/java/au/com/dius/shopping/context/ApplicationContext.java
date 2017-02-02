package au.com.dius.shopping.context;

import au.com.dius.shopping.constant.SKU;
import au.com.dius.shopping.domain.Catalogue;
import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.rules.BulkDiscountPricingRule;
import au.com.dius.shopping.rules.FreeBeePricingRule;
import au.com.dius.shopping.rules.IPricingRule;
import au.com.dius.shopping.rules.ThreeForTwoPricingRule;

import java.util.*;

/**
 * A class representing root context of the Shopping application.
 * It does the central initialization of catalog and pricing rules for items sold in the computer store.
 */
public class ApplicationContext {

    private ApplicationContext() {
    }

    private enum Singleton {

        INSTANCE;

        private Catalogue catalogue;
        private Map<SKU, IPricingRule> pricingRules = new HashMap<>();

        Singleton() {

            // build catalogue
            Set<Item> items = new HashSet<>();
            items.add(new Item(SKU.ipd, "Super iPad", 549.99));
            items.add(new Item(SKU.mbp, "MacBook Pro", 1399.99));
            items.add(new Item(SKU.atv, "Apple TV", 109.50));
            items.add(new Item(SKU.vga, "VGA adapter", 30.00));
            catalogue = new Catalogue(items);

            // build pricing rules
            pricingRules.put(SKU.atv, new ThreeForTwoPricingRule(SKU.atv));
            pricingRules.put(SKU.ipd, new BulkDiscountPricingRule(SKU.ipd, 4, 499.99));
            pricingRules.put(SKU.mbp, new FreeBeePricingRule(SKU.mbp, SKU.vga));
            pricingRules.put(SKU.vga, new FreeBeePricingRule(SKU.mbp, SKU.vga));

        }

        private Catalogue getCatalogue() {
            return catalogue;
        }

        private Map<SKU, IPricingRule> getPricingRules() {
            return Collections.unmodifiableMap(pricingRules);
        }

    }

    /**
     * @return catalogue
     */
    public static Catalogue getCatalogue() {
        return Singleton.INSTANCE.getCatalogue();
    }

    /**
     * @return map of pricing rules
     */
    public static Map<SKU, IPricingRule> getPricingRules() {
        return Singleton.INSTANCE.getPricingRules();
    }

}
