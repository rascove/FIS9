package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.hall.controller.manager.HallFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.model.Voucher;

/**
 * @author Nor Azman Mat Ariff
 */
class VoucherManager extends RevenueTableManager
{
	VoucherManager(RevenueFacade facade)
	{
		super(facade);
	}

	HallFacade hFacade = new HallFacade();

	private int write(Voucher voucher, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, voucher.getVoucherID());
		ps.setString(2, voucher.getVoucherNo().replaceAll("\\s", "").toUpperCase());
		ps.setString(3, voucher.getRemarks());
		ps.setDate(4, toSQLDate(voucher.getDate()));
		ps.setInt(5, voucher.getCategory());
		nullable(ps, 6, voucher.getLicenseID());
		nullable(ps, 7, voucher.getPermitID());
		ps.setString(8, voucher.getRecorderID());
		ps.setTimestamp(9, voucher.getRecordTime());
		ps.setBigDecimal(10, voucher.getTotal());
		ps.setString(11, voucher.getStatus());

		return ps.executeUpdate();
	}

	int addVoucher(Voucher voucher) throws SQLException
	{
		int status = 0;
		PreparedStatement ps = null;
		if (checkExistingVoucherNo(voucher) == false)
		{
			ps = facade.prepareStatement(
					"INSERT INTO Voucher (VoucherID, VoucherNo, Remarks, Date, Category, LicenseID, PermitID, RecorderID, RecordTime, Total, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			status = write(voucher, ps);
		}

		return status;
	}

	private boolean checkExistingVoucherNo(Voucher voucher) throws SQLException
	{
		boolean exist = false;
		PreparedStatement ps = facade.prepareStatement(
				"SELECT * FROM Voucher WHERE VoucherNo = ? AND Status = 'A'");
		ps.setString(1, voucher.getVoucherNo());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			exist = true;
		}

		return exist;
	}

	int deleteVoucher(Voucher voucher) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Voucher SET Status = 'I' WHERE VoucherID = ?");
		ps.setLong(1, voucher.getVoucherID());

		return ps.executeUpdate();
	}

	int updateStatusVoucher(Voucher voucher) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE Voucher SET Status = ? WHERE VoucherID = ?");
		ps.setString(1, voucher.getStatus());
		ps.setLong(2, voucher.getVoucherID());

		return ps.executeUpdate();
	}

	private Voucher read(ResultSet rs) throws SQLException
	{
		Voucher voucher = new Voucher();

		voucher.setVoucherID(rs.getLong("VoucherID"));
		voucher.setVoucherNo(rs.getString("VoucherNo"));
		voucher.setRemarks(rs.getString("Remarks"));
		voucher.setDate(rs.getDate("Date"));
		voucher.setCategory(rs.getInt("Category"));
		voucher.setLicenseID(rs.getLong("LicenseID"));
		voucher.setPermitID(rs.getLong("PermitID"));
		voucher.setRecorderID(rs.getString("RecorderID"));
		voucher.setRecorderName(rs.getString("RecorderName"));
		voucher.setRecordTime(rs.getTimestamp("RecordTime"));
		voucher.setTotal(rs.getBigDecimal("Total"));
		voucher.setStatus(rs.getString("Status"));

		if (voucher.getCategory() == 0)
		{
			readLicense(voucher);
		}
		else
		{
			if (voucher.getCategory() == 1)
			{
				readPermit(voucher);
			}
			else
			{
				
			}
		}

		voucher.setVoucherRecords(facade.getVoucherRecords(voucher));

		return voucher;
	}

	private Voucher readLicense(Voucher voucher) throws SQLException
	{
		PreparedStatement ps = facade
				.prepareStatement("SELECT l.licenseNo, lc.CompanyName AS LicenseeCompanyName, lt.LicenseTypeID, lc.RegistrationSerialNo AS LicenseeNo "
						+ "FROM License l, LoggingContractor lc, LicenseType lt "
						+ "WHERE l.LicenseeID = lc.LoggingContractorID AND l.LicenseTypeID = lt.LicenseTypeID AND l.LicenseID = ?");
		
		ps.setLong(1, voucher.getLicenseID());
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			voucher.setLicenseNo(rs.getString("licenseNo"));
			voucher.setLicenseeCompanyName(rs.getString("LicenseeCompanyName"));
			voucher.setLicenseTypeID(rs.getInt("licenseTypeID"));
			voucher.setLicenseeNo(rs.getString("LicenseeNo"));
		}
		
		return voucher;
	}
	
	private Voucher readPermit(Voucher voucher) throws SQLException
	{
		PreparedStatement ps = facade
				.prepareStatement("SELECT p.PermitNo, l.LicenseNo, lc.CompanyName AS LicenseeCompanyName, pt.PermitTypeID, lc.RegistrationSerialNo AS LicenseeNo "
						+ "FROM Permit p LEFT JOIN License l ON p.LicenseID = l.LicenseID LEFT JOIN LoggingContractor lc ON l.LicenseeID = lc.LoggingContractorID JOIN PermitType pt "
						+ "WHERE p.PermitTypeID = pt.PermitTypeID AND p.PermitID = ?");
		
		ps.setLong(1, voucher.getPermitID());
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			voucher.setLicenseNo(rs.getString("LicenseNo"));
			voucher.setPermitNo(rs.getString("PermitNo"));
			voucher.setLicenseeCompanyName(rs.getString("LicenseeCompanyName"));
			voucher.setPermitTypeID(rs.getInt("PermitTypeID"));
			voucher.setLicenseeNo(rs.getString("LicenseeNo"));
		}
		
		return voucher;
	}

	private ArrayList<Voucher> getVouchers(ResultSet rs) throws SQLException
	{
		ArrayList<Voucher> vouchers = new ArrayList<>();

		while (rs.next())
		{
			Voucher voucher = read(rs);
			vouchers.add(voucher);
		}
		return vouchers;
	}

	Voucher getVoucher(long voucherID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT v.*, l.LicenseNo, lc.RegistrationSerialNo AS LicenseeNo, lc.CompanyName AS LicenseeCompanyName, lc.Address, lc.TelNo, d.DistrictID, d.Name AS DistrictName, s.StateID, s.Name AS StateName, s.Name AS RecorderName  "
						+ "FROM Voucher v, License l, LoggingContractor lc, Hall h, District d, State stt, Staff s "
						+ "WHERE v.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND l.HallID = h.HallID AND h.DistrictID = d.DistrictID AND d.StateID = stt.StateID AND v.RecorderID = s.StaffID "
						+ "AND v.VoucherID = ?");

		ps.setLong(1, voucherID);

		ResultSet rs = ps.executeQuery();

		return read(rs);
	}

	ArrayList<Voucher> getVouchers(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT v.*, s.Name AS RecorderName  "
						+ "FROM Voucher v, Staff s "
						+ "WHERE v.RecorderID = s.StaffID "
						+ "AND v.Status = ?");
		ps.setString(1, status);

		return getVouchers(ps.executeQuery());
	}

	ArrayList<Voucher> getVouchers(Staff staff) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT v.*, s.Name AS RecorderName  "
						+ "FROM Voucher v, Staff s "
						+ "WHERE v.RecorderID = s.StaffID "
						+ "AND s.StaffID = ?");
		ps.setString(1, staff.getStaffID());

		return getVouchers(ps.executeQuery());
	}

	ArrayList<Voucher> getVouchers(District district) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT v.*, l.LicenseNo, lc.RegistrationSerialNo AS LicenseeNo, lc.CompanyName AS LicenseeCompanyName, lc.Address, lc.TelNo, d.DistrictID, d.Name AS DistrictName, s.StateID, s.Name AS StateName, s.Name AS RecorderName  "
						+ "FROM Voucher v, License l, LoggingContractor lc, Hall h, District d, State stt, Staff s "
						+ "WHERE v.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND l.HallID = h.HallID AND h.DistrictID = d.DistrictID AND d.StateID = stt.StateID AND v.RecorderID = s.StaffID "
						+ "AND d.DistrictID = ?");
		ps.setInt(1, district.getDistrictID());

		return getVouchers(ps.executeQuery());
	}

	ArrayList<Voucher> getVouchers(State state) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT v.*, l.LicenseNo, lc.RegistrationSerialNo AS LicenseeNo, lc.CompanyName AS LicenseeCompanyName, lc.Address, lc.TelNo, d.DistrictID, d.Name AS DistrictName, s.StateID, s.Name AS StateName, s.Name AS RecorderName  "
						+ "FROM Voucher v, License l, LoggingContractor lc, Hall h, District d, State stt, Staff s "
						+ "WHERE v.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND l.HallID = h.HallID AND h.DistrictID = d.DistrictID AND d.StateID = stt.StateID AND v.RecorderID = s.StaffID "
						+ "AND stt.StateID = ?");
		ps.setInt(1, state.getStateID());

		return getVouchers(ps.executeQuery());
	}

	String[] getVoucherHeader(long voucherID, int category) throws SQLException
	{
		String query = null;
		if(category == 0)
		{
			query = "SELECT YEAR(v.Date) AS Tahun, v.voucherNo, v.Date AS Tarikh, lc.CompanyName AS NamaPenerima, lc.RegistrationSerialNo AS NoKPNoDaftarSyarikat, v.Remarks AS PerihalBayaran, s.Name AS Penyedia "
					+ "FROM Voucher v, License l, LoggingContractor lc, Staff s "
					+ "WHERE v.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND v.RecorderID = s.StaffID  AND v.VoucherID = ?";
		}
		else
		{
			if(category == 1)
			{
				query = "SELECT YEAR(v.Date) AS Tahun, v.voucherNo, v.Date AS Tarikh, lc.CompanyName AS NamaPenerima, lc.RegistrationSerialNo AS NoKPNoDaftarSyarikat, v.Remarks AS PerihalBayaran, s.Name AS Penyedia "
						+ "FROM Voucher v, Permit p, License l, LoggingContractor lc, Staff s "
						+ "WHERE v.PermitID = p.PermitID AND p.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND v.RecorderID = s.StaffID  AND v.VoucherID = ?";
			}
		}
		PreparedStatement ps = facade.prepareStatement(query);

		ps.setLong(1, voucherID);

		ResultSet rs = ps.executeQuery();

		String[] voucher = new String[20];

		while (rs.next())
		{
			voucher[0] = rs.getString("Tahun");
			voucher[1] = "120";
			voucher[2] = rs.getString("VoucherNo");
			voucher[3] = new SimpleDateFormat("dd/MM/yyyy")
					.format(rs.getDate("Tarikh"));
			voucher[4] = "0800";
			voucher[5] = "JABATAN PERHUTANAN NEGERI";
			voucher[6] = "80000100";
			voucher[7] = "IBU PEJABAT JABATAN PERHUTANAN NEGERI";
			voucher[8] = rs.getString("NamaPenerima");
			voucher[9] = rs.getString("NoKPNoDaftarSyarikat");
			voucher[10] = "";
			voucher[11] = "";
			voucher[12] = "";
			voucher[13] = rs.getString("PerihalBayaran");
			voucher[14] = "";
			voucher[15] = "";
			voucher[16] = "";
			voucher[17] = "";
			voucher[18] = "";
			voucher[19] = rs.getString("Penyedia");
		}

		return voucher;
	}

	ArrayList<String[]> getVoucherRecords(long voucherID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT bv.Code AS KodAmanah, vr.Total AS Amaun "
						+ "FROM Voucher v, VoucherRecord vr, TrustFund tf, DepartmentVot dv, BursaryVot bv "
						+ "WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND v.VoucherID = ?");

		ps.setLong(1, voucherID);

		ResultSet rs = ps.executeQuery();

		ArrayList<String[]> voucherRecords = new ArrayList<String[]>();

		while (rs.next())
		{
			String[] voucherRecord = new String[4];
			voucherRecord[0] = "0800";
			voucherRecord[1] = "8000100";
			voucherRecord[2] = rs.getString("KodAmanah");
			voucherRecord[3] = rs.getBigDecimal("Amaun")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			voucherRecords.add(voucherRecord);
		}

		return voucherRecords;
	}

	String[] getLaporanKedudukanKewanganLesen(long licenseID)
			throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement(
				"SELECT tp.* "
						+ "FROM TransferPass tp, License l  "
						+ "WHERE tp.LicenseID = l.LicenseID AND l.LicenseID = ? AND tp.Status != 'I'");
		
		ps.setLong(1, licenseID);

		ResultSet rs = ps.executeQuery();
		
		boolean transferPassExist = true; 
		
		if(!rs.next()) 
		{
			transferPassExist = false; 
		}
		else
		{
			transferPassExist = true; 
		}
		
		String query = "";
			
		if(transferPassExist == true)
		{
			query = "SELECT l.LicenseNo, lc.CompanyName, l.LicenseFund, SUM(tp.RoyaltyAmount + tp.CessAmount) AS KutipanRoyaltiSes "
					+ "FROM TransferPass tp, License l, LoggingContractor lc "
					+ "WHERE tp.LicenseID = l.LicenseID AND l.LicenseeID = lc.LoggingContractorID AND l.LicenseID = ? AND tp.Status = 'P' "
					+ "GROUP BY l.LicenseID";
		}
		else
		{
			query = "SELECT l.LicenseNo, lc.CompanyName, l.LicenseFund, 0 AS KutipanRoyaltiSes "
					+ "FROM License l, LoggingContractor lc "
					+ "WHERE l.LicenseeID = lc.LoggingContractorID AND l.LicenseID = ?";
		}
		
		ps = facade.prepareStatement(query);

		ps.setLong(1, licenseID);

		rs = ps.executeQuery();

		String[] data = new String[9];

		if (rs.next())
		{
			data[0] = rs.getString("LicenseNo");
			data[1] = rs.getString("CompanyName");
			data[2] = rs.getString("LicenseFund");
			data[3] = "0.00";
			data[4] = "0.00";
			data[5] = "0.00";			
			data[6] = "0.00";
			data[7] = rs.getBigDecimal("KutipanRoyaltiSes")
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			data[8] = "0.00";
		}
		
		ps = facade.prepareStatement(
				"SELECT SUM(ROUND(rr.Rate*rr.Quantity, 2)) AS KutipanWangCagaran " + 
				"FROM Receipt r, ReceiptRecord rr, License l, DepartmentVot dv, TrustFund tf " + 
				"WHERE rr.ReceiptId = r.ReceiptID AND r.LicenseID = l.LicenseID AND rr.DepartmentVotID = dv.DepartmentVotID AND tf.DepartmentVotID = dv.DepartmentVotID AND tf.Symbol = \"WAKK\" AND r.Category IN (6, 10) AND r.Status = 'A' AND l.LicenseID = ?");
		
		ps.setLong(1, licenseID);
		
		rs = ps.executeQuery();
		
		while (rs.next())
		{
			
			data[6] = rs.getBigDecimal("KutipanWangCagaran").toString();
		}
		
		ps = facade.prepareStatement(
				"SELECT IFNULL(p.PermitFund, 0) AS WangAmanahMatau " + 
				"FROM Permit p, License l, PermitType pt " + 
				"WHERE p.LicenseID = l.LicenseID AND p.PermitTypeID = pt.PermitTypeID AND pt.PermitTypeID = 1 AND l.LicenseID = ?");
		
		ps.setLong(1, licenseID);
		
		rs = ps.executeQuery();
		
		while (rs.next())
		{
			
			data[4] = rs.getBigDecimal("WangAmanahMatau").toString();
		}
		
		ps = facade.prepareStatement(
				"SELECT IFNULL(p.PermitFund, 0) AS WangAmanahJalan " + 
				"FROM Permit p, License l, PermitType pt " + 
				"WHERE p.LicenseID = l.LicenseID AND p.PermitTypeID = pt.PermitTypeID AND pt.PermitTypeID = 3 AND l.LicenseID = ?");
		
		ps.setLong(1, licenseID);
		
		rs = ps.executeQuery();
		
		while (rs.next())
		{
			
			data[3] = rs.getBigDecimal("WangAmanahJalan").toString();
		}
		
		ps = facade.prepareStatement(
				"SELECT IFNULL(p.PermitFund, 0) AS WangAmanahKongsi " + 
				"FROM Permit p, License l, PermitType pt " + 
				"WHERE p.LicenseID = l.LicenseID AND p.PermitTypeID = pt.PermitTypeID AND pt.PermitTypeID = 2 AND l.LicenseID = ?");
		
		ps.setLong(1, licenseID);
		
		rs = ps.executeQuery();
		
		while (rs.next())
		{
			
			data[5] = rs.getBigDecimal("WangAmanahKongsi").toString();
		}

		return data;
	}
}