package my.edu.utem.ftmk.fis9.hall.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.hall.model.MainRevenueTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.TransferPass;

/**
 * @author Nor Azman Mat Ariff
 */
class MainRevenueTransferPassRecordManager extends HallTableManager
{
	MainRevenueTransferPassRecordManager(HallFacade facade)
	{
		super(facade);
	}

	private int write(MainRevenueTransferPassRecord mainRevenueTransferPassRecord, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, mainRevenueTransferPassRecord.getTransferPassID());
		ps.setLong(2, mainRevenueTransferPassRecord.getLogID());
		ps.setLong(3, mainRevenueTransferPassRecord.getMainRevenueRoyaltyRateID());
		ps.setBigDecimal(4, mainRevenueTransferPassRecord.getRoyalty());
		ps.setBigDecimal(5, mainRevenueTransferPassRecord.getCess());
		ps.setLong(6, mainRevenueTransferPassRecord.getMainRevenueTransferPassRecordID());

		return ps.executeUpdate();
	}

	int addMainRevenueTransferPassRecord(MainRevenueTransferPassRecord mainRevenueTransferPassRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO MainRevenueTransferPassRecord (TransferPassID, LogID, MainRevenueRoyaltyRateID, Royalty, Cess, MainRevenueTransferPassRecordID) "
					+ "VALUES (?, ?, ?, ?, ?, ?)"); 
			
		return write(mainRevenueTransferPassRecord, ps);
	}
	
	int updateMainRevenueTransferPassRecord(MainRevenueTransferPassRecord mainRevenueTransferPassRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE MainRevenueTransferPassRecord SET "
				+ "TransferPassID = ?, LogID = ?, MainRevenueRoyaltyRateID = ?, Royalty = ?, Cess = ? "
				+ "WHERE MainRevenueTransferPassRecordID = ?");

		return write(mainRevenueTransferPassRecord, ps);
	}

	int deleteMainRevenueTransferPassRecord(MainRevenueTransferPassRecord mainRevenueTransferPassRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM MainRevenueTransferPassRecord WHERE MainRevenueTransferPassRecordID = ?");
		ps.setLong(1, mainRevenueTransferPassRecord.getMainRevenueTransferPassRecordID());

		return ps.executeUpdate();
	}

	private MainRevenueTransferPassRecord readMainRevenueTransferPassRecord(ResultSet rs) throws SQLException
	{
		MainRevenueTransferPassRecord mainRevenueTransferPassRecord = new MainRevenueTransferPassRecord();

		mainRevenueTransferPassRecord.setMainRevenueTransferPassRecordID(rs.getLong("MainRevenueTransferPassRecordID"));
		mainRevenueTransferPassRecord.setTransferPassID(rs.getLong("TransferPassID"));
		mainRevenueTransferPassRecord.setLogID(rs.getLong("LogID"));
		mainRevenueTransferPassRecord.setTaggingRecordID(rs.getLong("TaggingRecordID"));
		mainRevenueTransferPassRecord.setSpeciesID(rs.getInt("SpeciesID"));		
		mainRevenueTransferPassRecord.setSpeciesName(rs.getString("SpeciesName"));
		mainRevenueTransferPassRecord.setSpeciesCode(rs.getString("SpeciesCode"));
		mainRevenueTransferPassRecord.setSpeciesSymbol(rs.getString("SpeciesSymbol"));
		mainRevenueTransferPassRecord.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));		
		mainRevenueTransferPassRecord.setSpeciesTypeName(rs.getString("SpeciesTypeName"));		
		mainRevenueTransferPassRecord.setLogNo(rs.getInt("LogNo"));
		mainRevenueTransferPassRecord.setLogSerialNo(rs.getString("LogSerialNo"));
		mainRevenueTransferPassRecord.setLength(rs.getBigDecimal("Length"));
		mainRevenueTransferPassRecord.setDiameter(rs.getBigDecimal("Diameter"));
		mainRevenueTransferPassRecord.setHoleDiameter(rs.getBigDecimal("HoleDiameter"));
		mainRevenueTransferPassRecord.setMainRevenueRoyaltyRateID(rs.getLong("MainRevenueRoyaltyRateID"));
		mainRevenueTransferPassRecord.setBigSizeRoyaltyRate(rs.getBigDecimal("BigSizeRoyaltyRate"));
		mainRevenueTransferPassRecord.setSmallSizeRoyaltyRate(rs.getBigDecimal("SmallSizeRoyaltyRate"));
		mainRevenueTransferPassRecord.setCessRate(rs.getBigDecimal("CessRate"));
		mainRevenueTransferPassRecord.setJarasRoyaltyRate(rs.getBigDecimal("JarasRoyaltyRate"));
		mainRevenueTransferPassRecord.setJarasCessRate(rs.getBigDecimal("JarasCessRate"));
		mainRevenueTransferPassRecord.setRoyalty(rs.getBigDecimal("Royalty"));
		mainRevenueTransferPassRecord.setCess(rs.getBigDecimal("Cess"));

		return mainRevenueTransferPassRecord;
	}

	private ArrayList<MainRevenueTransferPassRecord> getMainRevenueTransferPassRecords(ResultSet rs) throws SQLException
	{
		ArrayList<MainRevenueTransferPassRecord> mainRevenueTransferPassRecordes = new ArrayList<>();

		while (rs.next())
		{
			MainRevenueTransferPassRecord mainRevenueTransferPassRecord = readMainRevenueTransferPassRecord(rs);
			mainRevenueTransferPassRecordes.add(mainRevenueTransferPassRecord);
		}
		return mainRevenueTransferPassRecordes;
	}

	MainRevenueTransferPassRecord getMainRevenueTransferPassRecord(long mainRevenueTransferPassRecordID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT mrtpr.*, l.*, s.SpeciesID, s.Name AS SpeciesName, s.Code AS SpeciesCode, s.Symbol AS SpeciesSymbol, st.SpeciesTypeID, st.Name AS SpeciesTypeName, mrrr.BigSizeRoyaltyRate, mrrr.SmallSizeRoyaltyRate, mrrr.CessRate, mrrr.JarasRoyaltyRate, mrrr.JarasCessRate " + 
				"FROM MainRevenueTransferPassRecord mrtpr, TransferPass tp, Log l, TaggingRecord tr, Species s, SpeciesType st, MainRevenueRoyaltyRate mrrr " + 
				"WHERE mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = l.LogID AND l.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND s.SpeciesTypeID = st.SpeciesTypeID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND mrtpr.mainRevenueTransferPassRecordID = ?");
		
		ps.setLong(1, mainRevenueTransferPassRecordID);

		MainRevenueTransferPassRecord mainRevenueTransferPassRecord = null;
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			mainRevenueTransferPassRecord = readMainRevenueTransferPassRecord(rs);
		}

		return mainRevenueTransferPassRecord;
	}
	
	ArrayList<MainRevenueTransferPassRecord> getMainRevenueTransferPassRecords() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT mrtpr.*, l.*, s.SpeciesID, s.Name AS SpeciesName, s.Code AS SpeciesCode,s.Symbol AS SpeciesSymbol, st.SpeciesTypeID, st.Name AS SpeciesTypeName, mrrr.BigSizeRoyaltyRate, mrrr.SmallSizeRoyaltyRate, mrrr.CessRate, mrrr.JarasRoyaltyRate, mrrr.JarasCessRate " + 
				"FROM MainRevenueTransferPassRecord mrtpr, TransferPass tp, Log l, TaggingRecord tr, Species s, SpeciesType st, MainRevenueRoyaltyRate mrrr " + 
				"WHERE mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = l.LogID AND l.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND s.SpeciesTypeID = st.SpeciesTypeID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID");

		return getMainRevenueTransferPassRecords(ps.executeQuery());
	}
	
	ArrayList<MainRevenueTransferPassRecord> getMainRevenueTransferPassRecords(TransferPass transferPass) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT mrtpr.*, l.*, s.SpeciesID, s.Name AS SpeciesName, s.Code AS SpeciesCode, s.Symbol AS SpeciesSymbol, st.SpeciesTypeID, st.Name AS SpeciesTypeName, mrrr.BigSizeRoyaltyRate, mrrr.SmallSizeRoyaltyRate, mrrr.CessRate, mrrr.JarasRoyaltyRate, mrrr.JarasCessRate " + 
				"FROM MainRevenueTransferPassRecord mrtpr, TransferPass tp, Log l, TaggingRecord tr, Species s, SpeciesType st, MainRevenueRoyaltyRate mrrr " + 
				"WHERE mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = l.LogID AND l.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND s.SpeciesTypeID = st.SpeciesTypeID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND mrtpr.TransferPassID = ?");
		
		ps.setLong(1, transferPass.getTransferPassID());

		return getMainRevenueTransferPassRecords(ps.executeQuery());
	}
}