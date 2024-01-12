package my.edu.utem.ftmk.fis9.hall.model;

import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.model.AbstractModel;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingRecord;

/**
 * @author Nor Azman Mat Ariff
 */
public class TaggingSession extends AbstractModel
{
	private static final long serialVersionUID = VERSION;
	private long taggingSessionID;
	private long surveyID;
	private int hallID;
	private String hallName;
	private int forestID;
	private String forestCode;
	private String forestName;	
	private String comptBlockNo;
	private int year;

	private ArrayList<TaggingRecord> taggingRecords;

	public long getTaggingSessionID() {
		return taggingSessionID;
	}

	public void setTaggingSessionID(long taggingSessionID) {
		this.taggingSessionID = taggingSessionID;
	}

	public long getSurveyID() {
		return surveyID;
	}

	public void setSurveyID(long surveyID) {
		this.surveyID = surveyID;
	}

	public int getHallID() {
		return hallID;
	}

	public void setHallID(int hallID) {
		this.hallID = hallID;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public ArrayList<TaggingRecord> getTaggingRecords() {
		return taggingRecords;
	}

	public void setTaggingRecords(ArrayList<TaggingRecord> taggingRecords) {
		this.taggingRecords = taggingRecords;
	}

	public int getForestID() {
		return forestID;
	}

	public void setForestID(int forestID) {
		this.forestID = forestID;
	}

	public String getForestCode() {
		return forestCode;
	}

	public void setForestCode(String forestCode) {
		this.forestCode = forestCode;
	}

	public String getForestName() {
		return forestName;
	}

	public void setForestName(String forestName) {
		this.forestName = forestName;
	}

	public String getComptBlockNo() {
		return comptBlockNo;
	}

	public void setComptBlockNo(String comptBlockNo) {
		this.comptBlockNo = comptBlockNo;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString()
	{
		return forestName + " no. kompatmen/blok " + comptBlockNo + " tahun " + year;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof TaggingSession)
		{
			TaggingSession that = (TaggingSession) obj;
			equal = getTaggingSessionID() == that.getTaggingSessionID();
		}

		return equal;
	}
}