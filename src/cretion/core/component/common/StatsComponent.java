package cretion.core.component.common;

import cretion.core.component.Component;

public class StatsComponent extends Component {
    private int strength;
    private int dexterity;
    private int intelligence;
    private int luck;

    public StatsComponent() {
        strength = 0;
        dexterity = 0;
        intelligence = 0;
        luck = 0;
    }

    public int getStrength() {
        return strength;
    }

    public StatsComponent setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public int getDexterity() {
        return dexterity;
    }

    public StatsComponent setDexterity(int dexterity) {
        this.dexterity = dexterity;
        return this;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public StatsComponent setIntelligence(int intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public int getLuck() {
        return luck;
    }

    public StatsComponent setLuck(int luck) {
        this.luck = luck;
        return this;
    }
}
