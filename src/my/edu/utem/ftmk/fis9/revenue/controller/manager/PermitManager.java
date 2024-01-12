package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.model.Permit;

/**
 * @author Nor Azman Mat Ariff
 */
class PermitManager extends RevenueTableManager
{
	PermitManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(Permit permit, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, permit.getReceiptID());
		ps.setInt(2, permit.getPermitTypeID());
		ps.setString(3, permit.getPermitNo());
		ps.setString(4, permit.getFileReferencePHN());
		ps.setString(5, permit.getFileReferencePHD());
		nullable(ps, 6, permit.getLicenseID());
		ps.setInt(7, permit.getForestID());
		ps.setString(8, permit.getCompartmentNo());
		ps.setDate(9, toSQLDate(permit.getStartDate()));
		ps.setDate(10, toSQLDate(permit.getEndDate()));
		ps.setDate(11, toSQLDate(permit.getRegistrationDate()));
		ps.setString(12, permit.getReferenceNo());
		ps.setString(13, permit.getRecorderID());
		ps.setTimestamp(14, permit.getRecordTime());
		ps.setBigDecimal(15, permit.getPermitFund());
		ps.setString(16, permit.getStatus());
		ps.setLong(17, permit.getPermitID());

		return ps.executeUpdate();
	}

	int addPermit(Permit permit) throws SQLException
	{
		int status = 0;
		if (checkExistingPermitNo(permit) == false)
		{
			PreparedStatement ps = facade.prepareStatement(
					"INSERT INTO Permit (ReceiptID, PermitTypeID, PermitNo, FileReferencePHN, FileReferencePHD, LicenseID, ForestID, CompartmentNo, StartDate, EndDate, RegistrationDate, "
							+ "ReferenceNo, RecorderID, RecordTime, PermitFund, Status, PermitID) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, ?, ?)");

			status = write(permit, ps);
		}

		return status;
	}

	private boolean checkExistingPermitNo(Permit permit) throws SQLException
	{
		boolean exist = false;
		PreparedStatement ps = facade.prepareStatement(
				"SELECT * FROM Permit WHERE PermitID != ? AND PermitNo = ? AND Status = 'A'");
		ps.setLong(1, permit.getPermitID());
		ps.setString(2, permit.getPermitNo());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			exist = true;
		}

		return exist;
	}

	int updatePermit(Permit permit) throws SQLException
	{
		int status = 0;
		if (checkExistingPermitNo(permit) == false)
		{
			PreparedStatement ps = facade.prepareStatement(
					"UPDATE Permit SET ReceiptID = ?, PermitTypeID = ?, PermitNo = ?, FileReferencePHN = ?, FileReferencePHD = ?, LicenseID = ?, ForestID = ?, CompartmentNo = ?, StartDate = ?, "
							+ "EndDate = ?, RegistrationDate = ?, ReferenceNo = ?, RecorderID = ?, RecordTime = ?, PermitFund = ?, Status = ? WHERE PermitID = ?");

			status = write(permit, ps);
		}

		return status;
	}

	int updatePermitStatus(Permit permit, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Permit SET Status = ? WHERE PermitID = ?");
		ps.setString(1, status);
		ps.setLong(2, permit.getPermitID());

		return ps.executeUpdate();
	}

	int addPermitFund(BigDecimal value, long permitID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Permit SET PermitFund = ROUND(PermitFund + ?, 2) WHERE PermitID = ?");
		ps.setBigDecimal(1, value);
		ps.setLong(2, permitID);

		return ps.executeUpdate();
	}

	int subtractPermitFund(BigDecimal value, long permitID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Permit SET PermitFund = ROUND(PermitFund - ?, 2) WHERE PermitID = ?");
		ps.setBigDecimal(1, value);
		ps.setLong(2, permitID);

		return ps.executeUpdate();
	}

	int deletePermit(Permit permit) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Permit SET Status = 'I' WHERE PermitID = ?");
		ps.setLong(1, permit.getPermitID());

		return ps.executeUpdate();
	}

	int closePermit(Permit permit) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Permit SET PermitFund = ?, Status = ? WHERE PermitID = ?");
		ps.setBigDecimal(1, permit.getPermitFund());
		ps.setString(2, permit.getStatus());
		ps.setLong(3, permit.getPermitID());

		return ps.executeUpdate();
	}

	private Permit readPermit(ResultSet rs) throws SQLException
	{
		Permit permit = new Permit();

		permit.setPermitID(rs.getLong("PermitID"));
		permit.setReceiptID(rs.getLong("ReceiptID"));
		permit.setReceiptNo(rs.getString("ReceiptNo"));
		permit.setPermitTypeID(rs.getInt("PermitTypeID"));
		permit.setPermitTypeCode(rs.getString("PermitTypeCode"));
		permit.setPermitTypeName(rs.getString("PermitTypeName"));
		permit.setPermitNo(rs.getString("PermitNo"));
		permit.setFileReferencePHN(rs.getString("FileReferencePHN"));
		permit.setFileReferencePHD(rs.getString("FileReferencePHD"));
		permit.setLicenseID(rs.getLong("LicenseID"));
		permit.setLicenseNo(rs.getString("LicenseNo"));
		permit.setLicenseeID(rs.getLong("LicenseeID"));
		permit.setLicenseeNo(rs.getString("LicenseeNo"));
		permit.setLicenseeName(rs.getString("LicenseeName"));
		permit.setLicenseeCompanyName(rs.getString("LicenseeCompanyName"));
		permit.setContractorID(rs.getLong("ContractorID"));
		permit.setContractorNo(rs.getString("ContractorNo"));
		permit.setContractorName(rs.getString("ContractorName"));
		permit.setContractorCompanyName(rs.getString("ContractorCompanyName"));
		permit.setForestID(rs.getInt("ForestID"));
		permit.setForestCode(rs.getString("ForestCode"));
		permit.setForestName(rs.getString("ForestName"));
		permit.setDistrictID(rs.getInt("DistrictID"));
		permit.setDistrictCode(rs.getString("DistrictCode"));
		permit.setDistrictName(rs.getString("DistrictName"));
		permit.setStateID(rs.getInt("StateID"));
		permit.setStateCode(rs.getString("StateCode"));
		permit.setStateName(rs.getString("StateName"));
		permit.setCompartmentNo(rs.getString("CompartmentNo"));
		permit.setStartDate(rs.getDate("StartDate"));
		permit.setEndDate(rs.getDate("EndDate"));
		permit.setRegistrationDate(rs.getDate("RegistrationDate"));
		permit.setPermitTypeID(rs.getInt("PermitTypeID"));
		permit.setPermitTypeCode(rs.getString("PermitTypeCode"));
		permit.setPermitTypeName(rs.getString("PermitTypeName"));
		permit.setReferenceNo(rs.getString("ReferenceNo"));
		permit.setRecorderID(rs.getString("RecorderID"));
		permit.setRecorderName(rs.getString("RecorderName"));
		permit.setRecordTime(rs.getTimestamp("RecordTime"));
		permit.setRenews(facade.getRenews(permit, "A"));
		permit.setPermitFund(rs.getBigDecimal("PermitFund"));
		permit.setStatus(rs.getString("Status"));

		return permit;
	}

	private ArrayList<Permit> getPermits(ResultSet rs) throws SQLException
	{
		ArrayList<Permit> permits = new ArrayList<>();

		while (rs.next())
		{
			Permit permit = readPermit(rs);
			permits.add(permit);
		}
		return permits;
	}

	Permit getPermit(long permitID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT p.*, r.receiptNo, pt.Code AS PermitTypeCode, pt.Name AS PermitTypeName, l.LicenseNo, lc.LoggingContractorID AS LicenseeID, lc.RegistrationSerialNo AS LicenseeNo, lc.Name AS LicenseeName, lc.CompanyName AS LicenseeCompanyName, c.LoggingContractorID AS contractorID, c.RegistrationSerialNo AS ContractorNo, c.Name AS ContractorName, c.CompanyName AS ContractorCompanyName, f.Code AS ForestCode, f.Name AS ForestName, d.DistrictID, d.Code AS DistrictCode, d.Name AS DistrictName, stt.StateID, stt.Code AS StateCode, stt.Name AS StateName, s.Name AS RecorderName "
						+ "FROM Permit p JOIN Receipt r JOIN PermitType pt JOIN Forest f JOIN District d JOIN State stt JOIN Staff s LEFT JOIN License l ON p.LicenseID = l.LicenseID LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN LoggingContractor c ON l.ContractorID = c.LoggingContractorID "
						+ "WHERE p.ReceiptID = r.ReceiptID AND p.PermitTypeID = pt.PermitTypeID AND p.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = stt.StateID AND p.RecorderID = s.StaffID "
						+ "AND p.PermitID = ?");

		ps.setLong(1, permitID);

		Permit permit = null;
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			permit = readPermit(rs);
		}

		return permit;
	}

	ArrayList<Permit> getPermits(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT p.*, r.receiptNo, pt.Code AS PermitTypeCode, pt.Name AS PermitTypeName, l.LicenseNo, lc.LoggingContractorID AS LicenseeID, lc.RegistrationSerialNo AS LicenseeNo, lc.Name AS LicenseeName, lc.CompanyName AS LicenseeCompanyName, c.LoggingContractorID AS contractorID, c.RegistrationSerialNo AS ContractorNo, c.Name AS ContractorName, c.CompanyName AS ContractorCompanyName, f.Code AS ForestCode, f.Name AS ForestName, d.DistrictID, d.Code AS DistrictCode, d.Name AS DistrictName, stt.StateID, stt.Code AS StateCode, stt.Name AS StateName, s.Name AS RecorderName "
						+ "FROM Permit p JOIN Receipt r JOIN PermitType pt JOIN Forest f JOIN District d JOIN State stt JOIN Staff s LEFT JOIN License l ON p.LicenseID = l.LicenseID LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN LoggingContractor c ON l.ContractorID = c.LoggingContractorID "
						+ "WHERE p.ReceiptID = r.ReceiptID AND p.PermitTypeID = pt.PermitTypeID AND p.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = stt.StateID AND p.RecorderID = s.StaffID "
						+ "AND p.Status = ? "
						+ "ORDER BY p.PermitNo");

		ps.setString(1, status);

		return getPermits(ps.executeQuery());
	}

	ArrayList<Permit> getPermits(District district, String status)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT p.*, r.receiptNo, pt.Code AS PermitTypeCode, pt.Name AS PermitTypeName, l.LicenseNo, lc.LoggingContractorID AS LicenseeID, lc.RegistrationSerialNo AS LicenseeNo, lc.Name AS LicenseeName, lc.CompanyName AS LicenseeCompanyName, c.LoggingContractorID AS contractorID, c.RegistrationSerialNo AS ContractorNo, c.Name AS ContractorName, c.CompanyName AS ContractorCompanyName, f.Code AS ForestCode, f.Name AS ForestName, d.DistrictID, d.Code AS DistrictCode, d.Name AS DistrictName, stt.StateID, stt.Code AS StateCode, stt.Name AS StateName, s.Name AS RecorderName "
						+ "FROM Permit p JOIN Receipt r JOIN PermitType pt JOIN Forest f JOIN District d JOIN State stt JOIN Staff s LEFT JOIN License l ON p.LicenseID = l.LicenseID LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN LoggingContractor c ON l.ContractorID = c.LoggingContractorID "
						+ "WHERE p.ReceiptID = r.ReceiptID AND p.PermitTypeID = pt.PermitTypeID AND p.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = stt.StateID AND p.RecorderID = s.StaffID "
						+ "AND d.DistrictID = ? AND p.Status = ? "
						+ "ORDER BY p.PermitNo");

		ps.setInt(1, district.getDistrictID());
		ps.setString(2, status);

		return getPermits(ps.executeQuery());
	}

	ArrayList<Permit> getPermits(State state, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT p.*, r.receiptNo, pt.Code AS PermitTypeCode, pt.Name AS PermitTypeName, l.LicenseNo, lc.LoggingContractorID AS LicenseeID, lc.RegistrationSerialNo AS LicenseeNo, lc.Name AS LicenseeName, lc.CompanyName AS LicenseeCompanyName, c.LoggingContractorID AS contractorID, c.RegistrationSerialNo AS ContractorNo, c.Name AS ContractorName, c.CompanyName AS ContractorCompanyName, f.Code AS ForestCode, f.Name AS ForestName, d.DistrictID, d.Code AS DistrictCode, d.Name AS DistrictName, stt.StateID, stt.Code AS StateCode, stt.Name AS StateName, s.Name AS RecorderName "
						+ "FROM Permit p JOIN Receipt r JOIN PermitType pt JOIN Forest f JOIN District d JOIN State stt JOIN Staff s LEFT JOIN License l ON p.LicenseID = l.LicenseID LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN LoggingContractor c ON l.ContractorID = c.LoggingContractorID "
						+ "WHERE p.ReceiptID = r.ReceiptID AND p.PermitTypeID = pt.PermitTypeID AND p.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = stt.StateID AND p.RecorderID = s.StaffID "
						+ "AND stt.StateID = ? AND p.Status = ? "
						+ "ORDER BY p.PermitNo");

		ps.setInt(1, state.getStateID());
		ps.setString(2, status);

		return getPermits(ps.executeQuery());
	}
	
	ArrayList<Permit> getPermits(Staff user, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT p.*, r.receiptNo, pt.Code AS PermitTypeCode, pt.Name AS PermitTypeName, l.LicenseNo, lc.LoggingContractorID AS LicenseeID, lc.RegistrationSerialNo AS LicenseeNo, lc.Name AS LicenseeName, lc.CompanyName AS LicenseeCompanyName, c.LoggingContractorID AS contractorID, c.RegistrationSerialNo AS ContractorNo, c.Name AS ContractorName, c.CompanyName AS ContractorCompanyName, f.Code AS ForestCode, f.Name AS ForestName, d.DistrictID, d.Code AS DistrictCode, d.Name AS DistrictName, stt.StateID, stt.Code AS StateCode, stt.Name AS StateName, s.Name AS RecorderName "
						+ "FROM Permit p JOIN Receipt r JOIN PermitType pt JOIN Forest f JOIN District d JOIN State stt JOIN Staff s LEFT JOIN License l ON p.LicenseID = l.LicenseID LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID LEFT JOIN LoggingContractor c ON l.ContractorID = c.LoggingContractorID "
						+ "WHERE p.ReceiptID = r.ReceiptID AND p.PermitTypeID = pt.PermitTypeID AND p.ForestID = f.ForestID AND f.DistrictID = d.DistrictID AND d.StateID = stt.StateID AND p.RecorderID = s.StaffID "
						+ "AND s.StaffID = ? AND p.Status = ? "
						+ "ORDER BY p.PermitNo");

		ps.setString(1, user.getStaffID());
		ps.setString(2, status);

		return getPermits(ps.executeQuery());
	}
}