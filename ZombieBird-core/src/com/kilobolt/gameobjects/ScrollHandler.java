package com.kilobolt.gameobjects;

import com.kilobolt.gameworld.GameWorld;
import com.kilobolt.zbhelpers.AssetLoader;

public class ScrollHandler {

	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;
	private Portal portal1;
	private Bird bird;
	private Cannon startCannon;
	//private float newSpeed;
	private float SCROLL_SPEED = -200;
	///private float speed;
	public static final int PIPE_GAP = 500;

	private GameWorld gameWorld;

	public ScrollHandler(GameWorld gameWorld, float yPos) {
		this.gameWorld = gameWorld;
		/*
		 * System.out.println(SCROLL_SPEED); if (SCROLL_SPEED == 0) {
		 * SCROLL_SPEED = -50; }
		 */
		frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
		backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);

		startCannon = new Cannon(5, yPos - 33, 42, 33, SCROLL_SPEED);

		pipe1 = new Pipe(210 + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
		pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
		pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
		portal1 = new Portal(pipe1.getTailX() + PIPE_GAP / 2, yPos - 6, 37, 11, SCROLL_SPEED);
	}

	public void update(float delta) {
		// Update our objects
		frontGrass.update(delta);
		backGrass.update(delta);
		startCannon.update(delta);
		pipe1.update(delta);
		pipe2.update(delta);
		pipe3.update(delta);
		portal1.update(delta);
		// SCROLL_SPEED = -bird.getSpeed();
		// System.out.println(SCROLL_SPEED);

		// Check if any of the pipes are scrolled left,
		// and reset accordingly
		if (pipe1.isScrolledLeft()) {
			pipe1.reset(pipe3.getTailX() + PIPE_GAP);
		} else if (pipe2.isScrolledLeft()) {
			pipe2.reset(pipe1.getTailX() + PIPE_GAP);

		} else if (pipe3.isScrolledLeft()) {
			pipe3.reset(pipe2.getTailX() + PIPE_GAP);
		}

		if (portal1.isScrolledLeft()) {
			portal1.reset(pipe1.getTailX() + PIPE_GAP / 2);
		}

		// Same with grass
		if (frontGrass.isScrolledLeft()) {
			frontGrass.reset(backGrass.getTailX());

		} else if (backGrass.isScrolledLeft()) {
			backGrass.reset(frontGrass.getTailX());

		}
	}

	public void stop() {
		frontGrass.stop();
		backGrass.stop();
		pipe1.stop();
		pipe2.stop();
		pipe3.stop();
		portal1.stop();
	}

	public boolean collides(Bird bird) {

		if (!pipe1.isScored() && pipe1.getX() + (pipe1.getWidth() / 2) < bird.getX() + bird.getWidth()) {
			addScore(1);
			pipe1.setScored(true);
			AssetLoader.coin.play();
		} else if (!pipe2.isScored() && pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX() + bird.getWidth()) {
			addScore(1);
			pipe2.setScored(true);
			AssetLoader.coin.play();

		} else if (!pipe3.isScored() && pipe3.getX() + (pipe3.getWidth() / 2) < bird.getX() + bird.getWidth()) {
			addScore(1);
			pipe3.setScored(true);
			AssetLoader.coin.play();

		}

		return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird));
	}

	public boolean portals(Bird bird) {
		return (portal1.collides(bird));
	}

	private void addScore(int increment) {
		gameWorld.addScore(increment);
	}

	public Grass getFrontGrass() {
		return frontGrass;
	}

	public Grass getBackGrass() {
		return backGrass;
	}

	public Cannon getStartCannon() {
		return startCannon;
	}

	public Pipe getPipe1() {
		return pipe1;
	}

	public Pipe getPipe2() {
		return pipe2;
	}

	public Pipe getPipe3() {
		return pipe3;
	}

	public Portal getPortal1() {
		return portal1;
	}

	public void onRestart() {
		// SCROLL_SPEED = -50;
		frontGrass.onRestart(0, SCROLL_SPEED);
		backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
		startCannon.onRestart(20, SCROLL_SPEED);
		pipe1.onRestart(210 + PIPE_GAP, SCROLL_SPEED);
		pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
		pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
		portal1.onRestart(pipe1.getTailX() + PIPE_GAP / 2, SCROLL_SPEED);
	}

	public void bounce() {
		// SCROLL_SPEED = SCROLL_SPEED - 20;
		frontGrass.bounce();
		backGrass.bounce();
		startCannon.bounce();
		pipe1.bounce();
		pipe2.bounce();
		pipe3.bounce();
		portal1.bounce();

	}

	public void teleport(float speed) {
		// SCROLL_SPEED = SCROLL_SPEED - 20;
		//newSpeed = speed;
		frontGrass.teleport(speed);
		backGrass.teleport(speed);
		startCannon.teleport(speed);
		pipe1.teleport(speed);
		pipe2.teleport(speed);
		pipe3.teleport(speed);
		portal1.teleport(speed);

	}
	
	public boolean xStopped() {
		return backGrass.xStopped();
	}

}