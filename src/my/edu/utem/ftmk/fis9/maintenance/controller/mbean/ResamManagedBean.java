package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Resam;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "resamMBean")
public class ResamManagedBean extends AbstractManagedBean<Resam>
{
	private static final long serialVersionUID = VERSION;

	public ResamManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getResams();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Resam getResam()
	{
		return model;
	}

	public void setResam(Resam resam)
	{
		this.model = resam;
	}

	public ArrayList<Resam> getResams()
	{
		return models;
	}

	public void setResams(ArrayList<Resam> resams)
	{
		this.models = resams;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Resam() : (Resam) copy(models, model);
	}

	public void resamEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addResam(model) : facade.updateResam(model), addOperation, facade, "resam, ID " + model.getResamID(), null, models, model);
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