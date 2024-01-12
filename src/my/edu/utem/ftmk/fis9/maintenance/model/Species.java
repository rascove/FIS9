package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class Species extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int speciesID;
	private String code;
	private String name;
	private String scientificName;
	private String symbol;
	private int speciesTypeID;
	private int timberGroupID;
	private int timberTypeID;
	private String speciesTypeName;
	private String timberGroupName;
	private String timberTypeName;

	public int getSpeciesID()
	{
		return speciesID;
	}

	public void setSpeciesID(int speciesID)
	{
		this.speciesID = speciesID;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getScientificName()
	{
		return scientificName;
	}

	public void setScientificName(String scientificName)
	{
		this.scientificName = scientificName;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}

	public int getSpeciesTypeID()
	{
		return speciesTypeID;
	}

	public void setSpeciesTypeID(int speciesTypeID)
	{
		this.speciesTypeID = speciesTypeID;
	}

	public int getTimberGroupID()
	{
		return timberGroupID;
	}

	public void setTimberGroupID(int timberGroupID)
	{
		this.timberGroupID = timberGroupID;
	}

	public int getTimberTypeID()
	{
		return timberTypeID;
	}

	public void setTimberTypeID(int timberTypeID)
	{
		this.timberTypeID = timberTypeID;
	}

	public String getSpeciesTypeName()
	{
		return speciesTypeName;
	}

	public void setSpeciesTypeName(String speciesTypeName)
	{
		this.speciesTypeName = speciesTypeName;
	}

	public String getTimberGroupName()
	{
		return timberGroupName;
	}

	public void setTimberGroupName(String timberGroupName)
	{
		this.timberGroupName = timberGroupName;
	}

	public String getTimberTypeName()
	{
		return timberTypeName;
	}

	public void setTimberTypeName(String timberTypeName)
	{
		this.timberTypeName = timberTypeName;
	}

	@Override
	public String toString()
	{
		return (code == null ? "" : (code + " - ")) + name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof Species)
		{
			Species that = (Species) obj;
			equal = speciesID == that.speciesID;
		}

		return equal;
	}

	@Override
	public int compareTo(AbstractModel model)
	{
		Species that = (Species) model;
		int status = 0;

		if (code == null && that.code == null || code != null && that.code != null)
			status = toString().compareTo(that.toString());
		else
		{
			if (code != null)
				return -1;
			else
				return 1;
		}

		return status;
	}
}