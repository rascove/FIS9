package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Aspect;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "aspectMBean")
public class AspectManagedBean extends AbstractManagedBean<Aspect>
{
	private static final long serialVersionUID = VERSION;

	public AspectManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getAspects();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Aspect getAspect()
	{
		return model;
	}

	public void setAspect(Aspect aspect)
	{
		this.model = aspect;
	}

	public ArrayList<Aspect> getAspects()
	{
		return models;
	}

	public void setAspects(ArrayList<Aspect> aspects)
	{
		this.models = aspects;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Aspect() : (Aspect) copy(models, model);
	}

	public void aspectEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addAspect(model) : facade.updateAspect(model), addOperation, facade, "aspek, ID " + model.getAspectID(), null, models, model);
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