package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.model.License;

/**
 * @author Nor Azman Mat Ariff
 */
class LicenseManager extends RevenueTableManager
{
	LicenseManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(License license, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, license.getReceiptID());
		ps.setString(2, license.getLicenseNo());
		ps.setString(3, license.getFileReferencePHN());
		ps.setString(4, license.getFileReferencePHD());
		nullable(ps, 5, license.getLicenseeID());
		nullable(ps, 6, license.getContractorID());
		ps.setString(7, license.getAddress());
		ps.setInt(8, license.getLicenseTypeID());
		ps.setDate(9, toSQLDate(license.getStartDate()));
		ps.setDate(10, toSQLDate(license.getEndDate()));
		ps.setDate(11, toSQLDate(license.getRegistrationDate()));
		ps.setString(12, license.getFileNo());
		ps.setInt(13, license.getForestCategoryID());
		nullable(ps, 14, license.getForestID());
		ps.setString(15, license.getCompartmentNo());
		ps.setBigDecimal(16, license.getCompartmentArea());
		ps.setLong(17, license.getHallID());
		ps.setLong(18, license.getHallOfficerID());
		ps.setBigDecimal(19, license.getWoodWorkFund());
		ps.setBigDecimal(20, license.getLicenseFund());
		ps.setBigDecimal(21, license.getResinLimit());
		ps.setBigDecimal(22, license.getNonResinLimit());
		ps.setBigDecimal(23, license.getChengalLimit());
		ps.setBigDecimal(24, license.getLogLimit());
		ps.setBigDecimal(25, license.getJarasLimit());
		ps.setBigDecimal(26, license.getWasteWoodLimit());
		ps.setString(27, license.getRecorderID());
		ps.setTimestamp(28, license.getRecordTime());
		ps.setString(29, license.getStatus());
		ps.setLong(30, license.getLicenseID());

		return ps.executeUpdate();
	}

	int addLicense(License license, boolean ignoreDuplicate) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement((ignoreDuplicate
				? "INSERT IGNORE"
				: "INSERT")
				+ " INTO License (ReceiptID, LicenseNo, FileReferencePHN, FileReferencePHD, LicenseeID, ContractorID, Address, LicenseTypeID, startDate, EndDate, "
				+ "RegistrationDate, FileNo, ForestCategoryID, ForestID, CompartmentNo, CompartmentArea, HallID, HallOfficerID, WoodWorkFund, LicenseFund, "
				+ "ResinLimit, NonResinLimit, ChengalLimit, LogLimit, JarasLimit, WasteWoodLimit, RecorderID, RecordTime, Status, LicenseID) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
				+ (ignoreDuplicate ? ""
						: " ON DUPLICATE KEY UPDATE ReceiptID = ?, LicenseNo = ?, FileReferencePHN = ?, FileReferencePHD = ?, LicenseeID = ?, ContractorID = ?, Address = ?, LicenseTypeID = ?, startDate = ?, EndDate = ?, "
								+ "RegistrationDate = ?, FileNo = ?, ForestCategoryID = ?, ForestID = ?, CompartmentNo = ?, CompartmentArea = ?, HallID = ?, HallOfficerID = ?, WoodWorkFund = ?, LicenseFund = ?,"
								+ "ResinLimit = ?, NonResinLimit = ?, ChengalLimit = ?, LogLimit = ?, JarasLimit = ?, WasteWoodLimit = ?, RecorderID = ?, RecordTime = ?, Status = ?"));

		if (!ignoreDuplicate)
		{
			ps.setLong(31, license.getReceiptID());
			ps.setString(32, license.getLicenseNo());
			ps.setString(33, license.getFileReferencePHN());
			ps.setString(34, license.getFileReferencePHD());
			ps.setLong(35, license.getLicenseeID());
			nullable(ps, 36, license.getContractorID());
			ps.setString(37, license.getAddress());
			ps.setInt(38, license.getLicenseTypeID());
			ps.setDate(39, toSQLDate(license.getStartDate()));
			ps.setDate(40, toSQLDate(license.getEndDate()));
			ps.setDate(41, toSQLDate(license.getRegistrationDate()));
			ps.setString(42, license.getFileNo());
			ps.setInt(43, license.getForestCategoryID());
			nullable(ps, 44, license.getForestID());
			ps.setString(45, license.getCompartmentNo());
			ps.setBigDecimal(46, license.getCompartmentArea());
			ps.setLong(47, license.getHallID());
			ps.setLong(48, license.getHallOfficerID());
			ps.setBigDecimal(49, license.getWoodWorkFund());
			ps.setBigDecimal(50, license.getLicenseFund());
			ps.setBigDecimal(51, license.getResinLimit());
			ps.setBigDecimal(52, license.getNonResinLimit());
			ps.setBigDecimal(53, license.getChengalLimit());
			ps.setBigDecimal(54, license.getLogLimit());
			ps.setBigDecimal(55, license.getJarasLimit());
			ps.setBigDecimal(56, license.getWasteWoodLimit());
			ps.setString(57, license.getRecorderID());
			ps.setTimestamp(58, license.getRecordTime());
			ps.setString(59, license.getStatus());
		}

		int status = write(license, ps);

		if (status == 0 && !ignoreDuplicate)
			status = 1;

		return status;
	}

	int updateLicense(License license) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET ReceiptID = ?, LicenseNo = ?, FileReferencePHN = ?, FileReferencePHD = ?, LicenseeID = ?, ContractorID = ?, Address = ?, LicenseTypeID = ?, startDate = ?, EndDate = ?, "
						+ "RegistrationDate = ?, FileNo = ?, ForestCategoryID = ?, ForestID = ?, CompartmentNo = ?, CompartmentArea = ?, HallID = ?, HallOfficerID = ?, WoodWorkFund = ?, LicenseFund = ?, "
						+ "ResinLimit = ?, NonResinLimit = ?, ChengalLimit = ?, LogLimit = ?, JarasLimit = ?, WasteWoodLimit = ?, RecorderID = ?, RecordTime = ?, Status = ? WHERE LicenseID = ?");

		return write(license, ps);
	}

	int updateLicenseStatus(License license, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET Status = ? WHERE LicenseID = ?");
		ps.setString(1, status);
		ps.setLong(2, license.getLicenseID());

		return ps.executeUpdate();
	}

	int addWoodWorkFund(BigDecimal value, long licenseID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET WoodWorkFund = ROUND(WoodWorkFund + ?, 2) WHERE LicenseID = ?");
		ps.setBigDecimal(1, value);
		ps.setLong(2, licenseID);

		return ps.executeUpdate();
	}

	int addLicenseFund(BigDecimal value, long licenseID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET LicenseFund = ROUND(LicenseFund + ?, 2) WHERE LicenseID = ?");
		ps.setBigDecimal(1, value);
		ps.setLong(2, licenseID);

		return ps.executeUpdate();
	}

	int subtractWoodWorkFund(BigDecimal value, long licenseID)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET WoodWorkFund = ROUND(WoodWorkFund - ?, 2) WHERE LicenseID = ?");
		ps.setBigDecimal(1, value);
		ps.setLong(2, licenseID);

		return ps.executeUpdate();
	}

	int subtractLicenseFund(BigDecimal value, long licenseID)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET LicenseFund = ROUND(LicenseFund - ?, 2) WHERE LicenseID = ?");
		ps.setBigDecimal(1, value);
		ps.setLong(2, licenseID);

		return ps.executeUpdate();
	}

	int updateLimit(BigDecimal resinLimit, BigDecimal nonResinLimit,
			BigDecimal chengalLimit, License license) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET ResinLimit = ROUND(ResinLimit - ?, 2), NonResinLimit = ROUND(NonResinLimit - ?, 2), "
						+ "ChengalLimit = ROUND(ChengalLimit - ?, 2) WHERE LicenseID = ?");
		ps.setBigDecimal(1, resinLimit);
		ps.setBigDecimal(2, nonResinLimit);
		ps.setBigDecimal(3, chengalLimit);
		ps.setLong(4, license.getLicenseID());

		return ps.executeUpdate();
	}

	int subtractFundAndLimit(boolean updateResin, BigDecimal totalResin,
			boolean updateNonResin, BigDecimal totalNonResin,
			boolean updateChengal, BigDecimal totalChengal, boolean updateLog,
			BigDecimal totalLog, boolean updateJaras, BigDecimal totalJaras,
			BigDecimal royaltyAndCess, License license) throws SQLException
	{

		if (updateResin == false)
			totalResin = BigDecimal.ZERO;
		if (updateNonResin == false)
			totalNonResin = BigDecimal.ZERO;
		if (updateResin == false)
			totalChengal = BigDecimal.ZERO;
		if (updateLog == false)
			totalLog = BigDecimal.ZERO;
		if (updateJaras == false)
			totalJaras = BigDecimal.ZERO;

		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET ResinLimit = ROUND(ResinLimit - ?, 2), NonResinLimit = ROUND(NonResinLimit - ?, 2), "
						+ "ChengalLimit = ROUND(ChengalLimit - ?, 2), LogLimit = ROUND(LogLimit - ?, 2), JarasLimit = ROUND(JarasLimit - ?, 0), WoodWorkFund = ROUND(WoodWorkFund - ?, 2) WHERE LicenseID = ?");
		ps.setBigDecimal(1, totalResin);
		ps.setBigDecimal(2, totalNonResin);
		ps.setBigDecimal(3, totalChengal);
		ps.setBigDecimal(4, totalLog);
		ps.setBigDecimal(5, totalJaras);
		ps.setBigDecimal(6, royaltyAndCess);
		ps.setLong(7, license.getLicenseID());

		return ps.executeUpdate();
	}

	int addFundAndLimit(boolean updateResin, BigDecimal totalResin,
			boolean updateNonResin, BigDecimal totalNonResin,
			boolean updateChengal, BigDecimal totalChengal, boolean updateLog,
			BigDecimal totalLog, boolean updateJaras, BigDecimal totalJaras,
			BigDecimal royaltyAndCess, License license) throws SQLException
	{

		if (updateResin == false)
			totalResin = BigDecimal.ZERO;
		if (updateNonResin == false)
			totalNonResin = BigDecimal.ZERO;
		if (updateResin == false)
			totalChengal = BigDecimal.ZERO;
		if (updateLog == false)
			totalLog = BigDecimal.ZERO;
		if (updateJaras == false)
			totalJaras = BigDecimal.ZERO;

		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET ResinLimit = ROUND(ResinLimit + ?, 2), NonResinLimit = ROUND(NonResinLimit + ?, 2), "
						+ "ChengalLimit = ROUND(ChengalLimit + ?, 2), LogLimit = ROUND(LogLimit + ?, 2), JarasLimit = ROUND(JarasLimit + ?, 0), WoodWorkFund = ROUND(WoodWorkFund + ?, 2) WHERE LicenseID = ?");
		ps.setBigDecimal(1, totalResin);
		ps.setBigDecimal(2, totalNonResin);
		ps.setBigDecimal(3, totalChengal);
		ps.setBigDecimal(4, totalLog);
		ps.setBigDecimal(5, totalJaras);
		ps.setBigDecimal(6, royaltyAndCess);
		ps.setLong(7, license.getLicenseID());

		return ps.executeUpdate();
	}

	int closeLicense(License license) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET WoodWorkFund = ?, LicenseFund = ?, Status = ? WHERE LicenseID = ?");
		ps.setBigDecimal(1, license.getWoodWorkFund());
		ps.setBigDecimal(2, license.getLicenseFund());
		ps.setString(3, license.getStatus());
		ps.setLong(4, license.getLicenseID());

		return ps.executeUpdate();
	}

	int deleteLicense(License license) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE License SET Status = 'I' WHERE LicenseID = ?");
		ps.setLong(1, license.getLicenseID());

		return ps.executeUpdate();
	}

	private License readLicense(ResultSet rs) throws SQLException
	{
		License license = new License();

		license.setLicenseID(rs.getLong("LicenseID"));
		license.setReceiptID(rs.getLong("ReceiptID"));
		license.setReceiptNo(rs.getString("ReceiptNo"));
		license.setLicenseNo(rs.getString("LicenseNo"));
		license.setFileReferencePHN(rs.getString("FileReferencePHN"));
		license.setFileReferencePHD(rs.getString("FileReferencePHD"));
		license.setLicenseeID(rs.getLong("LicenseeID"));
		license.setLicenseeNo(rs.getString("LicenseeNo"));
		license.setLicenseeName(rs.getString("LicenseeName"));
		license.setLicenseeCompanyName(rs.getString("LicenseeCompanyName"));
		license.setContractorID(rs.getLong("ContractorID"));
		license.setContractorNo(rs.getString("ContractorNo"));
		license.setContractorName(rs.getString("ContractorName"));
		license.setContractorCompanyName(rs.getString("ContractorCompanyName"));
		license.setLicenseTypeID(rs.getInt("LicenseTypeID"));
		license.setLicenseTypeCode(rs.getString("LicenseTypeCode"));
		license.setLicenseTypeName(rs.getString("LicenseTypeName"));
		license.setAddress(rs.getString("Address"));
		license.setStartDate(rs.getDate("StartDate"));
		license.setEndDate(rs.getDate("EndDate"));
		license.setRegistrationDate(rs.getDate("RegistrationDate"));
		license.setFileNo(rs.getString("FileNo"));
		license.setForestCategoryID(rs.getInt("ForestCategoryID"));
		license.setForestCategoryCode(rs.getString("ForestCategoryCode"));
		license.setForestCategoryName(rs.getString("ForestCategoryName"));
		license.setCompartmentNo(rs.getString("CompartmentNo"));
		license.setCompartmentArea(rs.getBigDecimal("CompartmentArea"));
		license.setHallID(rs.getLong("HallID"));
		license.setHallName(rs.getString("HallName"));
		license.setDistrictID(rs.getInt("DistrictID"));
		license.setDistrictCode(rs.getString("DistrictCode"));
		license.setDistrictName(rs.getString("DistrictName"));
		license.setStateID(rs.getInt("StateID"));
		license.setStateCode(rs.getString("StateCode"));
		license.setStateName(rs.getString("StateName"));
		license.setForestID(rs.getInt("ForestID"));
		license.setForestCode(rs.getString("ForestCode"));
		license.setForestName(rs.getString("ForestName"));
		license.setHallOfficerID(rs.getLong("HallOfficerID"));
		license.setHallOfficerHammerNo(rs.getString("HammerNo"));
		license.setHallOfficerName(rs.getString("HallOfficerName"));
		license.setWoodWorkFund(rs.getBigDecimal("WoodWorkFund"));
		license.setLicenseFund(rs.getBigDecimal("LicenseFund"));
		license.setResinLimit(rs.getBigDecimal("ResinLimit"));
		license.setNonResinLimit(rs.getBigDecimal("NonResinLimit"));
		license.setChengalLimit(rs.getBigDecimal("ChengalLimit"));
		license.setLogLimit(rs.getBigDecimal("LogLimit"));
		license.setJarasLimit(rs.getBigDecimal("JarasLimit"));
		license.setWasteWoodLimit(rs.getBigDecimal("WasteWoodLimit"));
		license.setRecorderID(rs.getString("RecorderID"));
		license.setRecorderName(rs.getString("RecorderName"));
		license.setRecordTime(rs.getTimestamp("RecordTime"));
		license.setRenews(facade.getRenews(license, "A"));
		license.setStatus(rs.getString("Status"));

		return license;
	}

	private ArrayList<License> getLicenses(ResultSet rs) throws SQLException
	{
		ArrayList<License> licenses = new ArrayList<>();

		while (rs.next())
		{
			License license = readLicense(rs);
			licenses.add(license);
		}
		return licenses;
	}

	License getLicense(long licenseID) throws SQLException
	{
		License license = null;
		PreparedStatement ps = facade.prepareStatement(
				"SELECT l.*, r.ReceiptNo, lcl.RegistrationSerialNo AS LicenseeNo, lcl.Name AS LicenseeName, lcl.CompanyName AS LicenseeCompanyName, "
						+ "lcc.RegistrationSerialNo AS ContractorNo, lcc.Name AS ContractorName, lcc.CompanyName AS ContractorCompanyName, "
						+ "lt.Code AS LicenseTypeCode, lt.Name AS LicenseTypeName, fc.Code AS ForestCategoryCode, "
						+ "fc.Name AS ForestCategoryName, H.Name AS HallName, ho.HallOfficerName, ho.HammerNo, s.Name AS RecorderName, d.DistrictID, d.Code AS DistrictCode, d.Name AS DistrictName, st.StateID, st.Code AS StateCode, st.Name AS StateName, "
						+ "f.Code AS ForestCode, f.Name AS ForestName "
						+ "FROM License l JOIN Receipt r JOIN LicenseType lt JOIN ForestCategory fc JOIN Hall h JOIN District d "
						+ "JOIN State st JOIN (SELECT ho.*, s.Name AS HallOfficerName FROM HallOfficer ho, Staff s WHERE ho.StaffID = s.StaffID) AS ho "
						+ "JOIN Staff s LEFT JOIN (SELECT * FROM LoggingContractor WHERE Type = 'L') AS lcl ON l.LicenseeID = lcl.LoggingContractorID "
						+ "LEFT JOIN (SELECT * FROM LoggingContractor WHERE Type = 'C') AS lcc ON l.ContractorID = lcc.LoggingContractorID "
						+ "LEFT JOIN Forest f ON l.ForestID = f.ForestID "
						+ "WHERE l.ReceiptID = r.ReceiptID AND l.LicenseTypeID = lt.LicenseTypeID "
						+ "AND l.ForestCategoryID = fc.ForestCategoryID AND l.HallID = h.HallID AND h.DistrictID = d.DistrictID "
						+ "AND d.StateID = st.StateID AND l.HallOfficerID = ho.HallOfficerID AND l.RecorderID = s.StaffID "
						+ "AND l.LicenseID = ?");

		ps.setLong(1, licenseID);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			license = readLicense(rs);
		}

		return license;
	}

	ArrayList<License> getLicenses(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT l.*, r.ReceiptNo, lcl.RegistrationSerialNo AS LicenseeNo, lcl.Name AS LicenseeName, lcl.CompanyName AS LicenseeCompanyName, "
						+ "lcc.RegistrationSerialNo AS ContractorNo, lcc.Name AS ContractorName, lcc.CompanyName AS ContractorCompanyName, "
						+ "lt.Code AS LicenseTypeCode, lt.Name AS LicenseTypeName, fc.Code AS ForestCategoryCode, "
						+ "fc.Name AS ForestCategoryName, H.Name AS HallName, ho.HallOfficerName, ho.HammerNo, s.Name AS RecorderName, d.DistrictID, d.Code AS DistrictCode, d.Name AS DistrictName, st.StateID, st.Code AS StateCode, st.Name AS StateName, "
						+ "f.Code AS ForestCode, f.Name AS ForestName "
						+ "FROM License l JOIN Receipt r JOIN LicenseType lt JOIN ForestCategory fc JOIN Hall h JOIN District d "
						+ "JOIN State st JOIN (SELECT ho.*, s.Name AS HallOfficerName FROM HallOfficer ho, Staff s WHERE ho.StaffID = s.StaffID) AS ho "
						+ "JOIN Staff s LEFT JOIN (SELECT * FROM LoggingContractor WHERE Type = 'L') AS lcl ON l.LicenseeID = lcl.LoggingContractorID "
						+ "LEFT JOIN (SELECT * FROM LoggingContractor WHERE Type = 'C') AS lcc ON l.ContractorID = lcc.LoggingContractorID "
						+ "LEFT JOIN Forest f ON l.ForestID = f.ForestID "
						+ "WHERE l.ReceiptID = r.ReceiptID AND l.LicenseTypeID = lt.LicenseTypeID "
						+ "AND l.ForestCategoryID = fc.ForestCategoryID AND l.HallID = h.HallID AND h.DistrictID = d.DistrictID "
						+ "AND d.StateID = st.StateID AND l.HallOfficerID = ho.HallOfficerID AND l.RecorderID = s.StaffID "
						+ "AND l.Status = ? "
						+ "ORDER BY l.licenseNo");

		ps.setString(1, status);

		return getLicenses(ps.executeQuery());
	}

	ArrayList<License> getLicenses(District district, String status)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT l.*, r.ReceiptNo, lcl.RegistrationSerialNo AS LicenseeNo, lcl.Name AS LicenseeName, lcl.CompanyName AS LicenseeCompanyName, "
						+ "lcc.RegistrationSerialNo AS ContractorNo, lcc.Name AS ContractorName, lcc.CompanyName AS ContractorCompanyName, "
						+ "lt.Code AS LicenseTypeCode, lt.Name AS LicenseTypeName, fc.Code AS ForestCategoryCode, "
						+ "fc.Name AS ForestCategoryName, H.Name AS HallName, ho.HallOfficerName, ho.HammerNo, s.Name AS RecorderName, d.DistrictID, d.Code AS DistrictCode, d.Name AS DistrictName, st.StateID, st.Code AS StateCode, st.Name AS StateName, "
						+ "f.Code AS ForestCode, f.Name AS ForestName "
						+ "FROM License l JOIN Receipt r JOIN LicenseType lt JOIN ForestCategory fc JOIN Hall h JOIN District d "
						+ "JOIN State st JOIN (SELECT ho.*, s.Name AS HallOfficerName FROM HallOfficer ho, Staff s WHERE ho.StaffID = s.StaffID) AS ho "
						+ "JOIN Staff s LEFT JOIN (SELECT * FROM LoggingContractor WHERE Type = 'L') AS lcl ON l.LicenseeID = lcl.LoggingContractorID "
						+ "LEFT JOIN (SELECT * FROM LoggingContractor WHERE Type = 'C') AS lcc ON l.ContractorID = lcc.LoggingContractorID "
						+ "LEFT JOIN Forest f ON l.ForestID = f.ForestID "
						+ "WHERE l.ReceiptID = r.ReceiptID AND l.LicenseTypeID = lt.LicenseTypeID "
						+ "AND l.ForestCategoryID = fc.ForestCategoryID AND l.HallID = h.HallID AND h.DistrictID = d.DistrictID "
						+ "AND d.StateID = st.StateID AND l.HallOfficerID = ho.HallOfficerID AND l.RecorderID = s.StaffID "
						+ "AND d.DistrictID = ? AND l.Status = ? "
						+ "ORDER BY l.licenseNo");

		ps.setInt(1, district.getDistrictID());
		ps.setString(2, status);

		return getLicenses(ps.executeQuery());
	}

	ArrayList<License> getLicenses(State state, String status)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT l.*, r.ReceiptNo, lcl.RegistrationSerialNo AS LicenseeNo, lcl.Name AS LicenseeName, lcl.CompanyName AS LicenseeCompanyName, "
						+ "lcc.RegistrationSerialNo AS ContractorNo, lcc.Name AS ContractorName, lcc.CompanyName AS ContractorCompanyName, "
						+ "lt.Code AS LicenseTypeCode, lt.Name AS LicenseTypeName, fc.Code AS ForestCategoryCode, "
						+ "fc.Name AS ForestCategoryName, H.Name AS HallName, ho.HallOfficerName, ho.HammerNo, s.Name AS RecorderName, d.DistrictID, d.Code AS DistrictCode, d.Name AS DistrictName, st.StateID, st.Code AS StateCode, st.Name AS StateName, "
						+ "f.Code AS ForestCode, f.Name AS ForestName "
						+ "FROM License l JOIN Receipt r JOIN LicenseType lt JOIN ForestCategory fc JOIN Hall h JOIN District d "
						+ "JOIN State st JOIN (SELECT ho.*, s.Name AS HallOfficerName FROM HallOfficer ho, Staff s WHERE ho.StaffID = s.StaffID) AS ho "
						+ "JOIN Staff s LEFT JOIN (SELECT * FROM LoggingContractor WHERE Type = 'L') AS lcl ON l.LicenseeID = lcl.LoggingContractorID "
						+ "LEFT JOIN (SELECT * FROM LoggingContractor WHERE Type = 'C') AS lcc ON l.ContractorID = lcc.LoggingContractorID "
						+ "LEFT JOIN Forest f ON l.ForestID = f.ForestID "
						+ "WHERE l.ReceiptID = r.ReceiptID AND l.LicenseTypeID = lt.LicenseTypeID "
						+ "AND l.ForestCategoryID = fc.ForestCategoryID AND l.HallID = h.HallID AND h.DistrictID = d.DistrictID "
						+ "AND d.StateID = st.StateID AND l.HallOfficerID = ho.HallOfficerID AND l.RecorderID = s.StaffID "
						+ "AND st.StateID = ? AND l.Status = ? "
						+ "ORDER BY l.licenseNo");

		ps.setInt(1, state.getStateID());
		ps.setString(2, status);

		return getLicenses(ps.executeQuery());
	}
}