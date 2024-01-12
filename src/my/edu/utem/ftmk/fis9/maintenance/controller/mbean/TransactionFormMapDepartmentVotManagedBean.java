package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.DepartmentVot;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionForm;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionFormMapDepartmentVot;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "transactionFormMapDepartmentVotMBean")
public class TransactionFormMapDepartmentVotManagedBean extends AbstractManagedBean<TransactionFormMapDepartmentVot>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<TransactionForm> transactionForms;
	private ArrayList<DepartmentVot> departmentVots;

	public TransactionFormMapDepartmentVotManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getTransactionFormMapDepartmentVots("A");
			transactionForms = facade.getTransactionForms("A");
			departmentVots = facade.getDepartmentVots("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TransactionFormMapDepartmentVot getTransactionFormMapDepartmentVot()
	{
		return model;
	}

	public void setTransactionFormMapDepartmentVot(TransactionFormMapDepartmentVot transactionFormMapDepartmentVot)
	{
		this.model = transactionFormMapDepartmentVot;
	}

	public ArrayList<TransactionFormMapDepartmentVot> getTransactionFormMapDepartmentVots()
	{
		return models;
	}

	public void setTransactionFormMapDepartmentVots(ArrayList<TransactionFormMapDepartmentVot> transactionFormMapDepartmentVots)
	{
		this.models = transactionFormMapDepartmentVots;
	}

	public ArrayList<TransactionForm> getTransactionForms() 
	{
		return transactionForms;
	}

	public void setTransactionForms(ArrayList<TransactionForm> transactionForms) 
	{
		this.transactionForms = transactionForms;
	}

	public ArrayList<DepartmentVot> getDepartmentVots() 
	{
		return departmentVots;
	}

	public void setDepartmentVots(ArrayList<DepartmentVot> departmentVots) 
	{
		this.departmentVots = departmentVots;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new TransactionFormMapDepartmentVot() : (TransactionFormMapDepartmentVot) copy(models, model);
	}

	public void transactionFormMapDepartmentVotEntry()
	{
		for (TransactionForm transactionForm : transactionForms)
		{
			if (transactionForm.getTransactionFormID() == model.getTransactionFormID())
			{
				model.setTransactionFormCode(transactionForm.getCode());
				model.setTransactionFormName(transactionForm.getName());
			}
		}
		for (DepartmentVot departmentVot : departmentVots)
		{
			if (departmentVot.getDepartmentVotID() == model.getDepartmentVotID())
			{
				model.setDepartmentVotCode(departmentVot.getCode());
				model.setDepartmentVotName(departmentVot.getName());
			}
		}
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addTransactionFormMapDepartmentVot(model) : facade.updateTransactionFormMapDepartmentVot(model), addOperation, facade, "vot jabatan, ID " + model.getTransactionFormMapDepartmentVotID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void transactionFormMapDepartmentVotDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteTransactionFormMapDepartmentVot(model);
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