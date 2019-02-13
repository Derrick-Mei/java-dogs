// 4

package com.dkm.javadogs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long>
{
//    List<Dog> findOneByBreedTest(String name);
//    Dog findByBreedIgnoreCase(String breed);
}
