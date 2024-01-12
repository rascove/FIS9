package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.TreeStatus;

/**
 * @author Zurina
 */
class TreeStatusManager extends MaintenanceTableManager
{
	TreeStatusManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(TreeStatus status, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, status.getCode());
		ps.setString(2, status.getName());

		if (status.getTreeStatusID() != 0)
			ps.setInt(3, status.getTreeStatusID());

		return ps.executeUpdate();
	}

	int addTreeStatus(TreeStatus status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO TreeStatus (Code, Name) VALUES (?, ?)");
		int statusPS = write(status, ps);

		if (statusPS != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				status.setTreeStatusID(rs.getInt(1));
		}

		return statusPS;
	}

	int updateTreeStatus(TreeStatus status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TreeStatus SET Code = ?, Name = ? WHERE TreeStatusID = ?");

		return write(status, ps);
	}

	private ArrayList<TreeStatus> getStatuses(ResultSet rs) throws SQLException
	{
		ArrayList<TreeStatus> statuses = new ArrayList<>();

		while (rs.next())
		{
			TreeStatus status = new TreeStatus();

			status.setTreeStatusID(rs.getInt("TreeStatusID"));
			status.setCode(rs.getString("Code"));
			status.setName(rs.getString("Name"));

			statuses.add(status);
		}

		return statuses;
	}

	ArrayList<TreeStatus> getTreeStatuses() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM TreeStatus ORDER BY Code");

		return getStatuses(ps.executeQuery());
	}
}