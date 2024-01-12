package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.ForestType;

/**
 * @author Satrya Fajri Pratama
 */
class ForestTypeManager extends MaintenanceTableManager
{
	ForestTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(ForestType forestType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, forestType.getCode());
		ps.setString(2, forestType.getName());

		if (forestType.getForestTypeID() != 0)
			ps.setInt(3, forestType.getForestTypeID());

		return ps.executeUpdate();
	}

	int addForestType(ForestType forestType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO ForestType (Code, Name) VALUES (?, ?)");
		int status = write(forestType, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				forestType.setForestTypeID(rs.getInt(1));
		}

		return status;
	}

	int updateForestType(ForestType forestType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ForestType SET Code = ?, Name = ? WHERE ForestTypeID = ?");

		return write(forestType, ps);
	}

	private ArrayList<ForestType> getForestTypes(ResultSet rs) throws SQLException
	{
		ArrayList<ForestType> forestTypes = new ArrayList<>();

		while (rs.next())
		{
			ForestType forestType = new ForestType();

			forestType.setForestTypeID(rs.getInt("ForestTypeID"));
			forestType.setCode(rs.getString("Code"));
			forestType.setName(rs.getString("Name"));

			forestTypes.add(forestType);
		}

		return forestTypes;
	}

	ArrayList<ForestType> getForestTypes() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ForestType ORDER BY Code");

		return getForestTypes(ps.executeQuery());
	}
}