package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.DepartmentVot;
import my.edu.utem.ftmk.fis9.maintenance.model.TrustFund;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "trustFundMBean")
public class TrustFundManagedBean extends AbstractManagedBean<TrustFund>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<DepartmentVot> departmentVots;

	public TrustFundManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getTrustFunds("A");
			departmentVots = facade.getDepartmentVots("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TrustFund getTrustFund()
	{
		return model;
	}

	public void setTrustFund(TrustFund trustFund)
	{
		this.model = trustFund;
	}

	public ArrayList<TrustFund> getTrustFunds()
	{
		return models;
	}

	public void setTrustFunds(ArrayList<TrustFund> trustFunds)
	{
		this.models = trustFunds;
	}

	public ArrayList<DepartmentVot> getDepartmentVots() {
		return departmentVots;
	}

	public void setDepartmentVots(ArrayList<DepartmentVot> departmentVots) {
		this.departmentVots = departmentVots;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new TrustFund() : (TrustFund) copy(models, model);
	}

	public void trustFundEntry()
	{
		for (DepartmentVot departmentVot : departmentVots)
			if (departmentVot.getDepartmentVotID() == model.getDepartmentVotID())
				model.setDepartmentVotName(departmentVot.getName());
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addTrustFund(model) : facade.updateTrustFund(model), addOperation, facade, "vot jabatan, ID " + model.getTrustFundID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void trustFundDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteTrustFund(model);
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