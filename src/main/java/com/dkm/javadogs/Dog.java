// 2

package com.dkm.javadogs;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@Entity
public class Dog
{
    private @Id @GeneratedValue Long Id;
    private String breed;
    private int avgWeight;
    private boolean apt;

    public Dog()
    {
    }

    public Dog(String breed, int avgWeight, boolean apt)
    {
        this.breed = breed;
        this.avgWeight = avgWeight;
        this.apt = apt;
    }

}
