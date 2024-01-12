package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.ForestDevelopmentWorkType;

/**
 * @author Nor Azman Mat Ariff
 */
class ForestDevelopmentWorkTypeManager extends MaintenanceTableManager
{
	ForestDevelopmentWorkTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(ForestDevelopmentWorkType forestDevelopmentWorkType, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, forestDevelopmentWorkType.getHeaderNo());
		ps.setString(2, forestDevelopmentWorkType.getDescription());
		ps.setString(3, forestDevelopmentWorkType.getStatus());
		
		if (forestDevelopmentWorkType.getForestDevelopmentWorkTypeID() != 0)
			ps.setInt(4, forestDevelopmentWorkType.getForestDevelopmentWorkTypeID());

		return ps.executeUpdate();
	}

	int addForestDevelopmentWorkType(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		int status = 0;
		PreparedStatement ps = null;
		
		if(checkExistingForestDevelopmentWorkType(forestDevelopmentWorkType) == false)
		{
			ps = facade.prepareStatement("INSERT INTO ForestDevelopmentWorkType (HeaderNo, Description, Status) VALUES (?, ?, ?)");
			status = write(forestDevelopmentWorkType, ps);
		}
		
		if(status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				forestDevelopmentWorkType.setForestDevelopmentWorkTypeID(rs.getInt(1));
		}

		return status;
	}
	
	private boolean checkExistingForestDevelopmentWorkType(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		boolean exist = false;
		
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ForestDevelopmentWorkType WHERE ForestDevelopmentWorkTypeID != ? AND HeaderNo = ? AND Status = 'A'");
		
		ps.setInt(1, forestDevelopmentWorkType.getForestDevelopmentWorkTypeID());
		ps.setInt(2, forestDevelopmentWorkType.getHeaderNo());
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
		{
			exist = true;
		}
		
		return exist;
	}

	int updateForestDevelopmentWorkType(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		int status = 0;

		if(checkExistingForestDevelopmentWorkType(forestDevelopmentWorkType) == false)
		{
			PreparedStatement ps = facade.prepareStatement("UPDATE ForestDevelopmentWorkType SET HeaderNo = ?, Description = ?, Status = ? WHERE ForestDevelopmentWorkTypeID = ?");
			status = write(forestDevelopmentWorkType, ps);
		}
		
		return status;
	}
	
	int deleteForestDevelopmentWorkType(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ForestDevelopmentWorkType SET Status = 'I' WHERE ForestDevelopmentWorkTypeID = ?");
		ps.setInt(1, forestDevelopmentWorkType.getForestDevelopmentWorkTypeID());
		
		return ps.executeUpdate();
	}

	private ForestDevelopmentWorkType read(ResultSet rs) throws SQLException
	{
		ForestDevelopmentWorkType forestDevelopmentWorkType = new ForestDevelopmentWorkType();

		forestDevelopmentWorkType.setForestDevelopmentWorkTypeID(rs.getInt("ForestDevelopmentWorkTypeID"));
		forestDevelopmentWorkType.setHeaderNo(rs.getInt("HeaderNo"));
		forestDevelopmentWorkType.setDescription(rs.getString("Description"));
		forestDevelopmentWorkType.setForestDevelopmentSubWorkTypes(facade.getForestDevelopmentSubWorkTypes(forestDevelopmentWorkType));
		forestDevelopmentWorkType.setStatus(rs.getString("Status"));

		return forestDevelopmentWorkType;
	}
	
	private ArrayList<ForestDevelopmentWorkType> getForestDevelopmentWorkTypes(ResultSet rs) throws SQLException
	{
		ArrayList<ForestDevelopmentWorkType> forestDevelopmentWorkTypes = new ArrayList<>();

		while (rs.next())
			forestDevelopmentWorkTypes.add(read(rs));

		return forestDevelopmentWorkTypes;
	}

	ForestDevelopmentWorkType getForestDevelopmentWorkType(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ForestDevelopmentWorkType WHERE ForestDevelopmentWorkTypeID = ?");

		ps.setInt(1, forestDevelopmentWorkType.getForestDevelopmentWorkTypeID());

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			forestDevelopmentWorkType = read(rs);
		
		return forestDevelopmentWorkType;
	}
	
	ArrayList<ForestDevelopmentWorkType> getForestDevelopmentWorkTypes(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ForestDevelopmentWorkType WHERE Status = ?");

		ps.setString(1, status);

		return getForestDevelopmentWorkTypes(ps.executeQuery());
	}
}