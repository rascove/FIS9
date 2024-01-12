package my.edu.utem.ftmk.fis9.maintenance.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class TaggingType extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private int taggingTypeID;
	private String name;

	public int getTaggingTypeID()
	{
		return taggingTypeID;
	}

	public void setTaggingTypeID(int taggingTypeID)
	{
		this.taggingTypeID = taggingTypeID;
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

		if (obj instanceof TaggingType)
		{
			TaggingType that = (TaggingType) obj;
			equal = taggingTypeID == that.taggingTypeID;
		}

		return equal;
	}
}