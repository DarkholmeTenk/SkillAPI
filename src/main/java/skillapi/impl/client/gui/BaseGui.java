package skillapi.impl.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class BaseGui extends GuiScreen
{
	/**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawRect(int x, int y, int X, int Y, int u, int v, int U, int V)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, Y, zLevel, u, V);
        tessellator.addVertexWithUV(X, Y, zLevel, U, V);
        tessellator.addVertexWithUV(X, y, zLevel, U, v);
        tessellator.addVertexWithUV(x, y, zLevel, u, v);
        tessellator.draw();
    }

    public void bindTexture(ResourceLocation rl)
    {
    	Minecraft.getMinecraft().renderEngine.bindTexture(rl);
    }
}
