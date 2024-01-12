package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import my.edu.utem.ftmk.fis9.maintenance.model.Module;

/**
 * @author Satrya Fajri Pratama
 */
class ModuleManager extends MaintenanceTableManager
{
	ModuleManager(MaintenanceFacade facade)
	{
		super(facade);
	}

	private ArrayList<Module> getModules(ResultSet rs) throws SQLException
	{
		ArrayList<Module> modules = new ArrayList<>();

		while (rs.next())
		{
			Module module = new Module();

			module.setModuleID(rs.getInt("ModuleID"));
			module.setName(rs.getString("Name"));
			module.setForms(facade.getForms(module));

			modules.add(module);
		}

		return modules;
	}

	ArrayList<Module> getModules() throws SQLException
	{
		PreparedStatement ps = facade.prepareStatement("SELECT * FROM Module");

		return getModules(ps.executeQuery());
	}
}