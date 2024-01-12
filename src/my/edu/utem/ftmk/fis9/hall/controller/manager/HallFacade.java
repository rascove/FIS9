package my.edu.utem.ftmk.fis9.hall.controller.manager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.hall.model.Log;
import my.edu.utem.ftmk.fis9.hall.model.MainRevenueTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.SmallProductTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.SpecialTransferPassRecord;
import my.edu.utem.ftmk.fis9.hall.model.TransferPass;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.revenue.model.Journal;
import my.edu.utem.ftmk.fis9.revenue.model.License;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;

/**
 * @author Nor Azman Mat Ariff
 */
public class HallFacade extends AbstractFacade
{
	private TransferPassManager transferPassManager;
	private MainRevenueTransferPassRecordManager mainRevenueTransferPassRecordManager;
	private SmallProductTransferPassRecordManager smallProductTransferPassRecordManager;
	private SpecialTransferPassRecordManager specialTransferPassRecordManager;
	private LogManager logManager;

	private TransferPassManager getTransferPassManager()
	{
		if (transferPassManager == null)
			transferPassManager = new TransferPassManager(this);

		return transferPassManager;
	}
	
	private MainRevenueTransferPassRecordManager getMainRevenueTransferPassRecordManager()
	{
		if (mainRevenueTransferPassRecordManager == null)
			mainRevenueTransferPassRecordManager = new MainRevenueTransferPassRecordManager(this);

		return mainRevenueTransferPassRecordManager;
	}
	
	private SmallProductTransferPassRecordManager getSmallProductTransferPassRecordManager()
	{
		if (smallProductTransferPassRecordManager == null)
			smallProductTransferPassRecordManager = new SmallProductTransferPassRecordManager(this);

		return smallProductTransferPassRecordManager;
	}
	
	private SpecialTransferPassRecordManager getSpecialTransferPassRecordManager()
	{
		if (specialTransferPassRecordManager == null)
			specialTransferPassRecordManager = new SpecialTransferPassRecordManager(this);

		return specialTransferPassRecordManager;
	}
	
	private LogManager getLogManager()
	{
		if (logManager == null)
			logManager = new LogManager(this);

		return logManager;
	}

	@Override
	protected PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return getPreparedStatement(sql);
	}

	public int addTransferPass(TransferPass transferPass, boolean ignoreDuplicate) throws SQLException
	{
		return getTransferPassManager().addTransferPass(transferPass, ignoreDuplicate);
	}
	
	public boolean selectTransferPassNo(TransferPass transferPass) throws SQLException
	{
		return getTransferPassManager().selectTransferPassNo(transferPass);
	}
	
	public int deleteTransferPass(TransferPass transferPass) throws SQLException
	{
		return getTransferPassManager().deleteTransferPass(transferPass);
	}	
	
	public int updateTransferPass(TransferPass transferPass) throws SQLException
	{
		return getTransferPassManager().updateTransferPass(transferPass);
	}	
	
	public int updateTransferPassStatus(TransferPass transferPass) throws SQLException
	{
		return getTransferPassManager().updateTransferPassStatus(transferPass);
	}	
	
	public int updateRoyaltyAndCess(TransferPass transferPass) throws SQLException
	{
		return getTransferPassManager().updateRoyaltyAndCess(transferPass);
	}	
	
	public int updateTransferPassJournal(TransferPass transferPass) throws SQLException
	{
		return getTransferPassManager().updateTransferPassJournal(transferPass);
	}	
	
	public TransferPass getTransferPass(long transferPassID) throws SQLException
	{
		return getTransferPassManager().getTransferPass(transferPassID);
	}
	
	public ArrayList<TransferPass> getTransferPasses(String status) throws SQLException
	{
		return getTransferPassManager().getTransferPasses(status);
	}
	
	public ArrayList<TransferPass> getTransferPasses(Tagging tagging) throws SQLException
	{
		return getTransferPassManager().getTransferPasses(tagging);
	}
	
	public ArrayList<TransferPass> getTransferPasses(Journal journal) throws SQLException
	{
		return getTransferPassManager().getTransferPasses(journal);
	}
	
	public ArrayList<TransferPass> getTransferPasses(License license, String status) throws SQLException
	{
		return getTransferPassManager().getTransferPasses(license, status);
	}
	
	public ArrayList<TransferPass> getTransferPasses(License license) throws SQLException
	{
		return getTransferPassManager().getTransferPasses(license);
	}
	
	public ArrayList<TransferPass> getTransferPasses(State state, String status) throws SQLException
	{
		return getTransferPassManager().getTransferPasses(state, status);
	}	
	
	public ArrayList<String[]> getBigSizeLogShuttleReport(int month, int year) throws SQLException
	{
		return getTransferPassManager().getBigSizeLogShuttleReport(month, year);
	}
	
	public ArrayList<String[]> getSmallSizeLogShuttleReport(int month, int year) throws SQLException
	{
		return getTransferPassManager().getSmallSizeLogShuttleReport(month, year);
	}
	
	public String[] getTransferPassString(long transferPassID) throws SQLException
	{
		return getTransferPassManager().getTransferPassString(transferPassID);
	}
	
	public String[] getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenHeaderInfo(long licenseID) throws SQLException
	{
		return getTransferPassManager().getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenHeaderInfo(licenseID);
	}
	
	public String[][] getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenMainTable(Date startDate, Date endDate, long licenseID, int logSize) throws SQLException
	{
		return getTransferPassManager().getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenMainTable(startDate, endDate, licenseID, logSize);
	}
	
	public ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenTypeAPage1andPage2(Date startDate, Date endDate, long licenseID, int logSize) throws SQLException
	{
		return getTransferPassManager().getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenTypeAPage1andPage2(startDate, endDate, licenseID, logSize);
	}
	
	public ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenJarasBesar(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getTransferPassManager().getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenJarasBesar(startDate, endDate, licenseID);
	}
	
	public ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenJarasKecil(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getTransferPassManager().getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenJarasKecil(startDate, endDate, licenseID);
	}
	
	public ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPengeluaranTableBigLog(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getTransferPassManager().getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPengeluaranTableBigLog(startDate, endDate, licenseID);
	}
	
	public ArrayList<String[]> getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPengeluaranTableSmallLog(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getTransferPassManager().getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPengeluaranTableSmallLog(startDate, endDate, licenseID);
	}
	
	public String[] getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPembayaranTableBigLog(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getTransferPassManager().getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPembayaranTableBigLog(startDate, endDate, licenseID);
	}
	
	public String[] getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPembayaranTableSmallLog(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getTransferPassManager().getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenRingkasanPembayaranTableSmallLog(startDate, endDate, licenseID);
	}
	
	public String[][] getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenDetailTransferPass(Date startDate, Date endDate, long licenseID) throws SQLException, ParseException
	{
		return getTransferPassManager().getRingkasanPasMemindahYangDikeluarkanDiBalaiDariTarikhMulaHinggaTarikhAkhirBagiPelesenDetailTransferPass(startDate, endDate, licenseID);
	}
	
	public String[] getLaporanPengeluaranKayuBalakDiBalaiHeader(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getTransferPassManager().getLaporanPengeluaranKayuBalakDiBalaiHeader(startDate,endDate, licenseID);
	}
	
	public ArrayList<String[]> getLaporanPengeluaranKayuBalakDiBalai(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getTransferPassManager().getLaporanPengeluaranKayuBalakDiBalai(startDate, endDate, licenseID);
	}
	
	public ArrayList<String[]> getTransferPassRecordString(TransferPass transferPass) throws SQLException
	{
		return getTransferPassManager().getTransferPassRecordString(transferPass);
	}
	
	public String[] getHeaderRingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(long licenseID) throws SQLException
	{
		return getTransferPassManager().getHeaderRingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(licenseID);
	}
	
	public ArrayList<String[]> getContentRingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(Date startDate, Date endDate, long licenseID) throws SQLException
	{
		return getTransferPassManager().getContentRingkasanPengeluaranHasilHutanDariBulanDanTahunMulaHinggaBulanDanTahunAkhir(startDate, endDate, licenseID);
	}
	
	public int addLog(Log log, boolean ignoreDuplicate) throws SQLException
	{
		return getLogManager().addLog(log, ignoreDuplicate);
	}
	
	public int addLogs(ArrayList<Log> logs) throws SQLException
	{
		return getLogManager().addLogs(logs);
	}
	
	public int updateLog(Log log) throws SQLException
	{
		return getLogManager().updateLog(log);
	}
	
	public int updateLogStatus(Log log) throws SQLException
	{
		return getLogManager().updateLogStatus(log);
	}
	
	public int deleteLog(Log log) throws SQLException
	{
		return getLogManager().deleteLog(log);
	}	
	
	public Log getLog(long logID) throws SQLException
	{
		return getLogManager().getLog(logID);
	}
	
	public ArrayList<Log> getLogs(String status) throws SQLException
	{
		return getLogManager().getLogs(status);
	}
	
	public ArrayList<Log> getLogs(Tagging tagging) throws SQLException
	{
		return getLogManager().getLogs(tagging);
	}
	
	public ArrayList<Log> getLogs(Tagging tagging, String status) throws SQLException
	{
		return getLogManager().getLogs(tagging, status);
	}
	
	public int addMainRevenueTransferPassRecord(MainRevenueTransferPassRecord mainRevenueTransferPassRecord) throws SQLException
	{
		return getMainRevenueTransferPassRecordManager().addMainRevenueTransferPassRecord(mainRevenueTransferPassRecord);
	}
	
	public int updateMainRevenueTransferPassRecord(MainRevenueTransferPassRecord mainRevenueTransferPassRecord) throws SQLException
	{
		return getMainRevenueTransferPassRecordManager().updateMainRevenueTransferPassRecord(mainRevenueTransferPassRecord);
	}
	
	public int deleteMainRevenueTransferPassRecord(MainRevenueTransferPassRecord mainRevenueTransferPassRecord) throws SQLException
	{
		return getMainRevenueTransferPassRecordManager().deleteMainRevenueTransferPassRecord(mainRevenueTransferPassRecord);
	}	
	
	public MainRevenueTransferPassRecord getMainRevenueTransferPassRecord(long mainRevenueTransferPassRecordID) throws SQLException
	{
		return getMainRevenueTransferPassRecordManager().getMainRevenueTransferPassRecord(mainRevenueTransferPassRecordID);
	}
	
	public ArrayList<MainRevenueTransferPassRecord> getMainRevenueTransferPassRecords() throws SQLException
	{
		return getMainRevenueTransferPassRecordManager().getMainRevenueTransferPassRecords();
	}
	
	public ArrayList<MainRevenueTransferPassRecord> getMainRevenueTransferPassRecords(TransferPass transferPass) throws SQLException
	{
		return getMainRevenueTransferPassRecordManager().getMainRevenueTransferPassRecords(transferPass);
	}
	
	public int addSmallProductTransferPassRecord(SmallProductTransferPassRecord smallProductTransferPassRecord) throws SQLException
	{
		return getSmallProductTransferPassRecordManager().addSmallProductTransferPassRecord(smallProductTransferPassRecord);
	}
	
	public int updateSmallProductTransferPassRecord(SmallProductTransferPassRecord smallProductTransferPassRecord) throws SQLException
	{
		return getSmallProductTransferPassRecordManager().updateSmallProductTransferPassRecord(smallProductTransferPassRecord);
	}
	
	public int deleteSmallProductTransferPassRecord(SmallProductTransferPassRecord smallProductTransferPassRecord) throws SQLException
	{
		return getSmallProductTransferPassRecordManager().deleteSmallProductTransferPassRecord(smallProductTransferPassRecord);
	}	
	
	public SmallProductTransferPassRecord getSmallProductTransferPassRecord(long smallProductTransferPassRecordID) throws SQLException
	{
		return getSmallProductTransferPassRecordManager().getSmallProductTransferPassRecord(smallProductTransferPassRecordID);
	}
	
	public ArrayList<SmallProductTransferPassRecord> getSmallProductTransferPassRecords() throws SQLException
	{
		return getSmallProductTransferPassRecordManager().getSmallProductTransferPassRecords();
	}
	
	public ArrayList<SmallProductTransferPassRecord> getSmallProductTransferPassRecords(TransferPass transferPass) throws SQLException
	{
		return getSmallProductTransferPassRecordManager().getSmallProductTransferPassRecords(transferPass);
	}
	
	public int addSpecialTransferPassRecord(SpecialTransferPassRecord specialTransferPassRecord) throws SQLException
	{
		return getSpecialTransferPassRecordManager().addSpecialTransferPassRecord(specialTransferPassRecord);
	}
	
	public int updateSpecialTransferPassRecord(SpecialTransferPassRecord specialTransferPassRecord) throws SQLException
	{
		return getSpecialTransferPassRecordManager().updateSpecialTransferPassRecord(specialTransferPassRecord);
	}
	
	public SpecialTransferPassRecord getSpecialTransferPassRecord(long specialTransferPassRecordID) throws SQLException
	{
		return getSpecialTransferPassRecordManager().getSpecialTransferPassRecord(specialTransferPassRecordID);
	}
	
	public ArrayList<SpecialTransferPassRecord> getSpecialTransferPassRecords() throws SQLException
	{
		return getSpecialTransferPassRecordManager().getSpecialTransferPassRecords();
	}
	
	public ArrayList<SpecialTransferPassRecord> getSpecialTransferPassRecords(TransferPass transferPass) throws SQLException
	{
		return getSpecialTransferPassRecordManager().getSpecialTransferPassRecords(transferPass);
	}	

}