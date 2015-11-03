package skillapi.impl.data;

import io.darkcraft.darkcore.mod.helpers.ServerHelper;

import java.util.WeakHashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import skillapi.api.internal.ISkillHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SkillHandlerFactory
{
	private WeakHashMap<EntityLivingBase,SkillHandler> entMap = new WeakHashMap();

	public static final String epid = "SkillApiEPID";
	public ISkillHandler getSkillHandler(EntityLivingBase ent)
	{
		if(ent != null)
		{
			if(entMap.containsKey(ent))
				return entMap.get(ent);
			IExtendedEntityProperties props = ent.getExtendedProperties(epid);
			SkillHandler handler;
			if(props != null)
			{
				handler = (SkillHandler) props;
				entMap.put(ent, handler);
			}
			else
			{
				handler = new SkillHandler(ent);
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
		if((ent instanceof EntityLivingBase) && ServerHelper.isServer())
		{
			SkillHandler handler = new SkillHandler((EntityLivingBase) ent);
			ent.registerExtendedProperties(epid, handler);
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayerMP)
		{
			EntityPlayerMP pl = (EntityPlayerMP) (event.entity);
			SkillHandler handler = (SkillHandler) getSkillHandler(pl);
			handler.sync();
		}
	}
}
