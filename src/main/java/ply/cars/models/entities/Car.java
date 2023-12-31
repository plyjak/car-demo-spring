package ply.cars.models.entities;


import java.time.LocalDateTime;
import javax.validation.constraints.*;

import io.micrometer.common.lang.Nullable;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString
public class Car{
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nonnull
    @Column(unique=true)
    @Pattern(regexp="^[A-Z0-9]{6}$", message="In this demo car plate number numbers must match regexp pattern \"^[A-Z0-9]{6}$\".")
    private String plates;

    /*
     * The first modern car—a practical, marketable automobile 
     * for everyday use—and the first car put into series production 
     * appeared in 1886, when Carl Benz developed a gasoline-powered 
     * automobile and made several identical copies.
     * 
     * https://en.wikipedia.org/wiki/History_of_the_automobile
     */
    @Min(1886)
    // yearOfProduction
    private int yop;

    @Nonnull
    private String brand;

    @Nonnull
    private String model;

    /* 
     * In this simple implementation deleting data from the DB is modeled
     * as flagging it as deleted, not as a real delete. If this field != null
     * it means that the record is deleted, value marks when it was deleted.
     * A better approach would be to for example move it to another table,
     * so the data wouldn't need to be filtrated as often as with this approach
     * but since it's just a simple demo I decided it's fine for this purpose.
     */

    @Column(name = "deleted_on")
    @Nullable
    private LocalDateTime deletedOn;
}
