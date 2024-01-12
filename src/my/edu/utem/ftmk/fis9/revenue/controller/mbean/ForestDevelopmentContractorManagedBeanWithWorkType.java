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
import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
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
@ManagedBean(name = "forestDevelopmentContractorMBeanWithWorkType")
public class ForestDevelopmentContractorManagedBeanWithWorkType extends AbstractManagedBean<ForestDevelopmentContractor>
{
	private static final long serialVersionUID = VERSION;
	private Receipt receipt;
	private District district;
	private ArrayList<Staff> unRegisteredForestDevelopmentContractors;
	private ArrayList<LicenseStatus> licenseStatuses;
	private ArrayList<Renew> renews;
	private ArrayList<ForestDevelopmentWorkType> forestDevelopmentWorkTypes;
	private ArrayList<ForestDevelopmentSubWorkType> forestDevelopmentSubWorkTypes;
	private ArrayList<ForestDevelopmentSubWorkType> selectedForestDevelopmentSubWorkTypes;
	private ArrayList<District> districts;
	private ArrayList<Receipt> receipts;
	private ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> forestDevelopmentContractorSubWorkTypeRecords;
	private Date today;
	private Date nextYear;
	private Date lastYear;	
	private int selectedReceiptID;

	public ForestDevelopmentContractorManagedBeanWithWorkType()
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
				
				if (state.getDirectorID().equals(staffID) || designationID > 24 && designationID < 28)
				{
					models = rFacade.getForestDevelopmentContractors(state, "A");
				}
				else
				{
					models = rFacade.getForestDevelopmentContractors(user, "A");
				}
			}
			
			Designation designation = new Designation();
			designation.setDesignationID(28);
			designation = null;
			
			receipts = rFacade.getReceipts("2","I");
			
			licenseStatuses = mFacade.getLicenseStatuss("A");
			
			forestDevelopmentWorkTypes = mFacade.getForestDevelopmentWorkTypes("A");
			forestDevelopmentSubWorkTypes = mFacade.getForestDevelopmentSubWorkTypes("A");

			Calendar calToday = Calendar.getInstance();
			today = resetCalendar(calToday, 0, 0);
			
			Calendar calLastYear = Calendar.getInstance();
			lastYear = resetCalendar(calLastYear, -1, 0);
			
			Calendar calNextYear = Calendar.getInstance();
			nextYear = resetCalendar(calNextYear, 1, 0);
			
			Calendar calNextMonth = Calendar.getInstance();
			Date nextMonth = resetCalendar(calNextMonth, 0, 1);

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
						if(forestDevelopmentContractor.getEndDate().compareTo(nextMonth) > 0)
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

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
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

	public ArrayList<Staff> getUnRegisteredForestDevelopmentContractors() {
		return unRegisteredForestDevelopmentContractors;
	}

	public void setUnRegisteredForestDevelopmentContractors(ArrayList<Staff> unRegisteredForestDevelopmentContractors) {
		this.unRegisteredForestDevelopmentContractors = unRegisteredForestDevelopmentContractors;
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
			for(LicenseStatus licenseStatus : licenseStatuses)
			{
				if(licenseStatus.getLicenseStatusID() == model.getLicenseStatusID())
				{
					model.setLicenseStatusCode(licenseStatus.getCode());
					model.setLicenseStatusName(licenseStatus.getName());
					break;
				}
			}
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			model.setRecordTime(timestamp);
			
			if(addOperation)
			{
				if(facade.addForestDevelopmentContractor(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya ditambahkan.");
					log(facade, "Tambah kontraktor pembangunan hutan, ID " + model.getForestDevelopmentContractorID());			
					Renew renew = new Renew(); 
					renew.setRenewID(timestamp.getTime());
					renew.setForestDevelopmentContractorID(model.getForestDevelopmentContractorID());
					renew.setType("F");
					renew.setStartDate(today);
					renew.setReceiptID(model.getReceiptID());
					renew.setEndDate(nextYear);
					renew.setStatus("A");
					if(facade.addRenew(renew) != 0)
					{
						model.getRenews().add(renew);
						model.setEndDate(renew.getEndDate());
					}

					renew = null;
					
					if(selectedForestDevelopmentSubWorkTypes.size() == 0)
					{
						addMessage(FacesMessage.SEVERITY_WARN, null, model + " tidak diproses kerana sub kerja pembangunan hutan dipilih.");
					}
					else
					{	
						for(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType : selectedForestDevelopmentSubWorkTypes)
						{
							ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord = new ForestDevelopmentContractorSubWorkTypeRecord();
							forestDevelopmentContractorSubWorkTypeRecord.setForestDevelopmentContractorID(model.getForestDevelopmentContractorID());
							forestDevelopmentContractorSubWorkTypeRecord.setForestDevelopmentSubWorkTypeID(forestDevelopmentSubWorkType.getForestDevelopmentSubWorkTypeID());
							forestDevelopmentContractorSubWorkTypeRecord.setStatus("A");
							facade.addForestDevelopmentContractorSubWorkTypeRecord(forestDevelopmentContractorSubWorkTypeRecord);
							log(facade, "Tambah rekod sub kerja kontraktor pembangunan hutan, ID " + model.getForestDevelopmentContractorID());
							model.getForestDevelopmentContractorSubWorkTypeRecords().add(forestDevelopmentContractorSubWorkTypeRecord);
						}
						sort(model.getForestDevelopmentContractorSubWorkTypeRecords());			
					}
					models.add(model);
				}
			}			
			else
			{
				if(facade.updateForestDevelopmentContractor(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya dikemaskini.");
					log(facade, "Kemaskini pengusaha, ID " + model.getForestDevelopmentContractorID());
					int index = models.indexOf(model);
					models.set(index, model);
				}				
			}
			
			model = null;
			selectedReceiptID = 0;
			execute("PF('popup').hide()");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
		execute("PF('popupForestDevelopmentContractorSubWorkType').hide()");
	}

	public void forestDevelopmentContractorDelete()
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