package org.example;

public class SwapPairsSolution {
    public ListNode swapPairs(ListNode head) {
        if(head == null) {
            return null;
        }

        if(head.next == null) {
            return head;
        }

        ListNode result = head.next;
        ListNode curr = head;

        do {
            ListNode first = curr;
            ListNode second = curr.next;

            if(second.next != null && second.next.next != null) {
                first.next = second.next.next;
            }
            else {
                first.next = second.next;
            }

            curr = second.next; // reset curr
            second.next = first;
        } while (curr != null && curr.next != null);

        return result;
    }

    private void driver() {
        Main.FastReader reader = new Main.FastReader();

        int n = reader.nextInt();

        LinkedList list = new LinkedList();
        for(int i=0;i<n;i++) {
            int x = reader.nextInt();

            list.insert(new ListNode(x));
        }

        ListNode curr = new SwapPairsSolution().swapPairs(list.getHead());

        while(curr != null) {
            System.out.println(curr.val);

            curr = curr.next;
        }
    }
}

class LinkedList {
    private ListNode head, tail;

    public ListNode getHead() {
        return head;
    }

    public void insert(ListNode n) {
        if(head == null) {
            head = n;
            tail = n;

            return;
        }

        tail.next = n;
        tail = n;
    }
}