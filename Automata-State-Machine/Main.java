import java.nio.Buffer;
import java.util.*;
import java.io.*;

/*
    Name: Tam Nguyen
    Class: CS 317
    School: WSU-V
    Assignment info: Given a txt file containing a postfix expression, convert the expression(s) to an NFA
    Additional info:    NFA is presented in a table with the same format as the instructions.
                        Coded with Visual Studio Code so that's why there may be extra class file...
*/

class NFA {
	int		initNode;
	int		finNode;
	LinkedList<State> stateList;
    
	NFA(int start, int finalState){	//constructor
		initNode = start;
		finNode = finalState;
		stateList = new LinkedList<State>();
    }
    
    public LinkedList <State> getStatelist()
    { return stateList; } //dont think we're gonna access this but we'll see


    public void printNFA() {//iusing the same format table as shown on the instruction
        System.out.println("");
		for (int j = stateList.size(), i = 0; i < j; i++){
			State temp = stateList.get(i);
			for (int jj = temp.listOfTransitions.size(), ii = 0; ii < jj; ii++){
				delta tempT = temp.listOfTransitions.get(ii);
				System.out.println("(q"+ tempT.current + ", " + tempT.symbol + ") -> q" + tempT.pointsTo);
			}
			
		}
		System.out.println("\nStarting state: q" + this.initNode);
		System.out.println("Final state: q" + this.finNode);
	}
}
//----------------------------------------New Class -------------------------------------//
class State {
	int stateID;
	Boolean isBegin, isEnd;
	LinkedList<delta> listOfTransitions;
	
	State ( int stateNumber, Boolean isStart, Boolean isFinal){	//constructor for a node
		stateID = stateNumber;
		isBegin = isStart;
		isEnd = isFinal;
		listOfTransitions = new LinkedList<delta>(); //init new LL
	}
}
//----------------------------------------New Class -------------------------------------//
class delta { //the transition, called it delta because that's the symbol of the tuple
	int current, pointsTo;
	char symbol;
	
	delta(int fromState, char acceptSym, int toState){//constructor transitions
		current = fromState;
		symbol = acceptSym; //the acceptance symbol
		pointsTo = toState;
	}
}
//----------------------------------------New Class -------------------------------------//
public class Main {
    static String inFileName = "input.txt";
    static Stack<NFA> nfaStack = new Stack();
    static int stateNum = 1;
    public static void main(String[] args) {
        nfaStack.clear();//make sure we have a clean stack

        File inputFile = new File(inFileName); // file will only contain expression in post fix
        Scanner sc = null;
        int opt = 0; //0 = file is there, 1 = given argument, 2 = no file and no arg

        if (args.length > 0)
            opt = 1;
        else
        {
            try {
                sc = new Scanner (inputFile); //try to read the file
            } catch (Exception e) { opt = 2; } //flag
        }
        switch (opt)
        {
            case 0: //file exist
                
                System.out.println("Arguments isn't given; using " + inFileName);
                readTxtFile(sc); //read the text file
                break;
            case 1: //argument exist
                if (args.length > 1)
                {
                    System.out.println("Detected multiple arguments, using first one...");
                    System.out.println("NOTE: please give only one POSTFIX expression. Ex: java <program> <Valid PostFix Express>");
                }
                readArg(args[0]);
            case 2: //neither exist
                if (opt == 2) 
                    System.out.println("No arguments nor " + inFileName + " detected, please give enter a VALID POSTFIX expression:");
                sc = new Scanner(System.in);
                readKeyboard(sc);
                break;
            default:
                return;
        }
        return;
    }

    static void readTxtFile (Scanner sc)
    {
        stateNum = 1;
        nfaStack.clear(); //making sure we start off with a fresh nfa
        while (sc.hasNextLine())
        {
            String str = sc.nextLine();
            System.out.println("Expression: " + str);
            readPostFix(str);
            NFA tmp = nfaStack.pop();
            tmp.printNFA();
            nfaStack.clear();
            stateNum = 1;
            System.out.println("\n\t-------------\t\n");
        }
        return;
    }

    static void readArg(String arg)
    {
        stateNum = 1;
        nfaStack.clear(); //making sure we start off with a fresh nfa
        System.out.println("Expression: " + arg);
        readPostFix(arg);
        NFA tmp = nfaStack.pop();
        tmp.printNFA();
        nfaStack.clear();
        stateNum = 1;
        System.out.println("\nEnter another expression.\n" +
                            "Type Q or q! to quit.");
    }

    static void readKeyboard(Scanner sc)
    {
        stateNum = 1;
        nfaStack.clear(); //making sure we start off with a fresh nfa
        while (sc.hasNextLine())
        {
            String str = sc.nextLine();
            if (str.equals("Q") || str.equals("q!"))
                break;
            System.out.println("\nExpression: " + str);
            readPostFix(str);
            NFA tmp = nfaStack.pop();
            tmp.printNFA();
            stateNum = 1;
            nfaStack.clear();
        }
    }
  
    static void readPostFix(String postfix)
    {
        if (postfix == null || postfix.equals(""))
            return;
        for(int i = 0; i < postfix.length(); i++)
        {
            char c = postfix.charAt(i);	//parse the input string
            
            if (c == '*'){	//repeating node
                NFA starNFA = nfaStack.pop();
                starNFA = epsNFA(starNFA, stateNum);
                nfaStack.push(starNFA);
                stateNum++;
                
            }
            else if (c == '|'){		//union, splitting
                NFA secondOne = nfaStack.pop();
                NFA firstOne = nfaStack.pop();
                NFA union = union(firstOne, secondOne, stateNum);
                nfaStack.push(union);
                stateNum = stateNum + 2;
                
            }
            else if ( c == '&'){	//concating
                NFA secondOne = nfaStack.pop();
                NFA firstOne = nfaStack.pop();
                NFA union = concat(firstOne, secondOne);
                nfaStack.push(union);
                
            }
            else{		//it's a letter, so make a state that accepts it
                NFA singleLetter = oneCharNFA(c, stateNum);
                nfaStack.push(singleLetter);
                stateNum += 2;
            }
        }
    }

    public static NFA oneCharNFA(char character, int statenum){	//return a one character NFA
		NFA tmpNFA = new NFA(statenum, 1+statenum); //since each character contain 2 stage, make two state
		tmpNFA.getStatelist().add(new State(statenum,true,false)); //now we're making the two nodes for the NFA
		tmpNFA.getStatelist().add(new State(1+statenum,false,true));
		tmpNFA.getStatelist().getFirst().listOfTransitions.add(new delta(statenum,character,1+statenum));
		return tmpNFA;
		
	}
	
	public static NFA concat(NFA nfa1, NFA nfa2){	//return the concat of two nfa
		
		nfa1.stateList.getLast().listOfTransitions.add(new delta(nfa1.finNode, 'E', nfa2.initNode)); //add a new transition from final node of nfa1 to first node of nfa2
		nfa1.stateList.getLast().isEnd = false; //change the flag of original final node from nfa1
		NFA temp = new NFA (nfa1.initNode, nfa2.finNode); //make the new nfa
		temp.stateList.addAll(nfa1.stateList); //that connect the two nfa
		temp.stateList.addAll(nfa2.stateList);
		return temp;
	}

	public static NFA union(NFA nfa1, NFA nfa2, int sNum) {	//a new nfa that's a union of the old two
        NFA temp = new NFA(sNum, sNum+1);; //the returning new nfa
        //mod the new nfa
		temp.stateList.add(new State(sNum,true,false)); //make a new node for nfa
		temp.stateList.getFirst().listOfTransitions.add(new delta(sNum, 'E',nfa1.initNode));//add a epsil trans from first node to the nfa1
        temp.stateList.getFirst().listOfTransitions.add(new delta(sNum, 'E',nfa2.initNode));//same but do it to nfa2
        //mod the last two popped
		nfa1.stateList.getLast().listOfTransitions.add(new delta(nfa1.finNode, 'E', temp.finNode));//connect the nfa1 and 2 to the final node using episol trans
		nfa2.stateList.getLast().listOfTransitions.add(new delta(nfa2.finNode, 'E', temp.finNode));
        //update new nfa
        temp.stateList.addAll(nfa1.stateList);//merge the statelist into the new nfa statelist
		temp.stateList.addAll(nfa2.stateList);//same with nfa2
		temp.stateList.add(new State(sNum+1,false,true));//add a new final state for the union.
        //return to put back to stack              
		return temp;
	}

	public static NFA epsNFA(NFA starNFA, int number) {	//returns r1*
        NFA temp = new NFA ( number, number);//make a new nfa
        //add new transition to the last transition of the lastpop nfa
		starNFA.stateList.getLast().listOfTransitions.add(new delta(starNFA.finNode, 'E', temp.finNode));//add a epsilon trans from the last node of lastpop to last node of the tmp 
        temp.stateList.addAll(starNFA.stateList);//merge the transition to the new nfa
		temp.stateList.add(new State(number,true,true));//add node that is final and starting
		temp.stateList.getLast().listOfTransitions.add(new delta(number, 'E', starNFA.initNode));//update that last node with a ep trans to initial node of the last pop
        //return to be push back to stack
		return temp;
	}
    
}