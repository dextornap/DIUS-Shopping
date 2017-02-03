package au.com.dius.shopping.domain;

import au.com.dius.shopping.constant.SKU;

import java.util.Collections;
import java.util.Set;

/**
 * A class representing a Catalog of products/items in the store.
 */
public class Catalogue {

    private Set<Item> items;

    /**
     * @param items
     */
    public Catalogue(final Set<Item> items) {
        this.items = items;
    }

    /**
     * @param sku
     * @return
     */
    public Item getItem(final SKU sku) {
        return items.stream().filter(item -> item.getSKU() == sku).findFirst().get();
    }

    /**
     * @return
     */
    public Set<Item> getAllItems() {
        return Collections.unmodifiableSet(items);
    }
}
