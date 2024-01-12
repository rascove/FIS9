package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.ProductGroup;

/**
 * @author Nor Azman Mat Ariff
 */
class ProductGroupManager extends MaintenanceTableManager
{
	ProductGroupManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(ProductGroup productGroup, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, productGroup.getCode());
		ps.setString(2, productGroup.getName());
		ps.setString(3, productGroup.getStatus());

		if (productGroup.getProductGroupID() != 0)
			ps.setInt(4, productGroup.getProductGroupID());

		return ps.executeUpdate();
	}

	int addProductGroup(ProductGroup productGroup) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(productGroup) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO ProductGroup (Code, Name, Status) VALUES (?, ?, ?)");
			status = write(productGroup, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					productGroup.setProductGroupID(rs.getInt(1));
			}
		}
		
		return status;
	}
	
	ProductGroup checkExistingCode(ProductGroup productGroup) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ProductGroup WHERE Code = ? AND Status = 'A'");
		ps.setString(1, productGroup.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		ProductGroup oldProductGroup = null;
		
		if (rs.next())
		{
			oldProductGroup = read(rs);
		}
		
		return oldProductGroup;
	}

	int updateProductGroup(ProductGroup productGroup) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ProductGroup SET Code = ?, Name = ?, Status = ? WHERE ProductGroupID = ?");

		return write(productGroup, ps);
	}
	
	int deleteProductGroup(ProductGroup productGroup) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ProductGroup SET Status = 'I' WHERE ProductGroupID = ?");
		ps.setInt(1, productGroup.getProductGroupID());

		return ps.executeUpdate();
	}

	private ProductGroup read(ResultSet rs) throws SQLException
	{
		ProductGroup productGroup = new ProductGroup();

		productGroup.setProductGroupID(rs.getInt("ProductGroupID"));
		productGroup.setCode(rs.getString("Code"));
		productGroup.setName(rs.getString("Name"));
		productGroup.setStatus(rs.getString("Status"));

		return productGroup;
	}
	
	private ArrayList<ProductGroup> getProductGroups(ResultSet rs) throws SQLException
	{
		ArrayList<ProductGroup> productGroups = new ArrayList<>();

		while (rs.next())
		{
			productGroups.add(read(rs));
		}
		return productGroups;
	}

	ProductGroup getProductGroup(int productGroupID) throws SQLException
	{
		ProductGroup productGroup = null;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ProductGroup WHERE ProductGroupID = ?");

		ps.setInt(1, productGroupID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			productGroup = read(rs);
		
		return productGroup;
	}
	
	ArrayList<ProductGroup> getProductGroups(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ProductGroup WHERE Status = ? ORDER BY Code");

		ps.setString(1, status);

		return getProductGroups(ps.executeQuery());
	}
}