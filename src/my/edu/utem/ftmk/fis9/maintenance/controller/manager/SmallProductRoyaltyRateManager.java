package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.SmallProductRoyaltyRate;

/**
 * @author Nor Azman Mat Ariff
 */
class SmallProductRoyaltyRateManager extends MaintenanceTableManager
{
	SmallProductRoyaltyRateManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(SmallProductRoyaltyRate smallProductRoyaltyRate, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, smallProductRoyaltyRate.getSmallProductID());
		ps.setInt(2, smallProductRoyaltyRate.getUnitID());
		ps.setBigDecimal(3, smallProductRoyaltyRate.getRoyaltyRate());
		ps.setBigDecimal(4, smallProductRoyaltyRate.getCessRate());
		ps.setLong(5, smallProductRoyaltyRate.getSmallProductRoyaltyRateID());
		
		return ps.executeUpdate();
	}

	int addSmallProductRoyaltyRate(SmallProductRoyaltyRate smallProductRoyaltyRate) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO SmallProductRoyaltyRate (SmallProductID, UnitID, RoyaltyRate, CessRate, StartDate, EndDate, Status, SmallProductRoyaltyRateID) VALUES (?, ?, ?, ?, CURRENT_DATE, NULL, 'A', ?) ON DUPLICATE KEY UPDATE EndDate = CURRENT_DATE, Status = NULL");
		int status = write(smallProductRoyaltyRate, ps);
	
		if (status == 2)
			status = ps.executeUpdate();
		
		return status;
	}
	
	int deleteSmallProductRoyaltyRate(SmallProductRoyaltyRate smallProductRoyaltyRate) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE SmallProductRoyaltyRate SET EndDate = CURRENT_DATE, Status = NULL WHERE SmallProductRoyaltyRateID = ?");
		ps.setLong(1, smallProductRoyaltyRate.getSmallProductRoyaltyRateID());

		return ps.executeUpdate();
	}

	private SmallProductRoyaltyRate read(ResultSet rs) throws SQLException
	{
		SmallProductRoyaltyRate smallProductRoyaltyRate = new SmallProductRoyaltyRate();

		smallProductRoyaltyRate.setSmallProductRoyaltyRateID(rs.getLong("SmallProductRoyaltyRateID"));
		smallProductRoyaltyRate.setSmallProductID(rs.getInt("SmallProductID"));
		smallProductRoyaltyRate.setSmallProductCode(rs.getString("SmallProductCode"));
		smallProductRoyaltyRate.setSmallProductName(rs.getString("SmallProductName"));
		smallProductRoyaltyRate.setUnitID(rs.getInt("UnitID"));
		smallProductRoyaltyRate.setUnitCode(rs.getString("UnitCode"));
		smallProductRoyaltyRate.setUnitName(rs.getString("UnitName"));
		smallProductRoyaltyRate.setRoyaltyRate(rs.getBigDecimal("RoyaltyRate"));
		smallProductRoyaltyRate.setCessRate(rs.getBigDecimal("CessRate"));
		smallProductRoyaltyRate.setStartDate(rs.getDate("StartDate"));
		smallProductRoyaltyRate.setEndDate(rs.getDate("EndDate"));
		smallProductRoyaltyRate.setStatus(rs.getString("Status"));
		
		if (smallProductRoyaltyRate.getStatus() == null)
			smallProductRoyaltyRate.setStatus("I");

		return smallProductRoyaltyRate;
	}
	
	private ArrayList<SmallProductRoyaltyRate> getSmallProductRoyaltyRates(ResultSet rs) throws SQLException
	{
		ArrayList<SmallProductRoyaltyRate> smallProductRoyaltyRates = new ArrayList<>();

		while (rs.next())
		{
			smallProductRoyaltyRates.add(read(rs));
		}
		return smallProductRoyaltyRates;
	}

	SmallProductRoyaltyRate getSmallProductRoyaltyRate(long smallProductRoyaltyRateID) throws SQLException
	{
		SmallProductRoyaltyRate smallProductRoyaltyRate = null;
		PreparedStatement ps = facade.prepareStatement("SELECT sprr.*, sp.Code AS SmallProductCode, sp.Name AS SmallProductName, u.Code AS UnitCode, u.Name AS UnitName FROM SmallProductRoyaltyRate sprr, SmallProduct sp, Unit u WHERE sprr.SmallProductID = sp.SmallProductID AND sprr.UnitID = u.UnitID AND sprr.SmallProductRoyaltyRateID = ?");

		ps.setLong(1, smallProductRoyaltyRateID);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			smallProductRoyaltyRate = read(rs);
		
		return smallProductRoyaltyRate;
	}
	
	ArrayList<SmallProductRoyaltyRate> getSmallProductRoyaltyRates(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT sprr.*, sp.Code AS SmallProductCode, sp.Name AS SmallProductName, u.Code AS UnitCode, u.Name AS UnitName FROM SmallProductRoyaltyRate sprr, SmallProduct sp, Unit u WHERE sprr.SmallProductID = sp.SmallProductID AND sprr.UnitID = u.UnitID AND sprr.Status = ? ORDER BY SmallProductCode");

		ps.setString(1, status);

		return getSmallProductRoyaltyRates(ps.executeQuery());
	}
}