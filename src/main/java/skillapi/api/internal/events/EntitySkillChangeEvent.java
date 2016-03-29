package skillapi.api.internal.events;

import net.minecraft.entity.EntityLivingBase;
import skillapi.api.implement.ISkill;
import cpw.mods.fml.common.eventhandler.Event;

public class EntitySkillChangeEvent extends Event
{
	public final EntityLivingBase ent;
	public final ISkill skill;
	public final int oldLevel;
	public final int newLevel;

	public EntitySkillChangeEvent(EntityLivingBase _ent, ISkill _skill, int ol, int nl)
	{
		ent = _ent;
		skill = _skill;
		oldLevel = ol;
		newLevel = nl;
	}
}
