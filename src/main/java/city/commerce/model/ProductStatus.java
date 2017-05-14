package city.commerce.model;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
public enum ProductStatus {
    OnSale(0),
    SoldOut(1)
    ;

    private int status;

    ProductStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static ProductStatus of(int status) {
        for (ProductStatus productStatus : values()) {
            if (productStatus.getStatus() == status) {
                return productStatus;
            }
        }

        return null;
    }
}
