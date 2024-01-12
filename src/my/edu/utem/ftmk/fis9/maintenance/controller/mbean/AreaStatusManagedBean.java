package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.AreaStatus;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "areaStatusMBean")
public class AreaStatusManagedBean extends AbstractManagedBean<AreaStatus>
{
	private static final long serialVersionUID = VERSION;

	public AreaStatusManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getAreaStatuses();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public AreaStatus getAreaStatus()
	{
		return model;
	}

	public void setAreaStatus(AreaStatus areaStatus)
	{
		this.model = areaStatus;
	}

	public ArrayList<AreaStatus> getAreaStatuses()
	{
		return models;
	}

	public void setAreaStatuses(ArrayList<AreaStatus> areaStatuses)
	{
		this.models = areaStatuses;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new AreaStatus() : (AreaStatus) copy(models, model);
	}

	public void areaStatusEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addAreaStatus(model) : facade.updateAreaStatus(model), addOperation, facade, "keadaan kawasan, ID " + model.getAreaStatusID(), null, models, model);
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