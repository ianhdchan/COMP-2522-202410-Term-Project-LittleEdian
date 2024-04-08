/**
 * Bandit Class for the game.
 *
 * @author Edro Gonzales A01257468
 * @author Ian Chan A00910012
 * @version 2024
 */

package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;


public class Bandit extends Enemy {
    /** * Speed of the bandit. */
    public float speed = 150;
    /** * Speed of the bandit. */
    public int xMovement = 0;
    protected Texture ufoImage;

    public Bandit(final GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    void spawnEnemy() {
        Rectangle eachBandit = new Rectangle();
        eachBandit.x = xMovement;
        eachBandit.y = 10;
        eachBandit.width = 30; // 30 pixels wide
        eachBandit.height = 50; // 50 pixels height
        enemy.add(eachBandit); // add the bandit into the array
        lastSpawnTime = TimeUtils.nanoTime();
    }

    @Override
    void removeEnemy() {
        iterEnemy = enemy.iterator();
        while (iterEnemy.hasNext()) {
            Rectangle banditRun = iterEnemy.next();
            banditRun.x += speed * Gdx.graphics.getDeltaTime();
            if (banditRun.x + 64 < 0) {
                iterEnemy.remove();
            }
            if (banditRun.overlaps(gameScreen.player.cowboy)) {
                gameScreen.healthPoints--;
                gameScreen.damageNoise.setVolume(gameScreen.damageNoise.play(),
                        0.3F);
                iterEnemy.remove();
            }
        }
    }

    @Override
    void drawEnemy() {
        ufoImage = new Texture(Gdx.files.internal("ufo_sprite.png"));
        for (Rectangle aBandit: enemy) {
            gameScreen.game.batch.draw(ufoImage, aBandit.x, aBandit.y);
        }
    }

    @Override
    public void dispose() {
        ufoImage.dispose();
    }
}