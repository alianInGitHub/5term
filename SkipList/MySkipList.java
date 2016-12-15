package com.mycompany.app;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by anastasia on 07.12.16.
 */

public class MySkipList {

    private class Node{
        private volatile boolean isNull;
        private volatile int value;
        private volatile int index;
        private volatile Node next;
        private volatile Node lowerLayer;



        public Node(){
            isNull = true;
            this.index = 0;
            //this.next = this.lowerLayer = null;
        }

        public Node(int value, int index, Node next){
            isNull = false;
            this.value = value;
            this.index = index;
            this.next = next;
            this.lowerLayer = null;
        }

        public Node(int value, int index, Node next, Node lowerLayer){
            isNull = false;
            this.value = value;
            this.index = index;
            this.next = next;
            this.lowerLayer = lowerLayer;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getLowerLayer() {
            return lowerLayer;
        }

        public void setLowerLayer(Node lowerLayer) {
            this.lowerLayer = lowerLayer;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
            isNull = false;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void updateIndexes(){
            Node current = this;
            while (current.getNext() != null) {
                current.getNext().setIndex(current.getIndex() + 1);
                current = current.getNext();
            }
        }

        public boolean isNull() {
            return isNull;
        }
    }


  

    private static final int HMAX = 16;

    private int h;
    private Node[] list = null;
    private volatile int size = 0;
    private static Random random = new Random();


    public MySkipList(){
        h = 2 + random.nextInt(HMAX - 2);
        list = new Node[h];
        for(int i = 0; i < h; i++){
            list[i] = new Node();
        }
    }

    public void add(Integer a){
        size++;
        int level = h - 1;
        Node current = list[level];
        //skip null arrays
        while ((level >= 0) && current.isNull){
            level--;
            if(level >= 0)
                current = list[level];
        }
        if(level == -1){
            // initial adding to head
            if(list[0].isNull) {
                current = list[0];
                current.setValue(a);
                for (int i = 1; i < h; i++) {
                    list[i].setValue(a);
                    list[i].setLowerLayer(list[i - 1]);
                }
            }
        } else {

            //adding to list in other cases
            while (current != null) {
                //adding to head
                if (current.getValue() > a) {
                    while (current.getLowerLayer() != null) {
                        current = current.getLowerLayer();
                    }
                    Node newHead = new Node(a, 0, current);
                    list[0] = newHead;
                    while (current != null) {
                        current.setIndex(current.getIndex() + 1);
                        current = current.getNext();
                    }
                    for (int i = 1; i < h; i++) {
                        newHead = new Node(a, 0, list[i], list[i - 1]);
                        list[i] = newHead;
                        current = newHead.getNext();
                        while (current != null) {
                            current.setIndex(current.getIndex() + 1);
                            current = current.getNext();
                        }
                    }
                } else {
                    //adding in the middle
                    while ((current.getNext() != null) && (current.getNext().getValue() < a))
                        current = current.getNext();
                    if (current.getLowerLayer() != null)
                        current = current.getLowerLayer();
                    else {
                        // if we reached the bottom layer
                        // we have to add a node after current
                        if ((current.getNext() != null) && (current.getNext().getValue() == a))
                            return;
                        Node newNode = new Node(a, current.getIndex() + 1, current.getNext());
                        current.setNext(newNode);
                        current = newNode.getNext();
                        while (current != null) {
                            current.setIndex(current.getIndex() + 1);
                            current = current.getNext();
                        }

                        // adding to upper layers
                        // toss a coin
                        int p = random.nextInt(1);
                        level = 1;
                        //if head - add node
                        while ((p != 0) && (level < h)) {
                            current = list[level];
                            while ((current.getNext() != null) && (current.getNext().getValue() < a)) {
                                current = current.getNext();
                            }
                            current.setNext(new Node(a, current.getIndex() + 1, current.getNext(), newNode));
                            p = random.nextInt(1);
                            level++;
                        }
                        current = null;
                    }
                }
            }
        }
    }

    public void remove(int a){
        Node current = list[h - 1];
        if(current.getValue() == a){
            //remove head
            for(int level = 0; level < h; level++){
                if(list[level].getNext() == null){
                    list[level] = new Node();
                } else {
                    if((level == 0) || (list[level].getNext().getValue() == list[0].getValue())) {
                        list[level] = list[level].getNext();
                    } else {
                        list[level] = new Node(list[level - 1].getValue(), 0, list[level].getNext(), list[level - 1]);
                    }
                }
                list[level].setIndex(0);
                list[level].updateIndexes();

            }
        } else {
            while (current != null) {
                while ((current.getNext() != null) && (current.getNext().getValue() < a))
                    current = current.getNext();
                if ((current.getNext() != null) && (current.getNext().getValue() == a)) {
                    //remove
                    Node removing = current.getNext();
                    current.setNext(removing.getNext());
                    removing.setNext(null);
                    current.updateIndexes();
                    current = removing.getLowerLayer();
                } else
                    current = current.getLowerLayer();
            }
        }
        size--;
    }

    public Node getNode(int a){
        Node current = list[h - 1];
        if (current.getValue() > a)
            return null;
        while ((current != null) && (current.getValue() != a)){
            while ((current.getNext() != null) && (current.getNext().getValue() < a))
                current = current.getNext();
            if((current.getNext() != null) && (current.getNext().getValue() == a))
                return current.getNext();
            current = current.getLowerLayer();
        }
        if(current != null)
            return current;
        return null;
    }

    public int getPosition(int a){
        Node n = getNode(a);
        if(n != null)
            return n.getIndex();
        return -1;
    }

    public boolean contains(int a){
        if(getPosition(a) != -1)
            return true;
        return false;
    }

    public String getString(){
        Node n = list[0];
        String s = "";
        while (n != null){
            s += n.getValue() + "[" + n.getIndex() + "], ";
            n = n.getNext();
        }
        s = s.substring(0, s.length() - 1);
        s += "\n";
        return s;
    }

}
