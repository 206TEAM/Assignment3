package Model;

import Control.MainController;

/**
 * Acts as a mediator for which various controllers
 * can communicate with each other.
 *
 * @author Eric Pedrido
 */
public class Mediator {

	private String _page;
	private static final Mediator MEDIATOR_SINGLETON = new Mediator();
	private MainController _main;

	public void setPage(String page) {
		_page = page;
	}

	public void setMain(MainController main) {
		_main = main;
	}

	public String getPage() {
		return _page;
	}

	public void loadPane() {
		_main.loadPane();
	}

	public static Mediator getInstance() {
		return MEDIATOR_SINGLETON;
	}
}
