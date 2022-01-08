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
}
