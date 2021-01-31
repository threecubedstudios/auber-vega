package com.team3.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team3.game.GameMain;
import com.team3.game.tools.BackgroundRenderer;
import com.team3.game.tools.Serializer;

/**
 * MainMenu.
 */
public class MainMenu extends ScreenAdapter {

  private Viewport viewport;
  private OrthographicCamera camera;
  private TextureAtlas atlas;
  private Stage stage;
  private Skin skin;
  private SpriteBatch batch;
  private BackgroundRenderer backgroundRenderer;
  private Texture instructionsTexture;

  /**
   * Creates an instantiated instance of the MainMenu screen.

   * @param batch The spritebatch to draw with
   */
  public MainMenu(SpriteBatch batch) {
    this.batch = batch;
    atlas = new TextureAtlas("skin/hudskin/comic-ui.atlas");
    skin = new Skin(Gdx.files.internal("skin/hudskin/comic-ui.json"), atlas);
    instructionsTexture = new Texture(Gdx.files.internal("menu/instructions.png"));

    camera = new OrthographicCamera();
    camera.setToOrtho(false);
    viewport = new ScreenViewport(camera);
    viewport.apply();

    camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    camera.update();

    stage = new Stage(viewport, batch);

    backgroundRenderer = new BackgroundRenderer(batch, viewport);


  }

  @Override
  public void show() {
    // Passes all input to the stage.
    Gdx.input.setInputProcessor(stage);

    // Create a main table into which all ui elements will be placed.
    Table root = new Table();
    root.setFillParent(true);
    root.center();

    // Main play button (others can be added easily as needed).
    TextButton playButton = new TextButton("Play", skin);
    TextButton demoButton = new TextButton("Demo", skin);

    // Creates a listener to listen for clicks on the button,
    // when button is clicked start an instance of Gameplay to start playing the game.
    playButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        GameMain game = (GameMain) Gdx.app.getApplicationListener();
        Gameplay gameplay = new Gameplay(game, false);
        game.setScreen(gameplay);
      }
    });
    demoButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        GameMain game = (GameMain) Gdx.app.getApplicationListener();
        game.setScreen(new GameDemo(game));
      }
    });

    // Game title
    Label title = new Label("Vega - Auber", skin);

    // Instructions image
    Image instructionsImage = new Image(instructionsTexture);
    instructionsImage.setScaling(Scaling.fit);

    root.add(title);
    root.row();
    root.add(playButton);
    root.row();
    root.add(demoButton);
    root.row();
    root.add(instructionsImage);

    stage.addActor(root);
  }

  @Override
  public void render(float delta) {
    viewport.apply();
    backgroundRenderer.update(delta);
    stage.act();

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.position.set(0, 0, 0);
    camera.update();
    batch.setProjectionMatrix(camera.combined);
    backgroundRenderer.render();
    camera.position.set(viewport.getScreenWidth() / 2f, viewport.getScreenHeight() / 2f, 0);
    camera.update();
    batch.setProjectionMatrix(camera.combined);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height, true);
  }

  @Override
  public void dispose() {
    skin.dispose();
    atlas.dispose();
  }
}
