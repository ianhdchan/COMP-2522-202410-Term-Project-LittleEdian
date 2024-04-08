package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    final COMP2522TermProject game;
    private final Texture gameOver;

    OrthographicCamera camera;

    public GameOverScreen(COMP2522TermProject game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        gameOver = new TextureRegion(new Texture("gameover.jpg"), 0, 0, 800, 600).getTexture();

    }

    public void draw() {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.setColor(0,0,0,1);
        game.batch.draw(gameOver, 0, 0, 800, 600);
        game.font.draw(game.batch, "GAME OVER!", 360, 500);
        game.font.draw(game.batch, "You ran out of health Points!", 90, 125);
        game.font.draw(game.batch, "Press the Space Bar to retry again.", 90, 90);
        game.font.setColor(255,255,255,1);
        game.batch.end();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        draw();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
