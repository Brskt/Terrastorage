package me.timvinci.terrastorage.integration;

import me.timvinci.terrastorage.inventory.SlotBackedInventory;
import me.timvinci.terrastorage.util.StorageAction;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Compatibility layer for Blankpack Fabricated.
 * Uses reflection to avoid compile-time dependency on the mod.
 * Blankpack injects BackpackMenu interface into all ScreenHandlers via mixin,
 * so the methods are available at runtime when the mod is installed.
 */
public class BlankpackCompat implements ExternalInventoryProvider {
    private static final String BACKPACK_STORAGE_SLOT_CLASS = "com.yyz.yyzsbackpack.base.BackpackStorageSlot";

    private static Class<?> backpackSlotClass;
    private static Method isBackpackVisibleMethod;
    private static boolean reflectionInitialized = false;
    private static boolean reflectionFailed = false;

    /**
     * Initializes cached reflection lookups.
     */
    private static void initReflection() {
        if (reflectionInitialized) return;
        reflectionInitialized = true;

        try {
            backpackSlotClass = Class.forName(BACKPACK_STORAGE_SLOT_CLASS);
            // BackpackMenu is injected via mixin on ScreenHandler, so the method exists on the class.
            isBackpackVisibleMethod = ScreenHandler.class.getMethod("isBackpackVisible");
        } catch (Exception e) {
            reflectionFailed = true;
        }
    }

    /**
     * Checks if the backpack is currently visible in the given screen handler.
     * @param handler The screen handler to check.
     * @return True if the backpack panel is visible, false otherwise.
     */
    private static boolean isBackpackVisible(ScreenHandler handler) {
        initReflection();
        if (reflectionFailed) return false;

        try {
            return (boolean) isBackpackVisibleMethod.invoke(handler);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isAvailable(ScreenHandler handler) {
        return handler instanceof PlayerScreenHandler && isBackpackVisible(handler);
    }

    @Override
    public Inventory getInventory(ScreenHandler handler) {
        List<Slot> backpackSlots = getSlots(handler);
        if (backpackSlots.isEmpty()) {
            return null;
        }
        return new SlotBackedInventory(backpackSlots);
    }

    @Override
    public List<Slot> getSlots(ScreenHandler handler) {
        initReflection();
        if (reflectionFailed || backpackSlotClass == null) return Collections.emptyList();

        return handler.slots.stream()
                .filter(slot -> backpackSlotClass.isInstance(slot))
                .toList();
    }

    @Override
    public StorageAction[] getSupportedActions() {
        StorageAction[] allActions = StorageAction.values();
        // Exclude RENAME and QUICK_STACK_TO_NEARBY (last two values).
        return Arrays.copyOf(allActions, allActions.length - 2);
    }
}
