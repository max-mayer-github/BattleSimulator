package backend.entities.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BattleDataHelper {
    private static final String COMMA_DELIMITER = ",";
    public static final int MILITIA = 1;
    public static final int ARCHER = 2;
    public static final int FOOTSOLDIER = 3;
    public static final int CAVALRY = 4;
    public static final int LONGBOW_ARCHER = 5;
    public static final int KNIGHT = 6;
    public static final int CROSSBOWMAN = 7;
    public static final int CUIRASSIER = 8;
    public static final int CANNONEER = 9;
    public static final int ORCLING = 10;
    public static final int ORC_HUNTER = 11;
    public static final int ORC_RAIDER = 12;
    public static final int WARG_RIDER = 13;
    public static final int ELITE_ORC_HUNTER = 14;
    public static final int ORC_VETERAN = 15;
    public static final int ELITE_ORC_SNIPER = 16;
    public static final int ORC_VANGUARD = 17;
    public static final int ORC_DEMOLISHER = 18;
    public static final int BULA = 19;
    public static final int AGUK = 20;
    public static final int MAZOGA = 21;
    public static final int DURGASH = 22;
    public List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public List<List<String>> getData() {
        List<List<String>> rawData = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/backend/data/units.csv"));) {
            while (scanner.hasNextLine()) {
                rawData.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return rawData;
    }
}
