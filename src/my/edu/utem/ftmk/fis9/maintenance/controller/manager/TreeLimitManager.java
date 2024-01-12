package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.TreeLimit;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class TreeLimitManager extends MaintenanceTableManager
{
	TreeLimitManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(TreeLimit treeLimit, PreparedStatement ps) throws SQLException
	{
		ps.setDouble(1, treeLimit.getMotherLimit());
		ps.setDouble(2, treeLimit.getChengalLimit());
		ps.setDouble(3, treeLimit.getProtectedLimit());
		ps.setInt(4, treeLimit.getMaximumPerPlot());
		ps.setInt(5, treeLimit.getStateID());

		if (treeLimit.getTreeLimitID() != 0)
			ps.setInt(6, treeLimit.getTreeLimitID());

		return ps.executeUpdate();
	}

	int addTreeLimit(TreeLimit treeLimit) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO TreeLimit (MotherLimit, ChengalLimit, ProtectedLimit, MaximumPerPlot, StateID) VALUES (?, ?, ?, ?, ?)");
		int status = write(treeLimit, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				treeLimit.setTreeLimitID(rs.getInt(1));
		}

		return status;
	}

	int updateTreeLimit(TreeLimit treeLimit) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TreeLimit SET MotherLimit = ?, ChengalLimit = ?, ProtectedLimit = ?, MaximumPerPlot = ?, StateID = ? WHERE TreeLimitID = ?");

		return write(treeLimit, ps);
	}

	private TreeLimit read(ResultSet rs) throws SQLException
	{
		TreeLimit treeLimit = new TreeLimit();

		treeLimit.setTreeLimitID(rs.getInt("TreeLimitID"));
		treeLimit.setMotherLimit(rs.getDouble("MotherLimit"));
		treeLimit.setChengalLimit(rs.getDouble("ChengalLimit"));
		treeLimit.setProtectedLimit(rs.getDouble("ProtectedLimit"));
		treeLimit.setMaximumPerPlot(rs.getInt("MaximumPerPlot"));
		treeLimit.setStateID(rs.getInt("StateID"));
		treeLimit.setStateName(rs.getString("StateName"));

		return treeLimit;
	}

	TreeLimit getTreeLimit(State state) throws SQLException
	{
		TreeLimit treeLimit = null;
		PreparedStatement ps = facade.prepareStatement("SELECT tl.*, s.Name AS StateName FROM TreeLimit tl, State s WHERE tl.StateID = ? AND tl.StateID = s.StateID");

		ps.setInt(1, state.getStateID());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			treeLimit = read(rs);

		return treeLimit;
	}

	ArrayList<TreeLimit> getTreeLimits() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tl.*, s.Name AS StateName FROM TreeLimit tl, State s WHERE tl.StateID = s.StateID ORDER BY tl.StateID");
		ResultSet rs = ps.executeQuery();
		ArrayList<TreeLimit> treeLimits = new ArrayList<>();

		while (rs.next())
			treeLimits.add(read(rs));

		return treeLimits;
	}
}