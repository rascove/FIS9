package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.model.LoggingContractor;

/**
 * @author Nor Azman Mat Ariff
 */
class LoggingContractorManager extends RevenueTableManager
{
	LoggingContractorManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(LoggingContractor loggingContractor, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, loggingContractor.getReceiptID());
		ps.setString(2, loggingContractor.getRegistrationSerialNo());
		ps.setString(3, loggingContractor.getType());
		ps.setString(4, loggingContractor.getName());
		ps.setString(5, loggingContractor.getIcNo());
		ps.setString(6, loggingContractor.getCompanyName());
		ps.setString(7, loggingContractor.getAddress());
		ps.setString(8, loggingContractor.getRegisteredAddress());
		ps.setString(9, loggingContractor.getBusinessRegistrationNo());
		ps.setString(10, loggingContractor.getTelNo());
		ps.setInt(11, loggingContractor.getLicenseStatusID());
		ps.setDate(12, toSQLDate(loggingContractor.getStartDate()));
		ps.setDate(13, toSQLDate(loggingContractor.getEndDate()));
		ps.setDate(14, toSQLDate(loggingContractor.getRegistrationDate()));
		ps.setInt(15, loggingContractor.getManagementStaffNo());
		ps.setInt(16, loggingContractor.getClericalStaffNo());
		ps.setInt(17, loggingContractor.getOthersStaffNo());
		ps.setInt(18, loggingContractor.getJcbNo());
		ps.setInt(19, loggingContractor.getPenyanggaNo());
		ps.setInt(20, loggingContractor.getPenggeredNo());
		ps.setInt(21, loggingContractor.getLorryNo());
		ps.setString(22, loggingContractor.getPreviousLicensePermitNo());
		nullable(ps, 23, loggingContractor.getArea());
		nullable(ps, 24, loggingContractor.getStateID());
		ps.setString(25, loggingContractor.getRecorderID());
		ps.setTimestamp(26, loggingContractor.getRecordTime());
		ps.setString(27, loggingContractor.getStatus());
		ps.setLong(28, loggingContractor.getLoggingContractorID());

		return ps.executeUpdate();
	}

	int addLoggingContractor(LoggingContractor loggingContractor) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT IGNORE INTO LoggingContractor (ReceiptID, RegistrationSerialNo, Type, Name, ICNo, CompanyName, Address, RegisteredAddress, BusinessRegistrationNo, TelNo, "
				+ "LicenseStatusID, StartDate, EndDate, RegistrationDate, ManagementStaffNo, ClericalStaffNo, OthersStaffNo, JCBNo, PenyanggaNo, PenggeredNo, "
				+ "LorryNo, PreviousLicensePermitNo, Area, StateID, RecorderID, RecordTime, Status, LoggingContractorID) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?)"); 

		return write(loggingContractor, ps);
	}

	int updateLoggingContractor(LoggingContractor loggingContractor) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE LoggingContractor SET ReceiptID = ?, RegistrationSerialNo = ?, Type = ?, Name = ?, ICNo = ?, CompanyName = ?, Address = ?, RegisteredAddress = ?, BusinessRegistrationNo = ?, TelNo = ?, " 
				+ "LicenseStatusID = ?, StartDate = ?, EndDate = ?, RegistrationDate = ?, ManagementStaffNo = ?, ClericalStaffNo = ?, OthersStaffNo = ?, JCBNo = ?, PenyanggaNo = ?, PenggeredNo = ?, "  
				+ "LorryNo = ?, PreviousLicensePermitNo = ?, Area = ?, StateID = ?, RecorderID = ?, RecordTime = ?, Status = ? WHERE LoggingContractorID = ?");

		return write(loggingContractor, ps);
	}

	int updateLoggingContractorStatus(LoggingContractor loggingContractor) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE LoggingContractor SET Status = ? WHERE LoggingContractorID = ?");
		ps.setString(1, loggingContractor.getStatus());
		ps.setLong(2, loggingContractor.getLoggingContractorID());

		return ps.executeUpdate();
	}

	int deleteLoggingContractor(LoggingContractor loggingContractor) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE LoggingContractor SET Status = 'I' WHERE LoggingContractorID = ?");
		ps.setLong(1, loggingContractor.getLoggingContractorID());

		return ps.executeUpdate();
	}

	private LoggingContractor readLoggingContractor(ResultSet rs) throws SQLException
	{
		LoggingContractor loggingContractor = new LoggingContractor();

		loggingContractor.setLoggingContractorID(rs.getLong("LoggingContractorID"));
		loggingContractor.setReceiptID(rs.getLong("ReceiptID"));
		loggingContractor.setReceiptNo(rs.getString("ReceiptNo"));
		loggingContractor.setRegistrationSerialNo(rs.getString("RegistrationSerialNo"));
		loggingContractor.setName(rs.getString("Name"));
		loggingContractor.setType(rs.getString("Type"));
		loggingContractor.setIcNo(rs.getString("ICNo"));
		loggingContractor.setCompanyName(rs.getString("CompanyName"));
		loggingContractor.setAddress(rs.getString("Address"));
		loggingContractor.setRegisteredAddress(rs.getString("RegisteredAddress"));
		loggingContractor.setBusinessRegistrationNo(rs.getString("BusinessRegistrationNo"));
		loggingContractor.setTelNo(rs.getString("TelNo"));
		loggingContractor.setLicenseStatusID(rs.getInt("LicenseStatusID"));
		loggingContractor.setLicenseStatusCode(rs.getString("LicenseStatusCode"));
		loggingContractor.setLicenseStatusName(rs.getString("LicenseStatusName"));
		loggingContractor.setStartDate(rs.getDate("StartDate"));
		loggingContractor.setEndDate(rs.getDate("EndDate"));
		loggingContractor.setRegistrationDate(rs.getDate("RegistrationDate"));
		loggingContractor.setManagementStaffNo(rs.getInt("ManagementStaffNo"));
		loggingContractor.setClericalStaffNo(rs.getInt("ClericalStaffNo"));
		loggingContractor.setOthersStaffNo(rs.getInt("OthersStaffNo"));
		loggingContractor.setJcbNo(rs.getInt("JcbNo"));
		loggingContractor.setPenyanggaNo(rs.getInt("PenyanggaNo"));
		loggingContractor.setPenggeredNo(rs.getInt("PenggeredNo"));
		loggingContractor.setLorryNo(rs.getInt("LorryNo"));
		loggingContractor.setPreviousLicensePermitNo(rs.getString("PreviousLicensePermitNo"));
		loggingContractor.setArea(rs.getBigDecimal("Area"));
		loggingContractor.setStateID(rs.getInt("StateID"));
		loggingContractor.setStateName(rs.getString("StateName"));
		loggingContractor.setRecorderID(rs.getString("RecorderID"));
		loggingContractor.setRecorderName(rs.getString("RecorderName"));	
		loggingContractor.setRecordTime(rs.getTimestamp("RecordTime"));
		loggingContractor.setRenews(facade.getRenews(loggingContractor, "A"));
		loggingContractor.setStatus(rs.getString("Status"));	

		return loggingContractor;
	}

	private ArrayList<LoggingContractor> getLoggingContractors(ResultSet rs) throws SQLException
	{
		ArrayList<LoggingContractor> loggingContractors = new ArrayList<>();

		while (rs.next())
		{
			LoggingContractor loggingContractor = readLoggingContractor(rs);
			loggingContractors.add(loggingContractor);
		}
		return loggingContractors;
	}

	LoggingContractor getLoggingContractor(long loggingContractorID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT lc.*, r.ReceiptNo, ls.Code AS LicenseStatusCode, ls.Name AS LicenseStatusName, stt.Name AS StateName, stf.Name AS RecorderName " + 
				"FROM LoggingContractor lc LEFT JOIN Receipt r ON lc.ReceiptID = r.ReceiptID LEFT JOIN LicenseStatus ls ON lc.LicenseStatusID = ls.LicenseStatusID LEFT JOIN Staff stf ON lc.RecorderID = stf.StaffID LEFT JOIN State stt ON lc.StateID = stt.StateID " + 
				"WHERE lc.LoggingContractorID = ? AND lc.ReceiptID = r.ReceiptID AND lc.LicenseStatusID = ls.LicenseStatusID AND lc.RecorderID = stf.StaffID");

		ps.setLong(1, loggingContractorID);

		LoggingContractor loggingContractor = null;
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			loggingContractor = readLoggingContractor(rs);
		}

		return loggingContractor;
	}

	ArrayList<LoggingContractor> getLoggingContractors(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT lc.*, r.ReceiptNo, ls.Code AS LicenseStatusCode, ls.Name AS LicenseStatusName, stt.Name AS StateName, stf.Name AS RecorderName, stt.Name AS StateName " + 
				"FROM LoggingContractor lc LEFT JOIN Receipt r ON lc.ReceiptID = r.ReceiptID LEFT JOIN LicenseStatus ls ON lc.LicenseStatusID = ls.LicenseStatusID LEFT JOIN Staff stf ON lc.RecorderID = stf.StaffID LEFT JOIN State stt ON lc.StateID = stt.StateID " + 
				"WHERE lc.ReceiptID = r.ReceiptID AND lc.LicenseStatusID = ls.LicenseStatusID AND lc.RecorderID = stf.StaffID AND lc.Status = ? ORDER BY lc.CompanyName");

		ps.setString(1, status);

		return getLoggingContractors(ps.executeQuery());
	}

	ArrayList<LoggingContractor> getLoggingContractors(State state, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT lc.*, r.ReceiptNo, ls.Code AS LicenseStatusCode, ls.Name AS LicenseStatusName, stt.Name AS StateName, stf.Name AS RecorderName, stt.Name AS StateName " + 
				"FROM LoggingContractor lc LEFT JOIN Receipt r ON lc.ReceiptID = r.ReceiptID LEFT JOIN LicenseStatus ls ON lc.LicenseStatusID = ls.LicenseStatusID LEFT JOIN Staff stf ON lc.RecorderID = stf.StaffID LEFT JOIN State stt ON lc.StateID = stt.StateID " + 
				"WHERE lc.ReceiptID = r.ReceiptID AND lc.LicenseStatusID = ls.LicenseStatusID AND lc.RecorderID = stf.StaffID AND stf.StateID = ? AND lc.Status = ? ORDER BY lc.CompanyName");

		ps.setInt(1, state.getStateID());
		ps.setString(2, status);

		return getLoggingContractors(ps.executeQuery());
	}
	
	ArrayList<LoggingContractor> getLoggingContractors(Staff user, String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT lc.*, r.ReceiptNo, ls.Code AS LicenseStatusCode, ls.Name AS LicenseStatusName, stt.Name AS StateName, stf.Name AS RecorderName, stt.Name AS StateName " + 
				"FROM LoggingContractor lc LEFT JOIN Receipt r ON lc.ReceiptID = r.ReceiptID LEFT JOIN LicenseStatus ls ON lc.LicenseStatusID = ls.LicenseStatusID LEFT JOIN Staff stf ON lc.RecorderID = stf.StaffID LEFT JOIN State stt ON lc.StateID = stt.StateID " + 
				"WHERE lc.ReceiptID = r.ReceiptID AND lc.LicenseStatusID = ls.LicenseStatusID AND lc.RecorderID = stf.StaffID AND lc.RecorderID = ? AND lc.Status = ? ORDER BY lc.CompanyName");

		ps.setString(1, user.getStaffID());
		ps.setString(2, status);

		return getLoggingContractors(ps.executeQuery());
	}
}