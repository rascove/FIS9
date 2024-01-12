package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class TreeType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int treeTypeID;
	private String name;

	public int getTreeTypeID()
	{
		return treeTypeID;
	}

	public void setTreeTypeID(int treeTypeID)
	{
		this.treeTypeID = treeTypeID;
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

		if (obj instanceof TreeType)
		{
			TreeType that = (TreeType) obj;
			equal = treeTypeID == that.treeTypeID;
		}

		return equal;
	}
}