// 5

package com.dkm.javadogs;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class DogResourceAssembler implements ResourceAssembler<Dog, Resource<Dog>>
{
    @Override
    public Resource<Dog> toResource(Dog dog)
    {
        return new Resource<>(dog,
//                linkTo(methodOn(DogController.class).all()).withRel("dogs"),
//                linkTo(methodOn(DogController.class).returnDogsByApt()).withRel("@@@ Dogs by Apt @@@"),
                linkTo(methodOn(DogController.class).allDogs()).withRel("dogs"));
//                linkTo(methodOn(DogController.class).findOneByBreed()).withSelfRel(),
//                linkTo(methodOn(DogController.class).returnDogsByWeight()).withRel("=== Dogs by Weight ==="),
//                linkTo(methodOn(DogController.class).returnDogsByBreed()).withRel("***Dogs by Breed ***"));

    }




}
