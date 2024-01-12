package my.edu.utem.ftmk.fis9.global.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.model.TrailLog;

/**
 * @author Satrya Fajri Pratama
 */
class TrailLogManager extends AbstractTableManager
{
	private AbstractFacade facade;
	
	TrailLogManager(AbstractFacade facade)
	{
		this.facade = facade;
	}

	int addTrailLog(TrailLog trailLog) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO TrailLog (Operation, StaffID) VALUES (?, ?)");

		ps.setString(1, trailLog.getOperation());
		ps.setString(2, trailLog.getStaffID());

		return ps.executeUpdate();
	}

	private ArrayList<TrailLog> getTrailLogs(ResultSet rs) throws SQLException
	{
		ArrayList<TrailLog> trailLogs = new ArrayList<>();
		
		while (rs.next())
		{
			TrailLog trailLog = new TrailLog();

			trailLog.setLogTime(rs.getTimestamp("LogTime"));
			trailLog.setOperation(rs.getString("Operation"));
			trailLog.setStaffID(rs.getString("StaffID"));
			trailLog.setStaffName(rs.getString("StaffName"));

			trailLogs.add(trailLog);
		}

		return trailLogs;
	}
	
	ArrayList<TrailLog> getTrailLogs(String staffID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tl.*, s.Name AS StaffName FROM TrailLog tl, Staff s WHERE tl.StaffID = s.StaffID AND tl.StaffID = ? ORDER BY tl.LogTime, tl.StaffID");

		ps.setString(1, staffID);
		
		return getTrailLogs(ps.executeQuery());
	}
	
	ArrayList<TrailLog> getTrailLogs(Date logTime) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tl.*, s.Name AS StaffName FROM TrailLog tl, Staff s WHERE tl.StaffID = s.StaffID AND CAST(tl.LogTime AS DATE) = ? ORDER BY tl.LogTime, tl.StaffID");

		ps.setDate(1, toSQLDate(logTime));
		
		return getTrailLogs(ps.executeQuery());
	}
	
	ArrayList<TrailLog> getTrailLogs(String staffID, Date logTime) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tl.*, s.Name AS StaffName FROM TrailLog tl, Staff s WHERE tl.StaffID = s.StaffID AND tl.StaffID = ? AND CAST(tl.LogTime AS DATE) = ? ORDER BY tl.LogTime, tl.StaffID");

		ps.setString(1, staffID);
		ps.setDate(2, toSQLDate(logTime));
		
		return getTrailLogs(ps.executeQuery());
	}
}