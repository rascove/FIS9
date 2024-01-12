package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Module extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int moduleID;
	private String name;
	private ArrayList<Form> forms;
	
	public int getModuleID()
	{
		return moduleID;
	}

	public void setModuleID(int moduleID)
	{
		this.moduleID = moduleID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public ArrayList<Form> getForms()
	{
		return forms;
	}

	public void setForms(ArrayList<Form> forms)
	{
		this.forms = forms;
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
		
		if (obj instanceof Module)
		{
			Module that = (Module) obj;
			equal = moduleID == that.moduleID;
		}
		
		return equal;
	}
}