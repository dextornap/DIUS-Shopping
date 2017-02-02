package au.com.dius.shopping.rules;

import au.com.dius.shopping.constant.SKU;
import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.domain.ItemStatistics;
import au.com.dius.shopping.util.CommonUtils;

import java.util.Map;

/**
 * A class representing three for price of two items pricing rule implementation.
 */
public class ThreeForTwoPricingRule implements IPricingRule {

    private SKU offerItemSKU;

    /**
     * @param offerItemSKU item on which to apply this rule
     */
    public ThreeForTwoPricingRule(final SKU offerItemSKU) {
        this.offerItemSKU = offerItemSKU;
    }

    /**
     * If the quantity of offer item in the cart is a multiple of three,
     * then, for each such triplet amount payable is updated with amount for two items
     * For less than 3 items amount payable is sum of marked price per item
     *
     * @param cart shopping cart
     */
    @Override
    public void applyOn(final Map<Item, ItemStatistics> cart) {

        Item offerItem = CommonUtils.findItemInCart(offerItemSKU, cart);
        ItemStatistics stats = cart.get(offerItem);

        if (stats != null) {
            int quantity = stats.getQuantity();
            double price = offerItem.getPrice();
            double amountToPay = (quantity - quantity / 3) * price;
            stats.setAmount(amountToPay);
        }

    }
}
