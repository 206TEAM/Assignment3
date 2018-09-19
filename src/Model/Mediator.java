package Model;

/**
 * Acts as a mediator for which various controllers
 * can communicate with each other.
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
