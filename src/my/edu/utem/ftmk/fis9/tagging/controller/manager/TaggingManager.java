package my.edu.utem.ftmk.fis9.tagging.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Hammer;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;

/**
 * @author Satrya Fajri Pratama
 */
class TaggingManager extends TaggingTableManager
{
	TaggingManager(TaggingFacade facade)
	{
		super(facade);
	}

	private void write(Tagging tagging, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, tagging.getYear());
		nullable(ps, 2, tagging.getStartDate());
		nullable(ps, 3, tagging.getEndDate());
		ps.setInt(4, toInt(tagging.isOpen()));
		ps.setDouble(5, tagging.getGrossVolumeLimit());
		ps.setDouble(6, tagging.getNetVolumeLimit());
		nullable(ps, 7, tagging.getHallID());
		ps.setString(8, tagging.getTeamLeaderID());
		nullable(ps, 9, tagging.getTenderNo());
		ps.setLong(10, tagging.getTaggingID());
	}

	int addTagging(Tagging tagging, boolean ignoreDuplicate) throws SQLException
	{
		String sql = (ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO Tagging (Year, StartDate, EndDate, Open, GrossVolumeLimit, NetVolumeLimit, HallID, TeamLeaderID, TenderNo, TaggingID, CreatorID, PreFellingSurveyID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE StartDate = ?, EndDate = ?, Open = ?, GrossVolumeLimit = ?, NetVolumeLimit = ?, HallID = ?, TeamLeaderID = ?, TenderNo = ?");
		PreparedStatement ps = facade.prepareStatement(sql);

		write(tagging, ps);
		ps.setString(11, tagging.getCreatorID());
		ps.setLong(12, tagging.getPreFellingSurveyID());

		if (!ignoreDuplicate)
		{
			nullable(ps, 13, tagging.getStartDate());
			nullable(ps, 14, tagging.getEndDate());
			ps.setInt(15, toInt(tagging.isOpen()));
			ps.setDouble(16, tagging.getGrossVolumeLimit());
			ps.setDouble(17, tagging.getNetVolumeLimit());
			nullable(ps, 18, tagging.getHallID());
			ps.setString(19, tagging.getTeamLeaderID());
			ps.setString(20, tagging.getTenderNo());
		}

		int status = ps.executeUpdate();

		if (status != 0 || !ignoreDuplicate)
		{
			if (status == 0)
				status = 1;

			setTaggingTeams(tagging);
			setTaggingHammers(tagging);
			facade.setTaggingLimitExceptions(tagging);
		}

		return status;
	}

	int updateTagging(Tagging tagging) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Tagging SET Year = ?, StartDate = ?, EndDate = ?, Open = ?, GrossVolumeLimit = ?, NetVolumeLimit = ?, HallID = ?, TeamLeaderID = ?, TenderNo = ? WHERE TaggingID = ?");

		write(tagging, ps);

		int status = ps.executeUpdate();
		int[] t_statuses = setTaggingTeams(tagging);
		int[] h_statuses = setTaggingHammers(tagging);
		int e_status = facade.setTaggingLimitExceptions(tagging);

		if (status == 0)
			for (int s : t_statuses)
				status += s;

		if (status == 0)
			for (int s : h_statuses)
				status += s;

		if (status == 0)
			status = e_status;

		return status;
	}

	int[] getTaggingYearRange() throws SQLException
	{
		int[] range = new int[2];
		PreparedStatement ps = facade.prepareStatement("SELECT MIN(Year), MAX(Year) FROM Tagging");
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			range[0] = rs.getInt(1);
			range[1] = rs.getInt(2);
		}

		return range;
	}

	private Tagging read(ResultSet rs) throws SQLException
	{
		Tagging tagging = new Tagging();

		tagging.setTaggingID(rs.getLong("TaggingID"));
		tagging.setStartDate(rs.getDate("StartDate"));
		tagging.setEndDate(rs.getDate("EndDate"));
		tagging.setOpen(toBoolean(rs.getInt("Open")));
		tagging.setGrossVolumeLimit(rs.getDouble("GrossVolumeLimit"));
		tagging.setNetVolumeLimit(rs.getDouble("NetVolumeLimit"));
		tagging.setHallID(rs.getLong("HallID"));
		tagging.setTenderNo(rs.getString("TenderNo"));
		tagging.setTeamLeaderID(rs.getString("TeamLeaderID"));
		tagging.setCreatorID(rs.getString("CreatorID"));
		tagging.setPreFellingSurveyID(rs.getLong("PreFellingSurveyID"));
		tagging.setComptBlockNo(rs.getString("ComptBlockNo"));
		tagging.setArea(rs.getDouble("Area"));
		tagging.setYear(rs.getInt("Year"));
		tagging.setForestID(rs.getInt("ForestID"));
		tagging.setRangeID(rs.getInt("RangeID"));
		tagging.setDistrictID(rs.getInt("DistrictID"));
		tagging.setStateID(rs.getInt("StateID"));
		tagging.setDipterocarpLimit(rs.getDouble("DipterocarpLimit"));
		tagging.setNonDipterocarpLimit(rs.getDouble("NonDipterocarpLimit"));
		tagging.setForestCode(rs.getString("ForestCode"));
		tagging.setForestName(rs.getString("ForestName"));
		tagging.setHallName(rs.getString("HallName"));
		tagging.setRangeName(rs.getString("RangeName"));
		tagging.setDistrictCode(rs.getString("DistrictCode"));
		tagging.setDistrictName(rs.getString("DistrictName"));
		tagging.setStateCode(rs.getString("StateCode"));
		tagging.setStateName(rs.getString("StateName"));
		tagging.setTeamLeaderName(rs.getString("TeamLeaderName"));
		tagging.setTeamLeaderDesignation(rs.getString("TeamLeaderDesignation"));
		tagging.setCreatorName(rs.getString("CreatorName"));

		return tagging;
	}

	private ArrayList<Tagging> getTaggings(ResultSet rs) throws SQLException
	{
		ArrayList<Tagging> taggings = new ArrayList<>();

		while (rs.next())
			taggings.add(read(rs));

		return taggings;
	}

	Tagging getTagging(long taggingID) throws SQLException
	{
		Tagging tagging = null;
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, co.DipterocarpLimit, co.NonDipterocarpLimit, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, a.Name AS CreatorName, h.Name AS HallName, tl.Name AS TeamLeaderName, g.Name AS TeamLeaderDesignation FROM Tagging t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s JOIN CuttingOption co JOIN Staff a LEFT JOIN Hall h ON t.HallID = h.HallID LEFT JOIN (Staff tl, Designation g) ON (t.TeamLeaderID = tl.StaffID AND tl.DesignationID = g.DesignationID) WHERE t.TaggingID = ? AND t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CuttingOptionID = co.CuttingOptionID AND v.CreatorID = a.StaffID ORDER BY t.StartDate");

		ps.setLong(1, taggingID);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			tagging = read(rs);

		return tagging;
	}

	ArrayList<Tagging> getTaggings(boolean closedOnly, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, co.DipterocarpLimit, co.NonDipterocarpLimit, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, a.Name AS CreatorName, h.Name AS HallName, tl.Name AS TeamLeaderName, g.Name AS TeamLeaderDesignation FROM Tagging t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s JOIN CuttingOption co JOIN Staff a LEFT JOIN Hall h ON t.HallID = h.HallID LEFT JOIN (Staff tl, Designation g) ON (t.TeamLeaderID = tl.StaffID AND tl.DesignationID = g.DesignationID) WHERE t.Year BETWEEN ? AND ? AND t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CuttingOptionID = co.CuttingOptionID AND v.CreatorID = a.StaffID " + (closedOnly ? "AND t.Open = 0 " : "") + "ORDER BY t.StartDate");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);

		return getTaggings(ps.executeQuery());
	}

	ArrayList<Tagging> getTaggings(State state, boolean closedOnly, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, co.DipterocarpLimit, co.NonDipterocarpLimit, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, a.Name AS CreatorName, h.Name AS HallName, tl.Name AS TeamLeaderName, g.Name AS TeamLeaderDesignation FROM Tagging t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s JOIN CuttingOption co JOIN Staff a LEFT JOIN Hall h ON t.HallID = h.HallID LEFT JOIN (Staff tl, Designation g) ON (t.TeamLeaderID = tl.StaffID AND tl.DesignationID = g.DesignationID) WHERE t.Year BETWEEN ? AND ? AND t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND s.StateID = ? AND v.CuttingOptionID = co.CuttingOptionID AND v.CreatorID = a.StaffID " + (closedOnly ? "AND t.Open = 0 " : "") + "ORDER BY t.StartDate");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setInt(3, state.getStateID());

		return getTaggings(ps.executeQuery());
	}

	ArrayList<Tagging> getTaggings(District district, boolean closedOnly, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, co.DipterocarpLimit, co.NonDipterocarpLimit, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, a.Name AS CreatorName, h.Name AS HallName, tl.Name AS TeamLeaderName, g.Name AS TeamLeaderDesignation FROM Tagging t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s JOIN CuttingOption co JOIN Staff a LEFT JOIN Hall h ON t.HallID = h.HallID LEFT JOIN (Staff tl, Designation g) ON (t.TeamLeaderID = tl.StaffID AND tl.DesignationID = g.DesignationID) WHERE t.Year BETWEEN ? AND ? AND t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = ? AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CuttingOptionID = co.CuttingOptionID AND v.CreatorID = a.StaffID " + (closedOnly ? "AND t.Open = 0 " : "") + "ORDER BY t.StartDate");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setInt(3, district.getDistrictID());

		return getTaggings(ps.executeQuery());
	}

	ArrayList<Tagging> getTaggings(Range range, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, co.DipterocarpLimit, co.NonDipterocarpLimit, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, a.Name AS CreatorName, h.Name AS HallName, tl.Name AS TeamLeaderName, g.Name AS TeamLeaderDesignation FROM Tagging t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s JOIN CuttingOption co JOIN Staff a LEFT JOIN Hall h ON t.HallID = h.HallID LEFT JOIN (Staff tl, Designation g) ON (t.TeamLeaderID = tl.StaffID AND tl.DesignationID = g.DesignationID) WHERE t.Year BETWEEN ? AND ? AND t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = ? AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CuttingOptionID = co.CuttingOptionID AND v.CreatorID = a.StaffID ORDER BY t.StartDate");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setInt(3, range.getRangeID());

		return getTaggings(ps.executeQuery());
	}

	ArrayList<Tagging> getTaggings(Staff staff, int startYear, int endYear) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT t.*, v.ComptBlockNo, v.Area, v.ForestID, v.RangeID, d.DistrictID, s.StateID, co.DipterocarpLimit, co.NonDipterocarpLimit, f.Code AS ForestCode, f.Name AS ForestName, r.Name AS RangeName, d.Code AS DistrictCode, d.Name AS DistrictName, s.Code AS StateCode, s.Name AS StateName, a.Name AS CreatorName, h.Name AS HallName, tl.Name AS TeamLeaderName, g.Name AS TeamLeaderDesignation FROM Tagging t JOIN PreFellingSurvey v JOIN Forest f JOIN `Range` r JOIN District d JOIN State s JOIN CuttingOption co JOIN Staff a LEFT JOIN Hall h ON t.HallID = h.HallID LEFT JOIN (Staff tl, Designation g) ON (t.TeamLeaderID = tl.StaffID AND tl.DesignationID = g.DesignationID) WHERE t.Year BETWEEN ? AND ? AND t.PreFellingSurveyID = v.PreFellingSurveyID AND v.ForestID = f.ForestID AND v.RangeID = r.RangeID AND f.DistrictID = d.DistrictID AND d.StateID = s.StateID AND v.CuttingOptionID = co.CuttingOptionID AND v.CreatorID = a.StaffID AND (t.TeamLeaderID = ? OR t.TaggingID IN (SELECT TaggingID FROM TaggingTeam WHERE RecorderID = ?) OR t.TenderNo IN (SELECT TenderNo FROM Tender WHERE ContractorID = (SELECT ContractorID FROM Staff WHERE StaffID = ?))) ORDER BY t.StartDate");

		ps.setInt(1, startYear);
		ps.setInt(2, endYear);
		ps.setString(3, staff.getStaffID());
		ps.setString(4, staff.getStaffID());
		ps.setString(5, staff.getStaffID());

		return getTaggings(ps.executeQuery());
	}

	private int[] setTaggingTeams(Tagging tagging) throws SQLException
	{
		ArrayList<Staff> recorders = tagging.getRecorders();
		PreparedStatement clear = facade.prepareStatement("DELETE FROM TaggingTeam WHERE TaggingID = ?");

		clear.setLong(1, tagging.getTaggingID());
		clear.executeUpdate();

		int[] statuses = null;

		if (recorders != null && !recorders.isEmpty())
		{
			PreparedStatement batch = facade.prepareStatement("INSERT INTO TaggingTeam VALUES (?, ?)");

			for (Staff recorder : recorders)
			{
				batch.setString(1, recorder.getStaffID());
				batch.setLong(2, tagging.getTaggingID());
				batch.addBatch();
			}

			statuses = batch.executeBatch();
		}
		else
			statuses = new int[0];

		return statuses;
	}

	private int[] setTaggingHammers(Tagging tagging) throws SQLException
	{
		ArrayList<Hammer> hammers = tagging.getHammers();
		PreparedStatement clear = facade.prepareStatement("DELETE FROM TaggingHammer WHERE TaggingID = ?");

		clear.setLong(1, tagging.getTaggingID());
		clear.executeUpdate();

		int[] statuses = null;

		if (hammers != null && !hammers.isEmpty())
		{
			PreparedStatement batch = facade.prepareStatement("INSERT INTO TaggingHammer VALUES (?, ?)");

			for (Hammer hammer : hammers)
			{
				batch.setString(1, hammer.getHammerNo());
				batch.setLong(2, tagging.getTaggingID());
				batch.addBatch();
			}

			statuses = batch.executeBatch();
		}
		else
			statuses = new int[0];

		return statuses;
	}
}