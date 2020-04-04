package hanhen;

import java.util.ArrayList;
import java.util.List;

public class HanhenTest {

    public static void main(String[] args) {

        Animal[] animals = new Animal[100];
        animals[0] = new Dog();
        animals[1] = new Cat();
        animals[2] = new Animal();

        for (Animal animal : animals) {
            if (animal != null)
                animal.say();
        }

        List<Animal> animalList = new ArrayList<>();
        List<Dog> dogList = new ArrayList<>();
        dogList.add(new Dog());

        List<Cat> catList = new ArrayList<>();
        catList.add(new Cat());

//        animalList = dogList; // error
        animalList.addAll(dogList);
        animalList.addAll(catList);

        // Listは、Animalであることは保証されている。つまり、Animalを上限としたリスト
        List<? extends Animal> zyougenAnimals = new ArrayList<>();
//        zyougenAnimals.add(new Object());// 一切の追加は許されない。
//        zyougenAnimals.add(new Dog()); // エラー
//        zyougenAnimals.add(zyougenAnimals.get(0)); // エラー。
        zyougenAnimals.forEach(Animal::say);// Animalであることは保証されている。

        // 反変
        // Listは、Animalか、その上位クラス。つまりAnimalを下限としたリスト
        List<? super Animal> kagenAnimals = new ArrayList<>();
        kagenAnimals.add(new Dog());
        kagenAnimals.add(new Cat()); // Animalの
//        kagenAnimals = dogList; // error
        kagenAnimals.addAll(dogList);
        kagenAnimals.addAll(catList);

        zyougenAnimals.forEach(Animal::say);
        System.out.println("-----------------------------------");
        kagenAnimals.stream().map(Animal.class::cast).forEach(Animal::say);
    }
}
