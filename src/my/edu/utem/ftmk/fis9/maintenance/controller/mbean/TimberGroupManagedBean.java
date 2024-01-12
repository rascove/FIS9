package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.TimberGroup;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "timberGroupMBean")
public class TimberGroupManagedBean extends AbstractManagedBean<TimberGroup>
{
	private static final long serialVersionUID = VERSION;

	public TimberGroupManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getTimberGroups();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TimberGroup getTimberGroup()
	{
		return model;
	}

	public void setTimberGroup(TimberGroup timberGroup)
	{
		this.model = timberGroup;
	}

	public ArrayList<TimberGroup> getTimberGroups()
	{
		return models;
	}

	public void setTimberGroups(ArrayList<TimberGroup> timberGroups)
	{
		this.models = timberGroups;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new TimberGroup() : (TimberGroup) copy(models, model);
	}

	public void timberGroupEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addTimberGroup(model) : facade.updateTimberGroup(model), addOperation, facade, "kumpulan jenis kayu-kayan, ID " + model.getTimberGroupID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
}