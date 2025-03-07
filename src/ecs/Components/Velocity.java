package ecs.Components;

public class Velocity extends Component {
    public float xVelocity;
    public float yVelocity;

    public Velocity(float xVelocity, float yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public Velocity() {
        this(0f, 0f);
    }
}
