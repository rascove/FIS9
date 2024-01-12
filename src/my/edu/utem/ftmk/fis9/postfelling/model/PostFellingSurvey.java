package my.edu.utem.ftmk.fis9.postfelling.model;

import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.FieldWork;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingReport;

/**
 * @author Zurina
 */
public class PostFellingSurvey extends FieldWork<PostFellingSurveyCard>
{
	private static final long serialVersionUID = VERSION;
	private long preFellingSurveyID;
	private PostFellingReport postFellingReport;
	private PostFellingInspectionReport postFellingInspectionReport;
	private String inspectionLeaderID;
	private String inspectionLeaderName;
	private Date inspectionStartDate;
	private Date inspectionEndDate;
	private Date inspectionStartWorkDate;
	private Date inspectionEndWorkDate;
	private int inspectionOpen;
	ArrayList<Staff> inspectionRecorders;
	private Date ownershipDate;
	private int inspectionNo;
	private int inspectionSignage;
	private boolean inspectionBearing;
	private boolean inspectionLineDistance;
	private boolean inspectionStake;
	private boolean inspectionSteepness;
	private String commentInspectionLeader;
	private String commentPPPN;
	private int totalInventoryLine;
	private ArrayList<PostFellingInspectionLine> postFellingInspectionLines;
	
	
	public long getPostFellingSurveyID()
	{
		return getFieldWorkID();
	}

	public void setPostFellingSurveyID(long postFellingSurveyID)
	{
		setFieldWorkID(postFellingSurveyID);
	}

	public long getPreFellingSurveyID()
	{
		return preFellingSurveyID;
	}

	public void setPreFellingSurveyID(long preFellingSurveyID)
	{
		this.preFellingSurveyID = preFellingSurveyID;
	}

	public PostFellingReport getPostFellingReport()
	{
		return postFellingReport;
	}

	public void setPostFellingReport(PostFellingReport postFellingReport)
	{
		this.postFellingReport = postFellingReport;
	}

	public PostFellingInspectionReport getPostFellingInspectionReport()
	{
		return postFellingInspectionReport;
	}

	public void setPostFellingInspectionReport(PostFellingInspectionReport postFellingInspectionReport)
	{
		this.postFellingInspectionReport = postFellingInspectionReport;
	}

	public ArrayList<PostFellingSurveyCard> getPostFellingSurveyCards()
	{
		return getFieldWorksheets();
	}

	public void setPostFellingSurveyCards(ArrayList<PostFellingSurveyCard> postFellingSurveyCards)
	{
		setFieldWorksheets(postFellingSurveyCards);
	}

	
	public String getInspectionLeaderID()
	{
		return inspectionLeaderID;
	}

	public void setInspectionLeaderID(String inspectionLeaderID)
	{
		this.inspectionLeaderID = inspectionLeaderID;
	}
	
	public Date getInspectionStartDate()
	{
		return inspectionStartDate;
	}

	public void setInspectionStartDate(Date inspectionStartDate)
	{
		this.inspectionStartDate = inspectionStartDate;
	}

	public Date getInspectionEndDate()
	{
		return inspectionEndDate;
	}

	public void setInspectionEndDate(Date inspectionEndDate)
	{
		this.inspectionEndDate = inspectionEndDate;
	}


	
	
	public int getInspectionOpen()
	{
		return inspectionOpen;
	}

	public void setInspectionOpen(int inspectionOpen)
	{
		this.inspectionOpen = inspectionOpen;
	}

	public ArrayList<Staff> getInspectionRecorders()
	{
		return inspectionRecorders;
	}

	public void setInspectionRecorders(ArrayList<Staff> inspectionRecorders)
	{
		this.inspectionRecorders = inspectionRecorders;
	}

	public String getInspectionLeaderName()
	{
		return inspectionLeaderName;
	}

	public void setInspectionLeaderName(String inspectionLeaderName)
	{
		this.inspectionLeaderName = inspectionLeaderName;
	}
	

	public Date getOwnershipDate()
	{
		return ownershipDate;
	}

	public void setOwnershipDate(Date ownershipDate)
	{
		this.ownershipDate = ownershipDate;
	}

	public int getInspectionNo()
	{
		return inspectionNo;
	}

	public void setInspectionNo(int inspectionNo)
	{
		this.inspectionNo = inspectionNo;
	}

	

	public int getInspectionSignage()
	{
		return inspectionSignage;
	}

	public void setInspectionSignage(int inspectionSignage)
	{
		this.inspectionSignage = inspectionSignage;
	}

	public boolean isInspectionBearing()
	{
		return inspectionBearing;
	}

	public void setInspectionBearing(boolean inspectionBearing)
	{
		this.inspectionBearing = inspectionBearing;
	}

	public boolean isInspectionLineDistance()
	{
		return inspectionLineDistance;
	}

	public void setInspectionLineDistance(boolean inspectionLineDistance)
	{
		this.inspectionLineDistance = inspectionLineDistance;
	}

	public boolean isInspectionStake()
	{
		return inspectionStake;
	}

	public void setInspectionStake(boolean inspectionStake)
	{
		this.inspectionStake = inspectionStake;
	}

	public boolean isInspectionSteepness()
	{
		return inspectionSteepness;
	}

	public void setInspectionSteepness(boolean inspectionSteepness)
	{
		this.inspectionSteepness = inspectionSteepness;
	}

	

	public String getCommentPPPN()
	{
		return commentPPPN;
	}

	public void setCommentPPPN(String commentPPPN)
	{
		this.commentPPPN = commentPPPN;
	}

	
	public int getTotalInventoryLine()
	{
		return totalInventoryLine;
	}

	public void setTotalInventoryLine(int totalInventoryLine)
	{
		this.totalInventoryLine = totalInventoryLine;
	}

	public String getCommentInspectionLeader()
	{
		return commentInspectionLeader;
	}

	public void setCommentInspectionLeader(String commentInspectionLeader)
	{
		this.commentInspectionLeader = commentInspectionLeader;
	}

	public Date getInspectionStartWorkDate()
	{
		return inspectionStartWorkDate;
	}

	public void setInspectionStartWorkDate(Date inspectionStartWorkDate)
	{
		this.inspectionStartWorkDate = inspectionStartWorkDate;
	}

	public Date getInspectionEndWorkDate()
	{
		return inspectionEndWorkDate;
	}

	public void setInspectionEndWorkDate(Date inspectionEndWorkDate)
	{
		this.inspectionEndWorkDate = inspectionEndWorkDate;
	}

	
	public ArrayList<PostFellingInspectionLine> getPostFellingInspectionLines()
	{
		return postFellingInspectionLines;
	}

	public void setPostFellingInspectionLines(ArrayList<PostFellingInspectionLine> postFellingInspectionLines)
	{
		this.postFellingInspectionLines = postFellingInspectionLines;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equal = false;

		if (obj instanceof PostFellingSurvey)
		{
			PostFellingSurvey that = (PostFellingSurvey) obj;
			equal = getPostFellingSurveyID() == that.getPostFellingSurveyID();
		}

		return equal;
	}
}