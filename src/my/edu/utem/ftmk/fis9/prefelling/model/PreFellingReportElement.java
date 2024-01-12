package my.edu.utem.ftmk.fis9.prefelling.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class PreFellingReportElement extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private String group;
	private String subgroup;
	private String criteria;
	private double[] values;
	
	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}

	public String getSubgroup()
	{
		return subgroup;
	}

	public void setSubgroup(String subgroup)
	{
		this.subgroup = subgroup;
	}

	public String getCriteria()
	{
		return criteria;
	}

	public void setCriteria(String criteria)
	{
		this.criteria = criteria;
	}

	public double[] getValues()
	{
		return values;
	}

	public void setValues(double[] values)
	{
		this.values = values;
	}

	@Override
	public String toString()
	{
		return "";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return false;
	}
}