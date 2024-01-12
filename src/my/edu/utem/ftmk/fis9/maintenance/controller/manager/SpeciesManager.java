package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;

/**
 * @author Satrya Fajri Pratama
 */
class SpeciesManager extends MaintenanceTableManager
{
	SpeciesManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(Species species, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, species.getCode());
		ps.setString(2, species.getName());
		ps.setString(3, species.getScientificName());
		ps.setString(4, species.getScientificName());
		ps.setInt(5, species.getSpeciesTypeID());
		nullable(ps, 6, species.getTimberGroupID());
		nullable(ps, 7, species.getTimberTypeID());

		if (species.getSpeciesID() != 0)
			ps.setInt(8, species.getSpeciesID());

		return ps.executeUpdate();
	}

	int addSpecies(Species species) throws SQLException
	{
		if (species.getSpeciesID() != 0)
			species.setSpeciesID(0);

		PreparedStatement ps = facade.prepareStatement("INSERT INTO Species (Code, Name, ScientificName, Symbol, SpeciesTypeID, TimberGroupID, TimberTypeID) VALUES (?, ?, ?, ?, ?, ?, ?)");
		int status = write(species, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				species.setSpeciesID(rs.getInt(1));
		}

		return status;
	}

	int updateSpecies(Species species) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Species SET Code = ?, Name = ?, ScientificName = ?, Symbol = ?, SpeciesTypeID = ?, TimberGroupID = ?, TimberTypeID = ? WHERE SpeciesID = ?");

		return write(species, ps);
	}

	private ArrayList<Species> getSpeciesList(ResultSet rs) throws SQLException
	{
		ArrayList<Species> speciesList = new ArrayList<>();

		while (rs.next())
		{
			Species species = new Species();

			species.setSpeciesID(rs.getInt("SpeciesID"));
			species.setCode(rs.getString("Code"));
			species.setName(rs.getString("Name"));
			species.setScientificName(rs.getString("ScientificName"));
			species.setSymbol(rs.getString("Symbol"));
			species.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
			species.setTimberGroupID(rs.getInt("TimberGroupID"));
			species.setTimberTypeID(rs.getInt("TimberTypeID"));
			species.setSpeciesTypeName(rs.getString("SpeciesTypeName"));
			species.setTimberGroupName(rs.getString("TimberGroupName"));
			species.setTimberTypeName(rs.getString("TimberTypeName"));

			speciesList.add(species);
		}

		return speciesList;
	}

	ArrayList<Species> getSpeciesList() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, st.Name AS SpeciesTypeName, tg.Name AS TimberGroupName, tt.Name AS TimberTypeName FROM Species s JOIN SpeciesType st LEFT JOIN TimberGroup tg ON s.TimberGroupID = tg.TimberGroupID LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID WHERE s.SpeciesTypeID = st.SpeciesTypeID ORDER BY s.Code");

		return getSpeciesList(ps.executeQuery());
	}

	ArrayList<Species> getSpeciesList(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, st.Name AS SpeciesTypeName, tg.Name AS TimberGroupName, tt.Name AS TimberTypeName FROM Species s JOIN SpeciesType st LEFT JOIN TimberGroup tg ON s.TimberGroupID = tg.TimberGroupID LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID WHERE s.SpeciesID NOT IN (SELECT s.SpeciesID FROM Species s, ProtectedSpecies ps, ProtectedType pt WHERE s.SpeciesID = ps.SpeciesID AND ps.ProtectedTypeID = pt.ProtectedTypeID AND (pt.StateID = ? OR pt.StateID IS NULL)) AND s.SpeciesTypeID = st.SpeciesTypeID ORDER BY s.Code");

		ps.setInt(1, state.getStateID());

		return getSpeciesList(ps.executeQuery());
	}
}