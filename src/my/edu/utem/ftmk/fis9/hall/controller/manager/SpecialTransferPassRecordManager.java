package my.edu.utem.ftmk.fis9.hall.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.hall.model.SpecialTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.TransferPass;

/**
 * @author Nor Azman Mat Ariff
 */
class SpecialTransferPassRecordManager extends HallTableManager
{
	SpecialTransferPassRecordManager(HallFacade facade)
	{
		super(facade);
	}

	private int write(SpecialTransferPassRecord specialTransferPassRecord, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, specialTransferPassRecord.getTransferPassID());
		ps.setInt(2, specialTransferPassRecord.getSpeciesID());
		ps.setLong(3, specialTransferPassRecord.getMainRevenueRoyaltyRateID());
		ps.setBigDecimal(4, specialTransferPassRecord.getLength());
		ps.setBigDecimal(5, specialTransferPassRecord.getDiameter());
		ps.setBigDecimal(6, specialTransferPassRecord.getRoyalty());
		ps.setBigDecimal(7, specialTransferPassRecord.getCess());
		ps.setLong(8, specialTransferPassRecord.getSpecialTransferPassRecordID());

		return ps.executeUpdate();
	}

	int addSpecialTransferPassRecord(SpecialTransferPassRecord specialTransferPassRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO SpecialTransferPassRecord (TransferPassID, SpeciesID, MainRevenueRoyaltyRateID, Length, Diameter, Royalty, Cess, SpecialTransferPassRecordID) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"); 
			
		return write(specialTransferPassRecord, ps);
	}
	
	int updateSpecialTransferPassRecord(SpecialTransferPassRecord specialTransferPassRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE SpecialTransferPassRecord SET "
				+ "TransferPassID = ?, SpeciesID = ?, MainRevenueRoyaltyRateID = ?, Length = ?, Diameter = ?, Royalty = ?, Cess = ? "
				+ "WHERE SpecialTransferPassRecordID = ?");

		return write(specialTransferPassRecord, ps);
	}

	private SpecialTransferPassRecord readSpecialTransferPassRecord(ResultSet rs) throws SQLException
	{
		SpecialTransferPassRecord specialTransferPassRecord = new SpecialTransferPassRecord();

		specialTransferPassRecord.setSpecialTransferPassRecordID(rs.getLong("SpecialTransferPassRecordID"));
		specialTransferPassRecord.setTransferPassID(rs.getLong("TransferPassID"));
		specialTransferPassRecord.setSpeciesID(rs.getInt("SpeciesID"));
		specialTransferPassRecord.setSpeciesName(rs.getString("SpeciesName"));
		specialTransferPassRecord.setSpeciesCode(rs.getString("SpeciesCode"));
		specialTransferPassRecord.setSpeciesSymbol(rs.getString("SpeciesSymbol"));
		specialTransferPassRecord.setMainRevenueRoyaltyRateID(rs.getLong("MainRevenueRoyaltyRateID"));
		specialTransferPassRecord.setBigSizeRoyaltyRate(rs.getBigDecimal("BigSizeRoyaltyRate"));
		specialTransferPassRecord.setSmallSizeRoyaltyRate(rs.getBigDecimal("SmallSizeRoyaltyRate"));
		specialTransferPassRecord.setCessRate(rs.getBigDecimal("CessRate"));
		specialTransferPassRecord.setJarasRoyaltyRate(rs.getBigDecimal("JarasRoyaltyRate"));
		specialTransferPassRecord.setJarasCessRate(rs.getBigDecimal("JarasCessRate"));
		specialTransferPassRecord.setLength(rs.getBigDecimal("Length"));
		specialTransferPassRecord.setDiameter(rs.getBigDecimal("Diameter"));
		specialTransferPassRecord.setRoyalty(rs.getBigDecimal("Royalty"));
		specialTransferPassRecord.setCess(rs.getBigDecimal("Cess"));

		return specialTransferPassRecord;
	}

	private ArrayList<SpecialTransferPassRecord> getSpecialTransferPassRecords(ResultSet rs) throws SQLException
	{
		ArrayList<SpecialTransferPassRecord> specialTransferPassRecordes = new ArrayList<>();

		while (rs.next())
		{
			SpecialTransferPassRecord specialTransferPassRecord = readSpecialTransferPassRecord(rs);
			specialTransferPassRecordes.add(specialTransferPassRecord);
		}
		return specialTransferPassRecordes;
	}

	SpecialTransferPassRecord getSpecialTransferPassRecord(long specialTransferPassRecordID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT stpr.*, s.Name AS SpeciesName, s.Code AS SpeciesCode, s.Symbol AS SpeciesSymbol, mrrr.BigSizeRoyaltyRate, mrrr.SmallSizeRoyaltyRate, mrrr.CessRate, mrrr.JarasRoyaltyRate, mrrr.JarasCessRate " + 
				"FROM SpecialTransferPassRecord stpr, TransferPass tp, Species s, MainRevenueRoyaltyRate mrrr " + 
				"WHERE stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND stpr.specialTransferPassRecordID = ?");
		
		ps.setLong(1, specialTransferPassRecordID);

		SpecialTransferPassRecord specialTransferPassRecord = null;
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			specialTransferPassRecord = readSpecialTransferPassRecord(rs);
		}

		return specialTransferPassRecord;
	}
	
	ArrayList<SpecialTransferPassRecord> getSpecialTransferPassRecords() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT stpr.*, s.Name AS SpeciesName, s.Code AS SpeciesCode, s.Symbol AS SpeciesSymbol, mrrr.BigSizeRoyaltyRate, mrrr.SmallSizeRoyaltyRate, mrrr.CessRate, mrrr.JarasRoyaltyRate, mrrr.JarasCessRate " + 
				"FROM SpecialTransferPassRecord stpr, TransferPass tp, Species s, MainRevenueRoyaltyRate mrrr " + 
				"WHERE stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID");

		return getSpecialTransferPassRecords(ps.executeQuery());
	}
	
	ArrayList<SpecialTransferPassRecord> getSpecialTransferPassRecords(TransferPass transferPass) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT stpr.*, s.Name AS SpeciesName, s.Code AS SpeciesCode, s.Symbol AS SpeciesSymbol, mrrr.BigSizeRoyaltyRate, mrrr.SmallSizeRoyaltyRate, mrrr.CessRate, mrrr.JarasRoyaltyRate, mrrr.JarasCessRate " + 
				"FROM SpecialTransferPassRecord stpr, TransferPass tp, Species s, MainRevenueRoyaltyRate mrrr " + 
				"WHERE stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND stpr.TransferPassID = ?");
		
		ps.setLong(1, transferPass.getTransferPassID());

		return getSpecialTransferPassRecords(ps.executeQuery());
	}
}