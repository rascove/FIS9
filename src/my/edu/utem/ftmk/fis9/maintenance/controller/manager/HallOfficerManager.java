package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.HallOfficer;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Nor Azman Mat Ariff
 */
class HallOfficerManager extends MaintenanceTableManager
{
	HallOfficerManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(HallOfficer hallOfficer, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, hallOfficer.getStaffID());
		ps.setString(2, hallOfficer.getHammerNo());
		ps.setDate(3, toSQLDate(hallOfficer.getStartDate()));
		ps.setDate(4, toSQLDate(hallOfficer.getEndDate()));
		ps.setLong(5, hallOfficer.getHallID());
		ps.setString(6, hallOfficer.getStatus());
		ps.setLong(7, hallOfficer.getHallOfficerID());

		return ps.executeUpdate();
	}

	int addHallOfficer(HallOfficer hallOfficer) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO HallOfficer (StaffID, HammerNo, StartDate, EndDate, HallID, Status, HallOfficerID) VALUES (?, ?, ?, ?, ?, ?, ?)");

		return write(hallOfficer, ps);
	}
	
	int updateHallOfficer(HallOfficer hallOfficer) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE HallOfficer SET StaffID = ?, HammerNo = ?, Status = ? WHERE HallOfficerID = ?");
		
		return write(hallOfficer, ps);
	}
	
	int deleteHallOfficer(HallOfficer hallOfficer) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE HallOfficer SET Status = NULL WHERE HallOfficerID = ?");
		ps.setLong(1, hallOfficer.getHallOfficerID());
		
		return ps.executeUpdate();
	}
	
	int updateExpirationStatusHallOfficer(HallOfficer hallOfficer) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE HallOfficer SET Status = 'E' WHERE HallOfficerID = ? AND Status ='A'");
		ps.setString(1, hallOfficer.getStaffID());

		return ps.executeUpdate();
	}

	private HallOfficer read(ResultSet rs) throws SQLException
	{
		HallOfficer hallOfficer = new HallOfficer();

		hallOfficer.setHallOfficerID(rs.getLong("HallOfficerID"));
		hallOfficer.setStaffID(rs.getString("StaffID"));
		hallOfficer.setHallOfficerName(rs.getString("HallOfficerName"));
		hallOfficer.setHammerNo(rs.getString("HammerNo"));
		hallOfficer.setDistrictID(rs.getInt("districtID"));
		hallOfficer.setStartDate(rs.getDate("StartDate"));
		hallOfficer.setEndDate(rs.getDate("EndDate"));
		hallOfficer.setHallID(rs.getLong("HallID"));
		hallOfficer.setHallName(rs.getString("HallName"));
		hallOfficer.setStatus(rs.getString("Status"));

		return hallOfficer;
	}
	
	private ArrayList<HallOfficer> getHallOfficers(ResultSet rs) throws SQLException
	{
		ArrayList<HallOfficer> hallOfficers = new ArrayList<>();

		while (rs.next())
			hallOfficers.add(read(rs));

		return hallOfficers;
	}

	HallOfficer getHallOfficer(HallOfficer hallOfficer) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ho.*, h.Name AS HallName, s.Name as HallOfficerName, hm.DistrictID FROM HallOfficer ho, Hall h, Staff s WHERE ho.HallID = h.HallID AND ho.StaffID = s.StaffID AND ho.StaffID = ?");

		ps.setString(1, hallOfficer.getStaffID());

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			hallOfficer = read(rs);
		
		return hallOfficer;
	}
	
	ArrayList<HallOfficer> getHallOfficers(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ho.*, h.Name AS HallName, s.Name as HallOfficerName, hm.DistrictID " + 
				"FROM HallOfficer ho LEFT JOIN Hall h ON ho.HallID = h.HallID LEFT JOIN Staff s ON ho.StaffID = s.StaffID LEFT JOIN Hammer hm ON ho.HammerNo = hm.HammerNo LEFT JOIN District d ON hm.DistrictID = d.DistrictID LEFT JOIN State stt ON d.StateID = stt.StateID " + 
				"WHERE ho.Status = ?");

		ps.setString(1, status);

		return getHallOfficers(ps.executeQuery());
	}
	
	ArrayList<HallOfficer> getHallOfficers(Date currentDate, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ho.*, h.Name AS HallName, s.Name as HallOfficerName, hm.DistrictID " + 
				"FROM HallOfficer ho LEFT JOIN Hall h ON ho.HallID = h.HallID LEFT JOIN Staff s ON ho.StaffID = s.StaffID LEFT JOIN Hammer hm ON ho.HammerNo = hm.HammerNo LEFT JOIN District d ON hm.DistrictID = d.DistrictID LEFT JOIN State stt ON d.StateID = stt.StateID " + 
				"WHERE ? BETWEEN ho.StartDate AND ho.EndDate AND ho.Status = ?");
		ps.setDate(1, toSQLDate(currentDate));
		ps.setString(2, status);

		return getHallOfficers(ps.executeQuery());
	}
	
	ArrayList<HallOfficer> getHallOfficers(Staff staff) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ho.*, h.Name AS HallName, s.Name as HallOfficerName, hm.DistrictID " + 
				"FROM HallOfficer ho LEFT JOIN Hall h ON ho.HallID = h.HallID LEFT JOIN Staff s ON ho.StaffID = s.StaffID LEFT JOIN Hammer hm ON ho.HammerNo = hm.HammerNo LEFT JOIN District d ON hm.DistrictID = d.DistrictID LEFT JOIN State stt ON d.StateID = stt.StateID " + 
				"WHERE ho.StaffID = ?");
		ps.setString(1, staff.getStaffID());

		return getHallOfficers(ps.executeQuery());
	}
	
	ArrayList<HallOfficer> getHallOfficers(Date currentDate, Staff staff) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ho.*, h.Name AS HallName, s.Name as HallOfficerName, hm.DistrictID " + 
				"FROM HallOfficer ho LEFT JOIN Hall h ON ho.HallID = h.HallID LEFT JOIN Staff s ON ho.StaffID = s.StaffID LEFT JOIN Hammer hm ON ho.HammerNo = hm.HammerNo LEFT JOIN District d ON hm.DistrictID = d.DistrictID LEFT JOIN State stt ON d.StateID = stt.StateID " + 
				"WHERE ? BETWEEN ho.StartDate AND ho.EndDate AND ho.StaffID = ?");
		ps.setDate(1, toSQLDate(currentDate));
		ps.setString(2, staff.getStaffID());

		return getHallOfficers(ps.executeQuery());
	}
	
	ArrayList<HallOfficer> getHallOfficers(District districtID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ho.*, h.Name AS HallName, s.Name as HallOfficerName, hm.DistrictID FROM HallOfficer ho, Hall h, Staff s, Hammer hm, District d WHERE ho.HallID = h.HallID AND ho.StaffID = s.StaffID AND ho.HammerNo = hm.HammerNo AND hm.DistrictID = d.DistrictID AND ho.status = 'A' AND d.DistrictID = ?");

		ps.setInt(1, districtID.getDistrictID());

		return getHallOfficers(ps.executeQuery());
	}
	
	ArrayList<HallOfficer> getHallOfficers(Date currentDate, District districtID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ho.*, h.Name AS HallName, s.Name as HallOfficerName, hm.DistrictID " + 
				"FROM HallOfficer ho LEFT JOIN Hall h ON ho.HallID = h.HallID LEFT JOIN Staff s ON ho.StaffID = s.StaffID LEFT JOIN Hammer hm ON ho.HammerNo = hm.HammerNo LEFT JOIN District d ON hm.DistrictID = d.DistrictID LEFT JOIN State stt ON d.StateID = stt.StateID " + 
				"WHERE ho.status = 'A' AND ? BETWEEN ho.StartDate AND ho.EndDate AND d.DistrictID = ?");
		
		ps.setDate(1, toSQLDate(currentDate));
		ps.setInt(2, districtID.getDistrictID());

		return getHallOfficers(ps.executeQuery());
	}
	
	ArrayList<HallOfficer> getHallOfficers(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ho.*, h.Name AS HallName, s.Name as HallOfficerName, hm.DistrictID FROM HallOfficer ho, Hall h, Staff s, Hammer hm, District d, State stt WHERE ho.HallID = h.HallID AND ho.StaffID = s.StaffID AND ho.HammerNo = hm.HammerNo AND hm.DistrictID = d.DistrictID AND d.StateID = stt.StateID AND ho.status = 'A' AND stt.StateID = ?");

		ps.setInt(1, state.getStateID());

		return getHallOfficers(ps.executeQuery());
	}
	
	ArrayList<HallOfficer> getHallOfficers(Date currentDate, State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ho.*, h.Name AS HallName, s.Name as HallOfficerName, hm.DistrictID " + 
				"FROM HallOfficer ho LEFT JOIN Hall h ON ho.HallID = h.HallID LEFT JOIN Staff s ON ho.StaffID = s.StaffID LEFT JOIN Hammer hm ON ho.HammerNo = hm.HammerNo LEFT JOIN District d ON hm.DistrictID = d.DistrictID LEFT JOIN State stt ON d.StateID = stt.StateID " + 
				"WHERE ho.status = 'A' AND ? BETWEEN ho.StartDate AND ho.EndDate AND stt.StateID = ?");
		
		ps.setDate(1, toSQLDate(currentDate));
		ps.setInt(2, state.getStateID());

		return getHallOfficers(ps.executeQuery());
	}
}