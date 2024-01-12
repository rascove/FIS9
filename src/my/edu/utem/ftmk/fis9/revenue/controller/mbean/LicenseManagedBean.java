package my.edu.utem.ftmk.fis9.revenue.controller.mbean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;



import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.global.util.ArrayListConverter;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Forest;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestCategory;
import my.edu.utem.ftmk.fis9.maintenance.model.Hall;
import my.edu.utem.ftmk.fis9.maintenance.model.HallOfficer;
import my.edu.utem.ftmk.fis9.maintenance.model.LicenseType;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.TrustFund;
import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;
import my.edu.utem.ftmk.fis9.revenue.model.License;
import my.edu.utem.ftmk.fis9.revenue.model.LoggingContractor;
import my.edu.utem.ftmk.fis9.revenue.model.Receipt;
import my.edu.utem.ftmk.fis9.revenue.model.ReceiptRecord;
import my.edu.utem.ftmk.fis9.revenue.model.Renew;

/**
 * @author Nor Azman Bin Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "licenseMBean")
public class LicenseManagedBean extends AbstractManagedBean<License>
{
	private static final long serialVersionUID = VERSION;
	private Receipt receipt;
	private District district;
	private ArrayList<LoggingContractor> loggingContractors;
	private ArrayList<LoggingContractor> licensees;
	private ArrayList<LoggingContractor> contractors;
	private ArrayList<TrustFund> trustFunds;
	private ArrayList<Renew> renews;
	private ArrayList<District> districts;
	private ArrayList<Receipt> receipts;
	private ArrayList<LicenseType> licenseTypes;
	private ArrayList<ForestCategory> forestCategories;
	private ArrayList<Forest> forests;
	private ArrayList<Hall> halls;
	private ArrayList<HallOfficer> hallOfficers;	
	private ArrayList<SelectItem> forestList;
	private ArrayList<SelectItem> hallList;
	private ArrayList<SelectItem> hallOfficerList;
	private Date today;
	private Date nextYear;
	private Date lastYear;	
	private int selectedReceiptID;
	private int accessLevel;
	private int duration;
	private BigDecimal maximumLimit;

	public LicenseManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); RevenueFacade rFacade = new RevenueFacade();)
		{
			AbstractFacade.group(mFacade, rFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(), designationID = user.getDesignationID();
			
			maximumLimit = new BigDecimal(1000000000);

			Calendar calToday = Calendar.getInstance();
			today = resetCalendar(calToday, 0, 0);

			Calendar calLastYear = Calendar.getInstance();
			lastYear = resetCalendar(calLastYear, -1, 0);

			Calendar calNextYear = Calendar.getInstance();
			nextYear = resetCalendar(calNextYear, 1, 0);

			Calendar calNextMonth = Calendar.getInstance();
			Date nextThreeMonth = resetCalendar(calNextMonth, 0, 3);

			loggingContractors = rFacade.getLoggingContractors("A");
			
			trustFunds = mFacade.getTrustFunds("A");
			
			renews = rFacade.getRenews("A", lastYear);
			
			boolean exist = false;
			
			licensees = new ArrayList<LoggingContractor>();
			contractors = new ArrayList<LoggingContractor>();
			
			for(LoggingContractor loggingContractor : loggingContractors)
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
					if(loggingContractor.getEndDate().compareTo(today) >= 0)
					{
						loggingContractor.setStatus("A");
						if(loggingContractor.getType().equalsIgnoreCase("L"))
						{
							licensees.add(loggingContractor);
						}
						else
						{
							if(loggingContractor.getType().equalsIgnoreCase("C"))
							{
								contractors.add(loggingContractor);
							}
						}
					}
				}
			}

				licenseTypes = mFacade.getLicenseTypes("A");
				forestCategories = mFacade.getForestCategorys("A");
				receipts = rFacade.getReceipts("6","I");

				hallList = new ArrayList<>();
				forestList = new ArrayList<>();
				hallOfficerList = new ArrayList<>();
				districts = new ArrayList<>();

				if (stateID == 0)
				{
					accessLevel = 1;
					models = rFacade.getLicenses("A");
					ArrayList<State> states = mFacade.getStates();
					forests = mFacade.getForests();
					halls = mFacade.getHalls();
					hallOfficers = mFacade.getHallOfficers(today, "A");
					

					for (State state : states)
					{
						ArrayList<District> districts = mFacade.getDistricts(state);

						for (District district : districts)
						{
							SelectItemGroup forestGroup = new SelectItemGroup(district.getName() + ", " + state.getName());
							ArrayList<SelectItem> forestItems = new ArrayList<>();
							SelectItemGroup hallGroup = new SelectItemGroup(district.getName()+ ", " + state.getName());
							ArrayList<SelectItem> hallItems = new ArrayList<>();	
							SelectItemGroup hallOfficerGroup = new SelectItemGroup(district.getName()+ ", " + state.getName());
							ArrayList<SelectItem> hallOfficerItems = new ArrayList<>();						

							for (Forest forest : forests)
								if (forest.getDistrictID() == district.getDistrictID())
									forestItems.add(new SelectItem(forest.getForestID(), forest.toString()));

							for (Hall hall : halls)
								if(hall.getDistrictID() == district.getDistrictID())
									hallItems.add(new SelectItem(hall.getHallID(), hall.toString()));

							for (HallOfficer hallOfficer : hallOfficers)
								if(hallOfficer.getDistrictID() == district.getDistrictID())
									hallOfficerItems.add(new SelectItem(hallOfficer.getHallOfficerID(), hallOfficer.toString()));						

							if (!forestItems.isEmpty())
							{
								forestGroup.setSelectItems(ArrayListConverter.asSelectItem(forestItems));
								forestList.add(forestGroup);
							}

							if (!hallItems.isEmpty())
							{
								hallGroup.setSelectItems(ArrayListConverter.asSelectItem(hallItems));
								hallList.add(hallGroup);
							}

							if (!hallOfficerItems.isEmpty())
							{
								hallOfficerGroup.setSelectItems(ArrayListConverter.asSelectItem(hallOfficerItems));
								hallOfficerList.add(hallOfficerGroup);
							}						
						}
						this.districts.addAll(districts);
					}
				}
				else
				{				
					State state = mFacade.getState(stateID);
					district = mFacade.getDistrict(user);
					if (state.getDirectorID().equals(staffID) || designationID == 25 || designationID == 26 || (designationID == 27 && district == null))
					{
						if (state.getDirectorID().equals(staffID))
							accessLevel = 1;
						
						if (designationID == 25 || designationID == 26)
							accessLevel = 2;
						
						if (designationID == 27)
							accessLevel = 3;
						
						models = rFacade.getLicenses(state, "A");
						forests = mFacade.getForests(state);
						halls = mFacade.getHalls(state, 1);
						hallOfficers = mFacade.getHallOfficers(today, state);

						ArrayList<District> districts = mFacade.getDistricts(state);
						
						for (District district : districts)
						{
							SelectItemGroup forestGroup = new SelectItemGroup(district.getName() + ", " + state.getName());
							ArrayList<SelectItem> forestItems = new ArrayList<>();
							SelectItemGroup hallGroup = new SelectItemGroup(district.getName()+ ", " + state.getName());
							ArrayList<SelectItem> hallItems = new ArrayList<>();	
							SelectItemGroup hallOfficerGroup = new SelectItemGroup(district.getName()+ ", " + state.getName());
							ArrayList<SelectItem> hallOfficerItems = new ArrayList<>();						

							for (Forest forest : forests)
								if (forest.getDistrictID() == district.getDistrictID())
									forestItems.add(new SelectItem(forest.getForestID(), forest.toString()));

							for (Hall hall : halls)
								if(hall.getDistrictID() == district.getDistrictID())
									hallItems.add(new SelectItem(hall.getHallID(), hall.toString()));

							for (HallOfficer hallOfficer : hallOfficers)
								if(hallOfficer.getDistrictID() == district.getDistrictID())
									hallOfficerItems.add(new SelectItem(hallOfficer.getHallOfficerID(), hallOfficer.toString()));						

							if (!forestItems.isEmpty())
							{
								forestGroup.setSelectItems(ArrayListConverter.asSelectItem(forestItems));
								forestList.add(forestGroup);
							}

							if (!hallItems.isEmpty())
							{
								hallGroup.setSelectItems(ArrayListConverter.asSelectItem(hallItems));
								hallList.add(hallGroup);
							}

							if (!hallOfficerItems.isEmpty())
							{
								hallOfficerGroup.setSelectItems(ArrayListConverter.asSelectItem(hallOfficerItems));
								hallOfficerList.add(hallOfficerGroup);
							}						
						}
						this.districts.addAll(districts);
					}
					else
					{
						if (district != null)
						{
							accessLevel = 3;
							models = rFacade.getLicenses(district, "A");
							forests = mFacade.getForests(district);
							halls = mFacade.getHalls(district);
							hallOfficers = mFacade.getHallOfficers(today, district);

							for (Forest forest : forests)
							{
								if (forest.getDistrictID() == district.getDistrictID())
								{
									SelectItem item = new SelectItem(forest.getForestID(), forest.toString());

									if (!forestList.contains(item))
										forestList.add(item);
								}
							}		

							for (Hall hall : halls)
							{
								if (hall.getDistrictID() == district.getDistrictID())
								{
									SelectItem item = new SelectItem(hall.getHallID(), hall.toString());

									if (!hallList.contains(item))
										hallList.add(item);
								}
							}

							for (HallOfficer hallOfficer : hallOfficers)
							{
								if (hallOfficer.getDistrictID() == district.getDistrictID())
								{
									SelectItem item = new SelectItem(hallOfficer.getHallOfficerID(), hallOfficer.toString());

									if (!hallOfficerList.contains(item))
										hallOfficerList.add(item);
								}
							}						
						}
					}
				}
				ArrayList<License> tempLicenses = new ArrayList<License>();
				for(License license : models)
				{
					exist = false;
					license.setEndDate(lastYear);
					license.setRenews(new ArrayList<Renew>());
					for(Renew renew : renews)
					{
						if(license.getLicenseID() == renew.getLicenseID())
						{
							exist = true;
							if(license.getEndDate().compareTo(renew.getEndDate()) <= 0)
							{
								license.setEndDate(renew.getEndDate());
							}
							license.getRenews().add(renew);
						}
					}
					sort(license.getRenews());
					if(exist == true)
					{
						if(license.getEndDate().compareTo(today) < 0)
						{
							license.setStatus("E");
						}
						else
						{
							if(license.getEndDate().compareTo(nextThreeMonth) > 0)
							{
								license.setStatus("A");
							}
							else 
							{
								license.setStatus("W");							
							}	
						}
						tempLicenses.add(license);
					}
				}
				models = tempLicenses;
				tempLicenses = null;

			}
			catch (SQLException e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}

		public License getLicense()
		{
			return model;
		}

		public void setLicense(License license)
		{
			this.model = license;
		}

		public ArrayList<License> getLicenses()
		{
			return models;
		}

		public void setLicenses(ArrayList<License> licenses)
		{
			this.models = licenses;
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

		public ArrayList<LicenseType> getLicenseTypes() {
			return licenseTypes;
		}

		public void setLicenseTypes(ArrayList<LicenseType> licenseTypes) {
			this.licenseTypes = licenseTypes;
		}

		public ArrayList<ForestCategory> getForestCategories() {
			return forestCategories;
		}

		public void setForestCategories(ArrayList<ForestCategory> forestCategories) {
			this.forestCategories = forestCategories;
		}

		public ArrayList<Forest> getForests() {
			return forests;
		}

		public void setForests(ArrayList<Forest> forests) {
			this.forests = forests;
		}

		public ArrayList<Hall> getHalls() {
			return halls;
		}

		public void setHalls(ArrayList<Hall> halls) {
			this.halls = halls;
		}

		public ArrayList<HallOfficer> getHallOfficers() {
			return hallOfficers;
		}

		public void setHallOfficers(ArrayList<HallOfficer> hallOfficers) {
			this.hallOfficers = hallOfficers;
		}

		public ArrayList<SelectItem> getForestList() {
			return forestList;
		}

		public void setForestList(ArrayList<SelectItem> forestList) {
			this.forestList = forestList;
		}

		public ArrayList<SelectItem> getHallList() {
			return hallList;
		}

		public void setHallList(ArrayList<SelectItem> hallList) {
			this.hallList = hallList;
		}

		public int getSelectedReceiptID() {
			return selectedReceiptID;
		}

		public void setSelectedReceiptID(int selectedReceiptID) {
			this.selectedReceiptID = selectedReceiptID;
		}

		public ArrayList<Renew> getRenews() {
			return renews;
		}

		public void setRenews(ArrayList<Renew> renews) {
			this.renews = renews;
		}

		public ArrayList<SelectItem> getHallOfficerList() {
			return hallOfficerList;
		}

		public void setHallOfficerList(ArrayList<SelectItem> hallOfficerList) {
			this.hallOfficerList = hallOfficerList;
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

		public ArrayList<LoggingContractor> getLoggingContractors() {
			return loggingContractors;
		}

		public void setLoggingContractors(ArrayList<LoggingContractor> loggingContractors) {
			this.loggingContractors = loggingContractors;
		}

		public ArrayList<LoggingContractor> getLicensees() {
			return licensees;
		}

		public void setLicensees(ArrayList<LoggingContractor> licensees) {
			this.licensees = licensees;
		}

		public ArrayList<LoggingContractor> getContractors() {
			return contractors;
		}

		public void setContractors(ArrayList<LoggingContractor> contractors) {
			this.contractors = contractors;
		}

		public int getAccessLevel()
		{
			return accessLevel;
		}

		public void setAccessLevel(int accessLevel)
		{
			this.accessLevel = accessLevel;
		}

		public int getDuration()
		{
			return duration;
		}

		public void setDuration(int duration)
		{
			this.duration = duration;
		}

		public BigDecimal getMaximumLimit()
		{
			return maximumLimit;
		}

		public void setMaximumLimit(BigDecimal maximumLimit)
		{
			this.maximumLimit = maximumLimit;
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
				model = new License();
				Date date = new Date();
				Staff user = getCurrentUser();

				model.setLicenseID(date.getTime());
				model.setRegistrationDate(date);
				model.setWoodWorkFund(BigDecimal.ZERO);
				model.setLicenseFund(BigDecimal.ZERO);
				model.setResinLimit(BigDecimal.ZERO);
				model.setNonResinLimit(BigDecimal.ZERO);
				model.setChengalLimit(BigDecimal.ZERO);
				model.setLogLimit(BigDecimal.ZERO);
				model.setJarasLimit(BigDecimal.ZERO);
				model.setWasteWoodLimit(BigDecimal.ZERO);
				model.setRecorderID(user.getStaffID());
				model.setRecorderName(user.getName());
				model.setRenews(new ArrayList<Renew>());
				model.setStatus("A");
			}
			else
				model = (License) copy(models, model);
		}

		public void licenseEntry()
		{
			try (RevenueFacade facade = new RevenueFacade())
			{
				for(Receipt receipt : receipts)
				{
					if(receipt.getReceiptID() == model.getReceiptID())
					{
						model.setReceiptNo(receipt.getReceiptNo());
						receipt.setLicenseID(model.getLicenseID());
						break;
					}
				}
				for (LoggingContractor loggingContractor : licensees)
				{
					if (loggingContractor.getLoggingContractorID() == model.getLicenseeID())
					{
						model.setLicenseeName(loggingContractor.getName());
						model.setLicenseeNo(loggingContractor.getRegistrationSerialNo());
						model.setLicenseeCompanyName(loggingContractor.getCompanyName());
						break;
					}
				}

				for (LoggingContractor loggingContractor : contractors)
				{
					if (loggingContractor.getLoggingContractorID() == model.getContractorID())
					{
						model.setContractorName(loggingContractor.getName());
						model.setContractorNo(loggingContractor.getRegistrationSerialNo());
						model.setContractorCompanyName(loggingContractor.getCompanyName());
						break;
					}
				}

				for (LicenseType licenseType : licenseTypes)
				{
					if (licenseType.getLicenseTypeID() == model.getLicenseTypeID())
					{
						model.setLicenseTypeCode(licenseType.getCode());
						model.setLicenseTypeName(licenseType.getName());
						break;
					}
				}
				
				for (ForestCategory forestCategory : forestCategories)
				{
					if (forestCategory.getForestCategoryID() == model.getForestCategoryID())
					{
						model.setForestCategoryCode(forestCategory.getCode());	
						model.setForestCategoryName(forestCategory.getName());
						break;
					}
				}	
				
				if(model.getLicenseTypeID() != 3 && model.getForestCategoryID() != 3)
				{
					for (Forest forest : forests)
					{
						if (forest.getForestID() == model.getForestID())
						{
							model.setForestCode(forest.getCode());	
							model.setForestName(forest.getName());
							break;
						}
					}
				}
				
				for (Hall hall : halls)
				{
					if (hall.getHallID() == model.getHallID())
					{
						model.setHallName(hall.getName());
						model.setDistrictID(hall.getDistrictID());
						for(District district : districts)
						{
							if(district.getDistrictID() == model.getDistrictID())
							{
								model.setDistrictCode(district.getCode());	
								model.setDistrictName(district.getName());	
							}
						}
					}
				}		

				for (HallOfficer hallOfficer : hallOfficers)
				{
					if (hallOfficer.getHallOfficerID() == model.getHallOfficerID())
					{
						model.setHallOfficerHammerNo(hallOfficer.getHammerNo());
						model.setHallOfficerName(hallOfficer.getHallOfficerName());
						break;
					}
				}	
				
				model.setLicenseNo(model.getLicenseNo().toUpperCase());
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(model.getStartDate());
				calendar.add(Calendar.MONTH, duration);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				model.setEndDate(calendar.getTime());

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				model.setRecordTime(timestamp);

				if(addOperation)
				{
					model.setWoodWorkFund(BigDecimal.ZERO);
					model.setLicenseFund(BigDecimal.ZERO);
					if(model.getResinLimit().compareTo(BigDecimal.ZERO) == 0)
					{
						model.setResinLimit(maximumLimit);
					}
					if(model.getNonResinLimit().compareTo(BigDecimal.ZERO) == 0)
					{
						model.setNonResinLimit(maximumLimit);
					}
					if(model.getChengalLimit().compareTo(BigDecimal.ZERO) == 0)
					{
						model.setChengalLimit(maximumLimit);
					}
					if(model.getLogLimit().compareTo(BigDecimal.ZERO) == 0)
					{
						model.setLogLimit(maximumLimit);
					}
					if(model.getJarasLimit().compareTo(BigDecimal.ZERO) == 0)
					{
						model.setJarasLimit(maximumLimit);
					}
					
					for(Receipt receipt : receipts)
					{
						if(receipt.getReceiptID() == model.getReceiptID())
						{
							for(ReceiptRecord receiptRecord : receipt.getReceiptRecords())
							{
								for(TrustFund trustFund : trustFunds)
								{
									if(trustFund.getSymbol().equalsIgnoreCase("WAKK") && (trustFund.getDepartmentVotID() == receiptRecord.getDepartmentVotID()))
									{
										model.setWoodWorkFund(model.getWoodWorkFund().add(receiptRecord.getTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));
										break;
									}
									if(trustFund.getSymbol().equalsIgnoreCase("WAL") && (trustFund.getDepartmentVotID() == receiptRecord.getDepartmentVotID()))
									{
										model.setLicenseFund(model.getLicenseFund().add(receiptRecord.getTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));
										break;
									}								
								}
							}
						}
					}	
					
					if(facade.addLicense(model, true) != 0)
					{
						addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya ditambahkan.");
						log(facade, "Tambah lesen, ID " + model.getLicenseID());	
						
						/*Create renew*/
						Renew renew = new Renew();
						renew.setRenewID(timestamp.getTime());
						renew.setType("L");
						renew.setLicenseID(model.getLicenseID());
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
						
						for(Receipt receipt : receipts)
						{
							if(receipt.getReceiptID() == model.getReceiptID())
							{
								receipt.setStatus("A");
								if(facade.updateStatusReceipt(receipt) != 0)
								{
									log(facade, "Kemaskini status resit daftar kontraktor pembalakan, ID " + receipt.getReceiptID());
									receipts.remove(receipt);
								}
								break;
							}
						}
						models.add(model);
					}
					else
					{
						addMessage(FacesMessage.SEVERITY_WARN, null, model + " tidak dapat ditambahkan kerana no. lesen telah direkodkan sebelumnya.");
					}
				}			
				else
				{
					if(facade.updateLicense(model) != 0)
					{
						addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya dikemaskini.");
						log(facade, "Kemaskini lesen, ID " + model.getLicenseID());
						int index = models.indexOf(model);
						models.set(index, model);
					}				
				}
				model = null;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				addMessage(e);
			}

			execute("PF('popup').hide()");
		}

		public void licenseDelete()
		{
			try (RevenueFacade facade = new RevenueFacade())
			{
				if (facade.deleteLicense(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya dipadamkan.");
					models.remove(model);
					log(facade, "Padam lesen, ID " + model.getLicenseID());
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