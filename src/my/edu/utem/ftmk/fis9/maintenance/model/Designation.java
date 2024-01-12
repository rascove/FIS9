package my.edu.utem.ftmk.fis9.maintenance.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Designation extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int designationID;
	private String name;
	private ArrayList<Form> forms;

	public int getDesignationID()
	{
		return designationID;
	}

	public void setDesignationID(int designationID)
	{
		this.designationID = designationID;
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

		if (obj instanceof Designation)
		{
			Designation that = (Designation) obj;
			equal = designationID == that.designationID;
		}

		return equal;
	}
}