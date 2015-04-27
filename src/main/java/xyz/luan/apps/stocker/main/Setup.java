package xyz.luan.apps.stocker.main;

import xyz.luan.console.fn.FnSetup;
import xyz.luan.console.parser.Application;

public class Setup extends FnSetup<StockContext> {

	public Setup() {
		super("xyz.luan.apps.stocker.controller");
	}

	public static void main(String[] args) {
		create().run(args);
	}

	public static Application create() {
		return new Setup().setupApplication(new StockContext());
	}
}
