package my.edu.utem.ftmk.fis9.maintenance.controller.mbean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.LogSize;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "logSizeMBean")
public class LogSizeManagedBean extends AbstractManagedBean<LogSize>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<State> states;
	private boolean logSizeExist;

	public LogSizeManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			Staff user = getCurrentUser();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
			{
				if (designationID == 0)
				{
					models = facade.getLogSizes("A");
					states = facade.getStates();
				}
				else
					models = new ArrayList<>();
			}
			else
			{
				State state = facade.getState(stateID);
				models.add(facade.getLogSize(state));
				states = new ArrayList<>();

				states.add(state);
			}

			sort(models);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public LogSize getLogSize()
	{
		return model;
	}

	public void setLogSize(LogSize logSize)
	{
		this.model = logSize;
	}

	public ArrayList<LogSize> getLogSizes()
	{
		return models;
	}

	public void setLogSizes(ArrayList<LogSize> logSizes)
	{
		this.models = logSizes;
	}

	public ArrayList<State> getStates()
	{
		return states;
	}

	public void setStates(ArrayList<State> states)
	{
		this.states = states;
	}

	public boolean isLogSizeExist() {
		return logSizeExist;
	}

	public void setLogSizeExist(boolean logSizeExist) {
		this.logSizeExist = logSizeExist;
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new LogSize();
			Staff user = getCurrentUser();
			int stateID = user.getStateID();

			if (stateID != 0)
				model.setStateID(stateID);
		}
		else
			model = (LogSize) copy(models, model);
	}

	public void logSizeEntry()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			logSizeExist = false;
			for (State state : states)
				if (state.getStateID() == model.getStateID())
					model.setStateName(state.getName());
			
			if(addOperation)
			{
				for(LogSize ls : models)
				{
					if(ls.getStateID() == model.getStateID()) 
					{
						logSizeExist = true;
						addMessage(FacesMessage.SEVERITY_WARN, null, "Saiz balak tidak dapat ditambah kerana rekod saiz balak bagi "+model.getStateName()+" telah wujud");
					}
				}
			}
			
			if(logSizeExist == false)
			{
				model.setStartDate(new Timestamp(Calendar.getInstance().getTime().getTime()));
				model.setEndDate(null);
				model.setStatus("A");

				finalizeModelEntry(addOperation ? facade.addLogSize(model) : facade.updateLogSize(model), addOperation, facade, "daftar saiz balak, ID " + model.getLogSizeID(), null, models, model);
				model = null;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	
			execute("PF('popup').hide()");
		
	}
}