package com.talentengine.pocketpixelpets.database.entities;

import static com.talentengine.pocketpixelpets.database.AppDatabase.PETS_TABLE;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = PETS_TABLE)
public class Pets {
    @PrimaryKey(autoGenerate = true)
    private int pet_id;
    private String name;
    private int hunger;
    private int hygiene;
    private int happiness;
    private String pet_type;
    private String pet_color;
    private String pet_personality;
    private String favorite_food;
    private String background;
    public LocalDateTime created_at;

    public Pets(String name, String pet_type, String pet_color, String pet_personality, String favorite_food, String background) {
        this.name = name;
        this.pet_type = pet_type;
        this.pet_color = pet_color;
        this.pet_personality = pet_personality;
        this.favorite_food = favorite_food;
        this.background = background;
        created_at = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pets pets = (Pets) o;
        return pet_id == pets.pet_id && hunger == pets.hunger && hygiene == pets.hygiene && happiness == pets.happiness && Objects.equals(name, pets.name) && Objects.equals(pet_type, pets.pet_type) && Objects.equals(pet_color, pets.pet_color) && Objects.equals(pet_personality, pets.pet_personality) && Objects.equals(favorite_food, pets.favorite_food) && Objects.equals(background, pets.background) && Objects.equals(created_at, pets.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pet_id, name, hunger, hygiene, happiness, pet_type, pet_color, pet_personality, favorite_food, background, created_at);
    }

    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getHygiene() {
        return hygiene;
    }

    public void setHygiene(int hygiene) {
        this.hygiene = hygiene;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public String getPet_type() {
        return pet_type;
    }

    public void setPet_type(String pet_type) {
        this.pet_type = pet_type;
    }

    public String getPet_color() {
        return pet_color;
    }

    public void setPet_color(String pet_color) {
        this.pet_color = pet_color;
    }

    public String getPet_personality() {
        return pet_personality;
    }

    public void setPet_personality(String pet_personality) {
        this.pet_personality = pet_personality;
    }

    public String getFavorite_food() {
        return favorite_food;
    }

    public void setFavorite_food(String favorite_food) {
        this.favorite_food = favorite_food;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
