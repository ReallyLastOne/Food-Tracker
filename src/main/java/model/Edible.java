package model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@DiscriminatorColumn(name="EDIBLE_TYPE")
@Entity
@Table(name = "edibles")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Edible {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    int id;
    @Column(name = "name", unique = false, nullable = false)
    String name;
    @Column(name = "carbs", nullable = false)
    double carbs;
    @Column(name = "proteins", nullable = false)
    double proteins;
    @Column(name = "fats", nullable = false)
    double fats;
    @Setter(value = AccessLevel.NONE)
    @Column(name = "calories")
    double calories;
    @Setter(value = AccessLevel.NONE)
    @Column(name = "total_grams", nullable = false)
    double totalGrams;
    @Column(name = "deprecated")
    boolean deprecated = false;
}
