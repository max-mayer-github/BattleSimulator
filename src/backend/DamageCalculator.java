package backend;

import backend.entities.BattleData;
import backend.entities.Unit;

import java.util.Comparator;
import java.util.HashMap;

public class DamageCalculator {
    BattleData battleData;

    public DamageCalculator(BattleData battleData) {
        this.battleData = battleData;
        battleData.getOrcs().sort(Comparator.comparing(Unit::getFlankingPriority));
    }

    public void recover() {
        units.clear();
        flankingTargets.clear();
        totalHp = 0;

        for (Unit unit : originalUnits) {
            unit.recover();
            units.add(unit);
            flankingTargets.add(unit);
            totalHp += unit.getHp();
        }

        flankingTargets.sort(Comparator.comparing(Unit::getFlankingPriority));
    }

    public void takeDamage(Attack attack) {
        if (totalHp == 0) return;

        int power = attack.power();
        int amount = attack.amount();

        if (critsEnabled && power == 1) amount = (int) (amount * (1 + attack.crit()));
        else if (critsEnabled) power = (int) (power * (1 + attack.crit()));

        boolean flanking = attack.flanking();
        Unit target = getTarget(flanking);

        while (target != null && amount > 0) {
            int frontHP = target.getRemainder();

            // First attack the combatant in the front if it's damaged
            if (frontHP < target.maxHp) {
                // Attack either until frontline combatant is dead, or amount runs out
                if (power * amount <= frontHP) { // not enough attack to get through first guy
                    damage(target, power * amount);
                    break;
                } else { // get through first guy
                    amount -= Math.ceilDiv(frontHP, power);
                    damage(target, frontHP);
                }

                if (target.isDead()) {
                    target = removeUnitAndGetNext(target, flanking);
                    continue;
                }
            }

            // Then attack everybody else.
            // Either attack until back combatant is dead, or amount runs out
            int attacksPerKill = Math.ceilDiv(target.maxHp, power);
            int targetPop = target.getCount();

            if (amount / attacksPerKill < targetPop) { // not enough attack to get through target
                damage(target, (amount / attacksPerKill * target.maxHp) + (amount % attacksPerKill) * power);
                if (target.isDead()) removeUnit(target);
                break;
            } else { // wiped unit
                amount -= attacksPerKill * targetPop;
                damage(target, target.getHp());

                target = removeUnitAndGetNext(target, flanking);
            }
        }
    }

    private void damage(Unit target, int dmg) {
        target.damage(dmg);
        totalHp -= dmg;
    }

    private void removeUnit(Unit unit) {
        units.remove(unit);
        flankingTargets.remove(unit);
    }

    private Unit removeUnitAndGetNext(Unit unit, boolean flanking) {
        units.remove(unit);
        flankingTargets.remove(unit);

        if (units.size() == 0) return null;
        else if (flanking) return flankingTargets.get(0);
        else return units.get(0);
    }

    public Unit getTarget(boolean flanking) {
        if (flanking && flankingTargets.size() > 0)
            return flankingTargets.get(0);
        else if (units.size() > 0)
            return units.get(0);
        else
            return null;
    }

    public Unit getUnit(int i) {
        return units.get(i);
    }

    public int getSize() {
        return units.size();
    }

    public int getTotalHp() {
        return totalHp;
    }

    public int getTotalValue() {
        int totalCost = 0;
        for (Unit unit : units) totalCost += unit.value * unit.getCount();

        return totalCost;
    }

    public int getTotalTiers() {
        int totalTiers = 0;
        for (Unit unit : units) totalTiers += unit.tier * unit.getCount();

        return totalTiers;
    }

    public int getPopulation() {
        int pop = 0;
        for (Unit unit : units) pop += unit.getCount();
        return pop;
    }
}
