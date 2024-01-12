package my.edu.utem.ftmk.fis9.tagging.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.FieldWork;
import my.edu.utem.ftmk.fis9.maintenance.model.Hammer;

/**
 * @author Satrya Fajri Pratama
 */
public class Tagging extends FieldWork<TaggingForm>
{
	private static final long serialVersionUID = VERSION;
	private double grossVolumeLimit;
	private double netVolumeLimit;
	private long preFellingSurveyID;
	private long hallID;
	private String hallName;
	private String teamLeaderDesignation;
	private ArrayList<Hammer> hammers;
	private ArrayList<TaggingLimitException> taggingLimitExceptions;

	public long getTaggingID()
	{
		return getFieldWorkID();
	}

	public void setTaggingID(long taggingID)
	{
		setFieldWorkID(taggingID);
	}

	public double getGrossVolumeLimit()
	{
		return grossVolumeLimit;
	}

	public void setGrossVolumeLimit(double grossVolumeLimit)
	{
		this.grossVolumeLimit = grossVolumeLimit;
	}

	public double getNetVolumeLimit()
	{
		return netVolumeLimit;
	}

	public void setNetVolumeLimit(double netVolumeLimit)
	{
		this.netVolumeLimit = netVolumeLimit;
	}

	public long getPreFellingSurveyID()
	{
		return preFellingSurveyID;
	}

	public void setPreFellingSurveyID(long preFellingSurveyID)
	{
		this.preFellingSurveyID = preFellingSurveyID;
	}

	public long getHallID()
	{
		return hallID;
	}

	public void setHallID(long hallID)
	{
		this.hallID = hallID;
	}

	public String getHallName()
	{
		return hallName;
	}

	public void setHallName(String hallName)
	{
		this.hallName = hallName;
	}

	public String getTeamLeaderDesignation()
	{
		return teamLeaderDesignation;
	}

	public void setTeamLeaderDesignation(String teamLeaderDesignation)
	{
		this.teamLeaderDesignation = teamLeaderDesignation;
	}

	public ArrayList<Hammer> getHammers()
	{
		return hammers;
	}

	public void setHammers(ArrayList<Hammer> hammers)
	{
		this.hammers = hammers;
	}

	public ArrayList<TaggingForm> getTaggingForms()
	{
		return getFieldWorksheets();
	}

	public void setTaggingForms(ArrayList<TaggingForm> taggingForms)
	{
		setFieldWorksheets(taggingForms);
	}

	public ArrayList<TaggingLimitException> getTaggingLimitExceptions()
	{
		return taggingLimitExceptions;
	}

	public void setTaggingLimitExceptions(ArrayList<TaggingLimitException> taggingLimitExceptions)
	{
		this.taggingLimitExceptions = taggingLimitExceptions;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof Tagging)
		{
			Tagging that = (Tagging) obj;
			equal = getTaggingID() == that.getTaggingID();
		}

		return equal;
	}
}