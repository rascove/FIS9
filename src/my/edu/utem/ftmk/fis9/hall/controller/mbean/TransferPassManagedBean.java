package my.edu.utem.ftmk.fis9.hall.controller.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.TreeSet;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.hall.controller.manager.HallFacade;
import my.edu.utem.ftmk.fis9.hall.model.Log;
import my.edu.utem.ftmk.fis9.hall.model.MainRevenueTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.SmallProductTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.SpecialTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.TransferPass;
import my.edu.utem.ftmk.fis9.hall.util.BukuKawalanPengeluaranGenerator;
import my.edu.utem.ftmk.fis9.hall.util.LaporanPengeluaranKayuBalakDiBalaiGenerator;
import my.edu.utem.ftmk.fis9.hall.util.RingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenGenerator;
import my.edu.utem.ftmk.fis9.hall.util.RingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhirGenerator;
import my.edu.utem.ftmk.fis9.hall.util.ShuttleReportGenerator;
import my.edu.utem.ftmk.fis9.hall.util.TransferPassGenerator;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Hall;
import my.edu.utem.ftmk.fis9.maintenance.model.HallOfficer;
import my.edu.utem.ftmk.fis9.maintenance.model.Hammer;
import my.edu.utem.ftmk.fis9.maintenance.model.LogSize;
import my.edu.utem.ftmk.fis9.maintenance.model.MainRevenueRoyaltyRate;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.SmallProduct;
import my.edu.utem.ftmk.fis9.maintenance.model.SmallProductRoyaltyRate;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.Tender;
import my.edu.utem.ftmk.fis9.maintenance.model.Unit;
import my.edu.utem.ftmk.fis9.prefelling.controller.manager.PreFellingFacade;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;
import my.edu.utem.ftmk.fis9.revenue.model.License;
import my.edu.utem.ftmk.fis9.revenue.model.LoggingContractor;
import my.edu.utem.ftmk.fis9.revenue.model.Receipt;
import my.edu.utem.ftmk.fis9.revenue.model.Renew;
import my.edu.utem.ftmk.fis9.tagging.controller.manager.TaggingFacade;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingForm;
import my.edu.utem.ftmk.fis9.tagging.model.TaggingRecord;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "transferPassMBean")
public class TransferPassManagedBean extends AbstractManagedBean<TransferPass>
{
	private static final long serialVersionUID = VERSION;
	private Staff user;
	private License license;
	private Tagging tagging;
	private Log log;
	private District district;
	private LogSize logSize;
	private MainRevenueTransferPassRecord mainRevenueTransferPassRecord;
	private SmallProductTransferPassRecord smallProductTransferPassRecord;
	private ArrayList<HallOfficer> hallOfficers;
	private ArrayList<Hall> halls;
	private ArrayList<License> licenses;
	private ArrayList<Renew> renews;
	private ArrayList<State> destinationStates;
	private ArrayList<Tagging> taggings;
	private ArrayList<Log> logs;
	private ArrayList<LogSize> logSizes;
	private ArrayList<Log> selectedLogs;
	private ArrayList<SmallProduct> smallProducts;
	private ArrayList<Unit> units;
	private ArrayList<Unit> unitCollections;
	private String recorderName;
	private ArrayList<MainRevenueTransferPassRecord> mainRevenueTransferPassRecords;
	private ArrayList<SmallProductTransferPassRecord> smallProductTransferPassRecords;
	private ArrayList<SpecialTransferPassRecord> specialTransferPassRecords;
	private ArrayList<Species> specieses;
	private ArrayList<MainRevenueRoyaltyRate> mainRevenueRoyaltyRates;
	private ArrayList<SmallProductRoyaltyRate> smallProductRoyaltyRates;
	private LinkedHashMap<String, ArrayList<TransferPass>> map;
	private boolean royaltyAndCessRateExist;
	private boolean smallProductRoyaltyAndCessRateExist;
	private boolean logSizeExist;
	private boolean barLicense;
	private boolean updateResin;
	private boolean updateNonResin;
	private boolean updateChengal;
	private boolean updateLog;
	private boolean updateJaras;
	private boolean exceedResinLimit;
	private boolean exceedNonResinLimit;
	private boolean exceedChengalLimit;
	private boolean exceedLogLimit;
	private boolean exceedJarasLimit;
	private boolean transferPassNoExist;
	private long selectedLicenseID;
	private long selectedTaggingID;
	private long selectedTaggingID1;
	private int code;
	private Date today;
	private Date lastYear;
	private Date shuttleReportDate;
	private Date startDate;
	private Date endDate;
	private String message;
	private int accessLevel;
	private ArrayList<String> months;
	private ArrayList<String> years;
	private String month;
	private String startMonth;
	private String endMonth;
	private String year;
	private int endYear;
	private int reportTypeID;
	private SimpleDateFormat sdfMonth;
	private SimpleDateFormat sdf;
	private BigDecimal maximumLimit;
	private BigDecimal totalDipterokarp;
	private BigDecimal totalNonDipterokarp;
	private BigDecimal totalChengal;
	private BigDecimal totalJaras;
	private DecimalFormat df;
	private int maximumRecords;
	private int selectedRecords;
	private boolean backButtonClicked;
	private String hallOfficerErrorMessage;
	private String confirmationPageMessages;
	private String errorMessage;

	public TransferPassManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				TaggingFacade tFacade = new TaggingFacade();
				RevenueFacade rFacade = new RevenueFacade();
				HallFacade hFacade = new HallFacade();)
		{
			AbstractFacade.group(mFacade, tFacade, rFacade, hFacade);

			hallOfficerErrorMessage = null;
			errorMessage = null;

			maximumRecords = 20;

			df = new DecimalFormat("#,###,##0.00");

			maximumLimit = new BigDecimal(1000000000);

			sdf = new SimpleDateFormat("dd/MM/yyyy");

			Calendar calToday = Calendar.getInstance();
			today = resetCalendar(calToday, 0, 0);

			Calendar calLastYear = Calendar.getInstance();
			lastYear = resetCalendar(calLastYear, -1, 0);

			Calendar calNextMonth = Calendar.getInstance();
			Date nextMonth = resetCalendar(calNextMonth, 0, 1);

			GregorianCalendar gc = new GregorianCalendar();
			GregorianCalendar gcYear = new GregorianCalendar();
			sdfMonth = new SimpleDateFormat("MMMM", new Locale("ms"));
			SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy",
					new Locale("ms"));
			months = new ArrayList<String>();
			years = new ArrayList<>();

			for (int i = 0; i < 12; i++)
			{
				months.add(sdfMonth.format(gc.getTime()));
				years.add(sdfYear.format(gcYear.getTime()));
				gc.add(GregorianCalendar.MONTH, 1);
				gcYear.add(GregorianCalendar.YEAR, -1);
			}

			user = getCurrentUser();
			String staffID = user.getStaffID();
			int designationID = user.getDesignationID();
			int stateID = user.getStateID();
			endYear = tFacade.getTaggingYearRange()[1];
			recorderName = user.getName();
			destinationStates = mFacade.getStates();
			renews = rFacade.getRenews("A", lastYear);
			mainRevenueRoyaltyRates = mFacade.getMainRevenueRoyaltyRates("A");
			smallProductRoyaltyRates = mFacade.getSmallProductRoyaltyRates("A");
			smallProducts = mFacade.getSmallProducts("A");
			specieses = mFacade.getSpeciesList();

			ArrayList<SmallProduct> tempSmallProduct = new ArrayList<SmallProduct>();
			for (SmallProduct smallProduct : smallProducts)
			{
				for (SmallProductRoyaltyRate smallProductRoyaltyRate : smallProductRoyaltyRates)
				{
					if (smallProduct
							.getSmallProductID() == smallProductRoyaltyRate
									.getSmallProductID())
					{
						tempSmallProduct.add(smallProduct);
						break;
					}
				}
			}
			smallProducts = tempSmallProduct;
			tempSmallProduct = null;

			logSizes = mFacade.getLogSizes("A");
			logSizeExist = false;

			if (stateID == 0)
			{
				accessLevel = 1;
				taggings = tFacade.getTaggings(true, 0, endYear);
				hallOfficers = mFacade.getHallOfficers(today, "A");
				licenses = rFacade.getLicenses("A");
				if (!logSizes.isEmpty())
				{
					for (LogSize ls : logSizes)
					{
						if (ls.getStateID() == 5)
						{
							logSize = ls;
							logSizeExist = true;
						}
					}
				}
			}
			else
			{
				State state = mFacade.getState(stateID);
				if (!logSizes.isEmpty())
				{
					for (LogSize ls : logSizes)
					{
						if (ls.getStateID() == state.getStateID())
						{
							logSize = ls;
							logSizeExist = true;
						}
					}
				}
				if (state.getDirectorID().equals(staffID)
						|| (state.getDeputyDirector1ID() != null
								&& state.getDeputyDirector1ID().equals(staffID))
						|| (state.getDeputyDirector2ID() != null
								&& state.getDeputyDirector2ID().equals(staffID))
						|| designationID == 6 || designationID == 12)
				{
					accessLevel = 1;
					taggings = tFacade.getTaggings(state, true, 0, endYear);
					hallOfficers = mFacade.getHallOfficers(today, state);
					licenses = rFacade.getLicenses(state, "A");
				}
				else
				{
					hallOfficers = mFacade.getHallOfficers(today, user);
					if (hallOfficers.size() == 0)
					{
						district = mFacade.getDistrict(user);
						if (district != null)
						{
							taggings = tFacade.getTaggings(district, true, 0,
									endYear);
							licenses = rFacade.getLicenses(district, "A");
							if (staffID.equals(district.getOfficerID())
									|| (district.getAsstOfficerID() != null
											&& staffID.equals(district
													.getAsstOfficerID())))
							{
								accessLevel = 1;
								hallOfficers = mFacade.getHallOfficers(today,
										district);
							}
							else
							{
								accessLevel = 0;
								errorMessage = "Anda tidak dibenarkan untuk menggunakan Modul Pas Memindah.";
							}
						}
						else
						{
							accessLevel = 0;
							if (mFacade.getHallOfficers(user) != null)
							{
								errorMessage = "Anda tidak boleh mengeluarkan pas memindah kerana tidak dilantik sebagai pegawai balai bagi Balai Pemeriksa Hutan.";
							}
							else
							{
								errorMessage = "Anda tidak dibenarkan untuk menggunakan Modul Pas Memindah.";
							}
						}
					}
					else
					{
						accessLevel = 2;
						taggings = new ArrayList<Tagging>();
						licenses = new ArrayList<License>();
						ArrayList<Integer> districtIDs = new ArrayList<Integer>();
						ArrayList<District> districts = new ArrayList<District>();
						for (HallOfficer hallOfficer : hallOfficers)
						{
							if (!districtIDs
									.contains(hallOfficer.getDistrictID()))
							{
								System.out.println(
										"semua hallOfficer.getDistrictID()="
												+ hallOfficer.getDistrictID());
								System.out
										.println("hallOfficer.getDistrictID()="
												+ hallOfficer.getDistrictID());
								districtIDs.add(hallOfficer.getDistrictID());
								District district = new District();
								district.setDistrictID(
										hallOfficer.getDistrictID());
								districts.add(district);
							}
						}

						for (District district : districts)
						{
							ArrayList<License> tempLicenses = rFacade
									.getLicenses(district, "A");
							for (License license : tempLicenses)
							{
								licenses.add(license);
							}

						}

						districtIDs = null;
						districts = null;
					}
				}
			}

			if (logSizeExist == false)
			{
				if (errorMessage == null)
				{
					errorMessage = "Pas Memindah tidak dapat diproses kerana saiz balak bagi Negeri Sembilan Darul Khusus tidak direkodkan.";
				}
				else
				{
					errorMessage = errorMessage
							+ " Pas Memindah tidak dapat diproses kerana saiz balak bagi Negeri Sembilan Darul Khusus tidak direkodkan.";
				}
			}
			if (hallOfficers.isEmpty())
			{
				if (errorMessage == null)
				{
					errorMessage = "Pas Memindah tidak dapat diproses kerana tiada pegawai balai yang aktif.";
				}
				else
				{
					errorMessage = errorMessage
							+ " Pas Memindah tidak dapat diproses kerana tiada pegawai balai yang aktif.";
				}
			}

			if (!licenses.isEmpty())
			{
				boolean exist = false;
				ArrayList<License> tempLicenses = new ArrayList<License>();

				for (License license : licenses)
				{
					exist = false;
					license.setEndDate(lastYear);
					license.setRenews(new ArrayList<Renew>());

					for (Renew renew : renews)
					{
						if (license.getLicenseID() == renew.getLicenseID())
						{
							exist = true;

							if (license.getEndDate()
									.compareTo(renew.getEndDate()) <= 0)
								license.setEndDate(renew.getEndDate());

							license.getRenews().add(renew);
						}
					}

					sort(license.getRenews());

					if (exist == true)
					{
						if (license.getEndDate().compareTo(today) < 0)
						{
							license.setStatus("E");
							if (accessLevel < 3)
							{
								tempLicenses.add(license);
							}
						}
						else
						{
							if (license.getEndDate().compareTo(nextMonth) > 0)
							{
								license.setStatus("A");
							}
							else
							{
								license.setStatus("W");
							}
							tempLicenses.add(license);
						}
					}
				}

				licenses = tempLicenses;
				tempLicenses = null;
			}
			else
			{
				if (errorMessage == null)
				{
					errorMessage = "Pas Memindah tidak dapat diproses kerana tiada lesen yang aktif.";
				}
				else
				{
					errorMessage = errorMessage
							+ " Pas Memindah tidak dapat diproses kerana tiada lesen yang aktif.";
				}
			}
			System.out.println("errorMessage=" + errorMessage);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public TransferPass getTransferPass()
	{
		return model;
	}

	public void setTransferPass(TransferPass transferPass)
	{
		this.model = transferPass;
	}

	public License getLicense()
	{
		return license;
	}

	public void setLicense(License license)
	{
		this.license = license;
	}

	public Tagging getTagging()
	{
		return tagging;
	}

	public void setTagging(Tagging tagging)
	{
		this.tagging = tagging;
	}

	public String getRecorderName()
	{
		return recorderName;
	}

	public void setRecorderName(String recorderName)
	{
		this.recorderName = recorderName;
	}

	public long getSelectedLicenseID()
	{
		return selectedLicenseID;
	}

	public void setSelectedLicenseID(long selectedLicenseID)
	{
		this.selectedLicenseID = selectedLicenseID;
	}

	public ArrayList<TransferPass> getTransferPasses()
	{
		return models;
	}

	public void setTransferPasses(ArrayList<TransferPass> transferPasses)
	{
		this.models = transferPasses;
	}

	public ArrayList<Tagging> getTaggings()
	{
		return taggings;
	}

	public void setTaggings(ArrayList<Tagging> taggings)
	{
		this.taggings = taggings;
	}

	public long getSelectedTaggingID()
	{
		return selectedTaggingID;
	}

	public void setSelectedTaggingID(long selectedTaggingID)
	{
		this.selectedTaggingID = selectedTaggingID;
	}

	public Log getLog()
	{
		return log;
	}

	public void setLog(Log log)
	{
		this.log = log;
	}

	public District getDistrict()
	{
		return district;
	}

	public void setDistrict(District district)
	{
		this.district = district;
	}

	public LogSize getLogSize()
	{
		return logSize;
	}

	public void setLogSize(LogSize logSize)
	{
		this.logSize = logSize;
	}

	public ArrayList<HallOfficer> getHallOfficers()
	{
		return hallOfficers;
	}

	public void setHallOfficers(ArrayList<HallOfficer> hallOfficers)
	{
		this.hallOfficers = hallOfficers;
	}

	public ArrayList<Hall> getHalls()
	{
		return halls;
	}

	public void setHalls(ArrayList<Hall> halls)
	{
		this.halls = halls;
	}

	public ArrayList<License> getLicenses()
	{
		return licenses;
	}

	public void setLicenses(ArrayList<License> licenses)
	{
		this.licenses = licenses;
	}

	public ArrayList<State> getDestinationStates()
	{
		return destinationStates;
	}

	public void setDestinationStates(ArrayList<State> destinationStates)
	{
		this.destinationStates = destinationStates;
	}

	public ArrayList<Log> getLogs()
	{
		return logs;
	}

	public void setLogs(ArrayList<Log> logs)
	{
		this.logs = logs;
	}

	public ArrayList<LogSize> getLogSizes()
	{
		return logSizes;
	}

	public void setLogSizes(ArrayList<LogSize> logSizes)
	{
		this.logSizes = logSizes;
	}

	public ArrayList<Unit> getUnitCollections()
	{
		return unitCollections;
	}

	public void setUnitCollections(ArrayList<Unit> unitCollections)
	{
		this.unitCollections = unitCollections;
	}

	public ArrayList<Log> getSelectedLogs()
	{
		return selectedLogs;
	}

	public void setSelectedLogs(ArrayList<Log> selectedLogs)
	{
		this.selectedLogs = selectedLogs;
	}

	public ArrayList<MainRevenueTransferPassRecord> getMainRevenueTransferPassRecords()
	{
		return mainRevenueTransferPassRecords;
	}

	public void setMainRevenueTransferPassRecords(
			ArrayList<MainRevenueTransferPassRecord> mainRevenueTransferPassRecords)
	{
		this.mainRevenueTransferPassRecords = mainRevenueTransferPassRecords;
	}

	public ArrayList<MainRevenueRoyaltyRate> getMainRevenueRoyaltyRates()
	{
		return mainRevenueRoyaltyRates;
	}

	public void setMainRevenueRoyaltyRates(
			ArrayList<MainRevenueRoyaltyRate> mainRevenueRoyaltyRates)
	{
		this.mainRevenueRoyaltyRates = mainRevenueRoyaltyRates;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public boolean isRoyaltyAndCessRateExist()
	{
		return royaltyAndCessRateExist;
	}

	public void setRoyaltyAndCessRateExist(boolean royaltyAndCessRateExist)
	{
		this.royaltyAndCessRateExist = royaltyAndCessRateExist;
	}

	public boolean isLogSizeExist()
	{
		return logSizeExist;
	}

	public void setLogSizeExist(boolean logSizeExist)
	{
		this.logSizeExist = logSizeExist;
	}

	public ArrayList<SmallProduct> getSmallProducts()
	{
		return smallProducts;
	}

	public void setSmallProducts(ArrayList<SmallProduct> smallProducts)
	{
		this.smallProducts = smallProducts;
	}

	public LinkedHashMap<String, ArrayList<TransferPass>> getMap()
	{
		return map;
	}

	public void setMap(LinkedHashMap<String, ArrayList<TransferPass>> map)
	{
		this.map = map;
	}

	public ArrayList<SmallProductTransferPassRecord> getSmallProductTransferPassRecords()
	{
		return smallProductTransferPassRecords;
	}

	public void setSmallProductTransferPassRecords(
			ArrayList<SmallProductTransferPassRecord> smallProductTransferPassRecords)
	{
		this.smallProductTransferPassRecords = smallProductTransferPassRecords;
	}

	public ArrayList<Unit> getUnits()
	{
		return units;
	}

	public void setUnits(ArrayList<Unit> units)
	{
		this.units = units;
	}

	public ArrayList<SmallProductRoyaltyRate> getSmallProductRoyaltyRates()
	{
		return smallProductRoyaltyRates;
	}

	public void setSmallProductRoyaltyRates(
			ArrayList<SmallProductRoyaltyRate> smallProductRoyaltyRates)
	{
		this.smallProductRoyaltyRates = smallProductRoyaltyRates;
	}

	public boolean isSmallProductRoyaltyAndCessRateExist()
	{
		return smallProductRoyaltyAndCessRateExist;
	}

	public void setSmallProductRoyaltyAndCessRateExist(
			boolean smallProductRoyaltyAndCessRateExist)
	{
		this.smallProductRoyaltyAndCessRateExist = smallProductRoyaltyAndCessRateExist;
	}

	public MainRevenueTransferPassRecord getMainRevenueTransferPassRecord()
	{
		return mainRevenueTransferPassRecord;
	}

	public void setMainRevenueTransferPassRecord(
			MainRevenueTransferPassRecord mainRevenueTransferPassRecord)
	{
		this.mainRevenueTransferPassRecord = mainRevenueTransferPassRecord;
	}

	public SmallProductTransferPassRecord getSmallProductTransferPassRecord()
	{
		return smallProductTransferPassRecord;
	}

	public void setSmallProductTransferPassRecord(
			SmallProductTransferPassRecord smallProductTransferPassRecord)
	{
		this.smallProductTransferPassRecord = smallProductTransferPassRecord;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public boolean isBarLicense()
	{
		return barLicense;
	}

	public void setBarLicense(boolean barLicense)
	{
		this.barLicense = barLicense;
	}

	public ArrayList<SpecialTransferPassRecord> getSpecialTransferPassRecords()
	{
		return specialTransferPassRecords;
	}

	public void setSpecialTransferPassRecords(
			ArrayList<SpecialTransferPassRecord> specialTransferPassRecords)
	{
		this.specialTransferPassRecords = specialTransferPassRecords;
	}

	public ArrayList<Species> getSpecieses()
	{
		return specieses;
	}

	public void setSpecieses(ArrayList<Species> specieses)
	{
		this.specieses = specieses;
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

	public Date getShuttleReportDate()
	{
		return shuttleReportDate;
	}

	public void setShuttleReportDate(Date shuttleReportDate)
	{
		this.shuttleReportDate = shuttleReportDate;
	}

	public long getSelectedTaggingID1()
	{
		return selectedTaggingID1;
	}

	public void setSelectedTaggingID1(long selectedTaggingID1)
	{
		this.selectedTaggingID1 = selectedTaggingID1;
	}

	public String getComponent()
	{
		return ":frmManager:table" + (model == null ? ""
				: ":" + models.indexOf(model) + ":subtable");
	}

	public int getAccessLevel()
	{
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel)
	{
		this.accessLevel = accessLevel;
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

	public int getReportTypeID()
	{
		return reportTypeID;
	}

	public void setReportTypeID(int reportTypeID)
	{
		this.reportTypeID = reportTypeID;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public int getMaximumRecords()
	{
		return maximumRecords;
	}

	public void setMaximumRecords(int maximumRecords)
	{
		this.maximumRecords = maximumRecords;
	}

	public int getSelectedRecords()
	{
		return selectedRecords;
	}

	public void setSelectedRecords(int selectedRecords)
	{
		this.selectedRecords = selectedRecords;
	}

	public boolean isBackButtonClicked()
	{
		return backButtonClicked;
	}

	public void setBackButtonClicked(boolean backButtonClicked)
	{
		this.backButtonClicked = backButtonClicked;
	}

	public boolean isTransferPassNoExist()
	{
		return transferPassNoExist;
	}

	public void setTransferPassNoExist(boolean transferPassNoExist)
	{
		this.transferPassNoExist = transferPassNoExist;
	}

	public boolean isExceedResinLimit()
	{
		return exceedResinLimit;
	}

	public void setExceedResinLimit(boolean exceedResinLimit)
	{
		this.exceedResinLimit = exceedResinLimit;
	}

	public boolean isExceedNonResinLimit()
	{
		return exceedNonResinLimit;
	}

	public void setExceedNonResinLimit(boolean exceedNonResinLimit)
	{
		this.exceedNonResinLimit = exceedNonResinLimit;
	}

	public boolean isExceedChengalLimit()
	{
		return exceedChengalLimit;
	}

	public void setExceedChengalLimit(boolean exceedChengalLimit)
	{
		this.exceedChengalLimit = exceedChengalLimit;
	}

	public String getConfirmationPageMessages()
	{
		return confirmationPageMessages;
	}

	public void setConfirmationPageMessages(String confirmationPageMessages)
	{
		this.confirmationPageMessages = confirmationPageMessages;
	}

	public String getHallOfficerErrorMessage()
	{
		return hallOfficerErrorMessage;
	}

	public void setHallOfficerErrorMessage(String hallOfficerErrorMessage)
	{
		this.hallOfficerErrorMessage = hallOfficerErrorMessage;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	@Override
	public void handleOpen()
	{
		if (addOperation)
		{
			unitCollections = new ArrayList<Unit>();
			Staff user = getCurrentUser();
			Date date = new Date();

			model = new TransferPass();

			model.setTransferPassID(System.currentTimeMillis());
			model.setBatchNo(null);
			model.setDate(date);
			model.setCode(code);
			model.setRoyaltyRate(1);
			model.setCessRate(1);
			model.setPremiumRate(1);
			model.setLicenseID(license.getLicenseID());
			model.setLicenseNo(license.getLicenseNo());
			model.setCompanyName(license.getLicenseeCompanyName());
			model.setRecorderID(user.getStaffID());
			model.setRecorderName(user.getName());
			model.setRoyaltyAmount(new BigDecimal(0));
			model.setCessAmount(new BigDecimal(0));
			model.setPremiumAmount(new BigDecimal(0));
			model.setJournalID(0);
			model.setRecordTime(new Timestamp(date.getTime()));
			model.setStatus("A");

			if (model.getCode() == 0)
			{
				model.setLogSizeID(logSize.getLogSizeID());
				model.setMainRevenueTransferPassRecords(new ArrayList<>());
			}
			else if (model.getCode() == 1)
			{
				model.setSmallProductTransferPassRecords(new ArrayList<>());
			}
			else if (model.getCode() == 2)
			{
				model.setLogSizeID(logSize.getLogSizeID());
				model.setSpecialTransferPassRecords(new ArrayList<>());
			}
		}
		else
			model = (TransferPass) copy(models, model);
	}

	public void handleLicenseIDChange()
	{
		clearFilter();

		barLicense = false;

		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				HallFacade hFacade = new HallFacade();
				TaggingFacade tFacade = new TaggingFacade())
		{
			license = null;
			for (License l : licenses)
			{
				if (l.getLicenseID() == selectedLicenseID)
				{
					license = l;
					break;
				}
			}
			models = hFacade.getTransferPasses(license);
			District district = new District();
			district.setDistrictID(license.getDistrictID());
			taggings = tFacade.getTaggings(district, true, 0, endYear);
			hallOfficers = mFacade.getHallOfficers(today, district);

			if (accessLevel == 2)
			{
				ArrayList<TransferPass> tempTransferPasses = new ArrayList<TransferPass>();
				for (TransferPass tp : models)
				{
					if (getCurrentUser().getStaffID()
							.equalsIgnoreCase(tp.getRecorderID()))
					{
						tempTransferPasses.add(tp);
					}
				}
				models = tempTransferPasses;
				tempTransferPasses = null;
			}
			sort(models);

			if (license.getResinLimit().compareTo(maximumLimit) == 0)
			{
				updateResin = false;
			}
			else
			{
				updateResin = true;
			}

			if (license.getNonResinLimit().compareTo(maximumLimit) == 0)
			{
				updateNonResin = false;
			}
			else
			{
				updateNonResin = true;
			}

			if (license.getChengalLimit().compareTo(maximumLimit) == 0)
			{
				updateChengal = false;
			}
			else
			{
				updateChengal = true;
			}

			if (license.getLogLimit().compareTo(maximumLimit) == 0)
			{
				updateLog = false;
			}
			else
			{
				updateLog = true;
			}

			if (license.getJarasLimit().compareTo(maximumLimit) == 0)
			{
				updateJaras = false;
			}
			else
			{
				updateJaras = true;
			}

			message = updateMessage(license);

			if (taggings.isEmpty())
				addMessage(FacesMessage.SEVERITY_WARN, null,
						"Pas memindah hasil utama tidak boleh dikeluarkan kerana tiada sesi penandaan untuk diproses.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	private String updateMessage(License license)
	{
		String message;
		if (license.getWoodWorkFund().compareTo(new BigDecimal("2000")) >= 0)
		{
			message = "Baki wang amanah kerja kayu (royalti dan ses) adalah RM "
					+ df.format(license.getWoodWorkFund().setScale(2,
							BigDecimal.ROUND_HALF_UP))
					+ ".";
		}
		else
		{
			if (license.getWoodWorkFund().compareTo(BigDecimal.ZERO) == 0)
			{
				message = "SEKATAN : Lesen tidak mempunyai wang amanah kerja kayu yang mencukupi. Urusan pengeluaran pas memindah tidak boleh dilaksanakan."
						+ "\n";
				barLicense = true;
			}
			else
			{
				message = "PERINGATAN : Baki wang amanah kerja kayu (royalti dan ses) adalah RM "
						+ df.format(license.getWoodWorkFund().setScale(2,
								BigDecimal.ROUND_HALF_UP))
						+ "." + "\n"
						+ "Mohon maklumkan kepada pelesen untuk menambah wang amanah kerja kayu.";
			}
		}

		message = message + " Baki had pengeluaran lesen adalah ";
		if (license.getLogLimit().compareTo(maximumLimit) != 0)
		{
			message = message + df.format(
					license.getLogLimit().setScale(2, BigDecimal.ROUND_HALF_UP))
					+ " m\u00B3 kayu balak";
		}
		else
		{
			BigDecimal volume = BigDecimal.ZERO;
			if (license.getResinLimit().compareTo(maximumLimit) != 0)
			{
				volume = volume.add(license.getResinLimit().setScale(2,
						BigDecimal.ROUND_HALF_UP));
			}
			if (license.getNonResinLimit().compareTo(maximumLimit) != 0)
			{
				volume = volume.add(license.getNonResinLimit().setScale(2,
						BigDecimal.ROUND_HALF_UP));
			}
			if (license.getChengalLimit().compareTo(maximumLimit) != 0)
			{
				volume = volume.add(license.getChengalLimit().setScale(2,
						BigDecimal.ROUND_HALF_UP));
			}
			message = message + volume.setScale(2, BigDecimal.ROUND_HALF_UP)
					+ " m\u00B3 ";

			if (license.getResinLimit().compareTo(maximumLimit) != 0)
			{
				message = message
						+ "( Kaum Damar = " + df.format(license.getResinLimit()
								.setScale(2, BigDecimal.ROUND_HALF_UP))
						+ " m\u00B3 )";
			}
			if (license.getNonResinLimit().compareTo(maximumLimit) != 0)
			{
				if (license.getResinLimit().compareTo(maximumLimit) != 0)
				{
					message = message + ", ";
				}
				if (license.getJarasLimit().compareTo(maximumLimit) == 0
						&& license.getChengalLimit()
								.compareTo(maximumLimit) == 0)
				{
					message = message + "dan ";
				}
				message = message + "( Kaum Bukan Damar = "
						+ df.format(license.getNonResinLimit().setScale(2,
								BigDecimal.ROUND_HALF_UP))
						+ " m\u00B3 )";
			}
			if (license.getChengalLimit().compareTo(maximumLimit) != 0)
			{
				if (license.getResinLimit().compareTo(maximumLimit) != 0
						|| license.getNonResinLimit()
								.compareTo(maximumLimit) != 0)
				{
					message = message + ", ";
				}
				if (license.getJarasLimit().compareTo(maximumLimit) == 0)
				{
					message = message + "dan ";
				}
				message = message
						+ "( Chengal = " + df.format(license.getChengalLimit()
								.setScale(2, BigDecimal.ROUND_HALF_UP))
						+ " m\u00B3 )";
			}
		}

		if (license.getJarasLimit().compareTo(maximumLimit) != 0)
		{
			if (license.getLogLimit().compareTo(maximumLimit) != 0
					|| license.getResinLimit().compareTo(maximumLimit) != 0
					|| license.getNonResinLimit().compareTo(maximumLimit) != 0
					|| license.getChengalLimit().compareTo(maximumLimit) != 0)
			{
				message = message + " dan ";
			}
			message = message + license.getJarasLimit().setScale(0,
					BigDecimal.ROUND_HALF_UP) + " batang kayu jaras";
		}

		message = message + ".";

		return message;
	}

	public void handleTaggingIDChange()
	{
		clearFilter();

		try (HallFacade hFacade = new HallFacade())
		{
			tagging = null;
			logSizeExist = false;
			logs = null;

			for (Tagging t : taggings)
			{
				if (t.getTaggingID() == selectedTaggingID)
				{
					tagging = t;
					model.setTaggingID(selectedTaggingID);
				}
			}

			LogSize temp = null;

			for (LogSize ls : logSizes)
			{

				if (ls.getStateID() == 0)
					temp = ls;
				else if (ls.getStateID() == tagging.getStateID())
				{
					logSizeExist = true;
					logSize = ls;
				}
			}

			if (temp != null && logSize == null)
			{
				logSizeExist = true;
				logSize = temp;
			}

			if (!logSizeExist)
			{
				addMessage(FacesMessage.SEVERITY_WARN, null,
						"pas memindah tidak boleh dikeluarkan kerana maklumat saiz balak tidak didaftarkan.");
			}
			else
			{
				model.setLogSizeID(logSize.getLogSizeID());
				logs = hFacade.getLogs(tagging, "C");
				if (logs.size() == 0)
					logs = null;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleOpenLogs()
	{
		clearFilter();

		try (HallFacade hFacade = new HallFacade())
		{
			logs = hFacade.getLogs(tagging, "C");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleOpenSmallProductTransferPassRecords()
	{
		long current = System.currentTimeMillis();
		smallProductTransferPassRecords = new ArrayList<SmallProductTransferPassRecord>();

		for (int i = 0; i < maximumRecords; i++)
		{
			SmallProductTransferPassRecord smallProductTransferPassRecord = new SmallProductTransferPassRecord();
			smallProductTransferPassRecord
					.setSmallProductTransferPassRecordID(current + i);
			smallProductTransferPassRecord
					.setTransferPassID(model.getTransferPassID());
			smallProductTransferPassRecords.add(smallProductTransferPassRecord);
		}

		units = new ArrayList<Unit>();
	}

	public void handleOpenSpecialTransferPassRecords()
	{
		long current = System.currentTimeMillis();
		specialTransferPassRecords = new ArrayList<SpecialTransferPassRecord>();
		for (int i = 0; i < maximumRecords; i++)
		{
			SpecialTransferPassRecord specialTransferPassRecord = new SpecialTransferPassRecord();
			specialTransferPassRecord
					.setSpecialTransferPassRecordID(current + i);
			specialTransferPassRecords.add(specialTransferPassRecord);
		}
	}

	public void handleSmallProductIDChange(
			SmallProductTransferPassRecord smallProductTransferPassRecord)
	{
		clearFilter();

		for (SmallProductRoyaltyRate smallProductRoyaltyRate : smallProductRoyaltyRates)
		{
			if (smallProductTransferPassRecord
					.getSmallProductID() == smallProductRoyaltyRate
							.getSmallProductID())
			{
				Unit unit = new Unit();
				unit.setUnitID(smallProductRoyaltyRate.getUnitID());
				unit.setCode(smallProductRoyaltyRate.getUnitCode());
				unit.setName(smallProductRoyaltyRate.getUnitName());
				if (!units.contains(unit))
				{
					units.add(unit);
				}

				if (!unitCollections.contains(unit))
				{
					unitCollections.add(unit);
				}
			}
		}
	}

	public void transferPassRecordSelection()
	{
		try (HallFacade hFacade = new HallFacade())
		{
			long current = System.currentTimeMillis();
			model.setTransferPassNo(model.getTransferPassNo()
					.replaceAll("\\s", "").toUpperCase());

			if (model.getCode() == 0)
			{
				if (logs == null)
				{
					addMessage(FacesMessage.SEVERITY_WARN, null, model
							+ " tidak boleh diproses kerana tiada tual balak.");
				}
				else
				{
					update("frmEntryLog");
					execute("PF('popupLog').show()");
				}
			}
			else if (model.getCode() == 1)
			{
				smallProductTransferPassRecords = new ArrayList<SmallProductTransferPassRecord>();

				for (int i = 0; i < maximumRecords; i++)
				{
					SmallProductTransferPassRecord smallProductTransferPassRecord = new SmallProductTransferPassRecord();
					smallProductTransferPassRecord
							.setSmallProductTransferPassRecordID(current + i);
					smallProductTransferPassRecord
							.setTransferPassID(model.getTransferPassID());
					smallProductTransferPassRecords
							.add(smallProductTransferPassRecord);
				}

				units = new ArrayList<Unit>();

				update("frmEntrySmallProductTransferPassRecord");
				execute("PF('popupSmallProductTransferPassRecord').show()");
			}
			else if (model.getCode() == 2)
			{
				specialTransferPassRecords = new ArrayList<SpecialTransferPassRecord>();

				for (int i = 0; i < maximumRecords; i++)
				{
					SpecialTransferPassRecord specialTransferPassRecord = new SpecialTransferPassRecord();
					specialTransferPassRecord
							.setSpecialTransferPassRecordID(current + i);
					specialTransferPassRecord
							.setTransferPassID(model.getTransferPassID());
					specialTransferPassRecords.add(specialTransferPassRecord);
				}

				update("frmEntrySpecialTransferPassRecord");
				execute("PF('popupSpecialTransferPassRecord').show()");
			}

			execute("PF('popup').hide()");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void transferPassEntry()
	{
		try (HallFacade hFacade = new HallFacade();
				RevenueFacade rFacade = new RevenueFacade())
		{
			AbstractFacade.group(hFacade, rFacade);
			if (addOperation)
			{
				if (hFacade.addTransferPass(model, true) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null,
							model + " berjaya ditambahkan.");
					log(hFacade, "Tambah pas memindah, ID "
							+ model.getTransferPassID());
					models.add(model);
					sort(models);

					int count = 0;
					if (model.getCode() == 0)
					{
						selectedLogs = null;
						sort(model.getMainRevenueTransferPassRecords());
						for (MainRevenueTransferPassRecord mainRevenueTransferPassRecord : model
								.getMainRevenueTransferPassRecords())
						{
							if (hFacade.addMainRevenueTransferPassRecord(
									mainRevenueTransferPassRecord) != 0)
							{
								count++;
								log(hFacade,
										"Tambah rekod pas memindah hasil utama, ID "
												+ mainRevenueTransferPassRecord
														.getMainRevenueTransferPassRecordID());

								Log log = new Log();
								log.setLogID(mainRevenueTransferPassRecord
										.getLogID());
								log.setStatus("P");
								if (hFacade.updateLogStatus(log) != 0)
								{
									log(hFacade,
											"Kemaskini status tual balak, ID "
													+ mainRevenueTransferPassRecord
															.getMainRevenueTransferPassRecordID());
								}
								for (Iterator<Log> logIterator = logs
										.iterator(); logIterator.hasNext();)
								{
									Log l = logIterator.next();
									if (l.getLogID() == log.getLogID())
									{
										logIterator.remove();
									}
								}
								log = null;
							}
							else
							{
								addMessage(FacesMessage.SEVERITY_WARN, null,
										"tual balak "
												+ mainRevenueTransferPassRecord
														.getLogSerialNo()
												+ " tidak dapat ditambah.");
							}
						}
						addMessage(FacesMessage.SEVERITY_INFO, null, "Sebanyak "
								+ count + " tual balak berjaya ditambahkan.");
					}
					else if (model.getCode() == 1)
					{
						sort(model.getSmallProductTransferPassRecords());
						for (SmallProductTransferPassRecord smallProductTransferPassRecord : model
								.getSmallProductTransferPassRecords())
						{
							if (hFacade.addSmallProductTransferPassRecord(
									smallProductTransferPassRecord) != 0)
							{
								count++;
								log(hFacade,
										"Tambah rekod pas memindah keluaran kecil, ID "
												+ smallProductTransferPassRecord
														.getSmallProductTransferPassRecordID());
							}
							else
							{
								addMessage(FacesMessage.SEVERITY_WARN, null,
										"rekod pas memindah "
												+ smallProductTransferPassRecord
														.getSmallProductCode()
												+ " - "
												+ smallProductTransferPassRecord
														.getSmallProductName()
												+ " tidak dapat ditambah.");
							}
						}

						addMessage(FacesMessage.SEVERITY_INFO, null, "Sebanyak "
								+ count
								+ " rekod pas memindah keluaran kecil berjaya ditambahkan.");
					}
					else if (model.getCode() == 2)
					{
						sort(model.getSpecialTransferPassRecords());
						for (SpecialTransferPassRecord specialTransferPassRecord : model
								.getSpecialTransferPassRecords())
						{
							if (hFacade.addSpecialTransferPassRecord(
									specialTransferPassRecord) != 0)
							{
								count++;
								log(hFacade,
										"Tambah rekod pas memindah projek, ID "
												+ specialTransferPassRecord
														.getSpecialTransferPassRecordID());
							}
							else
							{
								addMessage(FacesMessage.SEVERITY_WARN, null,
										"tual balak "
												+ specialTransferPassRecord
														.getSpeciesCode()
												+ " - "
												+ specialTransferPassRecord
														.getSpeciesName()
												+ " tidak dapat ditambah.");
							}
						}
						addMessage(FacesMessage.SEVERITY_INFO, null, "Sebanyak "
								+ count + " tual balak berjaya ditambahkan.");
					}

					if (rFacade.subtractFundAndLimit(updateResin,
							totalDipterokarp, updateNonResin,
							totalNonDipterokarp, updateChengal, totalChengal,
							updateLog,
							totalDipterokarp
									.add(totalNonDipterokarp.add(totalChengal)),
							updateJaras, totalJaras,
							model.getRoyaltyAmount().add(model.getCessAmount())
									.setScale(2, BigDecimal.ROUND_HALF_UP),
							license) != 0)
					{
						license.setWoodWorkFund(license.getWoodWorkFund()
								.subtract(model.getRoyaltyAmount()
										.add(model.getCessAmount()).setScale(2,
												BigDecimal.ROUND_HALF_UP)));
						if (updateResin == true)
							license.setResinLimit(license.getResinLimit()
									.subtract(totalDipterokarp));
						if (updateNonResin == true)
							license.setNonResinLimit(license.getNonResinLimit()
									.subtract(totalNonDipterokarp));
						if (updateChengal == true)
							license.setChengalLimit(license.getChengalLimit()
									.subtract(totalChengal));
						if (updateLog == true)
							license.setLogLimit(license.getLogLimit().subtract(
									totalDipterokarp.add(totalNonDipterokarp
											.add(totalChengal))));
						if (updateJaras == true)
							license.setJarasLimit(license.getJarasLimit()
									.subtract(totalJaras));

						log(hFacade,
								"Kemaskini wang amanah dan had keluaran bagi lesen, ID "
										+ license.getLicenseID());
					}
					else
					{
						addMessage(FacesMessage.SEVERITY_WARN, null,
								"Kemaskini wang amanah dan had keluaran bagi lesen "
										+ license
										+ " tidak dapat dilaksanakan.");
					}
				}

				else
				{
					addMessage(FacesMessage.SEVERITY_WARN, null, model
							+ " tidak dapat ditambahkan kerana no. pas memindah telah direkodkan sebelumnya.");
				}

				message = updateMessage(license);

				update(":frmManager:reminder");

				execute("PF('popupConfirmation').hide()");
			}
			else
			{
				if (hFacade.updateTransferPass(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null,
							model + " berjaya dikemaskini.");
					log(hFacade, "Kemaskini pas memindah, ID "
							+ model.getTransferPassID());
				}
				else
				{
					addMessage(FacesMessage.SEVERITY_WARN, null,
							model + " tidak dapat dikemaskini.");
				}
				execute("PF('popup').hide()");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void confirmationPage()
	{
		int counter = 0;

		if (model.getCode() == 0)
		{
			if (selectedLogs.size() == 0)
			{
				selectedRecords = 0;
				update("frmExceedMaximumRecords");
				execute("PF('popupExceedMaximumRecords').show()");
			}
			else
			{
				if (selectedLogs.size() > maximumRecords)
				{
					selectedRecords = selectedLogs.size();
					update("frmExceedMaximumRecords");
					execute("PF('popupExceedMaximumRecords').show()");
				}
				else
				{
					execute("PF('popupLog').hide()");
					infoToDisplayInConfirmationPage();
				}
			}
		}
		else
		{
			if (model.getCode() == 1)
			{
				for (SmallProductTransferPassRecord smallProductTransferPassRecord : smallProductTransferPassRecords)
				{
					if (smallProductTransferPassRecord.getSmallProductID() != 0)
					{
						counter++;
					}
				}
				if (counter == 0)
				{
					selectedRecords = 0;
					update("frmExceedMaximumRecords");
					execute("PF('popupExceedMaximumRecords').show()");
				}
				else
				{
					execute("PF('popupSmallProductTransferPassRecord').hide()");
					infoToDisplayInConfirmationPage();
				}
			}
			else
			{
				if (model.getCode() == 2)
				{
					for (SpecialTransferPassRecord specialTransferPassRecord : specialTransferPassRecords)
					{
						if (specialTransferPassRecord.getSpeciesID() != 0)
						{
							for (Species species : specieses)
							{
								if (specialTransferPassRecord
										.getSpeciesID() == species
												.getSpeciesID())
								{
									counter++;
								}
							}
						}
					}

					if (counter == 0)
					{
						selectedRecords = 0;
						update("frmExceedMaximumRecords");
						execute("PF('popupExceedMaximumRecords').show()");
					}
					else
					{
						execute("PF('popupSpecialTransferPassRecord').hide()");
						infoToDisplayInConfirmationPage();
					}
				}
			}
		}
	}

	public void infoToDisplayInConfirmationPage()
	{
		try (HallFacade hFacade = new HallFacade();
				RevenueFacade rFacade = new RevenueFacade())
		{
			AbstractFacade.group(hFacade, rFacade);
			confirmationPageMessages = "";

			int count = 0;
			totalDipterokarp = BigDecimal.ZERO;
			totalNonDipterokarp = BigDecimal.ZERO;
			totalChengal = BigDecimal.ZERO;
			totalJaras = BigDecimal.ZERO;
			model.setRoyaltyAmount(BigDecimal.ZERO);
			model.setCessAmount(BigDecimal.ZERO);
			model.setPremiumAmount(BigDecimal.ZERO);
			String royaltyRateNotExistMessage = null;

			for (HallOfficer hallOfficer : hallOfficers)
			{
				if (hallOfficer.getHallOfficerID() == model.getHallOfficerID())
				{
					model.setHallOfficerName(hallOfficer.getHallOfficerName());
					model.setHallOfficerHammerNo(hallOfficer.getHammerNo());
					model.setHallName(hallOfficer.getHallName());
				}
			}

			for (State state : destinationStates)
			{
				if (model.getDestinationStateID() == state.getStateID())
				{
					model.setDestinationStateName(state.getName());
				}
			}

			transferPassNoExist = false;
			transferPassNoExist = hFacade.selectTransferPassNo(model);

			if (model.getCode() == 0)
			{
				long logID = System.currentTimeMillis();

				for (Log log : selectedLogs)
				{
					MainRevenueTransferPassRecord mainRevenueTransferPassRecord = new MainRevenueTransferPassRecord();
					mainRevenueTransferPassRecord
							.setMainRevenueTransferPassRecordID(logID + count);
					mainRevenueTransferPassRecord
							.setTransferPassID(model.getTransferPassID());
					mainRevenueTransferPassRecord.setLogID(log.getLogID());
					mainRevenueTransferPassRecord.setLogNo(log.getLogNo());
					mainRevenueTransferPassRecord
							.setLogSerialNo(log.getLogSerialNo());
					mainRevenueTransferPassRecord
							.setSpeciesCode(log.getSpeciesCode());
					mainRevenueTransferPassRecord
							.setSpeciesName(log.getSpeciesName());
					mainRevenueTransferPassRecord
							.setSpeciesTypeID(log.getSpeciesTypeID());
					mainRevenueTransferPassRecord
							.setDiameter(log.getDiameter());
					mainRevenueTransferPassRecord.setLength(log.getLength());
					mainRevenueTransferPassRecord
							.setGrossVolume(log.getGrossVolume());
					mainRevenueTransferPassRecord
							.setHoleDiameter(log.getHoleDiameter());
					mainRevenueTransferPassRecord
							.setNetVolume(log.getNetVolume());
					mainRevenueTransferPassRecord
							.setTaggingRecordID(log.getTaggingRecordID());
					mainRevenueTransferPassRecord
							.setSpeciesID(log.getSpeciesID());

					royaltyAndCessRateExist = false;

					for (MainRevenueRoyaltyRate mrrr : mainRevenueRoyaltyRates)
					{
						if (mrrr.getSpeciesID() == log.getSpeciesID()
								&& mrrr.getStateID() == tagging.getStateID())
						{
							royaltyAndCessRateExist = true;
							mainRevenueTransferPassRecord
									.setMainRevenueRoyaltyRateID(
											mrrr.getMainRevenueRoyaltyRateID());
							mainRevenueTransferPassRecord.setBigSizeRoyaltyRate(
									mrrr.getBigSizeRoyaltyRate());
							mainRevenueTransferPassRecord
									.setSmallSizeRoyaltyRate(
											mrrr.getSmallSizeRoyaltyRate());
							mainRevenueTransferPassRecord
									.setCessRate(mrrr.getCessRate());
							mainRevenueTransferPassRecord.setJarasRoyaltyRate(
									mrrr.getJarasRoyaltyRate());
							mainRevenueTransferPassRecord
									.setJarasCessRate(mrrr.getJarasCessRate());
							break;
						}
					}

					if (royaltyAndCessRateExist == true)
					{
						if (mainRevenueTransferPassRecord.getDiameter()
								.compareTo(new BigDecimal(
										logSize.getMinBigSize())) >= 0)
						{
							mainRevenueTransferPassRecord.setRoyalty(
									mainRevenueTransferPassRecord.getNetVolume()
											.multiply(
													mainRevenueTransferPassRecord
															.getBigSizeRoyaltyRate())
											.setScale(2,
													BigDecimal.ROUND_HALF_UP));
							mainRevenueTransferPassRecord.setRoyalty(
									mainRevenueTransferPassRecord.getRoyalty()
											.multiply(new BigDecimal(
													model.getRoyaltyRate()))
											.setScale(2,
													BigDecimal.ROUND_HALF_UP));
						}
						else
						{
							if (mainRevenueTransferPassRecord.getDiameter()
									.compareTo(new BigDecimal(
											logSize.getMinSmallSize())) >= 0)
							{
								mainRevenueTransferPassRecord.setRoyalty(
										mainRevenueTransferPassRecord
												.getNetVolume()
												.multiply(
														mainRevenueTransferPassRecord
																.getSmallSizeRoyaltyRate())
												.setScale(2,
														BigDecimal.ROUND_HALF_UP));
								mainRevenueTransferPassRecord.setRoyalty(
										mainRevenueTransferPassRecord
												.getRoyalty()
												.multiply(new BigDecimal(
														model.getRoyaltyRate()))
												.setScale(2,
														BigDecimal.ROUND_HALF_UP));
							}
							else
							{

							}
						}

						mainRevenueTransferPassRecord.setCess(
								mainRevenueTransferPassRecord.getNetVolume()
										.multiply(mainRevenueTransferPassRecord
												.getCessRate())
										.setScale(2, BigDecimal.ROUND_HALF_UP));

						mainRevenueTransferPassRecord
								.setCess(mainRevenueTransferPassRecord.getCess()
										.multiply(new BigDecimal(
												model.getCessRate()))
										.setScale(2, BigDecimal.ROUND_HALF_UP));

						model.getMainRevenueTransferPassRecords()
								.add(mainRevenueTransferPassRecord);

						model.setRoyaltyAmount(model.getRoyaltyAmount().add(
								mainRevenueTransferPassRecord.getRoyalty()));
						model.setCessAmount(model.getCessAmount()
								.add(mainRevenueTransferPassRecord.getCess()));

						if (log.getSpeciesTypeID() == 1
								|| log.getSpeciesTypeID() == 2)
						{
							if ("1201".equals(log.getSpeciesCode()))
							{
								totalChengal = totalChengal
										.add(log.getNetVolume());
							}
							else
							{
								totalDipterokarp = totalDipterokarp
										.add(log.getNetVolume());
							}
						}
						else
						{
							if (log.getSpeciesTypeID() == 3)
							{
								totalNonDipterokarp = totalNonDipterokarp
										.add(log.getNetVolume());
							}
						}

						count++;
					}
					else
					{
						royaltyRateNotExistMessage = log.getSpeciesCode() + "-"
								+ log.getSpeciesName();
						break;
					}
				}
			}
			else
			{
				if (model.getCode() == 1)
				{
					long smallProductTransferPassRecordID = System
							.currentTimeMillis();

					for (SmallProductTransferPassRecord smallProductTransferPassRecord : smallProductTransferPassRecords)
					{
						if (smallProductTransferPassRecord
								.getSmallProductID() != 0)
						{
							smallProductTransferPassRecord.setTransferPassID(
									model.getTransferPassID());

							for (SmallProduct smallProduct : smallProducts)
							{
								if (smallProduct
										.getSmallProductID() == smallProductTransferPassRecord
												.getSmallProductID())
								{
									smallProductTransferPassRecord
											.setSmallProductCode(
													smallProduct.getCode());
									smallProductTransferPassRecord
											.setSmallProductName(
													smallProduct.getName());
									break;
								}
							}

							for (Unit unit : units)
							{
								if (unit.getUnitID() == smallProductTransferPassRecord
										.getUnitID())
								{
									smallProductTransferPassRecord
											.setUnitCode(unit.getCode());
									smallProductTransferPassRecord
											.setUnitName(unit.getName());
									break;
								}
							}

							royaltyAndCessRateExist = false;
							for (SmallProductRoyaltyRate smallProductRoyaltyRate : smallProductRoyaltyRates)
							{
								if (smallProductRoyaltyRate
										.getSmallProductID() == smallProductTransferPassRecord
												.getSmallProductID()
										&& smallProductRoyaltyRate
												.getUnitID() == smallProductTransferPassRecord
														.getUnitID())
								{
									royaltyAndCessRateExist = true;

									smallProductTransferPassRecord
											.setSmallProductTransferPassRecordID(
													smallProductTransferPassRecordID
															+ count);
									smallProductTransferPassRecord
											.setSmallProductRoyaltyRateID(
													smallProductRoyaltyRate
															.getSmallProductRoyaltyRateID());

									smallProductTransferPassRecord
											.setRoyaltyRate(
													smallProductRoyaltyRate
															.getRoyaltyRate());

									smallProductTransferPassRecord
											.setCessRate(smallProductRoyaltyRate
													.getCessRate());

									smallProductTransferPassRecord.setRoyalty(
											smallProductTransferPassRecord
													.getRoyaltyRate().multiply(
															smallProductTransferPassRecord
																	.getQuantity()));

									smallProductTransferPassRecord.setRoyalty(
											smallProductTransferPassRecord
													.getRoyalty()
													.multiply(new BigDecimal(
															model.getRoyaltyRate())));

									smallProductTransferPassRecord.setCess(
											smallProductTransferPassRecord
													.getCessRate().multiply(
															smallProductTransferPassRecord
																	.getQuantity()));

									smallProductTransferPassRecord.setCess(
											smallProductTransferPassRecord
													.getCess()
													.multiply(new BigDecimal(
															model.getCessRate())));

									model.setRoyaltyAmount(model
											.getRoyaltyAmount()
											.add(smallProductTransferPassRecord
													.getRoyalty()));

									model.setCessAmount(model.getCessAmount()
											.add(smallProductTransferPassRecord
													.getCess()));

									model.getSmallProductTransferPassRecords()
											.add(smallProductTransferPassRecord);
									if (smallProductTransferPassRecord
											.getSmallProductCode()
											.regionMatches(0, "JB", 0, 2)
											|| smallProductTransferPassRecord
													.getSmallProductCode()
													.regionMatches(0, "JK", 0,
															2))
									{
										totalJaras = totalJaras.add(
												smallProductTransferPassRecord
														.getQuantity());
									}

									count++;
									break;
								}
							}
							if (royaltyAndCessRateExist == false)
							{
								royaltyRateNotExistMessage = smallProductTransferPassRecord
										.getSmallProductCode()
										+ " - "
										+ smallProductTransferPassRecord
												.getSmallProductName()
										+ " dengan unit "
										+ smallProductTransferPassRecord
												.getUnitCode()
										+ " - " + smallProductTransferPassRecord
												.getUnitName();
								break;
							}
						}
					}
				}
				else
				{
					if (model.getCode() == 2)
					{
						model.setLogSizeID(logSize.getLogSizeID());
						long specialTransferPassRecordID = System
								.currentTimeMillis();

						for (SpecialTransferPassRecord specialTransferPassRecord : specialTransferPassRecords)
						{
							if (specialTransferPassRecord.getSpeciesID() != 0)
							{
								for (Species species : specieses)
								{
									if (specialTransferPassRecord
											.getSpeciesID() == species
													.getSpeciesID())
									{
										specialTransferPassRecord
												.setSpecialTransferPassRecordID(
														specialTransferPassRecordID
																+ count);
										specialTransferPassRecord
												.setSpeciesCode(
														species.getCode());
										specialTransferPassRecord
												.setSpeciesName(
														species.getName());
										specialTransferPassRecord
												.setSpeciesTypeID(species
														.getSpeciesTypeID());
										count++;
										break;
									}
								}

								royaltyAndCessRateExist = false;
								for (MainRevenueRoyaltyRate mrrr : mainRevenueRoyaltyRates)
								{
									if (mrrr.getSpeciesID() == specialTransferPassRecord
											.getSpeciesID()
											&& mrrr.getStateID() == license
													.getStateID())
									{
										royaltyAndCessRateExist = true;
										specialTransferPassRecord
												.setMainRevenueRoyaltyRateID(
														mrrr.getMainRevenueRoyaltyRateID());
										specialTransferPassRecord
												.setBigSizeRoyaltyRate(mrrr
														.getBigSizeRoyaltyRate());
										specialTransferPassRecord
												.setSmallSizeRoyaltyRate(mrrr
														.getSmallSizeRoyaltyRate());
										specialTransferPassRecord.setCessRate(
												mrrr.getCessRate());
										specialTransferPassRecord
												.setJarasRoyaltyRate(mrrr
														.getJarasRoyaltyRate());
										specialTransferPassRecord
												.setJarasCessRate(mrrr
														.getJarasCessRate());
										break;
									}
								}

								if (royaltyAndCessRateExist == true)
								{
									if (specialTransferPassRecord.getDiameter()
											.compareTo(new BigDecimal(logSize
													.getMinBigSize())) >= 0)
									{
										specialTransferPassRecord.setRoyalty(
												specialTransferPassRecord
														.getVolume()
														.multiply(
																specialTransferPassRecord
																		.getBigSizeRoyaltyRate())
														.setScale(2,
																BigDecimal.ROUND_HALF_UP));

										specialTransferPassRecord.setRoyalty(
												specialTransferPassRecord
														.getRoyalty()
														.multiply(
																new BigDecimal(
																		model.getRoyaltyRate()))
														.setScale(2,
																BigDecimal.ROUND_HALF_UP));
									}
									else
									{
										if (specialTransferPassRecord
												.getDiameter()
												.compareTo(new BigDecimal(
														logSize.getMinSmallSize())) >= 0)
										{
											specialTransferPassRecord
													.setRoyalty(
															specialTransferPassRecord
																	.getVolume()
																	.multiply(
																			specialTransferPassRecord
																					.getSmallSizeRoyaltyRate())
																	.setScale(2,
																			BigDecimal.ROUND_HALF_UP));

											specialTransferPassRecord
													.setRoyalty(
															specialTransferPassRecord
																	.getRoyalty()
																	.multiply(
																			new BigDecimal(
																					model.getRoyaltyRate()))
																	.setScale(2,
																			BigDecimal.ROUND_HALF_UP));
										}
										else
										{

										}
									}

									specialTransferPassRecord
											.setCess(specialTransferPassRecord
													.getVolume()
													.multiply(
															specialTransferPassRecord
																	.getCessRate())
													.setScale(2,
															BigDecimal.ROUND_HALF_UP));

									specialTransferPassRecord.setCess(
											specialTransferPassRecord.getCess()
													.multiply(new BigDecimal(
															model.getCessRate()))
													.setScale(2,
															BigDecimal.ROUND_HALF_UP));

									model.getSpecialTransferPassRecords()
											.add(specialTransferPassRecord);

									model.setRoyaltyAmount(
											model.getRoyaltyAmount().add(
													specialTransferPassRecord
															.getRoyalty()));

									model.setCessAmount(model.getCessAmount()
											.add(specialTransferPassRecord
													.getCess()));

									if (specialTransferPassRecord
											.getSpeciesTypeID() == 1
											|| specialTransferPassRecord
													.getSpeciesTypeID() == 2)
									{
										if (specialTransferPassRecord
												.getSpeciesCode()
												.equalsIgnoreCase("1201"))
											totalChengal = totalChengal.add(
													specialTransferPassRecord
															.getVolume());
										else
											totalDipterokarp = totalDipterokarp
													.add(specialTransferPassRecord
															.getVolume());
									}
									else if (specialTransferPassRecord
											.getSpeciesTypeID() == 3)
										totalNonDipterokarp = totalNonDipterokarp
												.add(specialTransferPassRecord
														.getVolume());

								}
								else
								{
									royaltyRateNotExistMessage = specialTransferPassRecord
											.getSpeciesCode() + " - "
											+ specialTransferPassRecord
													.getSpeciesName();
									break;
								}
							}
						}
					}
				}
			}

			if (royaltyAndCessRateExist == false)
			{

				confirmationPageMessages = model
						+ " tidak dapat diproses kerana kadar royalti bagi "
						+ royaltyRateNotExistMessage
						+ " yang dipilih tidak didaftarkan.\n";
			}
			else
			{
				if ((model.getRoyaltyAmount().add(model.getCessAmount())
						.compareTo(license.getWoodWorkFund()) > 0))
				{
					confirmationPageMessages = model
							+ " tidak dapat ditambahkan kerana jumlah wang amanah kerja kayu bagi lesen "
							+ license + " tidak mencukupi.\n";
				}
				else
				{
					if (model.getCode() != 1)
					{
						if (updateResin == true)
						{
							if (license.getResinLimit()
									.compareTo(totalDipterokarp) < 0)
							{
								exceedResinLimit = true;
							}
							else
							{
								exceedResinLimit = false;
							}
						}

						if (updateNonResin == true)
						{
							if (license.getNonResinLimit()
									.compareTo(totalNonDipterokarp) < 0)
							{
								exceedNonResinLimit = true;
							}
							else
							{
								exceedNonResinLimit = false;
							}
						}

						if (updateChengal == true)
						{
							if (license.getChengalLimit()
									.compareTo(totalChengal) < 0)
							{
								exceedChengalLimit = true;
							}
							else
							{
								exceedChengalLimit = false;
							}
						}

						if (updateLog == true)
						{
							if (license.getLogLimit().compareTo(
									totalDipterokarp.add(totalNonDipterokarp
											.add(totalChengal))) < 0)
							{
								exceedLogLimit = true;
							}
							else
							{
								exceedLogLimit = false;
							}
						}

						if (exceedResinLimit == true
								|| exceedNonResinLimit == true
								|| exceedChengalLimit == true
								|| exceedLogLimit == true)
						{
							if (exceedResinLimit == true)
							{
								confirmationPageMessages = model
										+ " tidak dapat ditambahkan kerana had damar pas memindah melebihi dari jumlah had damar yang dibenarkan bagi pelesen.\n";
							}
							else if (exceedNonResinLimit == true)
							{
								confirmationPageMessages = model
										+ " tidak dapat ditambahkan kerana had bukan damar pas memindah melebihi dari jumlah had bukan damar yang dibenarkan bagi pelesen.\n";
							}
							else if (exceedChengalLimit == true)
							{
								confirmationPageMessages = model
										+ " tidak dapat ditambahkan kerana had chengal pas memindah melebihi dari jumlah had chengal yang dibenarkan bagi pelesen.\n";
							}
							else if (exceedLogLimit == true)
							{
								confirmationPageMessages = model
										+ " tidak dapat ditambahkan kerana had isipadu pas memindah melebihi dari jumlah had isipadu balak yang dibenarkan bagi pelesen.\n";
							}
						}
						else
						{
							if (transferPassNoExist)
							{
								confirmationPageMessages = model
										+ " tidak dapat ditambahkan kerana no. pas memindah telah direkodkan sebelumnya.";
							}
						}
					}
					else
					{
						if (license.getJarasLimit().compareTo(totalJaras) < 0)
						{
							exceedJarasLimit = true;
						}
						else
						{
							exceedJarasLimit = false;
						}
						if (exceedJarasLimit == true)
						{
							confirmationPageMessages = model
									+ " tidak dapat ditambahkan kerana had jaras pas memindah melebihi dari jumlah had jaras yang dibenarkan bagi pelesen.\n";
						}
						else
						{
							if (transferPassNoExist)
							{
								confirmationPageMessages = model
										+ " tidak dapat ditambahkan kerana no. pas memindah telah direkodkan sebelumnya.";
							}
						}
					}
				}
			}

			update("frmConfirmation");
			execute("PF('popupConfirmation').show()");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void backButtonTransferPass()
	{
		backButtonClicked = true;
		if (model.getCode() == 0)
		{
			execute("PF('popupLog').hide()");
		}
		else if (model.getCode() == 1)
		{
			execute("PF('popupSmallProductTransferPassRecord').hide()");
		}
		else if (model.getCode() == 2)
		{
			execute("PF('popupSpecialTransferPassRecord').hide()");
		}
		execute("PF('popup').show()");
	}

	public void backButtonTransferPassRecord()
	{
		backButtonClicked = true;
		execute("PF('popupConfirmation').hide()");
		if (model.getCode() == 0)
		{
			model.setMainRevenueTransferPassRecords(null);
			model.setMainRevenueTransferPassRecords(new ArrayList<>());
			execute("PF('popupLog').show()");
		}
		else if (model.getCode() == 1)
		{
			model.setSmallProductTransferPassRecords(null);
			model.setSmallProductTransferPassRecords(new ArrayList<>());
			execute("PF('popupSmallProductTransferPassRecord').show()");
		}
		else if (model.getCode() == 2)
		{
			model.setSpecialTransferPassRecords(null);
			model.setSpecialTransferPassRecords(new ArrayList<>());
			execute("PF('popupSpecialTransferPassRecord').show()");
		}
	}

	public void mainRevenueTransferPassRecordDelete()
	{
		try (HallFacade facade = new HallFacade())
		{
			if (facade.deleteMainRevenueTransferPassRecord(
					mainRevenueTransferPassRecord) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null,
						mainRevenueTransferPassRecord + " berjaya dipadamkan.");

				log(facade,
						"Padam rekod pas memindah hasil utama, ID "
								+ mainRevenueTransferPassRecord
										.getMainRevenueTransferPassRecordID());
				Log log = new Log();
				log.setLogID(mainRevenueTransferPassRecord.getLogID());
				log.setStatus("C");
				if (facade.updateLogStatus(log) != 0)
				{
					if (logs == null)
					{
						logs = new ArrayList<Log>();
					}
					logs.add(log);
					log(facade, "Kemaskini tual balak, ID " + log.getLogID());
				}
				log = null;
				model.setRoyaltyAmount(model.getRoyaltyAmount()
						.subtract(mainRevenueTransferPassRecord.getRoyalty()));
				model.setCessAmount(model.getCessAmount()
						.subtract(mainRevenueTransferPassRecord.getCess()));
				if (facade.updateTransferPass(model) != 0)
				{
					log(facade, "Kemaskini pas memindah, ID "
							+ model.getTransferPassID());
				}
				model.getMainRevenueTransferPassRecords()
						.remove(mainRevenueTransferPassRecord);
				sort(model.getMainRevenueTransferPassRecords());
				mainRevenueTransferPassRecord = null;
			}
			else
			{
				addMessage(FacesMessage.SEVERITY_WARN, null,
						mainRevenueTransferPassRecord
								+ " tidak dapat dipadamkan.");
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void smallProductTransferPassRecordDelete()
	{
		try (HallFacade facade = new HallFacade())
		{
			if (facade.deleteSmallProductTransferPassRecord(
					smallProductTransferPassRecord) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null,
						smallProductTransferPassRecord
								+ " berjaya dipadamkan.");

				log(facade,
						"Padam rekod pas memindah keluaran kecil, ID "
								+ smallProductTransferPassRecord
										.getSmallProductTransferPassRecordID());

				model.setRoyaltyAmount(model.getRoyaltyAmount()
						.subtract(smallProductTransferPassRecord.getRoyalty()));
				model.setCessAmount(model.getCessAmount()
						.subtract(smallProductTransferPassRecord.getCess()));
				facade.updateTransferPass(model);
				log(facade, "Kemaskini pas memindah, ID "
						+ model.getTransferPassID());
				model.getSmallProductTransferPassRecords()
						.remove(smallProductTransferPassRecord);
				sort(model.getSmallProductTransferPassRecords());
				smallProductTransferPassRecord = null;
				model.setRoyaltyAmount(model.getRoyaltyAmount()
						.subtract(smallProductTransferPassRecord.getRoyalty()));
				model.setCessAmount(model.getCessAmount()
						.subtract(smallProductTransferPassRecord.getCess()));
				if (facade.updateTransferPass(model) != 0)
				{
					log(facade, "Kemaskini pas memindah, ID "
							+ model.getTransferPassID());
				}
			}
			else
			{
				addMessage(FacesMessage.SEVERITY_WARN, null,
						smallProductTransferPassRecord
								+ " tidak dapat dipadamkan.");
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void deleteTransferPass()
	{
		try (HallFacade hFacade = new HallFacade();
				TaggingFacade tFacade = new TaggingFacade();
				RevenueFacade rFacade = new RevenueFacade();
				MaintenanceFacade mFacade = new MaintenanceFacade())
		{
			AbstractFacade.group(hFacade, tFacade, rFacade);

			if (hFacade.deleteTransferPass(model) != 0)
			{
				model.setStatus("I");
				BigDecimal totalDipterokarp = BigDecimal.ZERO;
				BigDecimal totalNonDipterokarp = BigDecimal.ZERO;
				BigDecimal totalChengal = BigDecimal.ZERO;
				BigDecimal totalJaras = BigDecimal.ZERO;

				if (model.getCode() == 0)
				{
					System.out.println(
							model.getMainRevenueTransferPassRecords().size());
					for (MainRevenueTransferPassRecord mainRevenueTransferPassRecord : model
							.getMainRevenueTransferPassRecords())
					{

						Log log = new Log();

						System.out.println(
								"mainRevenueTransferPassRecord.getLogID()="
										+ mainRevenueTransferPassRecord
												.getLogID());
						System.out.println(
								"mainRevenueTransferPassRecord.getLogNo()="
										+ mainRevenueTransferPassRecord
												.getLogNo());
						System.out.println(
								"mainRevenueTransferPassRecord.getLogSerialNo()="
										+ mainRevenueTransferPassRecord
												.getLogSerialNo());
						System.out.println(
								"mainRevenueTransferPassRecord.getLength()="
										+ mainRevenueTransferPassRecord
												.getLength());
						System.out.println(
								"mainRevenueTransferPassRecord.getDiameter()="
										+ mainRevenueTransferPassRecord
												.getDiameter());
						System.out.println(
								"mainRevenueTransferPassRecord.getGrossVolume()="
										+ mainRevenueTransferPassRecord
												.getGrossVolume());
						System.out.println(
								"mainRevenueTransferPassRecord.getHoleDiameter()="
										+ mainRevenueTransferPassRecord
												.getHoleDiameter());
						System.out.println(
								"mainRevenueTransferPassRecord.getNetVolume()="
										+ mainRevenueTransferPassRecord
												.getNetVolume());
						System.out.println(
								"mainRevenueTransferPassRecord.getTaggingRecordID()="
										+ mainRevenueTransferPassRecord
												.getTaggingRecordID());
						System.out.println(
								"mainRevenueTransferPassRecord.getSpeciesID()="
										+ mainRevenueTransferPassRecord
												.getSpeciesID());
						System.out.println(
								"mainRevenueTransferPassRecord.getSpeciesCode()="
										+ mainRevenueTransferPassRecord
												.getSpeciesCode());
						System.out.println(
								"mainRevenueTransferPassRecord.getSpeciesName()="
										+ mainRevenueTransferPassRecord
												.getSpeciesName());
						System.out.println(
								"mainRevenueTransferPassRecord.getSpeciesTypeID()="
										+ mainRevenueTransferPassRecord
												.getSpeciesTypeID());
						log.setLogID(mainRevenueTransferPassRecord.getLogID());
						log.setLogNo(mainRevenueTransferPassRecord.getLogNo());
						log.setLogSerialNo(
								mainRevenueTransferPassRecord.getLogSerialNo());
						log.setLength(
								mainRevenueTransferPassRecord.getLength());
						log.setDiameter(
								mainRevenueTransferPassRecord.getDiameter());
						// log.setGrossVolume(mainRevenueTransferPassRecord.getGrossVolume());
						log.setHoleDiameter(mainRevenueTransferPassRecord
								.getHoleDiameter());
						// log.setGrossVolume(mainRevenueTransferPassRecord.getNetVolume());
						log.setTaggingRecordID(mainRevenueTransferPassRecord
								.getTaggingRecordID());
						log.setSpeciesID(
								mainRevenueTransferPassRecord.getSpeciesID());
						log.setSpeciesCode(
								mainRevenueTransferPassRecord.getSpeciesCode());
						log.setSpeciesName(
								mainRevenueTransferPassRecord.getSpeciesName());
						log.setSummaryTaggingRecord(
								mainRevenueTransferPassRecord.getLogNo() + "-( "
										+ mainRevenueTransferPassRecord
												.getSpeciesCode()
										+ "-" + mainRevenueTransferPassRecord
												.getSpeciesName()
										+ " )");
						log.setSpeciesTypeID(mainRevenueTransferPassRecord
								.getSpeciesTypeID());
						log.setStatus("C");

						if (log.getSpeciesTypeID() == 1
								|| log.getSpeciesTypeID() == 2)
						{
							if ("1201".equals(log.getSpeciesCode()))
							{
								totalChengal = totalChengal
										.add(log.getNetVolume());
							}
							else
							{
								totalDipterokarp = totalDipterokarp
										.add(log.getNetVolume());
							}
						}
						else
						{
							if (log.getSpeciesTypeID() == 3)
							{
								totalNonDipterokarp = totalNonDipterokarp
										.add(log.getNetVolume());
							}
						}
						// TODO
						if (hFacade.updateLogStatus(log) != 0)
						{
							log(mFacade, "Kemaskini tual balak, ID "
									+ log.getLogID());
							TaggingRecord taggingRecord = new TaggingRecord();
							taggingRecord.setTaggingRecordID(
									log.getTaggingRecordID());
							taggingRecord.setStatus("P");
							if (tFacade.updateTaggingRecordStatus(
									taggingRecord) != 0)
							{
								log(mFacade, "Kemaskini rekod penandaan, ID "
										+ taggingRecord.getTaggingRecordID());
							}
							taggingRecord = null;
						}
						if (logs != null)
						{
							logs.add(log);
						}
						log = null;
					}
				}
				else
				{
					if (model.getCode() == 1)
					{
						for (SmallProductTransferPassRecord smallProductTransferPassRecord : model
								.getSmallProductTransferPassRecords())
						{
							if (smallProductTransferPassRecord
									.getSmallProductCode()
									.regionMatches(0, "JB", 0, 2)
									|| smallProductTransferPassRecord
											.getSmallProductCode()
											.regionMatches(0, "JK", 0, 2))
							{
								totalJaras = totalJaras
										.add(smallProductTransferPassRecord
												.getQuantity());
							}
						}

					}
					else
					{
						if (model.getCode() == 2)
						{
							for (SpecialTransferPassRecord specialTransferPassRecord : model
									.getSpecialTransferPassRecords())
							{
								if (specialTransferPassRecord
										.getSpeciesTypeID() == 1
										|| specialTransferPassRecord
												.getSpeciesTypeID() == 2)
								{
									if ("1201".equals(specialTransferPassRecord
											.getSpeciesCode()))
									{
										totalChengal = totalChengal
												.add(specialTransferPassRecord
														.getVolume());
									}
									else
									{
										totalDipterokarp = totalDipterokarp
												.add(specialTransferPassRecord
														.getVolume());
									}
								}
								else
								{
									if (specialTransferPassRecord
											.getSpeciesTypeID() == 3)
									{
										totalNonDipterokarp = totalNonDipterokarp
												.add(specialTransferPassRecord
														.getVolume());
									}
								}
							}
						}
					}
				}

				if (rFacade.addFundAndLimit(updateResin, totalDipterokarp,
						updateNonResin, totalNonDipterokarp, updateChengal,
						totalChengal, updateLog,
						totalDipterokarp
								.add(totalNonDipterokarp.add(totalChengal))
								.setScale(2, BigDecimal.ROUND_HALF_UP),
						updateJaras, totalJaras,
						model.getRoyaltyAmount().add(model.getCessAmount())
								.setScale(2, BigDecimal.ROUND_HALF_UP),
						license) != 0)
				{
					license.setWoodWorkFund(license.getWoodWorkFund()
							.add(model.getRoyaltyAmount()
									.add(model.getCessAmount())
									.setScale(2, BigDecimal.ROUND_HALF_UP)));
					if (updateResin == true)
						license.setResinLimit(
								license.getResinLimit().add(totalDipterokarp));
					if (updateNonResin == true)
						license.setNonResinLimit(license.getNonResinLimit()
								.add(totalNonDipterokarp));
					if (updateChengal == true)
						license.setChengalLimit(
								license.getChengalLimit().add(totalChengal));
					if (updateLog == true)
						license.setLogLimit(license.getLogLimit()
								.add(totalDipterokarp
										.add(totalNonDipterokarp
												.add(totalChengal))
										.setScale(2,
												BigDecimal.ROUND_HALF_UP)));
					if (updateJaras == true)
						license.setJarasLimit(
								license.getJarasLimit().add(totalJaras));

					log(hFacade,
							"Kemaskini wang amanah dan had keluaran bagi lesen, ID "
									+ license.getLicenseID());
				}

				log(mFacade,
						"Batal pas memindah, ID " + model.getTransferPassID());
				addMessage(FacesMessage.SEVERITY_INFO, null,
						model + " berjaya dipadamkan.");

				// models.remove(model);
				sort(models);
			}
			else
			{
				addMessage(FacesMessage.SEVERITY_WARN, null,
						model + " tidak berjaya dipadamkan.");
			}
			message = updateMessage(license);

			update(":frmManager:reminder");
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

	public void prepareDownload()
	{

	}

	public StreamedContent download()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();

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
		int monthInt = cal.get(Calendar.MONTH);

		String name = "Laporan_Shuttle_" + month + "_" + year + ".pdf";
		File file = new File(external.getRealPath("/") + "files/hall/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (HallFacade hFacade = new HallFacade())
		{
			ArrayList<String[]> bigSizeLogShuttleReport = hFacade
					.getBigSizeLogShuttleReport((monthInt + 1),
							Integer.parseInt(year));
			ArrayList<String[]> smallSizeLogShuttleReport = hFacade
					.getSmallSizeLogShuttleReport((monthInt + 1),
							Integer.parseInt(year));

			ShuttleReportGenerator.generate(file, bigSizeLogShuttleReport,
					smallSizeLogShuttleReport, month, year);

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

	public StreamedContent downloadTransferPass(TransferPass transferPass)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();

		String name = "PasMemindah_" + transferPass.getTransferPassNo()
				+ ".pdf";
		File file = new File(external.getRealPath("/") + "files/hall/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (HallFacade hFacade = new HallFacade())
		{
			String[] transferPassString = hFacade
					.getTransferPassString(transferPass.getTransferPassID());
			ArrayList<String[]> transferPassRecordStrings = hFacade
					.getTransferPassRecordString(transferPass);

			TransferPassGenerator.generate(file, transferPassString,
					transferPassRecordStrings, transferPass.getCode());

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

	public StreamedContent downloadBukuKawalanPengeluaran(long taggingID)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();

		String name = "BukuKawalanPengeluaran_" + taggingID + ".pdf";
		File file = new File(external.getRealPath("/") + "files/hall/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (TaggingFacade tFacade = new TaggingFacade())
		{
			ArrayList<String[]> currentStatusTaggingRecords = tFacade
					.getCurrentStatusTaggingRecords(taggingID);
			String[] generalInfo = tFacade.getHeaderBukuKawalanPengeluaran(
					taggingID, license.getLicenseID());
			if (generalInfo[0] == null)
			{
				addMessage(FacesMessage.SEVERITY_WARN, null,
						"Tiada Buku Kawalan Pengeluaran bagi sesi penandaan yang dipilih dibawah lesen "
								+ license);
			}
			else
			{
				BukuKawalanPengeluaranGenerator.generate(file, generalInfo,
						currentStatusTaggingRecords, today);

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

		License license = null;
		for (License l : licenses)
		{
			if (l.getLicenseID() == selectedLicenseID)
			{
				license = l;
				break;
			}
		}

		String name = null;
		StreamedContent content = null;
		File file = null;

		try (HallFacade hFacade = new HallFacade())
		{
			if (reportTypeID == 1)
			{
				name = "Ringkasan_Pas_Memindah_Yang_Dikeluarkan_DiBalai_Bagi_"
						+ license.getLicenseNo().replaceAll("/", "_") + "_Dari_"
						+ new SimpleDateFormat("dd_MMM_yyyy").format(startDate)
						+ "_Hingga_"
						+ new SimpleDateFormat("dd_MMM_yyyy").format(endDate)
						+ ".pdf";

				String[] headerInfo = hFacade
						.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenHeaderInfo(
								selectedLicenseID);
				ArrayList<String[]> smallLogs = hFacade
						.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenTypeAPage1andPage2(
								startDate, endDate, selectedLicenseID, 0);
				ArrayList<String[]> bigLogs = hFacade
						.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenTypeAPage1andPage2(
								startDate, endDate, selectedLicenseID, 1);
				ArrayList<String[]> bigJaras = hFacade
						.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenJarasBesar(
								startDate, endDate, selectedLicenseID);
				ArrayList<String[]> smallJaras = hFacade
						.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenJarasKecil(
								startDate, endDate, selectedLicenseID);
				ArrayList<String[]> ringkasanPengeluaranTableBigLog = hFacade
						.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPengeluaranTableBigLog(
								startDate, endDate, selectedLicenseID);
				ArrayList<String[]> ringkasanPengeluaranTableSmallLog = hFacade
						.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPengeluaranTableSmallLog(
								startDate, endDate, selectedLicenseID);
				String[] ringkasanPembayaranTableBigLog = hFacade
						.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPembayaranTableBigLog(
								startDate, endDate, selectedLicenseID);
				String[] ringkasanPembayaranTableSmallLog = hFacade
						.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPembayaranTableSmallLog(
								startDate, endDate, selectedLicenseID);
				String[][] page2 = hFacade
						.getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenDetailTransferPass(
								startDate, endDate, selectedLicenseID);
				String[] infoLain = new String[5];
				infoLain[0] = sdf.format(license.getStartDate()) + " hingga "
						+ sdf.format(startDate);
				infoLain[1] = df
						.format(Double.valueOf(page2[2][page2[2].length - 1]));
				infoLain[2] = "Kayu Balak";
				infoLain[3] = license.getForestName() + ", "
						+ license.getDistrictName() + ".";
				infoLain[4] = user.getName();

				file = new File(
						external.getRealPath("/") + "files/hall/" + name);

				RingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenGenerator
						.generate(file, headerInfo, smallLogs, bigLogs,
								bigJaras, smallJaras,
								ringkasanPengeluaranTableBigLog,
								ringkasanPengeluaranTableSmallLog,
								ringkasanPembayaranTableBigLog,
								ringkasanPembayaranTableSmallLog, page2,
								infoLain, startDate, endDate,
								selectedLicenseID);
			}
			else
			{
				if (reportTypeID == 2)
				{
					name = "Laporan_Pengeluaran_Kayu_Balak_DiBalai_Bagi_"
							+ "_Dari_"
							+ new SimpleDateFormat("dd_MMM_yyyy")
									.format(startDate)
							+ "_Hingga_" + new SimpleDateFormat("dd_MMM_yyyy")
									.format(endDate)
							+ ".pdf";
					ArrayList<String[]> data = hFacade
							.getLaporanPengeluaranKayuBalakDiBalai(startDate,
									endDate, selectedLicenseID);
					String[] information = hFacade
							.getLaporanPengeluaranKayuBalakDiBalaiHeader(
									startDate, endDate, selectedLicenseID);

					file = new File(
							external.getRealPath("/") + "files/hall/" + name);
					LaporanPengeluaranKayuBalakDiBalaiGenerator.generate(file,
							information, data, startDate, endDate);
				}
			}

			file.getParentFile().mkdirs();

			File temp = file;
			content = DefaultStreamedContent.builder()
					.contentType("application/pdf").name(name).stream(() ->
					{
						FileInputStream fis = null;

						try
						{
							fis = new FileInputStream(temp);
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

	public StreamedContent downloadRingkasanPengeluaranHasilHutan()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();

		String name = "Ringkasan_Pengeluaran_Hasil_Hutan_Dari_" + startMonth
				+ "_Hingga_" + endMonth + "_Bagi_" + year + ".pdf";

		License license = null;
		for (License l : licenses)
		{
			if (l.getLicenseID() == selectedLicenseID)
			{
				license = l;
				break;
			}
		}

		String[][] bulan = {{"Januari", "1"}, {"Februari", "2"}, {"Mac", "3"},
				{"April", "4"}, {"Mei", "5"}, {"Jun", "6"}, {"Julai", "7"},
				{"Ogos", "8"}, {"September", "9"}, {"Oktober", "10"},
				{"November", "11"}, {"Disember", "12"}};
		int startMonthInt = 0, endMonthInt = 0;
		for (int i = 0; i < 12; i++)
		{
			if (startMonth.equalsIgnoreCase(bulan[i][0]))
			{
				startMonthInt = Integer.parseInt(bulan[i][1]);
			}
			if (endMonth.equalsIgnoreCase(bulan[i][0]))
			{
				endMonthInt = Integer.parseInt(bulan[i][1]);
			}
		}

		startDate = new GregorianCalendar(Integer.parseInt(year),
				(startMonthInt - 1), 1).getTime();
		endDate = new GregorianCalendar(Integer.parseInt(year),
				(endMonthInt - 1), 1).getTime();

		File file = new File(external.getRealPath("/") + "files/hall/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (HallFacade hFacade = new HallFacade();)
		{
			String[] header = hFacade
					.getHeaderRingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
							license.getLicenseID());
			ArrayList<String[]> contents = hFacade
					.getContentRingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(
							startDate, endDate, license.getLicenseID());

			RingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhirGenerator
					.generate(file, startDate, endDate, header, contents);

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

	public StreamedContent downloadPrerequisites()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();

		String name = "Prasyarat_"
				+ license.getLicenseNo().replaceAll("\\s", "") + ".ctpd";
		File file = new File(external.getRealPath("/") + "files/hall/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				PreFellingFacade pFacade = new PreFellingFacade();
				TaggingFacade tFacade = new TaggingFacade();
				RevenueFacade rFacade = new RevenueFacade();
				HallFacade hFacade = new HallFacade())
		{
			AbstractFacade.group(mFacade, pFacade, tFacade, rFacade, hFacade);

			TreeSet<String> staffIDs = new TreeSet<>();
			TreeSet<String> hammerNos = new TreeSet<>();
			TreeSet<String> tenderNos = new TreeSet<>();
			TreeSet<String> contractorIDs = new TreeSet<>();
			District tempDistrict = district;
			ArrayList<HallOfficer> officers = hallOfficers;

			if (tempDistrict == null)
			{
				tempDistrict = mFacade.getDistrict(license.getHallID());
				officers = mFacade.getHallOfficers(tempDistrict);
			}

			LoggingContractor licensee = rFacade
					.getLoggingContractor(license.getLicenseeID());
			LoggingContractor contractor = rFacade
					.getLoggingContractor(license.getContractorID());
			Receipt licenseReceipt = rFacade.getReceipt(license.getReceiptID());
			Receipt licenseeReceipt = rFacade
					.getReceipt(licensee.getReceiptID());
			Receipt contractorReceipt = contractor != null
					? rFacade.getReceipt(contractor.getReceiptID())
					: null;

			staffIDs.add(tempDistrict.getOfficerID());

			if (tempDistrict.getAsstOfficerID() != null)
				staffIDs.add(tempDistrict.getAsstOfficerID());

			if (tempDistrict.getClerk1ID() != null)
				staffIDs.add(tempDistrict.getClerk1ID());

			if (tempDistrict.getClerk2ID() != null)
				staffIDs.add(tempDistrict.getClerk2ID());

			if (tempDistrict.getClerk3ID() != null)
				staffIDs.add(tempDistrict.getClerk3ID());

			for (Range range : tempDistrict.getRanges())
				staffIDs.add(range.getAsstOfficerID());

			for (HallOfficer officer : officers)
			{
				staffIDs.add(officer.getStaffID());
				hammerNos.add(officer.getHammerNo());
			}

			staffIDs.add(license.getRecorderID());
			staffIDs.add(licensee.getRecorderID());
			staffIDs.add(licenseReceipt.getRecorderID());
			staffIDs.add(licenseeReceipt.getRecorderID());

			if (contractorReceipt != null)
			{
				staffIDs.add(contractorReceipt.getRecorderID());
				staffIDs.add(contractor.getRecorderID());
			}

			int index1 = 0, index2 = 0, index3 = 0, index4 = 0,
					size = taggings.size();
			Staff[] staffs = new Staff[staffIDs.size()];
			Hammer[] hammers = new Hammer[hammerNos.size()];
			PreFellingSurvey[] preFellingSurveys = new PreFellingSurvey[size];
			Tagging[] taggingArray = new Tagging[size];

			for (String staffID : staffIDs)
				staffs[index1++] = mFacade.getStaff(staffID, null);

			for (String hammerNo : hammerNos)
				hammers[index2++] = mFacade.getHammer(hammerNo);

			for (int i = 0; i < size; i++)
			{
				Tagging tagging = taggings.get(i);
				PreFellingSurvey preFellingSurvey = pFacade
						.getPreFellingSurvey(tagging.getPreFellingSurveyID());
				preFellingSurveys[i] = preFellingSurvey;
				taggingArray[i] = tagging;

				tagging.setTaggingForms(tFacade.getTaggingForms(tagging));

				if (preFellingSurvey.getTenderNo() != null)
					tenderNos.add(preFellingSurvey.getTenderNo());

				if (tagging.getTenderNo() != null)
					tenderNos.add(tagging.getTenderNo());
			}

			Tender[] tenders = new Tender[tenderNos.size()];

			for (String tenderNo : tenderNos)
			{
				Tender tender = mFacade.getTender(tenderNo);
				tenders[index3++] = tender;

				contractorIDs.add(tender.getContractorID());
			}

			Contractor[] contractors = new Contractor[contractorIDs.size()];

			for (String contractorID : contractorIDs)
				contractors[index4++] = mFacade.getContractor(contractorID);

			try (ObjectOutputStream oos = new ObjectOutputStream(
					new GZIPOutputStream(new FileOutputStream(file))))
			{
				oos.writeInt(51);
				oos.writeObject(contractors);
				oos.writeObject(tenders);
				oos.writeObject(staffs);
				oos.writeObject(hammers);
				oos.writeObject(tempDistrict);
				oos.writeObject(officers.toArray(new HallOfficer[0]));
				oos.writeObject(preFellingSurveys);
				oos.writeObject(taggingArray);
				oos.writeObject(licenseeReceipt);
				oos.writeObject(licensee);
				oos.writeObject(contractorReceipt);
				oos.writeObject(contractor);
				oos.writeObject(licenseReceipt);
				oos.writeObject(license);
				oos.writeObject(mainRevenueRoyaltyRates
						.toArray(new MainRevenueRoyaltyRate[0]));
				oos.writeObject(smallProductRoyaltyRates
						.toArray(new SmallProductRoyaltyRate[0]));
			}

			content = DefaultStreamedContent.builder()
					.contentType("application/octet-stream").name(name)
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
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		return content;
	}

	public StreamedContent downloadTransferPasses()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();

		String name = "PasMemindah_"
				+ license.getLicenseNo().replaceAll("\\s", "") + ".ctpo";
		File file = new File(external.getRealPath("/") + "files/hall/" + name);
		ArrayList<Log> logs = new ArrayList<>();
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (ObjectOutputStream oos = new ObjectOutputStream(
				new GZIPOutputStream(new FileOutputStream(file)));
				HallFacade facade = new HallFacade())
		{
			for (Tagging tagging : taggings)
				logs.addAll(facade.getLogs(tagging));

			oos.writeInt(52);
			oos.writeObject(logs.toArray(new Log[0]));
			oos.writeObject(models.toArray(new TransferPass[0]));

			for (TransferPass transferPass : models)
			{
				if (transferPass.getStatus().equalsIgnoreCase("A"))
				{
					transferPass.setStatus("P");
					facade.updateTransferPassStatus(transferPass);
				}
			}

			content = DefaultStreamedContent.builder()
					.contentType("application/octet-stream").name(name)
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
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		return content;
	}

	public void uploadPrerequisites(FileUploadEvent event)
	{
		UploadedFile file = event.getFile();

		if (file != null)
		{
			try (ObjectInputStream ois = new ObjectInputStream(
					new GZIPInputStream(file.getInputStream()));
					MaintenanceFacade mFacade = new MaintenanceFacade();
					PreFellingFacade pFacade = new PreFellingFacade();
					TaggingFacade tFacade = new TaggingFacade();
					RevenueFacade rFacade = new RevenueFacade();
					HallFacade hFacade = new HallFacade())
			{
				AbstractFacade.group(mFacade, pFacade, tFacade, rFacade,
						hFacade);

				if (ois.readInt() != 51)
					throw new InvalidClassException(
							"Not TransferPass dependencies");

				Contractor[] contractors = (Contractor[]) ois.readObject();
				Tender[] tenders = (Tender[]) ois.readObject();
				Staff[] staffs = (Staff[]) ois.readObject();
				Hammer[] hammers = (Hammer[]) ois.readObject();
				District district = (District) ois.readObject();
				HallOfficer[] hallOfficers = (HallOfficer[]) ois.readObject();
				PreFellingSurvey[] preFellingSurveys = (PreFellingSurvey[]) ois
						.readObject();
				Tagging[] taggings = (Tagging[]) ois.readObject();
				Receipt licenseeReceipt = (Receipt) ois.readObject();
				LoggingContractor licensee = (LoggingContractor) ois
						.readObject();
				Receipt contractorReceipt = (Receipt) ois.readObject();
				LoggingContractor contractor = (LoggingContractor) ois
						.readObject();
				Receipt licenseReceipt = (Receipt) ois.readObject();
				License license = (License) ois.readObject();
				MainRevenueRoyaltyRate[] mainRevenueRoyaltyRates = (MainRevenueRoyaltyRate[]) ois
						.readObject();
				SmallProductRoyaltyRate[] smallProductRoyaltyRates = (SmallProductRoyaltyRate[]) ois
						.readObject();

				for (Contractor c : contractors)
					if (mFacade.addContractor(c) != 0)
						log(mFacade,
								"Tambah kontraktor, ID " + c.getContractorID());

				for (Tender tender : tenders)
					if (mFacade.addTender(tender) != 0)
						log(mFacade, "Tambah sebut harga, ID "
								+ tender.getTenderNo());

				for (Staff staff : staffs)
				{
					if (staff != null)
					{
						if (mFacade.addStaff(staff) != 0)
							log(mFacade, "Tambah pekerja dan akses, ID "
									+ staff.getStaffID());
						else if (mFacade.updateStaff(staff, false) != 0)
							log(mFacade, "Kemaskini pekerja dan akses, ID "
									+ staff.getStaffID());
					}
				}

				for (Hammer hammer : hammers)
					if (mFacade.addHammer(hammer) != 0)
						log(mFacade,
								"Tambah tukul, ID " + hammer.getHammerNo());

				if (mFacade.getDistrict(district.getDistrictID()) == null)
				{
					district.setDistrictID(0);

					if (mFacade.addDistrict(district) != 0)
					{
						ArrayList<Range> ranges = district.getRanges();
						ArrayList<Hall> halls = district.getHalls();

						for (Range range : ranges)
						{
							range.setDistrictID(district.getDistrictID());
							mFacade.addRange(range);
						}

						for (Hall hall : halls)
						{
							hall.setDistrictID(district.getDistrictID());
							mFacade.addHall(hall, false);
						}
					}
				}
				else
				{
					if (mFacade.updateDistrict(district) != 0)
					{
						ArrayList<Range> ranges = district.getRanges();
						ArrayList<Hall> halls = district.getHalls();

						for (Range range : ranges)
						{
							if (mFacade.getRange(range.getRangeID()) == null)
							{
								range.setRangeID(0);
								mFacade.addRange(range);
							}
							else
								mFacade.updateRange(range);
						}

						for (Hall hall : halls)
							mFacade.addHall(hall, false);
					}
				}

				for (HallOfficer hallOfficer : hallOfficers)
				{
					if (mFacade.addHallOfficer(hallOfficer) != 0)
					{
						this.hallOfficers.add(hallOfficer);
						log(mFacade, "Tambah pegawai balai, ID "
								+ hallOfficer.getHallOfficerID());
					}
				}

				for (PreFellingSurvey preFellingSurvey : preFellingSurveys)
					if (pFacade.addPreFellingSurvey(preFellingSurvey,
							false) != 0)
						log(pFacade, "Tambah bancian, ID "
								+ preFellingSurvey.getPreFellingSurveyID());

				for (Tagging tagging : taggings)
				{
					if (tFacade.addTagging(tagging, false) != 0)
					{
						this.taggings.add(tagging);
						log(tFacade, "Tambah penandaan, ID "
								+ tagging.getTaggingID());

						ArrayList<TaggingForm> taggingForms = tagging
								.getTaggingForms();

						for (TaggingForm taggingForm : taggingForms)
						{
							if (tFacade.addTaggingForm(taggingForm, false) != 0)
							{
								log(tFacade, "Tambah borang penandaan, ID "
										+ taggingForm.getTaggingFormID());
								ArrayList<TaggingRecord> taggingRecords = taggingForm
										.getTaggingRecords();

								for (TaggingRecord taggingRecord : taggingRecords)
								{
									if (tFacade.addTaggingRecord(taggingRecord,
											false) != 0)
										log(tFacade,
												"Tambah rekod penandaan, ID "
														+ taggingRecord
																.getTaggingRecordID());
								}
							}
						}
					}
				}

				if (rFacade.addReceipt(licenseeReceipt) != 0)
					log(rFacade, "Tambah resit, ID "
							+ licenseeReceipt.getReceiptID());

				if (rFacade.addLoggingContractor(licensee) != 0)
				{
					log(rFacade, "Tambah kontraktor pembangunan hutan, ID "
							+ licensee.getLoggingContractorID());
					ArrayList<Renew> renews = licensee.getRenews();

					for (Renew renew : renews)
						if (rFacade.addRenew(renew) != 0)
							log(rFacade,
									"Tambah renew, ID " + renew.getRenewID());
				}

				if (contractorReceipt != null
						&& rFacade.addReceipt(contractorReceipt) != 0)
					log(rFacade, "Tambah resit, ID "
							+ contractorReceipt.getReceiptID());

				if (contractor != null
						&& rFacade.addLoggingContractor(contractor) != 0)
				{
					log(rFacade, "Tambah kontraktor pembangunan hutan, ID "
							+ contractor.getLoggingContractorID());
					ArrayList<Renew> renews = contractor.getRenews();

					for (Renew renew : renews)
						if (rFacade.addRenew(renew) != 0)
							log(rFacade,
									"Tambah renew, ID " + renew.getRenewID());
				}

				if (rFacade.addReceipt(licenseReceipt) != 0)
					log(rFacade, "Tambah resit, ID "
							+ licenseReceipt.getReceiptID());

				if (license != null && rFacade.addLicense(license, false) != 0)
				{
					licenses.add(license);
					log(rFacade, "Tambah lesen, ID " + license.getLicenseID());

					ArrayList<Renew> renews = license.getRenews();

					for (Renew renew : renews)
					{
						if (rFacade.addRenew(renew) != 0)
							log(rFacade,
									"Tambah renew, ID " + renew.getRenewID());

						if (renew.getEndDate().after(license.getEndDate()))
							license.setEndDate(renew.getEndDate());
					}

					Calendar calendar = Calendar.getInstance();

					calendar.add(Calendar.MONTH, 1);

					if (license.getEndDate().compareTo(calendar.getTime()) <= 0)
						license.setStatus("W");
				}

				ArrayList<MainRevenueRoyaltyRate> deletablesMR = new ArrayList<>();
				ArrayList<SmallProductRoyaltyRate> deletablesSP = new ArrayList<>();

				for (MainRevenueRoyaltyRate mainRevenueRoyaltyRate : mainRevenueRoyaltyRates)
				{
					if (!this.mainRevenueRoyaltyRates
							.contains(mainRevenueRoyaltyRate)
							&& mFacade.addMainRevenueRoyaltyRate(
									mainRevenueRoyaltyRate) != 0)
					{
						for (MainRevenueRoyaltyRate mrrr : this.mainRevenueRoyaltyRates)
							if (mainRevenueRoyaltyRate.getStateID() == mrrr
									.getStateID()
									&& mainRevenueRoyaltyRate
											.getSpeciesID() == mrrr
													.getSpeciesID())
								deletablesMR.add(mrrr);

						this.mainRevenueRoyaltyRates
								.add(mainRevenueRoyaltyRate);
						log(mFacade,
								"Tambah kadar royalti hasil utama, ID "
										+ mainRevenueRoyaltyRate
												.getMainRevenueRoyaltyRateID());
					}
				}

				for (SmallProductRoyaltyRate smallProductRoyaltyRate : smallProductRoyaltyRates)
				{
					if (!this.smallProductRoyaltyRates
							.contains(smallProductRoyaltyRate)
							&& mFacade.addSmallProductRoyaltyRate(
									smallProductRoyaltyRate) != 0)
					{
						for (SmallProductRoyaltyRate sprr : this.smallProductRoyaltyRates)
							if (smallProductRoyaltyRate
									.getSmallProductID() == sprr
											.getSmallProductID()
									&& smallProductRoyaltyRate
											.getUnitID() == sprr.getUnitID())
								deletablesSP.add(sprr);

						this.smallProductRoyaltyRates
								.add(smallProductRoyaltyRate);
						log(mFacade, "Tambah kadar royalti keluaran kecil, ID "
								+ smallProductRoyaltyRate
										.getSmallProductRoyaltyRateID());

						boolean contains = false;

						for (SmallProduct sp : smallProducts)
						{
							if (sp.getSmallProductID() == smallProductRoyaltyRate
									.getSmallProductID())
							{
								contains = true;
								break;
							}
						}

						if (!contains)
							smallProducts.add(mFacade
									.getSmallProduct(smallProductRoyaltyRate
											.getSmallProductID()));
					}
				}

				this.mainRevenueRoyaltyRates.removeAll(deletablesMR);
				this.smallProductRoyaltyRates.removeAll(deletablesSP);

				sort(this.taggings);
				sort(this.hallOfficers);
				sort(this.licenses);
				sort(this.mainRevenueRoyaltyRates);
				sort(this.smallProductRoyaltyRates);
				sort(smallProducts);

				if (license != null && license.equals(this.license))
				{
					this.license = license;
					message = updateMessage(license);
				}

				addMessage(FacesMessage.SEVERITY_INFO, null,
						"Prasyarat pas memindah berjaya dimuat naik.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}

	public void uploadTransferPasses(FileUploadEvent event)
	{
		UploadedFile file = event.getFile();

		if (file != null)
		{
			try (ObjectInputStream ois = new ObjectInputStream(
					new GZIPInputStream(file.getInputStream()));
					TaggingFacade tFacade = new TaggingFacade();
					RevenueFacade rFacade = new RevenueFacade();
					HallFacade hFacade = new HallFacade())
			{
				AbstractFacade.group(tFacade, rFacade, hFacade);

				if (ois.readInt() != 52)
					throw new InvalidClassException("Not TransferPasses");

				Log[] logs = (Log[]) ois.readObject();
				TransferPass[] transferPasses = (TransferPass[]) ois
						.readObject();
				LinkedHashMap<Long, Log> map = new LinkedHashMap<>();
				TreeSet<Long> taggingRecordIDs = new TreeSet<>();
				BigDecimal totalDipterokarp = BigDecimal.ZERO;
				BigDecimal totalNonDipterokarp = BigDecimal.ZERO;
				BigDecimal totalChengal = BigDecimal.ZERO;

				if (this.logs == null)
					this.logs = new ArrayList<>();

				for (Log log : logs)
				{
					if (hFacade.addLog(log, false) != 0)
					{
						int index = this.logs.indexOf(log);

						if (index == -1)
							this.logs.add(log);
						else
							this.logs.set(index, log);
					}

					map.put(log.getLogID(), log);
					taggingRecordIDs.add(log.getTaggingRecordID());
				}

				if (this.logs.isEmpty())
					this.logs = null;

				for (Long taggingRecordID : taggingRecordIDs)
				{
					TaggingRecord taggingRecord = new TaggingRecord();

					taggingRecord.setTaggingRecordID(taggingRecordID);
					taggingRecord.setStatus("P");

					if (tFacade.updateTaggingRecordStatus(taggingRecord) != 0)
						log(tFacade, "Kemaskini rekod penandaan, ID "
								+ taggingRecordID);
				}

				int countPasses = 0, countMainRevenues = 0,
						countSmallProduct = 0, countSpecial = 0;

				for (TransferPass transferPass : transferPasses)
				{
					transferPass.setJournalID(0);

					int status = hFacade.addTransferPass(transferPass, false),
							index = models.indexOf(transferPass),
							code = transferPass.getCode();

					if (status != 0)
					{
						countPasses++;

						if (status == 1)
							log(hFacade, "Tambah pas memindah, ID "
									+ transferPass.getTransferPassID());
						else if (status == 2)
							log(hFacade, "Kemaskini pas memindah, ID "
									+ transferPass.getTransferPassID());
					}

					if (index == -1)
						models.add(transferPass);
					else
						models.set(index, transferPass);

					if (code == 0)
					{
						ArrayList<MainRevenueTransferPassRecord> mainRevenueTransferPassRecords = transferPass
								.getMainRevenueTransferPassRecords();
						sort(mainRevenueTransferPassRecords);

						for (MainRevenueTransferPassRecord mainRevenueTransferPassRecord : mainRevenueTransferPassRecords)
						{
							if (hFacade.addMainRevenueTransferPassRecord(
									mainRevenueTransferPassRecord) != 0)
							{
								countMainRevenues++;
								long mainRevenueTransferPassRecordID = mainRevenueTransferPassRecord
										.getMainRevenueTransferPassRecordID(),
										logID = mainRevenueTransferPassRecord
												.getLogID();
								Log log = map.get(logID);

								log(hFacade,
										"Tambah rekod pas memindah hasil utama, ID "
												+ mainRevenueTransferPassRecordID);
								log.setStatus("P");

								if (log.getSpeciesTypeID() == 1
										|| log.getSpeciesTypeID() == 2)
								{
									if ("1201".equals(log.getSpeciesCode()))
										totalChengal = totalChengal
												.add(log.getNetVolume());
									else
										totalDipterokarp = totalDipterokarp
												.add(log.getNetVolume());
								}
								else if (log.getSpeciesTypeID() == 3)
									totalNonDipterokarp = totalNonDipterokarp
											.add(log.getNetVolume());

								if (hFacade.updateLogStatus(log) != 0)
									log(hFacade,
											"Kemaskini status tual balak, ID "
													+ mainRevenueTransferPassRecordID);
							}
						}
					}
					else if (code == 1)
					{
						ArrayList<SmallProductTransferPassRecord> smallProductTransferPassRecords = transferPass
								.getSmallProductTransferPassRecords();
						sort(smallProductTransferPassRecords);

						for (SmallProductTransferPassRecord smallProductTransferPassRecord : smallProductTransferPassRecords)
						{
							if (hFacade.addSmallProductTransferPassRecord(
									smallProductTransferPassRecord) != 0)
							{
								countSmallProduct++;
								log(hFacade,
										"Tambah rekod pas memindah keluaran kecil, ID "
												+ smallProductTransferPassRecord
														.getSmallProductTransferPassRecordID());
							}
						}
					}
					else if (code == 2)
					{
						ArrayList<SpecialTransferPassRecord> specialTransferPassRecords = transferPass
								.getSpecialTransferPassRecords();
						sort(specialTransferPassRecords);

						for (SpecialTransferPassRecord specialTransferPassRecord : specialTransferPassRecords)
						{
							if (hFacade.addSpecialTransferPassRecord(
									specialTransferPassRecord) != 0)
							{
								countSpecial++;
								log(hFacade,
										"Tambah rekod pas memindah projek, ID "
												+ specialTransferPassRecord
														.getSpecialTransferPassRecordID());

								if (specialTransferPassRecord
										.getSpeciesTypeID() == 1
										|| specialTransferPassRecord
												.getSpeciesTypeID() == 2)
								{
									if ("1201".equals(specialTransferPassRecord
											.getSpeciesCode()))
										totalChengal = totalChengal
												.add(specialTransferPassRecord
														.getVolume());
									else
										totalDipterokarp = totalDipterokarp
												.add(specialTransferPassRecord
														.getVolume());
								}
								else if (specialTransferPassRecord
										.getSpeciesTypeID() == 3)
									totalNonDipterokarp = totalNonDipterokarp
											.add(specialTransferPassRecord
													.getVolume());
							}
						}
					}
				}

				if (countPasses + countMainRevenues + countSpecial != 0
						&& rFacade.updateLimit(totalDipterokarp,
								totalNonDipterokarp, totalChengal,
								license) != 0)
					log(hFacade, "Kemaskini had keluaran damar, ID "
							+ license.getLicenseID());

				if (this.logs != null)
					sort(this.logs);

				sort(models);
				addMessage(FacesMessage.SEVERITY_INFO, null,
						"Sebanyak " + countPasses + " pas memindah, "
								+ countMainRevenues
								+ " rekod pas memindah hasil utama, "
								+ countSmallProduct
								+ " rekod pas memindah keluaran kecil, dan "
								+ countSpecial
								+ " pas memindah projek berjaya dimuat naik.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addMessage(e);
			}
		}
	}
}