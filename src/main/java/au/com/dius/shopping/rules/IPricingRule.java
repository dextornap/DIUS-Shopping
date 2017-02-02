package au.com.dius.shopping.rules;

import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.domain.ItemStatistics;

import java.util.Map;

/**
 * Interface representing a pricing rule that can be applied on to a shopping cart of items.
 */
public interface IPricingRule {

    /**
     * Applies pricing rule on the shopping cart
     *
     * @param cart
     */
    void applyOn(final Map<Item, ItemStatistics> cart);

}
