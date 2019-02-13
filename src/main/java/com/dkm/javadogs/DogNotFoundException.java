// 6

package com.dkm.javadogs;

public class DogNotFoundException extends RuntimeException
{
    public DogNotFoundException(String breee)
    {
        super("Could not find dog with breed: " + breee);
    }

}
