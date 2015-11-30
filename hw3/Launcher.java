public class Launcher {
  public static void main(String[] args) {
    Animal a0 = new Animal();   // Line 1
    Fox f0 = new Fox();         // Line 2
    Animal a1 = f0;
    //Fox f1 = a0;// This errors, parent cannot be converted to child
    a1.testSpeak();
    a0.speak();                 // Line 3
    f0.speak();                 // Line 4
    ((Animal) f0).speak();      // Line 5
    System.out.println("Here");
    Animal a2 = new Fox();
    a2.speak();
    ((Animal) a2).speak();
    ((Animal) a2).shout();

     System.out.println("Q3");
     Animal a3 = new Animal();
     System.out.println(a2.name);
     Animal a4 = new Fox("SuperFox");
     System.out.println(a4.name);
     System.out.println(((Animal) a4).name);
     a4.speak();
    //Fox f1 = new Animal();// Parent cannot be converted to child
    //((Fox) a0).speak(); //This errors, connot cast parent object to child class
  }
}
