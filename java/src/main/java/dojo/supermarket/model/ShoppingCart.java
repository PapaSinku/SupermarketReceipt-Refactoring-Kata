package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();


    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    public Map<Product, Double> getProductQuantities() {
        return productQuantities;
    }


    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product product: getProductQuantities().keySet()) {
            double quantity = productQuantities.get(product);
            if (!offers.containsKey(product)) {
                continue;
            }
            Offer offer = offers.get(product);
            double unitPrice = catalog.getUnitPrice(product);
            Discount discount = offer.getDiscount(product, quantity, unitPrice);
            if (discount != null)
                receipt.addDiscount(discount);

        }
    }

}
