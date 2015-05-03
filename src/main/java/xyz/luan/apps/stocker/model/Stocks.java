package xyz.luan.apps.stocker.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import lombok.Getter;
import xyz.luan.apps.stocker.util.SimpleObjectAccess;

public class Stocks implements Serializable {

    private static final long serialVersionUID = 6186571566636936718L;

    private static final String FILE_NAME = "stocks.dat";

    @Getter
    private Map<String, Stock> stocks;

    private Stocks() {
        this.stocks = new HashMap<>();
    }

    public static Stocks readOrCreate() {
        Stocks fromFile = SimpleObjectAccess.readFrom(FILE_NAME);
        if (fromFile == null) {
            return new Stocks().save();
        }
        return fromFile;
    }

    public Stocks save() {
        return SimpleObjectAccess.saveTo(FILE_NAME, this);
    }

    public Stocks forEach(Consumer<Stock> consumer) {
        this.stocks.values().forEach(consumer);
        return this;
    }

    public Stocks add(Stock stock) {
        this.stocks.put(stock.getName(), stock);
        return this;
    }

    public Stock get(String stock) {
        return stocks.get(stock);
        // TODO pretty exception to be handled
    }

    public Stocks clear() {
        stocks.clear();
        return this;
    }
}
