package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.BursaryVot;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "bursaryVotMBean")
public class BursaryVotManagedBean extends AbstractManagedBean<BursaryVot>
{
	private static final long serialVersionUID = VERSION;

	public BursaryVotManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			models = facade.getBursaryVots("A");
			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public BursaryVot getBursaryVot()
	{
		return model;
	}

	public void setBursaryVot(BursaryVot bursaryVot)
	{
		this.model = bursaryVot;
	}

	public ArrayList<BursaryVot> getBursaryVots()
	{
		return models;
	}

	public void setBursaryVots(ArrayList<BursaryVot> bursaryVots)
	{
		this.models = bursaryVots;
	}

	@Override
	public void handleOpen()
	{
		model = addOperation ? new BursaryVot() : (BursaryVot) copy(models, model);
	}

	public void bursaryVotEntry()
	{
		if(addOperation) model.setStatus("A");
		if(model.getType().equalsIgnoreCase("R"))
		{
			model.setTypeName("Hasil");
		}
		else
		{
			if(model.getType().equalsIgnoreCase("T"))
			{
				model.setTypeName("Amanah");
			}
		}
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			finalizeModelEntry(addOperation ? facade.addBursaryVot(model) : facade.updateBursaryVot(model), addOperation, facade, "kod objek, ID " + model.getBursaryVotID(), null, models, model);
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popup').hide()");
	}
	
	public void bursaryVotDelete()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			facade.deleteBursaryVot(model);
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