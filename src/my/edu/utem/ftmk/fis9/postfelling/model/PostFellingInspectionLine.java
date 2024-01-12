package my.edu.utem.ftmk.fis9.postfelling.model;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;

public class PostFellingInspectionLine extends AbstractModel
{

	private static final long serialVersionUID = VERSION;	
	private long postFellingSurveyID;
	private long postFellingInspectionLineID;
	private String lineNo;
	private double lineBefore;
	private double lineAfter;
	private boolean startPole;
	private boolean endPole;
	private int bearingError;
	private int plotError;
	private boolean stake;
	private boolean steepness;
	private boolean boundarySignageStart;
	private boolean boundaryCleanStart;
	private boolean boundarySignageEnd;
	private boolean boundaryCleanEnd;

	public long getPostFellingSurveyID()
	{
		return postFellingSurveyID;
	}

	public void setPostFellingSurveyID(long postFellingSurveyID)
	{
		this.postFellingSurveyID = postFellingSurveyID;
	}

	
	public long getPostFellingInspectionLineID()
	{
		return postFellingInspectionLineID;
	}

	public void setPostFellingInspectionLineID(long postFellingInspectionLineID)
	{
		this.postFellingInspectionLineID = postFellingInspectionLineID;
	}

	public String getLineNo()
	{
		return lineNo;
	}

	public void setLineNo(String lineNo)
	{
		this.lineNo = lineNo;
	}

	public double getLineBefore()
	{
		return lineBefore;
	}

	public void setLineBefore(double lineBefore)
	{
		this.lineBefore = lineBefore;
	}

	public double getLineAfter()
	{
		return lineAfter;
	}

	public void setLineAfter(double lineAfter)
	{
		this.lineAfter = lineAfter;
	}

	public boolean isStartPole()
	{
		return startPole;
	}

	public void setStartPole(boolean startPole)
	{
		this.startPole = startPole;
	}

	public boolean isEndPole()
	{
		return endPole;
	}

	public void setEndPole(boolean endPole)
	{
		this.endPole = endPole;
	}

	public int getBearingError()
	{
		return bearingError;
	}

	public void setBearingError(int bearingError)
	{
		this.bearingError = bearingError;
	}

	public int getPlotError()
	{
		return plotError;
	}

	public void setPlotError(int plotError)
	{
		this.plotError = plotError;
	}

	public boolean isStake()
	{
		return stake;
	}

	public void setStake(boolean stake)
	{
		this.stake = stake;
	}

	public boolean isSteepness()
	{
		return steepness;
	}

	public void setSteepness(boolean steepness)
	{
		this.steepness = steepness;
	}

	public boolean isBoundarySignageStart()
	{
		return boundarySignageStart;
	}

	public void setBoundarySignageStart(boolean boundarySignageStart)
	{
		this.boundarySignageStart = boundarySignageStart;
	}

	public boolean isBoundaryCleanStart()
	{
		return boundaryCleanStart;
	}

	public void setBoundaryCleanStart(boolean boundaryCleanStart)
	{
		this.boundaryCleanStart = boundaryCleanStart;
	}

	public boolean isBoundarySignageEnd()
	{
		return boundarySignageEnd;
	}

	public void setBoundarySignageEnd(boolean boundarySignageEnd)
	{
		this.boundarySignageEnd = boundarySignageEnd;
	}

	public boolean isBoundaryCleanEnd()
	{
		return boundaryCleanEnd;
	}

	public void setBoundaryCleanEnd(boolean boundaryCleanEnd)
	{
		this.boundaryCleanEnd = boundaryCleanEnd;
	}

	@Override
	public String toString()
	{
		return null;
	}

	@Override
	public boolean equals(Object obj)
	{
		return false;
	}
	
	

}
