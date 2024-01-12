package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractFacade;
import my.edu.utem.ftmk.fis9.maintenance.model.AbstractsSpeciesGroup;
import my.edu.utem.ftmk.fis9.maintenance.model.AbstractsSpeciesGroupRecord;
import my.edu.utem.ftmk.fis9.maintenance.model.AreaStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.Aspect;
import my.edu.utem.ftmk.fis9.maintenance.model.Banana;
import my.edu.utem.ftmk.fis9.maintenance.model.Bank;
import my.edu.utem.ftmk.fis9.maintenance.model.BursaryVot;
import my.edu.utem.ftmk.fis9.maintenance.model.ChequeType;
import my.edu.utem.ftmk.fis9.maintenance.model.Contractor;
import my.edu.utem.ftmk.fis9.maintenance.model.CuttingOption;
import my.edu.utem.ftmk.fis9.maintenance.model.DepartmentVot;
import my.edu.utem.ftmk.fis9.maintenance.model.Designation;
import my.edu.utem.ftmk.fis9.maintenance.model.District;
import my.edu.utem.ftmk.fis9.maintenance.model.Dominance;
import my.edu.utem.ftmk.fis9.maintenance.model.Elevation;
import my.edu.utem.ftmk.fis9.maintenance.model.Fertility;
import my.edu.utem.ftmk.fis9.maintenance.model.Forest;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestCategory;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestDevelopmentSubWorkType;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestDevelopmentWorkType;
import my.edu.utem.ftmk.fis9.maintenance.model.ForestType;
import my.edu.utem.ftmk.fis9.maintenance.model.Form;
import my.edu.utem.ftmk.fis9.maintenance.model.Geology;
import my.edu.utem.ftmk.fis9.maintenance.model.Ginger;
import my.edu.utem.ftmk.fis9.maintenance.model.Hall;
import my.edu.utem.ftmk.fis9.maintenance.model.HallOfficer;
import my.edu.utem.ftmk.fis9.maintenance.model.Hammer;
import my.edu.utem.ftmk.fis9.maintenance.model.HammerType;
import my.edu.utem.ftmk.fis9.maintenance.model.LicenseStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.LicenseType;
import my.edu.utem.ftmk.fis9.maintenance.model.LogQuality;
import my.edu.utem.ftmk.fis9.maintenance.model.LogSize;
import my.edu.utem.ftmk.fis9.maintenance.model.MainRevenueRoyaltyRate;
import my.edu.utem.ftmk.fis9.maintenance.model.Module;
import my.edu.utem.ftmk.fis9.maintenance.model.PaymentType;
import my.edu.utem.ftmk.fis9.maintenance.model.PermitType;
import my.edu.utem.ftmk.fis9.maintenance.model.PlotType;
import my.edu.utem.ftmk.fis9.maintenance.model.ProductGroup;
import my.edu.utem.ftmk.fis9.maintenance.model.ProtectedSpecies;
import my.edu.utem.ftmk.fis9.maintenance.model.ProtectedType;
import my.edu.utem.ftmk.fis9.maintenance.model.Range;
import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationSpecies;
import my.edu.utem.ftmk.fis9.maintenance.model.RegenerationType;
import my.edu.utem.ftmk.fis9.maintenance.model.Region;
import my.edu.utem.ftmk.fis9.maintenance.model.Resam;
import my.edu.utem.ftmk.fis9.maintenance.model.Silara;
import my.edu.utem.ftmk.fis9.maintenance.model.SlopeLocation;
import my.edu.utem.ftmk.fis9.maintenance.model.SmallProduct;
import my.edu.utem.ftmk.fis9.maintenance.model.SmallProductRoyaltyRate;
import my.edu.utem.ftmk.fis9.maintenance.model.SoilStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.SoilType;
import my.edu.utem.ftmk.fis9.maintenance.model.Species;
import my.edu.utem.ftmk.fis9.maintenance.model.SpeciesType;
import my.edu.utem.ftmk.fis9.maintenance.model.Staff;
import my.edu.utem.ftmk.fis9.maintenance.model.State;
import my.edu.utem.ftmk.fis9.maintenance.model.TaggingType;
import my.edu.utem.ftmk.fis9.maintenance.model.Tender;
import my.edu.utem.ftmk.fis9.maintenance.model.TimberGroup;
import my.edu.utem.ftmk.fis9.maintenance.model.TimberType;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionForm;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionFormMapDepartmentVot;
import my.edu.utem.ftmk.fis9.maintenance.model.TransactionFormMapTrustFund;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeLimit;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeStatus;
import my.edu.utem.ftmk.fis9.maintenance.model.TreeType;
import my.edu.utem.ftmk.fis9.maintenance.model.TrustFund;
import my.edu.utem.ftmk.fis9.maintenance.model.Unit;
import my.edu.utem.ftmk.fis9.maintenance.model.VineSpreadth;
import my.edu.utem.ftmk.fis9.maintenance.model.VolumeDiameter;
import my.edu.utem.ftmk.fis9.maintenance.model.VolumeGroup;
import my.edu.utem.ftmk.fis9.maintenance.model.VolumeSpecies;
import my.edu.utem.ftmk.fis9.postfelling.model.PostFellingSurvey;
import my.edu.utem.ftmk.fis9.prefelling.model.PreFellingSurvey;
import my.edu.utem.ftmk.fis9.tagging.model.Tagging;

/**
 * @author Satrya Fajri Pratama
 */
public class MaintenanceFacade extends AbstractFacade
{
	private ModuleManager moduleManager;
	private FormManager formManager;
	private DesignationManager designationManager;
	private StaffManager staffManager;
	private StateManager stateManager;
	private DistrictManager districtManager;
	private RegionManager regionManager;
	private RangeManager rangeManager;
	private HallManager hallManager;
	private ForestManager forestManager;
	private ForestTypeManager forestTypeManager;
	private SoilTypeManager soilTypeManager;
	private GeologyManager geologyManager;
	private AreaStatusManager areaStatusManager;
	private SoilStatusManager soilStatusManager;
	private AspectManager aspectManager;
	private SlopeLocationManager slopeLocationManager;
	private ElevationManager elevationManager;
	private ResamManager resamManager;
	private GingerManager gingerManager;
	private BananaManager bananaManager;
	private TimberGroupManager timberGroupManager;
	private TimberTypeManager timberTypeManager;
	private SpeciesTypeManager speciesTypeManager;
	private SpeciesManager speciesManager;
	private RegenerationTypeManager regenerationTypeManager;
	private RegenerationSpeciesManager regenerationSpeciesManager;
	private ProtectedTypeManager protectedTypeManager;
	private ProtectedSpeciesManager protectedSpeciesManager;
	private TreeLimitManager treeLimitManager;
	private PlotTypeManager plotTypeManager;
	private LogQualityManager logQualityManager;
	private FertilityManager fertilityManager;
	private VineSpreadthManager vineSpreadthManager;
	private TreeStatusManager treeStatusManager;
	private SilaraManager silaraManager;
	private DominanceManager dominanceManager;
	private CuttingOptionManager cuttingOptionManager;
	private HammerTypeManager hammerTypeManager;
	private HammerManager hammerManager;
	private TaggingTypeManager taggingTypeManager;
	private TreeTypeManager treeTypeManager;
	private VolumeGroupManager volumeGroupManager;
	private VolumeSpeciesManager volumeSpeciesManager;
	private VolumeDiameterManager volumeDiameterManager;
	private ContractorManager contractorManager;
	private TenderManager tenderManager;
	private BursaryVotManager bursaryVotManager;
	private DepartmentVotManager departmentVotManager;
	private TrustFundManager trustFundManager;
	private ProductGroupManager productGroupManager;
	private SmallProductManager smallProductManager;
	private TransactionFormManager transactionFormManager;
	private TransactionFormMapDepartmentVotManager transactionFormMapDepartmentVotManager;
	private TransactionFormMapTrustFundManager transactionFormMapTrustFundManager;	
	private PaymentTypeManager paymentTypeManager;
	private BankManager bankManager;
	private UnitManager unitManager;
	private ChequeTypeManager chequeTypeManager;
	private ForestCategoryManager forestCategoryManager;
	private LicenseStatusManager licenseStatusManager;
	private LicenseTypeManager licenseTypeManager;
	private MainRevenueRoyaltyRateManager mainRevenueRoyaltyRateManager;
	private SmallProductRoyaltyRateManager smallProductRoyaltyRateManager;
	private LogSizeManager logSizeManager;
	private HallOfficerManager hallOfficerManager;
	private ForestDevelopmentWorkTypeManager forestDevelopmentWorkTypeManager;
	private ForestDevelopmentSubWorkTypeManager forestDevelopmentSubWorkTypeManager;
	private PermitTypeManager permitTypeManager;
	private AbstractsSpeciesGroupManager abstractsSpeciesGroupManager;
	private AbstractsSpeciesGroupRecordManager abstractsSpeciesGroupRecordManager;
	
	private ModuleManager getModuleManager()
	{
		return moduleManager == null ? moduleManager = new ModuleManager(this) : moduleManager;
	}

	private FormManager getFormManager()
	{
		return formManager == null ? formManager = new FormManager(this) : formManager;
	}

	private DesignationManager getDesignationManager()
	{
		return designationManager == null ? designationManager = new DesignationManager(this) : designationManager;
	}

	private StaffManager getStaffManager()
	{
		return staffManager == null ? staffManager = new StaffManager(this) : staffManager;
	}

	private StateManager getStateManager()
	{
		return stateManager == null ? stateManager = new StateManager(this) : stateManager;
	}

	private DistrictManager getDistrictManager()
	{
		return districtManager == null ? districtManager = new DistrictManager(this) : districtManager;
	}

	private RegionManager getRegionManager()
	{
		return regionManager == null ? regionManager = new RegionManager(this) : regionManager;
	}

	private RangeManager getRangeManager()
	{
		return rangeManager == null ? rangeManager = new RangeManager(this) : rangeManager;
	}

	private HallManager getHallManager()
	{
		return hallManager == null ? hallManager = new HallManager(this) : hallManager;
	}

	private ForestManager getForestManager()
	{
		return forestManager == null ? forestManager = new ForestManager(this) : forestManager;
	}

	private ForestTypeManager getForestTypeManager()
	{
		return forestTypeManager == null ? forestTypeManager = new ForestTypeManager(this) : forestTypeManager;
	}

	private SoilTypeManager getSoilTypeManager()
	{
		return soilTypeManager == null ? soilTypeManager = new SoilTypeManager(this) : soilTypeManager;
	}

	private GeologyManager getGeologyManager()
	{
		return geologyManager == null ? geologyManager = new GeologyManager(this) : geologyManager;
	}

	private AreaStatusManager getAreaStatusManager()
	{
		return areaStatusManager == null ? areaStatusManager = new AreaStatusManager(this) : areaStatusManager;
	}

	private SoilStatusManager getSoilStatusManager()
	{
		return soilStatusManager == null ? soilStatusManager = new SoilStatusManager(this) : soilStatusManager;
	}

	private AspectManager getAspectManager()
	{
		return aspectManager == null ? aspectManager = new AspectManager(this) : aspectManager;
	}

	private SlopeLocationManager getSlopeLocationManager()
	{
		return slopeLocationManager == null ? slopeLocationManager = new SlopeLocationManager(this) : slopeLocationManager;
	}

	private ElevationManager getElevationManager()
	{
		return elevationManager == null ? elevationManager = new ElevationManager(this) : elevationManager;
	}

	private ResamManager getResamManager()
	{
		return resamManager == null ? resamManager = new ResamManager(this) : resamManager;
	}

	private GingerManager getGingerManager()
	{
		return gingerManager == null ? gingerManager = new GingerManager(this) : gingerManager;
	}

	private BananaManager getBananaManager()
	{
		return bananaManager == null ? bananaManager = new BananaManager(this) : bananaManager;
	}

	private TimberGroupManager getTimberGroupManager()
	{
		return timberGroupManager == null ? timberGroupManager = new TimberGroupManager(this) : timberGroupManager;
	}

	private TimberTypeManager getTimberTypeManager()
	{
		return timberTypeManager == null ? timberTypeManager = new TimberTypeManager(this) : timberTypeManager;
	}

	private SpeciesTypeManager getSpeciesTypeManager()
	{
		return speciesTypeManager == null ? speciesTypeManager = new SpeciesTypeManager(this) : speciesTypeManager;
	}

	private SpeciesManager getSpeciesManager()
	{
		return speciesManager == null ? speciesManager = new SpeciesManager(this) : speciesManager;
	}

	private RegenerationTypeManager getRegenerationTypeManager()
	{
		return regenerationTypeManager == null ? regenerationTypeManager = new RegenerationTypeManager(this) : regenerationTypeManager;
	}

	private RegenerationSpeciesManager getRegenerationSpeciesManager()
	{
		return regenerationSpeciesManager == null ? regenerationSpeciesManager = new RegenerationSpeciesManager(this) : regenerationSpeciesManager;
	}

	private ProtectedTypeManager getProtectedTypeManager()
	{
		return protectedTypeManager == null ? protectedTypeManager = new ProtectedTypeManager(this) : protectedTypeManager;
	}

	private ProtectedSpeciesManager getProtectedSpeciesManager()
	{
		return protectedSpeciesManager == null ? protectedSpeciesManager = new ProtectedSpeciesManager(this) : protectedSpeciesManager;
	}

	private TreeLimitManager getTreeLimitManager()
	{
		return treeLimitManager == null ? treeLimitManager = new TreeLimitManager(this) : treeLimitManager;
	}

	private PlotTypeManager getPlotTypeManager()
	{
		return plotTypeManager == null ? plotTypeManager = new PlotTypeManager(this) : plotTypeManager;
	}

	private LogQualityManager getLogQualityManager()
	{
		return logQualityManager == null ? logQualityManager = new LogQualityManager(this) : logQualityManager;
	}

	private FertilityManager getFertilityManager()
	{
		return fertilityManager == null ? fertilityManager = new FertilityManager(this) : fertilityManager;
	}

	private VineSpreadthManager getVineSpreadthManager()
	{
		return vineSpreadthManager == null ? vineSpreadthManager = new VineSpreadthManager(this) : vineSpreadthManager;
	}

	private TreeStatusManager getTreeStatusManager()
	{
		return treeStatusManager == null ? treeStatusManager = new TreeStatusManager(this) : treeStatusManager;
	}

	private SilaraManager getSilaraManager()
	{
		return silaraManager == null ? silaraManager = new SilaraManager(this) : silaraManager;
	}

	private DominanceManager getDominanceManager()
	{
		return dominanceManager == null ? dominanceManager = new DominanceManager(this) : dominanceManager;
	}

	private CuttingOptionManager getCuttingOptionManager()
	{
		return cuttingOptionManager == null ? cuttingOptionManager = new CuttingOptionManager(this) : cuttingOptionManager;
	}

	private HammerTypeManager getHammerTypeManager()
	{
		return hammerTypeManager == null ? hammerTypeManager = new HammerTypeManager(this) : hammerTypeManager;
	}

	private HammerManager getHammerManager()
	{
		return hammerManager == null ? hammerManager = new HammerManager(this) : hammerManager;
	}

	private TaggingTypeManager getTaggingTypeManager()
	{
		return taggingTypeManager == null ? taggingTypeManager = new TaggingTypeManager(this) : taggingTypeManager;
	}

	private TreeTypeManager getTreeTypeManager()
	{
		return treeTypeManager == null ? treeTypeManager = new TreeTypeManager(this) : treeTypeManager;
	}

	private VolumeGroupManager getVolumeGroupManager()
	{
		return volumeGroupManager == null ? volumeGroupManager = new VolumeGroupManager(this) : volumeGroupManager;
	}

	private VolumeSpeciesManager getVolumeSpeciesManager()
	{
		return volumeSpeciesManager == null ? volumeSpeciesManager = new VolumeSpeciesManager(this) : volumeSpeciesManager;
	}

	private VolumeDiameterManager getVolumeDiameterManager()
	{
		return volumeDiameterManager == null ? volumeDiameterManager = new VolumeDiameterManager(this) : volumeDiameterManager;
	}

	private ContractorManager getContractorManager()
	{
		return contractorManager == null ? contractorManager = new ContractorManager(this) : contractorManager;
	}

	private TenderManager getTenderManager()
	{
		return tenderManager == null ? tenderManager = new TenderManager(this) : tenderManager;
	}

	private BursaryVotManager getBursaryVotManager()
	{
		return bursaryVotManager == null ? bursaryVotManager = new BursaryVotManager(this) : bursaryVotManager;
	}

	private DepartmentVotManager getDepartmentVotManager()
	{
		return departmentVotManager == null ? departmentVotManager = new DepartmentVotManager(this) : departmentVotManager;
	}

	private TrustFundManager getTrustFundManager()
	{
		return trustFundManager == null ? trustFundManager = new TrustFundManager(this) : trustFundManager;
	}

	private ProductGroupManager getProductGroupManager()
	{
		return productGroupManager == null ? productGroupManager = new ProductGroupManager(this) : productGroupManager;
	}

	private SmallProductManager getSmallProductManager()
	{
		return smallProductManager == null ? smallProductManager = new SmallProductManager(this) : smallProductManager;
	}

	private TransactionFormManager getTransactionFormManager()
	{
		return transactionFormManager == null ? transactionFormManager = new TransactionFormManager(this) : transactionFormManager;
	}

	private TransactionFormMapDepartmentVotManager getTransactionFormMapDepartmentVotManager()
	{
		return transactionFormMapDepartmentVotManager == null ? transactionFormMapDepartmentVotManager = new TransactionFormMapDepartmentVotManager(this) : transactionFormMapDepartmentVotManager;
	}

	private TransactionFormMapTrustFundManager getTransactionFormMapTrustFundManager()
	{
		return transactionFormMapTrustFundManager == null ? transactionFormMapTrustFundManager = new TransactionFormMapTrustFundManager(this) : transactionFormMapTrustFundManager;
	}

	private PaymentTypeManager getPaymentTypeManager()
	{
		return paymentTypeManager == null ? paymentTypeManager = new PaymentTypeManager(this) : paymentTypeManager;
	}

	private BankManager getBankManager()
	{
		return bankManager == null ? bankManager = new BankManager(this) : bankManager;
	}

	private UnitManager getUnitManager()
	{
		return unitManager == null ? unitManager = new UnitManager(this) : unitManager;
	}

	private ChequeTypeManager getChequeTypeManager()
	{
		return chequeTypeManager == null ? chequeTypeManager = new ChequeTypeManager(this) : chequeTypeManager;
	}

	private ForestCategoryManager getForestCategoryManager()
	{
		return forestCategoryManager == null ? forestCategoryManager = new ForestCategoryManager(this) : forestCategoryManager;
	}

	private LicenseStatusManager getLicenseStatusManager()
	{
		return licenseStatusManager == null ? licenseStatusManager = new LicenseStatusManager(this) : licenseStatusManager;
	}

	private LicenseTypeManager getLicenseTypeManager()
	{
		return licenseTypeManager == null ? licenseTypeManager = new LicenseTypeManager(this) : licenseTypeManager;
	}

	private MainRevenueRoyaltyRateManager getMainRevenueRoyaltyRateManager()
	{
		return mainRevenueRoyaltyRateManager == null ? mainRevenueRoyaltyRateManager = new MainRevenueRoyaltyRateManager(this) : mainRevenueRoyaltyRateManager;
	}

	private SmallProductRoyaltyRateManager getSmallProductRoyaltyRateManager()
	{
		return smallProductRoyaltyRateManager == null ? smallProductRoyaltyRateManager = new SmallProductRoyaltyRateManager(this) : smallProductRoyaltyRateManager;
	}

	private LogSizeManager getLogSizeManager()
	{
		return logSizeManager == null ? logSizeManager = new LogSizeManager(this) : logSizeManager;
	}

	private HallOfficerManager getHallOfficerManager()
	{
		return hallOfficerManager == null ? hallOfficerManager = new HallOfficerManager(this) : hallOfficerManager;
	}

	private ForestDevelopmentWorkTypeManager getForestDevelopmentWorkTypeManager()
	{
		return forestDevelopmentWorkTypeManager == null ? forestDevelopmentWorkTypeManager = new ForestDevelopmentWorkTypeManager(this) : forestDevelopmentWorkTypeManager;
	}

	private ForestDevelopmentSubWorkTypeManager getForestDevelopmentSubWorkTypeManager()
	{
		return forestDevelopmentSubWorkTypeManager == null ? forestDevelopmentSubWorkTypeManager = new ForestDevelopmentSubWorkTypeManager(this) : forestDevelopmentSubWorkTypeManager;
	}

	private PermitTypeManager getPermitTypeManager()
	{
		return permitTypeManager == null ? permitTypeManager = new PermitTypeManager(this) : permitTypeManager;
	}
	
	private AbstractsSpeciesGroupManager getAbstractsSpeciesGroupManager()
	{
		return abstractsSpeciesGroupManager == null ? abstractsSpeciesGroupManager = new AbstractsSpeciesGroupManager(this) : abstractsSpeciesGroupManager;
	}
	
	private AbstractsSpeciesGroupRecordManager getAbstractsSpeciesGroupRecordManager()
	{
		return abstractsSpeciesGroupRecordManager == null ? abstractsSpeciesGroupRecordManager = new AbstractsSpeciesGroupRecordManager(this) : abstractsSpeciesGroupRecordManager;
	}

	@Override
	protected PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return getPreparedStatement(sql);
	}

	ArrayList<Form> getForms(Module module) throws SQLException
	{
		return getFormManager().getForms(module);
	}

	ArrayList<Form> getForms(Designation designation) throws SQLException
	{
		return getFormManager().getForms(designation);
	}

	ArrayList<Region> getRegions(State state) throws SQLException
	{
		return getRegionManager().getRegions(state);
	}

	ArrayList<Range> getRanges(District district) throws SQLException
	{
		return getRangeManager().getRanges(district);
	}

	ArrayList<RegenerationSpecies> getRegenerationSpeciesList(RegenerationType regenerationType) throws SQLException
	{
		return getRegenerationSpeciesManager().getRegenerationSpeciesList(regenerationType);
	}

	ArrayList<ProtectedSpecies> getProtectedSpeciesList(ProtectedType protectedType) throws SQLException
	{
		return getProtectedSpeciesManager().getProtectedSpeciesList(protectedType);
	}

	ArrayList<VolumeSpecies> getVolumeSpeciesList(VolumeGroup volumeGroup) throws SQLException
	{
		return getVolumeSpeciesManager().getVolumeSpeciesList(volumeGroup);
	}

	ArrayList<VolumeDiameter> getVolumeDiameterList(VolumeGroup volumeGroup) throws SQLException
	{
		return getVolumeDiameterManager().getVolumeDiameterList(volumeGroup);
	}

	public ArrayList<Module> getModules() throws SQLException
	{
		return getModuleManager().getModules();
	}

	public int addDesignation(Designation designation) throws SQLException
	{
		return getDesignationManager().addDesignation(designation);
	}

	public int updateDesignation(Designation designation) throws SQLException
	{
		return getDesignationManager().updateDesignation(designation);
	}

	public ArrayList<Designation> getDesignations() throws SQLException
	{
		return getDesignationManager().getDesignations();
	}

	public int addStaff(Staff staff) throws SQLException
	{
		return getStaffManager().addStaff(staff);
	}

	public int updateStaff(Staff staff, boolean passwordChanged) throws SQLException
	{
		return getStaffManager().updateStaff(staff, passwordChanged);
	}

	public Staff getStaff(String staffID, String password) throws SQLException
	{
		return getStaffManager().getStaff(staffID, password);
	}

	public ArrayList<Staff> getStaffs() throws SQLException
	{
		return getStaffManager().getStaffs();
	}

	public ArrayList<Staff> getStaffs(Designation designation) throws SQLException
	{
		return getStaffManager().getStaffs(designation);
	}

	public ArrayList<Staff> getStaffs(State state) throws SQLException
	{
		return getStaffManager().getStaffs(state);
	}

	public ArrayList<Staff> getStaffs(State state, String field, String id) throws SQLException
	{
		return getStaffManager().getStaffs(state, field, id);
	}

	public ArrayList<Staff> getStaffs(PreFellingSurvey preFellingSurvey) throws SQLException
	{
		return getStaffManager().getStaffs(preFellingSurvey);
	}

	public ArrayList<Staff> getStaffs(Tagging tagging) throws SQLException
	{
		return getStaffManager().getStaffs(tagging);
	}

	public ArrayList<Staff> getStaffs(PostFellingSurvey postFellingSurvey, boolean survey) throws SQLException
	{
		return getStaffManager().getStaffs(postFellingSurvey, survey);
	}

	public int addState(State state) throws SQLException
	{
		return getStateManager().addState(state);
	}

	public int updateState(State state) throws SQLException
	{
		return getStateManager().updateState(state);
	}

	public State getState(int stateID) throws SQLException
	{
		return getStateManager().getState(stateID);
	}

	public ArrayList<State> getStates() throws SQLException
	{
		return getStateManager().getStates();
	}

	public int addDistrict(District district) throws SQLException
	{
		return getDistrictManager().addDistrict(district);
	}

	public int updateDistrict(District district) throws SQLException
	{
		return getDistrictManager().updateDistrict(district);
	}

	public District getDistrict(int districtID) throws SQLException
	{
		return getDistrictManager().getDistrict(districtID);
	}

	public District getDistrict(long hallID) throws SQLException
	{
		return getDistrictManager().getDistrict(hallID);
	}

	public District getDistrict(Staff staff) throws SQLException
	{
		return getDistrictManager().getDistrict(staff);
	}
	
	public ArrayList<District> getDistricts(HallOfficer hallOfficer) throws SQLException
	{
		return getDistrictManager().getDistricts(hallOfficer);
	}

	public ArrayList<District> getDistricts() throws SQLException
	{
		return getDistrictManager().getDistricts();
	}

	public ArrayList<District> getDistricts(State state) throws SQLException
	{
		return getDistrictManager().getDistricts(state);
	}

	public int addRegion(Region region) throws SQLException
	{
		return getRegionManager().addRegion(region);
	}

	public int updateRegion(Region region) throws SQLException
	{
		return getRegionManager().updateRegion(region);
	}

	public int addRange(Range range) throws SQLException
	{
		return getRangeManager().addRange(range);
	}

	public int updateRange(Range range) throws SQLException
	{
		return getRangeManager().updateRange(range);
	}

	public Range getRange(int rangeID) throws SQLException
	{
		return getRangeManager().getRange(rangeID);
	}

	public Range getRange(Staff staff) throws SQLException
	{
		return getRangeManager().getRange(staff);
	}

	public int addHall(Hall hall, boolean ignoreDuplicate) throws SQLException
	{
		return getHallManager().addHall(hall, ignoreDuplicate);
	}

	public int updateHall(Hall hall) throws SQLException
	{
		return getHallManager().updateHall(hall);
	}

	public ArrayList<Hall> getHalls(District district) throws SQLException
	{
		return getHallManager().getHalls(district);
	}

	public ArrayList<Hall> getHalls() throws SQLException
	{
		return getHallManager().getHalls();
	}

	public ArrayList<Hall> getHalls(int status) throws SQLException
	{
		return getHallManager().getHalls(status);
	}	

	public ArrayList<Hall> getHalls(State state, int status) throws SQLException
	{
		return getHallManager().getHalls(state, status);
	}		

	public int addForest(Forest forest) throws SQLException
	{
		return getForestManager().addForest(forest);
	}

	public int updateForest(Forest forest) throws SQLException
	{
		return getForestManager().updateForest(forest);
	}

	public Forest getForest(int forestID) throws SQLException
	{
		return getForestManager().getForest(forestID);
	}

	public ArrayList<Forest> getForests() throws SQLException
	{
		return getForestManager().getForests();
	}

	public ArrayList<Forest> getForests(District district) throws SQLException
	{
		return getForestManager().getForests(district);
	}

	public ArrayList<Forest> getForests(State state) throws SQLException
	{
		return getForestManager().getForests(state);
	}

	public int addForestType(ForestType forestType) throws SQLException
	{
		return getForestTypeManager().addForestType(forestType);
	}

	public int updateForestType(ForestType forestType) throws SQLException
	{
		return getForestTypeManager().updateForestType(forestType);
	}

	public ArrayList<ForestType> getForestTypes() throws SQLException
	{
		return getForestTypeManager().getForestTypes();
	}

	public int addSoilType(SoilType soilType) throws SQLException
	{
		return getSoilTypeManager().addSoilType(soilType);
	}

	public int updateSoilType(SoilType soilType) throws SQLException
	{
		return getSoilTypeManager().updateSoilType(soilType);
	}

	public ArrayList<SoilType> getSoilTypes() throws SQLException
	{
		return getSoilTypeManager().getSoilTypes();
	}

	public int addGeology(Geology geology) throws SQLException
	{
		return getGeologyManager().addGeology(geology);
	}

	public int updateGeology(Geology geology) throws SQLException
	{
		return getGeologyManager().updateGeology(geology);
	}

	public ArrayList<Geology> getGeologies() throws SQLException
	{
		return getGeologyManager().getGeologies();
	}

	public int addAreaStatus(AreaStatus areaStatus) throws SQLException
	{
		return getAreaStatusManager().addAreaStatus(areaStatus);
	}

	public int updateAreaStatus(AreaStatus areaStatus) throws SQLException
	{
		return getAreaStatusManager().updateAreaStatus(areaStatus);
	}

	public ArrayList<AreaStatus> getAreaStatuses() throws SQLException
	{
		return getAreaStatusManager().getAreaStatuses();
	}

	public int addSoilStatus(SoilStatus soilStatus) throws SQLException
	{
		return getSoilStatusManager().addSoilStatus(soilStatus);
	}

	public int updateSoilStatus(SoilStatus soilStatus) throws SQLException
	{
		return getSoilStatusManager().updateSoilStatus(soilStatus);
	}

	public ArrayList<SoilStatus> getSoilStatuses() throws SQLException
	{
		return getSoilStatusManager().getSoilStatuses();
	}

	public int addAspect(Aspect aspect) throws SQLException
	{
		return getAspectManager().addAspect(aspect);
	}

	public int updateAspect(Aspect aspect) throws SQLException
	{
		return getAspectManager().updateAspect(aspect);
	}

	public ArrayList<Aspect> getAspects() throws SQLException
	{
		return getAspectManager().getAspects();
	}

	public int addSlopeLocation(SlopeLocation slopeLocation) throws SQLException
	{
		return getSlopeLocationManager().addSlopeLocation(slopeLocation);
	}

	public int updateSlopeLocation(SlopeLocation slopeLocation) throws SQLException
	{
		return getSlopeLocationManager().updateSlopeLocation(slopeLocation);
	}

	public ArrayList<SlopeLocation> getSlopeLocations() throws SQLException
	{
		return getSlopeLocationManager().getSlopeLocations();
	}

	public int addElevation(Elevation elevation) throws SQLException
	{
		return getElevationManager().addElevation(elevation);
	}

	public int updateElevation(Elevation elevation) throws SQLException
	{
		return getElevationManager().updateElevation(elevation);
	}

	public ArrayList<Elevation> getElevations() throws SQLException
	{
		return getElevationManager().getElevations();
	}

	public int addResam(Resam resam) throws SQLException
	{
		return getResamManager().addResam(resam);
	}

	public int updateResam(Resam resam) throws SQLException
	{
		return getResamManager().updateResam(resam);
	}

	public ArrayList<Resam> getResams() throws SQLException
	{
		return getResamManager().getResams();
	}

	public int addGinger(Ginger ginger) throws SQLException
	{
		return getGingerManager().addGinger(ginger);
	}

	public int updateGinger(Ginger ginger) throws SQLException
	{
		return getGingerManager().updateGinger(ginger);
	}

	public ArrayList<Ginger> getGingers() throws SQLException
	{
		return getGingerManager().getGingers();
	}

	public int addBanana(Banana banana) throws SQLException
	{
		return getBananaManager().addBanana(banana);
	}

	public int updateBanana(Banana banana) throws SQLException
	{
		return getBananaManager().updateBanana(banana);
	}

	public ArrayList<Banana> getBananas() throws SQLException
	{
		return getBananaManager().getBananas();
	}

	public int addTimberGroup(TimberGroup timberGroup) throws SQLException
	{
		return getTimberGroupManager().addTimberGroup(timberGroup);
	}

	public int updateTimberGroup(TimberGroup timberGroup) throws SQLException
	{
		return getTimberGroupManager().updateTimberGroup(timberGroup);
	}

	public ArrayList<TimberGroup> getTimberGroups() throws SQLException
	{
		return getTimberGroupManager().getTimberGroups();
	}

	public int addTimberType(TimberType timberType) throws SQLException
	{
		return getTimberTypeManager().addTimberType(timberType);
	}

	public int updateTimberType(TimberType timberType) throws SQLException
	{
		return getTimberTypeManager().updateTimberType(timberType);
	}

	public ArrayList<TimberType> getTimberTypes() throws SQLException
	{
		return getTimberTypeManager().getTimberTypes();
	}

	public int addSpeciesType(SpeciesType speciesType) throws SQLException
	{
		return getSpeciesTypeManager().addSpeciesType(speciesType);
	}

	public int updateSpeciesType(SpeciesType speciesType) throws SQLException
	{
		return getSpeciesTypeManager().updateSpeciesType(speciesType);
	}

	public ArrayList<SpeciesType> getSpeciesTypes() throws SQLException
	{
		return getSpeciesTypeManager().getSpeciesTypes();
	}

	public int addSpecies(Species species) throws SQLException
	{
		return getSpeciesManager().addSpecies(species);
	}

	public int updateSpecies(Species species) throws SQLException
	{
		return getSpeciesManager().updateSpecies(species);
	}

	public ArrayList<Species> getSpeciesList() throws SQLException
	{
		return getSpeciesManager().getSpeciesList();
	}

	public ArrayList<Species> getSpeciesList(State state) throws SQLException
	{
		return getSpeciesManager().getSpeciesList(state);
	}

	public int addRegenerationType(RegenerationType regenerationType) throws SQLException
	{
		return getRegenerationTypeManager().addRegenerationType(regenerationType);
	}

	public int updateRegenerationType(RegenerationType regenerationType) throws SQLException
	{
		return getRegenerationTypeManager().updateRegenerationType(regenerationType);
	}

	public ArrayList<RegenerationType> getRegenerationTypes() throws SQLException
	{
		return getRegenerationTypeManager().getRegenerationTypes();
	}

	public ArrayList<RegenerationType> getRegenerationTypes(State state) throws SQLException
	{
		return getRegenerationTypeManager().getRegenerationTypes(state);
	}

	public int addRegenerationSpecies(RegenerationSpecies regenerationSpecies) throws SQLException
	{
		return getRegenerationSpeciesManager().addRegenerationSpecies(regenerationSpecies);
	}

	public int updateRegenerationSpecies(RegenerationSpecies regenerationSpecies) throws SQLException
	{
		return getRegenerationSpeciesManager().updateRegenerationSpecies(regenerationSpecies);
	}

	public int deleteRegenerationSpecies(RegenerationSpecies regenerationSpecies) throws SQLException
	{
		return getRegenerationSpeciesManager().deleteRegenerationSpecies(regenerationSpecies);
	}

	public ArrayList<RegenerationSpecies> getRegenerationSpeciesList(State state) throws SQLException
	{
		return getRegenerationSpeciesManager().getRegenerationSpeciesList(state);
	}

	public int addProtectedType(ProtectedType protectedType) throws SQLException
	{
		return getProtectedTypeManager().addProtectedType(protectedType);
	}

	public int updateProtectedType(ProtectedType protectedType) throws SQLException
	{
		return getProtectedTypeManager().updateProtectedType(protectedType);
	}

	public ArrayList<ProtectedType> getProtectedTypes() throws SQLException
	{
		return getProtectedTypeManager().getProtectedTypes();
	}

	public ArrayList<ProtectedType> getProtectedTypes(State state) throws SQLException
	{
		return getProtectedTypeManager().getProtectedTypes(state);
	}

	public int addProtectedSpecies(ProtectedSpecies protectedSpecies) throws SQLException
	{
		return getProtectedSpeciesManager().addProtectedSpecies(protectedSpecies);
	}

	public int updateProtectedSpecies(ProtectedSpecies protectedSpecies) throws SQLException
	{
		return getProtectedSpeciesManager().updateProtectedSpecies(protectedSpecies);
	}

	public int deleteProtectedSpecies(ProtectedSpecies protectedSpecies) throws SQLException
	{
		return getProtectedSpeciesManager().deleteProtectedSpecies(protectedSpecies);
	}

	public ArrayList<ProtectedSpecies> getProtectedSpeciesList(State state) throws SQLException
	{
		return getProtectedSpeciesManager().getProtectedSpeciesList(state);
	}

	public int addTreeLimit(TreeLimit treeLimit) throws SQLException
	{
		return getTreeLimitManager().addTreeLimit(treeLimit);
	}

	public int updateTreeLimit(TreeLimit treeLimit) throws SQLException
	{
		return getTreeLimitManager().updateTreeLimit(treeLimit);
	}

	public TreeLimit getTreeLimit(State state) throws SQLException
	{
		return getTreeLimitManager().getTreeLimit(state);
	}

	public ArrayList<TreeLimit> getTreeLimits() throws SQLException
	{
		return getTreeLimitManager().getTreeLimits();
	}

	public int addPlotType(PlotType plotType) throws SQLException
	{
		return getPlotTypeManager().addPlotType(plotType);
	}

	public int updatePlotType(PlotType plotType) throws SQLException
	{
		return getPlotTypeManager().updatePlotType(plotType);
	}

	public ArrayList<PlotType> getPlotTypes() throws SQLException
	{
		return getPlotTypeManager().getPlotTypes();
	}

	public int addLogQuality(LogQuality logQuality) throws SQLException
	{
		return getLogQualityManager().addLogQuality(logQuality);
	}

	public int updateLogQuality(LogQuality logQuality) throws SQLException
	{
		return getLogQualityManager().updateLogQuality(logQuality);
	}

	public ArrayList<LogQuality> getLogQualities() throws SQLException
	{
		return getLogQualityManager().getLogQualities();
	}

	public int addFertility(Fertility fertility) throws SQLException
	{
		return getFertilityManager().addFertility(fertility);
	}

	public int updateFertility(Fertility fertility) throws SQLException
	{
		return getFertilityManager().updateFertility(fertility);
	}

	public ArrayList<Fertility> getFertilities() throws SQLException
	{
		return getFertilityManager().getFertilities();
	}

	public int addVineSpreadth(VineSpreadth vineSpreadth) throws SQLException
	{
		return getVineSpreadthManager().addVineSpreadth(vineSpreadth);
	}

	public int updateVineSpreadth(VineSpreadth vineSpreadth) throws SQLException
	{
		return getVineSpreadthManager().updateVineSpreadth(vineSpreadth);
	}

	public ArrayList<VineSpreadth> getVineSpreadths() throws SQLException
	{
		return getVineSpreadthManager().getVineSpreadths();
	}

	public int addTreeStatus(TreeStatus treeStatus) throws SQLException
	{
		return getTreeStatusManager().addTreeStatus(treeStatus);
	}

	public int updateTreeStatus(TreeStatus treeStatus) throws SQLException
	{
		return getTreeStatusManager().updateTreeStatus(treeStatus);
	}

	public ArrayList<TreeStatus> getTreeStatuses() throws SQLException
	{
		return getTreeStatusManager().getTreeStatuses();
	}

	public int addSilara(Silara silara) throws SQLException
	{
		return getSilaraManager().addSilara(silara);
	}

	public int updateSilara(Silara silara) throws SQLException
	{
		return getSilaraManager().updateSilara(silara);
	}

	public ArrayList<Silara> getSilaras() throws SQLException
	{
		return getSilaraManager().getSilaras();
	}

	public int addDominance(Dominance dominance) throws SQLException
	{
		return getDominanceManager().addDominance(dominance);
	}

	public int updateDominance(Dominance dominance) throws SQLException
	{
		return getDominanceManager().updateDominance(dominance);
	}

	public ArrayList<Dominance> getDominances() throws SQLException
	{
		return getDominanceManager().getDominances();
	}

	public int addCuttingOption(CuttingOption cuttingOption) throws SQLException
	{
		return getCuttingOptionManager().addCuttingOption(cuttingOption);
	}

	public int updateCuttingOption(CuttingOption cuttingOption) throws SQLException
	{
		return getCuttingOptionManager().updateCuttingOption(cuttingOption);
	}

	public ArrayList<CuttingOption> getCuttingOptions() throws SQLException
	{
		return getCuttingOptionManager().getCuttingOptions();
	}

	public ArrayList<CuttingOption> getCuttingOptions(State state) throws SQLException
	{
		return getCuttingOptionManager().getCuttingOptions(state);
	}

	public int addHammerType(HammerType hammerType) throws SQLException
	{
		return getHammerTypeManager().addHammerType(hammerType);
	}

	public int updateHammerType(HammerType hammerType) throws SQLException
	{
		return getHammerTypeManager().updateHammerType(hammerType);
	}

	public ArrayList<HammerType> getHammerTypes() throws SQLException
	{
		return getHammerTypeManager().getHammerTypes();
	}

	public int addHammer(Hammer hammer) throws SQLException
	{
		return getHammerManager().addHammer(hammer);
	}

	public int updateHammer(Hammer hammer) throws SQLException
	{
		return getHammerManager().updateHammer(hammer);
	}

	public Hammer getHammer(String hammerNo) throws SQLException
	{
		return getHammerManager().getHammer(hammerNo);
	}

	public ArrayList<Hammer> getHammers() throws SQLException
	{
		return getHammerManager().getHammers();
	}

	public ArrayList<Hammer> getHammers(State state) throws SQLException
	{
		return getHammerManager().getHammers(state);
	}

	public ArrayList<Hammer> getHammers(District district) throws SQLException
	{
		return getHammerManager().getHammers(district);
	}

	public ArrayList<Hammer> getHammers(Contractor contractor) throws SQLException
	{
		return getHammerManager().getHammers(contractor);
	}

	public ArrayList<Hammer> getHammers(Tagging tagging) throws SQLException
	{
		return getHammerManager().getHammers(tagging);
	}

	public ArrayList<Hammer> getHammers(State state, HammerType hammerType) throws SQLException
	{
		return getHammerManager().getHammers(state, hammerType);
	}

	public ArrayList<Hammer> getHammers(HammerType hammerType) throws SQLException
	{
		return getHammerManager().getHammers(hammerType);
	}

	public int addTaggingType(TaggingType taggingType) throws SQLException
	{
		return getTaggingTypeManager().addTaggingType(taggingType);
	}

	public int updateTaggingType(TaggingType taggingType) throws SQLException
	{
		return getTaggingTypeManager().updateTaggingType(taggingType);
	}

	public ArrayList<TaggingType> getTaggingTypes() throws SQLException
	{
		return getTaggingTypeManager().getTaggingTypes();
	}

	public int addTreeType(TreeType treeType) throws SQLException
	{
		return getTreeTypeManager().addTreeType(treeType);
	}

	public int updateTreeType(TreeType treeType) throws SQLException
	{
		return getTreeTypeManager().updateTreeType(treeType);
	}

	public ArrayList<TreeType> getTreeTypes() throws SQLException
	{
		return getTreeTypeManager().getTreeTypes();
	}

	public int addVolumeGroup(VolumeGroup volumeGroup) throws SQLException
	{
		return getVolumeGroupManager().addVolumeGroup(volumeGroup);
	}

	public int updateVolumeGroup(VolumeGroup volumeGroup) throws SQLException
	{
		return getVolumeGroupManager().updateVolumeGroup(volumeGroup);
	}

	public ArrayList<VolumeGroup> getVolumeGroups() throws SQLException
	{
		return getVolumeGroupManager().getVolumeGroups();
	}

	public ArrayList<VolumeGroup> getVolumeGroups(State state) throws SQLException
	{
		return getVolumeGroupManager().getVolumeGroups(state);
	}

	public TreeMap<String, Double> getVolumeMap() throws SQLException
	{
		return getVolumeGroupManager().getVolumeMap();
	}

	public TreeMap<String, Double> getVolumeMap(State state) throws SQLException
	{
		return getVolumeGroupManager().getVolumeMap(state);
	}

	public int addVolumeSpecies(VolumeSpecies volumeSpecies) throws SQLException
	{
		return getVolumeSpeciesManager().addVolumeSpecies(volumeSpecies);
	}

	public int updateVolumeSpecies(VolumeSpecies volumeSpecies) throws SQLException
	{
		return getVolumeSpeciesManager().updateVolumeSpecies(volumeSpecies);
	}

	public int deleteVolumeSpecies(VolumeSpecies volumeSpecies) throws SQLException
	{
		return getVolumeSpeciesManager().deleteVolumeSpecies(volumeSpecies);
	}

	public ArrayList<VolumeSpecies> getVolumeSpeciesList(State state) throws SQLException
	{
		return getVolumeSpeciesManager().getVolumeSpeciesList(state);
	}

	public int addVolumeDiameter(VolumeDiameter volumeDiameter) throws SQLException
	{
		return getVolumeDiameterManager().addVolumeDiameter(volumeDiameter);
	}

	public int updateVolumeDiameter(VolumeDiameter volumeDiameter) throws SQLException
	{
		return getVolumeDiameterManager().updateVolumeDiameter(volumeDiameter);
	}

	public int deleteVolumeDiameter(VolumeDiameter volumeDiameter) throws SQLException
	{
		return getVolumeDiameterManager().deleteVolumeDiameter(volumeDiameter);
	}

	public ArrayList<VolumeDiameter> getVolumeDiameterList(State state) throws SQLException
	{
		return getVolumeDiameterManager().getVolumeDiameterList(state);
	}

	public int addContractor(Contractor contractor) throws SQLException
	{
		return getContractorManager().addContractor(contractor);
	}

	public int updateContractor(Contractor contractor) throws SQLException
	{
		return getContractorManager().updateContractor(contractor);
	}

	public Contractor getContractor(String contractorID) throws SQLException
	{
		return getContractorManager().getContractor(contractorID);
	}

	public Contractor getContractor(Contractor contractor, String status) throws SQLException
	{
		return getContractorManager().getContractor(contractor, status);
	}

	public ArrayList<Contractor> getContractors(String status) throws SQLException
	{
		return getContractorManager().getContractors(status);
	}

	public int addTender(Tender tender) throws SQLException
	{
		return getTenderManager().addTender(tender);
	}

	public int updateTender(Tender tender) throws SQLException
	{
		return getTenderManager().updateTender(tender);
	}

	public Tender getTender(String tenderNo) throws SQLException
	{
		return getTenderManager().getTender(tenderNo);
	}

	public Tender getTender(String tenderNo, String status) throws SQLException
	{
		return getTenderManager().getTender(tenderNo, status);
	}

	public ArrayList<Tender> getTenders(String status) throws SQLException
	{
		return getTenderManager().getTenders(status);
	}

	public ArrayList<Tender> getTenders(String status, State state) throws SQLException
	{
		return getTenderManager().getTenders(status, state);
	}

	public ArrayList<Tender> getTenders(String status, Staff staff) throws SQLException
	{
		return getTenderManager().getTenders(status, staff);
	}

	public ArrayList<Tender> getTenders(String workType, String status, State state) throws SQLException
	{
		return getTenderManager().getTenders(workType, status, state);
	}

	public ArrayList<Tender> getTenders(String workType, String status, Staff staff) throws SQLException
	{
		return getTenderManager().getTenders(workType, status, staff);
	}

	public int addBursaryVot(BursaryVot bursaryVot) throws SQLException
	{
		return getBursaryVotManager().addBursaryVot(bursaryVot);
	}

	public int updateBursaryVot(BursaryVot bursaryVot) throws SQLException
	{
		return getBursaryVotManager().updateBursaryVot(bursaryVot);
	}

	public int deleteBursaryVot(BursaryVot bursaryVot) throws SQLException
	{
		return getBursaryVotManager().deleteBursaryVot(bursaryVot);
	}

	public BursaryVot getBursaryVot(int bursaryVotID) throws SQLException
	{
		return getBursaryVotManager().getBursaryVot(bursaryVotID);
	}

	public ArrayList<BursaryVot> getBursaryVots(String status) throws SQLException
	{
		return getBursaryVotManager().getBursaryVots(status);
	}

	public int addDepartmentVot(DepartmentVot departmentVot) throws SQLException
	{
		return getDepartmentVotManager().addDepartmentVot(departmentVot);
	}

	public int updateDepartmentVot(DepartmentVot departmentVot) throws SQLException
	{
		return getDepartmentVotManager().updateDepartmentVot(departmentVot);
	}

	public int deleteDepartmentVot(DepartmentVot departmentVot) throws SQLException
	{
		return getDepartmentVotManager().deleteDepartmentVot(departmentVot);
	}

	public DepartmentVot getDepartmentVot(int departmentVotID) throws SQLException
	{
		return getDepartmentVotManager().getDepartmentVot(departmentVotID);
	}

	public ArrayList<DepartmentVot> getDepartmentVots(String status) throws SQLException
	{
		return getDepartmentVotManager().getDepartmentVots(status);
	}

	public ArrayList<DepartmentVot> getDepartmentVots(String status, int receiptType) throws SQLException
	{
		return getDepartmentVotManager().getDepartmentVots(status, receiptType);
	}

	public int addTrustFund(TrustFund trustFund) throws SQLException
	{
		return getTrustFundManager().addTrustFund(trustFund);
	}

	public int updateTrustFund(TrustFund trustFund) throws SQLException
	{
		return getTrustFundManager().updateTrustFund(trustFund);
	}

	public int deleteTrustFund(TrustFund trustFund) throws SQLException
	{
		return getTrustFundManager().deleteTrustFund(trustFund);
	}

	public TrustFund getTrustFund(int trustFundID) throws SQLException
	{
		return getTrustFundManager().getTrustFund(trustFundID);
	}

	public ArrayList<TrustFund> getTrustFunds(String status) throws SQLException
	{
		return getTrustFundManager().getTrustFunds(status);
	}

	public int addProductGroup(ProductGroup productGroup) throws SQLException
	{
		return getProductGroupManager().addProductGroup(productGroup);
	}

	public int updateProductGroup(ProductGroup productGroup) throws SQLException
	{
		return getProductGroupManager().updateProductGroup(productGroup);
	}

	public int deleteProductGroup(ProductGroup productGroup) throws SQLException
	{
		return getProductGroupManager().deleteProductGroup(productGroup);
	}

	public ProductGroup getProductGroup(int productGroupID) throws SQLException
	{
		return getProductGroupManager().getProductGroup(productGroupID);
	}

	public ArrayList<ProductGroup> getProductGroups(String status) throws SQLException
	{
		return getProductGroupManager().getProductGroups(status);
	}

	public int addSmallProduct(SmallProduct smallProduct) throws SQLException
	{
		return getSmallProductManager().addSmallProduct(smallProduct);
	}

	public int updateSmallProduct(SmallProduct smallProduct) throws SQLException
	{
		return getSmallProductManager().updateSmallProduct(smallProduct);
	}

	public int deleteSmallProduct(SmallProduct smallProduct) throws SQLException
	{
		return getSmallProductManager().deleteSmallProduct(smallProduct);
	}

	public SmallProduct getSmallProduct(int smallProductID) throws SQLException
	{
		return getSmallProductManager().getSmallProduct(smallProductID);
	}

	public ArrayList<SmallProduct> getSmallProducts(String status) throws SQLException
	{
		return getSmallProductManager().getSmallProducts(status);
	}

	public int addTransactionForm(TransactionForm transactionForm) throws SQLException
	{
		return getTransactionFormManager().addTransactionForm(transactionForm);
	}

	public int updateTransactionForm(TransactionForm transactionForm) throws SQLException
	{
		return getTransactionFormManager().updateTransactionForm(transactionForm);
	}

	public int deleteTransactionForm(TransactionForm transactionForm) throws SQLException
	{
		return getTransactionFormManager().deleteTransactionForm(transactionForm);
	}

	public TransactionForm getTransactionForm(int transactionFormID) throws SQLException
	{
		return getTransactionFormManager().getTransactionForm(transactionFormID);
	}

	public ArrayList<TransactionForm> getTransactionForms(String status) throws SQLException
	{
		return getTransactionFormManager().getTransactionForms(status);
	}

	public TransactionForm getTransactionFormByName(TransactionForm transactionForm) throws SQLException
	{
		return getTransactionFormManager().getTransactionFormByName(transactionForm);
	}

	public int addTransactionFormMapDepartmentVot(TransactionFormMapDepartmentVot transactionFormMapDepartmentVot) throws SQLException
	{
		return getTransactionFormMapDepartmentVotManager().addTransactionFormMapDepartmentVot(transactionFormMapDepartmentVot);
	}

	public int updateTransactionFormMapDepartmentVot(TransactionFormMapDepartmentVot transactionFormMapDepartmentVot) throws SQLException
	{
		return getTransactionFormMapDepartmentVotManager().updateTransactionFormMapDepartmentVot(transactionFormMapDepartmentVot);
	}

	public int deleteTransactionFormMapDepartmentVot(TransactionFormMapDepartmentVot transactionFormMapDepartmentVot) throws SQLException
	{
		return getTransactionFormMapDepartmentVotManager().deleteTransactionFormMapDepartmentVot(transactionFormMapDepartmentVot);
	}

	public TransactionFormMapDepartmentVot getTransactionFormMapDepartmentVot(int transactionFormMapDepartmentVotID) throws SQLException
	{
		return getTransactionFormMapDepartmentVotManager().getTransactionFormMapDepartmentVot(transactionFormMapDepartmentVotID);
	}

	public ArrayList<TransactionFormMapDepartmentVot> getTransactionFormMapDepartmentVots(String status) throws SQLException
	{
		return getTransactionFormMapDepartmentVotManager().getTransactionFormMapDepartmentVots(status);
	}

	public ArrayList<TransactionFormMapDepartmentVot> getTransactionFormMapDepartmentVots(TransactionForm transactionForm, String status) throws SQLException
	{
		return getTransactionFormMapDepartmentVotManager().getTransactionFormMapDepartmentVots(transactionForm, status);
	}

	public int addTransactionFormMapTrustFund(TransactionFormMapTrustFund transactionFormMapTrustFund) throws SQLException
	{
		return getTransactionFormMapTrustFundManager().addTransactionFormMapTrustFund(transactionFormMapTrustFund);
	}

	public int updateTransactionFormMapTrustFund(TransactionFormMapTrustFund transactionFormMapTrustFund) throws SQLException
	{
		return getTransactionFormMapTrustFundManager().updateTransactionFormMapTrustFund(transactionFormMapTrustFund);
	}

	public int deleteTransactionFormMapTrustFund(TransactionFormMapTrustFund transactionFormMapTrustFund) throws SQLException
	{
		return getTransactionFormMapTrustFundManager().deleteTransactionFormMapTrustFund(transactionFormMapTrustFund);
	}

	public TransactionFormMapTrustFund getTransactionFormMapTrustFund(int transactionFormMapTrustFundID) throws SQLException
	{
		return getTransactionFormMapTrustFundManager().getTransactionFormMapTrustFund(transactionFormMapTrustFundID);
	}

	public ArrayList<TransactionFormMapTrustFund> getTransactionFormMapTrustFunds(String status) throws SQLException
	{
		return getTransactionFormMapTrustFundManager().getTransactionFormMapTrustFunds(status);
	}

	public ArrayList<TransactionFormMapTrustFund> getTransactionFormMapTrustFunds(TransactionForm transactionForm, String status) throws SQLException
	{
		return getTransactionFormMapTrustFundManager().getTransactionFormMapTrustFunds(transactionForm, status);
	}

	public int addPaymentType(PaymentType paymentType) throws SQLException
	{
		return getPaymentTypeManager().addPaymentType(paymentType);
	}

	public int updatePaymentType(PaymentType paymentType) throws SQLException
	{
		return getPaymentTypeManager().updatePaymentType(paymentType);
	}

	public int deletePaymentType(PaymentType paymentType) throws SQLException
	{
		return getPaymentTypeManager().deletePaymentType(paymentType);
	}

	public PaymentType getPaymentType(int paymentTypeID) throws SQLException
	{
		return getPaymentTypeManager().getPaymentType(paymentTypeID);
	}

	public ArrayList<PaymentType> getPaymentTypes(String status) throws SQLException
	{
		return getPaymentTypeManager().getPaymentTypes(status);
	}

	public int addBank(Bank bank) throws SQLException
	{
		return getBankManager().addBank(bank);
	}

	public int updateBank(Bank bank) throws SQLException
	{
		return getBankManager().updateBank(bank);
	}

	public int deleteBank(Bank bank) throws SQLException
	{
		return getBankManager().deleteBank(bank);
	}

	public Bank getBank(int bankID) throws SQLException
	{
		return getBankManager().getBank(bankID);
	}

	public ArrayList<Bank> getBanks(String status) throws SQLException
	{
		return getBankManager().getBanks(status);
	}

	public int addUnit(Unit unit) throws SQLException
	{
		return getUnitManager().addUnit(unit);
	}

	public int updateUnit(Unit unit) throws SQLException
	{
		return getUnitManager().updateUnit(unit);
	}

	public int deleteUnit(Unit unit) throws SQLException
	{
		return getUnitManager().deleteUnit(unit);
	}

	public Unit getUnit(int unitID) throws SQLException
	{
		return getUnitManager().getUnit(unitID);
	}

	public ArrayList<Unit> getUnits(String status) throws SQLException
	{
		return getUnitManager().getUnits(status);
	}

	public ArrayList<Unit> getUnits(int smallProductID) throws SQLException
	{
		return getUnitManager().getUnits(smallProductID);
	}

	public int addChequeType(ChequeType chequeType) throws SQLException
	{
		return getChequeTypeManager().addChequeType(chequeType);
	}

	public int updateChequeType(ChequeType chequeType) throws SQLException
	{
		return getChequeTypeManager().updateChequeType(chequeType);
	}

	public int deleteChequeType(ChequeType chequeType) throws SQLException
	{
		return getChequeTypeManager().deleteChequeType(chequeType);
	}

	public ChequeType getChequeType(int chequeTypeID) throws SQLException
	{
		return getChequeTypeManager().getChequeType(chequeTypeID);
	}

	public ArrayList<ChequeType> getChequeTypes(String status) throws SQLException
	{
		return getChequeTypeManager().getChequeTypes(status);
	}

	public int addForestCategory(ForestCategory forestCategory) throws SQLException
	{
		return getForestCategoryManager().addForestCategory(forestCategory);
	}

	public int updateForestCategory(ForestCategory forestCategory) throws SQLException
	{
		return getForestCategoryManager().updateForestCategory(forestCategory);
	}

	public int deleteForestCategory(ForestCategory forestCategory) throws SQLException
	{
		return getForestCategoryManager().deleteForestCategory(forestCategory);
	}

	public ForestCategory getForestCategory(int forestCategoryID) throws SQLException
	{
		return getForestCategoryManager().getForestCategory(forestCategoryID);
	}

	public ArrayList<ForestCategory> getForestCategorys(String status) throws SQLException
	{
		return getForestCategoryManager().getForestCategorys(status);
	}

	public int addLicenseStatus(LicenseStatus licenseStatus) throws SQLException
	{
		return getLicenseStatusManager().addLicenseStatus(licenseStatus);
	}

	public int updateLicenseStatus(LicenseStatus licenseStatus) throws SQLException
	{
		return getLicenseStatusManager().updateLicenseStatus(licenseStatus);
	}

	public int deleteLicenseStatus(LicenseStatus licenseStatus) throws SQLException
	{
		return getLicenseStatusManager().deleteLicenseStatus(licenseStatus);
	}

	public LicenseStatus getLicenseStatus(int licenseStatusID) throws SQLException
	{
		return getLicenseStatusManager().getLicenseStatus(licenseStatusID);
	}

	public ArrayList<LicenseStatus> getLicenseStatuss(String status) throws SQLException
	{
		return getLicenseStatusManager().getLicenseStatuss(status);
	}

	public int addLicenseType(LicenseType licenseType) throws SQLException
	{
		return getLicenseTypeManager().addLicenseType(licenseType);
	}

	public int updateLicenseType(LicenseType licenseType) throws SQLException
	{
		return getLicenseTypeManager().updateLicenseType(licenseType);
	}

	public int deleteLicenseType(LicenseType licenseType) throws SQLException
	{
		return getLicenseTypeManager().deleteLicenseType(licenseType);
	}

	public LicenseType getLicenseType(int licenseTypeID) throws SQLException
	{
		return getLicenseTypeManager().getLicenseType(licenseTypeID);
	}

	public ArrayList<LicenseType> getLicenseTypes(String status) throws SQLException
	{
		return getLicenseTypeManager().getLicenseTypes(status);
	}

	public int addMainRevenueRoyaltyRate(MainRevenueRoyaltyRate mainRevenueRoyaltyRate) throws SQLException
	{
		return getMainRevenueRoyaltyRateManager().addMainRevenueRoyaltyRate(mainRevenueRoyaltyRate);
	}

	public int deleteMainRevenueRoyaltyRate(MainRevenueRoyaltyRate mainRevenueRoyaltyRate) throws SQLException
	{
		return getMainRevenueRoyaltyRateManager().deleteMainRevenueRoyaltyRate(mainRevenueRoyaltyRate);
	}

	public MainRevenueRoyaltyRate getMainRevenueRoyaltyRate(int mainRevenueRoyaltyRateID) throws SQLException
	{
		return getMainRevenueRoyaltyRateManager().getMainRevenueRoyaltyRate(mainRevenueRoyaltyRateID);
	}

	public ArrayList<MainRevenueRoyaltyRate> getMainRevenueRoyaltyRates(String status) throws SQLException
	{
		return getMainRevenueRoyaltyRateManager().getMainRevenueRoyaltyRates(status);
	}

	public ArrayList<MainRevenueRoyaltyRate> getMainRevenueRoyaltyRates(State state, String status) throws SQLException
	{
		return getMainRevenueRoyaltyRateManager().getMainRevenueRoyaltyRates(state, status);
	}

	public int addSmallProductRoyaltyRate(SmallProductRoyaltyRate smallProductRoyaltyRate) throws SQLException
	{
		return getSmallProductRoyaltyRateManager().addSmallProductRoyaltyRate(smallProductRoyaltyRate);
	}

	public int deleteSmallProductRoyaltyRate(SmallProductRoyaltyRate smallProductRoyaltyRate) throws SQLException
	{
		return getSmallProductRoyaltyRateManager().deleteSmallProductRoyaltyRate(smallProductRoyaltyRate);
	}

	public SmallProductRoyaltyRate getSmallProductRoyaltyRate(int smallProductRoyaltyRateID) throws SQLException
	{
		return getSmallProductRoyaltyRateManager().getSmallProductRoyaltyRate(smallProductRoyaltyRateID);
	}

	public ArrayList<SmallProductRoyaltyRate> getSmallProductRoyaltyRates(String status) throws SQLException
	{
		return getSmallProductRoyaltyRateManager().getSmallProductRoyaltyRates(status);
	}

	public int addLogSize(LogSize logSize) throws SQLException
	{
		return getLogSizeManager().addLogSize(logSize);
	}

	public int updateLogSize(LogSize logSize) throws SQLException
	{
		return getLogSizeManager().updateLogSize(logSize);
	}

	public LogSize getLogSize(int logSizeID) throws SQLException
	{
		return getLogSizeManager().getLogSize(logSizeID);
	}

	public ArrayList<LogSize> getLogSizes(String status) throws SQLException
	{
		return getLogSizeManager().getLogSizes(status);
	}

	public LogSize getLogSize(State state) throws SQLException
	{
		return getLogSizeManager().getLogSize(state);
	}

	public int addHallOfficer(HallOfficer hallOfficer) throws SQLException
	{
		return getHallOfficerManager().addHallOfficer(hallOfficer);
	}

	public int updateHallOfficer(HallOfficer hallOfficer) throws SQLException
	{
		return getHallOfficerManager().updateHallOfficer(hallOfficer);
	}

	public int deleteHallOfficer(HallOfficer hallOfficer) throws SQLException
	{
		return getHallOfficerManager().deleteHallOfficer(hallOfficer);
	}

	public int updateExpirationStatusHallOfficer(HallOfficer hallOfficer) throws SQLException
	{
		return getHallOfficerManager().updateExpirationStatusHallOfficer(hallOfficer);
	}

	public HallOfficer getHallOfficer(HallOfficer hallOfficer) throws SQLException
	{
		return getHallOfficerManager().getHallOfficer(hallOfficer);
	}

	public ArrayList<HallOfficer> getHallOfficers(String status) throws SQLException
	{
		return getHallOfficerManager().getHallOfficers(status);
	}
	
	public ArrayList<HallOfficer> getHallOfficers(Date currentDate, String status) throws SQLException
	{
		return getHallOfficerManager().getHallOfficers(currentDate, status);
	}
	
	public ArrayList<HallOfficer> getHallOfficers(Staff staff) throws SQLException
	{
		return getHallOfficerManager().getHallOfficers(staff);
	}
	
	public ArrayList<HallOfficer> getHallOfficers(Date currentDate, Staff staff) throws SQLException
	{
		return getHallOfficerManager().getHallOfficers(currentDate, staff);
	}

	public ArrayList<HallOfficer> getHallOfficers(State state) throws SQLException
	{
		return getHallOfficerManager().getHallOfficers(state);
	}

	public ArrayList<HallOfficer> getHallOfficers(Date currentDate, State state) throws SQLException
	{
		return getHallOfficerManager().getHallOfficers(currentDate, state);
	}
	
	public ArrayList<HallOfficer> getHallOfficers(District district) throws SQLException
	{
		return getHallOfficerManager().getHallOfficers(district);
	}
	
	public ArrayList<HallOfficer> getHallOfficers(Date currentDate, District district) throws SQLException
	{
		return getHallOfficerManager().getHallOfficers(currentDate, district);
	}

	public int addForestDevelopmentWorkType(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		return getForestDevelopmentWorkTypeManager().addForestDevelopmentWorkType(forestDevelopmentWorkType);
	}

	public int updateForestDevelopmentWorkType(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		return getForestDevelopmentWorkTypeManager().updateForestDevelopmentWorkType(forestDevelopmentWorkType);
	}

	public int deleteForestDevelopmentWorkType(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		return getForestDevelopmentWorkTypeManager().deleteForestDevelopmentWorkType(forestDevelopmentWorkType);
	}

	public ForestDevelopmentWorkType getForestDevelopmentWorkType(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		return getForestDevelopmentWorkTypeManager().getForestDevelopmentWorkType(forestDevelopmentWorkType);
	}

	public ArrayList<ForestDevelopmentWorkType> getForestDevelopmentWorkTypes(String status) throws SQLException
	{
		return getForestDevelopmentWorkTypeManager().getForestDevelopmentWorkTypes(status);
	}

	public int addForestDevelopmentSubWorkType(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType) throws SQLException
	{
		return getForestDevelopmentSubWorkTypeManager().addForestDevelopmentSubWorkType(forestDevelopmentSubWorkType);
	}

	public int updateForestDevelopmentSubWorkType(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType) throws SQLException
	{
		return getForestDevelopmentSubWorkTypeManager().updateForestDevelopmentSubWorkType(forestDevelopmentSubWorkType);
	}

	public int deleteForestDevelopmentSubWorkType(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType) throws SQLException
	{
		return getForestDevelopmentSubWorkTypeManager().deleteForestDevelopmentSubWorkType(forestDevelopmentSubWorkType);
	}

	public ForestDevelopmentSubWorkType getForestDevelopmentSubWorkType(ForestDevelopmentSubWorkType forestDevelopmentSubWorkType) throws SQLException
	{
		return getForestDevelopmentSubWorkTypeManager().getForestDevelopmentSubWorkType(forestDevelopmentSubWorkType);
	}

	public ArrayList<ForestDevelopmentSubWorkType> getForestDevelopmentSubWorkTypes(ForestDevelopmentWorkType forestDevelopmentWorkType) throws SQLException
	{
		return getForestDevelopmentSubWorkTypeManager().getForestDevelopmentSubWorkTypes(forestDevelopmentWorkType);
	}

	public ArrayList<ForestDevelopmentSubWorkType> getForestDevelopmentSubWorkTypes(String status) throws SQLException
	{
		return getForestDevelopmentSubWorkTypeManager().getForestDevelopmentSubWorkTypes(status);
	}	

	public int addPermitType(PermitType permitType) throws SQLException
	{
		return getPermitTypeManager().addPermitType(permitType);
	}

	public int updatePermitType(PermitType permitType) throws SQLException
	{
		return getPermitTypeManager().updatePermitType(permitType);
	}

	public int deletePermitType(PermitType permitType) throws SQLException
	{
		return getPermitTypeManager().deletePermitType(permitType);
	}

	public PermitType getPermitType(int permitTypeID) throws SQLException
	{
		return getPermitTypeManager().getPermitType(permitTypeID);
	}

	public ArrayList<PermitType> getPermitTypes(String status) throws SQLException
	{
		return getPermitTypeManager().getPermitTypes(status);
	}
	
	public int addAbstractsSpeciesGroup(AbstractsSpeciesGroup abstractsSpeciesGroup) throws SQLException
	{
		return getAbstractsSpeciesGroupManager().addAbstractsSpeciesGroup(abstractsSpeciesGroup);
	}
	
	public int deleteAbstractsSpeciesGroup(AbstractsSpeciesGroup abstractsSpeciesGroup) throws SQLException
	{
		return getAbstractsSpeciesGroupManager().deleteAbstractsSpeciesGroup(abstractsSpeciesGroup);
	}
	
	public AbstractsSpeciesGroup getAbstractsSpeciesGroup(int abstractsSpeciesGroupID) throws SQLException
	{
		return getAbstractsSpeciesGroupManager().getAbstractsSpeciesGroup(abstractsSpeciesGroupID);
	}
	
	public ArrayList<AbstractsSpeciesGroup> getAbstractsSpeciesGroups(String status) throws SQLException
	{
		return getAbstractsSpeciesGroupManager().getAbstractsSpeciesGroups(status);
	}
	
	public int updateAbstractsSpeciesGroup(AbstractsSpeciesGroup abstractsSpeciesGroup) throws SQLException
	{
		return getAbstractsSpeciesGroupManager().updateAbstractsSpeciesGroup(abstractsSpeciesGroup);
	}
	
	public int addAbstractsSpeciesGroupRecord(AbstractsSpeciesGroupRecord abstractsSpeciesGroupRecord) throws SQLException
	{
		return getAbstractsSpeciesGroupRecordManager().addAbstractsSpeciesGroupRecord(abstractsSpeciesGroupRecord);
	}
	
	public int deleteAbstractsSpeciesGroupRecord(AbstractsSpeciesGroupRecord abstractsSpeciesGroupRecord) throws SQLException
	{
		return getAbstractsSpeciesGroupRecordManager().deleteAbstractsSpeciesGroupRecord(abstractsSpeciesGroupRecord);
	}
	
	public AbstractsSpeciesGroupRecord getAbstractsSpeciesGroupRecord(int abstractsSpeciesGroupRecordID) throws SQLException
	{
		return getAbstractsSpeciesGroupRecordManager().getAbstractsSpeciesGroupRecord(abstractsSpeciesGroupRecordID);
	}
	
	public ArrayList<AbstractsSpeciesGroupRecord> getAbstractsSpeciesGroupRecords(String status) throws SQLException
	{
		return getAbstractsSpeciesGroupRecordManager().getAbstractsSpeciesGroupRecords(status);
	}
	
	public int updateAbstractsSpeciesGroupRecord(AbstractsSpeciesGroupRecord abstractsSpeciesGroupRecord) throws SQLException
	{
		return getAbstractsSpeciesGroupRecordManager().updateAbstractsSpeciesGroupRecord(abstractsSpeciesGroupRecord);
	}
	
	public ArrayList<AbstractsSpeciesGroupRecord> getAbstractsSpeciesGroupRecords(AbstractsSpeciesGroup abstractsSpeciesGroup) throws SQLException
	{
		return getAbstractsSpeciesGroupRecordManager().getAbstractsSpeciesGroupRecords(abstractsSpeciesGroup);
	}
}