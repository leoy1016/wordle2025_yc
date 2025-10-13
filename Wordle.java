import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Wordle {
    private static Scanner s = new Scanner(System.in);
    public ArrayList<String> validGuesses=new ArrayList<>();
    public ArrayList<String> validAnswers=new ArrayList<>();

    public String answer;

    public Wordle(String input) {
        ArrayList<String[]> rawData = CSVImporter.importData(input);
        for (String[] row : rawData) {
            String rowGuess= row[1];
            validGuesses.add(rowGuess);


            //JIAMU DAI FIX NEEDED: row[2] runs out of range after about index 2309, need to not include index 0 values
            if (Integer.parseInt(row[0])<2309){
                String rowAnswer= row[2];
                validAnswers.add(rowAnswer);}




    }}


    public void printArrayLists(){
        for(String s: validGuesses){
            System.out.println(s);
        }

    }

    //compares guess to answer
    public String solve(){
        int random = (int)(Math.random() * validGuesses.size());
        //chooses a random String from validGuesses for the first guess
        String guess =  validGuesses.get(random);

        return guess;
    }

    //chooses a random answer in valid answers
    public String chooseAnswer(){

        int random = (int)(Math.random() * validAnswers.size());
        answer = validAnswers.get(random);
        System.out.println(answer);
        return answer;
    }

    // [0,1,2,1,0] 0- match, 1- wrong location, 2-no

    public int[] compareStrings(String guess, String answer) {
        int[] validate = new int[guess.length()];
        for (int i = 0; i < guess.length(); i++) {
            for (int j = 0; j < answer.length(); j++) {
                //check letter at each index
                if (guess.charAt(i) != answer.charAt(i) && answer.contains(guess.charAt(i) + "")) {
                    validate[i] = 1;
                }
                else if (guess.charAt(i) != answer.charAt(i) && !(answer.contains(guess.charAt(i) + ""))) {
                    validate[i] = 2;
                }
                validate[i] = 0;
            }
        }
        return validate;
    }

    public void removeGuesses(String guess, int[] validate){
        for (int i = 0; i < validate.length; i++) {
            if(validate[i]==2){
                //code that removes all answers containing
                for (int j = 0; j < validGuesses.size(); j++) {
                    if (validGuesses.get(j).contains(guess.charAt(i))){
                        validGuesses.remove(i);
                        j+=1;
                    }
                }
            }
        }

    }
}