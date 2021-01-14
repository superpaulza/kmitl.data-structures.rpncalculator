import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        boolean debug = false;
        System.out.print("Enable Debug? (Y/N) = "); String ans = (new Scanner(System.in)).nextLine();
        if (ans.equals("Y") || ans.equals("y")) { debug = true;
        System.out.println("Debug Enabled!"); }
        else System.out.println("Debug Disabled!");
        MyQueue q = new MyQueue();
        MyStack s = new MyStack();
        System.out.print("Input the infix expression (with whitespace): ");
        int count = 1;
        //Test cases
        //String infix = "0 / 0 - 0 + 0";
        //String infix = "5 + 3 * 2 / ( 7 - 1 )";
        //String infix = "5 + 5 * 5 + 5";
        //String infix = "7 + 7 / 7 + 7 * 7 - 7";
        //String infix = "3 + 8 / (5 - 1)";
        //String infix = "( 3 * 2 / 4 + 2 - 3 + 1 ) * ( 3 * ( 2 / 4 - ( 3 + ( 1 * 2.5 ) ) ) )";
        //String infix = "( 3.95 * 2.45 / 4.35 + 2.98 - 3.5432 + ( 1.2295 * 5.4523 / 4.4654 - 3.75756 ) ) * ( 3.45465 * ( 2.4564 / 4.546 - ( 3.5454 + ( 1.54564 * 2.5 ) ) ) )";
        //String infix = "2 + 2 / 2 * 3 - 1 * 2 + 3 + ( 3 / 2 * 5 + 7 - 1 * ( 3 * ( 2 / 9 + 4 - ( 8 * 5 + ( 4 * 3 - ( 2 + 1 ) ) ) ) ) )";
        //String infix = "2.5 + 8.96 / 2.35 * 3.45 - 1.11 * 2.3985 + 3.4258710 + ( 3.345400 / 2.43241 * 5.541321 + 7.465456 - 1.213132 * ( 3.1321324 * ( 2.4564654 / 9.767654 + 4.8745646 - ( 8.8754 * 5.4621 + ( 4.15213 * 3.153123 - ( 2.4432423 + 1.45413 ) ) ) ) ) )";
        //String infix = "2.5 + 8.96 / 2.35 * 3.45 - 1.11 * 2.3985 + 3.4258710 + ( 3.345400 / 2.43241 * 5.541321 + 7.465456 - 1.213132 * ( 3.1321324 * ( 2.4564654 / 9.767654 + 4.8745646 - ( 8.8754 * 5.4621 + ( 4.15213 * 3.153123 - ( 2.4432423 + 1.45413 ) ) / 2123 * 123.54 - 4654 + 5 * 4123 ) ) ) + 545412 / 34232.566 )";
        String infix = (new Scanner(System.in)).nextLine();
        StringTokenizer st = new StringTokenizer(infix," ");
        System.out.println("===========================");
        if (debug) System.out.println("\nShunting Yard Algorithm\n===========================");
        while(st.hasMoreTokens()) {
            String t = st.nextToken();
            printDebug(debug,t,count);
            if (t.equals("(")) {
                // (
                s.push(t);
            } else if (t.equals(")")) {
                // )
                while(!s.isEmpty() && !s.top().equals("(")) {
                    q.enqueue(s.pop());
                    printDebug(debug, s, q);
                }
                //remove "("
                s.pop();
                if (debug) System.out.println("Pop ( from stack!");
            } else if(t.equals("*")) {
                // *
                while(checkOP(2,s)) {
                    q.enqueue(s.pop());
                    printDebug(debug, s, q);
                }
                s.push(t);
            } else if (t.equals("/")) {
                // /
                while(checkOP(2,s)) {
                    q.enqueue(s.pop());
                    printDebug(debug, s, q);
                }
                s.push(t);
            } else if (t.equals("+")) {
                // +
                while(checkOP(1,s)) {
                    q.enqueue(s.pop());
                    printDebug(debug, s, q);
                }
                s.push(t);
            } else if (t.equals("-")) {
                // -
                while(checkOP(1,s)) {
                    q.enqueue(s.pop());
                    printDebug(debug, s, q);
                }
                s.push(t);
            } else {
                //number
                q.enqueue(t);
            }
            printDebug(debug, t, q, s);
            count++;
            if (debug) System.out.println("===========================");
        }

        while(!s.isEmpty()) {
            printDebug(debug, count);
            q.enqueue(s.pop());
			printDebug(debug, s, q);
            printDebug(debug, q, s);
            count++;
			if (debug) System.out.println("===========================");
        }
        if (debug) System.out.println("===========================\n");
        System.out.println("Infix form: " + infix + "\n"); 
        System.out.println("Convert to postfix form: " + q.toString(0) + "\n");
        if (debug) System.out.println("===========================\n"); 
        
        //Calculation
        if (debug) System.out.println("\nReverse Polish Notation (RPN) Calculation\n===========================");
        MyStackD sd = new MyStackD();
        count = 1;
        while(!q.isEmpty()) {
            double b = 0 ,a = 0;
            String t = q.peek();
            printDebug(debug, count);
            if(t.equals("*")) {
                b = sd.pop();
                a = sd.pop();
                sd.push(a*b);
            } else if (t.equals("/")) {
                b = sd.pop();
                a = sd.pop();
                sd.push(a/b);
            } else if (t.equals("+")) {
                b = sd.pop();
                a = sd.pop();
                sd.push(a+b);
            } else if (t.equals("-")) {
                b = sd.pop();
                a = sd.pop();
                sd.push(a-b);
            } else {
                sd.push(Double.parseDouble(t));
            }
            q.dequeue();
            printDebug(debug, t, b, a, q, sd);
            count++;
        }
        System.out.printf("The answer is : %.4f\n",sd.pop());
        if (debug) {
            long stop = System.currentTimeMillis();
            System.out.println("\nExecution Time: " + (stop-start) + " ms."); 
        }
        System.out.println("===========================");
    }

    
    static boolean checkOP(int fromToken, MyStack s) {
        // * / + - ()
        //convert to num
        //check piority
        if(!s.isEmpty()) {
            int p = 0;
            if (s.top().equals("(")) p = 0;
            else if (s.top().equals("*") || s.top().equals("/")) p = 2; 
            else if (s.top().equals("+") || s.top().equals("-")) p = 1; 
        
            if (p >= fromToken) return true;
            return false;
        }
        return false;
    }

    /*static void printDebug(boolean enable, MyQueue q) {
        if(enable) {
        System.out.println("Pop " + q.end() + " from stack!");
        System.out.println("Enqueue " + q.end() + " to queue!");
        }
    }*/

    static void printDebug(boolean enable, MyStack s, MyQueue q) {
        if(enable) {
        System.out.println("Pop " + q.end() + " from stack!");
        System.out.println("Enqueue " + q.end() + " to queue!");
        printDebug(enable, q, s);
        }
    }

    static void printDebug(boolean enable, String t, int count) {
        if(enable) {
            System.out.println("Steps [" + count +"]");
            System.out.println("Token: " + t + "\n");
        }
    }

    static void printDebug(boolean enable, int count) {
        if(enable) {
            System.out.println("Steps [" + count +"]\n");
        }
    }

    static void printDebug(boolean enable, String t, MyQueue q, MyStack s) {
        if (enable) {
            if (t.equals("(") || t.equals("*") || t.equals("/") || t.equals("+") || t.equals("-")) { 
				System.out.println("Push " + t + " to stack!");
            } else if (!s.isEmpty() && t.equals(")")) {
                System.out.println("Pop " + s.top() + " from stack!");
            } else System.out.println("Enqueue " + t + " to queue!");
            printDebug(true, q, s);
        }
    }

    static void printDebug(boolean enable, MyQueue q, MyStack s) {
        if (enable) {
            System.out.println("\n[ Status ]");
            System.out.println("Stack: " + s.toString(0));
            System.out.println("Queue: " + q.toString(0) + "\n");
        }
    }

    static void printDebug(boolean enable,String t, double b, double a, MyQueue q, MyStackD s) {
        if (enable) {
            if (t.equals("(") || t.equals("*") || t.equals("/") || t.equals("+") || t.equals("-")) {
            System.out.println("Found " + "\"" + t + "\"\n");
            System.out.println("Pop " + b + " & " + a + " from stack!");
                if(t.equals("*")) { 
                    System.out.println("[ Calculate ] " + a + " " + t + " " + b + " = " + (a*b));
                    System.out.println("Push " + (a*b) + " to stack!");
                } else if (t.equals("/")) { 
                    System.out.println("[ Calculate ] " + a + " " + t + " " + b + " = " + (a/b));
                    System.out.println("Push " + (a/b) + " to stack!");
                } else if (t.equals("+")) { 
                    System.out.println("[ Calculate ] " + a + " " + t + " " + b + " = " + (a+b));
                    System.out.println("Push " + (a+b) + " to stack!");
                } else if (t.equals("-")) { 
                    System.out.println("[ Calculate ] " + a + " " + t + " " + b + " = " + (a-b));
                    System.out.println("Push " + (a-b) + " to stack!");
                }
            }
            else System.out.println("Push " + t + " to stack!");
            System.out.println("\n[ Status ]");
            System.out.println("Stack: " + s.toString(0));
            System.out.println("Queue: " + q.toString(0)+ "\n");
            System.out.println("===========================");
        }
    }
}
