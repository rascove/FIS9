package my.edu.utem.ftmk.fis9.revenue.controller.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import my.edu.utem.ftmk.fis9.hall.model.TransferPass;
import my.edu.utem.ftmk.fis9.maintenance.controller.manager.MaintenanceFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.DepartmentVot;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionForm;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionFormMapDepartmentVot;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionFormMapTrustFund;
import my.edu.utem.ftmk.fis9.maintenance.model.TrustFund;
import my.edu.utem.ftmk.fis9.revenue.controller.manager.RevenueFacade;
import my.edu.utem.ftmk.fis9.revenue.model.Journal;
import my.edu.utem.ftmk.fis9.revenue.model.JournalRecord;
import my.edu.utem.ftmk.fis9.revenue.model.License;
import my.edu.utem.ftmk.fis9.revenue.model.Permit;
import my.edu.utem.ftmk.fis9.revenue.model.Revenue;
import my.edu.utem.ftmk.fis9.revenue.model.Transaction;
import my.edu.utem.ftmk.fis9.revenue.util.JurnalGenerator;

/**
 * @author Nor Azman Mat Ariff
 */
@ViewScoped
@ManagedBean(name = "journalMBean")
public class JournalManagedBean extends AbstractManagedBean<Journal>
{
	private static final long serialVersionUID = VERSION;
	private ArrayList<TransferPass> transferPasses;
	private ArrayList<TransferPass> allTransferPasses;
	private ArrayList<TransferPass> selectedTransferPasses;
	private ArrayList<JournalRecord> journalRecords;
	private ArrayList<JournalRecord> selectedJournalRecords;
	private ArrayList<DepartmentVot> departmentVots;
	private ArrayList<TrustFund> trustFunds;
	private String transactionFormName;
	private TransactionForm transactionForm;
	private ArrayList<TransactionFormMapDepartmentVot> transactionFormMapDepartmentVots;
	private ArrayList<TransactionFormMapTrustFund> transactionFormMapTrustFunds;
	private ArrayList<License> licenses;
	private ArrayList<Permit> permits;
	private License currentLicense;
	private Permit currentPermit;
	private BigDecimal royaltyAmount;
	private BigDecimal cessAmount;
	private int category;
	private Staff user;

	public JournalManagedBean()
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

			ArrayList<License> tempLicenses = new ArrayList<License>();

			if (stateID == 0)
			{
				licenses = rFacade.getLicenses("A");
				permits = rFacade.getPermits("A");
				allTransferPasses = hFacade.getTransferPasses("A");

				models = rFacade.getJournals("A");
			}
			else
			{
				State state = mFacade.getState(stateID);

				licenses = rFacade.getLicenses(state, "A");
				permits = rFacade.getPermits(state, "A");
				allTransferPasses = hFacade.getTransferPasses(state, "A");

				if (state.getDirectorID().equals(staffID)
						|| designationID > 24 && designationID < 28)
				{
					models = rFacade.getJournals(state);
				}
				else
				{
					models = rFacade.getJournals(user);
				}
			}

			for (License license : licenses)
			{
				for (TransferPass transferPass : allTransferPasses)
				{
					if (transferPass.getLicenseID() == license.getLicenseID())
					{
						tempLicenses.add(license);
						break;
					}
				}
			}
			licenses = tempLicenses;
			tempLicenses = null;

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

	public ArrayList<TransferPass> getTransferPasses()
	{
		return transferPasses;
	}

	public void setTransferPasses(ArrayList<TransferPass> transferPasses)
	{
		this.transferPasses = transferPasses;
	}

	public ArrayList<TransferPass> getSelectedTransferPasses()
	{
		return selectedTransferPasses;
	}

	public void setSelectedTransferPasses(
			ArrayList<TransferPass> selectedTransferPasses)
	{
		this.selectedTransferPasses = selectedTransferPasses;
	}

	public Staff getUser()
	{
		return user;
	}

	public void setUser(Staff user)
	{
		this.user = user;
	}

	public Journal getJournal()
	{
		return model;
	}

	public void setJournal(Journal journal)
	{
		this.model = journal;
	}

	public ArrayList<Journal> getJournals()
	{
		return models;
	}

	public void setJournals(ArrayList<Journal> journals)
	{
		this.models = journals;
	}

	public ArrayList<JournalRecord> getJournalRecords()
	{
		return journalRecords;
	}

	public void setJournalRecords(ArrayList<JournalRecord> journalRecords)
	{
		this.journalRecords = journalRecords;
	}

	public ArrayList<License> getLicenses()
	{
		return licenses;
	}

	public void setLicenses(ArrayList<License> licenses)
	{
		this.licenses = licenses;
	}

	public License getCurrentLicense()
	{
		return currentLicense;
	}

	public void setCurrentLicense(License currentLicense)
	{
		this.currentLicense = currentLicense;
	}

	public ArrayList<TransactionFormMapDepartmentVot> getTransactionFormMapDepartmentVots()
	{
		return transactionFormMapDepartmentVots;
	}

	public void setTransactionFormMapDepartmentVots(
			ArrayList<TransactionFormMapDepartmentVot> transactionFormMapDepartmentVots)
	{
		this.transactionFormMapDepartmentVots = transactionFormMapDepartmentVots;
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

	public ArrayList<JournalRecord> getSelectedJournalRecords()
	{
		return selectedJournalRecords;
	}

	public void setSelectedJournalRecords(
			ArrayList<JournalRecord> selectedJournalRecords)
	{
		this.selectedJournalRecords = selectedJournalRecords;
	}

	public ArrayList<DepartmentVot> getDepartmentVots()
	{
		return departmentVots;
	}

	public void setDepartmentVots(ArrayList<DepartmentVot> departmentVots)
	{
		this.departmentVots = departmentVots;
	}

	public ArrayList<TrustFund> getTrustFunds()
	{
		return trustFunds;
	}

	public void setTrustFunds(ArrayList<TrustFund> trustFunds)
	{
		this.trustFunds = trustFunds;
	}

	public ArrayList<Permit> getPermits()
	{
		return permits;
	}

	public void setPermits(ArrayList<Permit> permits)
	{
		this.permits = permits;
	}

	public String getTransactionFormName()
	{
		return transactionFormName;
	}

	public void setTransactionFormName(String transactionFormName)
	{
		this.transactionFormName = transactionFormName;
	}

	public TransactionForm getTransactionForm()
	{
		return transactionForm;
	}

	public void setTransactionForm(TransactionForm transactionForm)
	{
		this.transactionForm = transactionForm;
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

	public ArrayList<TransferPass> getAllTransferPasses()
	{
		return allTransferPasses;
	}

	public void setAllTransferPasses(ArrayList<TransferPass> allTransferPasses)
	{
		this.allTransferPasses = allTransferPasses;
	}

	public String getComponent1()
	{
		return ":frmManager:table" + (model == null ? ""
				: ":" + models.indexOf(model) + ":subtable1");
	}

	public String getComponent2()
	{
		return ":frmManager:table" + (model == null ? ""
				: ":" + models.indexOf(model) + ":subtable2");
	}

	@Override
	public void handleOpen()
	{
		model = new Journal();
		Date date = new Date();

		model.setCategory(category);
		model.setDate(date);
		model.setRecorderID(user.getStaffID());
		model.setRecorderName(user.getName());
		model.setTotal(BigDecimal.ZERO);
		model.setJournalRecords(new ArrayList<JournalRecord>());
		if (model.getCategory() == 0)
			model.setTransferPasses(new ArrayList<TransferPass>());
		model.setStatus("A");

		try (MaintenanceFacade mFacade = new MaintenanceFacade())
		{
			transactionForm = new TransactionForm();
			transactionForm.setName(transactionFormName);
			transactionForm = mFacade.getTransactionFormByName(transactionForm);
			transactionFormMapDepartmentVots = mFacade
					.getTransactionFormMapDepartmentVots(transactionForm, "A");
			transactionFormMapTrustFunds = mFacade
					.getTransactionFormMapTrustFunds(transactionForm, "A");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void handleOpenTransferPasses()
	{
		clearFilter();

		try (HallFacade hFacade = new HallFacade())
		{
			transferPasses = null;
			transferPasses = new ArrayList<TransferPass>();
			for (TransferPass transferPass : allTransferPasses)
			{
				if (transferPass.getLicenseID() == model.getLicenseID())
				{
					transferPasses.add(transferPass);
				}
			}
			sort(transferPasses);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}

	}

	public void handleOpenJournalRecords()
	{

		journalRecords = null;
		journalRecords = new ArrayList<JournalRecord>();

		if (model.getCategory() == 0)
		{
			royaltyAmount = BigDecimal.ZERO;
			cessAmount = BigDecimal.ZERO;

			for (TransferPass transferPass : selectedTransferPasses)
			{
				royaltyAmount = royaltyAmount
						.add(transferPass.getRoyaltyAmount());
				cessAmount = cessAmount.add(transferPass.getCessAmount());
			}

			JournalRecord journalRecordRoyalty = new JournalRecord();
			journalRecordRoyalty.setTotal(royaltyAmount);

			JournalRecord journalRecordCess = new JournalRecord();
			journalRecordCess.setTotal(cessAmount);

			for (TrustFund trustFund : trustFunds)
			{
				if (trustFund.getSymbol().equalsIgnoreCase("ROY"))
				{
					journalRecordRoyalty
							.setBursaryVotID(trustFund.getBursaryVotID());
					journalRecordRoyalty
							.setBursaryVotCode(trustFund.getBursaryVotCode());
					journalRecordRoyalty
							.setBursaryVotName(trustFund.getBursaryVotName());
					journalRecordRoyalty
							.setDepartmentVotID(trustFund.getDepartmentVotID());
					journalRecordRoyalty.setDepartmentVotCode(
							trustFund.getDepartmentVotCode());
					journalRecordRoyalty.setDepartmentVotName(
							trustFund.getDepartmentVotName());
				}
				if (trustFund.getSymbol().equalsIgnoreCase("SES"))
				{
					journalRecordCess
							.setBursaryVotID(trustFund.getBursaryVotID());
					journalRecordCess
							.setBursaryVotCode(trustFund.getBursaryVotCode());
					journalRecordCess
							.setBursaryVotName(trustFund.getBursaryVotName());
					journalRecordCess
							.setDepartmentVotID(trustFund.getDepartmentVotID());
					journalRecordCess.setDepartmentVotCode(
							trustFund.getDepartmentVotCode());
					journalRecordCess.setDepartmentVotName(
							trustFund.getDepartmentVotName());
				}
				if (trustFund.getSymbol().equalsIgnoreCase("WAKK"))
				{
					journalRecordRoyalty
							.setTrustFundID(trustFund.getTrustFundID());
					journalRecordRoyalty.setTrustFundBursaryVotID(
							trustFund.getBursaryVotID());
					journalRecordRoyalty.setTrustFundBursaryVotCode(
							trustFund.getBursaryVotCode());
					journalRecordRoyalty.setTrustFundBursaryVotName(
							trustFund.getBursaryVotName());
					journalRecordRoyalty.setTrustFundDepartmentVotID(
							trustFund.getDepartmentVotID());
					journalRecordRoyalty.setTrustFundDepartmentVotCode(
							trustFund.getDepartmentVotCode());
					journalRecordRoyalty.setTrustFundDepartmentVotName(
							trustFund.getDepartmentVotName());

					journalRecordCess
							.setTrustFundID(trustFund.getTrustFundID());
					journalRecordCess.setTrustFundBursaryVotID(
							trustFund.getBursaryVotID());
					journalRecordCess.setTrustFundBursaryVotCode(
							trustFund.getBursaryVotCode());
					journalRecordCess.setTrustFundBursaryVotName(
							trustFund.getBursaryVotName());
					journalRecordCess.setTrustFundDepartmentVotID(
							trustFund.getDepartmentVotID());
					journalRecordCess.setTrustFundDepartmentVotCode(
							trustFund.getDepartmentVotCode());
					journalRecordCess.setTrustFundDepartmentVotName(
							trustFund.getDepartmentVotName());
				}
			}

			journalRecords.add(journalRecordRoyalty);
			journalRecords.add(journalRecordCess);
		}
		else
		{
			if (model.getCategory() == 1)
			{
				JournalRecord journalRecord = new JournalRecord();

				for (TrustFund trustFund : trustFunds)
				{
					if (currentPermit.getPermitTypeID() == 1)
					{
						if (trustFund.getSymbol().equalsIgnoreCase("WAM"))
						{
							journalRecord
									.setTrustFundID(trustFund.getTrustFundID());
							journalRecord.setTrustFundBursaryVotID(
									trustFund.getBursaryVotID());
							journalRecord.setTrustFundBursaryVotCode(
									trustFund.getBursaryVotCode());
							journalRecord.setTrustFundBursaryVotName(
									trustFund.getBursaryVotName());
							journalRecord.setTrustFundDepartmentVotID(
									trustFund.getDepartmentVotID());
							journalRecord.setTrustFundDepartmentVotCode(
									trustFund.getDepartmentVotCode());
							journalRecord.setTrustFundDepartmentVotName(
									trustFund.getDepartmentVotName());

						}
					}
					else
					{
						if (currentPermit.getPermitTypeID() == 2)
						{
							if (trustFund.getSymbol().equalsIgnoreCase("WAKO"))
							{
								journalRecord.setTrustFundID(
										trustFund.getTrustFundID());
								journalRecord.setTrustFundBursaryVotID(
										trustFund.getBursaryVotID());
								journalRecord.setTrustFundBursaryVotCode(
										trustFund.getBursaryVotCode());
								journalRecord.setTrustFundBursaryVotName(
										trustFund.getBursaryVotName());
								journalRecord.setTrustFundDepartmentVotID(
										trustFund.getDepartmentVotID());
								journalRecord.setTrustFundDepartmentVotCode(
										trustFund.getDepartmentVotCode());
								journalRecord.setTrustFundDepartmentVotName(
										trustFund.getDepartmentVotName());
							}
						}
						else
						{
							if (currentPermit.getPermitTypeID() == 3)
							{
								if (trustFund.getSymbol()
										.equalsIgnoreCase("WAJ"))
								{
									journalRecord.setTrustFundID(
											trustFund.getTrustFundID());
									journalRecord.setTrustFundBursaryVotID(
											trustFund.getBursaryVotID());
									journalRecord.setTrustFundBursaryVotCode(
											trustFund.getBursaryVotCode());
									journalRecord.setTrustFundBursaryVotName(
											trustFund.getBursaryVotName());
									journalRecord.setTrustFundDepartmentVotID(
											trustFund.getDepartmentVotID());
									journalRecord.setTrustFundDepartmentVotCode(
											trustFund.getDepartmentVotCode());
									journalRecord.setTrustFundDepartmentVotName(
											trustFund.getDepartmentVotName());
								}
							}
						}
					}
				}
				journalRecords.add(journalRecord);
			}
		}
	}

	public void journalEntry()
	{
		try (RevenueFacade rFacade = new RevenueFacade();
				HallFacade hFacade = new HallFacade())
		{
			int count = 0;
			boolean removeLicenseStatus = true;
			boolean exceedFund = false;

			model.setJournalID(System.currentTimeMillis());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			model.setRecordTime(timestamp);
			model.setJournalNo(
					model.getJournalNo().replaceAll("\\s", "").toUpperCase());

			for (JournalRecord journalRecord : journalRecords)
			{
				model.setTotal(model.getTotal().add(journalRecord.getTotal()));
			}

			exceedFund = false;

			if (model.getCategory() == 0)
			{
				if (model.getTotal()
						.compareTo(currentLicense.getWoodWorkFund()) > 0)
				{
					exceedFund = true;
				}
			}
			else
			{
				if (model.getCategory() == 1)
				{
					if (model.getTotal()
							.compareTo(currentPermit.getPermitFund()) > 0)
					{
						exceedFund = true;
					}
				}
			}

			if (exceedFund == true)
			{
				addMessage(FacesMessage.SEVERITY_WARN, null, model
						+ " tidak dapat ditambahkan kerana wang amanah tidak mencukupi.");
			}
			else
			{
				if (rFacade.addJournal(model) != 0)
				{
					addMessage(FacesMessage.SEVERITY_INFO, null,
							model + " berjaya ditambahkan.");
					log(rFacade, "Tambah jurnal, ID " + model.getJournalID());

					long journalRecordID = System.currentTimeMillis();
					long transactionID = System.currentTimeMillis();
					for (JournalRecord journalRecord : journalRecords)
					{
						journalRecord.setJournalRecordID(journalRecordID);
						journalRecord.setJournalID(model.getJournalID());

						if (rFacade.addJournalRecord(journalRecord) != 0)
						{
							count++;
							model.getJournalRecords().add(journalRecord);
							log(rFacade, "Tambah rekod jurnal, ID "
									+ journalRecord.getJournalRecordID());

							Revenue revenue = new Revenue();
							revenue.setDepartmentVotID(
									journalRecord.getDepartmentVotID());
							revenue.setValue(journalRecord.getTotal()
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							if (rFacade.addRevenue(revenue) != 0)
							{
								log(rFacade, "Tambah hasil, ID "
										+ revenue.getDepartmentVotID());
							}
							revenue = null;

							revenue = new Revenue();
							revenue.setDepartmentVotID(journalRecord
									.getTrustFundDepartmentVotID());
							revenue.setValue(journalRecord.getTotal()
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							if (rFacade.subtractRevenue(revenue) != 0)
							{
								log(rFacade, "Tolak hasil, ID "
										+ revenue.getDepartmentVotID());
							}

							Transaction transaction = new Transaction();
							transaction.setTransactionID(transactionID);
							transaction.setTransactionType(
									"12" + model.getCategory());
							transaction
									.setTransactionFormID(model.getJournalID());
							transaction.setDepartmentVotID(
									journalRecord.getDepartmentVotID());
							transaction.setValue(journalRecord.getTotal()
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							transaction.setRecordTime(timestamp);
							rFacade.addTransaction(transaction);
							transaction = null;

							transaction = new Transaction();
							transaction.setTransactionID(transactionID + 1);
							transaction.setTransactionType(
									"02" + model.getCategory());
							transaction
									.setTransactionFormID(model.getJournalID());
							transaction.setDepartmentVotID(
									journalRecord.getTrustFundID());
							transaction.setValue(journalRecord.getTotal()
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							transaction.setRecordTime(timestamp);
							rFacade.addTransaction(transaction);
							transaction = null;

							if (model.getCategory() == 0)
							{
								if (rFacade.subtractWoodWorkFund(
										journalRecord.getTotal(),
										model.getLicenseID()) != 0)
								{
									model.setWoodWorkFund(
											model.getWoodWorkFund().subtract(
													journalRecord.getTotal()));
									log(rFacade, "Kemaskini lesen, ID "
											+ model.getLicenseID());
								}
							}
							else
							{
								if (model.getCategory() == 1)
								{
									for (TransactionFormMapDepartmentVot transactionFormMapDepartmentVot : transactionFormMapDepartmentVots)
									{
										if (transactionFormMapDepartmentVot
												.getDepartmentVotID() == journalRecord
														.getDepartmentVotID())
										{
											journalRecord.setBursaryVotCode(
													transactionFormMapDepartmentVot
															.getBursaryVotCode());
											journalRecord.setBursaryVotName(
													transactionFormMapDepartmentVot
															.getBursaryVotName());
											journalRecord.setDepartmentVotCode(
													transactionFormMapDepartmentVot
															.getDepartmentVotCode());
											journalRecord.setDepartmentVotName(
													transactionFormMapDepartmentVot
															.getDepartmentVotName());
											break;
										}
									}

									if (rFacade.subtractJalanMatauKongsiFund(
											journalRecord.getTotal(),
											model.getPermitID()) != 0)
									{
										model.setPermitFund(
												model.getPermitFund()
														.subtract(journalRecord
																.getTotal()));
										log(rFacade, "Kemaskini permit, ID "
												+ model.getPermitID());
									}
								}
							}
						}
						journalRecordID++;
						transactionID = transactionID + 2;
					}

					if (model.getCategory() == 0)
					{
						for (TransferPass transferPass : selectedTransferPasses)
						{
							transferPass.setStatus("P");
							transferPass.setJournalID(model.getJournalID());
							if (hFacade.updateTransferPassJournal(
									transferPass) != 0)
							{
								log(rFacade, "Kemaskini pas memindah, ID "
										+ transferPass.getTransferPassID());
							}
							allTransferPasses.remove(transferPass);
							model.getTransferPasses().add(transferPass);
						}

						for (TransferPass transferPass : transferPasses)
						{
							if (!selectedTransferPasses.contains(transferPass))
							{
								removeLicenseStatus = false;
							}
						}

						if (removeLicenseStatus == true)
						{
							licenses.remove(currentLicense);
							sort(licenses);
						}
					}

					selectedTransferPasses = null;
					transferPasses = null;
					journalRecords = null;
					sort(model.getJournalRecords());
					for (Journal journal : models)
					{
						if (journal.getCategory() == 0)
						{
							if (journal.getLicenseID() == model.getLicenseID())
							{
								journal.setWoodWorkFund(
										model.getWoodWorkFund());
							}
						}
						else
						{
							if (journal.getCategory() == 1)
							{
								if (journal.getPermitID() == model
										.getPermitID())
								{
									journal.setPermitFund(
											model.getPermitFund());
								}
							}
						}

					}
					models.add(model);
					sort(models);
					addMessage(FacesMessage.SEVERITY_INFO, null, "Sebanyak "
							+ count + " rekod jurnal berjaya ditambahkan.");
				}
				else
				{
					addMessage(FacesMessage.SEVERITY_WARN, null, model
							+ " tidak dapat ditambahkan kerana no. jurnal telah direkodkan sebelumnya.");
				}
			}
			model = null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
		execute("PF('popupJournalRecord').hide()");
	}

	public void journalEntryPreparation()
	{
		try (RevenueFacade facade = new RevenueFacade())
		{
			if (model.getCategory() == 0)
			{
				for (License license : licenses)
				{
					if (license.getLicenseID() == model.getLicenseID())
					{
						currentLicense = license;
						model.setLicenseNo(license.getLicenseNo());
						model.setLicenseeCompanyName(
								license.getLicenseeCompanyName());
						model.setWoodWorkFund(license.getWoodWorkFund());
						model.setLicenseFund(license.getLicenseFund());
						break;
					}
				}
				execute("PF('popup').hide()");
				handleOpenTransferPasses();
				update("frmEntryTransferPass");
				execute("PF('popupTransferPass').show()");
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
							model.setLicenseeCompanyName(
									permit.getLicenseeCompanyName());
							model.setPermitFund(permit.getPermitFund());
							break;
						}
					}
					execute("PF('popup').hide()");
					handleOpenJournalRecords();
					update("frmEntryJournalRecord");
					execute("PF('popupJournalRecord').show()");
				}
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public void transferPassEntry()
	{

		try (MaintenanceFacade mFacade = new MaintenanceFacade();
				HallFacade hFacade = new HallFacade())
		{
			AbstractFacade.group(mFacade, hFacade);
			execute("PF('popupTransferPass').hide()");
			handleOpenJournalRecords();
			update("frmEntryJournalRecord");
			execute("PF('popupJournalRecord').show()");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			addMessage(e);
		}
	}

	public StreamedContent download(Journal journal)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();

		String name = "Jurnal_" + journal.getJournalNo() + ".pdf";
		File file = new File(
				external.getRealPath("/") + "files/revenue/" + name);
		StreamedContent content = null;

		file.getParentFile().mkdirs();

		try (RevenueFacade rFacade = new RevenueFacade())
		{
			String[] journalHeader = rFacade
					.getJournalHeader(journal.getJournalID());
			ArrayList<String[]> journalRecords = rFacade
					.getJournalRecords(journal.getJournalID());

			JurnalGenerator.generate(file, journalHeader, journalRecords);

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