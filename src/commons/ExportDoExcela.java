package commons;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;

import npcGenerator.CechyPotworow;
import npcGenerator.Potwory;

import org.apache.poi.ss.usermodel.*;

/**
 * klasa która eksportuje zapisanych bohaterów jak i NPC do arkusza excel, ka¿da postaæ jest zapisane do osobnej zak³adki
 * @author Tom
 *
 */
public class ExportDoExcela {
	private static final Logger logger = Logger.getLogger(ExportDoExcela.class.getName()); 
	private static final String [] STATYSTYKI = {"WW","US","S","Wt","I","Zw","Zr","Int","SW","Ogd"};
	
	Workbook wb;
	Potwory potwor;

	
	public ExportDoExcela() {
		setLogger();
		wb = new HSSFWorkbook();
	}
	
	private void setLogger() {
		if(System.getProperty("java.util.logging.config.class")==null && System.getProperty("java.util.logging.config.file")==null) {
			try {
				Logger.getLogger("").setLevel(Level.ALL);
				final int LOG_ROTATION_COUNT = 10;
				Handler handler = new FileHandler("%h/ExportDoExcela.log",0,LOG_ROTATION_COUNT);
				Logger.getLogger("").addHandler(handler);
				
			} catch (Exception e) {
				logger.log(Level.SEVERE,"Nie mo¿na utworzyæ handlera pliku dziennika",e);
			}
		}
		
	}
	
	public void createNPCSheet(Potwory pt) {
		logger.entering("ExportDoExcela", "createNPCSheet");
		//nazwa arkusza
		String safeName = WorkbookUtil.createSafeSheetName(pt.getNazwa());
		Sheet sheet = wb.createSheet(safeName);
		
		CellStyle csAlign = getCellStyleBold(false, true, false);
		CellStyle csBold = getCellStyleBold(true, false, false);
		CellStyle csBold2 = getCellStyleBold(true, false, false);
		csBold2.setVerticalAlignment(VerticalAlignment.CENTER);
		CellStyle csBoldAlign = getCellStyleBold(true, true, false);
		
		CellStyle csWrapFontSmall = getCellStyleBold(false, false, false);
		csWrapFontSmall.setWrapText(true);
		csWrapFontSmall.setVerticalAlignment(VerticalAlignment.TOP);
		Font font8 = wb.createFont();
		font8.setFontHeightInPoints((short)8);
		csWrapFontSmall.setFont(font8);
		
		
		Row row = sheet.createRow(0);
		createAndSetCell(row, 0, pt.getNazwa(), csBoldAlign);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
		
		//statystki
		String [] cechy = Potwory.getCechynazwa();
		int [] statyPotwora = pt.getStatyPotwora();
		
		row = sheet.createRow(1);
		Row row2 = sheet.createRow(2);
		for(int i = 0; i<12; i++) {
			createAndSetCell(row, i, cechy[i], csBoldAlign);
			createAndSetCell(row2, i, statyPotwora[i], csAlign);
		}
		//Cechy
		ArrayList<CechyPotworow> cechyPotworow = pt.getCechy();
		row = sheet.createRow(3);
		createAndSetCell(row, 0, "Cechy (specjalne) potwora/NPCa", csBold);
		sheet.addMergedRegion(new CellRangeAddress(3,3, 0, 11));
		
		for(int i = 0; i<cechyPotworow.size(); i++) {
			row = sheet.createRow(4+i);
			createAndSetCell(row, 0, cechyPotworow.get(i).getNazwa(), csBold2);
			createAndSetCell(row, 3, cechyPotworow.get(i).getOpis(), csWrapFontSmall);
			float s = cechyPotworow.get(i).getOpis().length();
			if(s>100) {
				//logger.info("Rozmiar = " + s);
				s = s/100;
				row.setHeightInPoints((s+1)*9);
			}
			sheet.addMergedRegion(new CellRangeAddress(4+i,4+i, 3, 11));
			sheet.addMergedRegion(new CellRangeAddress(4+i,4+i, 0, 2));
		}
		row = sheet.createRow(row.getRowNum()+1);
		createAndSetCell(row, 0, "Opis:", csBold);
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),row.getRowNum(), 0, 5));
		row = sheet.createRow(row.getRowNum()+1);
		createAndSetCell(row, 0, pt.getOpisStwora(), csWrapFontSmall);
		float s = pt.getOpisStwora().length();
		if(s>120) {
			s = s/120;
			row.setHeightInPoints(s*11);
		}
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),row.getRowNum()+1, 0, 11));
		logger.exiting("ExportDoExcela", "createNPCSheet");
	}

	/**
	 * Tworzy arkusz z wszystkimi danymi bohatera
	 * @param bh - postaæ pohatera do zapisania w arkuszu
	 */
	public void createBohaterSheet(Bohater bh) {
		logger.entering("ExportdoExela", "createBohaterSheet");
		//nazwa arkusza
		String safeName = WorkbookUtil.createSafeSheetName(bh.getImieNazwisko());
		Sheet sheet = wb.createSheet(safeName);
		
		CellStyle justowanie = getCellStyleBold(false, true, false);
		CellStyle justAlign = getCellStyleBold(false, true, false);
		justAlign.setWrapText(true);
		CellStyle pogrubiony = getCellStyleBold(true, false, false);
		CellStyle csBoldAlign = getCellStyleBold(true, true, false);
		CellStyle pogrubionJustTlo = getCellStyleBold(true, true, true);
		Font font8 = wb.createFont();
		font8.setFontHeightInPoints((short)8);
		Font font12 = wb.createFont();
		font12.setFontHeightInPoints((short)12);
		font12.setBold(true);
		
		Row row = sheet.createRow(0);
		CellStyle cellCenter = wb.createCellStyle();
		cellCenter.setVerticalAlignment(VerticalAlignment.CENTER);
		cellCenter.setAlignment(HorizontalAlignment.CENTER);
		
		//imie i nazwisko
		Cell cellB = row.createCell(0);
		CellStyle temp = getCellStyleBold(true, true, false);;
		temp.setFont(font12);
		createAndSetCell(row, 0, bh.getImieNazwisko(), temp);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		
		//rasa
		createAndSetCell(row, 3, "Rasa:", null);
		createAndSetCell(row, 4, bh.getRasaName(), csBoldAlign);
		//klasa
		row.createCell(5).setCellValue("Klasa");
		createAndSetCell(row, 6, bh.getKlasaProfesji(), csBoldAlign);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
		
		//Rz¹d 1 - Profesja / poziom profesji / œciezka profesji
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("Profesja:");
		row.createCell(1).setCellValue(bh.getCurrentProfesjaName());
		row.createCell(2).setCellValue("Poziom:");
		createAndSetCell(row, 3, bh.getCurrentProfPoziom(), justowanie);
		row.createCell(4).setCellValue("Œcie¿ka:");
		row.createCell(5).setCellValue(bh.getProfesjaSciezka());
		
		//Rz¹d -2 - wiek, wygl¹d
		row = sheet.createRow(2);
		row.createCell(0).setCellValue("Wiek:");
		createAndSetCell(row, 1, bh.getWygladWiek(), justowanie);
		row.createCell(2).setCellValue("Wzrost:");
		createAndSetCell(row, 3, bh.getWygladWzrost(), justowanie);
		row.createCell(4).setCellValue("W³osy:");
		createAndSetCell(row, 5, bh.getWygladWlosy(), justowanie);
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 6));
		row.createCell(7).setCellValue("Oczy:");
		createAndSetCell(row, 8, bh.getWygladOczy(), justowanie);
		
		//Rz¹d 3,4,5,6 - Cechy - opis
		row = sheet.createRow(3);
		int[] aktualne = bh.getCechyAktualneInt();
		int [] rozwiniecia = bh.getCechyRozwinieciaInt();
		createAndSetCell(row, 0, "", pogrubionJustTlo);
		for(int i = 1; i< 11; i++) {
			cellB = row.createCell(i);
			cellB.setCellStyle(pogrubionJustTlo);
			if(bh.czyJestCechaRozwojuProfesji(i-1)) {
				cellB.setCellValue(STATYSTYKI[i-1]+"*");
			}else {
				cellB.setCellValue(STATYSTYKI[i-1]);
			}
			
			}
		row = sheet.createRow(4);
		cellB = row.createCell(0);
		cellB.setCellStyle(pogrubiony);
		cellB.setCellValue("Pocz¹tkowa:");
		sheet.autoSizeColumn(0);
		for(int i = 1; i< 11; i++) {
			row.createCell(i).setCellValue(aktualne[i-1]-rozwiniecia[i-1]);
			Cell cell = row.getCell(i);
			cell.setCellStyle(cellCenter);
			}
		row = sheet.createRow(5);
		cellB = row.createCell(0);
		cellB.setCellValue("Rozwiniêcia:");
		cellB.setCellStyle(pogrubiony);
		for(int i = 1; i< 11; i++) {
			row.createCell(i).setCellValue(rozwiniecia[i-1]);
			Cell cell = row.getCell(i);
			cell.setCellStyle(cellCenter);
			}
		row = sheet.createRow(6);
		cellB = row.createCell(0);
		cellB.setCellValue("Aktualne:");
		cellB.setCellStyle(getCellStyleBold(true, false, false));
		for(int i = 1; i< 11; i++) {
			cellB = row.createCell(i);
			cellB.setCellValue(aktualne[i-1]);
			cellB.setCellStyle(getCellStyleBold(true, true, false));
			}
		
		//Rz¹d 7 - Szybkoœæ
		row = sheet.createRow(7);
		row.createCell(0).setCellValue("Szybkoœæ:");
		createAndSetCell(row, 1, bh.getCechySzybkosc(), justowanie);
		row.createCell(2).setCellValue("Chód:");
		createAndSetCell(row, 3, bh.getCechySzybkosc()*2, justowanie);
		row.createCell(4).setCellValue("Bieg:");
		createAndSetCell(row, 5, bh.getCechySzybkosc()*4, justowanie);
		
		//rz¹d 8 - ¿ywotnoœæ
		row = sheet.createRow(8);
		row.createCell(0).setCellValue("¯ywotnoœæ:");
		createAndSetCell(row, 1, bh.getCechyHpString(), csBoldAlign);
		
		//Umiejêtnoœci i talenty - rz¹d 9+
		row = sheet.createRow(9);
		createAndSetCell(row, 0, "Umiejêtnoœci:", pogrubionJustTlo);
		sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 2));
		createAndSetCell(row, 3, "Cecha:", pogrubionJustTlo);
		createAndSetCell(row, 4, "Rozwiniêcia:", pogrubionJustTlo);
		sheet.autoSizeColumn(4);
		createAndSetCell(row, 5, "Suma:", pogrubionJustTlo);
		
				
		for(int i = 0; i<bh.znaneUmiejetnosci.size();i++) {
			row = sheet.createRow(10+i);
			Umiejetnosc um = bh.znaneUmiejetnosci.get(i);
			createAndSetCell(row, 0, um.getName(), pogrubiony);
			createAndSetCell(row, 3, STATYSTYKI[um.getPozycjaCechy()], justowanie);
			createAndSetCell(row, 4, um.getPoziom(), justowanie);
			createAndSetCell(row, 5, um.getPoziom()+aktualne[um.getPozycjaCechy()], csBoldAlign);
			sheet.addMergedRegion(new CellRangeAddress(10+i, 10+i, 0, 2));
		}
		int rowCount = row.getRowNum();
		row = sheet.createRow(rowCount+1);
		createAndSetCell(row, 0, "Talenty:", pogrubionJustTlo);
		rowCount = row.getRowNum();
		sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 10));
		rowCount +=1;
		//zawijanie w komórkach
		
		CellStyle cs = wb.createCellStyle();
		cs.setWrapText(true);
		cs.setFont(font8);
		cs.setAlignment(HorizontalAlignment.JUSTIFY);
		cs.setVerticalAlignment(VerticalAlignment.TOP);
		
		for(int i = 0; i<bh.znaneTalenty.size();i++) {
			row = sheet.createRow(rowCount+i);
			Talent tl = bh.znaneTalenty.get(i);
			createAndSetCell(row, 0, tl.getName(), csBoldAlign);
			createAndSetCell(row, 3, tl.getPoziomValue(), justowanie);
			createAndSetCell(row, 4, tl.getTest(), justAlign);
			Cell cell = row.createCell(5);
			int x = tl.getOpisString().length();
			if(x>50) {
				x = x/50;
				row.setHeightInPoints((float)(11*x));
			}
			cell.setCellValue(tl.getOpisString());

			sheet.addMergedRegion(new CellRangeAddress(rowCount+i, rowCount+i, 0, 2));
			sheet.addMergedRegion(new CellRangeAddress(rowCount+i, rowCount+i, 5, 10));
			cell.setCellStyle(cs);
		}

		logger.exiting("ExportdoExela", "createBohaterSheet");
	}
	
	public void saveWorkBook() {
		logger.entering("ExportdoExela", "saveWorkBook");
		//TODO - zapisanie w wybranym miejscu!!!
		String urlSavaPdf = null;
		try {
			JFileChooser dialogFolder = new JFileChooser();
			dialogFolder.setDialogTitle("Wybierz lokacjê gdzie ma byæ zapisany plik oraz wpisz jego nazwê, bez podawania rozszerzenia pliku!!");
			//dialogFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = dialogFolder.showSaveDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				urlSavaPdf = dialogFolder.getSelectedFile().getAbsolutePath();
			}
			FileOutputStream fileOut;
			if(urlSavaPdf != null)
				fileOut = new FileOutputStream(urlSavaPdf+".xls");
			else {
				fileOut = new FileOutputStream("C:\\workbook.xls");
			}
			
			wb.write(fileOut);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Problem podczas zapisywania excela", e);
		}
		
		logger.exiting("ExportdoExela", "saveWorkBook");
	}
	
	/**
	 * @param bold - czy ma byæ pogrubiona czcionka
	 * @param align - czy ma byæ wyœrodkowana
	 * @param fill - czy ma byæ t³o
	 * @return - zwraca styl komórki
	 */
	private CellStyle getCellStyleBold(boolean bold, boolean align, boolean fill) {
		CellStyle styleBold = wb.createCellStyle();
		Font fontBold = wb.createFont();
		if(bold) {
			fontBold.setBold(true);
			styleBold.setFont(fontBold);
		}
		if(align) {
			styleBold.setVerticalAlignment(VerticalAlignment.CENTER);
			styleBold.setAlignment(HorizontalAlignment.CENTER);
		}
		if(fill) {
			styleBold.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			styleBold.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		return styleBold;
	}
	/**
	 * 
	 * @param r - rz¹d
	 * @param n - która komórka ma byæ utworzona
	 * @param s - jakie dane maj¹ byæ zapisane w komórce (String)
	 * @param cs -  styl komórki
	 */
	private void createAndSetCell(Row r, int n, String s,CellStyle cs) {
		Cell cell = r.createCell(n);
		cell.setCellValue(s);
		if(cs != null)
			cell.setCellStyle(cs);
	}
	
	/**
	 * 
	 * @param r - rz¹d
	 * @param n - która komórka ma byæ utworzona
	 * @param i - jakie dane maj¹ byæ zapisane w komórce (int)
	 * @param cs -  styl komórki
	 */
	private void createAndSetCell(Row r, int n, int i,CellStyle cs) {
		Cell cell = r.createCell(n);
		cell.setCellValue(i);
		if(cs != null)
			cell.setCellStyle(cs);
	}

}
