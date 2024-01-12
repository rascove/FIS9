package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Dominance;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "dominanceMBean")
public class DominanceManagedBean extends AbstractManagedBean<Dominance>
{
	private static final long serialVersionUID = VERSION;

	public DominanceManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getDominances();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Dominance getDominance()
	{
		return model;
	}

	public void setDominance(Dominance dominance)
	{
		this.model = dominance;
	}

	public ArrayList<Dominance> getDominances()
	{
		return models;
	}

	public void setDominances(ArrayList<Dominance> dominances)
	{
		this.models = dominances;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Dominance() : (Dominance) copy(models, model);
	}

	public void dominanceEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addDominance(model) : facade.updateDominance(model), addOperation, facade, "keunggulan, ID " + model.getDominanceID(), null, models, model);
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