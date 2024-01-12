package my.edu.utem.ftmk.fis9.postfelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingInspectionLine;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyCard;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurveyRecord;

/**
 * @author Satrya Fajri Pratama
 */
public class PostFellingFacade extends AbstractFacade
{
	private PostFellingSurveyManager postFellingSurveyManager;
	private PostFellingSurveyCardManager postFellingSurveyCardManager;
	private PostFellingSurveyRecordManager postFellingSurveyRecordManager;
	private PostFellingInspectionLineManager postFellingInspectionLineManager;
	
	
	private PostFellingInspectionLineManager getPostFellingInspectionLineManager()
	{
		if (postFellingInspectionLineManager == null)
			postFellingInspectionLineManager = new PostFellingInspectionLineManager(this);

		return postFellingInspectionLineManager;
	}
	private PostFellingSurveyManager getPostFellingSurveyManager()
	{
		if (postFellingSurveyManager == null)
			postFellingSurveyManager = new PostFellingSurveyManager(this);

		return postFellingSurveyManager;
	}
	private PostFellingSurveyCardManager getPostFellingSurveyCardManager()
	{
		if (postFellingSurveyCardManager == null)
			postFellingSurveyCardManager = new PostFellingSurveyCardManager(this);

		return postFellingSurveyCardManager;
	}

	private PostFellingSurveyRecordManager getPostFellingSurveyRecordManager()
	{
		if (postFellingSurveyRecordManager == null)
			postFellingSurveyRecordManager = new PostFellingSurveyRecordManager(this);

		return postFellingSurveyRecordManager;
	}

	@Override
	protected PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return getPreparedStatement(sql);
	}

	public ArrayList<PostFellingInspectionLine> getPostFellingInspectionLines(PostFellingSurvey postFellingSurvey) throws SQLException
	{
		return getPostFellingInspectionLineManager().getPostFellingInspectionLines(postFellingSurvey);
	}
	
	public int addPostFellingInspectionLine(PostFellingInspectionLine postFellingInspectionLine, boolean ignoreDuplicate) throws SQLException
	{
		return getPostFellingInspectionLineManager().addPostFellingInspectionLine(postFellingInspectionLine, ignoreDuplicate);
	}
	
	public int updatePostFellingInspectionLine(PostFellingInspectionLine postFellingInspectionLine) throws SQLException
	{
		return getPostFellingInspectionLineManager().updatePostFellingInspectionLine(postFellingInspectionLine);
	}
	ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecords(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		return getPostFellingSurveyRecordManager().getPostFellingSurveyRecords(postFellingSurveyCard);
	}
	
	ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecordsFilterInspection(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		return getPostFellingSurveyRecordManager().getPostFellingSurveyRecordsFilterInspection(postFellingSurveyCard);
	}
	
	ArrayList<PostFellingSurveyRecord> getPostFellingSurveyRecordsForInspection(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		return getPostFellingSurveyRecordManager().getPostFellingSurveyRecordsForInspection(postFellingSurveyCard);
	}

	public int addPostFellingSurvey(PostFellingSurvey postFellingSurvey, boolean ignoreDuplicate) throws SQLException
	{
		return getPostFellingSurveyManager().addPostFellingSurvey(postFellingSurvey, ignoreDuplicate);
	}

	public int updatePostFellingSurvey(PostFellingSurvey postFellingSurvey) throws SQLException
	{
		return getPostFellingSurveyManager().updatePostFellingSurvey(postFellingSurvey);
	}

	public PostFellingSurvey getPostFellingSurvey(long postFellingSurveyID) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurvey(postFellingSurveyID);
	}

	public ArrayList<PostFellingSurvey> getPostFellingSurveys() throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveys();
	}

	public ArrayList<PostFellingSurvey> getPostFellingSurveys(State state) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveys(state);
	}

	public ArrayList<PostFellingSurvey> getPostFellingSurveys(District district) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveys(district);
	}

	public ArrayList<PostFellingSurvey> getPostFellingSurveys(Staff staff) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveys(staff);
	}
	
	public ArrayList<PostFellingSurvey> getPostFellingSurveys(Range range) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveys(range);
	}
	
	public ArrayList<PostFellingSurvey> getPostFellingSurveys(int startYear, int endYear) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveys(startYear, endYear);
	}

	public ArrayList<PostFellingSurvey> getPostFellingSurveys(State state, int startYear, int endYear) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveys(state, startYear, endYear);
	}

	public ArrayList<PostFellingSurvey> getPostFellingSurveys(District district, int startYear, int endYear) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveys(district, startYear, endYear);
	}

	public ArrayList<PostFellingSurvey> getPostFellingSurveys(Range range, int startYear, int endYear) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveys(range, startYear, endYear);
	}

	public ArrayList<PostFellingSurvey> getPostFellingSurveys(Staff staff, int startYear, int endYear) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveys(staff, startYear, endYear);
	}

	public int addPostFellingSurveyCard(PostFellingSurveyCard postFellingSurveyCard, boolean ignoreDuplicate) throws SQLException
	{
		return getPostFellingSurveyCardManager().addPostFellingSurveyCard(postFellingSurveyCard, ignoreDuplicate);
	}

	public int updatePostFellingSurveyCard(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		return getPostFellingSurveyCardManager().updatePostFellingSurveyCard(postFellingSurveyCard);
	}
	
	public int deletePostFellingSurveyCard(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		return getPostFellingSurveyCardManager().deletePostFellingSurveyCard(postFellingSurveyCard);
	}
	
	public int updatePostFellingSurveyCardInspection(PostFellingSurveyCard postFellingSurveyCard) throws SQLException
	{
		return getPostFellingSurveyCardManager().updatePostFellingSurveyCardInspection(postFellingSurveyCard);
	}

	public ArrayList<PostFellingSurveyCard> getPostFellingSurveyCards(PostFellingSurvey postFellingSurvey) throws SQLException
	{
		return getPostFellingSurveyCardManager().getPostFellingSurveyCards(postFellingSurvey);
	}

	public int addPostFellingSurveyRecord(PostFellingSurveyRecord postFellingSurveyRecord, boolean ignoreDuplicate) throws SQLException
	{
		return getPostFellingSurveyRecordManager().addPostFellingSurveyRecord(postFellingSurveyRecord, ignoreDuplicate);
	}
	

	public int updatePostFellingSurveyRecord(PostFellingSurveyRecord postFellingSurveyRecord) throws SQLException
	{
		return getPostFellingSurveyRecordManager().updatePostFellingSurveyRecord(postFellingSurveyRecord);
	}
	
	public int updatePostFellingInspectionRecord(PostFellingSurveyRecord postFellingSurveyRecord) throws SQLException
	{
	return getPostFellingSurveyRecordManager().updatePostFellingInspectionRecord(postFellingSurveyRecord);
	}

	public int deletePostFellingSurveyRecord(PostFellingSurveyRecord postFellingSurveyRecord) throws SQLException
	{
		return getPostFellingSurveyRecordManager().deletePostFellingSurveyRecord(postFellingSurveyRecord);
	}
	
	
	public int resetPostFellingSurveyRecordForInspection(PostFellingSurveyRecord postFellingSurveyRecord) throws SQLException
	{
		return getPostFellingSurveyRecordManager().resetPostFellingSurveyRecordForInspection(postFellingSurveyRecord);
	}
	
	public PostFellingSurveyCard getPostFellingSurveyCard(long postFellingSurveyCardID) throws SQLException
	{
		return getPostFellingSurveyCardManager().getPostFellingSurveyCard(postFellingSurveyCardID);
	}
	

	
	public ArrayList<PostFellingSurvey> getPostFellingInspections() throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingInspections();
	}
	
	
	public ArrayList<PostFellingSurvey> getPostFellingInspections(State state) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingInspections(state);
	}

	public ArrayList<PostFellingSurvey> getPostFellingInspections(Staff staff) throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingInspections(staff);
	}
	public ArrayList<PostFellingSurveyCard> getPostFellingInspectionCards(PostFellingSurvey postFellingSurvey) throws SQLException
	{
		return getPostFellingSurveyCardManager().getPostFellingInspectionCards(postFellingSurvey);
	}
	
	public int[] getPostFellingSurveyYearRange() throws SQLException
	{
		return getPostFellingSurveyManager().getPostFellingSurveyYearRange();
	}

	
}