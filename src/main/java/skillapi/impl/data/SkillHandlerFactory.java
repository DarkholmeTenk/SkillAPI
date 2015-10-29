package skillapi.impl.data;

import java.util.WeakHashMap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.IExtendedEntityProperties;
import skillapi.api.internal.IEntitySkillHandler;

public class SkillHandlerFactory
{
	private WeakHashMap<EntityLivingBase,EntitySkillHandler> entMap = new WeakHashMap();

	private static final String epid = "SkillApiEPID";
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
}
