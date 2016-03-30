package skillapi.impl.data;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import skillapi.api.internal.ISkillHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SkillHandlerFactory
{
	public ISkillHandler getSkillHandler(EntityLivingBase ent)
	{
		if(ent != null)
		{
			IExtendedEntityProperties props = ent.getExtendedProperties(SkillHandler.disc);
			if(!(props instanceof SkillHandler))
			{
				props = new SkillHandler(ent);
				ent.registerExtendedProperties(SkillHandler.disc, props);
			}
			return (SkillHandler) props;
		}
		return null;
	}

	@SubscribeEvent
	public void entityConstruction(EntityConstructing event)
	{
		Entity ent = event.entity;
		if(ent instanceof EntityLivingBase)
		{
			SkillHandler handler = new SkillHandler((EntityLivingBase) ent);
			ent.registerExtendedProperties(SkillHandler.disc, handler);
			handler.queueUpdate();
		}
	}
}
