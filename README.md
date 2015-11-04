# SkillAPI

SkillAPI is a mod which provides an interface to track player "skills", as well as an interface for players to view those skills, which reduces the amount of work that you would need to go through if you wanted to implement a player based skill system in your mod.

Instead of implementing a way to track and display player skills, you only need to get an API instance, register your skill and get an entity skill handler.

Using the entity skill handler, you can add/set/get xp, get/set levels for any skill for the entity in question.

Demo on how to use:

skillapi/demo/DemoMod.java
```java
package skillapi.demo

@Mod(modid = "SkillAPIDemo", name = "Skill API Demo", version = "0.01", dependencies = "required-after:FML; required-after:darkcore@[0.3,]; required-after:SkillAPI")
public class DemoMod
{
  public static ISkillAPI api;
  public static ISkill mySkill;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FMLInterModComms.sendMessage("SkillAPI", "register", "skillapi.demo.DemoMod.requestAPI");
	}

	public static void requestAPI(ISkillAPI apiR)
	{
		api = apiR;
		mySkill = new DemoSkill();
		mySkill = api.registerSkill(mySkill);
	}
}
```

skillapi/demo/DemoSkill.java
```java
package skillapi.demo

public class DemoSkill implements ISkill
{
  private ISkillIcon icon = new ISkillIcon(){
    private ResourceLocation rl = new ResourceLocation("skillapidemo","textures/gui/skill/icon.png");
    public ResourceLocation getResourceLocation(){return rl;}
    public double u() { return 0;}
    public double U() { return 1;}
    public double v() { return 0;}
    public double V() { return 1;}
  };
  
  public String getID(){return "DemoSkill";}
  public String getName(){ return "skillapidemo.skill.name";}
  public String getDescription(){ return "skillapidemo.skill.desc";}
  public ISkillIcon getIcon(ISkillHandler handler){ return icon;}
  public SkillVisibility getVisibility(){ return SkillVisibility.ALWAYS;}
  public int getMinimumSkillLevel(ISkillHandler handler){ return 0;}
  public int getMaximumSkillLevel(ISkillHandler handler){ return 100;}
  public double getXPForNextLevel(int currentLevel, ISkillHandler handler){ return currentLevel * 10;}
}
```

After this, to interact with an EntityLivingBase's skills, you just need to call `DemoMod.api.getSkillHandler(ent)` which returns the skill handler for ent. Using this, you can use the methods (which have full javadocs) in ISkillHandler to interact with ent's skills.
