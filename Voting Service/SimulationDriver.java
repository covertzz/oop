/* 
 * Author: Palmer Du
 * Class: CS3560 OOP
 * Date last modified: 9/27/23
 * - This program is designed to run with VotingService.java
 * - This will simulate a poll, creating imaginary students and their votes to use with VotingService.java
 * - Questions and answers are, by default, prompted for from the user, however, I have built some preset question settings 
 *   in case any user does not want to input every time.
 * - to enable the preset question settings, uncomment lines 56-58, and comment lines 35 and 45-52
 * - This program works by using a bias system to influence the frequency of the votes. The students votes are still random, however,
 *   higher or lower biases will influence the results. For example, a multiple choice question with an answer bias of 0.9 means that 
 *   students have a 90% chance to vote for that answer.
 * - It should be noted that the biases for non-multiple choice questions should all add up to 1 (or 100%).
 */
 
import java.util.*; 

public class SimulationDriver {

    //preset question settings 1
    //static String question = "How are you today?";
    //static String[] answers = {"Great", "Good", "Ok", "Bad"};
    //static ArrayList<Double> biases= new ArrayList<>(List.of(0.05, 0.3, 0.5, 0.15));
    //static boolean mc = false;

    //preset questions settings 2
    //static String question = "What did you do this morning?";
    //static String[] answers = {"Brush Teeth", "Eat Breakfast", "Shower", "Do Hair/Makeup", "Excercise"};
    //static ArrayList<Double> biases= new ArrayList<>(List.of(0.95, 0.8, 0.33, 0.6, 0.05));
    //static boolean mc = true;
    
    //commment the line below out if using the above preset question settings
    static ArrayList<Double> biases = new ArrayList<Double>();

    public static void main(String args[]) {
        Random rand = new Random();
        Question q = new Question();
        Scanner sc = new Scanner(System.in);

        //if choosing  to use the preset questions, uncomment the "preset questions" block and comment the "Non-preset questions" block

        //non-preset questions block
        createQuestion(q, sc); 
        createMC(q, sc);
        createAnswers(q, sc);
        createBiases(q, sc);
        showQuestion(q);
        showMC(q);
        showAnswers(q);
        showBiases(q); 
        //end non-preset questions block

        /*preset questions block
        q.setQuestion(question);
        q.setMC(mc);
        for (String a: answers) {q.addAnswer(a);}
        end preset question block */

        //create a random number of students, up to an arbitrary bound
        int numStudents = rand.nextInt(1000);
        
        ArrayList<Student> studentList = simulateVotes(q, rand, numStudents);
        q.showResults(studentList);

        sc.close();
    }

    //prompts user to create a question, multiple choice status, answers, and biases
    private static void createQuestion(Question q, Scanner sc) {
        System.out.print("Please enter a question: ");
        q.setQuestion(sc.nextLine());
    }
    private static void createMC(Question q, Scanner sc) {
        System.out.print("Is this a multiple choice question? (Y/N): ");
        String choice = sc.nextLine();
        if (choice.equals("Y") || choice.equals("y") || choice.equals("Yes") || choice.equals("yes")) {q.setMC(true);}
        else {q.setMC(false);}
    }
    private static void createAnswers(Question q, Scanner sc) {
        System.out.print("\nPlease enter the answers (newline=quit):\n");
        String temp;
        while(true) {
            temp = sc.nextLine();
            if (temp == "") {break;}
            q.addAnswer(temp);
        }
    }
    private static void createBiases(Question q, Scanner sc) {
        System.out.print("\nPlease enter the bias for each answer as a value from 0.0 to 1.0 (non-MCQs should have biases that total to 1.0):\n");
        for (int i = 0; i < q.getAnswers().size(); i++) {
            System.out.print(q.getAnswers().get(i)+ ": ");
            biases.add(sc.nextDouble());
        }
    }

    //displays user's choices for questions, MC status, answers, and biases 
    public static void showQuestion(Question q) {
        System.out.printf("\nQuestion: " + q.getQuestion());
    }
    public static void showMC(Question q) {
        System.out.printf("\nMultiple Choice: " + q.getMC());
    }
    public static void showAnswers(Question q) {
        System.out.printf("\nAnswers: " + q.getAnswers());
    }
    public static void showBiases(Question q) {
        System.out.printf("\nBiases: ");
        for (Double i: biases) {
            System.out.printf("%.2f ", i);
        }
    }

    //create list of students with ID = "st#" & set random votes for the question using fillVote method
    private static ArrayList<Student> simulateVotes(Question q, Random rand, int numStudents) {
        ArrayList<Student> studentList = new ArrayList<Student>();
        for (int i = 0; i < numStudents; i++) {
            studentList.add(new Student());
            studentList.get(i).setID("st"+ String.valueOf(i));
            studentList.get(i).setVote(fillVote(q, rand));
        }
        return studentList;
    }

    //randomly creates votes for the students based on biases and multiple choice status
    private static boolean[] fillVote(Question q, Random rand) {
        boolean[] temp = new boolean[q.getAnswers().size()];

        /* If not a multiple choice question, then generate a number between 0.0-0.99 then use the biases to determine the vote choice.
         * If the random number falls into the range of a bias, then that is the answer choice. 
         * For example given bias = [0.5, 0.3, 0.2]: the range 0.0-0.50 indicates answer choice 1, 0.50-0.80 choice 2, and 0.80-0.99 choice 3. 
         * If the number generated is 0.42, then choice 1 is voted for. 
         */
        if (q.getMC() == false) {
            double val = rand.nextDouble();            
            double sum = 0.0;

            for (int i = 0; i < q.getAnswers().size(); i++) {
                sum+=biases.get(i);
                if (val <= sum) {
                    temp[i] = true;
                    Arrays.fill(temp, i+1, temp.length, false);
                    break;
                }
                else {
                    temp[i] = false;
                }
            }
        }

        /* If the question is multiple choice, then just chose the answer for each given probability[bias].
         * for example given bias [1.9, 0.75, , 0.5, 0.1, 0.0]: choice 1 has a 100% chance to be selected, 
         * choice 2 has a 75% chance, and so on. Choice 5 has a 0% chance of being selected.
         */
        else {
            for (int i = 0; i < q.getAnswers().size(); i++) {
                temp[i] = rand.nextDouble() < biases.get(i);
            }
        }
        return temp;
    }   
}
