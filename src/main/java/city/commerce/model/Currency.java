package city.commerce.model;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-06
 */
public enum Currency {
    EUR("EUR", "€", 100),
    USD("USD", "$", 100)
    ;

    private String code;
    private String symbol;
    private int factor;

    Currency(String code, String symbol, int factor) {
        this.code = code;
        this.symbol = symbol;
        this.factor = factor;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getFactor() {
        return factor;
    }
}
