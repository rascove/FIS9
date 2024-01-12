package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Bank;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "bankMBean")
public class BankManagedBean extends AbstractManagedBean<Bank>
{
	private static final long serialVersionUID = VERSION;

	public BankManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getBanks("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Bank getBank()
	{
		return model;
	}

	public void setBank(Bank bank)
	{
		this.model = bank;
	}

	public ArrayList<Bank> getBanks()
	{
		return models;
	}

	public void setBanks(ArrayList<Bank> banks)
	{
		this.models = banks;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new Bank() : (Bank) copy(models, model);
	}

	public void bankEntry()
	{
		if(addOperation) model.setStatus("A");
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addBank(model) : facade.updateBank(model), addOperation, facade, "bank, ID " + model.getBankID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void bankDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteBank(model);
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