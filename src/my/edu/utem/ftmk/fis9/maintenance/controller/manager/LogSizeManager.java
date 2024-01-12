package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.LogSize;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Nor Azman Mat Ariff
 */
class LogSizeManager extends MaintenanceTableManager
{
	LogSizeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(LogSize logSize, PreparedStatement ps) throws SQLException
	{
		nullable(ps, 1, logSize.getStateID());
		ps.setDouble(2, logSize.getMinBigSize());
		ps.setDouble(3, logSize.getMinSmallSize());
		ps.setTimestamp(4, logSize.getStartDate());
		ps.setTimestamp(5, logSize.getEndDate());
		ps.setString(6, logSize.getStatus());

		if (logSize.getLogSizeID() != 0)
			ps.setInt(7, logSize.getLogSizeID());

		return ps.executeUpdate();
	}

	int addLogSize(LogSize logSize) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO LogSize (StateID, MinBigSize, MinSmallSize, StartDate, EndDate, Status) VALUES (?, ?, ?, ?, ?, ?)");
		int status = write(logSize, ps);
		
		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				logSize.setLogSizeID(rs.getInt(1));
		}

		return status;
	}

	int updateLogSize(LogSize logSize) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE LogSize SET EndDate = CURRENT_TIMESTAMP, Status = 'I' WHERE LogSizeID = ?");
		ps.setInt(1, logSize.getLogSizeID());
		ps.executeUpdate();
		
		logSize.setLogSizeID(0);
		return addLogSize(logSize);
	}

	private LogSize read(ResultSet rs) throws SQLException
	{
		LogSize logSize = new LogSize();

		logSize.setLogSizeID(rs.getInt("LogSizeID"));
		logSize.setStateID(rs.getInt("StateID"));
		logSize.setStateName(rs.getString("StateName"));
		logSize.setMinBigSize(rs.getDouble("MinBigSize"));
		logSize.setMinSmallSize(rs.getDouble("MinSmallSize"));
		logSize.setStartDate(rs.getTimestamp("StartDate"));
		logSize.setEndDate(rs.getTimestamp("EndDate"));
		logSize.setStatus(rs.getString("Status"));

		return logSize;
	}
	
	private ArrayList<LogSize> getLogSizes(ResultSet rs) throws SQLException
	{
		ArrayList<LogSize> logSizes = new ArrayList<>();

		while (rs.next())
			logSizes.add(read(rs));

		return logSizes;
	}
	
	LogSize getLogSize(int logSizeID) throws SQLException
	{
		LogSize logSize = null;
		PreparedStatement ps = facade.prepareStatement("SELECT l.*, s.Name AS stateName FROM LogSize l LEFT JOIN State s ON l.StateID = s.StateID WHERE l.LogSizeID = ?");

		ps.setInt(1, logSizeID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			logSize = read(rs);
		
		return logSize;
	}
	
	LogSize getLogSize(State state) throws SQLException
	{
		LogSize logSize = null;
		PreparedStatement ps = facade.prepareStatement("SELECT l.*, s.Name AS stateName FROM LogSize l LEFT JOIN State s ON l.StateID = s.StateID WHERE l.Status = 'A' AND (l.StateID IS NULL OR l.StateID = ?) ORDER BY l.StateID DESC");

		ps.setInt(1, state.getStateID());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			logSize = read(rs);
		
		return logSize;
	}
	
	ArrayList<LogSize> getLogSizes(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT l.*, s.Name AS stateName FROM LogSize l LEFT JOIN State s ON l.StateID = s.StateID WHERE l.Status = ? ORDER BY s.Name, l.StartDate ASC");

		ps.setString(1, status);
		
		return getLogSizes(ps.executeQuery());
	}
}