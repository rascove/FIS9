package my.edu.utem.ftmk.fis9.tagging.controller.manager;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractTableManager;

/**
 * @author Satrya Fajri Pratama
 */
abstract class TaggingTableManager extends AbstractTableManager
{
	TaggingFacade facade;
	
	TaggingTableManager(TaggingFacade facade)
	{
		this.facade = facade;
	}
}