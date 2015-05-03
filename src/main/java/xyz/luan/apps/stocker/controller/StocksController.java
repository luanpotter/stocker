package xyz.luan.apps.stocker.controller;

import java.util.List;

import xyz.luan.apps.stocker.main.StockContext;
import xyz.luan.apps.stocker.model.Stock;
import xyz.luan.apps.stocker.util.BovespaRequest;
import xyz.luan.apps.stocker.util.BovespaRequest.InvalidPaperException;
import xyz.luan.apps.stocker.util.MapBuilder;
import xyz.luan.apps.stocker.util.SotcksFileReader;
import xyz.luan.console.fn.FnController;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.call.CallResult;
import xyz.luan.console.parser.callable.ActionCall;
import xyz.luan.console.parser.callable.Callable;

@FnController
public class StocksController extends Controller<StockContext> {

    @Action("clear")
    public CallResult clear() {
        context.s().clear().save();
        console.result("All Stocks cleared.");
        return CallResult.SUCCESS;
    }

    @Action("parseFile")
    public CallResult parseFile(String file) {
        SotcksFileReader.read(context.s(), file);
        context.s().save();
        console.result("File parsed.");
        return CallResult.SUCCESS;
    }

    @Action("create")
    public CallResult create(String name) {
        context.s().add(new Stock(name)).save();
        console.result("New Stock was added succesfully.");
        return CallResult.SUCCESS;
    }

    @Action("buy")
    public CallResult buy(String stock, Integer amount, Double value) {
        context.s().get(stock).buy(amount, value);
        context.s().save();
        console.result("New Stock was added succesfully.");
        return CallResult.SUCCESS;
    }

    @Action("list")
    public CallResult list() {
        console.result("-- Stocks");
        context.s().forEach(stock -> console.result("- " + stock));
        return CallResult.SUCCESS;
    }

    @Action("fetch")
    public CallResult fetch(String stock) throws InvalidPaperException {
        final BovespaRequest bovespa = new BovespaRequest();
        console.result(String.format("Stock value for %s is: %s", stock, bovespa.getCurrentValue(stock)));
        return CallResult.SUCCESS;
    }

    @Action("update")
    public CallResult update() {
        final BovespaRequest bovespa = new BovespaRequest();
        context.s().forEach(stock -> {
            try {
                double currentValue = bovespa.getCurrentValue(stock.getName());
                stock.updatePrice(currentValue);
            } catch (InvalidPaperException e) {
                console.error("Invalid paper name: " + stock.getName());
            }
        }).save();
        console.result("All Stocks were updated successfully.");
        return CallResult.SUCCESS;
    }

    public static void defaultCallables(String name, List<Callable> callables) {
        callables.add(new ActionCall(name + ":clear", ":clear", "Clear all stocks."));
        callables.add(new ActionCall(name + ":parseFile", ":parse", MapBuilder.from("file", ".stocks"), "Parse the stock file '.stocks'."));
        callables.add(new ActionCall(name + ":parseFile", ":parse file", "Parse the stock file."));
        callables.add(new ActionCall(name + ":create", ":create name", "Create a new stock with name."));
        callables.add(new ActionCall(name + ":buy", ":buy stock amount value", "Buy amount of stock via value."));
        callables.add(new ActionCall(name + ":list", ":stocks", "List all stocks."));
        callables.add(new ActionCall(name + ":fetch", ":fetch stock", "Fetch current value for a specific stock."));
        callables.add(new ActionCall(name + ":update", ":update", "Update current value of all stocks."));
    }

}
