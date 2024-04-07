package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Laser extends Enemy {
    protected Texture laserImage;

    public Laser(final GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    void spawnEnemy() {
        Rectangle laserBeam = new Rectangle();
        laserBeam.x = MathUtils.random(0, 800 - 64);
        laserBeam.y = 600;
        laserBeam.width = 1; // 1 pixels wide
        laserBeam.height = 40; // 64 pixels height
        enemy.add(laserBeam); // add the laserbeam into the array
        lastSpawnTime = TimeUtils.nanoTime();
    }

    @Override
    void removeEnemy() {
        iterEnemy = enemy.iterator();
        while (iterEnemy.hasNext()) {
            Rectangle laserDrop = iterEnemy.next();
            laserDrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (laserDrop.y + 64 < 0) {
                iterEnemy.remove();
            }
            if (laserDrop.overlaps(gameScreen.player.cowboy)) {
                gameScreen.healthPoints--;
                gameScreen.damageNoise.setVolume(gameScreen.damageNoise.play(),
                0.3F);
                iterEnemy.remove();
            }
        }

    }

    @Override
    void drawEnemy() {
        laserImage = new Texture(Gdx.files.internal("laser.png"));
        for (Rectangle laserDrop : enemy) {
            gameScreen.game.batch.draw(laserImage, laserDrop.x, laserDrop.y);
        }
    }

    @Override
    public void dispose() {
        laserImage.dispose();

    }

}