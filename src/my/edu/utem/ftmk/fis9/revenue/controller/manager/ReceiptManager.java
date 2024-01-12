package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.revenue.model.Receipt;
import my.edu.utem.ftmk.fis9.revenue.model.ReceiptRecord;

/**
 * @author Nor Azman Mat Ariff
 */
class ReceiptManager extends RevenueTableManager
{
	ReceiptManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(Receipt receipt, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, receipt.getReceiptNo());
		nullable(ps, 2, receipt.getCategory());
		nullable(ps, 3, receipt.getForestDevelopmentContractorID());
		nullable(ps, 4, receipt.getLoggingContractorID());
		nullable(ps, 5, receipt.getLicenseID());
		nullable(ps, 6, receipt.getPermitID());
		ps.setString(7, receipt.getName().toUpperCase());
		ps.setDate(8, toSQLDate(receipt.getDate()));
		ps.setString(9, receipt.getNotes().toUpperCase());
		ps.setInt(10, receipt.getPaymentTypeID());
		nullable(ps, 11, receipt.getBankID());
		nullable(ps, 12, receipt.getChequeTypeID());
		ps.setString(13, receipt.getChequeNo());
		nullable(ps, 14, receipt.getChequeDate());
		ps.setBigDecimal(15, receipt.getGrandTotal());
		ps.setString(16, receipt.getRecorderID());
		ps.setTimestamp(17, receipt.getRecordTime());
		ps.setString(18, receipt.getStatus());
		ps.setLong(19, receipt.getReceiptID());

		return ps.executeUpdate();
	}

	int addReceipt(Receipt receipt) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"INSERT IGNORE INTO Receipt (ReceiptNo, Category, ForestDevelopmentContractorID, LoggingContractorID, LicenseID, PermitID, Name, Date, Notes, PaymentTypeID, "
						+ "BankID, ChequeTypeID, ChequeNo, ChequeDate, GrandTotal, RecorderID, RecordTime, Status, ReceiptID) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?)");

		return write(receipt, ps);
	}
	
	boolean checkExistingReceiptNo(Receipt receipt) throws SQLException
	{
		boolean exist = false;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Receipt WHERE ReceiptNo = ?");
		ps.setString(1, receipt.getReceiptNo());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			exist = true;
		}

		return exist;
	}

	int deleteReceipt(Receipt receipt) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Receipt SET Status = 'C' WHERE ReceiptID = ?");
		ps.setLong(1, receipt.getReceiptID());

		return ps.executeUpdate();
	}
	
	int updateReceiptCategory(Receipt receipt) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Receipt SET Category = ? WHERE ReceiptID = ?");
		ps.setInt(1, receipt.getCategory());
		ps.setLong(2, receipt.getReceiptID());

		return ps.executeUpdate();
	}

	int updateStatusReceipt(Receipt receipt) throws SQLException
	{
		String query = "";
		if (receipt.getCategory() == 2)
		{
			query = "UPDATE Receipt SET ForestDevelopmentContractorID = ?, Status = ? WHERE ReceiptID = ?";
		}
		else
		{
			if (receipt.getCategory() == 3)
			{
				query = "UPDATE Receipt SET LoggingContractorID = ?, Status = ? WHERE ReceiptID = ?";
			}
			else
			{
				if (receipt.getCategory() == 6)
				{
					query = "UPDATE Receipt SET LicenseID = ?, Status = ? WHERE ReceiptID = ?";
				}
				else
				{
					if (receipt.getCategory() == 7)
					{
						query = "UPDATE Receipt SET PermitID = ?, Status = ? WHERE ReceiptID = ?";
					}
					else
					{

					}
				}
			}
		}
		PreparedStatement ps = facade.prepareStatement(query);
		if (receipt.getCategory() == 2)
		{
			ps.setLong(1, receipt.getForestDevelopmentContractorID());
		}
		else
		{
			if (receipt.getCategory() == 3)
			{
				ps.setLong(1, receipt.getLoggingContractorID());
			}
			else
			{
				if (receipt.getCategory() == 6)
				{
					ps.setLong(1, receipt.getLicenseID());
				}
				else
				{
					if (receipt.getCategory() == 7)
					{
						ps.setLong(1, receipt.getPermitID());
					}
					else
					{

					}
				}
			}
		}
		ps.setString(2, receipt.getStatus());
		ps.setLong(3, receipt.getReceiptID());

		return ps.executeUpdate();
	}

	int updateCollectorStatement(Receipt receipt) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Receipt SET CollectorStatement = ? WHERE ReceiptID = ?");

		ps.setString(1, receipt.getCollectorStatement());
		ps.setLong(2, receipt.getReceiptID());

		return ps.executeUpdate();
	}

	private Receipt readReceipt(ResultSet rs) throws SQLException
	{
		Receipt receipt = new Receipt();

		receipt.setReceiptID(rs.getLong("ReceiptID"));
		receipt.setReceiptNo(rs.getString("ReceiptNo"));
		receipt.setCategory(rs.getInt("Category"));
		
		if (!(receipt.getCategory() >= 4 && receipt.getCategory() <= 13))
		{
		}
		else
		{
			if (receipt.getCategory() == 4 || receipt.getCategory() == 5)
			{
				receipt = readForestDevelopmentContractor(receipt);
			}
			else
			{
				if (receipt.getCategory() >= 6 && receipt.getCategory() <= 9)
				{
					if(receipt.getLoggingContractorID() != 0) receipt = readLoggingContractor(receipt);
				}
				else
				{
					if (receipt.getCategory() == 10
							|| receipt.getCategory() == 11)
					{
						receipt = readLicense(receipt);
					}
					else
					{
						if (receipt.getCategory() == 12
								|| receipt.getCategory() == 13)
						{
							receipt = readPermit(receipt);
						}
					}
				}
			}
		}
		receipt.setName(rs.getString("Name"));
		receipt.setDate(rs.getDate("Date"));
		receipt.setNotes(rs.getString("Notes"));
		receipt.setCollectorStatement(rs.getString("CollectorStatement"));
		receipt.setPaymentTypeID(rs.getInt("PaymentTypeID"));
		receipt.setPaymentTypeCode(rs.getString("PaymentTypeCode"));
		receipt.setPaymentTypeName(rs.getString("PaymentTypeName"));
		if (receipt.getPaymentTypeID() != 1)
		{
			readBankAndChequeType(receipt);
		}
		receipt.setChequeNo(rs.getString("ChequeNo"));
		receipt.setChequeDate(rs.getDate("ChequeDate"));
		receipt.setGrandTotal(rs.getBigDecimal("GrandTotal"));
		receipt.setRecorderID(rs.getString("RecorderID"));
		receipt.setRecorderName(rs.getString("RecorderName"));
		receipt.setRecordTime(rs.getTimestamp("RecordTime"));
		receipt.setStatus(rs.getString("Status"));
		receipt.setReceiptRecords(
				facade.getReceiptRecords(receipt.getReceiptID()));

		return receipt;
	}

	private Receipt readForestDevelopmentContractor(Receipt receipt)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT fdc.Name AS ContractorName, fdc.CompanyName, fdc.RegisteredBusinessNo AS CompanyRegistrationNo, fdc.Address, fdc.TelNo "
						+ "FROM Receipt r, ForestDevelopmentContractor fdc "
						+ "WHERE r.ForestDevelopmentContractorID = fdc.ForestDevelopmentContractorID AND r.receiptID = ?");

		ps.setLong(1, receipt.getReceiptID());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			receipt = readForestDevelopmentContractor(receipt, rs);

		return receipt;
	}

	private Receipt readForestDevelopmentContractor(Receipt receipt,
			ResultSet rs) throws SQLException
	{
		receipt.setContractorName(rs.getString("ContractorName"));
		receipt.setCompanyName(rs.getString("CompanyName"));
		receipt.setCompanyRegistrationNo(rs.getString("CompanyRegistrationNo"));
		receipt.setAddress(rs.getString("Address"));
		receipt.setTelNo(rs.getString("TelNo"));

		return receipt;
	}

	private Receipt readLoggingContractor(Receipt receipt) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT lc.RegistrationSerialNo AS LoggingContractorRegistrationSerialNo, lc.Type AS loggingContractorType, lc.Name AS ContractorName, lc.CompanyName, lc.BusinessRegistrationNo AS CompanyRegistrationNo, lc.Address, lc.TelNo "
						+ "FROM Receipt r, LoggingContractor lc "
						+ "WHERE r.LoggingContractorID = lc.LoggingContractorID AND r.receiptID = ?");

		ps.setLong(1, receipt.getReceiptID());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			receipt = readLoggingContractor(receipt, rs);

		return receipt;
	}

	private Receipt readLoggingContractor(Receipt receipt, ResultSet rs)
			throws SQLException
	{
		receipt.setLoggingContractorRegistrationSerialNo(
				rs.getString("LoggingContractorRegistrationSerialNo"));
		receipt.setLoggingContractorType(rs.getString("LoggingContractorType"));
		receipt.setContractorName(rs.getString("ContractorName"));
		receipt.setCompanyName(rs.getString("CompanyName"));
		receipt.setCompanyRegistrationNo(rs.getString("CompanyRegistrationNo"));
		receipt.setAddress(rs.getString("Address"));
		receipt.setTelNo(rs.getString("TelNo"));

		return receipt;
	}

	private Receipt readLicense(Receipt receipt) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT r.LicenseID, lt.LicenseTypeID, lt.Code AS LicenseTypeCode, lt.Name AS LicenseTypeName, l.LicenseNo, lc.Name AS ContractorName, lc.CompanyName, lc.BusinessRegistrationNo AS CompanyRegistrationNo, lc.Address, lc.TelNo "
						+ "FROM Receipt r, License l, LoggingContractor lc, LicenseType lt "
						+ "WHERE r.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID  AND l.LicenseTypeID = lt.LicenseTypeID AND r.receiptID = ?");

		ps.setLong(1, receipt.getReceiptID());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			receipt = readLicense(receipt, rs);

		return receipt;
	}

	private Receipt readLicense(Receipt receipt, ResultSet rs)
			throws SQLException
	{
		receipt.setLicenseID(rs.getLong("LicenseID"));
		receipt.setLicenseTypeID(rs.getInt("LicenseTypeID"));
		receipt.setLicenseTypeCode(rs.getString("LicenseTypeCode"));
		receipt.setLicenseTypeName(rs.getString("LicenseTypeName"));
		receipt.setContractorName(rs.getString("ContractorName"));
		receipt.setCompanyName(rs.getString("CompanyName"));
		receipt.setCompanyRegistrationNo(rs.getString("CompanyRegistrationNo"));
		receipt.setAddress(rs.getString("Address"));
		receipt.setTelNo(rs.getString("TelNo"));

		return receipt;
	}

	private Receipt readPermit(Receipt receipt) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT r.PermitID, p.PermitTypeID, pt.Code AS PermitTypeCode, pt.Name AS PermitTypeName, p.PermitNo, lc.Name AS ContractorName, lc.CompanyName, lc.BusinessRegistrationNo AS CompanyRegistrationNo, lc.Address, lc.TelNo "
						+ "FROM Receipt r, Permit p, LoggingContractor lc, PermitType pt "
						+ "WHERE r.PermitID = p.PermitID AND p.LicenseeID = lc.LoggingContractorID AND p.PermitTypeID = pt.PermitTypeID AND r.receiptID = ?");

		ps.setLong(1, receipt.getReceiptID());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			receipt = readPermit(receipt, rs);

		return receipt;
	}

	private Receipt readPermit(Receipt receipt, ResultSet rs)
			throws SQLException
	{
		receipt.setPermitID(rs.getLong("PermitID"));
		receipt.setPermitTypeID(rs.getInt("PermitTypeID"));
		receipt.setPermitTypeCode(rs.getString("PermitTypeCode"));
		receipt.setPermitTypeName(rs.getString("PermitTypeName"));
		receipt.setPermitNo(rs.getString("PermitNo"));
		receipt.setContractorName(rs.getString("ContractorName"));
		receipt.setCompanyName(rs.getString("CompanyName"));
		receipt.setCompanyRegistrationNo(rs.getString("CompanyRegistrationNo"));
		receipt.setAddress(rs.getString("Address"));
		receipt.setTelNo(rs.getString("TelNo"));

		return receipt;
	}

	private Receipt readBankAndChequeType(Receipt receipt) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT b.BankID, b.Code AS bankCode, b.Name AS BankName, c.ChequeTypeID, c.Code AS ChequeTypeCode, c.Name AS ChequeTypeName "
						+ "FROM Receipt r LEFT JOIN ChequeType c ON r.ChequeTypeID = c.ChequeTypeID, Bank b "
						+ "WHERE r.BankID = b.BankID AND r.ReceiptID = ?");

		ps.setLong(1, receipt.getReceiptID());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			receipt = readBankAndChequeType(receipt, rs);

		return receipt;

	}

	private Receipt readBankAndChequeType(Receipt receipt, ResultSet rs)
			throws SQLException
	{
		receipt.setBankID(rs.getInt("BankID"));
		receipt.setBankCode(rs.getString("BankCode"));
		receipt.setBankName(rs.getString("BankName"));
		receipt.setChequeTypeID(rs.getInt("ChequeTypeID"));
		receipt.setChequeTypeCode(rs.getString("ChequeTypeCode"));
		receipt.setChequeTypeName(rs.getString("ChequeTypeName"));

		return receipt;
	}

	private ArrayList<Receipt> getReceipts(ResultSet rs) throws SQLException
	{
		ArrayList<Receipt> receipts = new ArrayList<>();

		while (rs.next())
		{
			Receipt receipt = readReceipt(rs);
			receipts.add(receipt);
		}
		return receipts;
	}

	Receipt getReceipt(long receiptID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT r.*, p.Code AS PaymentTypeCode, p.Name AS PaymentTypeName, "
						+ "s.Name AS RecorderName "
						+ "FROM Receipt r, PaymentType p, Staff s "
						+ "WHERE r.PaymentTypeID = p.PaymentTypeID AND r.RecorderID = s.StaffID AND r.ReceiptID = ?");

		ps.setLong(1, receiptID);

		Receipt receipt = null;
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			receipt = readReceipt(rs);
		}

		return receipt;
	}

	ArrayList<Receipt> getReceipts(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT r.*, p.Code AS PaymentTypeCode, p.Name AS PaymentTypeName, "
						+ "s.Name AS RecorderName "
						+ "FROM Receipt r, PaymentType p, Staff s "
						+ "WHERE r.PaymentTypeID = p.PaymentTypeID AND r.RecorderID = s.StaffID AND r.Status != ?");

		ps.setString(1, status);

		return getReceipts(ps.executeQuery());
	}

	ArrayList<Receipt> getReceiptsByUser(String status, String staffID)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT r.*, p.Code AS PaymentTypeCode, p.Name AS PaymentTypeName, "
						+ "s.Name AS RecorderName "
						+ "FROM Receipt r, PaymentType p, Staff s "
						+ "WHERE r.PaymentTypeID = p.PaymentTypeID AND r.RecorderID = s.StaffID AND r.Status != ? AND s.StaffID = ?");

		ps.setString(1, status);
		ps.setString(2, staffID);

		return getReceipts(ps.executeQuery());
	}

	ArrayList<Receipt> getReceipts(String receiptCategory, String status)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT r.*, p.Code AS PaymentTypeCode, p.Name AS PaymentTypeName, "
						+ "s.Name AS RecorderName "
						+ "FROM Receipt r, PaymentType p, Staff s "
						+ "WHERE r.PaymentTypeID = p.PaymentTypeID AND r.RecorderID = s.StaffID AND r.Category = ? AND r.Status = ?");

		ps.setString(1, receiptCategory);
		ps.setString(2, status);

		return getReceipts(ps.executeQuery());
	}

	ArrayList<Receipt> getReceipts(String receiptCategory, String statusInactive, String registeredAsContractor, String registeredAsLicensee)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT r.*, p.Code AS PaymentTypeCode, p.Name AS PaymentTypeName, "
						+ "s.Name AS RecorderName "
						+ "FROM Receipt r, PaymentType p, Staff s "
						+ "WHERE r.PaymentTypeID = p.PaymentTypeID AND r.RecorderID = s.StaffID AND r.Category = ? AND r.Status in (?, ?, ?)");

		ps.setString(1, receiptCategory);
		ps.setString(2, statusInactive);
		ps.setString(3, registeredAsContractor);
		ps.setString(4, registeredAsLicensee);

		return getReceipts(ps.executeQuery());
	}

	ArrayList<Receipt> getReceipts(Date startDate, Date endDate,
			String extraQuery) throws SQLException
	{
		String query = "SELECT r.*, p.Code AS PaymentTypeCode, p.Name AS PaymentTypeName, "
				+ "s.Name AS RecorderName "
				+ "FROM Receipt r, PaymentType p, Staff s "
				+ "WHERE r.PaymentTypeID = p.PaymentTypeID AND r.RecorderID = s.StaffID AND r.Date between ? AND ?";

		query = query + extraQuery;

		PreparedStatement ps = facade.prepareStatement(query);

		ps.setDate(1, toSQLDate(startDate));
		ps.setDate(2, toSQLDate(endDate));

		return getReceipts(ps.executeQuery());
	}
	
	private ArrayList<Integer> getTrustFundsID() throws SQLException
	{
		ArrayList<Integer> trustFundsID = new ArrayList<Integer>();
		PreparedStatement ps = facade
				.prepareStatement("SELECT dv.DepartmentVotID "
						+ "FROM BursaryVot bv, DepartmentVot dv "
						+ "WHERE dv.BursaryVotID = bv.BursaryVotID AND dv.Status = 'A'");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			trustFundsID.add(rs.getInt("DepartmentVotID"));
		}
		
	return trustFundsID;
	}

	ArrayList<String[]> getReceiptsString(Date startDate, Date endDate,
			int reportType) throws SQLException
	{
		String extraQuery = "";
		if (reportType == 1 || reportType == 2)
		{
			extraQuery = " ORDER BY r.Date, r.ReceiptNo";
		}
		else
		{
			if (reportType == 3)
			{
				extraQuery = " ORDER BY r.Date, p.PaymentTypeID, r.ReceiptNo";
			}
			else
			{
				if (reportType == 4)
				{
					extraQuery = " ORDER BY p.PaymentTypeID, r.ReceiptNo";
				}
				else
				{
					if (reportType == 5)
					{
						extraQuery = " GROUP BY p.PaymentTypeCode ORDER BY p.PaymentTypeID, r.ReceiptNo";
					}
				}
			}
		}

		ArrayList<String[]> receiptsString = new ArrayList<String[]>();
		ArrayList<Receipt> receipts = null;

		if (reportType >= 1 && reportType <= 3)
		{
			receipts = getReceipts(startDate, endDate, extraQuery);
		}
		else
		{
			if (reportType == 4)
			{
				receipts = getReceipts(startDate, startDate, extraQuery);
			}
		}

		PreparedStatement ps = facade
				.prepareStatement("SELECT dv.DepartmentVotID "
						+ "FROM TrustFund tf, DepartmentVot dv "
						+ "WHERE tf.DepartmentVotID = dv.DepartmentVotID ");

		ArrayList<Integer> trustFundDepartmentVotIDs = new ArrayList<Integer>();

		ResultSet rs = ps.executeQuery();

		int departmentVotID = 0;

		while (rs.next())
		{
			departmentVotID = rs.getInt("DepartmentVotID");
			trustFundDepartmentVotIDs.add(departmentVotID);
		}

		boolean trustFund = false;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for (Receipt receipt : receipts)
		{
			if (reportType == 1
					|| (reportType == 2
							&& receipt.getStatus().equalsIgnoreCase("C"))
					|| ((reportType == 3 || reportType == 4)
							&& !receipt.getStatus().equalsIgnoreCase("C")))
			{
				for (ReceiptRecord receiptRecord : receipt.getReceiptRecords())
				{
					String[] receiptString = new String[12];
					receiptString[0] = receipt.getReceiptNo();
					receiptString[1] = receipt.getPaymentTypeName();
					receiptString[2] = receipt.getRecorderName();

					trustFund = false;
					for (Integer trustFundDepartmentVotID : trustFundDepartmentVotIDs)
					{
						if (receiptRecord
								.getDepartmentVotID() == trustFundDepartmentVotID)
						{
							receiptString[3] = receiptRecord.getBursaryVotCode()
									+ "-"
									+ receiptRecord.getDepartmentVotCode();
							receiptString[4] = "0";
							trustFund = true;
							break;
						}
					}

					if (trustFund == false)
					{
						receiptString[3] = "0";
						receiptString[4] = receiptRecord.getBursaryVotCode()
								+ "-" + receiptRecord.getDepartmentVotCode();
					}

					receiptString[5] = receiptRecord.getDescription();
					if (!receipt.getStatus().equalsIgnoreCase("C"))
					{
						receiptString[6] = "Diterima";
					}
					else
					{
						receiptString[6] = "Dibatal";
					}
					receiptString[7] = String.format("%,.2f",
							receiptRecord.getRate()
									.multiply(receiptRecord.getQuantity())
									.setScale(2, BigDecimal.ROUND_HALF_UP));
					receiptString[8] = dateFormat.format(receipt.getDate());
					if (receipt.getCategory() >= 1
							&& receipt.getCategory() <= 3)
					{
						receiptString[9] = receipt.getName();
					}
					else
					{
						if (receipt.getCategory() >= 4
								&& receipt.getCategory() <= 13)
						{
							receiptString[9] = receipt.getCompanyName();
						}
					}
					receiptString[10] = new SimpleDateFormat("HH:mm:ss")
							.format(receipt.getRecordTime());
					if (receipt.getCollectorStatement() != null)
					{
						receiptString[11] = receipt.getCollectorStatement();
					}
					else
					{
						receiptString[11] = "";
					}
					receiptsString.add(receiptString);
				}
			}
			else
			{
			}
		}
		return receiptsString;
	}

	ArrayList<ArrayList<String[]>> getReceiptsReportType5(Date startDate,
			Date endDate) throws SQLException
	{
		ArrayList<ArrayList<String[]>> receipts = new ArrayList<ArrayList<String[]>>();

		PreparedStatement ps = facade
				.prepareStatement("SELECT dv.DepartmentVotID "
						+ "FROM TrustFund tf, DepartmentVot dv "
						+ "WHERE tf.DepartmentVotID = dv.DepartmentVotID ");

		ArrayList<Integer> trustFundDepartmentVotIDs = new ArrayList<Integer>();

		ResultSet rs = ps.executeQuery();

		int departmentVotID = 0;

		while (rs.next())
		{
			departmentVotID = rs.getInt("DepartmentVotID");
			trustFundDepartmentVotIDs.add(departmentVotID);
		}

		PreparedStatement ps1 = facade.prepareStatement(
				"SELECT dv.DepartmentVotID, bv.Code AS BursaryVotCode, COUNT(rr.ReceiptID) AS NoOfTransactions, SUM(rr.Quantity*rr.Rate) AS Amount, IF(r.Status != \"C\", \"Terima\", \"Batal\") AS Status "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND r.Date BETWEEN ? AND ? "
						+ "GROUP BY bv.BursaryVotID, r.Status "
						+ "ORDER BY bv.Code, r.Status");

		ps1.setDate(1, toSQLDate(startDate));
		ps1.setDate(2, toSQLDate(endDate));

		ResultSet rs1 = ps1.executeQuery();

		ArrayList<String[]> receiptsInfo1 = new ArrayList<String[]>();
		boolean trustFund = false;

		while (rs1.next())
		{
			String[] receiptInfo1 = new String[5];

			trustFund = false;
			for (Integer trustFundDepartmentVotID : trustFundDepartmentVotIDs)
			{
				if (rs1.getInt("DepartmentVotID") == trustFundDepartmentVotID)
				{
					receiptInfo1[0] = rs1.getString("BursaryVotCode");
					receiptInfo1[1] = "";
					trustFund = true;
					break;
				}
			}

			if (trustFund == false)
			{
				receiptInfo1[0] = "";
				receiptInfo1[1] = rs1.getString("BursaryVotCode");
			}

			receiptInfo1[2] = Integer.toString(rs1.getInt("NoOfTransactions"));
			receiptInfo1[3] = String.format("%,.2f", rs1.getBigDecimal("Amount")
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			receiptInfo1[4] = rs1.getString("Status");
			receiptsInfo1.add(receiptInfo1);
		}
		receipts.add(receiptsInfo1);

		PreparedStatement ps2 = facade.prepareStatement(
				"SELECT p.Code AS PaymentTypeCode, p.Name AS PaymentTypeName, "
						+ "SUM(IF(r.Status != \"C\", 1, 0)) AS NoOfRecords, SUM(IF(r.Status != \"C\", r.GrandTotal, 0)) AS Amount, "
						+ "SUM(IF(r.Status = \"C\", 1, 0)) AS NoOfCancelledRecords, SUM(IF(r.Status = \"C\", r.GrandTotal, 0)) AS CancelledAmount "
						+ "FROM Receipt r, PaymentType p WHERE r.PaymentTypeID = p.PaymentTypeID AND r.Date BETWEEN ? AND ? "
						+ "GROUP BY p.PaymentTypeID "
						+ "ORDER BY p.PaymentTypeID");

		ps2.setDate(1, toSQLDate(startDate));
		ps2.setDate(2, toSQLDate(endDate));

		ResultSet rs2 = ps2.executeQuery();

		ArrayList<String[]> receiptsInfo2 = new ArrayList<String[]>();
		while (rs2.next())
		{
			String[] receiptInfo2 = new String[5];
			receiptInfo2[0] = rs2.getString("PaymentTypeName") + " ("
					+ rs2.getString("PaymentTypeCode") + ")";
			receiptInfo2[1] = Integer.toString(rs2.getInt("NoOfRecords"));
			receiptInfo2[2] = String.format("%,.2f", rs2.getBigDecimal("Amount")
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			receiptInfo2[3] = Integer
					.toString(rs2.getInt("NoOfCancelledRecords"));
			receiptInfo2[4] = Integer.toString(rs2.getInt("CancelledAmount"));
			receiptsInfo2.add(receiptInfo2);
		}
		receipts.add(receiptsInfo2);

		return receipts;
	}

	ArrayList<String[]> getLaporanKutipanHarianMengikutOperatorSepertiPadaTarikh(
			Date date, Staff staff) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT r.ReceiptNo, r.Name AS ReceiptName, pt.PaymentTypeID, r.RecordTime, "
						+ "IF(bv.BursaryVotID = 13 OR bv.BursaryVotID = 18, bv.Code, 0) AS KodAmanah, IF(bv.BursaryVotID != 13 AND bv.BursaryVotID != 18, bv.Code, 0) AS KodOsol, r.Status, r.GrandTotal AS Amount "
						+ "FROM Receipt r, PaymentType pt, Staff s, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE r.PaymentTypeID = pt.PaymentTypeID AND r.RecorderID = s.StaffID AND rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND "
						+ "r.Status != 'C' AND r.Date = ? AND r.RecorderID = ?");

		ps.setDate(1, toSQLDate(date));
		ps.setString(2, staff.getStaffID());

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> receiptsInfo = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] receiptInfo = new String[7];
			receiptInfo[0] = rs.getString("ReceiptNo");
			receiptInfo[1] = rs.getString("ReceiptName");
			receiptInfo[2] = Integer.toString(rs.getInt("PaymentTypeID"));
			receiptInfo[3] = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(rs.getTimestamp("RecordTime")).toString();
			receiptInfo[4] = rs.getString("KodAmanah");
			receiptInfo[5] = rs.getString("KodOsol");
			receiptInfo[6] = rs.getBigDecimal("Amount")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			receiptsInfo.add(receiptInfo);
		}
		return receiptsInfo;
	}

	ArrayList<String[]> getLaporanUrusniagaPadaBulanDanTahun(
			int month, int year, boolean trustFundCategory) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		ps = facade
				.prepareStatement("SELECT dv.DepartmentVotID "
						+ "FROM TrustFund tf, DepartmentVot dv "
						+ "WHERE tf.DepartmentVotID = dv.DepartmentVotID ");

		ArrayList<Integer> trustFundDepartmentVotIDs = new ArrayList<Integer>();

		rs = ps.executeQuery();

		int departmentVotID = 0;

		while (rs.next())
		{
			departmentVotID = rs.getInt("DepartmentVotID");
			trustFundDepartmentVotIDs.add(departmentVotID);
		}
				
		ps = facade.prepareStatement(
				"SELECT z.Tarikh, z.BursaryVotID, z.BursaryVotCode, z.BursaryVotName, z.BursaryVotType, z.JenisUrusniaga, z.NoDokumen, z.NoResit, z.Debit, z.Kredit "
						+ "FROM("
						+ "SELECT rr.DepartmentVotID, dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type AS BursaryVotType, r.Date AS Tarikh, \"140\" AS JenisUrusniaga, IFNULL(r.CollectorStatement, \"\") AS NoDokumen, r.ReceiptNo AS NoResit, \"0\" AS Debit, rr.Quantity*rr.Rate AS Kredit "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND r.Status != 'C' AND MONTH(r.Date) = ? AND YEAR(r.Date) = ? "
						+ "UNION ALL "
						+ "SELECT tf.DepartmentVotID, dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type AS BursaryVotType, v.Date AS Tarikh, \"120\" AS JenisUrusniaga, v.VoucherNo AS NoDokumen, \"\" AS NoResit, vr.Total AS Debit, \"0\" AS Kredit "
						+ "FROM Voucher v, VoucherRecord vr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND v.Status = 'A' AND MONTH(v.Date) = ? AND YEAR(v.Date) = ? "
						+ "UNION ALL "
						+ "SELECT tf.DepartmentVotID, dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type AS BursaryVotType, Date AS Tarikh, \"160\" AS JenisUrusniaga, j.JournalNo AS NoDokumen, \"\" AS NoResit, jr.Total AS Debit, \"0\" AS Kredit "
						+ "FROM Journal j, JournalRecord jr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE jr.JournalID = j.JournalID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND MONTH(j.Date) = ? AND YEAR(j.Date) = ? "
						+ "UNION ALL "
						+ "SELECT jr.DepartmentVotID, dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type AS BursaryVotType, j.Date AS Tarikh, \"160\" AS JenisUrusniaga, j.JournalNo AS NoDokumen, \"\" AS NoResit, \"0\" AS Debit, jr.Total AS Kredit "
						+ "FROM Journal j, JournalRecord jr, DepartmentVot dv, BursaryVot bv  "
						+ "WHERE jr.JournalID = j.JournalID AND jr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND MONTH(j.Date) = ? AND YEAR(j.Date) = ? "
						+ ") AS z ORDER BY z.BursaryVotCode, z.Tarikh");

		for (int i = 0; i < 4; i++)
		{
			ps.setInt((i * 2 + 1), month + 1);
			ps.setInt((i * 2 + 2), year);
		}
		
		rs = ps.executeQuery();

		ArrayList<String[]> trustFunds = new ArrayList<String[]>();
		ArrayList<String[]> revenueFunds = new ArrayList<String[]>();
		
		ArrayList<String[]> funds = new ArrayList<String[]>();

		while (rs.next())
		{
			String[] vot = new String[13];
			vot[0] = rs.getString("BursaryVotCode").toUpperCase() + "\t"
					+ rs.getString("BursaryVotName").toUpperCase();
			vot[1] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("Tarikh"));
			vot[2] = rs.getString("JenisUrusniaga");
			vot[3] = rs.getString("NoDokumen");
			vot[4] = rs.getString("NoResit");
			vot[5] = rs.getString("Debit");
			vot[6] = rs.getString("Kredit");
			vot[7] = "0.00";
			vot[8] = "0.00";
			vot[9] = rs.getString("BursaryVotID");
			vot[10] = rs.getString("BursaryVotType");
			vot[11] = "0";
			vot[12] = "0";
			funds.add(vot);
		}
				
		ps = facade.prepareStatement(
				"SELECT z.BursaryVotID, z.BursaryVotCode, z.BursaryVotName, z.JenisUrusniaga, z.Debit, z.Kredit "
						+ "FROM("
						+ "SELECT bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type, \"140\" AS JenisUrusniaga, \"0\" AS Debit, SUM(rr.Quantity*rr.Rate) AS Kredit "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND r.Status != 'C' AND r.Date BETWEEN ? AND ? "
						+ "GROUP BY bv.BursaryVotID "
						+ "UNION ALL "
						+ "SELECT bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type, \"120\" AS JenisUrusniaga, SUM(vr.Total) AS Debit, \"0\" AS Kredit "
						+ "FROM Voucher v, VoucherRecord vr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND v.Status = 'A' AND v.Date BETWEEN ? AND ? "
						+ "GROUP BY bv.BursaryVotID "
						+ "UNION ALL "
						+ "SELECT bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type, \"160\" AS JenisUrusniaga, SUM(jr.Total) AS Debit, \"0\" AS Kredit "
						+ "FROM Journal j, JournalRecord jr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE jr.JournalID = j.JournalID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND j.Date BETWEEN ? AND ? "
						+ "GROUP BY bv.BursaryVotID "
						+ "UNION ALL "
						+ "SELECT bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type, \"160\" AS JenisUrusniaga, \"0\" AS Debit, SUM(jr.Total) AS Kredit "
						+ "FROM Journal j, JournalRecord jr, DepartmentVot dv, BursaryVot bv  "
						+ "WHERE jr.JournalID = j.JournalID AND jr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND j.Date BETWEEN ? AND  ? "
						+ "GROUP BY bv.BursaryVotID "
						+ ") AS z ORDER BY z.BursaryVotCode");
	
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_YEAR, 1);    
		Date start = cal.getTime();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		Date end = cal.getTime();
		
		for (int i = 0; i < 4; i++)
		{
			ps.setDate((i * 2 + 1), toSQLDate(start));
			ps.setDate((i * 2 + 2), toSQLDate(end));
		}
		
		rs = ps.executeQuery();
		
		while (rs.next())
		{
			for(String[] fund : funds)
			{
				if(Integer.parseInt(fund[9]) == rs.getInt("BursaryVotID"))
				{
					fund[11] = (new BigDecimal(fund[11])).add(rs.getBigDecimal("Kredit")).toString();
					fund[11] = (new BigDecimal(fund[11])).subtract(rs.getBigDecimal("Debit")).toString();
				}				
			}
		}
		
		ps = facade.prepareStatement(
				"SELECT z.BursaryVotID, z.BursaryVotCode, z.BursaryVotName, z.JenisUrusniaga, z.Debit, z.Kredit "
						+ "FROM("
						+ "SELECT bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type, \"140\" AS JenisUrusniaga, \"0\" AS Debit, SUM(rr.Quantity*rr.Rate) AS Kredit "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND r.Status != 'C' AND YEAR(r.Date) = ? "
						+ "GROUP BY bv.BursaryVotID "
						+ "UNION ALL "
						+ "SELECT bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type, \"120\" AS JenisUrusniaga, SUM(vr.Total) AS Debit, \"0\" AS Kredit "
						+ "FROM Voucher v, VoucherRecord vr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND v.Status = 'A' AND YEAR(v.Date) = ? "
						+ "GROUP BY bv.BursaryVotID "
						+ "UNION ALL "
						+ "SELECT bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type, \"160\" AS JenisUrusniaga, SUM(jr.Total) AS Debit, \"0\" AS Kredit "
						+ "FROM Journal j, JournalRecord jr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE jr.JournalID = j.JournalID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND YEAR(j.Date) = ? "
						+ "GROUP BY bv.BursaryVotID "
						+ "UNION ALL "
						+ "SELECT bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, bv.Type, \"160\" AS JenisUrusniaga, \"0\" AS Debit, SUM(jr.Total) AS Kredit "
						+ "FROM Journal j, JournalRecord jr, DepartmentVot dv, BursaryVot bv  "
						+ "WHERE jr.JournalID = j.JournalID AND jr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND YEAR(j.Date) = ? "
						+ "GROUP BY bv.BursaryVotID "
						+ ") AS z ORDER BY z.BursaryVotCode");
		
		for (int i = 0; i < 4; i++)
		{
			ps.setInt((i + 1), (year - 1));
		}
		
		rs = ps.executeQuery();
		
		while (rs.next())
		{
			for(String[] fund : funds)
			{
				if(Integer.parseInt(fund[9]) == rs.getInt("BursaryVotID"))
				{
					fund[12] = (new BigDecimal(fund[12])).add(rs.getBigDecimal("Kredit")).toString();
					fund[12] = (new BigDecimal(fund[12])).subtract(rs.getBigDecimal("Debit")).toString();
				}
			}
		}
		
		for(String[] fund : funds)
		{
			if(fund[10].equalsIgnoreCase("R"))
			{
				revenueFunds.add(fund);
			}
			else
			{
				if(fund[10].equalsIgnoreCase("T"))
				{
					trustFunds.add(fund);
				}
			}
		}

		if(trustFundCategory == true)
		{
			return trustFunds;
		}
		else
		{
			return revenueFunds;
		}
		
	}

	ArrayList<String[]> getLaporanUrusniagaAkaunAmanahPadaBulanDanTahunBagiVot79501(
			int month, int year) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.Tarikh, z.JenisUrusniaga, z.NoDokumen, z.NoResit, z.Debit, z.Kredit "
						+ "FROM" + "("
						+ "SELECT r.Date AS Tarikh, \"140\" AS JenisUrusniaga, r.Notes AS NoDokumen, r.ReceiptNo AS NoResit, \"0\" AS Debit, rr.Quantity*rr.Rate AS Kredit "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND r.Status != 'C' AND bv.Code = \"79501\" AND MONTH(r.Date) = ? AND YEAR(r.Date) = ? "
						+ "UNION ALL "
						+ "SELECT v.Date AS Tarikh, \"120\" AS JenisUrusniaga, v.VoucherNo AS NoDokumen, \"\" AS NoResit, vr.Total AS Debit, \"0\" AS Kredit "
						+ "FROM Voucher v, VoucherRecord vr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND v.Status = 'A' AND bv.Code = \"79501\" AND MONTH(v.Date) = ? AND YEAR(v.Date) = ? "
						+ "UNION ALL "
						+ "SELECT j.Date AS Tarikh, \"160\" AS JenisUrusniaga, j.JournalNo AS NoDokumen, \"\" AS NoResit, jr.Total AS Debit, \"0\" AS Kredit "
						+ "FROM Journal j, JournalRecord jr, TrustFund tf, DepartmentVot dv, BursaryVot bv  "
						+ "WHERE jr.JournalID = j.JournalID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND bv.Code = \"79501\" AND MONTH(j.Date) = ? AND YEAR(j.Date) = ? "
						+ ") AS z ORDER BY z.Tarikh");

		for (int i = 0; i < 3; i++)
		{
			ps.setInt((i * 2 + 1), month);
			ps.setInt((i * 2 + 2), year);
		}

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> vots79501 = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] vot79501 = new String[7];
			vot79501[0] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("Tarikh"));
			vot79501[1] = rs.getString("JenisUrusniaga");
			vot79501[2] = rs.getString("NoDokumen");
			vot79501[3] = rs.getString("NoResit");
			vot79501[4] = rs.getString("Debit");
			vot79501[5] = rs.getString("Kredit");
			vots79501.add(vot79501);
		}
		return vots79501;
	}

	ArrayList<String[]> getLaporanUrusniagaAkaunAmanahPadaBulanDanTahunBagiVot79503(
			int month, int year) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.Tarikh, z.JenisUrusniaga, z.NoDokumen, z.NoResit, z.Debit, z.Kredit "
						+ "FROM " + "("
						+ "SELECT r.Date AS Tarikh, \"140\" AS JenisUrusniaga, r.Notes AS NoDokumen, r.ReceiptNo AS NoResit, \"0\" AS Debit, rr.Quantity*rr.Rate AS Kredit "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND r.Status != 'C' AND bv.Code = \"79503\" AND MONTH(r.Date) = ? AND YEAR(r.Date) = ? "
						+ "UNION ALL "
						+ "SELECT v.Date AS Tarikh, \"120\" AS JenisUrusniaga, v.VoucherNo AS NoDokumen, \"\" AS NoResit, vr.Total AS Debit, \"0\" AS Kredit "
						+ "FROM Voucher v, VoucherRecord vr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND v.Status = 'A' AND bv.Code = \"79503\" AND MONTH(v.Date) = ? AND YEAR(v.Date) = ? "
						+ "UNION ALL "
						+ "SELECT j.Date AS Tarikh, \"160\" AS JenisUrusniaga, j.JournalNo AS NoDokumen, \"\" AS NoResit, jr.Total AS Debit, \"0\" AS Kredit "
						+ "FROM Journal j, JournalRecord jr, TrustFund tf, DepartmentVot dv, BursaryVot bv  "
						+ "WHERE jr.JournalID = j.JournalID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND bv.Code = \"79503\" AND MONTH(j.Date) = ? AND YEAR(j.Date) = ? "
						+ ") AS z ORDER BY z.Tarikh");

		for (int i = 0; i < 3; i++)
		{
			ps.setInt((i * 2 + 1), month);
			ps.setInt((i * 2 + 2), year);
		}

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> vots79503 = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] vot79503 = new String[7];
			vot79503[0] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("Tarikh"));
			vot79503[1] = rs.getString("JenisUrusniaga");
			vot79503[2] = rs.getString("NoDokumen");
			vot79503[3] = rs.getString("NoResit");
			vot79503[4] = rs.getString("Debit");
			vot79503[5] = rs.getString("Kredit");
			vots79503.add(vot79503);
		}
		return vots79503;
	}

	String[] getReceiptHeader(long receiptID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT r.Name, r.ReceiptNo, r.Date, r.RecordTime, p.Code AS PaymentTypeCode, p.Name AS PaymentTypeName, r.ChequeNo, s.Name AS RecorderName, lc.RegistrationSerialNo "
						+ "FROM Receipt r, PaymentType p, Staff s, LoggingContractor lc  "
						+ "WHERE r.PaymentTypeID = p.PaymentTypeID AND r.RecorderID = s.StaffID AND lc.ReceiptID = r.ReceiptID AND r.ReceiptID = ?");

		ps.setLong(1, receiptID);

		ResultSet rs = ps.executeQuery();
		String[] receipt = new String[13];

		while (rs.next())
		{
			receipt[0] = "0800";
			receipt[1] = "JABATAN PERHUTANAN NEGERI";
			receipt[2] = "80000100";
			receipt[3] = "IBU PEJABAT JABATAN PERHUTANAN NEGERI";
			receipt[4] = rs.getString("Name");
			receipt[5] = rs.getString("RegistrationSerialNo");
			receipt[6] = rs.getString("ReceiptNo");
			receipt[7] = rs.getString("Date");
			receipt[8] = rs.getString("RecordTime");
			receipt[9] = rs.getString("PaymentTypeName");
			receipt[10] = rs.getString("ChequeNo");
			receipt[11] = rs.getString("Bank");
			receipt[12] = rs.getString("RecorderName");
		}
		return receipt;
	}

	ArrayList<String[]> getReceiptRecords(long receiptID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT rr.Description, rr.Rate, rr.Quantity, dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName "
						+ "FROM ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND rr.ReceiptID = ?");

		ps.setLong(1, receiptID);

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> receiptRecords = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] receiptRecord = new String[3];
			receiptRecord[0] = rs.getString("Description");
			receiptRecord[1] = rs.getString("BursaryVotCode");
			receiptRecord[2] = String.format("%,.2f",
					rs.getBigDecimal("Rate")
							.multiply(rs.getBigDecimal("Quantity"))
							.setScale(2, BigDecimal.ROUND_HALF_UP));
			receiptRecords.add(receiptRecord);
		}
		return receiptRecords;
	}

	ArrayList<String[]> getSenaraiBayaranBagiSemuaLesenPadaBulanDanTahun(
			Date date) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, r.Date, r.ReceiptNo, l.LicenseNo, lc.CompanyName, pt.Name AS PaymentTypeName, rr.Rate*rr.Quantity AS total "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, License l, LoggingContractor lc, PaymentType pt "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND r.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND r.PaymentTypeID = pt.PaymentTypeID AND r.Status != 'C' AND MONTH(r.Date) = ? AND YEAR(r.Date) = ? "
						+ "ORDER BY dv.Code, dv.Name ");

		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();

		ps.setInt(1, localDate.getMonthValue());
		ps.setInt(2, localDate.getYear());

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> listOfPaymentForLicenses = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] paymentForLicense = new String[8];
			paymentForLicense[0] = rs.getString("DepartmentVotCode");
			paymentForLicense[1] = rs.getString("DepartmentVotName");
			paymentForLicense[2] = rs.getString("Date");
			paymentForLicense[3] = rs.getString("ReceiptNo");
			paymentForLicense[4] = rs.getString("LicenseNo");
			paymentForLicense[5] = rs.getString("CompanyName");
			paymentForLicense[6] = rs.getString("PaymentTypeName");
			paymentForLicense[7] = String.format("%,.2f",
					rs.getBigDecimal("total").setScale(2,
							BigDecimal.ROUND_HALF_UP));
			listOfPaymentForLicenses.add(paymentForLicense);
		}
		return listOfPaymentForLicenses;
	}
	
	ArrayList<String[]> getSenaraiResitYangDibatalkanDariTarikhMulaHinggaAkhir(
			Date startDate, Date endDate) throws SQLException
	{
		ArrayList<Integer> trustFundsID = getTrustFundsID();
		
		PreparedStatement ps = facade.prepareStatement(
				"SELECT r.*, p.Code AS PaymentTypeCode, p.Name AS PaymentTypeName, " + 
				"s.Name AS RecorderName " + 
				"FROM Receipt r, PaymentType p, Staff s " + 
				"WHERE r.PaymentTypeID = p.PaymentTypeID AND r.RecorderID = s.StaffID AND r.Status = 'C' AND r.Date between ? AND ? ORDER BY r.Date, r.ReceiptNo");

			ps.setDate(1, toSQLDate(startDate));
			ps.setDate(2, toSQLDate(endDate));
			
			ArrayList<Receipt> receipts = getReceipts(ps.executeQuery());
			ArrayList<String[]> receiptsString = new ArrayList<String[]>();
			
			boolean trustFund = false;
			for(Receipt receipt : receipts)
			{
				String[] receiptString = new String[12];
				for(ReceiptRecord receiptRecord : receipt.getReceiptRecords())
				{
					receiptString[0] = receipt.getReceiptNo();
					receiptString[1] = receipt.getPaymentTypeName();
					receiptString[2] = receipt.getRecorderName();
					
					trustFund = false;
					for (Integer trustFundDepartmentVotID : trustFundsID)
					{
						if (receiptRecord.getDepartmentVotID() == trustFundDepartmentVotID)
						{
							receiptString[3] = receiptRecord.getBursaryVotCode() + "-" + receiptRecord.getDepartmentVotCode();
							receiptString[4] = "0";
							trustFund = true;
							break;
						}
					}

					if (trustFund == false)
					{
						receiptString[3] = "0";
						receiptString[4] = receiptRecord.getBursaryVotCode() + "-" + receiptRecord.getDepartmentVotCode();
					}

					receiptString[5] = receiptRecord.getDescription();

					receiptString[6] = "Dibatal";
					
					receiptString[7] = String.format("%,.2f", receiptRecord.getRate().multiply(receiptRecord.getQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP));
					receiptString[8] = new SimpleDateFormat("dd-MMM-yyyy").format(receipt.getDate());

					receiptString[9] = receipt.getName();
					
					receiptString[10] = new SimpleDateFormat("HH:mm:ss").format(receipt.getRecordTime());
					
					if (receipt.getCollectorStatement() != null)
					{
						receiptString[11] = receipt.getCollectorStatement();
					}
					else
					{
						receiptString[11] = "";
					}
				}
				receiptsString.add(receiptString);
			}
		
		return receiptsString;
	}

	ArrayList<String[]> getLaporanBagiPembatalanHasilDariTarikhMulaHinggaAkhir(
			Date startDate, Date endDate) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.TransactionType, z.TransactionNo, z.TransactionDate, z.TransactionCancelDate, z.Description, z.Total "
						+ "FROM " + "("
						+ "SELECT '1' AS TransactionType, ReceiptNo AS TransactionNo, Date AS TransactionDate, Date(RecordTime) AS TransactionCancelDate, Notes AS Description, GrandTotal AS Total "
						+ "FROM Receipt "
						+ "WHERE Status = 'C' AND Date BETWEEN ? AND ? "
						+ "UNION ALL "
						+ "SELECT '2' AS TransactionType, VoucherNo AS TransactionNo, Date AS TransactionDate, Date(RecordTime) AS TransactionCancelDate, Remarks AS Description, Total "
						+ "FROM Voucher "
						+ "WHERE Status = 'C' AND Date BETWEEN ? AND ? "
						+ "UNION ALL "
						+ "SELECT '3' AS TransactionType, JournalNo AS TransactionNo, Date AS TransactionDate, Date(RecordTime) AS TransactionCancelDate, Remarks AS Description, Total "
						+ "FROM Journal "
						+ "WHERE Status = 'C' AND Date BETWEEN ? AND ? "
						+ ") AS z "
						+ "ORDER BY z.TransactionType, z.TransactionDate");

		for (int i = 0; i < 3; i++)
		{
			ps.setDate(i * 2 + 1, toSQLDate(startDate));
			ps.setDate(i * 2 + 2, toSQLDate(endDate));
		}

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> listOfCancelledTransactions = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] cancelledTransaction = new String[6];
			cancelledTransaction[0] = rs.getString("TransactionType");
			cancelledTransaction[1] = rs.getString("TransactionNo");
			cancelledTransaction[2] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("TransactionDate"));
			cancelledTransaction[3] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("TransactionCancelDate"));
			cancelledTransaction[4] = rs.getString("Description");
			cancelledTransaction[5] = String.format("%,.2f",
					rs.getBigDecimal("Total").setScale(2,
							BigDecimal.ROUND_HALF_UP));
			listOfCancelledTransactions.add(cancelledTransaction);
		}
		return listOfCancelledTransactions;
	}

	ArrayList<String[]> getLaporanKutipanHarianPadaTarikh(Date date)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.TransactionType, z.TransactionNo, z.TransactionDate, z.Description, z.Total "
						+ "FROM " + "("
						+ "SELECT '1' AS TransactionType, ReceiptNo AS TransactionNo, Date AS TransactionDate, Notes AS Description, GrandTotal AS Total "
						+ "FROM Receipt " + "WHERE Status <> 'C' AND Date = ? "
						+ "UNION ALL "
						+ "SELECT '2' AS TransactionType, VoucherNo AS TransactionNo, Date AS TransactionDate, Remarks AS Description, Total "
						+ "FROM Voucher " + "WHERE Status = 'A' AND Date = ? "
						+ "UNION ALL "
						+ "SELECT '3' AS TransactionType, JournalNo AS TransactionNo, Date AS TransactionDate, Remarks AS Description, Total "
						+ "FROM Journal " + "WHERE Status = 'A' AND Date = ? "
						+ ") AS z "
						+ "ORDER BY z.TransactionType, z.TransactionDate");

		for (int i = 0; i < 3; i++)
		{
			ps.setDate(i + 1, toSQLDate(date));
		}

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> listOfDailyTransactions = new ArrayList<String[]>();
		while (rs.next())
		{
			String[] dailyTransaction = new String[5];
			dailyTransaction[0] = rs.getString("TransactionType");
			dailyTransaction[1] = rs.getString("TransactionNo");
			dailyTransaction[2] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("TransactionDate"));
			dailyTransaction[3] = rs.getString("Description");
			dailyTransaction[4] = String.format("%,.2f",
					rs.getBigDecimal("Total").setScale(2,
							BigDecimal.ROUND_HALF_UP));
			listOfDailyTransactions.add(dailyTransaction);
		}
		return listOfDailyTransactions;
	}

	String[] getHeaderPenyataAkaunHasilMengikutJabatanPadaBulanDanTahun()
			throws SQLException
	{
		String[] header = new String[4];
		header[0] = "0080";
		header[1] = "80000100";
		header[2] = "JABATAN PERHUTANAN NEGERI";
		header[3] = "IBU PEJABAT JABATAN PERHUTANAN NEGERI";
		return header;
	}

	ArrayList<String[]> getContentPenyataAkaunHasilMengikutJabatanPadaBulanDanTahun(
			int month, int year) throws SQLException
	{
		ArrayList<String[]> contents = new ArrayList<String[]>();
		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.BursaryVotCode, z.BursaryVotName, SUM(z.Debit) AS Debit, SUM(z.Credit) AS Credit "
						+ "FROM ( "
						+ "SELECT bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, 0 AS Debit, rr.Quantity*rr.Rate AS Credit "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND r.Status != 'C'  AND bv.Type = 'R' AND MONTH(r.Date) = ? AND YEAR(r.Date) = ? "
						+ "UNION ALL "
						+ "SELECT bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, vr.Total AS Debit, 0 AS Credit "
						+ "FROM Voucher v, VoucherRecord vr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND v.Status = 'A' AND bv.Type = 'R' AND MONTH(v.Date) = ? AND YEAR(v.Date) = ? "
						+ "UNION ALL "
						+ "SELECT bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, 0 AS Debit, jr.Total AS Credit "
						+ "FROM Journal j, JournalRecord jr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE jr.JournalID = j.JournalID AND jr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A'  AND bv.Type = 'R' AND MONTH(j.Date) = ? AND YEAR(j.Date) = ? "
						+ "UNION ALL "
						+ "SELECT bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, jr.Total AS Debit, 0 AS Credit "
						+ "FROM Journal j, JournalRecord jr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE jr.JournalID = j.JournalID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND bv.Type = 'R' AND MONTH(j.Date) = ? AND YEAR(j.Date) = ? "
						+ ") AS z GROUP BY z.BursaryVotCode ORDER BY z.BursaryVotCode");

		for (int i = 0; i < 4; i++)
		{
			ps.setInt((i * 2 + 1), month + 1);
			ps.setInt((i * 2 + 2), year);
		}

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			String[] content = new String[11];
			content[0] = rs.getString("BursaryVotCode").toUpperCase();
			content[1] = rs.getString("BursaryVotName").toUpperCase();
			content[2] = "0.00";
			content[3] = "0.00";
			content[4] = rs.getBigDecimal("Debit").toString();
			content[5] = rs.getBigDecimal("Credit").toString();
			content[6] = "0.00";
			content[7] = "0.00";
			content[8] = "0.00";
			content[9] = "0.00";
			content[10] = "0.00";
			contents.add(content);
		}
		return contents;
	}

	String[] getHeaderPenyataAkaunAmanahMengikutJabatanPadaBulanDanTahun()
			throws SQLException
	{
		String[] header = new String[4];
		header[0] = "0080";
		header[1] = "80000100";
		header[2] = "JABATAN PERHUTANAN NEGERI";
		header[3] = "IBU PEJABAT JABATAN PERHUTANAN NEGERI";
		return header;
	}

	ArrayList<String[]> getContentPenyataAkaunAmanahMengikutJabatanPadaBulanDanTahun(
			int month, int year) throws SQLException
	{
		ArrayList<String[]> contents = new ArrayList<String[]>();

		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.BursaryVotCode, z.BursaryVotName, SUM(z.Debit) AS Debit, SUM(z.Credit) AS Credit "
						+ "FROM ( "
						+ "SELECT bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, 0 AS Debit, rr.Quantity*rr.Rate AS Credit "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND r.Status != 'C' AND bv.Type = 'T' AND MONTH(r.Date) = ? AND YEAR(r.Date) = ? "
						+ "UNION ALL "
						+ "SELECT bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, vr.Total AS Debit, 0 AS Credit "
						+ "FROM Voucher v, VoucherRecord vr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND v.Status = 'A' AND bv.Type = 'T' AND MONTH(v.Date) = ? AND YEAR(v.Date) = ? "
						+ "UNION ALL "
						+ "SELECT bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, 0 AS Debit, jr.Total AS Credit "
						+ "FROM Journal j, JournalRecord jr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE jr.JournalID = j.JournalID AND jr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND bv.Type = 'T' AND MONTH(j.Date) = ? AND YEAR(j.Date) = ? "
						+ "UNION ALL "
						+ "SELECT bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName, jr.Total AS Debit, 0 AS Credit "
						+ "FROM Journal j, JournalRecord jr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE jr.JournalID = j.JournalID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND bv.Type = 'T' AND MONTH(j.Date) = ? AND YEAR(j.Date) = ? "
						+ ") AS z GROUP BY z.BursaryVotCode ORDER BY z.BursaryVotCode");

		for (int i = 0; i < 4; i++)
		{
			ps.setInt((i * 2 + 1), month + 1);
			ps.setInt((i * 2 + 2), year);
		}

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			String[] content = new String[9];
			content[0] = rs.getString("BursaryVotCode").toUpperCase();
			content[1] = rs.getString("BursaryVotName").toUpperCase();
			content[2] = "0.00";
			content[3] = rs.getBigDecimal("Debit").toString();
			content[4] = rs.getBigDecimal("Credit").toString();
			content[5] = "0.00";
			content[6] = "0.00";
			content[7] = "0.00";
			content[8] = "0.00";
			contents.add(content);
		}
		return contents;
	}

	String[] getHeaderLaporanUrusniagaAkaunHasilPadaBulanDanTahun()
			throws SQLException
	{
		String[] header = new String[5];
		header[0] = "2017";
		header[1] = "0080" + "\t" + "JABATAN PERHUTANAN NEGERI";
		header[2] = "80000100" + "\t" + "IBU PEJABAT JABATAN PERHUTANAN NEGERI";
		header[3] = "0080" + "\t" + "JABATAN PERHUTANAN NEGERI";
		header[4] = "80000100" + "\t" + "IBU PEJABAT JABATAN PERHUTANAN NEGERI";
		return header;
	}

	ArrayList<String[]> getContentLaporanUrusniagaAkaunHasilPadaBulanDanTahun(
			int month, int year) throws SQLException
	{
		ArrayList<String[]> contents = new ArrayList<String[]>();
		PreparedStatement ps = facade.prepareStatement(
				"SELECT z.DepartmentVotCode, z.DepartmentVotName, z.Tarikh, z.JenisUrusniaga, z.NoDokumen, z.NoResit, z.Debit, z.Kredit FROM ("
						+ "SELECT dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, r.Date AS Tarikh, \"140\" AS JenisUrusniaga, r.Notes AS NoDokumen, r.ReceiptNo AS NoResit, \"0\" AS Debit, rr.Quantity*rr.Rate AS Kredit "
						+ "FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv "
						+ "WHERE rr.ReceiptID = r.ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND r.Status != 'C' AND bv.Code != \"79501\" AND bv.Code != \"79503\" AND MONTH(r.Date) = ? AND YEAR(r.Date) = ? "
						+ "UNION ALL "
						+ "SELECT dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, v.Date AS Tarikh, \"120\" AS JenisUrusniaga, v.VoucherNo AS NoDokumen, \"\" AS NoResit, vr.Total AS Debit, \"0\" AS Kredit "
						+ "FROM Voucher v, VoucherRecord vr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND v.Status = 'A' AND bv.Code != \"79501\" AND bv.Code != \"79503\"  AND MONTH(v.Date) = ? AND YEAR(v.Date) = ? "
						+ "UNION ALL "
						+ "SELECT dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, j.Date AS Tarikh, \"160\" AS JenisUrusniaga, j.JournalNo AS NoDokumen, \"\" AS NoResit, jr.Total AS Debit, \"0\" AS Kredit "
						+ "FROM Journal j, JournalRecord jr, TrustFund tf, DepartmentVot dv, BursaryVot bv  "
						+ "WHERE jr.JournalID = j.JournalID AND jr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND j.Status = 'A' AND bv.Code = \"79501\" AND bv.Code != \"79503\" AND MONTH(j.Date) = ? AND YEAR(j.Date) = ? "
						+ ") AS z ORDER BY z.Tarikh");

		for (int i = 0; i < 3; i++)
		{
			ps.setInt((i * 2 + 1), month);
			ps.setInt((i * 2 + 2), year);
		}

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			String[] content = new String[9];
			content[0] = rs.getString("DepartmentVotCode").toUpperCase() + "\t"
					+ rs.getString("DepartmentVotName").toUpperCase();
			content[1] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("Tarikh"));
			content[2] = rs.getString("JenisUrusniaga");
			content[3] = rs.getString("NoDokumen");
			content[4] = rs.getString("NoResit");
			content[5] = rs.getString("Debit");
			content[6] = rs.getString("Kredit");
			content[7] = "0.00";
			content[8] = "0.00";
			contents.add(content);
		}
		return contents;
	}

	String[] getHeaderPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir()
			throws SQLException
	{
		String[] header = new String[5];
		header[0] = "MUDAH BUAT ENTERPRISE";
		header[1] = "L20262011";
		header[2] = "NEGERI SEMBILAN BARAT";
		header[3] = "JANUARI HINGGA DISEMBER";
		header[4] = "2018";
		return header;
	}

	ArrayList<String[]> getContentPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
			Date startDate, Date endDate, long licenseID) throws SQLException
	{
		ArrayList<String[]> contents = new ArrayList<String[]>();

		String[] row1 = new String[7];
		row1[0] = "11/10/2011";
		row1[1] = "WANG AMANAH LESEN/LESEN PEMINDAH";
		row1[2] = "TR016301";
		row1[3] = "K";
		row1[4] = "10000.00";
		row1[5] = "";
		row1[6] = "";
		contents.add(row1);

		String[] row2 = new String[6];
		row2[0] = "11/10/2011";
		row2[1] = "PENDAFTARAN TUKUL TANDA HARTA";
		row2[2] = "TR016302";
		row2[3] = "H";
		row2[4] = "";
		row2[5] = "";
		row2[6] = "50.00";
		contents.add(row2);

		String[] row3 = new String[6];
		row3[0] = "11/10/2011";
		row3[1] = "FEE LESEN MENGAMBIL HASIL HUTAN";
		row3[2] = "TR016302";
		row3[3] = "H";
		row3[4] = "";
		row3[5] = "";
		row3[6] = "50.00";
		contents.add(row3);

		String[] row4 = new String[6];
		row4[0] = "31/10/2011";
		row4[1] = "PEMINDAHAN AMANAH K. KAYU KE AKAUN HASIL (ROYALTI)";
		row4[2] = "TR016302";
		row4[3] = "D";
		row4[4] = "";
		row4[5] = "5000.00";
		row4[6] = "";
		contents.add(row4);
		return contents;
	}

	String[] getBalancePenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir()
			throws SQLException
	{
		String[] balance = new String[2];
		balance[0] = "5000.00";
		balance[1] = "10000.00";
		return balance;
	}

	String[] getCessPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir()
			throws SQLException
	{
		String[] cess = new String[3];
		cess[0] = "60003";
		cess[1] = "SES HASIL HUTAN";
		cess[2] = "0.00";

		return cess;
	}

	ArrayList<String[]> getTrailerPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
			Date startDate, Date endDate, long licenseID) throws SQLException
	{
		ArrayList<String[]> trailers = new ArrayList<String[]>();

		String[] trailer1 = new String[3];
		trailer1[0] = "61801";
		trailer1[1] = "ROYALTI HASIL HUTAN";
		trailer1[2] = "0.00";
		trailers.add(trailer1);

		String[] trailer2 = new String[3];
		trailer2[0] = "71701";
		trailer2[1] = "FEE LESEN MENGAMBIL HASIL HUTAN";
		trailer2[2] = "50.00";
		trailers.add(trailer2);

		String[] trailer3 = new String[3];
		trailer3[0] = "71801";
		trailer3[1] = "PENDAFTARAN TUKUL TANDA HARTA";
		trailer3[2] = "50.00";
		trailers.add(trailer3);

		return trailers;
	}
}