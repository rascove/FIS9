package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Zurina
 */
public class TreeStatus extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int treeStatusID;
	private String code;
	private String name;

	public int getTreeStatusID()
	{
		return treeStatusID;
	}

	public void setTreeStatusID(int treeStatusID)
	{
		this.treeStatusID = treeStatusID;
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

	@Override
	public String toString()
	{
		return code + " - " + name;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof TreeStatus)
		{
			TreeStatus that = (TreeStatus) obj;
			equal = treeStatusID == that.treeStatusID;
		}

		return equal;
	}
}