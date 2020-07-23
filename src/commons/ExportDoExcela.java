package commons;

import java.io.FileOutputStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.ss.util.WorkbookUtil;

import npcGenerator.Potwory;

import org.apache.poi.ss.usermodel.*;

/**
 * klasa kt�ra eksportuje zapisanych bohater�w jak i NPC do arkusza excel, ka�da posta� jest zapisane do osobnej zak�adki
 * @author Tom
 *
 */
public class ExportDoExcela {
	private static final Logger logger = Logger.getLogger(ExportDoExcela.class.getName()); 
	private static final String [] STATYSTYKI = {"WW","US","S","Wt","I","Zw","Zr","Int","SW","Ogd"};
	
	Workbook wb;
	Bohater bohater;
	Potwory potwor;
	
	private Object [] objDoZapisania;

	
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
				logger.log(Level.SEVERE,"Nie mo�na utworzy� handlera pliku dziennika",e);
			}
		}
		
	}
	


	/**
	 * Tworzy arkusz z wszystkimi danymi bohatera
	 * @param bh - posta� pohatera do zapisania w arkuszu
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
		createAndSetCell(row, 4, bh.getRasaName(), pogrubiony);
		//klasa
		row.createCell(5).setCellValue("Klasa");
		row.createCell(6).setCellValue(bh.getKlasaProfesji());
		
		//Rz�d 1 - Profesja / poziom profesji / �ciezka profesji
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("Profesja:");
		row.createCell(1).setCellValue(bh.getCurrentProfesjaName());
		row.createCell(2).setCellValue("Poziom:");
		row.createCell(3).setCellValue(bh.getCurrentProfPoziom());
		row.createCell(4).setCellValue("�cie�ka:");
		row.createCell(5).setCellValue(bh.getProfesjaSciezka());
		
		//Rz�d -2 - wiek, wygl�d
		row = sheet.createRow(2);
		row.createCell(0).setCellValue("Wiek:");
		row.createCell(1).setCellValue(bh.getWygladWiek());
		row.createCell(2).setCellValue("Wzrost:");
		row.createCell(3).setCellValue(bh.getWygladWzrost());
		row.createCell(4).setCellValue("W�osy:");
		row.createCell(5).setCellValue(bh.getWygladWlosy());
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 6));
		row.createCell(7).setCellValue("Oczy:");
		row.createCell(8).setCellValue(bh.getWygladOczy());
		
		//Rz�d 3,4,5,6 - Cechy - opis
		row = sheet.createRow(3);
		int[] aktualne = bh.getCechyAktualneInt();
		int [] rozwiniecia = bh.getCechyRozwinieciaInt();
		createAndSetCell(row, 0, "", pogrubionJustTlo);
		for(int i = 1; i< 11; i++) {
			cellB = row.createCell(i);
			cellB.setCellStyle(pogrubionJustTlo);
			cellB.setCellValue(STATYSTYKI[i-1]);
			}
		row = sheet.createRow(4);
		cellB = row.createCell(0);
		cellB.setCellStyle(pogrubiony);
		cellB.setCellValue("Pocz�tkowa:");
		sheet.autoSizeColumn(0);
		for(int i = 1; i< 11; i++) {
			row.createCell(i).setCellValue(aktualne[i-1]-rozwiniecia[i-1]);
			Cell cell = row.getCell(i);
			cell.setCellStyle(cellCenter);
			}
		row = sheet.createRow(5);
		cellB = row.createCell(0);
		cellB.setCellValue("Rozwini�cia:");
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
		
		//Rz�d 7 - Szybko��
		row = sheet.createRow(7);
		row.createCell(0).setCellValue("Szybko��:");
		row.createCell(1).setCellValue(bh.getCechySzybkosc());
		row.createCell(2).setCellValue("Ch�d:");
		row.createCell(3).setCellValue(bh.getCechySzybkosc()*2);
		row.createCell(4).setCellValue("Bieg:");
		row.createCell(5).setCellValue(bh.getCechySzybkosc()*4);
		
		//rz�d 8 - �ywotno��
		row = sheet.createRow(8);
		row.createCell(0).setCellValue("�ywotno��:");
		row.createCell(1).setCellValue(bh.getCechyHpString());
		
		//Umiej�tno�ci i talenty - rz�d 9+
		row = sheet.createRow(9);
		createAndSetCell(row, 0, "Umiej�tno�ci:", pogrubionJustTlo);
		sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 2));
		createAndSetCell(row, 3, "Cecha:", pogrubionJustTlo);
		createAndSetCell(row, 4, "Rozwini�cia:", pogrubionJustTlo);
		sheet.autoSizeColumn(4);
		createAndSetCell(row, 5, "Suma:", pogrubionJustTlo);
		
				
		for(int i = 0; i<bh.znaneUmiejetnosci.size();i++) {
			row = sheet.createRow(10+i);
			Umiejetnosc um = bh.znaneUmiejetnosci.get(i);
			createAndSetCell(row, 0, um.getName(), pogrubiony);
			createAndSetCell(row, 3, STATYSTYKI[um.getPozycjaCechy()], justowanie);
			createAndSetCell(row, 4, um.getPoziom(), justowanie);
			createAndSetCell(row, 5, um.getPoziom()+aktualne[um.getPozycjaCechy()], pogrubiony);
			sheet.addMergedRegion(new CellRangeAddress(10+i, 10+i, 0, 2));
		}
		int rowCount = row.getRowNum();
		row = sheet.createRow(rowCount+1);
		createAndSetCell(row, 0, "Talenty:", pogrubionJustTlo);
		rowCount = row.getRowNum();
		sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 10));
		rowCount +=1;
		//zawijanie w kom�rkach
		
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
		try {
			FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Tom\\Desktop\\workbook.xls");
			wb.write(fileOut);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Problem podczas zapisywania excela", e);
		}
		
		logger.exiting("ExportdoExela", "saveWorkBook");
	}
	
	/**
	 * @param bold - czy ma by� pogrubiona czcionka
	 * @param align - czy ma by� wy�rodkowana
	 * @param fill - czy ma by� t�o
	 * @return - zwraca styl kom�rki
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
	 * @param r - rz�d
	 * @param n - kt�ra kom�rka ma by� utworzona
	 * @param s - jakie dane maj� by� zapisane w kom�rce (String)
	 * @param cs -  styl kom�rki
	 */
	private void createAndSetCell(Row r, int n, String s,CellStyle cs) {
		Cell cell = r.createCell(n);
		cell.setCellValue(s);
		if(cs != null)
			cell.setCellStyle(cs);
	}
	
	/**
	 * 
	 * @param r - rz�d
	 * @param n - kt�ra kom�rka ma by� utworzona
	 * @param i - jakie dane maj� by� zapisane w kom�rce (int)
	 * @param cs -  styl kom�rki
	 */
	private void createAndSetCell(Row r, int n, int i,CellStyle cs) {
		Cell cell = r.createCell(n);
		cell.setCellValue(i);
		if(cs != null)
			cell.setCellStyle(cs);
	}
	
	
	private void addBorders(Sheet sh) {
		PropertyTemplate pt = new PropertyTemplate();
		
		
	}

}
