package au.com.dius.shopping.domain;

/**
 * A class to hold statistical data about an item/product.
 * It is a part of the line item for product/item scanned at the POS (point of sales) checkout.
 */
public class ItemStatistics {

    private int quantity;
    private double amount;

    /**
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void incrementQuantity(final int quantity) {
        this.quantity += quantity;
    }

    /**
     * @return amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(final double amount) {
        this.amount = amount;
    }
}
