/**
 * 
 */
package mvcOknoGlowne;

import domain.Race;

/**
 * @author Tom
 *
 */
public interface GenBohKontrolerInterface {
	void selectRasa(String rs);
	void setRacaCbBox();
	void aktywujPodniesPoziom();
	void aktywujZapiszPostac();
	void aktywujNowaProfesja();
	void aktywujExportDoPdf();
	void wylaczExportDoPdf();
	void aktywujExportDoExcel();

}
