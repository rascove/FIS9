package my.edu.utem.ftmk.fis9.revenue.controller.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.global.controller.mbean.AbstractManagedBean;
import my.edu.utem.ftmk.fis9.hall.controller.manager.HallFacade;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionForm;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionFormMapTrustFund;
import my.edu.utem.ftmk.fis9.maintenance.model.TrustFund;
import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;
import my.edu.utem.ftmk.fis9.revenue.model.License;
import my.edu.utem.ftmk.fis9.revenue.model.Permit;
import my.edu.utem.ftmk.fis9.revenue.model.Voucher;
import my.edu.utem.ftmk.fis9.revenue.model.VoucherRecord;
import my.edu.utem.ftmk.fis9.revenue.util.BaucarGenerator;
import my.edu.utem.ftmk.fis9.revenue.util.LaporanKedudukanKewanganLesen1Generator;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "voucherMBean")
public class VoucherManagedBean extends AbstractManagedBean<Voucher>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<VoucherRecord> voucherRecords;
	private ArrayList<TrustFund> trustFunds;
	private TransactionForm tempTransactionForm;
	private ArrayList<TransactionFormMapTrustFund> transactionFormMapTrustFunds;
	private ArrayList<License> licenses;
	private ArrayList<Permit> permits;
	private License currentLicense;
	private Permit currentPermit;
	private int category;
	private String transactionFormName;
	private BigDecimal royaltyAmount;
	private BigDecimal cessAmount;
	private int noOfVots;
	private long selectedLicenseID;
	private Staff user;

	public VoucherManagedBean()
	{
		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				RevenueFacade rFacade = new RevenueFacade();
				HallFacade hFacade = new HallFacade())
		{
			AbstractFacade.group(mFacade, rFacade);

			user = getCurrentUser();
			String staffID = user.getStaffID();
			int stateID = user.getStateID(),
					designationID = user.getDesignationID();

			trustFunds = mFacade.getTrustFunds("A");

			if (stateID == 0)
			{
				licenses = rFacade.getLicenses("A");
				permits = rFacade.getPermits("A");
				if (designationID == 0)
				{
					models = rFacade.getVouchers("A");
				}
				else
				{
					models = rFacade.getVouchers(user);
				}
			}
			else
			{
				State state = mFacade.getState(stateID);
				licenses = rFacade.getLicenses(state, "A");
				permits = rFacade.getPermits(state, "A");
				if (state.getDirectorID().equals(staffID))
				{
					models = rFacade.getVouchers(state);
				}
				else
				{
					
					models = rFacade.getVouchers(user);
				}
			}

			if (models != null)
			{
				sort(models);
			}
			else
			{
				models = new ArrayList<>();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public Staff getUser()
	{
		return user;
	}

	public void setUser(Staff user)
	{
		this.user = user;
	}

	public Voucher getVoucher()
	{
		return model;
	}

	public void setVoucher(Voucher voucher)
	{
		this.model = voucher;
	}

	public ArrayList<Voucher> getVouchers()
	{
		return models;
	}

	public void setVouchers(ArrayList<Voucher> vouchers)
	{
		this.models = vouchers;
	}

	public ArrayList<VoucherRecord> getVoucherRecords()
	{
		return voucherRecords;
	}

	public void setVoucherRecords(ArrayList<VoucherRecord> voucherRecords)
	{
		this.voucherRecords = voucherRecords;
	}

	public ArrayList<License> getLicenses()
	{
		return licenses;
	}

	public void setLicenses(ArrayList<License> licenses)
	{
		this.licenses = licenses;
	}

	public ArrayList<Permit> getPermits()
	{
		return permits;
	}

	public void setPermits(ArrayList<Permit> permits)
	{
		this.permits = permits;
	}

	public ArrayList<TransactionFormMapTrustFund> getTransactionFormMapTrustFunds()
	{
		return transactionFormMapTrustFunds;
	}

	public void setTransactionFormMapTrustFunds(
			ArrayList<TransactionFormMapTrustFund> transactionFormMapTrustFunds)
	{
		this.transactionFormMapTrustFunds = transactionFormMapTrustFunds;
	}

	public ArrayList<TrustFund> getTrustFunds()
	{
		return trustFunds;
	}

	public void setTrustFunds(ArrayList<TrustFund> trustFunds)
	{
		this.trustFunds = trustFunds;
	}

	public TransactionForm getTempTransactionForm()
	{
		return tempTransactionForm;
	}

	public void setTempTransactionForm(TransactionForm tempTransactionForm)
	{
		this.tempTransactionForm = tempTransactionForm;
	}

	public BigDecimal getRoyaltyAmount()
	{
		return royaltyAmount;
	}

	public void setRoyaltyAmount(BigDecimal royaltyAmount)
	{
		this.royaltyAmount = royaltyAmount;
	}

	public BigDecimal getCessAmount()
	{
		return cessAmount;
	}

	public void setCessAmount(BigDecimal cessAmount)
	{
		this.cessAmount = cessAmount;
	}

	public int getCategory()
	{
		return category;
	}

	public void setCategory(int category)
	{
		this.category = category;
	}

	public String getTransactionFormName()
	{
		return transactionFormName;
	}

	public void setTransactionFormName(String transactionFormName)
	{
		this.transactionFormName = transactionFormName;
	}

	public License getCurrentLicense()
	{
		return currentLicense;
	}

	public void setCurrentLicense(License currentLicense)
	{
		this.currentLicense = currentLicense;
	}

	public int getNoOfVots()
	{
		return noOfVots;
	}

	public void setNoOfVots(int noOfVots)
	{
		this.noOfVots = noOfVots;
	}

	public long getSelectedLicenseID()
	{
		return selectedLicenseID;
	}

	public void setSelectedLicenseID(long selectedLicenseID)
	{
		this.selectedLicenseID = selectedLicenseID;
	}

	public String getComponent()
	{
		return ":frmManager:table" + (model == null ? ""
				: ":" + models.indexOf(model) + ":subtable");
	}

	public Permit getCurrentPermit()
	{
		return currentPermit;
	}

	public void setCurrentPermit(Permit currentPermit)
	{
		this.currentPermit = currentPermit;
	}

	@Override
	public void handleOpen()
	{
		model = new Voucher();
		Date date = new Date();
		
		model.setCategory(category);
		model.setDate(date);
		model.setRecorderID(user.getStaffID());
		model.setRecorderName(user.getName());
		model.setTotal(BigDecimal.ZERO);
		model.setVoucherRecords(new ArrayList<VoucherRecord>());
		model.setStatus("A");

		try (MaintenanceFacade mFacade = new MaintenanceFacade())
		{
			tempTransactionForm = new TransactionForm();
			tempTransactionForm.setName(transactionFormName);
			tempTransactionForm = mFacade
					.getTransactionFormByName(tempTransactionForm);
			transactionFormMapTrustFunds = mFacade
					.getTransactionFormMapTrustFunds(tempTransactionForm, "A");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

	}

	public void handleOpenVoucherRecords()
	{
		voucherRecords = new ArrayList<VoucherRecord>();
		String comments = "";
		if (model.getCategory() == 0)
		{
			for(Permit permit : permits)
			{
				if(permit.getLicenseID() == model.getLicenseID())
				{
					if(!comments.equalsIgnoreCase("")) comments = comments + ", ";
					comments = comments + permit.getPermitNo();
				}
			}
			if(!comments.equalsIgnoreCase("")) 
			{
				addMessage(FacesMessage.SEVERITY_WARN, null,
						model + " tidak berjaya ditutup kerana terdapat permit " + comments + " masih aktif.");
				execute("PF('popup').hide()");
			}
			else
			{
				for (License license : licenses)
				{
					if (license.getLicenseID() == model.getLicenseID())
					{
						currentLicense = license;
						model.setLicenseNo(license.getLicenseNo());
						model.setLicenseeCompanyName(license.getLicenseeCompanyName());
						
						VoucherRecord voucherRecordWAKK = new VoucherRecord();
						VoucherRecord voucherRecordWAL = new VoucherRecord();
	
						for (TrustFund trustFund : trustFunds)
						{
							if (trustFund.getSymbol()
									.equalsIgnoreCase("WAKK"))
							{
								voucherRecordWAKK
										.setTrustFundID(trustFund.getTrustFundID());
								voucherRecordWAKK.setTrustFundDepartmentVotID(
										trustFund.getDepartmentVotID());
								voucherRecordWAKK.setTrustFundDepartmentVotCode(
										trustFund.getDepartmentVotCode());
								voucherRecordWAKK.setTrustFundDepartmentVotName(
										trustFund.getDepartmentVotName());
								voucherRecordWAKK.setTrustFundBursaryVotID(
										trustFund.getBursaryVotID());
								voucherRecordWAKK.setTrustFundBursaryVotCode(
										trustFund.getBursaryVotCode());
								voucherRecordWAKK.setTrustFundBursaryVotName(
										trustFund.getBursaryVotName());							
								voucherRecordWAKK.setTrustFundDescription(
										trustFund.getDescription());
								voucherRecordWAKK
										.setTotal(license.getWoodWorkFund());
								voucherRecords.add(voucherRecordWAKK);
							}
							if (trustFund.getSymbol()
									.equalsIgnoreCase("WAL"))
							{
								voucherRecordWAL
										.setTrustFundID(trustFund.getTrustFundID());
								voucherRecordWAL.setTrustFundDepartmentVotID(
										trustFund.getDepartmentVotID());
								voucherRecordWAL.setTrustFundDepartmentVotCode(
										trustFund.getDepartmentVotCode());
								voucherRecordWAL.setTrustFundDepartmentVotName(
										trustFund.getDepartmentVotName());
								voucherRecordWAL.setTrustFundBursaryVotID(
										trustFund.getBursaryVotID());
								voucherRecordWAL.setTrustFundBursaryVotCode(
										trustFund.getBursaryVotCode());
								voucherRecordWAL.setTrustFundBursaryVotName(
										trustFund.getBursaryVotName());								
								voucherRecordWAL.setTrustFundDescription(
										trustFund.getDescription());
								voucherRecordWAL.setTotal(license.getLicenseFund());
								voucherRecords.add(voucherRecordWAL);
							}
						}
						break;
					}
				}
			}
		}
		else
		{
			if (model.getCategory() == 1)
			{
				for (Permit permit : permits)
				{
					if (permit.getPermitID() == model.getPermitID())
					{
						currentPermit = permit;
						model.setPermitNo(permit.getPermitNo());
						model.setLicenseeCompanyName(permit.getLicenseeCompanyName());
						
						VoucherRecord voucherRecord = new VoucherRecord();

						for (TrustFund trustFund : trustFunds)
						{
							if (trustFund.getSymbol()
									.equalsIgnoreCase("WAJ")
									|| trustFund.getSymbol()
											.equalsIgnoreCase(
													"WAKO") || trustFund.getSymbol()
											.equalsIgnoreCase(
													"WAM"))
							{
								voucherRecord.setTrustFundID(
										trustFund.getTrustFundID());
								voucherRecord.setTrustFundDepartmentVotID(
										trustFund.getDepartmentVotID());
								voucherRecord.setTrustFundDepartmentVotCode(
										trustFund.getDepartmentVotCode());
								voucherRecord.setTrustFundDepartmentVotName(
										trustFund.getDepartmentVotName());
								voucherRecord.setTrustFundBursaryVotID(
										trustFund.getBursaryVotID());
								voucherRecord.setTrustFundBursaryVotCode(
										trustFund.getBursaryVotCode());
								voucherRecord.setTrustFundBursaryVotName(
										trustFund.getBursaryVotName());									
								voucherRecord.setTrustFundDescription(
										trustFund.getDescription());
								voucherRecord
										.setTotal(permit.getPermitFund());
								voucherRecords.add(voucherRecord);
								break;
							}
						}
						break;
					}
				}
			}
		}
		
		if(comments.equalsIgnoreCase("")) 
		{
			execute("PF('popup').hide()");
			update("frmEntryVoucherRecord");
			execute("PF('popupVoucherRecord').show()");
		}
	}

	public void handleTrustFundIDChange(VoucherRecord voucherRecord)
	{
		
	}

	public void voucherEntry()
	{
		try (RevenueFacade rFacade = new RevenueFacade();
				HallFacade hFacade = new HallFacade())
		{
			AbstractFacade.group(rFacade, hFacade);

			int count = 0;

			model.setVoucherID(System.currentTimeMillis());
			model.setVoucherNo(model.getVoucherNo().toUpperCase());
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			model.setRecordTime(timestamp);

			BigDecimal total = BigDecimal.ZERO;
			for (VoucherRecord voucherRecord : voucherRecords)
			{
				if (voucherRecord.getTrustFundID() != 0)
				{
					total = total.add(voucherRecord.getTotal());
				}
			}
			model.setTotal(total);
			
			if (rFacade.addVoucher(model) != 0)
			{
				addMessage(FacesMessage.SEVERITY_INFO, null,
						model + " berjaya ditambahkan.");
				log(rFacade, "Tambah baucar, ID " + model.getVoucherID());
				models.add(model);
				sort(models);

				long voucherRecordID = System.currentTimeMillis();
				for (VoucherRecord voucherRecord : voucherRecords)
				{
					if (voucherRecord.getTrustFundID() != 0)
					{
						voucherRecord.setVoucherRecordID(voucherRecordID);
						voucherRecord.setVoucherID(model.getVoucherID());
						
						if (rFacade.addVoucherRecord(voucherRecord) != 0)
						{
							count++;
							model.getVoucherRecords().add(voucherRecord);
							log(rFacade, "Tambah rekod baucar, ID "
									+ voucherRecord.getVoucherRecordID());
						}
					}
					voucherRecordID++;
				}
				
				if (model.getCategory() == 0)
				{
					currentLicense.setWoodWorkFund(BigDecimal.ZERO);
					currentLicense.setLicenseFund(BigDecimal.ZERO);
					currentLicense.setStatus("I");
					rFacade.closeLicense(currentLicense);
					log(rFacade, "Kemaskini lesen, ID "
							+ currentLicense.getLicenseID());
					licenses.remove(currentLicense);
					sort(licenses);
				}
				else
				{
					if (model.getCategory() == 1)
					{
						currentPermit.setPermitFund(BigDecimal.ZERO);
						currentPermit.setStatus("I");
						rFacade.closePermit(currentPermit);
						log(rFacade, "Kemaskini permit, ID "
								+ currentPermit.getPermitID());
						permits.remove(currentPermit);
						sort(permits);
					}
				}

				voucherRecords = null;
				sort(model.getVoucherRecords());

				addMessage(FacesMessage.SEVERITY_INFO, null, "Sebanyak " + count
						+ " rekod baucar berjaya ditambahkan.");
			}
			else
			{
				addMessage(FacesMessage.SEVERITY_WARN, null, model
						+ " tidak dapat ditambahkan kerana no. baucar telah direkodkan sebelumnya.");
			}
			execute("PF('popupVoucherRecord').hide()");
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public StreamedContent download(Voucher voucherTemp)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		
		String name = "Baucar_" + voucherTemp.getVoucherNo() + ".pdf";
		File file = new File(
				external.getRealPath("/") + "files/revenue/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (RevenueFacade rFacade = new RevenueFacade())
		{
			String[] voucher = rFacade.getVoucherHeader(voucherTemp.getVoucherID(), voucherTemp.getCategory());
			ArrayList<String[]> voucherRecords = rFacade
					.getVoucherRecords(voucherTemp.getVoucherID());

			BaucarGenerator.generate(file, voucher, voucherRecords);

			content = new DefaultStreamedContent(new FileInputStream(file),
					"application/pdf", name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		return content;
	}

	public StreamedContent downloadLaporanKedudukanKewanganLesen()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();

		Date currentDate = new Date();

		License license = null;
		for (License l : licenses)
		{
			if (l.getLicenseID() == selectedLicenseID)
			{
				license = l;
				break;
			}
		}

		String name = "Laporan_Kedudukan_Kewangan_Lesen_"
				+ license.getLicenseNo() + "_"
				+ new SimpleDateFormat("dd_MMM_yyyy").format(currentDate)
				+ ".pdf";
		;

		File file = new File(
				external.getRealPath("/") + "files/revenue/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (RevenueFacade rFacade = new RevenueFacade();)
		{

			String[] data = rFacade
					.getLaporanKedudukanKewanganLesen(selectedLicenseID);

			LaporanKedudukanKewanganLesen1Generator.generate(file, data);

			content = new DefaultStreamedContent(new FileInputStream(file),
					"application/pdf", name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addMessage(e);
		}

		return content;
	}
}