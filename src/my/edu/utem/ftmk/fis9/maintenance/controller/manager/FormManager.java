package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.Form;
import my.edu.utem.ftmk.fis9.maintenance.model.Module;

/**
 * @author Satrya Fajri Pratama
 */
class FormManager extends MaintenanceTableManager
{
	FormManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private ArrayList<Form> getForms(ResultSet rs) throws SQLException
	{
		ArrayList<Form> forms = new ArrayList<>();

		while (rs.next())
		{
			Form form = new Form();

			form.setFormID(rs.getInt("FormID"));
			form.setName(rs.getString("Name"));
			form.setPath(rs.getString("Path"));
			form.setIcon(rs.getString("Icon"));
			form.setCategory(rs.getString("Category"));
			form.setModuleID(rs.getInt("ModuleID"));

			forms.add(form);
		}

		return forms;
	}

	ArrayList<Form> getForms(Module module) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Form WHERE ModuleID = ? ORDER BY ModuleID, FormID");

		ps.setInt(1, module.getModuleID());

		return getForms(ps.executeQuery());
	}

	ArrayList<Form> getForms(Designation designation) throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Form WHERE FormID IN (SELECT FormID FROM DefaultAccess WHERE DesignationID = ?) ORDER BY ModuleID, FormID");

		ps.setInt(1, designation.getDesignationID());

		return getForms(ps.executeQuery());
	}
}