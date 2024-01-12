package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.Form;

/**
 * @author Satrya Fajri Pratama
 */
class DesignationManager extends MaintenanceTableManager
{
	DesignationManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private int setDefaultAccess(Designation designation, boolean add) throws SQLException
	{
		ArrayList<Form> forms = designation.getForms();
		int status = 0;
		
		if (!add)
		{
			PreparedStatement clear = facade.prepareStatement("DELETE FROM DefaultAccess WHERE DesignationID = ?");

			clear.setInt(1, designation.getDesignationID());
			clear.executeUpdate();
		}

		if (!forms.isEmpty())
		{
			PreparedStatement batch = facade.prepareStatement("INSERT INTO DefaultAccess VALUES (?, ?)");

			for (Form form : forms)
			{
				batch.setInt(1, designation.getDesignationID());
				batch.setInt(2, form.getFormID());
				batch.addBatch();
			}

			int[] statuses = batch.executeBatch();
			
			for (int s : statuses)
				status += s;
		}
		
		return status;
	}

	int addDesignation(Designation designation) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("INSERT INTO Designation (Name) VALUES (?)");

		ps.setString(1, designation.getName());

		int status = ps.executeUpdate();

		if (status != 0)
		{
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next())
			{
				designation.setDesignationID(rs.getInt(1));
				setDefaultAccess(designation, true);
			}
		}
		
		return status;
	}

	int updateDesignation(Designation designation) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("UPDATE Designation SET Name = ? WHERE DesignationID = ?");

		ps.setString(1, designation.getName());
		ps.setInt(2, designation.getDesignationID());

		int status = ps.executeUpdate();
		int temp = setDefaultAccess(designation, false);
		
		if (status == 0)
			status = temp;

		return status;
	}

	private Designation read(ResultSet rs) throws SQLException
	{
		Designation designation = new Designation();

		designation.setDesignationID(rs.getInt("DesignationID"));
		designation.setName(rs.getString("Name"));
		designation.setForms(facade.getForms(designation));

		return designation;
	}

	ArrayList<Designation> getDesignations() throws SQLException
	{
		ArrayList<Designation> designations = new ArrayList<>();
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Designation ORDER BY DesignationID");
		ResultSet rs = ps.executeQuery();

		while (rs.next())
			designations.add(read(rs));

		return designations;
	}
}