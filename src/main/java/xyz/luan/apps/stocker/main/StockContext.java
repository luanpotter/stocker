package xyz.luan.apps.stocker.main;

import xyz.luan.apps.stocker.model.Stocks;
import xyz.luan.console.parser.Context;

public class StockContext extends Context {

    private static final long serialVersionUID = 4751119237571453875L;

    private Stocks stocks;

    public StockContext() {
        this.stocks = Stocks.readOrCreate();
    }

    public Stocks s() {
        return stocks;
    }
}
