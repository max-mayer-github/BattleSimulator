package backend.entities;

import java.lang.reflect.Array;

public class Special {
    public boolean reach;
    public boolean flanking;
    public boolean firstStrike;
    public boolean doubleStrike;
    public boolean lastStrike;
    public boolean trample;

    public Special(String specialString) {
        String[] data = specialString.split(";");
        for (String special:data) {
            switch(special) {
                case "Reach":
                    reach = true;
                case "Flanking":
                    flanking = true;
                case "First Strike":
                    firstStrike = true;
                case "Double Strike":
                    doubleStrike = true;
                case "Last Strike":
                    lastStrike = true;
                case "Trample":
                    trample = true;
            }
        }
    }
}
