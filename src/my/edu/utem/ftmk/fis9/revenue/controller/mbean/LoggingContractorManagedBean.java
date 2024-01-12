package my.edu.utem.ftmk.fis9.revenue.controller.mbean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.LicenseStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;
import my.edu.utem.ftmk.fis9.revenue.model.LoggingContractor;
import my.edu.utem.ftmk.fis9.revenue.model.Receipt;
import my.edu.utem.ftmk.fis9.revenue.model.Renew;

/**
 * @author Nor Azman Bin Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "loggingContractorMBean")
public class LoggingContractorManagedBean extends AbstractManagedBean<LoggingContractor>
{
	private static final long serialVersionUID = VERSION;
	private District district;
	private ArrayList<LicenseStatus> licenseStatuses;
	private ArrayList<Renew> renews;
	private ArrayList<State> states;
	private ArrayList<District> districts;
	private ArrayList<Receipt> receipts;
	private Date today;
	private Date nextYear;
	private Date lastYear;	
	private int selectedReceiptID;
	private String message;
	private int duration;

	public LoggingContractorManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); RevenueFacade rFacade = new RevenueFacade();)
		{
			AbstractFacade.group(mFacade, rFacade);
			
			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
			{
				models = rFacade.getLoggingContractors("A");
			}
			else
			{				
				State state = mFacade.getState(stateID);
				district = mFacade.getDistrict(user);

				if (state.getDirectorID().equals(staffID) || (designationID > 24 && designationID < 27) || (designationID == 27 && district == null))
				{
					models = rFacade.getLoggingContractors(state, "A");
				}
				else
				{
					models = rFacade.getLoggingContractors(user, "A");
				}
			}
			receipts = rFacade.getReceipts("3","I", "K", "L");
			
			licenseStatuses = mFacade.getLicenseStatuss("A");

			Calendar calToday = Calendar.getInstance();
			today = resetCalendar(calToday, 0, 0);
			
			Calendar calLastYear = Calendar.getInstance();
			lastYear = resetCalendar(calLastYear, -1, 0);
			
			Calendar calNextYear = Calendar.getInstance();
			nextYear = resetCalendar(calNextYear, 1, 0);
			
			Calendar calNextMonth = Calendar.getInstance();
			Date nextThreeMonth = resetCalendar(calNextMonth, 0, 3);

			renews = rFacade.getRenews("C", "A", lastYear);
			
			boolean exist = false;
			
			ArrayList<LoggingContractor> tempLoggingContractors = new ArrayList<LoggingContractor>();
			for(LoggingContractor loggingContractor : models)
			{
				exist = false;
				loggingContractor.setEndDate(lastYear);
				loggingContractor.setRenews(new ArrayList<Renew>());
				for(Renew renew : renews)
				{
					if(loggingContractor.getLoggingContractorID() == renew.getLoggingContractorID())
					{
						exist = true;
						if(loggingContractor.getEndDate().compareTo(renew.getEndDate()) <= 0)
						{
							loggingContractor.setEndDate(renew.getEndDate());
						}
						loggingContractor.getRenews().add(renew);
					}
				}
				sort(loggingContractor.getRenews());
				if(exist == true)
				{
					if(loggingContractor.getEndDate().compareTo(today) < 0)
					{
						loggingContractor.setStatus("E");
					}
					else
					{
						if(loggingContractor.getEndDate().compareTo(nextThreeMonth) > 0)
						{
							loggingContractor.setStatus("A");
						}
						else 
						{
							loggingContractor.setStatus("W");							
						}	
					}
					tempLoggingContractors.add(loggingContractor);
				}

			}
			
			
			states = mFacade.getStates();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public LoggingContractor getLoggingContractor()
	{
		return model;
	}

	public void setLoggingContractor(LoggingContractor loggingContractor)
	{
		this.model = loggingContractor;
	}

	public ArrayList<LoggingContractor> getLoggingContractors()
	{
		return models;
	}

	public void setLoggingContractors(ArrayList<LoggingContractor> loggingContractors)
	{
		this.models = loggingContractors;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public ArrayList<District> getDistricts() {
		return districts;
	}

	public void setDistricts(ArrayList<District> districts) {
		this.districts = districts;
	}

	public ArrayList<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(ArrayList<Receipt> receipts) {
		this.receipts = receipts;
	}

	public int getSelectedReceiptID() {
		return selectedReceiptID;
	}

	public void setSelectedReceiptID(int selectedReceiptID) {
		this.selectedReceiptID = selectedReceiptID;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public Date getNextYear() {
		return nextYear;
	}

	public void setNextYear(Date nextYear) {
		this.nextYear = nextYear;
	}

	public Date getLastYear() {
		return lastYear;
	}

	public void setLastYear(Date lastYear) {
		this.lastYear = lastYear;
	}

	public ArrayList<LicenseStatus> getLicenseStatuses() {
		return licenseStatuses;
	}

	public void setLicenseStatuses(ArrayList<LicenseStatus> licenseStatuses) {
		this.licenseStatuses = licenseStatuses;
	}

	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getComponent()
	{
		return ":frmManager:table" + (model == null ? "" : ":" + models.indexOf(model) + ":subtable");
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			model = new LoggingContractor();	
			
			Date date = new Date();
			Staff user = getCurrentUser();

			model.setLoggingContractorID(date.getTime());
			model.setRegistrationDate(date);
			model.setStartDate(date);
			model.setRecorderID(user.getStaffID());
			model.setRecorderName(user.getName());
			model.setRenews(new ArrayList<Renew>());
			model.setStatus("A");
		}
		else
			model = (LoggingContractor) copy(models, model);
	}
	
	public void handleReceiptIDChange()
	{
		clearFilter();

		for (Receipt receipt : receipts)
		{
			if (receipt.getReceiptID() == model.getReceiptID())
			{
				model.setName(receipt.getName());
				model.setReceiptStatus(receipt.getStatus());
				break;
			}
		}
	}

	public void loggingContractorEntry()
	{	
		try (RevenueFacade facade = new RevenueFacade())
		{
			for(Receipt receipt : receipts)
			{
				if(receipt.getReceiptID() == model.getReceiptID())
				{
					model.setReceiptNo(receipt.getReceiptNo());
					break;
				}
			}
			
			for(LicenseStatus licenseStatus : licenseStatuses)
			{
				if(licenseStatus.getLicenseStatusID() == model.getLicenseStatusID())
				{
					model.setLicenseStatusCode(licenseStatus.getCode());
					model.setLicenseStatusName(licenseStatus.getName());
					break;
				}
			}
			
			for(State state : states)
			{
				if(state.getStateID() == model.getStateID())
				{
					model.setStateName(state.getName());
					break;
				}
			}
			
			if ("".equals(model.getBusinessRegistrationNo()))
				model.setBusinessRegistrationNo(null);
			
			if ("".equals(model.getTelNo()))
				model.setTelNo(null);
			
			if ("".equals(model.getPreviousLicensePermitNo()))
				model.setPreviousLicensePermitNo(null);
			
			model.setRegistrationSerialNo(model.getRegistrationSerialNo().toUpperCase());
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(model.getStartDate());
			calendar.add(Calendar.MONTH, duration);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			model.setEndDate(calendar.getTime());
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			model.setRecordTime(timestamp);
			
			if(addOperation)
			{
				if(facade.addLoggingContractor(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya ditambahkan.");
					log(facade, "Tambah kontraktor pembangunan hutan, ID " + model.getLoggingContractorID());		
					
					/*Create renew*/
					Renew renew = new Renew();
					renew.setRenewID(timestamp.getTime());
					renew.setType("C");
					renew.setLoggingContractorID(model.getLoggingContractorID());
					renew.setStartDate(model.getStartDate());
					renew.setReceiptID(model.getReceiptID());
					renew.setEndDate(model.getEndDate());
					renew.setStatus("A");
					if(facade.addRenew(renew) != 0)
					{
						log(facade, "Tambah renew, ID " + renew.getRenewID());	
						model.getRenews().add(renew);
						model.setEndDate(renew.getEndDate());

					}
					
					/*Update receipt*/
					for(Receipt receipt : receipts)
					{
						if(receipt.getReceiptID() == model.getReceiptID())
						{
							receipt.setLoggingContractorID(model.getLoggingContractorID());
							if(receipt.getStatus().equalsIgnoreCase("I"))
							{
								if(model.getType().equalsIgnoreCase("L"))
								{
									receipt.setStatus("L");
								}
								else
								{
									if(model.getType().equalsIgnoreCase("C"))
									{
										receipt.setStatus("K");
									}
								}
							}
							else
							{
								receipt.setStatus("A");
							}
							
							if(facade.updateStatusReceipt(receipt) != 0)
							{
								log(facade, "Kemaskini status resit daftar kontraktor pembalakan, ID " + receipt.getReceiptID());
								if(receipt.getStatus().equalsIgnoreCase("A")) receipts.remove(receipt);
							}
							break;
						}
					}

					models.add(model);
				}
				else
				{
					addMessage(FacesMessage.SEVERITY_WARN, null, model + " tidak dapat ditambahkan kerana no. siri pendaftaran telah direkodkan sebelumnya.");
				}
			}			
			else
			{
				if(facade.updateLoggingContractor(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya dikemaskini.");
					log(facade, "Kemaskini pengusaha, ID " + model.getLoggingContractorID());
					int index = models.indexOf(model);
					models.set(index, model);
				}				
			}
			
			model = null;
			duration = 0;
			selectedReceiptID = 0;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
		execute("PF('popup').hide()");
	}

	public void cancelLoggingContractor()
	{
		try (RevenueFacade facade = new RevenueFacade())
		{
			if (facade.deleteLoggingContractor(model) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya dipadamkan.");
				models.remove(model);
				log(facade, "Padam pengusaha/kontraktor pembalakan, ID " + model.getLoggingContractorID());
			}
			else
				addMessage(FacesMessage.SEVERITY_WARN, null, model + " tidak dapat dipadamkan.");

			model = null;
			execute("PF('popupProcess').hide()");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}
	
	private Date resetCalendar(Calendar cal, int year, int month)
	{
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if(year != 0)
		{
			cal.add(Calendar.YEAR, year);
		}
		if(month != 0)
		{
			cal.add(Calendar.MONTH, month);	
		}
		
		return cal.getTime();
	}
}