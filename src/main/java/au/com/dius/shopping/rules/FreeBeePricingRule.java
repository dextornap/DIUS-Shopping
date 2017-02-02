package au.com.dius.shopping.rules;


import au.com.dius.shopping.constant.SKU;
import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.domain.ItemStatistics;
import au.com.dius.shopping.util.CommonUtils;

import java.util.Map;

/**
 * A class representing "free item with a paid item/product" pricing rule
 */
public class FreeBeePricingRule implements IPricingRule {

    private SKU offerItemSKU;
    private SKU freeBeeItemSKU;

    /**
     * @param offerItemSKU   item on which to apply this rule
     * @param freeBeeItemSKU item that must go free with offer item
     */
    public FreeBeePricingRule(final SKU offerItemSKU, final SKU freeBeeItemSKU) {
        this.offerItemSKU = offerItemSKU;
        this.freeBeeItemSKU = freeBeeItemSKU;
    }

    /**
     * All Offer items in the cart are billed at the marked price.
     * If freeBee items in the cart are less than or equal to number of offer items in the cart,
     * then, freeBee items amount payable is set to zero.
     * If number of freeBee items are more than that of offer items in the cart,
     * then, amount payable for the freeBee item is updated with (difference quantity * price per freeBee item)
     *
     * @param cart shopping cart
     */
    @Override
    public void applyOn(final Map<Item, ItemStatistics> cart) {

        Item offerItem = CommonUtils.findItemInCart(offerItemSKU, cart);
        Item freeBeeItem = CommonUtils.findItemInCart(freeBeeItemSKU, cart);

        ItemStatistics offerItemStats = cart.get(offerItem);
        ItemStatistics freeBeeItemStats = cart.get(freeBeeItem);

        if (offerItemStats != null) {
            offerItemStats.setAmount(offerItemStats.getQuantity() * (offerItem == null ? 0 : offerItem.getPrice()));
        }

        if (freeBeeItemStats != null) {
            int offerItemQuantity = offerItemStats == null ? 0 : offerItemStats.getQuantity();
            int diffQuantity = offerItemQuantity - freeBeeItemStats.getQuantity();
            int multiplier = diffQuantity > 0 ? 0 : (-1 * diffQuantity);
            freeBeeItemStats.setAmount(multiplier * (freeBeeItem == null ? 0 : freeBeeItem.getPrice()));
        }

    }
}
