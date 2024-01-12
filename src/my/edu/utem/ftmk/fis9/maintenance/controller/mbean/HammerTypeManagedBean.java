package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.HammerType;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "hammerTypeMBean")
public class HammerTypeManagedBean extends AbstractManagedBean<HammerType>
{
	private static final long serialVersionUID = VERSION;

	public HammerTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getHammerTypes();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public HammerType getHammerType()
	{
		return model;
	}

	public void setHammerType(HammerType hammerType)
	{
		this.model = hammerType;
	}

	public ArrayList<HammerType> getHammerTypes()
	{
		return models;
	}

	public void setHammerTypes(ArrayList<HammerType> hammerTypes)
	{
		this.models = hammerTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new HammerType() : (HammerType) copy(models, model);
	}

	public void hammerTypeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addHammerType(model) : facade.updateHammerType(model), addOperation, facade, "jenis tukul, ID " + model.getHammerTypeID(), null, models, model);
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