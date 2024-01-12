package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.SmallProduct;

/**
 * @author Nor Azman Mat Ariff
 */
class SmallProductManager extends MaintenanceTableManager
{
	SmallProductManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(SmallProduct smallProduct, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, smallProduct.getProductGroupID());
		ps.setString(2, smallProduct.getCode());
		ps.setString(3, smallProduct.getName());
		ps.setString(4, smallProduct.getStatus());

		if (smallProduct.getSmallProductID() != 0)
			ps.setInt(5, smallProduct.getSmallProductID());

		return ps.executeUpdate();
	}

	int addSmallProduct(SmallProduct smallProduct) throws SQLException
	{
		int status = 0;
		if(checkExistingCode(smallProduct) == null)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO SmallProduct (ProductGroupID, Code, Name, Status) VALUES (?, ?, ?, ?)");
			status = write(smallProduct, ps);
	
			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
	
				if (rs.next())
					smallProduct.setSmallProductID(rs.getInt(1));
			}
		}

		return status;
	}
	
	SmallProduct checkExistingCode(SmallProduct smallProduct) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, p.Code AS ProductGroupCode, p.Name AS ProductGroupName FROM SmallProduct s, ProductGroup p WHERE s.ProductGroupID = p.ProductGroupID AND s.Code = ? AND s.Status = 'A'");
		ps.setString(1, smallProduct.getCode());
		
		ResultSet rs = ps.executeQuery();
		
		SmallProduct oldSmallProduct = null;
		
		if (rs.next())
		{
			oldSmallProduct = read(rs);
		}
		
		return oldSmallProduct;
	}

	int updateSmallProduct(SmallProduct smallProduct) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE SmallProduct SET ProductGroupID = ?, Code = ?, Name = ?, Status = ? WHERE SmallProductID = ?");

		return write(smallProduct, ps);
	}
	
	int deleteSmallProduct(SmallProduct smallProduct) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE SmallProduct SET Status = 'I' WHERE SmallProductID = ?");
		ps.setInt(1, smallProduct.getSmallProductID());

		return ps.executeUpdate();
	}

	private SmallProduct read(ResultSet rs) throws SQLException
	{
		SmallProduct smallProduct = new SmallProduct();

		smallProduct.setSmallProductID(rs.getInt("SmallProductID"));
		smallProduct.setProductGroupID(rs.getInt("ProductGroupID"));
		smallProduct.setProductGroupCode(rs.getString("ProductGroupCode"));
		smallProduct.setProductGroupName(rs.getString("ProductGroupName"));
		smallProduct.setCode(rs.getString("Code"));
		smallProduct.setName(rs.getString("Name"));
		smallProduct.setStatus(rs.getString("Status"));

		return smallProduct;
	}
	
	private ArrayList<SmallProduct> getSmallProducts(ResultSet rs) throws SQLException
	{
		ArrayList<SmallProduct> smallProducts = new ArrayList<>();

		while (rs.next())
		{
			smallProducts.add(read(rs));
		}
		return smallProducts;
	}

	SmallProduct getSmallProduct(int smallProductID) throws SQLException
	{
		SmallProduct smallProduct = null;
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, p.Code AS ProductGroupCode, p.Name AS ProductGroupName FROM SmallProduct s, ProductGroup p WHERE s.ProductGroupID = p.ProductGroupID AND s.SmallProductID = ?");

		ps.setInt(1, smallProductID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			smallProduct = read(rs);
		
		return smallProduct;
	}
	
	ArrayList<SmallProduct> getSmallProducts(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT s.*, p.Code AS ProductGroupCode, p.Name AS ProductGroupName FROM SmallProduct s, ProductGroup p WHERE s.ProductGroupID = p.ProductGroupID AND s.Status = ? ORDER BY s.Code");

		ps.setString(1, status);

		return getSmallProducts(ps.executeQuery());
	}
}