package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.ForestCategory;

/**
 * @author Nor Azman Mat Ariff
 */
class ForestCategoryManager extends MaintenanceTableManager
{
	ForestCategoryManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(ForestCategory forestCategory, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, forestCategory.getCode());
		ps.setString(2, forestCategory.getName());
		ps.setString(3, forestCategory.getStatus());

		if (forestCategory.getForestCategoryID() != 0)
			ps.setInt(4, forestCategory.getForestCategoryID());

		return ps.executeUpdate();
	}

	int addForestCategory(ForestCategory forestCategory) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(forestCategory) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO ForestCategory (Code, Name, Status) VALUES (?, ?, ?)");
			status = write(forestCategory, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					forestCategory.setForestCategoryID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	private ForestCategory checkExistingCode(ForestCategory forestCategory) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ForestCategory WHERE Code = ? AND Status = 'A'");
		ps.setString(1, forestCategory.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		ForestCategory oldForestCategory = null;
		
		if (rs.next())
		{
			oldForestCategory = read(rs);
		}
		
		return oldForestCategory;
	}

	int updateForestCategory(ForestCategory forestCategory) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ForestCategory SET Code = ?, Name = ?, Status = ? WHERE ForestCategoryID = ?");

		return write(forestCategory, ps);
	}
	
	int deleteForestCategory(ForestCategory forestCategory) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ForestCategory SET Status = 'I' WHERE ForestCategoryID = ?");
		ps.setInt(1, forestCategory.getForestCategoryID());

		return ps.executeUpdate();
	}

	private ForestCategory read(ResultSet rs) throws SQLException
	{
		ForestCategory forestCategory = new ForestCategory();

		forestCategory.setForestCategoryID(rs.getInt("ForestCategoryID"));
		forestCategory.setCode(rs.getString("Code"));
		forestCategory.setName(rs.getString("Name"));
		forestCategory.setStatus(rs.getString("Status"));

		return forestCategory;
	}
	
	private ArrayList<ForestCategory> getForestCategorys(ResultSet rs) throws SQLException
	{
		ArrayList<ForestCategory> forestCategorys = new ArrayList<>();

		while (rs.next())
		{
			forestCategorys.add(read(rs));
		}
		return forestCategorys;
	}

	ForestCategory getForestCategory(int forestCategoryID) throws SQLException
	{
		ForestCategory forestCategory = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ForestCategory WHERE ForestCategoryID = ?");

		ps.setInt(1, forestCategoryID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			forestCategory = read(rs);
		
		return forestCategory;
	}
	
	ArrayList<ForestCategory> getForestCategorys(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ForestCategory WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getForestCategorys(ps.executeQuery());
	}
}