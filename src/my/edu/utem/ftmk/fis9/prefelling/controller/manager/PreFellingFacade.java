package my.edu.utem.ftmk.fis9.prefelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.prefelling.model.CuttingLimit;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingCuttingOption;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurveyCard;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurveyRecord;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;

/**
 * @author Satrya Fajri Pratama
 */
public class PreFellingFacade extends AbstractFacade
{
	private PreFellingSurveyManager preFellingSurveyManager;
	private PreFellingSurveyCardManager preFellingSurveyCardManager;
	private PreFellingSurveyRecordManager preFellingSurveyRecordManager;
	private CuttingLimitManager cuttingLimitManager;
	private CuttingOptionRecommendationManager cuttingOptionRecommendationManager;

	private PreFellingSurveyManager getPreFellingSurveyManager()
	{
		if (preFellingSurveyManager == null)
			preFellingSurveyManager = new PreFellingSurveyManager(this);

		return preFellingSurveyManager;
	}

	private PreFellingSurveyCardManager getPreFellingSurveyCardManager()
	{
		if (preFellingSurveyCardManager == null)
			preFellingSurveyCardManager = new PreFellingSurveyCardManager(this);

		return preFellingSurveyCardManager;
	}

	private PreFellingSurveyRecordManager getPreFellingSurveyRecordManager()
	{
		if (preFellingSurveyRecordManager == null)
			preFellingSurveyRecordManager = new PreFellingSurveyRecordManager(this);

		return preFellingSurveyRecordManager;
	}

	private CuttingLimitManager getCuttingLimitManager()
	{
		if (cuttingLimitManager == null)
			cuttingLimitManager = new CuttingLimitManager(this);

		return cuttingLimitManager;
	}

	private CuttingOptionRecommendationManager getCuttingOptionRecommendationManager()
	{
		if (cuttingOptionRecommendationManager == null)
			cuttingOptionRecommendationManager = new CuttingOptionRecommendationManager(this);

		return cuttingOptionRecommendationManager;
	}

	@Override
	protected PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return getPreparedStatement(sql);
	}

	ArrayList<PreFellingSurveyRecord> getPreFellingSurveyRecords(PreFellingSurveyCard preFellingSurveyCard) throws SQLException
	{
		return getPreFellingSurveyRecordManager().getPreFellingSurveyRecords(preFellingSurveyCard);
	}

	int[] addCuttingLimits(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		return getCuttingLimitManager().addCuttingLimits(preFellingSurvey);
	}

	public int addPreFellingSurvey(PreFellingSurvey preFellingSurvey, boolean ignoreDuplicate) throws SQLException
	{
		return getPreFellingSurveyManager().addPreFellingSurvey(preFellingSurvey, ignoreDuplicate);
	}

	public int updatePreFellingSurvey(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		return getPreFellingSurveyManager().updatePreFellingSurvey(preFellingSurvey);
	}

	public int[] getPreFellingSurveyYearRange() throws SQLException
	{
		return getPreFellingSurveyManager().getPreFellingSurveyYearRange();
	}

	public PreFellingSurvey getPreFellingSurvey(long preFellingSurveyID) throws SQLException
	{
		return getPreFellingSurveyManager().getPreFellingSurvey(preFellingSurveyID);
	}

	public ArrayList<PreFellingSurvey> getPreFellingSurveys(int startYear, int endYear) throws SQLException
	{
		return getPreFellingSurveyManager().getPreFellingSurveys(startYear, endYear);
	}

	public ArrayList<PreFellingSurvey> getPreFellingSurveys(State state, int startYear, int endYear) throws SQLException
	{
		return getPreFellingSurveyManager().getPreFellingSurveys(state, startYear, endYear);
	}

	public ArrayList<PreFellingSurvey> getPreFellingSurveys(District district, int startYear, int endYear) throws SQLException
	{
		return getPreFellingSurveyManager().getPreFellingSurveys(district, startYear, endYear);
	}

	public ArrayList<PreFellingSurvey> getPreFellingSurveys(Range range, int startYear, int endYear) throws SQLException
	{
		return getPreFellingSurveyManager().getPreFellingSurveys(range, startYear, endYear);
	}

	public ArrayList<PreFellingSurvey> getPreFellingSurveys(Staff staff, int startYear, int endYear) throws SQLException
	{
		return getPreFellingSurveyManager().getPreFellingSurveys(staff, startYear, endYear);
	}

	public ArrayList<PreFellingSurvey> getPreFellingSurveys(boolean forTagging) throws SQLException
	{
		return getPreFellingSurveyManager().getPreFellingSurveys(forTagging);
	}

	public ArrayList<PreFellingSurvey> getPreFellingSurveys(State state, boolean forTagging) throws SQLException
	{
		return getPreFellingSurveyManager().getPreFellingSurveys(state, forTagging);
	}

	public int addPreFellingSurveyCard(PreFellingSurveyCard preFellingSurveyCard, boolean ignoreDuplicate) throws SQLException
	{
		return getPreFellingSurveyCardManager().addPreFellingSurveyCard(preFellingSurveyCard, ignoreDuplicate);
	}

	public int updatePreFellingSurveyCard(PreFellingSurveyCard preFellingSurveyCard) throws SQLException
	{
		return getPreFellingSurveyCardManager().updatePreFellingSurveyCard(preFellingSurveyCard);
	}

	public ArrayList<PreFellingSurveyCard> getPreFellingSurveyCards(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		return getPreFellingSurveyCardManager().getPreFellingSurveyCards(preFellingSurvey);
	}

	public int addPreFellingSurveyRecord(PreFellingSurveyRecord preFellingSurveyRecord, boolean ignoreDuplicate) throws SQLException
	{
		return getPreFellingSurveyRecordManager().addPreFellingSurveyRecord(preFellingSurveyRecord, ignoreDuplicate);
	}

	public int updatePreFellingSurveyRecord(PreFellingSurveyRecord preFellingSurveyRecord) throws SQLException
	{
		return getPreFellingSurveyRecordManager().updatePreFellingSurveyRecord(preFellingSurveyRecord);
	}

	public int deletePreFellingSurveyRecord(PreFellingSurveyRecord preFellingSurveyRecord) throws SQLException
	{
		return getPreFellingSurveyRecordManager().deletePreFellingSurveyRecord(preFellingSurveyRecord);
	}

	public ArrayList<CuttingLimit> getCuttingLimits(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		return getCuttingLimitManager().getCuttingLimits(preFellingSurvey);
	}

	public ArrayList<CuttingLimit> getCuttingLimits(Tagging tagging) throws SQLException
	{
		return getCuttingLimitManager().getCuttingLimits(tagging);
	}

	public int addPreFellingCuttingOption(PreFellingCuttingOption preFellingCuttingOption) throws SQLException
	{
		return getCuttingOptionRecommendationManager().addPreFellingCuttingOption(preFellingCuttingOption);
	}

	public int deleteCuttingOptionRecommendations(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		return getCuttingOptionRecommendationManager().deleteCuttingOptionRecommendations(preFellingSurvey);
	}

	public ArrayList<Integer> getCuttingOptionRecommendations(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		return getCuttingOptionRecommendationManager().getCuttingOptionRecommendations(preFellingSurvey);
	}
}