package dojo.supermarket.model;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    public int getNumberOfBoughtItemsRequired() {
        int numberOfBoughtItemsRequired = 1;
        if (this.offerType == SpecialOfferType.ThreeForTwo) {
            numberOfBoughtItemsRequired = 3;

        } else if (this.offerType == SpecialOfferType.TwoForAmount) {
            numberOfBoughtItemsRequired = 2;

        }
        if (this.offerType == SpecialOfferType.FiveForAmount) {
            numberOfBoughtItemsRequired = 5;
        }
        return numberOfBoughtItemsRequired;
    }

    Discount getDiscount(Product product, double quantity, double unitPrice) {
        int quantityAsInt = (int) quantity;
        int numberOfBoughtItemsRequired = this.getNumberOfBoughtItemsRequired();
        int numberOfApplies = quantityAsInt / numberOfBoughtItemsRequired;
        Discount discount = null;
        if (offerType == SpecialOfferType.TenPercentDiscount) {
            discount = new Discount(product, argument + "% off", -quantity * unitPrice * argument / 100.0);
        }
        if (numberOfApplies > 0) {
            if (offerType == SpecialOfferType.ThreeForTwo) {
                double discountAmount = quantity * unitPrice - ((numberOfApplies * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
                discount = new Discount(product, "3 for 2", -discountAmount);
            }
            if (offerType == SpecialOfferType.TwoForAmount) {
                double pricePerUnit = argument * numberOfApplies;
                double theTotal = (quantityAsInt % 2) * unitPrice;
                double total = pricePerUnit + theTotal;
                double discountN = unitPrice * quantity - total;
                discount = new Discount(product, "2 for " + argument, -discountN);
            }
            if (offerType == SpecialOfferType.FiveForAmount) {
                double discountTotal = unitPrice * quantity - (argument * numberOfApplies + quantityAsInt % 5 * unitPrice);
                discount = new Discount(product, numberOfBoughtItemsRequired + " for " + argument, -discountTotal);
            }
        }

        return discount;
    }
}
