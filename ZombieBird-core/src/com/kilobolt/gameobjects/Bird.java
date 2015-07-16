package com.kilobolt.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.kilobolt.zbhelpers.AssetLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.gameobjects.ScrollHandler;

public class Bird {

	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private float speed, swapper;
	private long teleTimer;

	private float rotation;
	private int width;
	private int height;

	private boolean isAlive;
	private boolean bounced;

	private Circle boundingCircle;

	public Bird(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 460);
		boundingCircle = new Circle();
		speed = -200;
		teleTimer = System.currentTimeMillis();
		isAlive = true;
	}

	public void update(float delta) {

		velocity.add(acceleration.cpy().scl(delta));

		if (velocity.y > 200) {
			velocity.y = 200;
		}

		// CEILING CHECK
		if (position.y < -13) {
			position.y = -13;
			velocity.y = 0;
		}

		// GROUND CHECK
		// if (position.y > 150) {
		// position.y = 150;
		// velocity.y = -2*velocity.y/3;
		// speed = speed*3/2;
		// }

		position.add(velocity.cpy().scl(delta));

		// Set the circle's center to be (9, 6) with respect to the bird.
		// Set the circle's radius to be 6.5f;
		boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

		// Rotate counterclockwise
		if (velocity.y < 0) {
			rotation -= 600 * delta;

			if (rotation < -20) {
				rotation = -20;
			}
		}

		// Rotate clockwise
		if (isFalling() || !isAlive) {
			rotation += 480 * delta;
			if (rotation > 90) {
				rotation = 90;
			}

		}

	}

	public boolean isFalling() {
		return velocity.y > 110;
	}

	public boolean shouldntFlap() {
		return velocity.y > 70 || !isAlive;
	}

	public void onClick() {
		if (isAlive) {
			AssetLoader.flap.play();
			velocity.y = -140;
		}
	}

	public void die() {
		isAlive = false;
		velocity.y = 0;
	}

	public void bounce(float y) {
		if (System.currentTimeMillis() - teleTimer > 200) {
			teleTimer = System.currentTimeMillis();
			if (velocity.y > 1) {
				velocity.y = -velocity.y * 3 / 4;
				position.y = y + 55;
				bounced = true;
				speed = speed * 3 / 4;
			} else {
				if (velocity.y > -1) {
					velocity.y = 0;
				}
			}
		}
	}

	public void teleport(int y) {
		if (System.currentTimeMillis() - teleTimer > 200) {
			teleTimer = System.currentTimeMillis();
			if (position.y > y) {
				position.y += -150;
				swapper = -velocity.y;
				velocity.y = speed;
				speed = swapper;
			} else {
				position.y += 140;
				if (velocity.y > 0) {
					velocity.y = -velocity.y;
				}
			}
		}
	}

	public void decelerate() {
		acceleration.y = 0;
	}

	public void onRestart(int y) {
		rotation = 0;
		position.x = -25;
		position.y = y;
		velocity.x = 0;
		velocity.y = 0;
		acceleration.x = 0;
		acceleration.y = 460;
		speed = 100;
		isAlive = true;
	}

	public void moveBird(int newX, int newY) {
		position.y = newY;
		position.x = newX;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getSpeed() {
		return speed;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public boolean isAlive() {
		return isAlive;
	}
}