// 6

package com.dkm.javadogs;

public class DogNotFoundException extends RuntimeException
{
    public DogNotFoundException(Long id)
    {
        super("Could not find employee with Id: " + id);
    }

}
