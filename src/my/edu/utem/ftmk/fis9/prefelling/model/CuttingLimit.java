package my.edu.utem.ftmk.fis9.prefelling.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

/**
 * @author Satrya Fajri Pratama
 */
public class CuttingLimit extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private double minDiameter;
	private long preFellingSurveyID;
	private int speciesID;
	private int speciesTypeID;
	private String speciesName;

	public double getMinDiameter()
	{
		return minDiameter;
	}

	public void setMinDiameter(double minDiameter)
	{
		this.minDiameter = minDiameter;
	}

	public long getPreFellingSurveyID()
	{
		return preFellingSurveyID;
	}

	public void setPreFellingSurveyID(long preFellingSurveyID)
	{
		this.preFellingSurveyID = preFellingSurveyID;
	}

	public int getSpeciesID()
	{
		return speciesID;
	}

	public void setSpeciesID(int speciesID)
	{
		this.speciesID = speciesID;
	}

	public int getSpeciesTypeID()
	{
		return speciesTypeID;
	}

	public void setSpeciesTypeID(int speciesTypeID)
	{
		this.speciesTypeID = speciesTypeID;
	}

	public String getSpeciesName()
	{
		return speciesName;
	}

	public void setSpeciesName(String speciesName)
	{
		this.speciesName = speciesName;
	}

	@Override
	public String toString()
	{
		return "Had tebangan " + speciesName + ": " + minDiameter + " cm.";
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof CuttingLimit)
		{
			CuttingLimit that = (CuttingLimit) obj;
			equal = preFellingSurveyID == that.preFellingSurveyID && speciesID == that.speciesID;
		}

		return equal;
	}
}