package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.revenue.model.Voucher;
import my.edu.utem.ftmk.fis9.revenue.model.VoucherRecord;

/**
 * @author Nor Azman Mat Ariff
 */
class VoucherRecordManager extends RevenueTableManager
{
	VoucherRecordManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(VoucherRecord voucherRecord, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, voucherRecord.getVoucherRecordID());
		ps.setLong(2, voucherRecord.getVoucherID());
		ps.setInt(3, voucherRecord.getTrustFundID());
		ps.setString(4, voucherRecord.getDescription());	
		ps.setBigDecimal(5, voucherRecord.getTotal());

		return ps.executeUpdate();
	}

	int addVoucherRecord(VoucherRecord voucherRecord) throws SQLException
	{
		PreparedStatement ps = null;
		ps = facade.prepareStatement("INSERT INTO VoucherRecord (VoucherRecordID, VoucherID, TrustFundID, Description, Total) VALUES (?, ?, ?, ?, ?)");
		int status = write(voucherRecord, ps);

		return status;
	}
	
	int updateVoucherRecord(VoucherRecord voucherRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE VoucherRecord SET VoucherID = ?, TrustFundID = ?, Description = ?, Total = ? WHERE VoucherRecordID = ?");
		ps.setLong(1, voucherRecord.getVoucherRecordID());

		return ps.executeUpdate();
	}

	int deleteVoucherRecord(VoucherRecord voucherRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("DELETE FROM VoucherRecord WHERE VoucherRecordID = ?");
		ps.setLong(1, voucherRecord.getVoucherRecordID());

		return ps.executeUpdate();
	}

	private VoucherRecord read(ResultSet rs) throws SQLException
	{
		VoucherRecord voucherRecord = new VoucherRecord();

		voucherRecord.setVoucherRecordID(rs.getLong("VoucherRecordID"));
		voucherRecord.setVoucherID(rs.getLong("VoucherID"));
		voucherRecord.setTrustFundID(rs.getInt("TrustFundID"));
		voucherRecord.setTrustFundDepartmentVotID(rs.getInt("TrustFundDepartmentVotID"));
		voucherRecord.setTrustFundDepartmentVotCode(rs.getString("TrustFundDepartmentVotCode"));
		voucherRecord.setTrustFundDepartmentVotName(rs.getString("TrustFundDepartmentVotName"));
		voucherRecord.setTrustFundBursaryVotID(rs.getInt("TrustFundBursaryVotID"));
		voucherRecord.setTrustFundBursaryVotCode(rs.getString("TrustFundBursaryVotCode"));
		voucherRecord.setTrustFundBursaryVotName(rs.getString("TrustFundBursaryVotName"));		
		voucherRecord.setTrustFundDescription(rs.getString("TrustFundDescription"));
		voucherRecord.setDescription(rs.getString("Description"));
		voucherRecord.setTotal(rs.getBigDecimal("Total"));
		
		return voucherRecord;
	}

	private ArrayList<VoucherRecord> getVoucherRecords(ResultSet rs) throws SQLException
	{
		ArrayList<VoucherRecord> voucherRecords = new ArrayList<>();

		while (rs.next())
		{
			VoucherRecord voucherRecord = read(rs);
			voucherRecords.add(voucherRecord);
		}
		return voucherRecords;
	}

	VoucherRecord getVoucherRecord(long voucherRecordID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT vr.*, tf.TrustFundID, dv.DepartmentVotID AS TrustFundDepartmentVotID, dv.Code AS TrustFundDepartmentVotCode, dv.Name AS TrustFundDepartmentVotName, bv.BursaryVotID AS TrustFundBursaryVotID, bv.Code AS TrustFundBursaryVotCode, bv.Name AS TrustFundBursaryVotName, tf.Description AS TrustFundDescription " + 
				"FROM VoucherRecord vr, Voucher v, TrustFund tf, DepartmentVot dv, BursaryVot bv " + 
				"WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND vr.VoucherRecordID = ?");

		ps.setLong(1, voucherRecordID);

		ResultSet rs = ps.executeQuery();

		return read(rs);
	}
	
	ArrayList<VoucherRecord> getVoucherRecords(Voucher voucher) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT vr.*, tf.TrustFundID, dv.DepartmentVotID AS TrustFundDepartmentVotID, dv.Code AS TrustFundDepartmentVotCode, dv.Name AS TrustFundDepartmentVotName, bv.BursaryVotID AS TrustFundBursaryVotID, bv.Code AS TrustFundBursaryVotCode, bv.Name AS TrustFundBursaryVotName, tf.Description AS TrustFundDescription " + 
				"FROM VoucherRecord vr, Voucher v, TrustFund tf, DepartmentVot dv, BursaryVot bv " + 
				"WHERE vr.VoucherID = v.VoucherID AND vr.TrustFundID = tf.TrustFundID AND tf.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND v.VoucherID = ?");
		ps.setLong(1, voucher.getVoucherID());

		return getVoucherRecords(ps.executeQuery());
	}
}