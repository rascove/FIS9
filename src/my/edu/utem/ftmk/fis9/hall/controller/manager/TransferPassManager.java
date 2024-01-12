package my.edu.utem.ftmk.fis9.hall.controller.manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import my.edu.utem.ftmk.fis9.hall.model.MainRevenueTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.SmallProductTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.SpecialTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.TransferPass;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.model.Journal;
import my.edu.utem.ftmk.fis9.revenue.model.License;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;

/**
 * @author Nor Azman Mat Ariff
 */
class TransferPassManager extends HallTableManager
{

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	TransferPassManager(HallFacade facade)
	{
		super(facade);
	}

	private int write(TransferPass transferPass, PreparedStatement ps)
			throws SQLException
	{
		ps.setString(1, transferPass.getTransferPassNo());
		ps.setString(2, transferPass.getBatchNo());
		ps.setDate(3, toSQLDate(transferPass.getDate()));
		ps.setLong(4, transferPass.getHallOfficerID());
		ps.setInt(5, transferPass.getCode());
		ps.setInt(6, transferPass.getRoyaltyRate());
		ps.setInt(7, transferPass.getCessRate());
		ps.setInt(8, transferPass.getPremiumRate());
		ps.setLong(9, transferPass.getLicenseID());
		ps.setString(10, transferPass.getDestinationAddress());
		ps.setInt(11, transferPass.getDestinationStateID());
		ps.setString(12, transferPass.getDriverName());
		ps.setString(13, transferPass.getDriverICNo());
		ps.setString(14, transferPass.getDriverAddress());
		ps.setString(15, transferPass.getVehicleNo());
		ps.setBigDecimal(16, transferPass.getGrossVehicleWeight());
		nullable(ps, 17, transferPass.getTaggingID());
		nullable(ps, 18, transferPass.getLogSizeID());
		ps.setBigDecimal(19, transferPass.getRoyaltyAmount());
		ps.setBigDecimal(20, transferPass.getCessAmount());
		ps.setBigDecimal(21, transferPass.getPremiumAmount());
		ps.setString(22, transferPass.getRecorderID());
		ps.setTimestamp(23, transferPass.getRecordTime());
		nullable(ps, 24, transferPass.getJournalID());
		ps.setString(25, transferPass.getRemarks());
		ps.setString(26, transferPass.getStatus());
		ps.setLong(27, transferPass.getTransferPassID());

		return ps.executeUpdate();
	}

	int addTransferPass(TransferPass transferPass, boolean ignoreDuplicate)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement((ignoreDuplicate
				? "INSERT IGNORE"
				: "INSERT")
				+ " INTO TransferPass (TransferPassNo, BatchNo, Date, HallOfficerID, Code, RoyaltyRate, CessRate, PremiumRate, LicenseID, DestinationAddress, "
				+ "DestinationStateID, DriverName, DriverICNo, DriverAddress, VehicleNo, GrossVehicleWeight, TaggingID, LogSizeID, RoyaltyAmount, CessAmount, "
				+ "PremiumAmount, RecorderID, RecordTime, JournalID, Remarks, Status, TransferPassID) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + "?, ?, ?, ?, ?, ?, ?)"
				+ (ignoreDuplicate ? ""
						: " ON DUPLICATE KEY UPDATE BatchNo = ?, Date = ?, HallOfficerID = ?, Code = ?, RoyaltyRate = ?, CessRate = ?, PremiumRate = ?, LicenseID = ?, "
								+ "DestinationAddress = ?, DestinationStateID = ?, DriverName = ?, DriverICNo = ?, DriverAddress = ?, VehicleNo = ?, GrossVehicleWeight = ?, TaggingID = ?, LogSizeID = ?, RoyaltyAmount = ?, "
								+ "CessAmount = ?, PremiumAmount = ?, Remarks = ?, JournalID = IF(Status = 'A', ?, JournalID), Status = IF(Status = 'A', ?, Status)"));

		if (!ignoreDuplicate)
		{
			ps.setString(28, transferPass.getBatchNo());
			ps.setDate(29, toSQLDate(transferPass.getDate()));
			ps.setLong(30, transferPass.getHallOfficerID());
			ps.setInt(31, transferPass.getCode());
			ps.setInt(32, transferPass.getRoyaltyRate());
			ps.setInt(33, transferPass.getCessRate());
			ps.setInt(34, transferPass.getPremiumRate());
			ps.setLong(35, transferPass.getLicenseID());
			ps.setString(36, transferPass.getDestinationAddress());
			ps.setInt(37, transferPass.getDestinationStateID());
			ps.setString(38, transferPass.getDriverName());
			ps.setString(39, transferPass.getDriverICNo());
			ps.setString(40, transferPass.getDriverAddress());
			ps.setString(41, transferPass.getVehicleNo());
			ps.setBigDecimal(42, transferPass.getGrossVehicleWeight());
			nullable(ps, 43, transferPass.getTaggingID());
			nullable(ps, 44, transferPass.getLogSizeID());
			ps.setBigDecimal(45, transferPass.getRoyaltyAmount());
			ps.setBigDecimal(46, transferPass.getCessAmount());
			ps.setBigDecimal(47, transferPass.getPremiumAmount());
			ps.setString(48, transferPass.getRemarks());
			nullable(ps, 49, transferPass.getJournalID());
			ps.setString(50, transferPass.getStatus());
		}

		int status = write(transferPass, ps);

		if (status == 0 && !ignoreDuplicate)
			status = 1;

		return status;
	}
	
	boolean selectTransferPassNo(TransferPass transferPass) throws SQLException
	{
		boolean exist = false;
		
		PreparedStatement ps = facade.prepareStatement("SELECT TransferPassNo FROM TransferPass WHERE TRansferPassNo=?");
		
		ps.setString(1, transferPass.getTransferPassNo());
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next())
		{
			exist = true;
		}
		return exist;
	}

	int updateTransferPass(TransferPass transferPass) throws SQLException
	{
		PreparedStatement ps = facade
				.prepareStatement("UPDATE TransferPass SET "
						+ "TransferPassNo = ?, BatchNo = ?, Date = ?, HallOfficerID = ?, Code = ?, RoyaltyRate = ?, CessRate = ?, PremiumRate = ?, LicenseID = ?, "
						+ "DestinationAddress = ?, DestinationStateID = ?, DriverName = ?, DriverICNo = ?, DriverAddress = ?, VehicleNo = ?, GrossVehicleWeight = ?, TaggingID = ?, LogSizeID = ?, RoyaltyAmount = ?, "
						+ "CessAmount = ?, PremiumAmount = ?, RecorderID = ?, RecordTime = ?, JournalID = ?, Remarks = ?, Status = ? "
						+ "WHERE TransferPassID = ?");

		return write(transferPass, ps);
	}

	int updateRoyaltyAndCess(TransferPass transferPass) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE TransferPass SET RoyaltyAmount = ?, CessAmount = ? WHERE TransferPassID = ?");

		ps.setBigDecimal(1, transferPass.getRoyaltyAmount());
		ps.setBigDecimal(2, transferPass.getCessAmount());
		ps.setLong(3, transferPass.getTransferPassID());

		return ps.executeUpdate();
	}

	int updateTransferPassStatus(TransferPass transferPass) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE TransferPass SET Status = ? WHERE TransferPassID = ?");
		ps.setString(1, transferPass.getStatus());
		ps.setLong(2, transferPass.getTransferPassID());

		return ps.executeUpdate();
	}

	int updateTransferPassJournal(TransferPass transferPass) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE TransferPass SET JournalID = ?, Status = ? WHERE TransferPassID = ?");
		ps.setLong(1, transferPass.getJournalID());
		ps.setString(2, transferPass.getStatus());
		ps.setLong(3, transferPass.getTransferPassID());

		return ps.executeUpdate();
	}

	int deleteTransferPass(TransferPass transferPass) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE TransferPass SET Status = 'I' WHERE TransferPassID = ?");
		ps.setLong(1, transferPass.getTransferPassID());

		return ps.executeUpdate();
	}

	private TransferPass read(ResultSet rs) throws SQLException
	{
		TransferPass transferPass = new TransferPass();

		transferPass.setTransferPassID(rs.getLong("TransferPassID"));
		transferPass.setTransferPassNo(rs.getString("TransferPassNo"));
		transferPass.setBatchNo(rs.getString("BatchNo"));
		transferPass.setDate(rs.getDate("Date"));
		transferPass.setHallOfficerID(rs.getLong("HallOfficerID"));
		transferPass
				.setHallOfficerHammerNo(rs.getString("HallOfficerHammerNo"));
		transferPass.setHallOfficerName(rs.getString("HallOfficerName"));
		transferPass.setHallName(rs.getString("HallName"));
		transferPass.setCode(rs.getInt("Code"));
		transferPass.setRoyaltyRate(rs.getInt("RoyaltyRate"));
		transferPass.setCessRate(rs.getInt("CessRate"));
		transferPass.setPremiumRate(rs.getInt("PremiumRate"));
		transferPass.setLicenseID(rs.getLong("LicenseID"));
		transferPass.setLicenseNo(rs.getString("LicenseNo"));
		transferPass.setCompanyName(rs.getString("CompanyName"));
		transferPass.setLicenseeNo(rs.getString("LicenseeNo"));
		transferPass.setAddress(rs.getString("Address"));
		transferPass.setTelNo(rs.getString("TelNo"));
		transferPass.setDestinationAddress(rs.getString("DestinationAddress"));
		transferPass.setDestinationStateID(rs.getInt("DestinationStateID"));
		transferPass
				.setDestinationStateName(rs.getString("DestinationStateName"));
		transferPass.setDriverName(rs.getString("DriverName"));
		transferPass.setDriverICNo(rs.getString("DriverICNo"));
		transferPass.setDriverAddress(rs.getString("DriverAddress"));
		transferPass.setVehicleNo(rs.getString("VehicleNo"));
		transferPass
				.setGrossVehicleWeight(rs.getBigDecimal("GrossVehicleWeight"));
		transferPass.setTaggingID(rs.getLong("TaggingID"));
		transferPass.setLogSizeID(rs.getInt("LogSizeID"));
		transferPass.setRoyaltyAmount(rs.getBigDecimal("RoyaltyAmount"));
		transferPass.setCessAmount(rs.getBigDecimal("CessAmount"));
		transferPass.setRecorderID(rs.getString("RecorderID"));
		transferPass.setRecorderName(rs.getString("RecorderName"));
		transferPass.setRecordTime(rs.getTimestamp("RecordTime"));
		transferPass.setJournalID(rs.getLong("JournalID"));
		transferPass.setRemarks(rs.getString("Remarks"));
		transferPass.setStatus(rs.getString("Status"));
		transferPass.setLogMinBigSize(rs.getBigDecimal("LogMinBigSize"));
		transferPass.setLogMinSmallSize(rs.getBigDecimal("LogMinSmallSize"));
		if (transferPass.getCode() == 0)
		{
			transferPass.setMainRevenueTransferPassRecords(
					facade.getMainRevenueTransferPassRecords(transferPass));
		}
		else
		{
			if (transferPass.getCode() == 1)
			{
				transferPass.setSmallProductTransferPassRecords(facade
						.getSmallProductTransferPassRecords(transferPass));
			}
			else
			{
				if (transferPass.getCode() == 2)
				{
					transferPass.setSpecialTransferPassRecords(
							facade.getSpecialTransferPassRecords(transferPass));
				}
			}
		}

		return transferPass;
	}

	private ArrayList<TransferPass> getTransferPasses(ResultSet rs)
			throws SQLException
	{
		ArrayList<TransferPass> transferPasses = new ArrayList<>();

		while (rs.next())
		{
			TransferPass transferPass = read(rs);
			transferPasses.add(transferPass);
		}
		return transferPasses;
	}

	TransferPass readLogSize(TransferPass transferPass) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT ls.MinBigSize AS LogMinBigSize, ls.MinSmallSize AS LogMinSmallSize "
						+ "	FROM TransferPass tp, LogSize ls "
						+ "	WHERE tp.LogSizeID = ls.LogSizeID AND tp.TransferPassID = ? ");

		ps.setLong(1, transferPass.getTransferPassID());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			transferPass.setLogMinBigSize(rs.getBigDecimal("LogMinBigSize"));
			transferPass
					.setLogMinSmallSize(rs.getBigDecimal("LogMinSmallSize"));
		}

		return transferPass;
	}

	TransferPass getTransferPass(long transferPassID) throws SQLException
	{
		TransferPass transferPass = null;
		PreparedStatement ps = facade.prepareStatement(
				"SELECT tp.*, ho.HallOfficerName, ho.HallOfficerHammerNo, h.HallID, h.Name AS HallName, l.LicenseNo, lc.CompanyName, lc.RegistrationSerialNo AS LicenseeNo, lc.Address, lc.TelNo, ls.MinBigSize AS LogMinBigSize, ls.MinSmallSize AS LogMinSmallSize, sttd.Name AS DestinationStateName, s.Name AS RecorderName " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN (SELECT ho.HallOfficerID, s.Name AS HallOfficerName, ho.HammerNo AS HallOfficerHammerNo FROM HallOfficer ho LEFT JOIN Staff s ON ho.StaffID = s.StaffID WHERE ho.Status = 'A') AS ho ON tp.HallOfficerID = ho.HallOfficerID " + 
				"LEFT JOIN License l ON tp.LicenseID = l.LicenseID " + 
				"LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID " + 
				"LEFT JOIN Hall h ON l.HallID = h.HallID " + 
				"LEFT JOIN Staff s ON tp.RecorderID = s.StaffID " + 
				"LEFT JOIN State sttd ON tp.DestinationStateID = sttd.StateID " + 
				"LEFT JOIN LogSize ls ON ls.LogSizeID = tp.LogSizeID " + 
				"LEFT JOIN Tagging t ON t.TaggingID = tp.TaggingID " + 
				"WHERE tp.TransferPassID = ?");

		ps.setLong(1, transferPassID);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			transferPass = read(rs);
		}

		return transferPass;
	}

	ArrayList<TransferPass> getTransferPasses(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT tp.*, ho.HallOfficerName, ho.HallOfficerHammerNo, h.HallID, h.Name AS HallName, l.LicenseNo, lc.CompanyName, lc.RegistrationSerialNo AS LicenseeNo, lc.Address, lc.TelNo, ls.MinBigSize AS LogMinBigSize, ls.MinSmallSize AS LogMinSmallSize, st.Name AS DestinationStateName, s.Name AS RecorderName " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN (SELECT ho.HallOfficerID, s.Name AS HallOfficerName, ho.HammerNo AS HallOfficerHammerNo FROM HallOfficer ho LEFT JOIN Staff s ON ho.StaffID = s.StaffID WHERE ho.Status = 'A') AS ho ON tp.HallOfficerID = ho.HallOfficerID " + 
				"LEFT JOIN License l ON tp.LicenseID = l.LicenseID " + 
				"LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID " + 
				"LEFT JOIN Hall h ON l.HallID = h.HallID " + 
				"LEFT JOIN State st ON tp.DestinationStateID = st.StateID " + 
				"LEFT JOIN Staff s ON tp.RecorderID = s.StaffID  " + 
				"LEFT JOIN LogSize ls ON ls.LogSizeID = tp.LogSizeID " + 
				"LEFT JOIN Tagging t ON t.TaggingID = tp.TaggingID " + 
				"WHERE tp.STATUS = 'A'");

		ps.setString(1, status);

		return getTransferPasses(ps.executeQuery());
	}

	ArrayList<TransferPass> getTransferPasses(Tagging tagging)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT tp.*, ho.HallOfficerName, ho.HallOfficerHammerNo, h.HallID, h.Name AS HallName, l.LicenseNo, lc.CompanyName, lc.RegistrationSerialNo AS LicenseeNo, lc.Address, lc.TelNo, ls.MinBigSize AS LogMinBigSize, ls.MinSmallSize AS LogMinSmallSize, st.Name AS DestinationStateName, s.Name AS RecorderName " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN (SELECT ho.HallOfficerID, s.Name AS HallOfficerName, ho.HammerNo AS HallOfficerHammerNo FROM HallOfficer ho LEFT JOIN Staff s ON ho.StaffID = s.StaffID WHERE ho.Status = 'A') AS ho ON tp.HallOfficerID = ho.HallOfficerID " + 
				"LEFT JOIN License l ON tp.LicenseID = l.LicenseID " + 
				"LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID " + 
				"LEFT JOIN Hall h ON l.HallID = h.HallID " + 
				"LEFT JOIN State st ON tp.DestinationStateID = st.StateID " + 
				"LEFT JOIN Staff s ON tp.RecorderID = s.StaffID " + 
				"LEFT JOIN LogSize ls ON ls.LogSizeID = tp.LogSizeID " + 
				"LEFT JOIN Tagging t ON t.TaggingID = tp.TaggingID " + 
				"WHERE tp.TaggingID = ?");

		ps.setLong(1, tagging.getTaggingID());

		return getTransferPasses(ps.executeQuery());
	}

	ArrayList<TransferPass> getTransferPasses(Journal journal)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT tp.*, ho.HallOfficerName, ho.HallOfficerHammerNo, h.HallID, h.Name AS HallName, l.LicenseNo, lc.CompanyName, lc.RegistrationSerialNo AS LicenseeNo, lc.Address, lc.TelNo, ls.MinBigSize AS LogMinBigSize, ls.MinSmallSize AS LogMinSmallSize, st.Name AS DestinationStateName, s.Name AS RecorderName " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN (SELECT ho.HallOfficerID, s.Name AS HallOfficerName, ho.HammerNo AS HallOfficerHammerNo FROM HallOfficer ho LEFT JOIN Staff s ON ho.StaffID = s.StaffID WHERE ho.Status = 'A') AS ho ON tp.HallOfficerID = ho.HallOfficerID " + 
				"LEFT JOIN License l ON tp.LicenseID = l.LicenseID " + 
				"LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID " + 
				"LEFT JOIN Hall h ON l.HallID = h.HallID " + 
				"LEFT JOIN State st ON tp.DestinationStateID = st.StateID " + 
				"LEFT JOIN Staff s ON tp.RecorderID = s.StaffID " + 
				"LEFT JOIN LogSize ls ON ls.LogSizeID = tp.LogSizeID " + 
				"LEFT JOIN Tagging t ON t.TaggingID = tp.TaggingID " + 
				"WHERE tp.JournalID = ?");

		ps.setLong(1, journal.getJournalID());

		return getTransferPasses(ps.executeQuery());
	}

	ArrayList<TransferPass> getTransferPasses(License license, String status)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT tp.*, ho.HallOfficerName, ho.HallOfficerHammerNo, h.HallID, h.Name AS HallName, l.LicenseNo, lc.CompanyName, lc.RegistrationSerialNo AS LicenseeNo, lc.Address, lc.TelNo, ls.MinBigSize AS LogMinBigSize, ls.MinSmallSize AS LogMinSmallSize, st.Name AS DestinationStateName, s.Name AS RecorderName " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN (SELECT ho.HallOfficerID, s.Name AS HallOfficerName, ho.HammerNo AS HallOfficerHammerNo FROM HallOfficer ho LEFT JOIN Staff s ON ho.StaffID = s.StaffID WHERE ho.Status = 'A') AS ho ON tp.HallOfficerID = ho.HallOfficerID " + 
				"LEFT JOIN License l ON tp.LicenseID = l.LicenseID " + 
				"LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID " + 
				"LEFT JOIN Hall h ON l.HallID = h.HallID " + 
				"LEFT JOIN State st ON tp.DestinationStateID = st.StateID " + 
				"LEFT JOIN Staff s ON tp.RecorderID = s.StaffID " + 
				"LEFT JOIN LogSize ls ON ls.LogSizeID = tp.LogSizeID " + 
				"LEFT JOIN Tagging t ON t.TaggingID = tp.TaggingID " + 
				"WHERE tp.LicenseID = ? AND tp.Status = ?");

		ps.setLong(1, license.getLicenseID());
		ps.setString(2, status);

		return getTransferPasses(ps.executeQuery());
	}

	ArrayList<TransferPass> getTransferPasses(License license)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT tp.*, ho.HallOfficerName, ho.HallOfficerHammerNo, h.HallID, h.Name AS HallName, l.LicenseNo, st.Name AS DestinationStateName, s.Name AS RecorderName, ls.MinBigSize AS LogMinBigSize, ls.MinSmallSize AS LogMinSmallSize, lc.CompanyName, lc.RegistrationSerialNo AS LicenseeNo, lc.Address, lc.TelNo " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN (SELECT ho.HallOfficerID, s.Name AS HallOfficerName, ho.HammerNo AS HallOfficerHammerNo FROM HallOfficer ho LEFT JOIN Staff s ON ho.StaffID = s.StaffID WHERE ho.Status = 'A') AS ho ON tp.HallOfficerID = ho.HallOfficerID " + 
				"LEFT JOIN License l ON tp.LicenseID = l.LicenseID " + 
				"LEFT JOIN Hall h ON l.HallID = h.HallID " + 
				"LEFT JOIN State st ON tp.DestinationStateID = st.StateID " + 
				"LEFT JOIN Staff s ON tp.RecorderID = s.StaffID " + 
				"LEFT JOIN LogSize ls ON ls.LogSizeID = tp.LogSizeID " + 
				"LEFT JOIN Tagging t ON t.TaggingID = tp.TaggingID " + 
				"LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID  " + 
				"WHERE tp.LicenseID = ?");

		ps.setLong(1, license.getLicenseID());

		return getTransferPasses(ps.executeQuery());
	}

	ArrayList<TransferPass> getTransferPasses(State state, String status)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT tp.*, ho.HallOfficerName, ho.HallOfficerHammerNo, h.HallID, h.Name AS HallName, l.LicenseNo, st.Name AS DestinationStateName, s.Name AS RecorderName, ls.MinBigSize AS LogMinBigSize, ls.MinSmallSize AS LogMinSmallSize, lc.CompanyName, lc.RegistrationSerialNo AS LicenseeNo, lc.Address, lc.TelNo " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN (SELECT ho.HallOfficerID, s.Name AS HallOfficerName, ho.HammerNo AS HallOfficerHammerNo FROM HallOfficer ho LEFT JOIN Staff s ON ho.StaffID = s.StaffID WHERE ho.Status = 'A') AS ho ON tp.HallOfficerID = ho.HallOfficerID " + 
				"LEFT JOIN License l ON tp.LicenseID = l.LicenseID " + 
				"LEFT JOIN Hall h ON l.HallID = h.HallID " + 
				"LEFT JOIN State st ON tp.DestinationStateID = st.StateID " + 
				"LEFT JOIN Staff s ON tp.RecorderID = s.StaffID " + 
				"LEFT JOIN LogSize ls ON ls.LogSizeID = tp.LogSizeID " + 
				"LEFT JOIN Tagging t ON t.TaggingID = tp.TaggingID " + 
				"LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID " + 
				"WHERE s.StateID = ? AND tp.STATUS = ?");

		ps.setInt(1, state.getStateID());
		ps.setString(2, status);

		return getTransferPasses(ps.executeQuery());
	}

	String[] getTransferPassString(long transferPassID) throws SQLException
	{
		TransferPass transferPass = getTransferPass(transferPassID);
		String[] transferPassString = new String[14];

		transferPassString[0] = transferPass.getTransferPassNo();
		transferPassString[1] = transferPass.getDriverName();
		transferPassString[2] = transferPass.getDriverICNo();
		transferPassString[3] = transferPass.getDriverAddress();
		transferPassString[4] = transferPass.getHallName();
		transferPassString[5] = transferPass.getLicenseNo();
		transferPassString[6] = transferPass.getCompanyName();
		transferPassString[7] = transferPass.getVehicleNo();
		transferPassString[8] = transferPass.getDestinationAddress();
		transferPassString[9] = transferPass.getGrossVehicleWeight() + " KG";
		transferPassString[10] = timeFormat
				.format(transferPass.getRecordTime());
		transferPassString[11] = dateFormat.format(transferPass.getDate());
		transferPassString[12] = transferPass.getHallOfficerHammerNo();
		transferPassString[13] = transferPass.getHallOfficerName();

		return transferPassString;
	}

	ArrayList<String[]> getTransferPassRecordString(TransferPass transferPass)
			throws SQLException
	{
		ArrayList<String[]> transferPassRecordStrings = new ArrayList<String[]>();
		if (transferPass.getCode() == 0)
		{
			ArrayList<MainRevenueTransferPassRecord> mainRevenueTransferPassRecords = facade
					.getMainRevenueTransferPassRecords(transferPass);
			for (MainRevenueTransferPassRecord mainRevenueTransferPassRecord : mainRevenueTransferPassRecords)
			{
				String[] transferPassRecordString = new String[12];
				transferPassRecordString[0] = mainRevenueTransferPassRecord
						.getLogSerialNo() + " - "
						+ mainRevenueTransferPassRecord.getSpeciesSymbol();
				BigDecimal length = mainRevenueTransferPassRecord.getLength()
						.setScale(2);
				transferPassRecordString[1] = length.toString();
				BigDecimal diameter = mainRevenueTransferPassRecord
						.getDiameter().setScale(2, BigDecimal.ROUND_HALF_UP);
				transferPassRecordString[2] = diameter.toString();
				BigDecimal grossVolume = ((new BigDecimal(Math.PI))
						.multiply(((diameter.divide(new BigDecimal(2)))
								.divide(new BigDecimal(100))).pow(2)))
										.multiply(length)
										.setScale(2, BigDecimal.ROUND_HALF_UP);
				transferPassRecordString[3] = grossVolume.toString();
				BigDecimal holeDiameter = mainRevenueTransferPassRecord
						.getHoleDiameter()
						.setScale(2, BigDecimal.ROUND_HALF_UP);
				transferPassRecordString[4] = holeDiameter.toString();
				BigDecimal holeVolume = ((new BigDecimal(Math.PI))
						.multiply(((holeDiameter.divide(new BigDecimal(2)))
								.divide(new BigDecimal(100))).pow(2)))
										.multiply(length)
										.setScale(2, BigDecimal.ROUND_HALF_UP);
				transferPassRecordString[5] = holeVolume.toString();
				BigDecimal netVolume = grossVolume.subtract(holeVolume)
						.setScale(2, BigDecimal.ROUND_HALF_UP);
				transferPassRecordString[6] = netVolume.toString();
				BigDecimal royaltyRate = new BigDecimal("0");
				if (diameter.compareTo(new BigDecimal("45")) != -1)
				{
					royaltyRate = mainRevenueTransferPassRecord
							.getBigSizeRoyaltyRate()
							.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				else
				{
					royaltyRate = mainRevenueTransferPassRecord
							.getSmallSizeRoyaltyRate()
							.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				transferPassRecordString[7] = royaltyRate.toString();
				transferPassRecordString[8] = mainRevenueTransferPassRecord
						.getRoyalty().setScale(2, BigDecimal.ROUND_HALF_UP)
						.toString();
				transferPassRecordString[9] = mainRevenueTransferPassRecord
						.getCess().setScale(2, BigDecimal.ROUND_HALF_UP)
						.toString();
				transferPassRecordString[10] = mainRevenueTransferPassRecord
						.getCessRate().setScale(2, BigDecimal.ROUND_HALF_UP)
						.toString();
				transferPassRecordString[11] = mainRevenueTransferPassRecord
						.getLogSerialNo();
				transferPassRecordStrings.add(transferPassRecordString);
			}

		}
		else
		{
			if (transferPass.getCode() == 1)
			{
				ArrayList<SmallProductTransferPassRecord> smallProductTransferPassRecords = facade
						.getSmallProductTransferPassRecords(transferPass);
				for (SmallProductTransferPassRecord smallProductTransferPassRecord : smallProductTransferPassRecords)
				{
					String[] transferPassRecordString = new String[10];
					transferPassRecordString[0] = smallProductTransferPassRecord
							.getSmallProductSymbol();
					BigDecimal quantity = smallProductTransferPassRecord
							.getQuantity();
					transferPassRecordString[1] = quantity.toString();
					transferPassRecordString[2] = smallProductTransferPassRecord
							.getUnitName();
					transferPassRecordString[3] = "";
					transferPassRecordString[4] = "";
					transferPassRecordString[5] = "";
					transferPassRecordString[6] = "";
					BigDecimal royaltyRate = smallProductTransferPassRecord
							.getRoyaltyRate()
							.setScale(2, BigDecimal.ROUND_HALF_UP);
					transferPassRecordString[7] = royaltyRate
							.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
					transferPassRecordString[8] = smallProductTransferPassRecord
							.getRoyalty().setScale(2, BigDecimal.ROUND_HALF_UP)
							.toString();
					transferPassRecordString[9] = smallProductTransferPassRecord
							.getCess().setScale(2, BigDecimal.ROUND_HALF_UP)
							.toString();
					transferPassRecordStrings.add(transferPassRecordString);
				}
			}
			else
			{
				if (transferPass.getCode() == 2)
				{
					ArrayList<SpecialTransferPassRecord> specialTransferPassRecords = facade
							.getSpecialTransferPassRecords(transferPass);
					for (SpecialTransferPassRecord specialTransferPassRecord : specialTransferPassRecords)
					{
						String[] transferPassRecordString = new String[11];
						transferPassRecordString[0] = specialTransferPassRecord
								.getSpeciesSymbol();
						BigDecimal length = specialTransferPassRecord
								.getLength().setScale(2);
						transferPassRecordString[1] = length.toString();
						BigDecimal diameter = specialTransferPassRecord
								.getDiameter().setScale(2);
						transferPassRecordString[2] = diameter.toString();
						BigDecimal grossVolume = ((new BigDecimal(Math.PI))
								.multiply(((diameter.divide(new BigDecimal(2)))
										.divide(new BigDecimal(100))).pow(2)))
												.multiply(length);
						transferPassRecordString[3] = grossVolume.toString();
						BigDecimal holeDiameter = new BigDecimal("0");
						transferPassRecordString[4] = holeDiameter.toString();
						BigDecimal holeVolume = ((new BigDecimal(Math.PI))
								.multiply(((holeDiameter
										.divide(new BigDecimal(2)))
												.divide(new BigDecimal(100)))
														.pow(2))).multiply(
																length);
						transferPassRecordString[5] = holeVolume.toString();
						BigDecimal netVolume = grossVolume.subtract(holeVolume);
						transferPassRecordString[6] = netVolume.toString();
						BigDecimal royaltyRate = new BigDecimal("0");
						if (diameter.compareTo(new BigDecimal("45")) != -1)
						{
							royaltyRate = specialTransferPassRecord
									.getBigSizeRoyaltyRate().setScale(2);
						}
						else
						{
							royaltyRate = specialTransferPassRecord
									.getSmallSizeRoyaltyRate().setScale(2);
						}
						transferPassRecordString[7] = royaltyRate.toString();
						transferPassRecordString[8] = specialTransferPassRecord
								.getRoyalty().toString();
						transferPassRecordString[9] = specialTransferPassRecord
								.getCess().toString();
						transferPassRecordString[10] = specialTransferPassRecord
								.getCessRate().toString();
						transferPassRecordStrings.add(transferPassRecordString);
					}
				}
			}
		}

		return transferPassRecordStrings;
	}

	private String[] readShuttleReport(ResultSet rs) throws SQLException
	{
		String[] shuttleReport = new String[15];

		shuttleReport[0] = rs.getString("SpeciesName");
		shuttleReport[1] = rs.getString("TimberTypeCode");
		shuttleReport[2] = rs.getString("SpeciesCode");
		BigDecimal royaltyRate = rs.getBigDecimal("RoyaltyRate").setScale(2,
				BigDecimal.ROUND_HALF_UP);
		shuttleReport[3] = royaltyRate.toString();
		BigDecimal hutanSimpanVolume = rs.getBigDecimal("HutanSimpanVolume")
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		shuttleReport[4] = hutanSimpanVolume.toString();
		BigDecimal hutanSimpanRoyalty = rs.getBigDecimal("HutanSimpanRoyalty")
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		shuttleReport[5] = hutanSimpanRoyalty.toString();
		BigDecimal hutanNegeriVolume = rs.getBigDecimal("HutanNegeriVolume")
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		shuttleReport[6] = hutanNegeriVolume.toString();
		BigDecimal hutanNegeriRoyalty = rs.getBigDecimal("HutanNegeriRoyalty")
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		shuttleReport[7] = hutanNegeriRoyalty.toString();
		BigDecimal volumeHSHN = hutanSimpanVolume.add(hutanNegeriVolume)
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		shuttleReport[8] = volumeHSHN.toString();
		BigDecimal royaltyHSHN = hutanSimpanRoyalty.add(hutanNegeriRoyalty)
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		shuttleReport[9] = royaltyHSHN.toString();
		shuttleReport[10] = royaltyRate.toString();
		BigDecimal tanahMilikVolume = rs.getBigDecimal("TanahMilikVolume")
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		shuttleReport[11] = tanahMilikVolume.toString();
		BigDecimal tanahMilikRoyalty = rs.getBigDecimal("TanahMilikRoyalty")
				.setScale(2, BigDecimal.ROUND_HALF_UP);
		shuttleReport[12] = tanahMilikRoyalty.toString();
		BigDecimal volumeAll = volumeHSHN.add(tanahMilikVolume).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		shuttleReport[13] = volumeAll.toString();
		BigDecimal royaltyAll = royaltyHSHN.add(tanahMilikRoyalty).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		shuttleReport[14] = royaltyAll.toString();

		return shuttleReport;
	}

	private ArrayList<String[]> getShuttleReports(ResultSet rs)
			throws SQLException
	{
		ArrayList<String[]> shuttleReports = new ArrayList<>();

		while (rs.next())
		{
			String[] shuttleReport = readShuttleReport(rs);
			shuttleReports.add(shuttleReport);
		}
		return shuttleReports;
	}

	ArrayList<String[]> getBigSizeLogShuttleReport(int month, int year)
			throws SQLException
	{

		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.SpeciesName, z.SpeciesCode, z.TimberTypeID, z.BigSizeRoyaltyRate AS RoyaltyRate, " + 
				"IFNULL(SUM(z.HutanSimpanVolume),0) AS HutanSimpanVolume, IFNULL(SUM(z.HutanSimpanRoyalty),0) AS HutanSimpanRoyalty, " + 
				"IFNULL(SUM(z.HutanNegeriVolume),0) AS HutanNegeriVolume, IFNULL(SUM(z.HutanNegeriRoyalty),0) AS HutanNegeriRoyalty, " + 
				"IFNULL(SUM(z.TanahMilikVolume),0) AS TanahMilikVolume, IFNULL(SUM(z.TanahMilikRoyalty),0) AS TanahMilikRoyalty, " + 
				"z.TimberTypeCode, z.ForestCategoryCode  " + 
				"FROM(" + 
				"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, IFNULL(ROUND((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 2), 0) AS HutanSimpanVolume, IFNULL(mrtpr.Royalty, 0) AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
				"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
				"LEFT JOIN MainRevenueTransferPassRecord mrtpr ON mrtpr.TransferPassID = tp.TransferPassID " + 
				"LEFT JOIN Log lg ON mrtpr.LogID = lg.LogID " + 
				"LEFT JOIN TaggingRecord tr ON lg.TaggingRecordID = tr.TaggingRecordID " + 
				"LEFT JOIN Species s ON tr.CorrectedSpeciesID = s.SpeciesID " + 
				"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
				"LEFT JOIN MainRevenueRoyaltyRate mrrr ON mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
				"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
				"WHERE fc.Code = \"HS\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
				"UNION ALL " + 
				"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate,0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty,  IFNULL(ROUND((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 2), 0) AS HutanNegeriVolume, IFNULL(mrtpr.Royalty, 0) AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode  " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
				"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
				"LEFT JOIN MainRevenueTransferPassRecord mrtpr ON mrtpr.TransferPassID = tp.TransferPassID " + 
				"LEFT JOIN Log lg ON mrtpr.LogID = lg.LogID " + 
				"LEFT JOIN TaggingRecord tr ON lg.TaggingRecordID = tr.TaggingRecordID " + 
				"LEFT JOIN Species s ON tr.CorrectedSpeciesID = s.SpeciesID " + 
				"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
				"LEFT JOIN MainRevenueRoyaltyRate mrrr ON mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
				"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
				"WHERE fc.Code = \"HN\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
				"UNION ALL " + 
				"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, 0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, IFNULL(ROUND((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 2), 0) AS TanahMilikVolume, IFNULL(mrtpr.Royalty, 0) AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode  " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
				"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
				"LEFT JOIN MainRevenueTransferPassRecord mrtpr ON mrtpr.TransferPassID = tp.TransferPassID " + 
				"LEFT JOIN Log lg ON mrtpr.LogID = lg.LogID " + 
				"LEFT JOIN TaggingRecord tr ON lg.TaggingRecordID = tr.TaggingRecordID " + 
				"LEFT JOIN Species s ON tr.CorrectedSpeciesID = s.SpeciesID " + 
				"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
				"LEFT JOIN MainRevenueRoyaltyRate mrrr ON mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
				"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
				"WHERE fc.Code = \"TM\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
				"UNION ALL " + 
				"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, IFNULL(ROUND((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 2), 0) AS HutanSimpanVolume, IFNULL(stpr.Royalty, 0) AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
				"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
				"LEFT JOIN SpecialTransferPassRecord stpr ON stpr.TransferPassID = tp.TransferPassID " + 
				"LEFT JOIN Species s ON stpr.SpeciesID = s.SpeciesID " + 
				"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
				"LEFT JOIN MainRevenueRoyaltyRate mrrr ON stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
				"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
				"WHERE fc.Code = \"HS\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
				"UNION ALL " + 
				"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate,0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, IFNULL(ROUND((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 2), 0) AS HutanNegeriVolume, IFNULL(stpr.Royalty, 0) AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
				"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
				"LEFT JOIN SpecialTransferPassRecord stpr ON stpr.TransferPassID = tp.TransferPassID " + 
				"LEFT JOIN Species s ON stpr.SpeciesID = s.SpeciesID " + 
				"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
				"LEFT JOIN MainRevenueRoyaltyRate mrrr ON stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
				"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
				"WHERE fc.Code = \"HN\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
				"UNION ALL " + 
				"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, 0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, IFNULL(ROUND((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 2), 0) AS TanahMilikVolume, IFNULL(stpr.Royalty, 0) AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode " + 
				"FROM TransferPass tp " + 
				"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
				"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
				"LEFT JOIN SpecialTransferPassRecord stpr ON stpr.TransferPassID = tp.TransferPassID " + 
				"LEFT JOIN Species s ON stpr.SpeciesID = s.SpeciesID " + 
				"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
				"LEFT JOIN MainRevenueRoyaltyRate mrrr ON stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
				"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
				"WHERE fc.Code = \"TM\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
				") AS z " + 
				"GROUP BY z.SpeciesCode " + 
				"ORDER BY z.TimberTypeID, z.SpeciesName");
		/*
		 * "SELECT z.SpeciesName, z.SpeciesCode, z.TimberTypeID, z.BigSizeRoyaltyRate AS RoyaltyRate, IFNULL(SUM(z.HutanSimpanVolume),0) AS HutanSimpanVolume, IFNULL(SUM(z.HutanSimpanRoyalty),0) AS HutanSimpanRoyalty, "
		 * +
		 * "IFNULL(SUM(z.HutanNegeriVolume),0) AS HutanNegeriVolume, IFNULL(SUM(z.HutanNegeriRoyalty),0) AS HutanNegeriRoyalty, "
		 * +
		 * "IFNULL(SUM(z.TanahMilikVolume),0) AS TanahMilikVolume, IFNULL(SUM(z.TanahMilikRoyalty),0) AS TanahMilikRoyalty, z.TimberTypeCode, z.ForestCategoryCode  "
		 * + "FROM(" +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, IFNULL((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 0) AS HutanSimpanVolume, IFNULL(mrtpr.Royalty, 0) AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"HS\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + "UNION ALL " +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate,0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty,  IFNULL((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 0) AS HutanNegeriVolume, IFNULL(mrtpr.Royalty, 0) AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode  "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"HN\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + "UNION ALL " +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, 0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, IFNULL((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 0) AS TanahMilikVolume, IFNULL(mrtpr.Royalty, 0) AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode  "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"TM\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + "UNION ALL " +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, IFNULL((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 0) AS HutanSimpanVolume, IFNULL(stpr.Royalty, 0) AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, SpecialTransferPassRecord stpr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"HS\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + "UNION ALL " +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate,0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, IFNULL((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 0) AS HutanNegeriVolume, IFNULL(stpr.Royalty, 0) AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, SpecialTransferPassRecord stpr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"HN\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + "UNION ALL " +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, 0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, IFNULL((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 0) AS TanahMilikVolume, IFNULL(stpr.Royalty, 0) AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, SpecialTransferPassRecord stpr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"TM\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter >= ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + ") AS Z " +
		 * "GROUP BY z.SpeciesName, z.SpeciesCode, z.TimberTypeID, z.BigSizeRoyaltyRate, z.TimberTypeCode, z.ForestCategoryCode "
		 * + "ORDER BY z.TimberTypeID");
		 */

		for (int i = 0; i < 6; i++)
		{
			ps.setInt(i * 2 + 1, month);
			ps.setInt(i * 2 + 2, year);
		}

		return getShuttleReports(ps.executeQuery());
	}

	ArrayList<String[]> getSmallSizeLogShuttleReport(int month, int year)
			throws SQLException
	{

		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.SpeciesName, z.SpeciesCode, z.TimberTypeID, z.BigSizeRoyaltyRate AS RoyaltyRate, " + 
						"IFNULL(SUM(z.HutanSimpanVolume),0) AS HutanSimpanVolume, IFNULL(SUM(z.HutanSimpanRoyalty),0) AS HutanSimpanRoyalty, " + 
						"IFNULL(SUM(z.HutanNegeriVolume),0) AS HutanNegeriVolume, IFNULL(SUM(z.HutanNegeriRoyalty),0) AS HutanNegeriRoyalty, " + 
						"IFNULL(SUM(z.TanahMilikVolume),0) AS TanahMilikVolume, IFNULL(SUM(z.TanahMilikRoyalty),0) AS TanahMilikRoyalty, " + 
						"z.TimberTypeCode, z.ForestCategoryCode  " + 
						"FROM(" + 
						"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, IFNULL(ROUND((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 2), 0) AS HutanSimpanVolume, IFNULL(mrtpr.Royalty, 0) AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode " + 
						"FROM TransferPass tp " + 
						"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
						"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
						"LEFT JOIN MainRevenueTransferPassRecord mrtpr ON mrtpr.TransferPassID = tp.TransferPassID " + 
						"LEFT JOIN Log lg ON mrtpr.LogID = lg.LogID " + 
						"LEFT JOIN TaggingRecord tr ON lg.TaggingRecordID = tr.TaggingRecordID " + 
						"LEFT JOIN Species s ON tr.CorrectedSpeciesID = s.SpeciesID " + 
						"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
						"LEFT JOIN MainRevenueRoyaltyRate mrrr ON mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
						"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
						"WHERE fc.Code = \"HS\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
						"UNION ALL " + 
						"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate,0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty,  IFNULL(ROUND((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 2), 0) AS HutanNegeriVolume, IFNULL(mrtpr.Royalty, 0) AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode  " + 
						"FROM TransferPass tp " + 
						"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
						"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
						"LEFT JOIN MainRevenueTransferPassRecord mrtpr ON mrtpr.TransferPassID = tp.TransferPassID " + 
						"LEFT JOIN Log lg ON mrtpr.LogID = lg.LogID " + 
						"LEFT JOIN TaggingRecord tr ON lg.TaggingRecordID = tr.TaggingRecordID " + 
						"LEFT JOIN Species s ON tr.CorrectedSpeciesID = s.SpeciesID " + 
						"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
						"LEFT JOIN MainRevenueRoyaltyRate mrrr ON mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
						"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
						"WHERE fc.Code = \"HN\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
						"UNION ALL " + 
						"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, 0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, IFNULL(ROUND((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 2), 0) AS TanahMilikVolume, IFNULL(mrtpr.Royalty, 0) AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode  " + 
						"FROM TransferPass tp " + 
						"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
						"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
						"LEFT JOIN MainRevenueTransferPassRecord mrtpr ON mrtpr.TransferPassID = tp.TransferPassID " + 
						"LEFT JOIN Log lg ON mrtpr.LogID = lg.LogID " + 
						"LEFT JOIN TaggingRecord tr ON lg.TaggingRecordID = tr.TaggingRecordID " + 
						"LEFT JOIN Species s ON tr.CorrectedSpeciesID = s.SpeciesID " + 
						"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
						"LEFT JOIN MainRevenueRoyaltyRate mrrr ON mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
						"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
						"WHERE fc.Code = \"TM\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
						"UNION ALL " + 
						"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, IFNULL(ROUND((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 2), 0) AS HutanSimpanVolume, IFNULL(stpr.Royalty, 0) AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode " + 
						"FROM TransferPass tp " + 
						"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
						"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
						"LEFT JOIN SpecialTransferPassRecord stpr ON stpr.TransferPassID = tp.TransferPassID " + 
						"LEFT JOIN Species s ON stpr.SpeciesID = s.SpeciesID " + 
						"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
						"LEFT JOIN MainRevenueRoyaltyRate mrrr ON stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
						"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
						"WHERE fc.Code = \"HS\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
						"UNION ALL " + 
						"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate,0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, IFNULL(ROUND((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 2), 0) AS HutanNegeriVolume, IFNULL(stpr.Royalty, 0) AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode " + 
						"FROM TransferPass tp " + 
						"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
						"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
						"LEFT JOIN SpecialTransferPassRecord stpr ON stpr.TransferPassID = tp.TransferPassID " + 
						"LEFT JOIN Species s ON stpr.SpeciesID = s.SpeciesID " + 
						"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
						"LEFT JOIN MainRevenueRoyaltyRate mrrr ON stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
						"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
						"WHERE fc.Code = \"HN\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
						"UNION ALL " + 
						"SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.BigSizeRoyaltyRate, 0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, IFNULL(ROUND((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 2), 0) AS TanahMilikVolume, IFNULL(stpr.Royalty, 0) AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode " + 
						"FROM TransferPass tp " + 
						"LEFT JOIN License lc ON tp.LicenseID = lc.LicenseID " + 
						"LEFT JOIN ForestCategory fc ON lc.ForestCategoryID = fc.ForestCategoryID " + 
						"LEFT JOIN SpecialTransferPassRecord stpr ON stpr.TransferPassID = tp.TransferPassID " + 
						"LEFT JOIN Species s ON stpr.SpeciesID = s.SpeciesID " + 
						"LEFT JOIN TimberType tt ON s.TimberTypeID = tt.TimberTypeID " + 
						"LEFT JOIN MainRevenueRoyaltyRate mrrr ON stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID " + 
						"LEFT JOIN LogSize ls ON tp.LogSizeID = ls.LogSizeID " + 
						"WHERE fc.Code = \"TM\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.DATE) = ? " + 
						") AS z " + 
						"GROUP BY z.SpeciesCode " + 
						"ORDER BY z.TimberTypeID, z.SpeciesName");				
		/*
		 * "SELECT z.SpeciesName, z.SpeciesCode, z.TimberTypeID, z.SmallSizeRoyaltyRate AS RoyaltyRate, IFNULL(SUM(z.HutanSimpanVolume),0) AS HutanSimpanVolume, IFNULL(SUM(z.HutanSimpanRoyalty),0) AS HutanSimpanRoyalty, "
		 * +
		 * "IFNULL(SUM(z.HutanNegeriVolume),0) AS HutanNegeriVolume, IFNULL(SUM(z.HutanNegeriRoyalty),0) AS HutanNegeriRoyalty, "
		 * +
		 * "IFNULL(SUM(z.TanahMilikVolume),0) AS TanahMilikVolume, IFNULL(SUM(z.TanahMilikRoyalty),0) AS TanahMilikRoyalty, z.TimberTypeCode, z.ForestCategoryCode  "
		 * + "FROM(" +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.SmallSizeRoyaltyRate, IFNULL((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 0) AS HutanSimpanVolume, IFNULL(mrtpr.Royalty, 0) AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"HS\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + "UNION ALL " +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.SmallSizeRoyaltyRate,0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty,  IFNULL((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 0) AS HutanNegeriVolume, IFNULL(mrtpr.Royalty, 0) AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode  "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"HN\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + "UNION ALL " +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.SmallSizeRoyaltyRate, 0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, IFNULL((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 0) AS TanahMilikVolume, IFNULL(mrtpr.Royalty, 0) AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode  "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"TM\" AND tp.Status != 'I' AND tp.Code = 0 AND lg.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + "UNION ALL " +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.SmallSizeRoyaltyRate, IFNULL((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 0) AS HutanSimpanVolume, IFNULL(stpr.Royalty, 0) AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, SpecialTransferPassRecord stpr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"HS\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + "UNION ALL " +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.SmallSizeRoyaltyRate,0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, IFNULL((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 0) AS HutanNegeriVolume, IFNULL(stpr.Royalty, 0) AS HutanNegeriRoyalty, 0 AS TanahMilikVolume, 0 AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, SpecialTransferPassRecord stpr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"HN\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + "UNION ALL " +
		 * "SELECT s.Name AS SpeciesName, s.Code AS SpeciesCode, tt.TimberTypeID, mrrr.SmallSizeRoyaltyRate, 0 AS HutanSimpanVolume, 0 AS HutanSimpanRoyalty, 0 AS HutanNegeriVolume, 0 AS HutanNegeriRoyalty, IFNULL((PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length), 0) AS TanahMilikVolume, IFNULL(stpr.Royalty, 0) AS TanahMilikRoyalty, tt.Code AS TimberTypeCode, fc.Code AS ForestCategoryCode "
		 * +
		 * "FROM TransferPass tp, License lc, ForestCategory fc, SpecialTransferPassRecord stpr, Species s, TimberType tt, MainRevenueRoyaltyRate mrrr, LogSize ls  "
		 * +
		 * "WHERE tp.LicenseID = lc.LicenseID AND lc.ForestCategoryID = fc.ForestCategoryID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND s.TimberTypeID = tt.TimberTypeID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND fc.Code = \"TM\" AND tp.Status != 'I' AND tp.Code = 2 AND stpr.Diameter < ls.MinBigSize AND MONTH(tp.Date) = ? AND YEAR(tp.Date) = ? "
		 * + ") AS Z " +
		 * "GROUP BY z.SpeciesName, z.SpeciesCode, z.TimberTypeID, z.SmallSizeRoyaltyRate, z.TimberTypeCode, z.ForestCategoryCode "
		 * + "ORDER BY z.TimberTypeID");
		 */

		for (int i = 0; i < 6; i++)
		{
			ps.setInt(i * 2 + 1, month);
			ps.setInt(i * 2 + 2, year);
		}

		return getShuttleReports(ps.executeQuery());
	}

	ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesen(
			Date startDate, Date endDate, int licenseID) throws SQLException
	{

		PreparedStatement ps1 = facade.prepareStatement(
				"SELECT s.SpeciesID, s.Name AS SpeciesName, SUM((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length)) AS Volume, mrrr.BigSizeRoyaltyRate, SUM(mrtpr.Royalty) FROM TransferPass tp, License l, LoggingContractor lc, HallOfficer ho, Hall h, LogSize ls,  MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, MainRevenueRoyaltyRate mrrr "
						+ "WHERE tp.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND tp.HallOfficerID = ho.HallOfficerID AND l.HallID = h.HallID AND tp.LogSizeID = ls.LogSizeID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND "
						+ "lg.Diameter >= ls.MinBigSize AND tp.Code = 0 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status ='P' "
						+ "GROUP BY s.SpeciesID " + "ORDER BY s.Code");

		ps1.setInt(1, licenseID);
		ps1.setDate(2, toSQLDate(startDate));
		ps1.setDate(3, toSQLDate(endDate));

		ResultSet rs1 = ps1.executeQuery();

		ArrayList<String[]> bigSizeLogs = new ArrayList<String[]>();
		while (rs1.next())
		{
			String[] bigSizeLog = new String[5];
			bigSizeLog[0] = rs1.getString("SpeciesName");
			bigSizeLog[1] = String.format("%,.2f", rs1.getBigDecimal("Volume")
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			bigSizeLog[2] = Integer.toString(rs1.getInt("NoOfTransactions"));
			bigSizeLog[3] = String.format("%,.2f", rs1.getBigDecimal("Amount")
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			bigSizeLog[4] = rs1.getString("Status");
			bigSizeLogs.add(bigSizeLog);
		}
		return bigSizeLogs;
	}

	String[] getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenHeaderInfo(
			long licenseID) throws SQLException
	{
		String[] data = new String[4];

		PreparedStatement ps = facade.prepareStatement(
				"SELECT l.LicenseNo, lc.CompanyName AS LicenseeCompanyName, h.Name AS HallName, l.CompartmentNo  "
						+ "FROM License l, LoggingContractor lc, Hall h "
						+ "WHERE l.LicenseeID = lc.LoggingContractorID AND l.HallID = h.HallID AND l.LicenseID = ?");
		ps.setLong(1, licenseID);

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			data[0] = rs.getString("LicenseNo");
			data[1] = rs.getString("LicenseeCompanyName");
			data[2] = rs.getString("HallName");
			data[3] = rs.getString("CompartmentNo");
		}
		return data;
	}

	String[][] getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenMainTable(
			Date startDate, Date endDate, long licenseID, int logSize)
			throws SQLException
	{
		PreparedStatement ps = null;

		ps = facade.prepareStatement(
				"SELECT asg.AbstractsSpeciesGroupID, asg.Name "
						+ "FROM AbstractsSpeciesGroup asg "
						+ "WHERE asg.Status = 'A'");

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> speciesGroups = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] group = new String[6];
			group[0] = rs.getString("Name").toUpperCase();
			group[1] = "0";
			group[2] = "0";
			group[3] = "0";
			group[4] = "0";
			group[5] = rs.getString("AbstractsSpeciesGroupID");
			speciesGroups.add(group);
		}

		ps = null;
		rs = null;

		if (logSize == 0)
		{
			ps = facade.prepareStatement(
					"SELECT asg.AbstractsSpeciesGroupID, asg.Name, mrrr.SmallSizeRoyaltyRate "
							+ "FROM AbstractsSpeciesGroup asg, AbstractsSpeciesGroupRecord asgr, Species s, MainRevenueRoyaltyRate mrrr "
							+ "WHERE asgr.AbstractsSpeciesGroupID = asg.AbstractsSpeciesGroupID AND asgr.SpeciesID = s.SpeciesID AND mrrr.SpeciesID = s.SpeciesID AND asg.Status = 'A' AND mrrr.Status = 'A' "
							+ "GROUP BY asg.AbstractsSpeciesGroupID, asg.Name, mrrr.SmallSizeRoyaltyRate ORDER BY asg.AbstractsSpeciesGroupID");
		}
		else
		{
			if (logSize == 1)
			{
				ps = facade.prepareStatement(
						"SELECT asg.AbstractsSpeciesGroupID, asg.Name, mrrr.BigSizeRoyaltyRate "
								+ "FROM AbstractsSpeciesGroup asg, AbstractsSpeciesGroupRecord asgr, Species s, MainRevenueRoyaltyRate mrrr "
								+ "WHERE asgr.AbstractsSpeciesGroupID = asg.AbstractsSpeciesGroupID AND asgr.SpeciesID = s.SpeciesID AND mrrr.SpeciesID = s.SpeciesID AND asg.Status = 'A' AND mrrr.Status = 'A' "
								+ "GROUP BY asg.AbstractsSpeciesGroupID, asg.Name, mrrr.BigSizeRoyaltyRate ORDER BY asg.AbstractsSpeciesGroupID");
			}
		}

		rs = ps.executeQuery();

		ArrayList<String[]> royaltyRates = new ArrayList<String[]>();
		if (logSize == 0)
		{
			while (rs.next())
			{
				String[] royaltyRate = new String[2];
				royaltyRate[0] = rs.getString("SmallSizeRoyaltyRate");
				royaltyRate[1] = rs.getString("AbstractsSpeciesGroupID");
				royaltyRates.add(royaltyRate);
			}
		}
		else
		{
			if (logSize == 1)
			{
				while (rs.next())
				{
					String[] royaltyRate = new String[2];
					royaltyRate[0] = rs.getString("BigSizeRoyaltyRate");
					royaltyRate[1] = rs.getString("AbstractsSpeciesGroupID");
					royaltyRates.add(royaltyRate);
				}
			}
		}

		for (String[] group : speciesGroups)
		{
			for (String[] royaltyRate : royaltyRates)
			{
				if (group[5].equalsIgnoreCase(royaltyRate[1]))
				{
					group[2] = royaltyRate[0];
					break;
				}
			}
		}

		ps = null;
		rs = null;

		if (logSize == 0)
		{
			ps = facade.prepareStatement(
					"SELECT z.AbstractsSpeciesGroupID, z.AbstractsSpeciesGroupName, IFNULL(SUM(z.Volume), 0) AS Volume, IFNULL(z.RoyaltyRate, 0) AS RoyaltyRate, IFNULL(SUM(z.RoyaltyAmount), 0) AS RoyaltyAmount, IFNULL(SUM(z.CessAmount), 0) AS CessAmount "
							+ "FROM ("
							+ "SELECT asg.AbstractsSpeciesGroupID, asg.Name AS AbstractsSpeciesGroupName, ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2) AS Volume, mrrr.BigSizeRoyaltyRate AS RoyaltyRate, mrtpr.Royalty AS RoyaltyAmount, mrtpr.Cess AS CessAmount "
							+ "FROM TransferPass tp, License l, LogSize ls,  MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, MainRevenueRoyaltyRate mrrr, AbstractsSpeciesGroup asg, AbstractsSpeciesGroupRecord asgr "
							+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND asgr.AbstractsSpeciesGroupID = asg.AbstractsSpeciesGroupID AND asgr.SpeciesID = s.SpeciesID AND lg.Diameter >= ls.MinSmallSize AND lg.Diameter < ls.MinBigSize AND tp.Code = 0 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' "
							+ "UNION ALL "
							+ "SELECT asg.AbstractsSpeciesGroupID, asg.Name AS AbstractsSpeciesGroupName, ROUND((PI()*(stpr.Diameter/2/100)*(stpr.Diameter/2/100)*stpr.Length), 2) AS Volume, mrrr.BigSizeRoyaltyRate AS RoyaltyRate, stpr.Royalty AS RoyaltyAmount, stpr.Cess AS CessAmount "
							+ "FROM TransferPass tp, License l, LogSize ls,  SpecialTransferPassRecord stpr, Species s, MainRevenueRoyaltyRate mrrr, AbstractsSpeciesGroup asg, AbstractsSpeciesGroupRecord asgr  "
							+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND asgr.AbstractsSpeciesGroupID = asg.AbstractsSpeciesGroupID AND asgr.SpeciesID = s.SpeciesID AND stpr.Diameter >= ls.MinBigSize AND tp.Code = 2 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' "
							+ ") AS z GROUP BY z.AbstractsSpeciesGroupID, z.AbstractsSpeciesGroupName, z.RoyaltyRate ORDER BY z.AbstractsSpeciesGroupID");
		}
		else
		{
			if (logSize == 1)
			{
				ps = facade.prepareStatement(
						"SELECT z.AbstractsSpeciesGroupID, z.AbstractsSpeciesGroupName, IFNULL(SUM(z.Volume), 0) AS Volume, IFNULL(z.RoyaltyRate, 0) AS RoyaltyRate, IFNULL(SUM(z.RoyaltyAmount), 0) AS RoyaltyAmount, IFNULL(SUM(z.CessAmount), 0) AS CessAmount "
								+ "FROM ("
								+ "SELECT asg.AbstractsSpeciesGroupID, asg.Name AS AbstractsSpeciesGroupName, ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2) AS Volume, mrrr.BigSizeRoyaltyRate AS RoyaltyRate, mrtpr.Royalty AS RoyaltyAmount, mrtpr.Cess AS CessAmount "
								+ "FROM TransferPass tp, License l, LogSize ls,  MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, MainRevenueRoyaltyRate mrrr, AbstractsSpeciesGroup asg, AbstractsSpeciesGroupRecord asgr "
								+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND asgr.AbstractsSpeciesGroupID = asg.AbstractsSpeciesGroupID AND asgr.SpeciesID = s.SpeciesID AND lg.Diameter >= ls.MinBigSize AND tp.Code = 0 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' "
								+ "UNION ALL "
								+ "SELECT asg.AbstractsSpeciesGroupID, asg.Name AS AbstractsSpeciesGroupName, ROUND((PI()*(stpr.Diameter/2/100)*(stpr.Diameter/2/100)*stpr.Length), 2) AS Volume, mrrr.BigSizeRoyaltyRate AS RoyaltyRate, stpr.Royalty AS RoyaltyAmount, stpr.Cess AS CessAmount "
								+ "FROM TransferPass tp, License l, LogSize ls,  SpecialTransferPassRecord stpr, Species s, MainRevenueRoyaltyRate mrrr, AbstractsSpeciesGroup asg, AbstractsSpeciesGroupRecord asgr  "
								+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND asgr.AbstractsSpeciesGroupID = asg.AbstractsSpeciesGroupID AND asgr.SpeciesID = s.SpeciesID AND stpr.Diameter >= ls.MinBigSize AND tp.Code = 2 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' "
								+ ") AS z GROUP BY z.AbstractsSpeciesGroupID, z.AbstractsSpeciesGroupName, z.RoyaltyRate ORDER BY z.AbstractsSpeciesGroupID");
			}
		}

		ps.setLong(1, licenseID);
		ps.setDate(2, toSQLDate(startDate));
		ps.setDate(3, toSQLDate(endDate));
		ps.setLong(4, licenseID);
		ps.setDate(5, toSQLDate(startDate));
		ps.setDate(6, toSQLDate(endDate));

		rs = ps.executeQuery();

		ArrayList<String[]> speciesList = new ArrayList<String[]>();

		while (rs.next())
		{
			String[] species = new String[5];
			species[0] = rs.getString("AbstractsSpeciesGroupName")
					.toUpperCase();

			species[1] = rs.getBigDecimal("Volume")
					.setScale(2, RoundingMode.HALF_UP).toString();
			species[2] = rs.getBigDecimal("RoyaltyAmount")
					.setScale(2, RoundingMode.HALF_UP).toString();
			species[3] = rs.getBigDecimal("CessAmount")
					.setScale(2, RoundingMode.HALF_UP).toString();
			species[4] = rs.getString("AbstractsSpeciesGroupID");
			speciesList.add(species);
		}

		String[][] species = new String[speciesGroups.size()][5];
		int counter = 0;
		for (String[] groupData : speciesGroups)
		{
			for (String[] speciesData : speciesList)
			{
				if (groupData[5].equalsIgnoreCase(speciesData[4]))
				{
					groupData[1] = speciesData[1];
					groupData[3] = speciesData[2];
					groupData[4] = speciesData[3];
				}
			}
			species[counter] = groupData;
			counter++;
		}
		counter = 0;

		return species;
	}

	ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenTypeAPage1andPage2(
			Date startDate, Date endDate, long licenseID, int logSize)
			throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (logSize == 0)
		{
			ps = facade.prepareStatement(
					"SELECT z.SpeciesID, z.SpeciesName, IFNULL(SUM(z.Volume), 0) AS Volume, IFNULL(z.RoyaltyRate, 0) AS RoyaltyRate, IFNULL(SUM(z.RoyaltyAmount), 0) AS RoyaltyAmount, IFNULL(SUM(z.CessAmount), 0) AS CessAmount "
							+ "FROM ( "
							+ "SELECT s.SpeciesID, s.Name AS SpeciesName, ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2) AS Volume, mrrr.SmallSizeRoyaltyRate AS RoyaltyRate, mrtpr.Royalty AS RoyaltyAmount, mrtpr.Cess AS CessAmount "
							+ "FROM TransferPass tp, License l, LogSize ls,  MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, MainRevenueRoyaltyRate mrrr "
							+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND lg.Diameter >= ls.MinSmallSize AND lg.Diameter < ls.MinBigSize AND tp.Code = 0 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' "
							+ "UNION ALL "
							+ "SELECT s.SpeciesID, s.Name AS SpeciesName, ROUND((PI()*(stpr.Diameter/2/100)*(stpr.Diameter/2/100)*stpr.Length), 2) AS Volume, mrrr.SmallSizeRoyaltyRate AS RoyaltyRate, stpr.Royalty AS RoyaltyAmount, stpr.Cess AS CessAmount "
							+ "FROM TransferPass tp, License l, LogSize ls,  SpecialTransferPassRecord stpr, Species s, MainRevenueRoyaltyRate mrrr "
							+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND stpr.Diameter >= ls.MinSmallSize AND stpr.Diameter < ls.MinBigSize AND tp.Code = 2 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' "
							+ ") "
							+ "AS z GROUP BY z.SpeciesID, z.SpeciesName, z.RoyaltyRate ORDER BY z.SpeciesName");
		}
		else
		{
			if (logSize == 1)
			{
				ps = facade.prepareStatement(
						"SELECT z.SpeciesID, z.SpeciesName, IFNULL(SUM(z.Volume), 0) AS Volume, IFNULL(z.RoyaltyRate, 0) AS RoyaltyRate, IFNULL(SUM(z.RoyaltyAmount), 0) AS RoyaltyAmount, IFNULL(SUM(z.CessAmount), 0) AS CessAmount "
								+ "FROM ( "
								+ "SELECT s.SpeciesID, s.Name AS SpeciesName, ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2) AS Volume, mrrr.BigSizeRoyaltyRate AS RoyaltyRate, mrtpr.Royalty AS RoyaltyAmount, mrtpr.Cess AS CessAmount "
								+ "FROM TransferPass tp, License l, LogSize ls,  MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, MainRevenueRoyaltyRate mrrr "
								+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND mrtpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND lg.Diameter >= ls.MinBigSize AND tp.Code = 0 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' "
								+ "UNION ALL "
								+ "SELECT s.SpeciesID, s.Name AS SpeciesName, ROUND((PI()*(stpr.Diameter/2/100)*(stpr.Diameter/2/100)*stpr.Length), 2) AS Volume, mrrr.BigSizeRoyaltyRate AS RoyaltyRate, stpr.Royalty AS RoyaltyAmount, stpr.Cess AS CessAmount "
								+ "FROM TransferPass tp, License l, LogSize ls,  SpecialTransferPassRecord stpr, Species s, MainRevenueRoyaltyRate mrrr "
								+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND stpr.MainRevenueRoyaltyRateID = mrrr.MainRevenueRoyaltyRateID AND stpr.Diameter >= ls.MinBigSize AND tp.Code = 2 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' "
								+ ") "
								+ "AS z GROUP BY z.SpeciesID, z.SpeciesName, z.RoyaltyRate ORDER BY z.SpeciesName");
			}
		}

		ps.setLong(1, licenseID);
		ps.setDate(2, toSQLDate(startDate));
		ps.setDate(3, toSQLDate(endDate));
		ps.setLong(4, licenseID);
		ps.setDate(5, toSQLDate(startDate));
		ps.setDate(6, toSQLDate(endDate));

		rs = ps.executeQuery();

		ArrayList<String[]> speciesList = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] species = new String[5];
			species[0] = rs.getString("SpeciesName").toUpperCase();
			species[1] = rs.getBigDecimal("Volume")
					.setScale(2, RoundingMode.HALF_UP).toString();
			species[2] = rs.getBigDecimal("RoyaltyRate")
					.setScale(2, RoundingMode.HALF_UP).toString();
			species[3] = rs.getBigDecimal("RoyaltyAmount")
					.setScale(2, RoundingMode.HALF_UP).toString();
			species[4] = rs.getBigDecimal("CessAmount")
					.setScale(2, RoundingMode.HALF_UP).toString();
			speciesList.add(species);
		}

		return speciesList;
	}

	ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenJarasBesar(
			Date startDate, Date endDate, long licenseID) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;

		ps = facade.prepareStatement(
				"SELECT SUBSTRING(sp.Name, 82) AS JarasName, SUM(sptpr.Quantity) AS Quantity, sprr.RoyaltyRate, SUM(sptpr.Royalty) AS RoyaltyAmount, SUM(sptpr.Cess) AS CessAmount "
						+ "FROM TransferPass tp, License l, SmallProductTransferPassRecord sptpr, SmallProduct sp, SmallProductRoyaltyRate sprr, Unit u "
						+ "WHERE tp.LicenseID = l.LicenseID AND sptpr.TransferPassID = tp.TransferPassID AND sptpr.SmallProductID = sp.SmallProductID AND sprr.SmallProductID = sp.SmallProductID AND sprr.UnitID = u.UnitID AND u.Code = '1' AND SUBSTRING(sp.Code, 1, 2) = \"JB\" AND tp.Code = 1 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' "
						+ "GROUP BY sp.SmallProductID, sp.Name, sprr.RoyaltyRate ORDER BY sp.Code");

		ps.setLong(1, licenseID);
		ps.setDate(2, toSQLDate(startDate));
		ps.setDate(3, toSQLDate(endDate));

		rs = ps.executeQuery();

		ArrayList<String[]> bigJaras = new ArrayList<String[]>();

		while (rs.next())
		{
			String[] jaras = new String[5];
			jaras[0] = rs.getString("JarasName").toUpperCase();
			jaras[1] = rs.getBigDecimal("Quantity")
					.setScale(0, RoundingMode.HALF_UP).toString();
			jaras[2] = rs.getBigDecimal("RoyaltyRate")
					.setScale(2, RoundingMode.HALF_UP).toString();
			jaras[3] = rs.getBigDecimal("RoyaltyAmount")
					.setScale(2, RoundingMode.HALF_UP).toString();
			jaras[4] = rs.getBigDecimal("CessAmount")
					.setScale(2, RoundingMode.HALF_UP).toString();
			bigJaras.add(jaras);
		}

		return bigJaras;
	}

	ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenJarasKecil(
			Date startDate, Date endDate, long licenseID) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;

		ps = facade.prepareStatement(
				"SELECT sp.Code AS SmallProductCode, SUM(sptpr.Quantity) AS Quantity, sprr.RoyaltyRate, SUM(sptpr.Royalty) AS RoyaltyAmount, SUM(sptpr.Cess) AS CessAmount "
						+ "FROM TransferPass tp, License l, SmallProductTransferPassRecord sptpr, SmallProduct sp, SmallProductRoyaltyRate sprr, Unit u "
						+ "WHERE tp.LicenseID = l.LicenseID AND sptpr.TransferPassID = tp.TransferPassID AND sptpr.SmallProductID = sp.SmallProductID AND sprr.SmallProductID = sp.SmallProductID AND sprr.UnitID = u.UnitID AND u.Code = '1' AND SUBSTRING(sp.Code, 1, 2) = \"JK\" AND tp.Code = 1 AND l.LicenseID = ? AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' "
						+ "GROUP BY sp.SmallProductID, sp.Code, sprr.RoyaltyRate ORDER BY sp.Code");

		ps.setLong(1, licenseID);
		ps.setDate(2, toSQLDate(startDate));
		ps.setDate(3, toSQLDate(endDate));

		rs = ps.executeQuery();

		ArrayList<String[]> smallJaras = new ArrayList<String[]>();

		while (rs.next())
		{
			String[] jaras = new String[5];
			String code = rs.getString("SmallProductCode");

			if (code.equalsIgnoreCase("JK01A"))
				jaras[0] = "1";
			else if (code.equalsIgnoreCase("JK03A"))
				jaras[0] = "2";
			else if (code.equalsIgnoreCase("JK02A"))
				jaras[0] = "3";
			else if (code.equalsIgnoreCase("JK02B"))
				jaras[0] = "4";
			else if (code.equalsIgnoreCase("JK04A"))
				jaras[0] = "5";
			else if (code.equalsIgnoreCase("JK05A"))
				jaras[0] = "6";
			else if (code.equalsIgnoreCase("JK05B"))
				jaras[0] = "7";
			else if (code.equalsIgnoreCase("JK06A"))
				jaras[0] = "8";
		
			jaras[1] = rs.getBigDecimal("Quantity")
					.setScale(0, RoundingMode.HALF_UP).toString();
			jaras[2] = rs.getBigDecimal("RoyaltyRate")
					.setScale(2, RoundingMode.HALF_UP).toString();
			jaras[3] = rs.getBigDecimal("RoyaltyAmount")
					.setScale(2, RoundingMode.HALF_UP).toString();
			jaras[4] = rs.getBigDecimal("CessAmount")
					.setScale(2, RoundingMode.HALF_UP).toString();
			smallJaras.add(jaras);
		}

		return smallJaras;
	}

	ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPengeluaranTableBigLog(
			Date startDate, Date endDate, long licenseID) throws SQLException
	{
		ArrayList<String[]> data = new ArrayList<String[]>();
		PreparedStatement ps = facade.prepareStatement("SELECT SUM(z.Volume) AS Volume "
				+ "FROM ( "
				+ "SELECT IFNULL(SUM(ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2)), 0) AS Volume "
				+ "FROM License l, TransferPass tp, MainRevenueTransferPassRecord mrtpr, Log lg, LogSize ls "
				+ "WHERE tp.LicenseID = l.LicenseID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID "
				+ "AND tp.LogSizeID = ls.LogSizeID AND lg.Diameter >= ls.MinBigSize AND tp.Date < ? AND l.LicenseID = ? AND tp.Status != 'I' "
				+ "UNION ALL "
				+ "SELECT IFNULL(SUM(ROUND((PI()*(stpr.Diameter/2/100)*(stpr.Diameter/2/100)*stpr.Length), 2)), 0) AS Volume "
				+ "FROM License l, TransferPass tp, SpecialTransferPassRecord stpr, LogSize ls "
				+ "WHERE tp.LicenseID = l.LicenseID AND stpr.TransferPassID = tp.TransferPassID "
				+ "AND tp.LogSizeID = ls.LogSizeID AND stpr.Diameter >= ls.MinBigSize AND tp.Date < ? AND l.LicenseID = ? AND tp.Status != 'I' "
				+ ") As z");

		for (int i = 0; i < 2; i++)
		{
			ps.setDate((i * 2 + 1), toSQLDate(startDate));
			ps.setLong((i * 2 + 2), licenseID);
		}

		ResultSet rs = ps.executeQuery();

		String[] before = new String[2];
		while (rs.next())
		{
			before[0] = "sebelum";
			before[1] = rs.getString("Volume");
		}
		data.add(before);

		ps = facade.prepareStatement("SELECT SUM(z.Volume) AS Volume "
				+ "FROM ( "
				+ "SELECT IFNULL(SUM(ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2)), 0) AS Volume "
				+ "FROM License l, TransferPass tp, MainRevenueTransferPassRecord mrtpr, Log lg, LogSize ls "
				+ "WHERE tp.LicenseID = l.LicenseID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID "
				+ "AND tp.LogSizeID = ls.LogSizeID AND lg.Diameter >= ls.MinBigSize AND tp.Date BETWEEN ? AND ? AND l.LicenseID = ? AND tp.Status != 'I' "
				+ "UNION ALL "
				+ "SELECT IFNULL(SUM(ROUND((PI()*(stpr.Diameter/2/100)*(stpr.Diameter/2/100)*stpr.Length), 2)), 0) AS Volume "
				+ "FROM License l, TransferPass tp, SpecialTransferPassRecord stpr, LogSize ls "
				+ "WHERE tp.LicenseID = l.LicenseID AND stpr.TransferPassID = tp.TransferPassID "
				+ "AND tp.LogSizeID = ls.LogSizeID AND stpr.Diameter >= ls.MinBigSize AND tp.Date BETWEEN ? AND ? AND l.LicenseID = ? AND tp.Status != 'I' "
				+ ") As z");

		for (int i = 0; i < 2; i++)
		{
			ps.setDate((i * 3 + 1), toSQLDate(startDate));
			ps.setDate((i * 3 + 2), toSQLDate(endDate));
			ps.setLong((i * 3 + 3), licenseID);
		}

		rs = ps.executeQuery();

		String[] current = new String[2];
		while (rs.next())
		{
			current[0] = "semasa";
			current[1] = rs.getString("Volume");
		}
		data.add(current);

		return data;
	}
	
	ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPengeluaranTableSmallLog(
			Date startDate, Date endDate, long licenseID) throws SQLException
	{
		ArrayList<String[]> data = new ArrayList<String[]>();
		PreparedStatement ps = facade.prepareStatement("SELECT SUM(z.Volume) AS Volume "
				+ "FROM ( "
				+ "SELECT IFNULL(SUM(ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2)), 0) AS Volume "
				+ "FROM License l, TransferPass tp, MainRevenueTransferPassRecord mrtpr, Log lg, LogSize ls "
				+ "WHERE tp.LicenseID = l.LicenseID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID "
				+ "AND tp.LogSizeID = ls.LogSizeID AND lg.Diameter >= ls.MinSmallSize AND lg.Diameter < ls.MinBigSize AND tp.Date < ? AND l.LicenseID = ? AND tp.Status != 'I' "
				+ "UNION ALL "
				+ "SELECT IFNULL(SUM(ROUND((PI()*(stpr.Diameter/2/100)*(stpr.Diameter/2/100)*stpr.Length), 2)), 0) AS Volume "
				+ "FROM License l, TransferPass tp, SpecialTransferPassRecord stpr, LogSize ls "
				+ "WHERE tp.LicenseID = l.LicenseID AND stpr.TransferPassID = tp.TransferPassID "
				+ "AND tp.LogSizeID = ls.LogSizeID AND stpr.Diameter >= ls.MinSmallSize AND stpr.Diameter < ls.MinBigSize AND tp.Date < ? AND l.LicenseID = ? AND tp.Status != 'I' "
				+ ") As z");

		for (int i = 0; i < 2; i++)
		{
			ps.setDate((i * 2 + 1), toSQLDate(startDate));
			ps.setLong((i * 2 + 2), licenseID);
		}

		ResultSet rs = ps.executeQuery();

		String[] before = new String[2];
		while (rs.next())
		{
			before[0] = "sebelum";
			before[1] = rs.getString("Volume");
		}
		data.add(before);

		ps = facade.prepareStatement("SELECT SUM(z.Volume) AS Volume "
				+ "FROM ( "
				+ "SELECT IFNULL(SUM(ROUND((PI()*(lg.Diameter/2/100)*(lg.Diameter/2/100)*lg.Length)-(PI()*(lg.HoleDiameter/2/100)*(lg.HoleDiameter/2/100)*lg.Length), 2)), 0) AS Volume "
				+ "FROM License l, TransferPass tp, MainRevenueTransferPassRecord mrtpr, Log lg, LogSize ls  "
				+ "WHERE tp.LicenseID = l.LicenseID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID "
				+ "AND tp.LogSizeID = ls.LogSizeID AND lg.Diameter >= ls.MinSmallSize AND lg.Diameter < ls.MinBigSize AND tp.Date BETWEEN ? AND ? AND l.LicenseID = ? AND tp.Status != 'I' "
				+ "UNION ALL "
				+ "SELECT IFNULL(SUM(ROUND((PI()*(stpr.Diameter/2/100)*(stpr.Diameter/2/100)*stpr.Length), 2)), 0) AS Volume "
				+ "FROM License l, TransferPass tp, SpecialTransferPassRecord stpr, LogSize ls "
				+ "WHERE tp.LicenseID = l.LicenseID AND stpr.TransferPassID = tp.TransferPassID "
				+ "AND tp.LogSizeID = ls.LogSizeID AND stpr.Diameter >= ls.MinSmallSize AND stpr.Diameter < ls.MinBigSize AND tp.Date BETWEEN ? AND ? AND l.LicenseID = ? AND tp.Status != 'I' "
				+ ") As z");

		for (int i = 0; i < 2; i++)
		{
			ps.setDate((i * 3 + 1), toSQLDate(startDate));
			ps.setDate((i * 3 + 2), toSQLDate(endDate));
			ps.setLong((i * 3 + 3), licenseID);
		}

		rs = ps.executeQuery();

		String[] current = new String[2];
		while (rs.next())
		{
			current[0] = "semasa";
			current[1] = rs.getString("Volume");
		}
		data.add(current);

		return data;
	}

	String[] getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPembayaranTableBigLog(
			Date startDate, Date endDate, long licenseID) throws SQLException
	{
		String[] data = new String[4];
		PreparedStatement ps = facade.prepareStatement(
				"SELECT IFNULL(SUM(z.RoyaltyBefore), 0) AS RoyaltyBefore, IFNULL(SUM(z.CessBefore), 0) AS CessBefore FROM "
						+ "("
						+ "SELECT IFNULL(ROUND(SUM(mrtpr.Royalty), 2), 0) AS RoyaltyBefore, IFNULL(ROUND(SUM(mrtpr.Cess), 2), 0) AS CessBefore "
						+ "FROM License l, TransferPass tp, MainRevenueTransferPassRecord mrtpr, Log lg, LogSize ls "
						+ "WHERE tp.LicenseID = l.LicenseID AND mrtpr.TransferPassID = tp.TransferPassID AND "
						+ "mrtpr.LogID = lg.LogID AND tp.LogSizeID = ls.LogSizeID AND lg.Diameter >= ls.MinBigSize AND tp.Code = 0 AND tp.Date < ? AND l.LicenseID = ? AND tp.Status != 'I' "
						+ "UNION ALL "
						+ "SELECT IFNULL(ROUND(SUM(stpr.Royalty), 2), 0) AS RoyaltyBefore, IFNULL(ROUND(SUM(stpr.Cess), 2), 0) AS CessBefore "
						+ "FROM License l, TransferPass tp, SpecialTransferPassRecord stpr, LogSize ls "
						+ "WHERE tp.LicenseID = l.LicenseID AND stpr.TransferPassID = tp.TransferPassID AND "
						+ "tp.LogSizeID = ls.LogSizeID AND stpr.Diameter >= ls.MinBigSize AND tp.Code = 2 AND tp.Date < ? AND l.LicenseID = ? AND tp.Status != 'I'"
						+ ") AS z");

		ps.setDate(1, toSQLDate(startDate));
		ps.setLong(2, licenseID);
		ps.setDate(3, toSQLDate(startDate));
		ps.setLong(4, licenseID);

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			data[0] = rs.getBigDecimal("RoyaltyBefore")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			data[2] = rs.getBigDecimal("CessBefore")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}

		ps = facade.prepareStatement(
				"SELECT IFNULL(SUM(z.CurrentRoyalty), 0) AS CurrentRoyalty, IFNULL(SUM(z.CurrentCess), 0) AS CurrentCess FROM "
						+ "("
						+ "SELECT IFNULL(ROUND(SUM(mrtpr.Royalty), 2), 0) AS CurrentRoyalty, IFNULL(ROUND(SUM(mrtpr.Cess), 2), 0) AS CurrentCess "
						+ "FROM License l, TransferPass tp, MainRevenueTransferPassRecord mrtpr, Log lg, LogSize ls "
						+ "WHERE tp.LicenseID = l.LicenseID AND mrtpr.TransferPassID = tp.TransferPassID AND "
						+ "mrtpr.LogID = lg.LogID AND tp.LogSizeID = ls.LogSizeID AND lg.Diameter >= ls.MinBigSize AND tp.Code = 0 AND tp.Date BETWEEN ? AND ? AND l.LicenseID = ? AND tp.Status != 'I' "
						+ "UNION ALL "
						+ "SELECT IFNULL(SUM(stpr.Royalty), 0) AS CurrentRoyalty, IFNULL(SUM(stpr.Cess), 0) AS CurrentCess "
						+ "FROM License l, TransferPass tp, SpecialTransferPassRecord stpr, LogSize ls "
						+ "WHERE tp.LicenseID = l.LicenseID AND stpr.TransferPassID = tp.TransferPassID AND "
						+ "tp.LogSizeID = ls.LogSizeID AND stpr.Diameter >= ls.MinBigSize AND tp.Code = 2 AND tp.Date BETWEEN ? AND ? AND l.LicenseID = ? AND tp.Status != 'I'"
						+ ") AS z");

		ps.setDate(1, toSQLDate(startDate));
		ps.setDate(2, toSQLDate(endDate));
		ps.setLong(3, licenseID);
		ps.setDate(4, toSQLDate(startDate));
		ps.setDate(5, toSQLDate(endDate));
		ps.setLong(6, licenseID);

		rs = ps.executeQuery();

		while (rs.next())
		{
			data[1] = rs.getBigDecimal("CurrentRoyalty")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			data[3] = rs.getBigDecimal("CurrentCess")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}

		return data;
	}
	
	String[] getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPembayaranTableSmallLog(
			Date startDate, Date endDate, long licenseID) throws SQLException
	{
		String[] data = new String[4];
		PreparedStatement ps = facade.prepareStatement(
				"SELECT IFNULL(SUM(z.RoyaltyBefore), 0) AS RoyaltyBefore, IFNULL(SUM(z.CessBefore), 0) AS CessBefore FROM "
						+ "("
						+ "SELECT IFNULL(ROUND(SUM(mrtpr.Royalty), 2), 0) AS RoyaltyBefore, IFNULL(ROUND(SUM(mrtpr.Cess), 2), 0) AS CessBefore "
						+ "FROM License l, TransferPass tp, MainRevenueTransferPassRecord mrtpr, Log lg, LogSize ls "
						+ "WHERE tp.LicenseID = l.LicenseID AND mrtpr.TransferPassID = tp.TransferPassID AND "
						+ "mrtpr.LogID = lg.LogID AND tp.LogSizeID = ls.LogSizeID AND lg.Diameter >= ls.MinSmallSize AND lg.Diameter < ls.MinBigSize AND tp.Code = 0 AND tp.Date < ? AND l.LicenseID = ? AND tp.Status != 'I' "
						+ "UNION ALL "
						+ "SELECT IFNULL(ROUND(SUM(stpr.Royalty), 2), 0) AS RoyaltyBefore, IFNULL(ROUND(SUM(stpr.Cess), 2), 0) AS CessBefore "
						+ "FROM License l, TransferPass tp, SpecialTransferPassRecord stpr, LogSize ls "
						+ "WHERE tp.LicenseID = l.LicenseID AND stpr.TransferPassID = tp.TransferPassID AND "
						+ "tp.LogSizeID = ls.LogSizeID AND stpr.Diameter >= ls.MinSmallSize AND stpr.Diameter < ls.MinBigSize AND tp.Code = 2 AND tp.Date < ? AND l.LicenseID = ? AND tp.Status != 'I'"
						+ ") AS z");

		ps.setDate(1, toSQLDate(startDate));
		ps.setLong(2, licenseID);
		ps.setDate(3, toSQLDate(startDate));
		ps.setLong(4, licenseID);

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			data[0] = rs.getBigDecimal("RoyaltyBefore")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			data[2] = rs.getBigDecimal("CessBefore")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}

		ps = facade.prepareStatement(
				"SELECT IFNULL(SUM(z.CurrentRoyalty), 0) AS CurrentRoyalty, IFNULL(SUM(z.CurrentCess), 0) AS CurrentCess FROM "
						+ "("
						+ "SELECT IFNULL(ROUND(SUM(mrtpr.Royalty), 2), 0) AS CurrentRoyalty, IFNULL(ROUND(SUM(mrtpr.Cess), 2), 0) AS CurrentCess "
						+ "FROM License l, TransferPass tp, MainRevenueTransferPassRecord mrtpr, Log lg, LogSize ls "
						+ "WHERE tp.LicenseID = l.LicenseID AND mrtpr.TransferPassID = tp.TransferPassID AND "
						+ "mrtpr.LogID = lg.LogID AND tp.LogSizeID = ls.LogSizeID AND lg.Diameter >= ls.MinSmallSize AND lg.Diameter < ls.MinBigSize AND tp.Code = 0 AND tp.Date BETWEEN ? AND ? AND l.LicenseID = ? AND tp.Status != 'I' "
						+ "UNION ALL "
						+ "SELECT IFNULL(SUM(stpr.Royalty), 0) AS CurrentRoyalty, IFNULL(SUM(stpr.Cess), 0) AS CurrentCess "
						+ "FROM License l, TransferPass tp, SpecialTransferPassRecord stpr, LogSize ls "
						+ "WHERE tp.LicenseID = l.LicenseID AND stpr.TransferPassID = tp.TransferPassID AND "
						+ "tp.LogSizeID = ls.LogSizeID AND stpr.Diameter >= ls.MinSmallSize AND stpr.Diameter < ls.MinBigSize AND tp.Code = 2 AND tp.Date BETWEEN ? AND ? AND l.LicenseID = ? AND tp.Status != 'I'"
						+ ") AS z");

		ps.setDate(1, toSQLDate(startDate));
		ps.setDate(2, toSQLDate(endDate));
		ps.setLong(3, licenseID);
		ps.setDate(4, toSQLDate(startDate));
		ps.setDate(5, toSQLDate(endDate));
		ps.setLong(6, licenseID);

		rs = ps.executeQuery();

		while (rs.next())
		{
			data[1] = rs.getBigDecimal("CurrentRoyalty")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			data[3] = rs.getBigDecimal("CurrentCess")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}

		return data;
	}

	String[][] getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenDetailTransferPass(
			Date startDate, Date endDate, long licenseID)
			throws SQLException, ParseException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.TransferPassID, z.Tarikh, z.NoPasMemindah, z.Isipadu, z.RoyaltyAmount, z.CessAmount, z.RoyaltyJaras, z.CessJaras, z.Code "
						+ "FROM ("
						+ "SELECT tp.TransferPassID, tp.Date AS Tarikh, tp.TransferPassNo AS NoPasMemindah, IFNULL(SUM(ROUND(PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length, 2)), 0) AS Isipadu, tp.RoyaltyAmount, tp.CessAmount, 0 AS RoyaltyJaras, 0 AS CessJaras, tp.Code "
						+ "FROM TransferPass tp, License l, MainRevenueTransferPassRecord mrtpr, Log lg "
						+ "WHERE tp.LicenseID = l.LicenseID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND tp.Code = 0 AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' AND l.LicenseID = ? "
						+ "GROUP BY tp.TransferPassID " + "UNION ALL "
						+ "SELECT tp.TransferPassID, tp.Date AS Tarikh, tp.TransferPassNo AS NoPasMemindah, 0 AS Isipadu, 0 AS RoyaltyAmount, 0 AS CessAmount, tp.RoyaltyAmount AS RoyaltyJaras, tp.CessAmount AS CessJaras, tp.Code "
						+ "FROM TransferPass tp, License l "
						+ "WHERE tp.LicenseID = l.LicenseID AND tp.Code = 1 AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' AND l.LicenseID = ? "
						+ "GROUP BY tp.TransferPassID " + "UNION ALL "
						+ "SELECT tp.TransferPassID, tp.Date AS Tarikh, tp.TransferPassNo AS NoPasMemindah, IFNULL(SUM(ROUND(PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length, 2)), 0) AS Isipadu, tp.RoyaltyAmount, tp.CessAmount, 0 AS RoyaltyJaras, 0 AS CessJaras, tp.Code "
						+ "FROM TransferPass tp, License l, SpecialTransferPassRecord stpr "
						+ "WHERE tp.LicenseID = l.LicenseID AND stpr.TransferPassID = tp.TransferPassID AND tp.Code = 2 AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' AND l.LicenseID = ? "
						+ "GROUP BY tp.TransferPassID" + ") AS z "
						+ "ORDER BY z.Tarikh, z.NoPasMemindah");

		for (int i = 0; i < 3; i++)
		{
			ps.setDate(i * 3 + 1, toSQLDate(startDate));
			ps.setDate(i * 3 + 2, toSQLDate(endDate));
			ps.setLong(i * 3 + 3, licenseID);
		}

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> uniqueTransferPasses = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] uniqueTransferPass = new String[9];
			uniqueTransferPass[0] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("Tarikh"));
			uniqueTransferPass[1] = rs.getString("NoPasMemindah");
			uniqueTransferPass[2] = rs.getBigDecimal("Isipadu")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			uniqueTransferPass[3] = rs.getString("CessAmount");
			uniqueTransferPass[4] = rs.getBigDecimal("RoyaltyAmount").add(rs.getBigDecimal("CessAmount")).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			uniqueTransferPass[5] = rs.getString("CessJaras");
			uniqueTransferPass[6] = rs.getBigDecimal("RoyaltyJaras").add(rs.getBigDecimal("CessJaras")).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			uniqueTransferPass[7] = rs.getString("TransferPassID");
			uniqueTransferPass[8] = rs.getString("Code");
			uniqueTransferPasses.add(uniqueTransferPass);
		}

		ps = facade.prepareStatement(
				"SELECT z.TransferPassID, z.SpeciesID, z.SpeciesName, ROUND(SUM(z.Royalty), 2) AS Royalty FROM("
						+ "SELECT tp.Date, tp.TransferPassID, tp.TransferPassNo, s.SpeciesID, s.Name AS SpeciesName, mrtpr.Royalty "
						+ "FROM TransferPass tp, License l, LogSize ls, MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s "
						+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND tp.Code = 0 AND lg.Diameter >= ls.MinBigSize AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' AND l.LicenseID = ? "
						+ "UNION ALL "
						+ "SELECT tp.Date, tp.TransferPassID, tp.TransferPassNo, s.SpeciesID, s.Name AS SpeciesName, stpr.Royalty "
						+ "FROM TransferPass tp, License l, LogSize ls, SpecialTransferPassRecord stpr, Species s "
						+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND tp.Code = 2 AND stpr.Diameter >= ls.MinBigSize AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' AND l.LicenseID = ? "
						+ ") AS z GROUP BY z.Date, z.TransferPassID, z.TransferPassNo, z.SpeciesID, z.SpeciesName ORDER BY z.Date, z.TransferPassNo, z.SpeciesName");

		for (int i = 0; i < 2; i++)
		{
			ps.setDate(i * 3 + 1, toSQLDate(startDate));
			ps.setDate(i * 3 + 2, toSQLDate(endDate));
			ps.setLong(i * 3 + 3, licenseID);
		}

		rs = ps.executeQuery();

		ArrayList<String[]> bigSizeLogs = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] bigSizeLog = new String[3];
			bigSizeLog[0] = rs.getString("TransferPassID");
			bigSizeLog[1] = rs.getString("SpeciesName");
			bigSizeLog[2] = rs.getBigDecimal("Royalty")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			bigSizeLogs.add(bigSizeLog);
		}

		ArrayList<String> uniqueBigSizeSpecies = new ArrayList<String>();
		for (String[] bigSizeLog : bigSizeLogs)
		{
			if (!uniqueBigSizeSpecies.contains(bigSizeLog[1]))
			{
				uniqueBigSizeSpecies.add(bigSizeLog[1]);
			}
		}

		ps = facade.prepareStatement(
				"SELECT z.TransferPassID, z.SpeciesID, z.SpeciesName, ROUND(SUM(z.Royalty), 2) AS Royalty FROM("
						+ "SELECT tp.Date, tp.TransferPassID, tp.TransferPassNo, s.SpeciesID, s.Name AS SpeciesName, mrtpr.Royalty "
						+ "FROM TransferPass tp, License l, LogSize ls, MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s "
						+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND tp.Code = 0 AND lg.Diameter >= ls.MinSmallSize AND lg.Diameter < ls.MinBigSize AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' AND l.LicenseID = ? "
						+ "UNION ALL "
						+ "SELECT tp.Date, tp.TransferPassID, tp.TransferPassNo, s.SpeciesID, s.Name AS SpeciesName, stpr.Royalty "
						+ "FROM TransferPass tp, License l, LogSize ls, SpecialTransferPassRecord stpr, Species s "
						+ "WHERE tp.LicenseID = l.LicenseID AND tp.LogSizeID = ls.LogSizeID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND tp.Code = 2 AND stpr.Diameter >= ls.MinSmallSize AND stpr.Diameter < ls.MinBigSize AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' AND l.LicenseID = ? "
						+ ") AS z GROUP BY z.Date, z.TransferPassID, z.TransferPassNo, z.SpeciesID, z.SpeciesName ORDER BY z.Date, z.TransferPassNo, z.SpeciesName");

		for (int i = 0; i < 2; i++)
		{
			ps.setDate(i * 3 + 1, toSQLDate(startDate));
			ps.setDate(i * 3 + 2, toSQLDate(endDate));
			ps.setLong(i * 3 + 3, licenseID);
		}

		rs = ps.executeQuery();

		ArrayList<String[]> smallSizeLogs = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] smallSizeLog = new String[3];
			smallSizeLog[0] = rs.getString("TransferPassID");
			smallSizeLog[1] = rs.getString("SpeciesName");
			smallSizeLog[2] = rs.getBigDecimal("Royalty")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			smallSizeLogs.add(smallSizeLog);
		}

		ArrayList<String> uniqueSmallSizeSpecies = new ArrayList<String>();
		for (String[] smallSizeLog : smallSizeLogs)
		{
			if (!uniqueSmallSizeSpecies.contains(smallSizeLog[1]))
			{
				uniqueSmallSizeSpecies.add(smallSizeLog[1]);
			}
		}
		
		ps = facade.prepareStatement(
				"SELECT tp.Date, tp.TransferPassID, tp.TransferPassNo, SUM(ROUND(IF(SUBSTRING(sp.Code, 1, 2) = \"JB\", sptpr.Royalty, 0), 2)) AS JB, SUM(ROUND(IF(SUBSTRING(sp.Code, 1, 4) = \"JK01A\", sptpr.Royalty, 0), 2)) AS JK01A, SUM(ROUND(IF(SUBSTRING(sp.Code, 1, 5) = \"JK02A\", sptpr.Royalty, 0), 2)) AS JK02A, SUM(ROUND(IF(SUBSTRING(sp.Code, 1, 5) = \"JK02B\", sptpr.Royalty, 0), 2)) AS JK02B, SUM(ROUND(IF(SUBSTRING(sp.Code, 1, 5) = \"JK02C\", sptpr.Royalty, 0), 2)) AS JK02C, SUM(ROUND(IF(SUBSTRING(sp.Code, 1, 5) = \"JK03A\", sptpr.Royalty, 0), 2)) AS JK03A, SUM(ROUND(IF(SUBSTRING(sp.Code, 1, 5) = \"JK03B\", sptpr.Royalty, 0), 2)) AS JK03B, SUM(ROUND(IF(SUBSTRING(sp.Code, 1, 5) = \"JK03C\", sptpr.Royalty, 0), 2)) AS JK03C, SUM(ROUND(IF(SUBSTRING(sp.Code, 1, 5) = \"JK04A\", sptpr.Royalty, 0), 2)) AS JK04A " + 
				"FROM TransferPass tp, License l, SmallProductTransferPassRecord sptpr, SmallProduct sp " + 
				"WHERE tp.LicenseID = l.LicenseID AND sptpr.TransferPassID = tp.TransferPassID AND sptpr.SmallProductID = sp.SmallProductID AND tp.Status != 'I' " + 
				"AND tp.Date BETWEEN ? AND ? AND l.LicenseID = ? "
				+ "GROUP BY tp.TransferPassID");

		for (int i = 0; i < 2; i++)
		{
			ps.setDate(1, toSQLDate(startDate));
			ps.setDate(2, toSQLDate(endDate));
			ps.setLong(3, licenseID);
		}

		rs = ps.executeQuery();

		ArrayList<String[]> jarases = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] jaras = new String[10];
			jaras[0] = rs.getString("TransferPassID");
			jaras[1] = rs.getBigDecimal("JB").setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			jaras[2] = rs.getBigDecimal("JK01A").setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			jaras[3] = rs.getBigDecimal("JK02A").setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			jaras[4] = rs.getBigDecimal("JK02B").setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			jaras[5] = rs.getBigDecimal("JK02C").setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			jaras[6] = rs.getBigDecimal("JK03A").setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			jaras[7] = rs.getBigDecimal("JK03B").setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			jaras[8] = rs.getBigDecimal("JK03C").setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			jaras[9] = rs.getBigDecimal("JK04A").setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			jarases.add(jaras);
		}

		ps = facade.prepareStatement(
				"SELECT IFNULL(SUM(ROUND(rr.rate*rr.Quantity, 2)), 0) AS bakiAwalPelesen "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, TrustFund tf, License l "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID "
						+ "AND tf.DepartmentVotID = dv.DepartmentVotID AND r.LicenseID = l.LicenseID AND r.Status = 'A' "
						+ "AND tf.Symbol = \"WAKK\" AND r.Date < ? AND l.LicenseID = ?");

		ps.setDate(1, toSQLDate(startDate));
		ps.setLong(2, licenseID);
		
		rs = ps.executeQuery();

		BigDecimal bakiAwalPelesen = BigDecimal.ZERO;

		while (rs.next())
		{
			BigDecimal temp = rs.getBigDecimal("bakiAwalPelesen");
			
			if (temp != null)
				bakiAwalPelesen = temp;
		}

		ps = facade.prepareStatement(
				"SELECT IFNULL(SUM(ROUND((tp.RoyaltyAmount + tp.CessAmount), 2)), 0) AS kutipanRoyaltiDanSes "
						+ "FROM TransferPass tp, License l "
						+ "WHERE tp.LicenseID = l.LicenseID AND tp.Status != 'I' "
						+ "AND tp.Date < ? AND l.LicenseID = ? "
						+ "ORDER BY tp.Date");

		ps.setDate(1, toSQLDate(startDate));
		ps.setLong(2, licenseID);
		
		rs = ps.executeQuery();

		while (rs.next())
		{
			BigDecimal kutipanRoyaltiDanSes = rs.getBigDecimal("kutipanRoyaltiDanSes");
			
			if (kutipanRoyaltiDanSes != null)
				bakiAwalPelesen = bakiAwalPelesen.subtract(kutipanRoyaltiDanSes);
		}

		ps = facade.prepareStatement(
				"SELECT r.Date AS tarikh, ROUND(rr.rate*rr.Quantity, 2) AS tambahanWangAmanahKerjaKayu, r.ReceiptNo "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, TrustFund tf, License l "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID "
						+ "AND tf.DepartmentVotID = dv.DepartmentVotID AND r.LicenseID = l.LicenseID AND r.Status = 'A' "
						+ "AND tf.Symbol = \"WAKK\" AND r.Date BETWEEN ? AND ? AND l.LicenseID = ?");

		ps.setDate(1, toSQLDate(startDate));
		ps.setDate(2, toSQLDate(endDate));
		ps.setLong(3, licenseID);
		
		rs = ps.executeQuery();

		ArrayList<String[]> wakks = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] wakk = new String[3];
			wakk[0] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("tarikh")).toString();
			wakk[1] = rs.getBigDecimal("tambahanWangAmanahKerjaKayu")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			wakk[2] = rs.getString("ReceiptNo");
			wakks.add(wakk);
		}

		int column;
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		c1.add(Calendar.DATE, -1);
		
		String[][] data = new String[uniqueTransferPasses.size()
				+ 3 + wakks.size()][uniqueBigSizeSpecies.size() + uniqueSmallSizeSpecies.size()
						+ 16];
		data[0][0] = "TARIKH";
		data[0][1] = "NO PAS MEMINDAH";
		data[1][0] = "TARIKH";
		data[1][1] = "NO PAS MEMINDAH";
		data[2][0] = "BAKI CUKAI DARI " + dateFormat.format(c1.getTime());
		data[2][1] = "";
		column = 2;

		for (String bigSizeSpecies : uniqueBigSizeSpecies)
		{
			data[0][column] = "BALAK BESAR >= 45 CM";
			data[1][column] = bigSizeSpecies.toUpperCase();
			data[2][column] = "";
			column++;
		}

		for (String smallSizeSpecies : uniqueSmallSizeSpecies)
		{
			data[0][column] = "BALAK KECIL < 45 CM";
			data[1][column] = smallSizeSpecies.toUpperCase();
			data[2][column] = "";
			column++;
		}
		data[0][column] = "K. JARAS";
		data[1][column] = "20 - 30 CM lebih 2 M";
		data[2][column] = "";
		column++;
		data[0][column] = "K. JARAS";
		data[1][column] = "20 - 30 CM kurang 2 M";
		data[2][column] = "";
		column++;
		data[0][column] = "K. JARAS";
		data[1][column] = "10 - 20 CM kurang 2 M";
		data[2][column] = "";
		column++;
		data[0][column] = "K. JARAS";
		data[1][column] = "10 - 20 CM lebih 2 M kurang 5 M";
		data[2][column] = "";
		column++;
		data[0][column] = "K. JARAS";
		data[1][column] = "10 - 20 CM lebih 5 M";
		data[2][column] = "";
		column++;
		data[0][column] = "K. JARAS";
		data[1][column] = "5 - 10 CM kurang 2 M";
		data[2][column] = "";
		column++;
		data[0][column] = "K. JARAS";
		data[1][column] = "5 - 10 CM lebih 2 M kurang 5 M";
		data[2][column] = "";
		column++;		
		data[0][column] = "K. JARAS";
		data[1][column] = "5 - 10 CM lebih 5 M";
		data[2][column] = "";
		column++;
		data[0][column] = "K. JARAS";
		data[1][column] = "kurang 5 CM";
		data[2][column] = "";
		column++;
		data[0][column] = "";
		data[1][column] = "SES JARAS";
		data[2][column] = "";
		column++;
		data[0][column] = "";
		data[1][column] = "M3 KAYU BALAK";
		data[2][column] = "";
		column++;
		data[0][column] = "";
		data[1][column] = "SES KAYU BALAK";
		data[2][column] = "";
		column++;
		data[0][column] = "";
		data[1][column] = "ROYALTI";
		data[2][column] = "";
		column++;
		data[0][column] = "";
		data[1][column] = "BAKI";
		data[2][column] = bakiAwalPelesen.toString();

		boolean bigSizeExist = false;
		boolean smallSizeExist = false;
		int transferPassIndex = 0, wangAmanahKerjaKayuIndex = 0;
		
		String[][] transferPasses = new String[uniqueTransferPasses.size()][8];	
		int counter = 0;
		for (String[] tp : uniqueTransferPasses)
		{
			transferPasses[counter] = tp;
			counter ++;
		}
		
		String[][] wangAmanahKerjaKayu = new String[wakks.size()][2];	
		counter = 0;
		for (String[] wakk : wakks)
		{
			wangAmanahKerjaKayu[counter] = wakk;
			counter ++;
		}		
		
		int transferPassSize = uniqueTransferPasses.size();
		int wangAmanahKerjaKayuSize = wakks.size();
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DATE, 1);
		Date invalidDate = c.getTime();
		
		Date transferPassDate = new Date();
		Date wangAmanahKerjaKayuDate = new Date();
		for(int row = 3; row < (uniqueTransferPasses.size() + 3 + wakks.size()); row ++)
		{
			column = 2;
			if(transferPassIndex == transferPassSize)
			{
				transferPassDate = invalidDate;
			}
			else
			{
				transferPassDate = new SimpleDateFormat("dd/MM/yyyy").parse(transferPasses[transferPassIndex][0]);
			}
			
			if(wangAmanahKerjaKayuIndex == wangAmanahKerjaKayuSize)
			{
				wangAmanahKerjaKayuDate = invalidDate;
			}
			else
			{
				wangAmanahKerjaKayuDate = new SimpleDateFormat("dd/MM/yyyy").parse(wangAmanahKerjaKayu[wangAmanahKerjaKayuIndex][0]);
			}

			if (wangAmanahKerjaKayuSize != 0 && wangAmanahKerjaKayuDate.compareTo(transferPassDate) <= 0 && wangAmanahKerjaKayuDate != invalidDate)
			{
				data[row][0] = wangAmanahKerjaKayu[wangAmanahKerjaKayuIndex][0];
				data[row][1] = null;
				data[row][2] = "MASUK CUKAI SEBANYAK RM " + wangAmanahKerjaKayu[wangAmanahKerjaKayuIndex][1] + ", NO. RESIT : " + wangAmanahKerjaKayu[wangAmanahKerjaKayuIndex][2];
				bakiAwalPelesen = bakiAwalPelesen.add(new BigDecimal(wangAmanahKerjaKayu[wangAmanahKerjaKayuIndex][1])).setScale(2, BigDecimal.ROUND_HALF_UP);
				data[row][uniqueBigSizeSpecies.size() +  uniqueSmallSizeSpecies.size() + 15] = bakiAwalPelesen.toString();		
				wangAmanahKerjaKayuIndex++;
			}
			else
			{
				if(transferPassSize != 0 && transferPassDate != invalidDate)
				{
				bigSizeExist = false;
				smallSizeExist = false;
				data[row][0] = transferPasses[transferPassIndex][0];
				data[row][1] = transferPasses[transferPassIndex][1];
				
				if( !transferPasses[transferPassIndex][8].equalsIgnoreCase("1") )
				{
					for (String[] bigSizeLog : bigSizeLogs)
					{
						if (transferPasses[transferPassIndex][7].equalsIgnoreCase(bigSizeLog[0]))
						{
							bigSizeExist = true;
							for (int noOfBigSpecies = 0; noOfBigSpecies < uniqueBigSizeSpecies.size(); noOfBigSpecies++)
							{
								if (bigSizeLog[1].equalsIgnoreCase(data[1][column + noOfBigSpecies]))
								{
									data[row][column + noOfBigSpecies] = bigSizeLog[2];
								}
								else
								{
									if (data[row][column + noOfBigSpecies] == null || data[row][column + noOfBigSpecies].equalsIgnoreCase("0"))
									{
										data[row][column + noOfBigSpecies] = "0.00";
									}
								}
							}
						}
					}
	
					if (bigSizeExist == false)
					{
						for (int noOfBigSpecies = 0; noOfBigSpecies < uniqueBigSizeSpecies.size(); noOfBigSpecies++)
						{
							data[row][column + noOfBigSpecies] = "0";
						}
					}
				
				
				column = 2 + uniqueBigSizeSpecies.size();
	
				for (String[] smallSizeLog : smallSizeLogs)
				{
					if (transferPasses[transferPassIndex][7].equalsIgnoreCase(smallSizeLog[0]))
					{
						smallSizeExist = true;
						for (int noOfSmallSpecies = 0; noOfSmallSpecies < uniqueSmallSizeSpecies.size(); noOfSmallSpecies++)
						{
							if (smallSizeLog[1].equalsIgnoreCase(data[1][column + noOfSmallSpecies]))
							{
								data[row][column + noOfSmallSpecies] = smallSizeLog[2];
							}
							else
							{
								if (data[row][column + noOfSmallSpecies] == null || data[row][column + noOfSmallSpecies].equalsIgnoreCase("0"))
								{
									data[row][column + noOfSmallSpecies] = "0";
								}
							}
						}
					}
				}
	
				if (smallSizeExist == false)
				{
					for (int noOfSmallSpecies = 0; noOfSmallSpecies < uniqueSmallSizeSpecies.size(); noOfSmallSpecies++)
					{
						data[row][column + noOfSmallSpecies] = "0";
					}
				}
				column = 2 + uniqueBigSizeSpecies.size() + uniqueSmallSizeSpecies.size();
				data[row][column] = "0.00";
				data[row][column + 1] = "0.00";
				data[row][column + 2] = "0.00";
				data[row][column + 3] = "0.00";
				data[row][column + 4] = "0.00";
				data[row][column + 5] = "0.00";
				data[row][column + 6] = "0.00";
				data[row][column + 7] = "0.00";
				data[row][column + 8] = "0.00";
				data[row][column + 9] = "0.00";
				data[row][column + 10] = transferPasses[transferPassIndex][2];
				data[row][column + 11] = transferPasses[transferPassIndex][3];
				data[row][column + 12] = transferPasses[transferPassIndex][4];
				bakiAwalPelesen = bakiAwalPelesen.subtract(new BigDecimal(transferPasses[transferPassIndex][4])).setScale(2, BigDecimal.ROUND_HALF_UP);
				data[row][column + 13] = bakiAwalPelesen.toString();
				transferPassIndex++;
			}
			else
			{
				for(String[] jaras : jarases)
				{
					if(jaras[0].equalsIgnoreCase(transferPasses[transferPassIndex][7]))
					{
						column = 2 + uniqueBigSizeSpecies.size() + uniqueSmallSizeSpecies.size();
						for(int j = 2; j < column;  j++)
						{
							data[row][j] = "0.00";
						}
						data[row][column] = jaras[1];
						data[row][column + 1] = jaras[2];
						data[row][column + 2] = jaras[3];
						data[row][column + 3] = jaras[4];
						data[row][column + 4] = jaras[5];
						data[row][column + 5] = jaras[6];
						data[row][column + 6] = jaras[7];
						data[row][column + 7] = jaras[8];
						data[row][column + 8] = jaras[9];
						data[row][column + 9] = transferPasses[transferPassIndex][5];
						data[row][column + 10] = "0.00";
						data[row][column + 11] = "0.00";
						data[row][column + 12] = transferPasses[transferPassIndex][6];
						bakiAwalPelesen = bakiAwalPelesen.subtract(new BigDecimal(transferPasses[transferPassIndex][6])).setScale(2, BigDecimal.ROUND_HALF_UP);
						data[row][column + 13] = bakiAwalPelesen.toString();
						transferPassIndex++;
						break;
					}
				}
			}
		}
			}
	}
		return data;
	}

	String[] getLaporanPengeluaranKayuBalakDiBalaiHeader(Date startDate,
			Date endDate, long licenseID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT h.Name AS HallName, l.LicenseNo, l.WoodWorkFund, lc.CompanyName, lc.startDate, MAX(r.EndDate) AS endDate, f.Name AS ForestName, l.CompartmentNo, l.CompartmentArea "
				+ "FROM License l JOIN Renew r JOIN Hall h JOIN TransferPass tp LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN Forest f ON l.ForestID = f.ForestID "
				+ "WHERE r.LicenseID = l.LicenseID AND l.HallID = h.HallID AND tp.LicenseID = l.LicenseID AND tp.Status != 'I' AND r.Status = 'A' AND l.LicenseID = ?");

		ps.setLong(1, licenseID);

		ResultSet rs = ps.executeQuery();

		String[] data = new String[11];

		while (rs.next())
		{
			data[0] = rs.getString("HallName");
			data[1] = rs.getString("LicenseNo") + " "
					+ rs.getString("CompanyName");
			data[2] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("startDate"));
			data[3] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("endDate"));
			if("".equalsIgnoreCase(rs.getString("CompartmentNo")) && rs.getString("ForestName") == null)
			{
				data[4] = "";
			}
			else
			{
				data[4] = rs.getString("CompartmentNo") + ", "
						+ rs.getString("ForestName");
			}
			if(rs.getString("CompartmentArea") == null) 
			{
				data[5] = "0";
			}
			else
			{
				data[5] = rs.getString("CompartmentArea");
			}
			data[6] = new SimpleDateFormat("dd/MM/yyyy")
					.format(startDate);
			data[7] = rs.getString("WoodWorkFund");
			data[8] = new SimpleDateFormat("dd/MM/yyyy")
					.format(endDate);
		}
		
		ps = facade.prepareStatement(
				"SELECT COUNT(tp.TransferPassID) AS tpNo "
				+ "FROM License l LEFT JOIN TransferPass tp ON tp.LicenseID = l.LicenseID "
				+ "WHERE tp.Status != 'I' AND tp.Date BETWEEN ? AND ? AND l.LicenseID = ?");

		ps.setDate(1, toSQLDate(startDate));
		ps.setDate(2, toSQLDate(endDate));
		ps.setLong(3, licenseID);

		rs = ps.executeQuery();

		while (rs.next())
		{
			data[9] = rs.getString("tpNo");
		}
		
		ps = facade.prepareStatement(
				"SELECT SUM(ROUND(tp.royaltyAmount + tp.cessAmount, 2)) AS totalRoyaltyAndCess "
				+ "FROM License l LEFT JOIN TransferPass tp ON tp.LicenseID = l.LicenseID "
				+ "WHERE tp.Status != 'I' AND l.LicenseID = ?");

		ps.setLong(1, licenseID);

		rs = ps.executeQuery();

		while (rs.next())
		{
			data[10] = (new BigDecimal(data[7]).add(rs.getBigDecimal("totalRoyaltyAndCess")).setScale(2, RoundingMode.HALF_UP)).toString();
		}

		return data;
	}

	ArrayList<String[]> getLaporanPengeluaranKayuBalakDiBalai(Date startDate,
			Date endDate, long licenseID) throws SQLException
	{
		ArrayList<String[]> data = new ArrayList<String[]>();
		PreparedStatement ps = facade.prepareStatement(
				"SELECT MONTH(z.Tarikh) AS Bulan, YEAR(z.Tarikh) AS Tahun, SUM(z.KaumDamar) AS KaumDamar, SUM(z.BukanKaumDamar) AS BukanKaumDamar, SUM(z.Chengal) AS Chengal, SUM(z.Royalti) AS Royalti, SUM(z.Ses) AS Ses, SUM(z.RoyaltiJaras) AS RoyaltiJaras, SUM(z.SesJaras) AS SesJaras, SUM(z.JumlahBesarRoyaltiDanSes) AS JumlahBesarRoyaltiDanSes, SUM(z.JarasBesar) AS JarasBesar, SUM(z.JarasKecil20to30) AS JarasKecil20to30, SUM(z.JarasKecil10to20LengthGT2) AS JarasKecil10to20LengthGT2, SUM(z.JarasKecil10to20LengthLT2) AS JarasKecil10to20LengthLT2, SUM(z.JarasKecil5to10LT2) AS JarasKecil5to10LT2 FROM "
						+ "("
						+ "SELECT tp.Date AS Tarikh, IF(st.SpeciesTypeID != 3 AND s.Code != \"1201\", ROUND(PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length, 2), 0) AS KaumDamar, IF(st.SpeciesTypeID = 3, ROUND(PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length, 2), 0) AS BukanKaumDamar, IF(s.Code = \"1201\" , ROUND(PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length, 2), 0) AS Chengal, mrtpr.Royalty AS Royalti, mrtpr.Cess AS Ses, 0 AS RoyaltiJaras, 0 AS SesJaras, (mrtpr.Royalty+mrtpr.Cess) AS JumlahBesarRoyaltiDanSes, 0 AS JarasBesar, 0 AS JarasKecil20to30, 0 AS JarasKecil10to20LengthGT2, 0 AS JarasKecil10to20LengthLT2, 0 AS JarasKecil5to10LT2 "
						+ "FROM TransferPass tp, License l, MainRevenueTransferPassRecord mrtpr, Log lg, TaggingRecord tr, Species s, SpeciesType st "
						+ "WHERE tp.LicenseID = l.LicenseID AND mrtpr.TransferPassID = tp.TransferPassID AND mrtpr.LogID = lg.LogID AND lg.TaggingRecordID = tr.TaggingRecordID AND tr.CorrectedSpeciesID = s.SpeciesID AND s.SpeciesTypeID = st.SpeciesTypeID AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' AND l.LicenseID = ? "
						+ "UNION ALL "
						+ "SELECT tp.Date AS Tarikh, IF(st.SpeciesTypeID != 3 AND s.Code != \"1201\", ROUND(PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length, 2), 0) AS KaumDamar, IF(st.SpeciesTypeID = 3, ROUND(PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length, 2), 0) AS BukanKaumDamar, IF(s.Code = \"1201\" , ROUND(PI()*((stpr.Diameter)/100/2)*((stpr.Diameter)/100/2)*stpr.Length, 2), 0) AS Chengal, stpr.Royalty AS Royalti, stpr.Cess AS Ses, 0 AS RoyaltiJaras, 0 AS SesJaras, (stpr.Royalty+stpr.Cess) AS JumlahBesarRoyaltiDanSes, 0 AS JarasBesar, 0 AS JarasKecil20to30, 0 AS JarasKecil10to20LengthGT2, 0 AS JarasKecil10to20LengthLT2, 0 AS JarasKecil5to10LT2 "
						+ "FROM TransferPass tp, License l, SpecialTransferPassRecord stpr, Species s, SpeciesType st "
						+ "WHERE tp.LicenseID = l.LicenseID AND stpr.TransferPassID = tp.TransferPassID AND stpr.SpeciesID = s.SpeciesID AND s.SpeciesTypeID = st.SpeciesTypeID AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' AND l.LicenseID = ? "
						+ "UNION ALL "
						+ "SELECT tp.Date AS Tarikh, 0 AS KaumDamar, 0 AS BukanKaumDamar, 0 AS Chengal, 0 AS Royalti, 0 AS Ses, sptpr.Royalty AS RoyaltiJaras, sptpr.Cess AS SesJaras, 0 AS JumlahBesarRoyaltiDanSes, IF(SUBSTRING(sp.Code, 1, 2) = \"JB\", sptpr.Quantity, 0) AS JarasBesar, IF(SUBSTRING(sp.Code, 1, 4) = \"JK01\", sptpr.Quantity, 0) AS JarasKecil20to30, IF(SUBSTRING(sp.Code, 1, 5) = \"JK02B\" OR SUBSTRING(sp.Code, 1, 5) = \"JK02C\", sptpr.Quantity, 0) AS JarasKecil10to20LengthGT2, IF(SUBSTRING(sp.Code, 1, 5) = \"JK02A\", sptpr.Quantity, 0) AS JarasKecil10to20LengthLT2, IF(SUBSTRING(sp.Code, 1, 4) = \"JK03\", sptpr.Quantity, 0) AS JarasKecil5to10LT2 "
						+ "FROM TransferPass tp, License l, SmallProductTransferPassRecord sptpr, SmallProduct sp, Unit u  "
						+ "WHERE tp.LicenseID = l.LicenseID AND sptpr.TransferPassID = tp.TransferPassID AND sptpr.SmallProductID = sp.SmallProductID AND sptpr.UnitID = u.UnitID AND u.UnitID = 1 AND tp.Date BETWEEN ? AND ? AND tp.Status != 'I' AND l.LicenseID = ? "
						+ ") AS z GROUP BY MONTH(z.Tarikh), YEAR(z.Tarikh)");

		for (int i = 0; i < 3; i++)
		{
			ps.setDate(i * 3 + 1, toSQLDate(startDate));
			ps.setDate(i * 3 + 2, toSQLDate(endDate));
			ps.setLong(i * 3 + 3, licenseID);
		}

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			String[] rowData = new String[14];
			rowData[0] = (new DateFormatSymbols(new Locale("ms"))
					.getMonths()[rs.getInt("Bulan") - 1]) + " "
					+ String.valueOf(rs.getInt("Tahun"));
			rowData[1] = rs.getBigDecimal("KaumDamar")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[2] = rs.getBigDecimal("BukanKaumDamar")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[3] = rs.getBigDecimal("Chengal")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[4] = rs.getBigDecimal("Royalti")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[5] = rs.getBigDecimal("Ses")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[6] = rs.getBigDecimal("RoyaltiJaras")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[7] = rs.getBigDecimal("SesJaras")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[8] = rs.getBigDecimal("JumlahBesarRoyaltiDanSes")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[9] = rs.getBigDecimal("JarasBesar")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[10] = rs.getBigDecimal("JarasKecil20to30")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[11] = rs.getBigDecimal("JarasKecil10to20LengthGT2")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[12] = rs.getBigDecimal("JarasKecil10to20LengthLT2")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			rowData[13] = rs.getBigDecimal("JarasKecil5to10LT2")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

			data.add(rowData);
		}

		return data;
	}

	String[] getHeaderRingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
			long licenseID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT l.LicenseNo, lc.CompanyName, h.Name AS HallName "
						+ "FROM License l LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN Hall h ON l.HallID = h.HallID "
						+ "WHERE l.LicenseID = ?");

		ps.setLong(1, licenseID);

		ResultSet rs = ps.executeQuery();

		String[] header = new String[3];
		while (rs.next())
		{
			header[0] = rs.getString("LicenseNo");
			header[1] = rs.getString("CompanyName");
			header[2] = rs.getString("HallName");
		}

		return header;
	}

	ArrayList<String[]> getContentRingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
			Date startDate, Date endDate, long licenseID) throws SQLException
	{
		/*
		 * System.out.println(
		 * "SELECT z.SpeciesCode, z.SpeciesName, z.Royalty, z.Cess, z.Total, z.Volume "
		 * + "FROM	" + "(" +
		 * "SELECT s.SpeciesID, s.Code AS SpeciesCode, s.Name AS SpeciesName, SUM(ROUND(mrtpr.Royalty, 2)) AS Royalty, SUM(ROUND(mrtpr.Cess, 2)) AS Cess, (SUM(ROUND(mrtpr.Royalty, 2)) + SUM(ROUND(mrtpr.Cess, 2))) AS Total, SUM(ROUND((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 2)) AS Volume "
		 * +
		 * "FROM TransferPass tp LEFT JOIN License l ON tp.LicenseID = l.LicenseID LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN MainRevenueTransferPassRecord mrtpr ON mrtpr.TransferPassID = tp.TransferPassID LEFT JOIN Log lg ON mrtpr.LogID = lg.LogID LEFT JOIN TaggingRecord tr ON lg.TaggingRecordID = tr.TaggingRecordID LEFT JOIN Species s ON tr.CorrectedSpeciesID = s.SpeciesID "
		 * + "WHERE tp.Date BETWEEN DATE_ADD(DATE_ADD(LAST_DAY(" + toSQLDate(startDate)
		 * + "), INTERVAL 1 DAY), INTERVAL -1 MONTH) AND LAST_DAY(" + toSQLDate(endDate)
		 * + ") AND l.LicenseID = " + licenseID + " AND tp.Status != 'I' " +
		 * "GROUP BY s.SpeciesID	" + "UNION ALL " +
		 * "SELECT s.SpeciesID, s.Code AS SpeciesCode, s.Name AS SpeciesName, SUM(ROUND(stpr.Royalty, 2)) AS Royalty, SUM(ROUND(stpr.Cess, 2)) AS Cess, (SUM(ROUND(stpr.Royalty, 2)) + SUM(ROUND(stpr.Cess, 2))) AS Total, SUM(ROUND((PI()*(stpr.Diameter/100/2)*(stpr.Diameter/100/2)*stpr.Length), 2)) AS Volume	"
		 * +
		 * "FROM TransferPass tp LEFT JOIN License l ON tp.LicenseID = l.LicenseID LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN SpecialTransferPassRecord stpr ON stpr.TransferPassID = tp.TransferPassID LEFT JOIN Species s ON stpr.SpeciesID = s.SpeciesID "
		 * + "WHERE tp.Date BETWEEN DATE_ADD(DATE_ADD(LAST_DAY(" + toSQLDate(startDate)
		 * + "), INTERVAL 1 DAY), INTERVAL -1 MONTH) AND LAST_DAY(" + toSQLDate(endDate)
		 * + ") AND l.LicenseID = " + licenseID + " AND tp.Status != 'I' " +
		 * "GROUP BY s.SpeciesID" + ") AS z " + "GROUP BY z.SpeciesID");
		 */
		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.SpeciesCode, z.SpeciesName, z.Royalty, z.Cess, z.Total, z.Volume "
						+ "FROM	" + "("
						+ "SELECT s.SpeciesID, s.Code AS SpeciesCode, s.Name AS SpeciesName, SUM(ROUND(mrtpr.Royalty, 2)) AS Royalty, SUM(ROUND(mrtpr.Cess, 2)) AS Cess, (SUM(ROUND(mrtpr.Royalty, 2)) + SUM(ROUND(mrtpr.Cess, 2))) AS Total, SUM(ROUND((PI()*((lg.Diameter-lg.HoleDiameter)/100/2)*((lg.Diameter-lg.HoleDiameter)/100/2)*lg.Length), 2)) AS Volume "
						+ "FROM TransferPass tp LEFT JOIN License l ON tp.LicenseID = l.LicenseID LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN MainRevenueTransferPassRecord mrtpr ON mrtpr.TransferPassID = tp.TransferPassID LEFT JOIN Log lg ON mrtpr.LogID = lg.LogID LEFT JOIN TaggingRecord tr ON lg.TaggingRecordID = tr.TaggingRecordID LEFT JOIN Species s ON tr.CorrectedSpeciesID = s.SpeciesID "
						+ "WHERE tp.Date BETWEEN DATE_ADD(DATE_ADD(LAST_DAY(?), INTERVAL 1 DAY), INTERVAL -1 MONTH) AND LAST_DAY(?) AND l.LicenseID = ? AND tp.Status != 'I' "
						+ "GROUP BY s.SpeciesID	" + "UNION ALL "
						+ "SELECT s.SpeciesID, s.Code AS SpeciesCode, s.Name AS SpeciesName, SUM(ROUND(stpr.Royalty, 2)) AS Royalty, SUM(ROUND(stpr.Cess, 2)) AS Cess, (SUM(ROUND(stpr.Royalty, 2)) + SUM(ROUND(stpr.Cess, 2))) AS Total, SUM(ROUND((PI()*(stpr.Diameter/100/2)*(stpr.Diameter/100/2)*stpr.Length), 2)) AS Volume	"
						+ "FROM TransferPass tp LEFT JOIN License l ON tp.LicenseID = l.LicenseID LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN SpecialTransferPassRecord stpr ON stpr.TransferPassID = tp.TransferPassID LEFT JOIN Species s ON stpr.SpeciesID = s.SpeciesID "
						+ "WHERE tp.Date BETWEEN DATE_ADD(DATE_ADD(LAST_DAY(?), INTERVAL 1 DAY), INTERVAL -1 MONTH) AND LAST_DAY(?) AND l.LicenseID = ? AND tp.Status != 'I' "
						+ "GROUP BY s.SpeciesID" + ") AS z "
						+ "GROUP BY z.SpeciesID");

		for (int i = 0; i < 2; i++)
		{
			ps.setDate(i * 3 + 1, toSQLDate(startDate));
			ps.setDate(i * 3 + 2, toSQLDate(endDate));
			ps.setLong(i * 3 + 3, licenseID);
		}

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> contents = new ArrayList<String[]>();

		while (rs.next())
		{
			if(rs.getString("SpeciesCode") != null) 
			{
			String[] content = new String[6];
			content[0] = rs.getString("SpeciesCode");
			content[1] = rs.getString("SpeciesName");
			content[2] = String.format("%,.2f", rs.getBigDecimal("Royalty")
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			content[3] = String.format("%,.2f", rs.getBigDecimal("Cess")
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			content[4] = String.format("%,.2f", rs.getBigDecimal("Total")
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			content[5] = String.format("%,.2f", rs.getBigDecimal("Volume")
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			contents.add(content);
		}
		}

		return contents;
	}
}