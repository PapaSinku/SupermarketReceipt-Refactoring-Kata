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
        if (numberOfApplies > 0 || offerType == SpecialOfferType.TenPercentDiscount) {
            switch (offerType) {
                case TenPercentDiscount:
                    discount = getTenPercentDiscount(product, quantity, unitPrice);
                    break;
                case ThreeForTwo:
                    discount = getThreeForTwoDiscount(product, quantity, unitPrice, quantityAsInt, numberOfApplies);
                    break;
                case TwoForAmount:
                    discount = getTwoForAmountDiscount(product, quantity, unitPrice, quantityAsInt, numberOfApplies);
                    break;
                case FiveForAmount:
                    discount = getFiveForAmountDiscount(product, quantity, unitPrice,
                                quantityAsInt, numberOfBoughtItemsRequired, numberOfApplies);
                    break;
            }
        }

        return discount;
    }

    private Discount getFiveForAmountDiscount(Product product, double quantity, double unitPrice,
                                              int quantityAsInt, int numberOfBoughtItemsRequired, int numberOfApplies) {
        double discountTotal = unitPrice * quantity - (argument * numberOfApplies + quantityAsInt % 5 * unitPrice);
        return new Discount(product, numberOfBoughtItemsRequired + " for " + argument, -discountTotal);
    }

    private Discount getTwoForAmountDiscount(Product product, double quantity, double unitPrice,
                                                        int quantityAsInt, int numberOfApplies) {
        double pricePerUnit = argument * numberOfApplies;
        double theTotal = (quantityAsInt % 2) * unitPrice;
        double total = pricePerUnit + theTotal;
        double discountN = unitPrice * quantity - total;
        return new Discount(product, "2 for " + argument, -discountN);
    }

    private Discount getThreeForTwoDiscount(Product product, double quantity,
                                            double unitPrice, int quantityAsInt, int numberOfApplies) {
        double discountAmount = quantity * unitPrice - ((numberOfApplies * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
        return new Discount(product, "3 for 2", -discountAmount);
    }

    private Discount getTenPercentDiscount(Product product, double quantity, double unitPrice) {
        return new Discount(product, argument + "% off", -quantity * unitPrice * argument / 100.0);
    }
}
