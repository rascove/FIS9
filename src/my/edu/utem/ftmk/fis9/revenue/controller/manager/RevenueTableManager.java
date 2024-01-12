package my.edu.utem.ftmk.fis9.revenue.controller.manager;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractTableManager;

/**
 * @author Nor Azman Mat Ariff
 */
abstract class RevenueTableManager extends AbstractTableManager
{
	RevenueFacade facade;
	
	RevenueTableManager(RevenueFacade facade)
	{
		this.facade = facade;
	}
}