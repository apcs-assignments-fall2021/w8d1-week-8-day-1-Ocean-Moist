import java.util.Scanner;

public class MyMain {

    // Generates a random number between 10 and 20, inclusive
    public static int randomTeen() {
        return (int) (Math.random()*11) + 10;
    }

    // Use your previous method to generate three random numbers between 10 and 20, inclusive.
    // Your program should print out the three numbers, as well as the largest and smallest
    // values of the three.
    // Example of running your code:
    // The three random numbers are 20, 14, and 10
    // The largest number is 20
    // The smallest number is 10
    public static void main(String[] args) {
        // Uncomment this code later!
        System.out.println("Mathey.max tests");
        System.out.println(Mathey.max(1, 2)); // 2
        System.out.println(Mathey.max(2, 1)); // 2
        System.out.println(
            Mathey.max(2.0, 3.0, 4.0, 5.0)
            + "\nMathey.randomInteger test " + Mathey.randomInteger(1,3) + " " + Mathey.randomInteger(3)
            + "\nMathey.pow test " + Mathey.pow(3,4) + " " + Mathey.pow(4, 10)
            + "\nMathey.abs test " + Mathey.abs(-9) + " " + Mathey.abs(-7676786)
            + "\nMathey.round test " + Mathey.round(9.87) + " " + Mathey.round(99.5)
            + "\nMathey.floor test " + Mathey.floor(2.99)  + " " + Mathey.floor(10.13)
            + "\nMathey.ceil test " + Mathey.ceil(2.9) + " " + Mathey.ceil(1.01)
//            + "\npythagoras theorem " + Mathey.sqrt(Mathey.pow(5,2) + Mathey.pow(12,2))
//            Math.pow()
        );
    }

}


