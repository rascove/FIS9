package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.VolumeGroup;

/**
 * @author Satrya Fajri Pratama
 */
class VolumeGroupManager extends MaintenanceTableManager
{
	VolumeGroupManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(VolumeGroup volumeGroup, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, volumeGroup.getName());
		nullable(ps, 2, volumeGroup.getStateID());

		if (volumeGroup.getVolumeGroupID() != 0)
			ps.setInt(3, volumeGroup.getVolumeGroupID());

		return ps.executeUpdate();
	}

	int addVolumeGroup(VolumeGroup volumeGroup) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO VolumeGroup (Name, StateID) VALUES (?, ?)");
		int status = write(volumeGroup, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				volumeGroup.setVolumeGroupID(rs.getInt(1));
		}

		return status;
	}

	int updateVolumeGroup(VolumeGroup volumeGroup) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE VolumeGroup SET Name = ?, StateID = ? WHERE VolumeGroupID = ?");

		return write(volumeGroup, ps);
	}

	private ArrayList<VolumeGroup> getVolumeGroups(ResultSet rs) throws SQLException
	{
		ArrayList<VolumeGroup> volumeGroups = new ArrayList<>();

		while (rs.next())
		{
			VolumeGroup volumeGroup = new VolumeGroup();

			volumeGroup.setVolumeGroupID(rs.getInt("VolumeGroupID"));
			volumeGroup.setName(rs.getString("Name"));
			volumeGroup.setStateID(rs.getInt("StateID"));
			volumeGroup.setStateName(rs.getString("StateName"));
			volumeGroup.setVolumeSpeciesList(facade.getVolumeSpeciesList(volumeGroup));
			volumeGroup.setVolumeDiameterList(facade.getVolumeDiameterList(volumeGroup));

			volumeGroups.add(volumeGroup);
		}

		return volumeGroups;
	}

	ArrayList<VolumeGroup> getVolumeGroups() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT vg.*, s.Name AS StateName FROM VolumeGroup vg LEFT JOIN State s ON vg.StateID = s.StateID ORDER BY vg.Name");

		return getVolumeGroups(ps.executeQuery());
	}
	
	ArrayList<VolumeGroup> getVolumeGroups(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT vg.*, s.Name AS StateName FROM VolumeGroup vg LEFT JOIN State s ON vg.StateID = s.StateID WHERE vg.StateID = ? OR vg.StateID IS NULL ORDER BY vg.Name");

		ps.setInt(1, state.getStateID());
		
		return getVolumeGroups(ps.executeQuery());
	}
	
	TreeMap<String, Double> getVolumeMap() throws SQLException
	{
		TreeMap<String, Double> map = new TreeMap<>();
		PreparedStatement ps = facade.prepareStatement("SELECT CONCAT(vs.SpeciesID, '-', vd.Diameter), vd.Volume FROM VolumeSpecies vs, VolumeDiameter vd, VolumeGroup vg WHERE vs.VolumeGroupID = vd.VolumeGroupID AND vs.VolumeGroupID = vg.VolumeGroupID AND vg.StateID IS NULL");
		ResultSet rs = ps.executeQuery();
		
		while (rs.next())
		{
			String key = rs.getString(1);
			
			if (!map.containsKey(key))
				map.put(key, rs.getDouble(2));
		}
		
		return map;
	}
	
	TreeMap<String, Double> getVolumeMap(State state) throws SQLException
	{
		TreeMap<String, Double> map = new TreeMap<>();
		PreparedStatement ps = facade.prepareStatement("SELECT CONCAT(vs.SpeciesID, '-', vd.Diameter), vd.Volume, vg.StateID FROM VolumeSpecies vs, VolumeDiameter vd, VolumeGroup vg WHERE vs.VolumeGroupID = vd.VolumeGroupID AND vs.VolumeGroupID = vg.VolumeGroupID AND (vg.StateID = ? OR vg.StateID IS NULL) ORDER BY vg.StateID DESC");

		ps.setInt(1, state.getStateID());
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next())
		{
			String key = rs.getString(1);
			
			if (!map.containsKey(key))
				map.put(key, rs.getDouble(2));
		}
		
		return map;
	}
}