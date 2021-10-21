package game.sniper_monkey.view.characterSelection;

import game.sniper_monkey.model.player.fighter.Fighter;

public interface ICharacterSelectedObserver {

    void onChosenCharacter(Fighter fighter);
}
