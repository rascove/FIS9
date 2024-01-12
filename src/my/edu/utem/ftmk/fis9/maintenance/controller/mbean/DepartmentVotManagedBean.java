package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.BursaryVot;
import my.edu.utem.ftmk.fis9.maintenance.model.DepartmentVot;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "departmentVotMBean")
public class DepartmentVotManagedBean extends AbstractManagedBean<DepartmentVot>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<BursaryVot> bursaryVots;

	public DepartmentVotManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getDepartmentVots("A");
			bursaryVots = facade.getBursaryVots("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public DepartmentVot getDepartmentVot()
	{
		return model;
	}

	public void setDepartmentVot(DepartmentVot departmentVot)
	{
		this.model = departmentVot;
	}

	public ArrayList<DepartmentVot> getDepartmentVots()
	{
		return models;
	}

	public void setDepartmentVots(ArrayList<DepartmentVot> departmentVots)
	{
		this.models = departmentVots;
	}

	public ArrayList<BursaryVot> getBursaryVots() {
		return bursaryVots;
	}

	public void setBursaryVots(ArrayList<BursaryVot> bursaryVots) {
		this.bursaryVots = bursaryVots;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new DepartmentVot() : (DepartmentVot) copy(models, model);
	}

	public void departmentVotEntry()
	{
		for (BursaryVot bursaryVot : bursaryVots)
		{
			if (bursaryVot.getBursaryVotID() == model.getBursaryVotID())
			{
				model.setBursaryVotCode(bursaryVot.getCode());
				model.setBursaryVotName(bursaryVot.getName());
			}
		}
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addDepartmentVot(model) : facade.updateDepartmentVot(model), addOperation, facade, "vot jabatan, ID " + model.getDepartmentVotID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void departmentVotDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteDepartmentVot(model);
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