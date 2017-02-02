DIUS Shopping : Checkout System
===============================

## Requirements 

DiUS is starting a computer store. This application builds the checkout system. We start with the following products in our catalogue


| SKU     | Name        | Price    |
| --------|:-----------:| --------:|
| ipd     | Super iPad  | $549.99  |
| mbp     | MacBook Pro | $1399.99 |
| atv     | Apple TV    | $109.50  |
| vga     | VGA adapter | $30.00   |

As we're launching our new computer store, we would like to have a few opening day specials.

- we're going to have a 3 for 2 deal on Apple TVs. For example, if you buy 3 Apple TVs, you will pay the price of 2 only
- the brand new Super iPad will have a bulk discounted applied, where the price will drop to $499.99 each, if someone buys more than 4
- we will bundle in a free VGA adapter free of charge with every MacBook Pro sold

As our Sales manager is quite indecisive, we want the pricing rules to be as flexible as possible as they can change in the future with little notice.

Our checkout system can scan items in any order.

Example scenarios
-----------------

SKUs Scanned: atv, atv, atv, vga
Total expected: $249.00

SKUs Scanned: atv, ipd, ipd, atv, ipd, ipd, ipd
Total expected: $2718.95

SKUs Scanned: mbp, vga, ipd
Total expected: $1949.98


Implementation Environment
--------------------------

    
 - Language used : Java 8
 
 - Unit testing : JUnit 4
 
 - IDE used : IntelliJ IDEA

 - Test Coverage Report : genearated using IntelliJ - `Run Tests with coverage` tool
 
 - Test Coverage Report Location : `/test/resources/coverage/index.html`


How to use
----------

 - Run application using Junit Tests provided:
    - `CheckoutUnitTest` : Runs the example scenarios listed above. Tests some corner cases as well.
    - `PricingRulesUnitTest` : Tests algorithms for 3 Pricing Rules (Strategy)
    
 - No GUI
 
 - No Command line interface
 
 - No Frameworks used, except JUnit for testing



Solution Design
---------------

This is an object oriented solution of the problem statement.

- `Checkout` : Entry point to the application. Represents a POS (point of sales) checkout counter.

- `ApplicationContext` : A single point of contact for all application level configurations. 
    - `Singleton` design pattern used.  
    - It builds a catalog and pricing rules for items/products sold in the computer store.
    
- `SKU` : Enumeration of SKUs maintained by the store for various products/items.

- Domain Model:
    - `Catalogue` : Catalog of products/items in the store.
    - `Item` : POJO representing an item sold at the store.
    - `ItemStatistics` : Statistical data about an item/product. It is a part of the line item for product/item scanned at the POS (point of sales) checkout. A shopping cart is represented as a Map of (Item -> ItemStatistics).
    
- Pricing Rules:
    - `Strategy` design pattern used. This keeps the pricing rules to be flexible. New rules can be added easily with a new concrete implementation of interface - `IPricingRule`
    - `IPricingRule` : Interface representing a pricing rule algorithm that can be applied on to a shopping cart of items.
    - Algorithms:
        - `ThreeForTwoPricingRule` : 3 for 2 deal strategy implementation.
        - `BulkDiscountPricingRule` : discounted price per item for items purchased in bulk.
        - `FreeBeePricingRule` : one item free with other item.