package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.revenue.model.ForestDevelopmentContractor;
import my.edu.utem.ftmk.fis9.revenue.model.ForestDevelopmentContractorSubWorkTypeRecord;

/**
 * @author Nor Azman Mat Ariff
 */
class ForestDevelopmentContractorSubWorkTypeRecordManager extends RevenueTableManager
{
	ForestDevelopmentContractorSubWorkTypeRecordManager(RevenueFacade facade)
	{
		super(facade);
	}

	private int write(ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord, PreparedStatement ps) throws SQLException
	{
		ps.setLong(1, forestDevelopmentContractorSubWorkTypeRecord.getForestDevelopmentContractorID());
		ps.setInt(2, forestDevelopmentContractorSubWorkTypeRecord.getForestDevelopmentSubWorkTypeID());
		ps.setString(3, forestDevelopmentContractorSubWorkTypeRecord.getStatus());

		return ps.executeUpdate();
	}

	int addForestDevelopmentContractorSubWorkTypeRecord(ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord) throws SQLException
	{
		int status = 0;
		if(checkExistingForestDevelopmentContractorSubWorkTypeRecordNo(forestDevelopmentContractorSubWorkTypeRecord) == false)
		{
			PreparedStatement ps = facade.prepareStatement("INSERT INTO ForestDevelopmentContractorSubWorkTypeRecord (ForestDevelopmentContractorID, ForestDevelopmentSubWorkTypeID, Status) "
					+ "VALUES (?, ?, ?)"); 
			
			status = write(forestDevelopmentContractorSubWorkTypeRecord, ps);

			if (status != 0)
			{
				ResultSet rs = ps.getGeneratedKeys();

				if (rs.next())
					forestDevelopmentContractorSubWorkTypeRecord.setForestDevelopmentContractorSubWorkTypeRecordID(rs.getInt(1));
			}
		}

		return status;
	}

	private boolean checkExistingForestDevelopmentContractorSubWorkTypeRecordNo(ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord) throws SQLException
	{
		boolean exist = false;
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM ForestDevelopmentContractorSubWorkTypeRecord WHERE ForestDevelopmentContractorSubWorkTypeRecordID != ? AND ForestDevelopmentContractorID = ? AND ForestDevelopmentSubWorkTypeID = ? AND Status = 'A'");
		ps.setInt(1, forestDevelopmentContractorSubWorkTypeRecord.getForestDevelopmentContractorSubWorkTypeRecordID());
		ps.setLong(2, forestDevelopmentContractorSubWorkTypeRecord.getForestDevelopmentContractorID());
		ps.setInt(3, forestDevelopmentContractorSubWorkTypeRecord.getForestDevelopmentSubWorkTypeID());

		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			exist = true;
		}

		return exist;
	}
	
	int updateForestDevelopmentContractorSubWorkTypeRecordStatus(ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ForestDevelopmentContractorSubWorkTypeRecord SET Status = ? WHERE ForestDevelopmentContractorSubWorkTypeRecordID = ?");
		ps.setString(1, forestDevelopmentContractorSubWorkTypeRecord.getStatus());
		ps.setInt(2, forestDevelopmentContractorSubWorkTypeRecord.getForestDevelopmentContractorSubWorkTypeRecordID());

		return ps.executeUpdate();
	}

	int deleteForestDevelopmentContractorSubWorkTypeRecord(ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE ForestDevelopmentContractorSubWorkTypeRecord SET Status = 'I' WHERE ForestDevelopmentContractorSubWorkTypeRecordID = ?");
		ps.setInt(1, forestDevelopmentContractorSubWorkTypeRecord.getForestDevelopmentContractorSubWorkTypeRecordID());

		return ps.executeUpdate();
	}

	private ForestDevelopmentContractorSubWorkTypeRecord readForestDevelopmentContractorSubWorkTypeRecord(ResultSet rs) throws SQLException
	{
		ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord = new ForestDevelopmentContractorSubWorkTypeRecord();

		forestDevelopmentContractorSubWorkTypeRecord.setForestDevelopmentContractorSubWorkTypeRecordID(rs.getInt("ForestDevelopmentContractorSubWorkTypeRecordID"));
		forestDevelopmentContractorSubWorkTypeRecord.setForestDevelopmentContractorID(rs.getLong("ForestDevelopmentContractorID"));
		forestDevelopmentContractorSubWorkTypeRecord.setForestDevelopmentSubWorkTypeID(rs.getInt("ForestDevelopmentSubWorkTypeID"));
		forestDevelopmentContractorSubWorkTypeRecord.setStatus(rs.getString("Status"));	

		return forestDevelopmentContractorSubWorkTypeRecord;
	}

	private ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> getForestDevelopmentContractorSubWorkTypeRecords(ResultSet rs) throws SQLException
	{
		ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> forestDevelopmentContractorSubWorkTypeRecords = new ArrayList<>();

		while (rs.next())
		{
			ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord = readForestDevelopmentContractorSubWorkTypeRecord(rs);
			forestDevelopmentContractorSubWorkTypeRecords.add(forestDevelopmentContractorSubWorkTypeRecord);
		}
		return forestDevelopmentContractorSubWorkTypeRecords;
	}

	ForestDevelopmentContractorSubWorkTypeRecord getForestDevelopmentContractorSubWorkTypeRecord(int forestDevelopmentContractorSubWorkTypeRecordID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT fdcswtr.* " + 
				"FROM ForestDevelopmentContractorSubWorkTypeRecord fdcswtr, ForestDevelopmentContractor fdc, ForestDevelopmentSubWorkType fdswt " + 
				"WHERE fdcswtr.ForestDevelopmentContractorID = fdc.ForestDevelopmentContractorID AND fdcswtr.ForestDevelopmentSubWorkTypeID = fdswt.ForestDevelopmentSubWorkTypeID");

		ps.setInt(1, forestDevelopmentContractorSubWorkTypeRecordID);

		ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord = null;
		ResultSet rs = ps.executeQuery();

		if (rs.next())
		{
			forestDevelopmentContractorSubWorkTypeRecord = readForestDevelopmentContractorSubWorkTypeRecord(rs);
		}

		return forestDevelopmentContractorSubWorkTypeRecord;
	}
	
	ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> getForestDevelopmentContractorSubWorkTypeRecords(String status) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT fdcswtr.* " + 
				"FROM ForestDevelopmentContractorSubWorkTypeRecord fdcswtr, ForestDevelopmentContractor fdc, ForestDevelopmentSubWorkType fdswt " + 
				"WHERE fdcswtr.ForestDevelopmentContractorID = fdc.ForestDevelopmentContractorID AND fdcswtr.ForestDevelopmentSubWorkTypeID = fdswt.ForestDevelopmentSubWorkTypeID AND fdcswtr.Status = ? ORDER BY fdcswtr.ForestDevelopmentContractorID");

		ps.setString(1, status);

		return getForestDevelopmentContractorSubWorkTypeRecords(ps.executeQuery());
	}
	
	ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> getForestDevelopmentContractorSubWorkTypeRecords(ForestDevelopmentContractor forestDevelopmentContractor) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT fdcswtr.* " + 
				"FROM ForestDevelopmentContractorSubWorkTypeRecord fdcswtr, ForestDevelopmentContractor fdc, ForestDevelopmentSubWorkType fdswt " + 
				"WHERE fdcswtr.ForestDevelopmentContractorID = fdc.ForestDevelopmentContractorID AND fdcswtr.ForestDevelopmentSubWorkTypeID = fdswt.ForestDevelopmentSubWorkTypeID AND fdc.ForestDevelopmentContractorID = ? ORDER BY fdcswtr.ForestDevelopmentSubWorkTypeID");

		ps.setLong(1, forestDevelopmentContractor.getForestDevelopmentContractorID());

		return getForestDevelopmentContractorSubWorkTypeRecords(ps.executeQuery());
	}
}