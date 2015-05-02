package xyz.luan.apps.stocker.controller;

import java.util.List;

import xyz.luan.apps.stocker.main.StockContext;
import xyz.luan.apps.stocker.util.BovespaRequest;
import xyz.luan.console.fn.FnController;
import xyz.luan.console.parser.Controller;
import xyz.luan.console.parser.actions.Action;
import xyz.luan.console.parser.call.CallResult;
import xyz.luan.console.parser.callable.ActionCall;
import xyz.luan.console.parser.callable.Callable;

@FnController
public class StocksController extends Controller<StockContext> {

	@Action("list")
	public CallResult list() {
		console.result("-- Stocks");
		context.s().forEach(stock -> console.result("- " + stock));
		return CallResult.SUCCESS;
	}

	@Action("fetch")
	public CallResult fetch(String stock) {
		final BovespaRequest bovespa = new BovespaRequest();
		console.result(String.format("Stock value for %s is: %s", stock, bovespa.getCurrentValue(stock)));
		return CallResult.SUCCESS;
	}

	@Action("update")
	public CallResult update() {
		final BovespaRequest bovespa = new BovespaRequest();
		context.s().forEach(stock -> stock.updatePrice(bovespa));
		console.result("Updated successfully.");
		return CallResult.SUCCESS;
	}

	public static void defaultCallables(String name, List<Callable> callables) {
		callables.add(new ActionCall(name + ":list", ":stocks", "List all stocks."));
		callables.add(new ActionCall(name + ":fetch", ":fetch stock", "Fetch current value for a specific stock."));
		callables.add(new ActionCall(name + ":update", ":update", "Update current value of all stocks."));
	}

}
