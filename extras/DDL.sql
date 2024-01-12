-- CREATE DATABASE dbFIS9 COLLATE 'UTF8mb4_bin';
-- USE dbFIS9;

CREATE TABLE Module
(
	ModuleID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Form
(
	FormID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL,
	Path VARCHAR(50) NOT NULL,
	Icon VARCHAR(20) NOT NULL,
	Category VARCHAR(30),
	ModuleID INT NOT NULL,
	FOREIGN KEY (ModuleID) REFERENCES Module (ModuleID) ON DELETE CASCADE
);

CREATE TABLE Designation
(
	DesignationID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(100) NOT NULL
);

CREATE TABLE DefaultAccess
(
	DesignationID INT NOT NULL,
	FormID INT NOT NULL,
	PRIMARY KEY (DesignationID, FormID),
	FOREIGN KEY (DesignationID) REFERENCES Designation (DesignationID) ON DELETE CASCADE,
	FOREIGN KEY (FormID) REFERENCES Form (FormID) ON DELETE CASCADE
);

CREATE TABLE Contractor
(
	ContractorID VARCHAR(20) PRIMARY KEY,
	Type VARCHAR(1) NOT NULL,
	Name VARCHAR(100) NOT NULL,
	CompanyName VARCHAR(20) NOT NULL,
	RegistrationNo VARCHAR(100) NOT NULL,
	Address VARCHAR(100) NOT NULL,
	Postcode VARCHAR(5) NOT NULL,
	RegionID INT NOT NULL,
	TelNo VARCHAR(15) NOT NULL,
	FaxNo VARCHAR(15),
	Status VARCHAR(1) NOT NULL
);

CREATE TABLE Staff
(
	StaffID VARCHAR(50) PRIMARY KEY,
	Name VARCHAR(100) NOT NULL,
	Password VARCHAR(100) NOT NULL,
	DesignationID INT,
	Status INT NOT NULL,
	Administrative INT NOT NULL,
	StateID INT,
	ContractorID VARCHAR(20),
	FOREIGN KEY (DesignationID) REFERENCES Designation (DesignationID),
	FOREIGN KEY (ContractorID) REFERENCES Contractor (ContractorID) ON UPDATE CASCADE
);

CREATE TABLE TrailLog
(
	LogTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	Operation VARCHAR(100) NOT NULL, 
	StaffID VARCHAR(50) NOT NULL,
	FOREIGN KEY (StaffID) REFERENCES Staff (StaffID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE State
(
	StateID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL,
	Motto VARCHAR(100) NOT NULL,
	DirectorID VARCHAR(50) NOT NULL,
	DeputyDirector1ID VARCHAR(50),
	DeputyDirector2ID VARCHAR(50),
	SeniorAsstDirector1ID VARCHAR(50),
	SeniorAsstDirector2ID VARCHAR(50),
	SeniorAsstDirector3ID VARCHAR(50),
	SeniorAsstDirector4ID VARCHAR(50),
	SeniorAsstDirector5ID VARCHAR(50),
	SeniorAsstDirector6ID VARCHAR(50),
	AsstDirector1ID VARCHAR(50),
	AsstDirector2ID VARCHAR(50),
	AsstDirector3ID VARCHAR(50),
	AsstDirector4ID VARCHAR(50),
	AsstDirector5ID VARCHAR(50),
	AsstDirector6ID VARCHAR(50),
	FOREIGN KEY (DirectorID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (DeputyDirector1ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (DeputyDirector2ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (SeniorAsstDirector1ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (SeniorAsstDirector2ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (SeniorAsstDirector3ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (SeniorAsstDirector4ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (SeniorAsstDirector5ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (SeniorAsstDirector6ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (AsstDirector1ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (AsstDirector2ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (AsstDirector3ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (AsstDirector4ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (AsstDirector5ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (AsstDirector6ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

ALTER TABLE Staff ADD CONSTRAINT Staff_State_FK FOREIGN KEY Staff (StateID) REFERENCES State (StateID);

CREATE TABLE Region
(
	RegionID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	StateID INT NOT NULL,
	FOREIGN KEY (StateID) REFERENCES State (StateID)
);

CREATE TABLE District
(
	DistrictID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Address VARCHAR(100) NOT NULL,
	StateID INT NOT NULL,
	OfficerID VARCHAR(50) NOT NULL,
	AsstOfficerID VARCHAR(50),
	Clerk1ID VARCHAR(50),
	Clerk2ID VARCHAR(50),
	Clerk3ID VARCHAR(50),
	FOREIGN KEY (StateID) REFERENCES State (StateID), 
	FOREIGN KEY (OfficerID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (AsstOfficerID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (Clerk1ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (Clerk2ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (Clerk3ID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE `Range`
(
	RangeID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL,
	Address VARCHAR(100) NOT NULL,
	DistrictID INT NOT NULL,
	AsstOfficerID VARCHAR(50) NOT NULL,
	FOREIGN KEY (DistrictID) REFERENCES District (DistrictID),
	FOREIGN KEY (AsstOfficerID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE Hall
(
	HallID BIGINT PRIMARY KEY,
	Name VARCHAR(50) NOT NULL,
	Status INT NOT NULL,
	DistrictID INT NOT NULL,
	FOREIGN KEY (DistrictID) REFERENCES District (DistrictID)
);

CREATE TABLE Forest
(
	ForestID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Area DOUBLE NOT NULL,
	VolumeRate DOUBLE,
	PowerRate DOUBLE,
	DistrictID INT NOT NULL,
	FOREIGN KEY (DistrictID) REFERENCES District (DistrictID)
);

CREATE TABLE ForestType
(
	ForestTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE SoilType
(
	SoilTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Geology
(
	GeologyID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE AreaStatus
(
	AreaStatusID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE SoilStatus
(
	SoilStatusID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Aspect
(
	AspectID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE SlopeLocation
(
	SlopeLocationID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Elevation
(
	ElevationID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Resam
(
	ResamID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Ginger
(
	GingerID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Banana
(
	BananaID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE TimberGroup
(
	TimberGroupID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL,
	Description VARCHAR(100) NOT NULL
);

CREATE TABLE TimberType
(
	TimberTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE SpeciesType
(
	SpeciesTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Species
(
	SpeciesID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5),
	Name VARCHAR(50) NOT NULL,
	ScientificName VARCHAR(50) NOT NULL,
	Symbol VARCHAR(10),
	SpeciesTypeID INT NOT NULL,
	TimberGroupID INT,
	TimberTypeID INT,
	FOREIGN KEY (SpeciesTypeID) REFERENCES SpeciesType (SpeciesTypeID),
	FOREIGN KEY (TimberGroupID) REFERENCES TimberGroup (TimberGroupID),
	FOREIGN KEY (TimberTypeID) REFERENCES TimberType (TimberTypeID)
);

CREATE TABLE RegenerationType
(
	RegenerationTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL,
	StateID INT,
	FOREIGN KEY (StateID) REFERENCES State (StateID) ON DELETE CASCADE
);

CREATE TABLE RegenerationSpecies
(
	RegenerationSpeciesID INT PRIMARY KEY AUTO_INCREMENT,
	RegenerationTypeID INT NOT NULL,
	SpeciesID INT NOT NULL,
	UNIQUE (RegenerationTypeID, SpeciesID),
	FOREIGN KEY (RegenerationTypeID) REFERENCES RegenerationType (RegenerationTypeID) ON DELETE CASCADE,
	FOREIGN KEY (SpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE
);

CREATE TABLE ProtectedType
(
	ProtectedTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL,
	StateID INT,
	FOREIGN KEY (StateID) REFERENCES State (StateID) ON DELETE CASCADE
);

CREATE TABLE ProtectedSpecies
(
	ProtectedSpeciesID INT PRIMARY KEY AUTO_INCREMENT,
	ProtectedTypeID INT NOT NULL,
	SpeciesID INT NOT NULL,
	UNIQUE (ProtectedTypeID, SpeciesID),
	FOREIGN KEY (ProtectedTypeID) REFERENCES ProtectedType (ProtectedTypeID) ON DELETE CASCADE,
	FOREIGN KEY (SpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE
);

CREATE TABLE TreeLimit
(
	TreeLimitID INT PRIMARY KEY AUTO_INCREMENT,
	MotherLimit DOUBLE NOT NULL,
	ChengalLimit DOUBLE NOT NULL,
	ProtectedLimit DOUBLE NOT NULL,
	MaximumPerPlot INT NOT NULL,
	StateID INT NOT NULL UNIQUE,
	FOREIGN KEY (StateID) REFERENCES State (StateID)
);

CREATE TABLE PlotType
(
	PlotTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL,
	Description VARCHAR(100) NOT NULL,
	Length DOUBLE NOT NULL,
	Width DOUBLE NOT NULL,
	MinDiameter DOUBLE,
	MaxDiameter DOUBLE
);

CREATE TABLE LogQuality
(
	LogQualityID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Fertility
(
	FertilityID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE VineSpreadth
(
	VineSpreadthID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE TreeStatus
(
	TreeStatusID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Silara
(
	SilaraID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Dominance
(
	DominanceID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(5) NOT NULL UNIQUE,
	Name VARCHAR(100) NOT NULL
);

CREATE TABLE CuttingOption
(
	CuttingOptionID INT PRIMARY KEY AUTO_INCREMENT,
	DipterocarpLimit DOUBLE NOT NULL,
	NonDipterocarpLimit DOUBLE NOT NULL,
	StateID INT,
	UNIQUE (DipterocarpLimit, NonDipterocarpLimit),
	FOREIGN KEY (StateID) REFERENCES State (StateID)
);

CREATE TABLE HammerType
(
	HammerTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE Hammer
(
	HammerNo VARCHAR(50) PRIMARY KEY,
	RegistrationNo VARCHAR(50) NOT NULL UNIQUE,
	HammerTypeID INT,
	DistrictID INT,
	ContractorID VARCHAR(20),
	FOREIGN KEY (HammerTypeID) REFERENCES HammerType (HammerTypeID),
	FOREIGN KEY (DistrictID) REFERENCES District (DistrictID),
	FOREIGN KEY (ContractorID) REFERENCES Contractor (ContractorID) ON UPDATE CASCADE
);

CREATE TABLE TaggingType
(
	TaggingTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE TreeType
(
	TreeTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL
);

CREATE TABLE VolumeGroup
(
	VolumeGroupID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL,
	StateID INT,
	FOREIGN KEY (StateID) REFERENCES State (StateID) ON DELETE CASCADE
);

CREATE TABLE VolumeSpecies
(
	VolumeSpeciesID INT PRIMARY KEY AUTO_INCREMENT,
	VolumeGroupID INT NOT NULL,
	SpeciesID INT NOT NULL,
	UNIQUE (VolumeGroupID, SpeciesID),
	FOREIGN KEY (VolumeGroupID) REFERENCES VolumeGroup (VolumeGroupID) ON DELETE CASCADE,
	FOREIGN KEY (SpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE
);

CREATE TABLE VolumeDiameter
(
	VolumeDiameterID INT PRIMARY KEY AUTO_INCREMENT,
	Diameter INT NOT NULL,
	Volume DOUBLE NOT NULL,
	VolumeGroupID INT NOT NULL,
	UNIQUE (VolumeGroupID, Diameter),
	FOREIGN KEY (VolumeGroupID) REFERENCES VolumeGroup (VolumeGroupID) ON DELETE CASCADE
);

CREATE TABLE AbstractsSpeciesGroup
(
	AbstractsSpeciesGroupID INT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(100) NOT NULL,
	Status VARCHAR(1) NOT NULL
);

CREATE TABLE AbstractsSpeciesGroupRecord
(
	AbstractsSpeciesGroupRecordID INT PRIMARY KEY AUTO_INCREMENT,
	AbstractsSpeciesGroupID INT NOT NULL,
	SpeciesID INT NOT NULL,
	Status VARCHAR(1) NOT NULL,
	FOREIGN KEY (AbstractsSpeciesGroupID) REFERENCES AbstractsSpeciesGroup (AbstractsSpeciesGroupID) ON DELETE CASCADE,
	FOREIGN KEY (SpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE
);

CREATE TABLE Tender
(
	TenderNo VARCHAR(20) PRIMARY KEY,
	ContractorID VARCHAR(20) NOT NULL,
	WorkType VARCHAR(1) NOT NULL,
	AppointDate DATE NOT NULL,
	StartDate DATE NOT NULL,
	EndDate DATE NOT NULL,
	Voucher VARCHAR(1) NOT NULL,
	Status VARCHAR(1) NOT NULL,
	StateID INT NOT NULL,
	FOREIGN KEY (ContractorID) REFERENCES Contractor (ContractorID) ON UPDATE CASCADE,
	FOREIGN KEY (StateID) REFERENCES State (StateID)
);

CREATE TABLE BursaryVot
(
	BursaryVotID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(100) NOT NULL,
	Type VARCHAR(1) COMMENT 'R = Revenue, T = TrustFund',
	Status VARCHAR(1) NOT NULL COMMENT 'A=Active, I=inactive'
);

CREATE TABLE DepartmentVot
(
	DepartmentVotID INT PRIMARY KEY AUTO_INCREMENT,
	BursaryVotID INT NOT NULL,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(200) NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A=Active, I=Inactive',
	FOREIGN KEY (BursaryVotID) REFERENCES BursaryVot (BursaryVotID)
);

CREATE TABLE TrustFund
(
	TrustFundID INT PRIMARY KEY AUTO_INCREMENT,
	DepartmentVotID INT NOT NULL,
	Description VARCHAR(100),
	Symbol VARCHAR(5),
	Status VARCHAR(1),
	FOREIGN KEY (DepartmentVotID) REFERENCES DepartmentVot (DepartmentVotID)
);

CREATE TABLE ProductGroup
(
	ProductGroupID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Status VARCHAR(1) NOT NULL
);

CREATE TABLE SmallProduct
(
	SmallProductID INT PRIMARY KEY AUTO_INCREMENT,
	ProductGroupID INT NOT NULL,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(100) NOT NULL,
	Symbol VARCHAR(50),
	Status VARCHAR(1) NOT NULL COMMENT '''A''=Active, ''I''=Inactive',
	FOREIGN KEY (ProductGroupID) REFERENCES ProductGroup (ProductGroupID)
);

CREATE TABLE TransactionForm
(
	TransactionFormID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Status VARCHAR(1) NOT NULL
);

CREATE TABLE TransactionFormMapDepartmentVot
(
	TransactionFormMapDepartmentVotID INT PRIMARY KEY AUTO_INCREMENT,
	TransactionFormID INT NOT NULL,
	DepartmentVotID INT NOT NULL,
	Status VARCHAR(1) NOT NULL,
	FOREIGN KEY (DepartmentVotID) REFERENCES DepartmentVot (DepartmentVotID),
	FOREIGN KEY (TransactionFormID) REFERENCES TransactionForm (TransactionFormID)
);

CREATE TABLE TransactionFormMapTrustFund
(
	TransactionFormMapTrustFundID INT PRIMARY KEY AUTO_INCREMENT,
	TransactionFormID INT NOT NULL,
	TrustFundID INT NOT NULL,
	Status VARCHAR(1) NOT NULL,
	FOREIGN KEY (TransactionFormID) REFERENCES TransactionForm (TransactionFormID),
	FOREIGN KEY (TrustFundID) REFERENCES TrustFund (TrustFundID)
);

CREATE TABLE PaymentType
(
	PaymentTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A=Active, I=Inactive'
);

CREATE TABLE Bank
(
	BankID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A=Active, I=Inactive'
);

CREATE TABLE Unit
(
	UnitID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Status VARCHAR(1) NOT NULL
);

CREATE TABLE ChequeType
(
	ChequeTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A="Active", I="Inactive"'
);

CREATE TABLE ForestCategory
(
	ForestCategoryID INT PRIMARY KEY AUTO_INCREMENT COMMENT 'For Revenue Report',
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Status VARCHAR(1) NOT NULL
);

CREATE TABLE LicenseStatus
(
	LicenseStatusID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT '"A" = Active, "I" = Inactive'
);

CREATE TABLE LicenseType
(
	LicenseTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(10) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A="Active", I="Inactive"'
);

CREATE TABLE MainRevenueRoyaltyRate
(
	MainRevenueRoyaltyRateID BIGINT PRIMARY KEY,
	StateID INT NOT NULL,
	SpeciesID INT NOT NULL,
	BigSizeRoyaltyRate DOUBLE NOT NULL DEFAULT 0,
	SmallSizeRoyaltyRate DOUBLE NOT NULL DEFAULT 0,
	CessRate DOUBLE NOT NULL DEFAULT 0,
	JarasRoyaltyRate DOUBLE NOT NULL,
	JarasCessRate DOUBLE NOT NULL COMMENT '%, Default 10%',
	StartDate DATE,
	EndDate DATE,
	Status VARCHAR(1) COMMENT 'A = Active, NULL = Inactive',
	UNIQUE (StateID, SpeciesID, Status),
	FOREIGN KEY (StateID) REFERENCES State (StateID),
	FOREIGN KEY (SpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE
);

CREATE TABLE SmallProductRoyaltyRate
(
	SmallProductRoyaltyRateID BIGINT PRIMARY KEY,
	SmallProductID INT NOT NULL,
	UnitID INT NOT NULL,
	RoyaltyRate DOUBLE NOT NULL,
	CessRate DOUBLE NOT NULL,
	StartDate DATE,
	EndDate DATE,
	Status VARCHAR(1) COMMENT 'A = Active, NULL = Inactive',
	UNIQUE (SmallProductID, UnitID, Status),
	FOREIGN KEY (SmallProductID) REFERENCES SmallProduct (SmallProductID),
	FOREIGN KEY (UnitID) REFERENCES Unit (UnitID)
);

CREATE TABLE LogSize
(
	LogSizeID INT PRIMARY KEY AUTO_INCREMENT,
	StateID INT,
	MinBigSize DOUBLE NOT NULL COMMENT 'Saiz Minimum bagi balak bersaiz besar',
	MinSmallSize DOUBLE NOT NULL COMMENT 'Saiz minimum bagi balak kecil',
	StartDate DATE,
	EndDate DATE,
	Status VARCHAR(1) NOT NULL COMMENT 'A=Active, I=Inactive',
	FOREIGN KEY (StateID) REFERENCES State (StateID)
);

CREATE TABLE HallOfficer
(
	HallOfficerID BIGINT PRIMARY KEY,
	StaffID VARCHAR(50) NOT NULL,
	HammerNo VARCHAR(50),
	StartDate DATE NOT NULL,
	EndDate DATE NOT NULL,
	HallID BIGINT,
	Status VARCHAR(1),
	UNIQUE (StaffID, HammerNo, HallID, Status),
	FOREIGN KEY (StaffID) REFERENCES Staff (StaffID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (HammerNo) REFERENCES Hammer (HammerNo),
	FOREIGN KEY (HallID) REFERENCES Hall (HallID)
);

CREATE TABLE PermitType
(
	PermitTypeID INT PRIMARY KEY AUTO_INCREMENT,
	Code VARCHAR(4) NOT NULL,
	Name VARCHAR(100) NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A="Active", I="Inactive"'
);

CREATE TABLE ForestDevelopmentWorkType
(
	ForestDevelopmentWorkTypeID INT PRIMARY KEY AUTO_INCREMENT,
	HeaderNo INT NOT NULL DEFAULT 0,
	Description VARCHAR(100) NOT NULL DEFAULT '0',
	Status VARCHAR(1) NOT NULL DEFAULT '0'
);

CREATE TABLE ForestDevelopmentSubWorkType
(
	ForestDevelopmentSubWorkTypeID INT PRIMARY KEY AUTO_INCREMENT,
	ForestDevelopmentWorkTypeID INT NOT NULL,
	HeaderNo VARCHAR(1) NOT NULL DEFAULT '0',
	Description VARCHAR(300) NOT NULL DEFAULT '0',
	Status VARCHAR(1) NOT NULL DEFAULT '0',
	FOREIGN KEY (ForestDevelopmentWorkTypeID) REFERENCES ForestDevelopmentWorkType (ForestDevelopmentWorkTypeID)
);

CREATE TABLE ForestDevelopmentContractor
(
	ForestDevelopmentContractorID BIGINT PRIMARY KEY,
	ReceiptID BIGINT NOT NULL,
	RegistrationNo VARCHAR(50) NOT NULL,
	Name VARCHAR(100) NOT NULL,
	ICNo VARCHAR(20) NOT NULL,
	CompanyName VARCHAR(100) NOT NULL,
	Address VARCHAR(100) NOT NULL,
	RegisteredAddress VARCHAR(100) NOT NULL,
	StateID INT NOT NULL,
	TelNo VARCHAR(20) NOT NULL,
	HPNo VARCHAR(20) NOT NULL,
	RegisteredBusinessNo VARCHAR(20) COMMENT 'boleh buang',
	LicenseStatusID INT NOT NULL,
	StartDate DATE NOT NULL,
	EndDate DATE NOT NULL,
	RegistrationDate DATE NOT NULL,
	ContractorServiceCenterTitle VARCHAR(100) NOT NULL,
	PKKRegistrationCertificateNo VARCHAR(20) NOT NULL,
	CIDBRegistrationNo VARCHAR(20) NOT NULL,
	ManagementStaffNo INT NOT NULL,
	SupervisionStaffNo INT NOT NULL,
	SkilledStaffNo INT NOT NULL,
	UnskilledStaffNo INT NOT NULL,
	OthersStaffNo INT NOT NULL,
	MachineryDescription VARCHAR(100) NOT NULL,
	PreviousExperience VARCHAR(100) NOT NULL,
	RecorderID VARCHAR(50) NOT NULL,
	RecordTime TIMESTAMP NOT NULL,
	Status VARCHAR(1) NOT NULL,
	FOREIGN KEY (StateID) REFERENCES State (StateID),
	FOREIGN KEY (LicenseStatusID) REFERENCES LicenseStatus (LicenseStatusID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE ForestDevelopmentContractorSubWorkTypeRecord
(
	ForestDevelopmentContractorSubWorkTypeRecordID INT PRIMARY KEY AUTO_INCREMENT,
	ForestDevelopmentContractorID BIGINT NOT NULL,
	ForestDevelopmentSubWorkTypeID INT NOT NULL,
	Status VARCHAR(1) NOT NULL DEFAULT '0',
	FOREIGN KEY (ForestDevelopmentContractorID) REFERENCES ForestDevelopmentContractor (ForestDevelopmentContractorID),
	FOREIGN KEY (ForestDevelopmentSubWorkTypeID) REFERENCES ForestDevelopmentSubWorkType (ForestDevelopmentSubWorkTypeID)
);

CREATE TABLE LoggingContractor
(
	LoggingContractorID BIGINT PRIMARY KEY AUTO_INCREMENT,
	ReceiptID BIGINT NOT NULL,
	RegistrationSerialNo VARCHAR(20) NOT NULL UNIQUE,
	Type VARCHAR(1) NOT NULL COMMENT 'L = Licensee, C = Contractor',
	Name VARCHAR(100) NOT NULL,
	ICNo VARCHAR(20) NOT NULL,
	CompanyName VARCHAR(100) NOT NULL,
	Address VARCHAR(100) NOT NULL,
	RegisteredAddress VARCHAR(100) NOT NULL,
	BusinessRegistrationNo VARCHAR(20) NOT NULL,
	TelNo VARCHAR(20) NOT NULL,
	LicenseStatusID INT NOT NULL,
	StartDate DATE NOT NULL,
	EndDate DATE NOT NULL,
	RegistrationDate DATE NOT NULL,
	ManagementStaffNo INT NOT NULL,
	ClericalStaffNo INT NOT NULL,
	OthersStaffNo INT NOT NULL,
	JCBNo INT NOT NULL,
	PenyanggaNo INT NOT NULL,
	PenggeredNo INT NOT NULL,
	LorryNo INT NOT NULL,
	PreviousLicensePermitNo VARCHAR(20),
	Area DOUBLE,
	StateID INT,
	RecorderID VARCHAR(50) NOT NULL,
	RecordTime TIMESTAMP NOT NULL,
	Status VARCHAR(1) NOT NULL,
	FOREIGN KEY (LicenseStatusID) REFERENCES LicenseStatus (LicenseStatusID),
	FOREIGN KEY (StateID) REFERENCES State (StateID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE PreFellingSurvey
(
	PreFellingSurveyID BIGINT PRIMARY KEY,
	ComptBlockNo VARCHAR(20) NOT NULL,
	Area DOUBLE NOT NULL,
	Year INT NOT NULL,
	StartDate DATE,
	EndDate DATE,
	Open INT NOT NULL,
	CuttingOptionID INT,
	ForestID INT NOT NULL,
	RangeID INT,
	TeamLeaderID VARCHAR(50),
	TenderNo VARCHAR(20),
	CreatorID VARCHAR(50) NOT NULL,
	UNIQUE (ForestID, ComptBlockNo, Year),
	FOREIGN KEY (CuttingOptionID) REFERENCES CuttingOption (CuttingOptionID),
	FOREIGN KEY (ForestID) REFERENCES Forest (ForestID),
	FOREIGN KEY (RangeID) REFERENCES `Range` (RangeID),
	FOREIGN KEY (TeamLeaderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (TenderNo) REFERENCES Tender (TenderNo) ON UPDATE CASCADE,
	FOREIGN KEY (CreatorID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE PreFellingSurveyTeam
(
	RecorderID VARCHAR(50) NOT NULL,
	PreFellingSurveyID BIGINT NOT NULL,
	PRIMARY KEY (RecorderID, PreFellingSurveyID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (PreFellingSurveyID) REFERENCES PreFellingSurvey (PreFellingSurveyID)
);

CREATE TABLE PreFellingSurveyCard
(
	PreFellingSurveyCardID BIGINT PRIMARY KEY,
	LineNo VARCHAR(20) NOT NULL,
	PlotNo VARCHAR(20) NOT NULL,
	SubBaseNo VARCHAR(20),
	Latitude DOUBLE,
	Longitude DOUBLE,
	ForestTypeID INT,
	SoilTypeID INT,
	Geology VARCHAR(5),
 	AreaStatusID INT,
	SoilStatusID INT,
	AspectID INT,
	Slope INT,
	SlopeLocationID INT,
	ElevationID INT,
	Bertam INT,
	Palm INT,
	ResamID INT,
	RattanA INT,
	RattanB INT,
	RattanC INT,
	RattanD INT,
	RattanE INT,
	RattanF INT,
	RattanG INT,
	BambooA INT,
	BambooB INT,
	BambooC INT,
	SurveyDate DATE NOT NULL,
	InspectionDate DATE,
	PreFellingSurveyID BIGINT NOT NULL,
	RecorderID VARCHAR(50) NOT NULL,
	InspectorID VARCHAR(50),
	UNIQUE (PreFellingSurveyID, LineNo, PlotNo, SubBaseNo),
	FOREIGN KEY (ForestTypeID) REFERENCES ForestType (ForestTypeID),
	FOREIGN KEY (SoilTypeID) REFERENCES SoilType (SoilTypeID),
	FOREIGN KEY (AreaStatusID) REFERENCES AreaStatus (AreaStatusID),
	FOREIGN KEY (SoilStatusID) REFERENCES SoilStatus (SoilStatusID),
	FOREIGN KEY (AspectID) REFERENCES Aspect (AspectID),
	FOREIGN KEY (SlopeLocationID) REFERENCES SlopeLocation (SlopeLocationID),
	FOREIGN KEY (ElevationID) REFERENCES Elevation (ElevationID),
	FOREIGN KEY (ResamID) REFERENCES Resam (ResamID),
	FOREIGN KEY (PreFellingSurveyID) REFERENCES PreFellingSurvey (PreFellingSurveyID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (InspectorID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE PreFellingSurveyRecord
(
	PreFellingSurveyRecordID BIGINT PRIMARY KEY,
	Diameter DOUBLE,
	LogQuantity INT,
	LogQualityID INT,
	FertilityID INT,
	VineDiameter INT,
	VineSpreadthID INT,
	SaplingQuantity INT,
	SeedlingQuantity INT,
	PreFellingSurveyCardID BIGINT NOT NULL,
	SpeciesID INT NOT NULL,
	PlotTypeID INT NOT NULL,
	FOREIGN KEY (LogQualityID) REFERENCES LogQuality (LogQualityID) ON DELETE CASCADE,
	FOREIGN KEY (FertilityID) REFERENCES Fertility (FertilityID) ON DELETE CASCADE,
	FOREIGN KEY (VineSpreadthID) REFERENCES VineSpreadth (VineSpreadthID) ON DELETE CASCADE,
	FOREIGN KEY (PreFellingSurveyCardID) REFERENCES PreFellingSurveyCard (PreFellingSurveyCardID) ON DELETE CASCADE,
	FOREIGN KEY (SpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE,
	FOREIGN KEY (PlotTypeID) REFERENCES PlotType (PlotTypeID) ON DELETE CASCADE
);

CREATE TABLE CuttingLimit
(
	MinDiameter DOUBLE NOT NULL,
	PreFellingSurveyID BIGINT NOT NULL,
	SpeciesID INT NOT NULL,
	PRIMARY KEY (PreFellingSurveyID, SpeciesID),
	FOREIGN KEY (PreFellingSurveyID) REFERENCES PreFellingSurvey (PreFellingSurveyID) ON DELETE CASCADE,
	FOREIGN KEY (SpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE
);

CREATE TABLE CuttingOptionRecommendation
(
	Rank INT NOT NULL,
	PreFellingSurveyID BIGINT NOT NULL,
	CuttingOptionID INT NOT NULL,
	PRIMARY KEY (PreFellingSurveyID, CuttingOptionID),
	FOREIGN KEY (PreFellingSurveyID) REFERENCES PreFellingSurvey (PreFellingSurveyID) ON DELETE CASCADE,
	FOREIGN KEY (CuttingOptionID) REFERENCES CuttingOption (CuttingOptionID) ON DELETE CASCADE
);

CREATE TABLE Tagging
(
	TaggingID BIGINT PRIMARY KEY,
	Year INT NOT NULL,
	StartDate DATE,
	EndDate DATE,
	Open INT NOT NULL,
	GrossVolumeLimit DOUBLE NOT NULL,
	NetVolumeLimit DOUBLE NOT NULL,
	HallID BIGINT,
	TeamLeaderID VARCHAR(50),
	TenderNo VARCHAR(20),
	CreatorID VARCHAR(50) NOT NULL,
	PreFellingSurveyID BIGINT NOT NULL,
	FOREIGN KEY (HallID) REFERENCES Hall (HallID),
	FOREIGN KEY (TeamLeaderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (TenderNo) REFERENCES Tender (TenderNo) ON UPDATE CASCADE,
	FOREIGN KEY (CreatorID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (PreFellingSurveyID) REFERENCES PreFellingSurvey (PreFellingSurveyID)
);

CREATE TABLE TaggingTeam
(
	RecorderID VARCHAR(50) NOT NULL,
	TaggingID BIGINT NOT NULL,
	PRIMARY KEY (RecorderID, TaggingID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (TaggingID) REFERENCES Tagging (TaggingID)
);

CREATE TABLE TaggingHammer
(
	HammerNo VARCHAR(50) NOT NULL,
	TaggingID BIGINT NOT NULL,
	PRIMARY KEY (HammerNo, TaggingID),
	FOREIGN KEY (HammerNo) REFERENCES Hammer (HammerNo) ON UPDATE CASCADE,
	FOREIGN KEY (TaggingID) REFERENCES Tagging (TaggingID)
);

CREATE TABLE TaggingLimitException
(
	BlockNo VARCHAR(20) NOT NULL,
	PlotNo VARCHAR(20) NOT NULL,
	Quantity INT NOT NULL,
	TaggingID BIGINT NOT NULL,
	UNIQUE (TaggingID, BlockNo, PlotNo),
	FOREIGN KEY (TaggingID) REFERENCES Tagging (TaggingID)
);

CREATE TABLE TaggingForm
(
	TaggingFormID BIGINT PRIMARY KEY,
	FormNo VARCHAR(20) NOT NULL,
	Length DOUBLE,
	Width DOUBLE,
	TaggingDate DATE NOT NULL,
	InspectionDate DATE,
	TaggingID BIGINT NOT NULL,
	TaggingTypeID INT NOT NULL,
	RecorderID VARCHAR(50) NOT NULL,
	InspectorID VARCHAR(50),
	UNIQUE (TaggingID, FormNo, TaggingTypeID),
	FOREIGN KEY (TaggingID) REFERENCES Tagging (TaggingID),
	FOREIGN KEY (TaggingTypeID) REFERENCES TaggingType (TaggingTypeID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (InspectorID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE TaggingRecord
(
	TaggingRecordID BIGINT PRIMARY KEY,
	PlotNo VARCHAR(20),
	SerialNo VARCHAR(20) NOT NULL UNIQUE,
	SpeciesID INT NOT NULL,
	TreeTypeID INT,
	Diameter DOUBLE NOT NULL,
	Estimation INT,
	LogQualityID INT,
	Note VARCHAR(100) NOT NULL,
	Latitude DOUBLE,
	Longitude DOUBLE,
	TaggingFormID BIGINT NOT NULL,
	CorrectedEstimation INT,
	Status VARCHAR(1) COMMENT 'O = Open, C = Closed, P = Pending',
	FOREIGN KEY (SpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE,
	FOREIGN KEY (TreeTypeID) REFERENCES TreeType (TreeTypeID) ON DELETE CASCADE,
	FOREIGN KEY (LogQualityID) REFERENCES LogQuality (LogQualityID) ON DELETE CASCADE,
	FOREIGN KEY (TaggingFormID) REFERENCES TaggingForm (TaggingFormID) ON DELETE CASCADE
);

CREATE TABLE PostFellingSurvey
(
	PostFellingSurveyID BIGINT PRIMARY KEY,
	Year INT NOT NULL,
	StartDate DATE,
	EndDate DATE,
	Open INT NOT NULL,
	TeamLeaderID VARCHAR(50),
	TenderNo VARCHAR(20),
	CreatorID VARCHAR(50) NOT NULL,
	PreFellingSurveyID BIGINT NOT NULL,
	InspectionLeaderID VARCHAR(50),
	InspectionStartDate DATE,
	InspectionEndDate DATE,
	InspectionOpen INT,
	OwnershipDate DATE,
	InspectionNo INT NOT NULL,
	InspectionSignage INT NOT NULL,
	InspectionBearing INT NOT NULL,
	InspectionLineDistance INT NOT NULL,
	InspectionStake INT NOT NULL,
	InspectionSteepness INT NOT NULL,
	CommentInspectionLeader TEXT,
	CommentPPPN TEXT,
	InspectionStartWorkDate DATE,
	InspectionEndWorkDate DATE,
	FOREIGN KEY (TeamLeaderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (TenderNo) REFERENCES Tender (TenderNo) ON UPDATE CASCADE,
	FOREIGN KEY (CreatorID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (PreFellingSurveyID) REFERENCES PreFellingSurvey (PreFellingSurveyID),
	FOREIGN KEY (InspectionLeaderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE PostFellingSurveyTeam
(
	RecorderID VARCHAR(50) NOT NULL,
	PostFellingSurveyID BIGINT NOT NULL,
	PRIMARY KEY (RecorderID, PostFellingSurveyID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (PostFellingSurveyID) REFERENCES PostFellingSurvey (PostFellingSurveyID)
);

CREATE TABLE PostFellingInspectionTeam
(
	RecorderID VARCHAR(50) NOT NULL,
	PostFellingSurveyID BIGINT NOT NULL,
	PRIMARY KEY (RecorderID, PostFellingSurveyID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (PostFellingSurveyID) REFERENCES PostFellingSurvey (PostFellingSurveyID)
);

CREATE TABLE PostFellingSurveyCard
(
	PostFellingSurveyCardID BIGINT PRIMARY KEY,
	LineNo VARCHAR(20) NOT NULL,
	PlotNo VARCHAR(20) NOT NULL,
	Latitude DOUBLE,
	Longitude DOUBLE,
	ForestTypeID INT,
	SoilStatusID INT,
	AspectID INT,
	Slope INT NOT NULL,
	SlopeLocationID INT,
	ElevationID INT,
	Bertam INT NOT NULL,
	Bamboo INT NOT NULL,
	Palm INT NOT NULL,
	ResamID INT,
	GingerID INT,
	BananaID INT,
	SurveyDate DATE NOT NULL,
	PostFellingSurveyID BIGINT NOT NULL,
	RecorderID VARCHAR(50) NOT NULL,
	InspectorID VARCHAR(50),
	InspectionDate DATE,
	InspectionForestTypeID INT,
	InspectionSoilStatusID INT,
	InspectionAspectID INT,
	InspectionSlope INT,
	InspectionSlopeLocationID INT,
	InspectionElevationID INT,
	InspectionBertam INT,
	InspectionBamboo INT,
	InspectionPalm INT,
	InspectionResamID INT,
	InspectionGingerID INT,
	InspectionBananaID INT,
	Inspection INT,
	UNIQUE (PostFellingSurveyID, LineNo, PlotNo),
	FOREIGN KEY (ForestTypeID) REFERENCES ForestType (ForestTypeID),
	FOREIGN KEY (SoilStatusID) REFERENCES SoilStatus (SoilStatusID),
	FOREIGN KEY (AspectID) REFERENCES Aspect (AspectID),
	FOREIGN KEY (SlopeLocationID) REFERENCES SlopeLocation (SlopeLocationID),
	FOREIGN KEY (ElevationID) REFERENCES Elevation (ElevationID),
	FOREIGN KEY (ResamID) REFERENCES Resam (ResamID),
	FOREIGN KEY (GingerID) REFERENCES Ginger (GingerID),
	FOREIGN KEY (BananaID) REFERENCES Banana (BananaID),
	FOREIGN KEY (PostFellingSurveyID) REFERENCES PostFellingSurvey (PostFellingSurveyID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (InspectorID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (InspectionForestTypeID) REFERENCES ForestType (ForestTypeID),
	FOREIGN KEY (InspectionSoilStatusID) REFERENCES SoilStatus (SoilStatusID),
	FOREIGN KEY (InspectionAspectID) REFERENCES Aspect (AspectID),
	FOREIGN KEY (InspectionSlopeLocationID) REFERENCES SlopeLocation (SlopeLocationID),
	FOREIGN KEY (InspectionElevationID) REFERENCES Elevation (ElevationID),
	FOREIGN KEY (InspectionResamID) REFERENCES Resam (ResamID),
	FOREIGN KEY (InspectionGingerID) REFERENCES Ginger (GingerID),
	FOREIGN KEY (InspectionBananaID) REFERENCES Banana (BananaID)
);

CREATE TABLE PostFellingSurveyRecord
(
	PostFellingSurveyRecordID BIGINT PRIMARY KEY,
	Diameter DOUBLE,
	LogQuantity INT,
	LogQualityID INT,
	TreeStatusID INT,
	SilaraID INT,
	DominanceID INT,
	Vine INT,
	FertilityID INT,
	SaplingQuantity INT,
	SeedlingQuantity INT,
	PostFellingSurveyCardID BIGINT NOT NULL,
	SpeciesID INT,
	PlotTypeID INT NOT NULL,
	InspectionDiameter DOUBLE,
	InspectionLogQuantity INT,
	InspectionLogQualityID INT,
	InspectionTreeStatusID INT,
	InspectionSilaraID INT,
	InspectionDominanceID INT,
	InspectionVine INT,
	InspectionFertilityID INT,
	InspectionSaplingQuantity INT,
	InspectionSeedlingQuantity INT,
	InspectionSpeciesID INT,
	Inspection INT NOT NULL,
	FOREIGN KEY (LogQualityID) REFERENCES LogQuality (LogQualityID) ON DELETE CASCADE,
	FOREIGN KEY (TreeStatusID) REFERENCES TreeStatus (TreeStatusID) ON DELETE CASCADE,
	FOREIGN KEY (SilaraID) REFERENCES Silara (SilaraID) ON DELETE CASCADE,
	FOREIGN KEY (DominanceID) REFERENCES Dominance (DominanceID) ON DELETE CASCADE,
	FOREIGN KEY (FertilityID) REFERENCES Fertility (FertilityID) ON DELETE CASCADE,
	FOREIGN KEY (PostFellingSurveyCardID) REFERENCES PostFellingSurveyCard (PostFellingSurveyCardID) ON DELETE CASCADE,
	FOREIGN KEY (SpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE,
	FOREIGN KEY (PlotTypeID) REFERENCES PlotType (PlotTypeID) ON DELETE CASCADE,
	FOREIGN KEY (InspectionLogQualityID) REFERENCES LogQuality (LogQualityID) ON DELETE CASCADE,
	FOREIGN KEY (InspectionTreeStatusID) REFERENCES TreeStatus (TreeStatusID) ON DELETE CASCADE,
	FOREIGN KEY (InspectionSilaraID) REFERENCES Silara (SilaraID) ON DELETE CASCADE,
	FOREIGN KEY (InspectionDominanceID) REFERENCES Dominance (DominanceID) ON DELETE CASCADE,
	FOREIGN KEY (InspectionFertilityID) REFERENCES Fertility (FertilityID) ON DELETE CASCADE,
	FOREIGN KEY (InspectionSpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE
);

CREATE TABLE PostFellingInspectionLine
(
	PostFellingInspectionLineID BIGINT PRIMARY KEY,
	PostFellingSurveyID BIGINT NOT NULL,
	LineNo VARCHAR(10) NOT NULL,
	LineBefore DOUBLE NOT NULL,
	LineAfter DOUBLE NOT NULL,
	StartPole TINYINT NOT NULL,
	EndPole TINYINT NOT NULL,
	BearingError TINYINT NOT NULL,
	PlotError TINYINT NOT NULL,
	Stake TINYINT NOT NULL,
	Steepness TINYINT NOT NULL,
	BoundarySignageStart TINYINT NOT NULL,
	BoundaryCleanStart TINYINT NOT NULL,
	BoundarySignageEnd TINYINT NOT NULL,
	BoundaryCleanEnd TINYINT NOT NULL,
	UNIQUE (PostFellingSurveyID, LineNo),
	FOREIGN KEY (PostFellingSurveyID) REFERENCES PostFellingSurvey (PostFellingSurveyID)
);

CREATE TABLE License
(
	LicenseID BIGINT PRIMARY KEY,
	ReceiptID BIGINT NOT NULL,
	LicenseNo VARCHAR(30) NOT NULL UNIQUE,
	FileReferenceA VARCHAR(30) NOT NULL,
	FileReferenceB VARCHAR(30) NOT NULL,
	LicenseeID BIGINT,
	ContractorID BIGINT,
	Address VARCHAR(100) COMMENT 'Tanah milik sahaja',
	LicenseTypeID INT NOT NULL,
	StartDate DATE NOT NULL,
	EndDate DATE NOT NULL,
	RegistrationDate DATE NOT NULL,
	FileNo VARCHAR(20) COMMENT 'Tanah',
	ForestCategoryID INT NOT NULL,
	ForestID INT,
	CompartmentNo VARCHAR(20) NOT NULL,
	CompartmentArea DOUBLE NOT NULL,
	HallID BIGINT NOT NULL,
	HallOfficerID BIGINT NOT NULL,
	WoodWorkFund DOUBLE,
	LicenseFund DOUBLE,
	ResinLimit DOUBLE COMMENT 'untuk tagging sahaja',
	NonResinLimit DOUBLE COMMENT 'untuk tagging sahaja',
	ChengalLimit DOUBLE COMMENT 'untuk tagging sahaja',
	LogLimit DOUBLE,
	JarasLimit DOUBLE,
	WasteWoodLimit DOUBLE,
	RecorderID VARCHAR(50) NOT NULL,
	RecordTime TIMESTAMP NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A=Active, I=Inactive(deleted), C=Closed',
	FOREIGN KEY (LicenseeID) REFERENCES LoggingContractor (LoggingContractorID),
	FOREIGN KEY (ContractorID) REFERENCES LoggingContractor (LoggingContractorID),
	FOREIGN KEY (LicenseTypeID) REFERENCES LicenseType (LicenseTypeID),
	FOREIGN KEY (ForestCategoryID) REFERENCES ForestCategory (ForestCategoryID),
	FOREIGN KEY (ForestID) REFERENCES Forest (ForestID),
	FOREIGN KEY (HallID) REFERENCES Hall (HallID),
	FOREIGN KEY (HallOfficerID) REFERENCES HallOfficer (HallOfficerID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE Permit
(
	PermitID BIGINT PRIMARY KEY,
	ReceiptID BIGINT NOT NULL,
	PermitTypeID INT NOT NULL COMMENT 'M = Matau, K = Kongsi, J = Jalan',
	PermitNo VARCHAR(30) NOT NULL,
	FileReferencePHN VARCHAR(30) NOT NULL,
	FileReferencePHD VARCHAR(30) NOT NULL,
	LicenseID BIGINT,
	ForestID INT NOT NULL,
	CompartmentNo VARCHAR(20) NOT NULL,
	StartDate DATE NOT NULL,
	EndDate DATE NOT NULL,
	RegistrationDate DATE NOT NULL,
	ReferenceNo VARCHAR(30),
	PermitFund DOUBLE,
	RecorderID VARCHAR(50) NOT NULL,
	RecordTime TIMESTAMP NOT NULL,
	-- XXX JalanMatauKongsiFund DOUBLE NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A = Active, E = Expired, I = Inactive',
	FOREIGN KEY (PermitTypeID) REFERENCES PermitType (PermitTypeID),
	FOREIGN KEY (LicenseID) REFERENCES License (LicenseID),
	FOREIGN KEY (ForestID) REFERENCES Forest (ForestID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE Receipt
(
	ReceiptID BIGINT PRIMARY KEY,
	ReceiptNo VARCHAR(20) NOT NULL UNIQUE,
	Category INT COMMENT '1 = Persendirian,  2 = Daftar ForestDevelopmentContractor, 3 = Daftar LoggingContractor, 4 = Daftar Lesen, 5 = Daftar Permit',
	ForestDevelopmentContractorID BIGINT,
	LoggingContractorID BIGINT,
	LicenseID BIGINT,
	PermitID BIGINT,
	Name VARCHAR(100),
	Date DATE,
	Notes VARCHAR(100),
	CollectorStatement VARCHAR(30),
	PaymentTypeID INT,
	BankID INT,
	ChequeTypeID INT,
	ChequeNo VARCHAR(20),
	ChequeDate DATE,
	GrandTotal DOUBLE,
	RecorderID VARCHAR(50) NOT NULL,
	RecordTime TIMESTAMP NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A=Active, C=Cancel, I=Inactive(belum claim)',
	FOREIGN KEY (ForestDevelopmentContractorID) REFERENCES ForestDevelopmentContractor (ForestDevelopmentContractorID),
	FOREIGN KEY (LoggingContractorID) REFERENCES LoggingContractor (LoggingContractorID),
	FOREIGN KEY (LicenseID) REFERENCES License (LicenseID),
	FOREIGN KEY (PermitID) REFERENCES Permit (PermitID),
	FOREIGN KEY (PaymentTypeID) REFERENCES PaymentType (PaymentTypeID),
	FOREIGN KEY (BankID) REFERENCES Bank (BankID),
	FOREIGN KEY (ChequeTypeID) REFERENCES ChequeType (ChequeTypeID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

ALTER TABLE License ADD CONSTRAINT License_Receipt_FK FOREIGN KEY License (ReceiptID) REFERENCES Receipt (ReceiptID);
ALTER TABLE Permit ADD CONSTRAINT Permit_Receipt_FK FOREIGN KEY Permit (ReceiptID) REFERENCES Receipt (ReceiptID);
ALTER TABLE ForestDevelopmentContractor ADD CONSTRAINT ForestDevelopmentContractor_Receipt_FK FOREIGN KEY ForestDevelopmentContractor (ReceiptID) REFERENCES Receipt (ReceiptID);
ALTER TABLE LoggingContractor ADD CONSTRAINT LoggingContractor_Receipt_FK FOREIGN KEY LoggingContractor (ReceiptID) REFERENCES Receipt (ReceiptID);

CREATE TABLE ReceiptRecord
(
	ReceiptRecordID BIGINT PRIMARY KEY,
	ReceiptID BIGINT NOT NULL,
	DepartmentVotID INT NOT NULL,
	Description VARCHAR(100),
	Rate DOUBLE NOT NULL,
	Quantity DOUBLE NOT NULL,
	FOREIGN KEY (ReceiptID) REFERENCES Receipt (ReceiptID) ON DELETE CASCADE,
	FOREIGN KEY (DepartmentVotID) REFERENCES DepartmentVot (DepartmentVotID)
);

CREATE TABLE Journal
(
	JournalID BIGINT PRIMARY KEY,
	JournalNo VARCHAR(20) NOT NULL,
	Name VARCHAR(100),
	Remarks VARCHAR(100),
	Date DATE NOT NULL,
	Category VARCHAR(1),
	LicenseID BIGINT,
	PermitID BIGINT,
	RecorderID VARCHAR(20) NOT NULL,
	RecordTime TIMESTAMP NOT NULL,
	Total DOUBLE NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A = Active, I = Inactive',
	FOREIGN KEY (LicenseID) REFERENCES License (LicenseID),
	FOREIGN KEY (PermitID) REFERENCES Permit (PermitID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE JournalRecord
(
	JournalRecordID BIGINT PRIMARY KEY,
	JournalID BIGINT NOT NULL,
	DepartmentVotID INT NOT NULL,
	TrustFundID INT NOT NULL,
	Description VARCHAR(100) NOT NULL,
	Total DOUBLE NOT NULL,
	FOREIGN KEY (DepartmentVotID) REFERENCES DepartmentVot (DepartmentVotID),
	FOREIGN KEY (JournalID) REFERENCES Journal (JournalID),
	FOREIGN KEY (TrustFundID) REFERENCES TrustFund (TrustFundID)
);

CREATE TABLE Voucher
(
	VoucherID BIGINT PRIMARY KEY,
	VoucherNo VARCHAR(10) NOT NULL,
	Remarks VARCHAR(100),
	Date DATE NOT NULL,
	Category VARCHAR(1) NOT NULL COMMENT '0 = Baucar tutup lesen',
	LicenseID BIGINT,
	PermitID BIGINT,
	RecorderID VARCHAR(50) NOT NULL,
	RecordTime TIMESTAMP NOT NULL,
	Total DOUBLE NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A = Active, I = Inactive',
	UNIQUE (VoucherNo),
	FOREIGN KEY (LicenseID) REFERENCES License (LicenseID),
	FOREIGN KEY (PermitID) REFERENCES Permit (PermitID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE
);

CREATE TABLE VoucherRecord
(
	VoucherRecordID BIGINT PRIMARY KEY,
	VoucherID BIGINT NOT NULL,
	TrustFundID INT,
	Description VARCHAR(100) NOT NULL,
	Total DOUBLE NOT NULL,
	FOREIGN KEY (VoucherID) REFERENCES Voucher (VoucherID),
	FOREIGN KEY (TrustFundID) REFERENCES TrustFund (TrustFundID)
);

CREATE TABLE Renew
(
	RenewID BIGINT PRIMARY KEY,
	Type VARCHAR(1) NOT NULL COMMENT 'F = ForestDevelopmentContractor, C = LoggingContractor, L = License, P = Permit',
	ForestDevelopmentContractorID BIGINT,
	LoggingContractorID BIGINT,
	LicenseID BIGINT,
	PermitID BIGINT,
	ReceiptID BIGINT NOT NULL,
	StartDate DATE NOT NULL,
	EndDate DATE NOT NULL,
	Status VARCHAR(1) NOT NULL COMMENT 'A = Active, I = Inactive',
	FOREIGN KEY (ForestDevelopmentContractorID) REFERENCES ForestDevelopmentContractor (ForestDevelopmentContractorID),
	FOREIGN KEY (LoggingContractorID) REFERENCES LoggingContractor (LoggingContractorID),
	FOREIGN KEY (LicenseID) REFERENCES License (LicenseID),
	FOREIGN KEY (PermitID) REFERENCES Permit (PermitID),
	FOREIGN KEY (ReceiptID) REFERENCES Receipt (ReceiptID)
);

CREATE TABLE Log
(
	LogID BIGINT PRIMARY KEY,
	LogNo INT NOT NULL DEFAULT 1,
	LogSerialNo VARCHAR(20) NOT NULL UNIQUE,
	Length DOUBLE NOT NULL,
	Diameter DOUBLE DEFAULT 0,
	HoleDiameter DOUBLE DEFAULT 0,
	TaggingRecordID BIGINT,
	Status VARCHAR(1) COMMENT 'O = Open, C = Closed, P = Processed',
	FOREIGN KEY (TaggingRecordID) REFERENCES TaggingRecord (TaggingRecordID)
);

CREATE TABLE TransferPass
(
	TransferPassID BIGINT PRIMARY KEY,
	TransferPassNo VARCHAR(20) NOT NULL UNIQUE,
	BatchNo VARCHAR(20),
	Date DATE NOT NULL,
	HallOfficerID BIGINT NOT NULL,
	Code INT NOT NULL COMMENT '0 - HU, 1-KK, 2-P, 01-HU & KK, 02-HU & P, 12-KK & P',
	RoyaltyRate INT NOT NULL,
	CessRate INT NOT NULL,
	PremiumRate INT NOT NULL,
	LicenseID BIGINT NOT NULL,
	DestinationAddress VARCHAR(100) NOT NULL,
	DestinationStateID INT,
	DriverName VARCHAR(100),
	DriverICNo VARCHAR(14),
	DriverAddress VARCHAR(100),
	VehicleNo VARCHAR(10),
	GrossVehicleWeight DOUBLE,
	TaggingID BIGINT,
	LogSizeID INT,
	RoyaltyAmount DOUBLE,
	CessAmount DOUBLE,
	PremiumAmount DOUBLE,
	RecorderID VARCHAR(20) NOT NULL,
	RecordTime TIMESTAMP NOT NULL,
	Remarks VARCHAR(100),
	JournalID BIGINT,
	Status VARCHAR(1) NOT NULL COMMENT 'I = Deleted, A = Active, P = Sudah Jurnal',
	FOREIGN KEY (DestinationStateID) REFERENCES State (StateID),
	FOREIGN KEY (HallOfficerID) REFERENCES HallOfficer (HallOfficerID),
	FOREIGN KEY (JournalID) REFERENCES Journal (JournalID),
	FOREIGN KEY (LicenseID) REFERENCES License (LicenseID),
	FOREIGN KEY (LogSizeID) REFERENCES LogSize (LogSizeID),
	FOREIGN KEY (RecorderID) REFERENCES Staff (StaffID) ON UPDATE CASCADE,
	FOREIGN KEY (TaggingID) REFERENCES Tagging (TaggingID)
);

CREATE TABLE MainRevenueTransferPassRecord
(
	MainRevenueTransferPassRecordID BIGINT PRIMARY KEY,
	TransferPassID BIGINT NOT NULL,
	LogID BIGINT NOT NULL,
	MainRevenueRoyaltyRateID BIGINT NOT NULL,
	Royalty DOUBLE NOT NULL,
	Cess DOUBLE NOT NULL,
	FOREIGN KEY (LogID) REFERENCES Log (LogID),
	FOREIGN KEY (MainRevenueRoyaltyRateID) REFERENCES MainRevenueRoyaltyRate (MainRevenueRoyaltyRateID),
	FOREIGN KEY (TransferPassID) REFERENCES TransferPass (TransferPassID)
);

CREATE TABLE SmallProductTransferPassRecord
(
	SmallProductTransferPassRecordID BIGINT PRIMARY KEY,
	TransferPassID BIGINT NOT NULL,
	SmallProductID INT NOT NULL,
	UnitID INT NOT NULL,
	SmallProductRoyaltyRateID BIGINT NOT NULL,
	Quantity DOUBLE NOT NULL,
	Royalty DOUBLE NOT NULL,
	Cess DOUBLE NOT NULL,
	FOREIGN KEY (SmallProductID) REFERENCES SmallProduct (SmallProductID),
	FOREIGN KEY (SmallProductRoyaltyRateID) REFERENCES SmallProductRoyaltyRate (SmallProductRoyaltyRateID),
	FOREIGN KEY (TransferPassID) REFERENCES TransferPass (TransferPassID),
	FOREIGN KEY (UnitID) REFERENCES Unit (UnitID)
);

CREATE TABLE SpecialTransferPassRecord
(
	SpecialTransferPassRecordID BIGINT PRIMARY KEY,
	TransferPassID BIGINT NOT NULL,
	SpeciesID INT NOT NULL,
	MainRevenueRoyaltyRateID BIGINT NOT NULL,
	Length DOUBLE NOT NULL,
	Diameter DOUBLE NOT NULL,
	Royalty DOUBLE NOT NULL,
	Cess DOUBLE NOT NULL,
	FOREIGN KEY (MainRevenueRoyaltyRateID) REFERENCES MainRevenueRoyaltyRate (MainRevenueRoyaltyRateID),
	FOREIGN KEY (SpeciesID) REFERENCES Species (SpeciesID) ON DELETE CASCADE,
	FOREIGN KEY (TransferPassID) REFERENCES TransferPass (TransferPassID)
);

CREATE TABLE Transaction
(
	TransactionID BIGINT PRIMARY KEY,
	TransactionType VARCHAR(4) NOT NULL COMMENT '1st aksara-(0 - Batal,1 - Add), 2nd aksara(1 - Resit, 2 - Voucher, 3 - Jurnal, 4 - PindahanWang), 3rd aksara(untuk resit, 1 - Persendirian, 2 - Daftar Akaun, 3, Daftar Lesen, 4 - Bayaran Lesen, 5 - Perbaharui Lesen | untuk voucher, 1 - Pembayaran Kerja, 2 - Tutup Lesen | untuk jurnal, 1 - Ses dan Royalti, 2 - Dendaan dan Bayaran Khas) Ex. 014 - Batal Resit Bayaran Lesen',
	TransactionFormID BIGINT NOT NULL COMMENT 'Id Resit, Voucher, Jurnal atau PindahanWang yang ditambah atau dibatalkan',
	DepartmentVotID INT NOT NULL,
	Value DOUBLE NOT NULL,
	RecordTime TIMESTAMP NOT NULL,
	FOREIGN KEY (DepartmentVotID) REFERENCES DepartmentVot (DepartmentVotID)
);

CREATE TABLE Revenue
(
	DepartmentVotID INT PRIMARY KEY,
	Value DOUBLE NOT NULL,
	FOREIGN KEY (DepartmentVotID) REFERENCES DepartmentVot (DepartmentVotID)
);