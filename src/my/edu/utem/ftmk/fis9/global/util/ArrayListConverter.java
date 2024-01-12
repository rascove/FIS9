package my.edu.utem.ftmk.fis9.global.util;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

public class ArrayListConverter
{
	public static String[] asString(ArrayList<?> objects)
	{
		int size = objects.size();
		String[] temp = new String[size];
		
		for (int i = 0; i < size; i++)
			temp[i] = objects.get(i).toString();
		
		return temp;
	}
	
	public static SelectItem[] asSelectItem(ArrayList<SelectItem> items)
	{
		return items.toArray(new SelectItem[0]);
	}
}