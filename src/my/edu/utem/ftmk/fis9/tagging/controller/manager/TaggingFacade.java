package my.edu.utem.ftmk.fis9.tagging.controller.manager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingForm;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingLimitException;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingRecord;

/**
 * @author Satrya Fajri Pratama
 */
public class TaggingFacade extends AbstractFacade
{
	private TaggingManager taggingManager;
	private TaggingFormManager taggingFormManager;
	private TaggingRecordManager taggingRecordManager;
	private TaggingLimitExceptionManager taggingLimitExceptionManager;

	private TaggingManager getTaggingManager()
	{
		if (taggingManager == null)
			taggingManager = new TaggingManager(this);

		return taggingManager;
	}

	private TaggingFormManager getTaggingFormManager()
	{
		if (taggingFormManager == null)
			taggingFormManager = new TaggingFormManager(this);

		return taggingFormManager;
	}

	private TaggingRecordManager getTaggingRecordManager()
	{
		if (taggingRecordManager == null)
			taggingRecordManager = new TaggingRecordManager(this);

		return taggingRecordManager;
	}

	private TaggingLimitExceptionManager getTaggingLimitExceptionManager()
	{
		if (taggingLimitExceptionManager == null)
			taggingLimitExceptionManager = new TaggingLimitExceptionManager(this);

		return taggingLimitExceptionManager;
	}

	@Override
	protected PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return getPreparedStatement(sql);
	}

	ArrayList<TaggingRecord> getTaggingRecords(TaggingForm taggingForm) throws SQLException
	{
		return getTaggingRecordManager().getTaggingRecords(taggingForm);
	}

	int setTaggingLimitExceptions(Tagging tagging) throws SQLException
	{
		return getTaggingLimitExceptionManager().setTaggingLimitExceptions(tagging);
	}

	public int addTagging(Tagging tagging, boolean ignoreDuplicate) throws SQLException
	{
		return getTaggingManager().addTagging(tagging, ignoreDuplicate);
	}

	public int updateTagging(Tagging tagging) throws SQLException
	{
		return getTaggingManager().updateTagging(tagging);
	}
	
	public int[] getTaggingYearRange() throws SQLException
	{
		return getTaggingManager().getTaggingYearRange();
	}

	public Tagging getTagging(long taggingID) throws SQLException
	{
		return getTaggingManager().getTagging(taggingID);
	}

	public ArrayList<Tagging> getTaggings(boolean closedOnly, int startYear, int endYear) throws SQLException
	{
		return getTaggingManager().getTaggings(closedOnly, startYear, endYear);
	}

	public ArrayList<Tagging> getTaggings(State state, boolean closedOnly, int startYear, int endYear) throws SQLException
	{
		return getTaggingManager().getTaggings(state, closedOnly, startYear, endYear);
	}

	public ArrayList<Tagging> getTaggings(District district, boolean closedOnly, int startYear, int endYear) throws SQLException
	{
		return getTaggingManager().getTaggings(district, closedOnly, startYear, endYear);
	}

	public ArrayList<Tagging> getTaggings(Range range, int startYear, int endYear) throws SQLException
	{
		return getTaggingManager().getTaggings(range, startYear, endYear);
	}

	public ArrayList<Tagging> getTaggings(Staff staff, int startYear, int endYear) throws SQLException
	{
		return getTaggingManager().getTaggings(staff, startYear, endYear);
	}

	public int addTaggingForm(TaggingForm taggingForm, boolean ignoreDuplicate) throws SQLException
	{
		return getTaggingFormManager().addTaggingForm(taggingForm, ignoreDuplicate);
	}

	public int updateTaggingForm(TaggingForm taggingForm) throws SQLException
	{
		return getTaggingFormManager().updateTaggingForm(taggingForm);
	}

	public TaggingForm getTaggingForm(long taggingFormID) throws SQLException
	{
		return getTaggingFormManager().getTaggingForm(taggingFormID);
	}
	
	public ArrayList<TaggingForm> getTaggingForms(Tagging tagging) throws SQLException
	{
		return getTaggingFormManager().getTaggingForms(tagging);
	}

	public int addTaggingRecord(TaggingRecord taggingRecord, boolean ignoreDuplicate) throws SQLException
	{
		return getTaggingRecordManager().addTaggingRecord(taggingRecord, ignoreDuplicate);
	}

	public int updateTaggingRecord(TaggingRecord taggingRecord) throws SQLException
	{
		return getTaggingRecordManager().updateTaggingRecord(taggingRecord);
	}
	
	public int updateTaggingRecordStatus(TaggingRecord taggingRecord) throws SQLException
	{
		return getTaggingRecordManager().updateTaggingRecordStatus(taggingRecord);
	}

	public int deleteTaggingRecord(TaggingRecord taggingRecord) throws SQLException
	{
		return getTaggingRecordManager().deleteTaggingRecord(taggingRecord);
	}

	public TaggingRecord getTaggingRecord(String serialNo) throws SQLException
	{
		return getTaggingRecordManager().getTaggingRecord(serialNo);
	}
	
	public ArrayList<TaggingRecord> getTaggingRecords(Tagging tagging) throws SQLException
	{
		return getTaggingRecordManager().getTaggingRecords(tagging);
	}
	
	public String[] getHeaderBukuKawalanPengeluaran(long taggingID, long licenseID) throws SQLException
	{
		return getTaggingRecordManager().getHeaderBukuKawalanPengeluaran(taggingID, licenseID);
	}
	
	public ArrayList<String[]> getCurrentStatusTaggingRecords(long taggingID) throws SQLException
	{
		return getTaggingRecordManager().getCurrentStatusTaggingRecords(taggingID);
	}

	public ArrayList<TaggingLimitException> getTaggingLimitExceptions(Tagging tagging) throws SQLException
	{
		return getTaggingLimitExceptionManager().getTaggingLimitExceptions(tagging);
	}
}