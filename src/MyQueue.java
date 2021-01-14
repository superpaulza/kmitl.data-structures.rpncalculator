public class MyQueue {
    class Node {
        String data;
        Node next;
        public Node(String _data) {
             data = _data;
             next = null;
        }
    }
 
    Node first = null,last; 
 
    void enqueue(String data) {
        if(isEmpty()) {
             first = last = new Node(data);
        }
        else {
             last.next = new Node(data);
             last = last.next;
        }
    }
 
    boolean isEmpty() {
        return first == null;
    }
 
    boolean IsFull() {
     return first != null;
    }
 
    Node dequeue() {
         Node p = first;
         if(!isEmpty()) {
             first = first.next;
         }
         return p;
    }

    String peek() {
        if(!isEmpty()) return first.data;
        return "";
    }

    String end() {
        if(!isEmpty()) return last.data;
        return "";
    }

    String toString(int d) {
        String str = "";
        Node p = first;
        if (isEmpty()) return "Empty";
        while(p != null) {
            str += p.data + " ";
            p = p.next;
        }
        return str;
    }
}
