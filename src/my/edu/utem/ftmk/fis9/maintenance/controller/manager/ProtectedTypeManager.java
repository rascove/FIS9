package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.ProtectedType;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class ProtectedTypeManager extends MaintenanceTableManager
{
	ProtectedTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(ProtectedType protectedType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, protectedType.getName());
		nullable(ps, 2, protectedType.getStateID());

		if (protectedType.getProtectedTypeID() != 0)
			ps.setInt(3, protectedType.getProtectedTypeID());

		return ps.executeUpdate();
	}

	int addProtectedType(ProtectedType protectedType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO ProtectedType (Name, StateID) VALUES (?, ?)");
		int status = write(protectedType, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				protectedType.setProtectedTypeID(rs.getInt(1));
		}

		return status;
	}

	int updateProtectedType(ProtectedType protectedType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ProtectedType SET Name = ?, StateID = ? WHERE ProtectedTypeID = ?");

		return write(protectedType, ps);
	}

	private ArrayList<ProtectedType> getProtectedTypes(ResultSet rs) throws SQLException
	{
		ArrayList<ProtectedType> protectedTypes = new ArrayList<>();

		while (rs.next())
		{
			ProtectedType protectedType = new ProtectedType();

			protectedType.setProtectedTypeID(rs.getInt("ProtectedTypeID"));
			protectedType.setName(rs.getString("Name"));
			protectedType.setStateID(rs.getInt("StateID"));
			protectedType.setStateName(rs.getString("StateName"));
			protectedType.setProtectedSpeciesList(facade.getProtectedSpeciesList(protectedType));

			protectedTypes.add(protectedType);
		}

		return protectedTypes;
	}

	ArrayList<ProtectedType> getProtectedTypes() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pt.*, s.Name AS StateName FROM ProtectedType pt LEFT JOIN State s ON pt.StateID = s.StateID ORDER BY pt.Name");

		return getProtectedTypes(ps.executeQuery());
	}
	
	ArrayList<ProtectedType> getProtectedTypes(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pt.*, s.Name AS StateName FROM ProtectedType pt LEFT JOIN State s ON pt.StateID = s.StateID WHERE pt.StateID = ? OR pt.StateID IS NULL ORDER BY pt.Name");

		ps.setInt(1, state.getStateID());
		
		return getProtectedTypes(ps.executeQuery());
	}
}