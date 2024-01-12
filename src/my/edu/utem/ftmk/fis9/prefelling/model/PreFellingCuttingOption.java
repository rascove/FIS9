package my.edu.utem.ftmk.fis9.prefelling.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.maintenance.model.CuttingOption;

/**
 * @author Satrya Fajri Pratama
 */
public class PreFellingCuttingOption extends CuttingOption
{
	private static final long serialVersionUID = VERSION;
	private long preFellingSurveyID;
	private double relativeDipCount;
	private double relativeNonDipCount;
	private double relativeDipNetVolume;
	private double relativeNonDipNetVolume;
	private double relativeDipNetCountAll;
	private double relativeDipNetCountPartial;
	private double relativeNonDipNetCountAll;
	private double relativeNonDipNetCountPartial;
	private double relativeTotalCount;
	private double relativeTotalCountRatio;
	private double relativeTotalNetVolume;
	private double relativeTotalNetCountAll;
	private double relativeTotalNetCountPartial;
	private double relativeTotalNetCountAllRatio;
	private double relativeTotalNetCountPartialRatio;
	private double originalStandRatio;
	private int rank;

	public long getPreFellingSurveyID()
	{
		return preFellingSurveyID;
	}

	public void setPreFellingSurveyID(long preFellingSurveyID)
	{
		this.preFellingSurveyID = preFellingSurveyID;
	}

	public double getRelativeDipCount()
	{
		return relativeDipCount;
	}

	public void setRelativeDipCount(double relativeDipCount)
	{
		this.relativeDipCount = relativeDipCount;
	}

	public double getRelativeNonDipCount()
	{
		return relativeNonDipCount;
	}

	public void setRelativeNonDipCount(double relativeNonDipCount)
	{
		this.relativeNonDipCount = relativeNonDipCount;
	}

	public double getRelativeDipNetVolume()
	{
		return relativeDipNetVolume;
	}

	public void setRelativeDipNetVolume(double relativeDipNetVolume)
	{
		this.relativeDipNetVolume = relativeDipNetVolume;
	}

	public double getRelativeNonDipNetVolume()
	{
		return relativeNonDipNetVolume;
	}

	public void setRelativeNonDipNetVolume(double relativeNonDipNetVolume)
	{
		this.relativeNonDipNetVolume = relativeNonDipNetVolume;
	}

	public double getRelativeDipNetCountAll()
	{
		return relativeDipNetCountAll;
	}

	public void setRelativeDipNetCountAll(double relativeDipNetCountAll)
	{
		this.relativeDipNetCountAll = relativeDipNetCountAll;
	}

	public double getRelativeDipNetCountPartial()
	{
		return relativeDipNetCountPartial;
	}

	public void setRelativeDipNetCountPartial(double relativeDipNetCountPartial)
	{
		this.relativeDipNetCountPartial = relativeDipNetCountPartial;
	}

	public double getRelativeNonDipNetCountAll()
	{
		return relativeNonDipNetCountAll;
	}

	public void setRelativeNonDipNetCountAll(double relativeNonDipNetCountAll)
	{
		this.relativeNonDipNetCountAll = relativeNonDipNetCountAll;
	}

	public double getRelativeNonDipNetCountPartial()
	{
		return relativeNonDipNetCountPartial;
	}

	public void setRelativeNonDipNetCountPartial(double relativeNonDipNetCountPartial)
	{
		this.relativeNonDipNetCountPartial = relativeNonDipNetCountPartial;
	}

	public final double getRelativeTotalCount()
	{
		return relativeTotalCount;
	}

	public final void setRelativeTotalCount(double relativeTotalCount)
	{
		this.relativeTotalCount = relativeTotalCount;
	}

	public final double getRelativeTotalCountRatio()
	{
		return relativeTotalCountRatio;
	}

	public final void setRelativeTotalCountRatio(double relativeTotalCountRatio)
	{
		this.relativeTotalCountRatio = relativeTotalCountRatio;
	}

	public final double getRelativeTotalNetVolume()
	{
		return relativeTotalNetVolume;
	}

	public final void setRelativeTotalNetVolume(double relativeTotalNetVolume)
	{
		this.relativeTotalNetVolume = relativeTotalNetVolume;
	}

	public final double getRelativeTotalNetCountAll()
	{
		return relativeTotalNetCountAll;
	}

	public final void setRelativeTotalNetCountAll(double relativeTotalNetCountAll)
	{
		this.relativeTotalNetCountAll = relativeTotalNetCountAll;
	}

	public final double getRelativeTotalNetCountPartial()
	{
		return relativeTotalNetCountPartial;
	}

	public final void setRelativeTotalNetCountPartial(double relativeTotalNetCountPartial)
	{
		this.relativeTotalNetCountPartial = relativeTotalNetCountPartial;
	}


	public final double getRelativeTotalNetCountAllRatio()
	{
		return relativeTotalNetCountAllRatio;
	}

	public final void setRelativeTotalNetCountAllRatio(double relativeTotalNetCountAllRatio)
	{
		this.relativeTotalNetCountAllRatio = relativeTotalNetCountAllRatio;
	}

	public final double getRelativeTotalNetCountPartialRatio()
	{
		return relativeTotalNetCountPartialRatio;
	}

	public final void setRelativeTotalNetCountPartialRatio(double relativeTotalNetCountPartialRatio)
	{
		this.relativeTotalNetCountPartialRatio = relativeTotalNetCountPartialRatio;
	}

	public double getOriginalStandRatio()
	{
		return originalStandRatio;
	}

	public void setOriginalStandRatio(double originalStandRatio)
	{
		this.originalStandRatio = originalStandRatio;
	}

	public int getRank()
	{
		return rank;
	}

	public void setRank(int rank)
	{
		this.rank = rank;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof PreFellingCuttingOption)
		{
			PreFellingCuttingOption that = (PreFellingCuttingOption) obj;
			equal = getCuttingOptionID() == that.getCuttingOptionID() && preFellingSurveyID == that.preFellingSurveyID;
		}

		return equal;
	}

	@Override
	public int compareTo(AbstractModel model)
	{
		PreFellingCuttingOption that = (PreFellingCuttingOption) model;
		int status = 0;

		if (rank != 0)
		{
			if (that.rank == 0)
				status = -1;
			else
			{
				if (rank < that.rank)
					status = -1;
				else if (rank > that.rank)
					status = 1;
			}
		}
		else
		{
			if (that.rank == 0)
				super.compareTo(model);
			else
				status = 1;
		}
		
		return status;
	}
}