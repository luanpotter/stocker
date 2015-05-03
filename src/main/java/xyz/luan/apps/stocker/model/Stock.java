package xyz.luan.apps.stocker.model;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;

public class Stock implements Serializable {

    private static final long serialVersionUID = -7744728250101428108L;

    @Getter
    private String name;

    private Map<LocalDateTime, Double> prices;
    private List<Acquisition> acquisitions;

    public Stock(String name) {
        this.name = name;
        this.acquisitions = new ArrayList<>();
        this.prices = new HashMap<>();
    }

    public void updatePrice(double price) {
        prices.put(LocalDateTime.now(), price);
    }

    public Double speed() {
        if (prices.size() < 2) {
            return null;
        }
        ArrayList<LocalDateTime> dateList = sortedDates();
        LocalDateTime from = dateList.get(1);
        LocalDateTime to = dateList.get(0);
        return (prices.get(to) - prices.get(from)) / getDuration(from, to);
    }

    private ArrayList<LocalDateTime> sortedDates() {
        ArrayList<LocalDateTime> priceList = new ArrayList<>(prices.keySet());
        Collections.sort(priceList, Collections.reverseOrder());
        return priceList;
    }

    public long getDuration(LocalDateTime start, LocalDateTime end) {
        Instant startInstant = start.toInstant(ZoneOffset.UTC);
        Instant endInstant = end.toInstant(ZoneOffset.UTC);
        return Duration.between(startInstant, endInstant).toNanos();
    }

    public void buy(int amount, double value) {
        this.acquisitions.add(new Acquisition(amount, value));
    }

    public int totalAmount() {
        return acquisitions.stream().collect(Collectors.summingInt(s -> s.getAmount()));
    }

    public double averageBuyPrice() {
        return acquisitions.stream().collect(Collectors.averagingDouble(s -> s.getValue()));
    }

    private double currentPrice() {
        ArrayList<LocalDateTime> dateList = sortedDates();
        return prices.get(dateList.get(0));
    }

    public String toString() {
        double buyPrice = averageBuyPrice();
        double currentPrice = currentPrice();
        double profit = currentPrice - buyPrice;
        return String.format("%s [%.2f -> %.2f] [%+.2f (%+.2f)]", name, buyPrice, currentPrice, profit, profit / (totalAmount() * buyPrice));
    }
}
