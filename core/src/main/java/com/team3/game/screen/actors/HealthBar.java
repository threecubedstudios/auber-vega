package com.team3.game.screen.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.team3.game.characters.Player;

/**
 * Displays the player's healthbar.
 */
public class HealthBar extends ProgressBar {
  // Label before the bar.
  public Label hpText;

  /**
   * Healthbar to show the auber's health. Adjustments help meet requirement UR_UX.
   */
  public HealthBar() {
    super(0, 100, .5f, false, new Skin(Gdx.files.internal("skin/hudskin/comic-ui.json")));
    setAnimateDuration(1.5f);
    hpText = new Label("HP", new Skin(Gdx.files.internal("skin/hudskin/comic-ui.json")), "title");
    hpText.getStyle().font.getData().setScale(.5f, .5f);
  }

  /**
   * Update the health of the player.

   * @param auber Player instance
   */
  public void updateHp(Player auber) {
    setValue(auber.health);
  }
}
