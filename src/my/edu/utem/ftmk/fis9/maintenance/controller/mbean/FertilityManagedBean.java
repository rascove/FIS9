package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Fertility;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "fertilityMBean")
public class FertilityManagedBean extends AbstractManagedBean<Fertility>
{
	private static final long serialVersionUID = VERSION;

	public FertilityManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getFertilities();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Fertility getFertility()
	{
		return model;
	}

	public void setFertility(Fertility fertility)
	{
		this.model = fertility;
	}

	public ArrayList<Fertility> getFertilities()
	{
		return models;
	}

	public void setFertilities(ArrayList<Fertility> fertilities)
	{
		this.models = fertilities;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Fertility() : (Fertility) copy(models, model);
	}

	public void fertilityEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addFertility(model) : facade.updateFertility(model), addOperation, facade, "kesuburan, ID " + model.getFertilityID(), null, models, model);
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