package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.LogQuality;

/**
 * @author Satrya Fajri Pratama
 */
class LogQualityManager extends MaintenanceTableManager
{
	LogQualityManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(LogQuality logQuality, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, logQuality.getCode());
		ps.setString(2, logQuality.getName());

		if (logQuality.getLogQualityID() != 0)
			ps.setInt(3, logQuality.getLogQualityID());

		return ps.executeUpdate();
	}

	int addLogQuality(LogQuality logQuality) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO LogQuality (Code, Name) VALUES (?, ?)");
		int status = write(logQuality, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				logQuality.setLogQualityID(rs.getInt(1));
		}

		return status;
	}

	int updateLogQuality(LogQuality logQuality) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE LogQuality SET Code = ?, Name = ? WHERE LogQualityID = ?");

		return write(logQuality, ps);
	}

	private ArrayList<LogQuality> getLogQualities(ResultSet rs) throws SQLException
	{
		ArrayList<LogQuality> logQualities = new ArrayList<>();

		while (rs.next())
		{
			LogQuality logQuality = new LogQuality();

			logQuality.setLogQualityID(rs.getInt("LogQualityID"));
			logQuality.setCode(rs.getString("Code"));
			logQuality.setName(rs.getString("Name"));

			logQualities.add(logQuality);
		}

		return logQualities;
	}

	ArrayList<LogQuality> getLogQualities() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM LogQuality ORDER BY Code");

		return getLogQualities(ps.executeQuery());
	}
}