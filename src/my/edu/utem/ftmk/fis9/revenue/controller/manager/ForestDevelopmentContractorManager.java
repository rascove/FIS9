package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.model.ForestDevelopmentContractor;

/**
 * @author Nor Azman Mat Ariff
 */
class ForestDevelopmentContractorManager extends RevenueTableManager
{
	ForestDevelopmentContractorManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(ForestDevelopmentContractor forestDevelopmentContractor,
			PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, forestDevelopmentContractor.getReceiptID());
		ps.setString(2, forestDevelopmentContractor.getRegistrationNo());
		ps.setString(3, forestDevelopmentContractor.getName());
		ps.setString(4, forestDevelopmentContractor.getIcNo());
		ps.setString(5, forestDevelopmentContractor.getCompanyName());
		ps.setString(6, forestDevelopmentContractor.getAddress());
		ps.setString(7, forestDevelopmentContractor.getRegisteredAddress());
		ps.setString(8, forestDevelopmentContractor.getTelNo());
		ps.setString(9, forestDevelopmentContractor.getHpNo());
		ps.setString(10, forestDevelopmentContractor.getRegisteredBusinessNo());
		ps.setInt(11, forestDevelopmentContractor.getLicenseStatusID());
		ps.setDate(12, toSQLDate(forestDevelopmentContractor.getStartDate()));
		ps.setDate(13, toSQLDate(forestDevelopmentContractor.getEndDate()));
		ps.setDate(14,
				toSQLDate(forestDevelopmentContractor.getRegistrationDate()));
		ps.setString(15,
				forestDevelopmentContractor.getContractorServiceCenterTitle());
		ps.setString(16,
				forestDevelopmentContractor.getPkkRegistrationCertificateNo());
		ps.setString(17, forestDevelopmentContractor.getCidbRegistrationNo());
		ps.setInt(18, forestDevelopmentContractor.getManagementStaffNo());
		ps.setInt(19, forestDevelopmentContractor.getSupervisionStaffNo());
		ps.setInt(20, forestDevelopmentContractor.getSkilledStaffNo());
		ps.setInt(21, forestDevelopmentContractor.getUnSkilledStaffNo());
		ps.setInt(22, forestDevelopmentContractor.getOthersStaffNo());
		ps.setString(23, forestDevelopmentContractor.getMachineryDescription());
		ps.setString(24, forestDevelopmentContractor.getPreviousExperience());
		ps.setString(25, forestDevelopmentContractor.getRecorderID());
		ps.setTimestamp(26, forestDevelopmentContractor.getRecordTime());
		ps.setString(27, forestDevelopmentContractor.getStatus());
		ps.setInt(28, forestDevelopmentContractor.getStateID());
		ps.setLong(29,
				forestDevelopmentContractor.getForestDevelopmentContractorID());

		return ps.executeUpdate();
	}

	int addForestDevelopmentContractor(
			ForestDevelopmentContractor forestDevelopmentContractor)
			throws SQLException
	{
		int status = 0;
		if (checkExistingForestDevelopmentContractorNo(
				forestDevelopmentContractor) == false)
		{
			PreparedStatement ps = facade.prepareStatement(
					"INSERT INTO ForestDevelopmentContractor (ReceiptID, RegistrationNo, Name, ICNo, CompanyName, Address, RegisteredAddress, TelNo, HPNo, RegisteredBusinessNo, "
							+ "LicenseStatusID, StartDate, EndDate, RegistrationDate, ContractorServiceCenterTitle, PKKRegistrationCertificateNo, CIDBRegistrationNo, ManagementStaffNo, SupervisionStaffNo, SkilledStaffNo, "
							+ "UnskilledStaffNo, OthersStaffNo, MachineryDescription, PreviousExperience, RecorderID, RecordTime, Status, StateID, ForestDevelopmentContractorID) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, ?, ?, ?, ?)");

			status = write(forestDevelopmentContractor, ps);
		}

		return status;
	}

	private boolean checkExistingForestDevelopmentContractorNo(
			ForestDevelopmentContractor forestDevelopmentContractor)
			throws SQLException
	{
		boolean exist = false;
		PreparedStatement ps = facade.prepareStatement(
				"SELECT * FROM ForestDevelopmentContractor WHERE ForestDevelopmentContractorID = ? AND Status = 'A'");
		ps.setLong(1,
				forestDevelopmentContractor.getForestDevelopmentContractorID());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			exist = true;
		}

		return exist;
	}

	int updateForestDevelopmentContractor(
			ForestDevelopmentContractor forestDevelopmentContractor)
			throws SQLException
	{
		int status = 0;

		PreparedStatement ps = facade.prepareStatement(
				"UPDATE ForestDevelopmentContractor SET ReceiptID = ?, RegistrationNo = ?, Name = ?, ICNo = ?, CompanyName = ?, Address = ?, RegisteredAddress = ?, TelNo = ?, HPNo = ?, RegisteredBusinessNo = ?, "
						+ "LicenseStatusID = ?, StartDate = ?, EndDate = ?, RegistrationDate = ?, ContractorServiceCenterTitle = ?, PKKRegistrationCertificateNo = ?, CIDBRegistrationNo = ?, ManagementStaffNo = ?, SupervisionStaffNo = ?, SkilledStaffNo = ?, "
						+ "UnskilledStaffNo = ?, OthersStaffNo = ?, MachineryDescription = ?, PreviousExperience = ?, RecorderID = ?, RecordTime = ?, Status = ?, StateID = ? WHERE ForestDevelopmentContractorID = ?");

		status = write(forestDevelopmentContractor, ps);

		return status;
	}

	int updateForestDevelopmentContractorStatus(
			ForestDevelopmentContractor forestDevelopmentContractor)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE ForestDevelopmentContractor SET Status = ? WHERE ForestDevelopmentContractorID = ?");
		ps.setString(1, forestDevelopmentContractor.getStatus());
		ps.setLong(2,
				forestDevelopmentContractor.getForestDevelopmentContractorID());

		return ps.executeUpdate();
	}

	int deleteForestDevelopmentContractor(
			ForestDevelopmentContractor forestDevelopmentContractor)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE ForestDevelopmentContractor SET Status = 'I' WHERE ForestDevelopmentContractorID = ?");
		ps.setLong(1,
				forestDevelopmentContractor.getForestDevelopmentContractorID());

		return ps.executeUpdate();
	}

	private ForestDevelopmentContractor readForestDevelopmentContractor(
			ResultSet rs) throws SQLException
	{
		ForestDevelopmentContractor forestDevelopmentContractor = new ForestDevelopmentContractor();

		forestDevelopmentContractor.setForestDevelopmentContractorID(
				rs.getLong("ForestDevelopmentContractorID"));
		forestDevelopmentContractor.setStateID(rs.getInt("StateID"));
		forestDevelopmentContractor.setStateName(rs.getString("StateName"));
		forestDevelopmentContractor.setReceiptID(rs.getLong("ReceiptID"));
		forestDevelopmentContractor.setReceiptNo(rs.getString("ReceiptNo"));
		forestDevelopmentContractor
				.setRegistrationNo(rs.getString("RegistrationNo"));
		forestDevelopmentContractor.setName(rs.getString("Name"));
		forestDevelopmentContractor.setIcNo(rs.getString("ICNo"));
		forestDevelopmentContractor.setCompanyName(rs.getString("CompanyName"));
		forestDevelopmentContractor.setAddress(rs.getString("Address"));
		forestDevelopmentContractor
				.setRegisteredAddress(rs.getString("RegisteredAddress"));
		forestDevelopmentContractor.setTelNo(rs.getString("TelNo"));
		forestDevelopmentContractor.setHpNo(rs.getString("HPNo"));
		forestDevelopmentContractor
				.setRegisteredBusinessNo(rs.getString("RegisteredBusinessNo"));
		forestDevelopmentContractor
				.setLicenseStatusID(rs.getInt("LicenseStatusID"));
		forestDevelopmentContractor
				.setLicenseStatusCode(rs.getString("LicenseStatusCode"));
		forestDevelopmentContractor
				.setLicenseStatusName(rs.getString("LicenseStatusName"));
		forestDevelopmentContractor.setStartDate(rs.getDate("StartDate"));
		forestDevelopmentContractor.setEndDate(rs.getDate("EndDate"));
		forestDevelopmentContractor
				.setRegistrationDate(rs.getDate("RegistrationDate"));
		forestDevelopmentContractor.setContractorServiceCenterTitle(
				rs.getString("ContractorServiceCenterTitle"));
		forestDevelopmentContractor.setPkkRegistrationCertificateNo(
				rs.getString("PkkRegistrationCertificateNo"));
		forestDevelopmentContractor
				.setCidbRegistrationNo(rs.getString("CidbRegistrationNo"));
		forestDevelopmentContractor
				.setManagementStaffNo(rs.getInt("ManagementStaffNo"));
		forestDevelopmentContractor
				.setSupervisionStaffNo(rs.getInt("SupervisionStaffNo"));
		forestDevelopmentContractor
				.setSkilledStaffNo(rs.getInt("SkilledStaffNo"));
		forestDevelopmentContractor
				.setUnSkilledStaffNo(rs.getInt("UnSkilledStaffNo"));
		forestDevelopmentContractor
				.setOthersStaffNo(rs.getInt("OthersStaffNo"));
		forestDevelopmentContractor
				.setMachineryDescription(rs.getString("MachineryDescription"));
		forestDevelopmentContractor
				.setPreviousExperience(rs.getString("PreviousExperience"));
		forestDevelopmentContractor.setRecorderID(rs.getString("RecorderID"));
		forestDevelopmentContractor
				.setRecorderName(rs.getString("RecorderName"));
		forestDevelopmentContractor
				.setRecordTime(rs.getTimestamp("RecordTime"));
		forestDevelopmentContractor
				.setForestDevelopmentContractorSubWorkTypeRecords(
						facade.getForestDevelopmentContractorSubWorkTypeRecords(
								forestDevelopmentContractor));
		forestDevelopmentContractor
				.setRenews(facade.getRenews(forestDevelopmentContractor, "A"));
		forestDevelopmentContractor.setStatus(rs.getString("Status"));

		return forestDevelopmentContractor;
	}

	private ArrayList<ForestDevelopmentContractor> getForestDevelopmentContractors(
			ResultSet rs) throws SQLException
	{
		ArrayList<ForestDevelopmentContractor> forestDevelopmentContractors = new ArrayList<>();

		while (rs.next())
		{
			ForestDevelopmentContractor forestDevelopmentContractor = readForestDevelopmentContractor(
					rs);
			forestDevelopmentContractors.add(forestDevelopmentContractor);
		}
		return forestDevelopmentContractors;
	}

	ForestDevelopmentContractor getForestDevelopmentContractor(
			long forestDevelopmentContractorID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT fdc.*, r.ReceiptNo, ls.Code AS LicenseStatusCode, ls.Name AS LicenseStatusName, s.Name AS RecorderName, stt.StateID, stt.Name AS StateName "
						+ "FROM ForestDevelopmentContractor fdc LEFT JOIN Receipt r ON fdc.ReceiptID = r.ReceiptID LEFT JOIN LicenseStatus ls ON fdc.LicenseStatusID = ls.LicenseStatusID "
						+ "LEFT JOIN Staff s ON fdc.RecorderID = s.StaffID LEFT JOIN State sttfdc ON fdc.StateID = sttfdc.StateID LEFT JOIN State stts ON s.StateID = stts.StateID "
						+ "WHERE fdc.ForestDevelopmentContractorID = ? "
						+ "ORDER BY fdc.CompanyName");

		ps.setLong(1, forestDevelopmentContractorID);

		ForestDevelopmentContractor forestDevelopmentContractor = null;
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			forestDevelopmentContractor = readForestDevelopmentContractor(rs);
		}

		return forestDevelopmentContractor;
	}

	ArrayList<ForestDevelopmentContractor> getForestDevelopmentContractors(
			String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT fdc.*, r.ReceiptNo, ls.Code AS LicenseStatusCode, ls.Name AS LicenseStatusName, s.Name AS RecorderName, sttfdc.StateID, sttfdc.Name AS StateName "
						+ "FROM ForestDevelopmentContractor fdc LEFT JOIN Receipt r ON fdc.ReceiptID = r.ReceiptID LEFT JOIN LicenseStatus ls ON fdc.LicenseStatusID = ls.LicenseStatusID "
						+ "LEFT JOIN Staff s ON fdc.RecorderID = s.StaffID LEFT JOIN State sttfdc ON fdc.StateID = sttfdc.StateID LEFT JOIN State stts ON s.StateID = stts.StateID "
						+ "WHERE fdc.Status = ? "
						+ "ORDER BY fdc.CompanyName");

		ps.setString(1, status);

		return getForestDevelopmentContractors(ps.executeQuery());
	}

	ArrayList<ForestDevelopmentContractor> getForestDevelopmentContractors(
			State state, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT fdc.*, r.ReceiptNo, ls.Code AS LicenseStatusCode, ls.Name AS LicenseStatusName, s.Name AS RecorderName, sttfdc.StateID, sttfdc.Name AS StateName "
						+ "FROM ForestDevelopmentContractor fdc LEFT JOIN Receipt r ON fdc.ReceiptID = r.ReceiptID LEFT JOIN LicenseStatus ls ON fdc.LicenseStatusID = ls.LicenseStatusID "
						+ "LEFT JOIN Staff s ON fdc.RecorderID = s.StaffID LEFT JOIN State sttfdc ON fdc.StateID = sttfdc.StateID LEFT JOIN State stts ON s.StateID = stts.StateID "
						+ "WHERE stts.StateID = ? AND fdc.Status = ? "
						+ "ORDER BY fdc.CompanyName");

		ps.setInt(1, state.getStateID());
		ps.setString(2, status);

		return getForestDevelopmentContractors(ps.executeQuery());
	}

	ArrayList<ForestDevelopmentContractor> getForestDevelopmentContractors(
			Staff user, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT fdc.*, r.ReceiptNo, ls.Code AS LicenseStatusCode, ls.Name AS LicenseStatusName, s.Name AS RecorderName, sttfdc.StateID, sttfdc.Name AS StateName "
						+ "FROM ForestDevelopmentContractor fdc LEFT JOIN Receipt r ON fdc.ReceiptID = r.ReceiptID LEFT JOIN LicenseStatus ls ON fdc.LicenseStatusID = ls.LicenseStatusID "
						+ "LEFT JOIN Staff s ON fdc.RecorderID = s.StaffID LEFT JOIN State sttfdc ON fdc.StateID = sttfdc.StateID LEFT JOIN State stts ON s.StateID = stts.StateID "
						+ "WHERE fdc.RecorderID = ? AND fdc.Status = ? "
						+ "ORDER BY fdc.CompanyName");

		ps.setString(1, user.getStaffID());
		ps.setString(2, status);

		return getForestDevelopmentContractors(ps.executeQuery());
	}

	ArrayList<ForestDevelopmentContractor> getForestDevelopmentContractors(
			State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT fdc.*, r.ReceiptNo, ls.Code AS LicenseStatusCode, ls.Name AS LicenseStatusName, s.Name AS RecorderName, stt.StateID, stt.Name AS StateName "
						+ "FROM ForestDevelopmentContractor fdc LEFT JOIN Receipt r ON fdc.ReceiptID = r.ReceiptID LEFT JOIN LicenseStatus ls ON fdc.LicenseStatusID = ls.LicenseStatusID "
						+ "LEFT JOIN Staff s ON fdc.RecorderID = s.StaffID LEFT JOIN State sttfdc ON fdc.StateID = sttfdc.StateID LEFT JOIN State stts ON s.StateID = stts.StateID "
						+ "WHERE stt.StateID = ? "
						+ "ORDER BY fdc.CompanyName");

		ps.setInt(1, state.getStateID());

		return getForestDevelopmentContractors(ps.executeQuery());
	}
}