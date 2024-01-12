package my.edu.utem.ftmk.fis9.hall.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.hall.model.SmallProductTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.TransferPass;

/**
 * @author Nor Azman Mat Ariff
 */
class SmallProductTransferPassRecordManager extends HallTableManager
{
	SmallProductTransferPassRecordManager(HallFacade facade)
	{
		super(facade);
	}

	private int write(SmallProductTransferPassRecord smallProductTransferPassRecord, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, smallProductTransferPassRecord.getTransferPassID());
		ps.setInt(2, smallProductTransferPassRecord.getSmallProductID());
		ps.setInt(3, smallProductTransferPassRecord.getUnitID());
		ps.setLong(4, smallProductTransferPassRecord.getSmallProductRoyaltyRateID());
		ps.setBigDecimal(5, smallProductTransferPassRecord.getQuantity());
		ps.setBigDecimal(6, smallProductTransferPassRecord.getRoyalty());
		ps.setBigDecimal(7, smallProductTransferPassRecord.getCess());
		ps.setLong(8, smallProductTransferPassRecord.getSmallProductTransferPassRecordID());

		return ps.executeUpdate();
	}

	int addSmallProductTransferPassRecord(SmallProductTransferPassRecord smallProductTransferPassRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO SmallProductTransferPassRecord (TransferPassID, SmallProductID, UnitID, SmallProductRoyaltyRateID, Quantity, Royalty, Cess, SmallProductTransferPassRecordID) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"); 
			
		return write(smallProductTransferPassRecord, ps);
	}
	
	int updateSmallProductTransferPassRecord(SmallProductTransferPassRecord smallProductTransferPassRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE SmallProductTransferPassRecord SET "
				+ "TransferPassID = ?, SmallProductID = ?, UnitID = ?, SmallProductRoyaltyRateID = ?, Quantity = ?, Royalty = ?, Cess = ? "
				+ "WHERE SmallProductTransferPassRecordID = ?");

		return write(smallProductTransferPassRecord, ps);
	}

	int deleteSmallProductTransferPassRecord(SmallProductTransferPassRecord smallProductTransferPassRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM SmallProductTransferPassRecord WHERE SmallProductTransferPassRecordID = ?");
		ps.setLong(1, smallProductTransferPassRecord.getSmallProductTransferPassRecordID());

		return ps.executeUpdate();
	}

	private SmallProductTransferPassRecord read(ResultSet rs) throws SQLException
	{
		SmallProductTransferPassRecord smallProductTransferPassRecord = new SmallProductTransferPassRecord();

		smallProductTransferPassRecord.setSmallProductTransferPassRecordID(rs.getLong("SmallProductTransferPassRecordID"));
		smallProductTransferPassRecord.setTransferPassID(rs.getLong("TransferPassID"));
		smallProductTransferPassRecord.setSmallProductID(rs.getInt("SmallProductID"));
		smallProductTransferPassRecord.setSmallProductCode(rs.getString("SmallProductCode"));
		smallProductTransferPassRecord.setSmallProductName(rs.getString("SmallProductName"));
		smallProductTransferPassRecord.setSmallProductSymbol(rs.getString("SmallProductSymbol"));
		smallProductTransferPassRecord.setUnitID(rs.getInt("UnitID"));
		smallProductTransferPassRecord.setUnitCode(rs.getString("UnitCode"));
		smallProductTransferPassRecord.setUnitName(rs.getString("UnitName"));
		smallProductTransferPassRecord.setSmallProductRoyaltyRateID(rs.getLong("SmallProductRoyaltyRateID"));
		smallProductTransferPassRecord.setRoyaltyRate(rs.getBigDecimal("RoyaltyRate"));
		smallProductTransferPassRecord.setCessRate(rs.getBigDecimal("CessRate"));
		smallProductTransferPassRecord.setQuantity(rs.getBigDecimal("Quantity"));
		smallProductTransferPassRecord.setRoyalty(rs.getBigDecimal("Royalty"));
		smallProductTransferPassRecord.setCess(rs.getBigDecimal("Cess"));

		return smallProductTransferPassRecord;
	}

	private ArrayList<SmallProductTransferPassRecord> getSmallProductTransferPassRecords(ResultSet rs) throws SQLException
	{
		ArrayList<SmallProductTransferPassRecord> smallProductTransferPassRecords = new ArrayList<>();

		while (rs.next())
		{
			SmallProductTransferPassRecord smallProductTransferPassRecord = read(rs);
			smallProductTransferPassRecords.add(smallProductTransferPassRecord);
		}
		return smallProductTransferPassRecords;
	}

	SmallProductTransferPassRecord getSmallProductTransferPassRecord(long smallProductTransferPassRecordID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT sptpr.*, sp.Code AS SmallProductCode, sp.Name AS SmallProductName, sp.Symbol AS SmallProductSymbol, u.Code AS UnitCode, u.Name AS UnitName, sprr.RoyaltyRate AS RoyaltyRate, sprr.CessRate AS CessRate " + 
				"FROM SmallProductTransferPassRecord sptpr, TransferPass tp, SmallProduct sp, Unit u, SmallProductRoyaltyRate sprr " + 
				"WHERE sptpr.TransferPassID = tp.TransferPassID AND sptpr.SmallProductID = sp.SmallProductID AND sptpr.UnitID = u.UnitID AND sptpr.SmallProductRoyaltyRateID = sprr.SmallProductRoyaltyRateID AND sptpr.SmallProductTransferPassRecordID = ?");
		
		ps.setLong(1, smallProductTransferPassRecordID);

		return read(ps.executeQuery());
	}
	
	ArrayList<SmallProductTransferPassRecord> getSmallProductTransferPassRecords() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT sptpr.*, sp.Code AS SmallProductCode, sp.Name AS SmallProductName, sp.Symbol AS SmallProductSymbol, u.Code AS UnitCode, u.Name AS UnitName, sprr.RoyaltyRate AS RoyaltyRate, sprr.CessRate AS CessRate " + 
				"FROM SmallProductTransferPassRecord sptpr, TransferPass tp, SmallProduct sp, Unit u, SmallProductRoyaltyRate sprr " + 
				"WHERE sptpr.TransferPassID = tp.TransferPassID AND sptpr.SmallProductID = sp.SmallProductID AND sptpr.UnitID = u.UnitID AND sptpr.SmallProductRoyaltyRateID = sprr.SmallProductRoyaltyRateID");

		return getSmallProductTransferPassRecords(ps.executeQuery());
	}
	
	ArrayList<SmallProductTransferPassRecord> getSmallProductTransferPassRecords(TransferPass transferPass) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT sptpr.*, sp.Code AS SmallProductCode, sp.Name AS SmallProductName, sp.Symbol AS SmallProductSymbol, u.Code AS UnitCode, u.Name AS UnitName, sprr.RoyaltyRate AS RoyaltyRate, sprr.CessRate AS CessRate " + 
				"FROM SmallProductTransferPassRecord sptpr, TransferPass tp, SmallProduct sp, Unit u, SmallProductRoyaltyRate sprr " + 
				"WHERE sptpr.TransferPassID = tp.TransferPassID AND sptpr.SmallProductID = sp.SmallProductID AND sptpr.UnitID = u.UnitID AND sptpr.SmallProductRoyaltyRateID = sprr.SmallProductRoyaltyRateID AND sptpr.TransferPassID = ?");
		
		ps.setLong(1, transferPass.getTransferPassID());

		return getSmallProductTransferPassRecords(ps.executeQuery());
	}
}