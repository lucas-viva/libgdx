/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.utils.ScreenUtils;

/** Tests the GestureDetector using Scene 2d actors by simulating touch down/ups on two different scroll panes. */
public class Scene2dGestureDetectorTest extends GdxTest {

	private Stage stage;
	private Skin skin;

	private ScrollPane pane1;
	private ScrollPane pane2;

	public void create () {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("data/uiskin.json"));

		pane1 = new ScrollPane(new Table(), skin);
		pane2 = new ScrollPane(new Table(), skin);

		Table table = new Table();
		table.setFillParent(true);
		table.add(pane1).grow();
		table.add(pane2).grow();

		stage.addActor(table);
	}

	private boolean gesturesTriggered = false;

	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		if (gesturesTriggered) {
			return;
		}

		Vector2 pane1Position = pane1.localToScreenCoordinates(new Vector2(10, 10));
		Vector2 pane2Position = pane2.localToScreenCoordinates(new Vector2(10, 10));

		// Simulates:
		// 1. Touch down on pane1 at (10, 10) with pointer 0.
		// 2. Touch down on pane2 at (10, 10) with pointer 1.
		// 3. Touch down on pane1 at (11, 10) with pointer 2.
		// 4. Touch up on pane1 at (11, 10) with pointer 2.
		stage.touchDown((int)pane1Position.x, (int)pane1Position.y, 0, 0);
		stage.touchDown((int)pane2Position.x, (int)pane2Position.y, 1, 0);
		stage.touchDown((int)pane1Position.x + 1, (int)pane1Position.y, 2, 0);
		stage.touchUp((int)pane1Position.x + 1, (int)pane1Position.y, 2, 0);
		gesturesTriggered = true;
	}

	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose () {
		stage.dispose();
		skin.dispose();
	}
}
