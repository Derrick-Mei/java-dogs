// 7

package com.dkm.javadogs;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class DogController
{
    private final DogRepository dogrepos;
    private final DogResourceAssembler assembler;
//    private ArrayList<Dog> dogList = new ArrayList<>();

    public DogController(DogRepository dogrepos, DogResourceAssembler assembler)
    {
        this.dogrepos = dogrepos;
        this.assembler = assembler;
    }
    // ========================================================================================================

    @GetMapping("/dogs/breeds")
    public Resources<Resource<Dog>> allDogs()
    {
        List<Resource<Dog>> dogs = dogrepos.findAll().stream()
                .map(assembler::toResource)
                .sorted((a, b) -> a.getContent().getBreed().compareToIgnoreCase(b.getContent().getBreed()))
                .collect(Collectors.toList());
        return new Resources<>(dogs, linkTo(methodOn(DogController.class).allDogs()).withSelfRel());
    }

    // ========================================================================================================

    @GetMapping("/dogs/weight")
    public Resources<Resource<Dog>> returnDogsByWeight()
    {
        List<Resource<Dog>> dogsByWeight = dogrepos.findAll().stream()
                .map(assembler::toResource)
                .sorted((a, b) -> (a.getContent().getAvgWeight() - b.getContent().getAvgWeight()))
                .collect(Collectors.toList());
        return new Resources<>(dogsByWeight, linkTo(methodOn(DogController.class).returnDogsByWeight()).withSelfRel());
    }

    // ========================================================================================================

    @GetMapping("/dogs/apartment")
    public Resources<Resource<Dog>> returnDogsByApt()
    {
        List<Resource<Dog>> dogsByApt = dogrepos.findAll().stream()
                .filter(Dog::isApt)
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(dogsByApt, linkTo(methodOn(DogController.class).returnDogsByApt()).withSelfRel());
    }

    // ========================================================================================================

    // WORKS BUT DOESN'T THROW EXCEPTION ERROR
//    @GetMapping("/dogs/breeds/{breed}")
//    public Resources<Resource<Dog>> findOneByBreed(@PathVariable String breed)
//    {
//        List<Resource<Dog>> foundByBreed = dogrepos.findAll().stream()
//                .filter(o -> (o.getBreed().equalsIgnoreCase(breed)))
//                .map(assembler::toResource)
//                .collect(Collectors.toList());
//
//        foundByBreed.get(0).getId();
//
//        return new Resources<>(foundByBreed, linkTo(methodOn(DogController.class).allDogs()).withSelfRel());
//    }

    // ========================================================================================================

    @GetMapping("/dogs/breed/{bree}")
    public Resource<Dog> findBreed(@PathVariable String bree)
    {
        for (Dog o : dogrepos.findAll())
        {
            if (o.getBreed().equalsIgnoreCase(bree))
                return assembler.toResource(o);
        }
        throw new DogNotFoundException(bree);

    }

    // ========================================================================================================

    // Me Try 1
//    @PutMapping("/dogs/{id}")
//    public ResponseEntity<?> replaceDog(@RequestBody Dog newDog, @PathVariable Long id)
//            throws URISyntaxException
//    {
//
//        dogrepos.findAll().forEach(o ->
//        {
//            if (o.getBreed().equalsIgnoreCase(newDog.getBreed()))
//            {
//                throw new DogAlreadyExistsException(newDog.getBreed());
//            }
//        });
//
//        Dog updatedDog = dogrepos.findById(id)
//                .map(dog ->
//                {
//                    dog.setApt(newDog.isApt());
//                    dog.setAvgWeight(newDog.getAvgWeight());
//                    dog.setBreed(newDog.getBreed());
//                    ;
//                    return dogrepos.save(dog);
//                })
//                .orElseGet(() ->
//                {
//                    newDog.setId(id);
//                    return dogrepos.save(newDog);
//                });
//
//        Resource<Dog> resource = assembler.toResource(updatedDog);
//
//        return ResponseEntity
//                .created(new URI(resource.getId().expand().getHref()))
//                .body(resource);
//    }


    // ========================================================================================================

    // Me Try 2
//    @PutMapping("/dogs/{id}")
//    public ResponseEntity<?> replaceDog(@RequestBody Dog newDog, @PathVariable Long id)
//            throws URISyntaxException
//    {
//        Dog updatedDog = dogrepos.findById(id)
//                .map(dog ->
//                {
//                    dog.setApt(newDog.isApt());
//                    dog.setAvgWeight(newDog.getAvgWeight());
//                    dog.setBreed(newDog.getBreed());
//                    return dogrepos.save(dog);
//                })
//                .orElseGet(() ->
//                {
//                    newDog.setId(id);
//                    return dogrepos.save(newDog);
//                });
//
//        Resource<Dog> resource = assembler.toResource(updatedDog);
//
//        return ResponseEntity
//                .created(new URI(resource.getId().expand().getHref()))
//                .body(resource);
//    }

    // Harrison ======================================
//    @PutMapping("/dogs/{id}")
//    public ResponseEntity<?> replaceDog(@RequestBody Dog newDog, @PathVariable Long id) throws URISyntaxException {
//        Dog updateDog = dogrepos.findById(id)
//                .map(dog -> {
//                    dog.setBreed(newDog.getBreed());
//                    dog.setAvgWeight(newDog.getAvgWeight());
//                    dog.setApt(newDog.isApt());
//                    return dogrepos.save(dog);
//                })
//                .orElseGet(() ->{
//                    newDog.setId(id);
//                    return dogrepos.save(newDog);
//                });
//
//        Resource<Dog> resource = assembler.toResource(updateDog);
//        return ResponseEntity
//                .created(new URI(resource.getId().expand().getHref()))
//                .body(resource);
//
//    }

    // Holly ===================================================
    @PutMapping("dogs/{id}")
    public Resource<Dog> updatedDog(@RequestBody Dog dog, @PathVariable Long id)
    {
        dogrepos.findAll().forEach(o ->
        {
            if (o.getBreed().equalsIgnoreCase(dog.getBreed()))
            {
                throw new DogAlreadyExistsException(dog.getBreed());
            }
        });

        Dog updated = dogrepos.findById(id)
                .map(d ->
                {
                    d.setBreed(dog.getBreed());
                    d.setAvgWeight(dog.getAvgWeight());
                    d.setApt(dog.isApt());
                    return dogrepos.save(d);
                })
                .orElseGet(() ->{
                    dog.setId(id);
                    return dogrepos.save(dog);
                });

        return assembler.toResource(updated);
    }

    // ====================================================================

    @DeleteMapping("/dogs/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id)
    {
        dogrepos.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
