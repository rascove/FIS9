package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.model.ForestDevelopmentContractor;
import my.edu.utem.ftmk.fis9.revenue.model.ForestDevelopmentContractorSubWorkTypeRecord;
import my.edu.utem.ftmk.fis9.revenue.model.Journal;
import my.edu.utem.ftmk.fis9.revenue.model.JournalRecord;
import my.edu.utem.ftmk.fis9.revenue.model.License;
import my.edu.utem.ftmk.fis9.revenue.model.LoggingContractor;
import my.edu.utem.ftmk.fis9.revenue.model.Permit;
import my.edu.utem.ftmk.fis9.revenue.model.Receipt;
import my.edu.utem.ftmk.fis9.revenue.model.ReceiptRecord;
import my.edu.utem.ftmk.fis9.revenue.model.Renew;
import my.edu.utem.ftmk.fis9.revenue.model.Revenue;
import my.edu.utem.ftmk.fis9.revenue.model.Transaction;
import my.edu.utem.ftmk.fis9.revenue.model.Voucher;
import my.edu.utem.ftmk.fis9.revenue.model.VoucherRecord;

/**
 * @author Nor Azman Mat Ariff
 */
public class RevenueFacade extends AbstractFacade
{
	private RevenueManager revenueManager;
	private TransactionManager transactionManager;
	private ReceiptManager receiptManager;
	private ReceiptRecordManager receiptRecordManager;
	private JournalManager journalManager;
	private JournalRecordManager journalRecordManager;
	private VoucherManager voucherManager;
	private VoucherRecordManager voucherRecordManager;	
	private ForestDevelopmentContractorManager forestDevelopmentContractorManager;
	private ForestDevelopmentContractorSubWorkTypeRecordManager forestDevelopmentContractorSubWorkTypeRecordManager;
	private LoggingContractorManager loggingContractorManager;
	private LicenseManager licenseManager;
	private PermitManager permitManager;
	private RenewManager renewManager;

	private RevenueManager getRevenueManager()
	{
		if (revenueManager == null)
			revenueManager = new RevenueManager(this);

		return revenueManager;
	}

	private TransactionManager getTransactionManager()
	{
		if (transactionManager == null)
			transactionManager = new TransactionManager(this);

		return transactionManager;
	}

	private ReceiptManager getReceiptManager()
	{
		if (receiptManager == null)
			receiptManager = new ReceiptManager(this);

		return receiptManager;
	}

	private ReceiptRecordManager getReceiptRecordManager()
	{
		if (receiptRecordManager == null)
			receiptRecordManager = new ReceiptRecordManager(this);

		return receiptRecordManager;
	}

	private JournalManager getJournalManager()
	{
		if (journalManager == null)
			journalManager = new JournalManager(this);

		return journalManager;
	}

	private JournalRecordManager getJournalRecordManager()
	{
		if (journalRecordManager == null)
			journalRecordManager = new JournalRecordManager(this);

		return journalRecordManager;
	}

	private VoucherManager getVoucherManager()
	{
		if (voucherManager == null)
			voucherManager = new VoucherManager(this);

		return voucherManager;
	}

	private VoucherRecordManager getVoucherRecordManager()
	{
		if (voucherRecordManager == null)
			voucherRecordManager = new VoucherRecordManager(this);

		return voucherRecordManager;
	}

	private ForestDevelopmentContractorManager getForestDevelopmentContractorManager()
	{
		if (forestDevelopmentContractorManager == null)
			forestDevelopmentContractorManager = new ForestDevelopmentContractorManager(this);

		return forestDevelopmentContractorManager;
	}

	private ForestDevelopmentContractorSubWorkTypeRecordManager getForestDevelopmentContractorSubWorkTypeRecordManager()
	{
		if (forestDevelopmentContractorSubWorkTypeRecordManager == null)
			forestDevelopmentContractorSubWorkTypeRecordManager = new ForestDevelopmentContractorSubWorkTypeRecordManager(this);

		return forestDevelopmentContractorSubWorkTypeRecordManager;
	}

	private LoggingContractorManager getLoggingContractorManager()
	{
		if (loggingContractorManager == null)
			loggingContractorManager = new LoggingContractorManager(this);

		return loggingContractorManager;
	}

	private LicenseManager getLicenseManager()
	{
		if (licenseManager == null)
			licenseManager = new LicenseManager(this);

		return licenseManager;
	}

	private PermitManager getPermitManager()
	{
		if (permitManager == null)
			permitManager = new PermitManager(this);

		return permitManager;
	}

	private RenewManager getRenewManager()
	{
		if (renewManager == null)
			renewManager = new RenewManager(this);

		return renewManager;
	}


	@Override
	protected PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return getPreparedStatement(sql);
	}

	public int addRevenue(Revenue revenue) throws SQLException
	{
		return getRevenueManager().addRevenue(revenue);
	}

	public int subtractRevenue(Revenue revenue) throws SQLException
	{
		return getRevenueManager().subtractRevenue(revenue);
	}	

	public int addTransaction(Transaction transaction) throws SQLException
	{
		return getTransactionManager().addTransaction(transaction);
	}

	public int addReceipt(Receipt receipt) throws SQLException
	{
		return getReceiptManager().addReceipt(receipt);
	}
	
	public boolean checkExistingReceiptNo(Receipt receipt) throws SQLException
	{
		return getReceiptManager().checkExistingReceiptNo(receipt);
	}

	public int deleteReceipt(Receipt receipt) throws SQLException
	{
		return getReceiptManager().deleteReceipt(receipt);
	}	
	
	public int updateReceiptCategory(Receipt receipt) throws SQLException
	{
		return getReceiptManager().updateReceiptCategory(receipt);
	}	

	public int updateStatusReceipt(Receipt receipt) throws SQLException
	{
		return getReceiptManager().updateStatusReceipt(receipt);
	}	
	
	public int updateCollectorStatement(Receipt receipt) throws SQLException
	{
		return getReceiptManager().updateCollectorStatement(receipt);
	}	

	public Receipt getReceipt(long receiptID) throws SQLException
	{
		return getReceiptManager().getReceipt(receiptID);
	}

	public ArrayList<Receipt> getReceipts(String status) throws SQLException
	{
		return getReceiptManager().getReceipts(status);
	}

	public ArrayList<Receipt> getReceipts(String receiptCategory, String status) throws SQLException
	{
		return getReceiptManager().getReceipts(receiptCategory, status);
	}
	
	public ArrayList<Receipt> getReceipts(String receiptCategory, String statusInactive, String registeredAsContractor, String registeredAsLicensee) throws SQLException
	{
		return getReceiptManager().getReceipts(receiptCategory, statusInactive, registeredAsContractor, registeredAsLicensee);
	}
	
	public ArrayList<Receipt> getReceiptsByUser(String status, String staffID) throws SQLException
	{
		return getReceiptManager().getReceiptsByUser(status, staffID);
	}

	public ArrayList<String[]> getReceiptsString(Date startDate, Date endDate, int reportType) throws SQLException
	{
		return getReceiptManager().getReceiptsString(startDate, endDate, reportType);
	}
	
	public ArrayList<String[]> getSenaraiResitYangDibatalkanDariTarikhMulaHinggaAkhir(Date startDate, Date endDate) throws SQLException
	{
		return getReceiptManager().getSenaraiResitYangDibatalkanDariTarikhMulaHinggaAkhir(startDate, endDate);
	}

	public ArrayList<ArrayList<String[]>> getReceiptsReportType5(Date startDate, Date endDate) throws SQLException
	{
		return getReceiptManager().getReceiptsReportType5(startDate, endDate);
	}

	public ArrayList<String[]> getLaporanKutipanHarianMengikutOperatorSepertiPadaTarikh(Date date, Staff staff) throws SQLException
	{
		return getReceiptManager().getLaporanKutipanHarianMengikutOperatorSepertiPadaTarikh(date, staff);
	}

	public ArrayList<String[]> getLaporanUrusniagaPadaBulanDanTahun(int month, int year, boolean trustFundCategory) throws SQLException
	{
		return getReceiptManager().getLaporanUrusniagaPadaBulanDanTahun(month, year, trustFundCategory);
	}
	
	public ArrayList<String[]> getLaporanUrusniagaAkaunAmanahPadaBulanDanTahunBagiVot79501(int month, int year) throws SQLException
	{
		return getReceiptManager().getLaporanUrusniagaAkaunAmanahPadaBulanDanTahunBagiVot79501(month, year);
	}

	public ArrayList<String[]> getLaporanUrusniagaAkaunAmanahPadaBulanDanTahunBagiVot79503(int month, int year) throws SQLException
	{
		return getReceiptManager().getLaporanUrusniagaAkaunAmanahPadaBulanDanTahunBagiVot79503(month, year);
	}

	public String[] getReceiptHeader(long receiptID) throws SQLException
	{
		return getReceiptManager().getReceiptHeader(receiptID);
	}	

	public ArrayList<String[]> getReceiptRecordsForReport(long receiptID) throws SQLException
	{
		return getReceiptManager().getReceiptRecords(receiptID);
	}	

	public ArrayList<String[]> getSenaraiBayaranBagiSemuaLesenPadaBulanDanTahun(Date date) throws SQLException
	{
		return getReceiptManager().getSenaraiBayaranBagiSemuaLesenPadaBulanDanTahun(date);
	}	

	public ArrayList<String[]> getLaporanBagiPembatalanHasilDariTarikhMulaHinggaAkhir(Date startDate, Date endDate) throws SQLException
	{
		return getReceiptManager().getLaporanBagiPembatalanHasilDariTarikhMulaHinggaAkhir(startDate, endDate);
	}	

	public ArrayList<String[]> getLaporanKutipanHarianPadaTarikh(Date date) throws SQLException
	{
		return getReceiptManager().getLaporanKutipanHarianPadaTarikh(date);
	}

	public String[] getHeaderPenyataAkaunHasilMengikutJabatanPadaBulanDanTahun() throws SQLException
	{
		return getReceiptManager().getHeaderPenyataAkaunHasilMengikutJabatanPadaBulanDanTahun();
	}	
	
	public ArrayList<String[]> getContentPenyataAkaunHasilMengikutJabatanPadaBulanDanTahun(int month, int year) throws SQLException
	{
		return getReceiptManager().getContentPenyataAkaunHasilMengikutJabatanPadaBulanDanTahun(month, year);
	}	

	public String[] getHeaderPenyataAkaunAmanahMengikutJabatanPadaBulanDanTahun() throws SQLException
	{
		return getReceiptManager().getHeaderPenyataAkaunAmanahMengikutJabatanPadaBulanDanTahun();
	}	
		
	public ArrayList<String[]> getContentPenyataAkaunAmanahMengikutJabatanPadaBulanDanTahun(int month, int year) throws SQLException
	{
		return getReceiptManager().getContentPenyataAkaunAmanahMengikutJabatanPadaBulanDanTahun(month, year);
	}	

	public String[] getHeaderLaporanUrusniagaAkaunHasilPadaBulanDanTahun() throws SQLException
	{
		return getReceiptManager().getHeaderLaporanUrusniagaAkaunHasilPadaBulanDanTahun();
	}	
		
	public ArrayList<String[]> getContentLaporanUrusniagaAkaunHasilPadaBulanDanTahun(int month, int year) throws SQLException
	{
		return getReceiptManager().getContentLaporanUrusniagaAkaunHasilPadaBulanDanTahun(month, year);
	}	

	public String[] getHeaderPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir() throws SQLException
	{
		return getReceiptManager().getHeaderPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir();
	}	

	public ArrayList<String[]> getContentPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getReceiptManager().getContentPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(startDate, endDate, licenseID);
	}

	public String[] getBalancePenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir() throws SQLException
	{
		return getReceiptManager().getBalancePenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir();
	}	

	public String[] getCessPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir() throws SQLException
	{
		return getReceiptManager().getCessPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir();
	}	
	
	public ArrayList<String[]> getTrailerPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getReceiptManager().getTrailerPenyataAkaunBagiLesenDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(startDate, endDate, licenseID);
	}

	public int addReceiptRecord(ReceiptRecord receiptRecord) throws SQLException
	{
		return getReceiptRecordManager().addReceiptRecord(receiptRecord);
	}

	public ArrayList<ReceiptRecord> getReceiptRecords(long receiptID) throws SQLException
	{
		return getReceiptRecordManager().getReceiptRecords(receiptID);
	}

	public int addLicense(License license, boolean ignoreDuplicate) throws SQLException
	{
		return getLicenseManager().addLicense(license, ignoreDuplicate);
	}

	public int updateLicense(License license) throws SQLException
	{
		return getLicenseManager().updateLicense(license);
	}

	public int updateLicenseStatus(License license, String status) throws SQLException
	{
		return getLicenseManager().updateLicenseStatus(license, status);
	}

	public int closeLicense(License license) throws SQLException
	{
		return getLicenseManager().closeLicense(license);
	}

	public int deleteLicense(License license) throws SQLException
	{
		return getLicenseManager().deleteLicense(license);
	}	

	public int updateLimit(BigDecimal resinLimit, BigDecimal nonResinLimit, BigDecimal chengalLimit, License license) throws SQLException
	{
		return getLicenseManager().updateLimit(resinLimit, nonResinLimit, chengalLimit, license);
	}
	
	public int subtractFundAndLimit(boolean updateResin, BigDecimal totalResin, boolean updateNonResin, BigDecimal totalNonResin, boolean updateChengal, BigDecimal totalChengal, boolean updateLog, BigDecimal totalLog, boolean updateJaras, BigDecimal totalJaras, BigDecimal royaltyAndCess, License license) throws SQLException
	{
		return getLicenseManager().subtractFundAndLimit(updateResin, totalResin, updateNonResin, totalNonResin, updateChengal, totalChengal, updateLog, totalLog, updateJaras, totalJaras, royaltyAndCess, license);
	}
	
	public int addFundAndLimit(boolean updateResin, BigDecimal totalResin, boolean updateNonResin, BigDecimal totalNonResin, boolean updateChengal, BigDecimal totalChengal,boolean updateLog, BigDecimal totalLog, boolean updateJaras, BigDecimal totalJaras, BigDecimal royaltyAndCess, License license) throws SQLException
	{
		return getLicenseManager().addFundAndLimit(updateResin, totalResin, updateNonResin, totalNonResin, updateChengal, totalChengal, updateLog, totalLog, updateJaras, totalJaras, royaltyAndCess, license);
	}

	public int addWoodWorkFund(BigDecimal value, long licenseID) throws SQLException
	{
		return getLicenseManager().addWoodWorkFund(value, licenseID);
	}

	public int addLicenseFund(BigDecimal value, long licenseID) throws SQLException
	{
		return getLicenseManager().addLicenseFund(value, licenseID);
	}

	public int subtractWoodWorkFund(BigDecimal value, long licenseID) throws SQLException
	{
		return getLicenseManager().subtractWoodWorkFund(value, licenseID);
	}

	public int subtractLicenseFund(BigDecimal value, long licenseID) throws SQLException
	{
		return getLicenseManager().subtractLicenseFund(value, licenseID);
	}

	public License getLicense(long licenseID) throws SQLException
	{
		return getLicenseManager().getLicense(licenseID);
	}

	public ArrayList<License> getLicenses(String status) throws SQLException
	{
		return getLicenseManager().getLicenses(status);
	}

	public ArrayList<License> getLicenses(District district, String status) throws SQLException
	{
		return getLicenseManager().getLicenses(district, status);
	}

	public ArrayList<License> getLicenses(State state, String status) throws SQLException
	{
		return getLicenseManager().getLicenses(state, status);
	}	

	public int addPermit(Permit permit) throws SQLException
	{
		return getPermitManager().addPermit(permit);
	}

	public int deletePermit(Permit permit) throws SQLException
	{
		return getPermitManager().deletePermit(permit);
	}
	
	public int closePermit(Permit permit) throws SQLException
	{
		return getPermitManager().closePermit(permit);
	}

	public int updatePermit(Permit permit) throws SQLException
	{
		return getPermitManager().updatePermit(permit);
	}

	public int updatePermitStatus(Permit permit, String status) throws SQLException
	{
		return getPermitManager().updatePermitStatus(permit, status);
	}
	
	public int addPermitFund(BigDecimal value, long permitID) throws SQLException
	{
		return getPermitManager().addPermitFund(value, permitID);
	}
	
	public int subtractJalanMatauKongsiFund(BigDecimal value, long permitID) throws SQLException
	{
		return getPermitManager().subtractPermitFund(value, permitID);
	}

	public Permit getPermit(long permitID) throws SQLException
	{
		return getPermitManager().getPermit(permitID);
	}

	public ArrayList<Permit> getPermits(String status) throws SQLException
	{
		return getPermitManager().getPermits(status);
	}

	public ArrayList<Permit> getPermits(District district, String status) throws SQLException
	{
		return getPermitManager().getPermits(district, status);
	}

	public ArrayList<Permit> getPermits(State state, String status) throws SQLException
	{
		return getPermitManager().getPermits(state, status);
	}
	
	public ArrayList<Permit> getPermits(Staff user, String status) throws SQLException
	{
		return getPermitManager().getPermits(user, status);
	}

	public int addJournal(Journal journal) throws SQLException
	{
		return getJournalManager().addJournal(journal);
	}

	public int deleteJournal(Journal journal) throws SQLException
	{
		return getJournalManager().deleteJournal(journal);
	}	

	public Journal getJournal(int journalID) throws SQLException
	{
		return getJournalManager().getJournal(journalID);
	}

	public ArrayList<Journal> getJournals(String status) throws SQLException
	{
		return getJournalManager().getJournals(status);
	}

	public ArrayList<Journal> getJournals(Staff staff) throws SQLException
	{
		return getJournalManager().getJournals(staff);
	}

	public ArrayList<Journal> getJournals(State state) throws SQLException
	{
		return getJournalManager().getJournals(state);
	}

	public String[] getJournalHeader(long journalID) throws SQLException
	{
		return getJournalManager().getJournalHeader(journalID);
	}

	public ArrayList<String[]> getJournalRecords(long journalID) throws SQLException
	{
		return getJournalManager().getJournalRecords(journalID);
	}

	public int addJournalRecord(JournalRecord journalRecord) throws SQLException
	{
		return getJournalRecordManager().addJournalRecord(journalRecord);
	}

	public int updateJournalRecord(JournalRecord journalRecord) throws SQLException
	{
		return getJournalRecordManager().updateJournalRecord(journalRecord);
	}

	public int deleteJournalRecord(JournalRecord journalRecord) throws SQLException
	{
		return getJournalRecordManager().deleteJournalRecord(journalRecord);
	}	

	public JournalRecord getJournalRecord(int journalRecordID) throws SQLException
	{
		return getJournalRecordManager().getJournalRecord(journalRecordID);
	}

	public ArrayList<JournalRecord> getJournalRecords(Journal journal) throws SQLException
	{
		return getJournalRecordManager().getJournalRecords(journal);
	}

	public int addVoucher(Voucher voucher) throws SQLException
	{
		return getVoucherManager().addVoucher(voucher);
	}

	public int deleteVoucher(Voucher voucher) throws SQLException
	{
		return getVoucherManager().deleteVoucher(voucher);
	}	

	public Voucher getVoucher(int voucherID) throws SQLException
	{
		return getVoucherManager().getVoucher(voucherID);
	}

	public ArrayList<Voucher> getVouchers(String status) throws SQLException
	{
		return getVoucherManager().getVouchers(status);
	}

	public ArrayList<Voucher> getVouchers(Staff staff) throws SQLException
	{
		return getVoucherManager().getVouchers(staff);
	}

	public ArrayList<Voucher> getVouchers(State state) throws SQLException
	{
		return getVoucherManager().getVouchers(state);
	}

	public String[] getVoucherHeader(long voucherID, int category) throws SQLException
	{
		return getVoucherManager().getVoucherHeader(voucherID, category);
	}
	
	public String[] getLaporanKedudukanKewanganLesen(long licenseID) throws SQLException
	{
		return getVoucherManager().getLaporanKedudukanKewanganLesen(licenseID);
	}
	
	public ArrayList<String[]> getVoucherRecords(long voucherID) throws SQLException
	{
		return getVoucherManager().getVoucherRecords(voucherID);
	}

	public int addVoucherRecord(VoucherRecord voucherRecord) throws SQLException
	{
		return getVoucherRecordManager().addVoucherRecord(voucherRecord);
	}

	public int updateVoucherRecord(VoucherRecord voucherRecord) throws SQLException
	{
		return getVoucherRecordManager().updateVoucherRecord(voucherRecord);
	}

	public int deleteVoucherRecord(VoucherRecord voucherRecord) throws SQLException
	{
		return getVoucherRecordManager().deleteVoucherRecord(voucherRecord);
	}	

	public VoucherRecord getVoucherRecord(int voucherRecordID) throws SQLException
	{
		return getVoucherRecordManager().getVoucherRecord(voucherRecordID);
	}

	public ArrayList<VoucherRecord> getVoucherRecords(Voucher voucher) throws SQLException
	{
		return getVoucherRecordManager().getVoucherRecords(voucher);
	}

	public int addForestDevelopmentContractor(ForestDevelopmentContractor forestDevelopmentContractor) throws SQLException
	{
		return getForestDevelopmentContractorManager().addForestDevelopmentContractor(forestDevelopmentContractor);
	}

	public int deleteForestDevelopmentContractor(ForestDevelopmentContractor forestDevelopmentContractor) throws SQLException
	{
		return getForestDevelopmentContractorManager().deleteForestDevelopmentContractor(forestDevelopmentContractor);
	}

	public int updateForestDevelopmentContractor(ForestDevelopmentContractor forestDevelopmentContractor) throws SQLException
	{
		return getForestDevelopmentContractorManager().updateForestDevelopmentContractor(forestDevelopmentContractor);
	}

	public int updateForestDevelopmentContractorStatus(ForestDevelopmentContractor forestDevelopmentContractor) throws SQLException
	{
		return getForestDevelopmentContractorManager().updateForestDevelopmentContractorStatus(forestDevelopmentContractor);
	}

	public ForestDevelopmentContractor getForestDevelopmentContractor(long forestDevelopmentContractorID) throws SQLException
	{
		return getForestDevelopmentContractorManager().getForestDevelopmentContractor(forestDevelopmentContractorID);
	}

	public ArrayList<ForestDevelopmentContractor> getForestDevelopmentContractors(String status) throws SQLException
	{
		return getForestDevelopmentContractorManager().getForestDevelopmentContractors(status);
	}

	public ArrayList<ForestDevelopmentContractor> getForestDevelopmentContractors(State state, String status) throws SQLException
	{
		return getForestDevelopmentContractorManager().getForestDevelopmentContractors(state, status);
	}
	
	public ArrayList<ForestDevelopmentContractor> getForestDevelopmentContractors(Staff user, String status) throws SQLException
	{
		return getForestDevelopmentContractorManager().getForestDevelopmentContractors(user, status);
	}

	public int addForestDevelopmentContractorSubWorkTypeRecord(ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord) throws SQLException
	{
		return getForestDevelopmentContractorSubWorkTypeRecordManager().addForestDevelopmentContractorSubWorkTypeRecord(forestDevelopmentContractorSubWorkTypeRecord);
	}

	public int deleteForestDevelopmentContractorSubWorkTypeRecord(ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord) throws SQLException
	{
		return getForestDevelopmentContractorSubWorkTypeRecordManager().deleteForestDevelopmentContractorSubWorkTypeRecord(forestDevelopmentContractorSubWorkTypeRecord);
	}

	public int updateForestDevelopmentContractorSubWorkTypeRecordStatus(ForestDevelopmentContractorSubWorkTypeRecord forestDevelopmentContractorSubWorkTypeRecord) throws SQLException
	{
		return getForestDevelopmentContractorSubWorkTypeRecordManager().updateForestDevelopmentContractorSubWorkTypeRecordStatus(forestDevelopmentContractorSubWorkTypeRecord);
	}

	public ForestDevelopmentContractorSubWorkTypeRecord getForestDevelopmentContractorSubWorkTypeRecord(int forestDevelopmentContractorSubWorkTypeRecordID) throws SQLException
	{
		return getForestDevelopmentContractorSubWorkTypeRecordManager().getForestDevelopmentContractorSubWorkTypeRecord(forestDevelopmentContractorSubWorkTypeRecordID);
	}

	public ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> getForestDevelopmentContractorSubWorkTypeRecords(ForestDevelopmentContractor forestDevelopmentContractor) throws SQLException
	{
		return getForestDevelopmentContractorSubWorkTypeRecordManager().getForestDevelopmentContractorSubWorkTypeRecords(forestDevelopmentContractor);
	}

	public ArrayList<ForestDevelopmentContractorSubWorkTypeRecord> getForestDevelopmentContractorSubWorkTypeRecords(String status) throws SQLException
	{
		return getForestDevelopmentContractorSubWorkTypeRecordManager().getForestDevelopmentContractorSubWorkTypeRecords(status);
	}

	public int addLoggingContractor(LoggingContractor loggingContractor) throws SQLException
	{
		return getLoggingContractorManager().addLoggingContractor(loggingContractor);
	}

	public int deleteLoggingContractor(LoggingContractor loggingContractor) throws SQLException
	{
		return getLoggingContractorManager().deleteLoggingContractor(loggingContractor);
	}

	public int updateLoggingContractor(LoggingContractor loggingContractor) throws SQLException
	{
		return getLoggingContractorManager().updateLoggingContractor(loggingContractor);
	}

	public int updateLoggingContractorStatus(LoggingContractor loggingContractor) throws SQLException
	{
		return getLoggingContractorManager().updateLoggingContractorStatus(loggingContractor);
	}

	public LoggingContractor getLoggingContractor(long loggingContractorID) throws SQLException
	{
		return getLoggingContractorManager().getLoggingContractor(loggingContractorID);
	}

	public ArrayList<LoggingContractor> getLoggingContractors(String status) throws SQLException
	{
		return getLoggingContractorManager().getLoggingContractors(status);
	}

	public ArrayList<LoggingContractor> getLoggingContractors(State state, String status) throws SQLException
	{
		return getLoggingContractorManager().getLoggingContractors(state, status);
	}
	
	public ArrayList<LoggingContractor> getLoggingContractors(Staff user, String status) throws SQLException
	{
		return getLoggingContractorManager().getLoggingContractors(user, status);
	}

	public int addRenew(Renew renew) throws SQLException
	{
		return getRenewManager().addRenew(renew);
	}

	public int deleteRenew(Renew renew) throws SQLException
	{
		return getRenewManager().deleteRenew(renew);
	}	

	public Renew getRenew(long renewID) throws SQLException
	{
		return getRenewManager().getRenew(renewID);
	}

	public Renew getRenew(Receipt receipt) throws SQLException
	{
		return getRenewManager().getRenew(receipt);
	}

	public ArrayList<Renew> getRenews(ForestDevelopmentContractor forestDevelopmentContractor, String status) throws SQLException
	{
		return getRenewManager().getRenews(forestDevelopmentContractor, status);
	}

	public ArrayList<Renew> getRenews(LoggingContractor loggingContractor, String status) throws SQLException
	{
		return getRenewManager().getRenews(loggingContractor, status);
	}

	public ArrayList<Renew> getRenews(License license, String status) throws SQLException
	{
		return getRenewManager().getRenews(license, status);
	}

	public ArrayList<Renew> getRenews(Permit permit, String status) throws SQLException
	{
		return getRenewManager().getRenews(permit, status);
	}

	public ArrayList<Renew> getRenews(String status) throws SQLException
	{
		return getRenewManager().getRenews(status);
	}

	public ArrayList<Renew> getRenews(String status, Date date) throws SQLException
	{
		return getRenewManager().getRenews(status, date);
	}

	public ArrayList<Renew> getRenews(String type, String status, Date date) throws SQLException
	{
		return getRenewManager().getRenews(type, status, date);
	}
}