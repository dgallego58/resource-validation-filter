package co.com.bancolombia;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

@Service
public class FileService implements FileUseCase {


    @Override
    public void process(byte[] file) {

        var map = new HashMap<String, String>();


        try (var bais = new ByteArrayInputStream(file)) {

            var workBook = new HSSFWorkbook(bais);
            var sheets = workBook.getSheet("Informe bla...");

            for (Row sheet : sheets) {
                for (Row cells : sheets) {
                    ///al final del for (PERO DENTRO DE ESTE)
                    map.put("obs", cells.getCell(sheet.getRowNum()).getStringCellValue());

                }
            }

        } catch (IOException e) {

        }

    }
}
