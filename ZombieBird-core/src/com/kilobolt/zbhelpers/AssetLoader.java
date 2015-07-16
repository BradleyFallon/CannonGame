package com.kilobolt.zbhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture;
	public static TextureRegion bg, cannon, grass;

	public static Animation birdAnimation;
	public static TextureRegion bird, birdDown, birdUp;

	public static Animation portalAnimationDown, portalAnimationUp;
	public static TextureRegion p1d, p2d, p3d, p4d, p1u, p2u, p3u, p4u;

	public static TextureRegion skullUp, skullDown, bar;

	public static Sound dead, flap, coin;

	public static BitmapFont font, shadow;

	public static Preferences prefs;

	public static void load() {

		texture = new Texture(Gdx.files.internal("data/texture.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		bg = new TextureRegion(texture, 0, 0, 136, 43);
		bg.flip(false, true);

		cannon = new TextureRegion(texture, 170, 55, 42, 33);
		cannon.flip(false, true);

		grass = new TextureRegion(texture, 0, 43, 143, 11);
		grass.flip(false, true);

		birdDown = new TextureRegion(texture, 136, 0, 17, 12);
		birdDown.flip(false, true);

		bird = new TextureRegion(texture, 153, 0, 17, 12);
		bird.flip(false, true);

		birdUp = new TextureRegion(texture, 170, 0, 17, 12);
		birdUp.flip(false, true);

		p1d = new TextureRegion(texture, 58, 79, 37, 11);
		p2d = new TextureRegion(texture, 95, 90, 37, 11);
		p3d = new TextureRegion(texture, 95, 79, 37, 11);
		p4d = new TextureRegion(texture, 58, 90, 37, 11);
		p1u = new TextureRegion(texture, 58, 101, 37, 11);
		p2u = new TextureRegion(texture, 95, 112, 37, 11);
		p3u = new TextureRegion(texture, 95, 101, 37, 11);
		p4u = new TextureRegion(texture, 58, 112, 37, 11);

		TextureRegion[] birds = { birdDown, bird, birdUp };
		birdAnimation = new Animation(0.12f, birds);
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		TextureRegion[] portalsDown = { p1d, p2d, p3d, p4d };
		portalAnimationDown = new Animation(0.06f, portalsDown);
		portalAnimationDown.setPlayMode(Animation.PlayMode.LOOP);
		TextureRegion[] portalsUp = { p1u, p2u, p3u, p4u };
		portalAnimationUp = new Animation(0.06f, portalsUp);
		portalAnimationUp.setPlayMode(Animation.PlayMode.LOOP);

		skullUp = new TextureRegion(texture, 192, 0, 24, 14);
		// Create by flipping existing skullUp
		skullDown = new TextureRegion(skullUp);
		skullDown.flip(false, true);

		bar = new TextureRegion(texture, 136, 16, 22, 3);
		bar.flip(false, true);

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.getData().setScale(.25f, -.25f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.getData().setScale(.25f, -.25f);

		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("ZombieBird");

		// Provide default high score of 0
		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}

	}

	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void dispose() {
		// We must dispose of the texture when we are finished.
		texture.dispose();

		// Dispose sounds
		dead.dispose();
		flap.dispose();
		coin.dispose();

		font.dispose();
		shadow.dispose();
	}

}