package skillapi.impl;

import io.darkcraft.darkcore.mod.DarkcoreMod;
import io.darkcraft.darkcore.mod.interfaces.IDataPacketHandler;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import skillapi.impl.data.SkillHandler;

public class SkillAPIPacketHandler implements IDataPacketHandler
{
	public static final String discriminator = "sapi.data";
	public static void register()
	{
		DarkcoreMod.packetHandler.registerHandler(discriminator, new SkillAPIPacketHandler());
	}

	@Override
	public void handleData(NBTTagCompound data)
	{
		String uuidStr = data.getString("uuid");
		UUID uuid = UUID.fromString(uuidStr);
		List<Entity> list = Minecraft.getMinecraft().theWorld.loadedEntityList;
		for(Entity ent : list)
			if(ent.getUniqueID().equals(uuid) && (ent instanceof EntityLivingBase))
				((SkillHandler) SkillAPI.i.getSkillHandler((EntityLivingBase) ent)).loadNBTData(data);
	}

}
