package my.edu.utem.ftmk.fis9.prefelling.controller.manager;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractTableManager;

/**
 * @author Satrya Fajri Pratama
 */
abstract class PreFellingTableManager extends AbstractTableManager
{
	PreFellingFacade facade;
	
	PreFellingTableManager(PreFellingFacade facade)
	{
		this.facade = facade;
	}
}