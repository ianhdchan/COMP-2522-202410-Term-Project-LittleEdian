package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BanditTest {
    @Mock
    Files files;
    @Mock
    FileHandle fileHandle;
    @Mock
    GL20 mockGL;
    private Bandit bandit;

    @BeforeEach
    public void setUp() {
        GameScreen gameScreen = mock(GameScreen.class);
        Texture mockTexture = mock(Texture.class);
        bandit = new Bandit(gameScreen, mockTexture);
        Cowboy cowboy = new Cowboy(gameScreen);

        Gdx.gl = mockGL;
        Gdx.files = files;
        Gdx.graphics = mock(Graphics.class);
        gameScreen.player = cowboy;
        gameScreen.game = mock(COMP2522TermProject.class);
        gameScreen.game.batch = mock(SpriteBatch.class);

        MockitoAnnotations.openMocks(this);

        when(files.internal("mock.jpg")).thenReturn(fileHandle);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.016f);
    }

    @Test
    public void testSpawnEnemy() {
        bandit.spawnEnemy();
        assertEquals(1, bandit.enemy.size, "One enemy should be spawned");
    }

    @Test
    public void testDrawEnemy() {
        bandit.spawnEnemy();
        bandit.drawEnemy();
        assertNotNull(bandit.banditImage, "UFO image should be initialized");
    }

    @Test
    public void testDispose() {
        bandit.spawnEnemy();
        bandit.dispose();
        assertNull(bandit.banditImage, "UFO image should be disposed");
    }
}