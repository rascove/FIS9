package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.SlopeLocation;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "slopeLocationMBean")
public class SlopeLocationManagedBean extends AbstractManagedBean<SlopeLocation>
{
	private static final long serialVersionUID = VERSION;

	public SlopeLocationManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getSlopeLocations();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public SlopeLocation getSlopeLocation()
	{
		return model;
	}

	public void setSlopeLocation(SlopeLocation slopeLocation)
	{
		this.model = slopeLocation;
	}

	public ArrayList<SlopeLocation> getSlopeLocations()
	{
		return models;
	}

	public void setSlopeLocations(ArrayList<SlopeLocation> slopeLocations)
	{
		this.models = slopeLocations;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new SlopeLocation() : (SlopeLocation) copy(models, model);
	}

	public void slopeLocationEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addSlopeLocation(model) : facade.updateSlopeLocation(model), addOperation, facade, "tempat cerun, ID " + model.getSlopeLocationID(), null, models, model);
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