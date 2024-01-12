package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Ginger;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "gingerMBean")
public class GingerManagedBean extends AbstractManagedBean<Ginger>
{
	private static final long serialVersionUID = VERSION;

	public GingerManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getGingers();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Ginger getGinger()
	{
		return model;
	}

	public void setGinger(Ginger ginger)
	{
		this.model = ginger;
	}

	public ArrayList<Ginger> getGingers()
	{
		return models;
	}

	public void setGingers(ArrayList<Ginger> gingers)
	{
		this.models = gingers;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Ginger() : (Ginger) copy(models, model);
	}

	public void gingerEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addGinger(model) : facade.updateGinger(model), addOperation, facade, "halia, ID " + model.getGingerID(), null, models, model);
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