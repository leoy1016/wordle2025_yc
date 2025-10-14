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

                count++;
                if (count < 2309) {
                    String rowAnswer = row[2];
                    validAnswers.add(rowAnswer);
                }
            }
    }
    }



    public void RUNDAGAME(){
        boolean win=false;
        chooseAnswer();
        System.out.println("Answer: "+ answer);
        //while(!win){
        for(int i=0;i<3;i++){
            String guess=solve();
            if(guess.equals(answer)){
                win=true;
            }
            else{ //
                int[] f= compareStrings(guess);
                removeGuesses(guess, f);
            }
        }



    }



    public void printArrayLists(){
        for(String s: validGuesses){
            System.out.println(s);
        }

    }

    //chooses a random guess for the first guess
    public String solve(){
        int random = (int)(Math.random() * validGuesses.size());
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
            if(validate[i]==red){
                //code that removes all answers containing the letter
                for (int j = 0; j < validGuesses.size(); j++) {
                    if (validGuesses.get(j).contains(guess.substring(i, i+1))){
                        validGuesses.remove(i);
                        j+=1;
                    }
                }
            }
            //run through each letter, categorize them as the conditional statement
            else if (validate[i]==yellow && (guess.substring(i, i+1).equals(guess))){
                //yellow is letter in the wrong position. remove all letters that have the
                //wrong letter in the position but is included in the word
                for(int j=0;j<validGuesses.size();j++){
                    if(validGuesses.get(j).contains(guess.substring(i, i+1)) &&
                            validGuesses.get(j).substring(i, i+1).equals(guess.substring(i, i+1))){//checks if that letter is in the position i
                        validGuesses.remove(j);
                    }
                }
            }
            //if it is a match
            //removes any guesses that do NOT have the specific letter
            //in that specific position
            else if(validate[i]==green){
                for (int j = 0; j < validGuesses.size(); j++) {
                    if(!validGuesses.get(j).substring(i, i+1).equals(guess.substring(i, i+1))){
                        validGuesses.remove(j);
                    }
                }
            }
        }
        System.out.print(validGuesses.size());
    }
}