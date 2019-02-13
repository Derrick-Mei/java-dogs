// 8

package com.dkm.javadogs;

public class DogAlreadyExistsException extends RuntimeException
{
    public DogAlreadyExistsException(String breee)
    {
        super("Dog with breed ***" + breee.toUpperCase() + "*** already exists");
    }

}

//            dogrepos.findAll().forEach( o -> {
//    if (o.getBreed().equalsIgnoreCase(newDog.getBreed()))
//    {
//        throw new DogAlreadyExistsException(newDog.getBreed());
//    }
//});