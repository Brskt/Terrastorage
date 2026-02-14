package me.timvinci.terrastorage.integration;

// TODO: Uncomment when Expanded Storage is updated for 1.21.11 (mod archived as of Feb 2026)
// import compasses.expandedstorage.api.EsChestType;
// import compasses.expandedstorage.api.ExpandedStorageAccessors;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Compatibility helper for Expanded Storage.
 * This class is only loaded when Expanded Storage is present,
 * avoiding ClassNotFoundException when the mod is absent.
 *
 * Currently disabled: Expanded Storage is archived and has no 1.21.11 version.
 * When the mod is updated, uncomment the imports and method body below.
 */
public class ExpandedStorageCompat {

    /**
     * Handles double chest detection and inventory creation for Expanded Storage chests.
     * @return A Pair of the combined inventory and center position, or null if it's a single chest.
     */
    public static Pair<Inventory, Vec3d> handleExpandedStorageChest(World world, BlockState state, BlockPos pos, Vec3d losPoint) {
        // TODO: Uncomment when Expanded Storage is updated for 1.21.11
        /*
        Optional<EsChestType> chestType = ExpandedStorageAccessors.getChestType(state);
        if (chestType.isEmpty() || chestType.get() == EsChestType.SINGLE) {
            return null;
        }

        BlockPos neighboringChestPos = pos.offset(ExpandedStorageAccessors.getAttachedChestDirection(state).get());
        Vec3d secondChestCenter = neighboringChestPos.toCenterPos();
        Vec3d doubleChestLosPoint = losPoint.add(secondChestCenter.x, losPoint.y, secondChestCenter.z).multiply(0.5);
        Inventory neighboringChestInventory = (Inventory) world.getBlockEntity(neighboringChestPos);

        DoubleInventory doubleInventory = chestType.get() == EsChestType.RIGHT ?
                new DoubleInventory((Inventory) world.getBlockEntity(pos), neighboringChestInventory) :
                new DoubleInventory(neighboringChestInventory, (Inventory) world.getBlockEntity(pos));

        return new Pair<>(doubleInventory, doubleChestLosPoint);
        */
        return null;
    }
}
