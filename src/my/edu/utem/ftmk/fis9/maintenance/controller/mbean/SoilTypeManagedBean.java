package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.SoilType;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "soilTypeMBean")
public class SoilTypeManagedBean extends AbstractManagedBean<SoilType>
{
	private static final long serialVersionUID = VERSION;

	public SoilTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getSoilTypes();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public SoilType getSoilType()
	{
		return model;
	}

	public void setSoilType(SoilType soilType)
	{
		this.model = soilType;
	}

	public ArrayList<SoilType> getSoilTypes()
	{
		return models;
	}

	public void setSoilTypes(ArrayList<SoilType> soilTypees)
	{
		this.models = soilTypees;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new SoilType() : (SoilType) copy(models, model);
	}

	public void soilTypeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addSoilType(model) : facade.updateSoilType(model), addOperation, facade, "jenis tanah, ID " + model.getSoilTypeID(), null, models, model);
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