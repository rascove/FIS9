package my.edu.utem.ftmk.fis9.prefelling.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.prefelling.model.CuttingLimit;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;

/**
 * @author Satrya Fajri Pratama
 */
class CuttingLimitManager extends PreFellingTableManager
{
	CuttingLimitManager(PreFellingFacade facade)
	{
		super(facade);
	}

	int[] addCuttingLimits(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		int[] statuses = null;
		ArrayList<CuttingLimit> cuttingLimits = preFellingSurvey.getCuttingLimits();
		PreparedStatement clear = facade.prepareStatement("DELETE FROM CuttingLimit WHERE PreFellingSurveyID = ?");

		clear.setLong(1, preFellingSurvey.getPreFellingSurveyID());
		clear.executeUpdate();

		if (cuttingLimits != null && !cuttingLimits.isEmpty())
		{
			PreparedStatement batch = facade.prepareStatement("INSERT INTO CuttingLimit VALUES (?, ?, ?)");

			for (CuttingLimit cuttingLimit : cuttingLimits)
			{
				batch.setDouble(1, cuttingLimit.getMinDiameter());
				batch.setLong(2, cuttingLimit.getPreFellingSurveyID());
				batch.setInt(3, cuttingLimit.getSpeciesID());
				batch.addBatch();
			}

			statuses = batch.executeBatch();
		}

		return statuses;
	}

	private ArrayList<CuttingLimit> getCuttingLimits(long preFellingSurveyID) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT cl.*, s.SpeciesTypeID, s.Name AS SpeciesName FROM CuttingLimit cl, Species s WHERE cl.PreFellingSurveyID = ? AND cl.SpeciesID = s.SpeciesID ORDER BY cl.SpeciesID");

		ps.setLong(1, preFellingSurveyID);

		ResultSet rs = ps.executeQuery();
		ArrayList<CuttingLimit> cuttingLimits = new ArrayList<>();

		while (rs.next())
		{
			CuttingLimit cuttingLimit = new CuttingLimit();

			cuttingLimit.setMinDiameter(rs.getDouble("MinDiameter"));
			cuttingLimit.setPreFellingSurveyID(rs.getLong("PreFellingSurveyID"));
			cuttingLimit.setSpeciesID(rs.getInt("SpeciesID"));
			cuttingLimit.setSpeciesTypeID(rs.getInt("SpeciesTypeID"));
			cuttingLimit.setSpeciesName(rs.getString("SpeciesName"));

			cuttingLimits.add(cuttingLimit);
		}

		return cuttingLimits;
	}
	
	ArrayList<CuttingLimit> getCuttingLimits(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		return getCuttingLimits(preFellingSurvey.getPreFellingSurveyID());
	}
	
	ArrayList<CuttingLimit> getCuttingLimits(Tagging tagging) throws SQLException
	{
		return getCuttingLimits(tagging.getPreFellingSurveyID());
	}
}