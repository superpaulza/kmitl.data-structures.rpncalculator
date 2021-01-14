public class MyStack {
    class Node {
        String data;
        Node next;
        Node(String _data) {
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
    
    void push(String data) {
        Node p = new Node(data);
        p.next = top;
        top =  p;
    }

    String pop() {
        String data = top.data;
        top = top.next;
        return data;
    }

    String top() {
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
