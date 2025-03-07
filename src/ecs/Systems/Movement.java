package ecs.Systems;

import ecs.Components.*;

public class Movement extends System {
    public Movement() {
        super(Movable.class, Position.class, Velocity.class);
    }

    @Override
    public void update(double elapsedTime) {
        for (var entity : entities.values()) {
            applyGravity(entity, elapsedTime);
            applyAcceleration(entity, elapsedTime);
            moveEntity(entity, elapsedTime);
        }
    }

    private void applyGravity(ecs.Entities.Entity entity, double elapsedTime) {
        var gravity = entity.get(Gravity.class);
        var velocity = entity.get(Velocity.class);

        if (gravity != null && velocity != null) {
            velocity.yVelocity += gravity.acceleration * elapsedTime;
        }
    }

    private void applyAcceleration(ecs.Entities.Entity entity, double elapsedTime){
        var acceleration = entity.get(Acceleration.class);
        var velocity = entity.get(Velocity.class);

        velocity.xVelocity += (float) (acceleration.xAcceleration * elapsedTime);
        velocity.yVelocity += (float) (acceleration.yAcceleration * elapsedTime);
    }

    private void moveEntity(ecs.Entities.Entity entity, double elapsedTime) {
        var movable = entity.get(Movable.class);
        var velocity = entity.get(Velocity.class);
        var position = entity.get(Position.class);

        float rotationSpeed = 50f; // degrees per second (adjust as needed)
        float thrustSpeed = 50f;   // thrust speed (adjust as needed)

        switch (movable.facing) {
            case RotationLeft:
                position.angle -= (float) (rotationSpeed * elapsedTime);
                break;
            case RotationRight:
                position.angle += (float) (rotationSpeed * elapsedTime);
                break;
            case Up:
                velocity.xVelocity += (float) (Math.cos(Math.toRadians(position.angle - 90)) * thrustSpeed * elapsedTime);
                velocity.yVelocity += (float) (Math.sin(Math.toRadians(position.angle - 90)) * thrustSpeed * elapsedTime);
                break;
            case Stopped:
                // Optionally add friction here if desired
                break;
        }

        position.x += (float) (velocity.xVelocity * elapsedTime);
        position.y += (float) (velocity.yVelocity * elapsedTime);
    }

}
