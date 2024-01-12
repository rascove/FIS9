package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.VolumeGroup;
import my.edu.utem.ftmk.fis9.maintenance.model.VolumeSpecies;

/**
 * @author Satrya Fajri Pratama
 */
class VolumeSpeciesManager extends MaintenanceTableManager
{
	VolumeSpeciesManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(VolumeSpecies volumeSpecies, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, volumeSpecies.getVolumeGroupID());
		ps.setInt(2, volumeSpecies.getSpeciesID());

		if (volumeSpecies.getVolumeSpeciesID() != 0)
			ps.setInt(3, volumeSpecies.getVolumeSpeciesID());

		return ps.executeUpdate();
	}

	int addVolumeSpecies(VolumeSpecies volumeSpecies) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO VolumeSpecies (VolumeGroupID, SpeciesID) VALUES (?, ?)");
		int status = write(volumeSpecies, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				volumeSpecies.setVolumeSpeciesID(rs.getInt(1));
		}

		return status;
	}

	int updateVolumeSpecies(VolumeSpecies volumeSpecies) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE VolumeSpecies SET VolumeGroupID = ?, SpeciesID = ? WHERE VolumeSpeciesID = ?");

		return write(volumeSpecies, ps);
	}

	int deleteVolumeSpecies(VolumeSpecies volumeSpecies) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM VolumeSpecies WHERE VolumeSpeciesID = ?");

		ps.setInt(1, volumeSpecies.getVolumeSpeciesID());

		return ps.executeUpdate();
	}
	
	private ArrayList<VolumeSpecies> getVolumeSpeciesList(ResultSet rs) throws SQLException
	{
		ArrayList<VolumeSpecies> volumeSpeciesList = new ArrayList<>();

		while (rs.next())
		{
			VolumeSpecies volumeSpecies = new VolumeSpecies();

			volumeSpecies.setVolumeSpeciesID(rs.getInt("VolumeSpeciesID"));
			volumeSpecies.setVolumeGroupID(rs.getInt("VolumeGroupID"));
			volumeSpecies.setSpeciesID(rs.getInt("SpeciesID"));
			volumeSpecies.setCode(rs.getString("Code"));
			volumeSpecies.setName(rs.getString("Name"));
			volumeSpecies.setScientificName(rs.getString("ScientificName"));
			volumeSpecies.setSymbol(rs.getString("Symbol"));
			volumeSpecies.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
			volumeSpecies.setTimberGroupID(rs.getInt("TimberGroupID"));
			volumeSpecies.setTimberTypeID(rs.getInt("TimberTypeID"));

			volumeSpeciesList.add(volumeSpecies);
		}

		return volumeSpeciesList;
	}
	
	ArrayList<VolumeSpecies> getVolumeSpeciesList(VolumeGroup volumeType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT vs.VolumeSpeciesID, vs.VolumeGroupID, s.* FROM VolumeSpecies vs, Species s WHERE vs.VolumeGroupID = ? AND vs.SpeciesID = s.SpeciesID ORDER BY s.Code");

		ps.setInt(1, volumeType.getVolumeGroupID());
		
		return getVolumeSpeciesList(ps.executeQuery());
	}
	
	ArrayList<VolumeSpecies> getVolumeSpeciesList(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT vs.VolumeSpeciesID, vs.VolumeGroupID, s.* FROM VolumeSpecies vs, VolumeGroup vg, Species s WHERE vs.VolumeGroupID = vg.VolumeGroupID AND (vg.StateID = ? OR vg.StateID IS NULL) AND vs.SpeciesID = s.SpeciesID ORDER BY s.Code");

		ps.setInt(1, state.getStateID());
		
		return getVolumeSpeciesList(ps.executeQuery());
	}
}