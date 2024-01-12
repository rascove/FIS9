package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestType;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "forestTypeMBean")
public class ForestTypeManagedBean extends AbstractManagedBean<ForestType>
{
	private static final long serialVersionUID = VERSION;

	public ForestTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getForestTypes();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public ForestType getForestType()
	{
		return model;
	}

	public void setForestType(ForestType forestType)
	{
		this.model = forestType;
	}

	public ArrayList<ForestType> getForestTypes()
	{
		return models;
	}

	public void setForestTypes(ArrayList<ForestType> forestTypes)
	{
		this.models = forestTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new ForestType() : (ForestType) copy(models, model);
	}

	public void forestTypeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addForestType(model) : facade.updateForestType(model), addOperation, facade, "jenis hutan, ID " + model.getForestTypeID(), null, models, model);
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