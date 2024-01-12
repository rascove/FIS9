package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.MainRevenueRoyaltyRate;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "mainRevenueRoyaltyRateMBean")
public class MainRevenueRoyaltyRateManagedBean extends AbstractManagedBean<MainRevenueRoyaltyRate>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<Species> speciesList;
	private ArrayList<State> states;

	public MainRevenueRoyaltyRateManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			speciesList = facade.getSpeciesList();
			states = facade.getStates();
			models = facade.getMainRevenueRoyaltyRates("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public MainRevenueRoyaltyRate getMainRevenueRoyaltyRate()
	{
		return model;
	}

	public void setMainRevenueRoyaltyRate(MainRevenueRoyaltyRate mainRevenueRoyaltyRate)
	{
		this.model = mainRevenueRoyaltyRate;
	}

	public ArrayList<MainRevenueRoyaltyRate> getMainRevenueRoyaltyRates()
	{
		return models;
	}

	public void setMainRevenueRoyaltyRates(ArrayList<MainRevenueRoyaltyRate> mainRevenueRoyaltyRates)
	{
		this.models = mainRevenueRoyaltyRates;
	}

	public ArrayList<Species> getSpeciesList() {
		return speciesList;
	}

	public void setSpeciesList(ArrayList<Species> speciesList) {
		this.speciesList = speciesList;
	}

	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new MainRevenueRoyaltyRate() : (MainRevenueRoyaltyRate) copy(models, model);
	}

	public void mainRevenueRoyaltyRateEntry()
	{
		if(addOperation)
		{
			model.setMainRevenueRoyaltyRateID(System.currentTimeMillis());
			model.setStatus("A");
		}
		for (Species species : speciesList)
		{
			if (species.getSpeciesID() == model.getSpeciesID())
			{
				model.setSpeciesCode(species.getCode());
				model.setSpeciesName(species.getName());
			}
		}
		for (State state : states)
		{
			if (state.getStateID() == model.getStateID())
			{
				model.setStateCode(state.getCode());
				model.setStateName(state.getName());
			}
		}	
		
	    for (ListIterator<MainRevenueRoyaltyRate> itr = models.listIterator(); itr.hasNext();) 
	    {
	    	MainRevenueRoyaltyRate mainRevenueRoyaltyRate = itr.next();
	    	if((mainRevenueRoyaltyRate.getSpeciesID() == model.getSpeciesID()) && (mainRevenueRoyaltyRate.getStateID() == model.getStateID()))
	    	{
				itr.remove();
	    	}
	    }
		
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addMainRevenueRoyaltyRate(model) : 0, addOperation, facade, "kadar royalti hasil utama, ID " + model.getMainRevenueRoyaltyRateID(), null, models, model);

			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void mainRevenueRoyaltyRateDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			model.setStatus("I");
			facade.deleteMainRevenueRoyaltyRate(model);
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