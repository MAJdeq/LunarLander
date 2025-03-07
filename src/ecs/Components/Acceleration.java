package ecs.Components;

public class Acceleration extends Component {
    public float xAcceleration;
    public float yAcceleration;

    public Acceleration() {
        this(0f, 0f);
    }

    public Acceleration(float xAccel, float yAccel) {
        this.xAcceleration = xAccel;
        this.yAcceleration = yAccel;
    }
}
