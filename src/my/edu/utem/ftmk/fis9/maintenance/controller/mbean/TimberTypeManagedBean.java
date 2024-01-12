package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.TimberType;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "timberTypeMBean")
public class TimberTypeManagedBean extends AbstractManagedBean<TimberType>
{
	private static final long serialVersionUID = VERSION;

	public TimberTypeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getTimberTypes();
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TimberType getTimberType()
	{
		return model;
	}

	public void setTimberType(TimberType timberType)
	{
		this.model = timberType;
	}

	public ArrayList<TimberType> getTimberTypes()
	{
		return models;
	}

	public void setTimberTypes(ArrayList<TimberType> timberTypes)
	{
		this.models = timberTypes;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new TimberType() : (TimberType) copy(models, model);
	}

	public void timberTypeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addTimberType(model) : facade.updateTimberType(model), addOperation, facade, "jenis kayu-kayan, ID " + model.getTimberTypeID(), null, models, model);
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