package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.HallOfficer;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class DistrictManager extends MaintenanceTableManager
{
	DistrictManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(District district, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, district.getCode());
		ps.setString(2, district.getName());
		ps.setString(3, district.getAddress());
		ps.setInt(4, district.getStateID());
		ps.setString(5, district.getOfficerID());
		ps.setString(6, district.getAsstOfficerID());
		nullable(ps, 7, district.getClerk1ID());
		nullable(ps, 8, district.getClerk2ID());
		nullable(ps, 9, district.getClerk3ID());

		if (district.getDistrictID() != 0)
			ps.setInt(10, district.getDistrictID());

		return ps.executeUpdate();
	}

	int addDistrict(District district) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO District (Code, Name, Address, StateID, OfficerID, AsstOfficerID, Clerk1ID, Clerk2ID, Clerk3ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		int status = write(district, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				district.setDistrictID(rs.getInt(1));
		}

		return status;
	}

	int updateDistrict(District district) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE District SET Code = ?, Name = ?, Address = ?, StateID = ?, OfficerID = ?, AsstOfficerID = ?, Clerk1ID = ?, Clerk2ID = ?, Clerk3ID = ? WHERE DistrictID = ?");

		return write(district, ps);
	}

	private District read(ResultSet rs) throws SQLException
	{
		District district = new District();

		district.setDistrictID(rs.getInt("DistrictID"));
		district.setCode(rs.getString("Code"));
		district.setName(rs.getString("Name"));
		district.setAddress(rs.getString("Address"));
		district.setStateID(rs.getInt("StateID"));
		district.setOfficerID(rs.getString("OfficerID"));
		district.setAsstOfficerID(rs.getString("AsstOfficerID"));
		district.setClerk1ID(rs.getString("Clerk1ID"));
		district.setClerk2ID(rs.getString("Clerk2ID"));
		district.setClerk3ID(rs.getString("Clerk3ID"));
		district.setStateCode(rs.getString("StateCode"));
		district.setStateName(rs.getString("StateName"));
		district.setOfficerName(rs.getString("OfficerName"));
		district.setAsstOfficerName(rs.getString("AsstOfficerName"));
		district.setClerk1Name(rs.getString("Clerk1Name"));
		district.setClerk2Name(rs.getString("Clerk2Name"));
		district.setClerk3Name(rs.getString("Clerk3Name"));
		district.setRanges(facade.getRanges(district));
		district.setHalls(facade.getHalls(district));

		return district;
	}

	private ArrayList<District> getDistricts(ResultSet rs) throws SQLException
	{
		ArrayList<District> districts = new ArrayList<>();

		while (rs.next())
			districts.add(read(rs));

		return districts;
	}

	District getDistrict(int districtID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT d.*, s.Code AS StateCode, s.Name AS StateName, s1.Name AS OfficerName, s2.Name AS AsstOfficerName, s3.Name AS Clerk1Name, s4.Name AS Clerk2Name, s5.Name AS Clerk3Name FROM District d JOIN State s JOIN Staff s1 LEFT JOIN Staff s2 ON d.AsstOfficerID = s2.StaffID LEFT JOIN Staff s3 ON d.Clerk1ID = s3.StaffID LEFT JOIN Staff s4 ON d.Clerk2ID = s4.StaffID LEFT JOIN Staff s5 ON d.Clerk3ID = s5.StaffID WHERE d.DistrictID = ? AND d.StateID = s.StateID AND d.OfficerID = s1.StaffID");

		ps.setInt(1, districtID);

		ResultSet rs = ps.executeQuery();
		District district = null;

		if (rs.next())
			district = read(rs);

		return district;
	}

	District getDistrict(long hallID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT d.*, s.Code AS StateCode, s.Name AS StateName, s1.Name AS OfficerName, s2.Name AS AsstOfficerName, s3.Name AS Clerk1Name, s4.Name AS Clerk2Name, s5.Name AS Clerk3Name FROM District d JOIN State s JOIN Staff s1 LEFT JOIN Staff s2 ON d.AsstOfficerID = s2.StaffID LEFT JOIN Staff s3 ON d.Clerk1ID = s3.StaffID LEFT JOIN Staff s4 ON d.Clerk2ID = s4.StaffID LEFT JOIN Staff s5 ON d.Clerk3ID = s5.StaffID WHERE d.DistrictID = (SELECT DistrictID FROM Hall WHERE HallID = ?) AND d.StateID = s.StateID AND d.OfficerID = s1.StaffID");

		ps.setLong(1, hallID);

		ResultSet rs = ps.executeQuery();
		District district = null;

		if (rs.next())
			district = read(rs);

		return district;
	}

	District getDistrict(Staff staff) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT d.*, s.Code AS StateCode, s.Name AS StateName, s1.Name AS OfficerName, s2.Name AS AsstOfficerName, s3.Name AS Clerk1Name, s4.Name AS Clerk2Name, s5.Name AS Clerk3Name FROM District d JOIN State s JOIN Staff s1 LEFT JOIN Staff s2 ON d.AsstOfficerID = s2.StaffID LEFT JOIN Staff s3 ON d.Clerk1ID = s3.StaffID LEFT JOIN Staff s4 ON d.Clerk2ID = s4.StaffID LEFT JOIN Staff s5 ON d.Clerk3ID = s5.StaffID WHERE ((d.OfficerID = ? OR d.AsstOfficerID = ? OR d.Clerk1ID = ? OR d.Clerk2ID = ? OR d.Clerk3ID = ?) OR d.DistrictID = (SELECT DistrictID FROM Hall WHERE HallID = (SELECT HallID FROM HallOfficer WHERE StaffID = ? AND EndDate >= CURDATE() AND Status = 'A'))) AND d.StateID = s.StateID AND d.OfficerID = s1.StaffID");

		ps.setString(1, staff.getStaffID());
		ps.setString(2, staff.getStaffID());
		ps.setString(3, staff.getStaffID());
		ps.setString(4, staff.getStaffID());
		ps.setString(5, staff.getStaffID());
		ps.setString(6, staff.getStaffID());

		ResultSet rs = ps.executeQuery();
		District district = null;

		if (rs.next())
			district = read(rs);

		return district;
	}

	ArrayList<District> getDistricts(HallOfficer hallOfficer) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT d.* " + 
				"FROM HallOfficer ho LEFT JOIN Hall h ON ho.HallID = h.HallID LEFT JOIN District d ON h.DistrictID = d.DistrictID " + 
				"WHERE ho.StaffID = ? AND ho.Status = 'A'");

		ps.setString(1, hallOfficer.getStaffID());

		return getDistricts(ps.executeQuery());
	}

	ArrayList<District> getDistricts() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT d.*, s.Code AS StateCode, s.Name AS StateName, s1.Name AS OfficerName, s2.Name AS AsstOfficerName, s3.Name AS Clerk1Name, s4.Name AS Clerk2Name, s5.Name AS Clerk3Name FROM District d JOIN State s JOIN Staff s1 LEFT JOIN Staff s2 ON d.AsstOfficerID = s2.StaffID LEFT JOIN Staff s3 ON d.Clerk1ID = s3.StaffID LEFT JOIN Staff s4 ON d.Clerk2ID = s4.StaffID LEFT JOIN Staff s5 ON d.Clerk3ID = s5.StaffID WHERE d.StateID = s.StateID AND d.OfficerID = s1.StaffID ORDER BY d.Code");

		return getDistricts(ps.executeQuery());
	}

	ArrayList<District> getDistricts(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT d.*, s.Code AS StateCode, s.Name AS StateName, s1.Name AS OfficerName, s2.Name AS AsstOfficerName, s3.Name AS Clerk1Name, s4.Name AS Clerk2Name, s5.Name AS Clerk3Name FROM District d JOIN State s JOIN Staff s1 LEFT JOIN Staff s2 ON d.AsstOfficerID = s2.StaffID LEFT JOIN Staff s3 ON d.Clerk1ID = s3.StaffID LEFT JOIN Staff s4 ON d.Clerk2ID = s4.StaffID LEFT JOIN Staff s5 ON d.Clerk3ID = s5.StaffID WHERE d.StateID = ? AND d.StateID = s.StateID AND d.OfficerID = s1.StaffID ORDER BY d.Code");

		ps.setInt(1, state.getStateID());

		return getDistricts(ps.executeQuery());
	}
}