package my.edu.utem.ftmk.fis9.prefelling.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.FieldWork;

/**
 * @author Satrya Fajri Pratama
 */
public class PreFellingSurvey extends FieldWork<PreFellingSurveyCard>
{
	private static final long serialVersionUID = VERSION;
	private int cuttingOptionID;
	private boolean recommended;
	private boolean taggingCreated;
	private boolean postFellingCreated;
	private PreFellingReport preFellingReport;
	private PreFellingCuttingOption preFellingCuttingOption;

	public long getPreFellingSurveyID()
	{
		return getFieldWorkID();
	}

	public void setPreFellingSurveyID(long preFellingSurveyID)
	{
		setFieldWorkID(preFellingSurveyID);
	}

	public int getCuttingOptionID()
	{
		return cuttingOptionID;
	}

	public void setCuttingOptionID(int cuttingOptionID)
	{
		this.cuttingOptionID = cuttingOptionID;
	}

	public boolean isRecommended()
	{
		return recommended;
	}

	public void setRecommended(boolean recommended)
	{
		this.recommended = recommended;
	}

	public boolean isTaggingCreated()
	{
		return taggingCreated;
	}

	public void setTaggingCreated(boolean taggingCreated)
	{
		this.taggingCreated = taggingCreated;
	}

	public boolean isPostFellingCreated()
	{
		return postFellingCreated;
	}

	public void setPostFellingCreated(boolean postFellingCreated)
	{
		this.postFellingCreated = postFellingCreated;
	}

	public PreFellingReport getPreFellingReport()
	{
		return preFellingReport;
	}

	public void setPreFellingReport(PreFellingReport preFellingReport)
	{
		this.preFellingReport = preFellingReport;
	}

	public PreFellingCuttingOption getPreFellingCuttingOption()
	{
		return preFellingCuttingOption;
	}

	public void setPreFellingCuttingOption(PreFellingCuttingOption preFellingCuttingOption)
	{
		this.preFellingCuttingOption = preFellingCuttingOption;
	}

	public ArrayList<PreFellingSurveyCard> getPreFellingSurveyCards()
	{
		return getFieldWorksheets();
	}

	public void setPreFellingSurveyCards(ArrayList<PreFellingSurveyCard> preFellingSurveyCards)
	{
		setFieldWorksheets(preFellingSurveyCards);
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof PreFellingSurvey)
		{
			PreFellingSurvey that = (PreFellingSurvey) obj;
			equal = getPreFellingSurveyID() == that.getPreFellingSurveyID();
		}

		return equal;
	}
}