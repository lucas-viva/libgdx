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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.utils.ScreenUtils;

/** Tests the GestureDetector using Scene 2d actors by simulating touch down/ups on a table that triggers a dialog popup. */
public class Scene2dGestureDetector2Test extends GdxTest {

	private Stage stage;
	private Skin skin;

	private Table table;

	private boolean gesturesTriggered = false;

	public void create () {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("data/uiskin.json"));

		table = new Table();
		table.addListener(new ActorGestureListener() {
			@Override
			public void tap (InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				Dialog dialog = new Dialog("Test", skin);
				dialog.show(stage);
			}
		});
		table.setFillParent(true);
		table.setTouchable(Touchable.enabled);

		stage.addActor(table);
	}

	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		if (gesturesTriggered) {
			return;
		}

		Vector2 tablePosition = table.localToScreenCoordinates(new Vector2(10, 10));

		// Simulates:
		// 1. Touch down on table at (10, 10) with button 0.
		// 2. Touch down on table at (11, 10) with button 1.
		// 3. Touch up on table at (11, 10) with button 1.
		stage.touchDown((int)tablePosition.x, (int)tablePosition.y, 0, 0);
		stage.touchDown((int)tablePosition.x + 1, (int)tablePosition.y, 0, 1);
		stage.touchUp((int)tablePosition.x + 1, (int)tablePosition.y, 0, 1);
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
