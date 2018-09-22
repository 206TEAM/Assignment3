package Control;

import javafx.fxml.Initializable;

/**
 * Defines Controllers that control JavaFX scenes that are
 * only placed in Panes of other scenes. {@link #itemSelected()}
 * allows those Controllers to be able to detect when an item
 * in the parent scene has been selected and act accordingly.
 *
 */
public interface SubSceneController extends Initializable {

	/**
	 * Notify the sub-scenes that an item in
	 * {@link MainController#mainListView} has been selected.
	 */
	void itemSelected();

	/**
	 * Notify the sub-scenes that an item in
	 * {@link MainController#originalListView} has been selected.
	 */
	void originalSelected();
}
