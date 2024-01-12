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
import my.edu.utem.ftmk.fis9.maintenance.model.ForestDevelopmentSubWorkType;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestDevelopmentWorkType;
import my.edu.utem.ftmk.fis9.maintenance.model.LicenseStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;
import my.edu.utem.ftmk.fis9.revenue.model.ForestDevelopmentContractor;
import my.edu.utem.ftmk.fis9.revenue.model.ForestDevelopmentContractorSubWorkTypeRecord;
import my.edu.utem.ftmk.fis9.revenue.model.Receipt;
import my.edu.utem.ftmk.fis9.revenue.model.Renew;

/**
 * @author Nor Azman Bin Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "forestDevelopmentContractorMBean")
public class ForestDevelopmentContractorManagedBean extends AbstractManagedBean<ForestDevelopmentContractor>
{
	private static final long serialVersionUID = VERSION;
	private District district;
	private ArrayList<LicenseStatus> licenseStatuses;
	private ArrayList<State> states;
	private ArrayList<Renew> renews;
	private ArrayList<ForestDevelopmentWorkType> forestDevelopmentWorkTypes;
	private ArrayList<ForestDevelopmentSubWorkType> forestDevelopmentSubWorkTypes;
	private ArrayList<ForestDevelopmentSubWorkType> selectedForestDevelopmentSubWorkTypes;
	private ArrayList<District> districts;
	private ArrayList<Receipt> receipts;
	private ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> forestDevelopmentContractorSubWorkTypeRecords;
	private String message;
	private Date today;
	private Date nextYear;
	private Date lastYear;	
	private int duration;

	public ForestDevelopmentContractorManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); RevenueFacade rFacade = new RevenueFacade();)
		{
			AbstractFacade.group(mFacade, rFacade);
			
			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			if (stateID == 0)
			{
				models = rFacade.getForestDevelopmentContractors("A");
			}
			else
			{				
				State state = mFacade.getState(stateID);
				district = mFacade.getDistrict(user);
				
				if (state.getDirectorID().equals(staffID) || (designationID > 24 && designationID < 27) || (designationID == 27 && district == null))
				{
					models = rFacade.getForestDevelopmentContractors(state, "A");
				}
				else
				{
					models = rFacade.getForestDevelopmentContractors(user, "A");
				}
			}
			
			receipts = rFacade.getReceipts("2","I");
			
			licenseStatuses = mFacade.getLicenseStatuss("A");
			
			states = mFacade.getStates();

			Calendar calToday = Calendar.getInstance();
			today = resetCalendar(calToday, 0, 0);
			
			Calendar calLastYear = Calendar.getInstance();
			lastYear = resetCalendar(calLastYear, -1, 0);
			
			Calendar calNextYear = Calendar.getInstance();
			nextYear = resetCalendar(calNextYear, 1, 0);
			
			Calendar calNextMonth = Calendar.getInstance();
			Date nextThreeMonth = resetCalendar(calNextMonth, 0, 3);

			renews = rFacade.getRenews("F", "A", lastYear);
			
			boolean exist = false;

			ArrayList<ForestDevelopmentContractor> tempForestDevelopmentContractors = new ArrayList<ForestDevelopmentContractor>();
			for(ForestDevelopmentContractor forestDevelopmentContractor : models)
			{
				exist = false;
				forestDevelopmentContractor.setEndDate(lastYear);
				forestDevelopmentContractor.setRenews(new ArrayList<Renew>());
				for(Renew renew : renews)
				{
					if(forestDevelopmentContractor.getForestDevelopmentContractorID() == renew.getForestDevelopmentContractorID())
					{
						exist = true;
						if(forestDevelopmentContractor.getEndDate().compareTo(renew.getEndDate()) <= 0)
						{
							forestDevelopmentContractor.setEndDate(renew.getEndDate());
						}
						forestDevelopmentContractor.getRenews().add(renew);
					}
				}
				sort(forestDevelopmentContractor.getRenews());
				if(exist == true)
				{
					if(forestDevelopmentContractor.getEndDate().compareTo(today) < 0)
					{
						forestDevelopmentContractor.setStatus("E");
					}
					else
					{
						if(forestDevelopmentContractor.getEndDate().compareTo(nextThreeMonth) > 0)
						{
							forestDevelopmentContractor.setStatus("A");
						}
						else 
						{
							forestDevelopmentContractor.setStatus("W");							
						}	
					}
					tempForestDevelopmentContractors.add(forestDevelopmentContractor);
				}
			}
			models = tempForestDevelopmentContractors;
			tempForestDevelopmentContractors = null;

			forestDevelopmentContractorSubWorkTypeRecords = rFacade.getForestDevelopmentContractorSubWorkTypeRecords("A");
		
			for(ForestDevelopmentContractor forestDevelopmentContractor : models)
			{
				forestDevelopmentContractor.setForestDevelopmentContractorSubWorkTypeRecords(new ArrayList<ForestDevelopmentContractorSubWorkTypeRecord>());
				for(ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord : forestDevelopmentContractorSubWorkTypeRecords) 
				{
					if(forestDevelopmentContractor.getForestDevelopmentContractorID() == forestDevelopmentContractorSubWorkTypeRecord.getForestDevelopmentContractorID())
					{
						ForestDevelopmentContractorSubWorkTypeRecord tempForestDevelopmentContractorSubWorkTypeRecord = forestDevelopmentContractorSubWorkTypeRecord;
						forestDevelopmentContractor.getForestDevelopmentContractorSubWorkTypeRecords().add(tempForestDevelopmentContractorSubWorkTypeRecord);
					}
				}
				sort(forestDevelopmentContractor.getForestDevelopmentContractorSubWorkTypeRecords());
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public ForestDevelopmentContractor getForestDevelopmentContractor()
	{
		return model;
	}

	public void setForestDevelopmentContractor(ForestDevelopmentContractor forestDevelopmentContractor)
	{
		this.model = forestDevelopmentContractor;
	}

	public ArrayList<ForestDevelopmentContractor> getForestDevelopmentContractors()
	{
		return models;
	}

	public void setForestDevelopmentContractors(ArrayList<ForestDevelopmentContractor> forestDevelopmentContractors)
	{
		this.models = forestDevelopmentContractors;
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

	public ArrayList<ForestDevelopmentSubWorkType> getSelectedForestDevelopmentSubWorkTypes() {
		return selectedForestDevelopmentSubWorkTypes;
	}

	public void setSelectedForestDevelopmentSubWorkTypes(
			ArrayList<ForestDevelopmentSubWorkType> selectedForestDevelopmentSubWorkTypes) {
		this.selectedForestDevelopmentSubWorkTypes = selectedForestDevelopmentSubWorkTypes;
	}

	public ArrayList<ForestDevelopmentWorkType> getForestDevelopmentWorkTypes() {
		return forestDevelopmentWorkTypes;
	}

	public void setForestDevelopmentWorkTypes(ArrayList<ForestDevelopmentWorkType> forestDevelopmentWorkTypes) {
		this.forestDevelopmentWorkTypes = forestDevelopmentWorkTypes;
	}

	public ArrayList<ForestDevelopmentSubWorkType> getForestDevelopmentSubWorkTypes() {
		return forestDevelopmentSubWorkTypes;
	}

	public void setForestDevelopmentSubWorkTypes(ArrayList<ForestDevelopmentSubWorkType> forestDevelopmentSubWorkTypes) {
		this.forestDevelopmentSubWorkTypes = forestDevelopmentSubWorkTypes;
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
			model = new ForestDevelopmentContractor();	
			
			Date date = new Date();
			Staff user = getCurrentUser();

			model.setForestDevelopmentContractorID(date.getTime());
			model.setRegistrationDate(date);
			model.setRecorderID(user.getStaffID());
			model.setRecorderName(user.getName());
			model.setRenews(new ArrayList<Renew>());
			model.setForestDevelopmentContractorSubWorkTypeRecords(new ArrayList<ForestDevelopmentContractorSubWorkTypeRecord>());
			model.setStatus("A");
		}
		else
			model = (ForestDevelopmentContractor) copy(models, model);
	}
	
	public void handleReceiptIDChange()
	{
		clearFilter();

		for (Receipt receipt : receipts)
		{
			if (receipt.getReceiptID() == model.getReceiptID())
			{
				model.setName(receipt.getName());
				break;
			}
		}
	}
	
	public void prepareForestDevelopmentContractor()
	{	
		execute("PF('popup').hide()");
		update("frmEntryForestDevelopmentContractorSubWorkType");
		execute("PF('popupForestDevelopmentContractorSubWorkType').show()");
	}

	public void forestDevelopmentContractorEntry()
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
			
			model.setRegistrationNo(model.getRegistrationNo().toUpperCase());
			
			if ("".equals(model.getTelNo()))
				model.setTelNo(null);

			if ("".equals(model.getHpNo()))
				model.setHpNo(null);
			
			if ("".equals(model.getRegisteredBusinessNo()))
				model.setRegisteredBusinessNo(null);			

			if ("".equals(model.getContractorServiceCenterTitle()))
				model.setContractorServiceCenterTitle(null);

			if ("".equals(model.getPkkRegistrationCertificateNo()))
				model.setPkkRegistrationCertificateNo(null);
			
			if ("".equals(model.getCidbRegistrationNo()))
				model.setCidbRegistrationNo(null);
			
			if ("".equals(model.getMachineryDescription()))
				model.setMachineryDescription(null);
			
			if ("".equals(model.getPreviousExperience()))
				model.setPreviousExperience(null);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(model.getStartDate());
			calendar.add(Calendar.MONTH, duration);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			model.setEndDate(calendar.getTime());
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			model.setRecordTime(timestamp);
			
			if(addOperation)
			{
				if(facade.addForestDevelopmentContractor(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya ditambahkan.");
					log(facade, "Tambah kontraktor pembangunan hutan, ID " + model.getForestDevelopmentContractorID());	
					
					/*Create renew*/
					Renew renew = new Renew(); 
					renew.setRenewID(timestamp.getTime());
					renew.setForestDevelopmentContractorID(model.getForestDevelopmentContractorID());
					renew.setType("F");
					renew.setStartDate(model.getStartDate());
					renew.setReceiptID(model.getReceiptID());
					renew.setEndDate(model.getEndDate());
					renew.setStatus("A");
					if(facade.addRenew(renew) != 0)
					{
						model.getRenews().add(renew);
						model.setEndDate(renew.getEndDate());
					}
					renew = null;
					
					/*Update receipt*/
					for(Receipt receipt : receipts)
					{
						if(receipt.getReceiptID() == model.getReceiptID())
						{
							receipt.setForestDevelopmentContractorID(model.getForestDevelopmentContractorID());
							receipt.setStatus("A");
							if(facade.updateStatusReceipt(receipt) != 0)
							{
								log(facade, "Kemaskini status resit daftar kontraktor pembangunan hutan, ID " + receipt.getReceiptID());
								receipts.remove(receipt);
							}
							break;
						}
					}
					models.add(model);										
				}
				else
				{
					addMessage(FacesMessage.SEVERITY_WARN, null, model + " tidak dapat ditambahkan kerana no. pendaftaran perniagaan telah direkodkan sebelumnya.");
				}
			}			
			else
			{
				if(facade.updateForestDevelopmentContractor(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya dikemaskini.");
					log(facade, "Kemaskini kontraktor pembangunan hutan, ID " + model.getForestDevelopmentContractorID());
					int index = models.indexOf(model);
					models.set(index, model);
				}				
			}		
			model = null;
			execute("PF('popup').hide()");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void cancelForestDevelopmentContractor()
	{
		try (RevenueFacade facade = new RevenueFacade())
		{
			if (facade.deleteForestDevelopmentContractor(model) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya dipadamkan.");
				models.remove(model);
				log(facade, "Padam kontraktor pembangunan hutan, ID " + model.getForestDevelopmentContractorID());
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