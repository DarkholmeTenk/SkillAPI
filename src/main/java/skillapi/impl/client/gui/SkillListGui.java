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

	private int boxWidth = 400;
	private int boxHeight = 300;
	private int sortSwitch = 0;
	private boolean showDesc = true;

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
		drawRect(mX - (boxWidth/2),mY - (boxHeight/2), mX + (boxWidth/2),mY + (boxHeight/2), 0,0, 1,1);
		drawSkills();
	}

	private void drawSkills()
	{
		int mX = width / 2;
		int mY = height / 2;
		int offset = 25;
		int x = (mX - (boxWidth/2)) + offset;
		int y = (mY - (boxHeight/2)) + offset;
		FontRenderer fr = fontRendererObj;
		int i = 0;
		for(i = scrollPos; (i < skillList.size()) && (y < ((mY + (boxHeight/2)) - (2*offset))); i++)
		{
			int bY = y;
			ISkill skill = skillList.get(i);
			ISkillIcon icon = skill.getIcon(handler);

			String name = StatCollector.translateToLocal(skill.getName());
			String desc = StatCollector.translateToLocal(skill.getDescription());

			if((icon == null) || (icon.getResourceLocation() == null))
				icon = defaultIcon;
			bindTexture(icon.getResourceLocation());
			int iconSize = showDesc ? 25 : 16;
			drawRect(x,y,x+iconSize,y+iconSize, icon.u(),icon.v(), icon.U(),icon.V());
			int sideX = Math.max(fr.drawString(name, x+iconSize+10, y += showDesc ? 3 : 4, 0, false)+15,mX-15);

			String lvlStr = "Lvl: " + handler.getLevel(skill) + "/"+skill.getMaximumSkillLevel(handler);
			String xpStr = String.format("XP: %.0f/%.0f", handler.getXP(skill),handler.getXPForNextLevel(skill));
			sideX = Math.max(fr.drawString(lvlStr,sideX, y, 0, false)+15,mX + 10);
			sideX = fr.drawString(xpStr, sideX, y, 0, false);

			y+=4;
			if(showDesc)
			{
				List<String> descLines = fr.listFormattedStringToWidth(desc, (boxWidth * 3) / 4);
				for(int j = 0; j <descLines.size(); j++)
				{
					String s = descLines.get(j);
					fr.drawString(s, j==0?x+35: x + 40, y += 10, 0, false);
				}
			}
			y = Math.max(y+15,bY + (showDesc ? 37 : 15));
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
		int buttonSize = 16;

		Boolean xB = null;
		Boolean yB = null;
		if((x >= ((mX + (boxWidth/2)) - buttonSize)) && (x <= (mX + (boxWidth/2))))
			xB = true;
		else if((x <= ((mX - (boxWidth/2)) + buttonSize)) && (x >= (mX - (boxWidth/2))))
			xB = false;
		if((y >= ((mY + (boxHeight/2)) - buttonSize)) && (y <= (mY + (boxHeight / 2))))
			yB = true;
		else if((y <= ((mY - (boxHeight/2)) + buttonSize)) && (y >= (mY - (boxHeight/2))))
			yB = false;
		if((xB == null) || (yB == null)) return;
		if(xB)
		{
			if(yB && over)
			{
				scrollPos++;
				over = false;
			}
			else if(!yB && (scrollPos > 0))
					scrollPos--;
		}
		else
		{
			if(yB)
			{
				sortSwitch = (sortSwitch+1) % 3;
				switch(sortSwitch)
				{
					case 0: Collections.sort(skillList, SkillSortContainer.nameSorterAsc); break;
					case 1: Collections.sort(skillList, SkillSortContainer.nameSorterDesc); break;
					case 2: Collections.sort(skillList, SkillSortContainer.idSorterAsc); break;
				}
			}
			else
				showDesc = !showDesc;
		}
    }
}
