package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Nor Azman Mat Ariff
 */
public class ForestCategory extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int forestCategoryID;
	private String code;
	private String name;
	private String status;

	public int getForestCategoryID() 
	{
		return forestCategoryID;
	}

	public void setForestCategoryID(int forestCategoryID) 
	{
		this.forestCategoryID = forestCategoryID;
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

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
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

		if (obj instanceof ForestCategory)
		{
			ForestCategory that = (ForestCategory) obj;
			equal = forestCategoryID == that.forestCategoryID;
		}

		return equal;
	}
}