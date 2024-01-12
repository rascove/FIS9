package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.SpeciesType;

/**
 * @author Satrya Fajri Pratama
 */
class SpeciesTypeManager extends MaintenanceTableManager
{
	SpeciesTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(SpeciesType speciesType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, speciesType.getName());

		if (speciesType.getSpeciesTypeID() != 0)
			ps.setInt(2, speciesType.getSpeciesTypeID());

		return ps.executeUpdate();
	}

	int addSpeciesType(SpeciesType speciesType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO SpeciesType (Name) VALUES (?)");
		int status = write(speciesType, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				speciesType.setSpeciesTypeID(rs.getInt(1));
		}

		return status;
	}

	int updateSpeciesType(SpeciesType speciesType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE SpeciesType SET Name = ? WHERE SpeciesTypeID = ?");

		return write(speciesType, ps);
	}

	private ArrayList<SpeciesType> getSpeciesTypes(ResultSet rs) throws SQLException
	{
		ArrayList<SpeciesType> speciesTypes = new ArrayList<>();

		while (rs.next())
		{
			SpeciesType speciesType = new SpeciesType();

			speciesType.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
			speciesType.setName(rs.getString("Name"));

			speciesTypes.add(speciesType);
		}

		return speciesTypes;
	}

	ArrayList<SpeciesType> getSpeciesTypes() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM SpeciesType ORDER BY Name");

		return getSpeciesTypes(ps.executeQuery());
	}
}