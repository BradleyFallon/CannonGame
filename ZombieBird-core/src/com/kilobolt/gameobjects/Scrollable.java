package com.kilobolt.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.kilobolt.gameworld.GameWorld;

public class Scrollable {

	// Protected is similar to private, but allows inheritance by subclasses.
	protected Vector2 position;
	public Vector2 velocity;
	protected int width;
	protected int height;
	//private float speed;
	protected boolean isScrolledLeft;
	public boolean xStopped = false;
	//private Bird bird;
	//private GameWorld myWorld;

	public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
		position = new Vector2(x, y);
		velocity = new Vector2(scrollSpeed, 0);
		this.width = width;
		this.height = height;
		isScrolledLeft = false;
		//bird = myWorld.getBird();
	}

	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));

		// If the Scrollable object is no longer visible:
		if (position.x + width < 0) {
			isScrolledLeft = true;
		}
	}

	// Reset: Should Override in subclass for more specific behavior.
	public void reset(float newX) {
		position.x = newX;
		isScrolledLeft = false;
	}

	public void stop() {
		velocity.x = 0;
	}
	
	public void bounce() {
		velocity.x = velocity.x * 3 / 4;
		if ( velocity.x > -1) {
			xStopped = true;
		} else {
			xStopped = false;
		}
	}
	
	public void teleport(float speed) {
		velocity.x = speed;
	}
	
	public boolean xStopped() {
		return xStopped;
	}

	// Getters for instance variables
	public boolean isScrolledLeft() {
		return isScrolledLeft;
	}

	public float getTailX() {
		return position.x + width;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}