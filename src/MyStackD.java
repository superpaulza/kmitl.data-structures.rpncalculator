public class MyStackD {
    class Node {
        double data;
        Node next;
        Node(double _data) {
            data = _data;
        }
    }

    Node top = null;
    
	boolean isFull() {
		return top != null;
	}

	//IsEmpty
	boolean isEmpty() {
		return top == null;
    }
    
    void push(double data) {
        Node p = new Node(data);
        p.next = top;
        top =  p;
    }

    double pop() {
        double data = top.data;
        top = top.next;
        return data;
    }

    double top() {
        return top.data;
    }

    String toString(int d) {
        String str = "";
        Node p = top;
        if (isEmpty()) return "Empty";
        while(p != null) {
            str += p.data + " ";
            p = p.next;
        }
        return str;
    } 
}
