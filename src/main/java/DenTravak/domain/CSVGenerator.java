package DenTravak.domain;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

import javax.servlet.http.HttpServletResponse;

public class CSVGenerator {
    private ArrayList<Order> orders;
    private HttpServletResponse response;
    public CSVGenerator(ArrayList<Order> orders, HttpServletResponse response){
            this.orders = orders;
            this.response = response;
    }

    public void GenerateCsv(){
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"userDirectory.csv\"");
        try
        {
            OutputStream outputStream = response.getOutputStream();
            for(Order o: orders){
                String outputResult = "Sandwish " + o.getName() +" met type brood: " + o.getBreadType() + " ,prijs: " + o.getPrice() + " ,gsm: " +o.getMobilePhoneNumber()+"\n";
                outputStream.write(outputResult.getBytes());
            }


            outputStream.flush();
            outputStream.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }


}
