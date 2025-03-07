package ecs.Components;

public class Position extends Component {
    public float x;
    public float y;
    public float angle; // rotation angle in degrees

    public Position(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
}
