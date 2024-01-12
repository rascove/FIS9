package my.edu.utem.ftmk.fis9.postfelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;

/**
 * @author Satrya Fajri Pratama
 */
class PostFellingSurveyManager extends PostFellingTableManager
{
	PostFellingSurveyManager(PostFellingFacade facade)
	{
		super(facade);
	}

	private void write(PostFellingSurvey postFellingSurvey, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, postFellingSurvey.getYear());
		nullable(ps, 2, postFellingSurvey.getStartDate());
		nullable(ps, 3, postFellingSurvey.getEndDate());
		ps.setInt(4, toInt(postFellingSurvey.isOpen()));
		nullable(ps, 5, postFellingSurvey.getTeamLeaderID());
		nullable(ps, 6, postFellingSurvey.getTenderNo());
		nullable(ps, 7, postFellingSurvey.getInspectionLeaderID());
		nullable(ps, 8, postFellingSurvey.getInspectionStartDate());
		nullable(ps, 9, postFellingSurvey.getInspectionEndDate());
		ps.setInt(10, postFellingSurvey.getInspectionOpen());
		ps.setInt(11, postFellingSurvey.getInspectionNo());
		nullable(ps, 12, postFellingSurvey.getOwnershipDate());
		nullable(ps, 13, postFellingSurvey.getInspectionStartWorkDate());
		nullable(ps, 14, postFellingSurvey.getInspectionEndWorkDate());
		ps.setInt(15, postFellingSurvey.getInspectionSignage());
		ps.setInt(16, toInt(postFellingSurvey.isInspectionBearing()));
		ps.setInt(17, toInt(postFellingSurvey.isInspectionLineDistance()));
		ps.setInt(18, toInt(postFellingSurvey.isInspectionStake()));
		ps.setInt(19, toInt(postFellingSurvey.isInspectionSteepness()));
		ps.setString(20, postFellingSurvey.getCommentInspectionLeader());
		ps.setString(21, postFellingSurvey.getCommentPPPN());
		
		
	
	}

	int addPostFellingSurvey(PostFellingSurvey postFellingSurvey, boolean ignoreDuplicate) throws SQLException
	{
		String sql = (ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO PostFellingSurvey (Year, StartDate, EndDate, Open, TeamLeaderID, TenderNo, InspectionLeaderID, InspectionStartDate, InspectionEndDate, InspectionOpen, InspectionNo, OwnershipDate, InspectionStartWorkDate, InspectionEndWorkDate, InspectionSignage, InspectionBearing, InspectionLineDistance, InspectionStake, InspectionSteepness, CommentInspectionLeader, CommentPPPN, PreFellingSurveyID, PostFellingSurveyID, CreatorID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE StartDate = ?, EndDate = ?, Open = ?, TeamLeaderID = ?, TenderNo = ?, InspectionLeaderID = ?, InspectionStartDate = ?, InspectionEndDate = ?, InspectionOpen = ?, InspectionNo = ?, OwnershipDate = ?, InspectionStartWorkDate = ?, InspectionEndWorkDate = ?, InspectionSignage = ?, InspectionBearing = ?, InspectionLineDistance = ?, InspectionStake = ?, InspectionSteepness = ?, CommentInspectionLeader = ?, CommentPPPN = ?");
		PreparedStatement ps = facade.prepareStatement(sql);

		write(postFellingSurvey, ps);
		ps.setLong(22, postFellingSurvey.getPreFellingSurveyID());
		ps.setLong(23, postFellingSurvey.getPostFellingSurveyID());
		ps.setString(24, postFellingSurvey.getCreatorID());
		
		if (!ignoreDuplicate)
		{
			nullable(ps, 25, postFellingSurvey.getStartDate());
			nullable(ps, 26, postFellingSurvey.getEndDate());
			ps.setInt(27, toInt(postFellingSurvey.isOpen()));
			ps.setString(28, postFellingSurvey.getTeamLeaderID());
			ps.setString(29, postFellingSurvey.getTenderNo());
			ps.setString(30, postFellingSurvey.getInspectionLeaderID());
			nullable(ps, 31, postFellingSurvey.getInspectionStartDate());
			nullable(ps, 32, postFellingSurvey.getInspectionEndDate());
			nullable(ps, 33, postFellingSurvey.getInspectionOpen());
			ps.setInt(34, postFellingSurvey.getInspectionNo());
			nullable(ps, 35, postFellingSurvey.getOwnershipDate());
			nullable(ps, 36, postFellingSurvey.getInspectionStartWorkDate());
			nullable(ps, 37, postFellingSurvey.getInspectionEndWorkDate());
			ps.setInt(38, postFellingSurvey.getInspectionSignage());
			ps.setInt(39, toInt(postFellingSurvey.isInspectionBearing()));
			ps.setInt(40, toInt(postFellingSurvey.isInspectionLineDistance()));
			ps.setInt(41, toInt(postFellingSurvey.isInspectionStake()));
			ps.setInt(42, toInt(postFellingSurvey.isInspectionSteepness()));
			ps.setString(43, postFellingSurvey.getCommentInspectionLeader());
			ps.setString(44, postFellingSurvey.getCommentPPPN());	
		}
		
		int status = ps.executeUpdate();
		
		if (status != 0 || !ignoreDuplicate)
		{
			if (status == 0)
				status = 1;
			
			setPostFellingSurveyTeams(postFellingSurvey);
		}
		
		return status;
	}

	int updatePostFellingSurvey(PostFellingSurvey postFellingSurvey) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PostFellingSurvey SET Year = ?, StartDate = ?, EndDate = ?, Open = ?, TeamLeaderID = ?, TenderNo = ?, InspectionLeaderID	= ?, InspectionStartDate = ?, InspectionEndDate = ?, InspectionOpen = ?, InspectionNo = ?, OwnershipDate = ?, InspectionStartWorkDate = ?, InspectionEndWorkDate = ?, InspectionSignage = ?, InspectionBearing = ?, InspectionLineDistance = ?, InspectionStake = ?, InspectionSteepness = ?, CommentInspectionLeader = ?, CommentPPPN = ?  WHERE PostFellingSurveyID = ?");
		write(postFellingSurvey, ps);
		ps.setLong(22, postFellingSurvey.getPostFellingSurveyID());
		
		int status = ps.executeUpdate();
		
		if (status != -1) {

			setPostFellingSurveyTeams(postFellingSurvey);
			if (postFellingSurvey.getInspectionRecorders() != null)
				setPostFellingInspectionTeams(postFellingSurvey);
			status = 1;
		}
		
		
		return status;
	}

	private PostFellingSurvey read(ResultSet rs) throws SQLException
	{
		PostFellingSurvey postFellingSurvey = new PostFellingSurvey();

		postFellingSurvey.setPostFellingSurveyID(rs.getLong("PostFellingSurveyID"));
		postFellingSurvey.setStartDate(rs.getDate("StartDate"));
		postFellingSurvey.setEndDate(rs.getDate("EndDate"));
		postFellingSurvey.setOpen(toBoolean(rs.getInt("Open")));
		postFellingSurvey.setTenderNo(rs.getString("TenderNo"));
		postFellingSurvey.setTeamLeaderID(rs.getString("TeamLeaderID"));
		postFellingSurvey.setCreatorID(rs.getString("CreatorID"));
		postFellingSurvey.setPreFellingSurveyID(rs.getLong("PreFellingSurveyID"));
		postFellingSurvey.setComptBlockNo(rs.getString("ComptBlockNo"));
		postFellingSurvey.setArea(rs.getDouble("Area"));
		postFellingSurvey.setYear(rs.getInt("Year"));
		postFellingSurvey.setForestID(rs.getInt("ForestID"));
		postFellingSurvey.setRangeID(rs.getInt("RangeID"));
		postFellingSurvey.setDistrictID(rs.getInt("DistrictID"));
		postFellingSurvey.setStateID(rs.getInt("StateID"));
		postFellingSurvey.setForestCode(rs.getString("ForestCode"));
		postFellingSurvey.setForestName(rs.getString("ForestName"));
		postFellingSurvey.setRangeName(rs.getString("RangeName"));
		postFellingSurvey.setDistrictCode(rs.getString("DistrictCode"));
		postFellingSurvey.setDistrictName(rs.getString("DistrictName"));
		postFellingSurvey.setStateCode(rs.getString("StateCode"));
		postFellingSurvey.setStateName(rs.getString("StateName"));
		postFellingSurvey.setTeamLeaderName(rs.getString("TeamLeaderName"));
		postFellingSurvey.setCreatorName(rs.getString("CreatorName"));
		postFellingSurvey.setInspectionLeaderID(rs.getString("InspectionLeaderID"));
		postFellingSurvey.setInspectionLeaderName(rs.getString("InspectionLeaderName"));
		postFellingSurvey.setInspectionStartDate(rs.getDate("InspectionStartDate"));
		postFellingSurvey.setInspectionEndDate(rs.getDate("InspectionEndDate"));
		postFellingSurvey.setInspectionOpen(rs.getInt("InspectionOpen"));
		postFellingSurvey.setInspectionNo(rs.getInt("InspectionNo"));
		postFellingSurvey.setOwnershipDate(rs.getDate("OwnershipDate"));
		postFellingSurvey.setInspectionStartWorkDate(rs.getDate("InspectionStartWorkDate"));
		postFellingSurvey.setInspectionEndWorkDate(rs.getDate("InspectionEndWorkDate"));
		postFellingSurvey.setInspectionOpen(rs.getInt("InspectionOpen"));
		postFellingSurvey.setInspectionSignage(rs.getInt("InspectionSignage"));
		postFellingSurvey.setInspectionBearing(toBoolean(rs.getInt("InspectionBearing")));
		postFellingSurvey.setInspectionLineDistance(toBoolean(rs.getInt("InspectionLineDistance")));
		postFellingSurvey.setInspectionStake(toBoolean(rs.getInt("InspectionStake")));
		postFellingSurvey.setInspectionSteepness(toBoolean(rs.getInt("InspectionSteepness")));
		postFellingSurvey.setCommentInspectionLeader(rs.getString("CommentInspectionLeader"));
		postFellingSurvey.setCommentPPPN(rs.getString("CommentPPPN"));
		postFellingSurvey.setTotalInventoryLine(getTotalInventoryLine(rs.getLong("PostFellingSurveyID")));
		
		return postFellingSurvey;
	}

	private ArrayList<PostFellingSurvey> getPostFellingSurveys(ResultSet rs) throws SQLException
	{
		ArrayList<PostFellingSurvey> postFellingSurveys = new ArrayList<>();

		while (rs.next())
			postFellingSurveys.add(read(rs));

		return postFellingSurveys;
	}

	PostFellingSurvey getPostFellingSurvey(long postFellingSurveyID) throws SQLException
	{
		PostFellingSurvey postFellingSurvey = null;
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, a.Name AS CreatorName, tl.Name AS TeamLeaderName, itl.Name AS InspectionLeaderName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s JOIN Staff a LEFT JOIN Staff tl ON t.TeamLeaderID = tl.StaffID WHERE t.PostFellingSurveyID = ? AND t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CreatorID = a.StaffID ORDER BY t.StartDate");

		ps.setLong(1, postFellingSurveyID);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			postFellingSurvey = read(rs);

		return postFellingSurvey;
	}

	ArrayList<PostFellingSurvey> getPostFellingSurveys() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");

		return getPostFellingSurveys(ps.executeQuery());
	}

	ArrayList<PostFellingSurvey> getPostFellingSurveys(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND s.StateID = ? ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");

		ps.setInt(1, state.getStateID());

		return getPostFellingSurveys(ps.executeQuery());
	}

	ArrayList<PostFellingSurvey> getPostFellingSurveys(District district) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND f.DistrictID = ? AND d.StateID = s.StateID ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");

		ps.setInt(1, district.getDistrictID());

		return getPostFellingSurveys(ps.executeQuery());
	}

	ArrayList<PostFellingSurvey> getPostFellingSurveys(Staff staff) throws SQLException
	{
		
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND (t.TeamLeaderID = ? OR t.InspectionLeaderID = ? OR t.PostFellingSurveyID IN (SELECT PostFellingSurveyID FROM PostFellingSurveyTeam WHERE RecorderID = ?) OR t.PostFellingSurveyID IN (SELECT PostFellingSurveyID FROM PostFellingInspectionTeam WHERE RecorderID = ?) OR t.TenderNo IN (SELECT TenderNo FROM Tender WHERE ContractorID = (SELECT ContractorID FROM Staff WHERE StaffID = ?))) ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");
		ps.setString(1, staff.getStaffID());
		ps.setString(2, staff.getStaffID());
		ps.setString(3, staff.getStaffID());
		ps.setString(4, staff.getStaffID());
		ps.setString(5, staff.getStaffID());

		return getPostFellingSurveys(ps.executeQuery());
	}
	
	ArrayList<PostFellingSurvey> getPostFellingSurveys(int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.PreFellingSurveyID = v.PreFellingSurveyID AND t.Year BETWEEN ? AND ? AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);

		return getPostFellingSurveys(ps.executeQuery());
	}

	ArrayList<PostFellingSurvey> getPostFellingSurveys(State state, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.PreFellingSurveyID = v.PreFellingSurveyID AND t.Year BETWEEN ? AND ? AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND s.StateID = ? ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setInt(3, state.getStateID());
		
		return getPostFellingSurveys(ps.executeQuery());
	}

	ArrayList<PostFellingSurvey> getPostFellingSurveys(District district, int startYear, int endYear) throws SQLException
	{
		
		
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.PreFellingSurveyID = v.PreFellingSurveyID AND t.Year BETWEEN ? AND ?  AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND f.DistrictID = ? AND d.StateID = s.StateID ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");
		
		
		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setInt(3, district.getDistrictID());

		return getPostFellingSurveys(ps.executeQuery());
	}

	ArrayList<PostFellingSurvey> getPostFellingSurveys(Range range, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.PreFellingSurveyID = v.PreFellingSurveyID AND t.Year BETWEEN ? AND ? AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND v.RangeID = ? AND d.StateID = s.StateID ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setInt(3, range.getRangeID());

		return getPostFellingSurveys(ps.executeQuery());
	}

	ArrayList<PostFellingSurvey> getPostFellingSurveys(Staff staff, int startYear, int endYear) throws SQLException
	{
		
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.PreFellingSurveyID = v.PreFellingSurveyID AND t.Year BETWEEN ? AND ? AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND (t.TeamLeaderID = ? OR t.InspectionLeaderID = ? OR t.PostFellingSurveyID IN (SELECT PostFellingSurveyID FROM PostFellingSurveyTeam WHERE RecorderID = ?) OR t.PostFellingSurveyID IN (SELECT PostFellingSurveyID FROM PostFellingInspectionTeam WHERE RecorderID = ?) OR t.TenderNo IN (SELECT TenderNo FROM Tender WHERE ContractorID = (SELECT ContractorID FROM Staff WHERE StaffID = ?))) ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");
		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setString(3, staff.getStaffID());
		ps.setString(4, staff.getStaffID());
		ps.setString(5, staff.getStaffID());
		ps.setString(6, staff.getStaffID());
		ps.setString(7, staff.getStaffID());
		
		return getPostFellingSurveys(ps.executeQuery());
	}
	
	
	ArrayList<PostFellingSurvey> getPostFellingSurveys(Range range) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND v.RangeID = ? AND d.StateID = s.StateID ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");

		ps.setInt(1, range.getRangeID());
		
		return getPostFellingSurveys(ps.executeQuery());
	}

	private void setPostFellingSurveyTeams(PostFellingSurvey postFellingSurvey) throws SQLException
	{
		ArrayList<Staff> recorders = postFellingSurvey.getRecorders();
		PreparedStatement clear = facade.prepareStatement("DELETE FROM PostFellingSurveyTeam WHERE PostFellingSurveyID = ?");

		clear.setLong(1, postFellingSurvey.getPostFellingSurveyID());
		clear.executeUpdate();

		if (recorders != null && !recorders.isEmpty())
		{
			PreparedStatement batch = facade.prepareStatement("INSERT INTO PostFellingSurveyTeam VALUES (?, ?)");

			for (Staff recorder : recorders)
			{
				batch.setString(1, recorder.getStaffID());
				batch.setLong(2, postFellingSurvey.getPostFellingSurveyID());
				batch.addBatch();
			}

			batch.executeBatch();
		}
	}
	
	
	private void setPostFellingInspectionTeams(PostFellingSurvey postFellingSurvey) throws SQLException
	{
		ArrayList<Staff> recorders = postFellingSurvey.getInspectionRecorders();
		PreparedStatement clear = facade.prepareStatement("DELETE FROM PostFellingInspectionTeam WHERE PostFellingSurveyID = ?");

		clear.setLong(1, postFellingSurvey.getPostFellingSurveyID());
		clear.executeUpdate();

		if (recorders != null && !recorders.isEmpty())
		{
			PreparedStatement batch = facade.prepareStatement("INSERT INTO PostFellingInspectionTeam VALUES (?, ?)");

			for (Staff recorder : recorders)
			{
				batch.setString(1, recorder.getStaffID());
				batch.setLong(2, postFellingSurvey.getPostFellingSurveyID());
				batch.addBatch();
			}

			batch.executeBatch();
		}
	}
	ArrayList<PostFellingSurvey> getPostFellingInspections() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.InspectionLeaderID IS NOT NULL AND t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");

		return getPostFellingSurveys(ps.executeQuery());
	}

	ArrayList<PostFellingSurvey> getPostFellingInspections(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.InspectionLeaderID IS NOT NULL AND t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND s.StateID = ? ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");

		ps.setInt(1, state.getStateID());

		return getPostFellingSurveys(ps.executeQuery());
	}
	
	ArrayList<PostFellingSurvey> getPostFellingInspections(Staff staff) throws SQLException
	{

		PreparedStatement ps = facade.prepareStatement("SELECT pf.*, cn.Name AS CreatorName, tl.Name AS TeamLeaderName, ist.Name AS InspectionLeaderName FROM (SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName FROM PostFellingSurvey t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s WHERE t.InspectionLeaderID IS NOT NULL AND t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND (t.InspectionLeaderID = ? OR t.PostFellingSurveyID IN (SELECT PostFellingSurveyID FROM PostFellingInspectionTeam WHERE RecorderID = ?)) ORDER BY t.StartDate) pf LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS cn ON pf.CreatorID = cn.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS tl ON pf.TeamLeaderID = tl.StaffID LEFT OUTER JOIN (SELECT StaffID, Name FROM Staff) AS ist ON pf.InspectionLeaderID = ist.StaffID");

		ps.setString(1, staff.getStaffID());
		ps.setString(2, staff.getStaffID());

		return getPostFellingSurveys(ps.executeQuery());
	}
	
	private int getTotalInventoryLine(long postFellingSurveyID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT COUNT(DISTINCT(LineNo)) as TotalInventoryLine FROM PostFellingSurveyCard WHERE PostFellingSurveyID = ?");
		ps.setLong(1, postFellingSurveyID);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("TotalInventoryLine");
	}
	
	int[] getPostFellingSurveyYearRange() throws SQLException
	{
		int[] range = new int[2];
		PreparedStatement ps = facade.prepareStatement("SELECT MIN(Year), MAX(Year) FROM PostFellingSurvey");
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			range[0] = rs.getInt(1);
			range[1] = rs.getInt(2);
		}

		return range;
	}
}