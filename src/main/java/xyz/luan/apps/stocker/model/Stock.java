package xyz.luan.apps.stocker.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import xyz.luan.apps.stocker.util.BovespaRequest;

public class Stock implements Serializable {

	private static final long serialVersionUID = -7744728250101428108L;

	private static final String STOCKS_FILE = ".stocker";

	private String name;
	private double buyPrice;
	private int amount;
	private Double currentPrice;

	public Stock(String name, int amount, double buyPrice) {
		this.name = name;
		this.amount = amount;
		this.buyPrice = buyPrice;
	}

	public static Stock fromLine(String line, BovespaRequest bovespa) {
		String[] parts = line.split(":");
		Stock stock = new Stock(parts[0], Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));
		stock.updatePrice(bovespa);
		return stock;
	}

	public String getName() {
		return name;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public int getAmount() {
		return amount;
	}

	public static Set<Stock> readOrCreate() {
		final BovespaRequest bovespa = new BovespaRequest();
		File file = new File(STOCKS_FILE);
		HashSet<Stock> stocks = new HashSet<>();
		if (file.exists()) {
			try (BufferedReader r = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = r.readLine()) != null) {
					stocks.add(Stock.fromLine(line, bovespa));
				}
			} catch (IOException e) {
				throw new RuntimeException("ooppps..", e); // TODO pretty this
			}
		}
		return stocks;
	}

	public void updatePrice(BovespaRequest bovespa) {
		this.currentPrice = bovespa.getCurrentValue(this.name);
	}

	public double relativeProfit() {
		return profit() / buyPrice;
	}

	public double profit() {
		return currentPrice - buyPrice;
	}

	@Override
	public String toString() {
		return String.format("%s (%d) [%.2f -> %.2f] [%+.2f (%+.4f)]", getName(), getAmount(), getBuyPrice(), getCurrentPrice(), profit(), relativeProfit());
	}
}
