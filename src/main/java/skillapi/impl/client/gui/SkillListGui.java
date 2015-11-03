package skillapi.impl.client.gui;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import skillapi.api.implement.ISkill;
import skillapi.api.implement.ISkillIcon;
import skillapi.api.internal.ISkillHandler;
import skillapi.impl.client.SkillSortContainer;

public class SkillListGui extends BaseGui
{
	public static final int ID = 102;
	public static final ResourceLocation r = new ResourceLocation("skillapi","textures/gui/main.png");
	public static final ISkillIcon defaultIcon = new DefaultIcon();

	private ISkillHandler handler;
	private int scrollPos = 0;
	List<ISkill> skillList;

	public SkillListGui(ISkillHandler skillHandler)
	{
		handler = skillHandler;
		skillList = handler.getVisibleSkills();
		Collections.sort(skillList, SkillSortContainer.nameSorterAsc);
	}

	@Override
	public void drawScreen(int x, int y, float f)
	{
		bindTexture(r);
		int mX = width / 2;
		int mY = height / 2;
		drawRect(mX - 200,mY - 150, mX + 200,mY + 150, 0,0, 1,1);
		drawSkills();
	}

	private void drawSkills()
	{
		int mX = width / 2;
		int mY = height / 2;
		int x = mX - 175;
		int y = mY - 125;
		FontRenderer fr = fontRendererObj;
		for(int i = 0; i < skillList.size(); i++)
		{
			ISkill skill = skillList.get(i);
			ISkillIcon icon = skill.getIcon(handler);

			String name = StatCollector.translateToLocal(skill.getName());
			String desc = StatCollector.translateToLocal(skill.getDescription());

			if((icon == null) || (icon.getResourceLocation() == null))
				icon = defaultIcon;
			bindTexture(icon.getResourceLocation());
			drawRect(x,y,x+25,y+25, icon.u(),icon.v(), icon.U(),icon.V());
			int sideX = fr.drawString(name, x+35, y += 3, 0, false);
			sideX = fr.drawString("Lvl: " + handler.getLevel(skill) + "/"+skill.getMaximumSkillLevel(handler),sideX + 25, y, 0, false);
			sideX = fr.drawString(String.format("XP: %.0f/%.0f", handler.getXP(skill),handler.getXPForNextLevel(skill)), sideX + 25, y, 0, false);

			y+=4;
			List<String> descLines = fr.listFormattedStringToWidth(desc, 300);
			for(int j = 0; j <descLines.size(); j++)
			{
				String s = descLines.get(j);
				fr.drawString(s, j==0?x+35: x + 40, y += 10, 0, false);
			}
			y += 15;
			GL11.glColor3d(1, 1, 1);
		}
	}
}
