package xyz.luan.apps.stocker.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Stock implements Serializable {

	private static final long serialVersionUID = -7744728250101428108L;

	private static final String STOCKS_FILE = ".stocker";

	private String name;
	private double buyPrice;

	public Stock(String name, double buyPrice) {
		this.name = name;
		this.buyPrice = buyPrice;
	}

	public static Stock fromLine(String line) {
		String[] parts = line.split(":");
		return new Stock(parts[0], Double.parseDouble(parts[1]));
	}

	public String getName() {
		return name;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public static Set<Stock> readOrCreate() {
		File file = new File(STOCKS_FILE);
		HashSet<Stock> stocks = new HashSet<>();
		if (file.exists()) {
			try (BufferedReader r = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = r.readLine()) != null) {
					stocks.add(Stock.fromLine(line));
				}
			} catch (IOException e) {
				throw new RuntimeException("ooppps..", e); // TODO pretty this
			}
		}
		return stocks;
	}
}
