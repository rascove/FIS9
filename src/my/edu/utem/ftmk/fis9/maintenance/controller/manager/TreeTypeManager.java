package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.TreeType;

/**
 * @author Satrya Fajri Pratama
 */
class TreeTypeManager extends MaintenanceTableManager
{
	TreeTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(TreeType treeType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, treeType.getName());

		if (treeType.getTreeTypeID() != 0)
			ps.setInt(2, treeType.getTreeTypeID());

		return ps.executeUpdate();
	}

	int addTreeType(TreeType treeType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO TreeType (Name) VALUES (?)");
		int status = write(treeType, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				treeType.setTreeTypeID(rs.getInt(1));
		}

		return status;
	}

	int updateTreeType(TreeType treeType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TreeType SET Name = ? WHERE TreeTypeID = ?");

		return write(treeType, ps);
	}

	private ArrayList<TreeType> getTreeTypes(ResultSet rs) throws SQLException
	{
		ArrayList<TreeType> treeTypes = new ArrayList<>();

		while (rs.next())
		{
			TreeType treeType = new TreeType();

			treeType.setTreeTypeID(rs.getInt("TreeTypeID"));
			treeType.setName(rs.getString("Name"));

			treeTypes.add(treeType);
		}

		return treeTypes;
	}

	ArrayList<TreeType> getTreeTypes() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM TreeType ORDER BY Name");

		return getTreeTypes(ps.executeQuery());
	}
}