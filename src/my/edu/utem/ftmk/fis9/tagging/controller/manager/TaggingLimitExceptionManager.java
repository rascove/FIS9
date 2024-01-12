package my.edu.utem.ftmk.fis9.tagging.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.tagging.model.Tagging;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingLimitException;

/**
 * @author Satrya Fajri Pratama
 */
class TaggingLimitExceptionManager extends TaggingTableManager
{
	TaggingLimitExceptionManager(TaggingFacade facade)
	{
		super(facade);
	}

	int setTaggingLimitExceptions(Tagging tagging) throws SQLException
	{
		int status = 0;
		ArrayList<TaggingLimitException> taggingLimitExceptions = tagging.getTaggingLimitExceptions();
		PreparedStatement clear = facade.prepareStatement("DELETE FROM TaggingLimitException WHERE TaggingID = ?");

		clear.setLong(1, tagging.getTaggingID());
		clear.executeUpdate();

		if (taggingLimitExceptions != null && !taggingLimitExceptions.isEmpty())
		{
			PreparedStatement batch = facade.prepareStatement("INSERT INTO TaggingLimitException VALUES (?, ?, ?, ?)");

			for (TaggingLimitException taggingLimitException : taggingLimitExceptions)
			{
				batch.setString(1, standardize(taggingLimitException.getBlockNo()));
				batch.setString(2, standardize(taggingLimitException.getPlotNo()));
				batch.setInt(3, taggingLimitException.getQuantity());
				batch.setLong(4, tagging.getTaggingID());
				batch.addBatch();
			}

			status = batch.executeBatch().length;
		}

		return status;
	}

	ArrayList<TaggingLimitException> getTaggingLimitExceptions(Tagging tagging) throws SQLException
	{
		ArrayList<TaggingLimitException> taggingLimitExceptions = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM TaggingLimitException WHERE TaggingID = ?");

		ps.setLong(1, tagging.getTaggingID());

		ResultSet rs = ps.executeQuery();

		while (rs.next())
		{
			TaggingLimitException taggingLimitException = new TaggingLimitException();

			taggingLimitException.setBlockNo(rs.getString("BlockNo"));
			taggingLimitException.setPlotNo(rs.getString("PlotNo"));
			taggingLimitException.setQuantity(rs.getInt("Quantity"));
			taggingLimitException.setTaggingID(rs.getLong("TaggingID"));

			taggingLimitExceptions.add(taggingLimitException);
		}

		return taggingLimitExceptions;
	}
}