package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.AbstractsSpeciesGroup;
import my.edu.utem.ftmk.fis9.maintenance.model.AbstractsSpeciesGroupRecord;

/**
 * @author Nor Azman Mat Ariff
 */
class AbstractsSpeciesGroupRecordManager extends MaintenanceTableManager {
	AbstractsSpeciesGroupRecordManager(MaintenanceFacade facade) {
		super(facade);
	}

	private int write(AbstractsSpeciesGroupRecord abstractsSpeciesGroupRecord, PreparedStatement ps)
			throws SQLException {
		ps.setInt(1, abstractsSpeciesGroupRecord.getAbstractsSpeciesGroupID());
		ps.setInt(2, abstractsSpeciesGroupRecord.getSpeciesID());
		ps.setString(3, abstractsSpeciesGroupRecord.getStatus());

		if (abstractsSpeciesGroupRecord.getAbstractsSpeciesGroupRecordID() != 0)
			ps.setInt(4, abstractsSpeciesGroupRecord.getAbstractsSpeciesGroupRecordID());

		return ps.executeUpdate();
	}

	int addAbstractsSpeciesGroupRecord(AbstractsSpeciesGroupRecord abstractsSpeciesGroupRecord) throws SQLException {
		int status = 0;
		if (checkExistingCode(abstractsSpeciesGroupRecord) == null) {
			PreparedStatement ps = facade.prepareStatement(
					"INSERT INTO AbstractsSpeciesGroupRecord (AbstractsSpeciesGroupID, SpeciesID, Status) VALUES (?, ?, ?)");
			status = write(abstractsSpeciesGroupRecord, ps);

			if (status != 0) {
				ResultSet rs = ps.getGeneratedKeys();

				if (rs.next())
					abstractsSpeciesGroupRecord.setAbstractsSpeciesGroupRecordID(rs.getInt(1));
			}
		}

		return status;
	}

	private AbstractsSpeciesGroupRecord checkExistingCode(AbstractsSpeciesGroupRecord abstractsSpeciesGroupRecord)
			throws SQLException {
		PreparedStatement ps = facade.prepareStatement(
				"SELECT * FROM AbstractsSpeciesGroupRecord WHERE AbstractsSpeciesGroupID = ? AND SpeciesID = ? AND Status = 'A'");
		ps.setInt(1, abstractsSpeciesGroupRecord.getAbstractsSpeciesGroupID());
		ps.setInt(2, abstractsSpeciesGroupRecord.getSpeciesID());

		ResultSet rs = ps.executeQuery();

		AbstractsSpeciesGroupRecord oldAbstractsSpeciesGroupRecord = null;

		if (rs.next()) {
			oldAbstractsSpeciesGroupRecord = read(rs);
		}

		return oldAbstractsSpeciesGroupRecord;
	}

	int updateAbstractsSpeciesGroupRecord(AbstractsSpeciesGroupRecord abstractsSpeciesGroupRecord) throws SQLException {
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE AbstractsSpeciesGroupRecord SET AbstractsSpeciesGroupID = ?, SpeciesID = ?, Status = ? WHERE AbstractsSpeciesGroupRecordID = ?");

		return write(abstractsSpeciesGroupRecord, ps);
	}

	int deleteAbstractsSpeciesGroupRecord(AbstractsSpeciesGroupRecord abstractsSpeciesGroupRecord) throws SQLException {
		PreparedStatement ps = facade.prepareStatement(
				"UPDATE AbstractsSpeciesGroupRecord SET Status = 'I' WHERE AbstractsSpeciesGroupRecordID = ?");
		ps.setInt(1, abstractsSpeciesGroupRecord.getAbstractsSpeciesGroupRecordID());

		return ps.executeUpdate();
	}

	private AbstractsSpeciesGroupRecord read(ResultSet rs) throws SQLException {
		AbstractsSpeciesGroupRecord abstractsSpeciesGroupRecord = new AbstractsSpeciesGroupRecord();

		abstractsSpeciesGroupRecord.setAbstractsSpeciesGroupRecordID(rs.getInt("AbstractsSpeciesGroupRecordID"));
		abstractsSpeciesGroupRecord.setAbstractsSpeciesGroupID(rs.getInt("AbstractsSpeciesGroupID"));
		abstractsSpeciesGroupRecord.setAbstractsSpeciesGroupName(rs.getString("AbstractsSpeciesGroupName"));
		abstractsSpeciesGroupRecord.setSpeciesID(rs.getInt("SpeciesID"));
		abstractsSpeciesGroupRecord.setSpeciesCode(rs.getString("SpeciesCode"));
		abstractsSpeciesGroupRecord.setSpeciesName(rs.getString("SpeciesName"));
		abstractsSpeciesGroupRecord.setStatus(rs.getString("Status"));

		return abstractsSpeciesGroupRecord;
	}

	private ArrayList<AbstractsSpeciesGroupRecord> getAbstractsSpeciesGroupRecords(ResultSet rs) throws SQLException {
		ArrayList<AbstractsSpeciesGroupRecord> abstractsSpeciesGroupRecords = new ArrayList<>();

		while (rs.next()) {
			abstractsSpeciesGroupRecords.add(read(rs));
		}
		return abstractsSpeciesGroupRecords;
	}

	AbstractsSpeciesGroupRecord getAbstractsSpeciesGroupRecord(int abstractsSpeciesGroupRecordID) throws SQLException {
		AbstractsSpeciesGroupRecord abstractsSpeciesGroupRecord = null;
		PreparedStatement ps = facade.prepareStatement(
				"SELECT asgr.*, asg.Name AS AbstractsSpeciesGroupName, s.Code AS SpeciesCode, s.Name AS SpeciesName "
						+ "FROM AbstractsSpeciesGroupRecord asgr, AbstractsSpeciesGroup asg, Species s "
						+ "WHERE asgr.AbstractsSpeciesGroupID = asg.AbstractsSpeciesGroupID AND asgr.SpeciesID = s.SpeciesID AND asgr.Status = 'A' AND asgr.abstractsSpeciesGroupRecordID = ?");

		ps.setInt(1, abstractsSpeciesGroupRecordID);

		ResultSet rs = ps.executeQuery();

		if (rs.next())
			abstractsSpeciesGroupRecord = read(rs);

		return abstractsSpeciesGroupRecord;
	}

	ArrayList<AbstractsSpeciesGroupRecord> getAbstractsSpeciesGroupRecords(String status) throws SQLException {
		PreparedStatement ps = facade.prepareStatement(
				"SELECT asgr.*, asg.Name AS AbstractsSpeciesGroupName, s.Code AS SpeciesCode, s.Name AS SpeciesName "
						+ "FROM AbstractsSpeciesGroupRecord asgr, AbstractsSpeciesGroup asg, Species s "
						+ "WHERE asgr.AbstractsSpeciesGroupID = asg.AbstractsSpeciesGroupID AND asgr.SpeciesID = s.SpeciesID AND asgr.Status = ? ORDER BY asgr.abstractsSpeciesGroupID, s.Name");

		ps.setString(1, status);

		return getAbstractsSpeciesGroupRecords(ps.executeQuery());
	}

	ArrayList<AbstractsSpeciesGroupRecord> getAbstractsSpeciesGroupRecords(AbstractsSpeciesGroup abstractsSpeciesGroup)
			throws SQLException {
		PreparedStatement ps = facade.prepareStatement(
				"SELECT asgr.*, asg.Name AS AbstractsSpeciesGroupName, s.Code AS SpeciesCode, s.Name AS SpeciesName "
						+ "FROM AbstractsSpeciesGroupRecord asgr, AbstractsSpeciesGroup asg, Species s "
						+ "WHERE asgr.AbstractsSpeciesGroupID = asg.AbstractsSpeciesGroupID AND asgr.SpeciesID = s.SpeciesID AND asgr.Status = 'A' AND asgr.abstractsSpeciesGroupID = ? ORDER BY s.Name");

		ps.setInt(1, abstractsSpeciesGroup.getAbstractsSpeciesGroupID());

		return getAbstractsSpeciesGroupRecords(ps.executeQuery());
	}
}