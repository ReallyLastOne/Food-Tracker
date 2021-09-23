package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Settings")
public class Settings {
    @Id
    private int id;
    @Column(name = "dark_mode", nullable = false)
    private boolean darkMode;
    @Column(name = "daily_calories", nullable = false )
    private int dailyCalories;
    @Column(name = "daily_carbs_percent", nullable = false)
    private int dailyCarbsPercent;
    @Column(name = "daily_proteins_percent", nullable = false)
    private int dailyProteinsPercent;
    @Column(name = "daily_fats_percent", nullable = false)
    private int dailyFatsPercent;

    public Settings(boolean darkMode, int dailyCalories, int dailyCarbsPercent, int dailyProteinsPercent,
                    int dailyFatsPercent) {
        if(dailyCarbsPercent + dailyFatsPercent + dailyProteinsPercent != 100) throw new IllegalArgumentException("Daily " +
                "percent macro distribution must sum up to 100");
        this.dailyCalories = dailyCalories;
        this.dailyCarbsPercent = dailyCarbsPercent;
        this.dailyProteinsPercent = dailyProteinsPercent;
        this.dailyFatsPercent = dailyFatsPercent;
        this.darkMode = darkMode;
    }

    public static Settings getRegularSettings() {
        return new Settings(false, 2500, 45, 35, 20);
    }

    public int[] getPercents() {
        return new int[] {dailyCarbsPercent, dailyProteinsPercent, dailyFatsPercent};
    }
}
