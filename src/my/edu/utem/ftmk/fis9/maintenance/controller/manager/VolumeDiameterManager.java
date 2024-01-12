package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.VolumeDiameter;
import my.edu.utem.ftmk.fis9.maintenance.model.VolumeGroup;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
class VolumeDiameterManager extends MaintenanceTableManager
{
	VolumeDiameterManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(VolumeDiameter volumeDiameter, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, volumeDiameter.getDiameter());
		ps.setDouble(2, volumeDiameter.getVolume());
		ps.setInt(3, volumeDiameter.getVolumeGroupID());

		if (volumeDiameter.getVolumeDiameterID() != 0)
			ps.setInt(4, volumeDiameter.getVolumeDiameterID());

		return ps.executeUpdate();
	}

	int addVolumeDiameter(VolumeDiameter volumeDiameter) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO VolumeDiameter (Diameter, Volume, VolumeGroupID) VALUES (?, ?, ?)");
		int status = write(volumeDiameter, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				volumeDiameter.setVolumeDiameterID(rs.getInt(1));
		}

		return status;
	}

	int updateVolumeDiameter(VolumeDiameter volumeDiameter) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE VolumeDiameter SET Diameter = ?, Volume = ?, VolumeGroupID = ? WHERE VolumeDiameterID = ?");

		return write(volumeDiameter, ps);
	}

	int deleteVolumeDiameter(VolumeDiameter volumeDiameter) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM VolumeDiameter WHERE VolumeDiameterID = ?");

		ps.setInt(1, volumeDiameter.getVolumeDiameterID());

		return ps.executeUpdate();
	}
	
	private ArrayList<VolumeDiameter> getVolumeDiameterList(ResultSet rs) throws SQLException
	{
		ArrayList<VolumeDiameter> volumeDiameterList = new ArrayList<>();

		while (rs.next())
		{
			VolumeDiameter volumeDiameter = new VolumeDiameter();

			volumeDiameter.setVolumeDiameterID(rs.getInt("VolumeDiameterID"));
			volumeDiameter.setDiameter(rs.getInt("Diameter"));
			volumeDiameter.setVolume(rs.getDouble("Volume"));
			volumeDiameter.setVolumeGroupID(rs.getInt("VolumeGroupID"));

			volumeDiameterList.add(volumeDiameter);
		}

		return volumeDiameterList;
	}
	
	ArrayList<VolumeDiameter> getVolumeDiameterList(VolumeGroup volumeType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM VolumeDiameter WHERE VolumeGroupID = ? ORDER BY Diameter");

		ps.setInt(1, volumeType.getVolumeGroupID());
		
		return getVolumeDiameterList(ps.executeQuery());
	}
	
	ArrayList<VolumeDiameter> getVolumeDiameterList(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM VolumeDiameter WHERE VolumeGroupID IN (SELECT VolumeGroupID FROM VolumeGroup WHERE StateID = ? OR StateID IS NULL) ORDER BY Diameter");

		ps.setInt(1, state.getStateID());
		
		return getVolumeDiameterList(ps.executeQuery());
	}
}