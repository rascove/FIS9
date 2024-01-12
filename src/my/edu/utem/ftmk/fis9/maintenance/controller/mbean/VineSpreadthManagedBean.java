package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.VineSpreadth;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "vineSpreadthMBean")
public class VineSpreadthManagedBean extends AbstractManagedBean<VineSpreadth>
{
	private static final long serialVersionUID = VERSION;

	public VineSpreadthManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getVineSpreadths();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public VineSpreadth getVineSpreadth()
	{
		return model;
	}

	public void setVineSpreadth(VineSpreadth vineSpreadth)
	{
		this.model = vineSpreadth;
	}

	public ArrayList<VineSpreadth> getVineSpreadths()
	{
		return models;
	}

	public void setVineSpreadths(ArrayList<VineSpreadth> vineSpreadths)
	{
		this.models = vineSpreadths;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new VineSpreadth() : (VineSpreadth) copy(models, model);
	}

	public void vineSpreadthEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addVineSpreadth(model) : facade.updateVineSpreadth(model), addOperation, facade, "melingkari, ID " + model.getVineSpreadthID(), null, models, model);
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