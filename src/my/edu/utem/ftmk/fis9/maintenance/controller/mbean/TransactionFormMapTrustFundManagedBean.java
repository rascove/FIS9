package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.TrustFund;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionForm;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionFormMapTrustFund;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "transactionFormMapTrustFundMBean")
public class TransactionFormMapTrustFundManagedBean extends AbstractManagedBean<TransactionFormMapTrustFund>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<TransactionForm> transactionForms;
	private ArrayList<TrustFund> trustFunds;

	public TransactionFormMapTrustFundManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getTransactionFormMapTrustFunds("A");
			transactionForms = facade.getTransactionForms("A");
			trustFunds = facade.getTrustFunds("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TransactionFormMapTrustFund getTransactionFormMapTrustFund()
	{
		return model;
	}

	public void setTransactionFormMapTrustFund(TransactionFormMapTrustFund transactionFormMapTrustFund)
	{
		this.model = transactionFormMapTrustFund;
	}

	public ArrayList<TransactionFormMapTrustFund> getTransactionFormMapTrustFunds()
	{
		return models;
	}

	public void setTransactionFormMapTrustFunds(ArrayList<TransactionFormMapTrustFund> transactionFormMapTrustFunds)
	{
		this.models = transactionFormMapTrustFunds;
	}

	public ArrayList<TransactionForm> getTransactionForms() 
	{
		return transactionForms;
	}

	public void setTransactionForms(ArrayList<TransactionForm> transactionForms) 
	{
		this.transactionForms = transactionForms;
	}

	public ArrayList<TrustFund> getTrustFunds() 
	{
		return trustFunds;
	}

	public void setTrustFunds(ArrayList<TrustFund> trustFunds) 
	{
		this.trustFunds = trustFunds;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new TransactionFormMapTrustFund() : (TransactionFormMapTrustFund) copy(models, model);
	}

	public void transactionFormMapTrustFundEntry()
	{
		for (TransactionForm transactionForm : transactionForms)
			if (transactionForm.getTransactionFormID() == model.getTransactionFormID())
				model.setTransactionFormName(transactionForm.getName());
		for (TrustFund trustFund : trustFunds)
			if (trustFund.getTrustFundID() == model.getTrustFundID())
				model.setTrustFundName(trustFund.getDepartmentVotName());
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addTransactionFormMapTrustFund(model) : facade.updateTransactionFormMapTrustFund(model), addOperation, facade, "vot jabatan, ID " + model.getTransactionFormMapTrustFundID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void transactionFormMapTrustFundDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteTransactionFormMapTrustFund(model);
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