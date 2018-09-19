package Model;

import Control.MainController;

/**
 * Mediates which scene to switch to in {@link MainController#loadPane()}.
 *
 * @author Eric Pedrido
 */
public class Mediator {

	private String _page;
	private static final Mediator MEDIATOR_SINGLETON = new Mediator();

	public void setPage(String page) {
		_page = page;
	}

	public String getPage() {
		return _page;
	}

	public static Mediator getInstance() {
		return MEDIATOR_SINGLETON;
	}
}
