package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Form extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int formID;
	private String name;
	private String path;
	private String icon;
	private String category;
	private int moduleID;
	
	public int getFormID()
	{
		return formID;
	}

	public void setFormID(int formID)
	{
		this.formID = formID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}
	
	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public int getModuleID()
	{
		return moduleID;
	}

	public void setModuleID(int moduleID)
	{
		this.moduleID = moduleID;
	}

	@Override
	public String toString()
	{
		return name;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;
		
		if (obj instanceof Form)
		{
			Form that = (Form) obj;
			equal = formID == that.formID;
		}
		
		return equal;
	}
}