import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Wordle {
    private static Scanner s = new Scanner(System.in);
    public ArrayList<String> validGuesses = new ArrayList<>();
    public ArrayList<String> validAnswers = new ArrayList<>();
    public String answer = "";
    public String input_alien;

    public Wordle(String input) {
        input_alien = input;
    }

    public void initialize(String input) {

        ArrayList<String[]> rawData = CSVImporter.importData(input);

        for (String[] row : rawData) {

            if (!row[0].equals("id")) {


                String rowGuess = row[1];
                validGuesses.add(rowGuess);


                if (row.length > 2) {
                    String rowAnswer = row[2];
                    validAnswers.add(rowAnswer);
                    validGuesses.add(rowAnswer);
                }
            }
        }
    }


    public void RUNDAGAME() {
        int rounds=0;
        double count = 0.0;
        while (rounds < 1) {
            initialize(input_alien);
            chooseAnswer();
            System.out.println("Answer: " + answer);
            boolean win = false;
            while (!win) {
                String guess = solve();
                count++;
                if (guess.equals(answer)) {
                    win = true;
                } else { //
                    int[] f = compareStrings(guess);
                    removeGuesses(guess, f);
                }
            }
            rounds++;
        }
        System.out.println("avg for 50: " + count);

    }


    public void printArrayLists() {
        for (String s : validGuesses) {
            System.out.println(s);
        }
        System.out.println(validGuesses.size());
        for(String s: validAnswers){
            System.out.println(s);
        }
        System.out.println(validAnswers.size());

    }

    //chooses a random guess for the first guess
    public String solve() {
        int random = (int) (Math.random() * validGuesses.size());
        System.out.println(" Selected Random Guess : " + random +" / "+ validGuesses.size());
        String guess = validGuesses.get(random);
        System.out.println();
        System.out.println("Guess: " + guess);
        return guess;
    }

    //chooses a random answer in valid answers
    public String chooseAnswer() {

        int random = (int) (Math.random() * validAnswers.size());
        answer = validAnswers.get(random);
        System.out.println(answer);
        return answer;
    }

    // [0,1,2,1,0] 0- match, 1- wrong location, 2-no
    int green = 0;
    int yellow = 1;
    int red = 2;

    public int[] compareStrings(String guess) {
        int[] validate = new int[guess.length()];
        for (int i = 0; i < guess.length(); i++) {
            for (int j = 0; j < answer.length(); j++) {
                //check letter at each index
                if ((guess.charAt(i) != answer.charAt(i)) && (answer.contains(guess.charAt(i) + ""))) {
                    validate[i] = yellow;

                    //establish variable, go through string, and get number of that letter

                } else if (guess.charAt(i) != answer.charAt(i) && !(answer.contains(guess.charAt(i) + ""))) {
                    validate[i] = red;

                } else {
                    validate[i] = green;
                }
            }
        }
        for (int i : validate) {
            System.out.print(i + " ");
        }
        return validate;
    }


    // [0,1,2,1,0] 0- match, 1- wrong location, 2-no
    public void removeGuesses(String guess, int[] validate) {
        for (int i = 0; i < validate.length; i++) {
            char charAtIndex = guess.charAt(i);
            if (validate[i] == red) {
                //code that removes all answers containing the letter
                for (int j = validGuesses.size() - 1; j >= 0; j--) {
                    String pg = validGuesses.get(j);
                    if (pg.contains(charAtIndex + "")) {
                        validGuesses.remove(j);
                        //System.out.println("Removed "+validGuesses.get(j));
                    }
                }
            }
//            run through each letter, categorize them as the conditional statement
            else if (validate[i] == yellow) {
                //yellow is letter in the wrong position. remove all letters that have the
                //wrong letter in the position but is included in the word
                for (int j = validGuesses.size() - 1; j > 0; j--) {
                    if (guess.substring(i, i + 1).equals(validGuesses.get(j).substring(i, i + 1)) || !validGuesses.get(j).contains(guess.substring(i, i + 1))) {//checks if that letter is in the position i
                        validGuesses.remove(j);
                    }
                }
            }
//            removes any guesses that do NOT have the specific letter
//            in that specific position
            else if (validate[i] == green) {
                for (int j = validGuesses.size() - 1; j > 0; j--) {
                    String pg = validGuesses.get(j);
                    if (!pg.substring(i, i + 1).equals(validGuesses.get(j).substring(i, i + 1))) {
                        validGuesses.remove(j);
                    }
                }
            }
        }
        System.out.print(validGuesses.size());
    }
}
