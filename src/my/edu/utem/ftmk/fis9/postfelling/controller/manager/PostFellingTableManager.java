package my.edu.utem.ftmk.fis9.postfelling.controller.manager;

import my.edu.utem.ftmk.fis9.global.controller.manager.AbstractTableManager;

/**
 * @author Satrya Fajri Pratama
 */
abstract class PostFellingTableManager extends AbstractTableManager
{
	PostFellingFacade facade;
	
	PostFellingTableManager(PostFellingFacade facade)
	{
		this.facade = facade;
	}
}