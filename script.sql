ALTER TABLE `smallproduct`
	ADD COLUMN `Symbol` VARCHAR(50) NULL DEFAULT NULL AFTER `Name`;
ALTER TABLE `taggingrecord`
	ADD COLUMN `CorrectedSpeciesID` INT(11) NULL AFTER `SpeciesID`,
	CHANGE COLUMN `CorrectedEstimation` `CorrectedEstimation` INT(11) NULL DEFAULT NULL AFTER `Estimation`,
	ADD CONSTRAINT `taggingrecord_ibfk_5` FOREIGN KEY (`CorrectedSpeciesID`) REFERENCES `species` (`SpeciesID`) ON DELETE CASCADE;
ALTER TABLE `transferpass`
	ALTER `RecorderID` DROP DEFAULT;
ALTER TABLE `transferpass`
	CHANGE COLUMN `RecorderID` `RecorderID` VARCHAR(50) NOT NULL AFTER `PremiumAmount`;
UPDATE `taggingrecord` SET `CorrectedSpeciesID` = `SpeciesID`;
UPDATE `taggingrecord` SET `CorrectedEstimation` = `Estimation`;	
