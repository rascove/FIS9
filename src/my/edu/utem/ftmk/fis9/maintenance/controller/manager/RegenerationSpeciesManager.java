package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationSpecies;
import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationType;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class RegenerationSpeciesManager extends MaintenanceTableManager
{
	RegenerationSpeciesManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(RegenerationSpecies regenerationSpecies, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, regenerationSpecies.getRegenerationTypeID());
		ps.setInt(2, regenerationSpecies.getSpeciesID());

		if (regenerationSpecies.getRegenerationSpeciesID() != 0)
			ps.setInt(3, regenerationSpecies.getRegenerationSpeciesID());

		return ps.executeUpdate();
	}

	int addRegenerationSpecies(RegenerationSpecies regenerationSpecies) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO RegenerationSpecies (RegenerationTypeID, SpeciesID) VALUES (?, ?)");
		int status = write(regenerationSpecies, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				regenerationSpecies.setRegenerationSpeciesID(rs.getInt(1));
		}

		return status;
	}

	int updateRegenerationSpecies(RegenerationSpecies regenerationSpecies) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE RegenerationSpecies SET RegenerationTypeID = ?, SpeciesID = ? WHERE RegenerationSpeciesID = ?");

		return write(regenerationSpecies, ps);
	}

	int deleteRegenerationSpecies(RegenerationSpecies regenerationSpecies) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM RegenerationSpecies WHERE RegenerationSpeciesID = ?");

		ps.setInt(1, regenerationSpecies.getRegenerationSpeciesID());

		return ps.executeUpdate();
	}
	
	private ArrayList<RegenerationSpecies> getRegenerationSpeciesList(ResultSet rs) throws SQLException
	{
		ArrayList<RegenerationSpecies> regenerationSpeciesList = new ArrayList<>();

		while (rs.next())
		{
			RegenerationSpecies regenerationSpecies = new RegenerationSpecies();

			regenerationSpecies.setRegenerationSpeciesID(rs.getInt("RegenerationSpeciesID"));
			regenerationSpecies.setRegenerationTypeID(rs.getInt("RegenerationTypeID"));
			regenerationSpecies.setSpeciesID(rs.getInt("SpeciesID"));
			regenerationSpecies.setCode(rs.getString("Code"));
			regenerationSpecies.setName(rs.getString("Name"));
			regenerationSpecies.setSymbol(rs.getString("Symbol"));
			regenerationSpecies.setScientificName(rs.getString("ScientificName"));
			regenerationSpecies.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
			regenerationSpecies.setTimberGroupID(rs.getInt("TimberGroupID"));
			regenerationSpecies.setTimberTypeID(rs.getInt("TimberTypeID"));

			regenerationSpeciesList.add(regenerationSpecies);
		}

		return regenerationSpeciesList;
	}
	
	ArrayList<RegenerationSpecies> getRegenerationSpeciesList(RegenerationType regenerationType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ps.RegenerationSpeciesID, ps.RegenerationTypeID, s.* FROM RegenerationSpecies ps, Species s WHERE ps.RegenerationTypeID = ? AND ps.SpeciesID = s.SpeciesID ORDER BY s.Code");

		ps.setInt(1, regenerationType.getRegenerationTypeID());
		
		return getRegenerationSpeciesList(ps.executeQuery());
	}
	
	ArrayList<RegenerationSpecies> getRegenerationSpeciesList(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ps.RegenerationSpeciesID, ps.RegenerationTypeID, s.* FROM RegenerationSpecies ps, RegenerationType pt, Species s WHERE ps.RegenerationTypeID = pt.RegenerationTypeID AND (pt.StateID = ? OR pt.StateID IS NULL) AND ps.SpeciesID = s.SpeciesID ORDER BY s.Code");

		ps.setInt(1, state.getStateID());
		
		return getRegenerationSpeciesList(ps.executeQuery());
	}
}