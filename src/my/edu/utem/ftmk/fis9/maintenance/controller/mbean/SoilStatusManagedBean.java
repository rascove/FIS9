package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.SoilStatus;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "soilStatusMBean")
public class SoilStatusManagedBean extends AbstractManagedBean<SoilStatus>
{
	private static final long serialVersionUID = VERSION;

	public SoilStatusManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getSoilStatuses();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public SoilStatus getSoilStatus()
	{
		return model;
	}

	public void setSoilStatus(SoilStatus soilStatus)
	{
		this.model = soilStatus;
	}

	public ArrayList<SoilStatus> getSoilStatuses()
	{
		return models;
	}

	public void setSoilStatuses(ArrayList<SoilStatus> soilStatuses)
	{
		this.models = soilStatuses;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new SoilStatus() : (SoilStatus) copy(models, model);
	}

	public void soilStatusEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addSoilStatus(model) : facade.updateSoilStatus(model), addOperation, facade, "keadaan tanah, ID " + model.getSoilStatusID(), null, models, model);
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