package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationType;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class RegenerationTypeManager extends MaintenanceTableManager
{
	RegenerationTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(RegenerationType regenerationType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, regenerationType.getName());
		nullable(ps, 2, regenerationType.getStateID());

		if (regenerationType.getRegenerationTypeID() != 0)
			ps.setInt(3, regenerationType.getRegenerationTypeID());

		return ps.executeUpdate();
	}

	int addRegenerationType(RegenerationType regenerationType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO RegenerationType (Name, StateID) VALUES (?, ?)");
		int status = write(regenerationType, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				regenerationType.setRegenerationTypeID(rs.getInt(1));
		}

		return status;
	}

	int updateRegenerationType(RegenerationType regenerationType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE RegenerationType SET Name = ?, StateID = ? WHERE RegenerationTypeID = ?");

		return write(regenerationType, ps);
	}

	private ArrayList<RegenerationType> getRegenerationTypes(ResultSet rs) throws SQLException
	{
		ArrayList<RegenerationType> regenerationTypes = new ArrayList<>();

		while (rs.next())
		{
			RegenerationType regenerationType = new RegenerationType();

			regenerationType.setRegenerationTypeID(rs.getInt("RegenerationTypeID"));
			regenerationType.setName(rs.getString("Name"));
			regenerationType.setStateID(rs.getInt("StateID"));
			regenerationType.setStateName(rs.getString("StateName"));
			regenerationType.setRegenerationSpeciesList(facade.getRegenerationSpeciesList(regenerationType));

			regenerationTypes.add(regenerationType);
		}

		return regenerationTypes;
	}

	ArrayList<RegenerationType> getRegenerationTypes() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pt.*, s.Name AS StateName FROM RegenerationType pt LEFT JOIN State s ON pt.StateID = s.StateID ORDER BY pt.Name");

		return getRegenerationTypes(ps.executeQuery());
	}
	
	ArrayList<RegenerationType> getRegenerationTypes(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT pt.*, s.Name AS StateName FROM RegenerationType pt LEFT JOIN State s ON pt.StateID = s.StateID WHERE pt.StateID = ? OR pt.StateID IS NULL ORDER BY pt.Name");

		ps.setInt(1, state.getStateID());
		
		return getRegenerationTypes(ps.executeQuery());
	}
}