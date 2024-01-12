package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Module;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "moduleMBean")
public class ModuleManagedBean extends AbstractManagedBean<Module>
{
	private static final long serialVersionUID = VERSION;

	public ModuleManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getModules();
			sort(models);

			for (Module module : models)
				sort(module.getForms());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public ArrayList<Module> getModules()
	{
		return models;
	}

	public void setModules(ArrayList<Module> modules)
	{
		this.models = modules;
	}

	@Override
	public void handleOpen()
	{
	}
}