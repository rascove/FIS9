package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.revenue.model.ReceiptRecord;

/**
 * @author Nor Azman Mat Ariff
 */
class ReceiptRecordManager extends RevenueTableManager
{
	ReceiptRecordManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(ReceiptRecord receiptRecord, PreparedStatement ps) throws SQLException
	{
		ps.setInt(1, receiptRecord.getDepartmentVotID());
		ps.setString(2, receiptRecord.getDescription());
		ps.setBigDecimal(3, receiptRecord.getRate());
		ps.setBigDecimal(4, receiptRecord.getQuantity());
		ps.setLong(5, receiptRecord.getReceiptID());
		ps.setLong(6, receiptRecord.getReceiptRecordID());
		
		return ps.executeUpdate();
	}

	int addReceiptRecord(ReceiptRecord receiptRecord) throws SQLException
	{
		int status = 0;
		String sql = "INSERT INTO ReceiptRecord (DepartmentVotID, Description, Rate, Quantity, ReceiptID, ReceiptRecordID) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = facade.prepareStatement(sql);
		status = write(receiptRecord, ps);
		
		return status;
	}

	private ReceiptRecord readReceiptRecord(ResultSet rs) throws SQLException
	{
		ReceiptRecord receiptRecord = new ReceiptRecord();

		receiptRecord.setReceiptRecordID(rs.getLong("ReceiptRecordID"));
		receiptRecord.setReceiptID(rs.getLong("ReceiptID"));
		receiptRecord.setDepartmentVotID(rs.getInt("departmentVotID"));
		receiptRecord.setDepartmentVotCode(rs.getString("departmentVotCode"));
		receiptRecord.setDepartmentVotName(rs.getString("departmentVotName"));
		receiptRecord.setBursaryVotID(rs.getInt("bursaryVotID"));
		receiptRecord.setBursaryVotCode(rs.getString("bursaryVotCode"));
		receiptRecord.setBursaryVotName(rs.getString("bursaryVotName"));		
		receiptRecord.setDescription(rs.getString("description"));
		receiptRecord.setRate(rs.getBigDecimal("rate"));
		receiptRecord.setQuantity(rs.getBigDecimal("quantity"));

		return receiptRecord;
	}

	private ArrayList<ReceiptRecord> getReceiptRecords(ResultSet rs) throws SQLException
	{
		ArrayList<ReceiptRecord> receiptRecords = new ArrayList<>();

		while (rs.next())
		{
			ReceiptRecord receiptRecord = readReceiptRecord(rs);
			receiptRecords.add(receiptRecord);
		}
		return receiptRecords;
	}
	
	ArrayList<ReceiptRecord> getReceiptRecords(long receiptID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT rr.*, dv.Code AS DepartmentVotCode, dv.Name AS DepartmentVotName, bv.BursaryVotID, bv.Code AS BursaryVotCode, bv.Name AS BursaryVotName " + 
				"FROM Receipt r, ReceiptRecord rr, DepartmentVot dv, BursaryVot bv " + 
				"WHERE rr.ReceiptID = r. ReceiptID AND rr.DepartmentVotID = dv.DepartmentVotID AND dv.BursaryVotID = bv.BursaryVotID AND r.ReceiptID = ?");

		ps.setLong(1, receiptID);

		return getReceiptRecords(ps.executeQuery());
	}
}