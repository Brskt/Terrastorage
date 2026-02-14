package me.timvinci.terrastorage.integration;

import me.timvinci.terrastorage.util.StorageAction;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.List;

/**
 * Interface for external inventory providers (backpacks, pouches, etc.).
 * Implement this interface to add Terrastorage compatibility with a new
 * inventory-providing mod.
 */
public interface ExternalInventoryProvider {
    /**
     * Checks whether this external inventory is available in the given screen handler.
     * @param handler The screen handler to check.
     * @return True if this provider's inventory is present and accessible.
     */
    boolean isAvailable(ScreenHandler handler);

    /**
     * Gets the external inventory from the screen handler.
     * @param handler The screen handler containing the external inventory slots.
     * @return An Inventory wrapping the external slots, or null if unavailable.
     */
    Inventory getInventory(ScreenHandler handler);

    /**
     * Gets the raw slots backing the external inventory.
     * @param handler The screen handler.
     * @return A list of slots belonging to the external inventory.
     */
    List<Slot> getSlots(ScreenHandler handler);

    /**
     * Returns the storage actions supported by this external inventory.
     * @return An array of supported StorageAction values.
     */
    StorageAction[] getSupportedActions();
}
