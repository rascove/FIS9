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
import my.edu.utem.ftmk.fis9.maintenance.model.Hall;
import my.edu.utem.ftmk.fis9.maintenance.model.HallOfficer;
import my.edu.utem.ftmk.fis9.maintenance.model.PermitType;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.TrustFund;
import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;
import my.edu.utem.ftmk.fis9.revenue.model.License;
import my.edu.utem.ftmk.fis9.revenue.model.Permit;
import my.edu.utem.ftmk.fis9.revenue.model.Receipt;
import my.edu.utem.ftmk.fis9.revenue.model.ReceiptRecord;
import my.edu.utem.ftmk.fis9.revenue.model.Renew;

/**
 * @author Nor Azman Bin Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "permitMBean")
public class PermitManagedBean extends AbstractManagedBean<Permit>
{
	private static final long serialVersionUID = VERSION;
	private District district;
	private ArrayList<License> licenses;
	private ArrayList<TrustFund> trustFunds;
	private ArrayList<Renew> renews;
	private ArrayList<District> districts;
	private ArrayList<Receipt> receipts;
	private ArrayList<PermitType> permitTypes;
	private ArrayList<Forest> forests;
	private ArrayList<Hall> halls;
	private ArrayList<HallOfficer> hallOfficers;	
	private ArrayList<SelectItem> forestList;
	private ArrayList<SelectItem> hallList;
	private ArrayList<SelectItem> hallOfficerList;
	private Date today;
	private Date nextYear;
	private Date lastYear;	
	private String message;
	private int accessLevel;
	private int duration;

	public PermitManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade(); RevenueFacade rFacade = new RevenueFacade();)
		{
			AbstractFacade.group(mFacade, rFacade);

			Staff user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(), designationID = user.getDesignationID();

			Calendar calToday = Calendar.getInstance();
			today = resetCalendar(calToday, 0, 0);

			Calendar calLastYear = Calendar.getInstance();
			lastYear = resetCalendar(calLastYear, -1, 0);

			Calendar calNextYear = Calendar.getInstance();
			nextYear = resetCalendar(calNextYear, 1, 0);

			Calendar calNextMonth = Calendar.getInstance();
			Date nextThreeMonth = resetCalendar(calNextMonth, 0, 3);

			licenses = rFacade.getLicenses("A");
			
			trustFunds = mFacade.getTrustFunds("A");
			renews = rFacade.getRenews("A", lastYear);
			
			boolean exist = false;
			
			for(License license : licenses)
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
					if(license.getEndDate().compareTo(today) >= 0)
					{
						license.setStatus("A");
					}
					else
					{
						license.setStatus("E");
					}
				}
			}

				permitTypes = mFacade.getPermitTypes("A");
				receipts = rFacade.getReceipts("7","I");

				hallList = new ArrayList<>();
				forestList = new ArrayList<>();
				hallOfficerList = new ArrayList<>();
				districts = new ArrayList<>();

				if (stateID == 0)
				{
					accessLevel = 1;
					models = rFacade.getPermits("A");
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
							
						models = rFacade.getPermits(state, "A");
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
							models = rFacade.getPermits(district, "A");
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
						else
						{						
							
						}
					}
				}

				ArrayList<Permit> tempPermits = new ArrayList<Permit>();
				for(Permit permit : models)
				{
					exist = false;
					permit.setEndDate(lastYear);
					permit.setRenews(new ArrayList<Renew>());
					for(Renew renew : renews)
					{
						if(permit.getPermitID() == renew.getPermitID())
						{
							exist = true;
							if(permit.getEndDate().compareTo(renew.getEndDate()) <= 0)
							{
								permit.setEndDate(renew.getEndDate());
							}
							permit.getRenews().add(renew);
						}
					}
					sort(permit.getRenews());
					if(exist == true)
					{
						if(permit.getEndDate().compareTo(today) < 0)
						{
							permit.setStatus("E");
						}
						else
						{
							if(permit.getEndDate().compareTo(nextThreeMonth) > 0)
							{
								permit.setStatus("A");
							}
							else 
							{
								permit.setStatus("W");							
							}	
						}
						tempPermits.add(permit);
					}
				}
				models = tempPermits;
				tempPermits = null;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}

		public Permit getPermit()
		{
			return model;
		}

		public void setPermit(Permit permit)
		{
			this.model = permit;
		}

		public ArrayList<Permit> getPermits()
		{
			return models;
		}

		public void setPermits(ArrayList<Permit> permits)
		{
			this.models = permits;
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

		public ArrayList<PermitType> getPermitTypes() {
			return permitTypes;
		}

		public void setPermitTypes(ArrayList<PermitType> permitTypes) {
			this.permitTypes = permitTypes;
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

		public ArrayList<License> getLicenses() {
			return licenses;
		}

		public void setLicenses(ArrayList<License> licenses) {
			this.licenses = licenses;
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
				model = new Permit();
				Date date = new Date();
				Staff user = getCurrentUser();

				model.setPermitID(System.currentTimeMillis());
				model.setRegistrationDate(date);
				model.setRecorderID(user.getStaffID());
				model.setRecorderName(user.getName());
				model.setRenews(new ArrayList<Renew>());
				model.setStatus("A");
			}
			else
				model = (Permit) copy(models, model);
		}

		public void permitEntry()
		{
			try (RevenueFacade facade = new RevenueFacade())
			{
				if(addOperation)
				{
					model.setPermitFund(BigDecimal.ZERO);
					for(Receipt receipt : receipts)
					{
						if(receipt.getReceiptID() == model.getReceiptID())
						{
							model.setReceiptNo(receipt.getReceiptNo());
							for(ReceiptRecord receiptRecord : receipt.getReceiptRecords())
							{
								for(TrustFund trustFund : trustFunds)
								{
									if(trustFund.getSymbol().equalsIgnoreCase("WAJ") || trustFund.getSymbol().equalsIgnoreCase("WAKO") || trustFund.getSymbol().equalsIgnoreCase("WAM"))
									{
										if(receiptRecord.getDepartmentVotID() == trustFund.getDepartmentVotID())
										{
											model.setPermitFund(receiptRecord
													.getRate()
													.multiply(
															receiptRecord
															.getQuantity())
													.setScale(2,
															BigDecimal.ROUND_HALF_UP));
											break;
										}
									}
								}
							}
							break;
						}
					}
				}
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(model.getStartDate());
				calendar.add(Calendar.MONTH, duration);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				model.setEndDate(calendar.getTime());
				
				model.setPermitNo(model.getPermitNo().replaceAll("\\s", "").toUpperCase());
				
				for (License license : licenses)
				{
					if (license.getLicenseID() == model.getLicenseID())
					{
						model.setLicenseNo(license.getLicenseNo());
						model.setLicenseeID(license.getLicenseeID());
						model.setLicenseeNo(license.getLicenseeNo());
						model.setLicenseeName(license.getLicenseeName());
						model.setLicenseeCompanyName(license.getLicenseeCompanyName());						
						model.setContractorID(license.getContractorID());
						model.setContractorNo(license.getContractorNo());
						model.setContractorName(license.getContractorName());
						model.setContractorCompanyName(license.getContractorCompanyName());
						break;
					}
				}

				for (PermitType permitType : permitTypes)
				{
					if (permitType.getPermitTypeID() == model.getPermitTypeID())
					{
						model.setPermitTypeCode(permitType.getCode());
						model.setPermitTypeName(permitType.getName());
						break;
					}
				}
				
				for (Forest forest : forests)
				{
					if (forest.getForestID() == model.getForestID())
					{
						model.setForestCode(forest.getCode());	
						model.setForestName(forest.getName());
						model.setDistrictID(forest.getDistrictID());
						for(District district : districts)
						{
							if(district.getDistrictID() == model.getDistrictID())
							{
								model.setDistrictCode(district.getCode());
								model.setDistrictName(district.getName());
								break;	
							}
						}
						break;
					}
				}		

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				model.setRecordTime(timestamp);			

				if(addOperation)
				{
					if(facade.addPermit(model) != 0)
					{
						addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya ditambahkan.");
						log(facade, "Tambah permit, ID " + model.getPermitID());	
						
						Renew renew = new Renew(); 
						renew.setRenewID(timestamp.getTime());
						renew.setType("P");
						renew.setPermitID(model.getPermitID());
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
								receipt.setPermitID(model.getPermitID());
								receipt.setStatus("A");
								if(facade.updateStatusReceipt(receipt) != 0)
								{
									log(facade, "Kemaskini status resit, ID " + receipt.getReceiptID());
									receipts.remove(receipt);
								}
								break;
							}
						}
						
						models.add(model);
					}
				}			
				else
				{
					String status = model.getStatus();
					model.setStatus("A");
					if(facade.updatePermit(model) != 0)
					{
						model.setStatus(status);
						addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya dikemaskini.");
						log(facade, "Kemaskini permit, ID " + model.getPermitID());
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

		public void cancelPermit()
		{
			try (RevenueFacade facade = new RevenueFacade())
			{
				if (facade.deletePermit(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null, model + " berjaya dipadamkan.");
					models.remove(model);
					log(facade, "Padam permit, ID " + model.getPermitID());
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