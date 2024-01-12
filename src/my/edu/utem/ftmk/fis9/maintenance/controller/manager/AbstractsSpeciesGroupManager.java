package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.AbstractsSpeciesGroup;

/**
 * @author Nor Azman Mat Ariff
 */
class AbstractsSpeciesGroupManager extends MaintenanceTableManager
{
	AbstractsSpeciesGroupManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(AbstractsSpeciesGroup abstractsSpeciesGroup, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, abstractsSpeciesGroup.getName());
		ps.setString(2, abstractsSpeciesGroup.getStatus());

		if (abstractsSpeciesGroup.getAbstractsSpeciesGroupID() != 0)
			ps.setInt(3, abstractsSpeciesGroup.getAbstractsSpeciesGroupID());

		return ps.executeUpdate();
	}

	int addAbstractsSpeciesGroup(AbstractsSpeciesGroup abstractsSpeciesGroup) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(abstractsSpeciesGroup) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO AbstractsSpeciesGroup (Name, Status) VALUES (?, ?)");
			status = write(abstractsSpeciesGroup, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					abstractsSpeciesGroup.setAbstractsSpeciesGroupID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	private AbstractsSpeciesGroup checkExistingCode(AbstractsSpeciesGroup abstractsSpeciesGroup) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM AbstractsSpeciesGroup WHERE Name = ? AND Status = 'A'");
		ps.setString(1, abstractsSpeciesGroup.getName());
		
		ResultSet rs = ps.executeQuery();
		
		AbstractsSpeciesGroup oldAbstractsSpeciesGroup = null;
		
		if (rs.next())
		{
			oldAbstractsSpeciesGroup = read(rs);
		}
		
		return oldAbstractsSpeciesGroup;
	}

	int updateAbstractsSpeciesGroup(AbstractsSpeciesGroup abstractsSpeciesGroup) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE AbstractsSpeciesGroup SET Name = ?, Status = ? WHERE AbstractsSpeciesGroupID = ?");

		return write(abstractsSpeciesGroup, ps);
	}
	
	int deleteAbstractsSpeciesGroup(AbstractsSpeciesGroup abstractsSpeciesGroup) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE AbstractsSpeciesGroup SET Status = 'I' WHERE AbstractsSpeciesGroupID = ?");
		ps.setInt(1, abstractsSpeciesGroup.getAbstractsSpeciesGroupID());

		return ps.executeUpdate();
	}

	private AbstractsSpeciesGroup read(ResultSet rs) throws SQLException
	{
		AbstractsSpeciesGroup abstractsSpeciesGroup = new AbstractsSpeciesGroup();

		abstractsSpeciesGroup.setAbstractsSpeciesGroupID(rs.getInt("AbstractsSpeciesGroupID"));
		abstractsSpeciesGroup.setName(rs.getString("Name"));
		abstractsSpeciesGroup.setStatus(rs.getString("Status"));
		abstractsSpeciesGroup.setAbstractsSpeciesGroupRecords(facade.getAbstractsSpeciesGroupRecords(abstractsSpeciesGroup));

		return abstractsSpeciesGroup;
	}
	
	private ArrayList<AbstractsSpeciesGroup> getAbstractsSpeciesGroups(ResultSet rs) throws SQLException
	{
		ArrayList<AbstractsSpeciesGroup> abstractsSpeciesGroups = new ArrayList<>();

		while (rs.next())
		{
			abstractsSpeciesGroups.add(read(rs));
		}
		return abstractsSpeciesGroups;
	}

	AbstractsSpeciesGroup getAbstractsSpeciesGroup(int abstractsSpeciesGroupID) throws SQLException
	{
		AbstractsSpeciesGroup abstractsSpeciesGroup = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM AbstractsSpeciesGroup WHERE AbstractsSpeciesGroupID = ?");

		ps.setInt(1, abstractsSpeciesGroupID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			abstractsSpeciesGroup = read(rs);
		
		return abstractsSpeciesGroup;
	}
	
	ArrayList<AbstractsSpeciesGroup> getAbstractsSpeciesGroups(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM AbstractsSpeciesGroup WHERE Status = ? ORDER BY Name");

		ps.setString(1, status);

		return getAbstractsSpeciesGroups(ps.executeQuery());
	}
}