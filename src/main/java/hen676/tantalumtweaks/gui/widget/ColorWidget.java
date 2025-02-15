package hen676.tantalumtweaks.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.function.Supplier;

/**
 * Colour widget to display currently configured colour
 */
@Environment(EnvType.CLIENT)
public class ColorWidget extends ClickableWidget {
    private int x;
    private int y;
    private final int width;
    private final int height;
    final Supplier<Integer> getColor;

    public ColorWidget(int width, int height, Supplier<Integer> getColor) {
        this(0, 0, width, height, getColor);
    }

    public ColorWidget(int x, int y, int width, int height, Supplier<Integer> getColor) {
        super(x, y, width, height, Text.empty());
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.getColor = getColor;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) { }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int x2 = this.x + this.width;
        int y2 = this.y + this.height;
        context.fill(this.x, this.y, x2, y2, Color.BLACK.getRGB());
        context.fill(this.x + 1, this.y + 1, x2 - 1, y2 - 1, this.getColor.get());
    }

}
