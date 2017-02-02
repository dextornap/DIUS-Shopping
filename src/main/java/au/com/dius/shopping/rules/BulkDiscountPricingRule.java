package au.com.dius.shopping.rules;

import au.com.dius.shopping.constant.SKU;
import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.domain.ItemStatistics;
import au.com.dius.shopping.util.CommonUtils;

import java.util.Map;

/**
 * A class representing bulk discount pricing rule implementation.
 */
public class BulkDiscountPricingRule implements IPricingRule {

    private SKU offerItemSKU;
    private int minQuantity;
    private Double discountedPricePerItem;

    /**
     * @param offerItemSKU           item on which to apply this rule
     * @param minQuantity            minimum quantity for rule to be applicable
     * @param discountedPricePerItem new price per item if rule applicable
     */
    public BulkDiscountPricingRule(final SKU offerItemSKU, final int minQuantity, final Double discountedPricePerItem) {
        this.offerItemSKU = offerItemSKU;
        this.minQuantity = minQuantity;
        this.discountedPricePerItem = discountedPricePerItem;
    }

    /**
     * If quantity of offer item in the cart is greater than minimum quantity configured,
     * the amount payable for that item is updated with discounted price per item.
     *
     * @param cart shopping cart
     */
    @Override
    public void applyOn(final Map<Item, ItemStatistics> cart) {

        Item offerItem = CommonUtils.findItemInCart(offerItemSKU, cart);
        ItemStatistics stats = cart.get(offerItem);

        if (stats != null) {
            int quantity = stats.getQuantity();
            double price = quantity > minQuantity ? discountedPricePerItem : offerItem.getPrice();
            stats.setAmount(quantity * price);
        }

    }
}
