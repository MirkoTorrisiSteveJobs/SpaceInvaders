import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class CSV {
    public static ArrayList<String[]> read(String filePath){
        ArrayList<String[]> data = new ArrayList<String[]>();
        String dataRow;

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while((dataRow = br.readLine()) != null){
                String[] dataRecords = dataRow.split(",");
                data.add(dataRecords);
            }
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static void writeAppend(String file, String content){
        try {
            Files.write(Paths.get(file), content.getBytes(), StandardOpenOption.APPEND);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void write(String file, String content){
        try {
            Files.write(Paths.get(file), content.getBytes());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}


