package my.edu.utem.ftmk.fis9.hall.controller.manager;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractTableManager;

/**
 * @author Nor Azman Mat Ariff
 */
abstract class HallTableManager extends AbstractTableManager
{
	HallFacade facade;
	
	HallTableManager(HallFacade facade)
	{
		this.facade = facade;
	}
}