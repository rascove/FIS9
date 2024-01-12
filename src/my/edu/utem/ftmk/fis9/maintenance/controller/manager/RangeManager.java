package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.District;

/**
 * @author Satrya Fajri Pratama
 */
class RangeManager extends MaintenanceTableManager
{
	RangeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Range range, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, range.getName());
		ps.setString(2, range.getAddress());
		ps.setInt(3, range.getDistrictID());
		ps.setString(4, range.getAsstOfficerID());

		if (range.getRangeID() != 0)
			ps.setInt(5, range.getRangeID());

		return ps.executeUpdate();
	}

	int addRange(Range range) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO `Range` (Name, Address, DistrictID, AsstOfficerID) VALUES (?, ?, ?, ?)");
		int status = write(range, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				range.setRangeID(rs.getInt(1));
		}

		return status;
	}

	int updateRange(Range range) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE `Range` SET Name = ?, Address = ?, DistrictID = ?, AsstOfficerID = ? WHERE RangeID = ?");

		return write(range, ps);
	}

	private Range read(ResultSet rs) throws SQLException
	{
		Range range = new Range();

		range.setRangeID(rs.getInt("RangeID"));
		range.setName(rs.getString("Name"));
		range.setAddress(rs.getString("Address"));
		range.setDistrictID(rs.getInt("DistrictID"));
		range.setStateID(rs.getInt("StateID"));
		range.setDistrictName(rs.getString("DistrictName"));
		range.setStateName(rs.getString("StateName"));
		range.setAsstOfficerID(rs.getString("AsstOfficerID"));
		range.setAsstOfficerName(rs.getString("AsstOfficerName"));

		return range;
	}

	private ArrayList<Range> getRanges(ResultSet rs) throws SQLException
	{
		ArrayList<Range> ranges = new ArrayList<>();

		while (rs.next())
			ranges.add(read(rs));

		return ranges;
	}

	Range getRange(int rangeID) throws SQLException
	{
		Range range = null;
		PreparedStatement ps = facade.prepareStatement("SELECT r.*, s.StateID, d.Name AS DistrictName, s.Name AS StateName, f.Name AS AsstOfficerName FROM `Range` r, District d, State s, Staff f WHERE r.RangeID = ? AND r.DistrictID = d.DistrictID AND d.StateID = s.StateID AND r.AsstOfficerID = f.StaffID");

		ps.setInt(1, rangeID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			range = read(rs);
		
		return range;
	}
	
	Range getRange(Staff staff) throws SQLException
	{
		Range range = null;
		PreparedStatement ps = facade.prepareStatement("SELECT r.*, s.StateID, d.Name AS DistrictName, s.Name AS StateName, f.Name AS AsstOfficerName FROM `Range` r, District d, State s, Staff f WHERE r.AsstOfficerID = ? AND r.DistrictID = d.DistrictID AND d.StateID = s.StateID AND r.AsstOfficerID = f.StaffID");

		ps.setString(1, staff.getStaffID());

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			range = read(rs);
		
		return range;
	}
	
	ArrayList<Range> getRanges(District district) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT r.*, s.StateID, d.Name AS DistrictName, s.Name AS StateName, f.Name AS AsstOfficerName FROM `Range` r, District d, State s, Staff f WHERE r.DistrictID = ? AND r.DistrictID = d.DistrictID AND d.StateID = s.StateID AND r.AsstOfficerID = f.StaffID ORDER BY r.Name");

		ps.setInt(1, district.getDistrictID());

		return getRanges(ps.executeQuery());
	}
}