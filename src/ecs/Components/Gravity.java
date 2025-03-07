package ecs.Components;

public class Gravity extends Component {
    public static final float DEFAULT_GRAVITY = 9.8f; // Earth's gravity in m/s² (adjust for game scale)
    public float acceleration;

    public Gravity() {
        this.acceleration = DEFAULT_GRAVITY; // Default gravity value
    }

    public Gravity(float customGravity) {
        this.acceleration = customGravity; // Allows customization (e.g., moon gravity)
    }
}
