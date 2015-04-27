package xyz.luan.apps.stocker.main;

import java.util.Set;

import xyz.luan.apps.stocker.model.Stock;
import xyz.luan.console.parser.Context;

public class StockContext extends Context {

	private static final long serialVersionUID = 4751119237571453875L;

	private Set<Stock> stocks;

	public StockContext() {
		this.stocks = Stock.readOrCreate();
	}

	public Set<Stock> s() {
		return stocks;
	}
}
