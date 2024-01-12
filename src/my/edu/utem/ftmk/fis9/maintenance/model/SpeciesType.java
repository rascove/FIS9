package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class SpeciesType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int speciesTypeID;
	private String name;

	public int getSpeciesTypeID()
	{
		return speciesTypeID;
	}

	public void setSpeciesTypeID(int speciesTypeID)
	{
		this.speciesTypeID = speciesTypeID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

		if (obj instanceof SpeciesType)
		{
			SpeciesType that = (SpeciesType) obj;
			equal = speciesTypeID == that.speciesTypeID;
		}

		return equal;
	}
}