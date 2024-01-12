package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.AreaStatus;

/**
 * @author Satrya Fajri Pratama
 */
class AreaStatusManager extends MaintenanceTableManager
{
	AreaStatusManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(AreaStatus areaStatus, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, areaStatus.getCode());
		ps.setString(2, areaStatus.getName());
		
		if (areaStatus.getAreaStatusID() != 0)
			ps.setInt(3, areaStatus.getAreaStatusID());
		
		return ps.executeUpdate();
	}
	
	int addAreaStatus(AreaStatus areaStatus) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO AreaStatus (Code, Name) VALUES (?, ?)");
		int status = write(areaStatus, ps);
		
		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next())
				areaStatus.setAreaStatusID(rs.getInt(1));
		}
		
		return status;
	}

	int updateAreaStatus(AreaStatus areaStatus) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE AreaStatus SET Code = ?, Name = ? WHERE AreaStatusID = ?");

		return write(areaStatus, ps);
	}

	private ArrayList<AreaStatus> getAreaStatuses(ResultSet rs) throws SQLException
	{
		ArrayList<AreaStatus> areaStatuses = new ArrayList<>();
		
		while (rs.next())
		{
			AreaStatus areaStatus = new AreaStatus();

			areaStatus.setAreaStatusID(rs.getInt("AreaStatusID"));
			areaStatus.setCode(rs.getString("Code"));
			areaStatus.setName(rs.getString("Name"));

			areaStatuses.add(areaStatus);
		}

		return areaStatuses;
	}
	
	ArrayList<AreaStatus> getAreaStatuses() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM AreaStatus ORDER BY Code");

		return getAreaStatuses(ps.executeQuery());
	}
}