package me.timvinci.terrastorage.integration;

import net.minecraft.screen.ScreenHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry for external inventory providers.
 * Providers are registered at mod initialization and queried at runtime
 * to determine which (if any) external inventory is available.
 */
public class ExternalInventoryManager {
    private static final List<ExternalInventoryProvider> providers = new ArrayList<>();

    /**
     * Registers an external inventory provider.
     * @param provider The provider to register.
     */
    public static void register(ExternalInventoryProvider provider) {
        providers.add(provider);
    }

    /**
     * Finds the first registered provider that claims availability for the given screen handler.
     * @param handler The screen handler to check.
     * @return The matching provider, or null if none matches.
     */
    public static ExternalInventoryProvider findProvider(ScreenHandler handler) {
        for (ExternalInventoryProvider provider : providers) {
            if (provider.isAvailable(handler)) {
                return provider;
            }
        }
        return null;
    }
}
