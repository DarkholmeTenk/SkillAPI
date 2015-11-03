package skillapi.impl.client;

import java.util.Comparator;

import net.minecraft.util.StatCollector;
import skillapi.api.implement.ISkill;

public class SkillSortContainer
{
	public static Comparator<ISkill> nameSorterAsc = new Comparator<ISkill>(){
		@Override
		public int compare(ISkill arg0, ISkill arg1)
		{
			String n1 = StatCollector.translateToLocal(arg0.getName());
			String n2 = StatCollector.translateToLocal(arg1.getName());
			return n1.compareTo(n2);
		}
	};
	public static Comparator<ISkill> nameSorterDesc = new Comparator<ISkill>(){
		@Override
		public int compare(ISkill arg0, ISkill arg1)
		{
			String n1 = StatCollector.translateToLocal(arg0.getName());
			String n2 = StatCollector.translateToLocal(arg1.getName());
			return -n1.compareTo(n2);
		}
	};
	public static Comparator<ISkill> idSorterAsc = new Comparator<ISkill>(){
		@Override
		public int compare(ISkill arg0, ISkill arg1)
		{
			String n1 = arg0.getID();
			String n2 = arg1.getID();
			return n1.compareTo(n2);
		}
	};
}
