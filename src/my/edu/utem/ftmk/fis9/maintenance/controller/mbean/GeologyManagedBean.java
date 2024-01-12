package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Geology;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "geologyMBean")
public class GeologyManagedBean extends AbstractManagedBean<Geology>
{
	private static final long serialVersionUID = VERSION;

	public GeologyManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getGeologies();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Geology getGeology()
	{
		return model;
	}

	public void setGeology(Geology geology)
	{
		this.model = geology;
	}

	public ArrayList<Geology> getGeologies()
	{
		return models;
	}

	public void setGeologies(ArrayList<Geology> geologies)
	{
		this.models = geologies;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Geology() : (Geology) copy(models, model);
	}

	public void geologyEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addGeology(model) : facade.updateGeology(model), addOperation, facade, "geologi, ID " + model.getGeologyID(), null, models, model);
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