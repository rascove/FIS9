package my.edu.utem.ftmk.fis9.tagging.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.tagging.model.Tagging;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingForm;

/**
 * @author Satrya Fajri Pratama
 */
class TaggingFormManager extends TaggingTableManager
{
	TaggingFormManager(TaggingFacade facade)
	{
		super(facade);
	}

	private void write(TaggingForm taggingForm, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, standardize(taggingForm.getFormNo()));
		nullable(ps, 2, taggingForm.getLength());
		nullable(ps, 3, taggingForm.getWidth());
		ps.setDate(4, toSQLDate(taggingForm.getTaggingDate()));
		nullable(ps, 5, taggingForm.getInspectionDate());
		ps.setInt(6, taggingForm.getTaggingTypeID());
		ps.setString(7, taggingForm.getInspectorID());
		ps.setLong(8, taggingForm.getTaggingFormID());
	}

	int addTaggingForm(TaggingForm taggingForm, boolean ignoreDuplicate) throws SQLException
	{
		String sql = (ignoreDuplicate ? "INSERT IGNORE" : "INSERT") + " INTO TaggingForm (FormNo, Length, Width, TaggingDate, InspectionDate, TaggingTypeID, InspectorID, TaggingFormID, TaggingID, RecorderID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + (ignoreDuplicate ? "" : " ON DUPLICATE KEY UPDATE Length = ?, Width = ?, TaggingDate = ?, InspectionDate = ?, InspectorID = ?");
		PreparedStatement ps = facade.prepareStatement(sql);

		write(taggingForm, ps);
		ps.setLong(9, taggingForm.getTaggingID());
		ps.setString(10, taggingForm.getRecorderID());

		if (!ignoreDuplicate)
		{
			nullable(ps, 11, taggingForm.getLength());
			nullable(ps, 12, taggingForm.getWidth());
			ps.setDate(13, toSQLDate(taggingForm.getTaggingDate()));
			nullable(ps, 14, taggingForm.getInspectionDate());
			ps.setString(15, taggingForm.getInspectorID());
		}

		int status = ps.executeUpdate();
		
		if (status == 0 && !ignoreDuplicate)
			status = 1;
		
		return status;
	}

	int updateTaggingForm(TaggingForm taggingForm) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TaggingForm SET FormNo = ?, Length = ?, Width = ?, TaggingDate = ?, InspectionDate = ?, TaggingTypeID = ?, InspectorID = ? WHERE TaggingFormID = ?");

		write(taggingForm, ps);

		return ps.executeUpdate();
	}

	private TaggingForm read(ResultSet rs) throws SQLException
	{
		TaggingForm taggingForm = new TaggingForm();

		taggingForm.setTaggingFormID(rs.getLong("TaggingFormID"));
		taggingForm.setFormNo(rs.getString("FormNo"));
		taggingForm.setLength(rs.getDouble("Length"));
		taggingForm.setWidth(rs.getDouble("Width"));
		taggingForm.setTaggingDate(rs.getDate("TaggingDate"));
		taggingForm.setInspectionDate(rs.getDate("InspectionDate"));
		taggingForm.setTaggingID(rs.getLong("TaggingID"));
		taggingForm.setTaggingTypeID(rs.getInt("TaggingTypeID"));
		taggingForm.setRecorderID(rs.getString("RecorderID"));
		taggingForm.setInspectorID(rs.getString("InspectorID"));
		taggingForm.setTaggingTypeName(rs.getString("TaggingTypeName"));
		taggingForm.setRecorderName(rs.getString("RecorderName"));
		taggingForm.setInspectorName(rs.getString("InspectorName"));
		taggingForm.setTaggingRecords(facade.getTaggingRecords(taggingForm));

		return taggingForm;
	}

	TaggingForm getTaggingForm(long taggingFormID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tf.*, tt.Name AS TaggingTypeName, s.Name AS RecorderName, s1.Name AS InspectorName FROM TaggingForm tf JOIN TaggingType tt JOIN Staff s LEFT JOIN Staff s1 ON tf.InspectorID = s1.StaffID WHERE tf.TaggingFormID = ? AND tf.TaggingTypeID = tt.TaggingTypeID AND tf.RecorderID = s.StaffID ORDER BY tf.TaggingTypeID, tf.FormNo");

		ps.setLong(1, taggingFormID);

		TaggingForm taggingForm = null;
		ResultSet rs = ps.executeQuery();
		
		if (rs.next())
			taggingForm = read(rs);
		
		return taggingForm;
	}
	
	private ArrayList<TaggingForm> getTaggingForms(ResultSet rs) throws SQLException
	{
		ArrayList<TaggingForm> taggingForms = new ArrayList<>();

		while (rs.next())
			taggingForms.add(read(rs));

		return taggingForms;
	}

	ArrayList<TaggingForm> getTaggingForms(Tagging tagging) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT tf.*, tt.Name AS TaggingTypeName, s.Name AS RecorderName, s1.Name AS InspectorName FROM TaggingForm tf JOIN TaggingType tt JOIN Staff s LEFT JOIN Staff s1 ON tf.InspectorID = s1.StaffID WHERE tf.TaggingID = ? AND tf.TaggingTypeID = tt.TaggingTypeID AND tf.RecorderID = s.StaffID ORDER BY tf.TaggingTypeID, tf.FormNo");

		ps.setLong(1, tagging.getTaggingID());

		return getTaggingForms(ps.executeQuery());
	}
}