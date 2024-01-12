package my.edu.utem.ftmk.fis9.postfelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingInspectionLine;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;

/**
 * @author Satrya Fajri Pratama
 */
class PostFellingInspectionLineManager extends PostFellingTableManager
{
	PostFellingInspectionLineManager(PostFellingFacade facade)
	{
		super(facade);
	}

	private void write(PostFellingInspectionLine postFellingInspectionLine, PreparedStatement ps) throws SQLException
	{
		
		
		ps.setDouble(1, postFellingInspectionLine.getLineBefore());
		ps.setDouble(2, postFellingInspectionLine.getLineAfter());
		ps.setInt(3, toInt(postFellingInspectionLine.isStartPole()));
		ps.setInt(4, toInt(postFellingInspectionLine.isEndPole()));
		ps.setInt(5, postFellingInspectionLine.getBearingError());
		ps.setInt(6, postFellingInspectionLine.getPlotError());
		ps.setInt(7, toInt(postFellingInspectionLine.isStake()));
		ps.setInt(8, toInt(postFellingInspectionLine.isSteepness()));
		ps.setInt(9, toInt(postFellingInspectionLine.isBoundarySignageStart()));
		ps.setInt(10, toInt(postFellingInspectionLine.isBoundaryCleanStart()));
		ps.setInt(11, toInt(postFellingInspectionLine.isBoundarySignageEnd()));
		ps.setInt(12, toInt(postFellingInspectionLine.isBoundaryCleanEnd()));
		
	}

	int addPostFellingInspectionLine(PostFellingInspectionLine postFellingInspectionLine, boolean ignoreDuplicate) throws SQLException
	{
		String sql = (ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO PostFellingInspectionLine (LineBefore, LineAfter, StartPole, EndPole, BearingError, PlotError, Stake, Steepness, BoundarySignageStart, BoundaryCleanStart, BoundarySignageEnd, BoundaryCleanEnd, PostFellingInspectionLineID, PostFellingSurveyID, LineNo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE LineBefore = ?, LineAfter = ?, StartPole = ?, EndPole = ?, BearingError = ?, PlotError = ?, Stake = ?, Steepness = ?, BoundarySignageStart = ?, BoundaryCleanStart = ?, BoundarySignageEnd = ?, BoundaryCleanEnd = ?");
		PreparedStatement ps = facade.prepareStatement(sql);

		write(postFellingInspectionLine, ps);
		ps.setLong(13, postFellingInspectionLine.getPostFellingInspectionLineID());
		ps.setLong(14, postFellingInspectionLine.getPostFellingSurveyID());
		ps.setString(15, postFellingInspectionLine.getLineNo());
		
		if (!ignoreDuplicate)
		{
			ps.setDouble(16, postFellingInspectionLine.getLineBefore());
			ps.setDouble(17, postFellingInspectionLine.getLineAfter());
			ps.setInt(18, toInt(postFellingInspectionLine.isStartPole()));
			ps.setInt(19, toInt(postFellingInspectionLine.isEndPole()));
			ps.setInt(20, postFellingInspectionLine.getBearingError());
			ps.setInt(21, postFellingInspectionLine.getPlotError());
			ps.setInt(22, toInt(postFellingInspectionLine.isStake()));
			ps.setInt(23, toInt(postFellingInspectionLine.isSteepness()));
			ps.setInt(24, toInt(postFellingInspectionLine.isBoundarySignageStart()));
			ps.setInt(25, toInt(postFellingInspectionLine.isBoundaryCleanStart()));
			ps.setInt(26, toInt(postFellingInspectionLine.isBoundarySignageEnd()));
			ps.setInt(27, toInt(postFellingInspectionLine.isBoundaryCleanEnd()));
		}
		
		int status = ps.executeUpdate();
		
		if (status == 0 && !ignoreDuplicate)
			status = 1;
		
		return status;
	}
	
	

	int updatePostFellingInspectionLine(PostFellingInspectionLine postFellingInspectionLine) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PostFellingInspectionLine SET LineBefore = ?, LineAfter = ?, StartPole = ?, EndPole = ?, BearingError = ?, PlotError = ?, Stake = ?, Steepness = ?, BoundarySignageStart = ?, BoundaryCleanStart = ?, BoundarySignageEnd = ?, BoundaryCleanEnd = ?  WHERE PostFellingInspectionLineID = ?");

		write(postFellingInspectionLine, ps);
		ps.setLong(13, postFellingInspectionLine.getPostFellingInspectionLineID());
		
		int status = ps.executeUpdate();
		return status;
	}

	
	
	private PostFellingInspectionLine read(ResultSet rs) throws SQLException
	{
		PostFellingInspectionLine postFellingInspectionLine = new PostFellingInspectionLine();

		postFellingInspectionLine.setPostFellingInspectionLineID(rs.getLong("PostFellingInspectionLineID"));
		postFellingInspectionLine.setPostFellingSurveyID(rs.getLong("PostFellingSurveyID"));
		postFellingInspectionLine.setLineNo(rs.getString("LineNo"));
		postFellingInspectionLine.setLineBefore(rs.getDouble("LineBefore"));
		postFellingInspectionLine.setLineAfter(rs.getDouble("LineAfter"));
		postFellingInspectionLine.setStartPole(toBoolean(rs.getInt("StartPole")));
		postFellingInspectionLine.setEndPole(toBoolean(rs.getInt("EndPole")));
		postFellingInspectionLine.setBearingError(rs.getInt("BearingError"));
		postFellingInspectionLine.setPlotError(rs.getInt("PlotError"));
		postFellingInspectionLine.setStake(toBoolean(rs.getInt("Stake")));
		postFellingInspectionLine.setSteepness(toBoolean(rs.getInt("Steepness")));
		postFellingInspectionLine.setBoundarySignageStart(toBoolean(rs.getInt("BoundarySignageStart")));
		postFellingInspectionLine.setBoundaryCleanStart(toBoolean(rs.getInt("BoundaryCleanStart")));
		postFellingInspectionLine.setBoundarySignageEnd(toBoolean(rs.getInt("BoundarySignageEnd")));
		postFellingInspectionLine.setBoundaryCleanEnd(toBoolean(rs.getInt("BoundaryCleanEnd")));
		
		return postFellingInspectionLine;
	}

	private ArrayList<PostFellingInspectionLine> getPostFellingInspectionLines(ResultSet rs) throws SQLException
	{
		ArrayList<PostFellingInspectionLine> postFellingInspectionLines = new ArrayList<>();

		while (rs.next())
			postFellingInspectionLines.add(read(rs));

		return postFellingInspectionLines;
	}

	

	ArrayList<PostFellingInspectionLine> getPostFellingInspectionLines(PostFellingSurvey postFellingSurvey) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM PostFellingInspectionLine WHERE PostFellingSurveyID = ? ORDER BY PostFellingInspectionLineID DESC");
		ps.setLong(1, postFellingSurvey.getPostFellingSurveyID());
		return getPostFellingInspectionLines(ps.executeQuery());
	}

	
	
	

	
}