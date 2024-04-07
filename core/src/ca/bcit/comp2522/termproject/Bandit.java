package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Bandit extends Enemy {
    public Bandit(final GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    void spawnEnemy() {
        Rectangle eachBandit = new Rectangle();
        eachBandit.x = 0;
        eachBandit.y = 10;
        eachBandit.width = 64; // 10 pixels wide
        eachBandit.height = 64; // 64 pixels height
        enemy.add(eachBandit); // add the laserbeam into the array
        lastSpawnTime = TimeUtils.nanoTime();
    }

    @Override
    void removeEnemy() {
        iterEnemy = enemy.iterator();
        while (iterEnemy.hasNext()) {
            Rectangle banditRun = iterEnemy.next();
            banditRun.x += 150 * Gdx.graphics.getDeltaTime();
            if (banditRun.x + 64 < 0) {
                iterEnemy.remove();
            }
            if (banditRun.overlaps(gameScreen.player.cowboy)) {
                gameScreen.damageNoise.setVolume(gameScreen.damageNoise.play(),
                        0.3F);
                iterEnemy.remove();
            }
        }

    }

    @Override
    void drawEnemy() {
        for (Rectangle aBandit: enemy) {
            gameScreen.game.batch.draw(gameScreen.bucketImage, aBandit.x, aBandit.y);
        }
    }
}