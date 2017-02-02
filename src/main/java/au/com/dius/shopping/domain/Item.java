package au.com.dius.shopping.domain;

import au.com.dius.shopping.constant.SKU;

/**
 * A class representing a product/item sold at the store
 */
public class Item {

    private SKU SKU;
    private String name;
    private double price;

    /**
     * @param SKU
     * @param name
     * @param price
     */
    public Item(final SKU SKU, final String name, final double price) {
        this.SKU = SKU;
        this.name = name;
        this.price = price;
    }

    /**
     * @return
     */
    public SKU getSKU() {
        return SKU;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return SKU == item.SKU;

    }

    @Override
    public int hashCode() {
        return SKU.hashCode();
    }

    @Override
    public String toString() {
        return "{" +
                "\"SKU\":\"" + SKU +
                "\", \"name\":\"" + name + '\'' +
                "\", \"price\":\"" + price +
                "\"}";
    }
}
