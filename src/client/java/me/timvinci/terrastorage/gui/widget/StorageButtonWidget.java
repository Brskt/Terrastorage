package me.timvinci.terrastorage.gui.widget;

import me.timvinci.terrastorage.mixin.client.PressableWidgetAccessor;
import me.timvinci.terrastorage.util.ButtonsStyle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

/**
 * A customized button widget.
 * Supports TEXT_ONLY mode (no background) and yellow text on hover (Terraria-style).
 * Custom rendering is driven by PressableWidgetMixin since renderWidget is final.
 */
public class StorageButtonWidget extends ButtonWidget {
    private ButtonsStyle buttonStyle;

    public StorageButtonWidget(int x, int y, int width, int height, net.minecraft.text.Text message, ButtonsStyle buttonStyle, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
        this.setButtonStyle(buttonStyle);
    }

    public void setButtonStyle(ButtonsStyle buttonsStyle) {
        this.buttonStyle = buttonsStyle;
    }

    public ButtonsStyle getButtonStyle() {
        return this.buttonStyle;
    }

    /**
     * No icon for text-based buttons.
     */
    @Override
    protected void drawIcon(DrawContext context, int mouseX, int mouseY, float delta) {
        // Text-only button, no icon to draw.
    }

    /**
     * Custom rendering called by PressableWidgetMixin.
     * Draws the button background only in DEFAULT style, and renders text in yellow when hovered.
     */
    public void renderCustom(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        // Draw the button background only if the style is DEFAULT.
        if (buttonStyle == ButtonsStyle.DEFAULT) {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, PressableWidgetAccessor.getTextures().get(this.active, this.isSelected()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ColorHelper.getWhite(this.alpha));
        }
        // Change the text color to yellow if the button is hovered.
        int i = this.hovered ? 16776960 : 16777215;
        context.drawCenteredTextWithShadow(minecraftClient.textRenderer, this.getMessage(), this.getX() + this.getWidth() / 2, this.getY() + (this.getHeight() - 8) / 2, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }
}
