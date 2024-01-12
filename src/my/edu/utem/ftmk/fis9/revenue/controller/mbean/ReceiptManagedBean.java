package my.edu.utem.ftmk.fis9.revenue.controller.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.global.util.ArrayListConverter;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Bank;
import my.edu.utem.ftmk.fis9.maintenance.model.BursaryVot;
import my.edu.utem.ftmk.fis9.maintenance.model.ChequeType;
import my.edu.utem.ftmk.fis9.maintenance.model.DepartmentVot;
import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.PaymentType;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.TrustFund;
import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;
import my.edu.utem.ftmk.fis9.revenue.model.ForestDevelopmentContractor;
import my.edu.utem.ftmk.fis9.revenue.model.License;
import my.edu.utem.ftmk.fis9.revenue.model.LoggingContractor;
import my.edu.utem.ftmk.fis9.revenue.model.Permit;
import my.edu.utem.ftmk.fis9.revenue.model.Receipt;
import my.edu.utem.ftmk.fis9.revenue.model.ReceiptRecord;
import my.edu.utem.ftmk.fis9.revenue.model.Renew;
import my.edu.utem.ftmk.fis9.revenue.model.Revenue;
import my.edu.utem.ftmk.fis9.revenue.model.Transaction;
import my.edu.utem.ftmk.fis9.revenue.util.BukuTunaiPungutanTerimaanDariTarikhMulaHinggaTarikhAkhirGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.LaporanBagiPembatalanHasilDariTarikhMulaHinggaTarikhAkhirGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.LaporanKutipanHarianMengikutBentukBayaranPadaTarikhGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.LaporanKutipanHarianMengikutJenisDariTarikhMulaHinggaTarikhAkhirGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.LaporanKutipanHarianMengikutOperatorSepertiPadaTarikhGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.LaporanKutipanHarianPadaTarikhGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.LaporanKutipanHarianResitDariTarikhMulaHinggaTarikhAkhirGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.LaporanUrusniagaAkaunAmanahPadaBulanDanTahunGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.LaporanUrusniagaAkaunHasilPadaBulanDanTahunGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.PenyataAkaunAmanahMengikutJabatanPadaBulanDanTahunGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.PenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhirGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.PenyataAkaunHasilMengikutJabatanPadaBulanDanTahunGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.ResitGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.SenaraiResitYangDibatalkanDariTarikhMulaHinggaTarikhAkhirGenerator;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "receiptMBean")
public class ReceiptManagedBean extends AbstractManagedBean<Receipt>
{
	private static final long serialVersionUID = VERSION;
	private ReceiptRecord receiptRecord;
	private ArrayList<TrustFund> trustFunds;
	private ArrayList<ForestDevelopmentContractor> allForestDevelopmentContractors;
	private ArrayList<ForestDevelopmentContractor> forestDevelopmentContractors;
	private ArrayList<LoggingContractor> allLoggingContractors;
	private ArrayList<LoggingContractor> loggingContractors;
	private ArrayList<License> allLicenses;
	private ArrayList<License> licenses;
	private ArrayList<Permit> allPermits;
	private ArrayList<Permit> permits;
	private ArrayList<Renew> renews;
	private ArrayList<BursaryVot> bursaryVots;
	private ArrayList<DepartmentVot> departmentVots;
	private ArrayList<SelectItem> departmentVotList;
	private ArrayList<PaymentType> paymentTypes;
	private ArrayList<Bank> banks;
	private ArrayList<ChequeType> chequeTypes;
	private ArrayList<ReceiptRecord> receiptRecords;
	private ForestDevelopmentContractor chosenForestDevelopmentContractor;
	private LoggingContractor chosenLoggingContractor;
	private License chosenLicense;
	private Permit chosenPermit;
	private boolean addReceiptRecordOperation;
	private int receiptCategory;
	private String receiptType;
	private District district;
	private String message;
	private Date today;
	private DateFormat dateFormat;
	private Date specificDate;
	private Date startDate;
	private Date endDate;
	private BigDecimal total;
	private int renewTypeID;
	private long forestDevelopmentContractorID;
	private long loggingContractorID;
	private long licenseID;
	private long permitID;
	private int reportType;
	ArrayList<String> months;
	ArrayList<String> years;
	private String month;
	private String startMonth;
	private String endMonth;
	private String year;
	private SimpleDateFormat sdfMonth;
	private SimpleDateFormat sdfYear;
	private SimpleDateFormat sdf;
	private SimpleDateFormat filenameDateFormat;
	private ArrayList<Staff> staffs;
	private Staff currentStaff;
	private Staff user;
	private String operatorID;
	private int currentYear;
	private boolean backButtonClicked;
	private int accessLevel;
	private int duration;
	private String[] ptj;

	public ReceiptManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				RevenueFacade rFacade = new RevenueFacade();)
		{
			AbstractFacade.group(mFacade, rFacade);

			Designation designation = new Designation();

			ptj = new String[2];

			user = getCurrentUser();
			int stateID = user.getStateID();
			String staffID = user.getStaffID();

			designation.setDesignationID(27);
			staffs = mFacade.getStaffs(designation);

			Calendar calToday = Calendar.getInstance();
			today = resetCalendar(calToday, 0, 0);

			bursaryVots = mFacade.getBursaryVots("A");
			trustFunds = mFacade.getTrustFunds("A");

			boolean exist = false;

			dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			filenameDateFormat = new SimpleDateFormat("dd_MMM_yyyy");

			GregorianCalendar gc = new GregorianCalendar();
			GregorianCalendar gcYear = new GregorianCalendar();
			currentYear = gcYear.get(Calendar.YEAR);
			sdf = new SimpleDateFormat("dd-MMM-yyyy");
			sdfMonth = new SimpleDateFormat("MMMM", new Locale("ms"));
			sdfYear = new SimpleDateFormat("yyyy", new Locale("ms"));
			months = new ArrayList<String>();
			years = new ArrayList<>();

			for (int i = 0; i < 12; i++)
			{
				months.add(sdfMonth.format(gc.getTime()));
				years.add(sdfYear.format(gcYear.getTime()));
				gc.add(GregorianCalendar.MONTH, 1);
				gcYear.add(GregorianCalendar.YEAR, -1);
			}

			allForestDevelopmentContractors = new ArrayList<ForestDevelopmentContractor>();
			allLoggingContractors = new ArrayList<LoggingContractor>();
			allLicenses = new ArrayList<License>();
			allPermits = new ArrayList<Permit>();

			renews = rFacade.getRenews("A", today);

			if (stateID == 0)
			{
				ptj[0] = "80000100";
				ptj[1] = "IBU PEJABAT JABATAN PERHUTANAN NEGERI";
				accessLevel = 1;
				models = rFacade.getReceipts("C");
				allForestDevelopmentContractors = rFacade
						.getForestDevelopmentContractors("A");
				allLoggingContractors = rFacade.getLoggingContractors("A");
				allLicenses = rFacade.getLicenses("A");
				allPermits = rFacade.getPermits("A");
			}
			else
			{
				State state = mFacade.getState(stateID);
				district = mFacade.getDistrict(user);

				if (state.getDirectorID().equals(staffID) || district == null)
				{
					ptj[0] = "80000100";
					ptj[1] = "IBU PEJABAT JABATAN PERHUTANAN NEGERI";
					if (state.getDirectorID().equals(staffID)
							|| user.getDesignationID() == 25
							|| user.getDesignationID() == 26)
					{
						if (state.getDirectorID().equals(staffID))
							accessLevel = 1;

						if (user.getDesignationID() == 25
								|| user.getDesignationID() == 26)
							accessLevel = 2;

						models = rFacade.getReceipts("C");
					}
					else
					{
						if (user.getDesignationID() == 27)
						{
							accessLevel = 3;
							models = rFacade.getReceiptsByUser("C",
									user.getStaffID());
						}
					}
					allForestDevelopmentContractors = rFacade
							.getForestDevelopmentContractors(state, "A");
					allLoggingContractors = rFacade.getLoggingContractors(state,
							"A");
					allLicenses = rFacade.getLicenses(state, "A");
					allPermits = rFacade.getPermits(state, "A");

				}
				else
				{
					accessLevel = 3;
					if (district.getCode().equalsIgnoreCase("NSB"))
					{
						ptj[0] = "80000200";
						ptj[1] = "JABATAN PERHUTANAN NEGERI SEMBILAN BARAT";
					}
					else if (district.getCode().equalsIgnoreCase("NST"))
					{
						ptj[0] = "80000300";
						ptj[1] = "JABATAN PERHUTANAN NEGERI SEMBILAN TIMUR";
					}
					models = rFacade.getReceiptsByUser("C", user.getStaffID());
					allForestDevelopmentContractors = rFacade
							.getForestDevelopmentContractors(user, "A");
					allLoggingContractors = rFacade.getLoggingContractors(user,
							"A");
					allLicenses = rFacade.getLicenses(district, "A");
					allPermits = rFacade.getPermits(district, "A");
				}
			}

			forestDevelopmentContractors = new ArrayList<ForestDevelopmentContractor>();
			loggingContractors = new ArrayList<LoggingContractor>();
			licenses = new ArrayList<License>();
			permits = new ArrayList<Permit>();

			for (ForestDevelopmentContractor forestDevelopmentContractor : allForestDevelopmentContractors)
			{
				exist = false;
				forestDevelopmentContractor.setEndDate(today);
				forestDevelopmentContractor.setRenews(new ArrayList<Renew>());
				for (Renew renew : renews)
				{
					if (renew.getType().equalsIgnoreCase("F"))
					{
						if (forestDevelopmentContractor
								.getForestDevelopmentContractorID() == renew
										.getForestDevelopmentContractorID())
						{
							exist = true;
							if (forestDevelopmentContractor.getEndDate()
									.compareTo(renew.getEndDate()) < 0)
							{
								forestDevelopmentContractor
										.setEndDate(renew.getEndDate());
							}
							forestDevelopmentContractor.getRenews().add(renew);
						}
					}
				}
				sort(forestDevelopmentContractor.getRenews());
				if (exist == true)
				{
					forestDevelopmentContractors
							.add(forestDevelopmentContractor);
				}
			}
			allForestDevelopmentContractors = null;

			for (LoggingContractor loggingContractor : allLoggingContractors)
			{
				exist = false;
				loggingContractor.setEndDate(today);
				loggingContractor.setRenews(new ArrayList<Renew>());
				for (Renew renew : renews)
				{
					if (renew.getType().equalsIgnoreCase("C"))
					{
						if (loggingContractor.getLoggingContractorID() == renew
								.getLoggingContractorID())
						{
							exist = true;
							if (loggingContractor.getEndDate()
									.compareTo(renew.getEndDate()) < 0)
							{
								loggingContractor
										.setEndDate(renew.getEndDate());
							}
							loggingContractor.getRenews().add(renew);
						}
					}
				}
				sort(loggingContractor.getRenews());
				if (exist == true)
				{
					loggingContractors.add(loggingContractor);
				}
			}
			allLoggingContractors = null;

			for (License license : allLicenses)
			{
				exist = false;
				license.setEndDate(today);
				license.setRenews(new ArrayList<Renew>());
				for (Renew renew : renews)
				{
					if (renew.getType().equalsIgnoreCase("L"))
					{
						if (license.getLicenseID() == renew.getLicenseID())
						{
							exist = true;
							if (license.getEndDate()
									.compareTo(renew.getEndDate()) < 0)
							{
								license.setEndDate(renew.getEndDate());
							}
							license.getRenews().add(renew);
						}
					}
				}
				sort(license.getRenews());
				if (exist == true)
				{
					licenses.add(license);
				}
			}
			allLicenses = null;

			for (Permit permit : allPermits)
			{
				exist = false;
				permit.setEndDate(today);
				permit.setRenews(new ArrayList<Renew>());
				for (Renew renew : renews)
				{
					if (renew.getType().equalsIgnoreCase("P"))
					{
						if (permit.getPermitID() == renew.getPermitID())
						{
							exist = true;
							if (permit.getEndDate()
									.compareTo(renew.getEndDate()) < 0)
							{
								permit.setEndDate(renew.getEndDate());
							}
							permit.getRenews().add(renew);
						}
					}
				}
				sort(permit.getRenews());
				if (exist == true)
				{
					permits.add(permit);
				}
			}
			allPermits = null;

			paymentTypes = mFacade.getPaymentTypes("A");
			banks = mFacade.getBanks("A");
			chequeTypes = mFacade.getChequeTypes("A");

			if (models != null)
			{
				sort(models);
			}
			else
				models = new ArrayList<>();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Receipt getReceipt()
	{
		return model;
	}

	public void setReceipt(Receipt receipt)
	{
		this.model = receipt;
	}

	public ReceiptRecord getReceiptRecord()
	{
		return receiptRecord;
	}

	public void setReceiptRecord(ReceiptRecord receiptRecord)
	{
		this.receiptRecord = receiptRecord;
	}

	public ArrayList<Receipt> getReceipts()
	{
		return models;
	}

	public void setReceipts(ArrayList<Receipt> receipts)
	{
		this.models = receipts;
	}

	public ArrayList<BursaryVot> getBursaryVots()
	{
		return bursaryVots;
	}

	public void setBursaryVots(ArrayList<BursaryVot> bursaryVots)
	{
		this.bursaryVots = bursaryVots;
	}

	public ArrayList<DepartmentVot> getDepartmentVots()
	{
		return departmentVots;
	}

	public void setDepartmentVots(ArrayList<DepartmentVot> departmentVots)
	{
		this.departmentVots = departmentVots;
	}

	public ArrayList<SelectItem> getDepartmentVotList()
	{
		return departmentVotList;
	}

	public void setDepartmentVotList(ArrayList<SelectItem> departmentVotList)
	{
		this.departmentVotList = departmentVotList;
	}

	public ArrayList<License> getLicenses()
	{
		return licenses;
	}

	public void setLicenses(ArrayList<License> licenses)
	{
		this.licenses = licenses;
	}

	public ArrayList<ForestDevelopmentContractor> getForestDevelopmentContractors()
	{
		return forestDevelopmentContractors;
	}

	public void setForestDevelopmentContractors(
			ArrayList<ForestDevelopmentContractor> forestDevelopmentContractors)
	{
		this.forestDevelopmentContractors = forestDevelopmentContractors;
	}

	public ArrayList<LoggingContractor> getLoggingContractors()
	{
		return loggingContractors;
	}

	public void setLoggingContractors(
			ArrayList<LoggingContractor> loggingContractors)
	{
		this.loggingContractors = loggingContractors;
	}

	public ArrayList<Permit> getPermits()
	{
		return permits;
	}

	public void setPermits(ArrayList<Permit> permits)
	{
		this.permits = permits;
	}

	public ArrayList<PaymentType> getPaymentTypes()
	{
		return paymentTypes;
	}

	public void setPaymentTypes(ArrayList<PaymentType> paymentTypes)
	{
		this.paymentTypes = paymentTypes;
	}

	public ArrayList<Bank> getBanks()
	{
		return banks;
	}

	public void setBanks(ArrayList<Bank> banks)
	{
		this.banks = banks;
	}

	public ArrayList<ChequeType> getChequeTypes()
	{
		return chequeTypes;
	}

	public void setChequeTypes(ArrayList<ChequeType> chequeTypes)
	{
		this.chequeTypes = chequeTypes;
	}

	public ArrayList<ReceiptRecord> getReceiptRecords()
	{
		return receiptRecords;
	}

	public void setReceiptRecords(ArrayList<ReceiptRecord> receiptRecords)
	{
		this.receiptRecords = receiptRecords;
	}

	public ForestDevelopmentContractor getChosenForestDevelopmentContractor()
	{
		return chosenForestDevelopmentContractor;
	}

	public void setChosenForestDevelopmentContractor(
			ForestDevelopmentContractor chosenForestDevelopmentContractor)
	{
		this.chosenForestDevelopmentContractor = chosenForestDevelopmentContractor;
	}

	public LoggingContractor getChosenLoggingContractor()
	{
		return chosenLoggingContractor;
	}

	public void setChosenLoggingContractor(
			LoggingContractor chosenLoggingContractor)
	{
		this.chosenLoggingContractor = chosenLoggingContractor;
	}

	public License getChosenLicense()
	{
		return chosenLicense;
	}

	public void setChosenLicense(License chosenLicense)
	{
		this.chosenLicense = chosenLicense;
	}

	public Permit getChosenPermit()
	{
		return chosenPermit;
	}

	public void setChosenPermit(Permit chosenPermit)
	{
		this.chosenPermit = chosenPermit;
	}

	public boolean isAddReceiptRecordOperation()
	{
		return addReceiptRecordOperation;
	}

	public void setAddReceiptRecordOperation(boolean addReceiptRecordOperation)
	{
		this.addReceiptRecordOperation = addReceiptRecordOperation;
	}

	public int getReceiptCategory()
	{
		return receiptCategory;
	}

	public void setReceiptCategory(int receiptCategory)
	{
		this.receiptCategory = receiptCategory;
	}

	public String getReceiptType()
	{
		return receiptType;
	}

	public void setReceiptType(String receiptType)
	{
		this.receiptType = receiptType;
	}

	public Date getToday()
	{
		return today;
	}

	public void setToday(Date today)
	{
		this.today = today;
	}

	public int getAccessLevel()
	{
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel)
	{
		this.accessLevel = accessLevel;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public BigDecimal getTotal()
	{
		return total;
	}

	public void setTotal(BigDecimal total)
	{
		this.total = total;
	}

	public int getRenewTypeID()
	{
		return renewTypeID;
	}

	public void setRenewTypeID(int renewTypeID)
	{
		this.renewTypeID = renewTypeID;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public long getForestDevelopmentContractorID()
	{
		return forestDevelopmentContractorID;
	}

	public void setForestDevelopmentContractorID(
			long forestDevelopmentContractorID)
	{
		this.forestDevelopmentContractorID = forestDevelopmentContractorID;
	}

	public long getLoggingContractorID()
	{
		return loggingContractorID;
	}

	public void setLoggingContractorID(long loggingContractorID)
	{
		this.loggingContractorID = loggingContractorID;
	}

	public long getLicenseID()
	{
		return licenseID;
	}

	public void setLicenseID(long licenseID)
	{
		this.licenseID = licenseID;
	}

	public long getPermitID()
	{
		return permitID;
	}

	public void setPermitID(long permitID)
	{
		this.permitID = permitID;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public Date getSpecificDate()
	{
		return specificDate;
	}

	public void setSpecificDate(Date specificDate)
	{
		this.specificDate = specificDate;
	}

	public int getReportType()
	{
		return reportType;
	}

	public void setReportType(int reportType)
	{
		this.reportType = reportType;
	}

	public ArrayList<String> getMonths()
	{
		return months;
	}

	public void setMonths(ArrayList<String> months)
	{
		this.months = months;
	}

	public ArrayList<String> getYears()
	{
		return years;
	}

	public void setYears(ArrayList<String> years)
	{
		this.years = years;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public String getStartMonth()
	{
		return startMonth;
	}

	public void setStartMonth(String startMonth)
	{
		this.startMonth = startMonth;
	}

	public String getEndMonth()
	{
		return endMonth;
	}

	public void setEndMonth(String endMonth)
	{
		this.endMonth = endMonth;
	}

	public String getYear()
	{
		return year;
	}

	public void setYear(String year)
	{
		this.year = year;
	}

	public ArrayList<Staff> getStaffs()
	{
		return staffs;
	}

	public void setStaffs(ArrayList<Staff> staffs)
	{
		this.staffs = staffs;
	}

	public String getOperatorID()
	{
		return operatorID;
	}

	public void setOperatorID(String operatorID)
	{
		this.operatorID = operatorID;
	}

	public int getCurrentYear()
	{
		return currentYear;
	}

	public void setCurrentYear(int currentYear)
	{
		this.currentYear = currentYear;
	}

	public String getComponent()
	{
		return ":frmManager:table" + (model == null ? ""
				: ":" + models.indexOf(model) + ":subtable");
	}

	@Override
	public void handleOpen()
	{
		backButtonClicked = false;
		if (addOperation)
		{

			Date date = new Date();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			renewTypeID = 0;
			duration = 0;

			model = new Receipt();

			model.setReceiptID(date.getTime());
			model.setDate(date);
			model.setCategory(receiptCategory);
			model.setPaymentTypeID(0);
			model.setRecorderID(user.getStaffID());
			model.setRecorderName(user.getName());
			model.setRecordTime(timestamp);
			model.setReceiptRecords(new ArrayList<ReceiptRecord>());

			if (receiptCategory == 2 || receiptCategory == 3
					|| receiptCategory == 6 || receiptCategory == 7)
			{
				model.setStatus("I");
			}
			else
			{
				model.setStatus("A");
			}
		}
		else
		{
			model = (Receipt) copy(models, model);
		}
	}

	public void backButtonReceipt()
	{
		backButtonClicked = true;
		execute("PF('popupReceiptRecord').hide()");
		execute("PF('popup').show()");
	}

	public void backButtonRecordReceipt()
	{
		backButtonClicked = true;
		model.setReceiptRecords(null);
		model.setReceiptRecords(new ArrayList<>());
		execute("PF('popupConfirmation').hide()");
		if (model.getCategory() == 5 || model.getCategory() == 9
				|| model.getCategory() == 11 || model.getCategory() == 13)
		{
			execute("PF('popupRenewReceiptRecord').show()");
		}
		else
		{
			execute("PF('popupReceiptRecord').show()");
		}
	}

	public void handleOpenReceiptRecord(int noOfRecords)
	{
		receiptRecords = null;
		receiptRecords = new ArrayList<ReceiptRecord>();

		long time = System.currentTimeMillis();

		for (int i = 0; i < noOfRecords; i++)
		{
			ReceiptRecord receiptRecord = new ReceiptRecord();

			receiptRecord.setReceiptRecordID(time + i);
			receiptRecord.setQuantity(new BigDecimal("1"));

			receiptRecords.add(receiptRecord);
		}
	}

	public void handleRenewReceiptRecord()
	{
		departmentVotList = null;
		departmentVotList = new ArrayList<>();
		model.setCategory(renewTypeID);

		try (MaintenanceFacade mFacade = new MaintenanceFacade();)
		{
			departmentVots = mFacade.getDepartmentVots("A",
					model.getCategory());
			for (BursaryVot bursaryVot : bursaryVots)
			{
				SelectItemGroup group = new SelectItemGroup(
						bursaryVot.getName());
				ArrayList<SelectItem> items = new ArrayList<>();

				for (DepartmentVot departmentVot : departmentVots)
					if (departmentVot.getBursaryVotID() == bursaryVot
							.getBursaryVotID())
						items.add(
								new SelectItem(departmentVot.getBursaryVotID(),
										departmentVot.toString()));

				if (!items.isEmpty())
				{
					group.setSelectItems(
							ArrayListConverter.asSelectItem(items));
					departmentVotList.add(group);
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void receiptEntryPreparation()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				RevenueFacade rFacade = new RevenueFacade())
		{
			AbstractFacade.group(mFacade, rFacade);

			GregorianCalendar calendar = new GregorianCalendar();
			model.setReceiptNo(model.getReceiptNo().toUpperCase() + "/"
					+ calendar.get(GregorianCalendar.YEAR));

			if (rFacade.checkExistingReceiptNo(model))
			{
				addMessage(FacesMessage.SEVERITY_WARN, null, model
						+ " tidak diproses kerana no resit telah wujud, sila masukkan semula no. resit yang sah.");
			}
			else
			{
				execute("PF('popup').hide()");
				if (!(model.getCategory() == 5 || model.getCategory() == 9
						|| model.getCategory() == 11
						|| model.getCategory() == 13))
				{
					departmentVots = mFacade.getDepartmentVots("A",
							model.getCategory());
					if (!backButtonClicked)
						handleOpenReceiptRecord(10);
					update("frmEntryReceiptRecord");
					execute("PF('popupReceiptRecord').show()");
				}
				else
				{
					if (!backButtonClicked)
					{
						handleOpenReceiptRecord(1);
					}
					update("frmEntryRenewReceiptRecord");
					execute("PF('popupRenewReceiptRecord').show()");
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void receiptEntry()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				RevenueFacade rFacade = new RevenueFacade())
		{
			AbstractFacade.group(mFacade, rFacade);

			int count = 0;

			if (addOperation)
			{
				long timestamp = System.currentTimeMillis();
				if (rFacade.addReceipt(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null,
							model + " berjaya ditambahkan.");
					log(rFacade, "Tambah resit, ID " + model.getReceiptID());

					for (ReceiptRecord receiptRecord : model
							.getReceiptRecords())
					{
						if (rFacade.addReceiptRecord(receiptRecord) != 0)
						{
							log(rFacade, "Tambah rekod resit, ID "
									+ receiptRecord.getReceiptRecordID());
							count++;

							if (renewTypeID != 0)
							{
								Renew renew = new Renew();

								renew.setRenewID(timestamp + count);
								renew.setReceiptID(model.getReceiptID());
								renew.setStatus("A");

								Calendar calendar = Calendar.getInstance();

								if (model.getCategory() == 5)
								{
									calendar.setTime(
											chosenForestDevelopmentContractor
													.getEndDate());

									calendar.add(Calendar.DAY_OF_MONTH, 1);
									renew.setStartDate(calendar.getTime());
									calendar.add(Calendar.MONTH, duration);
									calendar.add(Calendar.DAY_OF_MONTH, -1);
									renew.setEndDate(calendar.getTime());

									renew.setType("F");
									renew.setForestDevelopmentContractorID(model
											.getForestDevelopmentContractorID());

									if (rFacade.addRenew(renew) != 0)
									{
										chosenForestDevelopmentContractor
												.setEndDate(renew.getEndDate());
										chosenForestDevelopmentContractor
												.getRenews().add(renew);
										sort(chosenForestDevelopmentContractor
												.getRenews());

										addMessage(FacesMessage.SEVERITY_INFO,
												null,
												chosenForestDevelopmentContractor
														+ " berjaya diperbaharui.");
										log(rFacade,
												"Tambah perbaharui kontraktor pembangunan hutan, ID "
														+ renew.getRenewID());

										for (ForestDevelopmentContractor forestDevelopmentContractor : forestDevelopmentContractors)
										{
											if (forestDevelopmentContractor
													.getForestDevelopmentContractorID() == chosenForestDevelopmentContractor
															.getForestDevelopmentContractorID())
											{
												forestDevelopmentContractor = chosenForestDevelopmentContractor;
												break;
											}
										}
									}
									else
									{
										addMessage(FacesMessage.SEVERITY_INFO,
												null,
												chosenForestDevelopmentContractor
														+ " tidak berjaya diperbaharui.");
									}
								}
								else if (model.getCategory() == 9)
								{
									calendar.setTime(chosenLoggingContractor
											.getEndDate());

									calendar.add(Calendar.DAY_OF_MONTH, 1);
									renew.setStartDate(calendar.getTime());
									calendar.add(Calendar.MONTH, duration);
									calendar.add(Calendar.DAY_OF_MONTH, -1);
									renew.setEndDate(calendar.getTime());

									renew.setType("C");
									renew.setLoggingContractorID(
											model.getLoggingContractorID());

									if (rFacade.addRenew(renew) != 0)
									{
										chosenLoggingContractor
												.setEndDate(renew.getEndDate());
										chosenLoggingContractor.getRenews()
												.add(renew);
										sort(chosenLoggingContractor
												.getRenews());

										addMessage(FacesMessage.SEVERITY_INFO,
												null, chosenLoggingContractor
														+ " berjaya diperbaharui.");
										log(rFacade,
												"Tambah perbaharui pengusaha/kontraktor pembalakan, ID "
														+ renew.getRenewID());

										for (LoggingContractor loggingContractor : loggingContractors)
										{
											if (loggingContractor
													.getLoggingContractorID() == chosenLoggingContractor
															.getLoggingContractorID())
											{
												loggingContractor = chosenLoggingContractor;
												break;
											}
										}
									}
									else
									{
										addMessage(FacesMessage.SEVERITY_INFO,
												null, chosenLoggingContractor
														+ " tidak berjaya diperbaharui.");
									}
								}
								else if (model.getCategory() == 11)
								{
									calendar.setTime(
											chosenLicense.getEndDate());

									calendar.add(Calendar.DAY_OF_MONTH, 1);
									renew.setStartDate(calendar.getTime());
									calendar.add(Calendar.MONTH, duration);
									calendar.add(Calendar.DAY_OF_MONTH, -1);
									renew.setEndDate(calendar.getTime());

									renew.setType("L");
									renew.setLicenseID(model.getLicenseID());

									if (rFacade.addRenew(renew) != 0)
									{
										chosenLicense
												.setEndDate(renew.getEndDate());
										chosenLicense.getRenews().add(renew);
										sort(chosenLicense.getRenews());

										addMessage(FacesMessage.SEVERITY_INFO,
												null, chosenLicense
														+ " berjaya diperbaharui.");
										log(rFacade,
												"Tambah perbaharui lesen, ID "
														+ renew.getRenewID());

										for (License license : licenses)
										{
											if (license
													.getLicenseID() == chosenLicense
															.getLicenseID())
											{
												license = chosenLicense;
												break;
											}
										}
									}
									else
									{
										addMessage(FacesMessage.SEVERITY_INFO,
												null, chosenLicense
														+ " tidak berjaya diperbaharui.");
									}
								}
								else if (model.getCategory() == 13)
								{
									calendar.setTime(chosenPermit.getEndDate());
									calendar.add(Calendar.DAY_OF_MONTH, 1);
									renew.setStartDate(calendar.getTime());
									calendar.add(Calendar.MONTH, duration);
									calendar.add(Calendar.DAY_OF_MONTH, -1);
									renew.setEndDate(calendar.getTime());
									renew.setType("P");
									renew.setPermitID(model.getPermitID());

									if (rFacade.addRenew(renew) != 0)
									{
										chosenPermit
												.setEndDate(renew.getEndDate());
										chosenPermit.getRenews().add(renew);
										sort(chosenPermit.getRenews());

										addMessage(FacesMessage.SEVERITY_INFO,
												null, chosenPermit
														+ " berjaya diperbaharui.");
										log(rFacade,
												"Tambah perbaharui permit, ID "
														+ renew.getRenewID());

										for (Permit permit : permits)
										{
											if (permit
													.getPermitID() == chosenPermit
															.getPermitID())
											{
												permit = chosenPermit;
												break;
											}
										}
									}
									else
									{
										addMessage(FacesMessage.SEVERITY_INFO,
												null, chosenPermit
														+ " tidak berjaya diperbaharui.");
									}
								}
							}
							else
							{
								if (model.getCategory() == 10)
								{
									for (TrustFund trustFund : trustFunds)
									{
										if (trustFund.getSymbol()
												.equalsIgnoreCase("WAKK"))
										{
											if (trustFund
													.getDepartmentVotID() == receiptRecord
															.getDepartmentVotID())
											{
												rFacade.addWoodWorkFund(
														receiptRecord.getRate()
																.multiply(
																		receiptRecord
																				.getQuantity())
																.setScale(2,
																		BigDecimal.ROUND_HALF_UP),
														model.getLicenseID());
											}
										}
										if (trustFund.getSymbol()
												.equalsIgnoreCase("WAL"))
										{
											if (trustFund
													.getDepartmentVotID() == receiptRecord
															.getDepartmentVotID())
											{
												rFacade.addLicenseFund(
														receiptRecord.getRate()
																.multiply(
																		receiptRecord
																				.getQuantity())
																.setScale(2,
																		BigDecimal.ROUND_HALF_UP),
														model.getLicenseID());
											}
										}
									}
								}
								else if (model.getCategory() == 12)
								{
									for (TrustFund trustFund : trustFunds)
									{
										if (trustFund.getSymbol()
												.equalsIgnoreCase("WAJ")
												|| trustFund.getDescription()
														.equalsIgnoreCase(
																"WAKO")
												|| trustFund.getDescription()
														.equalsIgnoreCase(
																"WAM"))
										{
											if (trustFund
													.getDepartmentVotID() == receiptRecord
															.getDepartmentVotID())
											{
												rFacade.addPermitFund(
														receiptRecord.getRate()
																.multiply(
																		receiptRecord
																				.getQuantity())
																.setScale(2,
																		BigDecimal.ROUND_HALF_UP),
														model.getPermitID());
											}
										}
									}
								}

								Revenue revenue = new Revenue();
								revenue.setDepartmentVotID(
										receiptRecord.getDepartmentVotID());
								revenue.setValue(receiptRecord.getRate()
										.multiply(receiptRecord.getQuantity())
										.setScale(2, BigDecimal.ROUND_HALF_UP));
								rFacade.addRevenue(revenue);
								log(rFacade, "Tambah hasil, ID "
										+ revenue.getDepartmentVotID());
								revenue = null;

								Transaction transaction = new Transaction();
								transaction.setTransactionID(timestamp + count);
								transaction.setTransactionType(
										"11" + model.getCategory());
								transaction.setTransactionFormID(
										model.getReceiptID());
								transaction.setDepartmentVotID(
										receiptRecord.getDepartmentVotID());
								transaction.setValue(receiptRecord.getRate()
										.multiply(receiptRecord.getQuantity())
										.setScale(2, BigDecimal.ROUND_HALF_UP));
								transaction
										.setRecordTime(model.getRecordTime());
								rFacade.addTransaction(transaction);
								log(rFacade, "Tambah transaksi, ID "
										+ transaction.getTransactionID());
								transaction = null;
							}
						}
						else
						{
							addMessage(FacesMessage.SEVERITY_WARN, null,
									receiptRecord
											+ " tidak dapat ditambahkan kerana resit rekod telah direkodkan sebelumnya.");
						}
					}

					models.add(model);
					sort(models);

					addMessage(FacesMessage.SEVERITY_INFO, null, "Sebanyak "
							+ count + " rekod resit berjaya ditambahkan.");
				}
				else
				{
					addMessage(FacesMessage.SEVERITY_WARN, null, model
							+ " tidak dapat ditambahkan kerana no. resit telah direkodkan sebelumnya.");
				}

				execute("PF('popupConfirmation').hide()");
			}
			model = null;
		}
		catch (

		SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void receiptRenewEntry()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				RevenueFacade rFacade = new RevenueFacade())
		{

			AbstractFacade.group(mFacade, rFacade);

			if (renewTypeID == 5)
			{
				model.setForestDevelopmentContractorID(
						forestDevelopmentContractorID);
			}
			else
			{
				if (renewTypeID == 9)
				{
					model.setLoggingContractorID(loggingContractorID);
				}
				else
				{
					if (renewTypeID == 11)
					{
						model.setLicenseID(licenseID);
					}
					else
					{
						if (renewTypeID == 13)
						{
							model.setPermitID(permitID);
						}
					}
				}
			}

			model.setGrandTotal(BigDecimal.ZERO);

			for (ReceiptRecord receiptRecord : receiptRecords)
			{
				if (receiptRecord.getDepartmentVotID() != 0)
				{
					model.setGrandTotal(model.getGrandTotal()
							.add(receiptRecord.getRate()
									.multiply(receiptRecord.getQuantity()))
							.setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			model.setRecordTime(timestamp);

			if (addOperation)
			{
				boolean recordReceiptExist = false;
				for (ReceiptRecord receiptRecord : receiptRecords)
				{
					if (receiptRecord.getDepartmentVotID() != 0)
					{
						recordReceiptExist = true;
					}
				}

				if (recordReceiptExist == false)
				{
					addMessage(FacesMessage.SEVERITY_WARN, null, model
							+ " tidak diproses kerana tiada rekod resit.");
				}
				else
				{
					if (rFacade.addReceipt(model) != 0)
					{
						addMessage(FacesMessage.SEVERITY_INFO, null,
								model + " berjaya ditambahkan.");
						log(rFacade,
								"Tambah resit, ID " + model.getReceiptID());

						for (ReceiptRecord receiptRecord : receiptRecords)
						{
							if (receiptRecord.getDepartmentVotID() != 0)
							{
								receiptRecord.getRate().setScale(2,
										BigDecimal.ROUND_HALF_UP);
								receiptRecord
										.setReceiptID(model.getReceiptID());

								if (rFacade
										.addReceiptRecord(receiptRecord) != 0)
								{
									log(rFacade, "Tambah rekod resit, ID "
											+ receiptRecord
													.getReceiptRecordID());

									Revenue revenue = new Revenue();
									revenue.setDepartmentVotID(
											receiptRecord.getDepartmentVotID());
									revenue.setValue(receiptRecord.getRate()
											.multiply(
													receiptRecord.getQuantity())
											.setScale(2,
													BigDecimal.ROUND_HALF_UP));
									rFacade.addRevenue(revenue);
									log(rFacade, "Tambah hasil, ID "
											+ revenue.getDepartmentVotID());
									revenue = null;

									Transaction transaction = new Transaction();
									transaction.setTransactionType(
											"11" + model.getCategory());
									transaction.setTransactionID(
											model.getReceiptID());
									transaction.setDepartmentVotID(
											receiptRecord.getDepartmentVotID());
									transaction.setValue(receiptRecord.getRate()
											.multiply(
													receiptRecord.getQuantity())
											.setScale(2,
													BigDecimal.ROUND_HALF_UP));
									transaction.setRecordTime(timestamp);
									rFacade.addTransaction(transaction);
									transaction = null;

									for (DepartmentVot departmentVot : departmentVots)
									{
										if (receiptRecord
												.getDepartmentVotID() == departmentVot
														.getDepartmentVotID())
										{
											receiptRecord.setDepartmentVotCode(
													departmentVot.getCode());
											receiptRecord.setDepartmentVotName(
													departmentVot.getName());
										}
									}
									model.getReceiptRecords()
											.add(receiptRecord);
								}
							}
						}

						sort(model.getReceiptRecords());
						receiptRecords = null;

						Renew renew = new Renew();

						renew.setRenewID(timestamp.getTime());
						renew.setReceiptID(model.getReceiptID());
						renew.setStatus("A");

						Calendar calendar = Calendar.getInstance();

						if (model.getCategory() == 5)
						{
							for (ForestDevelopmentContractor forestDevelopmentContractor : forestDevelopmentContractors)
							{
								if (forestDevelopmentContractor
										.getForestDevelopmentContractorID() == model
												.getForestDevelopmentContractorID())
								{
									model.setContractorName(
											forestDevelopmentContractor
													.getName());
									model.setCompanyName(
											forestDevelopmentContractor
													.getCompanyName());
									model.setCompanyRegistrationNo(
											forestDevelopmentContractor
													.getRegisteredBusinessNo());
									model.setAddress(forestDevelopmentContractor
											.getAddress());
									model.setTelNo(forestDevelopmentContractor
											.getTelNo());
									chosenForestDevelopmentContractor = forestDevelopmentContractor;

									calendar.setTime(forestDevelopmentContractor
											.getEndDate());

									calendar.add(Calendar.DAY_OF_MONTH, 1);
									renew.setStartDate(calendar.getTime());
									calendar.add(Calendar.MONTH, duration);
									renew.setEndDate(calendar.getTime());

									renew.setType("F");
									renew.setForestDevelopmentContractorID(model
											.getForestDevelopmentContractorID());

									if (rFacade.addRenew(renew) != 0)
									{
										forestDevelopmentContractor
												.setEndDate(renew.getEndDate());
										forestDevelopmentContractor.getRenews()
												.add(renew);
										sort(forestDevelopmentContractor
												.getRenews());

										addMessage(FacesMessage.SEVERITY_INFO,
												null,
												forestDevelopmentContractor
														+ " berjaya diperbaharui.");
										log(rFacade,
												"Tambah perbaharui kontraktor pembangunan hutan, ID "
														+ renew.getRenewID());
									}
									else
									{
										addMessage(FacesMessage.SEVERITY_INFO,
												null,
												forestDevelopmentContractor
														+ " tidak berjaya diperbaharui.");
									}

									break;
								}
							}
						}
						else
						{
							if (model.getCategory() == 9)
							{
								for (LoggingContractor loggingContractor : loggingContractors)
								{
									if (loggingContractor
											.getLoggingContractorID() == model
													.getLoggingContractorID())
									{
										model.setLoggingContractorRegistrationSerialNo(
												loggingContractor
														.getRegistrationSerialNo());
										model.setLoggingContractorType(
												loggingContractor.getType());
										model.setContractorName(
												loggingContractor.getName());
										model.setCompanyName(loggingContractor
												.getCompanyName());
										model.setCompanyRegistrationNo(
												loggingContractor
														.getBusinessRegistrationNo());
										model.setAddress(
												loggingContractor.getAddress());
										model.setTelNo(
												loggingContractor.getTelNo());
										chosenLoggingContractor = loggingContractor;

										calendar.setTime(
												loggingContractor.getEndDate());

										calendar.add(Calendar.DAY_OF_MONTH, 1);
										renew.setStartDate(calendar.getTime());
										calendar.add(Calendar.MONTH, duration);
										renew.setEndDate(calendar.getTime());

										renew.setType("C");
										renew.setLoggingContractorID(
												model.getLoggingContractorID());

										if (rFacade.addRenew(renew) != 0)
										{
											loggingContractor.setEndDate(
													renew.getEndDate());
											loggingContractor.getRenews()
													.add(renew);
											sort(loggingContractor.getRenews());

											addMessage(
													FacesMessage.SEVERITY_INFO,
													null, loggingContractor
															+ " berjaya diperbaharui.");
											log(rFacade,
													"Tambah perbaharui pengusaha/kontraktor pembalakan, ID "
															+ renew.getRenewID());
										}
										else
										{
											addMessage(
													FacesMessage.SEVERITY_INFO,
													null, loggingContractor
															+ " tidak berjaya diperbaharui.");
										}

										break;
									}
								}
							}
							else
							{
								if (model.getCategory() == 11)
								{
									for (License license : licenses)
									{
										if (license.getLicenseID() == model
												.getLicenseID())
										{
											model.setLicenseNo(
													license.getLicenseNo());
											model.setContractorName(
													license.getLicenseeName());
											model.setCompanyName(license
													.getLicenseeCompanyName());
											model.setCompanyRegistrationNo(
													license.getLicenseNo());
											model.setAddress(
													license.getAddress());
											chosenLicense = license;

											calendar.setTime(
													license.getEndDate());

											calendar.add(Calendar.DAY_OF_MONTH,
													1);
											renew.setStartDate(
													calendar.getTime());
											calendar.add(Calendar.MONTH,
													duration);
											renew.setEndDate(
													calendar.getTime());

											renew.setType("L");
											renew.setLicenseID(
													model.getLicenseID());

											if (rFacade.addRenew(renew) != 0)
											{
												license.setEndDate(
														renew.getEndDate());
												license.getRenews().add(renew);
												sort(license.getRenews());

												addMessage(
														FacesMessage.SEVERITY_INFO,
														null,
														license + " berjaya diperbaharui.");
												log(rFacade,
														"Tambah perbaharui lesen, ID "
																+ renew.getRenewID());
											}
											else
											{
												addMessage(
														FacesMessage.SEVERITY_INFO,
														null,
														license + " tidak berjaya diperbaharui.");
											}

											break;
										}
									}
								}
								else
								{
									if (model.getCategory() == 13)
									{
										for (Permit permit : permits)
										{
											if (permit.getPermitID() == model
													.getPermitID())
											{
												model.setPermitTypeID(permit
														.getPermitTypeID());
												model.setPermitTypeCode(permit
														.getPermitTypeCode());
												model.setPaymentTypeName(permit
														.getPermitTypeName());
												model.setPermitNo(
														permit.getPermitNo());
												model.setContractorName(permit
														.getLicenseeName());
												model.setCompanyName(permit
														.getLicenseeCompanyName());
												model.setCompanyRegistrationNo(
														permit.getLicenseeNo());
												model.setAddress(permit
														.getLicenseeAddress());
												model.setTelNo(permit
														.getLicenseeTelNo());
												chosenPermit = permit;
												calendar.setTime(
														permit.getEndDate());
												calendar.add(
														Calendar.DAY_OF_MONTH,
														1);
												renew.setStartDate(
														calendar.getTime());
												calendar.add(Calendar.MONTH,
														duration);
												renew.setEndDate(
														calendar.getTime());
												renew.setType("P");
												renew.setPermitID(
														model.getPermitID());

												if (rFacade
														.addRenew(renew) != 0)
												{
													permit.setEndDate(
															renew.getEndDate());
													permit.getRenews()
															.add(renew);
													sort(permit.getRenews());

													addMessage(
															FacesMessage.SEVERITY_INFO,
															null,
															permit + " berjaya diperbaharui.");
													log(rFacade,
															"Tambah perbaharui permit, ID "
																	+ renew.getRenewID());
												}
												else
												{
													addMessage(
															FacesMessage.SEVERITY_INFO,
															null,
															permit + " tidak berjaya diperbaharui.");
												}

												break;
											}
										}
									}
								}
							}
						}

						for (PaymentType paymentType : paymentTypes)
						{
							if (paymentType.getPaymentTypeID() == model
									.getPaymentTypeID())
							{
								model.setPaymentTypeCode(paymentType.getCode());
								model.setPaymentTypeName(paymentType.getName());
							}
						}
						if (model.getPaymentTypeID() != 1)
						{
							for (Bank bank : banks)
							{
								if (bank.getBankID() == model.getBankID())
								{
									model.setBankCode(bank.getCode());
									model.setBankName(bank.getName());
								}
							}

							if (model.getPaymentTypeID() == 2)
							{
								for (ChequeType chequeType : chequeTypes)
								{
									if (chequeType.getChequeTypeID() == model
											.getChequeTypeID())
									{
										model.setChequeTypeCode(
												chequeType.getCode());
										model.setChequeTypeName(
												chequeType.getName());
									}
								}
							}
						}

						models.add(model);
						sort(models);
					}
					else
					{
						addMessage(FacesMessage.SEVERITY_WARN, null, model
								+ " tidak dapat ditambahkan kerana no. resit telah direkodkan sebelumnya.");
					}
				}
				execute("PF('popupRenewReceiptRecord').hide()");
			}
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void updateReceiptCategory()
	{
		try (RevenueFacade facade = new RevenueFacade())
		{
			model.setCategory(receiptCategory);
			if (facade.updateReceiptCategory(model) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null,
						model + " berjaya dikemaskini.");
				log(facade,
						"Kemaskini kategori resit, ID " + model.getReceiptID());
				int index = models.indexOf(model);
				models.set(index, model);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupUpdateReceiptCategory').hide()");
	}

	public void cancelReceipt()
	{
		try (RevenueFacade facade = new RevenueFacade())
		{
			if (facade.deleteReceipt(model) != 0)
			{
				Calendar calendar = new GregorianCalendar(1900, 0, 1);
				Date date = calendar.getTime();

				if (model.getCategory() == 5 || model.getCategory() == 9
						|| model.getCategory() == 11
						|| model.getCategory() == 13)
				{
					Renew renew = new Renew();
					renew = facade.getRenew(model);

					if (facade.deleteRenew(renew) != 0)
					{
						if (model.getCategory() == 5)
						{
							for (ForestDevelopmentContractor forestDevelopmentContractor : forestDevelopmentContractors)
							{
								if (forestDevelopmentContractor
										.getForestDevelopmentContractorID() == model
												.getForestDevelopmentContractorID())
								{
									forestDevelopmentContractor.getRenews()
											.remove(renew);
									forestDevelopmentContractor
											.setEndDate(date);
									for (Renew ra : forestDevelopmentContractor
											.getRenews())
									{
										if (ra.getEndDate().compareTo(
												forestDevelopmentContractor
														.getEndDate()) > 0)
										{
											forestDevelopmentContractor
													.setEndDate(
															ra.getEndDate());
										}
									}
									sort(forestDevelopmentContractor
											.getRenews());
									chosenForestDevelopmentContractor = forestDevelopmentContractor;
									break;
								}
							}
							addMessage(FacesMessage.SEVERITY_INFO, null,
									"Perbaharui "
											+ chosenForestDevelopmentContractor
											+ " berjaya dibatalkan.");
							log(facade,
									"Batal perbaharui kontraktor pembangunan hutan, ID "
											+ renew.getRenewID());
						}
						if (model.getCategory() == 9)
						{
							for (LoggingContractor loggingContractor : loggingContractors)
							{
								if (loggingContractor
										.getLoggingContractorID() == model
												.getLoggingContractorID())
								{
									loggingContractor.getRenews().remove(renew);
									loggingContractor.setEndDate(date);
									for (Renew ra : loggingContractor
											.getRenews())
									{
										if (ra.getEndDate()
												.compareTo(loggingContractor
														.getEndDate()) > 0)
										{
											loggingContractor.setEndDate(
													ra.getEndDate());
										}
									}
									sort(loggingContractor.getRenews());
									chosenLoggingContractor = loggingContractor;
									break;
								}
							}
							addMessage(FacesMessage.SEVERITY_INFO, null,
									"Perbaharui " + chosenLoggingContractor
											+ " berjaya dibatalkan.");
							log(facade,
									"Batal perbaharui kontraktor pembangunan hutan, ID "
											+ renew.getRenewID());
						}
						if (model.getCategory() == 11)
						{
							for (License license : licenses)
							{
								if (license.getLicenseID() == model
										.getLicenseID())
								{
									license.getRenews().remove(renew);
									license.setEndDate(date);
									for (Renew ra : license.getRenews())
									{
										if (ra.getEndDate().compareTo(
												license.getEndDate()) > 0)
										{
											license.setEndDate(ra.getEndDate());
										}
									}
									sort(license.getRenews());
									chosenLicense = license;
									break;
								}
							}
							addMessage(FacesMessage.SEVERITY_INFO, null,
									"Perbaharui " + chosenLicense
											+ " berjaya dibatalkan.");
							log(facade, "Batal perbaharui lesen, ID "
									+ renew.getRenewID());
						}
						if (model.getCategory() == 13)
						{
							for (Permit permit : permits)
							{
								if (permit.getPermitID() == model.getPermitID())
								{
									permit.getRenews().remove(renew);
									permit.setEndDate(date);
									for (Renew ra : permit.getRenews())
									{
										if (ra.getEndDate().compareTo(
												permit.getEndDate()) > 0)
										{
											permit.setEndDate(ra.getEndDate());
										}
									}
									sort(permit.getRenews());
									chosenPermit = permit;
									break;
								}
							}
							addMessage(FacesMessage.SEVERITY_INFO, null,
									"Perbaharui " + chosenPermit
											+ " berjaya dibatalkan.");
							log(facade, "Batal perbaharui permit, ID "
									+ renew.getRenewID());
						}
					}
				}

				models.remove(model);
				log(facade, "Padam resit, ID " + model.getReceiptID());

				for (ReceiptRecord receiptRecord : model.getReceiptRecords())
				{
					Revenue revenue = new Revenue();
					revenue.setDepartmentVotID(
							receiptRecord.getDepartmentVotID());
					revenue.setValue(receiptRecord.getRate()
							.multiply(receiptRecord.getQuantity())
							.setScale(2, BigDecimal.ROUND_HALF_UP));
					facade.subtractRevenue(revenue);
					log(facade,
							"Tolak hasil, ID " + revenue.getDepartmentVotID());
					revenue = null;

					Timestamp timestamp = new Timestamp(
							System.currentTimeMillis());

					Transaction transaction = new Transaction();
					transaction.setTransactionID(System.currentTimeMillis());
					transaction.setTransactionType("01" + model.getCategory());
					transaction.setTransactionFormID(model.getReceiptID());
					transaction.setDepartmentVotID(
							receiptRecord.getDepartmentVotID());
					transaction.setValue(receiptRecord.getRate()
							.multiply(receiptRecord.getQuantity())
							.setScale(2, BigDecimal.ROUND_HALF_UP));
					transaction.setRecordTime(timestamp);
					facade.addTransaction(transaction);
					transaction = null;

					if ((model.getCategory() == 6
							&& model.getStatus().equalsIgnoreCase("A"))
							|| model.getCategory() == 10)
					{
						for (TrustFund trustFund : trustFunds)
						{
							if (trustFund.getSymbol().equalsIgnoreCase("WAKK"))
							{
								if (trustFund
										.getDepartmentVotID() == receiptRecord
												.getDepartmentVotID())
								{
									facade.subtractWoodWorkFund(receiptRecord
											.getRate()
											.multiply(
													receiptRecord.getQuantity())
											.setScale(2,
													BigDecimal.ROUND_HALF_UP),
											model.getLicenseID());
								}
							}
							if (trustFund.getSymbol().equalsIgnoreCase("WAL"))
							{
								if (trustFund
										.getDepartmentVotID() == receiptRecord
												.getDepartmentVotID())
								{
									facade.subtractLicenseFund(receiptRecord
											.getRate()
											.multiply(
													receiptRecord.getQuantity())
											.setScale(2,
													BigDecimal.ROUND_HALF_UP),
											model.getLicenseID());
								}
							}
						}
					}

					if ((model.getCategory() == 7
							&& model.getStatus().equalsIgnoreCase("A"))
							|| model.getCategory() == 12)
					{
						for (TrustFund trustFund : trustFunds)
						{
							if (trustFund.getSymbol().equalsIgnoreCase("WAJ")
									|| trustFund.getSymbol()
											.equalsIgnoreCase("WAKO")
									|| trustFund.getSymbol()
											.equalsIgnoreCase("WAM"))
							{
								if (trustFund
										.getDepartmentVotID() == receiptRecord
												.getDepartmentVotID())
								{
									facade.subtractJalanMatauKongsiFund(
											receiptRecord.getRate()
													.multiply(receiptRecord
															.getQuantity())
													.setScale(2,
															BigDecimal.ROUND_HALF_UP),
											model.getPermitID());
								}
							}
						}
					}

				}
			}
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupProcess').hide()");
	}

	public void updateCollectorStatement()
	{
		try (RevenueFacade facade = new RevenueFacade())
		{
			if (facade.updateCollectorStatement(model) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null,
						"Kemaskini penyata pemungut berjaya dilaksanakan.");
				log(facade, "Kemaskini penyata pemungut, ID "
						+ model.getReceiptID());
			}
			else
			{
				addMessage(FacesMessage.SEVERITY_WARN, null,
						"Kemaskini penyata pemungut tidak berjaya dilaksanakan.");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		execute("PF('popupCollectorStatement').hide()");
	}

	public void confirmationPage()
	{
		boolean recordReceiptExist = false;

		model.setGrandTotal(BigDecimal.ZERO);

		for (ReceiptRecord receiptRecord : receiptRecords)
		{
			if (receiptRecord.getDepartmentVotID() != 0)
			{
				for (DepartmentVot departmentVot : departmentVots)
				{
					if (departmentVot.getDepartmentVotID() == receiptRecord
							.getDepartmentVotID())
					{
						receiptRecord
								.setDepartmentVotCode(departmentVot.getCode());
						receiptRecord
								.setDepartmentVotName(departmentVot.getName());
						break;
					}
				}
				receiptRecord.setReceiptID(model.getReceiptID());
				model.setGrandTotal(model.getGrandTotal()
						.add(receiptRecord.getRate()
								.multiply(receiptRecord.getQuantity()))
						.setScale(2, BigDecimal.ROUND_HALF_UP));
				model.getReceiptRecords().add(receiptRecord);
				recordReceiptExist = true;
			}
		}

		sort(model.getReceiptRecords());

		if (recordReceiptExist == false)
		{
			execute("PF('popupNoReceiptRecords').show()");
		}
		else
		{
			if (model.getCategory() > 3)
			{
				if (model.getCategory() >= 4 && model.getCategory() <= 5)
				{
					for (ForestDevelopmentContractor forestDevelopmentContractor : forestDevelopmentContractors)
					{
						if (forestDevelopmentContractor
								.getForestDevelopmentContractorID() == model
										.getForestDevelopmentContractorID())
						{
							model.setContractorName(
									forestDevelopmentContractor.getName());
							model.setCompanyName(forestDevelopmentContractor
									.getCompanyName());
							model.setCompanyRegistrationNo(
									forestDevelopmentContractor
											.getRegistrationNo());
							model.setAddress(
									forestDevelopmentContractor.getAddress());
							model.setTelNo(
									forestDevelopmentContractor.getTelNo());
							chosenForestDevelopmentContractor = forestDevelopmentContractor;
							break;
						}
					}
				}
				else if (model.getCategory() >= 6 && model.getCategory() <= 9)
				{
					for (LoggingContractor loggingContractor : loggingContractors)
					{
						if (loggingContractor.getLoggingContractorID() == model
								.getLoggingContractorID())
						{
							model.setLoggingContractorRegistrationSerialNo(
									loggingContractor
											.getRegistrationSerialNo());
							model.setLoggingContractorType(
									loggingContractor.getType());
							model.setContractorName(
									loggingContractor.getName());
							model.setCompanyName(
									loggingContractor.getCompanyName());
							model.setCompanyRegistrationNo(loggingContractor
									.getBusinessRegistrationNo());
							model.setAddress(loggingContractor.getAddress());
							model.setTelNo(loggingContractor.getTelNo());
							chosenLoggingContractor = loggingContractor;
							break;
						}

					}
				}
				else if (model.getCategory() >= 10 && model.getCategory() <= 11)
				{

					for (License license : licenses)
					{
						if (license.getLicenseID() == model.getLicenseID())
						{
							model.setLicenseTypeID(license.getLicenseTypeID());
							model.setLicenseNo(license.getLicenseNo());
							model.setLicenseTypeCode(
									license.getLicenseTypeCode());
							model.setLicenseTypeName(
									license.getLicenseTypeName());
							model.setContractorName(license.getLicenseeName());
							model.setCompanyName(
									license.getLicenseeCompanyName());
							model.setCompanyRegistrationNo(
									license.getLicenseNo());
							model.setAddress(license.getAddress());
							chosenLicense = license;
							break;
						}
					}
				}
				else if (model.getCategory() >= 12 && model.getCategory() <= 13)
				{
					for (Permit permit : permits)
					{
						if (permit.getPermitID() == model.getPermitID())
						{
							model.setPermitTypeID(permit.getPermitTypeID());
							model.setPermitTypeCode(permit.getPermitTypeCode());
							model.setPaymentTypeName(
									permit.getPermitTypeName());
							model.setPermitNo(permit.getPermitNo());
							model.setContractorName(permit.getLicenseeName());
							model.setCompanyName(
									permit.getLicenseeCompanyName());
							model.setCompanyRegistrationNo(
									permit.getLicenseeNo());
							model.setAddress(permit.getLicenseeAddress());
							model.setTelNo(permit.getLicenseeTelNo());
							chosenPermit = permit;
							break;
						}
					}
				}
			}

			for (PaymentType paymentType : paymentTypes)
			{
				if (paymentType.getPaymentTypeID() == model.getPaymentTypeID())
				{
					model.setPaymentTypeCode(paymentType.getCode());
					model.setPaymentTypeName(paymentType.getName());
					break;
				}
			}

			if (model.getPaymentTypeID() != 1 && model.getPaymentTypeID() != 3
					&& model.getPaymentTypeID() != 4)
			{
				for (Bank bank : banks)
				{
					if (bank.getBankID() == model.getBankID())
					{
						model.setBankCode(bank.getCode());
						model.setBankName(bank.getName());
						break;
					}
				}
			}

			if (model.getPaymentTypeID() == 2)
			{
				for (ChequeType chequeType : chequeTypes)
				{
					if (chequeType.getChequeTypeID() == model.getChequeTypeID())
					{
						model.setChequeTypeCode(chequeType.getCode());
						model.setChequeTypeName(chequeType.getName());
						break;
					}
				}
			}

			if (!(model.getCategory() == 5 || model.getCategory() == 9
					|| model.getCategory() == 11 || model.getCategory() == 13))
			{
				execute("PF('popupReceiptRecord').hide()");

			}
			else
			{
				execute("PF('popupRenewReceiptRecord').hide()");
			}
			update("frmConfirmation");
			execute("PF('popupConfirmation').show()");
		}
	}

	public void displayConfirmationPage()
	{
		if (model.getCategory() > 3)
		{
			if (model.getCategory() >= 4 && model.getCategory() <= 5)
			{
				for (ForestDevelopmentContractor forestDevelopmentContractor : forestDevelopmentContractors)
				{
					if (forestDevelopmentContractor
							.getForestDevelopmentContractorID() == model
									.getForestDevelopmentContractorID())
					{
						model.setContractorName(
								forestDevelopmentContractor.getName());
						model.setCompanyName(
								forestDevelopmentContractor.getCompanyName());
						model.setCompanyRegistrationNo(
								forestDevelopmentContractor
										.getRegisteredBusinessNo());
						model.setAddress(
								forestDevelopmentContractor.getAddress());
						model.setTelNo(forestDevelopmentContractor.getTelNo());
						chosenForestDevelopmentContractor = forestDevelopmentContractor;
					}
				}
			}
			else
			{
				if (model.getCategory() >= 6 && model.getCategory() <= 9)
				{
					for (LoggingContractor loggingContractor : loggingContractors)
					{
						if (loggingContractor.getLoggingContractorID() == model
								.getLoggingContractorID())
						{
							model.setLoggingContractorRegistrationSerialNo(
									loggingContractor
											.getRegistrationSerialNo());
							model.setLoggingContractorType(
									loggingContractor.getType());
							model.setContractorName(
									loggingContractor.getName());
							model.setCompanyName(
									loggingContractor.getCompanyName());
							model.setCompanyRegistrationNo(loggingContractor
									.getBusinessRegistrationNo());
							model.setAddress(loggingContractor.getAddress());
							model.setTelNo(loggingContractor.getTelNo());
							chosenLoggingContractor = loggingContractor;
						}
					}
				}
				else
				{
					if (model.getCategory() >= 10 && model.getCategory() <= 11)
					{
						for (License license : licenses)
						{
							if (license.getLicenseID() == model.getLicenseID())
							{
								model.setLicenseNo(license.getLicenseNo());
								model.setContractorName(
										license.getLicenseeName());
								model.setCompanyName(
										license.getLicenseeCompanyName());
								model.setCompanyRegistrationNo(
										license.getLicenseNo());
								model.setAddress(license.getAddress());
								chosenLicense = license;
							}
						}
					}
					else
					{
						if (model.getCategory() >= 12
								&& model.getCategory() <= 13)
						{
							for (Permit permit : permits)
							{
								if (permit.getPermitID() == model.getPermitID())
								{
									model.setPermitTypeID(
											permit.getPermitTypeID());
									model.setPermitTypeCode(
											permit.getPermitTypeCode());
									model.setPaymentTypeName(
											permit.getPermitTypeName());
									model.setPermitNo(permit.getPermitNo());
									model.setContractorName(
											permit.getLicenseeName());
									model.setCompanyName(
											permit.getLicenseeCompanyName());
									model.setCompanyRegistrationNo(
											permit.getLicenseeNo());
									model.setAddress(
											permit.getLicenseeAddress());
									model.setTelNo(permit.getLicenseeTelNo());
									chosenPermit = permit;
								}
							}
						}
					}
				}
			}
		}

		for (PaymentType paymentType : paymentTypes)
		{
			if (paymentType.getPaymentTypeID() == model.getPaymentTypeID())
			{
				model.setPaymentTypeCode(paymentType.getCode());
				model.setPaymentTypeName(paymentType.getName());
			}
		}
		if (model.getPaymentTypeID() != 1 && model.getPaymentTypeID() != 3
				&& model.getPaymentTypeID() != 4)
		{
			for (Bank bank : banks)
			{
				if (bank.getBankID() == model.getBankID())
				{
					model.setBankCode(bank.getCode());
					model.setBankName(bank.getName());
				}
			}
		}

		if (model.getPaymentTypeID() == 2)
		{
			for (ChequeType chequeType : chequeTypes)
			{
				if (chequeType.getChequeTypeID() == model.getChequeTypeID())
				{
					model.setChequeTypeCode(chequeType.getCode());
					model.setChequeTypeName(chequeType.getName());
				}
			}
		}

		model.setGrandTotal(BigDecimal.ZERO);

		for (ReceiptRecord receiptRecord : receiptRecords)
		{
			if (receiptRecord.getDepartmentVotID() != 0)
			{
				for (DepartmentVot departmentVot : departmentVots)
				{
					if (departmentVot.getDepartmentVotID() == receiptRecord
							.getDepartmentVotID())
					{
						receiptRecord
								.setDepartmentVotCode(departmentVot.getCode());
						receiptRecord
								.setDepartmentVotName(departmentVot.getName());
					}
				}
				model.setGrandTotal(model.getGrandTotal()
						.add(receiptRecord.getRate()
								.multiply(receiptRecord.getQuantity()))
						.setScale(2, BigDecimal.ROUND_HALF_UP));
				model.getReceiptRecords().add(receiptRecord);
			}
		}

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		model.setRecordTime(timestamp);
		execute("PF('popupReceiptRecord').hide()");
		update("frmConfirmation");
		execute("PF('popupConfirmation').show()");
	}

	private Date resetCalendar(Calendar cal, int year, int month)
	{
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (year != 0)
		{
			cal.add(Calendar.YEAR, year);
		}
		if (month != 0)
		{
			cal.add(Calendar.MONTH, month);
		}

		return cal.getTime();
	}

	public void calculateTotal(ReceiptRecord receiptRecord)
	{
		total = receiptRecord.getRate().multiply(receiptRecord.getQuantity());
	}

	public void resetTotal()
	{
		total = new BigDecimal(0);
	}

	public void prepareDownload()
	{

	}

	public StreamedContent download(Receipt r)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String name = "Resit_" + r.getReceiptNo() + ".pdf";
		File file = new File(
				external.getRealPath("/") + "files/revenue/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (RevenueFacade rFacade = new RevenueFacade();)
		{
			String[] receipt = new String[13];
			receipt[0] = "0800";
			receipt[1] = "JABATAN PERHUTANAN NEGERI";
			receipt[2] = ptj[0];
			receipt[3] = ptj[1];
			receipt[4] = r.getName();
			receipt[5] = r.getCompanyRegistrationNo();
			receipt[6] = r.getReceiptNo();
			receipt[7] = new SimpleDateFormat("dd/MM/yyyy").format(r.getDate());
			receipt[8] = new SimpleDateFormat("HH:mm:ss")
					.format(r.getRecordTime());
			receipt[9] = r.getPaymentTypeName();
			receipt[10] = r.getChequeNo();
			receipt[11] = r.getBankName();
			receipt[12] = r.getRecorderName();

			ArrayList<String[]> recordReceipts = rFacade
					.getReceiptRecordsForReport(r.getReceiptID());

			ResitGenerator.generate(file, receipt, recordReceipts);

			content = DefaultStreamedContent.builder()
					.contentType("application/pdf").name(name).stream(() ->
					{
						FileInputStream fis = null;

						try
						{
							fis = new FileInputStream(file);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}

						return fis;
					}).build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		return content;
	}

	public StreamedContent downloadReport()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();

		String name = null;
		int monthInt = 0;

		if (reportType >= 7 && reportType <= 10)
		{
			Date currentMonth = new Date();
			try
			{
				currentMonth = sdfMonth.parse(month);
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentMonth);
			monthInt = cal.get(Calendar.MONTH);
		}

		if (reportType == 1)
		{
			name = "Laporan_Kutipan_Harian_Resit_Dari_"
					+ dateFormat.format(startDate) + "_Hingga_"
					+ dateFormat.format(endDate) + ".pdf";
		}
		else
		{
			if (reportType == 2)
			{
				name = "Senarai_Resit_Yang_Dibatalkan_Dari_"
						+ filenameDateFormat.format(startDate) + "_Hingga_"
						+ filenameDateFormat.format(endDate) + ".pdf";
			}
			else
			{
				if (reportType == 3)
				{
					name = "Buku_Tunai_Pungutan_Terimaan_Dari_"
							+ dateFormat.format(startDate) + "_Hingga_"
							+ dateFormat.format(endDate) + ".pdf";
				}
				else
				{
					if (reportType == 4)
					{
						name = "Laporan_Kutipan_Harian_Mengikut_Bentuk_Bayaran_Pada_"
								+ dateFormat.format(specificDate) + ".pdf";
					}
					else
					{
						if (reportType == 5)
						{
							name = "Laporan_Kutipan_Harian_Mengikut_Jenis_Dari_"
									+ dateFormat.format(startDate) + "_Hingga_"
									+ dateFormat.format(endDate) + ".pdf";
						}
						else
						{
							if (reportType == 6)
							{
								name = "Laporan_Kutipan_Harian_Pada_"
										+ dateFormat.format(specificDate)
										+ ".pdf";
							}
							else
							{
								if (reportType == 7)
								{
									name = "Laporan_Urusniaga_Akaun_Amanah_Pada_Bulan_"
											+ month + "_" + year + ".pdf";
								}
								else
								{
									if (reportType == 8)
									{
										name = "Laporan_Urusniaga_Akaun_Hasil_Pada_Bulan_"
												+ month + "_" + year + ".pdf";
									}
									else
									{
										if (reportType == 9)
										{
											name = "Penyata_Akaun_Amanah_Mengikut_Jabatan_Pada_"
													+ month + "_" + year
													+ ".pdf";
										}
										else
										{
											if (reportType == 10)
											{
												name = "Penyata_Akaun_Hasil_Mengikut_Jabatan_Pada_"
														+ month + "_" + year
														+ ".pdf";
											}
											else
											{
												if (reportType == 11)
												{
													name = "Laporan_Bagi_Pembatalan_Hasil_Dari_"
															+ dateFormat.format(
																	startDate)
															+ "_Hingga_"
															+ dateFormat.format(
																	endDate)
															+ ".pdf";
												}
												else
												{
													if (reportType == 12)
													{
														name = "Laporan_Kutipan_Harian_Mengikut_Operator_Pada_"
																+ dateFormat
																		.format(specificDate)
																+ ".pdf";
													}
													else
													{
														if (reportType == 13)
														{
															name = "Penyata_Akaun_Bagi_Lesen_Dari_"
																	+ startMonth
																	+ "_Hingga_"
																	+ endMonth
																	+ "_Bagi_"
																	+ year
																	+ ".pdf";
														}
														else
														{

														}
													}
												}

											}
										}

									}

								}
							}

						}
					}
				}
			}
		}

		File file = new File(
				external.getRealPath("/") + "files/revenue/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (RevenueFacade rFacade = new RevenueFacade();)
		{
			if (reportType == 1)
			{
				ArrayList<String[]> receipts = rFacade
						.getReceiptsString(startDate, endDate, 1);
				LaporanKutipanHarianResitDariTarikhMulaHinggaTarikhAkhirGenerator
						.generate(file, startDate, endDate, receipts, 1);
			}
			else
			{
				if (reportType == 2)
				{
					ArrayList<String[]> receipts = rFacade
							.getSenaraiResitYangDibatalkanDariTarikhMulaHinggaAkhir(
									startDate, endDate);

					if (receipts.size() == 0)
					{
						addMessage(FacesMessage.SEVERITY_WARN, null,
								"Laporan tidak dijana kerana tiada resit yang dibatalkan dari "
										+ sdf.format(startDate) + " hingga "
										+ sdf.format(endDate) + ".");
					}
					else
					{
						SenaraiResitYangDibatalkanDariTarikhMulaHinggaTarikhAkhirGenerator
								.generate(file, startDate, endDate, receipts);
						content = DefaultStreamedContent.builder()
								.contentType("application/pdf").name(name)
								.stream(() ->
								{
									FileInputStream fis = null;

									try
									{
										fis = new FileInputStream(file);
									}
									catch (IOException e)
									{
										e.printStackTrace();
									}

									return fis;
								}).build();
					}
				}
				else
				{
					if (reportType == 3)
					{
						ArrayList<String[]> receipts = rFacade
								.getReceiptsString(startDate, endDate, 3);
						BukuTunaiPungutanTerimaanDariTarikhMulaHinggaTarikhAkhirGenerator
								.generate(file, startDate, endDate, receipts,
										3);
					}
					else
					{
						if (reportType == 4)
						{
							ArrayList<String[]> receipts = rFacade
									.getReceiptsString(specificDate,
											specificDate, 4);
							LaporanKutipanHarianMengikutBentukBayaranPadaTarikhGenerator
									.generate(file, specificDate, specificDate,
											receipts, 4);
						}
						else
						{
							if (reportType == 5)
							{
								ArrayList<ArrayList<String[]>> receipts = rFacade
										.getReceiptsReportType5(startDate,
												endDate);
								LaporanKutipanHarianMengikutJenisDariTarikhMulaHinggaTarikhAkhirGenerator
										.generate(file, startDate, endDate,
												receipts);

							}
							else
							{
								if (reportType == 6)
								{
									ArrayList<String[]> listOfDailyTransactions = rFacade
											.getLaporanKutipanHarianPadaTarikh(
													specificDate);
									LaporanKutipanHarianPadaTarikhGenerator
											.generate(file, specificDate,
													listOfDailyTransactions);

								}
								else
								{
									if (reportType == 7)
									{
										Calendar cal = Calendar.getInstance();
										cal.set(Calendar.MONTH, monthInt);
										cal.set(Calendar.YEAR,
												Integer.parseInt(year));
										ArrayList<String[]> vots = rFacade
												.getLaporanUrusniagaPadaBulanDanTahun(
														monthInt,
														Integer.parseInt(year),
														true);
										LaporanUrusniagaAkaunAmanahPadaBulanDanTahunGenerator
												.generate(file, vots,
														cal.getTime());

									}
									else
									{
										if (reportType == 8)
										{
											Calendar cal = Calendar
													.getInstance();
											cal.set(Calendar.MONTH, monthInt);
											cal.set(Calendar.YEAR,
													Integer.parseInt(year));
											String[] header = rFacade
													.getHeaderLaporanUrusniagaAkaunHasilPadaBulanDanTahun();
											ArrayList<String[]> vots = rFacade
													.getLaporanUrusniagaPadaBulanDanTahun(
															monthInt,
															Integer.parseInt(
																	year),
															false);
											LaporanUrusniagaAkaunHasilPadaBulanDanTahunGenerator
													.generate(file,
															cal.getTime(),
															header, vots);

										}
										else
										{
											if (reportType == 9)
											{
												Calendar cal = Calendar
														.getInstance();
												cal.set(Calendar.MONTH,
														monthInt);
												cal.set(Calendar.YEAR,
														Integer.parseInt(year));
												String[] header = rFacade
														.getHeaderPenyataAkaunAmanahMengikutJabatanPadaBulanDanTahun();
												ArrayList<String[]> contents = rFacade
														.getContentPenyataAkaunAmanahMengikutJabatanPadaBulanDanTahun(
																monthInt,
																Integer.parseInt(
																		year));
												PenyataAkaunAmanahMengikutJabatanPadaBulanDanTahunGenerator
														.generate(file,
																cal.getTime(),
																header,
																contents);

											}
											else
											{
												if (reportType == 10)
												{
													Calendar cal = Calendar
															.getInstance();
													cal.set(Calendar.MONTH,
															monthInt);
													cal.set(Calendar.YEAR,
															Integer.parseInt(
																	year));
													String[] header = rFacade
															.getHeaderPenyataAkaunHasilMengikutJabatanPadaBulanDanTahun();
													ArrayList<String[]> contents = rFacade
															.getContentPenyataAkaunHasilMengikutJabatanPadaBulanDanTahun(
																	monthInt,
																	Integer.parseInt(
																			year));
													PenyataAkaunHasilMengikutJabatanPadaBulanDanTahunGenerator
															.generate(file, cal
																	.getTime(),
																	header,
																	contents);

												}
												else
												{
													if (reportType == 11)
													{
														ArrayList<String[]> listOfCancelledTransactions = rFacade
																.getLaporanBagiPembatalanHasilDariTarikhMulaHinggaAkhir(
																		startDate,
																		endDate);

														LaporanBagiPembatalanHasilDariTarikhMulaHinggaTarikhAkhirGenerator
																.generate(file,
																		startDate,
																		endDate,
																		listOfCancelledTransactions);
													}
													else
													{
														if (reportType == 12)
														{
															for (Staff staff : staffs)
															{
																if (staff
																		.getStaffID()
																		.equalsIgnoreCase(
																				operatorID))
																{
																	currentStaff = staff;
																}
															}
															ArrayList<String[]> data = rFacade
																	.getLaporanKutipanHarianMengikutOperatorSepertiPadaTarikh(
																			specificDate,
																			currentStaff);

															LaporanKutipanHarianMengikutOperatorSepertiPadaTarikhGenerator
																	.generate(
																			file,
																			data,
																			specificDate,
																			currentStaff);
														}
														else
														{
															if (reportType == 13)
															{
																String[] header = rFacade
																		.getHeaderPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir();
																ArrayList<String[]> contents = rFacade
																		.getContentPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
																				startDate,
																				endDate,
																				licenseID);
																String[] balance = rFacade
																		.getBalancePenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir();
																String[] cess = rFacade
																		.getCessPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir();
																ArrayList<String[]> trailers = rFacade
																		.getTrailerPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
																				startDate,
																				endDate,
																				licenseID);
																PenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhirGenerator
																		.generate(
																				file,
																				startDate,
																				endDate,
																				header,
																				contents,
																				balance,
																				cess,
																				trailers);
															}
															else
															{

															}

														}
													}

												}

											}

										}
									}
								}

							}
						}
					}
				}
			}
			if (reportType != 2)
				content = DefaultStreamedContent.builder()
						.contentType("application/pdf").name(name).stream(() ->
						{
							FileInputStream fis = null;

							try
							{
								fis = new FileInputStream(file);
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}

							return fis;
						}).build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		return content;
	}
}