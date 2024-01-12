package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.SoilStatus;

/**
 * @author Satrya Fajri Pratama
 */
class SoilStatusManager extends MaintenanceTableManager
{
	SoilStatusManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(SoilStatus soilStatus, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, soilStatus.getCode());
		ps.setString(2, soilStatus.getName());

		if (soilStatus.getSoilStatusID() != 0)
			ps.setInt(3, soilStatus.getSoilStatusID());

		return ps.executeUpdate();
	}

	int addSoilStatus(SoilStatus soilStatus) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO SoilStatus (Code, Name) VALUES (?, ?)");
		int status = write(soilStatus, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				soilStatus.setSoilStatusID(rs.getInt(1));
		}

		return status;
	}

	int updateSoilStatus(SoilStatus soilStatus) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE SoilStatus SET Code = ?, Name = ? WHERE SoilStatusID = ?");

		return write(soilStatus, ps);
	}

	private ArrayList<SoilStatus> getSoilStatuses(ResultSet rs) throws SQLException
	{
		ArrayList<SoilStatus> soilStatuses = new ArrayList<>();

		while (rs.next())
		{
			SoilStatus soilStatus = new SoilStatus();

			soilStatus.setSoilStatusID(rs.getInt("SoilStatusID"));
			soilStatus.setCode(rs.getString("Code"));
			soilStatus.setName(rs.getString("Name"));

			soilStatuses.add(soilStatus);
		}

		return soilStatuses;
	}

	ArrayList<SoilStatus> getSoilStatuses() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM SoilStatus ORDER BY Code");

		return getSoilStatuses(ps.executeQuery());
	}
}