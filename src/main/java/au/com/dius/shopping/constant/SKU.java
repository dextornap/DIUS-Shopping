package au.com.dius.shopping.constant;

/**
 * Enumeration of SKUs maintained by the store for various products/items.
 */
public enum SKU {

    ipd("Super iPad"), mbp("MacBook Pro"), atv("Apple TV"), vga("VGA adapter"), iphone("Apple IPhone");

    private final String description;

    /**
     * @param description
     */
    SKU(final String description) {
        this.description = description;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }
}
