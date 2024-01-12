package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.ForestDevelopmentSubWorkType;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestDevelopmentWorkType;

/**
 * @author Nor Azman Mat Ariff
 */
class ForestDevelopmentSubWorkTypeManager extends MaintenanceTableManager
{
	ForestDevelopmentSubWorkTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, forestDevelopmentSubWorkType.getForestDevelopmentWorkTypeID());
		ps.setString(2, forestDevelopmentSubWorkType.getHeaderNo());
		ps.setString(3, forestDevelopmentSubWorkType.getDescription());
		ps.setString(4, forestDevelopmentSubWorkType.getStatus());
		
		if (forestDevelopmentSubWorkType.getForestDevelopmentSubWorkTypeID() != 0)
			ps.setInt(5, forestDevelopmentSubWorkType.getForestDevelopmentSubWorkTypeID());

		return ps.executeUpdate();
	}

	int addForestDevelopmentSubWorkType(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType) throws SQLException
	{
		int status = 0;
		PreparedStatement ps = null;
		if(checkExistingForestDevelopmentSubWorkType(forestDevelopmentSubWorkType) == false)
		{
			ps = facade.prepareStatement("INSERT INTO ForestDevelopmentSubWorkType (ForestDevelopmentWorkType, HeaderNo, Description, Status) VALUES (?, ?, ?, ?)");
			status = write(forestDevelopmentSubWorkType, ps);
		}
		if(status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				forestDevelopmentSubWorkType.setForestDevelopmentSubWorkTypeID(rs.getInt(1));
		}

		return status;
	}
	
	private boolean checkExistingForestDevelopmentSubWorkType(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType) throws SQLException
	{
		boolean exist = false;
		
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ForestDevelopmentSubWorkType WHERE ForestDevelopmentSubWorkTypeID != ? AND HeaderNo = ? AND Status = 'A'");
		
		ps.setInt(1, forestDevelopmentSubWorkType.getForestDevelopmentSubWorkTypeID());
		ps.setString(2, forestDevelopmentSubWorkType.getHeaderNo());

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
		{
			exist = true;
		}
		
		return exist;
	}

	int updateForestDevelopmentSubWorkType(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType) throws SQLException
	{
		int status = 0;
		
		if(checkExistingForestDevelopmentSubWorkType(forestDevelopmentSubWorkType) == false)
		{
			PreparedStatement ps = facade.prepareStatement("UPDATE ForestDevelopmentSubWorkType SET ForestDevelopmentWorkType = ?, HeaderNo = ?, Description = ?, Status = ? WHERE ForestDevelopmentSubWorkTypeID = ?");
			status = write(forestDevelopmentSubWorkType, ps);
		}
		
		return status;
	}
	
	int deleteForestDevelopmentSubWorkType(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ForestDevelopmentSubWorkType SET Status = 'I' WHERE ForestDevelopmentSubWorkTypeID = ?");
		ps.setInt(1, forestDevelopmentSubWorkType.getForestDevelopmentSubWorkTypeID());
		
		return ps.executeUpdate();
	}

	private ForestDevelopmentSubWorkType read(ResultSet rs) throws SQLException
	{
		ForestDevelopmentSubWorkType forestDevelopmentSubWorkType = new ForestDevelopmentSubWorkType();

		forestDevelopmentSubWorkType.setForestDevelopmentSubWorkTypeID(rs.getInt("ForestDevelopmentSubWorkTypeID"));
		forestDevelopmentSubWorkType.setForestDevelopmentWorkTypeID(rs.getInt("ForestDevelopmentWorkTypeID"));
		forestDevelopmentSubWorkType.setForestDevelopmentWorkTypeHeaderNo(rs.getInt("ForestDevelopmentWorkTypeHeaderNo"));
		forestDevelopmentSubWorkType.setForestDevelopmentWorkTypeDescription(rs.getString("ForestDevelopmentWorkTypeDescription"));		
		forestDevelopmentSubWorkType.setHeaderNo(rs.getString("HeaderNo"));
		forestDevelopmentSubWorkType.setDescription(rs.getString("Description"));
		forestDevelopmentSubWorkType.setStatus(rs.getString("Status"));

		return forestDevelopmentSubWorkType;
	}
	
	private ArrayList<ForestDevelopmentSubWorkType> getForestDevelopmentSubWorkTypes(ResultSet rs) throws SQLException
	{
		ArrayList<ForestDevelopmentSubWorkType> forestDevelopmentSubWorkTypes = new ArrayList<>();

		while (rs.next())
			forestDevelopmentSubWorkTypes.add(read(rs));

		return forestDevelopmentSubWorkTypes;
	}

	ForestDevelopmentSubWorkType getForestDevelopmentSubWorkType(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT fdswt.*, fdwt.HeaderNo AS ForestDevelopmentWorkTypeHeaderNo, fdwt.Description AS ForestDevelopmentWorkTypeDescription " + 
				"FROM ForestDevelopmentSubWorkType fdswt, ForestDevelopmentWorkType fdwt " + 
				"WHERE fdswt.ForestDevelopmentWorkTypeID = fdwt.ForestDevelopmentWorkTypeID AND fdswt.ForestDevelopmentSubWorkTypeID = ?");

		ps.setInt(1, forestDevelopmentSubWorkType.getForestDevelopmentSubWorkTypeID());

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			forestDevelopmentSubWorkType = read(rs);
		
		return forestDevelopmentSubWorkType;
	}
	
	ArrayList<ForestDevelopmentSubWorkType> getForestDevelopmentSubWorkTypes(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT fdswt.*, fdwt.HeaderNo AS ForestDevelopmentWorkTypeHeaderNo, fdwt.Description AS ForestDevelopmentWorkTypeDescription " + 
				"FROM ForestDevelopmentSubWorkType fdswt, ForestDevelopmentWorkType fdwt " + 
				"WHERE fdswt.ForestDevelopmentWorkTypeID = fdwt.ForestDevelopmentWorkTypeID AND fdswt.Status = ?");

		ps.setString(1, status);

		return getForestDevelopmentSubWorkTypes(ps.executeQuery());
	}
	
	ArrayList<ForestDevelopmentSubWorkType> getForestDevelopmentSubWorkTypes(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT fdswt.*, fdwt.HeaderNo AS ForestDevelopmentWorkTypeHeaderNo, fdwt.Description AS ForestDevelopmentWorkTypeDescription " + 
				"FROM ForestDevelopmentSubWorkType fdswt, ForestDevelopmentWorkType fdwt " + 
				"WHERE fdswt.ForestDevelopmentWorkTypeID = fdwt.ForestDevelopmentWorkTypeID AND fdswt.ForestDevelopmentWorkTypeID = ?");

		ps.setInt(1, forestDevelopmentWorkType.getForestDevelopmentWorkTypeID());

		return getForestDevelopmentSubWorkTypes(ps.executeQuery());
	}
}