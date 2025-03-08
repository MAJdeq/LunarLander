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
        var position = entity.get(Position.class);
        var velocity = entity.get(Velocity.class);

        float rotationSpeed = 50f; // Adjust rotation speed as needed
        float thrustAcceleration = 50f; // Adjust thrust acceleration as needed

        switch (movable.facing) {
            case RotationLeft:
                position.angle -= rotationSpeed * elapsedTime;
                break;
            case RotationRight:
                position.angle += rotationSpeed * elapsedTime;
                break;
            case Up:
                // Only apply thrust when explicitly thrusting (up key)
                velocity.xVelocity += (float)Math.cos(Math.toRadians(position.angle - 90)) * thrustAcceleration * elapsedTime;
                velocity.yVelocity += (float)Math.sin(Math.toRadians(position.angle - 90)) * thrustAcceleration * elapsedTime;
                break;
            case Stopped:
                // No rotation or thrust applied
                break;
        }

        // Gravity always affects vertical velocity
        velocity.yVelocity += entity.get(Gravity.class).acceleration * elapsedTime;

        // Update position based solely on velocity
        position.x += velocity.xVelocity * elapsedTime;
        position.y += velocity.yVelocity * elapsedTime;
    }


}
