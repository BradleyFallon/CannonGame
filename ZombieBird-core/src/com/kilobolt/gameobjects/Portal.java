/* package com.kilobolt.gameobjects;

public class Portal extends Scrollable {

	// When Grass's constructor is invoked, invoke the super (Scrollable)
	// constructor
	public Portal(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		System.out.println(scrollSpeed);
	}

	public void onRestart(float x, float scrollSpeed) {
		position.x = x;
		velocity.x = scrollSpeed;

	}

}
*/

package com.kilobolt.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Portal extends Scrollable {

    private Random r;

    private Rectangle portalUp, portalDown;

    public static final int VERTICAL_GAP = 45;

    private float groundY;


    // When Pipe's constructor is invoked, invoke the super (Scrollable)
    // constructor
    public Portal(float x, float y, int width, int height, float scrollSpeed ) {
        super(x, y, width, height, scrollSpeed);
        // Initialize a Random object for Random number generation

        portalUp = new Rectangle();
        portalDown = new Rectangle();
        
        this.groundY = groundY;
    }

    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);
 
        // This shift is equivalent to: (SKULL_WIDTH - width) / 2
        portalUp.set(position.x , position.y -150, width, height);
        portalDown.set(position.x , position.y , width, height);

    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    public Rectangle getPortalUp() {
        return portalUp;
    }

    public Rectangle getPortalDown() {
        return portalDown;
    }

    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth()) {
            return ( Intersector.overlaps(bird.getBoundingCircle(), portalUp) || Intersector
                        .overlaps(bird.getBoundingCircle(), portalDown));
        }
        return false;
    }

}
