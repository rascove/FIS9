package my.edu.utem.ftmk.fis9.global.controller.mbean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import my.edu.utem.ftmk.fis9.global.model.TrailLog;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;

/**
 * @author Satrya Fajri Pratama
 */
@ViewScoped
@ManagedBean(name = "trailLogMBean")
public class TrailLogManagedBean extends AbstractManagedBean<TrailLog>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<Staff> staffs;
	private String selectedStaffID;
	private Date selectedDate;

	public TrailLogManagedBean()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			Staff user = getCurrentUser();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID != 0)
			{
				State state = new State();
				state.setStateID(stateID);
				staffs = facade.getStaffs(state);
			}
			else
			{
				if (designationID == 0)
					staffs = facade.getStaffs();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public ArrayList<TrailLog> getTrailLogs()
	{
		return models;
	}

	public void setTrailLogs(ArrayList<TrailLog> trailLogs)
	{
		this.models = trailLogs;
	}

	public ArrayList<Staff> getStaffs()
	{
		return staffs;
	}

	public void setStaffs(ArrayList<Staff> staffs)
	{
		this.staffs = staffs;
	}

	public String getSelectedStaffID()
	{
		return selectedStaffID;
	}

	public void setSelectedStaffID(String selectedStaffID)
	{
		this.selectedStaffID = selectedStaffID;
	}

	public Date getSelectedDate()
	{
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate)
	{
		this.selectedDate = selectedDate;
	}

	@Override
	public void handleOpen()
	{
		try (MaintenanceFacade facade = new MaintenanceFacade())
		{
			if (selectedStaffID != null && selectedStaffID.isEmpty())
				selectedStaffID = null;

			if (selectedStaffID == null && selectedDate == null)
				addMessage(FacesMessage.SEVERITY_WARN, null,
						"Sila pilih nama pekerja dan/atau tarikh.");
			else
			{
				if (selectedStaffID != null && selectedDate != null)				
					models = facade.getTrailLogs(selectedStaffID, selectedDate);
				else
				{
					if (selectedStaffID != null)
						models = facade.getTrailLogs(selectedStaffID);
					else
						models = facade.getTrailLogs(selectedDate);
				}

				if (models != null && !models.isEmpty())
					execute("PF('popup').show()");
				else
					addMessage(FacesMessage.SEVERITY_INFO, null,
							"Tiada log aktiviti direkodkan.");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}
}