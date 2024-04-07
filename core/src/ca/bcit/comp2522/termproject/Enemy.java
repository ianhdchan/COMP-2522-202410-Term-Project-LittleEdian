package ca.bcit.comp2522.termproject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

abstract public class Enemy {
    protected final GameScreen gameScreen;
    protected Array<Rectangle> enemy;
    protected Iterator<Rectangle> iterEnemy;
    protected long lastSpawnTime;


    public Enemy(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.enemy = new Array<Rectangle>();

    }

    abstract void spawnEnemy();
    abstract void removeEnemy();



}
