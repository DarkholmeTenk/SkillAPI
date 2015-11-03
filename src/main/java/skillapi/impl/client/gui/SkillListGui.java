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
	private boolean over = false;
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
		int i = 0;
		for(i = scrollPos; (i < skillList.size()) && (y < (mY + 100)); i++)
		{
			ISkill skill = skillList.get(i);
			ISkillIcon icon = skill.getIcon(handler);

			String name = StatCollector.translateToLocal(skill.getName());
			String desc = StatCollector.translateToLocal(skill.getDescription());

			if((icon == null) || (icon.getResourceLocation() == null))
				icon = defaultIcon;
			bindTexture(icon.getResourceLocation());
			drawRect(x,y,x+25,y+25, icon.u(),icon.v(), icon.U(),icon.V());
			int sideX = Math.max(fr.drawString(name, x+35, y += 3, 0, false)+15,mX-15);

			String lvlStr = "Lvl: " + handler.getLevel(skill) + "/"+skill.getMaximumSkillLevel(handler);
			String xpStr = String.format("XP: %.0f/%.0f", handler.getXP(skill),handler.getXPForNextLevel(skill));
			sideX = Math.max(fr.drawString(lvlStr,sideX, y, 0, false)+15,mX + 10);
			sideX = fr.drawString(xpStr, sideX, y, 0, false);

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
		over = i < (skillList.size());
	}

	@Override
	protected void mouseClicked(int x, int y, int button)
    {
		if(button != 0)
			return;
		System.out.println("X:" + x + "	Y: " + y);
		int mX = width / 2;
		int mY = height / 2;
		if((x >= (mX + 184)) && (x <= (mX + 200)))
		{
			if((y >= (mY + 134)) && (y <= (mY + 150)))
			{
				if(over)
				{
					scrollPos++;
					over = false;
				}
			}
			else if((y <= (mY - 134)) && (y >= (mY - 150)))
			{
				if(scrollPos > 0)
					scrollPos--;
			}

		}
    }
}
