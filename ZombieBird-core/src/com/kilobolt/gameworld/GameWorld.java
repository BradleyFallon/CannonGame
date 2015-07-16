package com.kilobolt.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameobjects.ScrollHandler;
import com.kilobolt.zbhelpers.AssetLoader;

public class GameWorld {

	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;
	private int score = 0;
	private float speed;

	private int midPointY;

	private GameState currentState;

	public enum GameState {
		READY, FIRE, RUNNING, GAMEOVER, HIGHSCORE
	}

	public GameWorld(int midPointY) {
		currentState = GameState.READY;
		this.midPointY = midPointY;
		bird = new Bird(-20, midPointY - 5, 17, 12);
		// The grass should start 66 pixels below the midPointY
		scroller = new ScrollHandler(this, midPointY + 66);
		ground = new Rectangle(0, midPointY + 66, 137, 11);
	}

	public void update(float delta) {

		switch (currentState) {
		case READY:
			updateReady(delta);
			break;
			
		//case FIRE:
			//updateFire(delta);
			//break;

		case RUNNING:
			updateRunning(delta);
			break;
		default:
			break;
		}

	}

	private void updateReady(float delta) {
		// Do nothing for now
	}
	
	private void updateFire(float delta) {
		
	}

	public void updateRunning(float delta) {
		if (delta > .15f) {
			delta = .15f;
		}

		bird.update(delta);
		scroller.update(delta);

		if (scroller.collides(bird) && bird.isAlive()) {
			scroller.stop();
			bird.die();
			AssetLoader.dead.play();
			currentState = GameState.GAMEOVER;

			if (score > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore(score);
				currentState = GameState.HIGHSCORE;
			}
		}
		
		if (scroller.portals(bird) && bird.isAlive()) {
			bird.teleport(midPointY);
			speed = bird.getSpeed();
			scroller.teleport(speed);
			//AssetLoader.teleport.play();
		}

		if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
			bird.bounce(midPointY);
			scroller.bounce();
			if ( scroller.xStopped() ) {
				bird.die();
				scroller.stop();
				AssetLoader.dead.play();
				currentState = GameState.GAMEOVER; 
			}
		}

	}
	// scroller.stop();
	// bird.die();
	// bird.decelerate();
	// currentState = GameState.GAMEOVER;

	// if (score > AssetLoader.getHighScore()) {
	// AssetLoader.setHighScore(score);
	// currentState = GameState.HIGHSCORE;
	// }
	// }
	

	public Bird getBird() {
		return bird;

	}

	public ScrollHandler getScroller() {
		return scroller;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int increment) {
		score += increment;
	}

	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public void start() {
		currentState = GameState.FIRE;
	}
	
	public void shoot() {
		//bird = new Bird(33, midPointY - 5, 17, 12);
		bird.moveBird(35, midPointY + 20);
		currentState = GameState.RUNNING;
	}

	public void restart() {
		currentState = GameState.READY;
		score = 0;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		currentState = GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}
	
	public boolean isFire() {
		return currentState == GameState.FIRE;
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}
}