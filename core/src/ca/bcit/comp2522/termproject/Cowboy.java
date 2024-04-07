package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Cowboy {
    protected final GameScreen gameScreen;

    /** Tracks current frame of sprites **/
    private int currentFrame = 0;
    protected Rectangle cowboy;
    /** Checks if sprite is facing left **/
    private boolean isFacingLeft = false;


    /** Determines isJumping state of character. */
    private boolean isJumping = false;

    /** Determines isJumping state of character. */
    private boolean isFalling = false;

    /** How fast the jump action will be. */
    private final float jumpVelocity = 250;
    /** Max Jump Height. */
    private final float MAX_JUMP_HEIGHT = 200;

    protected final Texture[] runningLeft = new Texture[3];
    protected final Texture[] runningRight = new Texture[3];
    protected final Sprite[] runningLeftSprite = new Sprite[3];
    protected final Sprite[] runningRightSprite = new Sprite[3];
    protected Texture cowboyStillL;
    protected Texture cowboyStillR;

    public Cowboy(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

    }

    void cowboyStillTexture() {
        // load cowboy sprite images for running left, 64 x 64 pixels each
        runningLeft[0] = new Texture(Gdx.files.internal("Cowboy_L1.png"));
        runningLeft[1] = new Texture(Gdx.files.internal("Cowboy_L2.png"));
        runningLeft[2] = new Texture(Gdx.files.internal("Cowboy_L3.png"));
        for (int i = 0; i < 3; i++) {
            runningLeftSprite[i] = new Sprite(runningLeft[i]);
        }
        // load cowboy sprite images for running right, 64 x 64 pixels each
        runningRight[0] = new Texture(Gdx.files.internal("Cowboy_R1.png"));
        runningRight[1] = new Texture(Gdx.files.internal("Cowboy_R2.png"));
        runningRight[2] = new Texture(Gdx.files.internal("Cowboy_R3.png"));
        for (int i = 0; i < 3; i++) {
            runningRightSprite[i] = new Sprite(runningRight[i]);
        }
        cowboyStillL = new Texture(Gdx.files.internal("Cowboy_StillL.png"));
        cowboyStillR = new Texture(Gdx.files.internal("Cowboy_StillR.png"));
    }


    void createCowboy(){
        cowboy = new Rectangle();
        cowboy.x = 800 / 2 - 64 / 2; // This centers the cowboy horizontally (x-axis)
        cowboy.y = 20; // bottom

        cowboy.width = 64; // 64 pixels
        cowboy.height = 64; // 64 pixels
    }

    public void cowboyMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            updateCowboy(runningLeftSprite);
            cowboy.x -= 200 * Gdx.graphics.getDeltaTime();
            // Right key pressed
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            updateCowboy(runningRightSprite);
            cowboy.x += 200 * Gdx.graphics.getDeltaTime();
            // Checks if facing left direction
        } else {
            Texture stillSprite = isFacingLeft ? cowboyStillL : cowboyStillR;
            gameScreen.game.batch.draw(stillSprite, cowboy.x, cowboy.y);
        }


        // todo: ugly smelly code please fix
        //  y
        // JUMP LOGIC
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && !isJumping && !isFalling) {
            isJumping = true;
        }

        if (isJumping) {
            cowboy.y += jumpVelocity * Gdx.graphics.getDeltaTime();
            if (cowboy.y >= MAX_JUMP_HEIGHT - 1) {
                isJumping = false;
                isFalling = true;
            }
        } else if (isFalling) {
            cowboy.y -= jumpVelocity * Gdx.graphics.getDeltaTime();
            if (cowboy.y <= 0) {
                isJumping = false;
                isFalling = false;
            }
        }
    }

    private void updateCowboy(Sprite[] runningSprite) {
        currentFrame = (currentFrame + 1) % runningSprite.length;
        runningSprite[currentFrame].setPosition(cowboy.x, cowboy.y);
        runningSprite[currentFrame].draw(gameScreen.game.batch);
        isFacingLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT); // update facing direction
    }

    protected void isWithinBounds() {
        if (cowboy.y < 0) {
            cowboy.y  = 0;
        }

        // Ensure cowboy stays within screen bounds
        if (cowboy.x < 0) {
            cowboy.x = 0;
        }
        if (cowboy.x > 800 - 64) {
            cowboy.x = 800 - 64;
        }
    }
}
