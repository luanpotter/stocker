package xyz.luan.apps.stocker.controller;

import java.util.List;

import xyz.luan.apps.stocker.main.StockContext;
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
		context.s().forEach(stock -> console.result("- " + stock.getName() + " : " + stock.getBuyPrice()));
		return CallResult.SUCCESS;
	}

	public static void defaultCallables(String name, List<Callable> callables) {
		callables.add(new ActionCall(name + ":list", ":stocks", "List all stocks"));
	}

}
