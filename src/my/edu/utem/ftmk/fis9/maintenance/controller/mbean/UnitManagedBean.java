package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Unit;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "unitMBean")
public class UnitManagedBean extends AbstractManagedBean<Unit>
{
	private static final long serialVersionUID = VERSION;

	public UnitManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getUnits("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Unit getUnit()
	{
		return model;
	}

	public void setUnit(Unit unit)
	{
		this.model = unit;
	}

	public ArrayList<Unit> getUnits()
	{
		return models;
	}

	public void setUnits(ArrayList<Unit> units)
	{
		this.models = units;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Unit() : (Unit) copy(models, model);
	}

	public void unitEntry()
	{
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addUnit(model) : facade.updateUnit(model), addOperation, facade, "kumpulan keluaran, ID " + model.getUnitID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void unitDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteUnit(model);
			models.remove(model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupDelete').hide()");
	}
}