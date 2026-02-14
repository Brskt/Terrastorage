package me.timvinci.terrastorage.render;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Utility class for nametag position calculations.
 * The actual rendering is handled by BlockEntityRenderDispatcherMixin via submitLabel().
 */
public class NametagRenderer {

    /**
     * Calculates the nametag position offset from the block entity's block position.
     * If the block above is air, the nametag is placed at the center of the block + 1 block up.
     * If the block above is solid, the nametag is shifted towards the camera instead.
     * @param entityPos The block position of the block entity.
     * @param world The world for checking if the block above is air.
     * @param cameraPos The camera position for calculating the direction towards the player.
     * @return The offset vector from the block position to the nametag position.
     */
    public static Vec3d getNametagOffset(BlockPos entityPos, World world, Vec3d cameraPos) {
        Vec3d center = new Vec3d(0.5, 0.5, 0.5);

        if (!world.isAir(entityPos.up())) {
            // Block above is solid: shift nametag towards the camera.
            Vec3d entityCenter = Vec3d.ofCenter(entityPos);
            Vec3d direction = cameraPos.subtract(entityCenter).normalize();
            return center.add(direction);
        } else {
            // Block above is air: place nametag one block above.
            return center.add(0, 1, 0);
        }
    }
}
