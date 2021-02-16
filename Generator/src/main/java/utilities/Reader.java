package utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Reader {

    public static List<String []> getListOfArraysFrom(String fileName){
        List<String[]> hairAndEyes = new ArrayList<>();
        try {
            InputStream input = Reader.class.getClassLoader()
                    .getResourceAsStream( fileName );
            assert input != null;
            InputStreamReader reader = new InputStreamReader( input, StandardCharsets.UTF_8);

            BufferedReader bufferedReader = new BufferedReader( reader);
            String line;
            while ((line = bufferedReader.readLine())!= null) {
                if( line.equals( "" ) )
                    break;
                hairAndEyes.add( line.split(";"));
            }
            bufferedReader.close();
            return hairAndEyes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
