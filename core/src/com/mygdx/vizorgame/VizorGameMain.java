package com.mygdx.vizorgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.utils.GameManager;

public class VizorGameMain extends Game {
	private SpriteBatch batch;
	private GameScreen gScreen;

	private Stage stage;
	private Table table;
	private Skin skin;
	private TextButton changeHatButton, changeClothButton;

	@Override
	public void create() {
		batch = new SpriteBatch();
		gScreen = new GameScreen(batch);
		setScreen(gScreen);

		generateUI();

	}

	@Override
	public void render() {
		super.render();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}

	@Override
	public void dispose() {
		batch.dispose();
		GameManager.getInstance().dispose();
		super.dispose();
	}

	private void generateUI(){
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage(new ScreenViewport());
		table = new Table();
		table.setWidth(GameScreen.WIDTH);
		table.align(Align.right|Align.center);
		table.setPosition(0, Gdx.graphics.getHeight());

		changeHatButton = new TextButton("Change hat", skin);
		changeHatButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameManager.getInstance().playerController.changeHat();
			}
		});
		changeClothButton = new TextButton("Change cloth", skin);
		changeClothButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameManager.getInstance().playerController.changeCloth();
			}
		});

		table.padTop(100);
		table.padRight(20);
		table.add(changeHatButton).padBottom(15);
		table.row();
		table.add(changeClothButton);
		stage.addActor(table);

		InputMultiplexer im = new InputMultiplexer(stage, gScreen.input);
		Gdx.input.setInputProcessor(im);
	}

}
