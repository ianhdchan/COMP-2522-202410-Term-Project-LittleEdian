package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;

public class ChangeDifficulty {
    protected final GameScreen gameScreen;
    private float timeSeconds = 0f;
    private final float period = 1f;

    public ChangeDifficulty(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }



    protected void timer() {

        // Timer for events
        timeSeconds += Gdx.graphics.getDeltaTime();
        if (timeSeconds > period) {
            System.out.println(timeSeconds);
            if (timeSeconds > 5) {
                gameScreen.bandit2.xMovement = 800;
                gameScreen.bandit2.speed = -150;

            } else if (timeSeconds > 30) {
                gameScreen.oneSecond = 100000000;
            } else if (timeSeconds > 10) {
                gameScreen.oneSecond = 500000000;
            }
        }
    }
}