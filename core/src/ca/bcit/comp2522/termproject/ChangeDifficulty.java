package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;

public class ChangeDifficulty {
    protected final GameScreen gameScreen;
    protected float timeSeconds = 0f;
    private final float period = 1f;

    public ChangeDifficulty(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }



    protected void timer() {

        // Timer for events
        timeSeconds += Gdx.graphics.getDeltaTime();
        if (timeSeconds > period) {
            if (timeSeconds > 70) {
                gameScreen.oneSecond = 10000000;
            } else if (timeSeconds > 60) {
                gameScreen.oneSecond = 100000000;
            } else if (timeSeconds > 45) {
                gameScreen.oneSecond = 500000000;
            } else if (timeSeconds > 30) {
                gameScreen.bandit2.xMovement = 800;
                gameScreen.bandit2.speed = -150;
            } else if (timeSeconds > 10) {
                gameScreen.oneSecond = 800000000;
            }
        }
    }
}