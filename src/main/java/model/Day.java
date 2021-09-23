package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import utils.CalendarDay;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Class that represents single day with list of meals/ products eaten.
 * */
@Getter
@NoArgsConstructor
@Entity
public class Day {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Transient
    private CalendarDay calendarDay;
    @Column(unique = true)
    private String date;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name="grams")
    @CollectionTable(name="day_register", joinColumns = {@JoinColumn(name="day_id")})
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
    private final Map<Edible, Double> edibles = new HashMap<>();

    public Day(CalendarDay calendarDay) {
        this(calendarDay.toString());
        this.calendarDay = calendarDay;
    }

    public Day(String date) {
        this.date = date;
    }

    public void addEdible(Edible edible, double grams) {
        edibles.put(edible, grams);
    }

    public void removeEdible(Edible edible) {
        edibles.remove(edible);
    }

    public void removeEdible(int id) {
        for(Map.Entry<Edible, Double> entry : edibles.entrySet()) {
            if(entry.getKey().getId() == id) {
                removeEdible(entry.getKey());
                break;
            }
        }
    }

    // not recommended since there can be more than one edibles with same name
    public void removeEdible(String name) {
        for(Map.Entry<Edible, Double> entry : edibles.entrySet()) {
            if(entry.getKey().getName().equals(name)) {
                removeEdible(entry.getKey());
                break;
            }
        }
    }
}
