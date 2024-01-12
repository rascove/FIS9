package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.TaggingType;

/**
 * @author Satrya Fajri Pratama
 */
class TaggingTypeManager extends MaintenanceTableManager
{
	TaggingTypeManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int write(TaggingType taggingType, PreparedStatement ps) throws SQLException
	{
		ps.setString(1, taggingType.getName());

		if (taggingType.getTaggingTypeID() != 0)
			ps.setInt(2, taggingType.getTaggingTypeID());

		return ps.executeUpdate();
	}

	int addTaggingType(TaggingType taggingType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO TaggingType (Name) VALUES (?)");
		int status = write(taggingType, ps);

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				taggingType.setTaggingTypeID(rs.getInt(1));
		}

		return status;
	}

	int updateTaggingType(TaggingType taggingType) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE TaggingType SET Name = ? WHERE TaggingTypeID = ?");

		return write(taggingType, ps);
	}

	private ArrayList<TaggingType> getTaggingTypes(ResultSet rs) throws SQLException
	{
		ArrayList<TaggingType> taggingTypes = new ArrayList<>();

		while (rs.next())
		{
			TaggingType taggingType = new TaggingType();

			taggingType.setTaggingTypeID(rs.getInt("TaggingTypeID"));
			taggingType.setName(rs.getString("Name"));

			taggingTypes.add(taggingType);
		}

		return taggingTypes;
	}

	ArrayList<TaggingType> getTaggingTypes() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM TaggingType ORDER BY Name");

		return getTaggingTypes(ps.executeQuery());
	}
}