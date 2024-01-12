package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.ProtectedSpecies;
import my.edu.utem.ftmk.fis9.maintenance.model.ProtectedType;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class ProtectedSpeciesManager extends MaintenanceTableManager
{
	ProtectedSpeciesManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(ProtectedSpecies protectedSpecies, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, protectedSpecies.getProtectedTypeID());
		ps.setInt(2, protectedSpecies.getSpeciesID());

		if (protectedSpecies.getProtectedSpeciesID() != 0)
			ps.setInt(3, protectedSpecies.getProtectedSpeciesID());

		return ps.executeUpdate();
	}

	int addProtectedSpecies(ProtectedSpecies protectedSpecies) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO ProtectedSpecies (ProtectedTypeID, SpeciesID) VALUES (?, ?)");
		int status = write(protectedSpecies, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				protectedSpecies.setProtectedSpeciesID(rs.getInt(1));
		}

		return status;
	}

	int updateProtectedSpecies(ProtectedSpecies protectedSpecies) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ProtectedSpecies SET ProtectedTypeID = ?, SpeciesID = ? WHERE ProtectedSpeciesID = ?");

		return write(protectedSpecies, ps);
	}

	int deleteProtectedSpecies(ProtectedSpecies protectedSpecies) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM ProtectedSpecies WHERE ProtectedSpeciesID = ?");

		ps.setInt(1, protectedSpecies.getProtectedSpeciesID());

		return ps.executeUpdate();
	}
	
	private ArrayList<ProtectedSpecies> getProtectedSpeciesList(ResultSet rs) throws SQLException
	{
		ArrayList<ProtectedSpecies> protectedSpeciesList = new ArrayList<>();

		while (rs.next())
		{
			ProtectedSpecies protectedSpecies = new ProtectedSpecies();

			protectedSpecies.setProtectedSpeciesID(rs.getInt("ProtectedSpeciesID"));
			protectedSpecies.setProtectedTypeID(rs.getInt("ProtectedTypeID"));
			protectedSpecies.setSpeciesID(rs.getInt("SpeciesID"));
			protectedSpecies.setCode(rs.getString("Code"));
			protectedSpecies.setName(rs.getString("Name"));
			protectedSpecies.setScientificName(rs.getString("ScientificName"));
			protectedSpecies.setSymbol(rs.getString("Symbol"));
			protectedSpecies.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
			protectedSpecies.setTimberGroupID(rs.getInt("TimberGroupID"));
			protectedSpecies.setTimberTypeID(rs.getInt("TimberTypeID"));

			protectedSpeciesList.add(protectedSpecies);
		}

		return protectedSpeciesList;
	}
	
	ArrayList<ProtectedSpecies> getProtectedSpeciesList(ProtectedType protectedType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ps.ProtectedSpeciesID, ps.ProtectedTypeID, s.* FROM ProtectedSpecies ps, Species s WHERE ps.ProtectedTypeID = ? AND ps.SpeciesID = s.SpeciesID ORDER BY s.Code");

		ps.setInt(1, protectedType.getProtectedTypeID());
		
		return getProtectedSpeciesList(ps.executeQuery());
	}
	
	ArrayList<ProtectedSpecies> getProtectedSpeciesList(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT ps.ProtectedSpeciesID, ps.ProtectedTypeID, s.* FROM ProtectedSpecies ps, ProtectedType pt, Species s WHERE ps.ProtectedTypeID = pt.ProtectedTypeID AND (pt.StateID = ? OR pt.StateID IS NULL) AND ps.SpeciesID = s.SpeciesID ORDER BY s.Code");

		ps.setInt(1, state.getStateID());
		
		return getProtectedSpeciesList(ps.executeQuery());
	}
}