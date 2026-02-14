package me.timvinci.terrastorage.mixin.client;

import me.timvinci.terrastorage.render.NametagRenderer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRenderManager;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A mixin of the BlockEntityRenderManager class, adds nametag rendering for lootable container
 * block entities that have a custom name and are being targeted by the player's crosshair.
 */
@Mixin(BlockEntityRenderManager.class)
public class BlockEntityRenderDispatcherMixin {

    /**
     * Adds nametag rendering after a block entity has been rendered.
     * This covers all block entity types: those with vanilla renderers (chests, shulker boxes)
     * and those with the marker BlockNametagRenderer (barrels, hoppers, etc.).
     */
    @Inject(method = "render", at = @At("TAIL"))
    private <S extends BlockEntityRenderState> void afterRender(
            S renderState, MatrixStack matrixStack,
            OrderedRenderCommandQueue commandQueue,
            CameraRenderState cameraRenderState, CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        BlockEntity blockEntity = client.world.getBlockEntity(renderState.pos);
        if (!(blockEntity instanceof LootableContainerBlockEntity lootable)) return;
        if (!lootable.hasCustomName()) return;

        // Check if the player is targeting this block entity.
        if (!(client.crosshairTarget instanceof BlockHitResult blockHitResult)) return;
        if (!blockHitResult.getBlockPos().equals(renderState.pos)) return;

        // Calculate the nametag position offset from the block entity position.
        Vec3d nametagOffset = NametagRenderer.getNametagOffset(
                renderState.pos, client.world, cameraRenderState.pos);

        // Submit the nametag label to the render command queue.
        commandQueue.submitLabel(matrixStack, nametagOffset,
                0xFFFFFFFF, lootable.getCustomName(), true,
                64, 0.025, cameraRenderState);
    }
}
