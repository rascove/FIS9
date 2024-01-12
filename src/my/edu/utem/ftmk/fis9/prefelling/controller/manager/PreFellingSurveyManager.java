package my.edu.utem.ftmk.fis9.prefelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;

/**
 * @author Satrya Fajri Pratama
 */
class PreFellingSurveyManager extends PreFellingTableManager
{
	PreFellingSurveyManager(PreFellingFacade facade)
	{
		super(facade);
	}

	private void write(PreFellingSurvey preFellingSurvey, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, preFellingSurvey.getComptBlockNo());
		ps.setDouble(2, preFellingSurvey.getArea());
		ps.setInt(3, preFellingSurvey.getYear());
		nullable(ps, 4, preFellingSurvey.getStartDate());
		nullable(ps, 5, preFellingSurvey.getEndDate());
		ps.setInt(6, toInt(preFellingSurvey.isOpen()));
		nullable(ps, 7, preFellingSurvey.getCuttingOptionID());
		ps.setInt(8, preFellingSurvey.getForestID());
		nullable(ps, 9, preFellingSurvey.getRangeID());
		ps.setString(10, preFellingSurvey.getTeamLeaderID());
		nullable(ps, 11, preFellingSurvey.getTenderNo());
		ps.setLong(12, preFellingSurvey.getPreFellingSurveyID());
	}

	int addPreFellingSurvey(PreFellingSurvey preFellingSurvey, boolean ignoreDuplicate) throws SQLException
	{
		String sql = (ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO PreFellingSurvey (ComptBlockNo, Area, Year, StartDate, EndDate, Open, CuttingOptionID, ForestID, RangeID, TeamLeaderID, TenderNo, PreFellingSurveyID, CreatorID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE StartDate = ?, EndDate = ?, Open = ?, CuttingOptionID = ?, ForestID = ?, RangeID = ?, TeamLeaderID = ?, TenderNo = ?");
		PreparedStatement ps = facade.prepareStatement(sql);

		write(preFellingSurvey, ps);
		ps.setString(13, preFellingSurvey.getCreatorID());

		if (!ignoreDuplicate)
		{
			nullable(ps, 14, preFellingSurvey.getStartDate());
			nullable(ps, 15, preFellingSurvey.getEndDate());
			ps.setInt(16, toInt(preFellingSurvey.isOpen()));
			nullable(ps, 17, preFellingSurvey.getCuttingOptionID());
			ps.setInt(18, preFellingSurvey.getForestID());
			nullable(ps, 19, preFellingSurvey.getRangeID());
			ps.setString(20, preFellingSurvey.getTeamLeaderID());
			ps.setString(21, preFellingSurvey.getTenderNo());
		}

		int status = ps.executeUpdate();

		if (status != 0 || !ignoreDuplicate)
		{
			if (status == 0)
				status = 1;

			setPreFellingSurveyTeams(preFellingSurvey);
			facade.addCuttingLimits(preFellingSurvey);
		}

		return status;
	}

	int updatePreFellingSurvey(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE PreFellingSurvey SET ComptBlockNo = ?, Area = ?, Year = ?, StartDate = ?, EndDate = ?, Open = ?, CuttingOptionID = ?, ForestID = ?, RangeID = ?, TeamLeaderID = ?, TenderNo = ? WHERE PreFellingSurveyID = ?");

		write(preFellingSurvey, ps);

		int status = ps.executeUpdate();
		int[] t_statuses = setPreFellingSurveyTeams(preFellingSurvey);
		int[] c_statuses = facade.addCuttingLimits(preFellingSurvey);
		
		if (status == 0 && t_statuses != null)
			for (int s : t_statuses)
				status += s;
		
		if (status == 0 && c_statuses != null)
			for (int s : c_statuses)
				status += s;

		return status;
	}

	int[] getPreFellingSurveyYearRange() throws SQLException
	{
		int[] range = new int[2];
		PreparedStatement ps = facade.prepareStatement("SELECT MIN(Year), MAX(Year) FROM PreFellingSurvey");
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			range[0] = rs.getInt(1);
			range[1] = rs.getInt(2);
		}

		return range;
	}

	private PreFellingSurvey read(ResultSet rs) throws SQLException
	{
		PreFellingSurvey preFellingSurvey = new PreFellingSurvey();

		preFellingSurvey.setPreFellingSurveyID(rs.getLong("PreFellingSurveyID"));
		preFellingSurvey.setComptBlockNo(rs.getString("ComptBlockNo"));
		preFellingSurvey.setArea(rs.getDouble("Area"));
		preFellingSurvey.setYear(rs.getInt("Year"));
		preFellingSurvey.setStartDate(rs.getDate("StartDate"));
		preFellingSurvey.setEndDate(rs.getDate("EndDate"));
		preFellingSurvey.setOpen(toBoolean(rs.getInt("Open")));
		preFellingSurvey.setCuttingOptionID(rs.getInt("CuttingOptionID"));
		preFellingSurvey.setForestID(rs.getInt("ForestID"));
		preFellingSurvey.setRangeID(rs.getInt("RangeID"));
		preFellingSurvey.setTenderNo(rs.getString("TenderNo"));
		preFellingSurvey.setTeamLeaderID(rs.getString("TeamLeaderID"));
		preFellingSurvey.setCreatorID(rs.getString("CreatorID"));
		preFellingSurvey.setDipterocarpLimit(rs.getDouble("DipterocarpLimit"));
		preFellingSurvey.setNonDipterocarpLimit(rs.getDouble("NonDipterocarpLimit"));
		preFellingSurvey.setDistrictID(rs.getInt("DistrictID"));
		preFellingSurvey.setStateID(rs.getInt("StateID"));
		preFellingSurvey.setForestCode(rs.getString("ForestCode"));
		preFellingSurvey.setForestName(rs.getString("ForestName"));
		preFellingSurvey.setRangeName(rs.getString("RangeName"));
		preFellingSurvey.setDistrictCode(rs.getString("DistrictCode"));
		preFellingSurvey.setDistrictName(rs.getString("DistrictName"));
		preFellingSurvey.setStateCode(rs.getString("StateCode"));
		preFellingSurvey.setStateName(rs.getString("StateName"));
		preFellingSurvey.setTeamLeaderName(rs.getString("TeamLeaderName"));
		preFellingSurvey.setCreatorName(rs.getString("CreatorName"));
		preFellingSurvey.setTaggingCreated(rs.getLong("TaggingID") != 0);
		preFellingSurvey.setPostFellingCreated(rs.getLong("PostFellingSurveyID") != 0);

		if (!preFellingSurvey.isOpen())
			preFellingSurvey.setCuttingLimits(facade.getCuttingLimits(preFellingSurvey));

		return preFellingSurvey;
	}

	private ArrayList<PreFellingSurvey> getPreFellingSurveys(ResultSet rs) throws SQLException
	{
		ArrayList<PreFellingSurvey> preFellingSurveys = new ArrayList<>();

		while (rs.next())
			preFellingSurveys.add(read(rs));

		return preFellingSurveys;
	}

	PreFellingSurvey getPreFellingSurvey(long preFellingSurveyID) throws SQLException
	{
		PreFellingSurvey preFellingSurvey = null;
		PreparedStatement ps = facade.prepareStatement("SELECT v.*, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, t.Name AS CreatorName, co.DipterocarpLimit, co.NonDipterocarpLimit, r.Name AS RangeName, s1.Name AS TeamLeaderName, g.TaggingID, pf.PreFellingSurveyID AS PostFellingSurveyID FROM PreFellingSurvey v JOIN Forest f JOIN District d JOIN State s JOIN Staff t LEFT JOIN CuttingOption co ON v.CuttingOptionID = co.CuttingOptionID LEFT JOIN `Range` r ON v.RangeID = r.RangeID LEFT JOIN Staff s1 ON v.TeamLeaderID = s1.StaffID LEFT JOIN Tagging g ON v.PreFellingSurveyID = g.PreFellingSurveyID LEFT JOIN PostFellingSurvey pf ON v.PreFellingSurveyID = pf.PreFellingSurveyID WHERE v.PreFellingSurveyID = ? AND v.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CreatorID = t.StaffID");

		ps.setLong(1, preFellingSurveyID);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			preFellingSurvey = read(rs);

		return preFellingSurvey;
	}

	ArrayList<PreFellingSurvey> getPreFellingSurveys(int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT v.*, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, t.Name AS CreatorName, co.DipterocarpLimit, co.NonDipterocarpLimit, r.Name AS RangeName, s1.Name AS TeamLeaderName, g.TaggingID, pf.PreFellingSurveyID AS PostFellingSurveyID FROM PreFellingSurvey v JOIN Forest f JOIN District d JOIN State s JOIN Staff t LEFT JOIN CuttingOption co ON v.CuttingOptionID = co.CuttingOptionID LEFT JOIN `Range` r ON v.RangeID = r.RangeID LEFT JOIN Staff s1 ON v.TeamLeaderID = s1.StaffID LEFT JOIN Tagging g ON v.PreFellingSurveyID = g.PreFellingSurveyID LEFT JOIN PostFellingSurvey pf ON v.PreFellingSurveyID = pf.PreFellingSurveyID WHERE v.Year BETWEEN ? AND ? AND v.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CreatorID = t.StaffID ORDER BY v.StartDate");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);

		return getPreFellingSurveys(ps.executeQuery());
	}

	ArrayList<PreFellingSurvey> getPreFellingSurveys(State state, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT v.*, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, t.Name AS CreatorName, co.DipterocarpLimit, co.NonDipterocarpLimit, r.Name AS RangeName, s1.Name AS TeamLeaderName, g.TaggingID, pf.PreFellingSurveyID AS PostFellingSurveyID FROM PreFellingSurvey v JOIN Forest f JOIN District d JOIN State s JOIN Staff t LEFT JOIN CuttingOption co ON v.CuttingOptionID = co.CuttingOptionID LEFT JOIN `Range` r ON v.RangeID = r.RangeID LEFT JOIN Staff s1 ON v.TeamLeaderID = s1.StaffID LEFT JOIN Tagging g ON v.PreFellingSurveyID = g.PreFellingSurveyID LEFT JOIN PostFellingSurvey pf ON v.PreFellingSurveyID = pf.PreFellingSurveyID WHERE v.Year BETWEEN ? AND ? AND v.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND s.StateID = ? AND v.CreatorID = t.StaffID ORDER BY v.StartDate");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setInt(3, state.getStateID());

		return getPreFellingSurveys(ps.executeQuery());
	}

	ArrayList<PreFellingSurvey> getPreFellingSurveys(District district, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT v.*, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, t.Name AS CreatorName, co.DipterocarpLimit, co.NonDipterocarpLimit, r.Name AS RangeName, s1.Name AS TeamLeaderName, g.TaggingID, pf.PreFellingSurveyID AS PostFellingSurveyID FROM PreFellingSurvey v JOIN Forest f JOIN District d JOIN State s JOIN Staff t LEFT JOIN CuttingOption co ON v.CuttingOptionID = co.CuttingOptionID LEFT JOIN `Range` r ON v.RangeID = r.RangeID LEFT JOIN Staff s1 ON v.TeamLeaderID = s1.StaffID LEFT JOIN Tagging g ON v.PreFellingSurveyID = g.PreFellingSurveyID LEFT JOIN PostFellingSurvey pf ON v.PreFellingSurveyID = pf.PreFellingSurveyID WHERE v.Year BETWEEN ? AND ? AND v.ForestID = f.ForestID AND f.DistrictID = ? AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CreatorID = t.StaffID ORDER BY v.StartDate");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setInt(3, district.getDistrictID());

		return getPreFellingSurveys(ps.executeQuery());
	}

	ArrayList<PreFellingSurvey> getPreFellingSurveys(Range range, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT v.*, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, t.Name AS CreatorName, co.DipterocarpLimit, co.NonDipterocarpLimit, r.Name AS RangeName, s1.Name AS TeamLeaderName, g.TaggingID, pf.PreFellingSurveyID AS PostFellingSurveyID FROM PreFellingSurvey v JOIN Forest f JOIN District d JOIN State s JOIN Staff t JOIN `Range` r LEFT JOIN CuttingOption co ON v.CuttingOptionID = co.CuttingOptionID LEFT JOIN Staff s1 ON v.TeamLeaderID = s1.StaffID LEFT JOIN Tagging g ON v.PreFellingSurveyID = g.PreFellingSurveyID LEFT JOIN PostFellingSurvey pf ON v.PreFellingSurveyID = pf.PreFellingSurveyID WHERE v.Year BETWEEN ? AND ? AND v.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CreatorID = t.StaffID AND v.RangeID = ? AND v.RangeID = r.RangeID ORDER BY v.StartDate");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setInt(3, range.getRangeID());

		return getPreFellingSurveys(ps.executeQuery());
	}

	ArrayList<PreFellingSurvey> getPreFellingSurveys(Staff staff, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT v.*, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, t.Name AS CreatorName, co.DipterocarpLimit, co.NonDipterocarpLimit, r.Name AS RangeName, s1.Name AS TeamLeaderName, g.TaggingID, pf.PreFellingSurveyID AS PostFellingSurveyID FROM PreFellingSurvey v JOIN Forest f JOIN District d JOIN State s JOIN Staff t LEFT JOIN CuttingOption co ON v.CuttingOptionID = co.CuttingOptionID LEFT JOIN `Range` r ON v.RangeID = r.RangeID LEFT JOIN Staff s1 ON v.TeamLeaderID = s1.StaffID LEFT JOIN Tagging g ON v.PreFellingSurveyID = g.PreFellingSurveyID LEFT JOIN PostFellingSurvey pf ON v.PreFellingSurveyID = pf.PreFellingSurveyID WHERE v.Year BETWEEN ? AND ? AND v.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CreatorID = t.StaffID AND (v.TeamLeaderID = ? OR v.PreFellingSurveyID IN (SELECT PreFellingSurveyID FROM PreFellingSurveyTeam WHERE RecorderID = ?) OR v.TenderNo IN (SELECT TenderNo FROM Tender WHERE ContractorID = (SELECT ContractorID FROM Staff WHERE StaffID = ?))) ORDER BY v.StartDate");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setString(3, staff.getStaffID());
		ps.setString(4, staff.getStaffID());
		ps.setString(5, staff.getStaffID());

		return getPreFellingSurveys(ps.executeQuery());
	}

	ArrayList<PreFellingSurvey> getPreFellingSurveys(boolean forTagging) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT v.*, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, t.Name AS CreatorName, co.DipterocarpLimit, co.NonDipterocarpLimit, r.Name AS RangeName, s1.Name AS TeamLeaderName, g.TaggingID, pf.PreFellingSurveyID AS PostFellingSurveyID FROM PreFellingSurvey v JOIN Forest f JOIN District d JOIN State s JOIN Staff t LEFT JOIN CuttingOption co ON v.CuttingOptionID = co.CuttingOptionID LEFT JOIN `Range` r ON v.RangeID = r.RangeID LEFT JOIN Staff s1 ON v.TeamLeaderID = s1.StaffID LEFT JOIN Tagging g ON v.PreFellingSurveyID = g.PreFellingSurveyID LEFT JOIN PostFellingSurvey pf ON v.PreFellingSurveyID = pf.PreFellingSurveyID WHERE v.Open = 0 AND v.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CreatorID = t.StaffID AND g.TaggingID" + (forTagging ? "" : " IS NOT NULL AND g.Open = 0 AND pf.PreFellingSurveyID") + " IS NULL ORDER BY v.StartDate");

		return getPreFellingSurveys(ps.executeQuery());
	}

	ArrayList<PreFellingSurvey> getPreFellingSurveys(State state, boolean forTagging) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT v.*, d.DistrictID, s.StateID, f.Code AS ForestCode, f.Name AS ForestName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, t.Name AS CreatorName, co.DipterocarpLimit, co.NonDipterocarpLimit, r.Name AS RangeName, s1.Name AS TeamLeaderName, g.TaggingID, pf.PreFellingSurveyID AS PostFellingSurveyID FROM PreFellingSurvey v JOIN Forest f JOIN District d JOIN State s JOIN Staff t LEFT JOIN CuttingOption co ON v.CuttingOptionID = co.CuttingOptionID LEFT JOIN `Range` r ON v.RangeID = r.RangeID LEFT JOIN Staff s1 ON v.TeamLeaderID = s1.StaffID LEFT JOIN Tagging g ON v.PreFellingSurveyID = g.PreFellingSurveyID LEFT JOIN PostFellingSurvey pf ON v.PreFellingSurveyID = pf.PreFellingSurveyID WHERE v.Open = 0 AND v.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND s.StateID = ? AND v.CreatorID = t.StaffID AND g.TaggingID" + (forTagging ? "" : " IS NOT NULL AND g.Open = 0 AND pf.PreFellingSurveyID") + " IS NULL ORDER BY v.StartDate");

		ps.setInt(1, state.getStateID());

		return getPreFellingSurveys(ps.executeQuery());
	}

	private int[] setPreFellingSurveyTeams(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		ArrayList<Staff> recorders = preFellingSurvey.getRecorders();
		PreparedStatement clear = facade.prepareStatement("DELETE FROM PreFellingSurveyTeam WHERE PreFellingSurveyID = ?");

		clear.setLong(1, preFellingSurvey.getPreFellingSurveyID());
		clear.executeUpdate();

		int[] statuses = null;
		
		if (recorders != null && !recorders.isEmpty())
		{
			PreparedStatement batch = facade.prepareStatement("INSERT INTO PreFellingSurveyTeam VALUES (?, ?)");

			for (Staff recorder : recorders)
			{
				batch.setString(1, recorder.getStaffID());
				batch.setLong(2, preFellingSurvey.getPreFellingSurveyID());
				batch.addBatch();
			}

			statuses = batch.executeBatch();
		}
		else
			statuses = new int[0];
		
		return statuses;
	}
}