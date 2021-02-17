/**
 * 
 */
package mvcOknoGlowne;

import commons.Race;

/**
 * @author Tom
 *
 */
public interface GenBohKontrolerInterface {
	void selectRasa(Race rs);
	void setRacaCbBox();
	void aktywujPodniesPoziom();
	void aktywujZapiszPostac();
	void aktywujNowaProfesja();
	void aktywujExportDoPdf();
	void wylaczExportDoPdf();
	void aktywujExportDoExcel();

}
