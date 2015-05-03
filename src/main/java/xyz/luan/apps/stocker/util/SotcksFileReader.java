package xyz.luan.apps.stocker.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import xyz.luan.apps.stocker.model.Stock;
import xyz.luan.apps.stocker.model.Stocks;

@UtilityClass
public class SotcksFileReader {

    @SneakyThrows
    public static void read(Stocks s, String file) {
        try (BufferedReader r = new BufferedReader(new FileReader(new File(file)))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(":");
                Stock stock = new Stock(parts[0]);
                stock.buy(Integer.parseInt(parts[1]), Double.parseDouble(parts[2]));
                s.add(stock);
            }
        }
    }

}
