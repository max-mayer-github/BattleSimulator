package backend.entities;

import backend.entities.helper.BattleDataHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BattleData {
    private final BattleDataHelper helper;
    public ArrayList<Unit> units;
    public float winPercentage = 0;
    public boolean general = false;
    public Integer roundCount = 0;
    public Integer paragonLosses = 0;
    public Integer orcLosses = 0;
    public Integer totalHpParagon = 0;
    public Integer totalHpOrc = 0;

    public BattleData() {
        this.units = new ArrayList<>();
        this.helper = new BattleDataHelper();
        importUnits();
        for (Unit unit : getUnits()) {
            unit.setExpectedAttack((int) ((1 + unit.doubleDamageChance) * unit.attack));
        }
    }

    public void importUnits() {
        List<List<String>> units = this.helper.getData();

        for (List<String> unitData : units) {
            Unit newUnit = new Unit(unitData);
            newUnit.setBattleData(this);
            this.units.add(newUnit);
        }
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public float getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(float winPercentage) {
        this.winPercentage = winPercentage;
    }

    public boolean isGeneral() {
        return general;
    }

    public void setGeneral(boolean general) {
        this.general = general;
    }

    public Integer getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(Integer roundCount) {
        this.roundCount = roundCount;
    }

    public Integer getParagonLosses() {
        return paragonLosses;
    }

    public void setParagonLosses(Integer paragonLosses) {
        this.paragonLosses = paragonLosses;
    }

    public Integer getOrcLosses() {
        return orcLosses;
    }

    public void setOrcLosses(Integer orcLosses) {
        this.orcLosses = orcLosses;
    }

    public Unit getUnitById(Integer id) {
        for (Unit unit : units) {
            if (Objects.equals(id, unit.getId())) {
                return unit;
            }
        }
        return null;
    }

    public Integer getTotalHpParagon() {
        return totalHpParagon;
    }

    public Integer generateTotalHpParagon() {
        for (Unit unit : units) {
            if (Objects.equals(unit.getFaction(), "Paragons")) {
                totalHpParagon = totalHpParagon + unit.getSingleHealth() * unit.getCount();
            }
        }
        return totalHpParagon;
    }

    public Integer getTotalHpOrc() {
        return totalHpOrc;
    }

    public Integer generateTotalHpOrc() {
        for (Unit unit : units) {
            if (Objects.equals(unit.getFaction(), "Orcs")) {
                totalHpOrc = totalHpOrc + unit.getSingleHealth() * unit.getCount();
            }
        }
        return totalHpOrc;
    }

    public ArrayList<Unit> getOrcs() {
        ArrayList<Unit> orcs = new ArrayList<>();
        for (Unit unit : units) {
            if (Objects.equals(unit.getFaction(), "Orcs")) {
                orcs.add(unit);
            }
        }
        return orcs;
    }

    public ArrayList<Unit> getParagon() {
        ArrayList<Unit> paragon = new ArrayList<>();
        for (Unit unit : units) {
            if (Objects.equals(unit.getFaction(), "Paragons")) {
                paragon.add(unit);
            }
        }
        return paragon;
    }
}
