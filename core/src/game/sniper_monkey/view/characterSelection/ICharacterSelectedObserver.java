package game.sniper_monkey.view.characterSelection;

/**
 * An observer interface that is used to notify when a character has been selected.
 * <p>
 * Used by CharacterSelectionScreenController.
 * Used by SelectViewRectangle.
 *
 * @author Kevin Jeryd
 */
public interface ICharacterSelectedObserver {

    /**
     * The method that is called when player1 has selected a character.
     * Notifies subscribers of the event so they can adapt to it.
     */
    void onPlayer1CharacterSelected();

    /**
     * The method that is called when player2 has selected a character.
     * Notifies subscribers of the event so they can adapt to it.
     */
    void onPlayer2CharacterSelected();

}
