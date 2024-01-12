package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.LogQuality;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "logQualityMBean")
public class LogQualityManagedBean extends AbstractManagedBean<LogQuality>
{
	private static final long serialVersionUID = VERSION;

	public LogQualityManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getLogQualities();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public LogQuality getLogQuality()
	{
		return model;
	}

	public void setLogQuality(LogQuality logQuality)
	{
		this.model = logQuality;
	}

	public ArrayList<LogQuality> getLogQualities()
	{
		return models;
	}

	public void setLogQualities(ArrayList<LogQuality> logQualities)
	{
		this.models = logQualities;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new LogQuality() : (LogQuality) copy(models, model);
	}

	public void logQualityEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addLogQuality(model) : facade.updateLogQuality(model), addOperation, facade, "kualiti balak, ID " + model.getLogQualityID(), null, models, model);
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