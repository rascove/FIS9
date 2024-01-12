package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Elevation;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "elevationMBean")
public class ElevationManagedBean extends AbstractManagedBean<Elevation>
{
	private static final long serialVersionUID = VERSION;

	public ElevationManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getElevations();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Elevation getElevation()
	{
		return model;
	}

	public void setElevation(Elevation elevation)
	{
		this.model = elevation;
	}

	public ArrayList<Elevation> getElevations()
	{
		return models;
	}

	public void setElevations(ArrayList<Elevation> elevations)
	{
		this.models = elevations;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Elevation() : (Elevation) copy(models, model);
	}

	public void elevationEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addElevation(model) : facade.updateElevation(model), addOperation, facade, "dongakan, ID " + model.getElevationID(), null, models, model);
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