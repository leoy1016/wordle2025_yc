public class Solver {
    public static void main(String [] args){
        Wordle w= new Wordle("Word Lists.csv");
        System.out.println(w.chooseAnswer());
        System.out.println(w.solve());
    }
}