package au.com.dius.shopping.util;

import au.com.dius.shopping.constant.SKU;
import au.com.dius.shopping.context.ApplicationContext;
import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.domain.ItemStatistics;

import java.util.Map;

/**
 * A class to hold common utility methods for the application.
 */
public class CommonUtils {

    /**
     * Finds item in the given cart. If not found in cart, looks up in the catalogue
     *
     * @param sku  search item
     * @param cart shopping cart
     * @return
     */
    public static Item findItemInCart(final SKU sku, final Map<Item, ItemStatistics> cart) {
        return sku == null ? null : cart.keySet().stream().filter(item -> item.getSKU() == sku)
                .findFirst()
                .orElse(ApplicationContext.getCatalogue().getItem(sku));
    }
}
