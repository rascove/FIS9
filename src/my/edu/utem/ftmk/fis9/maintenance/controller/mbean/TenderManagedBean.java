package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.Tender;

/**
 * @author Nor Azman Mat Ariff (original)
 * @author Satrya Fajri Pratama (modified)
 */
@ViewScoped
@ManagedBean(name = "tenderMBean")
public class TenderManagedBean extends AbstractManagedBean<Tender>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<Contractor> contractors;
	private ArrayList<State> states;

	public TenderManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getTenders("A");
			contractors = facade.getContractors("A");
			Staff user = getCurrentUser();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
			{
				if (designationID == 0)
					states = facade.getStates();
			}
			else
			{
				ArrayList<Tender> temp = new ArrayList<>();

				for (Tender tender : models)
					if (tender.getStateID() == stateID)
						temp.add(tender);

				models = temp;
			}

			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Tender getTender()
	{
		return model;
	}

	public void setTender(Tender tender)
	{
		this.model = tender;
	}

	public ArrayList<Tender> getTenders()
	{
		return models;
	}

	public void setTenders(ArrayList<Tender> tenders)
	{
		this.models = tenders;
	}

	public ArrayList<Contractor> getContractors()
	{
		return contractors;
	}

	public void setContractors(ArrayList<Contractor> contractors)
	{
		this.contractors = contractors;
	}

	public ArrayList<State> getStates()
	{
		return states;
	}

	public void setStates(ArrayList<State> states)
	{
		this.states = states;
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new Tender();
			Staff user = getCurrentUser();

			model.setStatus("A");
			model.setStateID(user.getStateID());
			model.setAppointDate(new Date());
		}
		else
			model = (Tender) copy(models, model);
	}

	public void tenderEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			for (Contractor contractor : contractors)
				if (model.getContractorID().equals(contractor.getContractorID()))
					model.setContractorName(contractor.getCompanyName());

			finalizeModelEntry(addOperation ? facade.addTender(model) : facade.updateTender(model), addOperation, facade, "sebut harga, ID " + model.getTenderNo(), ", kerana no. sebut harga telah direkodkan sebelumnya", models, model);
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