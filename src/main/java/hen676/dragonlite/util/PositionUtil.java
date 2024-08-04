package hen676.dragonlite.util;

import net.minecraft.client.render.Camera;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PositionUtil {
    public double x;
    public double y;
    public double z;
    public float pitch;
    public float yaw;
    private final Quaternionf rotation = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
    private final Vector3f verticalPlane = new Vector3f(0.0F, 1.0F, 0.0F);
    private final Vector3f diagonalPlane = new Vector3f(1.0F, 0.0F, 0.0F);
    private final Vector3f horizontalPlane = new Vector3f(0.0F, 0.0F, 1.0F);

    public PositionUtil(Camera camera) {
        x = camera.getPos().x;
        y = camera.getPos().y;
        z = camera.getPos().z;
        setRotation(camera.getYaw(), camera.getPitch());
    }

    // From net.minecraft.client.render.Camera.setRotation
    public void setRotation(float yaw, float pitch) {
        this.pitch = pitch;
        this.yaw = yaw;
        rotation.rotationYXZ(-yaw * ((float) Math.PI / 180), pitch * ((float) Math.PI / 180), 0.0f);
        horizontalPlane.set(0.0f, 0.0f, 1.0f).rotate(rotation);
        verticalPlane.set(0.0f, 1.0f, 0.0f).rotate(rotation);
        diagonalPlane.set(1.0f, 0.0f, 0.0f).rotate(rotation);
    }
}
