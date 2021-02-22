package utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Reader {

    public static List<String []> getListOfArraysFrom(String fileName){
        List<String[]> temp = new ArrayList<>();
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
                temp.add( line.split(";"));
            }
            bufferedReader.close();
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static Set<Race> loadRaces(String fileName){
//        Set<Race> temp = new HashSet<>();
//        InputStream input = Reader.class.getClassLoader()
//                .getResourceAsStream( fileName );
//
//        assert input != null;
//        InputStreamReader reader = new InputStreamReader( input, StandardCharsets.UTF_8);
//
//        try( BufferedReader bufferedReader = new BufferedReader(reader)){
//            String wiersz = null;
//
//            while((wiersz = bufferedReader.readLine()) !=null){
//                if(wiersz.length()==0)
//                    break;
//                String nazwa = wiersz;
//                wiersz = bufferedReader.readLine();
//                String wynik1[] = wiersz.split(",");
//                wiersz = bufferedReader.readLine();
//                String wynik2[] = wiersz.split(",");
//                wiersz = bufferedReader.readLine();
//                String talenty[] = wiersz.split(",");
//                wiersz = bufferedReader.readLine();
//                //zamiana stringów na tablice intów -cechy bazowe
//                int [] tablica = new int[10];
//                for(int i = 0; i<10; i++){
//                    tablica[i] = Integer.parseInt(wynik1[i]);
//                }
//                //zapisanie umiejetnosci jako obiekty
//                ArrayList<Skill> umiej = new ArrayList<Skill>();
//                for(String x:wynik2){
//                    String[] doZapisaniaUm = x.split("/");
//                    Skill tempUm = Skill.builder()
//                            .name( doZapisaniaUm[0] )
//                            .statNumber( Integer.parseInt( doZapisaniaUm[1]) )
//                            .type( doZapisaniaUm[2] )
//                            .level( 0 )
//                            .isProfessional( false ).build();
//                    umiej.add(tempUm);
//                }
//                //konwersja talentow na obiekty
//                ArrayList<Talent> listaZnTalnetow = new ArrayList<Talent>();
//
//                for(String x:talenty){
//                    String[] doZapTalenty = x.split("/");
//                    Talent tempTlnt = Talent.builder()
//                            .name(doZapTalenty[0])
//                            .relatedStat( parseInt(doZapTalenty[1]))
//                            .test( doZapTalenty[2]).build();
//                    //todo dodanie opisu do talentow
//                    tempTlnt.setDescription( dodajOpisDoTalentu( tempTlnt));
//                    listaZnTalnetow.add(tempTlnt);
//                }
//
//
//                Race rs = new Race( nazwa, tablica , umiej, listaZnTalnetow, Integer.parseInt( wiersz));
//                temp.add(rs);
//            }
//            bufferedReader.close();
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }

}
