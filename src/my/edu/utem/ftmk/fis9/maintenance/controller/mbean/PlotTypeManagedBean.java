package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.PlotType;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "plotTypeMBean")
public class PlotTypeManagedBean extends AbstractManagedBean<PlotType>
{
	private static final long serialVersionUID = VERSION;

	public PlotTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getPlotTypes();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public PlotType getPlotType()
	{
		return model;
	}

	public void setPlotType(PlotType plotType)
	{
		this.model = plotType;
	}

	public ArrayList<PlotType> getPlotTypes()
	{
		return models;
	}

	public void setPlotTypes(ArrayList<PlotType> plotTypes)
	{
		this.models = plotTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new PlotType() : (PlotType) copy(models, model);
	}

	public void plotTypeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addPlotType(model) : facade.updatePlotType(model), addOperation, facade, "jenis petak, ID " + model.getPlotTypeID(), null, models, model);
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