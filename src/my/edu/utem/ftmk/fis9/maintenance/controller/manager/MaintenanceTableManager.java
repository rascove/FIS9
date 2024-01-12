package my.edu.utem.ftmk.fis9.maintenance.controller.manager;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractTableManager;

/**
 * @author Satrya Fajri Pratama
 */
class MaintenanceTableManager extends AbstractTableManager
{
	MaintenanceFacade facade;
	
	MaintenanceTableManager(MaintenanceFacade facade)
	{
		this.facade = facade;
	}
}