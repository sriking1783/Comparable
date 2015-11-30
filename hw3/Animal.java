public class Animal {
    String name;
    public Animal() {
        name = "SuperAnimal";
        System.out.println("I am "+name);
    }
    public void speak() {
        System.out.println("I'm an animal");
    }

    public void shout() {
      System.out.println("I AM ANIMAL");
    }

    public static void testSpeak(){
      System.out.println("I am test speak animal");
    }
}
