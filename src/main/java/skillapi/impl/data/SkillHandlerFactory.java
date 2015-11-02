package skillapi.impl.data;

import java.util.WeakHashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import skillapi.api.internal.IEntitySkillHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SkillHandlerFactory
{
	private WeakHashMap<EntityLivingBase,EntitySkillHandler> entMap = new WeakHashMap();

	public static final String epid = "SkillApiEPID";
	public IEntitySkillHandler getSkillHandler(EntityLivingBase ent)
	{
		if(ent != null)
		{
			if(entMap.containsKey(ent))
				return entMap.get(ent);
			IExtendedEntityProperties props = ent.getExtendedProperties(epid);
			EntitySkillHandler handler;
			if(props != null)
			{
				handler = (EntitySkillHandler) props;
				entMap.put(ent, handler);
			}
			else
			{
				handler = new EntitySkillHandler(ent);
				ent.registerExtendedProperties(epid, handler);
				entMap.put(ent, handler);
			}
			return handler;
		}
		return null;
	}

	@SubscribeEvent
	public void entityConstruction(EntityConstructing event)
	{
		Entity ent = event.entity;
		if(ent instanceof EntityLivingBase)
			ent.registerExtendedProperties(epid, new EntitySkillHandler((EntityLivingBase) ent));
	}
}
