package me.timvinci.terrastorage.keybinding;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

/**
 * Holds the keybindings used by Terrastorage, and a method for registering them.
 */
public class TerrastorageKeybindings {
    public static KeyBinding favoriteItemModifier;
    public static KeyBinding sortInventoryBind;

    private static final KeyBinding.Category MAIN_CATEGORY = KeyBinding.Category.create(Identifier.of("terrastorage", "main"));

    public static void registerKeybindings() {
        favoriteItemModifier = new KeyBinding(
            "terrastorage.keybinding.favorite_item_modifier",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                MAIN_CATEGORY
        );

        KeyBindingHelper.registerKeyBinding(favoriteItemModifier);

        sortInventoryBind = new KeyBinding(
                "terrastorage.keybinding.sort_inventory_bind",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                MAIN_CATEGORY
        );

        KeyBindingHelper.registerKeyBinding(sortInventoryBind);
    }
}
