package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.TimberGroup;

/**
 * @author Satrya Fajri Pratama
 */
class TimberGroupManager extends MaintenanceTableManager
{
	TimberGroupManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(TimberGroup timberGroup, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, timberGroup.getName());
		ps.setString(2, timberGroup.getDescription());

		if (timberGroup.getTimberGroupID() != 0)
			ps.setInt(3, timberGroup.getTimberGroupID());

		return ps.executeUpdate();
	}

	int addTimberGroup(TimberGroup timberGroup) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO TimberGroup (Name, Description) VALUES (?, ?)");
		int status = write(timberGroup, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				timberGroup.setTimberGroupID(rs.getInt(1));
		}

		return status;
	}

	int updateTimberGroup(TimberGroup timberGroup) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TimberGroup SET Name = ?, Description = ? WHERE TimberGroupID = ?");

		return write(timberGroup, ps);
	}

	private ArrayList<TimberGroup> getTimberGroups(ResultSet rs) throws SQLException
	{
		ArrayList<TimberGroup> timberGroups = new ArrayList<>();

		while (rs.next())
		{
			TimberGroup timberGroup = new TimberGroup();

			timberGroup.setTimberGroupID(rs.getInt("TimberGroupID"));
			timberGroup.setName(rs.getString("Name"));
			timberGroup.setDescription(rs.getString("Description"));

			timberGroups.add(timberGroup);
		}

		return timberGroups;
	}

	ArrayList<TimberGroup> getTimberGroups() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM TimberGroup");

		return getTimberGroups(ps.executeQuery());
	}
}