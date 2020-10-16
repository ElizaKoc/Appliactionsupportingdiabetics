package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "product_meal",
        primaryKeys = {"product_id", "meal_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Product.class,
                        parentColumns = "id",
                        childColumns = "product_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Meal.class,
                        parentColumns = "id",
                        childColumns = "meal_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("product_id"), @Index("meal_id")}
)

public class Product_meal {

    @ColumnInfo(name = "product_id")
    public int pId;

    @ColumnInfo(name = "meal_id")
    public int mlId;

    @ColumnInfo(name = "unit_quantity")
    public float unitQuantity;
}
