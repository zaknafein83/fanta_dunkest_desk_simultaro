package it.sisca.dunkest.dunkestsimulator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class ReadListoneDunkest {

    public static List<String> getListaRuoli(List<Giocatore> listaGiocatori) {
        return listaGiocatori.stream().map(Giocatore::ruolo).distinct().collect(Collectors.toList());

    }

    public static List<String> getListaSquadre(List<Giocatore> listaGiocatori) {
        return listaGiocatori.stream().map(Giocatore::squadra).distinct().collect(Collectors.toList());

    }

    public static List<Giocatore> getListaGiocatori(String path) {
        List<Giocatore> lista = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(new File(path));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            boolean skipFirstRow = true;
            while (rowIterator.hasNext()) {
                if (skipFirstRow) {
                    skipFirstRow = false;
                    rowIterator.next();
                }
                Row row = rowIterator.next();
                lista.add(Giocatore.builder()
                        .nome(row.getCell(0).getStringCellValue())
                        .squadra(row.getCell(1).getStringCellValue())
                        .ruolo(row.getCell(2).getStringCellValue())
                        .crediti(row.getCell(3).getNumericCellValue())
                        .build());
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


}
